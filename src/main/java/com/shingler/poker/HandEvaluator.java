package com.shingler.poker;

import java.util.ArrayList;
import java.util.Collections;

import com.shingler.poker.Card.Rank;

public class HandEvaluator {

    private static int handSize = 5;

    public static String evaluate(){
        return "";
    }

    protected static boolean isValidateHand(ArrayList<Card> cardArray){
        if (cardArray.size() != handSize){
            return false;
        }

        // There are no duplicates in the hand.
        Collections.sort(cardArray);
        String compare = "";
        for(Card card : cardArray){
            if (card.toString().equals(compare)) return false;
            compare = card.toString();
        }
        return true;
    }

    protected static Card findHighCard(ArrayList<Card> cardArray){
        Collections.sort(cardArray);
        return cardArray.get(cardArray.size() - 1);
    }

    protected static ArrayList<Card> findPair(ArrayList<Card> cardArray){
        ArrayList<Card> pair = findCardsInARow(cardArray, 2);
        Collections.sort(pair);
        return pair;
    }

    protected static ArrayList<Card> findTwoPairs(ArrayList<Card> cardArray){
        // Find the first pair.
        ArrayList<Card> firstPair = findCardsInARow(cardArray, 2);
        if (firstPair == null) return null;

        // Clone so cardArray is not changed and remove the first pair.
        ArrayList<Card> copy = (ArrayList<Card>)cardArray.clone();
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

    protected static ArrayList<Card> findThreeOfAKind(ArrayList<Card> cardArray){
        ArrayList<Card> threeOfAKind = findCardsInARow(cardArray, 3);
        Collections.sort(threeOfAKind);
        return threeOfAKind;
    }    

    protected static ArrayList<Card> findStraight(ArrayList<Card> cardArray){
        Collections.sort(cardArray);
        int previousRank = -5;
        int currentRank = -5;
        int count = 0;
        // For every card...
        for (Card card : cardArray) {
            // Update the current rank...
            currentRank = card.getRank().ordinal();
            // If the current rank is one higher than the previous rank increment count.
            if(currentRank == previousRank + 1) count++;
            previousRank = currentRank;
        }
        // In this case if its a straight, count must be four.
        return (count == 4) ? cardArray : null ;
    }

    protected static boolean isFlush(ArrayList<Card> cardArray){
        String suit = cardArray.get(0).getSuitAsChar();
        for (Card card : cardArray) {
            if (suit != card.getSuitAsChar()) return false;
        }
        return true; 
    }

    protected static ArrayList<Card> findFullHouse(ArrayList<Card> cardArray){
        // Find the three of a kinda first.
        ArrayList<Card> threeOfAKind = findCardsInARow(cardArray, 3);
        if(threeOfAKind == null){
            return null;
        }

        // Copy so we don't change cardArray and remove the three a kinda.
        ArrayList<Card> copy = (ArrayList<Card>) cardArray.clone();
        copy.removeAll(threeOfAKind);

        // If all that is left is a pair then we have a full house!
        ArrayList<Card> pair = findCardsInARow(copy, 2);
        if(pair != null) {
            pair.addAll(threeOfAKind);
            Collections.sort(pair);
            return pair;
        }
        
        return null;
    }

    protected static ArrayList<Card> findFourOfAKind(ArrayList<Card> cardArray){
        ArrayList<Card> result = findCardsInARow(cardArray, 4);
        Collections.sort(result);
        return result;
    }

    protected static ArrayList<Card> findStraightFlush(ArrayList<Card> cardArray){
        if (isFlush(cardArray)) {
            return findStraight(cardArray);
        }
        return null;
    }

    protected static ArrayList<Card> findCardsInARow(ArrayList<Card> cardArray, int ofAKind){
        Collections.sort(cardArray);
        ArrayList<Card> result = new ArrayList<Card>();
        Card previousCard = getCardOfDifferentRank(cardArray.get(0));

        // For ever card...
        for (Card card : cardArray) {
            // If the previous card's rank equals this card's rank...
            if(previousCard.getRankAsChar().equals(card.getRankAsChar())){
                // We are on a streak!
                result.add(card);
                // We need to account for the start of a steak.
                if(!result.contains(previousCard)) result.add(previousCard);
                // If we found ofAKind card's return them.
                if(result.size() == ofAKind){
                    return result;
                } 
            // If this card and the previous card's ranks are different and this results list is not empty.
            } else if(!result.isEmpty()) {
                // Clear results list bigger streak may be left.
                result = new ArrayList<Card>();
            }
            previousCard = card;
        }
        return null;
    }

    protected static Card getCardOfDifferentRank(Card card){
        int currentRank = card.getRank().ordinal();
        if (currentRank == Rank.ACE.ordinal()){
            return new Card("2C");
        } else {
            return new Card("AC");
        }
    }
}