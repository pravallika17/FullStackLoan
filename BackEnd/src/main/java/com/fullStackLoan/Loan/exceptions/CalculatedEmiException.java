package com.fullStackLoan.Loan.exceptions;

public class CalculatedEmiException   extends Exception {
	private static final long serialVersionUID = 1L;
	
	public CalculatedEmiException(String errorMessage) {
		super(errorMessage);
	}

}
