package org.pokedgram.utils;

import org.pokedgram.ConstStrings;
import org.pokedgram.PokedgramBot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.pokedgram.ConstStrings.NEXTLINE;

public class RankUtils extends PokedgramBot {
    public static int checkFlash(HashMap[] players, ArrayList[][] playersCards, ArrayList[] table) {
        ArrayList<Integer>[] matchedPlayers = new ArrayList[players.length];//{};
        String playersHandWithTable;
        int checkFlashCount = 0;
        int checkFlashSuit = -1;

        if (table[ 1 ].get(0).toString().matches(ConstStrings.FIND_FLASH_DRAW_CLUBS_REGEXP)) {
            checkFlashSuit = 0;
        } else if (table[ 1 ].get(0).toString().matches(ConstStrings.FIND_FLASH_DRAW_DIAMONDS_REGEXP)) {
            checkFlashSuit = 1;
        } else if (table[ 1 ].get(0).toString().matches(ConstStrings.FIND_FLASH_DRAW_HEARTS_REGEXP)) {
            checkFlashSuit = 2;
        } else if (table[ 1 ].get(0).toString().matches(ConstStrings.FIND_FLASH_DRAW_SPADES_REGEXP)) {
            checkFlashSuit = 3;
        }

        if (checkFlashSuit >= 0) {
            for (int iteratePlayer = 0; iteratePlayer < players.length; iteratePlayer++) {
                playersHandWithTable = playersCards[ iteratePlayer ][ 0 ].get(1).toString() + playersCards[ iteratePlayer ][ 1 ].get(1).toString() + table[ 1 ].get(0).toString();
                // 0 string;  1 suit; 2 rank; 3 value
                if (playersHandWithTable.matches(ConstStrings.FIND_FLASH_REGEXP)) {
                    matchedPlayers[ checkFlashCount ] = new ArrayList<Integer>();
                    matchedPlayers[ checkFlashCount ].add(iteratePlayer);
                    matchedPlayers[ checkFlashCount ].add(Integer.valueOf(players[ iteratePlayer ].get(0).toString()));
                    matchedPlayers[ checkFlashCount ].add(Integer.valueOf(playersHandWithTable));
                    players[ iteratePlayer ].put(ConstStrings.COMBOPOWER_VALUE, 5);
                    checkFlashCount++;
                    System.out.println("checkFlash: " + iteratePlayer + ": " + ((playersCards[ iteratePlayer ][ 0 ].get(1).toString() + playersCards[ iteratePlayer ][ 1 ].get(1).toString() + table[ 1 ].get(0).toString())));
                }
            }

            if (checkFlashCount > 1) {
                for (int matchedFlashCount = 0; matchedFlashCount <= checkFlashCount; matchedFlashCount++) {
                    matchedFlashCount = matchedFlashCount;
                    //playersCards[z][0].get(1) + playersCards[z][1].get(1);
                    //matchedPlayers[z].get(2);

                }
            }
        }

        return checkFlashCount;
    }

    public static Integer checkStraight(ArrayList<String>[][] playersCards) {
        System.out.println("checkStraight not implemented");
        return -1;

    }

