using Magic.Framework;
using Magic.Framework.ORM;

namespace Magic.Sys
{
    public partial class Province
    {
        public static SimpleJson GetJSON(ISession session, int id)
        {
            Province pro = Province.Retrieve(session, id);
            if (pro == null) return new SimpleJson().HandleError(string.Format("省份{0}不存在", id));
            SimpleJson json = new SimpleJson()
                .Add("parent", "")
                .Add("type", "p")
                .Add("id", pro.ProvinceId)
                .Add("code", pro.Code)
                .Add("name", pro.Name)
                .Add("alias", pro.Alias)
                .Add("desc", pro);
            return json;
        }
        public SimpleJson GetJSON()
        {
            SimpleJson json = new SimpleJson()
                .Add("parent", "")
                .Add("type", "p")
                .Add("id", this.ProvinceId)
                .Add("code", this.Code)
                .Add("name", this.Name)
                .Add("alias", this.Alias)
                .Add("desc", this);
            return json;
        }
        public override string ToString()
        {
            return this.Name + " (" + this.Code + ")";
        }
    }
}