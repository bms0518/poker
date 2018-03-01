package cards;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

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


  public List<Card> getCards() {
    return new ArrayList<>(cards);
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

  public List<Card> pop(int number) {
    Preconditions.checkArgument(cards.size() >= number);

    List<Card> poppedCards = new ArrayList<>();
    IntStream.range(0, number).forEach(num -> {
      poppedCards.add(pop());
    });
    return poppedCards;
  }

  public int size() {
    return cards.size();
  }


}
