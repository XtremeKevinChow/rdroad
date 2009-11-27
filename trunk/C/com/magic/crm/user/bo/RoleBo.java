package com.magic.crm.user.bo;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Fechina</p>
 * @author Kevin zhou
 * @version 1.0
 */
import com.magic.crm.user.dao.RoleDAO;
import com.magic.crm.user.exception.*;
import java.util.Collection;
import java.sql.Connection;
import java.sql.SQLException;


public class RoleBo {
  Connection conn = null;
  public void setConn(Connection newCon) {
    conn = newCon;
  }
  public RoleBo() {

  }

  public Collection getAll() throws FeException {
    Collection col = null;
    try {
       RoleDAO scDAO = new RoleDAO();
       col = scDAO.findAllRoles(conn);
       return col;
     } catch(Exception e) {
       System.out.println(e);
       throw new FeException("RoleDAO.getAll error.");
     }
  }

}