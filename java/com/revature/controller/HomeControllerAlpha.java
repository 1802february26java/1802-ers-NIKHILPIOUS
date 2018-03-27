package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

import com.revature.model.Employee;

public class HomeControllerAlpha implements HomeController {

	private static HomeController homeController = new HomeControllerAlpha();

	private HomeControllerAlpha() {}

	public static HomeController getInstance() {
		return homeController;
	}
		

	@Override
	public String showEmployeeHome(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");

		/* If customer is not logged in */
		if(loggedCustomer == null) {
			return "login.html";
		}

		return "home.html";
	}
	
	@Override
	public String showRequestForm(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");

		/* If customer is not logged in */
		if(loggedCustomer == null) {
			return "login.html";
		}

		return "request.html";
	}

	@Override
	public Object successReplay(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");

		/* If customer is not logged in */
		if(loggedCustomer == null) {
			return "login.html";
		}

		return "success.html";
	}

	@Override
	public Object showPendingPage(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");

		/* If customer is not logged in */
		if(loggedCustomer == null) {
			return "login.html";
		}

		return "pending.html";
	}
	

	@Override
	public Object showPofiePage(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");

		/* If customer is not logged in */
		if(loggedCustomer == null) {
			return "login.html";
		}

		return "profile.html";
	}
	
	


}

