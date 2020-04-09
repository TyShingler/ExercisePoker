package com.shingler.poker;

import java.util.ArrayList;
import java.util.Collections;

import com.shingler.poker.Card.Rank;
import com.shingler.poker.HandEvaluator.order;

public class HandEvaluator {

    private static int handSize = 5;

    public static String evaluate(ArrayList<Card> black, ArrayList<Card> white) {
        ArrayList<Card> validityCheck = (ArrayList<Card>) black.clone();
        validityCheck.addAll(white);
        if (!validateHands(validityCheck)) {
            return "[Error] Invalid hands to compare. Two hands can't have duplicate cards between them.";
        }

        Result blackResult = getEvaluation(black);
        Result whitResult = getEvaluation(white);

        if (blackResult.rank.ordinal() > whitResult.rank.ordinal()) {
            return "Black wins. -" + blackResult.winnerText;
        }
        return "White wins. -" + whitResult.winnerText;
    }

    static Result getEvaluation(ArrayList<Card> handIn) {
        ArrayList<Card> hand = new ArrayList<Card>();
        if ((hand = findStraightFlush(handIn)) != null) {
            Result result = new Result();
            result.rank = order.STRAIGHTFLUSH;
            result.winnerText = "with straight flush: " + cardArrayToString(hand);
            return result;
        } else if ((hand = findFourOfAKind(handIn)) != null) {
            Result result = new Result();
            result.rank = order.FOUROFAKIND;
            result.winnerText = "with four of a kind: " + cardArrayToString(hand);
            return result;
        } else if ((hand = findFullHouse(handIn)) != null) {
            Result result = new Result();
            result.rank = order.FULLHOUSE;
            result.winnerText = "with full house: " + cardArrayToString(hand);
            return result;
        } else if (isFlush(handIn)) {
            Result result = new Result();
            result.rank = order.FLUSH;
            result.winnerText = "with flush: " + cardArrayToString(handIn);
            return result;
        } else if ((hand = findStraight(handIn)) != null) {
            Result result = new Result();
            result.rank = order.STRAIGHT;
            result.winnerText = "with straight: " + cardArrayToString(hand);
            return result;
        } else if ((hand = findThreeOfAKind(handIn)) != null) {
            Result result = new Result();
            result.rank = order.THREEOFAKIND;
            result.winnerText = "with three of a kind: " + cardArrayToString(hand);
            return result;
        } else if ((hand = findTwoPairs(handIn)) != null) {
            Result result = new Result();
            result.rank = order.TWOPAIRS;
            result.winnerText = "with two pair: " + cardArrayToString(hand);
            return result;
        } else if ((hand = findPair(handIn)) != null) {
            Result result = new Result();
            result.rank = order.PAIR;
            result.winnerText = "with pair: " + cardArrayToString(hand);
            return result;
        } else if (findHighCard(handIn) != null) {
            Result result = new Result();
            result.rank = order.HIGHCARD;
            result.winnerText = "with high card: " + findHighCard(handIn).toString();
            return result;
        }
        Result result = new Result();
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

    private static boolean hasRepeatingCards(ArrayList<Card> cardArray){
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

    static Card findHighCard(ArrayList<Card> cardArray) {
        Collections.sort(cardArray);
        return cardArray.get(cardArray.size() - 1);
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
        if (currentRank == Rank.ACE.ordinal()) {
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
};