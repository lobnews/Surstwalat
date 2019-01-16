package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Niklas Sprenger
 *
 */
@Table(name="Game")
@Entity
@NamedQueries({
	@NamedQuery(name="Game.getAll", query="SELECT g FROM Game g"),
	@NamedQuery(name="Game.getById", query="SELECT g FROM Game g WHERE g.id = :id"),
	@NamedQuery(name="Game.getStarted", query="SELECT g FROM Game g WHERE g.gameStarted = true"),
	@NamedQuery(name="Game.getOpen", query="SELECT g FROM Game g WHERE g.gameStarted = false"),
})
public class Game implements Serializable{

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	//only necessary for lobby management
	private List<Account> humanUsersInGame;
	private List<Player> players;
	@Column(name="gameStarted")
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

	public List<Account> getHumanUsersInGame() {
		return humanUsersInGame;
	}

	public void setHumanUsersInGame(List<Account> humanUsersInGame) {
		this.humanUsersInGame = humanUsersInGame;
	}

	public void addHumanUserToOpenGame(Account user) {
		humanUsersInGame.add(user);
	}

	public void removeHumanUserFromOpenGame(Account user) {
		humanUsersInGame.remove(user);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (gameStarted ? 1231 : 1237);
		result = prime * result + ((humanUsersInGame == null) ? 0 : humanUsersInGame.hashCode());
		result = prime * result + id;
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (gameStarted != other.gameStarted)
			return false;
		if (humanUsersInGame == null) {
			if (other.humanUsersInGame != null)
				return false;
		} else if (!humanUsersInGame.equals(other.humanUsersInGame))
			return false;
		if (id != other.id)
			return false;
		if (players == null) {
			if (other.players != null)
				return false;
		} else if (!players.equals(other.players))
			return false;
		return true;
	}


}
