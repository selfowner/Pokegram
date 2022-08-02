package org.pokedgram;

import java.util.HashMap;

public class ConstStrings extends PokedgramBot {
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String USER_NICKNAME = "nickName";
    public static final String CHIPS_VALUE = "chips";
    public static final String ISACTIVE = "isActive";
    public static final String FOLD_FLAG = "foldFlag";
    public static final String ALLIN_FLAG = "allinFlag";
    public static final String RAISE_FLAG = "chipLeaderFlag";
    public static final String BET_STAGE = "currentStageBet";
    public static final String BET_ROUND = "currentRoundBet";
    public static final String ISAUTOALLINONBLIND = "isAutoAllinOnBlind";
    public static final String PLACEONTABLEFINISHED = "placeOnTableFinished";
    public static final String CHECK_FLAG = "checkFlag";
    public static final String COMBOPOWER_VALUE = "comboPower";
    public static final String KICKER1_VALUE = "kicker1Value";
    public static final String KICKER2_VALUE = "kicker2Value";
    public static final String REG_CLOSED = "Registration is not open. ";
    public static final String REG_FAILED = "Register failed. ";
    public static final String REG_FINISHED = "Players registered. Lets roll. ";
    public static final String DOT = ".";
    public static final String COMMA = ",";
    public static final String NEXTLINE = "\n";
    public static final String DOUBLE_NEXTLINE = "\n\n";
    public static final String TRIPLE_NEXTLINE = "\n\n\n";
    public static final String EMPTY_STRING = "";
    public static final String FALSE_TEXT = "false";
    public static final String TRUE_TEXT = "true";
    public static final String PHASE_PREFLOP_TEXT = "Preflop stage: ";
    public static final String PHASE_RIVER_TEXT = "River stage: ";
    public static final String PHASE_FLOP_TEXT = "Flop stage: ";
    public static final String PHASE_TURN_TEXT = "Turn stage: ";
    public static final String CURRENT_POT_TEXT = "Current pot size: ";
    public static final String DECK_SHUFFLE_TEXT = "*Dealer shuffling deck and deals cards*";
    public static final String ARGUMENT_REGEXP = "^.([a-zA-Z@_ ]*)(.*)$";
    public static final String COMMAND_REGEXP = "^(.[a-zA-Z@_]*) ([0-9]*)(.*)$";
    public static final String BET_REGEXP = "^((allin|number|bet) )([0-9]*)$";
    public static final String AUTODELETE_REGEXP = "^.*(DELETE_ME).*$";
    public static final String AUTODELETE_TEXT = "DELETE_ME";
    public static final String FIND_FLASH_REGEXP = "((.*(♠).*){5}|(.*(♣).*){5}|(.*(♥).*){5}|(.*(♦).*){5})";
    public static final String EXTRACT_DISTINCT_VALUES_REGEXP = "(?:(\\d+=[^234], |\\d+=[^234]))";
    public static final String EXTRACT_VALUES_DIRTY_REGEXP = "(?:([^=]|))";
    public static final String EXTRACT_DISTINCT_COUNT_REGEXP = "\\d+=(?:(\\d+|)),";
    public static final String FIND_CARDS_RANK_REGEXP = "[0-9JQKA]";
    public static final String FIND_CARDS_SUIT_REGEXP = "[♠♣♦♥♤♡♢♧]";
    public static final String FIND_2X_REGEXP = "^.*=[2].*$";
    public static final String FIND_2X2X_REGEXP = "^.*=[2].*=[2].*$";
    public static final String FIND_3X_REGEXP = "^.*=[3].*$";
    public static final String FIND_4X_REGEXP = "^.*=[4].*$";
    public static final String FIND_FLASH_DRAW_SPADES_REGEXP = "(.*(♠).*){3}";
    public static final String FIND_FLASH_DRAW_CLUBS_REGEXP = "(.*(♣).*){3}";
    public static final String FIND_FLASH_DRAW_HEARTS_REGEXP = "(.*(♥).*){3}";
    public static final String FIND_FLASH_DRAW_DIAMONDS_REGEXP = "(.*(♦).*){3}";
    public static final boolean ROLL_VISIBILITY = true;
    public static final boolean DEAL_VISIBILITY = false;
    public static final boolean CHECK_PLAYERS_INTERACTION = true;
    public static final int START_CHIPS = 20000;
    public static final int HAND_CARDS_COUNT = 2; // holdem
    public static final int SLEEP_MS = 2000;
    public static final int BLIND_INCREASE_RATE_DEALS = 5;
    public static final int START_SMALL_BLIND_SIZE = 250;
    public static String currentPlayerHandText, allHandsText = "";
    public static HashMap[] playersQueue;
    public static int buttonId = -1;
    public static int registeredPlayers = 0;
    public static int extractNumber;
    public static int maxPlayers;
    public static int bigBlindId = -1;
    public static int smallBlindId = -1;
    public static int smallBlindSize, bigBlindSize;
    public static int dealNumber;
    public static boolean registrationStarted = false;
    public static boolean pregameStarted = false;
    public static boolean gameStarted = false;
    public static boolean stageStarted = true;
    static String botName = "@pokedgram_bot";
    public static final String CARDS_DEALED_TEXT = ("cards have been dealed (check pm from " + botName + DOT);
    public final String title;

    public ConstStrings(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }


}
