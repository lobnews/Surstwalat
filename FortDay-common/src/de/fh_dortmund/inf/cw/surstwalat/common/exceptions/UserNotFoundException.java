package de.fh_dortmund.inf.cw.surstwalat.common.exceptions;

/**
 * @author Daniel Buschmann
 *
 */
public class UserNotFoundException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7550498359779039379L;

	public UserNotFoundException() {
		super("Incorrect username or password");
	}
}