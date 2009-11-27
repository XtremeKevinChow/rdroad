using System.Collections.Generic;
using System.Data;
using Magic.Framework.Data;
using Magic.Framework.ORM;

namespace Magic.Sys
{
    public class RegionRepository
    {
        private DataSet _ds;

        public RegionRepository(ISession session)
        {
            DataTable table;
            _ds = new DataSet("Province");
            DataSet p = session.CreateObjectQuery("select PRV_ID as Province_ID,PRV_CODE as \"Code\",PRV_NAME as \"Name\",PRV_ALIAS as \"Alias\" from Province")
                .Attach(typeof(Province))
                .DataSet();
            table = p.Tables[0];
            table.TableName = "p";
            table.Constraints.Add("PK_p", table.Columns["Province_ID"], true);
            p.Tables.Clear();
            _ds.Tables.Add(table);
            DataSet c = session.CreateObjectQuery("select PRV_ID as Province_ID,CITY_ID as City_ID,CITY_CODE as City_Code,CITY_NAME as \"Name\" from City")
                .Attach(typeof(City))
                .DataSet();
            table = c.Tables[0];
            table.TableName = "c";
            table.Constraints.Add("PK_c", table.Columns["City_ID"], true);
            c.Tables.Clear();
            _ds.Tables.Add(table);
            DataSet d = session.CreateObjectQuery("select CITY_ID as City_ID,DST_ID as District_ID,DST_NAME as \"Name\",DST_ZIPCODE as Zip_Code,DST_SHIP_TO as Door2Door from District")
                .Attach(typeof(District))
                .DataSet();
            table = d.Tables[0];
            table.TableName = "d";
            table.Constraints.Add("PK_d", table.Columns["District_ID"], true);
            d.Tables.Clear();
            _ds.Tables.Add(table);
        }

        public IList<Province> GetProvinces()
        {
            return Province.Row2Entity(_ds.Tables["p"].Select("", "Name ASC"));
        }
        public Province GetProvince(int id)
        {
            if (id <= 0) return null;
            DataRow[] rows = _ds.Tables["p"].Select("Province_ID=" + id);
            if (rows.Length > 0) return Province.Row2Entity(rows[0]);
            else return null;
        }
        public City GetCity(int id)
        {
            if (id <= 0) return null;
            DataRow[] rows = _ds.Tables["c"].Select("City_ID=" + id);
            if (rows.Length > 0) return City.Row2Entity(rows[0]);
            else return null;
        }
        public IList<City> GetCities(int provinceId)
        {
            if (provinceId <= 0) return null;
            DataRow[] rows = _ds.Tables["c"].Select("Province_ID=" + provinceId, "Name ASC");
            if (rows.Length > 0) return City.Row2Entity(rows);
            else return null;
        }
        public District GetDistrict(int id)
        {
            if (id <= 0) return null;
            DataRow[] rows = _ds.Tables["d"].Select("District_ID=" + id);
            if (rows.Length > 0) return District.Row2Entity(rows[0]);
            else return null;
        }
        public IList<District> GetDistricts(int cityId)
        {
            if (cityId <= 0) return null;
            DataRow[] rows = _ds.Tables["d"].Select("City_Id=" + cityId, "Name ASC");
            if (rows.Length > 0) return District.Row2Entity(rows);
            else return null;
        }
    }
}
