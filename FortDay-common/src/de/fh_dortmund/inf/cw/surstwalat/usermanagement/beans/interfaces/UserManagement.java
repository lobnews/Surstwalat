/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.usermanagement.beans.interfaces;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.GeneralServiceException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountAlreadyExistException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.AccountNotFoundException;
import de.fh_dortmund.inf.cw.surstwalat.usermanagement.exceptions.LoginFailedException;

/**
 * UserManagement interface
 *
 * @author Stephan Klimek
 */
public interface UserManagement {

    /**
     * Registration
     *
     * @param account
     * @throws AccountAlreadyExistException if there is no result
     * @throws GeneralServiceException if there is a general service exception
     */
    public void register(Account account) throws AccountAlreadyExistException, GeneralServiceException;

    /**
     * Login account
     *
     * @param account account to login
     * @return logged in user account
     * @throws AccountNotFoundException if account not exist
     * @throws LoginFailedException if input datas not match the account
     * @throws GeneralServiceException if there is a general service exception
     */
    public Account login(Account account) throws AccountNotFoundException, LoginFailedException, GeneralServiceException;

    /**
     * Update account email address
     *
     * @param account account to update
     * @throws GeneralServiceException if there is a general service exception
     */
    public void changePassword(Account account) throws GeneralServiceException;

    /**
     * Update account password
     *
     * @param account account to update
     * @throws GeneralServiceException if there is a general service exception
     */
    public void updateEmailAddress(Account account) throws GeneralServiceException;

    /**
     * Remove account
     *
     * @param account account to delete
     * @throws GeneralServiceException if there is a general service exception
     */
    public void deleteAccount(Account account) throws GeneralServiceException;

    /**
     * Get account data by account id
     *
     * @param accountName account name
     * @return account
     * @throws AccountNotFoundException if there is no result
     * @throws GeneralServiceException if there is a general service exception
     */
    public Account getAccountByName(String accountName) throws AccountNotFoundException, GeneralServiceException;

    /**
     * Get account data by account id
     *
     * @param accountId account id
     * @return account
     * @throws AccountNotFoundException if there is no result
     * @throws GeneralServiceException if there is a general service exception
     */
    public Account getAccountById(int accountId) throws AccountNotFoundException, GeneralServiceException;
}
