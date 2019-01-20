package de.fh_dortmund.inf.cw.surstwalat.usermanagement;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import java.util.ArrayList;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;

/**
 * @author Stephan Klimek
 *
 */
@MessageDriven(
        activationConfig = {
            @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")},
        mappedName = "java:global/jms/FortDayEventTopic")
public class UserManagementBean implements MessageListener {

    private final ArrayList<Account> accountList = new ArrayList<>();

    /**
     *
     * @param message Incoming message
     */
    @Override
    public void onMessage(Message message) {
        int messageType = -1;

        try {
            messageType = message.getIntProperty(PropertyType.MESSAGE_TYPE);
        } catch (JMSException e) {
            // TODO no message type
        }

        switch (messageType) {
            case MessageType.USER_REGISTER:
                register(message);
                break;
            case MessageType.USER_LOGIN:
                login(message);
                break;
            case MessageType.USER_UPDATE_EMAIL:
                updateEmailAddress(message);
                break;
            case MessageType.USER_DELETE:
                deleteAccount(message);
                break;
            case MessageType.USER_CHANGE_PASSWORD:
                changePassword(message);
                break;
        }
    }

    /**
     * Registered a new user
     *
     * @param message
     */
    public void register(Message message) {
        System.out.println("UserManagement: register!");

        ObjectMessage o = (ObjectMessage) message; 
        
        try {
            Account newAccount = (Account) o.getObject();
            
//            accountList.add(newAccount);
            System.out.println(newAccount.getEmail());
        } catch (JMSException e) {
            System.out.println("de.fh_dortmund.inf.cw.surstwalat.usermanagement.UserManagementBean.register(): ERROR - JSM EXCEPTION");
        }
    }

    /**
     * Login user
     *
     * @param message
     */
    public void login(Message message) {
        throw new UnsupportedOperationException("login is not supported yet.");

//        for (Account a : accountList) {
//            if (a.getName().equals(username)) {
//                if (a.getPassword().equals(generateHash(password))) {
//                    System.out.println(a.getEmail());
//                }
//            }
//        }
    }

    /**
     * Update email address
     *
     * @param message
     */
    public void updateEmailAddress(Message message) {
        throw new UnsupportedOperationException("updateEmailAddress is not supported yet.");
    }

    /**
     * Change password
     *
     * @param message
     */
    public void changePassword(Message message) {
        throw new UnsupportedOperationException("changePassword is not supported yet.");
    }

    /**
     * Delete account
     *
     * @param message
     */
    public void deleteAccount(Message message) {
        throw new UnsupportedOperationException("deleteAccount is not supported yet.");
    }

    /**
     *
     * @param plaintext
     * @return hashed text
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
