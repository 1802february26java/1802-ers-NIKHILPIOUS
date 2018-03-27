package com.revature.Testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
import com.revature.model.ReimbursementType;
import com.revature.repository.ReimbursementRepository;
import com.revature.repository.ReimbursementRepositoryDAO;

public class ReimbursementTesting {
	private ReimbursementRepository remiRep =null;
	private Reimbursement remi= null;
	
	@Before
	public void beforeTesting() {
		remiRep = ReimbursementRepositoryDAO.getInstance();
		
	}

	@Test@Ignore
	public void insertTest() {
		remi= new Reimbursement(LocalDateTime.now(),7030.5,null,null,new Employee(2),new ReimbursementStatus(1),new ReimbursementType(3));
		assertTrue(remiRep.insert(remi));
	}
	
	@Test @Ignore
	public void updateTest() {
		remi= new Reimbursement(21,LocalDateTime.now(),new ReimbursementStatus(2));
		assertTrue(remiRep.update(remi));
	}
	
	@Test @Ignore
	public void selectTest() {
		double expectedbalacne=2010D;
		assertEquals(expectedbalacne,remiRep.select(4).getAmount(),0.0);
	}
	
	@Test @Ignore
	public void selectAllPendingTest() {
		assertEquals(5,remiRep.selectAllPending().size());
	}
	
	@Test@Ignore
	public void selectAllFinalizedTest() {
		assertEquals(1,remiRep.selectAllFinalized().size());
	}
	
	@Test@Ignore
	public void selectPendingTest() {
		assertEquals(1,remiRep.selectPending(1).size());
	}
	
	@Test@Ignore
	public void selectFinalizedTest() {
		assertEquals(1,remiRep.selectFinalized(61).size());
	}
	@Test@Ignore
	public void selectTypesTest() {
		assertEquals(4,remiRep.selectTypes().size());
	}

	

}
