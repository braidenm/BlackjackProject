package com.skilldistillery.cards.blackjack;

import java.util.List;

import com.skilldistillery.cards.Card;
import com.skilldistillery.cards.Hand;

public class BlackjackHand extends Hand {

	@Override
	public int getHandvalue() {
		
		List<Card> cards = super.getCards();
		int value = 0;
		for (Card card : cards) {
			int oldvalue = value;
			value = card.getValue();
			value = value + oldvalue;
		}
		return value;
	}

	@Override
	public String toString() {
		return super.toString() + " ("+ this.getHandvalue()+")";
	}

}
