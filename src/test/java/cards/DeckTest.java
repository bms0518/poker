package cards;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DeckTest {
	@Test
	public void testStandardDeckSize() {

		Deck d = Deck.standard();
		assertEquals(Deck.STANDARD_SIZE, d.size());

		for (int i = 0; i < Deck.STANDARD_SIZE; i++) {
			Card c = d.pop();
			System.err.println(c);
			System.err.println(i);
		}
	}
}
