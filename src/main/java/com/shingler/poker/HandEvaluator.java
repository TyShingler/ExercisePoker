package com.shingler.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.shingler.poker.Card.Rank;
import com.shingler.poker.HandEvaluator.order;

public class HandEvaluator {

    private static int handSize = 5;

    public static String evaluate(String userIn) {

        String regex = "(Black|White):((?:\s[[2-9]|[A|K|Q|J|T]][H|D|S|C])+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userIn);
        if (!matcher.find()) {
            return "[Error] Invalid format.";
        }
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(userIn);

        ArrayList<Card> black = getHandFromMatcher(matcher);
        ArrayList<Card> white = getHandFromMatcher(matcher);

        return compareHands(black, white);
    }

    static ArrayList<Card> getHandFromMatcher(Matcher matcher) {
        matcher.find();
        String[] rawHand = matcher.group(2).trim().split(" ");
        ArrayList<Card> hand = new ArrayList<Card>();
        for (String cardString : rawHand) {
            hand.add(new Card(cardString));
        }
        return hand;
    }

    static String compareHands(ArrayList<Card> black, ArrayList<Card> white) {
        ArrayList<Card> validityCheck = (ArrayList<Card>) black.clone();
        validityCheck.addAll(white);
        if (!validateHands(validityCheck)) {
            return "[Error] Invalid hands to compare. Two hands can't have duplicate cards between them.";
        }

        Result blackResult = getEvaluation(black);
        Result whiteResult = getEvaluation(white);

        if (blackResult.rank.ordinal() > whiteResult.rank.ordinal()) {
            return "Black wins. -" + blackResult.winnerText;
        } else if (blackResult.rank.ordinal() == whiteResult.rank.ordinal()) {
            Card blackHighCard = findHighCard(blackResult.cardsUsed).get(0);
            Card whiteHighCard = findHighCard(whiteResult.cardsUsed).get(0);
            if (blackHighCard.getRank().compareTo(whiteHighCard.getRank()) > 0) {
                return "Black wins. -" + blackResult.winnerText;
            } else if(blackHighCard.getRank().compareTo(whiteHighCard.getRank()) < 0){
                return "White wins. -" + whiteResult.winnerText;
            } else {
                return "Tie.";
            }
        }
        return "White wins. -" + whiteResult.winnerText;
    }

    static Result getEvaluation(ArrayList<Card> handIn) {
        ArrayList<Card> hand = new ArrayList<Card>();
        Result result = new Result();
        if ((hand = findStraightFlush(handIn)) != null) {
            result.rank = order.STRAIGHTFLUSH;
            result.winnerText = "with straight flush: " + cardArrayToString(hand);
        } else if ((hand = findFourOfAKind(handIn)) != null) {
            result.rank = order.FOUROFAKIND;
            result.winnerText = "with four of a kind: " + cardArrayToString(hand);
        } else if ((hand = findFullHouse(handIn)) != null) {
            result.rank = order.FULLHOUSE;
            result.winnerText = "with full house: " + cardArrayToString(hand);
        } else if (isFlush(handIn)) {
            result.rank = order.FLUSH;
            result.winnerText = "with flush";
        } else if ((hand = findStraight(handIn)) != null) {
            result.rank = order.STRAIGHT;
            result.winnerText = "with straight: " + cardArrayToString(hand);
        } else if ((hand = findThreeOfAKind(handIn)) != null) {
            result.rank = order.THREEOFAKIND;
            result.winnerText = "with three of a kind: " + cardArrayToString(hand);
        } else if ((hand = findTwoPairs(handIn)) != null) {
            result.rank = order.TWOPAIRS;
            result.winnerText = "with two pair: " + cardArrayToString(hand);
        } else if ((hand = findPair(handIn)) != null) {
            result.rank = order.PAIR;
            result.winnerText = "with pair: " + cardArrayToString(hand);
        } else if ((hand = findHighCard(handIn)) != null) {
            result.rank = order.HIGHCARD;
            if (hand.get(0).getRank().ordinal() < 9) {
                result.winnerText = "with high card: " + findHighCard(handIn).get(0).getRankAsChar();
            } else {
                result.winnerText = "with high card: " + findHighCard(handIn).get(0).getRank().toString();
            }
        }

        if (result.rank != null) {
            result.cardsUsed = hand;
            return result;
        }
        result.rank = order.ERROR;
        result.winnerText = "[ERROR] Hand could not be evaluated";
        return result;
    }

    static String cardArrayToString(ArrayList<Card> cardArray) {
        String out = "";
        for (Card card : cardArray) {
            out += card.toString() + " ";
        }
        return out.trim();
    }

    private static boolean hasRepeatingCards(ArrayList<Card> cardArray) {
        // There are no duplicates in the hand.
        Collections.sort(cardArray);
        String compare = "";
        for (Card card : cardArray) {
            if (card.toString().equals(compare))
                return true;
            compare = card.toString();
        }
        return false;
    }

    static boolean validateHands(ArrayList<Card> cardArray) {
        if (cardArray.size() != handSize * 2) {
            return false;
        }

        return !hasRepeatingCards(cardArray);
    }

