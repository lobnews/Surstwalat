package de.fh_dortmund.cw.surstwalat.ejb.ki;

import java.util.List;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Item;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Player;
import de.fh_dortmund.inf.cw.surstwalat.common.model.Token;
import de.fh_dortmund.inf.cw.surstwalat.ki.beans.interfaces.KiLocal;
import de.fh_dortmund.inf.cw.surstwalat.usersession.beans.interfaces.UserSession;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Marcel Scholz
 *
 */
@Stateless
public class KiBean implements KiLocal {
	
	@EJB
	private OutgoingEventHelperBean sender;
	
	@PersistenceContext
	private EntityManager em;
	
	private int gameid;
	
	private int player_id;
	
	private Player player;
	
	private List<Item> inventory;

	public void init() {
		System.out.println("@@@FortDayKi started");
	}
	
	public void makeTurn(int playerid, int game_id) {
		
		this.getInventory(playerid);
		
		this.setGameid(game_id);
		
		Item item;
		
		if(!inventory.isEmpty())
		{
			item = inventory.get(0);
			sender.sendUseItem(gameid, player.getPlayerNo(), item);
		}
		else
		{
			sender.sendPlayerRoll(gameid,player.getPlayerNo());
		}
	}

	private void getInventory(int playerid) {
		Query getInventory = em.createNamedQuery("Player.getById");
		getInventory.setParameter("id", playerid);
		Player player = (Player) getInventory.getSingleResult();
		this.setPlayer(player);
		this.setInventory(player.getItems());
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getGameid() {
		return gameid;
	}

	public void setGameid(int gameid) {
		this.gameid = gameid;
	}

	public int getPlayerid() {
		return player_id;
	}

	public void setPlayerid(int playerid) {
		this.player_id = playerid;
	}

	public void setInventory(List<Item> inventory) {
		this.inventory = inventory;
	}
	
	public List<Token> getTokens(int playerid)
	{
		Query getTokenList = em.createNamedQuery("Token.getTokenList");
		getTokenList.setParameter("id", playerid);
		List<Token> tokelist = getTokenList.getResultList();
		
		return tokelist;
	}

	public void moveToken(int playerid, int count) {
		List<Token> tokelist = this.getTokens(playerid);
		sender.sendTokenMove(gameid, tokenid, count);;
	}
}