package com.revature.controller;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.ajax.UserMessage;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
import com.revature.model.ReimbursementType;
import com.revature.service.ReimbursementServiceBO;
import com.revature.util.ERSConstants;

public class ReimbursementControllerAlpha implements ReimbursementController {

	private static ReimbursementController reimCntrlAplpha = new ReimbursementControllerAlpha();
	private ReimbursementControllerAlpha() {}

	public static ReimbursementController getInstance() {
		return reimCntrlAplpha;
	}

	@Override
	public Object submitRequest(HttpServletRequest request) {
		HttpSession session =request.getSession();
		if(request.getMethod().equals("GET")){
			return "home.html";
		}
		Employee loggedEmp= (Employee)session.getAttribute("validUserInfo");
		String date =request.getParameter("date");

		LocalDateTime strToLDT = LocalDateTime.now();
		double amount= Double.valueOf(request.getParameter("amount"));
		int loggedEmpId= loggedEmp.getId();
		int statusId= Integer.parseInt(request.getParameter("statusid"));
		int typeId= Integer.parseInt(request.getParameter("typeid"));

		Reimbursement reimbursement = new Reimbursement(strToLDT,amount,null,null,new Employee(loggedEmpId),new ReimbursementStatus(statusId),new ReimbursementType(typeId));
		if(ReimbursementServiceBO.getInstance().submitRequest(reimbursement)){
			return ReimbursementServiceBO.getInstance().getUserPendingRequests(loggedEmp);
		}

		else{

			return new UserMessage(ERSConstants.REIMBURSEMENT_SUBMIT_UNSUCCESSFUL);
		}
	}

	@Override
	public Object singleRequest(HttpServletRequest request) {

		HttpSession session = request.getSession();
		Reimbursement reimb = new Reimbursement();
		reimb.setId(Integer.valueOf(request.getParameter("reimbursementid")));
		Reimbursement returendReimb =ReimbursementServiceBO.getInstance().getSingleRequest(reimb);

		if(returendReimb==null){
			return new  UserMessage(ERSConstants.REIMBURSEMENT_SINGLE_REQUEST_UNSUCCESSFUL);
		}
		else{
			session.setAttribute("singleList",returendReimb );
			return returendReimb;
		}
	}

	@Override
	public Object multipleRequests(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Employee emp= (Employee)session.getAttribute("validUserInfo");
		Integer specifyReq=null;
		if(request.getParameter("reqpor_id")==null) {

			Employee mngreqEmp = new Employee();
			mngreqEmp.setId(Integer.valueOf(request.getParameter("id")));
			Set<Reimbursement> pendingSet= ReimbursementServiceBO.getInstance().getUserPendingRequests(mngreqEmp);
			Set<Reimbursement> decisionSet= ReimbursementServiceBO.getInstance().getUserFinalizedRequests(mngreqEmp);
			Set<Reimbursement> combineSet = new HashSet<>();
			for(Reimbursement d:pendingSet) {
				combineSet.add(d);
			}
			for(Reimbursement d:decisionSet) {
				combineSet.add(d);
			}
			return combineSet;
		}


		else {
			specifyReq= Integer.parseInt(request.getParameter("reqpor_id"));


			if(emp.getEmployeeRole().getId()==1){
				Set<Reimbursement> pendingSet= ReimbursementServiceBO.getInstance().getUserPendingRequests(emp);
				Set<Reimbursement> decisionSet= ReimbursementServiceBO.getInstance().getUserFinalizedRequests(emp);
				if(pendingSet==null&&decisionSet==null){
					return new UserMessage(ERSConstants.REIMBURSEMENT_EMP_NOLIST);
				}
				if(specifyReq==1) {

					return pendingSet;
				}
				else if(specifyReq==2) {

					return decisionSet;
				}
			}
			else if(emp.getEmployeeRole().getId()==2){
				Set<Reimbursement> pendingSetManager= ReimbursementServiceBO.getInstance().getAllPendingRequests();
				Set<Reimbursement> decisionSetManager= ReimbursementServiceBO.getInstance().getAllResolvedRequests();

				if(pendingSetManager==null&&decisionSetManager==null){
					return new UserMessage(ERSConstants.REIMBURSEMENT_MNGR_NOLIST);
				}
				if(specifyReq==1) {

					return pendingSetManager;}

				else if(specifyReq==2) {

					return decisionSetManager;}

			}
		}

		return new UserMessage(ERSConstants.REIMBURSEMENT_MULTI_REQUEST_UNSUCCESSFUL);
	}

	@Override
	public Object finalizeRequest(HttpServletRequest request) {
		HttpSession session = request.getSession();
		int r_id = Integer.valueOf(request.getParameter("reimbid"));

		LocalDateTime strLdt = LocalDateTime.now();
		ReimbursementStatus reimStatus = new ReimbursementStatus(Integer.valueOf(request.getParameter("status_id")));
		Reimbursement reimToUpdate= new Reimbursement(r_id,strLdt,reimStatus);

		if(ReimbursementServiceBO.getInstance().finalizeRequest(reimToUpdate)){
			return new UserMessage(ERSConstants.SUCCESS_MESSAGE);
		}
		else{
			return new UserMessage(ERSConstants.REIMBURSEMENT_FINAL_REQUEST_UNSUCCESSFUL);
		}


	}

	@Override
	public Object getRequestTypes(HttpServletRequest request) {
		HttpSession session= request.getSession();
		Set<ReimbursementType> reqType = ReimbursementServiceBO.getInstance().getReimbursementTypes();
		if(reqType==null){
			return new UserMessage(ERSConstants.REIMBURSEMENT_REQUEST_TYPE_MESSAGE);
		}
		else{
			session.setAttribute("requestType", reqType);
			return new UserMessage(ERSConstants.SUCCESS_MESSAGE);
		}


	}

}
