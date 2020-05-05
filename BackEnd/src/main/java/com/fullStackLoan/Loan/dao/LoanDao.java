package com.fullStackLoan.Loan.dao;

import java.util.List;

import com.fullStackLoan.Loan.entities.Customer;
import com.fullStackLoan.Loan.entities.Passbook;
import com.fullStackLoan.Loan.exceptions.InvalidLoginCredentialsException;
import com.fullStackLoan.Loan.exceptions.PhoneNumberExistsException;
import com.fullStackLoan.Loan.exceptions.UserNameAlreadyExistsException;


public interface LoanDao {
	
	public List<Customer> getAll();
	
	public Customer addAccount(Customer cust);
	
	public int applyLoan(Customer cust);
	
	public int showBalance( String userName);
	
	public double calculateEMI( Customer cust);
	
	public double payEMI(Customer cust);
	
	public int foreClose(Customer cust);
	
	public boolean checkUser( String userName, String password) throws InvalidLoginCredentialsException;

	public boolean userNameExists(String userName) throws  UserNameAlreadyExistsException ;
	
	public double deleteAccount(String userName);

	List<Passbook> printTransactions(String userName) ;

	boolean phNoExists(String phoneNo) throws PhoneNumberExistsException;

	double depositAmount(Customer customer);
	
		
}

	
	
		


