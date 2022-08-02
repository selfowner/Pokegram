package org.pokedgram;

import org.pokedgram.utils.RankUtils;
import org.pokedgram.utils.DeckUtils;
import org.pokedgram.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.pokedgram.utils.DeckUtils.*;
import static org.pokedgram.ConstStrings.*;
import static org.pokedgram.utils.PlayerUtils.*;

public class RankTests extends PokedgramBot  {
    public static HashMap[] getShowdownWinnerIdTest(HashMap[] playerDataIngame, ArrayList<String>[][] playerCards, List<?> cardDeck) {
        int winnerPlayerNumber, winnerCount= -1;

        //ArrayList[] tableArray = drawTable(playerDataIngame.length, cardDeck);


        //if (false) {
        //sidepot (need >2 playerDataIngame)
        //}
        boolean gotWinner = false;

        //calculate winner and process chips
        while (!gotWinner) {

            //str fl 8 //quad 7 //fullhouse 6 //flash 5 //straight 4 //triple 3 //two pair 2 //pair 1 //high card 0

            //System.out.println("findWinner start");


            // TODO count kickers on table for some cases
            winnerCount += checkDistinctTest(playerCards, playerDataIngame);
            //winnerCount += checkFlash(playerDataIngame, playerCards, tableArray);
            winnerCount += RankUtils.checkStraight(playerCards);

            System.out.println("checkDistinct winnerCount after checkDistinct checkFlash checkStraight: " + winnerCount);


            if (Integer.parseInt(playerDataIngame[ 0 ].get(COMBOPOWER_VALUE).toString()) > Integer.parseInt(playerDataIngame[ 1 ].get(COMBOPOWER_VALUE).toString())) {
                System.out.println("player 0 win");
                winnerPlayerNumber = 0;
            } else if (Integer.parseInt(playerDataIngame[ 1 ].get(COMBOPOWER_VALUE).toString()) > Integer.parseInt(playerDataIngame[ 0 ].get(COMBOPOWER_VALUE).toString())){

                System.out.println("player 1 win");
                winnerPlayerNumber = 1;
            } else if ( // comboValue ==
                    Integer.parseInt(playerDataIngame[ 0 ].get(COMBOPOWER_VALUE).toString()) == Integer.parseInt(playerDataIngame[ 1 ].get(COMBOPOWER_VALUE).toString()) && (Integer.parseInt(playerDataIngame[ 0 ].get(COMBOPOWER_VALUE).toString()) > -1 && Integer.parseInt(playerDataIngame[ 1 ].get(COMBOPOWER_VALUE).toString()) > -1)
            ) {
                System.out.println("playerDataIngametie, checking kicker:");
                try {
                    winnerCount = RankUtils.checkKicker(playerDataIngame, playerCards,0);
                } catch (Exception e) {
                    winnerCount = 0;
                    e.printStackTrace();
                    System.out.println("kickers tie! pot split between playerDataIngame");
                }
            }

//            if (winnerCount == 0) {
//                System.out.println("checkDistinct winnerCount: " + winnerCount);
//
//                gotWinner = true;
//            }

            //System.out.println("findWinner end");
            break;
        }

        int playerIncome = getSplitRewardSize(currentPot,playerDataIngame);
        //find kicker or split reward
        if (winnerCount == 1) {
            //get winnerId
        } else if (winnerCount > 1) {
            for (HashMap player: playerDataIngame) {
                player.put(CHIPS_VALUE, Integer.parseInt(player.get(CHIPS_VALUE).toString()) + playerIncome);
                currentPot = 0;
            }
            System.out.println("winnerCount > 1; income "  + playerIncome);
            gotWinner = true;
            // process chips
        } else if (winnerCount == 0) {

            System.out.println("winnerCount == 0; no combo found. Splitting pot, setting gotWinner true "  + playerIncome);

            for (HashMap player: playerDataIngame) {
                player.put(CHIPS_VALUE, Integer.parseInt(player.get(CHIPS_VALUE).toString()) + playerIncome);
                currentPot = 0;
                gotWinner  = true;
                break;
            }
        }
        //messageSendToPlayingRoom(outputTest);
        return playerDataIngame;
    }
    static int checkDistinctTest(ArrayList<String>[][] playersCards, HashMap[] playerDataIngame) {
        //messageSendToPlayingRoom("checkDistinct: start");
        int countCombo = 0;
        int playerAndTableCards = 7;

        for (int iteratePlayer = 0; iteratePlayer < playerDataIngame.length; iteratePlayer++) {

            String[] playersDistinctCards = new String[ 3 ];
            int distinctQuantity = 0;
            String excludeSingle;
            ArrayList<?>[] currentPlayerCards = PlayerUtils.getPlayerCardsWithTable(iteratePlayer, playersCards, playerDataIngame);
            List<Integer> cardValues = new ArrayList<>();

            for (int iterateAvailableCards = 0; iterateAvailableCards < playerAndTableCards; iterateAvailableCards++) {

                cardValues.add(Integer.parseInt(currentPlayerCards[ 3 ].get(iterateAvailableCards).toString()));
            }

            excludeSingle = cardValues.stream().sorted().collect(Collectors.groupingBy(Function.identity(),
                    Collectors.counting())).toString().replaceAll(EXTRACT_DISTINCT_VALUES_REGEXP, EMPTY_STRING);

            distinctQuantity = excludeSingle.replaceAll(EXTRACT_VALUES_DIRTY_REGEXP, EMPTY_STRING).length();
            //messageSendToPlayingRoom("player: " + iteratePlayer + NEXTLINE + "rank = distinct: " + excludeSingle + "; distinctQuantity: " + distinctQuantity);//
            System.out.println(countCombo + NEXTLINE + "player: " + iteratePlayer + NEXTLINE + "rank = distinct: " + excludeSingle + "; distinctQuantity: " + distinctQuantity);
            try {

                if (distinctQuantity > 0) {
                    countCombo++;
                    playersDistinctCards[ 0 ] = excludeSingle.replaceAll(EXTRACT_DISTINCT_COUNT_REGEXP, "$1");
                    //str fl 8 //quad 7 //fullhouse 6 //flash 5 //straight 4 //triple 3 //two pair 2 //pair 1 //high card 0
                    if (playersDistinctCards[ 0 ].matches(FIND_4X_REGEXP)) {
                        System.out.println("matches " + FIND_4X_REGEXP);
                        playerDataIngame[ iteratePlayer ].put(COMBOPOWER_VALUE, 7);
                        //playerComboGrade[playerid] = %regex to find combo value%
                        //playerKickerCards[playerid] = %check hand cards + different cases
                    } else //4 of a kind
                        if (playersDistinctCards[ 0 ].matches(FIND_2X_REGEXP) &&      //full
                            playersDistinctCards[ 0 ].matches(FIND_3X_REGEXP)) {
                            System.out.println(".matches(\"^.*=[3].*$\").matches(\"^.*=[2].*$\")");

                            playerDataIngame[ iteratePlayer ].put(COMBOPOWER_VALUE, 6);
                        } else//house

                            if (playersDistinctCards[ 0 ].matches(FIND_3X_REGEXP)) {
                                System.out.println("matches " + FIND_3X_REGEXP);
                                playerDataIngame[ iteratePlayer ].put(COMBOPOWER_VALUE, 3);
                            } else//x3

                                if (playersDistinctCards[ 0 ].matches(FIND_2X2X_REGEXP)) {
                                    System.out.println("matches " + FIND_2X2X_REGEXP);

                                    playerDataIngame[ iteratePlayer ].put(COMBOPOWER_VALUE, 2);
                                } else//2x2

                                    if (playersDistinctCards[ 0 ].matches(FIND_2X_REGEXP)) {
                                        System.out.println("matches " + FIND_2X_REGEXP);

                                        playerDataIngame[ iteratePlayer ].put(COMBOPOWER_VALUE, 1);
                                    }  //x2


                    //else if check kicker
                    //if (distinctQuantity==4) {} else
                    //if (distinctQuantity == 3) {
                    //check 4x
                    //check 3x+2x
                    //check 2x + 2x
                    //check 2x
                    //
                    //           messageSendToPlayingRoom("distinctQuantity==3 " + NEXT_LINE + playersDistinctCards[ 0 ]);
                    // } else if (distinctQuantity == 2) {
                    //            messageSendToPlayingRoom("distinctQuantity==2 " + NEXT_LINE + playersDistinctCards[ 0 ]);
                    //} else if (distinctQuantity == 1) {
                    //                messageSendToPlayingRoom("distinctQuantity==1 " + NEXT_LINE + playersDistinctCards[ 0 ]);
                    //}


                    //} else { //0
                    //messageSendToPlayingRoom("distinctQuantity==0 - no combo found");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //messageSendToPlayingRoom("checkDistinct: end");
        }
        return countCombo;
    }

    public static List<String> prepareTestDataCardDeck3x2xand2x2x2x() {
        ArrayList<String> cardDeck = new ArrayList<>(52);
        cardDeck.add("6♥");
        cardDeck.add("5♣");
        cardDeck.add("5♥");
        cardDeck.add("5♦");
        cardDeck.add("6♣");
        cardDeck.add("6♦");
        cardDeck.add("3♣");
        cardDeck.add("K♣"); // burn
        cardDeck.add("6♠");
        cardDeck.add("K♠"); // burn
        cardDeck.add("8♣");
        cardDeck.add("J♣");
        cardDeck.add("Q♣");
        cardDeck.add("5♣");
        cardDeck.add("3♣");
        cardDeck.add("4♣");
        cardDeck.add("J♠");
        cardDeck.add("Q♠");
        cardDeck.add("A♠");
        return cardDeck;
    }
    public static List<String> prepareTestDataCardDeck4x() {
        List<String> cardDeck = new ArrayList<>(52);
        cardDeck.add("6♥");
        cardDeck.add("K♥");
        cardDeck.add("5♣");
        cardDeck.add("K♦");
        cardDeck.add("6♣");
        cardDeck.add("6♦");
        cardDeck.add("3♣"); // burn
        cardDeck.add("K♣");
        cardDeck.add("6♠");
        cardDeck.add("K♠"); // burn
        cardDeck.add("8♣");
        cardDeck.add("J♣");
        cardDeck.add("Q♣");
        cardDeck.add("5♣");
        cardDeck.add("3♣");
        cardDeck.add("4♣");
        cardDeck.add("J♠");
        cardDeck.add("Q♠");
        cardDeck.add("A♠");
        return cardDeck;
    }

    //TODO consider elements type
    public static HashMap[] prepareTestDataPlayers() {
        HashMap[] playerDataTest= new HashMap[ 2 ];

        playerDataTest[ 0 ] = new HashMap<>() {};
        playerDataTest[ 0 ].put(USER_ID, userId); //0 string userId
        playerDataTest[ 0 ].put(USER_NAME, userName); //1 string user firstname + lastname
        playerDataTest[ 0 ].put(USER_NICKNAME, nickName); //2
        playerDataTest[ 0 ].put(CHIPS_VALUE, 20000); //3
        playerDataTest[ 0 ].put(ISACTIVE, true); //4 isActive for reg/unreg
        playerDataTest[ 0 ].put(FOLD_FLAG, false); //5
        playerDataTest[ 0 ].put(ALLIN_FLAG, false); //6
        playerDataTest[ 0 ].put(RAISE_FLAG, false); //7 isChipleader?
        playerDataTest[ 0 ].put(CHECK_FLAG, false); //12 check this stage, true = active
        playerDataTest[ 0 ].put(BET_STAGE, 0); //8
        playerDataTest[ 0 ].put(BET_ROUND, 0); //9
        playerDataTest[ 0 ].put(COMBOPOWER_VALUE, -1); //13 str fl 8 //quad 7 //fullhouse 6 //flash 5 //straight 4 //triple 3 //two pair 2 //pair 1 //high card 0
        playerDataTest[ 0 ].put(KICKER1_VALUE, -1); //14
        playerDataTest[ 0 ].put(KICKER2_VALUE, -1); //15
        playerDataTest[ 0 ].put(ISAUTOALLINONBLIND, 0); //10 isAutoAllinOnBlind
        playerDataTest[ 0 ].put(PLACEONTABLEFINISHED, -1); //11 placeOnTableFinished, -1 = ingame

        playerDataTest[ 1 ] = new HashMap<>() {};
        playerDataTest[ 1 ].put(USER_ID, userId); //0 string userId
        playerDataTest[ 1 ].put(USER_NAME, userName); //1 string user firstname + lastname
        playerDataTest[ 1 ].put(USER_NICKNAME, nickName); //2
        playerDataTest[ 1 ].put(CHIPS_VALUE, 20000); //3 chipsQuantity
        playerDataTest[ 1 ].put(ISACTIVE, true); //4 isActive for reg/unreg
        playerDataTest[ 1 ].put(FOLD_FLAG, false); //5 FoldFlag
        playerDataTest[ 1 ].put(ALLIN_FLAG, false); //6 AllinFlag manual
        playerDataTest[ 1 ].put(RAISE_FLAG, false); //7 isChipleader?
        playerDataTest[ 1 ].put(CHECK_FLAG, false); //12 check this stage, true = active
        playerDataTest[ 1 ].put(BET_STAGE, 0); //8 currentStageBet
        playerDataTest[ 1 ].put(BET_ROUND, 0); //9 currentRoundBet
        playerDataTest[ 1 ].put(COMBOPOWER_VALUE, -1); //13 str fl 8 //quad 7 //fullhouse 6 //flash 5 //straight 4 //triple 3 //two pair 2 //pair 1 //high card 0
        playerDataTest[ 1 ].put(KICKER1_VALUE, -1); //14
        playerDataTest[ 1 ].put(KICKER2_VALUE, -1); //15
        playerDataTest[ 1 ].put(ISAUTOALLINONBLIND, 0); //10 isAutoAllinOnBlind
        playerDataTest[ 1 ].put(PLACEONTABLEFINISHED, -1); //11 placeOnTableFinished, -1 = ingame


        return playerDataTest;
    }


    public String getCurrentStageTableCardsTextTest(Integer currentPlayerStage, List<String> cardDeck) {
        String currentTableCards = EMPTY_STRING;
        String flopCards  = dealFlop(playerDataIngame.length, HAND_CARDS_COUNT, cardDeck);
        String turnCards  = flopCards + DeckUtils.dealTurn(playerDataIngame.length, HAND_CARDS_COUNT, cardDeck);
        String riverCards = turnCards + DeckUtils.dealRiver(playerDataIngame.length, HAND_CARDS_COUNT, cardDeck);

        return riverCards;
    }

}

