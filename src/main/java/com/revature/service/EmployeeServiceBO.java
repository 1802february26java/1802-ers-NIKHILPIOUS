package com.revature.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

import com.revature.model.Employee;
import com.revature.model.EmployeeToken;
import com.revature.repository.EmployeeRepositoryDAO;

public class EmployeeServiceBO implements EmployeeService {
	
	private static EmployeeService empServ = new EmployeeServiceBO();
	
	private EmployeeServiceBO(){}
	
	public static EmployeeService getInstance(){
		return empServ;
	}

	@Override
	public Employee authenticate(Employee employee) {
		
		Employee loggedEmp = EmployeeRepositoryDAO.getInstance().select(employee.getUsername());
		if(loggedEmp.getPassword().equals(EmployeeRepositoryDAO.getInstance().getPasswordHash(employee))){
			return loggedEmp;
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getEmployeeInformation(Employee employee) {
		// i can use id because I will get every info from loogedEmp
		
		return  EmployeeRepositoryDAO.getInstance().select(employee.getId());
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Employee> getAllEmployeesInformation() {
		
		// TODO Auto-generated method stub
		return EmployeeRepositoryDAO.getInstance().selectAll();
	}

	@Override
	public boolean createEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return EmployeeRepositoryDAO.getInstance().insert(employee);
	}

	@Override
	public boolean updateEmployeeInformation(Employee employee) {
		// TODO Auto-generated method stub
		return EmployeeRepositoryDAO.getInstance().update(employee);
	}

	@Override
	public boolean updatePassword(Employee employee) {
		employee.setPassword(EmployeeRepositoryDAO.getInstance().getPasswordHash(employee));
		// TODO Auto-generated method stub
		return EmployeeRepositoryDAO.getInstance().update(employee);
	}

	@Override
	public boolean isUsernameTaken(Employee employee) {
		if(EmployeeRepositoryDAO.getInstance().select(employee.getUsername())==null){
			return true;
		}
		return false;
	}

	@Override
	public boolean createPasswordToken(Employee employee) {
		// TODO Auto-generated method stub
		return EmployeeRepositoryDAO.getInstance().insertEmployeeToken(new EmployeeToken(0,null,LocalDateTime.now(),employee));
	}

	@Override
	public boolean deletePasswordToken(EmployeeToken employeeToken) {
		// TODO Auto-generated method stub
		return EmployeeRepositoryDAO.getInstance().deleteEmployeeToken(employeeToken);
	}

	@Override
	public boolean isTokenExpired(EmployeeToken employeeToken) {
		LocalDateTime created =EmployeeRepositoryDAO.getInstance().selectEmployeeToken(employeeToken).getCreationDate();
		LocalDateTime enddate =LocalDateTime.now();
		Duration duration = Duration.between(created,enddate);
		if(duration.getSeconds()<172800L) { //2 days{
			return true;
			}
		// TODO Auto-generated method stub
		return false;
	}

}
