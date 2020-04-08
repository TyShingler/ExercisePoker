package com.shingler.poker;

import com.shingler.poker.Card.Rank;
import com.shingler.poker.Card.Suit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CardTest extends TestCase {

    public CardTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(CardTest.class);
    }

    @org.junit.Test
    public void testIsValidateCardString(){
        assertTrue("2C is a valid cardString.", Card.isValidateCardString("2C"));
        assertTrue("9D is a valid cardString.", Card.isValidateCardString("9D"));
        assertTrue("TH is a valid cardString.", Card.isValidateCardString("TH"));
        assertTrue("AS is a valid cardString.", Card.isValidateCardString("AS"));
    }

    @org.junit.Test
    public void testCardConstructorWithEnums() {
        // Test constructing a card with enums and getter methods.
        Card card = new Card(Rank.TWO, Suit.CLUBS);
        assertTrue( "getRank() returns correct rank." , card.getRank() == Rank.TWO );
        assertTrue( "getRankAsChar() returns correct rank char." , card.getRankAsChar().equals("2") );
        assertTrue( "getSuit() returns correct suit." , card.getSuit() == Suit.CLUBS );
        assertTrue( "getSuitAsChar() returns correct suit char.", card.getSuitAsChar().equals("C"));

    }

    @org.junit.Test
    public void testCardConstructorWithStrings() {
        // Test constructing a card with cardString and getter methods.
        Card card = new Card("2C");
        assertTrue( "getRank() returns correct rank:Rank.TWO" , card.getRank() == Rank.TWO );
        assertTrue( "getRankAsChar() returns correct rank char:2" , card.getRankAsChar().equals("2") );
        assertTrue( "getSuit() returns correct suit:Suit.CLUBS" , card.getSuit() == Suit.CLUBS );
        assertTrue( "getSuitAsChar() returns correct suit char:C", card.getSuitAsChar().equals("C"));

        card = new Card("9C");
        assertTrue( "getRank() returns correct rank:Rank.NINE" , card.getRank() == Rank.NINE );
        assertTrue( "getRankAsChar() returns correct rank char:2" , card.getRankAsChar().equals("9") );
        assertTrue( "getSuit() returns correct suit:Suit.CLUBS" , card.getSuit() == Suit.CLUBS );
        assertTrue( "getSuitAsChar() returns correct suit char:C", card.getSuitAsChar().equals("C"));
    }

    @org.junit.Test
    public void testCardConstructorTenRank(){
        Card card = new Card("TH");
        // Test constructing a card with cardString and getter methods.
        assertTrue( "getRank() returns correct rank:Rank.TEN" , card.getRank() == Rank.TEN );
        assertTrue( "getRankAsChar() returns correct rank char:10" , card.getRankAsChar().equals("T"));
        assertTrue( "getSuit() returns correct suit:Suit.HEARTS", card.getSuit() == Suit.HEARTS);
        assertTrue( "getSuitAsChar() returns correct suit char:H", card.getSuitAsChar().equals("H"));

    }

    @org.junit.Test
    public void testCardConstructorFaceCard(){
        // Test constructing a face card
        Card card = new Card("AH");
        assertTrue( "getRank() returns correct rank:Rank.ACE" , card.getRank() == Rank.ACE );
        assertTrue( "getRankAsChar() returns correct rank char:A" , card.getRankAsChar().equals("A") );
        assertTrue( "getSuit() returns correct suit:Suit.HEARTS", card.getSuit() == Suit.HEARTS);
        assertTrue( "getSuitAsChar() returns correct suit char:H", card.getSuitAsChar().equals("H"));

    }

    @org.junit.Test
    public void testCardCompareTo(){
        Card cardArr[] = new Card[4];
        cardArr[0] = new Card("2H");
        cardArr[1] = new Card("3C");
        cardArr[2] = new Card("QD");
        cardArr[3] = new Card("KS");
        
        assertTrue("2H is less than 3C", cardArr[0].compareTo(cardArr[1]) < 0);
        assertTrue("3C is greater than 2H", cardArr[1].compareTo(cardArr[0]) > 0);
        assertTrue("3C is equal to 3C", cardArr[1].compareTo(cardArr[1]) == 0);

        assertTrue("QD is less than KS", cardArr[2].compareTo(cardArr[3]) < 0);
        assertTrue("KS is greater than QD", cardArr[3].compareTo(cardArr[2]) > 0);
        assertTrue("KS is equal to KS", cardArr[3].compareTo(cardArr[3]) == 0);
    }

    @org.junit.Test
    public void testCardCompareSuit(){
        Card one = new Card("KD");
        Card two = new Card("5D");
        Card three = new Card("QS");

        assertTrue("A KD has the same suit as 5D", one.compareSuit(two) == true);
        assertTrue("A KD has a different suit as 5D", one.compareSuit(three) == false);
        assertTrue("A KD has the same suit as 5D", one.compareSuit(one) == true);
    }

    @org.junit.Test
    public void testCardDotEquals(){
        Card one = new Card("KD");
        Card two = new Card("5D");

        assertTrue("Card one is equal to Card one", one.equals(one));
        assertTrue("Card one is not equal to Card one", one.equals("Not a card") == false);
        assertTrue("Card one is not equal to Card two", one.equals(two) == false);
    }

}