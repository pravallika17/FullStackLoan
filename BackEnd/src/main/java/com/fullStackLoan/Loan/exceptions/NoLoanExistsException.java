package com.fullStackLoan.Loan.exceptions;

public class NoLoanExistsException extends Exception {
	private static final long serialVersionUID = 1L;
	public NoLoanExistsException(String errorMessage) {
		super(errorMessage);
	}

}
