package de.fh_dortmund.inf.cw.surstwalat.dispatcher.interfaces;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.surstwalat.common.model.Dice;

@Local
public interface DiceRollLocal {

	int roll(Dice dice);
}
