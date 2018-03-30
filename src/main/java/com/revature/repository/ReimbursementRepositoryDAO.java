package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
import com.revature.model.ReimbursementType;
import com.revature.util.ConnectionUtil;
import com.revature.util.ERSQueryConstants;

public class ReimbursementRepositoryDAO implements ReimbursementRepository {
	
	private static ReimbursementRepository reimburseRepo = new ReimbursementRepositoryDAO(); 
	private static Logger logger = Logger.getLogger(ReimbursementRepositoryDAO.class);
	private ReimbursementRepositoryDAO() {};
	
	public static ReimbursementRepository getInstance() {
		return reimburseRepo;
	}

	@Override
	public boolean insert(Reimbursement reimbursement) {
		try(Connection connection = ConnectionUtil.getConnection()){ 
			String sql=ERSQueryConstants.REIMBURSEMENT_INSERT; 
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
				logger.error("Reimbursement insertion failed", e);
			}
		return false;
	}

	@Override
	public boolean update(Reimbursement reimbursement) {
		try(Connection connection = ConnectionUtil.getConnection()){ 
			String sql = ERSQueryConstants.REIMBURSEMENT_UPDATE;
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
			logger.error("Reimbursement update failed", e);
		}
		return false;
	}

	@Override
	public Reimbursement select(int reimbursementId) {
		try(Connection connection = ConnectionUtil.getConnection()){ 
			String sql=ERSQueryConstants.REIMBURSEMENT_SELECT;
			
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
			logger.error("Reimbursement select failed", e);
		}
		
		return null;
	}

	@Override
	public Set<Reimbursement> selectPending(int employeeId) {
		try(Connection connection = ConnectionUtil.getConnection()){ 
			Set <Reimbursement> reimbursementList =  new HashSet<>();
			String sql=ERSQueryConstants.REIMBURSEMENT_SELECT_PENDING;
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
			logger.error("Reimbursement select pedning request failed", e);
		}
		
		return null;
	}

	@Override
	public Set<Reimbursement> selectFinalized(int employeeId) {
		try(Connection connection = ConnectionUtil.getConnection()){ 
			Set <Reimbursement> reimbursementList =  new HashSet<>();
			String sql=ERSQueryConstants.REIMBURSEMENT_SELECT_FINALIZED;
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
			logger.error("Reimbursement select resoved requests failed", e);
		}
		
		return null;
	}

	@Override
	public Set<Reimbursement> selectAllPending() {
		try(Connection connection = ConnectionUtil.getConnection()){ 
			Set <Reimbursement> reimbursementList =  new HashSet<>();
			String sql=ERSQueryConstants.REIMBURSEMENT_SELECT_ALL_PENDING;
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
			logger.error("Reimbursement selecting all pending requests failed", e);
		}
		
		return null;
	}

	@Override
	public Set<Reimbursement> selectAllFinalized() {
		try(Connection connection = ConnectionUtil.getConnection()){ 
			Set <Reimbursement> reimbursementList =  new HashSet<>();
			String sql=ERSQueryConstants.REIMBURSEMENT_SELECT_ALL_FINALIZED; 
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
			String sql=ERSQueryConstants.REIMBURSEMENT_SELECT_ALL_TYPES;
			PreparedStatement prepstmt = connection.prepareStatement(sql);
			ResultSet rs = prepstmt.executeQuery();
			while(rs.next()) {
				ReimbursementType reimbursement = new ReimbursementType(rs.getInt("rt_id"),rs.getString("rt_type"));
				reimbursementList.add(reimbursement);
			}
			return reimbursementList;
		}
		catch(SQLException e) {
			logger.error("Reimbursement types select failed", e);
		}
		
		return null;
	}

}
