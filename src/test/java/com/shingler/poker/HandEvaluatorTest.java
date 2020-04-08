package com.shingler.poker;

import java.util.Arrays;

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
    public void test(){
        Card[] cardArray = {new Card(""), new Card(""), new Card(""), new Card(""), new Card("")}; 
        assertTrue("Test Example", false);
    }

    @org.junit.Test
    public void testValidateHand(){
        Card[] cardArray = {new Card("AH"), new Card("9H"), new Card("9S"), new Card("9H"), new Card("AH")}; 
        assertTrue("No duplicate cards.", HandEvaluator.isValidateHand(cardArray) == false);
    }

    @org.junit.Test
    public void testFindHighCard(){
        Card[] cardArray = {new Card("AH"), new Card("KC"), new Card("QD"), new Card("QD"), new Card("QC")}; 
        assertTrue("Finds AH as the High Card.", HandEvaluator.findHighCard(cardArray) == new Card("AH") );
    }

    @org.junit.Test
    public void testFindPair(){
        Card[] cardArray = {new Card("JC"), new Card("JD"), new Card("2D"), new Card("3D"), new Card("3C")};
        Card[] expected = {new Card("JC"), new Card("JD")};
        Arrays.sort(expected);
        assertTrue("Finds one pair.", HandEvaluator.findPair(cardArray) == expected);
    }

    @org.junit.Test
    public void testFindTwoPairs(){
        Card[] cardArray = {new Card("JC"), new Card("JD"), new Card("3D"), new Card("2D"), new Card("3C")};
        Card[] expected = {new Card("JC"), new Card("JD"), new Card("3D"), new Card("3C")};
        Arrays.sort(expected);
        assertTrue("Finds find the two pair.", HandEvaluator.findTwoPairs(cardArray) == expected);
    }

    @org.junit.Test
    public void testFindThreeOfAKind(){
        Card[] cardArray = {new Card("TC"), new Card("TD"), new Card("TS"), new Card("3D"), new Card("4C")};
        Card[] expected = {new Card("TC"), new Card("TD"), new Card("TS")};
        Arrays.sort(expected);
        assertTrue("Finds find three of a kind.", HandEvaluator.findThreeOfAKind(cardArray) == expected);
    }

    @org.junit.Test
    public void testFindStraight(){
        Card[] cardArray = {new Card("2H"), new Card("4D"), new Card("6C"), new Card("3C"), new Card("5D")};
        Card[] expected = {new Card("2H"), new Card("3C"), new Card("4D"), new Card("5D"), new Card("6C")};
        Arrays.sort(expected);
        assertTrue("Finds find the straight.", HandEvaluator.findStraight(cardArray) == expected);
    }

    @org.junit.Test
    public void testIsFlush(){
        Card[] cardArray = {new Card("AH"), new Card("KH"), new Card("QH"), new Card("3H"), new Card("6H")};
        assertTrue("Test find the flush.", HandEvaluator.isFlush(cardArray));
    }

    @org.junit.Test
    public void testFindFullHouse(){
        Card[] cardArray = {new Card("AH"), new Card("QD"), new Card("AC"), new Card("QD"), new Card("QC")};
        Card[] expected = {new Card("AH"), new Card("QD"), new Card("AC"), new Card("QD"), new Card("QC")};
        Arrays.sort(expected);
        assertTrue("Finds find the full house.", HandEvaluator.findFullHouse(cardArray) == expected);
    }

    @org.junit.Test
    public void testFindFourOfAKind(){
        Card[] cardArray = {new Card("AH"), new Card("AD"), new Card("AC"), new Card("AS"), new Card("QC")};
        Card[] expected = {new Card("AH"), new Card("AD"), new Card("AC"), new Card("AS")};
        Arrays.sort(expected);
        assertTrue("Finds four of a Kind.", HandEvaluator.findFourOfAKind(cardArray) == expected);
    }

    @org.junit.Test
    public void testFindStraightFlush(){
        Card[] cardArray = {new Card("2H"), new Card("4H"), new Card("6H"), new Card("3H"), new Card("5H")};
        Card[] expected = {new Card("2H"), new Card("3H"), new Card("4H"), new Card("5H"), new Card("6H")};

        assertTrue("Finds the straight flush.", HandEvaluator.findStraight(cardArray) == expected);
    }
}
