package org.dice_research.opal.licenses.old.exceptions;

public class UnknownLicenseException extends LicensesException {

	private static final long serialVersionUID = 1L;

	public UnknownLicenseException() {
		super();
	}

	public UnknownLicenseException(String message) {
		super(message);
	}

	public UnknownLicenseException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownLicenseException(Throwable cause) {
		super(cause);
	}

	protected UnknownLicenseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}