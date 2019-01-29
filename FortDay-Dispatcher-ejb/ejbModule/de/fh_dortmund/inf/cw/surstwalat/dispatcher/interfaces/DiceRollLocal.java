package de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Dice;

@Local
public interface DiceRollLocal {

	/**
	 * provides a simple roll functionality based on an @see de.fh_dortmund.inf.cw.surstwalat.common.model.Dice object
	 * @param dice the dice object
	 * @return the rolled value
	 */
	int roll(Dice dice);
}
