package com.revature.service;

import java.util.Set;

import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementType;
import com.revature.repository.ReimbursementRepositoryDAO;

public class ReimbursementServiceBO implements ReimbursementService {
	
	private static ReimbursementService reimSev = new ReimbursementServiceBO();
	
	private ReimbursementServiceBO(){}
	
	public static ReimbursementService getInstance(){
		return reimSev;
	}

	@Override
	public boolean submitRequest(Reimbursement reimbursement) {
		// TODO Auto-generated method stub
		return ReimbursementRepositoryDAO.getInstance().insert(reimbursement);
	}

	@Override
	public boolean finalizeRequest(Reimbursement reimbursement) {
		// TODO Auto-generated method stub
		return ReimbursementRepositoryDAO.getInstance().update(reimbursement);
	}

	@Override
	public Reimbursement getSingleRequest(Reimbursement reimbursement) {
		
		//so basically a manger  can view all pending and approved/declined reimbursements... sO IT ILL DIPAL AS A LIST
		//AND WHN HE CLIK ONEOF THE WE USE THIS  methood ... os we can get the  id from the lsit
		// TODO Auto-generated method stub
		return ReimbursementRepositoryDAO.getInstance().select(reimbursement.getId());
	}

	@Override
	public Set<Reimbursement> getUserPendingRequests(Employee employee) {
		// TODO Auto-generated method stub
		return ReimbursementRepositoryDAO.getInstance().selectPending(employee.getId());
	}

	@Override
	public Set<Reimbursement> getUserFinalizedRequests(Employee employee) {
		// TODO Auto-generated method stub
		return ReimbursementRepositoryDAO.getInstance().selectFinalized(employee.getId());
	}

	@Override
	public Set<Reimbursement> getAllPendingRequests() {
		// TODO Auto-generated method stub
		return ReimbursementRepositoryDAO.getInstance().selectAllPending();
	}

	@Override
	public Set<Reimbursement> getAllResolvedRequests() {
		// TODO Auto-generated method stub
		return ReimbursementRepositoryDAO.getInstance().selectAllFinalized();
	}

	@Override
	public Set<ReimbursementType> getReimbursementTypes() {
		// TODO Auto-generated method stub
		return ReimbursementRepositoryDAO.getInstance().selectTypes();
	}

}
