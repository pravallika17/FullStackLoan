package com.fullStackLoan.Loan.exceptions;

public class NoDepositException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public NoDepositException(String errorMessage) {
		super(errorMessage);
	}

}
