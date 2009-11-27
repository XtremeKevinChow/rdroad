namespace Magic.ERP.Orders
{
    using System;
    using System.Data;
    using System.Collections.Generic;
    using Magic.Framework.Data;
    using Magic.Framework.ORM;
    using Magic.Framework.ORM.Query;
    using Magic.ERP.Core;
    using Magic.Basis;
    using Magic.Framework.Utils;

    public partial class INVCheckHead : IApprovable, IWHTransHead
    {
        private log4net.ILog log = log4net.LogManager.GetLogger(typeof(INVCheckHead));

        public const string ORDER_TYPE_CHK = "SC0";
        public const string ORDER_TYPE_ADJ = "SA0";

        bool IWHTransHead.PreLockStock
        {
            get { return false; }
        }

        public INVCheckHead(string location, INVCheckType type, string note, int createUser)
        {
            this._orderNumber = ERPUtil.NextOrderNumber(ORDER_TYPE_CHK);
            this._orderTypeCode = ORDER_TYPE_CHK;
            this._locationCode = location;
            this._checkType = type;
            this._note = note;
            this._createUser = createUser;
            this._createTime = DateTime.Now;
            this._approveResult = ApproveStatus.UnApprove;
            this._approveUser = 0;
            this._approveTime = new DateTime(1900, 1, 1);
            this._currentLineNumber = "0000";
            this._status = INVCheckStatus.New;
        }

        /// <summary>
        /// ��һ���к���
        /// </summary>
        /// <returns></returns>
        public virtual string NextLineNumber()
        {
            int number = Magic.Framework.Utils.Cast.Int(this.CurrentLineNumber);
            number = number % 10 == 0 ? number + 10 : ((number + 9) / 10 * 10);
            string result = number.ToString().PadLeft(4, '0');
            this.CurrentLineNumber = result;
            return result;
        }

        /// <summary>
        /// ǩ�����
        /// </summary>
        /// <param name="session"></param>
        public virtual void OnApprove(ISession session)
        {
            if (this.ApproveResult == ApproveStatus.Reject)
            {
                if (this._orderTypeCode == INVCheckHead.ORDER_TYPE_CHK)
                    this.Status = INVCheckStatus.Confirm;
                else
                    this._status = INVCheckStatus.New;
                this.Update(session, "Status");
            }
        }

        /// <summary>
        /// ǩ�����
        /// </summary>
        /// <param name="session"></param>
        public virtual void PostApprove(ISession session)
        {
            try
            {
                session.BeginTransaction();
                this.Close(session);
                session.Commit();
            }
            catch (Exception er)
            {
                log.Error((this._orderTypeCode == ORDER_TYPE_CHK ? "sto chk" : "sto adj") + this.OrderNumber + " was approved, error occurs while trying to close this order automatically", er);
                session.Rollback();
            }
        }

        /// <summary>
        /// ���õ������ͣ�ֱ�Ӵ���ĳ�����׵ĵ������ͣ�
        /// �����Ա�˻������õ�������Ϊ����������
        /// </summary>
        public virtual string RefOrderType
        {
            get
            {
                return " ";
            }
        }

        public virtual string RefOrderNumber
        {
            get
            {
                return " ";
            }
        }

        /// <summary>
        /// ԭʼ��������
        /// �����Ա�˻���ԭʼ��������Ϊ��Ա���������۶���������
        /// </summary>
        public virtual string OriginalOrderType
        {
            get { return " "; }
        }

        public virtual string OrginalOrderNumber
        {
            get
            {
                return " ";
            }
        }

        /// <summary>
        /// ����ִ����ϵĻص�����
        /// </summary>
        /// <param name="session"></param>
        public virtual void AfterTransaction(ISession session)
        {
            //����̵��λ������
            if (this._orderTypeCode == ORDER_TYPE_CHK)
            {
                IList<WHArea> areas = session.CreateObjectQuery(@"
select 1 from WHArea where AreaCode in(
    select AreaCode from INVCheckWh where OrderNumber=?ordNumber
)")
                    .Attach(typeof(WHArea)).Attach(typeof(INVCheckWh))
                    .SetValue("?ordNumber", this._orderNumber, EntityManager.GetPropMapping(typeof(INVCheckWh), "OrderNumber").DbTypeInfo)
                    .List<WHArea>();
                foreach (WHArea area in areas)
                {
                    area.IsLocked = false;
                    area.Update(session, "IsLocked");
                }
            }
        }
        /// <summary>
        /// ȡ������ϸ
        /// </summary>
        /// <param name="session"></param>
        /// <returns></returns>
        public virtual IList<IWHTransLine> GetLines(ISession session)
        {
            //ֻ����������ȵ��̵���ϸ����ⷿ����
            IList<INVCheckLine> lines = session.CreateObjectQuery(@"
select 1 from INVCheckLine where BeforeQty<>CurrentQty and OrderNumber=?ordNum")
                .Attach(typeof(INVCheckLine))
                .SetValue("?ordNum", this._orderNumber, "OrderNumber")
                .OrderBy("SKUID").OrderBy("LineNumber")
                .List<INVCheckLine>();
            IList<IWHTransLine> result = new List<IWHTransLine>(lines.Count);
            foreach (INVCheckLine line in lines)
                result.Add(line);
            return result;
        }

        public void RemoveArea(ISession session, string area)
        {
            if (this._status != INVCheckStatus.New)
                throw new Exception("���ݲ����½�״̬���޷�ִ�иò���");
            if (string.IsNullOrEmpty(area) || area.Trim().Length <= 0) return;
            INVCheckWh.Delete(session, this.OrderNumber, area);
        }
        public void AddArea(ISession session, string area)
        {
            if (this._status != INVCheckStatus.New)
                throw new Exception("���ݲ����½�״̬���޷�ִ�иò���");
            if (string.IsNullOrEmpty(area) || area.Trim().Length <= 0) return;
            INVCheckWh wh = new INVCheckWh();
            wh.OrderNumber = this._orderNumber;
            wh.AreaCode = area;
            wh.Create(session);
        }
        public void ConfirmCheckOrder(ISession session)
        {
            if (this._status != INVCheckStatus.New)
                throw new Exception("���ݲ����½�״̬���޷�ִ�иò���");
            if (session.CreateEntityQuery<INVCheckWh>()
                .Where(Exp.Eq("OrderNumber", this._orderNumber))
                .Count() <= 0)
                throw new Exception("����û��ѡ��Ҫ�̵�Ŀ�λ");

            #region �����̵��λ
            IList<WHArea> areas = session.CreateObjectQuery(@"
select 1 from WHArea where AreaCode in(
    select AreaCode from INVCheckWh where OrderNumber=?ordNumber
)")
                .Attach(typeof(WHArea)).Attach(typeof(INVCheckWh))
                .SetValue("?ordNumber", this._orderNumber, EntityManager.GetPropMapping(typeof(INVCheckWh), "OrderNumber").DbTypeInfo)
                .List<WHArea>();
            foreach (WHArea area in areas)
            {
                area.IsLocked = true;
                area.Update(session, "IsLocked");
            }
            #endregion

            #region �̵㵥��ϸ
            //TODO: 
            //1. ��ϸ��¼���޷�����9999
            //2. ���϶��������״̬���̵�ʱ��������Щ������
            DbSession dbSession = session.DbSession as DbSession;
            Type type = typeof(INVCheckLine), stoType = typeof(StockDetail);
            System.Text.StringBuilder sql = new System.Text.StringBuilder();
            sql.Append("INSERT INTO ")
                .Append(EntityManager.GetEntityMapping(type).TableName)
                .Append("(")
                    .Append(EntityManager.GetPropMapping(type, "OrderNumber").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "LineNumber").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "SKUID").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "UnitID").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "LocationCode").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "AreaCode").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "SectionCode").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "BeforeQty").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "CurrentQty").ColumnName).Append(",")
                    .Append(EntityManager.GetPropMapping(type, "StockDetailID").ColumnName)
                .Append(") SELECT '")
                .Append(this._orderNumber).Append("' AS ORDNUM,")
                .Append("TRIM(TO_CHAR(ROWNUM,'0000')) AS LINENUM,")
                .Append("T.SKU,0 AS UNIT,T.LOC_C,T.AREA_C,T.SEC_C,T.BEF_QTY,0 AS CUR_QTY,STODID ")
                .Append("FROM(SELECT ")
                        .Append(EntityManager.GetPropMapping(stoType, "SKUID").ColumnName).Append(" AS SKU,")
                        .Append(EntityManager.GetPropMapping(stoType, "LocationCode").ColumnName).Append(" AS LOC_C,")
                        .Append(EntityManager.GetPropMapping(stoType, "AreaCode").ColumnName).Append(" AS AREA_C,")
                        .Append(EntityManager.GetPropMapping(stoType, "SectionCode").ColumnName).Append(" AS SEC_C,")
                        .Append(EntityManager.GetPropMapping(stoType, "StockQty").ColumnName).Append(" AS BEF_QTY,")
                        .Append(EntityManager.GetPropMapping(stoType, "StockDetailID").ColumnName).Append(" AS STODID ")
                    .Append("FROM ").Append(EntityManager.GetEntityMapping(stoType).TableName).Append(" ")
                    .Append("WHERE ")
                        .Append(EntityManager.GetPropMapping(stoType, "LocationCode").ColumnName).Append("='").Append(this._locationCode).Append("'")
                        .Append(" AND ").Append(EntityManager.GetPropMapping(stoType, "AreaCode").ColumnName).Append(" IN(")
                            .Append("SELECT ").Append(EntityManager.GetPropMapping(typeof(INVCheckWh), "AreaCode").ColumnName).Append(" ")
                            .Append("FROM ").Append(EntityManager.GetEntityMapping(typeof(INVCheckWh)).TableName).Append(" ")
                            .Append("WHERE ").Append(EntityManager.GetPropMapping(typeof(INVCheckWh), "OrderNumber").ColumnName).Append("='").Append(this._orderNumber).Append("'")
                        .Append(") ")
                    .Append("ORDER BY ")
                        .Append(EntityManager.GetPropMapping(stoType, "AreaCode").ColumnName).Append(",")
                        .Append(EntityManager.GetPropMapping(stoType, "SectionCode").ColumnName).Append(",")
                        .Append(EntityManager.GetPropMapping(stoType, "SKUID").ColumnName)
                .Append(")T");
            dbSession.ExecuteNonQuery(sql.ToString());
            #endregion

            this._status = INVCheckStatus.Confirm;
            this.Update(session, "Status");
        }
        public void Release(ISession session)
        {
            #region ���
            if (this.OrderTypeCode == ORDER_TYPE_CHK && this._status != INVCheckStatus.Confirm)
                throw new Exception("״̬Ϊȷ�ϵ��̵㵥�ſ���ִ�з�������");
            if (this.OrderTypeCode == ORDER_TYPE_ADJ && this._status != INVCheckStatus.New)
                throw new Exception("�½�״̬�Ŀ��������ſ���ִ�з�������");
            if (session.CreateEntityQuery<INVCheckLine>()
                .Where(Exp.Eq("OrderNumber", this._orderNumber))
                .SetLastOffset(1)
                .Count() <= 0)
                throw new Exception("û����ϸ���޷�ִ�з�������");
            #endregion

            this._status = INVCheckStatus.Release;
            this._approveResult = ApproveStatus.UnApprove;
            this.Update(session, "Status", "ApproveResult");
            ERPUtil.ApproveThis(session, this);
        }
        public void Close(ISession session)
        {
            if (this._status != INVCheckStatus.Release || this._approveResult != ApproveStatus.Approve)
                throw new Exception("ֻ�з�������̵㵥�ܹ�ִ�йرղ���");
            ERPUtil.CommitWHTrans(session, this);
            this._status = INVCheckStatus.Close;
            this.Update(session, "Status");
        }

        public static DataSet Query(ISession session, bool requireCount, out int count, string location, string sku, string itemCode, string itemName, string color, string size, string area, string section, int pageIndex, int pageSize)
        {
            ObjectQuery query = session.CreateObjectQuery(@"
select sku.BarCode as BarCode,i.ItemCode as ItemCode,i.ItemName as ItemName
    ,sku.ColorCode as ColorCode,color.ColorText as ColorText,sku.SizeCode as SizeCode
    ,sto.AreaCode as AreaCode,sto.SectionCode as SectionCode,sto.StockQty as StockQty,sto.FrozenQty as FrozenQty
    ,sto.StockDetailID as StockDetailID
from StockDetail sto
inner join ItemSpec sku on sku.SKUID=sto.SKUID
inner join ItemMaster i on i.ItemID=sku.ItemID
left join ItemColor color on color.ColorCode=sku.ColorCode
where sto.LocationCode=?loc
order by i.ItemCode,sku.ColorCode,sku.SizeCode,sto.AreaCode,sto.SectionCode")
                .Attach(typeof(StockDetail))
                .Attach(typeof(ItemSpec)).Attach(typeof(ItemMaster)).Attach(typeof(ItemColor))
                .SetValue("?loc", location, "sto.LocationCode")
                .SetPage(pageIndex, pageSize);

            if (!string.IsNullOrEmpty(area))
                query.And(Exp.Eq("sto.AreaCode", area));
            if (section.Trim().Length > 0) query.And(Exp.Like("sto.SectionCode", "%" + section.Trim().ToUpper() + "%"));
            if (sku.Trim().Length > 0) query.And(Exp.Like("sku.BarCode", "%" + sku.Trim().ToUpper() + "%"));
            if (itemCode.Trim().Length > 0) query.And(Exp.Like("i.ItemCode", "%" + itemCode.Trim().ToUpper() + "%"));
            if (itemName.Trim().Length > 0) query.And(Exp.Like("i.ItemName", "%" + itemName.Trim() + "%"));
            if (color.Trim().Length > 0) query.And(Exp.Like("sku.ColorCode", "%" + color.Trim().ToUpper() + "%"));
            if (size.Trim().Length > 0) query.And(Exp.Like("sku.SizeCode", "%" + size.Trim().ToUpper() + "%"));

            count = 0;
            if (requireCount) count = query.Count();

            return query.DataSet();
        }
        public INVCheckLine NewLineAdj(ISession session, int stoDetailId, decimal adjQty)
        {
            StockDetail sto = StockDetail.Retrieve(session, stoDetailId);
            if (sto == null)
            {
                log.ErrorFormat("add stock adj line for {0}, stock detail id {1} not exists", this.OrderNumber, stoDetailId);
                throw new Exception("ѡ��Ŀ�������");
            }

            INVCheckLine line = new INVCheckLine();
            line.OrderNumber = this.OrderNumber;
            line.StockDetailID = stoDetailId;
            line.LocationCode = sto.LocationCode;
            line.AreaCode = sto.AreaCode;
            line.SectionCode = sto.SectionCode;
            line.BeforeQty = sto.StockQty;
            line.CurrentQty = adjQty;
            line.SKUID = sto.SKUID;
            line.UnitID = 0;

            return line;
        }
        /// <summary>
        /// �������̵���ϸ��lines��ÿ������������ú�LineNumber��CurrentQty��2������
        /// </summary>
        /// <param name="session"></param>
        /// <param name="orderNumber"></param>
        /// <param name="lines"></param>
        public void UpdateLines(ISession session, IList<INVCheckLine> lines)
        {
            if (lines == null || lines.Count <= 0) return;
            foreach (INVCheckLine line in lines)
            {
                INVCheckLine lineToSave = INVCheckLine.Retrieve(session, this.OrderNumber, line.LineNumber);
                if (lineToSave == null) throw new Exception("����̵㵥" + this.OrderNumber + "�в�������ϸ��Ŀ" + line.LineNumber);
                lineToSave.CurrentQty = line.CurrentQty;
                lineToSave.Update(session, "CurrentQty");
            }
        }
        public void ClearCheckQty(ISession session)
        {
            session.CreateEntityQuery<INVCheckLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber))
                .Update(new string[] { "CurrentQty" }, new object[] { 0M });
        }
        /// <summary>
        /// ����������ӻ������ϸ
        /// <para>��StockDetailID�ж�INVCheckLine��ϸ�����Ƿ��Ѿ����ڣ������������������������</para>
        /// <para>�����������lines�ж���ĸ������Ա��붼�Ѿ����úã�����Ǹ��£�ֻ����CurrentQty����</para>
        /// </summary>
        /// <param name="session"></param>
        /// <param name="lines"></param>
        public void CreateOrUpdateLines(ISession session, IList<INVCheckLine> lines)
        {
            if (this.Status != INVCheckStatus.New && this.Status != INVCheckStatus.Confirm)
            {
                log.WarnFormat("status of sto {0} {1} is {2}, attemping to update lines is denied", this.OrderTypeCode == ORDER_TYPE_CHK ? "chk" : "adj", this.OrderNumber, this.Status.ToString());
                throw new Exception("�����Ը���");
            }
            bool needUpdateHead = false;
            foreach (INVCheckLine line in lines)
            {
                INVCheckLine temp = this.GetLine(session, line.StockDetailID);
                if (temp != null)
                {
                    temp.CurrentQty = line.CurrentQty;
                    temp.Update(session, "CurrentQty");
                }
                else
                {
                    line.LineNumber = this.NextLineNumber();
                    needUpdateHead = true;
                    line.Create(session);
                }
            }
            if (needUpdateHead) this.Update(session, "CurrentLineNumber");
        }
        private INVCheckLine GetLine(ISession session, int stodid)
        {
            if (stodid <= 0) return null;
            IList<INVCheckLine> lines = session.CreateEntityQuery<INVCheckLine>()
                .Where(Exp.Eq("OrderNumber", this.OrderNumber) & Exp.Eq("StockDetailID", stodid))
                .SetLastOffset(1)
                .List<INVCheckLine>();
            return lines == null || lines.Count <= 0 ? null : lines[0];
        }
    }
}