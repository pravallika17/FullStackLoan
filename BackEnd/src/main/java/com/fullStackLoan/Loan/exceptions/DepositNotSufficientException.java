package com.fullStackLoan.Loan.exceptions;

public class DepositNotSufficientException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public DepositNotSufficientException(String errorMessage) {
		super(errorMessage);
	}

}
