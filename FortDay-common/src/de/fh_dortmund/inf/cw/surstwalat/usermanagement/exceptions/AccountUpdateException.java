package de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions;

/**
 *
 * @author Stephan Klimek
 */
public class AccountUpdateException extends Exception {

    public AccountUpdateException() {
        super();
    }
    
    public AccountUpdateException(String message) {
        super(message);
    }
}
