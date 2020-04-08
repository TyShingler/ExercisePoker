package com.shingler.poker;

import java.util.Arrays;

public class HandEvaluator {

    public static String evaluate(){
        return "";
    }

    protected static boolean isValidateHand(Card[] cardArray){
        return false;
    }

    protected static Card findHighCard(Card[] cardArray){
        return new Card("2H");
    }

    protected static Card[] findPair(Card[] cardArray){
        return new Card[5];
    }

    protected static Card[] findTwoPairs(Card[] cardArray){
        return new Card[5];
    }

    protected static Card[] findThreeOfAKind(Card[] cardArray){
        return new Card[5];
    }    

    protected static Card[] findStraight(Card[] cardArray){
        return new Card[5];
    }

    protected static boolean isFlush(Card[] cardArray){
        return false; 
    }

    protected static Card[] findFullHouse(Card[] cardArray){
        return new Card[5];    
    }

    protected static Card[] findFourOfAKind(Card[] cardArray){
        return new Card[5];
    }

    protected static Card[] findStraightFlush(Card[] cardArray){
        return new Card[5];
    }


}