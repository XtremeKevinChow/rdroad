using Magic.Framework;
using Magic.Framework.ORM;

namespace Magic.Sys
{
    public partial class City
    {
        public static SimpleJson GetJSON(ISession session, int id)
        {
            City city = City.Retrieve(session, id);
            if (city == null) return new SimpleJson().HandleError(string.Format("城市{0}不存在", id));
            SimpleJson json = new SimpleJson()
                .Add("parent", city.ProvinceId)
                .Add("type", "c")
                .Add("id", city.CityId)
                .Add("code", city.CityCode)
                .Add("name", city.Name)
                .Add("desc", city);
            return json;
        }
        public SimpleJson GetJSON()
        {
            SimpleJson json = new SimpleJson()
                .Add("parent", this.ProvinceId)
                .Add("type", "c")
                .Add("id", this.CityId)
                .Add("code", this.CityCode)
                .Add("name", this.Name)
                .Add("desc", this);
            return json;
        }
        public override string ToString()
        {
            return this.Name + " (" + this.CityCode+")";
        }
    }
}