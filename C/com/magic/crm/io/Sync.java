/**
 * Sync.java
 * 2008-5-20
 * 上午09:37:04
 * user
 * Sync
 */
package com.magic.crm.io;

import java.sql.Connection;
/**
 * @author user
 * 数据同步接口，单向同步到网站
 */
public interface Sync {
	public void execute(Connection conn1, Connection conn2) throws Exception;
}
