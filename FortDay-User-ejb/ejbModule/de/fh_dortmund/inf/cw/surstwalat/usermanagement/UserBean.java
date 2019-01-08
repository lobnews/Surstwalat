package de.fh_dortmund.inf.cw.surstwalat.usermanagement;

import de.fh_dortmund.inf.cw.surstwalat.common.usermanagement.beans.interfaces.UserManagementLocal;
import de.fh_dortmund.inf.cw.surstwalat.common.usermanagement.beans.interfaces.UserManagementRemote;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Topic;

/**
 * @author Stephan Klimek
 */
@Stateful
public class UserBean implements UserManagementLocal, UserManagementRemote {

    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "java:global/jms/MessageTopic")
    private Topic UserMessageTopic;

    @EJB
    private UserManagementBean userManagement;

    @Override
    public void changePassword(String password, String newPassword) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(String password) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void disconnect() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getUserName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void login(String accountName, String password) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void logout() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void register(String accountName, String password) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
