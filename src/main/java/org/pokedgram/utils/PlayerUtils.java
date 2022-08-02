package org.pokedgram.utils;

import org.pokedgram.ConstStrings;
import org.pokedgram.PokedgramBot;

import java.util.*;
import java.util.stream.IntStream;

import static org.pokedgram.ConstStrings.*;

public class PlayerUtils extends PokedgramBot {
    //TODO create type "Player"
    public static HashMap[] addPlayerToQueue(String userId, String userName, HashMap[] playerDataPregame, String userNick, int registeredPlayers, int chips, boolean ifFull) {

        int updateId = PlayerUtils.checkUserRegExistAndActive(userId, playerDataPregame, registeredPlayers);
        int playerRegId;
        if (updateId > -1) {
            playerRegId = updateId;
        } else {
            playerRegId = registeredPlayers;
        }

        if (!ifFull) {
            //playerInfoPregame[ playerRegId ] = new HashMap<>() {};
            playerDataPregame[ playerRegId ] = new HashMap<>();
            playerDataPregame[ playerRegId ].put(USER_ID, userId); //0 string userId
            playerDataPregame[ playerRegId ].put(USER_NAME, userName); //1 string user firstname + lastname
            playerDataPregame[ playerRegId ].put(USER_NICKNAME, userNick); //2
            playerDataPregame[ playerRegId ].put(CHIPS_VALUE, chips); //3 chipsQuantity
            playerDataPregame[ playerRegId ].put(ISACTIVE, 1); //4 isActive for reg/unreg
            playerDataPregame[ playerRegId ].put(FOLD_FLAG, 0); //5 FoldFlag
            playerDataPregame[ playerRegId ].put(ALLIN_FLAG, 0); //6 AllinFlag manual
            playerDataPregame[ playerRegId ].put(RAISE_FLAG, 0); //7 isChipleader?
            playerDataPregame[ playerRegId ].put(CHECK_FLAG, 0); //12 check this stage, true = active
            playerDataPregame[ playerRegId ].put(BET_STAGE, 0); //8 currentStageBet
            playerDataPregame[ playerRegId ].put(BET_ROUND, 0); //9 currentRoundBet
            playerDataPregame[ playerRegId ].put(COMBOPOWER_VALUE, -1); //13 str fl 8 //quad 7 //fullhouse 6 //flash 5 //straight 4 //triple 3 //two pair 2 //pair 1 //high card 0
            playerDataPregame[ playerRegId ].put(KICKER1_VALUE, -1); //14
            playerDataPregame[ playerRegId ].put(KICKER2_VALUE, -1); //15
            playerDataPregame[ playerRegId ].put(ISAUTOALLINONBLIND, 0); //10 isAutoAllinOnBlind
            playerDataPregame[ playerRegId ].put(PLACEONTABLEFINISHED, -1); //11 placeOnTableFinished, -1 = ingame


        }
        return playerDataPregame;
    }

    public static void clearPlayerBets(HashMap[] playerDataIngame) {
        for (HashMap player : playerDataIngame) {
            player.put(BET_ROUND, 0);
            player.put(BET_STAGE, 0);
        }
    }

    public static void processPlayerBetsFromStageToRound(HashMap[] playerDataIngame) {
        for (HashMap player : playerDataIngame) {
            player.put(BET_ROUND, Integer.parseInt(player.get(BET_ROUND).toString()) + Integer.parseInt(player.get(BET_STAGE).toString()));
            player.put(BET_STAGE, 0);
        }
    }

    public static void setPlayerStageFlag(HashMap[] playerDataIngame, int moveCount, String flag) {
        if (flag.equals("check flag")) {
            playerDataIngame[ moveCount ].put(CHECK_FLAG, 1);
            return;
        }

        if (flag.equals("fold flag")) {
            playerDataIngame[ moveCount ].put(FOLD_FLAG, 1);
            return;
        }

        if (flag.equals("allin flag")) {
            playerDataIngame[ moveCount ].put(ALLIN_FLAG, 1);
        }
        if (flag.equals("raise flag")) {
            playerDataIngame[ moveCount ].put(RAISE_FLAG, 1);
        }

    }


