package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
	@OneToMany
	private List<Item> items;
	@Column
	private int accountId;
	@Column(name="isHuman")
	private boolean isHuman;
	@ManyToOne
	private Game game;


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
	public boolean isHuman() {
		return isHuman;
	}
	public void setHuman(boolean isHuman) {
		this.isHuman = isHuman;
	}
	public void addItem(Item...items) {
		for (Item item : items) {
			this.items.add(item);
		}
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

}