    static boolean validateHand(ArrayList<Card> cardArray) {
        if (cardArray.size() != handSize) {
            return false;
        }

        return !hasRepeatingCards(cardArray);
    }

    static ArrayList<Card> findHighCard(ArrayList<Card> cardArray) {
        Collections.sort(cardArray);
        ArrayList<Card> card = new ArrayList<Card>();
        card.add(cardArray.get(cardArray.size() - 1));
        return card;
    }

    static ArrayList<Card> findPair(ArrayList<Card> cardArray) {
        ArrayList<Card> pair = findCardsInARow(cardArray, 2);
        if (pair != null) {
            Collections.sort(pair);
        }
        return pair;
    }

    static ArrayList<Card> findTwoPairs(ArrayList<Card> cardArray) {
        // Find the first pair.
        ArrayList<Card> firstPair = findCardsInARow(cardArray, 2);
        if (firstPair == null)
            return null;

        // Clone so cardArray is not changed and remove the first pair.
        ArrayList<Card> copy = (ArrayList<Card>) cardArray.clone();
        copy.removeAll(firstPair);

        // Find the second pair.
        ArrayList<Card> secondPair = findCardsInARow(copy, 2);

        if (secondPair != null) {
            firstPair.addAll(secondPair);
            Collections.sort(firstPair);
            return firstPair;
        }
        return null;
    }

    static ArrayList<Card> findThreeOfAKind(ArrayList<Card> cardArray) {
        ArrayList<Card> threeOfAKind = findCardsInARow(cardArray, 3);
        if (threeOfAKind != null) {
            Collections.sort(threeOfAKind);
        }
        return threeOfAKind;
    }

    static ArrayList<Card> findStraight(ArrayList<Card> cardArray) {
        Collections.sort(cardArray);
        int previousRank = -5;
        int currentRank = -5;
        int count = 0;
        // For every card...
        for (Card card : cardArray) {
            // Update the current rank...
            currentRank = card.getRank().ordinal();
            // If the current rank is one higher than the previous rank increment count.
            if (currentRank == previousRank + 1)
                count++;
            previousRank = currentRank;
        }
        // In this case if its a straight, count must be four.
        return (count == 4) ? cardArray : null;
    }

    static boolean isFlush(ArrayList<Card> cardArray) {
        String suit = cardArray.get(0).getSuitAsChar();
        for (Card card : cardArray) {
            if (suit != card.getSuitAsChar())
                return false;
        }
        return true;
    }

    static ArrayList<Card> findFullHouse(ArrayList<Card> cardArray) {
        // Find the three of a kinda first.
        ArrayList<Card> threeOfAKind = findCardsInARow(cardArray, 3);
        if (threeOfAKind == null) {
            return null;
        }

        // Copy so we don't change cardArray and remove the three a kinda.
        ArrayList<Card> copy = (ArrayList<Card>) cardArray.clone();
        copy.removeAll(threeOfAKind);

        // If all that is left is a pair then we have a full house!
        ArrayList<Card> pair = findCardsInARow(copy, 2);
        if (pair != null) {
            pair.addAll(threeOfAKind);
            Collections.sort(pair);
            return pair;
        }

        return null;
    }

    static ArrayList<Card> findFourOfAKind(ArrayList<Card> cardArray) {
        ArrayList<Card> fourOfAKind = findCardsInARow(cardArray, 4);
        if (fourOfAKind != null) {
            Collections.sort(fourOfAKind);
        }
        return fourOfAKind;
    }

    static ArrayList<Card> findStraightFlush(ArrayList<Card> cardArray) {
        if (isFlush(cardArray)) {
            return findStraight(cardArray);
        }
        return null;
    }

    static ArrayList<Card> findCardsInARow(ArrayList<Card> cardArray, int ofAKind) {
        Collections.sort(cardArray);
        ArrayList<Card> result = new ArrayList<Card>();
        Card previousCard = getCardOfDifferentRank(cardArray.get(0));

        // For ever card...
        for (Card card : cardArray) {
            // If the previous card's rank equals this card's rank...
            if (previousCard.getRankAsChar().equals(card.getRankAsChar())) {
                // We are on a streak!
                result.add(card);
                // We need to account for the start of a steak.
                if (!result.contains(previousCard))
                    result.add(previousCard);
                // If we found ofAKind card's return them.
                if (result.size() == ofAKind) {
                    return result;
                }
                // If this card and the previous card's ranks are different and this results
                // list is not empty.
            } else if (!result.isEmpty()) {
                // Clear results list bigger streak may be left.
                result = new ArrayList<Card>();
            }
            previousCard = card;
        }
        return null;
    }

    static Card getCardOfDifferentRank(Card card) {
        int currentRank = card.getRank().ordinal();
        if (currentRank == Rank.Ace.ordinal()) {
            return new Card("2C");
        } else {
            return new Card("AC");
        }
    }

    enum order {
        HIGHCARD, PAIR, TWOPAIRS, THREEOFAKIND, STRAIGHT, FLUSH, FULLHOUSE, FOUROFAKIND, STRAIGHTFLUSH, ERROR
    }

}

class Result {
    public order rank;
    public String winnerText;
    public ArrayList<Card> cardsUsed;
};