package de.fh_dortmund.cw.surstwalat.ejb.itemmanagement;

import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import de.fh_dortmund.cw.surstwalat.common.Item;
import de.fh_dortmund.cw.surstwalat.common.MessageType;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") }, mappedName = "java:global/jms/ItemmanagementMessageTopic")
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
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
}