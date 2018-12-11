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

	@Id
	private String name;
	
	@JoinColumn(name = "id")
	@OneToMany(fetch = FetchType.LAZY)
	private List<Item> inventar;

	public List<Item> getInventar() {
		return inventar;
	}

	public void addInventa(Item item) {
		if(inventar == null) inventar = new ArrayList<>();
		inventar.add(item);
	}

	public String getName() {
		return name;
	}
}
