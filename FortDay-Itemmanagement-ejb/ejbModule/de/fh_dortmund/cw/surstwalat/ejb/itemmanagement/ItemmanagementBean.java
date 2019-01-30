package de.fh_dortmund.cw.surstwalat.ejb.itemmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Dice;
import de.fh_dortmund.inf.cw.surstwalat.common.model.HealthItem;
import de.fh_dortmund.inf.cw.surstwalat.common.model.HealthItem.Level;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Player;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Playground;

/**
 * @author Marvin Wölk
 *
 */
@Singleton
public class ItemmanagementBean {

	public static final String name = "[ITEMMANAG]";

	@Inject
	private JMSContext jmsContext;

	@PersistenceContext
	private EntityManager entitymanager;

	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic topic;

	private List<Item> defaultItems;

	private List<Item> airDropItems;

	@EJB
	private OutgoingEventHelperBean sender;

	@PostConstruct
	public void init() {
		System.out.println("[ITEMMANAGEMENT] Singleton bean gestartet");
		fillDefaultItems();

		fillAirDropItems();
	}

	private void fillAirDropItems() {
		airDropItems = new ArrayList<>();
		airDropItems.add(new HealthItem(Level.Stufe_2));
		airDropItems.add(new HealthItem(Level.Stufe_3));
		airDropItems.add(new HealthItem(Level.Stufe_3));
		airDropItems.add(new Dice(new int[] { 1 }, "1"));
		airDropItems.add(new Dice(new int[] { 2 }, "2"));
		airDropItems.add(new Dice(new int[] { 3 }, "3"));
		airDropItems.add(new Dice(new int[] { 4 }, "4"));
		airDropItems.add(new Dice(new int[] { 5 }, "5"));
		airDropItems.add(new Dice(new int[] { 6 }, "6"));
		airDropItems.add(new Dice(new int[] { 7 }, "7"));
		airDropItems.add(new Dice(new int[] { -1 }, "-1"));
		airDropItems.add(new Dice(new int[] { -2 }, "-2"));
		airDropItems.add(new Dice(new int[] { -3 }, "-3"));
		airDropItems.add(new Dice(new int[] { -4 }, "-4"));
		airDropItems.add(new Dice(new int[] { -5 }, "-5"));
		airDropItems.add(new Dice(new int[] { -6 }, "-6"));
		airDropItems.add(new Dice(new int[] { -7 }, "-7"));
	}

	private void fillDefaultItems() {
		defaultItems = new ArrayList<>();
		// healthItems
		defaultItems.add(new HealthItem(Level.Stufe_1));
		defaultItems.add(new HealthItem(Level.Stufe_1));
		defaultItems.add(new HealthItem(Level.Stufe_2));

		// wuerfel
		defaultItems.add(new Dice(new int[] { 1, 2, 3, 4, 5 }, "1-5"));
		defaultItems.add(new Dice(new int[] { 1, 2, 3, 4 }, "1-4"));
		defaultItems.add(new Dice(new int[] { 1, 2, 3 }, "1-3"));
		defaultItems.add(new Dice(new int[] { 1, 2 }, "1-2"));
		defaultItems.add(new Dice(new int[] { 2, 3, 4, 5, 6 }, "2-6"));
		defaultItems.add(new Dice(new int[] { 3, 4, 5, 6 }, "3-6"));
		defaultItems.add(new Dice(new int[] { 2, 3, 4, 5 }, "2-5"));
		defaultItems.add(new Dice(new int[] { 2, 3, 4 }, "2-4"));
		defaultItems.add(new Dice(new int[] { 1 }, "1"));
		defaultItems.add(new Dice(new int[] { 2 }, "2"));
		defaultItems.add(new Dice(new int[] { 3 }, "3"));
		defaultItems.add(new Dice(new int[] { -1 }, "-1"));
		defaultItems.add(new Dice(new int[] { -2 }, "-2"));
		defaultItems.add(new Dice(new int[] { -3 }, "-3"));
		defaultItems.add(new Dice(new int[] { -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6 }, "-6-6"));

		defaultItems.add(new Dice(new int[] { 6, 1, 1, 1, 1, 1 }, "1 zu 6"));

		defaultItems.add(new Dice(new int[] { 1, 3, 5 }, "Ungrade"));
		defaultItems.add(new Dice(new int[] { 2, 4, 6 }, "Grade"));
	}

	public void addItemToUser(int playerID, int itemID) {
		Player player = entitymanager.find(Player.class, playerID);
		Item item = entitymanager.find(Item.class, itemID);
		if (player != null && item != null) {
			player.addItem(item);
			entitymanager.merge(player);
			sender.sendAddItemToPlayer(player.getGame().getId(), player.getId(), item);
			System.out.println(name + " Item(" + itemID + ") wurd Player(" + player.getId() + ") hinzugefuegt");
		} else {
			System.out.println(name + " Player(" + playerID + ") oder Item(" + itemID + ") ungueltig");
		}
	}

	public void sendUserInventar(int gameId, int playerId) {
		Player player = entitymanager.find(Player.class, playerId);
		sender.sendInventar(gameId, player);
	}

	public void removeItem(int playerId, int itemId) {
		Player player = entitymanager.find(Player.class, playerId);
		for (Item item : player.getItems()) {
			if (item.getId() == itemId) {
				player.getItems().remove(item);
				entitymanager.merge(player);
				return;
			}
		}
	}

	public void spawnAirDrop(int gameId) {
		Random rnd = new Random();
		Item spawn = airDropItems.get(rnd.nextInt(defaultItems.size()));
		spawnItem(gameId, spawn, -1);
	}

	public void spawnAllItems(int gameId, List<Item> items) {
		items.forEach(e -> spawnItem(gameId, e, -1));
	}

	public void spawnItems(int gameId, int dichte) {
		TypedQuery<Playground> q = entitymanager.createNamedQuery("Playground.getByGameId", Playground.class);
		q.setParameter("gameId", gameId);
		try {
			int feldSize = q.getSingleResult().getFields().size();

			Random rnd = new Random();
			for (int i = 0; i < feldSize; i++) {
				if (rnd.nextInt(100) < dichte) {
					Item spawn = defaultItems.get(rnd.nextInt(defaultItems.size()));
					spawnItem(gameId, spawn, -1);
				}
			}
		} catch (NoResultException e) {
		}

	}

	private void spawnItem(int gameId, Item item, int pos) {
		sender.sendAddItemToPlayground(gameId, item, pos);
		System.out.println(name + gameId + " Spawn Item:{" + item + ", pos: " + pos + "}");
	}
}
