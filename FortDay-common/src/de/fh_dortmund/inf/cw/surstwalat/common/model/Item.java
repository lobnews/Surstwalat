package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * This Entity represents an item.
 * @author Niklas Sprenger
 *
 */
@Table(name="Item")
@Entity
@NamedQueries({
	@NamedQuery(name="Item.countBySpecial", query="SELECT count(i) FROM Item i WHERE i.special = :special"),
	@NamedQuery(name="Item.getBySpecial", query="SELECT i FROM Item i WHERE i.special = :special"),
	@NamedQuery(name="Item.getById", query="SELECT i FROM Item i WHERE i.id = :id"),
	@NamedQuery(name="Item.count", query="SELECT count(i) FROM Item i")
})
public class Item implements Serializable{

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	private boolean special;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isSpecial() {
		return special;
	}

	public void setSpecial(boolean special) {
		this.special = special;
	}

}
