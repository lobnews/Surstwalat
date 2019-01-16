package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.beans.events.eventhandler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.beans.interfaces.LocationManagementLocal;


@MessageDriven(
    activationConfig = { 
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "messageSelector", 
                                  propertyValue = PropertyType.MESSAGE_TYPE + " = " + MessageType.START_ROUND)
    }, 
    mappedName = "java:global/jms/FortDayEventTopic")

public class GameStartedEventHandlerBean implements MessageListener
{

    @EJB
    LocationManagementLocal locationManagement;
    
    @Override
    public void onMessage(Message message)
    {
        try
        {
            locationManagement.createPlayground(message.getIntProperty(PropertyType.GAME_ID),message.getIntProperty(PropertyType.GAME_FIELDSIZE));
        }
        catch (JMSException e)
        {
            e.printStackTrace();
        }
        
    }

}
