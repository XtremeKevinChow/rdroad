/**
 * Sync.java
 * 2008-5-20
 * ����09:37:04
 * user
 * Sync
 */
package com.magic.crm.io;

import java.sql.Connection;
/**
 * @author user
 * ����ͬ���ӿڣ�����ͬ������վ
 */
public interface Sync {
	public void execute(Connection conn1, Connection conn2) throws Exception;
}
