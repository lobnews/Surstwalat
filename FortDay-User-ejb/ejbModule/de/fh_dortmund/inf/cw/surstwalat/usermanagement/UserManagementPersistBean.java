package de.fh_dortmund.inf.cw.surstwalat.usermanagement;

import de.fh_dortmund.inf.cw.surstwalat.usermanagement.util.HashManager;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.beans.interfaces.UserManagementLocal;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountAlreadyExistException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountNotFoundException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.LoginFailedException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Stephan Klimek
 */
@Stateless
public class UserManagementPersistBean implements UserManagementLocal {

    @PersistenceContext(unitName = "FortDayDB")
    private EntityManager entityManager;

    /**
     * Registed new account
     *
     * @param account input account
     * @throws AccountAlreadyExistException if the account already exists.
     * @throws GeneralServiceException if there is a general service exception
     */
    @Override
    public void register(Account account) throws AccountAlreadyExistException, GeneralServiceException {
        try {
            entityManager.persist(account);
            entityManager.flush();
        } catch (EntityExistsException e) {
            throw new AccountAlreadyExistException();
        } catch (PersistenceException e) {
            throw new AccountAlreadyExistException();
        } catch (Exception e) {
            throw new GeneralServiceException();
        }
    }

    /**
     * Login account
     *
     * @param account input account
     * @return logged in user account
     * @throws AccountNotFoundException if account not exist
     * @throws LoginFailedException if input datas not match the account
     * @throws GeneralServiceException if there is a general service exception
     */
    @Override
    public Account login(Account account) throws AccountNotFoundException, LoginFailedException, GeneralServiceException {
        Account dbAccount = getAccountByName(account.getName());
        if (dbAccount.getPassword().equals(account.getPassword())) {
            return dbAccount;
        } else {
            throw new LoginFailedException();
        }
    }

    /**
     * Update account password
     *
     * @param account
     * @throws GeneralServiceException if there is a general service exception
     */
    @Override
    public void changePassword(Account account) throws GeneralServiceException {
        try {
            entityManager.merge(account);
            entityManager.flush();
        } catch (Exception e) {
            throw new GeneralServiceException();
        }
    }

    /**
     * Update account email address
     *
     * @param account
     * @throws GeneralServiceException if there is a general service exception
     */
    @Override
    public void updateEmailAddress(Account account) throws GeneralServiceException {
        try {
            entityManager.merge(account);
            entityManager.flush();
        } catch (Exception e) {
            throw new GeneralServiceException();
        }
    }

    /**
     * Remove account
     *
     * @param account
     * @throws GeneralServiceException if there is a general service exception
     */
    @Override
    public void deleteAccount(Account account) throws GeneralServiceException {
        try {
            entityManager.remove(account);
        } catch (Exception e) {
            throw new GeneralServiceException();
        }
    }

    /**
     * Get account data by account id
     *
     * @param id account id
     * @return account
     * @throws AccountNotFoundException if there is no result
     * @throws GeneralServiceException if there is a general service exception
     */
    public Account getAccountById(int id) throws AccountNotFoundException, GeneralServiceException {
        try {
            TypedQuery<Account> accountQuery = entityManager.createNamedQuery("Account.getById", Account.class);
            accountQuery.setParameter("id", id);
            Account account = accountQuery.getSingleResult();
            return account;
        } catch (NoResultException e) {
            throw new AccountNotFoundException();
        } catch (Exception e) {
            throw new GeneralServiceException();
        }
    }

    /**
     * Get account data by account name
     *
     * @param name account name
     * @return account from db
     * @throws AccountNotFoundException if there is no result
     * @throws GeneralServiceException if there is a general service exception
     */
    public Account getAccountByName(String name) throws AccountNotFoundException, GeneralServiceException {
        try {
            TypedQuery<Account> accountQuery = entityManager.createNamedQuery("Account.getByName", Account.class);
            accountQuery.setParameter("name", name);
            Account account = accountQuery.getSingleResult();
            return account;
        } catch (NoResultException e) {
            throw new AccountNotFoundException();
        } catch (Exception e) {
            System.err.println(e.getClass());
            throw new GeneralServiceException();
        }
    }
}
