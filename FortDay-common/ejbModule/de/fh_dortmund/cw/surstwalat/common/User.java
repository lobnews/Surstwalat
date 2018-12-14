package de.fh_dortmund.cw.surstwalat.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class User {

	private static final int MAX_ITEM = 10;
	
	@Id
	private String name;
	
	@JoinColumn(name = "id")
	@OneToMany(fetch = FetchType.LAZY)
	private List<Item> inventar;
	
	public List<Item> getInventar() {
		ArrayList<Item> ret = new ArrayList<Item>(inventar);
		ret.add(new Wuerfel(new int[]{1,2,3,4,5,6}, "1-6"));
		return ret;
	}

	public void addInventar(Item item) {
		if(inventar == null) inventar = new ArrayList<>();
		if(inventar.size() <= MAX_ITEM) inventar.add(item);
	}

	public String getName() {
		return name;
	}
}
