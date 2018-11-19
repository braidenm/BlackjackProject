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
		
		Dealer dealer = new Dealer("Bob the Dealer");
		Deck deck = new Deck();
		Deck backUpDeck = new Deck();
		Card card;
		List<Player> playerList = new ArrayList<>();
		List<BlackjackHand> playerHandList = new ArrayList<>();
		String quit = "";
		String splitYesNo = "";
		int choice = 0;
		int bet;
		int decks = 1;
		int minBet = 5;
		boolean split;
		
		
		
		
		System.out.println("Welcome to Blackjack,");
		System.out.print("What is your name? ");
		String playerName = sc.nextLine();
		System.out.println();
		while(true) {
			try {
				System.out.print("How many decks do you want to play with?");
				decks = sc.nextInt();
				if(decks<1) {
					System.err.println("Need at least 1 deck to play");
					continue;
				}
				System.out.print("How difficult do you want to make this? set the minimum bet (typically it is $5, $10, or $15): ");
				minBet = sc.nextInt();
				
				if(minBet<1 || minBet>100) {
					System.err.println("Needs to be between $1 and $100");
					continue;
				}
				break;
			}
			catch(Exception e) {
				System.err.println("Enter a valid number");
				sc.hasNextLine();
				continue;
			}
		
		}
		
		for (int i = 1; i < decks; i++) {
			Deck deck2 = new Deck();
			deck.addToDeck(deck2.getDeck());
			backUpDeck.addToDeck(deck2.getDeck());
		}
		Player player1 = new Player(playerName);
		
		System.out.println("Hello " + playerName+" Let's get started.");
		
		playerList.add(dealer);
		playerList.add(player1);
		
		deck.shuffle();
		do {
			quit = "";
			bet = 0;
			
			if(deck.checkDeckSize() < 20) {
				deck.getDeck().removeAll(deck.getDeck());
				deck.getDeck().addAll(backUpDeck.getDeck());
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
				System.out.println("You have: $"+ player1.getMoney());
				System.out.print("Minimum bet is: " + minBet+ ", how lucky are you feeling? ");
				bet = sc.nextInt();
				player1.getHand().setBet(player1.bet(bet));
				}
				catch (Exception e) {
					System.err.println("Not a valid number, try again");
				}
			if(bet< minBet) {
				player1.tie(bet);
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
				
				System.out.print("BlackJack! Dealer does not have Blackjack so, You win! pays 3 to 1.");
				player1.winMoney((int)((bet * 1.5)));
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
			//TODO:remove after testing
			Card ace = new Card(Suit.CLUBS, Rank.ACE);
			Card ace2 = new Card(Suit.DIAMONDS, Rank.ACE);
			player1.getHand().getCards().set(0, ace);
			player1.getHand().getCards().set(1, ace2);
		for (int j = 0; j < player1.getHandList().size(); j++) {
			BlackjackHand playerHand = player1.getHandList().get(j);
			do {
					split = canSplit(player1);
					
					if (split) {
						System.out.print("Do you want to split? Will double your bet(Y/N): ");
						splitYesNo = sc.next();
					}
					if (splitYesNo.equalsIgnoreCase("Y")) {
						
						if(containsAce(playerHand)) {
							ifContainsAce1ReplaceWithace(player1);
						}
						
						playerHandList = (player1.splitHand(player1.getHandList().get(j)));
						playerHand.addCard(deck.dealcard());
						playerHandList.get(j+1).addCard(deck.dealcard());
						playerHandList.get(j+1).setBet(player1.bet(bet));
						
						System.out.println(player1);
						System.out.println("Lets make decisions on your first hand then we will go through your second.");
						System.out.println();
						splitYesNo = "";
					}
				try {
				System.out.println(playerHand);
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
					String doubleDown = "";
					
					if(player1.getMoney() >= 2*bet && !doubleDown.equalsIgnoreCase("N")) {
						System.out.print("Double down? Will double your bet. (Y/N): ");
						doubleDown = sc.next();
					}
					
					if(doubleDown.equalsIgnoreCase("Y")) {
						playerHand.setBet(bet + player1.bet(bet));
					}
					
					card = deck.dealcard();
					System.out.println("you got " +card);
					System.out.println();
					Hit(player1, card);
					System.out.println();
					
					
					if (player1.getPlayerHandValue() > 21) {
						
						
						ifContainsAceReplaceWithace1(player1);
						
						if (player1.getPlayerHandValue() > 21) {
						
						System.out.println("You busted, dealer wins");
							player1.loseMoney(playerHand.getBet());
						
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
						doubleDown = "";
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
			
		}
		
			for(int j = 0; j < player1.getHandList().size(); j++) {
				BlackjackHand playerHand = player1.getHandList().get(j);
				
				if(choice == 2) {
					System.out.println(dealer);
					System.out.println("Your hand: "+playerHand);
					System.out.println();
				
				
					if (containsAce(dealer.getHand()) && dealer.getPlayerHandValue() >= 17) {
						ifContainsAceReplaceWithace1(dealer);
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
						
						player1.winMoney(playerHand.getBet());
						
						System.out.print("Leave the table? (Y/N) ");
						quit = sc.next();
					}
					
					else if (dealer.getPlayerHandValue() > player1.getPlayerHandValue()) {
							System.out.print("Dealer won.");
							
							player1.loseMoney(playerHand.getBet());
								
							System.out.println("leave the table? (Y/N) ");
							quit = sc.next();
					}
					else if(dealer.getPlayerHandValue() == player1.getPlayerHandValue()) {
							System.out.println("You push.");
							
							player1.tie(playerHand.getBet());
												
							System.out.println("leave the Table? (Y/N) ");
							quit = sc.next();
					}
					else if(dealer.getPlayerHandValue() < player1.getPlayerHandValue()) {
							System.out.print("You won!");
						
							player1.winMoney(playerHand.getBet());
							
	
							System.out.println("leave the table? (Y/N) ");
							quit = sc.next();
					}
				
				}
			
				if(choice == 3) {
					quit = "Y";
				}
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
	public void ifContainsAce1ReplaceWithace(Player player1) {
		
		List<Card> originalAceList = getOriginalAceList();
		List<Card> playerHand = player1.getHand().getCards();
		
		for (int i = 0 ; i < playerHand.size(); i++) {
			for (Card ace : originalAceList) {
				if (playerHand.get(i).equals(ace) && playerHand.get(i).getValue() == 1) {
					playerHand.set(i, ace);
					System.out.println("Soft ace reversed since you split: " +player1);
					System.out.println();
				}
				
			}
		}
	}
	public void ifContainsAceReplaceWithace1(Player player1) {
	
		List<Card> aceList = getAceList();
		
		List<Card> playerHand = player1.getHand().getCards();
		
		for (int i = 0 ; i < playerHand.size(); i++) {
			for (Card ace : aceList) {
				if (playerHand.get(i).equals(ace) && playerHand.get(i).getValue() == 11) {
					playerHand.set(i, ace);
					System.out.println("Soft ace take effect: " +player1);
					System.out.println();
				}
				
			}
		}
		
	}
	
	public boolean containsAce(BlackjackHand hand) {
		List<Card> aceList = getAceList();

		
		List<Card> playerHand = hand.getCards();
		
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
	public List<Card> getOriginalAceList(){
		
		List<Card> aceList = new ArrayList<>();
		Card ace1 = new Card(Suit.CLUBS, Rank.ACE);
		Card ace2 = new Card(Suit.DIAMONDS, Rank.ACE);
		Card ace3 = new Card(Suit.HEARTS, Rank.ACE);
		Card ace4 = new Card(Suit.SPADES, Rank.ACE);
		
		aceList.add(ace4);
		aceList.add(ace3);
		aceList.add(ace2);
		aceList.add(ace1);
		
		return aceList;
	}
	public boolean canSplit(Player player) {
		
		for (BlackjackHand hand : player.getHandList()) {
			
			Rank rank1 = hand.getCards().get(0).getRank();
			Rank rank2 = hand.getCards().get(1).getRank();
			
			if(rank1.equals(rank2)) {
				return true;
			}
		}
		return false;
		
	}
	
	


}

