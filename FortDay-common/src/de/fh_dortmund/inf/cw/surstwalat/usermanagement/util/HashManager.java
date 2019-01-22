package de.fh_dortmund.inf.cw.surstwalat.usermanagement.util;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hash manager for hashing text or passwords
 * 
 * @author Stephan Klimek
 */
public class HashManager {

    private final static String HASH_ALGORITHM = "SHA-1";

    /**
     * Hash account password
     *
     * @param account account with plain password
     * @return account with hashed password
     */
    public static Account hashAccount(Account account) {
        account.setPassword(generateHash(account.getPassword()));
        return account;
    }

    /**
     * Hash plain password
     *
     * @param plainPassword plain password
     * @return hashed password
     */
    public static String hashPassword(String plainPassword) {
        return generateHash(plainPassword);
    }

    /**
     * Hash plain text
     *
     * @param plaintext plain text
     * @return hashed text
     */
    private static String generateHash(String plaintext) {
        String hash;
        try {
            MessageDigest encoder = MessageDigest.getInstance(HASH_ALGORITHM);
            hash = String.format("%040x", new BigInteger(1, encoder.digest(plaintext.getBytes())));
        } catch (NoSuchAlgorithmException e) {
            hash = null;
        }
        return hash;
    }
}
