package de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions;

/**
 *
 * @author Stephan Klimek
 */
public class AccountNotFoundException extends Exception {

    public AccountNotFoundException() {
        super();
    }
    
    public AccountNotFoundException(String message) {
        super(message);
    }
}
