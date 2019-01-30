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
public class AssignPlayerEvent extends JMSMessageEvent {
    
    private final int gameID;
    private final int userID;
    private final int playerID;
    private final int playerNR;
    
    public AssignPlayerEvent(Message message) throws JMSException {
        super(message);
        gameID = message.getIntProperty(PropertyType.GAME_ID);
        userID = message.getIntProperty(PropertyType.USER_ID);
        playerID = message.getIntProperty(PropertyType.PLAYER_ID);
        playerNR = message.getBody(Integer.class);
    }

    public int getGameID() {
        return gameID;
    }

    public int getUserID() {
        return userID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getPlayerNR() {
        return playerNR;
    }
    
}
