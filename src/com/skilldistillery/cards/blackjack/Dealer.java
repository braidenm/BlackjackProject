package com.skilldistillery.cards.blackjack;

import java.util.List;

import com.skilldistillery.cards.Card;
import com.skilldistillery.cards.Hand;

public class Dealer extends Player {
	Hand hand;

	public Dealer(String name) {
		super(name);
		hand = super.getHand();
		
	}
	
	public void dealerFirstHand() {
		List<Card> cards = hand.getCards();
		System.out.println(super.getName() + " is showing a " + cards.get(0));
	}
	
	
	
	

		
		
	
}


