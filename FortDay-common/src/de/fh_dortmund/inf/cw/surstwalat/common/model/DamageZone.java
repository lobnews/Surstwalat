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
 * Damage zone
 */

@Table(name="DamageZone")
@Entity
@NamedQueries({
	@NamedQuery(name="DamageZone.getByGame", query="SELECT z FROM DamageZone z WHERE z.game = :game")
})
public class DamageZone implements Serializable{
    
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	@JoinColumn(name="game")
        @OneToOne(fetch = FetchType.LAZY)
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
	
        /**
         * @return the id of the zone
         */
	public int getId() {
		return id;
	}
        /**
         * set the id of the zone
         * 
         * @param id 
         */
	public void setId(int id) {
		this.id = id;
	}
        /**
         * @return game object of the game that this zone belongs to
         */
	public Game getGame() {
		return game;
	}
        /**
         * set the game this zone belongs to
         * 
         * @param game 
         */
	public void setGame(Game game) {
		this.game = game;
	}
        /**
         * @return damage of the zone
         */
	public int getDamage() {
		return damage;
	}
        /**
         * set the damage of the zone
         * 
         * @param damage 
         */
	public void setDamage(int damage) {
		this.damage = damage;
	}
        /**
         * @return the begin of the current damage zone
         */
	public int getCurrentZoneBegin() {
		return currentZoneBegin;
	}
        /**
         * set the begin of the current zone
         * 
         * @param currentZoneBegin 
         */
	public void setCurrentZoneBegin(int currentZoneBegin) {
		this.currentZoneBegin = currentZoneBegin;
	}
        /**
         * @return the size of the current zone
         */
	public int getCurrentZoneSize() {
		return currentZoneSize;
	}
        /**
         * set the size of the current zone
         * 
         * @param currentZoneSize 
         */
	public void setCurrentZoneSize(int currentZoneSize) {
		this.currentZoneSize = currentZoneSize;
	}
        /**
         * @return the begin of the next zone
         */
	public int getNextZoneBegin() {
		return nextZoneBegin;
	}
        /**
         * set the begin of the next zone
         * @param nextZoneBegin 
         */
	public void setNextZoneBegin(int nextZoneBegin) {
		this.nextZoneBegin = nextZoneBegin;
	}
        /**
         * @return the size of the next zone
         */
	public int getNextZoneSize() {
		return nextZoneSize;
	}
        /**
         * set the size of the next zone
         * 
         * @param nextZoneSize 
         */
	public void setNextZoneSize(int nextZoneSize) {
		this.nextZoneSize = nextZoneSize;
	}
}
