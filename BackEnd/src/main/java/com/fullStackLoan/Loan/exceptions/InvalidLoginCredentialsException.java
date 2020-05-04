package com.fullStackLoan.Loan.exceptions;

public class InvalidLoginCredentialsException  extends Exception {
	private static final long serialVersionUID = 1L;
	public InvalidLoginCredentialsException(String errorMessage) {
		super(errorMessage);
	}

}
