package de.fh_dortmund.inf.cw.surstwalat.common.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Dice extends Item {
	private static final long serialVersionUID = 1L;
	
	@Column
	private String label;
	
	private List<Integer> numbers;
	
	public Dice() {
		
	}
	
	public Dice(int[] numbers, String label) {
		setNumbers(numbers);
		this.label = label;
		
	}
	
	public Dice(List<Integer> zahlen, String label) {
		this.numbers = zahlen;
		this.label = label;
		
	}
	
	public List<Integer> getNumbers() {
		return numbers;
	}
	
	public Integer[] getNumbersAsArray() {
		return numbers.toArray(new Integer[] {});
	}
	
	public void setNumbers(List<Integer> zahlen) {
		this.numbers = zahlen;
	}
	
	public void setNumbers(int[] numberArray) {
		this.numbers = new ArrayList<>();
		for (int i = 0; i < numberArray.length; i++) {
			this.numbers.add(numberArray[i]);
		}
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
