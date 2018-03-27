package com.revature.controller;

import java.time.LocalDateTime;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.ajax.ClientMessage;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
import com.revature.model.ReimbursementType;
import com.revature.service.ReimbursementServiceBO;

public class ReimbursementControllerAlpha implements ReimbursementController {
	
	private static ReimbursementController reimCntrlAplpha = new ReimbursementControllerAlpha();
	private ReimbursementControllerAlpha() {}
	
	public static ReimbursementController getInstance() {
		return reimCntrlAplpha;
	}

	@Override
	public Object submitRequest(HttpServletRequest request) {
		System.out.println("yyyaya");
		HttpSession session =request.getSession();
		if(request.getMethod().equals("GET")){
			return "home.html";
		}
		Employee loggedEmp= (Employee)session.getAttribute("validUserInfo");
		System.out.println(loggedEmp.getId());
		String date =request.getParameter("date");
	
		LocalDateTime strToLDT = LocalDateTime.now();
		double amount= Double.valueOf(request.getParameter("amount"));
		int loggedEmpId= loggedEmp.getId();
		int statusId= Integer.parseInt(request.getParameter("statusid"));
		int typeId= Integer.parseInt(request.getParameter("typeid"));
		System.out.println(typeId);
		
		Reimbursement reimbursement = new Reimbursement(strToLDT,amount,null,null,new Employee(loggedEmpId),new ReimbursementStatus(statusId),new ReimbursementType(typeId));
		if(ReimbursementServiceBO.getInstance().submitRequest(reimbursement)){
			return ReimbursementServiceBO.getInstance().getUserPendingRequests(loggedEmp);
		}
		
		else{
		// TODO Auto-generated method stub
		return new ClientMessage("SOMETHING WORNG");
		}
	}

	@Override
	public Object singleRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// os I will have to get the id from front end.. so I am thniking tha when user enter it displaa list then when a user clik on one option it will send the id to 
		//thiis controller
		//basically a user manfager cna search theiemb by having just an id
		HttpSession session = request.getSession();
		Reimbursement reimb = new Reimbursement();
		reimb.setId(Integer.valueOf(request.getParameter("reimbursementid")));
		Reimbursement returendReimb =ReimbursementServiceBO.getInstance().getSingleRequest(reimb);
		
		if(returendReimb==null){
			System.out.println("no user found");
			return "samePage.html";
		}
		else{
			session.setAttribute("singleList",returendReimb );
			return null;
		}
	}

	@Override
	public Object multipleRequests(HttpServletRequest request) {
		System.out.println("yess");
		HttpSession session = request.getSession();
		Employee emp= (Employee)session.getAttribute("validUserInfo");
		if(emp.getEmployeeRole().getId()==1){
			System.out.println("yes");
			Set<Reimbursement> pendingSet= ReimbursementServiceBO.getInstance().getUserPendingRequests(emp);
			Set<Reimbursement> decisionSet= ReimbursementServiceBO.getInstance().getUserFinalizedRequests(emp);
			
			if(pendingSet==null&&decisionSet==null){
				return "nolstt.html";
			}
			session.setAttribute("reimList", pendingSet.addAll(decisionSet));
			return pendingSet;
		}
		else{
			System.out.println("nos");
			Set<Reimbursement> pendingSetManager= ReimbursementServiceBO.getInstance().getAllPendingRequests();
			Set<Reimbursement> decisionSetManager= ReimbursementServiceBO.getInstance().getAllResolvedRequests();
			
			if(pendingSetManager==null&&decisionSetManager==null){
				return "nolstt.html";
			}
			session.setAttribute("reimlistManager", pendingSetManager.addAll(decisionSetManager));
			return "NewList.html";
			
		}
	}

	@Override
	public Object finalizeRequest(HttpServletRequest request) {
		HttpSession session = request.getSession();
		int r_id = Integer.valueOf(request.getParameter("reimbid"));
		LocalDateTime strLdt = LocalDateTime.parse(request.getParameter("date"));
		ReimbursementStatus reimStatus = new ReimbursementStatus(Integer.valueOf(request.getParameter("statsu_id")));
		Reimbursement reimToUpdate= new Reimbursement(r_id,strLdt,reimStatus);
		
		if(ReimbursementServiceBO.getInstance().finalizeRequest(reimToUpdate)){
			return "success.html";
		}
		else{
			return "error.html";
		}
		// TODO Auto-generated method stub

	}

	@Override
	public Object getRequestTypes(HttpServletRequest request) {
		HttpSession session= request.getSession();
		Set<ReimbursementType> reqType = ReimbursementServiceBO.getInstance().getReimbursementTypes();
		if(reqType==null){
			return "error.html";
		}
		else{
			session.setAttribute("requestType", reqType);
			return "dropdown.html";
		}
	
		
	}

}
