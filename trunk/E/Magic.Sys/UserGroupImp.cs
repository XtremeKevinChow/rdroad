using System;
using System.Collections.Generic;
using Magic.Framework.Data;
using Magic.Framework.ORM;
using Magic.Framework.ORM.Mapping;
using Magic.Framework.ORM.Query;
using Magic.Security;
using System.Data;

namespace Magic.Sys
{
    public partial class UserGroup:IUserGroup
    {
        #region IEntity Methods
        /// <summary>
        /// Create new 用户组 entity
        /// <returns></returns>
        /// </summary>
        public bool Create(ISession session)
        {
            return EntityManager.Create(session, this);
        }

        /// <summary>
        /// Update 用户组 entity's whole properties
        /// <returns></returns>
        /// </summary>
        public bool Update(ISession session)
        {
            return EntityManager.Update(session, this);
        }

        /// <summary>
        ///Update 用户组 entity's given properties
        /// <returns></returns>
        /// </summary>
        public bool Update(ISession session, params string[] propertyNames2Update)
        {
            return EntityManager.Update(session, this, propertyNames2Update);
        }

        /// <summary>
        /// 删除用户组
        /// <returns></returns>
        /// </summary>
        public bool Delete(ISession session)
        {
            return EntityManager.Delete(session, this);
        }


        /// <summary>
        ///Retrive a 用户组 Entity from DB
        /// <returns></returns>
        /// </summary>
        public static UserGroup Retrieve(ISession session, int userGroupId)
        {
            return EntityManager.Retrieve<UserGroup>(session, userGroupId);
        }

        /// <summary>
        /// Delete by Primary key
        /// </summary>
        /// <returns></returns>
        public static bool Delete(ISession session, int userGroupId)
        {
            return EntityManager.Delete<UserGroup>(session, userGroupId);
        }

        /// <summary>
        ///  Map a DataRow to a UserGroup Entity.
        /// </summary>
        /// <returns></returns>
        public static UserGroup Row2Entity(System.Data.DataRow row)
        {
            if (row == null) return null;

            UserGroup entity = new UserGroup();          

            if (!row.IsNull("Group_ID"))
                entity._groupId = (int)(row["Group_ID"]);
            if (!row.IsNull("Parent_ID"))
                entity._parentId = (int)(row["Parent_ID"]);
            if (!row.IsNull("Name"))
                entity._name = (string)(row["Name"]);
            if (!row.IsNull("Description"))
                entity._description = (string)(row["Description"]);
            if (!row.IsNull("Group_Type"))
                entity._groupType = (UserGroupType)Enum.Parse(typeof(UserGroupType),(row["Group_Type"]).ToString());
            if (!row.IsNull("Group_Level"))
                entity._groupLevel = (short)(row["Group_Level"]);
            if (!row.IsNull("Create_Time"))
                entity._createTime = (DateTime)(row["Create_Time"]);
            if (!row.IsNull("Modify_Time"))
                entity._modifyTime = (DateTime)(row["Modify_Time"]);
            if (!row.IsNull("Create_By"))
                entity._createBy = (int)(row["Create_By"]);
            if (!row.IsNull("Modify_By"))
                entity._modifyBy = (int)(row["Modify_By"]);

            return entity;
        }

       
        #endregion

        #region IUserGroup Members

        /// <summary>
        /// 新增用户到用户组
        /// </summary>
        /// <param name="user"></param>
        /// <param name="session"></param>
        /// <returns></returns>
        public bool AddUser(UserBase user, ISession session)
        {
            if (!this.UserIsInGroup(user, session))
            {
                UserToGroup utg = new UserToGroup();
                utg.CreateTime = DateTime.Now;
                utg.GroupId = this.GroupId;
                utg.OperateBy = SecuritySession.CurrentUser.UserId;
                utg.UserId = user.UserId;
                
                return utg.Create(session);
            }
            else
            {
                return false;
            }
        }

        /// <summary>
        /// Find all users in the group
        /// </summary>
        /// <param name="session"></param>
        /// <returns></returns>
        public IList<User> FindUsersInGroup(ISession session)
        {
            string oql = @"select u.* from User u inner join UserToGroup ug on u.UserId=ug.UserId where ug.GroupId=?";

         return
             session.CreateObjectQuery(oql)
             .Attach(typeof(User))
             .Attach(typeof(UserToGroup))
             .SetValue(0, this.GroupId, DbTypeInfo.Int32())
                .List<User>();

        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="session"></param>
        /// <returns></returns>
        IList<UserBase> IUserGroup.FindUsersInGroup(ISession session)
        {
            string oql = @"select u.* from User u inner join UserToGroup ug on u.UserId=ug.UserId ";

            return
              session.CreateObjectQuery(oql)
              .Attach(typeof(User))
              .Attach(typeof(UserToGroup))
              .Attach(typeof(UserBase))
              .And(Exp.Eq("ug.GroupId",this.GroupId))
                 .List<UserBase>();

        }



        IList<IUserGroup> IUserGroup.FindChildGroups(ISession session)
        {
            IList<UserGroup> list = this.FindChildGroups(session);
            return ConvertTo(list);
        }
        IList<IUserGroup> IUserGroup.FindOffspringGroups(ISession session)
        {
            return ConvertTo(this.FindOffspringGroups(session));
        }
        IList<IUserGroup> IUserGroup.FindAncestryGroup(ISession sesion)
        {
            return ConvertTo(this.FindAncestryGroup(sesion));
        }
        public IList<UserGroup> FindOffspringGroups(ISession session)
        {
            return null;
        }

        public IList<UserGroup> FindChildGroups(ISession session)
        {
            GetChidren(session);
            return this.Children;
        }

        public bool UserIsInGroup(UserBase user,ISession session)
        {
            string oql = @"select 1 from UserToGroup";
            return
             session.CreateObjectQuery(oql)
             .Attach(typeof(UserToGroup))
             .And(Exp.Eq("UserId", user.UserId))
             .And(Exp.Eq("GroupId", this.GroupId))
             .Count() > 0;
        }

        public IList<UserGroup> FindAncestryGroup(ISession session)
        {
            return null;
        }

        /// <summary>
        /// 从当前Group中删除User
        /// </summary>
        /// <param name="users"></param>
        /// <returns></returns>
        public bool RemoveUserFromGroup(IList<UserBase> users, ISession session)
        {
            bool flag = false;
         
            foreach (UserBase user in users)
            {
                flag = UserToGroup.Delete(session, this.GroupId, user.UserId) == 1;
                if (!flag)
                    throw new ApplicationException(string.Format("删除用户{0}失败",user.UserName));
            }
     
            return flag;
        }

        #endregion

        private IList<IUserGroup> ConvertTo(IList<UserGroup> list)
        {
            if (list != null && list.Count > 0)
            {
                IList<IUserGroup> cList = new List<IUserGroup>(list.Count);
                foreach (UserGroup g in list)
                {
                    cList.Add(g);
                }
                return cList;
            }
            return null;
        }
    }
}