    public static void unsetRoundResult(HashMap[] playerDataIngame) {
        //for (ArrayList player : playerDataIngame) {
        for (int playerNumber = 0; playerNumber < playerDataIngame.length; playerNumber++) {
            playerDataIngame[ playerNumber ].put(COMBOPOWER_VALUE, -1);
            System.out.println("unsetRoundResult: " + playerNumber + NEXTLINE + playerDataIngame[ playerNumber ].get(COMBOPOWER_VALUE));
        }
    }


    public static void unsetAllPlayerStageFlag(HashMap[] playerDataIngame, String flag) {

        System.out.println("unsetAllPlayerStageFlag: ");
        //for (ArrayList player : playerDataIngame) {
        for (int playerNumber = 0; playerNumber < playerDataIngame.length; playerNumber++) {
            if (flag.equals("check flag")) {
                playerDataIngame[ playerNumber ].put(CHECK_FLAG, 0);
                System.out.println("unset check flag (12): player " + playerNumber + NEXTLINE + playerDataIngame[ playerNumber ].get(CHECK_FLAG));
            }

            if (flag.equals("fold flag")) {
                playerDataIngame[ playerNumber ].put(FOLD_FLAG, 0);
                System.out.println("unset fold flag (5): player " + playerNumber + NEXTLINE + playerDataIngame[ playerNumber ].get(FOLD_FLAG));
            }

            if (flag.equals("allin flag")) {
                playerDataIngame[ playerNumber ].put(ALLIN_FLAG, 0);
                System.out.println("unset allin flag (6): player " + playerNumber + NEXTLINE + playerDataIngame[ playerNumber ].get(ALLIN_FLAG));
            }

            if (flag.equals("bets flag")) {
                playerDataIngame[ playerNumber ].put(ALLIN_FLAG, 0);
                System.out.println("unset bets flag (6): player " + playerNumber + NEXTLINE + playerDataIngame[ playerNumber ].get(ALLIN_FLAG));
            }

            if (flag.equals("clean all")) {
                playerDataIngame[ playerNumber ].put(ALLIN_FLAG, 0);
                playerDataIngame[ playerNumber ].put(FOLD_FLAG, 0);
                playerDataIngame[ playerNumber ].put(CHECK_FLAG, 0);
                System.out.println(
                        "unset clean all (6 5 12): player " + playerNumber + NEXTLINE +
                        playerDataIngame[ playerNumber ].get(ALLIN_FLAG) + NEXTLINE +
                        playerDataIngame[ playerNumber ].get(FOLD_FLAG) + NEXTLINE +
                        playerDataIngame[ playerNumber ].get(CHECK_FLAG)
                );
                //playerDataIngame[ i ].put(COMBOPOWER_VALUE, -1);
            }
        }
    }

    public static boolean unsetPlayerStageFlag(HashMap[] playerDataIngame, int moveCount, String flag) {
        if (flag.equals("check flag")) {
            playerDataIngame[ moveCount ].put(CHECK_FLAG, 0);
            return true;
        }

        if (flag.equals("fold flag")) {
            playerDataIngame[ moveCount ].put(FOLD_FLAG, 0);
            return true;
        }

        if (flag.equals("allin flag")) {
            playerDataIngame[ moveCount ].put(ALLIN_FLAG, 0);
            return true;
        }

        if (flag.equals("clean all")) {
            playerDataIngame[ moveCount ].put(ALLIN_FLAG, 0);
            playerDataIngame[ moveCount ].put(FOLD_FLAG, 0);
            playerDataIngame[ moveCount ].put(CHECK_FLAG, 0);
            return true;
        }
        return false;
    }


    public static int checkFoldsQuantityThisRound(HashMap[] playerDataIngameList) {
        int foldCount = 0;
        for (int iteratePlayer = playerDataIngameList.length - 1; iteratePlayer >= 0; iteratePlayer--) {
            if (Objects.equals(playerDataIngameList[ iteratePlayer ].get(FOLD_FLAG).toString(), "1")) { // fold flag
                foldCount++;
            }

        }
        return foldCount;
    }

