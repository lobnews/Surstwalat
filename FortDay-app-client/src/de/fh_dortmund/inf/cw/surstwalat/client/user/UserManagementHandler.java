/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.user;

import de.fh_dortmund.inf.cw.surstwalat.usermanagement.beans.interfaces.UserManagementRemote;
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
 * @author Stephan Klimek
 */
public class UserManagementHandler implements MessageListener, ExceptionListener {

    private static UserManagementHandler instance;

    private Context ctx;
    private UserManagementRemote userManagementRemote;
    private Topic userMessageTopic;
    private JMSContext jmsContext;

    public UserManagementHandler() {
        String userManagementLookUp
                = "java:global/FortDay-ear/FortDay-User-ejb/UserBean!de.fh_dortmund.inf.cw.surstwalat.common.usermanagement.beans.interfaces.UserManagementRemote";

        try {
            ctx = new InitialContext();

            // ChatRemote
            userManagementRemote = (UserManagementRemote) ctx.lookup(userManagementLookUp);

            // initJMSConnection();
        } catch (NamingException ex) {
            System.err.println(ex.getMessage());
        }
    }

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

    public void register(String accountName, String email, String password) throws Exception {
        userManagementRemote.register(accountName, email, password);
    }

    public static UserManagementHandler getInstance() {
        if (instance == null) {
            instance = new UserManagementHandler();
        }
        return instance;
    }

    public static UserManagementHandler getNewInstance() {
        return new UserManagementHandler();
    }

    @Override
    public void onMessage(Message message) {
        System.out.println("Call onMessage");

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onException(JMSException exception) {
        System.err.println(exception.getMessage());
    }
}
