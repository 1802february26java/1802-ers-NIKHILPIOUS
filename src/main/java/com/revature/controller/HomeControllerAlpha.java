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

		if(loggedCustomer == null) {
			return "login.html";
		}

		return "home.html";
	}

	@Override
	public String showRequestForm(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");


		if(loggedCustomer == null) {
			return "login.html";
		}

		return "request.html";
	}

	@Override
	public Object successReplay(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");

		if(loggedCustomer == null) {
			return "login.html";
		}

		return "success.html";
	}

	@Override
	public Object showPendingPage(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");

		if(loggedCustomer == null) {
			return "login.html";
		}

		return "pending.html";
	}


	@Override
	public Object showPofiePage(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");


		if(loggedCustomer == null) {
			return "login.html";
		}

		return "profile.html";
	}

	@Override
	public Object updatePage(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");

		if(loggedCustomer == null) {
			return "login.html";
		}
		return "updateprof.html";
	}

	@Override
	public Object showManagerHome(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");


		if(loggedCustomer == null) {

			return "login.html";
		}

		return "mngrHome.html";
	}

	@Override
	public Object mngrPendPage(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");


		if(loggedCustomer == null) {
	
			return "login.html";
		}

		return "pendingMng.html";
	}



	@Override
	public Object mngrAllResPage(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");


		if(loggedCustomer == null) {

			return "login.html";
		}

		return "resolvedMng.html";
	}

	@Override
	public Object mngrViewSpeceficPage(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");


		if(loggedCustomer == null) {

			return "login.html";
		}

		return "mngrHome.html";
	}

	@Override
	public Object mngrAllEmpPage(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");

	
		if(loggedCustomer == null) {
			return "login.html";
		}

		return "viewallempMng.html";
	}

	@Override
	public Object showResolvedPage(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");

		
		if(loggedCustomer == null) {
			return "login.html";
		}

		return "resolveEmp.html";
	}

	@Override
	public Object decisionViewPage(HttpServletRequest request) {
		Employee loggedCustomer = (Employee) request.getSession().getAttribute("validUserInfo");

		
		if(loggedCustomer == null) {
			return "login.html";
		}

		return "decisionviewMng.html";
	}
	

}







