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
public class ItemSpawnEvent extends JMSMessageEvent{
    
    private final int gameID;
    private final int itemID;
    private final int itemPos;
    
    public ItemSpawnEvent(Message message) throws JMSException {
        super(message);
        gameID = message.getIntProperty(PropertyType.GAME_ID);
        itemID = message.getIntProperty(PropertyType.ITEM_ID);
        itemPos = message.getIntProperty(PropertyType.ITEM_POS);
    }

    public int getGameID() {
        return gameID;
    }

    public int getItemID() {
        return itemID;
    }

    public int getItemPos() {
        return itemPos;
    }
    
}
