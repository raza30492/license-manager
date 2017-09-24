package com.jazasoft.licensemanager.exception;

/**
 * Created by mdzahidraza on 22/09/17.
 */
public class InvalidLicenseException extends RuntimeException {

    public InvalidLicenseException() {
        super();
    }

    public InvalidLicenseException(String message) {
        super(message);
    }

    public InvalidLicenseException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLicenseException(Throwable cause) {
        super(cause);
    }
}
