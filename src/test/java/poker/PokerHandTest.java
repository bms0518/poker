package poker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Function;

import org.junit.Test;

import cards.Card;
import cards.CardRank;
import cards.Hand;
import cards.Suit;

public class PokerHandTest {

  @Test
  public void testHighCard() {
    assertTrue(PokerHands.HIGH_CARD.getHandEvaluator().apply(getHighCardHand()));
  }

  @Test
  public void testPair() {
    testBadAndGood(PokerHands.PAIR.getHandEvaluator(), getHighCardHand(), getPairHand());
  }

  @Test
  public void testTwoPair() {
    testBadAndGood(PokerHands.TWO_PAIR.getHandEvaluator(), getHighCardHand(), getTwoPairHand());
  }

  @Test
  public void testThreeOfAKind() {
    testBadAndGood(PokerHands.THREE_OF_A_KIND.getHandEvaluator(), getHighCardHand(),
        getThreeOfAKindHand());

  }

  @Test
  public void testBadStraight() {
    assertFalse(PokerHands.STRAIGHT.getHandEvaluator().apply(getHighCardHand()));
  }

  @Test
  public void testLowStraight() {
    assertTrue(PokerHands.STRAIGHT.getHandEvaluator().apply(getLowStraightHand()));
  }

  @Test
  public void testLowStraightDoubleAce() {
    assertFalse(PokerHands.STRAIGHT.getHandEvaluator().apply(getLowStraightHandDoubleAce()));
  }

  @Test
  public void testNormalStraight() {
    assertTrue(PokerHands.STRAIGHT.getHandEvaluator().apply(getStraightHand()));
  }

  @Test
  public void testHighStraight() {
    assertTrue(PokerHands.STRAIGHT.getHandEvaluator().apply(getHighStraightHand()));
  }

  @Test
  public void testFlush() {
    testBadAndGood(PokerHands.FLUSH.getHandEvaluator(), getHighCardHand(), getFlushHand());
  }

  @Test
  public void testFullHouse() {
    testBadAndGood(PokerHands.FULL_HOUSE.getHandEvaluator(), getHighCardHand(), getFullHouseHand());
  }

  @Test
  public void testFourOfaKind() {
    testBadAndGood(PokerHands.FOUR_OF_A_KIND.getHandEvaluator(), getHighCardHand(),
        getFourOfaKindHand());
  }

  @Test
  public void testStraightFlush() {
    testBadAndGood(PokerHands.STRAIGHT_FLUSH.getHandEvaluator(), getHighCardHand(),
        getStraightFlushHand());
  }

  @Test
  public void testHighCardHandEvaluator() {
    handEvaluatorTest(getHighCardHand(),PokerHands.HIGH_CARD);
    handEvaluatorTest(getPairHand(),PokerHands.PAIR);
    handEvaluatorTest(getTwoPairHand(),PokerHands.TWO_PAIR);
    handEvaluatorTest(getThreeOfAKindHand(),PokerHands.THREE_OF_A_KIND);
    handEvaluatorTest(getStraightHand(),PokerHands.STRAIGHT);
    handEvaluatorTest(getHighStraightHand(),PokerHands.STRAIGHT);
    handEvaluatorTest(getLowStraightHand(),PokerHands.STRAIGHT);
    handEvaluatorTest(getFlushHand(),PokerHands.FLUSH);
    handEvaluatorTest(getFullHouseHand(),PokerHands.FULL_HOUSE);
    handEvaluatorTest(getFourOfaKindHand(),PokerHands.FOUR_OF_A_KIND);
    handEvaluatorTest(getStraightFlushHand(),PokerHands.STRAIGHT_FLUSH);

  }

  private void handEvaluatorTest(Hand hand, PokerHands expectedType){
    PokerHands handType = PokerHands.evaluate(hand);
    assertEquals(expectedType, handType);
  }


  private Hand getFullHouseHand() {
    return Hand.ofPoker(Card.of(CardRank.TWO, Suit.DIAMOND), Card.of(CardRank.TWO, Suit.CLUB),
        Card.of(CardRank.TWO, Suit.SPADE), Card.of(CardRank.FOUR, Suit.DIAMOND),
        Card.of(CardRank.FOUR, Suit.HEART));
  }

