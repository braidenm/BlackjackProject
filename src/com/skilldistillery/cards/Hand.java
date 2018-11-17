package com.skilldistillery.cards;

import java.util.ArrayList;
import java.util.List;

public abstract class Hand {
	
	
	private List<Card> handOfCards;
		
	
	
	public Hand() {
		handOfCards = new ArrayList<>();
	}

	public abstract int getHandvalue();
	
	public void addCard(Card card){
		handOfCards.add(card);
		
	}
	public void clearHand() {
		handOfCards = null;
	}
	
	public List<Card> getCards(){
		
		return handOfCards;
	}

	@Override
	public String toString() {
		return handOfCards.toString();
	}
	
	
}

