package com.revature.request;

import javax.servlet.http.HttpServletRequest;

import com.revature.controller.EmployeeInformationControllerAlpha;
import com.revature.controller.ErrorControllerAlpha;
import com.revature.controller.HomeControllerAlpha;
import com.revature.controller.LoginControllerAlpha;
import com.revature.controller.ReimbursementControllerAlpha;

/**
 * The RequestHelper class is consulted by the MasterServlet and provides
 * him with a view URL or actual data that needs to be transferred to the
 * client.
 * 
 * It will execute a controller method depending on the requested URI.
 * 
 * Recommended to change this logic to consume a ControllerFactory.
 * 
 * @author Revature LLC
 */
public class RequestHelper {
	private static RequestHelper requestHelper;

	private RequestHelper() {}

	public static RequestHelper getRequestHelper() {
		if(requestHelper == null) {
			return new RequestHelper();
		}
		else {
			return requestHelper;
		}
	}
	
	/**
	 * Checks the URI within the request object passed by the MasterServlet
	 * and executes the right controller with a switch statement.
	 * 
	 * @param request
	 * 		  The request object which contains the solicited URI.
	 * @return A String containing the URI where the user should be
	 * forwarded, or data (any object) for AJAX requests.
	 */
	public Object process(HttpServletRequest request) {
		switch(request.getRequestURI())
		{
		case "/ERS/logout.do":
			return LoginControllerAlpha.getInstance().logout(request);
		case "/ERS/login.do":
			return LoginControllerAlpha.getInstance().login(request);
		case"/ERS/home.do":
			return HomeControllerAlpha.getInstance().showEmployeeHome(request);
		case"/ERS/request.do":
			return HomeControllerAlpha.getInstance().showRequestForm(request);
		case"/ERS/pendinglist.do":
			return HomeControllerAlpha.getInstance().showPendingPage(request);
		case"/ERS/submit.do":
			return ReimbursementControllerAlpha.getInstance().submitRequest(request);
		case"/ERS/success.do":
			return HomeControllerAlpha.getInstance().successReplay(request);
		case"/ERS/viewprofile.do":
			return HomeControllerAlpha.getInstance().showPofiePage(request);
		case"/ERS/pending.do":
			return ReimbursementControllerAlpha.getInstance().multipleRequests(request);
		
		case "/ERS/profile.do":{
			System.out.println("viewp");
			return EmployeeInformationControllerAlpha.getInstance().viewEmployeeInformation(request);
		}	
		default:
			return new ErrorControllerAlpha().showError(request);
		}
	}
}
