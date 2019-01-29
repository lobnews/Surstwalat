package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * This entity represents an Token. It is used to give a player object health and a number.
 * @author Niklas Sprenger
 *
 */
@Table(name="Token")
@NamedQueries(
{
  @NamedQuery(name = "Token.getById", query = "SELECT t FROM Token t WHERE t.id = :id"),
  @NamedQuery(name = "Token.getByPlayerIdAndTokenNumber", query = "SELECT t FROM Token t WHERE t.playerId = :playerId AND t.nr=:nr")

})
@Entity
public class Token implements Serializable{

	//temp, spaeter zusammengesetzten key nutzen ?
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	@Column(name="playerId")
	private int playerId;
	@Column(name="nr")
	private int nr;
	@Column(name="health")
	private int health;
	
    @Column(name="maxHealth")
	private int maxHealth;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public int getNr() {
		return nr;
	}
	public void setNr(int nr) {
		this.nr = nr;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
}