    public static int checkAllinsQuantityThisRound(HashMap[] playerDataIngameList) {
        int allinCount = 0;
        for (int iteratePlayer = playerDataIngameList.length - 1; iteratePlayer >= 0; iteratePlayer--) {
            if (Objects.equals(playerDataIngameList[ iteratePlayer ].get(ALLIN_FLAG).toString(), "1")) { // fold flag
                allinCount++;
            }
        }
        return allinCount;
    }

//get current bets
/*    public static String getCurrentBets(ArrayList[] playerDataIngame) {
        String output = EMPTY_STRING;

        new String(
                "1." + playerDataIngame[ smallBlindId ].get(2) + " bet: " + playerDataIngame[ smallBlindId ].get(BET_ROUND) + ". " +
                "Balance " + playerDataIngame[ smallBlindId ].get(CHIPS_VALUE) + NEXT_LINE +
                "2." + playerDataIngame[ bigBlindId ].get(2) + " bet: " + playerDataIngame[ bigBlindId ].get(BET_ROUND) + ". " +
                "Balance " + playerDataIngame[ bigBlindId ].get(CHIPS_VALUE));

        for (int i = 0; i < playerDataIngame.length; i++) {

            output += playerDataIngame[ smallBlindId ].get(2);



            } else {
                //all ok
            }
        }

        return playerDataIngame;


        return output;
    }*/

    public static HashMap[] checkLastChips(HashMap[] playerDataIngame, int bigBlindSize) {
        for (int iteratePlayer = 0; iteratePlayer < playerDataIngame.length; iteratePlayer++) {
            if (Integer.parseInt(playerDataIngame[ iteratePlayer ].get(CHIPS_VALUE).toString()) < bigBlindSize) {
                playerDataIngame[ iteratePlayer ].put(ISAUTOALLINONBLIND, 1);
                if (Integer.parseInt(playerDataIngame[ iteratePlayer ].get(CHIPS_VALUE).toString()) < bigBlindSize / 2) {
                    playerDataIngame[ iteratePlayer ].put(ISAUTOALLINONBLIND, 2);
                }
                if (Integer.parseInt(playerDataIngame[ iteratePlayer ].get(CHIPS_VALUE).toString()) == 0) {
                    playerDataIngame[ iteratePlayer ].put(ISAUTOALLINONBLIND, iteratePlayer);
                }
            } else {
                //all ok
            }
        }
        return playerDataIngame;
    }

    public static ArrayList<?>[] removeZeroBalance(ArrayList<Integer>[] playerDataIngame) {
        IntStream.range(0, playerDataIngame.length).filter(i -> !playerDataIngame[ i ].isEmpty() && Integer.parseInt(playerDataIngame[ i ].get(Integer.parseInt(ISAUTOALLINONBLIND)).toString()) > -1).forEach(i -> playerDataIngame[ i ] = null);

        //without refactor
//        for (int i = 0; i < playerDataIngame.length; i++) {
//            if (!playerDataIngame[ i ].isEmpty() && Integer.parseInt(playerDataIngame[ i ].get(ISAUTOALLINONBLIND).toString()) > -1) {
//                playerDataIngame[ i ] = null;
//            }
//        }
//        return playerDataIngame;

        return playerDataIngame;
    }

    public static int findMaxBet(HashMap[] playerDataIngameList) {


        int maxBet = 0;
        for (HashMap<? extends Object, ? extends Object> objects : playerDataIngameList) {
            if (maxBet <= Integer.parseInt(objects.get(BET_ROUND).toString())) {
                maxBet = Integer.parseInt(objects.get(BET_ROUND).toString());
            }
        }


        return maxBet;
    }

