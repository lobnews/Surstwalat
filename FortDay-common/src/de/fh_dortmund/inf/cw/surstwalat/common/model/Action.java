package de.fh_dortmund.inf.cw.surstwalat.common.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author Johannes Heiderich
 *
 */
@Entity
@Table(name="DispatcherAction")
public class Action {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private Player player;
	
	private ActionType actionType;
	
	@OneToOne
	private Item item;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ACTION_RESULT_ID", unique=true)
	private ActionResult result;

	public int getId() {
		return id;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ActionResult getResult() {
		return result;
	}

	public void setResult(ActionResult result) {
		this.result = result;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	
	
	
	
	
}
