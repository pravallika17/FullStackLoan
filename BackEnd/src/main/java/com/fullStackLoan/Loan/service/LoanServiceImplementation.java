package com.fullStackLoan.Loan.service;

import java.util.ArrayList;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullStackLoan.Loan.dao.LoanDaoImplementation;
import com.fullStackLoan.Loan.entities.Customer;
import com.fullStackLoan.Loan.entities.Passbook;
import com.fullStackLoan.Loan.exceptions.AssetValueValidationException;
import com.fullStackLoan.Loan.exceptions.CalculatedEmiException;
import com.fullStackLoan.Loan.exceptions.DepositNotSufficientException;
import com.fullStackLoan.Loan.exceptions.NoEmiExistsException;
import com.fullStackLoan.Loan.exceptions.InvalidLoginCredentialsException;
import com.fullStackLoan.Loan.exceptions.LoanAlreadyExistsException;
import com.fullStackLoan.Loan.exceptions.NoLoanExistsException;
import com.fullStackLoan.Loan.exceptions.PhoneNumberExistsException;
import com.fullStackLoan.Loan.exceptions.UserNameAlreadyExistsException;


@Service
@Transactional

public class LoanServiceImplementation implements LoanService{


@Autowired
LoanDaoImplementation dao;
	
	
	/* (non-Javadoc)
	 * @see com.fullStackLoan.Loan.service.LoanService#getAll()
	 * Method Name : getAll()
	 * Return Type : list<Customer>
	 * Description : get the list of all customers in the database to be handled by admin login
	 *
	 */
	@Override
	public List<Customer> getAll() {
		return dao.getAll();
	}


	
	/*(non-Javadoc)
	 * @see com.fullStackLoan.Loan.service.LoanService#addAccount(com.fullStackLoan.Loan.entities.Customer)
	 * Method Name : addAccount()
	 * Return Type : Customer
	 * Description : Creates a new account by taking data from the customer and assign the rest fields as 0 in
	 * 				the service layer
	 * Exceptions : If user name already exists -  UserNameAlreadyExistsException
	 *				If Phone number already exists -PhoneNumberExistsException
	 */
	@Override
	public Customer addAccount(Customer customer) throws UserNameAlreadyExistsException,PhoneNumberExistsException {
		dao.userNameExists(customer.getUser_name());
		dao.phNoExists(customer.getPhone_number());;
		customer.setEmi(0.0);
		customer.setLoan_amount(0.0);
		customer.setAsset_value(0.0);
		customer.setTransactions(new ArrayList<Passbook>());
		System.out.println("0");
		return dao.addAccount(customer);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.fullStackLoan.Loan.service.LoanService#depositAmount(java.lang.String, double)
	 * Method Name : Deposit()
	 * Return Type : double
	 * Description : Deposits the amount 
	 * 
	 */
	

	@Override
	public double depositAmount(String userName, double amount) {
		
		Customer customer=dao.getCustomer(userName);
		double sum = customer.getDeposit() + amount;
		customer.setDeposit(sum);
		
		
		Passbook transaction=new Passbook(dao.generateTransactionId(), "Deposit",sum);
		transaction.setCustomer_details(customer);
		List<Passbook> transactions = customer.getTransactions();
		transactions.add(transaction);
		customer.setTransactions(transactions);
		return dao.depositAmount(customer);
	}


	
	
	/*
	 * (non-Javadoc)
	 * @see com.fullStackLoan.Loan.service.LoanService#applyLoan(java.lang.String, double, double)
	 * Method Name : applyLoan
	 * Return Type : int
	 * Return value : loan amount
	 * Description : Appyling loan by taking fields asset value and loan amount
	 * Exceptions : If loan Account already exists - LoanAlreadyExistsException
	 * 				If Deposit Not sufficient - No sufficient Deposit Exception
	 * 				If asset value condition fails - AssetValueValidationException
	 */
	
	@Override
	public int applyLoan(String userName,double assetVal,double loanAmount) throws LoanAlreadyExistsException
											, AssetValueValidationException{
		
		Customer customer = dao.getCustomer (userName);
		
		if(customer.getLoan_amount()>0)
			throw new LoanAlreadyExistsException ("Loan Account With Balance Already Exists");
		
		if(customer.getDeposit() < (0.4 * loanAmount) )
		{
			throw new DepositNotSufficientException ("No sufficient Deposited Amount");
		}

		if( assetVal< (loanAmount*1.5) )
			throw new AssetValueValidationException ("Asset Value Should be 1.5 times greater than loan amount");
	
		
		customer.setAsset_value (assetVal);
		customer.setLoan_amount (loanAmount);
		
		//setting the transaction
		
		Passbook transaction=new Passbook (dao.generateTransactionId(), "Borrowed", loanAmount);
		transaction.setCustomer_details (customer);
		List<Passbook> transactions = customer.getTransactions();
		transactions.add(transaction);
		customer.setTransactions(transactions);
		
		return dao.applyLoan(customer);
		
	}


	
	/*
	 * (non-Javadoc)
	 * @see com.fullStackLoan.Loan.service.LoanService#showBalance(java.lang.String)
	 * Method Name : Show Balance
	 * Return Type : int
	 * Return Value : loan amount
	 * Description : shows Balance
	 */
	@Override
	public int showBalance(String userName) {
		return dao.showBalance(userName);	
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.fullStackLoan.Loan.service.LoanService#calculateEMI(java.lang.String, int, double)
	 * Method Name : calculateEmi
	 * Return Type : double
	 * Return Value : calculated EMI
	 * Description : calculates EMI for the applied loan
	 * Exceptions : if no loan account exists - NoLoanExistsException
	 * 				if emi already calculated - CalculatedEmiException
	 * 			
	 */
	
	@Override
	public double calculateEMI(String userName, int period, double roi) throws CalculatedEmiException, NoLoanExistsException {
		
		double emi = 0.0;
		
		Customer customer = dao.getCustomer(userName);
		
		//checks whether already a loan account exists or not
		if(customer.getLoan_amount() == 0)
			throw new NoLoanExistsException("Please Apply Loan First");
		
		//if EMI is already calculated
		if(customer.getEmi() > 0) {
			throw new CalculatedEmiException("You have already calculated EMI");
		}	
		
		//if no EMI is calculated, calculate
		else
		{
			emi = ((customer.getLoan_amount() * roi * (1 + roi)  * period)/((1 + roi)* period - 1))/period;
			double rounded_emi = Math.round(emi);
			customer.setEmi (rounded_emi);
			return dao.calculateEMI (customer);
		}
	
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.fullStackLoan.Loan.service.LoanService#payEMI(java.lang.String)
	 * Method Name : payEMI
	 * Return Type : double
	 * Return value : loan Balance
	 * Description : Pays EMI for the loan account
	 * Exceptions : if no loan applied - NoLoanExistsException
	 *			if EMI not calculated yet - NoEmiExistsException
	 *			if deposited amount is not sufficient to pay - DepositNotSufficientException
	 */

	@Override
	public double payEMI(String userName) throws NoEmiExistsException, NoLoanExistsException{
		Customer customer=dao.getCustomer(userName);
		
		//checks whether already a loan account exists or not
		if (customer.getLoan_amount() == 0)
			throw new NoLoanExistsException("Please Apply Loan First");
		
		//if EMI is not calculated yet
		if(customer.getEmi()==0)
			throw new NoEmiExistsException("Please Calculate EMI");
		
		double deposit_bal=customer.getDeposit()-customer.getEmi();
		double loan_bal=customer.getLoan_amount()-customer.getEmi();
		
		if(deposit_bal<0) {
			throw new DepositNotSufficientException("Please Deposit Amount");
		}
		//if all emi, loan balance and asset value greater than 0
		if(deposit_bal>=0 &&loan_bal>=0)
		{
			customer.setDeposit (deposit_bal);
			customer.setLoan_amount (loan_bal);
			Passbook transaction = new Passbook (dao.generateTransactionId(), "EMI Paid",customer.getEmi());
			transaction.setCustomer_details(customer);
			List<Passbook> transactions = customer.getTransactions();
			transactions.add(transaction);
			customer.setTransactions(transactions);
			return dao.payEMI(customer);
		}
		
		//if negative values occured for loan amount
		else {
			customer.setAsset_value(0);
			customer.setLoan_amount(0);
			customer.setEmi(0);
			Passbook transaction=new Passbook(dao.generateTransactionId(), "EMI Paid",customer.getEmi());
			transaction.setCustomer_details(customer);
			List<Passbook> transactions = customer.getTransactions();
			transactions.add(transaction);
			customer.setTransactions(transactions);
			return dao.payEMI(customer);
		}
			
	}
	

	
	/*
	 * (non-Javadoc)
	 * @see com.fullStackLoan.Loan.service.LoanService#foreClose(java.lang.String)
	 * Method Name : foreClose()
	 * Return Type : int
	 * Return value : loan Amount
	 * Description : closes the loan account
	 * Exceptions : if no loan applied - NoLoanExistsException
	 */
	
	@Override
	public int foreClose(String userName) throws NoLoanExistsException {
		Customer customer=dao.getCustomer(userName);
		
		//checks whether already a loan account exists or not
		if(customer.getLoan_amount() == 0)
			throw new NoLoanExistsException("Please Apply Loan First");
		
		
		customer.setAsset_value(0);
		customer.setEmi(0);
		customer.setLoan_amount(0);
		return dao.foreClose( customer);
	
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.fullStackLoan.Loan.service.LoanService#printTransactions(java.lang.String)
	 *Method name : printTransactions
	 * Description : list all the transactions
	 */
	@Override
	public List<Passbook> printTransactions(String userName) {
		return dao.printTransactions(userName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.fullStackLoan.Loan.service.LoanService#checkUser(java.lang.String, java.lang.String)
	 * Method Name : checkUser()
	 * Return Type : boolean
	 */

	@Override
	public boolean checkUser(String userName, String password) throws InvalidLoginCredentialsException {
		return dao.checkUser(userName,password);
	}


	@Override
	public boolean userNameExists(String userName) throws UserNameAlreadyExistsException{
		return dao.userNameExists(userName);
	}


	@Override
	public Customer getCustomer(String userName) {
		return dao.getCustomer(userName);
	}


	@Override
	public boolean phNoExists(String phNo) throws PhoneNumberExistsException {
		return dao.phNoExists(phNo);
	}

	

	@Override
	public double deleteAccount(String userName) {
		return dao.deleteAccount(userName);
	}





	




	
}