    public static boolean checkBetsEqual(HashMap[] playerDataIngameList) {

        int foldCount = checkFoldsQuantityThisRound(playerDataIngameList);
        int allinCount = checkAllinsQuantityThisRound(playerDataIngameList);
        int maxBet = 0;
        int maxBetCount = 0;
        for (HashMap<? extends Object, ? extends Object> objects : playerDataIngameList) {
            if (maxBet == Integer.parseInt(objects.get(BET_ROUND).toString())) {

                maxBetCount++;
            }

            if (maxBet < Integer.parseInt(objects.get(BET_ROUND).toString())) {

                maxBetCount = 0;
                maxBet      = Integer.parseInt(objects.get(BET_ROUND).toString());
                maxBetCount++;
            }
        }

        //System.out.println("checkBetsEqual - foldCount: " + foldCount + "\nallinCount: " + allinCount + "\nmaxBet:
        // " + maxBet + "\nmaxBetCount: " + maxBetCount);
        if (maxBetCount + foldCount >= playerDataIngameList.length || allinCount == (maxBetCount + foldCount) || allinCount + foldCount == playerDataIngameList.length) {

            return true;
        }

        return false;
    }
    public static HashMap[] shiftPlayerOrder(HashMap[] playerDataIngameList, int offset) {
        //System.out.println("shiftPlayerOrder started. playerDataIngamelist size: " + playerDataIngameList.length + ". offset: " + offset);
        //      try {
        HashMap[] newPlayersList = new java.util.HashMap[ playerDataIngameList.length ];
        int last = -1;

        if (offset > 0 && playerDataIngameList.length >= 2) { // no changes
            //System.out.println("offset > 0 && playerDataIngameList.length >=2");

            if (offset == 1) {
                //System.out.println("offset == 1");
                if (playerDataIngameList.length == 2) {

                    //System.out.println("playerDataIngameList.length == 2");
                    newPlayersList[ 0 ] = playerDataIngameList[ 1 ];
                    newPlayersList[ 1 ] = playerDataIngameList[ 0 ];
                    //System.out.println("id [0]: " + newPlayersList[0].get(USERNAME));
                    //System.out.println("id [1]: " + newPlayersList[1].get(USERNAME));
                    return newPlayersList;
                }

/*                if (playerDataIngameList.length > 2) {

                    System.out.println("playerDataIngameList.length > 2");
                    for (int forward = 0; (offset + forward) < playerDataIngameList.length; forward++) {
                        if ((offset+forward) == playerDataIngameList.length) {
                            System.out.println("(offset+forward) == playerDataIngameList.length");

                            newPlayersList[forward] = playerDataIngameList[0];
                        } else {
                            System.out.println("!((offset+forward) == playerDataIngameList.length)");
                            newPlayersList[forward] = playerDataIngameList[offset + forward];
                        }
                        last = forward;
                        System.out.println(newPlayersList[forward].get(USERNAME) + " equals " + playerDataIngameList[offset + forward].get(USERNAME));
                    }
                    if (last != playerDataIngameList.length) {

                        System.out.println("last != playerDataIngameList.length");
                        for (int backward = offset - 1; backward > 0; backward--) {
                            newPlayersList[last + backward] = playerDataIngameList[last + backward];
                            System.out.println(newPlayersList[last+backward].get(USERNAME) + " equals " + playerDataIngameList[last + backward].get(USERNAME));
                        }
                    }

                    //System.out.println("rollWinnerPlayerId > 0 && playerDataIngameList.length == 2");
                    return newPlayersList;
                }*/
            }
            return playerDataIngameList;
        }
        return playerDataIngameList;
        //if (offset > 0 && playerDataIngameList.length > 2) { //

    }
    public static int checkFoldCount(HashMap[] playerDataIngame) {
        int c = 0;
        for (HashMap<? extends Object, ? extends Object> player : playerDataIngame) {

            if (player.get(FOLD_FLAG).equals("1")) { // && ) {
                c++;
            }
        }
        return c;
    }
    public static ArrayList[] userUnreg(ArrayList[] playerDataIngameQueue, String userId) {

        for (int z = 0; z < playerDataIngameQueue.length - 1; z++) {
            if (playerDataIngameQueue[z] != null && playerDataIngameQueue[z].get(0) == userId) {
                //playerDataIngameQueue[z].put(4, T);
            }
        }
        return playerDataIngameQueue;

    }
    public static int checkUserRegExistAndActive(String userId, HashMap[] playerDataIngameQueue, int registeredPlayers) {

        for (int iteratePlayer = 0; iteratePlayer < registeredPlayers; iteratePlayer++) {

            if (playerDataIngameQueue[ iteratePlayer ] != null && playerDataIngameQueue[ iteratePlayer ].get(USER_ID).equals(userId) && playerDataIngameQueue[ iteratePlayer ].get(ISACTIVE).equals("1")) { // && ) {
                return iteratePlayer;
            }
        }
        return -1;
    }
    public static int checkUserUnReg(HashMap[] playerDataIngameQueue) {


        for (int iteratePlayer = 0; iteratePlayer < playerDataIngameQueue.length - 1; iteratePlayer++) {
            if (playerDataIngameQueue[ iteratePlayer ] != null && Objects.equals(playerDataIngameQueue[ iteratePlayer ].get(ISACTIVE), "0")) {

                return iteratePlayer;
            }
        }
        return -1; //ok

    }
    public static boolean checkUserLimit(int maxPlayers, int registeredPlayers) {

        return registeredPlayers >= maxPlayers;
    }
    public static String getUserReg(ArrayList<?>[] playerDataIngameQueue, int registeredPlayersCount, String userId) {
        StringBuilder users = new StringBuilder(EMPTY_STRING);
        for (int iteratePlayer = 0; iteratePlayer < registeredPlayersCount; iteratePlayer++) {
            if (playerDataIngameQueue[ iteratePlayer ].get(Integer.parseInt(USER_ID)) != null &&
                Objects.equals(playerDataIngameQueue[ iteratePlayer ].get(Integer.parseInt(userId)), Integer.parseInt(userId))) {
                users.append("\n").append(playerDataIngameQueue[ iteratePlayer ].get(Integer.parseInt(USER_NAME)).toString());
            }
        }
        return users.toString();

    }
    public static int getSplitRewardSize(int potSize, HashMap[] players) {
        System.out.println("getSplitRewardSize start");
        //int splitCount = 2;
        int playerReward = -1;
        if (potSize % 2 != 0) {
            potSize = potSize - 1;
            System.out.println("!!! if (potSize % 2 != 0) { potSize = potSize-1;");
        }
        if (potSize == 0) {
            playerReward = 0;
        } else {
            playerReward = potSize / players.length; // TODO fix 2players hardcode
        }

        System.out.println("potSize = " + potSize);
        System.out.println("playerReward = " + playerReward);

        System.out.println("getSplitRewardSize finish");
        return playerReward;
    }
    public static String getUserRegQueue(HashMap[] playerDataIngameList) {
        StringBuilder users = new StringBuilder("\n");
        //for (HashMap<? extends Object, ? extends Object> strings : playerDataIngameList) {
        for (HashMap strings : playerDataIngameList) {
            if (
                    strings != null &&
                    !strings.isEmpty() &&
                    strings.get(ISACTIVE).equals("1")
            ) {
                users = new StringBuilder("userName: " + users + strings.get(USER_NAME) + "\nuserId: " + strings.get(USER_ID) + "\n\n");
            }
        }
        return users.toString();

    }

