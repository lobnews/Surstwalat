package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.io.Serializable;
import java.util.List;

public class Player  implements Serializable{
	private int id;
	private List<Item> items;
	private int account_id;
	private boolean isHuman;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public boolean isHuman() {
		return isHuman;
	}
	public void setHuman(boolean isHuman) {
		this.isHuman = isHuman;
	}
	
	
}
