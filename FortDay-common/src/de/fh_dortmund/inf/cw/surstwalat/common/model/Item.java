package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Niklas Sprenger
 *
 */
@Entity
public class Item  implements Serializable{
	
	@Id
	@GeneratedValue
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
