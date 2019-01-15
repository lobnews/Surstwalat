package de.fh_dortmund.inf.cw.surstwalat.client.user.util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Validator
 * 
 * @author Stephan Klimek
 */
public class Validator {
    
    public static final int MINIMALINPUTLENGTH = 5;
    public static final int MAXIMALINPUTLENGTH = 30;
    
    /**
     * Check valid email address
     *
     * @param email
     * @return
     */
    public static boolean checkEmailAddress(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            return false;
        }
        return true;
    }

    /**
     * Check input string lenght
     *
     * @param input
     * @return
     */
    public static boolean checkStringLength(String input) {
        return (input.length() >= MINIMALINPUTLENGTH && input.length() <= MAXIMALINPUTLENGTH);
    }
    
    /**
     * Is empty string
     * 
     * @param str
     * @return 
     */
    public static boolean isEmptyString(String str) {
        return (str != null && !str.isEmpty());
    }
}
