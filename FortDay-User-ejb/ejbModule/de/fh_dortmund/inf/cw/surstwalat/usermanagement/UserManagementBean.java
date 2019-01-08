package de.fh_dortmund.inf.cw.surstwalat.usermanagement;

import de.fh_dortmund.inf.cw.surstwalat.common.usermanagement.Account;
import de.fh_dortmund.inf.cw.surstwalat.common.usermanagement.beans.interfaces.UserManagementLocal;
import de.fh_dortmund.inf.cw.surstwalat.common.usermanagement.beans.interfaces.UserManagementRemote;
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
            @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
        },
        mappedName = "java:global/jms/UserMessageTopic")
public class UserManagementBean implements MessageListener {

    private final ArrayList<Account> userList = new ArrayList<>();

    @Override
    public void onMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void delete(String username) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void changePassword(String password, String newPassword) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void disconnect() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String getUserName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void login(String accountName, String password) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void logout() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void register(String accountName, String email, String password) throws Exception {
        Account account = new Account();
        account.setName(accountName);
        account.setPassword(generateHash(password));

        userList.add(account);
    }

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