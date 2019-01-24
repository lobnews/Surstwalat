package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.events.eventhandler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.interfaces.DispatcherLocal;

/**
 * Message-Driven Bean, which listens on GAME_STARTED messages on the FortDayEventTopic
 * @author Johannes Heiderich
 */
@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
				@ActivationConfigProperty(propertyName = "messageSelector", 
				  propertyValue = PropertyType.MESSAGE_TYPE + " = " + MessageType.GAME_STARTED)
		}, 
		mappedName = "java:global/jms/FortDayEventTopic")
public class GameStartedEventHandlerBean implements MessageListener {

	
	@EJB
	private DispatcherLocal dispatcher;
	/**
     * @see MessageListener#onMessage(Message)
     * Extracts the GAME_ID MessageProperty of the received message and triggers the creation of new players at the DispatcherBean
     * @param message die empfangene Nachricht
     */
    public void onMessage(Message message) {
        try {
        	System.out.println("[DISPATCHER] GAME_STARTED received");
			dispatcher.createPlayers(message.getIntProperty(PropertyType.GAME_ID));
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
