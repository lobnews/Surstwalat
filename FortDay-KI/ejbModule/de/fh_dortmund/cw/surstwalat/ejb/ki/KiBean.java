package de.fh_dortmund.cw.surstwalat.ejb.ki;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.ki.beans.interfaces.KiRemote;
import de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSession;

public class KiBean implements KiRemote {
	
	@EJB
	private UserSession session;
	
	private int gameid;
	
	private int playerid;
	
	private List<Item> inventory;
	
	@PostConstruct
	public void init() {
		System.out.println("@@@FortDayKi started");
	}
	
	public List<Item> getInventory()
	{
		return null;
	}

	@Override
	public void makeTurn() {
		
	}

	public int getGameid() {
		return gameid;
	}

	public void setGameid(int gameid) {
		this.gameid = gameid;
	}

	public int getPlayerid() {
		return playerid;
	}

	public void setPlayerid(int playerid) {
		this.playerid = playerid;
	}

	public void setInventory(List<Item> inventory) {
		this.inventory = inventory;
	}

}
