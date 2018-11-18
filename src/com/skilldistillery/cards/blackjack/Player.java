package com.skilldistillery.cards.blackjack;

import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.cards.Card;

public class Player {
	private BlackjackHand hand;
	private String name;
	private int money;
	private List<BlackjackHand> handList;
	
	
	public Player(String name){
		this.name = name;
		hand = new BlackjackHand();
		money = 100;
		handList = new ArrayList<>();
		handList.add(hand);
		
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
	public List<BlackjackHand> getHandList() {
		return handList;
	}
	public void clearHandList() {
		handList = new ArrayList<>();
	}
	
	public List<BlackjackHand> splitHand(BlackjackHand hand1) {
		BlackjackHand hand2 = new BlackjackHand();
		
		hand2.addCard(hand1.removeCard());
		handList.add(hand1);
		handList.add(hand2);
		
		return handList;
	}
	
	
	@Override
	public String toString() {
		if (handList.size() > 1) {
			String viewAll = "[ ";
			for (int i = 0 ; i< handList.size() ; i++) {
				viewAll = viewAll + (i+1) + ") " + handList.get(i) +"] ";
			}
			return name + "'s hands = " +viewAll;
		}
		return name + "'s hand = " + hand +" ]";
	}

	public String getName() {
		return name;
	}

	public BlackjackHand getHand() {
		return hand;
	}
	
	public void loseMoney(int bet) {
		if(money == 0) {
			System.out.println("Out of money. Game Over");
			System.exit(0);
		}
		System.out.println("You lost $" + bet + " and now have: $"+money);
	}
	
	public int  getMoney() {
		return money;
	}
	
	public int bet (int bet) {
		if(money < bet) {
			System.out.println("Not enough money.");
		}
		if(money >= bet) {
			money -=bet;
			return bet;
		}
		
	return 0;
		
	}
	public int allIn() {
		
		int allIn = money;
		money=0;
		return allIn;
	}
	public void winMoney(int winnings) {
		System.out.print("You won $"+winnings);
		winnings +=winnings;
		money +=winnings;
		System.out.println(", your total is: $"+money);
	}
	public void tie (int bet) {
		money += bet;
		System.out.println("You won your money back. balance: " + money);
	}
	


	

}
