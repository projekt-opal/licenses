package org.dice_research.opal.licenses.exceptions;

public class LicensesException extends Exception {

	private static final long serialVersionUID = 1L;

	public LicensesException() {
		super();
	}

	public LicensesException(String message) {
		super(message);
	}

	public LicensesException(String message, Throwable cause) {
		super(message, cause);
	}

	public LicensesException(Throwable cause) {
		super(cause);
	}

	protected LicensesException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}