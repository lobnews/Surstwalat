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
public class UpdateZoneEvent extends JMSMessageEvent {
    
    private final int gameID;
    private final int zoneBegin;
    private final int zoneSize;
    private final int nextZoneBegin;
    private final int nextZoneSize;
    private final int damage;
    private final String displayMessage;
    
    public UpdateZoneEvent(Message message) throws JMSException {
        super(message);
        gameID = message.getIntProperty(PropertyType.GAME_ID);
        zoneBegin = message.getIntProperty(PropertyType.CURRENT_ZONE_BEGIN);
        zoneSize = message.getIntProperty(PropertyType.CURRENT_ZONE_SIZE);
        nextZoneBegin = message.getIntProperty(PropertyType.NEXT_ZONE_BEGIN);
        nextZoneSize = message.getIntProperty(PropertyType.NEXT_ZONE_SIZE);
        damage = message.getIntProperty(PropertyType.DAMAGE);
        displayMessage = message.getStringProperty(PropertyType.DISPLAY_MESSAGE);
    }

    public int getGameID() {
        return gameID;
    }

    public int getZoneBegin() {
        return zoneBegin;
    }

    public int getZoneSize() {
        return zoneSize;
    }

    public int getNextZoneBegin() {
        return nextZoneBegin;
    }

    public int getNextZoneSize() {
        return nextZoneSize;
    }

    public int getDamage() {
        return damage;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }
    
}
