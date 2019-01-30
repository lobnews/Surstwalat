package de.fh_dortmund.inf.cw.surstwalat.locationmanagement.beans.events.eventhandler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.locationmanagement.beans.interfaces.LocationManagementLocal;

@MessageDriven(activationConfig =
{
  @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
  @ActivationConfigProperty(
      propertyName = "messageSelector",
      propertyValue = PropertyType.MESSAGE_TYPE + " = " + MessageType.MOVE_TOKEN) //TODO: Anderes Event
}, mappedName = "java:global/jms/FortDayEventTopic")

public class MoveTokenEventHandlerBean implements MessageListener
{

    @EJB
    LocationManagementLocal locationManagement;

    @Override
    public void onMessage(Message message)
    {
    	System.out.println("[LOCATIONMANAGEMENT] MOVE_TOKEN received");
        try
        {
            ObjectMessage om = (ObjectMessage)message;
            locationManagement
                .moveToken(
                    message.getIntProperty(PropertyType.GAME_ID),
                    message.getIntProperty(PropertyType.TOKEN_ID),
                    (int)om.getObject());
        }
        catch (JMSException e)
        {
            e.printStackTrace();
        }

    }

}
