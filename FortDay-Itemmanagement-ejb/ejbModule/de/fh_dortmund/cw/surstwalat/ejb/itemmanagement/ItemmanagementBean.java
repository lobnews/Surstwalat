package de.fh_dortmund.cw.surstwalat.ejb.itemmanagement;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Player;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Playground;

/**
 * @author Marvin Woelk
 *
 */
@Stateless
public class ItemmanagementBean {

	public static final String name = "[ITEMMANAG]";

	@Inject
	private JMSContext jmsContext;

	@PersistenceContext
	private EntityManager entitymanager;

	@Resource(lookup = "java:global/jms/FortDayEventTopic")
	private Topic topic;

	@EJB
	private OutgoingEventHelperBean sender;

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
		Item spawn = selectRandomItem(true);
		spawnItem(gameId, spawn, -1);
	}
	
	

	public void spawnAllItems(int gameId, List<Item> items) {
		items.forEach(e -> spawnItem(gameId, e, -1));
	}

	public void spawnItems(int gameId, int dichte) {
		TypedQuery<Playground> q = entitymanager.createNamedQuery("Playground.getByGameId", Playground.class);
		q.setParameter("gameId", gameId);
		try {
			int feldSize = 40;

			Random rnd = new Random();
			for (int i = 0; i < feldSize; i++) {
				if (rnd.nextInt(100) < dichte) {
					Item spawn = selectRandomItem(false);
					spawnItem(gameId, spawn, -1);
				}
			}
		} catch (NoResultException e) {
			System.out.println("[ITEMMANAGEMENT]: Playground of game with ID " + gameId);
		}

	}

	private void spawnItem(int gameId, Item item, int pos) {
		sender.sendAddItemToPlayground(gameId, item, pos);
		System.out.println(name + gameId + " Spawn Item:{" + item + ", pos: " + pos + "}");
	}
	
	private Item selectRandomItem(boolean special) {
		TypedQuery<Long> countQuery = entitymanager.createNamedQuery("Item.countBySpecial", Long.class);
		countQuery.setParameter("special", special);
		long count = countQuery.getSingleResult();
		
		Random random = new Random();
	    int number = random.nextInt((int)count);
	    
	    TypedQuery<Item> selectQuery = entitymanager.createNamedQuery("Item.getBySpecial", Item.class);
	    selectQuery.setParameter("special", special);
	    selectQuery.setFirstResult(number);
	    selectQuery.setMaxResults(1);
	    return (Item)selectQuery.getSingleResult();
	}
}
