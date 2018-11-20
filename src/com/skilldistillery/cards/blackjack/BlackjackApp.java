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
		
		List<Player> computersPlayers = new ArrayList<>();
		computersPlayers.add(new Player("Harry P"));
		computersPlayers.add(new Player("Snape"));
		computersPlayers.add(new Player("Draco"));
		computersPlayers.add(new Player("Dumbledore"));
		Dealer dealer = new Dealer("Bob the Dealer");
		Deck deck = new Deck();
		Deck backUpDeck = new Deck();
		Card card;
		List<Player> playerList = new ArrayList<>();
		List<Player> computerList = new ArrayList<>();
		List<BlackjackHand> playerHandList = new ArrayList<>();
		String quit = "";
		String splitYesNo = "";
		int choice = 0;
		int bet;
		int decks = 1;
		int minBet = 5;
		int numPlayers = 0;
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
				while(true) {
					System.out.println("How man other players do you want? (0-4)");
					numPlayers = sc.nextInt();
					if (numPlayers < 0 || numPlayers > 4) {
						System.err.println("not a valid selction");
						continue;
					}
					break;
				}
				break;
			}
			catch(Exception e) {
				System.err.println("Enter a valid number");
				sc.hasNextLine();
				numPlayers = 0;
				minBet =5;
				decks = 2;
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
		
		for (int i = 0; i < numPlayers; i++) {
			computerList.add(computersPlayers.get(i));
		}
		
		
		deck.shuffle();
		do {
			quit = "";
			bet = 0;
			choice = 0;
			
			if(deck.checkDeckSize() < 30) {
				deck.getDeck().removeAll(deck.getDeck());
				deck.getDeck().addAll(backUpDeck.getDeck());
				deck.shuffle();
				System.out.println("The Deck has been re-shuffled");
				System.out.println();
			}
			for (int i = 0 ; i < computerList.size(); i++) {
				if (computerList.get(i).getMoney() < minBet) {
					System.out.println(computerList.get(i).getName() + " is out of money and has left");
					computerList.remove(i);
				}
					
			}
			
			
			clearAllCards(playerList, computerList);
			
			
			if(player1.getMoney() < minBet) {
				System.out.println("You don't have enough to play, better luck next time. goodbye!");
				System.exit(0);
			}
			
			
			do {
			try {
				System.out.println("You have: $"+ player1.getMoney());
				System.out.print("Minimum bet is: " + minBet+ ", how lucky are you feeling? ");
				bet = sc.nextInt();
				}
				catch (Exception e) {
					bet = 0;
					System.err.println("Not a valid number, try again");
				}
				if(bet< minBet) {
					System.err.println("You must meet the min bet.");
					bet = 0;
				}
				if(bet> player1.getMoney()) {
					System.err.println("Try Again");
					bet = 0;
				}
				player1.getHand().setBet(player1.bet(bet));
			
			}while(bet < minBet);
			
			for (Player computer : computerList) {
				computer.setBet(computer.bet(minBet + 5));
				
			}
			
			dealAllCards(playerList, computerList, deck);
			System.out.println(dealer.getName() + " deals out 2 cards to everyone ");
			System.out.println();
			
			
			dealer.dealerFirstHand();
			System.out.println();
//			System.out.println(player1);
//			System.out.println();
			//This may be excessive 
			
			ifContains2AcesChangeOne(player1);
			ifContains2AcesChangeOne(dealer);
			for (Player computer : computerList) {
				ifContains2AcesChangeOne(computer);
				
				if(computer.getPlayerHandValue() == 21 && dealer.getPlayerHandValue() !=21) {
					System.out.println(computer.getName() + " Has BlackJack");
					computer.winMoney((int)((computer.getBet() * 1.5)));
					computer.setBet(0); // setting the bet to zero here because of winning here
										//later on when the computer rolls it will say they won but will
										// get no additional money.
				}
				if(computer.getPlayerHandValue() == 21 && dealer.getPlayerHandValue() ==21) {
					
					System.out.println(computer.getName() + " pushes");
					computer.tie(computer.getBet());
					
					//don't need an if for the computer getting 21, player has already lost bet and doesn't need a message.
				}

			}
			
			
			
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
				player1.setBet(0);
				choice = 3;
				break;
			}
			
			if(dealer.showingCard().getValue() == 10 || dealer.showingCard().getValue() == 11) {
				
				System.out.println("Dealer will check if they have blackjack...");
				
				quit = doesDealerHaveBlackjack(playerList);
			
				if(quit.equalsIgnoreCase("N")) {
						continue;
				}
				
				System.out.println("Dealer does not have blackjack.");
				System.out.println();
			
			}
//			//TODO:remove after testing
//			Card ace = new Card(Suit.CLUBS, Rank.ACE);
//			Card ace2 = new Card(Suit.DIAMONDS, Rank.ACE);
//			player1.getHand().getCards().set(0, ace);
//			player1.getHand().getCards().set(1, ace2);
			
			if (player1.getBet() <= 0 ) {
				player1.getHandList().clear();
			}
		for (int j = 0; j < player1.getHandList().size(); j++) {
			BlackjackHand playerHand = player1.getHandList().get(j);
			do {
					split = canSplit(playerHand);
					splitYesNo = "";
					if (split) {
						System.out.print("Do you want to split? Will double your bet(Y/N): ");
						splitYesNo = sc.next();
					}
					if (splitYesNo.equalsIgnoreCase("Y")) {
						
						ifContainsAce1ReplaceWithace(playerHand);
						
						
						playerHandList = (player1.splitHand(player1.getHandList().get(j)));
						playerHand.addCard(deck.dealcard());
						playerHandList.get(j+1).addCard(deck.dealcard());
						playerHandList.get(j+1).setBet(player1.bet(bet));
						//j+1 only occurs if it enters the if statement and when it does the arrayList
						//gets bigger by one because of the splithand method. I want to deal out the second card
						//immediately to simulate the Casino table. If the player does not split they dont enter this 
						//statement
						
						
						System.out.println(player1);
						System.out.println("Lets make decisions on your first hand then we will go through your second.");
						System.out.println();
						splitYesNo = "";
					}
				try {
				System.out.println(player1.getName() + playerHand);
				System.out.println();

					mainMenu();
				
	
					choice = sc.nextInt();
					System.out.println();

				}
				catch (InputMismatchException e){
					System.err.println("Thats not a valid selection, try again");
					System.out.println();
					choice = 0;
					sc.nextLine();
				}
				
			}while (choice <= 0 || choice > 3);
			
			String doubleDown = "";
			do {
				if(choice == 1) {
					
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
					Hit(playerHand, card);
					System.out.println();
					
					
					if (playerHand.getHandvalue() > 21) {
						
						
						ifContainsAceReplaceWithace1(playerHand);
						
						if (playerHand.getHandvalue() > 21) {
						
						System.out.println("You busted, dealer wins");
							player1.loseMoney(playerHand.getBet());
							playerHand.setBet(0);
						
						System.out.print("Leave the table? (Y/N) ");
						quit = sc.next();
						}
						
						if(quit.equalsIgnoreCase("Y")) {
							choice = 3;
							break;
						}
						else if (quit.equalsIgnoreCase("N")){
							choice = 2;
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
							choice = 0;	
							sc.nextLine();
						}
					}while (choice <= 0 || choice > 3);
					
				}
			}while (choice == 1);
			
		}
		
		computerPlayers(computerList, deck);
		
		List<Player> computersLeft = new ArrayList<>();
		for (int i = 0; i < computerList.size(); i++) {
			if (computerList.get(i).getBet() > 0) {
				computersLeft.add(computerList.get(i));
			}
			
		}
		
			for(int j = 0; j < player1.getHandList().size(); j++) {
				BlackjackHand playerHand = player1.getHandList().get(j);
				
				if(choice == 2) {
					
					System.out.println();
					System.out.println("The dealer flips the other card over.");
					System.out.println(dealer);
					System.out.println();
//					System.out.println("Your hand: "+playerHand);
//					System.out.println();
					//not sure if I like this here
				
				
					if (containsAce(dealer.getHand()) && dealer.getPlayerHandValue() >= 17) {
						ifContainsAceReplaceWithace1(dealer.getHand());
						while (dealer.getPlayerHandValue() < 17) {
							System.out.println(dealer.getName() + " takes another card.");
							Hit(dealer.getHand(), deck.dealcard());
							}
					}
						
					
					if (dealer.getPlayerHandValue() < 17) {
						while (dealer.getPlayerHandValue() < 17) {
							System.out.println(dealer.getName() + " takes another card.");
							Hit(dealer.getHand(), deck.dealcard());
							
							}
					}
					for (int i = 0; i < computersLeft.size(); i++) {
						
						Player computer = computersLeft.get(i);
						
						if(dealer.getPlayerHandValue()> 21 ) {
							computer.winMoney(computer.getBet());
						}
						//keep the else here
						else if (dealer.getPlayerHandValue() > computer.getPlayerHandValue()) {
							System.out.println(computer.getName() + " lost");
						}
						else if(dealer.getPlayerHandValue() == computer.getPlayerHandValue()) {
							computer.tie(computer.getBet());
						}
						else if(dealer.getPlayerHandValue() < computer.getPlayerHandValue()) {
							computer.winMoney(computer.getBet());
						}
						
					}
					
					if (dealer.getPlayerHandValue() > 21) {
						System.out.println("Dealer busted");
						
						if(player1.getBet()> 0) { //doing this so the table can finish but player lost a while ago
							player1.winMoney(playerHand.getBet());
						}
						
						System.out.print("Leave the table? (Y/N) ");
						quit = sc.next();
					}
					
					else if (dealer.getPlayerHandValue() > player1.getPlayerHandValue()) {
							System.out.print("Dealer won.");
							
							if(player1.getBet()> 0) { //doing this so the table can finish but player lost a while ago

								player1.loseMoney(playerHand.getBet());
							}	
							System.out.println("leave the table? (Y/N) ");
							quit = sc.next();
					}
					else if(dealer.getPlayerHandValue() == player1.getPlayerHandValue()) {
						
							if(player1.getBet()> 0) { //doing this so the table can finish but player lost a while ago

								player1.tie(playerHand.getBet());
							}					
							System.out.println("leave the Table? (Y/N) ");
							quit = sc.next();
					}
					else if(dealer.getPlayerHandValue() < player1.getPlayerHandValue()) {
							
							if(player1.getBet()> 0) { //doing this so the table can finish but player lost a while ago

								player1.winMoney(playerHand.getBet());
							}
	
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
		
	private String doesDealerHaveBlackjack(List<Player> playerList) {
		String quit = "";
		Player dealer = playerList.get(0);
		Player player1 = playerList.get(1);
		
		
		
		if(dealer.getPlayerHandValue() == 21) {
				System.out.println("Dealer has blackjack and you don't. So you lose.");
				player1.loseMoney(player1.getBet());
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
	public void Hit(BlackjackHand playerHand, Card card){
		playerHand.addCard(card);
		System.out.println(playerHand);
		
	}
	public void dealAllCards(List<Player> playerList, List<Player> computerList, Deck deck){
		for (int i = 0; i < 2; i++) {
			
			for (Player player : playerList) {
				player.addCardPlayerHand(deck.dealcard());
			}
			
			for (Player computer : computerList) {
				computer.addCardPlayerHand(deck.dealcard());
			}
		}
	
	}
	public void clearAllCards(List<Player> playerList, List<Player> computerList){
		
		for (Player player : playerList) {
			
			//if this is buggy, nest the if statements.
			if (player.getPlayerHandValue() != 0) {
			player.clearPlayerHand();
			
			}
			if(player.getHandList().size()>0) {
				player.clearHandList();
			}
		}
		for (Player player : computerList) {
			if (player.getPlayerHandValue() != 0) {
				player.clearPlayerHand();
				
			}
		
			if(player.getHandList().size()>0) {
				player.clearHandList();
			}
		}
			
	}
	public void ifContainsAce1ReplaceWithace(BlackjackHand hand) {
		
		List<Card> originalAceList = getOriginalAceList();
		List<Card> playerHand = hand.getCards();
		
		for (int i = 0 ; i < playerHand.size(); i++) {
			for (Card ace : originalAceList) {
				if (playerHand.get(i).equals(ace) && playerHand.get(i).getValue() == 1) {
					playerHand.set(i, ace);
					System.out.println("Soft ace reversed since you split: " + hand);
					System.out.println();
				}
				
			}
		}
	}
	public void ifContainsAceReplaceWithace1(BlackjackHand playerHand) {
	
		List<Card> aceList = getAceList();
		
		List<Card> Hand = playerHand.getCards();
		
		for (int i = 0 ; i < Hand.size(); i++) {
			for (Card ace : aceList) {
				if (playerHand.getHandvalue()> 21) {//this if statement is so needed!!!!!!
					
					if (Hand.get(i).equals(ace) && Hand.get(i).getValue() == 11) {
						Hand.set(i, ace);
						System.out.println("Soft ace take effect: " +playerHand);
						System.out.println();
					}
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
		//this passes the player and not the hand because
		//the hand iteration doesn't start until later and this is when they get their
		//first hand of the game before any other logic happens
		
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
			
		if (!(player instanceof Dealer)) {
			//added this if statement because if the dealer had 2 aces to start
			//the aces needs to happen still but the player can't know the cards
			System.out.println("Since there are 2 aces one of them is now worth 1");
			System.out.println(player);
		}
		}
				
			
		
			
	}
	public List<Card> getAceList(){
		//These are the aces that have a value of one
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
		//this is a list of aces with a value of 11
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
	public boolean canSplit(BlackjackHand hand) {
		List<Card> aceList = getAceList();
		List<Card> numAce = new ArrayList<>();
			Rank rank1 = hand.getCards().get(0).getRank();
			Rank rank2 = hand.getCards().get(1).getRank();
			
			if(rank1.equals(rank2)) {
				return true;
				
			}
			
			//needed the rest of this for the Aces since they are special 
			//later on if one ace is valued at one it will be reversed to 11 
			for (int i = 0 ; i < hand.getCards().size(); i++) {
				for (Card ace : aceList) {
					if (hand.getCards().get(i).equals(ace)) {
						numAce.add(ace);
					}
				}
			}
			if(numAce.size() == hand.getCards().size()) {
				return true;
			}
		
		
		return false;
		
	}
	public List<Player> computerPlayers (List<Player> computerList, Deck deck) {
				
		for (int i = 0; i <computerList.size(); i++) {
			boolean split;
			String splitYesNo = "";
			Card card;
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			if ( computerList.get(i).getPlayerHandValue() == 21) {
				computerList.get(i).setBet(0);
				break;
			}
			
			List<BlackjackHand> playerHandList = new ArrayList<>();

			for (int j = 0; j < computerList.get(i).getHandList().size(); j++) {
				int choice = 0;
				BlackjackHand playerHand = computerList.get(i).getHandList().get(j);
			
				Player computer = computerList.get(i);
				split = false;
				if(computer.getMoney() > computer.getBet()) {
					split = canSplit(computerList.get(i).getHand());
				}
					if (split) {
						if(playerHand.getCards().get(0).getRank().getValue() > 7) {
							splitYesNo = "Y";
						}
						else {
							splitYesNo = "N";
						}
					}					
					if (splitYesNo.equalsIgnoreCase("Y")) {
						
						ifContainsAce1ReplaceWithace(computer.getHand());
						
						playerHandList = (computer.splitHand(computer.getHandList().get(j)));
						playerHand.addCard(deck.dealcard());
						playerHandList.get(j+1).addCard(deck.dealcard());							
						playerHandList.get(j+1).setBet(computer.bet(computer.getBet()));
						//this is the same logic in the player version. see line 260
						
						System.out.println(computer.getName() + " Split there hand");
						splitYesNo = "no";
							
					}
					
				System.out.println(computer.getName() +" "+playerHand);
						
				if(playerHand.getHandvalue()< 17)	{
					choice = 1;
				}
				
					
				
					String doubleDown = "";
					if(choice == 1) {
						
						if(computer.getMoney() >= (playerHand.getBet()) && !doubleDown.equalsIgnoreCase("N")) {
							if(playerHand.getHandvalue() > 7 && playerHand.getHandvalue() < 11) {
								doubleDown = "Y";
							}
							else {
								doubleDown = "N";
							}
						}
						
						if(doubleDown.equalsIgnoreCase("Y")) {
							System.out.println(computer.getName() + " doubled down");
							playerHand.setBet(playerHand.getBet() + computer.bet(playerHand.getBet()));
						}
						
						card = deck.dealcard();
						System.out.println(computer.getName() + " takes another card and got " +card + " and now has: ");
						System.out.println();
						Hit(playerHand, card);
						System.out.println();
						
						
						if (playerHand.getHandvalue() > 21) {
							
							
							ifContainsAceReplaceWithace1(playerHand);
							
							if (playerHand.getHandvalue() > 21) {
							
								System.out.println(computer.getName() +" busted.");
								System.out.println(computer.getName() + " lost $" + playerHand.getBet());
								System.out.println();
								playerHand.setBet(0);
							}
						
						}
							
					}
						
			}
		}
		return computerList;
		
	}
	


}

