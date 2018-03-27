package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.revature.model.Employee;
import com.revature.model.EmployeeRole;
import com.revature.model.EmployeeToken;
import com.revature.util.ConnectionUtil;

public class EmployeeRepositoryDAO implements EmployeeRepository {
	private static  EmployeeRepository empRepos = new EmployeeRepositoryDAO();
	
	private EmployeeRepositoryDAO() {}
	
	public static EmployeeRepository getInstance() {
		return empRepos;
	}
	

	@Override
	public  boolean insert(Employee employee) {
		try(Connection connection = ConnectionUtil.getConnection();){

			String sql="INSERT INTO user_t(u_firstname,u_lastname,u_username,u_password,u_email,ur_id) VALUES(?,?,?,?,?,?)";
			String role = employee.getEmployeeRole().getType();
			PreparedStatement prepstmt=connection.prepareStatement(sql);
			int parameterIndex=0;
			prepstmt.setString(++parameterIndex, employee.getFirstName());
			prepstmt.setString(++parameterIndex, employee.getLastName());
			prepstmt.setString(++parameterIndex, employee.getUsername());
			prepstmt.setString(++parameterIndex, employee.getPassword());
			prepstmt.setString(++parameterIndex, employee.getEmail());
			prepstmt.setInt(++parameterIndex, employee.getEmployeeRole().getId());
			
			if(prepstmt.executeUpdate()>0) {
				return true;
			}
			

		} catch (SQLException e) {

			e.printStackTrace();
		}


		return false;
	}

	@Override
	public boolean update(Employee employee) {
		try(Connection connection = ConnectionUtil.getConnection();){
			String sql= "UPDATE user_t SET  u_firstname=?,u_lastname=?,u_password=?,u_email=? WHERE u_id=?";
			PreparedStatement prepstmt = connection.prepareStatement(sql);
			int parameterIndex=0;
			prepstmt.setString(++parameterIndex, employee.getFirstName());
			prepstmt.setString(++parameterIndex, employee.getLastName());
			prepstmt.setString(++parameterIndex, employee.getPassword());
			prepstmt.setString(++parameterIndex, employee.getEmail());
			prepstmt.setInt(++parameterIndex, employee.getId());
			
			if(prepstmt.executeUpdate()>0) {
				return true;
			}
			

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Employee select(int employeeId) {
		try(Connection connection = ConnectionUtil.getConnection();){
			String sql= "SELECT ut.u_id,ut.u_firstname, ut.u_lastname,ut.u_username,ut.u_password,ut.u_email,ur.ur_id,ur.ur_type FROM user_t ut JOIN user_role ur ON ut.ur_id=ur.ur_id WHERE ut.u_id=?";
			PreparedStatement prepstmt = connection.prepareStatement(sql);
			prepstmt.setInt(1, employeeId);
			ResultSet rs = prepstmt.executeQuery();
			while(rs.next()) {
				return new Employee(rs.getInt("u_id"),rs.getString("u_firstname"),rs.getString("u_lastname"),rs.getString("u_username"),rs.getString("u_password"),
						rs.getString("u_email"),new EmployeeRole(rs.getInt("ur_id"),rs.getString("ur_type")) );
			}
			

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Employee select(String username) {
		try(Connection connection = ConnectionUtil.getConnection();){
			String sql= "SELECT ut.u_id,ut.u_firstname, ut.u_lastname,ut.u_username,ut.u_password,ut.u_email,ur.ur_id,ur.ur_type FROM user_t ut JOIN user_role ur ON ut.ur_id=ur.ur_id WHERE ut.u_username=?";
			PreparedStatement prepstmt = connection.prepareStatement(sql);
			prepstmt.setString(1,username);
			ResultSet rs = prepstmt.executeQuery();
			while(rs.next()) {
				return new Employee(rs.getInt("u_id"),rs.getString("u_firstname"),rs.getString("u_lastname"),rs.getString("u_username"),rs.getString("u_password"),
						rs.getString("u_email"),new EmployeeRole(rs.getInt("ur_id"),rs.getString("ur_type")));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Set<Employee> selectAll() {
		try(Connection connection = ConnectionUtil.getConnection();){
			Set <Employee> employeeList = new HashSet<>();
			
			String sql= "SELECT ut.u_id,ut.u_firstname, ut.u_lastname,ut.u_username,ut.u_password,ut.u_email,ur.ur_id,ur.ur_type FROM user_t ut JOIN user_role ur ON ut.ur_id=ur.ur_id";
			PreparedStatement prepstmt= connection.prepareStatement(sql);
			ResultSet rs = prepstmt.executeQuery();
			while(rs.next()) {
				Employee emp= new Employee(rs.getInt("u_id"),rs.getString("u_firstname"),rs.getString("u_lastname"),rs.getString("u_username"),rs.getString("u_password"),
						rs.getString("u_email"),new EmployeeRole(rs.getInt("ur_id"),rs.getString("ur_type")));
				employeeList.add(emp);
			}
			return employeeList;
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getPasswordHash(Employee employee) {
		try(Connection connection = ConnectionUtil.getConnection();){
			String command = "SELECT GET_HASH(?) AS HASH FROM DUAL";
			PreparedStatement statement = connection.prepareStatement(command);
			statement.setString(1, employee.getPassword());
			ResultSet result = statement.executeQuery();

			if(result.next()) {
				return result.getString("HASH");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean insertEmployeeToken(EmployeeToken employeeToken) {
		try(Connection connection = ConnectionUtil.getConnection();){
			String sql="INSERT INTO password_recovery(pr_token,pr_time,u_id) VALUES(?,?,?)"; //employeeid from empotkn.employee
			PreparedStatement prepstmt = connection.prepareStatement(sql);
			int parameterIndex=0;
			prepstmt.setString(++parameterIndex, employeeToken.getToken());
			prepstmt.setTimestamp(++parameterIndex, java.sql.Timestamp.valueOf(employeeToken.getCreationDate()));
			prepstmt.setInt(++parameterIndex, employeeToken.getRequester().getId());
			
			if(prepstmt.executeUpdate()>0) {
				return true;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean deleteEmployeeToken(EmployeeToken employeeToken) {
		try(Connection connection = ConnectionUtil.getConnection();){
			String sql= "DELETE FROM password_recovery WHERE u_id=?";
			PreparedStatement prepstmt = connection.prepareStatement(sql);
			prepstmt.setInt(1,employeeToken.getRequester().getId());
			
			if(prepstmt.executeUpdate()>0) {
				return true;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return false;
	}

	@Override
	public EmployeeToken selectEmployeeToken(EmployeeToken employeeToken) {
		try(Connection connection = ConnectionUtil.getConnection();){
			String sql="SELECT pr_id,pr_token,pr_time,u_id FROM password_recovery WHERE u_id=?";
			PreparedStatement prepstmt = connection.prepareStatement(sql);
			int parameterIndex=0;
			prepstmt.setInt(++parameterIndex, employeeToken.getRequester().getId());
			ResultSet rs= prepstmt.executeQuery();
			while(rs.next()) {
				return new EmployeeToken(rs.getInt("pr_id"),rs.getString("pr_token"),rs.getTimestamp("pr_time").toLocalDateTime(),new Employee(rs.getInt("u_id")));
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

}
