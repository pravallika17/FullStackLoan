package com.fullStackLoan.Loan;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.fullStackLoan.Loan.entities.Customer;
import com.fullStackLoan.Loan.exceptions.AssetValueValidationException;
import com.fullStackLoan.Loan.exceptions.CalculatedEmiException;
import com.fullStackLoan.Loan.exceptions.DepositNotSufficientException;
import com.fullStackLoan.Loan.exceptions.InvalidLoginCredentialsException;
import com.fullStackLoan.Loan.exceptions.LoanAlreadyExistsException;
import com.fullStackLoan.Loan.exceptions.NoEmiExistsException;
import com.fullStackLoan.Loan.exceptions.NoLoanExistsException;
import com.fullStackLoan.Loan.exceptions.UserNameAlreadyExistsException;
import com.fullStackLoan.Loan.service.LoanServiceImplementation;



@SpringBootTest
@Transactional
@Rollback(true)
class LoanApplicationTests {
	
	@Autowired
	public LoanServiceImplementation service;
	

	public Customer createAccount() throws Exception
	{
		Customer customer = new Customer ( 101011011,"Tester@12","Tester@12","test@gmail.com", "Tester","9090919090",0, 0,0,0);
		service.addAccount (customer);
		return customer;
	}
	
	//1. TEST CASE for validating User from the database
	@Test
	public void validateUser() throws Exception
	{
		createAccount();
		assertEquals (true,service.checkUser("Tester@12", "Tester@12"));
	}
	
	//2. TEST CASE for validating customer from the database
	@Test
	public void ValidateCustomer() throws Exception
	{
		createAccount();
		Customer customer = service.getCustomer("Tester@12");
		assertEquals("Tester@12", customer.getUser_name());
		assertEquals("Tester@12", customer.getPassword());
		assertEquals("test@gmail.com", customer.getEmail());
		assertNotEquals("9091909090", customer.getPhone_number());
	}
	
	//3. TEST CASE for validating Phone Number from the database
	@Test
	public void validatePhoneNumber() throws Exception
	{
		createAccount();
		Customer customer = service.getCustomer("Tester@12");
		assertNotEquals("9999999995", customer.getPhone_number());
	}
	
	
	//4. TEST CASE for validation of creating a new account for customer
	@Test
	public void validateAddAccount() throws Exception
	{
		createAccount();
		Customer get_details_of_customer = service.getCustomer("Tester@12");
		assertEquals("Tester",get_details_of_customer.getCustomer_name());
		assertEquals("test@gmail.com",get_details_of_customer.getEmail());
	}
	
	//5.TEST CASE for validating Deposit Function
	@Test
	public void deposit() throws Exception 
	{
		createAccount();
		service.depositAmount("Tester@12",  300000);
		double balance=service.depositAmount("Tester@12",  300000);
		
		assertEquals(600000, balance);
		
	}
	
	//6.TEST CASE for validating ApplyLoan Function
	@Test
	public void validateApplyLoan() throws Exception
	{
		createAccount();
		service.depositAmount("Tester@12",  300000);
		int loan_balance = service.applyLoan("Tester@12", 300000, 200000);
		assertEquals(200000, loan_balance);
	}
	
	//7. TEST CASE for validating  Calculate EMI 
	@Test
	public  void validateCalculateEmi() throws Exception
	{
		createAccount();
		service.depositAmount("Tester@12",  300000);
		service.applyLoan("Tester@12", 300000, 200000);
		double calculate_emi = service.calculateEMI("Tester@12", 14, 1.5);
		assertEquals(22059, calculate_emi);
	}
	
	//8. TEST CASE for validating Pay EMI
	@Test
	public void validatePayEmi() throws Exception
	{
		createAccount();
		service.depositAmount("Tester@12",  300000);
		service.applyLoan("Tester@12", 300000, 200000);
		service.calculateEMI("Tester@12", 14, 1.5);
		double paid_emi = service.payEMI("Tester@12");
		assertEquals(177941, paid_emi);
	}

	//9. TEST CASE for validating ForeClose
	@Test
	public void validateForeClose() throws Exception
	{
		createAccount();
		service.depositAmount("Tester@12",  300000);
		service.applyLoan("Tester@12", 300000, 200000);
		int loan_amount = service.foreClose("Tester@12");
		assertEquals(0, loan_amount);
	}
	//10. TEST CASE for validating show Balance
	@Test
	public void validateShowBalance() throws Exception
	{
		createAccount();
		service.depositAmount("Tester@12",  300000);
		service.applyLoan("Tester@12", 300000, 200000);
		assertEquals(200000, service.showBalance("Tester@12"));
	}

	// 11. TEST CASE for validating Loan already exists
	@Test
	public void validateLoanExists() throws Exception
	{
		createAccount();
		service.depositAmount("Tester@12",  300000);
		service.applyLoan("Tester@12", 300000, 200000);
		assertThrows(LoanAlreadyExistsException.class, ()->{
			service.applyLoan("Tester@12",200000,300000);
		});
		
	}
	
	//12. TEST CASE for validating asset condition fails
	@Test
	public void validateAssetConditionFails() throws Exception
	{
		createAccount();
		service.depositAmount("Tester@12",  300000);
		assertThrows(AssetValueValidationException.class, ()->{
		service.applyLoan("Tester@12", 100001, 100000);
		});
	}
	
	//13.TEST CASE for validating No Sufficient Deposit
	@Test
	public void validateNoSufficientDeposit() throws Exception
	{
		createAccount();
		assertThrows(DepositNotSufficientException.class, ()->{
		service.applyLoan("Tester@12", 100001, 100000);
		});
	}
	//12. TEST CASE for validating already calculated EMI
	@Test
	public void validateCalculatedEmiFails() throws Exception
	{
		createAccount();
		service.depositAmount("Tester@12",  300000);
		service.applyLoan("Tester@12", 300000, 200000);
		service.calculateEMI("Tester@12", 14, 1.5);
		assertThrows(CalculatedEmiException.class, ()->{
			service.calculateEMI("Tester@12", 15, 1.5);
		});
	}
	
	//14.TEST CASE for validating invalid login credentials
	@Test
	public void validateLoginCredentialsException() throws Exception
	{
		createAccount();
		assertThrows(InvalidLoginCredentialsException.class, ()->{
			service.checkUser("Tester@12", "Tester@1");
		});
	}
	
	
	//15. TEST CASE for validating loan already exists exception
	@Test
	public void  validateNoEmiExists() throws Exception
	{
		createAccount();
		service.depositAmount("Tester@12",  300000);
		service.applyLoan("Tester@12", 300000, 200000);
		assertThrows(NoEmiExistsException.class, ()->{
			service.payEMI("Tester@12");
		});
	}
		
	//16. TEST CASE for validating Loan Exists Exception
	@Test
	public void validateNoLoanExists() throws Exception
	{
		createAccount();
		assertThrows(NoLoanExistsException.class, ()->{
		service.foreClose("Tester@12");
		});
	}
	
	//17.TEST CASE for validating phone number exists
	@Test
	public void validateUserNameExists() throws Exception
	{
		createAccount();
		assertThrows(UserNameAlreadyExistsException.class, ()->{
			createAccount();
			
		});
	}
		

}
	
	
	
	
	
