package com.revature.util;

public class ERSQueryConstants {
	
	public static final String EMPLOYEE_INSERT = "INSERT INTO user_t(u_firstname,u_lastname,u_username,u_password,u_email,ur_id) VALUES(?,?,?,?,?,?)";
	public static final String EMPLOYEE_UPDATE = "UPDATE user_t SET  u_firstname=?,u_lastname=?,u_password=?,u_email=? WHERE u_id=?";
	public static final String EMPLOYEE_SELECT_WITH_ID = "SELECT ut.u_id,ut.u_firstname, ut.u_lastname,ut.u_username,ut.u_password,ut.u_email,ur.ur_id,ur.ur_type FROM user_t ut JOIN user_role ur ON ut.ur_id=ur.ur_id WHERE ut.u_id=?";
	public static final String EMPLOYEE_SELECT_WITH_USERNAME = "SELECT ut.u_id,ut.u_firstname, ut.u_lastname,ut.u_username,ut.u_password,ut.u_email,ur.ur_id,ur.ur_type FROM user_t ut JOIN user_role ur ON ut.ur_id=ur.ur_id WHERE ut.u_username=?";
	public static final String EMPLOYEE_SELECT_ALL = "SELECT ut.u_id,ut.u_firstname, ut.u_lastname,ut.u_username,ut.u_password,ut.u_email,ur.ur_id,ur.ur_type FROM user_t ut JOIN user_role ur ON ut.ur_id=ur.ur_id";
	public static final String EMPLOYEE_GET_PASSWORD_HASH= "SELECT GET_HASH(?) AS HASH FROM DUAL";
	public static final String EMPLOYEE_INSERT_EMP_TOKEN = "INSERT INTO password_recovery(pr_token,pr_time,u_id) VALUES(?,?,?)";
	public static final String EMPLOYEE_DELETE_EMP_TOKEN = "DELETE FROM password_recovery WHERE u_id=?";
	public static final String EMPLOYEE_SELECT_EMP_TOKEN = "SELECT pr_id,pr_token,pr_time,u_id FROM password_recovery WHERE u_id=?";
	
	
	public static final String REIMBURSEMENT_INSERT = "INSERT INTO reimbursement (r_requested,r_amount,r_description,r_receipt,employee_id,rs_id,rt_id) VALUES(?,?,?,?,?,?,?)";
	public static final String REIMBURSEMENT_UPDATE =  "UPDATE (SELECT rms.r_resolved,rmst.rs_id FROM  reimbursement rms JOIN reimbursement_status rmst ON rms.rs_id= rmst.rs_id WHERE rms.r_id=?)"
															+ "SET r_resolved=?, rs_id=?";
	public static final String REIMBURSEMENT_SELECT = "SELECT rms.r_id,rms.r_requested,rms.r_resolved,rms.r_amount,rms.r_description,rms.r_receipt,rms.employee_id,rms.manager_id,rms.rs_id,rms.rt_id,rmst.rs_status,rmtp.rt_type FROM "
															+ "reimbursement rms JOIN reimbursement_status rmst ON rms.rs_id= rmst.rs_id JOIN reimbursement_type rmtp ON rms.rt_id= rmtp.rt_id WHERE rms.r_id=?";
	public static final String REIMBURSEMENT_SELECT_PENDING = "SELECT rms.r_id,rms.r_requested,rms.r_resolved,rms.r_amount,rms.r_description,rms.r_receipt,rms.employee_id,rms.manager_id,rms.rs_id,rms.rt_id,rmst.rs_status,rmtp.rt_type FROM "
																+ "reimbursement rms JOIN reimbursement_status rmst ON rms.rs_id= rmst.rs_id JOIN reimbursement_type rmtp ON rms.rt_id= rmtp.rt_id WHERE rms.employee_id=? AND UPPER(rmst.rs_status)='PENDING'";
	public static final String REIMBURSEMENT_SELECT_FINALIZED="SELECT rms.r_id,rms.r_requested,rms.r_resolved,rms.r_amount,rms.r_description,rms.r_receipt,rms.employee_id,rms.manager_id,rms.rs_id,rms.rt_id,rmst.rs_status,rmtp.rt_type FROM "
																+ "reimbursement rms JOIN reimbursement_status rmst ON rms.rs_id= rmst.rs_id JOIN reimbursement_type rmtp ON rms.rt_id= rmtp.rt_id WHERE rms.employee_id=? AND (UPPER(rmst.rs_status)='DECLINED' OR UPPER(rmst.rs_status)='APPROVED')"; 	
	public static final String REIMBURSEMENT_SELECT_ALL_PENDING= "SELECT rms.r_id,rms.r_requested,rms.r_resolved,rms.r_amount,rms.r_description,rms.r_receipt,rms.employee_id,rms.manager_id,rms.rs_id,rms.rt_id,rmst.rs_status,rmtp.rt_type FROM "
																+ "reimbursement rms JOIN reimbursement_status rmst ON rms.rs_id= rmst.rs_id JOIN reimbursement_type rmtp ON rms.rt_id= rmtp.rt_id WHERE UPPER(rmst.rs_status)='PENDING'";
	public static final String REIMBURSEMENT_SELECT_ALL_FINALIZED = "SELECT rms.r_id,rms.r_requested,rms.r_resolved,rms.r_amount,rms.r_description,rms.r_receipt,rms.employee_id,rms.manager_id,rms.rs_id,rms.rt_id,rmst.rs_status,rmtp.rt_type FROM " + 
																	"reimbursement rms JOIN reimbursement_status rmst ON rms.rs_id= rmst.rs_id JOIN reimbursement_type rmtp ON rms.rt_id= rmtp.rt_id WHERE UPPER(rmst.rs_status)='DECLINED' OR UPPER(rmst.rs_status)='APPROVED'";
	
	public static final String REIMBURSEMENT_SELECT_ALL_TYPES = " SELECT * FROM reimbursement_type";

	
	

}
