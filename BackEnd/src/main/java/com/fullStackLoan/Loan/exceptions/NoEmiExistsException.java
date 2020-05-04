package com.fullStackLoan.Loan.exceptions;

public class NoEmiExistsException extends Exception {
	private static final long serialVersionUID = 1L;
	public NoEmiExistsException(String errorMessage) {
		super(errorMessage);
	}

}
