package com.fullStackLoan.Loan;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullStackLoan.Loan.entities.Customer;
import com.fullStackLoan.Loan.entities.Passbook;
import com.fullStackLoan.Loan.exceptions.AssetValueValidationException;
import com.fullStackLoan.Loan.exceptions.CalculatedEmiException;
import com.fullStackLoan.Loan.exceptions.NoEmiExistsException;
import com.fullStackLoan.Loan.exceptions.InvalidLoginCredentialsException;
import com.fullStackLoan.Loan.exceptions.LoanAlreadyExistsException;
import com.fullStackLoan.Loan.exceptions.NoLoanExistsException;
import com.fullStackLoan.Loan.exceptions.PhoneNumberExistsException;
import com.fullStackLoan.Loan.exceptions.UserNameAlreadyExistsException;
import com.fullStackLoan.Loan.service.LoanServiceImplementation;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api")
public class LoanController {
	
	/*
	 * Asking the autowired to create the instance of the bean
	 */
	@Autowired
	LoanServiceImplementation service;
	
	
	//HTTP request for getting all the users
	@GetMapping("/all")
	public List<Customer> getAll()
	{
		return service.getAll();
	}
	
	
	//HTTP request for creating a new account
	@PostMapping("/add")
	public Customer addAccount(@RequestBody Customer cust) throws  UserNameAlreadyExistsException, PhoneNumberExistsException
	{
		return service.addAccount(cust);
	}
	

	//HTTP request for  applying loan for customer	
	@GetMapping("/apply/{userName}/{assetVal}/{loanAmount}")
	public int applyLoan(@PathVariable String userName,@PathVariable double assetVal, @PathVariable double loanAmount) throws LoanAlreadyExistsException, AssetValueValidationException
	{
		return service.applyLoan(userName,assetVal,loanAmount);
	}
	
	
	
	//HTTP request for getting balance details of the customer
	@GetMapping("/get/{userName}")
	public int showBalance(@PathVariable String userName)
	{
		return service.showBalance(userName);
	}
	
	
	
	//HTTP request for calculating EMI for the customer
	@GetMapping("/calculate/{userName}/{years}/{roi}")
	public double calculateEMI(@PathVariable String userName,@PathVariable int years,@PathVariable double roi) throws  CalculatedEmiException, NoLoanExistsException
	{
		return service.calculateEMI(userName,years,roi);
	}

	
	
	//HTTP request for paying EMI for customer
	@GetMapping("/payEMI/{userName}")
	public double payEMI(@PathVariable String userName) throws NoEmiExistsException, NoLoanExistsException 
	{
		return service.payEMI(userName);
	}

	
	
	//HTTP request for foreclosing the customer account
	@GetMapping("/foreClose/{userName}")
	public int foreClose(@PathVariable String userName) throws NoLoanExistsException {
		return service.foreClose(userName);
	}
	
	
	
	//HTTP request for getting transactions of a specific customer
	@GetMapping("/print/{userName}")
	public List<Passbook> printAll(@PathVariable String userName) 
	{
		return service.printTransactions(userName);
	}

	
	
	//HTTP request for checking Login Credentials
	@GetMapping("/checkUser/{userName}/{password}")
	public boolean checkUser(@PathVariable String userName,@PathVariable String password) throws InvalidLoginCredentialsException {
		return  service.checkUser(userName,password);	
	} 

	
	
	//HTTP request for checking whether user name exists
	@GetMapping("/userNameexists/{userName}")
	public boolean userNameExists(@PathVariable String userName) throws UserNameAlreadyExistsException 
	{
		return service.userNameExists(userName);
	}

	
	
	//HTTP request for getting details of a specific Customer
	@GetMapping("/getCust/{userName}")
	public Customer getCustomer(@PathVariable String userName)
	{
		return service.getCustomer(userName);
	}

	
	
	//HTTP request for depositing amount
	@GetMapping("/deposit/{userName}/{amount}")
	public double depositAmount(@PathVariable String userName, @PathVariable double amount)
	{
		return service.depositAmount(userName, amount);
	}
	
	
	
	//HTTP request for editing account
	@DeleteMapping("/delete/{userName}")
	public double deleteAccount(@PathVariable String userName )
	{
		return service.deleteAccount(userName);
	}
	
	
}
