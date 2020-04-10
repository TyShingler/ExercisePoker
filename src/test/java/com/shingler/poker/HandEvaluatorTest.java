package com.shingler.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.shingler.poker.HandEvaluator.order;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HandEvaluatorTest extends TestCase {

    public HandEvaluatorTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(HandEvaluatorTest.class);
    }

    @org.junit.Test
    public void testEvaluate() {
        String testString0 = "Black: 2H 4H 6H 3H 5H  White: AH KH QH 7H 8H";
        String testString1 = "Black: 2H 4S 4C 2D 4H  White: 2S 8S AS QS 3S";
        String testString2 = "Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C KH";
        String testString3 = "Black: 2H 3D 5S 9C KD  White: 2D 2S 2C 9S KH";
        String testString4 = "Black: 2H 3D 5S 9C KD  White: 2C 3H 4S 8C AH";
        String testString5 = "Black: 2C 3D 5S 9C KD  White: 2H 3H 4H 8H AH";
        

        assertTrue("Should return black as winner.", HandEvaluator.evaluate(testString0).equals("Black wins. -with straight flush: 2H 3H 4H 5H 6H"));
        assertTrue("Should return black as winner.", HandEvaluator.evaluate(testString1).equals("Black wins. -with full house: 2H 2D 4H 4C 4S"));
        assertTrue("Should return Tie."            , HandEvaluator.evaluate(testString2).equals("Tie."));
        assertTrue("Should return White as winner.", HandEvaluator.evaluate(testString3).equals("White wins. -with three of a kind: 2C 2D 2S"));
        assertTrue("Should return White as winner.", HandEvaluator.evaluate(testString4).equals("White wins. -with high card: Ace"));
        assertTrue("Should return White as winner.", HandEvaluator.evaluate(testString5).equals("White wins. -with flush"));
    }

    @org.junit.Test
    public void testRegexPattern() {
        String testString0 = "Black: 2H 4H 6H 3H 5H  White: AH KH QH 7H 8H";
        String inValid = "ASDGFAGDFasdfasfda";
        String regex = "(Black|White):((?:\s[[2-9]|[A|K|Q|J|T]][H|D|S|C])+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(testString0);
        assertTrue("Regex passes valid strings.", matcher.find());
        matcher = pattern.matcher(inValid);
        assertTrue("Regex dose not pass valid strings.", !Pattern.matches(regex, inValid));
    }

    @org.junit.Test
    public void testGetHandFromMatcher() {
        ArrayList<Card> black = new ArrayList<Card>();
        black.add(new Card("2H"));
        black.add(new Card("4H"));
        black.add(new Card("6H"));
        black.add(new Card("3H"));
        black.add(new Card("5H"));
        String userIn = "Black: 2H 4H 6H 3H 5H";

        String regex = "(Black|White):((?:\\s[[2-9]|[A|K|Q|J|T]][H|D|S|C])+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userIn);

        assertTrue("GetHandFromMatcher should return 2H 4H 6H 3H 5H as current hand.", HandEvaluator.getHandFromMatcher(matcher).equals(black));

        HandEvaluator.validateHand(black);

        ArrayList<Card> white = new ArrayList<Card>();
        white.add(new Card("AH"));
        white.add(new Card("KH"));
        white.add(new Card("QH"));
        white.add(new Card("7H"));
        white.add(new Card("8H"));

        HandEvaluator.validateHand(white);

        assertTrue("Should return correct winner text for the hands.",
                HandEvaluator.compareHands(black, white).equals("Black wins. -with straight flush: 2H 3H 4H 5H 6H"));
    }

    @org.junit.Test
    public void testCompareHands() {
        ArrayList<Card> black = new ArrayList<Card>();
        black.add(new Card("2H"));
        black.add(new Card("4H"));
        black.add(new Card("6H"));
        black.add(new Card("3H"));
        black.add(new Card("5H"));

        HandEvaluator.validateHand(black);

        ArrayList<Card> white = new ArrayList<Card>();
        white.add(new Card("AH"));
        white.add(new Card("KH"));
        white.add(new Card("QH"));
        white.add(new Card("7H"));
        white.add(new Card("8H"));

        HandEvaluator.validateHand(white);

        assertTrue("Should return correct winner text for the hands.",
                HandEvaluator.compareHands(black, white).equals("Black wins. -with straight flush: 2H 3H 4H 5H 6H"));
    }

    @org.junit.Test
    public void testCardArrayToString() {
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("2H"));
        cardArray.add(new Card("4H"));
        cardArray.add(new Card("6H"));
        cardArray.add(new Card("3H"));
        cardArray.add(new Card("5H"));
        assertTrue("Should return a string of cards.",
                HandEvaluator.cardArrayToString(cardArray).equals("2H 4H 6H 3H 5H"));
    }

    @org.junit.Test
    public void testGetEvaluation() {
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("2H"));
        cardArray.add(new Card("4H"));
        cardArray.add(new Card("6H"));
        cardArray.add(new Card("3H"));
        cardArray.add(new Card("5H"));
        assertTrue("Hand should be a straight flush.",
                HandEvaluator.getEvaluation(cardArray).rank == order.STRAIGHTFLUSH);

        cardArray = new ArrayList<Card>();
        cardArray.add(new Card("AH"));
        cardArray.add(new Card("KC"));
        cardArray.add(new Card("2D"));
        cardArray.add(new Card("3D"));
        cardArray.add(new Card("4C"));
        assertTrue("Hand should be a high card:A high.", HandEvaluator.getEvaluation(cardArray).rank == order.HIGHCARD);
    }

    @org.junit.Test
    public void testValidateHand() {
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("AH"));
        cardArray.add(new Card("9H"));
        cardArray.add(new Card("AS"));
        cardArray.add(new Card("9H"));
        cardArray.add(new Card("AH"));
        assertTrue("No duplicate cards.", HandEvaluator.validateHand(cardArray) == false);
    }

    @org.junit.Test
    public void testFindHighCard() {

        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("AH"));
        cardArray.add(new Card("KC"));
        cardArray.add(new Card("QD"));
        cardArray.add(new Card("QD"));
        cardArray.add(new Card("QC"));
        assertTrue("Finds AH as the High Card.", HandEvaluator.findHighCard(cardArray).get(0).equals(new Card("AH")));

        cardArray = new ArrayList<Card>();
        cardArray.add(new Card("2H"));
        cardArray.add(new Card("2C"));
        cardArray.add(new Card("2D"));
        cardArray.add(new Card("2S"));
        cardArray.add(new Card("3H"));
        assertTrue("Finds 3H as the High Card.", HandEvaluator.findHighCard(cardArray).get(0).equals(new Card("3H")));
    }

    @org.junit.Test
    public void testFindPair() {
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

        cardArray = new ArrayList<Card>();
        cardArray.add(new Card("2C"));
        cardArray.add(new Card("3D"));
        cardArray.add(new Card("4D"));
        cardArray.add(new Card("7D"));
        cardArray.add(new Card("8C"));
        assertTrue("Finds nothing.", HandEvaluator.findPair(cardArray) == null);

    }

    @org.junit.Test
    public void testFindTwoPairs() {
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("JC"));
        cardArray.add(new Card("JD"));
        cardArray.add(new Card("3D"));
        cardArray.add(new Card("2D"));
        cardArray.add(new Card("3C"));
        ArrayList<Card> expected = new ArrayList<Card>();
        expected.add(new Card("JC"));
        expected.add(new Card("JD"));
        ;
        expected.add(new Card("3C"));
        expected.add(new Card("3D"));
        Collections.sort(expected);
        assertTrue("Finds find the two pair.", HandEvaluator.findTwoPairs(cardArray).equals(expected));

        cardArray = new ArrayList<Card>();
        cardArray.add(new Card("JC"));
        cardArray.add(new Card("JD"));
        cardArray.add(new Card("5D"));
        cardArray.add(new Card("2D"));
        cardArray.add(new Card("3C"));
        assertTrue("Finds nothing", HandEvaluator.findTwoPairs(cardArray) == null);

    }

    @org.junit.Test
    public void testFindThreeOfAKind() {
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

        cardArray = new ArrayList<Card>();
        cardArray.add(new Card("KC"));
        cardArray.add(new Card("TD"));
        cardArray.add(new Card("TS"));
        cardArray.add(new Card("3D"));
        cardArray.add(new Card("4C"));
        assertTrue("Finds nothing.", HandEvaluator.findThreeOfAKind(cardArray) == null);

    }

    @org.junit.Test
    public void testFindStraight() {
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

        cardArray = new ArrayList<Card>();
        cardArray.add(new Card("2H"));
        cardArray.add(new Card("4D"));
        cardArray.add(new Card("KC"));
        cardArray.add(new Card("3C"));
        cardArray.add(new Card("5D"));
        assertTrue("Finds nothing.", HandEvaluator.findStraight(cardArray) == null);
    }

    @org.junit.Test
    public void testIsFlush() {
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("AH"));
        cardArray.add(new Card("KH"));
        cardArray.add(new Card("QH"));
        cardArray.add(new Card("3H"));
        cardArray.add(new Card("6H"));
        assertTrue("Test find the flush.", HandEvaluator.isFlush(cardArray));

        cardArray = new ArrayList<Card>();
        cardArray.add(new Card("AH"));
        cardArray.add(new Card("KH"));
        cardArray.add(new Card("QD"));
        cardArray.add(new Card("3H"));
        cardArray.add(new Card("6H"));
        assertTrue("Test nothing.", HandEvaluator.isFlush(cardArray) == false);

    }

    @org.junit.Test
    public void testFindFullHouse() {
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("AH"));
        cardArray.add(new Card("QD"));
        cardArray.add(new Card("AC"));
        cardArray.add(new Card("QD"));
        cardArray.add(new Card("QC"));
        ArrayList<Card> expected = cardArray;
        Collections.sort(expected);
        assertTrue("Finds find the full house.", HandEvaluator.findFullHouse(cardArray).equals(expected));

        cardArray = new ArrayList<Card>();
        cardArray.add(new Card("AH"));
        cardArray.add(new Card("QD"));
        cardArray.add(new Card("AC"));
        cardArray.add(new Card("JD"));
        cardArray.add(new Card("QC"));
        assertTrue("Finds nothing.", HandEvaluator.findFullHouse(cardArray) == null);

    }

    @org.junit.Test
    public void testFindFourOfAKind() {
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

        cardArray = new ArrayList<Card>();
        cardArray.add(new Card("AH"));
        cardArray.add(new Card("QD"));
        cardArray.add(new Card("AC"));
        cardArray.add(new Card("AS"));
        cardArray.add(new Card("QC"));
        assertTrue("Finds nothing.", HandEvaluator.findFourOfAKind(cardArray) == null);

    }

    @org.junit.Test
    public void testFindStraightFlush() {
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.add(new Card("2H"));
        cardArray.add(new Card("4H"));
        cardArray.add(new Card("6H"));
        cardArray.add(new Card("3H"));
        cardArray.add(new Card("5H"));
        ArrayList<Card> expected = cardArray;
        Collections.sort(expected);
        assertTrue("Finds the straight flush.", HandEvaluator.findStraightFlush(cardArray).equals(expected));

        cardArray = new ArrayList<Card>();
        cardArray.add(new Card("2H"));
        cardArray.add(new Card("4C"));
        cardArray.add(new Card("6H"));
        cardArray.add(new Card("3H"));
        cardArray.add(new Card("5H"));
        assertTrue("Finds nothing.", HandEvaluator.findStraightFlush(cardArray) == null);
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
