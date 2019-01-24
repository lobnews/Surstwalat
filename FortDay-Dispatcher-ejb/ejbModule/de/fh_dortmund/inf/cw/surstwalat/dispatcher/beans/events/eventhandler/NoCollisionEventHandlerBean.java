package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.events.eventhandler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.interfaces.DispatcherLocal;

/**
 * Message-Driven Bean, which listens on NO_COLLISION messages on the FortDayEventTopic
 * @author Johannes Heiderich
 */
@MessageDriven(
		activationConfig = { 
			@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
			@ActivationConfigProperty(propertyName = "messageSelector", 
									  propertyValue = PropertyType.MESSAGE_TYPE + " = " + MessageType.NO_COLLISION)
		}, 
		mappedName = "java:global/jms/FortDayEventTopic")
public class NoCollisionEventHandlerBean implements MessageListener {

	@EJB
	private DispatcherLocal dispatcher;
	
	/**
	 * @see MessageListener#onMessage(Message)
	 * Extracts the GAME_ID MessageProperty of the received message and triggers a dispatch function at the DispatcherBean
	 * @param message die empfangene Nachricht
	 */
    public void onMessage(Message message) {
    	  try {
          	System.out.println("[DISPATCHER] NO_COLLISION received");
          	dispatcher.dispatch(message.getIntProperty(PropertyType.GAME_ID));
          	
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
    }

}