    public static int checkDistinct(ArrayList<String>[][] playersCards, HashMap[] players) {
        System.out.println("checkDistinct: start");
        int countCombo = 0;
        int playerAndTableCards = 7;

        for (int iteratePlayer = 0; iteratePlayer < players.length; iteratePlayer++) {

            String[] playersDistinctCards = new String[ 3 ];
            int distinctQuantity = 0;
            //countCombo = 0;
            String excludeSingle = ConstStrings.EMPTY_STRING;
            ArrayList<?>[] currentPlayerCards = PlayerUtils.getPlayerCardsWithTable(iteratePlayer, playersCards, players);
            List<Integer> cardValues = new ArrayList<>();

            System.out.println(NEXTLINE);
            System.out.println("player: " + iteratePlayer);
            for (int iterateAvailableCards = 0; iterateAvailableCards < playerAndTableCards; iterateAvailableCards++) {
                cardValues.add(Integer.parseInt(currentPlayerCards[ 3 ].get(iterateAvailableCards).toString()));
            }

            excludeSingle = cardValues.stream().sorted().collect(Collectors.groupingBy(Function.identity(),
                    Collectors.counting())).toString().replaceAll(ConstStrings.EXTRACT_DISTINCT_VALUES_REGEXP, ConstStrings.EMPTY_STRING);
            distinctQuantity = excludeSingle.replaceAll(ConstStrings.EXTRACT_VALUES_DIRTY_REGEXP, ConstStrings.EMPTY_STRING).length();
            System.out.println("rank = distinct: " + excludeSingle + "; distinctQuantity: " + distinctQuantity);//
            try {
                if (distinctQuantity > 0) {
                    countCombo++;
                    playersDistinctCards[ 0 ] = excludeSingle.replaceAll(ConstStrings.EXTRACT_DISTINCT_COUNT_REGEXP, "$1");
                    if (playersDistinctCards[ 0 ].matches(ConstStrings.FIND_4X_REGEXP)) {
                        System.out.println("matches " + ConstStrings.FIND_4X_REGEXP);
                        players[ iteratePlayer ].put(ConstStrings.COMBOPOWER_VALUE, 7);
                    } else if (playersDistinctCards[ 0 ].matches(ConstStrings.FIND_2X_REGEXP) &&      //full
                               playersDistinctCards[ 0 ].matches(ConstStrings.FIND_3X_REGEXP)) {
                        System.out.println(".matches(FIND_2X_REGEXP).matches(FIND_3X_REGEXP)");
                        players[ iteratePlayer ].put(ConstStrings.COMBOPOWER_VALUE, 6);
                    } else if (playersDistinctCards[ 0 ].matches(ConstStrings.FIND_3X_REGEXP)) {
                        System.out.println("matches(FIND_3X_REGEXP) " + ConstStrings.FIND_3X_REGEXP);
                        players[ iteratePlayer ].put(ConstStrings.COMBOPOWER_VALUE, 3);
                    } else if (playersDistinctCards[ 0 ].matches(ConstStrings.FIND_2X2X_REGEXP)) {
                        System.out.println("matches(FIND_2X2X_REGEXP) " + ConstStrings.FIND_2X2X_REGEXP);
                        players[ iteratePlayer ].put(ConstStrings.COMBOPOWER_VALUE, 2);
                    } else if (playersDistinctCards[ 0 ].matches(ConstStrings.FIND_2X_REGEXP)) {
                        System.out.println("matches(FIND_2X_REGEXP) " + ConstStrings.FIND_2X_REGEXP);
                        players[ iteratePlayer ].put(ConstStrings.COMBOPOWER_VALUE, 1);
                    }

                } else if (distinctQuantity == 0) {

                    System.out.println("distinctQuantity==0 - no combo found");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("checkDistinct: end" + NEXTLINE);
        }
        return countCombo;

    }

    //str fl 8 //quad 7 //fullhouse 6 //flash 5 //straight 4
    // triple 3 //two pair 2 //pair 1 //high card 0

    //playerComboGrade[playerid] = %regex to find combo value%
    //playerKickerCards[playerid] = %check hand cards + different cases
    //if (distinctQuantity==4) {} else
    //                        System.out.println("distinctQuantity==3 " + NEXTLINE + playersDistinctCards[ 0 ]);
    //                    } else if (distinctQuantity == 2) {
    //                        System.out.println("distinctQuantity==2 " + NEXTLINE + playersDistinctCards[ 0 ]);
    //                    } else if (distinctQuantity == 1) {
    //                        System.out.println("distinctQuantity==1 " + NEXTLINE + playersDistinctCards[ 0 ]);
    //                    }

    // else if check kicker
    // kicker = card(s) in players hand to break ties between hands of the same rank

    public static int checkKicker(HashMap[] players, ArrayList<String>[][] playerCards, int comboRank) throws Exception { //get highest card, if highest quantity > 1, return -1

        int handHighest = -1;
        int winnerCount = 0;
        int winnerId = -1;

        if (comboRank == 1 || comboRank == 2 || comboRank == 3 || comboRank == 6 || comboRank == 7) { // check pairs rank, mb another for rank 6 (fullhosue)

        }

        if (comboRank == 5) { // flash, compare with suit first, (?) otherwise split (?)

            // ...
        }

        if (comboRank == 4 || comboRank == 8) { //str & strflash - as a straight

            // ...
        }

        //str fl 8+ //quad 7 //fullhouse 6 //flash 5 //straight 4+ //triple 3 //two pair 2 //pair 1 //high card 0
        //TODO if kickers same - split reward
        //TODO copypaste from coinflip, here need set stats in players[].(13,
        if (comboRank == 0) { // if highest card equal  compare second
            handHighest = -1;
            for (int iteratePlayer = 0; iteratePlayer < players.length; iteratePlayer++) {
                for (int iterateCard = 0; iterateCard < ConstStrings.HAND_CARDS_COUNT; iterateCard++) {
                    if (handHighest < Integer.parseInt(playerCards[ iteratePlayer ][ iterateCard ].get(3))) {
                        handHighest = Integer.parseInt(playerCards[ iteratePlayer ][ iterateCard ].get(3));
                        winnerCount = 1;
                        winnerId    = iteratePlayer;
                        int cardValue = Integer.parseInt(playerCards[ iteratePlayer ][ iterateCard ].get(3));
                    } else if (handHighest == Integer.parseInt(playerCards[ iteratePlayer ][ iterateCard ].get(3))) {
                        winnerCount++;
                        winnerId = -1;
                    }
                }
            }
        }

        if (winnerId > -1) {
            if (winnerCount == 1) {
                return winnerId;
            }

            if (winnerCount == 2) {
                Exception E = new Exception("no kicker found, ");
                throw E;
                //return winnerId;
            }
        } else {
            return -1;
        }

        return winnerId;
    }

    public static int checkCardValue(String card) {
        int cardValue;
        if (Objects.equals(card, "A")) {
            cardValue = 14; // or 1? for str
        } else if (Objects.equals(card, "K")) {
            cardValue = 13;
        } else if (Objects.equals(card, "Q")) {
            cardValue = 12;
        } else if (Objects.equals(card, "J")) {
            cardValue = 11;
        } else {
            cardValue = Integer.parseInt(card);
        }
        return cardValue;
    }

    public static int checkCardRankPregame(int playersQuantity, ArrayList<String>[][] playerCards) {
        //get highest card, if highest quantity > 1, return -1
        int handHighest = -1;
        int winnerCount = 0;
        int winnerId = -1;
        for (int iteratePlayer = 0; iteratePlayer < playersQuantity; iteratePlayer++) {
            // for (int i = 0; i < playersCardsCount; i++) {
            if (handHighest < Integer.parseInt(playerCards[ iteratePlayer ][ 0 ].get(3))) {
                handHighest = Integer.parseInt(playerCards[ iteratePlayer ][ 0 ].get(3));
                winnerCount = 1;
                winnerId    = iteratePlayer;
                // }
            } else if (handHighest == Integer.parseInt(playerCards[ iteratePlayer ][ 0 ].get(3))) {
                winnerCount++;
                winnerId = -1;
            }
        }
        if (winnerCount == 1 && winnerId > -1) {
            return winnerId;
        } else {
            return -1;
        }
    }
}
