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
public class EliminatePlayerEvent extends JMSMessageEvent {
    
    private final int gameID;
    private final int playerID;
    private final String displayMessage;
    private final int playerNR;
    
    public EliminatePlayerEvent(Message message) throws JMSException {
        super(message);
        gameID = message.getIntProperty(PropertyType.GAME_ID);
        playerID = message.getIntProperty(PropertyType.PLAYER_ID);
        displayMessage = message.getStringProperty(PropertyType.DISPLAY_MESSAGE);
        playerNR = message.getBody(Integer.class);
    }

    public int getGameID() {
        return gameID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public int getPlayerNR() {
        return playerNR;
    }
    
}
