package com.fullStackLoan.Loan.entities;


import java.util.List;



import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity

public class Customer {
	
	@Id
	public int account_number;
	
	@Length(max=20)
	public String user_name;
	
	
	@Length(max=20)
	private String password;
	
	@Length(max=20)
	public String email;
	
	@Length(max=20)
	private String customer_name;

	@Length(max=15)
	private String phone_number;
	
	private double deposit;
	private double asset_value;
	private double loan_amount;
	private double emi;
	

	@JsonBackReference
	@OneToMany(mappedBy="customer_details",cascade=CascadeType.ALL)
	public List<Passbook> transactions ;
	
	
	
	public Customer() {
		
	}



	public Customer(int account_number, String user_name, String password,
			 String email, String customer_name, String phone_number, double deposit
			, double asset_value, double loan_amount, double emi) {
		super();
		this.account_number = account_number;
		this.user_name = user_name;
		this.password = password;
		this.email = email;
		this.customer_name = customer_name;
		this.phone_number = phone_number;
		this.deposit=deposit;
		this.asset_value = asset_value;
		this.loan_amount = loan_amount;
		this.emi = emi;
	}



	public int getAccount_number() {
		return account_number;
	}



	public void setAccount_number(int account_number) {
		this.account_number = account_number;
	}



	public String getUser_name() {
		return user_name;
	}



	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getCustomer_name() {
		return customer_name;
	}



	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}



	public String getPhone_number() {
		return phone_number;
	}



	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}



	public double getAsset_value() {
		return asset_value;
	}



	public void setAsset_value(double asset_value) {
		this.asset_value = asset_value;
	}



	public double getLoan_amount() {
		return loan_amount;
	}



	public void setLoan_amount(double loan_amount) {
		this.loan_amount = loan_amount;
	}



	public double getEmi() {
		return emi;
	}



	public void setEmi(double emi) {
		this.emi = emi;
	}



	public List<Passbook> getTransactions() {
		return transactions;
	}



	public void setTransactions(List<Passbook> transactions) {
		this.transactions = transactions;
	}

	


	public double getDeposit() {
		return deposit;
	}



	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}



	@Override
	public String toString() {
		return "Customer [account_number=" + account_number + ", user_name=" + user_name + ", password=" + password
				+ ", email=" + email + ", customer_name=" + customer_name + ", phone_number=" + phone_number
				+ ", asset_value=" + asset_value + ", loan_amount=" + loan_amount + ", emi=" + emi + ", transactions="
				+ transactions + "]";
	}




	
	
	
}
