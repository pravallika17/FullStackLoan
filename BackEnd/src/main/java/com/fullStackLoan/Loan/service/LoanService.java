package com.fullStackLoan.Loan.service;

import java.util.List;


import com.fullStackLoan.Loan.entities.Customer;
import com.fullStackLoan.Loan.entities.Passbook;
import com.fullStackLoan.Loan.exceptions.AssetValueValidationException;
import com.fullStackLoan.Loan.exceptions.CalculatedEmiException;
import com.fullStackLoan.Loan.exceptions.NoEmiExistsException;
import com.fullStackLoan.Loan.exceptions.HandlingException;
import com.fullStackLoan.Loan.exceptions.InvalidLoginCredentialsException;
import com.fullStackLoan.Loan.exceptions.LoanAlreadyExistsException;
import com.fullStackLoan.Loan.exceptions.NoLoanExistsException;
import com.fullStackLoan.Loan.exceptions.PhoneNumberExistsException;
import com.fullStackLoan.Loan.exceptions.UserNameAlreadyExistsException;

public interface LoanService {
	
	public List<Customer> getAll();
	
	public Customer addAccount(Customer cust) throws  UserNameAlreadyExistsException, PhoneNumberExistsException;
	
	public int applyLoan(String userName,double assetVal,double loanAmount) throws LoanAlreadyExistsException, AssetValueValidationException;
	
	public int showBalance( String userName);
	
	public double calculateEMI( String userName,int years,double roi) throws CalculatedEmiException, NoLoanExistsException;
	
	public double payEMI( String userName) throws HandlingException, NoEmiExistsException, NoLoanExistsException;
	
	public int foreClose( String userName) throws NoLoanExistsException;
	
	public List<Passbook> printTransactions( String userName);
	
	public boolean checkUser( String userName, String password) throws  InvalidLoginCredentialsException;
	
	public boolean userNameExists(String userName) throws  UserNameAlreadyExistsException;
	
	public Customer getCustomer(String userName); 

	boolean phNoExists(String phNo) throws  PhoneNumberExistsException;

	double depositAmount(String userName, double amount); 
	
	public double deleteAccount(String userName);
	
	public double editAccount(  Customer customer);
}
