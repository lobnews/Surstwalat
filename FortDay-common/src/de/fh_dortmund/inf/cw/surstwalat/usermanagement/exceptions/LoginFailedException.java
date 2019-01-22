package de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions;

/**
 *
 * @author Stephan Klimek
 */
public class LoginFailedException extends Exception {

    public LoginFailedException() {
        super();
    }
    
    public LoginFailedException(String message) {
        super(message);
    }
}
