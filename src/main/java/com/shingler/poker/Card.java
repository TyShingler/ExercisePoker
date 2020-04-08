package com.shingler.poker;

import java.util.regex.*;

/**
 * The Card class is one of the fundamanetal classes for the poker excersize. It
 * is comparable witch will help will evaluating a poker hand later. 
 * 
 * @author Tyler Shingler
 * @version 1.0
 */
public class Card implements Comparable<Card> {

    // The number part of a playing card.
    enum Rank {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NICE, TEN, JACK, QUEEN, KING, ACE
    }
    private Rank rank;

    enum Suit {
        HEARTS, CLUBS, DIAMANDS, SPADES
    }
    private Suit suit;

    public Card(final Rank rank, final Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Card(String cardString) {
        validateCardString(cardString);
        
        this.rank = getRankFromCardString(cardString);
        this.suit = getSuitFromCardString(cardString);
    }
    
    private static void validateCardString(String cardString) {
        // @throws IllegalArgumentException For validating cardStrings
        if (cardString == null || cardString == ""){
            throw new IllegalArgumentException("cardString most not be empty or null!");
        }else if (!Pattern.matches("^([2-9]|10|[J|Q|K|A])[H|C|D|S]$", cardString)){
            throw new IllegalArgumentException("cardString must satisfy the following regex expression /^\\d0?[H|C|D|S]$/");
        }
    }

    private static Rank getRankFromCardString(String cardString) {
        // Needs to subtract two because enum ordinals start at 0.
        int endSubstring = 1;
        if (cardString.length() == 3){
            endSubstring = 2;
        }

        String rankString = cardString.substring(0, endSubstring);
        if (Pattern.matches("^[JQKA]$", rankString)){
            if (rankString.equals("J")) return Rank.JACK;
            if (rankString.equals("Q")) return Rank.QUEEN;
            if (rankString.equals("K")) return Rank.KING;
            if (rankString.equals("A")) return Rank.ACE;
        }
        return Rank.values()[Integer.parseInt(cardString.substring(0, endSubstring)) - 2];
    }

    private static Suit getSuitFromCardString(String cardString) {
        String suitChar;
        if (cardString.length() == 3){
            suitChar = cardString.substring(2, 3);
        }else {
            suitChar = cardString.substring(1, 2);
        }
        if (suitChar.equals("H")) 
            return Suit.HEARTS;
        if (suitChar.equals("C"))
            return Suit.CLUBS;
        if (suitChar.equals("D"))
            return Suit.DIAMANDS;
        return Suit.SPADES;
    }

    @Override
    public int compareTo(Card o) {
        // Override compareTo() to make one Card comparable to another Card
        return this.rank.compareTo(o.rank);
    }

    public boolean compareSuit(Card o){
        return this.suit == o.suit;
    }

    @Override
    public boolean equals(Object o) {
        // Overriding equals() to compare two Cards
        // Am I comparing to myself?
        if (o == this) {
            return true;
        }

        // Is this an instnace of Card?
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
        if (suit == Suit.DIAMANDS)
            return "D";
        if (suit == Suit.SPADES)
            return "S";
        return "E";
    }

    public String getRankAsChar() {
        if (rank.ordinal() < 9)
            return Integer.toString(rank.ordinal() + 2);
        if (rank == Rank.JACK)
            return "J";
        if (rank == Rank.QUEEN)
            return "Q";
        if (rank == Rank.KING)
            return "K";
        if (rank == Rank.ACE)
            return "A";
        return "E";
    }

    public Rank getRank(){ return rank; }
    public Suit getSuit(){ return suit; }
}