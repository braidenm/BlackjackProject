package com.skilldistillery.cards.blackjack;

import java.util.List;

import com.skilldistillery.cards.Card;
import com.skilldistillery.cards.Hand;

public class BlackjackHand extends Hand {
	private int bet;

	public BlackjackHand() {
		super();
	}

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

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}
	
	

	
	


	


}
