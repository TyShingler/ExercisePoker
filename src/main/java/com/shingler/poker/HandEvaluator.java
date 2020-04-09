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
        ArrayList<Card> pair = cardsInARow(cardArray, 2);
        Collections.sort(pair);
        return pair;
    }

    protected static ArrayList<Card> findTwoPairs(ArrayList<Card> cardArray){
        ArrayList<Card> firstPair = cardsInARow(cardArray, 2);

        if (firstPair == null) return null;
        ArrayList<Card> copy = cardArray;

        copy.removeAll(firstPair);

        ArrayList<Card> secondPair = cardsInARow(cardArray, 2);
        if (secondPair != null) {
            firstPair.addAll(secondPair);
            Collections.sort(firstPair);
            return firstPair;
        }
        return null;
    }

    protected static ArrayList<Card> findThreeOfAKind(ArrayList<Card> cardArray){
        ArrayList<Card> threeOfAKind = cardsInARow(cardArray, 3);
        Collections.sort(threeOfAKind);
        return threeOfAKind;
    }    

    protected static ArrayList<Card> findStraight(ArrayList<Card> cardArray){
        Collections.sort(cardArray);
        int previousRank = -5;
        int currentRank = -5;
        int count = 0;
        for (Card card : cardArray) {
            currentRank = card.getRank().ordinal();
            if(currentRank == previousRank + 1) count++;
            previousRank = currentRank;
        }

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
        ArrayList<Card> threeOfAKind = cardsInARow(cardArray, 3);
        if(threeOfAKind == null){
            return null;
        }

        ArrayList<Card> copy = (ArrayList<Card>) cardArray.clone();
        copy.removeAll(threeOfAKind);

        ArrayList<Card> pair = cardsInARow(copy, 2);
        if(pair != null) {
            pair.addAll(threeOfAKind);
            Collections.sort(pair);
            return pair;
        }
        
        return null;
    }

    protected static ArrayList<Card> findFourOfAKind(ArrayList<Card> cardArray){
        ArrayList<Card> result = cardsInARow(cardArray, 4);
        Collections.sort(result);
        return result;
    }

    protected static ArrayList<Card> findStraightFlush(ArrayList<Card> cardArray){
        if (isFlush(cardArray)) {
            return findStraight(cardArray);
        }
        return null;
    }

    protected static ArrayList<Card> cardsInARow(ArrayList<Card> cardArray, int inARow){
        Collections.sort(cardArray);
        ArrayList<Card> result = new ArrayList<Card>();
        Card previousCard = getCardOfDifferentRank(cardArray.get(0));
        for (Card card : cardArray) {
            if(previousCard.getRankAsChar().equals(card.getRankAsChar())){
                result.add(card);
                if(!result.contains(previousCard)) result.add(previousCard);
                if(result.size() == inARow){
                    return result;
                } 
            } else if(!result.isEmpty()) {
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