  private Hand getFourOfaKindHand() {
    return Hand.ofPoker(Card.of(CardRank.TWO, Suit.DIAMOND), Card.of(CardRank.TWO, Suit.HEART),
        Card.of(CardRank.TWO, Suit.SPADE), Card.of(CardRank.TWO, Suit.CLUB),
        Card.of(CardRank.SIX, Suit.DIAMOND));
  }

  private Hand getStraightFlushHand() {
    return Hand.ofPoker(Card.of(CardRank.TWO, Suit.DIAMOND), Card.of(CardRank.THREE, Suit.DIAMOND),
        Card.of(CardRank.FOUR, Suit.DIAMOND), Card.of(CardRank.FIVE, Suit.DIAMOND),
        Card.of(CardRank.SIX, Suit.DIAMOND));
  }

  private Hand getFlushHand() {
    return Hand.ofPoker(Card.of(CardRank.ACE, Suit.DIAMOND), Card.of(CardRank.TWO, Suit.DIAMOND),
        Card.of(CardRank.THREE, Suit.DIAMOND), Card.of(CardRank.FOUR, Suit.DIAMOND),
        Card.of(CardRank.SIX, Suit.DIAMOND));
  }

  private void testBadAndGood(Function<Hand, Boolean> func, Hand handThatShouldFail,
      Hand handThatShouldPass) {
    assertFalse(func.apply(handThatShouldFail));
    assertTrue(func.apply(handThatShouldPass));
  }

  private Hand getLowStraightHand() {
    return Hand.ofPoker(Card.of(CardRank.ACE, Suit.DIAMOND), Card.of(CardRank.TWO, Suit.CLUB),
        Card.of(CardRank.THREE, Suit.CLUB), Card.of(CardRank.FOUR, Suit.DIAMOND),
        Card.of(CardRank.FIVE, Suit.CLUB));
  }

  private Hand getLowStraightHandDoubleAce() {
    return Hand.ofPoker(Card.of(CardRank.ACE, Suit.DIAMOND), Card.of(CardRank.TWO, Suit.CLUB),
        Card.of(CardRank.THREE, Suit.CLUB), Card.of(CardRank.FOUR, Suit.DIAMOND),
        Card.of(CardRank.ACE, Suit.CLUB));
  }

  private Hand getStraightHand() {
    return Hand.ofPoker(Card.of(CardRank.TWO, Suit.CLUB), Card.of(CardRank.THREE, Suit.CLUB),
        Card.of(CardRank.FOUR, Suit.DIAMOND), Card.of(CardRank.FIVE, Suit.CLUB),
        Card.of(CardRank.SIX, Suit.CLUB));
  }

  private Hand getHighStraightHand() {
    return Hand.ofPoker(Card.of(CardRank.TEN, Suit.DIAMOND), Card.of(CardRank.JACK, Suit.CLUB),
        Card.of(CardRank.QUEEN, Suit.CLUB), Card.of(CardRank.KING, Suit.DIAMOND),
        Card.of(CardRank.ACE, Suit.CLUB));
  }

  private Hand getHighCardHand() {
    return Hand.ofPoker(Card.of(CardRank.ACE, Suit.DIAMOND), Card.of(CardRank.KING, Suit.CLUB),
        Card.of(CardRank.QUEEN, Suit.CLUB), Card.of(CardRank.JACK, Suit.CLUB),
        Card.of(CardRank.NINE, Suit.CLUB));
  }

  private Hand getPairHand() {
    return Hand.ofPoker(Card.of(CardRank.ACE, Suit.DIAMOND), Card.of(CardRank.ACE, Suit.CLUB),
        Card.of(CardRank.QUEEN, Suit.CLUB), Card.of(CardRank.JACK, Suit.CLUB),
        Card.of(CardRank.NINE, Suit.CLUB));
  }

  private Hand getTwoPairHand() {
    return Hand.ofPoker(Card.of(CardRank.ACE, Suit.DIAMOND), Card.of(CardRank.ACE, Suit.CLUB),
        Card.of(CardRank.QUEEN, Suit.CLUB), Card.of(CardRank.QUEEN, Suit.DIAMOND),
        Card.of(CardRank.NINE, Suit.CLUB));
  }

  private Hand getThreeOfAKindHand() {
    return Hand.ofPoker(Card.of(CardRank.ACE, Suit.DIAMOND), Card.of(CardRank.ACE, Suit.CLUB),
        Card.of(CardRank.ACE, Suit.HEART), Card.of(CardRank.JACK, Suit.CLUB),
        Card.of(CardRank.NINE, Suit.CLUB));
  }

}
