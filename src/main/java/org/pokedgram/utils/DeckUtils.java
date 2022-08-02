package org.pokedgram.utils;

import org.pokedgram.ConstStrings;
import org.pokedgram.PokedgramBot;

import java.util.*;

import static org.pokedgram.ConstStrings.*;
import static org.pokedgram.utils.RankUtils.checkCardValue;


public class DeckUtils extends PokedgramBot {
    
    public static List<String> deckInit() {

        List<String> cardDeck = new ArrayList<>(52);
        String[] cardSuits = new String[]{"♣", "♦", "♥", "♠"}; //String[] cardSuits = new String[]{"♧", "♢", "♡", "♤"};
        String[] cardRanks = new String[]{"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        //Integer[] cardRanksValue = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}; //♠♥♦♣♤♡♢♧
        //Integer[] cardRanksValue = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 0};

        //System.out.println(List.of(cardSuits) + NEXT_LINE + List.of(cardRanks));

        for (String suit : cardSuits) {
            for (String rank : cardRanks) {
                cardDeck.add(rank + suit);
            }
        }
        return cardDeck;
    }

    public static List<String> deckShuffle(List<String> currentDeck) {
        Collections.shuffle(currentDeck);
        System.out.println("deck shuffled: " + currentDeck);
        //System.out.println("deck shuffled.");
        return currentDeck;
    }

    public static ArrayList<String>[][] deckDeal(int playersQuantity, int playersCardsCount, List<String> deck) {
        //static HashMap<String, String>[][] dealCardsFromDeck(int playersQuantity, int playersCardsCount, List<String> deck) {

            ArrayList<String>[][] playersHand = new ArrayList[ playersQuantity ][ playersCardsCount + 5 ];

            //deal cards to players
            for (int iterateCard = 0; iterateCard < playersCardsCount; iterateCard++) { // deal card loop
                for (int iteratePlayer = 0; iteratePlayer < playersQuantity; iteratePlayer++) { //select player to deal card
                    playersHand[ iteratePlayer ][ iterateCard ] = new ArrayList<>() {
                    };
                    playersHand[ iteratePlayer ][ iterateCard ].add(0, deck.get((iteratePlayer + (iterateCard * playersQuantity)))); // 0 string
                    playersHand[ iteratePlayer ][ iterateCard ].add(1, playersHand[ iteratePlayer ][ iterateCard ].get(0).replaceAll(FIND_CARDS_RANK_REGEXP, EMPTY_STRING)); // 1 suit
                    playersHand[ iteratePlayer ][ iterateCard ].add(2, playersHand[ iteratePlayer ][ iterateCard ].get(0).replaceAll(FIND_CARDS_SUIT_REGEXP, EMPTY_STRING)); // 2 rank
                    playersHand[ iteratePlayer ][ iterateCard ].add(3, String.valueOf(checkCardValue(playersHand[ iteratePlayer ][ iterateCard ].get(2)))); // 3 value

                    //System.out.println("Player " + (y) + " card: " + deck.get((y + (i * playersQuantity))));

                    //put table cards after players cards for easier calculacting
                    // TODO ref-cktor
                    if (iterateCard == 1) {
                        playersHand[ iteratePlayer ][ 2 ] = new ArrayList<>() {};
                        playersHand[ iteratePlayer ][ 3 ] = new ArrayList<>() {};
                        playersHand[ iteratePlayer ][ 4 ] = new ArrayList<>() {};
                        playersHand[ iteratePlayer ][ 5 ] = new ArrayList<>() {};
                        playersHand[ iteratePlayer ][ 6 ] = new ArrayList<>() {};

                        playersHand[ iteratePlayer ][ 2 ].add(0, deck.get((playersQuantity * playersCardsCount + 3)));
                        playersHand[ iteratePlayer ][ 2 ].add(1, deck.get((playersQuantity * playersCardsCount + 3)).replaceAll(FIND_CARDS_RANK_REGEXP, EMPTY_STRING));
                        playersHand[ iteratePlayer ][ 2 ].add(2, deck.get((playersQuantity * playersCardsCount + 3)).replaceAll(FIND_CARDS_SUIT_REGEXP, EMPTY_STRING));
                        playersHand[ iteratePlayer ][ 2 ].add(3, String.valueOf(checkCardValue(playersHand[ iteratePlayer ][ 2 ].get(2))));

                        playersHand[ iteratePlayer ][ 3 ].add(0, deck.get((playersQuantity * playersCardsCount + 3 + 1)));
                        playersHand[ iteratePlayer ][ 3 ].add(1, deck.get((playersQuantity * playersCardsCount + 3 + 1)).replaceAll(FIND_CARDS_RANK_REGEXP, EMPTY_STRING));
                        playersHand[ iteratePlayer ][ 3 ].add(2, deck.get((playersQuantity * playersCardsCount + 3 + 1)).replaceAll(FIND_CARDS_SUIT_REGEXP, EMPTY_STRING));
                        playersHand[ iteratePlayer ][ 3 ].add(3, String.valueOf(checkCardValue(playersHand[ iteratePlayer ][ 3 ].get(2))));

                        playersHand[ iteratePlayer ][ 4 ].add(0, deck.get((playersQuantity * playersCardsCount + 3 + 2)));
                        playersHand[ iteratePlayer ][ 4 ].add(1, deck.get((playersQuantity * playersCardsCount + 3 + 2)).replaceAll(FIND_CARDS_RANK_REGEXP, EMPTY_STRING));
                        playersHand[ iteratePlayer ][ 4 ].add(2, deck.get((playersQuantity * playersCardsCount + 3 + 2)).replaceAll(FIND_CARDS_SUIT_REGEXP, EMPTY_STRING));
                        playersHand[ iteratePlayer ][ 4 ].add(3, String.valueOf(checkCardValue(playersHand[ iteratePlayer ][ 4 ].get(2))));

                        playersHand[ iteratePlayer ][ 5 ].add(dealTurn(2, 2, deck));
                        playersHand[ iteratePlayer ][ 5 ].add(1, dealTurn(2, 2, deck).replaceAll(FIND_CARDS_RANK_REGEXP, EMPTY_STRING));
                        playersHand[ iteratePlayer ][ 5 ].add(2, dealTurn(2, 2, deck).replaceAll(FIND_CARDS_SUIT_REGEXP, EMPTY_STRING));
                        playersHand[ iteratePlayer ][ 5 ].add(3, String.valueOf(checkCardValue(playersHand[ iteratePlayer ][ 5 ].get(2))));

                        playersHand[ iteratePlayer ][ 6 ].add(dealRiver(2, 2, deck));
                        playersHand[ iteratePlayer ][ 6 ].add(1, dealRiver(2, 2, deck).replaceAll(FIND_CARDS_RANK_REGEXP, EMPTY_STRING));
                        playersHand[ iteratePlayer ][ 6 ].add(2, dealRiver(2, 2, deck).replaceAll(FIND_CARDS_SUIT_REGEXP, EMPTY_STRING));
                        playersHand[ iteratePlayer ][ 6 ].add(3, String.valueOf(checkCardValue(playersHand[ iteratePlayer ][ 6 ].get(2))));
                    }

                }
            }

            return playersHand;
        }

        // TODO() check if card burning shifts order correctly
    public static String dealFlop(int playersQuantity, int playersCardsCount, List<?> deck) {
        return deck.get((playersQuantity * playersCardsCount + 3)).toString() +
               deck.get((playersQuantity * playersCardsCount + 3 + 1)).toString() +
               deck.get((playersQuantity * playersCardsCount + 3 + 2)).toString();
    }

    public static String dealTurn(int playersQuantity, int playersCardsCount, List<?> deck) {
        return deck.get((playersQuantity * playersCardsCount + 3 + 2 + 1 + 1)).toString();
    }

    public static String dealRiver(int playersQuantity, int playersCardsCount, List<?> deck) {
        return deck.get((playersQuantity * playersCardsCount + 3 + 2 + 1 + 1 + 1 + 1)).toString();
    }

    public static ArrayList[] dealTable(int playersQuantity, List<?> deck) {


        ArrayList[] tableArray = new ArrayList[ playersQuantity ];//(playersQuantity);
        tableArray[ 0 ] = new ArrayList<>(5);
        tableArray[ 1 ] = new ArrayList<>(5);
        //tableArray[2] = new HashMap(5);
        tableArray[ 0 ].add((dealFlop(playersQuantity, 2, deck) +
                             dealTurn(playersQuantity, 2, deck) +
                             dealRiver(playersQuantity, 2, deck)));
        tableArray[ 1 ].add(tableArray[ 0 ].get(0).toString().replaceAll(FIND_CARDS_RANK_REGEXP, EMPTY_STRING));
        tableArray[ 1 ].add(Arrays.toString(Arrays.stream(tableArray[ 0 ].get(0).toString().replaceAll(FIND_CARDS_SUIT_REGEXP, " ").split("( )")).toArray()));
        System.out.println(tableArray[ 0 ].get(0));
        List<?> tableValue;// = new HashMap();
        tableValue = Arrays.stream(tableArray[ 0 ].get(0).toString().replaceAll(FIND_CARDS_SUIT_REGEXP, " ").split(COMMA)).toList();

        System.out.println("tableValue " + tableValue);
        return tableArray;
    }

    static public String dealCardsText(HashMap[] players, ArrayList<String>[][] playerCards, Integer HAND_CARDS_COUNT) {
        String currentPlayerHandText;
        StringBuilder allHandsText = new StringBuilder(EMPTY_STRING);

        for (int playerNumber = 0; playerNumber < players.length; playerNumber++) {

            StringBuilder hand = new StringBuilder();
            for (int playerCardNumber = 0; playerCardNumber < HAND_CARDS_COUNT; playerCardNumber++) {
                hand.append(playerCards[ playerNumber ][ playerCardNumber ].get(0));
            }

            currentPlayerHandText = players[ playerNumber ].get("userName") + " hand: " + hand + NEXTLINE;
            allHandsText.append(currentPlayerHandText);

        }
        return allHandsText.toString();
    }

}

