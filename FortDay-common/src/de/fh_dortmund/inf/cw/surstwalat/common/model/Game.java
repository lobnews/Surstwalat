package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.io.Serializable;
import java.util.List;

public class Game  implements Serializable{
	private int id;
	private List<Player> players;
	
	
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
}
