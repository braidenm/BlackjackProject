package com.skilldistillery.cards.blackjack;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.cards.Card;
import com.skilldistillery.cards.Deck;
import com.skilldistillery.cards.Rank;
import com.skilldistillery.cards.Suit;

public class BlackjackApp {
	Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		BlackjackApp blackjack = new BlackjackApp();
		blackjack.run();
	}

	private void run() {
		
		Deck deck = new Deck();
		Dealer dealer = new Dealer("Bob the Dealer");
		List<Player> playerList = new ArrayList<>();
		String quit = "";
		int choice = 0;
		Card card;
		int bet;
		
		
		
		
		System.out.println("Welcome to Blackjack");
		System.out.print("What is your name? ");
		String playerName = sc.nextLine();
		System.out.println();
		
		Player player1 = new Player(playerName);
		
		System.out.println("Hello " + playerName+" Let's get started.");
		
		playerList.add(dealer);
		playerList.add(player1);
		
		deck.shuffle();
		do {
			quit = "";
			bet = 0;
			
			if(deck.checkDeckSize() < 15) {
				deck = new Deck();
				deck.shuffle();
				System.out.println("The Deck has been re-shuffled");
				System.out.println();
			}
			
			clearAllCards(playerList);
			if(player1.getMoney() < 5) {
				System.out.println("You don't have enough to play, better luck next time. goodbye!");
				System.exit(0);
			}
			do {
			try {
				System.out.println("You have: $"+player1.getMoney());
				System.out.println("Minimum bet is $5, how lucky are you feeling? ");
				bet = sc.nextInt();
				bet = player1.bet(bet);
				}
				catch (Exception e) {
					System.err.println("Not a valid number, try again");
				}
			}while(bet < 5);
			
			
			dealAllCards(playerList, deck);
			System.out.println(dealer.getName() + " deals out 2 cards to everyone ");
			System.out.println();
			
			
			dealer.dealerFirstHand();
			System.out.println();
			System.out.println(player1);
			System.out.println();
			
			ifContains2AcesChangeOne(player1);
			ifContains2AcesChangeOne(dealer);
			
			
			
			
			if(player1.getPlayerHandValue() == 21 && dealer.getPlayerHandValue() !=21) {
				
				System.out.print("BlackJack! =You win! pays 3 to 1.");
				System.out.println("you won: $"+ ((int)(bet * 1.5)));
				player1.winMoney((int)(bet+ (bet * 1.5)));
				System.out.println("Leave the Table? (Y/N): ");
				quit = sc.next();
			}
			if(player1.getPlayerHandValue() == 21 && dealer.getPlayerHandValue() ==21) {
				System.out.print("Dealer also has Blackjack, its a push!");
				player1.tie(bet);
				System.out.println("Leave the Table? (Y/N): ");
				quit = sc.next();
				
			}
			if (quit.equalsIgnoreCase("N")) {
				continue;
			}
			if (quit.equalsIgnoreCase("Y")) {
				break;
			}
			
			if(dealer.showingCard().getValue() == 10 || dealer.showingCard().getValue() == 11) {
				
				System.out.println("Dealer will check if they have blackjack...");
				
				quit = doesDealerHaveBlackjack(dealer, player1, bet);
			
				if(quit.equalsIgnoreCase("N")) {
						continue;
				}
				
				System.out.println("Dealer does not have blackjack.");
				System.out.println();
			
			}
			
			do {
				try {
				
					mainMenu();
				
					choice = sc.nextInt();
				}
				catch (InputMismatchException e){
					System.err.println("Thats not a valid selection, try again");
					System.out.println();
					sc.nextLine();
					choice = 0;
				}
				
			}while (choice <= 0 || choice > 3);
			
			do {
				if(choice == 1) {
					String doubleDown = "N";
					
					if(player1.getMoney() >= 2*bet) {
						System.out.print("Double down? Will double your bet. (Y/N): ");
						doubleDown = sc.next();
					}
					
					if(doubleDown.equalsIgnoreCase("Y")) {
						bet = bet + player1.bet(bet);
					}
					
					card = deck.dealcard();
					System.out.println("you got " +card);
					System.out.println();
					Hit(player1, card);
					System.out.println();
					
					
					if (player1.getPlayerHandValue() > 21) {
						
						
						ifContainsAceReplaceWithNewValue(player1);
						
						if (player1.getPlayerHandValue() > 21) {
						
						System.out.println("You busted, dealer wins");
						player1.loseMoney(bet);
						System.out.print("Leave the table? (Y/N) ");
						quit = sc.next();
						}
						
						if(quit.equalsIgnoreCase("Y")) {
							choice = 3;
							break;
						}
						else if (quit.equalsIgnoreCase("N")){
							choice = 0;
								break;
						}
						
					}
					
					if(doubleDown.equalsIgnoreCase("Y")) {
						choice = 2;
						break;
					}
					do{
						try{
							mainMenu();
							choice = sc.nextInt();
						}
						catch (InputMismatchException e){
							System.err.println("Thats not a valid selection, try again");
							System.out.println();
							sc.nextLine();
							choice = 0;	
						}
					}while (choice <= 0 || choice > 3);
					
				}
			}while (choice == 1);
			
			if(choice == 2) {
				System.out.println(dealer);
				
				
				if (containsAce(dealer) && dealer.getPlayerHandValue() >= 17) {
					ifContainsAceReplaceWithNewValue(dealer);
					while (dealer.getPlayerHandValue() < 17) {
						System.out.println(dealer.getName() + " takes another card.");
						Hit(dealer, deck.dealcard());
						}
				}
					
				
				if (dealer.getPlayerHandValue() < 17) {
				while (dealer.getPlayerHandValue() < 17) {
					System.out.println(dealer.getName() + " takes another card.");
					Hit(dealer, deck.dealcard());
					}
				}
				if (dealer.getPlayerHandValue() > 21) {
					System.out.println("Dealer busted, You win!");
					player1.winMoney(bet);
					System.out.print("Leave the table? (Y/N) ");
					quit = sc.next();
				}
				
				else if (dealer.getPlayerHandValue() > player1.getPlayerHandValue()) {
						System.out.print("Dealer won.");
						player1.loseMoney(bet);
						System.out.println("leave the table? (Y/N) ");
						quit = sc.next();
				}
				else if(dealer.getPlayerHandValue() == player1.getPlayerHandValue()) {
						System.out.println("You push.");
						player1.tie(bet);
						System.out.println("leave the Table? (Y/N) ");
						quit = sc.next();
				}
				else if(dealer.getPlayerHandValue() < player1.getPlayerHandValue()) {
						System.out.print("You won!");
						player1.winMoney(bet);
						System.out.println("leave the table? (Y/N) ");
						quit = sc.next();
				}
				
			}
			if(choice == 3) {
				quit = "Y";
			}
			
		}while (!quit.equalsIgnoreCase("Y"));
		System.out.println("May the odds be in your favor, goodbye.");
		sc.close();
		
	}
		
	private String doesDealerHaveBlackjack(Player dealer, Player player1, int bet) {
		String quit = "";
		
		
		if(dealer.getPlayerHandValue() == 21) {
				System.out.println("Dealer has blackjack and you don't. So you lose.");
				player1.loseMoney(bet);
				System.out.println("Leave the Table? (Y/N) ");
				quit = sc.next();
				
		}
		
		if(quit.equalsIgnoreCase("y")) {
				System.out.println();
				System.out.println("May the odds be in your favor, goodbye.");
				System.exit(0);
		}
		return quit;
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
	public void clearAllCards(List<Player> playerList){
		
		for (Player player : playerList) {
			if (player.getPlayerHandValue() != 0) {
			player.clearPlayerHand();
			
			if(player.getHandList().size()>0) {
				player.clearHandList();
			}
			}
		}
			
	}
	public void ifContainsAceReplaceWithNewValue(Player player1) {
	
		List<Card> aceList = getAceList();

		List<Card> playerHand = player1.getHand().getCards();
		
		for (int i = 0 ; i < playerHand.size(); i++) {
			for (Card ace : aceList) {
				if (playerHand.get(i).equals(ace) && ace.getValue() == 11) {
					playerHand.set(i, ace);
					System.out.println("Soft ace take effect: " +player1);
					System.out.println();
				}
			}
		}
	}
	
	public boolean containsAce(Player player1) {
		List<Card> aceList = getAceList();

		
		List<Card> playerHand = player1.getHand().getCards();
		
		for (int i = 0 ; i < playerHand.size(); i++) {
			for (Card ace : aceList) {
				if (playerHand.get(i).equals(ace)) {
					return true;
				}
			}
		}
		
		
		return false;
		
	}
	
	public void ifContains2AcesChangeOne(Player player){
		
		List<Card> aceList = getAceList();

		List<Card> playerHand = player.getHand().getCards();
		
		List<Card> numberOfAces = new ArrayList<>();
		
		for (int i = 0 ; i < playerHand.size(); i++) {
			for (Card ace : aceList) {
				
				if ( playerHand.get(i).equals(ace)) {
					numberOfAces.add(ace);
					
				}
			}
		}
		
					
		if (playerHand.size() == numberOfAces.size()) {
			
			playerHand.set(0, numberOfAces.get(0));
			
			System.out.println("Since there are 2 aces one of them is now worth 1");
			System.out.println(player);
		}
				
			
		
			
	}
	public List<Card> getAceList(){
		
		List<Card> aceList = new ArrayList<>();
		Card ace1 = new Card(Suit.CLUBS, Rank.ACE1);
		Card ace2 = new Card(Suit.DIAMONDS, Rank.ACE1);
		Card ace3 = new Card(Suit.HEARTS, Rank.ACE1);
		Card ace4 = new Card(Suit.SPADES, Rank.ACE1);
		
		aceList.add(ace4);
		aceList.add(ace3);
		aceList.add(ace2);
		aceList.add(ace1);
		
		return aceList;
	}
	public boolean canSplit(Player player) {
		Rank rank1 = player.getHand().getCards().get(0).getRank();
		Rank rank2 = player.getHand().getCards().get(1).getRank();
			
		if(rank1.equals(rank2)) {
			return true;
		}
		return false;
		
	}
	
	


}

