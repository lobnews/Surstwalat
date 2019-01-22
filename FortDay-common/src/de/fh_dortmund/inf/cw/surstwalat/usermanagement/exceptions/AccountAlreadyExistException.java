package de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions;

/**
 *
 * @author Stephan Klimek
 */
public class AccountAlreadyExistException extends Exception {

    public AccountAlreadyExistException() {
        super();
    }
    
    public AccountAlreadyExistException(String message) {
        super(message);
    }
}
