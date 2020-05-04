package com.fullStackLoan.Loan.exceptions;

public class AssetValueValidationException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public AssetValueValidationException(String errorMessage) {
		super(errorMessage);
	}
}
