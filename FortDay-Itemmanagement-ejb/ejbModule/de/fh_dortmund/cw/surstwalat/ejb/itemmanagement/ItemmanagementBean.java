package de.fh_dortmund.cw.surstwalat.ejb.itemmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.fh_dortmund.cw.surstwalat.common.HealthItem;
import de.fh_dortmund.cw.surstwalat.common.HealthItem.Level;
import de.fh_dortmund.cw.surstwalat.common.Item;
import de.fh_dortmund.cw.surstwalat.common.MessageType;
import de.fh_dortmund.cw.surstwalat.common.User;
import de.fh_dortmund.cw.surstwalat.common.Wuerfel;

@Startup
@Singleton
public class ItemmanagementBean {
	
	@Inject
	private JMSContext jmsContext;
	
	@PersistenceContext
	private EntityManager entitymanager;
	
	@Resource(lookup="java:global/jms/ItemmanagementMessageTopic")
	private Topic topic;
	
	@Resource(lookup="java:global/jms/ItemmanagementMessageTopic")
	private Topic locationManagementDestination;
	
	private List<Item> defaultItems;
	
	private List<Item> airDropItems;
	
	@PostConstruct
	public void init() {
		fillDefaultItems();
		
		fillAirDropItems();
	}

	private void fillAirDropItems() {
		airDropItems = new ArrayList<>();
		defaultItems.add(new HealthItem(Level.Stufe_2));
		defaultItems.add(new HealthItem(Level.Stufe_3));
		defaultItems.add(new HealthItem(Level.Stufe_3));
		defaultItems.add(new Wuerfel(new int[]{1}, "1"));
		defaultItems.add(new Wuerfel(new int[]{2}, "2"));
		defaultItems.add(new Wuerfel(new int[]{3}, "3"));
		defaultItems.add(new Wuerfel(new int[]{4}, "4"));
		defaultItems.add(new Wuerfel(new int[]{5}, "5"));
		defaultItems.add(new Wuerfel(new int[]{6}, "6"));
		defaultItems.add(new Wuerfel(new int[]{7}, "7"));
		defaultItems.add(new Wuerfel(new int[]{-1}, "-1"));
		defaultItems.add(new Wuerfel(new int[]{-2}, "-2"));
		defaultItems.add(new Wuerfel(new int[]{-3}, "-3"));
		defaultItems.add(new Wuerfel(new int[]{-4}, "-4"));
		defaultItems.add(new Wuerfel(new int[]{-5}, "-5"));
		defaultItems.add(new Wuerfel(new int[]{-6}, "-6"));
		defaultItems.add(new Wuerfel(new int[]{-7}, "-7"));
	}

	private void fillDefaultItems() {
		defaultItems = new ArrayList<>();
		//healthItems
		defaultItems.add(new HealthItem(Level.Stufe_1));
		defaultItems.add(new HealthItem(Level.Stufe_1));
		defaultItems.add(new HealthItem(Level.Stufe_2));
		
		//würfel
		defaultItems.add(new Wuerfel(new int[]{1,2,3,4,5}, "1-5"));
		defaultItems.add(new Wuerfel(new int[]{1,2,3,4}, "1-4"));
		defaultItems.add(new Wuerfel(new int[]{1,2,3}, "1-3"));
		defaultItems.add(new Wuerfel(new int[]{1,2}, "1-2"));
		defaultItems.add(new Wuerfel(new int[]{2,3,4,5,6}, "2-6"));
		defaultItems.add(new Wuerfel(new int[]{3,4,5,6}, "3-6"));
		defaultItems.add(new Wuerfel(new int[]{2,3,4,5}, "2-5"));
		defaultItems.add(new Wuerfel(new int[]{2,3,4}, "2-4"));
		defaultItems.add(new Wuerfel(new int[]{1}, "1"));
		defaultItems.add(new Wuerfel(new int[]{2}, "2"));
		defaultItems.add(new Wuerfel(new int[]{3}, "3"));
		defaultItems.add(new Wuerfel(new int[]{-1}, "-1"));
		defaultItems.add(new Wuerfel(new int[]{-2}, "-2"));
		defaultItems.add(new Wuerfel(new int[]{-3}, "-3"));
		defaultItems.add(new Wuerfel(new int[]{-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6}, "-6-6"));
		
		defaultItems.add(new Wuerfel(new int[]{6,1,1,1,1,1}, "1 zu 6"));
		
		defaultItems.add(new Wuerfel(new int[]{1,3,5}, "Ungrade"));
		defaultItems.add(new Wuerfel(new int[]{2,4,6}, "Grade"));
	}
	
	public void addItemToUser(String UserID, Item item) {
		User user = entitymanager.find(User.class, UserID);
		if(user != null) {
			user.addInventar(item);
			entitymanager.merge(user);
		}
	}
	
	public void sendUserInventar(String UserID, Destination dest) {
		User user = entitymanager.find(User.class, UserID);
		Message msg = jmsContext.createMessage();
		
		try {
			msg.setStringProperty("MessageType", MessageType.PlayerInventar.name());
			msg.setStringProperty("UserID", UserID);
			msg.setObjectProperty("Inventar", user.getInventar());
			jmsContext.createProducer().send(dest, msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void spawnAirDrop() {
		Random rnd = new Random();
		Item spawn = airDropItems.get(rnd.nextInt(defaultItems.size()));
		spawnItem(spawn, -1);
	}
	
	public void spawnAllItems(List<Item> items) {
		items.forEach(e -> spawnItem(e, -1));
	}
	
	public void spawnItems(int feldSize, int dichte) {
		Random rnd = new Random();
		for(int i = 0; i < feldSize; i++) {
			if(rnd.nextInt(100) < dichte) {
				Item spawn = defaultItems.get(rnd.nextInt(defaultItems.size()));
				spawnItem(spawn, i);
			}
		}
	}
	
	private void spawnItem(Item item, int pos) {
		Message msg = jmsContext.createMessage();
		
		try {
			msg.setStringProperty("MessageType", MessageType.SpawnItem.name());
			msg.setObjectProperty("Item", item);
			msg.setIntProperty("Pos", pos);
			jmsContext.createProducer().send(locationManagementDestination, msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}
}
