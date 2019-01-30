/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.event.events;

import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 *
 * @author Lars
 */
public class ItemAddToUserEvent extends JMSMessageEvent{
    
    private final int gameID;
    private final int playerID;
    private final Item item;
    
    public ItemAddToUserEvent(Message message) throws JMSException {
        super(message);
        gameID = message.getIntProperty(PropertyType.GAME_ID);
        playerID = message.getIntProperty(PropertyType.PLAYER_ID);
        item = message.getBody(Item.class);
    }

    public int getGameID() {
        return gameID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public Item getItem() {
        return item;
    }
    
}
