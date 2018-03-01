package cards;

import com.google.common.base.Preconditions;

import java.util.Objects;

/**
 * Represents a Card in a Deck of cards.
 */
public class Card implements Comparable<Card> {

    private final CardRank cardRank;

    private final Suit suit;

    /**
     * Create a new Card.
     *
     * @param cardRank The Rank. Can not be null.
     * @param suit     The Suit. Can not be null.
     */
    public Card(CardRank cardRank, Suit suit) {
        Preconditions.checkArgument(cardRank != null);
        Preconditions.checkArgument(suit != null);
        this.cardRank = cardRank;
        this.suit = suit;
    }


    /**
     * @return the cardRank
     */
    public CardRank getCardRank() {
        return cardRank;

    }

    /**
     * @return the suit
     */
    public Suit getSuit() {
        return suit;
    }

    public static Card of(CardRank rank, Suit suit) {
        return new Card(rank, suit);
    }

    @Override
    public int compareTo(Card card) {

        int comparison = this.cardRank.compareTo(card.cardRank);
        if (comparison == 0) {
            comparison = this.suit.compareTo(card.suit);
        }

        return comparison;
    }

    @Override
    public String toString() {
        return cardRank.name() + " " + suit.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardRank == card.cardRank &&
                suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardRank, suit);
    }
}
