/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.event.events;

import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 *
 * @author Lars
 */
public class SetTokenHealthEvent extends JMSMessageEvent {
    
    private final int gameID;
    private final int tokenID;
    private final int health;

    public SetTokenHealthEvent(Message message) throws JMSException {
        super(message);
        gameID = message.getIntProperty(PropertyType.GAME_ID);
        tokenID = message.getIntProperty(PropertyType.TOKEN_ID);
        health = message.getIntProperty(PropertyType.HEALTH);
    }

    public int getGameID() {
        return gameID;
    }

    public int getTokenID() {
        return tokenID;
    }

    public int getHealth() {
        return health;
    }
    
}
