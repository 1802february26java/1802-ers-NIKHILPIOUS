package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * The HomeController provides a view to the user that fits his/her role.
 * 
 * @author Revature LLC
 */
public interface HomeController {
	
	/**
	 * Returns the home view to the employee if he/she is authenticated, matching their role.
	 */
	public String showEmployeeHome(HttpServletRequest request);

	public Object showPendingPage(HttpServletRequest request);

	public Object showPofiePage(HttpServletRequest request);

	public String showRequestForm(HttpServletRequest request);

	public Object successReplay(HttpServletRequest request);

	public Object updatePage(HttpServletRequest request);

	public Object showManagerHome(HttpServletRequest request);

	public Object mngrPendPage(HttpServletRequest request);

	public Object mngrAllResPage(HttpServletRequest request);

	public Object mngrViewSpeceficPage(HttpServletRequest request);

	public Object mngrAllEmpPage(HttpServletRequest request);

	public Object showResolvedPage(HttpServletRequest request);

	public Object decisionViewPage(HttpServletRequest request);
}
