package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.events.eventhandler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.ActionType;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans.interfaces.DispatcherLocal;

/**
 * Message-Driven Bean implementation class for: TokenEliminatedEventHandler
 * @author Johannes Heiderich
 */
@MessageDriven(
		activationConfig = { 
			@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
			@ActivationConfigProperty(propertyName = "messageSelector", 
									  propertyValue = PropertyType.MESSAGE_TYPE + " = " + MessageType.PLAYER_DEATH)
		}, 
		mappedName = "java:global/jms/FortDayEventTopic")
public class PlayerDeathEventHandler implements MessageListener {

	@EJB
	private DispatcherLocal dispatcher;

    public void onMessage(Message message) {
    	try {

			ActionType type = ActionType.values()[message.getIntProperty(PropertyType.ACTION_TYPE)];
			if (type == ActionType.ROLL) {
				System.out.println("[DISPATCHER] PLAYER_DEATH received");
				dispatcher.onPlayerDeath(message.getIntProperty(PropertyType.PLAYER_ID));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
