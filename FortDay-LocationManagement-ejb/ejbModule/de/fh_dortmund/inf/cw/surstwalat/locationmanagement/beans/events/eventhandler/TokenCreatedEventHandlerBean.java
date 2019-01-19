package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.beans.events.eventhandler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;

@MessageDriven(
    activationConfig = { 
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "messageSelector", 
                                  propertyValue = PropertyType.MESSAGE_TYPE + " = " + MessageType.ADD_ITEM_TO_PLAYER) //TODO: richtiger Type
    }, 
    mappedName = "java:global/jms/FortDayEventTopic")
public class TokenCreatedEventHandlerBean implements MessageListener
{

    @Override
    public void onMessage(Message arg0)
    {
        // TODO Auto-generated method stub
        
    }
    
}
