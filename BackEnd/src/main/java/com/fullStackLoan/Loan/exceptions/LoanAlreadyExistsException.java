package com.fullStackLoan.Loan.exceptions;

public class LoanAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1L;
	public LoanAlreadyExistsException(String errorMessage) {
		super(errorMessage);
	}

}
