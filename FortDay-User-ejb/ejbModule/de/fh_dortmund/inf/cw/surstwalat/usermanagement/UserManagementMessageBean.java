package de.fh_dortmund.inf.cw.surstwalat.usermanagement;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.beans.interfaces.UserManagementLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;

/**
 * @author Stephan Klimek
 *
 */
@Deprecated
@MessageDriven(
        activationConfig = {
            @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")},
        mappedName = "java:global/jms/FortDayEventTopic")
public class UserManagementMessageBean implements MessageListener {

    @EJB
    private UserManagementLocal userManagement;

    /**
     * Listens to messages
     *
     * @param incomingMessage Incoming message
     */
    @Override
    public void onMessage(Message incomingMessage) {
        try {
            ObjectMessage message = (ObjectMessage) incomingMessage;
            int messageType = -1;

            try {
                messageType = message.getIntProperty(PropertyType.MESSAGE_TYPE);
            } catch (JMSException e) {
                System.err.println("de.fh_dortmund.inf.cw.surstwalat.usermanagement.UserManagementMessageBean.onMessage(): JMSException");
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
        } catch (Exception ex) {
            Logger.getLogger(UserManagementMessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Registered a new user
     *
     * @param message
     * @throws Exception
     */
    public void register(ObjectMessage message) throws Exception {
        try {
            Account newAccount = (Account) message.getObject();
            userManagement.register(newAccount);
        } catch (JMSException e) {
            System.err.println("de.fh_dortmund.inf.cw.surstwalat.usermanagement.UserManagementMessageBean.register(): JMSException");
        }
    }

    /**
     * Login user
     *
     * @param message
     * @throws Exception
     */
    public void login(ObjectMessage message) throws Exception {
        try {
            Account newAccount = (Account) message.getObject();
            userManagement.login(newAccount);
        } catch (JMSException e) {
            System.err.println("de.fh_dortmund.inf.cw.surstwalat.usermanagement.UserManagementMessageBean.login(): JMSException");
        }
    }

    /**
     * Update email address
     *
     * @param message
     * @throws Exception
     */
    public void updateEmailAddress(ObjectMessage message) throws Exception {
        try {
            Account newAccount = (Account) message.getObject();
            userManagement.updateEmailAddress(newAccount);
        } catch (JMSException e) {
            System.err.println("de.fh_dortmund.inf.cw.surstwalat.usermanagement.UserManagementMessageBean.updateEmailAddress(): JMSException");
        }
    }

    /**
     * Change password
     *
     * @param message
     * @throws Exception
     */
    public void changePassword(ObjectMessage message) throws Exception {
        try {
            Account newAccount = (Account) message.getObject();
            userManagement.changePassword(newAccount);
        } catch (JMSException e) {
            System.err.println("de.fh_dortmund.inf.cw.surstwalat.usermanagement.UserManagementMessageBean.changePassword(): JMSException");
        }
    }

    /**
     * Delete account
     *
     * @param message
     * @throws Exception
     */
    public void deleteAccount(ObjectMessage message) throws Exception {
        try {
            Account newAccount = (Account) message.getObject();
            userManagement.deleteAccount(newAccount);
        } catch (JMSException e) {
            System.err.println("de.fh_dortmund.inf.cw.surstwalat.usermanagement.UserManagementMessageBean.deleteAccount(): JMSException");
        }
    }
}
