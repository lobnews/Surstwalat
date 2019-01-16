package de.fh_dortmund.inf.cw.surstwalat.dispatcher.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="DispatcherPlayer")
public class Player {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Integer userId;
	private Integer playerNo;
	
	@ManyToOne
	private Game game;
	
	@OneToMany(mappedBy="player")
	private List<Action> actionHistory;
	
	
	public Long getId() {
		return id;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPlayerNo() {
		return playerNo;
	}

	public void setPlayerNo(Integer playerNo) {
		this.playerNo = playerNo;
	}

	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public List<Action> getActionHistory() {
		return actionHistory;
	}
	public void setActionHistory(List<Action> actionHistory) {
		this.actionHistory = actionHistory;
	}
	
	
}
