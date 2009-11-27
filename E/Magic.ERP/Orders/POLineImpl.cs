namespace Magic.ERP.Orders
{
    using System;
    using System.Data;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Magic.Framework.ORM.Mapping;

    public partial class POLine
    {
        private log4net.ILog log = log4net.LogManager.GetLogger(typeof(POLine));

        public static string POLineStatusText(POLineStatus statusValue)
        {
            switch (statusValue)
            {
                case POLineStatus.Open: return "有效";
                case POLineStatus.Cancel: return "取消";
                case POLineStatus.Close: return "已完成";
            }
            return "";
        }

        public bool UnfinishedReceiveQtyChange(ISession session, decimal diffQty)
        {
            //使用DbSession，避免并发冲突
            if (diffQty == 0M) return true;
            //if (diffQty < 0M && diffQty + this.ReceivableQty() < 0M) return false;
            //if (diffQty > 0M && diffQty > this.ReceivableQty()) return false;
            DbSession dbSession = session.DbSession as DbSession;
            System.Text.StringBuilder builder = new System.Text.StringBuilder();
            builder.Append("update ")
                .Append(EntityManager.GetEntityMapping(typeof(POLine)).TableName)
                .Append(" set ")
                .Append(EntityManager.GetPropMapping(typeof(POLine), "UnfinishedReceiveQty").ColumnName)
                .Append("=")
                .Append(EntityManager.GetPropMapping(typeof(POLine), "UnfinishedReceiveQty").ColumnName)
                .Append("+:qty where ")
                .Append(EntityManager.GetPropMapping(typeof(POLine), "OrderNumber").ColumnName)
                .Append("=:ordNum and ")
                .Append(EntityManager.GetPropMapping(typeof(POLine), "LineNumber").ColumnName)
                .Append("=:lineNum");
            IDbCommand command = dbSession.CreateSqlStringCommand(builder.ToString());

            dbSession.AddParameter(command, ":qty", EntityManager.GetPropMapping(typeof(POLine), "UnfinishedReceiveQty").DbTypeInfo, diffQty);
            dbSession.AddParameter(command, ":ordNum", EntityManager.GetPropMapping(typeof(POLine), "OrderNumber").DbTypeInfo, this._orderNumber);
            dbSession.AddParameter(command, ":lineNum", EntityManager.GetPropMapping(typeof(POLine), "LineNumber").DbTypeInfo, this._lineNumber);
            return dbSession.ExecuteNonQuery(command) > 0;
        }

        public bool ReceiveFinish(ISession session, decimal rcvQty, decimal iqcQty)
        {
            //使用DbSession，避免并发冲突
            if (rcvQty == 0M && iqcQty == 0M) return true;
            if (this._lineStatus != POLineStatus.Open)
                throw new Exception(string.Format("订单行({0}-{1})不是Open状态，无法进行收货", this._orderNumber, this._lineNumber));
            DbSession dbSession = session.DbSession as DbSession;
            System.Text.StringBuilder builder = new System.Text.StringBuilder();
            builder.Append("update ")
                .Append(EntityManager.GetEntityMapping(typeof(POLine)).TableName)
                .Append(" set ")
                .Append(EntityManager.GetPropMapping(typeof(POLine), "UnfinishedReceiveQty").ColumnName)
                .Append("=")
                .Append(EntityManager.GetPropMapping(typeof(POLine), "UnfinishedReceiveQty").ColumnName)
                .Append("-:qty1,")
                .Append(EntityManager.GetPropMapping(typeof(POLine), "IQCQty").ColumnName)
                .Append("=")
                .Append(EntityManager.GetPropMapping(typeof(POLine), "IQCQty").ColumnName)
                .Append("+:qty2,")
                .Append(EntityManager.GetPropMapping(typeof(POLine), "ReceiveQty").ColumnName)
                .Append("=")
                .Append(EntityManager.GetPropMapping(typeof(POLine), "ReceiveQty").ColumnName)
                .Append("+:qty3 where ")
                .Append(EntityManager.GetPropMapping(typeof(POLine), "OrderNumber").ColumnName)
                .Append("=:ordNum and ")
                .Append(EntityManager.GetPropMapping(typeof(POLine), "LineNumber").ColumnName)
                .Append("=:lineNum");
            IDbCommand command = dbSession.CreateSqlStringCommand(builder.ToString());

            dbSession.AddParameter(command, ":qty1", EntityManager.GetPropMapping(typeof(POLine), "UnfinishedReceiveQty").DbTypeInfo, iqcQty);
            dbSession.AddParameter(command, ":qty2", EntityManager.GetPropMapping(typeof(POLine), "IQCQty").DbTypeInfo, iqcQty);
            dbSession.AddParameter(command, ":qty3", EntityManager.GetPropMapping(typeof(POLine), "ReceiveQty").DbTypeInfo, rcvQty);
            dbSession.AddParameter(command, ":ordNum", EntityManager.GetPropMapping(typeof(POLine), "OrderNumber").DbTypeInfo, this._orderNumber);
            dbSession.AddParameter(command, ":lineNum", EntityManager.GetPropMapping(typeof(POLine), "LineNumber").DbTypeInfo, this._lineNumber);
            bool result = dbSession.ExecuteNonQuery(command) > 0;

            //尝试自动关闭PO行，PO
            if (this._receiveQty + rcvQty >= this._purchaseQty)
            {
                //收货数量大于等于采购数量，自动关闭PO行
                //TODO: 是否采用参数配置这个动作
                this._lineStatus = POLineStatus.Close;
                this.Update(session, "LineStatus");
                //所有PO行状态为关闭或取消，则自动关闭PO
                if (session.CreateEntityQuery<POLine>()
                    .Where(Exp.Eq("OrderNumber", this._orderNumber) & Exp.Eq("LineStatus", POLineStatus.Open))
                    .Count() <= 0)
                {
                    POHead head = POHead.Retrieve(session, this._orderNumber);
                    if (head != null)
                    {
                        head.Status = POStatus.Close;
                        head.Update(session, "Status");
                    }
                }
            }

            return true;
        }

        public decimal ReceivableQty()
        {
            decimal qty = this._purchaseQty - this._receiveQty - this._unfinishedReceiveQty;
            return qty <= 0M ? 0M : qty;
        }
    }
}