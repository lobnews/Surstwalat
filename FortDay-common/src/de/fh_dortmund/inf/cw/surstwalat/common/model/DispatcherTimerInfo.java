package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.io.Serializable;
/**
 * Info class for the player timeout
 * @author Johannes Heiderich
 *
 */
public class DispatcherTimerInfo implements Serializable {
	private String id;
	private int gameId;
	private int playerId;
	private int playerNo;
	private int secondsLeft;
	
	public DispatcherTimerInfo(String id, int gameId, int playerId, int playerNo, int secondsLeft) {
		this.gameId = gameId;
		this.id = id;
		this.playerId = playerId;
		this.playerNo = playerNo;
		this.secondsLeft = secondsLeft;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getPlayerNo() {
		return playerNo;
	}

	public void setPlayerNo(int playerNo) {
		this.playerNo = playerNo;
	}

	public int getSecondsLeft() {
		return secondsLeft;
	}

	public void setSecondsLeft(int secondsLeft) {
		this.secondsLeft = secondsLeft;
	}	
	
	
}
