package de.fh_dortmund.inf.cw.surstwalat.itemmanagement;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Wuerfel extends Item {
	private static final long serialVersionUID = 1L;
	
	@Column
	private String label;
	
	@OneToMany
	@Column(name = "wuerfel")
	private List<Integer> zahlen;
	
	public Wuerfel() {
		
	}
	
	public Wuerfel(int[] zahlen, String label) {
		setZahlen(zahlen);
		this.label = label;
		
	}
	
	public Wuerfel(List<Integer> zahlen, String label) {
		this.zahlen = zahlen;
		this.label = label;
		
	}
	
	public List<Integer> getZahlenList() {
		return zahlen;
	}
	
	public Integer[] getZahlen() {
		return zahlen.toArray(new Integer[] {});
	}
	
	public void setZahlen(List<Integer> zahlen) {
		this.zahlen = zahlen;
	}
	
	public void setZahlen(int[] zahlen) {
		this.zahlen = new ArrayList<>();
		for (int i = 0; i < zahlen.length; i++) {
			this.zahlen.add(zahlen[i]);
		}
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
