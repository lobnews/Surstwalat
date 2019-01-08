package de.fh_dortmund.cw.surstwalat.ejb.itemmanagement;

import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import de.fh_dortmund.cw.surstwalat.common.MessageType;
import de.fh_dortmund.inf.cw.surstwalat.itemmanagement.Item;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") }, mappedName = "java:global/jms/FortDayEventTopic")
public class ItemmanagementMessageBean implements MessageListener {

	private static final int dichte = 50;
	
	@EJB
	private ItemmanagementBean itemBean;

	public void onMessage(Message message) {
		try {
			String msgType = message.getStringProperty("MessageType");
			System.out.println("ItemmanagementMessageBean nachricht: " + msgType);
			if (MessageType.SpawnNewItems.name().equals(msgType)) {
				int mapSize = message.getIntProperty("MapSize");
				itemBean.spawnItems(mapSize, dichte);
			} else if (MessageType.SpawnAirDrop.name().equals(msgType)) {
				itemBean.spawnAirDrop();
			} else if (MessageType.SpawnPlayerItems.name().equals(msgType)) {
				List<Item> items = (List<Item>) message.getObjectProperty("ItemList");
				itemBean.spawnAllItems(items);
			} else if (MessageType.AddItemToPlayer.name().equals(msgType)) {
				Item item = (Item) message.getObjectProperty("Item");
				String UserID = message.getStringProperty("UserID");
				itemBean.addItemToUser(UserID, item);
			}else if (MessageType.GetPlayerInventar.name().equals(msgType)) {
				String UserID = message.getStringProperty("UserID");
				Destination dest = (Destination) message.getObjectProperty("Dest");
				itemBean.sendUserInventar(UserID, dest);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
}
