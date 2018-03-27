package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	/* Make Tomcat now which database driver to use */
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			//logger.warn("Exception thrown adding oracle driver.", e);
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		String url="jdbc:oracle:thin:@npol7896tulavuguppi.ctwznr61g3sq.us-east-1.rds.amazonaws.com:1521:ORCL";
		String username= "REIMBURSEMENT_DB2";
		String password="p4ssw0rd";
		return DriverManager.getConnection(url,username,password);
		
	}

}
