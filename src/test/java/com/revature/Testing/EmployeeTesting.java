package com.revature.Testing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.revature.model.Employee;
import com.revature.model.EmployeeRole;
import com.revature.model.EmployeeToken;
import com.revature.repository.EmployeeRepository;
import com.revature.repository.EmployeeRepositoryDAO;

public class EmployeeTesting {
	private EmployeeRepository empJdbc = null;
	private Employee emp = null;
	private EmployeeToken emptk = null;
	
	@Before
	public void befroeTest() {
		empJdbc = EmployeeRepositoryDAO.getInstance();		
	}
	
	@Test@Ignore
	public void insertTest() {
		emp= new Employee(0,"nikhil","pious","nikii","123","nik@gmail.com",new EmployeeRole(1));
		assertTrue(empJdbc.insert(emp));		
	} 

	@Test
	public void selectTest() {	
		assertEquals("nikhil",empJdbc.select("nikii").getFirstName());		
	} 

	@Test@Ignore
	public void selectWithIdTest() {
		assertEquals("nihkil",empJdbc.select(2).getFirstName());		
	} 


	@Test @Ignore
	public void updateTest() {
		emp= new Employee(2,"nikhil","pious","nikii","123","nik@gmail.com",new EmployeeRole(1));
		assertTrue(empJdbc.update(emp));
	} 
	
	@Test@Ignore
	public void selectAllTest() {
		assertTrue(empJdbc.selectAll().size()==2);
	} 
	
	@Test@Ignore
	public void insertEmployeeTokenTest() {
		emptk= new  EmployeeToken(0,"lopezzopel",LocalDateTime.now(),new Employee(1));
		assertTrue(empJdbc.insertEmployeeToken(emptk));
	} 
	

	@Test@Ignore
	public void insertEmployeeTokenNullTest() {
		emptk= new  EmployeeToken(0,null,LocalDateTime.now(),new Employee(21));
		assertTrue(empJdbc.insertEmployeeToken(emptk));
	} 
	
	@Test@Ignore
	public void deleteEmployeeTokenTest() {
		emptk= new  EmployeeToken(0,null,LocalDateTime.now(),new Employee(21));
		assertTrue(empJdbc.deleteEmployeeToken(emptk));
	} 
	
	@Test@Ignore
	public void selectEmployeeTokenTest() {
		emptk= new  EmployeeToken(new Employee(1));
		System.out.println(empJdbc.selectEmployeeToken(emptk).getToken());
		assertTrue(empJdbc.selectEmployeeToken(emptk)!=null);
	} 
	
	@Test@Ignore
	public void getPasswordHashTest() {
		emp= new Employee(0,"nikhi","pious","niki17","123abcD","nik@gmail.com",new EmployeeRole(1));
		assertEquals(empJdbc.select(23).getPassword(),empJdbc.getPasswordHash(emp));
	}
	
	

}
