package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Niklas Sprenger
 *
 */
@Entity
public class Token  implements Serializable{

	//temp, sp�ter zusammengesetzten key nutzen ?
	@Id
	@GeneratedValue
	private int id;
	private int player_id;
	private int nr;
	private int health;
	private int maxHealth;
	
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
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
