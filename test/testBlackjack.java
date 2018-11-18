import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.skilldistillery.cards.Card;
import com.skilldistillery.cards.Deck;
import com.skilldistillery.cards.Rank;
import com.skilldistillery.cards.Suit;
import com.skilldistillery.cards.blackjack.BlackjackApp;
import com.skilldistillery.cards.blackjack.BlackjackHand;
import com.skilldistillery.cards.blackjack.Player;

class testBlackjack {
	
	private Deck deck;

	@BeforeEach
	void setUp() {
		deck = new Deck();
	}

	@AfterEach
	void tearDown(){
		deck = null;
	}

	@Test
	void test_if_get_hand_value_method_returns_correct_value() {
		BlackjackHand bjh = new BlackjackHand();
		Card card1 = deck.dealcard();
		Card card2 = deck.dealcard();
		Card card3 = deck.dealcard();
		Card card4 = deck.dealcard();
		
		int card1V = card1.getValue();
		int card2V = card2.getValue();
		int card3V = card3.getValue();
		int card4V = card4.getValue();
		
		bjh.addCard(card1);
		bjh.addCard(card2);
		bjh.addCard(card3);
		bjh.addCard(card4);
		
		int total = card1V+card2V+card3V+card4V;
		
		assertEquals(total, bjh.getHandvalue());
		
	}
	@Test
	void test_if_deck_has_52_cards_and_not_ace_value_1(){
		assertEquals(52, deck.checkDeckSize());
	}
	@Test
	void test_if_contains_ace_method_works() {
		List<Card> cardList = new ArrayList<>();
//		List<Card> aceList = new ArrayList<>();
		Card ace = new Card(Suit.HEARTS, Rank.ACE1);
		Card ace2 = new Card(Suit.SPADES, Rank.ACE1);
		
		//tried to check if I could pass a whole list
//		aceList.add(new Card(Suit.CLUBS, Rank.ACE));
//		aceList.add(new Card(Suit.DIAMONDS, Rank.ACE));
//		aceList.add(new Card(Suit.HEARTS, Rank.ACE));
//		aceList.add(new Card(Suit.SPADES, Rank.ACE));
		
		
		cardList.add(new Card(Suit.HEARTS, Rank.ACE));
		cardList.add(new Card(Suit.HEARTS, Rank.TEN));
		cardList.add(new Card(Suit.HEARTS, Rank.JACK));
		cardList.add(new Card(Suit.HEARTS, Rank.QUEEN));
		
		
		
		assertEquals(true, cardList.get(0).equals(ace));
		assertEquals(false, cardList.get(0).equals(ace2));
	}
	@Test
	void test_if_player_containing_two_aces_resets_only_one() {
		Card ace = new Card(Suit.HEARTS, Rank.ACE);
		Card ace2 = new Card(Suit.CLUBS, Rank.ACE);
		Player player = new Player("b");
		BlackjackApp app = new BlackjackApp();
		
		player.addCardPlayerHand(ace);
		player.addCardPlayerHand(ace2);
		
		app.ifContains2AcesChangeOne(player);
		
		assertEquals(12, player.getPlayerHandValue());
		
	}

}
