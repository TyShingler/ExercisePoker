package com.shingler.poker;

import java.util.regex.Pattern;

/**
 * The Card class is one of the fundamental classes for the poker exercise. It
 * is comparable witch will help will evaluating a poker hand later.
 * 
 * @author Tyler Shingler
 * @version 1.0
 */
public class Card implements Comparable<Card> {

    // The number part of a playing card.
    public enum Rank {
        Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace
    }

    private Rank rank;

    public enum Suit {
        HEARTS, CLUBS, DIAMONDS, SPADES
    }

    private Suit suit;

    public Card(final Rank rank, final Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Card(String cardString) {
        if (!isValidateCardString(cardString)) {
            throw new IllegalArgumentException(
                    "cardString must satisfy the following regex expression /^\\d0?[H|C|D|S]$/ but got:" + cardString);
        }

        this.rank = getRankFromCardString(cardString);
        this.suit = getSuitFromCardString(cardString);
    }

    protected static boolean isValidateCardString(String cardString) {
        // @throws IllegalArgumentException for invalidating cardStrings.
        if (cardString == null || cardString == "" || !Pattern.matches("^([2-9]|[T|J|Q|K|A])[H|C|D|S]$", cardString)) {
            return false;
        }
        return true;
    }

    private static Rank getRankFromCardString(String cardString) {
        // Needs to subtract two because enum ordinals start at 0.
        String rankString = cardString.substring(0, 1);
        if (Pattern.matches("^[TJQKA]$", rankString)) {
            if (rankString.equals("T"))
                return Rank.Ten;
            if (rankString.equals("J"))
                return Rank.Jack;
            if (rankString.equals("Q"))
                return Rank.Queen;
            if (rankString.equals("K"))
                return Rank.King;
            if (rankString.equals("A"))
                return Rank.Ace;
        }
        return Rank.values()[Integer.parseInt(rankString) - 2];
    }

    private static Suit getSuitFromCardString(String cardString) {
        String suitChar = cardString.substring(1, 2);
        if (suitChar.equals("H"))
            return Suit.HEARTS;
        if (suitChar.equals("C"))
            return Suit.CLUBS;
        if (suitChar.equals("D"))
            return Suit.DIAMONDS;
        return Suit.SPADES;
    }

    @Override
    public int compareTo(Card o) {
        // Override compareTo() to make one Card comparable to another Card.
        // Compares by Rank then Suit.
        if (this.rank.compareTo(o.rank) == 0) {
            return this.suit.compareTo(o.suit);
        }
        return this.rank.compareTo(o.rank);
    }

    public boolean compareSuit(Card o) {
        return this.suit == o.suit;
    }

    @Override
    public boolean equals(Object o) {
        // Overriding equals() to compare two Cards.
        // Am I comparing to myself?
        if (o == this) {
            return true;
        }

        // Is this an instance of Card?
        if (!(o instanceof Card)) {
            return false;
        }

        // Manual comparison.
        Card card = (Card) o;
        return this.rank == card.rank && this.suit == card.suit;
    }

    public String toString() {
        return getRankAsChar() + getSuitAsChar();
    }

    public String getSuitAsChar() {
        if (suit == Suit.HEARTS)
            return "H";
        if (suit == Suit.CLUBS)
            return "C";
        if (suit == Suit.DIAMONDS)
            return "D";
        if (suit == Suit.SPADES)
            return "S";
        return "E";
    }

    public String getRankAsChar() {
        if (rank.ordinal() < 8)
            return Integer.toString(rank.ordinal() + 2);
        if (rank == Rank.Ten)
            return "T";
        else if (rank == Rank.Jack)
            return "J";
        else if (rank == Rank.Queen)
            return "Q";
        else if (rank == Rank.King)
            return "K";
        else if (rank == Rank.Ace)
            return "A";
        return "E";
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }
}