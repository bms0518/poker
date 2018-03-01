package poker;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cards.Card;
import cards.CardRank;
import cards.Hand;
import cards.Suit;

public enum PokerHands {

  HIGH_CARD(PokerHands::highCardEvaluator),
  PAIR(PokerHands::pairEvaluator),
  TWO_PAIR(PokerHands::twoPairEvaluator),
  THREE_OF_A_KIND(PokerHands::threeOfAKindEvaluator),
  STRAIGHT(PokerHands::straightEvaluator),
  FLUSH(PokerHands::flushEvaluator),
  FULL_HOUSE(PokerHands::fullHouseEvaluator),
  FOUR_OF_A_KIND(PokerHands::fourOfAKindEvaluator),
  STRAIGHT_FLUSH(PokerHands::straightFlushEvaluator);

  private final Function<Hand, Boolean> handEvaluator;

  private static final Logger LOG = LoggerFactory.getLogger(PokerHands.class);

  PokerHands(Function<Hand, Boolean> handEvaluator) {
    this.handEvaluator = handEvaluator;
  }

  /**
   * @return the handEvaluator
   */
  public Function<Hand, Boolean> getHandEvaluator() {
    return handEvaluator;
  }

  private static boolean highCardEvaluator(Hand hand) {
    return true;
  }

  private static boolean pairEvaluator(Hand hand) {
    LOG.debug("pairEvaluator(), hand = {}", hand);
    Map<CardRank, Integer> rankCount = hand.getRankCount();

    int count = 0;
    for (Integer countOfEach : rankCount.values()) {
      if (countOfEach > 1) {
        count++;
      }
    }

    return count == 1;
  }

  private static boolean twoPairEvaluator(Hand hand) {
    LOG.debug("twoPairEvaluator(), hand = {}", hand);
    Map<CardRank, Integer> rankCount = hand.getRankCount();

    int count = 0;
    for (Integer countOfEach : rankCount.values()) {
      if (countOfEach > 1) {
        count++;
      }
    }

    return count == 2;
  }

  private static boolean threeOfAKindEvaluator(Hand hand) {
    LOG.debug("threeOfAKindEvaluator(), hand = {}", hand);
    Map<CardRank, Integer> rankCount = hand.getRankCount();

    int count = 0;
    for (Integer countOfEach : rankCount.values()) {
      if (countOfEach > 2) {
        count++;
      }
    }

    return count == 1;
  }

  private static boolean straightEvaluator(Hand hand) {
    LOG.debug("straightEvaluator(), hand = {}", hand);
    boolean returnValue = false;

    Iterator<Card> cardIterator = hand.getCards().iterator();

    Card lowestCard = cardIterator.next();
    Card toCheck = lowestCard;
    while (cardIterator.hasNext()) {
      Card c = cardIterator.next();

      int toCheckValue = toCheck.getCardRank().ordinal();
      int cValue = c.getCardRank().ordinal();

      LOG.debug("toCheck = {}", toCheck);
      LOG.debug("lowest = {}", lowestCard);
      LOG.debug("c = {}", c);

      returnValue = (cValue == toCheckValue + 1);
      LOG.debug("Return Value = {}", returnValue);
      toCheck = c;

      if (isLowStraightPossible(c, lowestCard)) {
        returnValue = !cardIterator.hasNext();
      }

      if (!returnValue) {
        break;
      }
    }

    return returnValue;
  }

  private static boolean isLowStraightPossible(Card toCheck, Card lowest) {
    return toCheck.getCardRank().equals(CardRank.ACE) && lowest.getCardRank().equals(CardRank.TWO);
  }

  private static boolean flushEvaluator(Hand hand) {
    LOG.debug("flushEvaluator(), hand = {}", hand);
    boolean returnValue = true;

    Suit suit = null;
    for (Card card : hand.getCards()) {
      if (suit == null) {
        suit = card.getSuit();
      }

      if (suit != card.getSuit()) {
        returnValue = false;
        break;
      }
    }

    return returnValue;
  }

  private static boolean fullHouseEvaluator(Hand hand) {
    LOG.debug("fullHouseEvaluator(), hand = {}", hand);
    Map<CardRank, Integer> rankCount = hand.getRankCount();

    int pairCount = 0;
    int threeOfAKindCount = 0;
    for (Integer countOfEach : rankCount.values()) {

      if (countOfEach == 2) {
        pairCount++;
      }

      if (countOfEach == 3) {
        threeOfAKindCount++;
      }
    }

    return pairCount == 1 && threeOfAKindCount == 1;
  }

  private static boolean fourOfAKindEvaluator(Hand hand) {
    LOG.debug("fourOfAKindEvaluator(), hand = {}", hand);
    Map<CardRank, Integer> rankCount = hand.getRankCount();

    int count = 0;
    for (Integer countOfEach : rankCount.values()) {
      if (countOfEach > 3) {
        count++;
      }
    }

    return count == 1;
  }

  private static boolean straightFlushEvaluator(Hand hand) {
    LOG.debug("straightFlushEvaluator(), hand = {}", hand);
    return STRAIGHT.handEvaluator.apply(hand) && FLUSH.handEvaluator.apply(hand);
  }

  public static PokerHands evaluate(Hand hand) {

    int highestHand = -1;
    for (PokerHands hands : PokerHands.values()) {
      boolean val = hands.handEvaluator.apply(hand);
      if (val) {
        if (hands.ordinal() > highestHand) {
          highestHand = hands.ordinal();
        }
      }
    }

    return PokerHands.values()[highestHand];

  }

}
