/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.event.events;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Account;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 *
 * @author Lars
 */
public class UserEvent extends JMSMessageEvent{
    
    private final Account account;
    
    public UserEvent(Message message) throws JMSException {
        super(message);
        account = message.getBody(Account.class);
    }

    public Account getAccount() {
        return account;
    }
    
}
