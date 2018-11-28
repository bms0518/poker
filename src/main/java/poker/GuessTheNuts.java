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
        Deck deck = Deck.standard();
        deck.shuffle();

        Hand hand = Hand.ofPoker(deck.pop(Hand.MAX_CARDS_IN_POKER_HAND));

        nuts(hand, deck);
    }

    private static void nuts(Hand boardHand, Deck deck) {
        List<Hand> possibleHandsUsing2HoleCards = mergeToPokerHands(getBoardHandsTrips(boardHand), getDeckHandsSize2(deck));
        List<Hand> possibleHandsUsing1HoleCards = mergeToPokerHands(getBoardHandsQuads(boardHand), getDeckHandsSize1(deck));

        PokerHands currentBestRank = null;
        Hand currentBestHand = null;


        for (Hand hand : possibleHandsUsing2HoleCards) {
            PokerHands pokerHand = PokerHands.evaluate(hand);
            if (currentBestRank == null) {
                currentBestRank = pokerHand;
            }

            if (currentBestHand == null) {
                currentBestHand = hand;
            }

            if (currentBestRank.compareTo(pokerHand) < 1) {
                currentBestRank = pokerHand;
                currentBestHand = hand;

                if(currentBestRank.equals(PokerHands.FOUR_OF_A_KIND)){
                    System.err.println(2);
                    System.err.println(currentBestHand);
                }
            }

        }

        for (Hand hand : possibleHandsUsing1HoleCards) {
            PokerHands pokerHand = PokerHands.evaluate(hand);
            if (currentBestRank.compareTo(pokerHand) < 1) {
                currentBestRank = pokerHand;
                currentBestHand = hand;

                if(currentBestRank.equals(PokerHands.FOUR_OF_A_KIND)){
                    System.err.println(2);
                    System.err.println(currentBestHand);
                }

            }


        }

        System.err.println(boardHand);
        System.err.println(currentBestRank);
        System.err.println(currentBestHand);


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


    private static List<Hand> getDeckHandsSize2(Deck deck) {
        //find all cards to use.
        List<Hand> deckHands = new ArrayList<>();

        List<Card> deckCards = deck.getCards();
        for (int i = 0; i < deckCards.size(); i++) {
            for (int j = i + 1; j < deckCards.size(); j++) {
                Card handCard1 = deckCards.get(i);
                Card handCard2 = deckCards.get(j);
                deckHands.add(Hand.ofMax(handCard1, handCard2));
            }
        }
        return deckHands;
    }

    private static List<Hand> getDeckHandsSize1(Deck deck) {
        //find all cards to use.
        List<Hand> deckHands = new ArrayList<>();

        List<Card> deckCards = deck.getCards();
        for (Card card : deckCards) {
            deckHands.add(Hand.ofMax(card));
        }

        return deckHands;
    }


    private static List<Hand> getBoardHandsTrips(Hand board) {
        //find all trips in hand.
        List<Hand> boardHands = new ArrayList<>();

        List<Card> boardCards = board.asList();

        for (int i = 0; i < boardCards.size(); i++) {
            for (int j = i + 1; j < boardCards.size(); j++) {
                for (int k = j + 1; k < boardCards.size(); k++) {
                    Card boardCard1 = boardCards.get(i);
                    Card boardCard2 = boardCards.get(j);
                    Card boardCard3 = boardCards.get(k);
                    boardHands.add(Hand.ofMax(boardCard1, boardCard2, boardCard3));
                }
            }
        }
        return boardHands;
    }

    private static List<Hand> getBoardHandsQuads(Hand board) {
        //find all trips in hand.
        List<Hand> boardHands = new ArrayList<>();

        List<Card> boardCards = board.asList();

        for (int i = 0; i < boardCards.size(); i++) {
            for (int j = i + 1; j < boardCards.size(); j++) {
                for (int k = j + 1; k < boardCards.size(); k++) {
                    for (int l = k + 1; l < boardCards.size(); l++) {
                        Card boardCard1 = boardCards.get(i);
                        Card boardCard2 = boardCards.get(j);
                        Card boardCard3 = boardCards.get(k);
                        Card boardCard4 = boardCards.get(l);
                        boardHands.add(Hand.ofMax(boardCard1, boardCard2, boardCard3, boardCard4));
                    }
                }
            }

        }
        return boardHands;
    }

}
