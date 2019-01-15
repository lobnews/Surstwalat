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
 * @author Niklas Sprenger
 *
 */
@Table(name="Item")
@Entity
@NamedQueries({
	@NamedQuery(name="Item.getById", query="SELECT i FROM Item i WHERE i.id = :id")
})
public class Item  implements Serializable{

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}
