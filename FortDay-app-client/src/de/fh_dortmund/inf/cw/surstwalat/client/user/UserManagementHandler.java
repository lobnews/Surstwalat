/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.user;

import de.fh_dortmund.inf.cw.surstwalat.common.interfaces.UserManagementRemote;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Handler for UserManagement
 *
 * @author Stephan Klimek
 */
public class UserManagementHandler implements MessageListener, ExceptionListener {

    private static UserManagementHandler instance;

    private Context ctx;
    private UserManagementRemote userManagementRemote;
    private Topic userMessageTopic;
    private JMSContext jmsContext;

    /**
     * Handler for UserManagement
     */
    public UserManagementHandler() {
        String userManagementLookUp
                = "java:global/FortDay-ear/FortDay-User-ejb/UserBean!de.fh_dortmund.inf.cw.surstwalat.common.interfaces.UserManagementRemote";

        try {
            ctx = new InitialContext();

            // ChatRemote
            userManagementRemote = (UserManagementRemote) ctx.lookup(userManagementLookUp);

            // initJMSConnection();
        } catch (NamingException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Initialize JMS connection
     */
    private void initJMSConnection() {
        try {
            // ConnectionFactory
            ConnectionFactory connectionFactory
                    = (ConnectionFactory) ctx.lookup("java:comp/DefaultJMSConnectionFactory");
            jmsContext = connectionFactory.createContext();
            jmsContext.setExceptionListener(this);

            // userMessageTopic
            userMessageTopic = (Topic) ctx.lookup("java:global/jms/UserMessageTopic");
            jmsContext.createConsumer(userMessageTopic).setMessageListener(this);

        } catch (NamingException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Get instance UserManagementHandler
     *
     * @return
     */
    public static UserManagementHandler getInstance() {
        if (instance == null) {
            instance = new UserManagementHandler();
        }
        return instance;
    }

    /**
     * Get new instance UserManagementHandler
     *
     * @return
     */
    public static UserManagementHandler getNewInstance() {
        return new UserManagementHandler();
    }

    /**
     * On Message
     *
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * On Exception
     *
     * @param exception
     */
    @Override
    public void onException(JMSException exception) {
        System.err.println(exception.getMessage());
    }

    /**
     * Register
     *
     * @param accountName
     * @param email
     * @param password
     * @throws Exception
     */
    public void register(String accountName, String email, String password) throws Exception {
        userManagementRemote.register(accountName, email, password);
    }

    /**
     * Delete account
     */
    public void deleteAccount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Login user
     */
    public void login() {
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
     * Update email address
     *
     * @param email
     */
    public void updateEmailAddress(String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
