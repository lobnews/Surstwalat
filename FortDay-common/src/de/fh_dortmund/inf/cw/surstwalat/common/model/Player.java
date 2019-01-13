package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Niklas Sprenger
 *
 */
@Table(name="Player")
@Entity
@NamedQueries({
	@NamedQuery(name="Player.getById", query="SELECT p FROM Player p WHERE p.id = :id")
})
public class Player  implements Serializable{


	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	@JoinColumn
	private List<Item> items;
	@JoinColumn
	private int account_id;
	@Column(name="isHuman")
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