    // TODO return Highest card with players id for counting winner
    public static ArrayList<?>[] getPlayerCardsWithTable(Integer moveCount, ArrayList<String>[][] playerCards, HashMap[] players) {

        ArrayList<String>[] playerCardsWithTable = new ArrayList[ 4 ];
        playerCardsWithTable[ 0 ] = new ArrayList();
        playerCardsWithTable[ 1 ] = new ArrayList();
        playerCardsWithTable[ 2 ] = new ArrayList();
        playerCardsWithTable[ 3 ] = new ArrayList();
        //List<HashMap> playersHandWithTable = new HashMap();

        List<?> playerCardsWithTableSorted;
        for (int iterateAvailableCards = 0; iterateAvailableCards < ConstStrings.HAND_CARDS_COUNT + 5; iterateAvailableCards++) {
            playerCardsWithTable[ 0 ].add(playerCards[ moveCount % players.length ][ iterateAvailableCards ].get(0));
            playerCardsWithTable[ 1 ].add(playerCards[ moveCount % players.length ][ iterateAvailableCards ].get(1));
            playerCardsWithTable[ 2 ].add(playerCards[ moveCount % players.length ][ iterateAvailableCards ].get(2));
            playerCardsWithTable[ 3 ].add(playerCards[ moveCount % players.length ][ iterateAvailableCards ].get(3));

            //playersHandWithTable = List.of(playerCardsWithTable[ 0 ]);
            // 0 string;  1 suit; 2 rank; 3 value

        }
        playerCardsWithTableSorted = playerCardsWithTable[ 3 ].stream().sorted().toList();
        System.out.println("playersHandWithTable output sorted " + playerCardsWithTableSorted);

        //System.out.println("playersHandWithTable output" + NEXT_LINE + playersHandWithTable + NEXT_LINE + List.of(playerCardsWithTable[1]) + NEXT_LINE  + List.of(playerCardsWithTable[2]) + NEXT_LINE + List.of(playerCardsWithTable[3]) + NEXT_LINE + "playersHandWithTable end");
        //System.out.println("playersHandWithTable output " + playerCardsWithTable[3].toString());

        return playerCardsWithTable;
    }

    public static int anyRandomIntRange(int low, int high) {
        Random random = new Random();
        return random.nextInt(high) + low + 1;
    }

}
