package de.fh_dortmund.cw.surstwalat.ejb.itemmanagement;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import de.fh_dortmund.inf.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.common.PropertyType;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Dice;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;

/**
 * @author Marvin Wölk
 *
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") }, mappedName = "java:global/jms/FortDayEventTopic")
public class ItemmanagementMessageBean implements MessageListener {

	private static final int dichte = 50;

	@EJB
	private ItemmanagementBean itemBean;

	public void onMessage(Message message) {
		try {
			int gameId = message.getIntProperty(PropertyType.GAME_ID);
			int msgType = message.getIntProperty(PropertyType.MESSAGE_TYPE);

			//System.out.println(itemBean.name + " Ankommende Nachricht: GameID: " + gameId + ", MESSAGE_TYPE: " + msgType + "; MSG:" + message);

			switch (msgType) {
				case MessageType.TRIGGER_AIRDROP:
					itemBean.spawnAirDrop(gameId);
					break;
				case MessageType.TRIGGER_STARTING_ITEMS:
					itemBean.spawnItems(gameId, dichte);
					break;
				case MessageType.COLLISION_WITH_ITEM:
					int playerID = message.getIntProperty(PropertyType.PLAYER_ID);
					int itemID = message.getIntProperty(PropertyType.ITEM_ID);
					itemBean.addItemToUser(playerID, itemID);
					break;
				case MessageType.SEND_PLAYER_INVENTAR:
					playerID = message.getIntProperty(PropertyType.PLAYER_ID);
					itemBean.sendUserInventar(gameId, playerID);
					break;
				case MessageType.PLAYER_ACTION :
					playerID = message.getIntProperty(PropertyType.PLAYER_ID);
					ObjectMessage objectMessage = (ObjectMessage) message;
					Item item = objectMessage.getBody(Item.class);
					itemBean.removeItem(playerID, item.getId());
					break;
				case MessageType.ELIMINATE_PLAYER:
					//ITEMS werden nach dem Tod nicht gespawnt
					break;
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
