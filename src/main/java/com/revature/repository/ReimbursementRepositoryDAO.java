package com.revature.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
import com.revature.model.ReimbursementType;
import com.revature.util.ConnectionUtil;

public class ReimbursementRepositoryDAO implements ReimbursementRepository {
	
	private static ReimbursementRepository reimburseRepo = new ReimbursementRepositoryDAO(); 
	
	private ReimbursementRepositoryDAO() {};
	
	public static ReimbursementRepository getInstance() {
		return reimburseRepo;
	}

	@Override
	public boolean insert(Reimbursement reimbursement) {
		try(Connection connection = ConnectionUtil.getConnection()){ 
			String sql="INSERT INTO reimbursement (r_requested,r_amount,r_description,r_receipt,employee_id,rs_id,rt_id) VALUES(?,?,?,?,?,?,?)"; 
			PreparedStatement prepstmt= connection.prepareStatement(sql);
			int parameterIndex=0;
			LocalDateTime resolvedTime=reimbursement.getResolved();
			//Date requestedDate = Date.valueOf(reimbursement.getRequested().toString());
			prepstmt.setTimestamp(++parameterIndex, java.sql.Timestamp.valueOf(reimbursement.getRequested()));
			prepstmt.setDouble(++parameterIndex, reimbursement.getAmount());
			prepstmt.setString(++parameterIndex,reimbursement.getDescription());
			prepstmt.setObject(++parameterIndex,reimbursement.getReceipt());
			prepstmt.setInt(++parameterIndex,reimbursement.getRequester().getId());
			prepstmt.setInt(++parameterIndex,reimbursement.getStatus().getId());
			prepstmt.setInt(++parameterIndex,reimbursement.getType().getId());
			
			if(prepstmt.executeUpdate()>0) {
				return true;
			}
			}
			catch(SQLException|NullPointerException e) {
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean update(Reimbursement reimbursement) {
		try(Connection connection = ConnectionUtil.getConnection()){ 
			String sql = "UPDATE (SELECT rms.r_resolved,rmst.rs_id FROM  reimbursement rms JOIN reimbursement_status rmst ON rms.rs_id= rmst.rs_id WHERE rms.r_id=?)"
					+ "SET r_resolved=?, rs_id=?";
			PreparedStatement prepstmt= connection.prepareStatement(sql);
			int parameterIndex=0;
			prepstmt.setInt(++parameterIndex,reimbursement.getId() );
			prepstmt.setTimestamp(++parameterIndex,java.sql.Timestamp.valueOf(reimbursement.getResolved()));
			prepstmt.setObject(++parameterIndex,reimbursement.getStatus().getId());
			if(prepstmt.executeUpdate()>0) {
				return true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Reimbursement select(int reimbursementId) {
		try(Connection connection = ConnectionUtil.getConnection()){ 
			String sql="SELECT rms.r_id,rms.r_requested,rms.r_resolved,rms.r_amount,rms.r_description,rms.r_receipt,rms.employee_id,rms.manager_id,rms.rs_id,rms.rt_id,rmst.rs_status,rmtp.rt_type FROM "
					+ "reimbursement rms JOIN reimbursement_status rmst ON rms.rs_id= rmst.rs_id JOIN reimbursement_type rmtp ON rms.rt_id= rmtp.rt_id WHERE rms.r_id=?";
			
			PreparedStatement prepstmt = connection.prepareStatement(sql);
			prepstmt.setInt(1, reimbursementId);
			ResultSet rs = prepstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getTimestamp("r_resolved")==null) {
					return new Reimbursement(rs.getInt("r_id"),rs.getTimestamp("r_requested").toLocalDateTime(),
							rs.getDouble("r_amount"),rs.getString("r_description"),rs.getObject("r_receipt")
							,new Employee(rs.getInt("employee_id")),new Employee(rs.getInt("employee_id")), new ReimbursementStatus(rs.getInt("rs_id"),rs.getString("rs_status")),new ReimbursementType(rs.getInt("rt_id"),rs.getString("rt_type")));
					
				}
				else {
				return new Reimbursement(rs.getInt("r_id"),rs.getTimestamp("r_requested").toLocalDateTime(),
						rs.getTimestamp("r_resolved").toLocalDateTime(),rs.getDouble("r_amount"),rs.getString("r_description"),rs.getObject("r_receipt")
						,new Employee(rs.getInt("employee_id")), new Employee(rs.getInt("manager_id")), new ReimbursementStatus(rs.getInt("rs_id"),rs.getString("rs_status")),new ReimbursementType(rs.getInt("rt_id"),rs.getString("rt_type")));
				}
				}
		}
		catch(SQLException |NullPointerException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Set<Reimbursement> selectPending(int employeeId) {
		try(Connection connection = ConnectionUtil.getConnection()){ 
			Set <Reimbursement> reimbursementList =  new HashSet<>();
			String sql="SELECT rms.r_id,rms.r_requested,rms.r_resolved,rms.r_amount,rms.r_description,rms.r_receipt,rms.employee_id,rms.manager_id,rms.rs_id,rms.rt_id,rmst.rs_status,rmtp.rt_type FROM "
					+ "reimbursement rms JOIN reimbursement_status rmst ON rms.rs_id= rmst.rs_id JOIN reimbursement_type rmtp ON rms.rt_id= rmtp.rt_id WHERE rms.employee_id=? AND UPPER(rmst.rs_status)='PENDING'";
			int parameterIndex=0;
			PreparedStatement prepstmt = connection.prepareStatement(sql);
			prepstmt.setInt(++parameterIndex, employeeId);
			ResultSet rs = prepstmt.executeQuery();
			while(rs.next()) {
				Reimbursement reimbursement = new Reimbursement(rs.getInt("r_id"),rs.getTimestamp("r_requested").toLocalDateTime(),
						rs.getDouble("r_amount"),rs.getString("r_description"),rs.getObject("r_receipt")
						,new Employee(rs.getInt("employee_id")),new Employee(rs.getInt("employee_id")), new ReimbursementStatus(rs.getInt("rs_id"),rs.getString("rs_status")),new ReimbursementType(rs.getInt("rt_id"),rs.getString("rt_type")));
				reimbursementList.add(reimbursement);
			}
			return reimbursementList;
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Set<Reimbursement> selectFinalized(int employeeId) {
		try(Connection connection = ConnectionUtil.getConnection()){ 
			Set <Reimbursement> reimbursementList =  new HashSet<>();
			String sql="SELECT rms.r_id,rms.r_requested,rms.r_resolved,rms.r_amount,rms.r_description,rms.r_receipt,rms.employee_id,rms.manager_id,rms.rs_id,rms.rt_id,rmst.rs_status,rmtp.rt_type FROM "
					+ "reimbursement rms JOIN reimbursement_status rmst ON rms.rs_id= rmst.rs_id JOIN reimbursement_type rmtp ON rms.rt_id= rmtp.rt_id WHERE rms.employee_id=? AND (UPPER(rmst.rs_status)='DECLINE' OR UPPER(rmst.rs_status)='ACCEPT')"; 
			int parameterIndex=0;
			PreparedStatement prepstmt = connection.prepareStatement(sql);
			prepstmt.setInt(++parameterIndex, employeeId);
			ResultSet rs = prepstmt.executeQuery();
			while(rs.next()) {
				Reimbursement reimbursement = new Reimbursement(rs.getInt("r_id"),rs.getTimestamp("r_requested").toLocalDateTime(),rs.getTimestamp("r_resolved").toLocalDateTime(),rs.getDouble("r_amount"),rs.getString("r_description"),rs.getObject("r_receipt")
						,new Employee(rs.getInt("employee_id")), new Employee(rs.getInt("manager_id")), new ReimbursementStatus(rs.getInt("rs_id"),rs.getString("rs_status")),new ReimbursementType(rs.getInt("rt_id"),rs.getString("rt_type")));
				reimbursementList.add(reimbursement);
			}
			return reimbursementList;
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Set<Reimbursement> selectAllPending() {
		try(Connection connection = ConnectionUtil.getConnection()){ 
			Set <Reimbursement> reimbursementList =  new HashSet<>();
			String sql="SELECT rms.r_id,rms.r_requested,rms.r_resolved,rms.r_amount,rms.r_description,rms.r_receipt,rms.employee_id,rms.manager_id,rms.rs_id,rms.rt_id,rmst.rs_status,rmtp.rt_type FROM "
					+ "reimbursement rms JOIN reimbursement_status rmst ON rms.rs_id= rmst.rs_id JOIN reimbursement_type rmtp ON rms.rt_id= rmtp.rt_id WHERE UPPER(rmst.rs_status)='PENDING'";
			PreparedStatement prepstmt = connection.prepareStatement(sql);
			ResultSet rs = prepstmt.executeQuery();
			while(rs.next()) {
				Reimbursement reimbursement = new Reimbursement(rs.getInt("r_id"),rs.getTimestamp("r_requested").toLocalDateTime(),
						rs.getDouble("r_amount"),rs.getString("r_description"),rs.getObject("r_receipt")
						,new Employee(rs.getInt("employee_id")),new Employee(rs.getInt("employee_id")), new ReimbursementStatus(rs.getInt("rs_id"),rs.getString("rs_status")),new ReimbursementType(rs.getInt("rt_id"),rs.getString("rt_type")));
				reimbursementList.add(reimbursement);
			}
			return reimbursementList;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Set<Reimbursement> selectAllFinalized() {
		try(Connection connection = ConnectionUtil.getConnection()){ 
			Set <Reimbursement> reimbursementList =  new HashSet<>();
			String sql="SELECT rms.r_id,rms.r_requested,rms.r_resolved,rms.r_amount,rms.r_description,rms.r_receipt,rms.employee_id,rms.manager_id,rms.rs_id,rms.rt_id,rmst.rs_status,rmtp.rt_type FROM "
					+ "reimbursement rms JOIN reimbursement_status rmst ON rms.rs_id= rmst.rs_id JOIN reimbursement_type rmtp ON rms.rt_id= rmtp.rt_id WHERE UPPER(rmst.rs_status)='DECLINED' OR UPPER(rmst.rs_status)='APPROVED'"; 
			PreparedStatement prepstmt = connection.prepareStatement(sql);
			ResultSet rs = prepstmt.executeQuery();
			while(rs.next()) {
				Reimbursement reimbursement = new Reimbursement(rs.getInt("r_id"),rs.getTimestamp("r_requested").toLocalDateTime(),rs.getTimestamp("r_resolved").toLocalDateTime(),rs.getDouble("r_amount"),rs.getString("r_description"),rs.getObject("r_receipt")
						,new Employee(rs.getInt("employee_id")), new Employee(rs.getInt("manager_id")), new ReimbursementStatus(rs.getInt("rs_id"),rs.getString("rs_status")),new ReimbursementType(rs.getInt("rt_id"),rs.getString("rt_type")));
				reimbursementList.add(reimbursement);
			}
			return reimbursementList;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Set<ReimbursementType> selectTypes() {
		try(Connection connection = ConnectionUtil.getConnection()){ 
			Set <ReimbursementType> reimbursementList =  new HashSet<>();
			String sql=" SELECT * FROM reimbursement_type";
			PreparedStatement prepstmt = connection.prepareStatement(sql);
			ResultSet rs = prepstmt.executeQuery();
			while(rs.next()) {
				ReimbursementType reimbursement = new ReimbursementType(rs.getInt("rt_id"),rs.getString("rt_type"));
				reimbursementList.add(reimbursement);
			}
			return reimbursementList;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
