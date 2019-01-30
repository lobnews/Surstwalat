/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.event.events;

import javax.jms.Message;

/**
 *
 * @author Lars
 */
public class ItemAddToUserEvent extends JMSMessageEvent{
    
    
    
    public ItemAddToUserEvent(Message message) {
        super(message);
    }
    
}
