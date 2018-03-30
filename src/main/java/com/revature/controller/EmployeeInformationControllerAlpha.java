package com.revature.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.ajax.UserMessage;
import com.revature.model.Employee;
import com.revature.model.EmployeeRole;
import com.revature.service.EmployeeServiceBO;
import com.revature.util.ERSConstants;

public class EmployeeInformationControllerAlpha implements EmployeeInformationController {
	
	private static EmployeeInformationController empCntlrAlpha = new EmployeeInformationControllerAlpha();
	
	private EmployeeInformationControllerAlpha() {}
	
	public static EmployeeInformationController getInstance() {
		return empCntlrAlpha;
	}

	@Override
	public Object registerEmployee(HttpServletRequest request) {
		
		if(request.getMethod().equals("GET")) {
			return "register.html";
		}
		HttpSession session = request.getSession();
		Employee mngr = (Employee)session.getAttribute("validUserInfo");
		UserMessage usermessage = null;
		
		if(mngr.getEmployeeRole().getId()==2) {
			String firstName=request.getParameter("firstname");
			String lastName=request.getParameter("lastname");
			String userName=request.getParameter("username");
			String password=request.getParameter("password");
			String email=request.getParameter("email");
			EmployeeRole empRole = new EmployeeRole(Integer.parseInt(request.getParameter("ur_id")));
					
			Employee newEmp = new Employee();
			
			newEmp.setUsername(userName);
			if(EmployeeServiceBO.getInstance().isUsernameTaken(newEmp)==false) {
				return new UserMessage(ERSConstants.EMPLOYEE_MESSAGE_USERNAME_TAKEN);
			}
			newEmp.setLastName(lastName);
			newEmp.setFirstName(firstName);
			newEmp.setPassword(password);
			newEmp.setEmail(email);
			newEmp.setEmployeeRole(empRole);
			
			if(EmployeeServiceBO.getInstance().createEmployee(newEmp)) {
				return new UserMessage(ERSConstants.EMPLOYEE_MESSAGE_REGISTRATION_SUCCESSFUL); 
			}
			else {
				return new UserMessage(ERSConstants.EMPLOYEE_MESSAGE_REGISTRATION_UNSUCCESSFUL);
			}
			}
		else {
			return new UserMessage(ERSConstants.UNAUTHORIZED_ATTEMPT_MESSAGE);
		}
	}

	@Override
	public Object updateEmployee(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Employee emp = (Employee)session.getAttribute("validUserInfo");
		UserMessage usermessage = new UserMessage();
		int empId=Integer.valueOf(request.getParameter("empId"));
		String firstName=request.getParameter("firstname");
		String lastName=request.getParameter("lastname");
		String password=request.getParameter("password");
		String email=request.getParameter("email");

		Employee updateEmp = new Employee();
		updateEmp.setId(empId);
		updateEmp.setFirstName(firstName);
		updateEmp.setLastName(lastName);
		updateEmp.setPassword(password);
		updateEmp.setEmail(email);
		if(!updateEmp.getPassword().isEmpty()) {
			EmployeeServiceBO.getInstance().updatePassword(updateEmp);
			updateEmp.setPassword(EmployeeServiceBO.getInstance().getEmployeeInformation(updateEmp).getPassword());
		}
		else {
			updateEmp.setPassword(EmployeeServiceBO.getInstance().getEmployeeInformation(updateEmp).getPassword());
			
		}

		if(EmployeeServiceBO.getInstance().updateEmployeeInformation(updateEmp)) {
			 usermessage.setMessage(ERSConstants.EMPLOYEE_MESSAGE_UPDATE_SUCCESSFUL);
		}
		else {
			 usermessage.setMessage(ERSConstants.EMPLOYEE_MESSAGE_UPDATE_SUCCESSFUL);
		}
		return usermessage;
	}

	@Override
	public Object viewEmployeeInformation(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Employee validEmp = (Employee)session.getAttribute("validUserInfo");
		UserMessage usermessage = new UserMessage();
		int roleid= validEmp.getEmployeeRole().getId();
		if(validEmp.getEmployeeRole().getId()==1) {
			Employee temp = new  Employee();
			int emp_id= validEmp.getId();
			temp.setId(emp_id);
			Employee employee = EmployeeServiceBO.getInstance().getEmployeeInformation(temp);
			if(employee==null) {
				usermessage.setMessage(ERSConstants.EMPLOYEE_MESSAGE_VIEW_INFORMATIOAN);
			}
			else {
				return employee;
			}
			
			}
		else if(validEmp.getEmployeeRole().getId()==2) {//*******************************
			Employee temp = new  Employee();
			temp.setId(Integer.parseInt(request.getParameter("emp_id")));
			Employee employee = EmployeeServiceBO.getInstance().getEmployeeInformation(temp);
			if(employee==null) {
				usermessage.setMessage(ERSConstants.EMPLOYEE_MESSAGE_UNKNOWN_FOR_NOW);
			}
			else {
				
				return "myProfile.html";
			}
		}

		return usermessage;
	}

	@Override
	public Object viewAllEmployees(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Employee validEmp = (Employee)session.getAttribute("validUserInfo");
		UserMessage usermessage = new UserMessage();
		if(validEmp.getEmployeeRole().getId()==2) {
			Set<Employee> listOfEmps= EmployeeServiceBO.getInstance().getAllEmployeesInformation();
			
			if(listOfEmps==null) {
				usermessage.setMessage(ERSConstants.EMPLOYEE_MESSAGE_VIEW_EMP_LIST);
			}
			else {
				return listOfEmps;
			}
			
		}
		else {
			usermessage.setMessage(ERSConstants.UNAUTHORIZED_ATTEMPT_MESSAGE);
		}
		return usermessage;
	}

	@Override
	public Object usernameExists(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Employee validEmp = (Employee)session.getAttribute("validUserInfo");
		UserMessage usermessage = new UserMessage();
		if(validEmp.getEmployeeRole().getId()==2) {
			Employee tempEmp = new Employee();
			tempEmp.setUsername(request.getParameter("username"));

			if(EmployeeServiceBO.getInstance().isUsernameTaken(tempEmp)){
				usermessage.setMessage(ERSConstants.EMPLOYEE_MESSAGE_USERNAME_AVAILABLITY_VALID);

			}
			else {
				usermessage.setMessage(ERSConstants.EMPLOYEE_MESSAGE_USERNAME_AVAILABLITY_UNSUCCESSFUL);
			}
			
		}

	
		return usermessage;
	}

}
