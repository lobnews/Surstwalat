package de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions;

/**
 *
 * @author Stephan Klimek
 */
public class WrongPasswordException extends Exception {

    public WrongPasswordException() {
        super();
    }
    
    public WrongPasswordException(String message) {
        super(message);
    }
}
