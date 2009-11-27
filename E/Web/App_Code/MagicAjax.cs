using System;
using System.Collections.Generic;
using System.Data;
using System.Reflection;
using System.Text;

using Magic.Sys;

using Magic.Framework;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Query;
using Magic.Framework.Utils;
using Magic.Security;
using Magic.ERP.Core;
using Magic.ERP;
using Magic.ERP.Orders;


/// <summary>
/// AjaxUtil 的摘要说明
/// </summary>
public class MagicAjax
{
    private static log4net.ILog log = log4net.LogManager.GetLogger("ERP.Web");

    #region InvokeAjaxCall
    public static SimpleJson InvokeAjaxCall2Json()
    {
        string action = WebUtil.Param("action");

        switch (action)
        {
            #region  Org
            case "LoadOrgTree":
                return LoadOrgTree();
            case "LoadOrg":
                return LoadOrg();
            case "SaveOrg":
                return SaveOrg();
            case "DeleteOrg":
                DeleteOrg();
                return null;
            case "SelectOrg":
                return SelectOrg();
            #endregion

            #region User
            case "SelectUser":
                return SelectUser();
            #endregion

            #region Region: 省、城市、区县
            case "LoadRegionTree":
                return LoadRegionTree();
            case "SelectRegion":
                return SelectRegion();
            case "LoadRegion":
                return LoadRegion();
            case "DeleteRegion":
                DeleteRegion();
                break;
            case "SaveRegion":
                return SaveRegion();
            #endregion

            #region 库位：Location, Area, Section
            case "LoadWHLocTree":
                return LoadWHLocTree();
            case "SelectWHLoc":
                return null;
            case "LoadWHLoc":
                return LoadWHLoc();
            case "DeleteWHLoc":
                DeleteWHLoc();
                break;
            case "SaveWHLoc":
                return SaveWHLoc();
            #endregion

            #region Operation
            case "LoadOperationTree":
                return LoadOperationTree();
            case "LoadOperation":
                return LoadOperation();
            case "SaveOperation":
                return SaveOperation();
            case "DeleteOperation":
                return DeleteOperation();
            case "SelectOperation":
                return SelectOperation();
            #endregion

            #region Permission Assign
            case "LoadPrmsTree":
                return LoadPermissionTree();
            case "SavePrmsTree":
                return SavePermissionTree();
            #endregion

            #region UserGroup Tree
            case "LoadUserGroupTree":
                return LoadUserGroupTree();
            case "SaveGroup":
                return SaveUserGroup();
            case "LoadGroup":
                return LoadUserGroup();
            case "DeleteGroup":
                return DeleteUserGroup();
            case "SelectUserGroup":
                return SelectUserGroupTree();
            case "MsgSubscriberSelectUserGroup":
                return MsgSubscriberUserGroupTree();
            #endregion

            #region Navigator
            //case "LoadNavigatorTree":
            //    return LoadNavigatorTree();
            //case "SaveNavigator":
            //    return SaveNavigator();
            //case "LoadNavigator":
            //    return LoadNavigator();
            //case "DeleteNavigator":
            //    return DeleteNavigator();
            //case "SelectNavigator":
            //    return SelectNavigator();
            #endregion

            #region Message
            case "GetUnreadMsgByUser":
                return QueryUnreadMsgByUser();
            case "DeleteReceivedMsg":
                return ReadMsg();
            #endregion

            case "DeliverOrder4Check":
                return DeliverOrder4Check();
            case "DeliverOrder4Package":
                return DeliverOrder4Package();
            case "CheckDeliverOrderFinish":
                return CheckDeliverOrderFinish();
            case "PackageDeliverOrderSave":
                return PackageDeliverOrderSave();
            case "PackageDeliverOrderFinish":
                return PackageDeliverOrderFinish();
            case "AddDeliverOrder2IC":
                return AddDeliverOrder2IC();
            case "DeliverOrder":
                return DeliverOrder();
            case "GetWHInfo": return GetWHInfo();
            case "SaveRCVLine": return SaveRCVLine();

            default:
                return new SimpleJson().HandleError("no such action for ajax handler,please check your request action name");
        }

        return null; ;
    }
    public static String InvokeAjaxCall2String()
    {
        string action = WebUtil.Param("action");

        switch (action)
        {
            default:
                return "No such action for ajax handler";
        }
    }
    #endregion

