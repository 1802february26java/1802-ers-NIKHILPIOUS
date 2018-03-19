package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	public static Connection getConnection() throws SQLException{
		String url="jdbc:oracle:thin:@npol7896tulavuguppi.ctwznr61g3sq.us-east-1.rds.amazonaws.com:1521:ORCL";
		String username= "REIMBURSEMENT_DB";
		String password="p4ssw0rd";
		return DriverManager.getConnection(url,username,password);
		
	}

}
