package com.fullStackLoan.Loan.dao;

import java.util.Collections;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fullStackLoan.Loan.entities.Customer;
import com.fullStackLoan.Loan.entities.Passbook;
import com.fullStackLoan.Loan.exceptions.InvalidLoginCredentialsException;
import com.fullStackLoan.Loan.exceptions.PhoneNumberExistsException;
import com.fullStackLoan.Loan.exceptions.UserNameAlreadyExistsException;

@Repository
public class LoanDaoImplementation implements LoanDao{
static int account_no =1010110201;

@Autowired
EntityManager entityManager;


	/*
	 * (non-Javadoc)
	 * @see com.fullStackLoan.Loan.dao.LoanDao#getAll()
	 * Method Name : getAll()
	 * Return Type : List<Customer>
	 * Description : get all the customers from data base using Hibernate Queries
	 */
	@Override
	public List<Customer> getAll() {
		String cmd = "select customer from Customer customer";
		TypedQuery<Customer> q1 = entityManager.createQuery(cmd,Customer.class);
		List<Customer> users = q1.getResultList();
		return users;
		
	}
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.fullStackLoan.Loan.dao.LoanDao#addAccount(com.fullStackLoan.Loan.entities.Customer)
	 * Method Name : addAccount()
	 * Description : create a new account using Hibernate Queries and persist is used to save into data base
	 */
	@Override
	public Customer addAccount(Customer customer) {
		
		//count of the existing accounts  to assign account number
		String command = "SELECT count(customer.account_number) from Customer customer";
		TypedQuery<Long> query1 = entityManager.createQuery(command, Long.class);
		Long count = query1.getSingleResult();

		//if count is  greater than zero increment by 1 to the maximum of account numbers
		if(count > 0) {
		String command2 = "select max(customer.account_number) from Customer customer";
		TypedQuery <Integer> query2 = entityManager.createQuery(command2,Integer.class);
		int account_number = query2.getSingleResult();
		customer.setAccount_number( account_number +1);
		System.out.println(account_number);
		
		}
		else
		{
			customer.setAccount_number (account_no);

			
		}
		//persisting the data into database
		
		entityManager.persist(customer);
		System.out.println(account_no);
		return customer;
	}
	
	
	
	@Override
	//applying loan
	public int applyLoan (Customer customer) {
		 //merging the data into database of loan amount and asset value
		 entityManager.merge (customer);	
		 return (int) customer.getLoan_amount();	
	
	}
	

	@Override
	//show balance
	public int showBalance(String userName) {
		//get the customer of specific userName
		Customer customer = getCustomer(userName);
		entityManager.find (Customer.class, customer.getAccount_number());
		 return (int) customer.getLoan_amount();
	}
	
	//get all the customer details of a specific user
	public Customer getCustomer(String username)
	{
		String command = "select customer_details from Customer customer_details where customer_details.user_name=:user";
		TypedQuery<Customer> query = entityManager.createQuery(command,Customer.class);
		query.setParameter("user", username);
		Customer customer = query.getSingleResult();
		return customer;
	}

	//calculating the EMI for the specific customer
	@Override
	public double calculateEMI(Customer customer) {
		//merges the calculated EMI
		entityManager.merge(customer);
		return customer.getEmi();
	}

	//paying the EMI for the specific customer
	@Override
	public double payEMI(Customer customer) {
		 //merges the values
		 entityManager.merge(customer);
		 return customer.getLoan_amount();
	
	}
	
	//Fore closing the specific customer account
	@Override
	public int foreClose(Customer customer) {
		//merges the value to database
		entityManager.merge(customer);
		 return (int) customer.getLoan_amount();
	}
	
	
	
	@Override
	//prints all the transactions of a particular user
	public List<Passbook> printTransactions(String userName) {
		Customer customer = getCustomer(userName);
		List<Passbook> list = customer.getTransactions();
		Collections.reverse (list);
		return list;

		
	
	}
	
	//checks if the user is valid 
	@Override
	public boolean checkUser(String userName, String password) throws  InvalidLoginCredentialsException{
			try{
				//command for selecting user name and password from the data
				String cmd = "select customer_details from Customer customer_details where customer_details.user_name=:user and customer_details.password=:pwd";
				TypedQuery<Customer> query = entityManager.createQuery(cmd,Customer.class);

				//setting the parameters in the command with given user name and password
				query.setParameter ("user",userName);
				query.setParameter ("pwd", password);
				query.getSingleResult();
				return true;
			}

			//if login details are wrong
			catch(Exception ex) 
			{throw new InvalidLoginCredentialsException("Invalid Login Credentials");}
		}

	//checks if user name already exists
	@Override
	public boolean userNameExists(String userName) throws UserNameAlreadyExistsException {
		
		String command = "SELECT customer.user_name FROM Customer customer";
		TypedQuery<String> query = entityManager.createQuery(command, String.class);
		List<String> list = query.getResultList();
		if(list.contains(userName)) {
			 throw new UserNameAlreadyExistsException("User Name already exists");		
		}
			return false;
			
	}

	@Override
	public boolean phNoExists(String phoneNo) throws PhoneNumberExistsException {
		String command = "SELECT customer.phone_number FROM Customer customer";
		TypedQuery<String> query = entityManager.createQuery(command, String.class);
		List<String> list = query.getResultList();
		if(list.contains(phoneNo)) {
			 throw new PhoneNumberExistsException("Phone Number already exists");		
		}
			return false;
			
	}
	
	//To generate transaction id for every transaction 	
		public int generateTransactionId() {
			int transaction_id = 1;
			String command1 = "SELECT count(passbook.transaction_id) from Passbook passbook";
			TypedQuery<Long> query1 = entityManager.createQuery( command1 , Long.class);
			Long count=query1.getSingleResult();
			if(count>0) {
				String command2 = "SELECT max(passbook.transaction_id) from Passbook passbook";
				TypedQuery<Integer> query2 = entityManager.createQuery(command2, Integer.class);
				transaction_id=query2.getSingleResult();
				return ++transaction_id;
			}
				
			else
				return transaction_id;
		}

		@Override
		public double depositAmount(Customer customer) {
			entityManager.merge(customer);
			return customer.getDeposit();
		}

		@Override
		public double deleteAccount(String userName) {

			Customer c=getCustomer(userName);
			entityManager.remove(c);
			return 0;
			
		}

	

	

}
