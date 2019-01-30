/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.event.events;

import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import java.util.LinkedList;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 *
 * @author Lars
 */
public class TokenCreatedEvent extends JMSMessageEvent{
    
    private final int playerID;
    private final int gameID;
    private final int playerNR;
    private final LinkedList<Integer> tokens;
    
    public TokenCreatedEvent(Message message) throws JMSException {
        super(message);
        playerID = message.getIntProperty(PropertyType.PLAYER_ID);
        playerNR = message.getIntProperty(PropertyType.PLAYER_NO);
        gameID = message.getIntProperty(PropertyType.GAME_ID);
        tokens = message.getBody(LinkedList.class);
    }

    public int getPlayerNR() {
        return playerNR;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getGameID() {
        return gameID;
    }

    public LinkedList<Integer> getTokens() {
        return tokens;
    }
    
}
