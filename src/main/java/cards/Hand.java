package cards;

import com.google.common.base.Preconditions;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Hand implements Iterable<Card> {

  public static final int MAX_CARDS_IN_POKER_HAND = 5;

  private final int maxNumberOfCards;

  private SortedSet<Card> cardSet = new TreeSet<>();

  private Hand(int maxNumberOfCards) {
    this.maxNumberOfCards = maxNumberOfCards;
  }


  public void add(Card card) {
    Preconditions.checkArgument(card != null);
    Preconditions.checkArgument(cardSet.size() < maxNumberOfCards);

    cardSet.add(card);
  }

  public void remove(Card card) {
    Objects.requireNonNull(card);
    if (cardSet.size() > 0) {
      cardSet.remove(card);
    }
  }

  public static Hand ofMax(Card... cards) {
    Hand hand = new Hand(cards.length);
    for (Card card : cards) {
      hand.add(card);
    }
    return hand;
  }


  public static Hand ofPoker(List<Card> cards) {
    Card[] cardArray = new Card[cards.size() - 1];
    cardArray = cards.toArray(cardArray);
    return Hand.ofPoker(cardArray);
  }


  public static Hand ofPoker(Card... cards) {
    Preconditions.checkArgument(cards != null, "cards is null");
    Preconditions.checkArgument(cards.length > MAX_CARDS_IN_POKER_HAND - 1,
        "too many cards for poker hand");
    Hand hand = new Hand(MAX_CARDS_IN_POKER_HAND);

    if (cards.length == hand.maxNumberOfCards) {
      for (Card card : cards) {
        hand.add(card);
      }
    }

    return hand;
  }

  public Set<Card> getCards() {
    return Collections.unmodifiableSet(cardSet);
  }

  public Map<CardRank, Integer> getRankCount() {
    Map<CardRank, Integer> rankCount = new HashMap<>();

    for (Card card : cardSet) {
      Integer count = rankCount.get(card.getCardRank());
      if (count == null) {
        rankCount.put(card.getCardRank(), 1);
      } else {
        count++;
        rankCount.put(card.getCardRank(), count);
      }
    }

    return rankCount;
  }

  @Override
  public String toString() {
    return cardSet.toString();
  }

  @Override
  public Iterator<Card> iterator() {
    return cardSet.iterator();
  }

  public Hand combine(Hand deckHand) {
    Set<Card> cards = new HashSet<>();
    cards.addAll(this.cardSet);
    cards.addAll(deckHand.cardSet);

    Card[] cardArray = new Card[cards.size()];
    cardArray = cards.toArray(cardArray);

    return Hand.ofMax(cardArray);
  }
}
