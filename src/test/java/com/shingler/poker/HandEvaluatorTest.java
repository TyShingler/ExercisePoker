package com.shingler.poker;

import java.util.ArrayList;
import java.util.Collections;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HandEvaluatorTest extends TestCase{
    
    public HandEvaluatorTest( String testName ){
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( HandEvaluatorTest.class );
    }

    public void testEvaluate(){

    }

    @org.junit.Test
    public void testValidateHand(){
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("AH"));
        cardArray.add(new Card("9H"));
        cardArray.add(new Card("AS"));
        cardArray.add(new Card("9H"));
        cardArray.add(new Card("AH"));
        assertTrue("No duplicate cards.", HandEvaluator.isValidateHand(cardArray) == false);
    }

    @org.junit.Test
    public void testFindHighCard(){
        
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("AH"));
        cardArray.add(new Card("KC"));
        cardArray.add(new Card("QD"));
        cardArray.add(new Card("QD"));
        cardArray.add(new Card("QC"));
        assertTrue("Finds AH as the High Card.", HandEvaluator.findHighCard(cardArray).equals(new Card("AH")) );
    }

    @org.junit.Test
    public void testFindPair(){
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("JC"));
        cardArray.add(new Card("JD"));
        cardArray.add(new Card("2D"));
        cardArray.add(new Card("3D"));
        cardArray.add(new Card("3C"));
        ArrayList<Card> expected = new ArrayList<Card>();
        expected.add(new Card("3D"));
        expected.add(new Card("3C"));
        Collections.sort(expected);
        assertTrue("Finds one pair.", HandEvaluator.findPair(cardArray).equals(expected));
    }

    @org.junit.Test
    public void testFindTwoPairs(){
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("JC"));
        cardArray.add(new Card("JD"));
        cardArray.add(new Card("3D"));
        cardArray.add(new Card("2D"));
        cardArray.add(new Card("3C"));
        ArrayList<Card> expected = new ArrayList<Card>();
        expected.add(new Card("JC"));
        expected.add(new Card("JD"));;
        expected.add(new Card("3C"));
        expected.add(new Card("3D"));
        Collections.sort(expected);
        assertTrue("Finds find the two pair.", HandEvaluator.findTwoPairs(cardArray).equals(expected));
    }

    @org.junit.Test
    public void testFindThreeOfAKind(){
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("TC"));
        cardArray.add(new Card("TD"));
        cardArray.add(new Card("TS"));
        cardArray.add(new Card("3D"));
        cardArray.add(new Card("4C"));
        ArrayList<Card> expected = new ArrayList<Card>();
        expected.add(new Card("TC"));
        expected.add(new Card("TD"));
        expected.add(new Card("TS"));
        Collections.sort(expected);
        assertTrue("Finds find three of a kind.", HandEvaluator.findThreeOfAKind(cardArray).equals(expected));
    }

    @org.junit.Test
    public void testFindStraight(){
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("2H"));
        cardArray.add(new Card("4D"));
        cardArray.add(new Card("6C"));
        cardArray.add(new Card("3C"));
        cardArray.add(new Card("5D"));
        ArrayList<Card> expected = new ArrayList<Card>();
        expected.add(new Card("2H"));
        expected.add(new Card("3C"));
        expected.add(new Card("4D"));
        expected.add(new Card("5D"));
        expected.add(new Card("6C"));
        Collections.sort(expected);
        assertTrue("Finds find the straight.", HandEvaluator.findStraight(cardArray).equals(expected));
    }

    @org.junit.Test
    public void testIsFlush(){
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("AH"));
        cardArray.add(new Card("KH"));
        cardArray.add(new Card("QH"));
        cardArray.add(new Card("3H"));
        cardArray.add(new Card("6H"));
        assertTrue("Test find the flush.", HandEvaluator.isFlush(cardArray));
    }

    @org.junit.Test
    public void testFindFullHouse(){
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("AH"));
        cardArray.add(new Card("QD"));
        cardArray.add(new Card("AC"));
        cardArray.add(new Card("QD"));
        cardArray.add(new Card("QC"));
        ArrayList<Card> expected = cardArray;
        Collections.sort(expected);
        assertTrue("Finds find the full house.", HandEvaluator.findFullHouse(cardArray).equals(expected));
    }

    @org.junit.Test
    public void testFindFourOfAKind(){
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("AH"));
        cardArray.add(new Card("AD"));
        cardArray.add(new Card("AC"));
        cardArray.add(new Card("AS"));
        cardArray.add(new Card("QC"));
        ArrayList<Card> expected = new ArrayList<Card>();
        expected.add(new Card("AH"));
        expected.add(new Card("AD"));
        expected.add(new Card("AC"));
        expected.add(new Card("AS"));
        Collections.sort(expected);
        assertTrue("Finds four of a Kind.", HandEvaluator.findFourOfAKind(cardArray).equals(expected));
    }

    @org.junit.Test
    public void testFindStraightFlush(){
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("2H"));
        cardArray.add(new Card("4H"));
        cardArray.add(new Card("6H"));
        cardArray.add(new Card("3H"));
        cardArray.add(new Card("5H"));
        ArrayList<Card> expected = cardArray;
        Collections.sort(expected);
        assertTrue("Finds the straight flush.", HandEvaluator.findStraightFlush(cardArray).equals(expected));
    }

    @org.junit.Test
    public void testGetCardOfADifferentRank() {
        Card card = new Card("2C");
        assertTrue("Returns a card this is not 2C", !HandEvaluator.getCardOfDifferentRank(card).equals(card));
        card = new Card("5D");
        assertTrue("Returns a card this is not 5D", !HandEvaluator.getCardOfDifferentRank(card).equals(card));
        card = new Card("AC");
        assertTrue("Returns a card this is not AC", !HandEvaluator.getCardOfDifferentRank(card).equals(card));
        card = new Card("AH");
        assertTrue("Returns a card this is not AH", !HandEvaluator.getCardOfDifferentRank(card).equals(card));
    }
}
