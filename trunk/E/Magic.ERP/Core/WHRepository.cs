using System.Collections.Generic;
using System.Data;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;

namespace Magic.ERP.Core
{
    public class WHRepository
    {
        private DataSet _ds;

        public WHRepository(ISession session)
        {
            DataTable table;
            _ds = new DataSet("Location");
            DataSet p = session.CreateObjectQuery("select LocationCode as LocationCode,CompanyID as CompanyID,Name as Name,Status as Status,Text as Text,Address as Address,ZipCode as ZipCode,Contact as Contact,Phone as Phone,FaxNumber as FaxNumber from WHLocation order by LocationCode")
                .Attach(typeof(WHLocation)).Where(Exp.In("Status", WHStatus.Disable, WHStatus.Enable))
                .DataSet();
            table = p.Tables[0];
            table.TableName = "l";
            p.Tables.Clear();
            _ds.Tables.Add(table);
            table.Constraints.Add("PK_l", table.Columns["LocationCode"], false);
            DataSet c = session.CreateObjectQuery("select AreaCode as AreaCode,LocationCode as LocationCode,ParentArea as ParentArea,Name as Name,Text as Text,Status as Status,AreaCapacity as AreaCapacity,HasSection as HasSection,IsTransArea as IsTransArea,IsReservedArea as IsReservedArea,AllowDelete as AllowDelete,AllowChild as AllowChild,UseFixCost as UseFixCost,CostFixValue as CostFixValue,CostTransRate as CostTransRate,FixedComsumeValue as FixedComsumeValue,IsQC as IsQC,IsNonFormal as IsNonFormal,IsScrap as IsScrap from WHArea")
                .Attach(typeof(WHArea)).Where(Exp.In("Status", WHStatus.Disable, WHStatus.Enable) & Exp.Eq("IsReservedArea", false))
                .DataSet();
            table = c.Tables[0];
            table.TableName = "a";
            c.Tables.Clear();
            _ds.Tables.Add(table);
            table.Constraints.Add("PK_a", table.Columns["AreaCode"], false);
            DataSet d = session.CreateObjectQuery("select AreaCode as AreaCode,SectionCode as SectionCode,Status as Status,SectionCapacity as SectionCapacity,Text as Text from WHSection")
                .Attach(typeof(WHSection)).Where(Exp.In("Status", WHStatus.Disable, WHStatus.Enable))
                .DataSet();
            table = d.Tables[0];
            table.TableName = "s";
            d.Tables.Clear();
            _ds.Tables.Add(table);
            //table.PrimaryKey = new DataColumn[] { table.Columns["AreaCode"], table.Columns["SectionCode"] };
        }

        public IList<WHLocation> GetLocations()
        {
            DataRow[] rows = _ds.Tables["l"].Select("", "LocationCode ASC");
            IList<WHLocation> lists = new List<WHLocation>();
            if (rows == null || rows.Length <= 0) return lists;
            foreach (DataRow row in rows)
                lists.Add(WHLocation.Row2Entity(row));
            return lists;
        }
        public WHLocation GetLocation(string locationCode)
        {
            if (string.IsNullOrEmpty(locationCode) || locationCode.Trim().Length <= 0) return null;
            DataRow[] rows = _ds.Tables["l"].Select("LocationCode='" + locationCode.Trim() + "'");
            if (rows!=null && rows.Length > 0) return WHLocation.Row2Entity(rows[0]);
            else return null;
        }
        public WHArea GetArea(string areaCode)
        {
            if (string.IsNullOrEmpty(areaCode) || areaCode.Trim().Length <= 0) return null;
            DataRow[] rows = _ds.Tables["a"].Select("AreaCode='" + areaCode.Trim() + "'");
            if (rows!=null && rows.Length > 0) return WHArea.Row2Entity(rows[0]);
            else return null;
        }
        public IList<WHArea> GetAreas(string locationCode)
        {
            IList<WHArea> lists = new List<WHArea>();
            if (string.IsNullOrEmpty(locationCode) || locationCode.Trim().Length <= 0) return lists;
            DataRow[] rows = _ds.Tables["a"].Select("LocationCode='" + locationCode.Trim() + "'", "AreaCode ASC");
            if (rows == null || rows.Length <= 0) return lists;
            foreach (DataRow row in rows)
                lists.Add(WHArea.Row2Entity(row));
            return lists;
        }
        public WHSection GetSection(string areaCode, string sectionCode)
        {
            if (string.IsNullOrEmpty(sectionCode) || sectionCode.Trim().Length <= 0 || string.IsNullOrEmpty(areaCode) || areaCode.Trim().Length <= 0) return null;
            DataRow[] rows = _ds.Tables["s"].Select("AreaCode='" + areaCode.Trim() + "' and SectionCode='" + sectionCode.Trim() + "'");
            if (rows!=null && rows.Length > 0) return WHSection.Row2Entity(rows[0]);
            else return null;
        }
        public IList<WHSection> GetSections(string areaCode)
        {
            IList<WHSection> lists = new List<WHSection>();
            if (string.IsNullOrEmpty(areaCode) || areaCode.Trim().Length <= 0) return lists;
            DataRow[] rows = _ds.Tables["s"].Select("AreaCode='" + areaCode.Trim() + "'", "SectionCode ASC");
            if (rows == null || rows.Length <= 0) return lists;
            foreach (DataRow row in rows)
                lists.Add(WHSection.Row2Entity(row));
            return lists;
        }
    }
}
