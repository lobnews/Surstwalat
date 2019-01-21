package de.fh_dortmund.inf.cw.surstwalat.usermanagement;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.beans.interfaces.UserManagementLocal;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.ejb.EJB;
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
public class UserManagementMessageBean implements MessageListener {

    @EJB
    private UserManagementLocal userManagement;

    /**
     *
     * @param incomingMessage Incoming message
     */
    @Override
    public void onMessage(Message incomingMessage) {
        ObjectMessage message = (ObjectMessage) incomingMessage;
        int messageType = -1;

        try {
            messageType = message.getIntProperty(PropertyType.MESSAGE_TYPE);
        } catch (JMSException e) {
            System.out.println("de.fh_dortmund.inf.cw.surstwalat.usermanagement.UserManagementMessageBean.onMessage(): JMSException");
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
    public void register(ObjectMessage message) {
        try {
            Account newAccount = (Account) message.getObject();
            userManagement.register(newAccount);
        } catch (JMSException e) {
            System.out.println("de.fh_dortmund.inf.cw.surstwalat.usermanagement.UserManagementMessageBean.register(): JMSException");
        }
    }

    /**
     * Login user
     *
     * @param message
     */
    public void login(ObjectMessage message) {
        try {
            Account newAccount = (Account) message.getObject();
            userManagement.login(newAccount);
        } catch (JMSException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Update email address
     *
     * @param message
     */
    public void updateEmailAddress(ObjectMessage message) {
        try {
            Account newAccount = (Account) message.getObject();
            userManagement.updateEmailAddress(newAccount);
        } catch (JMSException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Change password
     *
     * @param message
     */
    public void changePassword(ObjectMessage message) {
        try {
            Account newAccount = (Account) message.getObject();
            userManagement.changePassword(newAccount);
        } catch (JMSException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Delete account
     *
     * @param message
     */
    public void deleteAccount(ObjectMessage message) {
        try {
            Account newAccount = (Account) message.getObject();
            userManagement.deleteAccount(newAccount);
        } catch (JMSException e) {
            System.err.println(e.getMessage());
        }
    }
}
