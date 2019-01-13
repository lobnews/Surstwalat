package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Niklas Sprenger
 *
 */
@Entity
public class Game  implements Serializable{
	
	@Id
	@GeneratedValue
	private int id;
	private List<Player> players;
	private boolean gameStarted;
	
	public Game() {
		gameStarted = false;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	public boolean isGameStarted() {
		return gameStarted;
	}
	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}
}
