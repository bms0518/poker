package cards;

import java.util.Collections;
import java.util.Stack;

public class Deck {

    public static final int STANDARD_SIZE = 52;

    private final Stack<Card> cards = new Stack<>();

    private Deck() {
        for (Suit suit : Suit.values()) {
            for (CardRank cardValue : CardRank.values()) {
                cards.push(new Card(cardValue, suit));
            }
        }
    }


    public static Deck standard() {
        return new Deck();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card pop() {
        Card c = cards.pop();
        return c;
    }

    public int size() {
        return cards.size();
    }


}
