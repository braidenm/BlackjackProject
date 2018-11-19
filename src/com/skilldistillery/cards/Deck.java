package com.skilldistillery.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	private List<Card> deck;

	public Deck() {
		super();
		deck = new ArrayList<>();
		Suit[] suitArr= Suit.values();
		Rank[] rankArr = Rank.values();
		for (int i = 0 ; i < rankArr.length - 1; i++) {
			for (int j = 0; j < suitArr.length; j++) {
				deck.add(new Card(suitArr[j], rankArr[i]));
			}
		}
	}
	public int checkDeckSize() {
		return deck.size();
	}
	public Card dealcard() {
		return deck.remove(0);
	}
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	public List<Card> getDeck() {
		return deck;
	}
	public void addToDeck (List<Card> deck2) {
		deck.addAll(deck2);
	}

}
