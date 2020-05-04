package com.fullStackLoan.Loan.entities;



import java.time.LocalDateTime;


import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Length;


@Entity
public class Passbook {
@Id
private int transaction_id;

@Length(max=20)
private String operation;

private double amount;

private String transaction_date;

private String transaction_time;

@ManyToOne
@JoinColumn(name="CUSTOMERDETAILS")
public Customer customer_details;

public Passbook() {}

public Passbook(int transaction_id, String operation, double amount) {
	super();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
	LocalDateTime now=LocalDateTime.now();
	this.transaction_id = transaction_id;
	this.operation = operation;
	this.amount = amount;
	this.transaction_date = dtf.format(now).substring(0, 10);;
	this.transaction_time = dtf.format(now).substring(11);
}


public int getTransaction_id() {
	return transaction_id;
}


public void setTransaction_id(int transaction_id) {
	this.transaction_id = transaction_id;
}


public String getOperation() {
	return operation;
}


public void setOperation(String operation) {
	this.operation = operation;
}


public double getAmount() {
	return amount;
}


public void setAmount(double amount) {
	this.amount = amount;
}


public String getTransaction_date() {
	return transaction_date;
}


public void setTransaction_date(String transaction_date) {
	this.transaction_date = transaction_date;
}


public String getTransaction_time() {
	return transaction_time;
}


public void setTransaction_time(String transaction_time) {
	this.transaction_time = transaction_time;
}

public Customer getCustomer_details() {
	return customer_details;
}

public void setCustomer_details(Customer customer_details) {
	this.customer_details = customer_details;
}

@Override
public String toString() {
	return "Passbook [transaction_id=" + transaction_id + ", operation=" + operation + ", amount=" + amount
			+ ", transaction_date=" + transaction_date + ", transaction_time=" + transaction_time
			+ ", customer_details=" + customer_details + "]";
}



}

