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
public class PlayerRollEvent extends JMSMessageEvent{
    
    private final int gameID;
    private final String displayMessage;
    private final int playerNR;
    private final int number;
    
    public PlayerRollEvent(Message message) throws JMSException {
        super(message);
        gameID = message.getIntProperty(PropertyType.GAME_ID);
        displayMessage = message.getStringProperty(PropertyType.DISPLAY_MESSAGE);
        playerNR = message.getIntProperty(PropertyType.PLAYER_NO);
        number = message.getBody(Integer.class);
    }

    public int getGameID() {
        return gameID;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public int getPlayerNR() {
        return playerNR;
    }

    public int getNumber() {
        return number;
    }
    
}
