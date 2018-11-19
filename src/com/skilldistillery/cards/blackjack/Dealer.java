package com.skilldistillery.cards.blackjack;

import java.util.List;

import com.skilldistillery.cards.Card;
import com.skilldistillery.cards.Hand;
import com.skilldistillery.cards.Rank;

public class Dealer extends Player {
	private Hand hand;
	private List<Card> cards; 

	public Dealer(String name) {
		super(name);
		hand = super.getHand();
		cards = hand.getCards();
		
	}
	
	public void dealerFirstHand() {
		cards = hand.getCards();
		System.out.println(super.getName() + " is showing " + cards.get(0));
	}
	public Rank dealerShowingRank() {
		cards = hand.getCards();
		return cards.get(0).getRank();
	}
	public Card showingCard() {
		return cards.get(0);
	}
	
	
	
	

		
		
	
}


