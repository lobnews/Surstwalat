package de.fh_dortmund.inf.cw.surstwalat.dispatcher.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="DispatcherGame")
public class Game {
	@Id
	private Long id;
	
	@OneToMany(mappedBy="game", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private List<Player> players;
		
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	
}