    #region OrgTree
    private static OrgType GetType()
    {
        string type = WebUtil.Param("type");
        switch (type)
        {
            case "org":
                return OrgType.Own;
            default:
                throw new Exception("Invalidate parameter: type = " + type);
        }
    }
    private static SimpleJson LoadOrg()
    {
        if (WebUtil.ParamInt("id", 0) <= 0)
            return new SimpleJson()
                .HandleError(string.Format("Invalidate org id {0} in action LoadOrg", WebUtil.ParamInt("id", 0)));
        using (ISession session = new Session())
        {
            return Org.GetOrgJSON(session, GetType(), WebUtil.ParamInt("id", 0));
        }
    }
    private static SimpleJson SaveOrg()
    {
        using (ISession session = new Session())
        {
            Org org = new Org();
            org.OrgId = WebUtil.ParamInt("id", 0);
            org.OrgCode = WebUtil.Param("code");
            org.OrgName = WebUtil.Param("name");
            org.Description = WebUtil.Param("remark");
            org.ParentId = WebUtil.ParamInt("parent", 0);
            org.OrgSeq = System.Convert.ToInt16(WebUtil.ParamInt("seq", 0));
            org.Manager = WebUtil.ParamInt("managerId", 0);
            org.OrgType = GetType();
            if (OrgTypeRegistry.HasExtAttr(org.OrgType))
            {
                IDictionary<string, string> props = new Dictionary<string, string>();
                Type extType = OrgTypeRegistry.ExtAttrType(org.OrgType);
                PropertyInfo[] propInfo = extType.GetProperties(BindingFlags.Public | BindingFlags.Instance);
                foreach (PropertyInfo p in propInfo)
                    props.Add(p.Name, WebUtil.Param(p.Name));
                props.Add("ownerOrgId", WebUtil.Param("ownerOrgId"));
                props.Add("OurContactId", WebUtil.Param("OurContactId"));
                props.Add("OurChargeofId", WebUtil.Param("OurChargeofId"));
                org.ExtAttr = Activator.CreateInstance(extType, session, props) as IOrgExtend;
            }
            return Org.SaveOrg(session, org, org.OrgType, WebUtil.Param("opt"), (User)Magic.Security.SecuritySession.CurrentUser);
        }
    }
    private static void DeleteOrg()
    {
        using (ISession session = new Session())
        {
            Org.DeleteOrg(session, GetType(), WebUtil.ParamInt("id", 0));
        }
    }
    private static SimpleJson LoadOrgTree()
    {
        return InnerLoadOrgTree(false);
    }
    private static SimpleJson SelectOrg()
    {
        return InnerLoadOrgTree(true);
    }
    private static SimpleJson InnerLoadOrgTree(bool fromSelector)
    {
        try
        {
            using (ISession session = new Session())
            {
                Org org = Org.Get(session, GetType());
                string id = fromSelector ? "orgSelector" : "orgTree";

                StringBuilder builder = new StringBuilder();
                builder.Append(@"<ul id=""" + id + @""" virtual=""" + (org.IsVirtual ? "1" : "0") + @""">");
                if (org != null)
                    BuildTree(session, org, builder, fromSelector);
                builder.Append("</ul>");

                return new SimpleJson(2)
                    .Add("html", builder.ToString())
                    .Add("id", id);
            }
        }
        catch (Exception e)
        {
            log.Error("InnerLoadOrgTree", e);
            return new SimpleJson().HandleError(e);
        }
    }
    private static void BuildTree(ISession session, Org org, StringBuilder builder, bool fromSelector)
    {
        if (org == null) return;

        builder.Append("<li>");
        if (!org.IsVirtual && fromSelector)
            builder.Append("<input type='checkbox' style='width:14px;height:14px; border:0; font-size:12px;margin-top:1px;margin-bottom:0;' />");
        builder.Append(@"<span root=""" + (org.IsRoot ? "1" : "0") + @""" oid=""").Append(org.OrgId).Append(@""">")
            .Append(org)
            .Append("</span>");
        if (org.Children != null && org.Children.Count > 0)
        {
            builder.Append("<ul>");
            foreach (Org o in org.Children)
                BuildTree(session, o, builder, fromSelector);
            builder.Append("</ul>");
        }
        builder.Append("</li>");
    }
    #endregion

    #region RegionTree
    private static SimpleJson LoadRegion()
    {
        if (WebUtil.ParamInt("id", 0) <= 0)
            return new SimpleJson()
                .HandleError(string.Format("Invalidate id {0} in action LoadRegion", WebUtil.ParamInt("id", 0)));
        string type = WebUtil.Param("type");
        if (type != "p" && type != "c" && type != "d")
            return new SimpleJson()
                .HandleError(string.Format("Invalidate type {0} in action LoadRegion", type));
        using (ISession session = new Session())
        {
            if (type == "p")
                return Province.GetJSON(session, WebUtil.ParamInt("id", 0));
            else if (type == "c")
                return City.GetJSON(session, WebUtil.ParamInt("id", 0));
            else if (type == "d")
                return District.GetJSON(session, WebUtil.ParamInt("id", 0));
        }
        return null;
    }
    private static SimpleJson SaveRegion()
    {
        string type = WebUtil.Param("type");
        if (type != "p" && type != "c" && type != "d")
            return new SimpleJson()
                .HandleError(string.Format("Invalidate type {0} in action SaveRegion", type));
        string opt = WebUtil.Param("opt");
        if (opt != "create" && opt != "update")
            return new SimpleJson()
                .HandleError("Invalidate operation in action SaveRegion");
        using (ISession session = new Session())
        {
            if (type == "p")
            {
                #region province
                Province pro = new Province();
                pro.Code = WebUtil.Param("code");
                pro.Name = WebUtil.Param("name");
                pro.Alias = WebUtil.Param("alias");
                if (opt == "create")
                    pro.Create(session);
                else
                {
                    pro.ProvinceId = WebUtil.ParamInt("id", -1);
                    pro.Update(session, "Code", "Name", "Alias");
                }
                return pro.GetJSON();
                #endregion
            }
            else if (type == "c")
            {
                #region city
                City city = new City();
                city.CityCode = WebUtil.Param("code");
                city.Name = WebUtil.Param("name");
                city.ProvinceId = WebUtil.ParamInt("parent", -1);
                if (opt == "create")
                {
                    city.Create(session);
                }
                else
                {
                    city.CityId = WebUtil.ParamInt("id", -1);
                    city.Update(session, "CityCode", "Name");
                }
                return city.GetJSON();
                #endregion
            }
            else if (type == "d")
            {
                #region district
                District district = new District();
                district.Name = WebUtil.Param("name");
                district.ZipCode = WebUtil.Param("zip");
                district.Door2Door = WebUtil.Param("ship") == "1" ? true : false;
                district.CityId = WebUtil.ParamInt("parent", -1);
                if (opt == "create")
                {
                    district.Create(session);
                }
                else
                {
                    district.DistrictId = WebUtil.ParamInt("id", -1);
                    district.Update(session, "Name", "ZipCode", "Door2Door");
                }
                return district.GetJSON();
                #endregion
            }
        }
        return null;
    }
    private static void DeleteRegion()
    {
        string type = WebUtil.Param("type");
        if (type != "p" && type != "c" && type != "d")
            return;
        using (ISession session = new Session())
        {
            if (type == "p") Province.Delete(session, WebUtil.ParamInt("id", -1));
            else if (type == "c") City.Delete(session, WebUtil.ParamInt("id", -1));
            else if (type == "d") District.Delete(session, WebUtil.ParamInt("id", -1));
        }
    }
    private static SimpleJson LoadRegionTree()
    {
        return InnerLoadRegionTree(false);
    }
    private static SimpleJson SelectRegion()
    {
        return InnerLoadRegionTree(true);
    }
    private static SimpleJson InnerLoadRegionTree(bool fromSelector)
    {
        try
        {
            using (ISession session = new Session())
            {
                RegionRepository repository = new RegionRepository(session);
                string id = fromSelector ? "regionSelector" : "regionTree";
                StringBuilder builder = new StringBuilder();
                builder.Append("<ul id=\"" + id + "\" virtual=\"1\"><li><span ot=\"r\" oi=\"\" pi=\"\">省/市/区域</span>");
                builder.Append(@"<ul>");

                foreach (Province pro in repository.GetProvinces())
                {
                    builder.Append("<li class=\"closed\">");
                    if (fromSelector)
                        builder.Append("<input type='checkbox' style='width:14px;height:14px; border:0; font-size:12px;margin-top:1px;margin-bottom:0;' />");
                    builder.Append("<span ot=\"p\" pi=\"\"")
                        .Append(" oi=\"").Append(pro.ProvinceId).Append("\"")
                        .Append(">")
                        .Append(pro.Name).Append(" (").Append(pro.Code).Append(")")
                        .Append("</span>");
                    IList<City> cities = repository.GetCities(pro.ProvinceId);
                    if (cities != null && cities.Count > 0)
                    {
                        builder.Append("<ul>");
                        foreach (City city in cities)
                        {
                            builder.Append("<li class=\"closed\">");
                            if (fromSelector)
                                builder.Append("<input type='checkbox' style='width:14px;height:14px; border:0; font-size:12px;margin-top:1px;margin-bottom:0;' />");
                            builder.Append("<span ot=\"c\"")
                                .Append(" oi=\"").Append(city.CityId).Append("\"")
                                .Append(" pi=\"").Append(city.ProvinceId).Append("\"")
                                .Append(">")
                                .Append(city.Name).Append(" (").Append(city.CityCode).Append(")")
                                .Append("</span>");
                            IList<District> districts = repository.GetDistricts(city.CityId);
                            if (districts != null && districts.Count > 0)
                            {
                                builder.Append("<ul>");
                                foreach (District dist in districts)
                                {
                                    builder.Append("<li>");
                                    if (fromSelector)
                                        builder.Append("<input type='checkbox' style='width:14px;height:14px; border:0; font-size:12px;margin-top:1px;margin-bottom:0;' />");
                                    builder.Append("<span ot=\"d\"")
                                        .Append(" oi=\"").Append(dist.DistrictId).Append("\"")
                                        .Append(" pi=\"").Append(dist.CityId).Append("\"")
                                        .Append(">")
                                        .Append(dist.Name).Append(" (").Append(dist.ZipCode).Append(")")
                                        .Append("</span>");
                                    builder.Append("</li>");
                                }
                                builder.Append("</ul>");
                            }
                            builder.Append("</li>");
                        }//foreach (City city in cities)
                        builder.Append("</ul>");
                    }//if (cities.Count > 0)
                    builder.Append("</li>");
                }//foreach (Province pro in provinces)

                builder.Append("</ul></li></ul>");
                return new SimpleJson(2)
                    .Add("html", builder.ToString())
                    .Add("id", id);
            }
        }
        catch (Exception e)
        {
            log.Error("InnerLoadRegionTree", e);
            return new SimpleJson().HandleError(e);
        }
    }
    #endregion

    #region Operation
    private static SimpleJson SelectOperation()
    {
        return InnerLoadOperationTree(true);
    }

    private static SimpleJson DeleteOperation()
    {
        Operation op = new Operation();
        op.OperationId = WebUtil.ParamInt("id", 0);
        op.ParentId = WebUtil.ParamInt("parent", 0);
        using (Session session = new Session())
        {
            AuthorizationRepository repository = new AuthorizationRepository(session);
            repository.RemoveOperation(op);
        }
        return null;
    }

    private static SimpleJson SaveOperation()
    {
        string opt = WebUtil.Param("opt");

        Operation op = new Operation();
        op.OperationId = WebUtil.ParamInt("id", 0);
        op.ParentId = WebUtil.ParamInt("parent", 0);
        op.Name = WebUtil.Param("name");
        op.Description = WebUtil.Param("desc");
        op.Type = (OperationType)Enum.Parse(typeof(OperationType), WebUtil.Param("type"));
        op.SeqNo = WebUtil.ParamInt("seq", 0);
        op.Image = WebUtil.Param("img");
        op.Entry = WebUtil.Param("entry");
        op.Status = Cast.Enum<OperationStatus>(WebUtil.Param("status"));

        using (ISession session = new Session())
        {
            switch (opt)
            {
                case "create":
                    if (op.ParentId == Operation.ROOT_ID || op.ParentId == -1)
                    {
                        op.Level = 1;
                    }
                    else
                    {
                        Operation parent = Operation.Retrieve(session, op.ParentId);
                        op.Level = parent.Level + 1;
                    }
                    op.Create(session);
                    break;
                case "update":
                    op.Update(session, "Description", "Type", "Name", "Image", "Entry", "SeqNo", "Status");
                    break;
            }
        }
        return op.ToJSON();
    }


    private static SimpleJson LoadOperation()
    {
        int id = -2;
        if (!int.TryParse(WebUtil.Param("id"), out id))
        {
            return new SimpleJson()
                .HandleError(string.Format("can not find id in request params : LoadOperation"));
        }
        using (ISession session = new Session())
        {
            return Operation.Retrieve(session, id).ToJSON();
        }
    }

    private static SimpleJson LoadOperationTree()
    {
        return InnerLoadOperationTree(false);
    }
    private static SimpleJson InnerLoadOperationTree(bool fromSelector)
    {
        try
        {
            using (ISession session = new Session())
            {
                Operation op = Operation.GetRoot(session);
                string id = fromSelector ? "opSelector" : "opTree";

                StringBuilder builder = new StringBuilder();
                builder.Append(@"<ul id=""" + id + @""">");
                if (op != null)
                {
                    op.LoadSubTree(session);
                    BuildOperationTree(op, builder, fromSelector);
                }
                builder.Append("</ul>");

                return new SimpleJson(2)
                    .Add("html", builder.ToString())
                    .Add("id", id);
            }
        }
        catch (Exception e)
        {
            log.Error("InnerLoadOperationTree", e);
            return new SimpleJson().HandleError(e);
        }
    }
    private static void BuildOperationTree(Operation op, StringBuilder builder, bool fromSelector)
    {
        if (op == null) return;

        if (op.Level >= 1)
            builder.Append("<li class='closed'>");
        else
            builder.Append("<li>");

        if (fromSelector)
            builder.Append("<input type='checkbox' style='width:14px;height:14px; border:0; font-size:12px;margin-top:1px;margin-bottom:0;' />");
        builder.Append(GetOperationImg(op));

        builder.AppendFormat("<span root=\"{0}\" oid=\"{1}\" seq=\"{2}\">", op.IsRoot ? "1" : "0", op.OperationId, op.SeqNo)
                  .Append(op)
                  .Append("</span>");
        if (op.Children != null && op.Children.Count > 0)
        {
            builder.Append("<ul>");
            foreach (Operation o in op.Children)
                BuildOperationTree(o, builder, fromSelector);
            builder.Append("</ul>");
        }
        builder.Append("</li>");
    }
    #endregion

    #region Permission Tree
    private static SimpleJson SavePermissionTree()
    {
        PermissionType type = Cast.Enum<PermissionType>(WebUtil.ParamInt("type", 2));
        int uid = WebUtil.ParamInt("uid", -1);
        string removedOids = WebUtil.Param("removed");
        string assignedOids = WebUtil.Param("assigned");
        char[] sep = new char[] { ',' };
        string[] removeOidArray = removedOids.Split(sep, StringSplitOptions.RemoveEmptyEntries);
        string[] assignedOidArray = assignedOids.Split(sep, StringSplitOptions.RemoveEmptyEntries);
        int[] assignIds = WebUtil.ToIntArray(assignedOidArray);
        int[] removeIds = WebUtil.ToIntArray(removeOidArray);

        try
        {
            if ((assignIds != null && assignIds.Length > 0) || (removeIds != null && removeIds.Length > 0))
            {


                using (Session sesion = new Session())
                {
                    AuthorizationRepository repository = new AuthorizationRepository(sesion);
                    sesion.BeginTransaction();
                    try
                    {
                        if (assignIds != null && assignIds.Length > 0)
                        {
                            repository.AssignPermissions(type, new int[] { uid }, assignIds);
                        }
                        if (removeIds != null && removeIds.Length > 0)
                            repository.RemovePermission(type, new int[] { uid }, removeIds);
                        sesion.Commit();
                    }
                    catch
                    {
                        sesion.Rollback();
                        throw;
                    }
                }


            }
        }
        catch (Exception ex)
        {
            log.Error("SavePermissionTree", ex);
            return new SimpleJson().HandleError(new Exception("SavePermsTree", ex));
        }
        return new SimpleJson().Add("desc", "保存成功!");
    }

    private static SimpleJson LoadPermissionTree()
    {
        PermissionType type = Cast.Enum<PermissionType>(WebUtil.ParamInt("type", 2));
        int uid = WebUtil.ParamInt("uid", -1);
        string name = string.Empty;
        bool existed = false;

        try
        {
            using (Session session = new Session())
            {
                if (type == PermissionType.OnUser)
                {
                    User user = User.Retrieve(session, uid);
                    if (user != null)
                    {
                        name = user.UserName;
                        existed = true;
                    }

                }
                else if (type == PermissionType.OnUserGroup)
                {
                    UserGroup group = UserGroup.Retrieve(session, uid);
                    if (group != null)
                    {
                        name = group.Name;
                        existed = true;
                    }
                }
                if (!existed)
                {
                    throw new Exception("没有选择用户群组！");
                }
                AuthorizationRepository repository = new AuthorizationRepository(session);

                DataTable dt = null;
                dt = repository.GetAssignedPermssions(type, uid);

                Operation op = Operation.GetRoot(session);
                string id = "assignedPrms";

                StringBuilder builder = new StringBuilder();
                builder.Append(@"<ul id=""" + id + @""">");
                if (op != null)
                {
                    op.LoadSubTree(session);
                    BuildAssignedPrmsTree(session, op, builder, dt);
                }
                builder.Append("</ul>");

                return new SimpleJson(3)
                    .Add("html", builder.ToString())
                    .Add("name", name)
                    .Add("id", id);
            }
        }
        catch (Exception e)
        {
            log.Error("LoadPermissionTree", e);
            return new SimpleJson().HandleError(e);
        }
    }


    private static void BuildAssignedPrmsTree(ISession session, Operation op, StringBuilder builder, DataTable paDt)
    {
        if (op == null) return;

        builder.AppendFormat("<li class='{0}'>", op.Level == 1 ? "closed" : "");

        builder.Append(GetOperationImg(op));
        builder.AppendFormat("<span oid=\"{0}\" onclick=\"javascript:$(this).next().click(); \">", op.OperationId)
            .Append(op).Append("</span>");
        builder.Append("<input type='checkbox' style='width:14px;height:14px; border:0; font-size:12px;margin-top:1px;margin-bottom:0;' ");
        builder.AppendFormat(" opid='{0}' ", op.OperationId);
        DataRow[] rows = null;
        if (paDt != null && paDt.Rows.Count > 0)
            rows = paDt.Select(string.Format("OperationId={0}", op.OperationId));
        if (rows != null && rows.Length > 0 && Cast.Int(rows[0]["IsAllow"]) == 1)
        {
            builder.Append(" checked='checked' ");
            builder.Append(" assigned='true' ");
        }
        else
        {
            builder.Append(" assigned='false' ");
        }

        builder.Append(" />");

        if (op.Children != null && op.Children.Count > 0)
        {
            builder.Append("<ul>");
            foreach (Operation child in op.Children)
                BuildAssignedPrmsTree(session, child, builder, paDt);
            builder.Append("</ul>");
        }
        builder.Append("</li>");

    }
    #endregion

    #region UserGroup Tree

    private static SimpleJson LoadUserGroupTree()
    {

        try
        {
            using (Session session = new Session())
            {
                UserGroup group = UserGroup.Root;
                string id = "userGroup";

                StringBuilder builder = new StringBuilder();
                builder.Append(@"<ul id=""" + id + @""">");
                if (group != null)
                {
                    group.LoadSubTree(session);
                    BuildUserGroupTree(session, group, builder, false);
                }
                builder.Append("</ul>");

                return new SimpleJson(3)
                    .Add("html", builder.ToString())
                    .Add("id", id);
            }
        }
        catch (Exception e)
        {
            log.Error("LoadUserGroupTree", e);
            return new SimpleJson().HandleError(e);
        }
    }

    private static void BuildUserGroupTree(Session session, UserGroup group, StringBuilder builder, bool showCheckbox)
    {
        builder.Append("<li>");
        if (showCheckbox)
            builder.Append("<input type='checkbox' style='width:14px;height:14px; border:0; font-size:12px;margin-top:1px;margin-bottom:0;' />");
        builder.AppendFormat("<span oid=\"{0}\">", group.GroupId)
                  .Append(group.ToString())
                  .Append("</span>");
        if (group.Children != null && group.Children.Count > 0)
        {
            builder.Append("<ul>");
            foreach (UserGroup g in group.Children)
                BuildUserGroupTree(session, g, builder, showCheckbox);
            builder.Append("</ul>");
        }
        builder.Append("</li>");
    }

    private static void BuildMsgSubscriberUserGroupTree(Session session, UserGroup group, StringBuilder builder, DataTable dtGroupId)
    {
        builder.Append("<li>");

        if (dtGroupId != null)
        {
            DataRow[] rows = dtGroupId.Select(string.Format("GroupId={0}", group.GroupId));
            if (rows != null && rows.Length > 0)
            {
                dtGroupId.Rows.Remove(rows[0]);
                builder.Append("<span>&radic;</span>");
            }
            else
            {
                builder.Append("<input type='checkbox' style='width:14px;height:14px; border:0; font-size:12px;margin-top:1px;margin-bottom:0;' />");
            }
        }
        builder.AppendFormat("<span oid=\"{0}\">", group.GroupId)
                  .Append(group.ToString())
                  .Append("</span>");

        if (group.Children != null && group.Children.Count > 0)
        {
            builder.Append("<ul>");
            foreach (UserGroup g in group.Children)
                BuildMsgSubscriberUserGroupTree(session, g, builder, dtGroupId);
            builder.Append("</ul>");
        }
        builder.Append("</li>");
    }

    private static SimpleJson LoadUserGroup()
    {
        int id = -1;
        if (!int.TryParse(WebUtil.Param("id"), out id))
        {
            return new SimpleJson()
                .HandleError(string.Format("can not find id in request params : LoadOperation"));
        }
        if (id == UserGroup.Root.GroupId)
            return UserGroup.Root.ToJSON();
        using (ISession session = new Session())
        {
            return UserGroup.Retrieve(session, id).ToJSON();
        }
    }

    private static SimpleJson SaveUserGroup()
    {
        string opt = WebUtil.Param("opt");

        UserGroup group = new UserGroup();
        group.GroupId = WebUtil.ParamInt("id", 0);
        group.ParentId = WebUtil.ParamInt("parent", 0);
        group.Name = WebUtil.Param("name");
        group.Description = WebUtil.Param("desc");
        group.GroupType = (UserGroupType)Enum.Parse(typeof(UserGroupType), WebUtil.Param("type"));
        group.ModifyBy = SecuritySession.CurrentUser.UserId;
        group.ModifyTime = DateTime.Now;

        using (Session session = new Session())
        {
            AuthorizationRepository repository = new AuthorizationRepository(session);
            switch (opt)
            {
                case "create":
                    if (group.ParentId == UserGroup.Root.GroupId)
                        group.GroupLevel = (short)(UserGroup.Root.GroupLevel + 1);
                    else
                        group.GroupLevel = (short)(UserGroup.Retrieve(session, group.ParentId).GroupLevel + 1);
                    group.CreateBy = group.ModifyBy;
                    group.CreateTime = group.ModifyTime;

                    repository.CreateUserGroup(group);
                    break;
                case "update":
                    if (group.IsRoot)
                        return UserGroup.Root.ToJSON();
                    group.Update(session, "Name", "Description", "GroupType");
                    break;
            }
        }
        return group.ToJSON();
    }

    private static SimpleJson DeleteUserGroup()
    {
        int id = WebUtil.ParamInt("id", 0);
        try
        {
            using (Session session = new Session())
            {
                UserGroup group = UserGroup.Retrieve(session, id);
                if (group != null)
                {
                    AuthorizationRepository repository = new AuthorizationRepository(session);
                    repository.DeleteUserGroup(group, true);
                }
            }
        }
        catch (Exception e)
        {
            log.Error("DeleteUserGroup", e);
            return new SimpleJson().HandleError(e);
        }

        return null;
    }

    private static SimpleJson SelectUserGroupTree()
    {
        try
        {
            using (Session session = new Session())
            {
                UserGroup group = UserGroup.Root;
                string id = "userGroup";

                StringBuilder builder = new StringBuilder();
                builder.Append(@"<ul id=""" + id + @""">");
                if (group != null)
                {
                    group.LoadSubTree(session);
                    BuildUserGroupTree(session, group, builder, true);
                }
                builder.Append("</ul>");

                return new SimpleJson(3)
                    .Add("html", builder.ToString())
                    .Add("id", id);
            }
        }
        catch (Exception e)
        {
            log.Error("SelectUserGroupTree", e);
            return new SimpleJson().HandleError(e);
        }
    }

    private static SimpleJson MsgSubscriberUserGroupTree()
    {
        string tmplCode = WebUtil.Param("msgtmpl");

        try
        {
            using (Session session = new Session())
            {
                MsgTemplate msgtmpl = MsgTemplate.Retrieve(session, tmplCode);
                if (msgtmpl == null)
                    throw new Exception(string.Format("代码{0} 的模板找不到", tmplCode));

                DataTable dtGroupId = session.CreateObjectQuery("select s.GroupId as GroupId from MsgSubscriber s")
                    .Attach(typeof(MsgSubscriber))
                    .Where(Exp.Eq("s.TmplCode", tmplCode))
                    .DataSet().Tables[0];

                UserGroup group = UserGroup.Root;
                string id = "userGroup";

                StringBuilder builder = new StringBuilder();
                builder.Append(@"<ul id=""" + id + @""">");
                if (group != null)
                {
                    group.LoadSubTree(session);
                    foreach (UserGroup child in group.Children)
                    {
                        BuildMsgSubscriberUserGroupTree(session, child, builder, dtGroupId);
                    }
                }
                builder.Append("</ul>");

                return new SimpleJson(3)
                    .Add("html", builder.ToString())
                    .Add("id", id);
            }
        }
        catch (Exception e)
        {
            log.Error("MsgSubscriberUserGroupTree", e);
            return new SimpleJson().HandleError(e);
        }
    }


    #endregion

    #region Navigator

    //private static SimpleJson DeleteNavigator()
    //{
    //    int id = WebUtil.ParamInt("id", 0);
    //    try
    //    {
    //        using (Session session = new Session())
    //        {
    //            Navigator nav = Navigator.Retrieve(session, id);
    //            if (nav != null)
    //            {
    //                nav.Delete(session);
    //            }
    //        }
    //    }
    //    catch (Exception e)
    //    {
    //        log.Error("DeleteNaviagator", e);
    //        return new SimpleJson().HandleError(e);
    //    }

    //    return null;
    //}

    //private static SimpleJson LoadNavigator()
    //{
    //    int id = -1;
    //    if (!int.TryParse(WebUtil.Param("id"), out id))
    //    {
    //        return new SimpleJson()
    //            .HandleError(string.Format("can not find id in request params : LoadOperation"));
    //    }
    //    if (id == Navigator.Root.NavId)
    //        return Navigator.Root.ToJSON();
    //    using (ISession session = new Session())
    //    {
    //        return Navigator.Retrieve(session, id).ToJSON();
    //    }
    //}

    //private static SimpleJson SaveNavigator()
    //{
    //    string opt = WebUtil.Param("opt");

    //    Navigator nav = new Navigator();
    //    nav.NavId = WebUtil.ParamInt("id", 0);
    //    nav.ParentId = WebUtil.ParamInt("parent", -1);
    //    nav.Name = WebUtil.Param("name");
    //    nav.NavEntry = WebUtil.Param("entry");
    //    nav.NodeType = (NavigatorType)Enum.Parse(typeof(NavigatorType), WebUtil.Param("type"));
    //    nav.SeqNo = WebUtil.ParamInt("seq", 0);
    //    nav.Image = WebUtil.Param("img");

    //    try
    //    {
    //        using (Session session = new Session())
    //        {

    //            switch (opt)
    //            {
    //                case "create":
    //                    if (nav.ParentId == Navigator.Root.NavId || nav.ParentId == -1)
    //                    {
    //                        nav.NavLevel = Navigator.Root.NavLevel + 1;
    //                    }
    //                    else
    //                    {
    //                        Navigator parent = Navigator.Retrieve(session, nav.ParentId);
    //                        nav.NavLevel = parent.NavLevel + 1;
    //                    }
    //                    nav.Create(session);
    //                    break;
    //                case "update":
    //                    nav.Update(session, "SeqNo", "Name", "NavEntry", "NodeType", "Image");
    //                    break;
    //            }
    //        }
    //        return nav.ToJSON();
    //    }
    //    catch (Exception e)
    //    {
    //        log.Error(e);
    //        return new SimpleJson().HandleError(e);
    //    }
    //}

    //private static SimpleJson LoadNavigatorTree()
    //{
    //    try
    //    {
    //        using (Session session = new Session())
    //        {
    //            Navigator nav = Navigator.Root;
    //            string id = "navTree";
    //            nav.Name = "汇申信息网络公司业务综合平台";

    //            StringBuilder builder = new StringBuilder();
    //            builder.Append(@"<ul id=""" + id + @""">");
    //            if (nav != null)
    //            {
    //                nav.LoadSubTree(session);

    //                BuildNavigatorTree(session, nav, builder, false);

    //            }
    //            builder.Append("</ul>");

    //            return new SimpleJson(3)
    //                .Add("html", builder.ToString())
    //                .Add("id", id);
    //        }
    //    }
    //    catch (Exception e)
    //    {
    //        log.Error("LoadNavigatorTree", e);
    //        return new SimpleJson().HandleError(e);
    //    }
    //}

    //private static void BuildNavigatorTree(Session session, Navigator nav, StringBuilder builder, bool showCheckbox)
    //{
    //    if (nav.NavLevel == 1)
    //        builder.Append("<li class='closed'>");
    //    else
    //        builder.Append("<li>");
    //    if (showCheckbox)
    //        builder.Append("<input type='checkbox' style='width:14px;height:14px; border:0; font-size:12px;margin-top:1px;margin-bottom:0;' />");
    //    builder.AppendFormat("<span oid=\"{0}\" seq=\"{1}\">", nav.NavId, nav.SeqNo)
    //              .Append(nav.ToString())
    //              .Append("</span>");
    //    if (nav.Children != null && nav.Children.Count > 0)
    //    {
    //        builder.Append("<ul>");
    //        foreach (Navigator g in nav.Children)
    //            BuildNavigatorTree(session, g, builder, showCheckbox);
    //        builder.Append("</ul>");
    //    }
    //    builder.Append("</li>");
    //}

    //private static SimpleJson SelectNavigator()
    //{
    //    try
    //    {
    //        using (Session session = new Session())
    //        {
    //            Navigator nav = Navigator.Root;
    //            string id = "navSelector";

    //            StringBuilder builder = new StringBuilder();
    //            builder.Append(@"<ul id=""" + id + @""">");
    //            if (nav != null)
    //            {
    //                nav.LoadSubTree(session);
    //                foreach (Navigator child in nav.Children)
    //                {
    //                    BuildNavigatorTree(session, child, builder, true);
    //                }
    //                //BuildNavigatorTree(session, nav, builder,true);
    //            }
    //            builder.Append("</ul>");

    //            return new SimpleJson(3)
    //                .Add("html", builder.ToString())
    //                .Add("id", id);
    //        }
    //    }
    //    catch (Exception e)
    //    {
    //        log.Error("SelectNavigator", e);
    //        return new SimpleJson().HandleError(e);
    //    }
    //}


    #endregion

    #region Message

    private static SimpleJson QueryUnreadMsgByUser()
    {
        try
        {
            SimpleJson result = new SimpleJson();
            using (ISession session = new Session())
            {
                //当前5天的消息
                DataTable msgList = Message.QueryUnreadMessageByUser(session, SecuritySession.CurrentUser.UserId, DateTime.Now.AddDays(-5), DateTime.Now);

                string controlPath = "controls/MsgBoard.ascx";

                result.Add("time", DateTime.Now.ToString("yyyy-MM-DD HH:mm:ss"));
                result.Add("html", WebUtil.RenderUserControl(controlPath, new string[] { "DataSource" }, new object[] { msgList }));

                return result;
            }


        }
        catch (Exception ex)
        {
            log.Error("Magic.Ajax.QueryUnreadMsgByUser", ex);
            return new SimpleJson().HandleError(ex);
        }
    }

    private static SimpleJson ReadMsg()
    {
        int receiverId = WebUtil.ParamInt("receiveid", -1);
        if (receiverId > -1)
        {
            using (ISession session = new Session())
            {
                MsgReceiver msgReceiver = MsgReceiver.Retrieve(session, receiverId);
                if (msgReceiver != null)
                {
                    msgReceiver.ReadStatus = MessageReadStatus.Read;
                    msgReceiver.Update(session, "ReadStatus");
                }
            }
        }
        return null;
    }


    #endregion

    #region User
    private static SimpleJson SelectUser()
    {
        string userName = WebUtil.Param("username");
        string fullName = WebUtil.Param("fullname");
        int orgid = WebUtil.ParamInt("orgid", -1);
        SimpleJson result = new SimpleJson();
        string path = "controls/SelectUserTable.ascx";
        try
        {
            string html = WebUtil.RenderUserControl(path, new string[] { "UserName", "FullName", "OrgId" }, new object[] { userName, fullName, orgid });
            result.Add("id", "user");
            result.Add("html", html);
            log.Info(html);
            return result;
        }
        catch (Exception ex)
        {
            log.Error(ex);
            return new SimpleJson().HandleError(ex);
        }

    }
    #endregion

    #region 库位
    private static SimpleJson LoadWHLoc()
    {
        string type = WebUtil.Param("type");
        if (type != "l" && type != "a" && type != "s")
            return new SimpleJson()
                .HandleError(string.Format("Invalidate type {0} in action LoadWHLoc", type));
        using (ISession session = new Session())
        {
            if (type == "l")
            {
                if (WebUtil.Param("code").Trim().Length <= 0)
                    return new SimpleJson()
                        .HandleError(string.Format("Invalidate location code {0} in action LoadWHLoc", WebUtil.Param("code").Trim()));
                return WHLocation.GetJSON(session, WebUtil.Param("code").Trim());
            }
            else if (type == "a")
            {
                if (WebUtil.Param("code").Trim().Length <= 0)
                    return new SimpleJson()
                        .HandleError(string.Format("Invalidate area code {0} in action LoadWHLoc", WebUtil.Param("code").Trim()));
                return WHArea.GetJSON(session, WebUtil.Param("code").Trim());
            }
            else if (type == "s")
            {
                if (WebUtil.Param("code").Trim().Length <= 0 || WebUtil.Param("parent").Trim().Length <= 0)
                    return new SimpleJson()
                        .HandleError(string.Format("Invalidate location code {0} or parent {1} in action LoadWHLoc", WebUtil.Param("code").Trim(), WebUtil.Param("parent").Trim()));
                return WHSection.GetJSON(session, WebUtil.Param("parent").Trim(), WebUtil.Param("code").Trim());
            }
        }
        return null;
    }
    private static SimpleJson SaveWHLoc()
    {
        string type = WebUtil.Param("type");
        if (type != "l" && type != "a" && type != "s")
            return new SimpleJson()
                .HandleError(string.Format("Invalidate type {0} in action SaveWHLoc", type));
        string opt = WebUtil.Param("opt");
        if (opt != "create" && opt != "update")
            return new SimpleJson()
                .HandleError("Invalidate operation in action SaveWHLoc");
        if (WebUtil.Param("code").Trim().Length <= 0)
            return new SimpleJson()
                .HandleError("Invalidate code in action SaveWHLoc");
        using (ISession session = new Session())
        {
            if (type == "l")
            {
                #region WHLocation
                WHLocation location = new WHLocation();
                location.LocationCode = WebUtil.Param("code").Trim();
                location.Name = WebUtil.Param("name").Trim();
                location.Status = Cast.Enum<WHStatus>(WebUtil.ParamInt("status", 1));
                location.Text = WebUtil.Param("desc").Trim();
                location.Address = WebUtil.Param("addr").Trim();
                location.ZipCode = WebUtil.Param("zipcode").Trim();
                location.Contact = WebUtil.Param("contact");
                location.Phone = WebUtil.Param("phone").Trim();
                location.FaxNumber = WebUtil.Param("fax").Trim();
                location.CompanyID = WebUtil.ParamInt("comp", 0);
                if (opt == "create")
                {
                    if (session.CreateEntityQuery<WHLocation>().Where(Exp.Eq("LocationCode", location.LocationCode)).Count() > 0)
                        return new SimpleJson().HandleError(string.Format("仓库代码{0}已经存在", location.LocationCode));
                    location.Create(session);
                }
                else
                    location.Update(session);
                return location.GetJSON();
                #endregion
            }
            else if (type == "a")
            {
                #region WHArea
                if (WebUtil.Param("parent").Trim().Length <= 0)
                    return new SimpleJson().HandleError("Invalidate parent in action SaveWHLoc");
                string parentType = WebUtil.Param("parentType");
                if (parentType != "l" && parentType != "a")
                    return new SimpleJson().HandleError("Invalidate parentType in action SaveWHLoc");
                WHArea area = new WHArea();
                WHArea parentArea = null;
                area.AreaCode = WebUtil.Param("code").Trim();
                if (parentType == "l") area.LocationCode = WebUtil.Param("parent").Trim();
                else
                {
                    parentArea = WHArea.Retrieve(session, WebUtil.Param("parent").Trim());
                    if (parentArea == null)
                        return new SimpleJson().HandleError(string.Format("Parent {0} not found", WebUtil.Param("parent").Trim()));
                    area.LocationCode = parentArea.LocationCode;
                    area.ParentArea = parentArea.AreaCode;
                }
                area.Name = WebUtil.Param("name").Trim();
                area.Text = WebUtil.Param("desc").Trim();
                area.Status = Cast.Enum<WHStatus>(WebUtil.ParamInt("status", 1));
                area.AreaCapacity = WebUtil.ParamDecimal("cap", 99999999M);
                area.HasSection = WebUtil.Param("hassec").Trim() == "1" ? true : false;
                area.IsQC = WebUtil.Param("isqc").Trim() == "1" ? true : false;
                area.IsScrap = WebUtil.Param("isscrap").Trim() == "1" ? true : false;
                if (opt == "create")
                {
                    if (session.CreateEntityQuery<WHArea>().Where(Exp.Eq("AreaCode", area.AreaCode)).Count() > 0)
                        return new SimpleJson().HandleError(string.Format("存储区域代码{0}已经存在", area.AreaCode));
                    if (parentArea != null)
                    {
                        area.CostTransRate = parentArea.CostTransRate;
                        area.CostFixValue = parentArea.CostFixValue;
                        area.UseFixCost = parentArea.UseFixCost;
                        area.AllowChild = true;
                        area.AllowDelete = true;
                        area.IsReservedArea = false;
                        area.IsTransArea = parentArea.IsTransArea;
                    }
                    else
                    {
                        area.CostTransRate = 1M;
                        area.CostFixValue = 0M;
                        area.UseFixCost = false;
                        area.AllowChild = true;
                        area.AllowDelete = true;
                        area.IsReservedArea = false;
                        area.IsTransArea = true;
                    }
                    area.Create(session);
                }
                else
                {
                    area.Update(session, "Name", "Text", "Status", "AreaCapacity", "HasSection", "IsQC", "IsScrap");
                }
                return area.GetJSON();
                #endregion
            }
            else if (type == "s")
            {
                #region WHSection
                if (WebUtil.Param("parent").Trim().Length <= 0)
                    return new SimpleJson().HandleError("Invalidate parent in action SaveWHLoc");
                WHSection section = new WHSection();
                section.SectionCode = WebUtil.Param("code").Trim();
                section.AreaCode = WebUtil.Param("parent").Trim();
                section.Status = Cast.Enum<WHStatus>(WebUtil.ParamInt("status", 1));
                section.SectionCapacity = WebUtil.ParamDecimal("cap", 99999999M);
                section.Text = WebUtil.Param("desc").Trim();
                if (opt == "create")
                {
                    if (session.CreateEntityQuery<WHSection>().Where(Exp.Eq("AreaCode", section.AreaCode) & Exp.Eq("SectionCode", section.SectionCode)).Count() > 0)
                        return new SimpleJson().HandleError(string.Format("存储区域{0}下面已经存在货架{1}", section.AreaCode, section.SectionCode));
                    section.Create(session);
                }
                else
                {
                    section.Update(session, "Status", "SectionCapacity", "Text");
                }
                return section.GetJSON();
                #endregion
            }
        }
        return null;
    }
    private static void DeleteWHLoc()
    {
        string type = WebUtil.Param("type");
        if (type != "l" && type != "a" && type != "s")
            return;
        using (ISession session = new Session())
        {
            if (type == "l")
            {
                WHLocation location = WHLocation.Retrieve(session, WebUtil.Param("code").Trim());
                if (location == null) return;
                location.Status = WHStatus.Delete;
                location.Update(session, "Status");
            }
            else if (type == "a")
            {
                WHArea area = WHArea.Retrieve(session, WebUtil.Param("code").Trim());
                if (area == null || area.IsReservedArea || !area.AllowDelete) return;
                area.Status = WHStatus.Delete;
                area.Update(session, "Status");
            }
            else if (type == "s")
            {
                WHSection section = WHSection.Retrieve(session, WebUtil.Param("parent"), WebUtil.Param("code").Trim());
                if (section == null) return;
                section.Status = WHStatus.Delete;
                section.Update(session, "Status");
            }
        }
    }
    private static SimpleJson LoadWHLocTree()
    {
        return InnerLoadWHLocTree(false);
    }
    private static SimpleJson SelectWHLoc()
    {
        return InnerLoadWHLocTree(true);
    }
    private static SimpleJson InnerLoadWHLocTree(bool fromSelector)
    {
        try
        {
            using (ISession session = new Session())
            {
                WHRepository repository = new WHRepository(session);
                string id = fromSelector ? "whlocationSelector" : "whlocathonTree";
                StringBuilder builder = new StringBuilder();
                builder.Append("<ul id=\"" + id + "\" virtual=\"1\"><li><span ot=\"r\" oi=\"\" pi=\"\">仓库结构</span>");
                builder.Append(@"<ul>");

                #region 处理仓库 WHLocation
                foreach (WHLocation location in repository.GetLocations())
                {
                    builder.Append("<li>");
                    if (fromSelector)
                        builder.Append("<input type='checkbox' style='width:14px;height:14px; border:0; font-size:12px;margin-top:1px;margin-bottom:0;' />");
                    builder.Append("<span ot=\"l\" pi=\"\"")
                        .Append(" oi=\"").Append(location.LocationCode).Append("\"")
                        .Append(">")
                        .Append(location.ToString())
                        .Append("</span>");
                    IList<WHArea> areas = repository.GetAreas(location.LocationCode);
                    #region 处理库位 WHArea
                    if (areas != null && areas.Count > 0)
                    {
                        builder.Append("<ul>");
                        foreach (WHArea area in areas)
                        {
                            builder.Append("<li class=\"closed\">");
                            if (fromSelector)
                                builder.Append("<input type='checkbox' style='width:14px;height:14px; border:0; font-size:12px;margin-top:1px;margin-bottom:0;' />");
                            builder.Append("<span ot=\"a\"")
                                .Append(" oi=\"").Append(area.AreaCode).Append("\"")
                                .Append(" pi=\"").Append(location.LocationCode).Append("\"")
                                .Append(" a_del=\"").Append(area.AllowDelete ? "1" : "0").Append("\"")
                                .Append(" a_sec=\"").Append(area.HasSection ? "1" : "0").Append("\"")
                                .Append(">")
                                .Append(area.ToString())
                                .Append("</span>");
                            IList<WHSection> sections = repository.GetSections(area.AreaCode);
                            #region 处理货架 WHSection
                            if (sections != null && sections.Count > 0)
                            {
                                builder.Append("<ul>");
                                foreach (WHSection section in sections)
                                {
                                    builder.Append("<li>");
                                    if (fromSelector)
                                        builder.Append("<input type='checkbox' style='width:14px;height:14px; border:0; font-size:12px;margin-top:1px;margin-bottom:0;' />");
                                    builder.Append("<span ot=\"s\"")
                                        .Append(" oi=\"").Append(section.SectionCode).Append("\"")
                                        .Append(" pi=\"").Append(section.AreaCode).Append("\"")
                                        .Append(">")
                                        .Append(section.ToString())
                                        .Append("</span>");
                                    builder.Append("</li>");
                                }
                                builder.Append("</ul>");
                            }
                            #endregion
                            builder.Append("</li>");
                        }//foreach (City city in cities)
                        builder.Append("</ul>");
                    }//if (cities.Count > 0)
                    #endregion
                    builder.Append("</li>");
                }//foreach (Province pro in provinces)
                #endregion

                builder.Append("</ul></li></ul>");
                return new SimpleJson(2)
                    .Add("html", builder.ToString())
                    .Add("id", id);
            }
        }
        catch (Exception e)
        {
            log.Error("InnerLoadWHLocTree", e);
            return new SimpleJson().HandleError(e);
        }
    }
    #endregion

    private static SimpleJson DeliverOrder4Check()
    {
        string orderNumber = WebUtil.Param("ordNumber").Trim();
        if (string.IsNullOrEmpty(orderNumber)) return new SimpleJson().HandleError("发货单号码为空");
        using (ISession session = new Session())
        {
            return CRMSN.DeliverOrder4Check(session, orderNumber);
        }
    }
    private static SimpleJson DeliverOrder4Package()
    {
        string orderNumber = WebUtil.Param("ordNumber").Trim();
        if (string.IsNullOrEmpty(orderNumber)) return new SimpleJson().HandleError("发货单号码为空");
        SimpleJson json = new SimpleJson();
        using (ISession session = new Session())
        {
            json = CRMSN.DeliverOrder4Package(session, orderNumber);
        }
        if (log.IsDebugEnabled)
            log.Debug("sn loaded: " + json.ToString());
        return json;
    }
    private static SimpleJson DeliverOrder()
    {
        string orderNumber = WebUtil.Param("ordNumber").Trim();
        if (string.IsNullOrEmpty(orderNumber)) return new SimpleJson().HandleError("发货单号码为空");
        using (ISession session = new Session())
        {
            CRMSN sn = CRMSN.Retrieve(session, orderNumber);
            if (sn == null) return new SimpleJson().HandleError("发货单" + orderNumber + "不存在");
            SimpleJson json = sn.ToJSon(session);
            if (log.IsDebugEnabled)
                log.Debug("sn loaded:" + json.ToString());
            return json;
        }
    }
    private static SimpleJson CheckDeliverOrderFinish()
    {
        string orderNumber = WebUtil.Param("ordNumber").Trim();
        if (string.IsNullOrEmpty(orderNumber)) return new SimpleJson().HandleError("发货单号码为空");
        using (ISession session = new Session())
        {
            session.BeginTransaction();
            try
            {
                CRMSN.CheckFinish(session, orderNumber);
                session.Commit();
            }
            catch (Exception er)
            {
                session.Rollback();
                return new SimpleJson().HandleError("无法执行核货完成操作: " + er.Message);
            }
        }
        return new SimpleJson();
    }
    private static SimpleJson PackageDeliverOrderSave()
    {
        string orderNumber = WebUtil.Param("ordNumber").Trim();
        if (string.IsNullOrEmpty(orderNumber)) return new SimpleJson().HandleError("发货单号码为空");
        decimal packageWeight = WebUtil.ParamDecimal("packageWeight", 0M);
        string shippingNumber = WebUtil.Param("shippingNumber");
        string invoiceNumber = WebUtil.Param("invoiceNumber");
        int logisticId = WebUtil.ParamInt("logisticsId", 0);
        int packageCount = WebUtil.ParamInt("packageCount", 0);
        if (log.IsDebugEnabled)
            log.DebugFormat("save package info: ordNum={0}, logis={1}, shippingNum={2}, invoice={3}, weight={4}, package={5}", orderNumber, logisticId, shippingNumber, invoiceNumber, packageWeight, packageCount);
        using (ISession session = new Session())
        {
            session.BeginTransaction();
            try
            {
                CRMSN.PackageSave(session, orderNumber, packageWeight, shippingNumber, logisticId, invoiceNumber, packageCount);
                session.Commit();
            }
            catch (Exception er)
            {
                session.Rollback();
                return new SimpleJson().HandleError("保存失败: " + er.Message);
            }
        }
        return new SimpleJson();
    }
    private static SimpleJson PackageDeliverOrderFinish()
    {
        string orderNumber = WebUtil.Param("ordNumber").Trim();
        if (string.IsNullOrEmpty(orderNumber)) return new SimpleJson().HandleError("发货单号码为空");
        using (ISession session = new Session())
        {
            session.BeginTransaction();
            try
            {
                CRMSN.PackageFinish(session, orderNumber);
                session.Commit();
            }
            catch (Exception er)
            {
                session.Rollback();
                return new SimpleJson().HandleError("包装确认失败: " + er.Message);
            }
        }
        return new SimpleJson();
    }
    private static SimpleJson GetWHInfo()
    {
        using (ISession session = new Session())
        {
            return WHArea.GetWHInfo(session, WebUtil.Param("area"), WebUtil.Param("section"));
        }
    }
    private static SimpleJson SaveRCVLine()
    {
        string orderNumber = WebUtil.Param("ordNum").Trim();
        if (string.IsNullOrEmpty(orderNumber) || orderNumber.Length <= 0)
            return new SimpleJson().HandleError("收货单号码为空");
        using (ISession session = new Session())
        {
            RCVHead head = RCVHead.Retrieve(session, orderNumber);
            if (head == null) return new SimpleJson().HandleError("收货单" + orderNumber + "不存在");
            return head.AddLine(session, WebUtil.Param("poline"), WebUtil.Param("area"), WebUtil.Param("section"), WebUtil.ParamDecimal("qty", 0M));
        }
    }

    private static SimpleJson AddDeliverOrder2IC()
    {
        using (ISession session = new Session())
        {
            return ICHead.AddLine(session, WebUtil.Param("icNumber"), WebUtil.Param("ordNumber"));
        }
    }

    #region Private
    private static string ToJsonArrayString(IList<IJsonable> entitys)
    {
        StringBuilder sb = new StringBuilder();
        if (entitys != null && entitys.Count > 0)
        {
            sb.AppendFormat("[{0}", entitys[0].ToJson().ToJsonString());
            for (int i = 1; i < entitys.Count; i++)
            {
                sb.AppendFormat(",{0}", entitys[i].ToJson().ToJsonString());
            }
            sb.Append("]");
        }
        return sb.ToString();
    }
    private static string ToJsonArrayString(IList<SimpleJson> jsonObjs)
    {
        StringBuilder sb = new StringBuilder();
        if (jsonObjs != null && jsonObjs.Count > 0)
        {
            sb.AppendFormat("[{0}", jsonObjs[0].ToJsonString());
            for (int i = 1; i < jsonObjs.Count; i++)
            {
                sb.AppendFormat(",{0}", jsonObjs[i].ToJsonString());
            }
            sb.Append("]");
        }
        return sb.ToString();
    }
    private static string GetOperationImg(Operation op)
    {
        switch (op.Type)
        {
            case OperationType.System:
                return "<img src=\"../images/system.gif\" align=\"middle\" border=\"0\">";
            case OperationType.Module:
                return ("<img src=\"../images/module.gif\" align=\"middle\" border=\"0\">");

            case OperationType.Feature:
                return ("<img src=\"../images/function.gif\"/ align=\"middle\" border=\"0\">");

            case OperationType.Entity:
                return ("<img src=\"../images/entity.gif\"/ align=\"middle\" border=\"0\">");

            case OperationType.Field:
                return ("<img src=\"../images/field.gif\"/ align=\"middle\" border=\"0\">");

            case OperationType.Report:
                return ("<img src=\"../images/report.gif\"/ align=\"middle\" border=\"0\">");

        }
        return "";
    }
    #endregion
}