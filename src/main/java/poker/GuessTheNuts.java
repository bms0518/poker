package poker;

import cards.Card;
import cards.CardRank;
import cards.Deck;
import cards.Hand;
import cards.Suit;
import java.util.ArrayList;
import java.util.List;

public class GuessTheNuts {

  public static void main(String[] args) {
    Deck d = Deck.standard();

    d.shuffle();

    Hand hand = Hand.ofPoker(d.pop(Hand.MAX_CARDS_IN_POKER_HAND));

    System.err.println(hand);
    nuts(new ArrayList<>(hand.getCards()), d.getCards());
  }

  private static void nuts(List<Card> board, List<Card> deck) {

    List<Hand> hands = mergeToPokerHands(getBoardHands(board), getDeckHands(deck));

    System.err.println(hands.size());
    for (Hand hand : hands) {
      System.err.println(hand);
    }
    //Evaluate all 5 card hands
    //Sort and rank.

  }

  private static List<Hand> mergeToPokerHands(List<Hand> boardHands, List<Hand> deckHands) {
    List<Hand> combinedHands = new ArrayList<>();
    for (Hand boardHand : boardHands) {
      for (Hand deckHand : deckHands) {
        combinedHands.add(boardHand.combine(deckHand));
      }
    }
    return combinedHands;
  }


  private static List<Hand> getDeckHands(List<Card> deck) {
    //find all cards to use.
    List<Hand> deckHands = new ArrayList<>();
    for (int i = 0; i < deck.size(); i++) {
      for (int j = i + 1; j < deck.size(); j++) {
        Card handCard1 = deck.get(i);
        Card handCard2 = deck.get(j);
        deckHands.add(Hand.ofMax(handCard1, handCard2));
      }
    }
    return deckHands;
  }

  private static List<Hand> getBoardHands(List<Card> board) {
    //find all trips in hand.
    List<Hand> boardHands = new ArrayList<>();
    for (int i = 0; i < board.size(); i++) {
      for (int j = i + 1; j < board.size(); j++) {
        for (int k = j + 1; k < board.size(); k++) {
          Card boardCard1 = board.get(i);
          Card boardCard2 = board.get(j);
          Card boardCard3 = board.get(k);
          boardHands.add(Hand.ofMax(boardCard1, boardCard2, boardCard3));
        }
      }
    }
    return boardHands;
  }


}
