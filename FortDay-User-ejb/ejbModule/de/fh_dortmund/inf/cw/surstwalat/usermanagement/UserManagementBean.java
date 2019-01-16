package de.fh_dortmund.inf.cw.surstwalat.usermanagement;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import java.util.ArrayList;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Stephan Klimek
 *
 */
@MessageDriven(
        activationConfig = {
            @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")},
        mappedName = "java:global/jms/UserMessageTopic")
public class UserManagementBean implements MessageListener {

    private final ArrayList<Account> accountList = new ArrayList<>();

    /**
     *
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param accountName
     * @param email
     * @param password
     * @throws Exception
     */
    public void register(String accountName, String email, String password) throws Exception {
        Account newAccount = new Account();

        newAccount.setName(accountName);
        newAccount.setEmail(email);
        newAccount.setPassword(password);

        accountList.add(newAccount);
    }

    /**
     * Login user
     *
     * @param username
     * @param password
     * @throws java.lang.Exception
     */
    public void login(String username, String password) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Update email address
     *
     * @param email
     */
    public void updateEmailAddress(String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Change password
     *
     * @param oldPassword
     * @param newPassword
     */
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Delete account
     */
    public void deleteAccount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param plaintext
     * @return
     */
    public String generateHash(String plaintext) {
        String hash;
        try {
            MessageDigest encoder = MessageDigest.getInstance("SHA-1");
            hash = String.format("%040x", new BigInteger(1, encoder.digest(plaintext.getBytes())));
        } catch (NoSuchAlgorithmException e) {
            hash = null;
        }
        return hash;
    }
}
