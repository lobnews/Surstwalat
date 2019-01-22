package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author Rebekka Michel
 *
 */

@Table(name="Zone")
@Entity
@NamedQueries({
	@NamedQuery(name="Zone.getByGameId", query="SELECT z FROM Zone z WHERE z.game_id = :id")
})
public class Zone implements Serializable{
    
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="game_id")
	private Game game;
	@Column(name="damage")
	private int damage;
	@Column(name="currentZoneBegin")
	private int currentZoneBegin;
	@Column(name="currentZoneSize")
	private int currentZoneSize;
	@Column(name="nextZoneBegin")
	private int nextZoneBegin;
	@Column(name="nextZoneSize")
	private int nextZoneSize;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getCurrentZoneBegin() {
		return currentZoneBegin;
	}
	public void setCurrentZoneBegin(int currentZoneBegin) {
		this.currentZoneBegin = currentZoneBegin;
	}
	public int getCurrentZoneSize() {
		return currentZoneSize;
	}
	public void setCurrentZoneSize(int currentZoneSize) {
		this.currentZoneSize = currentZoneSize;
	}
	public int getNextZoneBegin() {
		return nextZoneBegin;
	}
	public void setNextZoneBegin(int nextZoneBegin) {
		this.nextZoneBegin = nextZoneBegin;
	}
	public int getNextZoneSize() {
		return nextZoneSize;
	}
	public void setNextZoneSize(int nextZoneSize) {
		this.nextZoneSize = nextZoneSize;
	}
}
