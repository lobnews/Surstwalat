package de.fh_dortmund.inf.cw.surstwalat.dispatcher.beans;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Dice;
import de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces.DiceRollLocal;

import java.util.Random;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class DiceRollBean
 * @author Johannes Heiderich
 */
@Stateless
public class DiceRollBean implements DiceRollLocal {

	@Override
	public int roll(Dice dice) {
		int size = dice.getNumbers().size();
		if(size > 0) {
			Random rnd = new Random();
			return dice.getNumbers().get(rnd.nextInt(size));
		}
		return 0;
	}

}
