package de.fh_dortmund.cw.surstwalat.ejb.itemmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.fh_dortmund.inf.cw.surstwalat.common.model.HealthItem;
import de.fh_dortmund.inf.cw.surstwalat.common.model.HealthItem.Level;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Player;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Wuerfel;

@Startup
@Singleton
public class ItemmanagementBean {
	
	@Inject
	private JMSContext jmsContext;
	
//	@PersistenceContext
//	private EntityManager entitymanager;
	
	@Resource(lookup="java:global/jms/FortDayEventTopic")
	private Topic topic;
	
	private List<Item> defaultItems;
	
	private List<Item> airDropItems;
	
	@EJB
	private OutgoingEventHelperBean sender;
	
	@PostConstruct
	public void init() {
		System.out.println("Itemmanagement wurde gestartet");
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
		
		//wuerfel
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
//		Player player = entitymanager.find(Player.class, UserID);
//		if(player != null) {
//			player.addItem(item);
//			entitymanager.merge(player);
//		}
	}
	
	public void sendUserInventar(int gameId, String player_No, Destination dest) {
//		Player player = entitymanager.find(Player.class, player_No);
//		sender.sendInventar(gameId, player);
	}
	
	public void spawnAirDrop(int gameId) {
		Random rnd = new Random();
		Item spawn = airDropItems.get(rnd.nextInt(defaultItems.size()));
		spawnItem(gameId, spawn);
	}
	
	public void spawnAllItems(int gameId, List<Item> items) {
		items.forEach(e -> spawnItem(gameId, e));
	}
	
	public void spawnItems(int gameId, int feldSize, int dichte) {
		Random rnd = new Random();
		for(int i = 0; i < feldSize; i++) {
			if(rnd.nextInt(100) < dichte) {
				Item spawn = defaultItems.get(rnd.nextInt(defaultItems.size()));
				spawnItem(gameId, spawn);
			}
		}
	}
	
	private void spawnItem(int gameId, Item item) {
		sender.sendAddItemToPlayground(gameId, item);
	}
}
