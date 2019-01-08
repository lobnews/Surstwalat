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
            @ActivationConfigProperty(
                    propertyName = "destinationType", propertyValue = "javax.jms.Topic")
        },
        mappedName = "java:global/jms/UserMessageTopic")
public class UserManagementBean implements MessageListener, UserManagementLocal, UserManagementRemote {

    private final ArrayList<Account> userList = new ArrayList<>();

    @Override
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
    }

//	public void delete(String username) throws Exception {
//		UserInfo u = getUserInfo(username);
//		u.setOnlineUser(false);
//		manager.remove(u);
//	}
//
//	public int getNumberOfOnlineUsers() {
//		TypedQuery<UserInfo> q = manager.createNamedQuery("getOnlineUsers", UserInfo.class);
//		List<UserInfo> result = q.getResultList();
//		return result.size();
//	}
//
//	public int getNumberOfRegisteredUsers() {
//		TypedQuery<Long> countOfUsersQuery = manager.createNamedQuery("countOfUsers", Long.class);
//		int countOfUsers = countOfUsersQuery.getSingleResult().intValue();
//		return countOfUsers;
//	}
//
//	public List<String> getOnlineUsers() {
//		TypedQuery<UserInfo> q = manager.createNamedQuery("getOnlineUsers", UserInfo.class);
//		List<UserInfo> result = q.getResultList();
//		List<String> online = new LinkedList<>();
//		for(UserInfo u:result) {
//			online.add(u.getUsername());
//		}
//		return online;
//	}
//
//	public void login(String userName) {
//		UserInfo u = getUserInfo(userName);
//		u.setOnlineUser(true);
//	}
//
//	public void logout(String username) throws Exception {
//		UserInfo u = getUserInfo(username);
//		u.setOnlineUser(false);
//	}
//
//	public UserInfo getUserInfo(String username) {
//		TypedQuery<UserInfo> userInfoQuery = manager.createNamedQuery("userInfo", UserInfo.class);
//		userInfoQuery.setParameter("username", username);
//		
//		try {
//			UserInfo user = userInfoQuery.getSingleResult();
//			return user;
//		} catch (NoResultException e) {
//			return null;
//		}
//	}
//
//	public boolean existUser(String username) {
//		TypedQuery<UserInfo> userInfoQuery = manager.createNamedQuery("userInfo", UserInfo.class);
//		userInfoQuery.setParameter("username", username);
//		
//		try {
//			userInfoQuery.getSingleResult();
//			return true;
//		} catch (NoResultException e) {
//			return false;
//		}
//	}
//
//	public void updateUserInfo(UserInfo u) {
//		manager.merge(u);
//	}
//
//	public boolean isOnline(String username) {
//		return getUserInfo(username).isOnlineUser();
//	}
//
//
//	public boolean checkPassword(String username, String password) {
//		return checkPassword(getUserInfo(username), password);
//	}
//
//	public boolean checkPassword(Account account, String password) {
//		return account.checkPassword(generateHash(password));
//	}
//
//
    @Override
    public void changePassword(String password, String newPassword) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String password) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void disconnect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUserName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void login(String accountName, String password) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void logout() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void register(String accountName, String password) throws Exception {
        Account account = new Account();
        account.setUsername(accountName);
        account.setPassword(generateHash(password));
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
