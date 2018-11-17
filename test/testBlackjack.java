import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.skilldistillery.cards.Card;
import com.skilldistillery.cards.Deck;
import com.skilldistillery.cards.blackjack.BlackjackHand;

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

}
