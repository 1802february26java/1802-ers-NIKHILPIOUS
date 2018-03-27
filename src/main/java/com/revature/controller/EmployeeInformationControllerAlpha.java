package com.revature.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.ajax.ClientMessage;
import com.revature.model.Employee;
import com.revature.model.EmployeeRole;
import com.revature.service.EmployeeServiceBO;

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
		ClientMessage cmsg = null;
		
		if(mngr.getEmployeeRole().getId()==2) {
			String firstName=request.getParameter("firstname");
			String lastName=request.getParameter("lastname");
			String userName=request.getParameter("username");
			String password=request.getParameter("password");
			String email=request.getParameter("email");
			EmployeeRole empRole = new EmployeeRole(Integer.parseInt(request.getParameter("ur_id")));
					
			Employee newEmp = new Employee();
			newEmp.setFirstName(firstName);
			newEmp.setLastName(lastName);
			newEmp.setUsername(userName);
			newEmp.setPassword(password);
			newEmp.setEmail(email);
			newEmp.setEmployeeRole(empRole);
			
			if(EmployeeServiceBO.getInstance().createEmployee(newEmp)) {
				System.out.println("registerd");
				cmsg = new ClientMessage("SUCCESS"); 
			}
			else {
				return "error.html";
			}
			}
		else {
			return "hackPage.html";
		}
	
		return cmsg;
	}

	@Override
	public Object updateEmployee(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Employee emp = (Employee)session.getAttribute("validUserInfo");
		ClientMessage cmsg = new ClientMessage();
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
		else {//if its empty
			updateEmp.setPassword(EmployeeServiceBO.getInstance().getEmployeeInformation(updateEmp).getPassword());
			
		}

		if(EmployeeServiceBO.getInstance().updateEmployeeInformation(updateEmp)) {
			 cmsg.setMessage("SUCCESS");
		}
		else {
			return "400.html";
		}
		return cmsg;
	}

	@Override
	public Object viewEmployeeInformation(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Employee validEmp = (Employee)session.getAttribute("validUserInfo");//it has all the inforamtion about an employee Includes the user id
		Object htmlPage = "";
		int roleid= validEmp.getEmployeeRole().getId();
		System.out.println(roleid+";kijipp");
		if(validEmp.getEmployeeRole().getId()==1) {
			
			// imagine you click on view my profile , then it will send a request along with the userid
			//so of now I am thinking I will get user id aliong with tthe request
			//if it's not true then i cna get the userid from claiduserinfio anyway
			Employee temp = new  Employee();
			//temp.setId(Integer.parseInt(request.getParameter("emp_id")));
			int emp_id= validEmp.getId();
			temp.setId(emp_id);
			
			//Employee emp = EmployeeServiceBO.getInstance().getEmployeeInformation(validEmp);
			
			Employee emp = EmployeeServiceBO.getInstance().getEmployeeInformation(temp);
			
			if(emp==null) {
				htmlPage="error.html";
			}
			else {
				htmlPage= emp;
			}
			}
		else if(validEmp.getEmployeeRole().getId()==2) {
			// i belive manger pick a empoloyee either from a list , ort  typing hios first or last name
			
			Employee temp = new  Employee();
			temp.setId(Integer.parseInt(request.getParameter("emp_id")));
			Employee emp = EmployeeServiceBO.getInstance().getEmployeeInformation(temp);
			if(emp==null) {
				htmlPage="error.html";
			}
			else {
				htmlPage="myProfile.html";
			}
		}

		return htmlPage;
	}

	@Override
	public Object viewAllEmployees(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Employee validEmp = (Employee)session.getAttribute("validUserInfo");
		String htmlPage = "";
		if(validEmp.getEmployeeRole().getId()==2) {
			Set<Employee> listOfEmps= EmployeeServiceBO.getInstance().getAllEmployeesInformation();
			
			if(listOfEmps==null) {
				htmlPage="null.html";
			}
			else {
				htmlPage="listEmp.html";
			}
			
		}
		else {
			htmlPage="hackPage.html";
		}
		return htmlPage;
	}

	@Override
	public Object usernameExists(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Employee validEmp = (Employee)session.getAttribute("validUserInfo");
		String htmlPage = "";
		if(validEmp.getEmployeeRole().getId()==2) {
			Employee tempEmp = new Employee();
			tempEmp.setUsername(request.getParameter("username"));

			if(EmployeeServiceBO.getInstance().isUsernameTaken(tempEmp)){
				htmlPage="goodToGO.html";
			}
			else {
				htmlPage="UserNameTaken.html";
			}
			
		}
		else {
			htmlPage="hackPage.html";
		}
	
		return htmlPage;
	}

}
