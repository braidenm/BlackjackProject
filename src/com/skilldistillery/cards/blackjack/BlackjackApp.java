package com.skilldistillery.cards.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.cards.Card;
import com.skilldistillery.cards.Deck;

public class BlackjackApp {
	public static void main(String[] args) {
		BlackjackApp blackjack = new BlackjackApp();
		blackjack.run();
	}

	private void run() {
		Scanner sc = new Scanner(System.in);
		Deck deck = new Deck();
		Dealer dealer = new Dealer("Bob the Dealer");
		List<Player> playerList = new ArrayList<>();
		
		
		String quit  = "N";
		int choice = 0;
		Card card;
		
		
		System.out.println("Welcome to BlackJAck");
		System.out.print("What is your name? ");
		String playerName = sc.nextLine();
		
		Player player1 = new Player(playerName);
		
		System.out.println("Hello " + playerName+" Let's get started.");
		
		playerList.add(dealer);
		playerList.add(player1);
		
		deck.shuffle();
		do {
			if(deck.checkDeckSize() < 10) {
				deck = new Deck();
				deck.shuffle();
				System.out.println("The Deck has been re-shuffled");
			}
			
			dealAllCards(playerList, deck);
			System.out.println(dealer.getName() + " deals out 2 cards to everyone ");
			
			
			dealer.dealerFirstHand();
			System.out.println(player1);
		
			mainMenu();
			try {
				
			choice = sc.nextInt();
			}
			catch (Exception e){
				sc.nextInt();
				System.err.println("Thats not a valid selection, try again");
				
			}
			if(choice == 1) {
				card = deck.dealcard();
				System.out.println("you got a " +card);
				Hit(player1, card);
				System.out.println(player1);
				if (player1.getPlayerHandValue() > 21) {
					System.out.println("You busted, dealer wins");
					do {
						
						System.out.print("Play Again (Y/N) ");
						quit = sc.nextLine();
						
						if(!quit.equalsIgnoreCase("Y")|| !quit.equalsIgnoreCase("N")) {
							System.out.println();
						}
					}while ( !quit.equalsIgnoreCase("Y") || !quit.equalsIgnoreCase("N"));
				}
				
				
				
			}
			
			if(choice == 2) {
				System.out.println(dealer);
				
				if (dealer.getPlayerHandValue() < 17) {
				while (dealer.getPlayerHandValue() < 17) {
					System.out.println(dealer.getName() + " takes another card.");
					Hit(dealer, deck.dealcard());
					System.out.println(dealer);
						
					}
				}
				
			if(choice == 3) {
				quit = "Y";
			}
			}
			
		}while (!quit.equalsIgnoreCase("N"));
		
		
		
	}
	public void mainMenu() {
		System.out.println("******What do you want to do?*******\n"
				+ "1) Hit\n"
				+ "2) Stay\n"
				+ "3) Quit");
	}
	public void Hit(Player player, Card card){
		player.addCardPlayerHand(card);
		System.out.println(player);
		
	}
	public void dealAllCards(List<Player> playerList, Deck deck){
		for (int i = 0; i < 2; i++) {
			
			for (Player player : playerList) {
				player.addCardPlayerHand(deck.dealcard());
			}
		}
	
	}
	

}
