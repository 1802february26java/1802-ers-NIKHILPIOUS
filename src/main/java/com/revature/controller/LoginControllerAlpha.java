package com.revature.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.ajax.UserMessage;
import com.revature.model.Employee;
import com.revature.service.EmployeeServiceBO;

public class LoginControllerAlpha implements LoginController {
	private static LoginController loginCntrlAplha = new LoginControllerAlpha();
	private LoginControllerAlpha() {}
	
	public static LoginController getInstance() {
		return loginCntrlAplha;
	}

	@Override
	public Object login(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(request.getMethod().equals("GET")){
			
		return "login.html";
		}

		Employee loginEmpAuth = EmployeeServiceBO.getInstance().authenticate(new Employee(request.getParameter("username"),request.getParameter("password")));
		
		if(loginEmpAuth==null){
		
			return new UserMessage("AUTHENITCATION ERROR");
		}
		
		else{
			session.setAttribute("validUserInfo", loginEmpAuth);
			return loginEmpAuth ;
			
		}
	}

	@Override
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "login.html";
	}

}
