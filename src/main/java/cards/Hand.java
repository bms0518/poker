package cards;

import com.google.common.base.Preconditions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Hand {

    private static final int MAX_CARDS_IN_POKER_HAND = 5;

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

    public static Hand ofPoker(Card... cards) {
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

}
