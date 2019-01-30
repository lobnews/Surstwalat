package de.fh_dortmund.cw.surstwalat.ejb.itemmanagement;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Dice;
import de.fh_dortmund.inf.cw.surstwalat.common.model.HealthItem;
import de.fh_dortmund.inf.cw.surstwalat.common.model.HealthItem.Level;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Playground;

/**
 * Session Bean implementation class ItemmanagementStarter
 */
@Singleton
@Startup
public class ItemmanagementStarterBean {
	
	@PersistenceContext
	private EntityManager entitymanager;
	
	@PostConstruct
	public void init() {
		TypedQuery<Long> q = entitymanager.createNamedQuery("Item.count", Long.class);
		Long itemsInDb = q.getSingleResult();
		if(itemsInDb == 0) {
			createItems();
		}
	}
	
	private void createItems() {
		for(Item item : createDefaultItems()) {
			entitymanager.persist(item);
		}
		for(Item item : createAirDropItems()) {
			item.setSpecial(true);
			entitymanager.persist(item);
		}
	}

	private List<Item> createAirDropItems() {
		List<Item> airDropItems = new ArrayList<>();
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
		return airDropItems;
	}

	private List<Item> createDefaultItems() {
		List<Item> defaultItems = new ArrayList<>();
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
		return defaultItems;
	}

}
