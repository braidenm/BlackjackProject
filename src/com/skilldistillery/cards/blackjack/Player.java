package com.skilldistillery.cards.blackjack;

import com.skilldistillery.cards.Card;

public class Player {
	private BlackjackHand hand;
	private String name;
	
	
	Player(String name){
		this.name = name;
		hand = new BlackjackHand();
		
	}

	public void addCardPlayerHand(Card card) {
		hand.addCard(card);
	}
	
	public void clearPlayerHand() {
		hand.clearHand();
	}
	
	public int getPlayerHandValue() {
		return hand.getHandvalue();
	}
	
	
	@Override
	public String toString() {
		return name + "'s hand = " + hand +" ]";
	}

	public String getName() {
		return name;
	}

	public BlackjackHand getHand() {
		return hand;
	}
	


	

}
