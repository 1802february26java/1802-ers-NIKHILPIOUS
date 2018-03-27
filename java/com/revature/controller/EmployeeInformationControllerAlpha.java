package com.revature.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
		HttpSession session = request.getSession();
		Employee mngr = (Employee)session.getAttribute("validUserInfo");
		String htmlPage = "";
		if(mngr.getEmployeeRole().getId()==2) {
			String firstName=request.getParameter("fname");
			String lastName=request.getParameter("lname");
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
				htmlPage="success.html";
			}
			else {
				htmlPage="error.html";
			}
			}
		else {
			htmlPage="hackPage.html";
		}
	
		return htmlPage;
	}

	@Override
	public Object updateEmployee(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String htmlPage = "";
		String firstName=request.getParameter("fname");
		String lastName=request.getParameter("lname");
		String password=request.getParameter("password");
		String email=request.getParameter("email");

		Employee updateEmp = new Employee();
		updateEmp.setFirstName(firstName);
		updateEmp.setLastName(lastName);
		updateEmp.setPassword(password);
		updateEmp.setEmail(email);

		if(EmployeeServiceBO.getInstance().updateEmployeeInformation(updateEmp)) {
			htmlPage="success.html";
		}
		else {
			htmlPage="400.html";
		}
		return null;
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
