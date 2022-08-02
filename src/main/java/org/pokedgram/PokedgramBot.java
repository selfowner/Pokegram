package org.pokedgram;

import org.pokedgram.utils.ControlUtils;
import org.pokedgram.utils.DeckUtils;
import org.pokedgram.utils.PlayerUtils;
import org.pokedgram.utils.RankUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static ch.qos.logback.core.util.Loader.getResourceBySelfClassLoader;
import static org.pokedgram.ConstStrings.*;
import static org.pokedgram.utils.DeckUtils.*;
import static org.pokedgram.utils.MsgUtils.*;
import static org.pokedgram.utils.PlayerUtils.*;

public class PokedgramBot extends TelegramLongPollingBot {

    int pokerMessageId;
    HashMap[] playerDataIngame= new HashMap[ 2 ];

    int checkSameStage = -2;
    boolean dealStarted = false;
    int raiseCount;

    int minBetSize; // = smallBlindSize;
    int currentPlayerMoveChoice;
    int currentPlayerNumberChoice;
    String TOURNAMENT_INFO;

    static String nickName = EMPTY_STRING;
    String[] playerStages = new String[]{"preflop", "flop", "turn", "river", "showdown"};
    int currentPlayerStage = -2;
    String flopCards, turnCards, riverCards = EMPTY_STRING;
    Path PARAMS_PATH, STICKERS_PATH;
    List<String> PARAMS_LIST;
    //List<String> STICKERS_LIST;

    {
        try {
            PARAMS_PATH   = Paths.get(getResourceBySelfClassLoader("config.tg").toURI()); // added to .gitignore. 1 line - token, 2nd - roomId, 3rd - room invite link
            //STICKERS_PATH = Paths.get(getResourceBySelfClassLoader("stickerpack.list").toURI());
            PARAMS_LIST   = Files.readAllLines(PARAMS_PATH, StandardCharsets.UTF_8);
            //STICKERS_LIST = Files.readAllLines(STICKERS_PATH, StandardCharsets.UTF_8);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

    }

    final String TOKEN = PARAMS_LIST.get(0);
    final String BOT_NAME = PARAMS_LIST.get(3);
    //final String STICKER = STICKERS_LIST.get(0);
    final String PLAYING_ROOM_ID = PARAMS_LIST.get(1);
    final String PLAYING_ROOM_LINK = PARAMS_LIST.get(2);

    static int currentPot = 0;
    int moveCount = 0;

    SendMessage message = new SendMessage();
    public static String COMMANDS_TEXT = MessageFormat.format("COMMANDS {0}   /about - about this bot  (alias command \"rules\"){1}   /commands - this list{2}   /poker <number> - start game registration with <number> of players{3}   /reg, /unreg - register to the game, or forfeit{4}   /invite <userId> - invite tg user to join game (success only if user communicated with bot in personal messages earlier){5}   /queue - get registered members list{6}   /test - run game mechanics tests{7}To be able to play with bot you need to write him any personal message (https://t.me/pokedgram_bot), then input commands in chat {8}. To set up in 1 step input command about. ", DOUBLE_NEXTLINE, NEXTLINE, NEXTLINE, NEXTLINE, NEXTLINE, NEXTLINE, NEXTLINE, DOUBLE_NEXTLINE);//, BOT_NAME);
    public static final String RULES_TEXT = MessageFormat.format("I''m NL holdem poker dealer bot. {0}To be able to play with bot you need to write him any personal message (https://t.me/pokedgram_bot), then input commands in chat {1} (without ''/'' before command){2}To start game: {3}1. type command \"poker [2-9]\" e.g. \"poker 3\" directly into playing room chat group, where arg = number of sits(=playerDataIngame) in the game. accepted values are >=2 && <=9 {4}2. type command \"reg\" to register to the game. (Also you can send invite to telegram user via \"invite\" command; {5}When registered users will take all the sits, game starts.{6}Game scenario:{7}1. Dealer unpacks and shuffles deck, then deals 1 card to each player (Cards revealed). Deal order determined by playerDataIngameregistration order. {8}2. The player with highest card starts on button, the playerDataIngameafter him start on blinds. (value order on roll stage currently is 2<3<4<5<6<7<8<9<10<J<Q<K<A>2; if more than 1 player got same highest value, dealer shuffles deck and deal repeats. ){9}3. The game starts, and all the rules match TEXAS NL HOLDEM : {10}To view cards enter @" + "zxc" + " . Player on his turn have to choose one of options (fold|check|call|bet|reraise). Player types his decision on his turn in playing room. {15}current build features bugs in{16}pregame:{17}+- create nl holdem game, currently only heads up, +chips{18}+- registration mechanics: +reg, +invite, +queue{19}+- card deal mechanics: +draw, +preflop, +turn, +river, +-showdown, +-burnwhendealing{20}game:{21}+- game state mechanics: +countBlindSize, +countBlindId, +-playerBalance +-pot +-removeBankrupt, +-finishGame {22}{23}+- player hand ranking: +2x +2x2x +3x +3x2x +4x -12345 +-&&&&& -1&2&3&4&5& +-highestkicker{24}- player turn stages mechanics: +check +call +-bet +raise +reraise +-allin -setBet -afk{25}- clearing stage mechanics: +-calculateHands +-splitPotReward{26}misc:{27}+-gui, +msgEdit, +commands, +-inlineCommands, +-markupMessages{28}-stats: -processUsersStat, -provideUserStat, +-processTournamentStat, -provideTournamentStat{29}TL;DR: currently unplayable after cards dealt", DOUBLE_NEXTLINE, "zxdcv", DOUBLE_NEXTLINE, NEXTLINE, NEXTLINE, TRIPLE_NEXTLINE, TRIPLE_NEXTLINE, NEXTLINE, NEXTLINE, NEXTLINE, NEXTLINE, DOUBLE_NEXTLINE, NEXTLINE, NEXTLINE, NEXTLINE, TRIPLE_NEXTLINE, DOUBLE_NEXTLINE, NEXTLINE, NEXTLINE, NEXTLINE, DOUBLE_NEXTLINE, NEXTLINE, NEXTLINE, NEXTLINE, NEXTLINE, NEXTLINE, NEXTLINE, NEXTLINE, NEXTLINE, TRIPLE_NEXTLINE);

    String tableCards;

    ArrayList<String>[][] playerCards = new ArrayList[ playerDataIngame.length ][ HAND_CARDS_COUNT ];

    List<String> cardDeck;
    Integer dealOrderMsg = 0;
    Integer currentDealMessageId;

    //static String nickName;
    static String userName;
    static String userId;

    private void switchPregame (String extractCommand, Integer extractNumber, Update update) {
        String chatId;
        // inline
        if (update != null) {
            userName      = update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName() + " " + "(" + update.getMessage().getFrom().getUserName() + ")";
            userId        = String.valueOf(update.getMessage().getFrom().getId());
            nickName      = update.getMessage().getFrom().getUserName();
            chatId = String.valueOf(update.getMessage().getChatId());

        } else {
            chatId = PARAMS_LIST.get(1);
        }

        switch (extractCommand.toLowerCase()) {
            case "commands", "/commands", "/commands@pokedgram_bot":
                messageSendToPlayerBack(chatId, COMMANDS_TEXT);
                break;

            case "rules", "/rules", "/rules@pokedgram_bot", "/about", "/about@pokedgram_bot":
                messageSendToPlayerBack(chatId, RULES_TEXT);
                break;

            case "reg", "/reg", "/reg@pokedgram_bot":
                if (registrationStarted) { // TODO split prereg and reg switch

                    int checkAlreadyReg = PlayerUtils.checkUserRegExistAndActive(userId, playersQueue, registeredPlayers);
                    if (checkAlreadyReg < 0) {

                        boolean checkQueueFull = PlayerUtils.checkUserLimit(maxPlayers, registeredPlayers);
                        int checkForUnreg = PlayerUtils.checkUserUnReg(playersQueue);
                        if (!checkQueueFull || checkForUnreg > -1) {
                            //TODO rework `!messageSendToPlayer(userId, "Register success.")` check
                            if (CHECK_PLAYERS_INTERACTION && !messageSendToPlayer(userId, "pmDelivered true; legitReg true; " + NEXTLINE + " Register success.")) {
                                execMessage(msgForPlayingRoom(REG_FAILED + "Unable to send message to user. User need to start interact with bot in personal chat first."));
                            } else {
                                playersQueue = PlayerUtils.addPlayerToQueue(userId, userName, playersQueue,
                                        nickName, registeredPlayers, START_CHIPS, checkQueueFull);
                                //editMessageInc(playersQueue[ playersQueue.length-1 ].get(USERNAME).toString() + "
                                // (" + playersQueue[ playersQueue.length-1 ].get(USERID).toString() + ")", pokerMessageId);
                                registeredPlayers++;
                                if (PlayerUtils.checkUserUnReg(playersQueue) == -1 && registeredPlayers == maxPlayers) {
                                    pregameStarted = true;
                                    dealStarted    = true;
                                    //editMessage(TOURNAMENT_INFO + DOUBLE_NEXTLINE + playersList + REG_FINISHED, pokerMessageId);
                                    messageEditInc(REG_FINISHED, pokerMessageId);
                                    break;
                                }
                                return;
                            }
                        } else
                            messageSendToPlayerBack(chatId, REG_FAILED + "checkForUnreg - " + checkForUnreg + ", " + "checkForUnreg - " + checkForUnreg);
                        break;
                    } else messageSendToPlayerBack(chatId, REG_FAILED + " or limits");
                    break;
                } else messageSendToPlayerBack(chatId, REG_CLOSED);
                break;

            case "invite", "/invite", "inv", "/inv", "/invite@pokedgram_bot", "invite@pokedgram_bot":
                if (registrationStarted) {
                    if (extractNumber >= 10000000 && extractNumber <= 2121212100 && extractNumber != 2052704458) {
                        String inviteUserId = String.valueOf(extractNumber);
                        userName = update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName();
                        nickName = inviteUserId;
                        int checkAlreadyReg = PlayerUtils.checkUserRegExistAndActive(inviteUserId, playersQueue, registeredPlayers);
                        if (checkAlreadyReg < 0) {

                            boolean checkQueueFull = PlayerUtils.checkUserLimit(maxPlayers, registeredPlayers);
                            int checkForUnreg = PlayerUtils.checkUserUnReg(playersQueue);
                            if (!checkQueueFull || checkForUnreg > -1) {

                                if (CHECK_PLAYERS_INTERACTION && !messageSendToPlayer(inviteUserId, "You have been invited to play some poker by" + userName)) {
                                    execMessage(msgForPlayingRoom("Invite failed: Unable to send message to user. User need to start interact with bot in personal chat first."));
                                } else {
                                    playersQueue = PlayerUtils.addPlayerToQueue(inviteUserId, "Player" + inviteUserId, playersQueue, nickName, registeredPlayers, START_CHIPS, checkQueueFull);

                                    // TODO count invites seperately
                                    registeredPlayers++;
                                    //sendBack(userId, "Invite success. ");

                                    if (PlayerUtils.checkUserUnReg(playersQueue) == -1 && registeredPlayers == maxPlayers) {
                                        pregameStarted = true;
                                        //registrationStarted = false;
                                        execMessage(msgForPlayingRoom(REG_FINISHED));
                                        break;
                                    } else {
                                        pregameStarted = false;
                                    }
                                }
                            }
                        } else
                            messageSendToPlayer(userId, "Invite failed: checkReg - " + checkAlreadyReg + ", checkLimits - " + PlayerUtils.checkUserLimit(maxPlayers, registeredPlayers));
                        break;
                    } else messageSendToPlayer(userId, "userId " + extractNumber + "do not match format.");
                    break;
                } else messageSendToPlayerBack(chatId, REG_CLOSED);
                break;

            case "/queue", "queue":
                if (registrationStarted) {
                    messageSendToPlayerBack(chatId, "Current queue: " + PlayerUtils.getUserRegQueue(playersQueue));
                } else {
                    messageSendToPlayerBack(chatId, REG_CLOSED);
                }
                break;

            case "/test", "test", "test@pokedgram_bot", "/test@pokedgram_bot":

                //ArrayList[] currentPlayerCards;//Integer potSize = 0;//Integer currentPot = 0;
                String testName;
                moveCount = 0;
                List<String> cardDeck;
                HashMap[] playerDataIngame= RankTests.prepareTestDataPlayers();

                //TODO make test more fair
                execMessage(msgForPlayingRoom("Deck test: " + TRIPLE_NEXTLINE +
                                              "New deck: " + deckInit() + DOUBLE_NEXTLINE +
                                              "Shuffled deck: " + deckShuffle(deckInit()) + DOUBLE_NEXTLINE +
                                              "Shuffled again: " + deckShuffle(deckInit()).toString().strip()));

                //TODO add tests for gui, chips processing, kickers, playerstate cleanup, name generator, ...
                cardDeck = RankTests.prepareTestDataCardDeck3x2xand2x2x2x();
                testName = "3x2x and 2x2x2x: ";
                playerCards  = DeckUtils.deckDeal(playerDataIngame.length, HAND_CARDS_COUNT, cardDeck);
                //currentPlayerCards = getPlayerCardsWithTable(moveCount,playerCards,playerDataIngame);
                allHandsText = deckDealCombosText(playerDataIngame, playerCards, HAND_CARDS_COUNT, cardDeck);
                execMessage(msgForPlayingRoom(testName + DOUBLE_NEXTLINE + allHandsText + NEXTLINE));
                RankTests.getShowdownWinnerIdTest(playerDataIngame, playerCards, cardDeck);

                cardDeck = RankTests.prepareTestDataCardDeck4x();
                testName = "4x and 2x2x2x: ";
                playerCards  = DeckUtils.deckDeal(playerDataIngame.length, HAND_CARDS_COUNT, cardDeck);
                //currentPlayerCards = getPlayerCardsWithTable(moveCount,playerCards,playerDataIngame);
                allHandsText = deckDealCombosText(playerDataIngame, playerCards, HAND_CARDS_COUNT, cardDeck);
                execMessage(msgForPlayingRoom(testName + DOUBLE_NEXTLINE + allHandsText + NEXTLINE));
                RankTests.getShowdownWinnerIdTest(playerDataIngame, playerCards, cardDeck);

                break;

            case "/poker", "poker", "poker@pokedgram_bot", "/poker@pokedgram_bot":

                //playersQueue = null;
                if (extractNumber >= 1 && extractNumber <= 9) {
                    maxPlayers = extractNumber;
                } else {
                    maxPlayers = 9;
                }
                registeredPlayers = 0;

                registrationStarted = true;
                playersQueue = new HashMap[ maxPlayers ];
                TOURNAMENT_INFO = "Tournament initiated! Registration free and open. Game Texas NL Holdem." + DOUBLE_NEXTLINE +
                                  "playersCount: " + maxPlayers + NEXTLINE +
                                  "Heads-up (1x1) " + NEXTLINE +
                                  "smallBlind " + START_SMALL_BLIND_SIZE + NEXTLINE +
                                  "startChips " + START_CHIPS + NEXTLINE +
                                  "blindIncrease x2 every 5 rounds";

                pokerMessageId = messageSendToPlayingRoomAndGetMessageId(TOURNAMENT_INFO);
                break;

        } // pregame menu switch

    }

    private Integer iterateMove(HashMap[] playerDataIngame, Integer moveCount) {
        System.out.println(NEXTLINE + playerDataIngame[ moveCount % playerDataIngame.length ].get(USER_NICKNAME) + " made bet");
        moveCount = moveCount + 1;
        return moveCount;
    }

    public boolean switchStage(Integer moveCount, HashMap[] playerDataIngame) {
        moveCount  = checkPlayerAllinOrFold(moveCount);

        if ((playerDataIngame.length - PlayerUtils.checkFoldCount(playerDataIngame)) == 1) {
            System.out.println("1 player left, and he is a winner");
            currentPlayerStage = 4;

        } else if (moveCount >= playerDataIngame.length) {
            if (PlayerUtils.checkBetsEqual(playerDataIngame)) {
                if (currentPlayerStage > -1 && currentPlayerStage < 4) {
                    System.out.println(playerStages[ currentPlayerStage ] + " stage rdy");
                    return true;
                } else if (currentPlayerStage == 4) {

                }
            }
        } else {
            System.out.println(playerStages[ currentPlayerStage ] + " stage now: movecount - " + moveCount + "; " +
                               "checkBetsEqual - " + PlayerUtils.checkBetsEqual(playerDataIngame));
        }
        return false;
    }

    public Integer extractMove(Update update, Integer moveCount) {
        String extractMove = update.getCallbackQuery().getData().toLowerCase().replaceAll(COMMAND_REGEXP, "$1");
        //System.out.println("extractMove start");
        //System.out.println("currentPlayerMoveChoice " + currentPlayerNumberChoice + "; currentPlayerNumberChoice: "
        // + currentPlayerNumberChoice);
        // public void extractMove(extract)

        switch (extractMove) {
            case "fold" -> {

                currentPlayerMoveChoice = 0;

                if (currentPlayerMoveChoice == 0) { //fold
                    //set fold
                    // playerDataIngame[  moveCount % playerDataIngame.length  ].put(5, true); //get(5)

                    setPlayerStageFlag(playerDataIngame, moveCount % playerDataIngame.length, "fold flag");
                    return currentPlayerMoveChoice;
                }
            }
            case "checkcall" -> {

                currentPlayerMoveChoice = 1;

                if (currentPlayerMoveChoice == 1) { // check/call
                    //check
                    if (PlayerUtils.checkBetsEqual(playerDataIngame)) {
                        //System.out.println("check success - PlayerUtils.checkBetsEqual(playerDataIngame) == " + PlayerUtils.checkBetsEqual(playerDataIngame));
                        setPlayerStageFlag(playerDataIngame, moveCount % playerDataIngame.length, "check flag");

                    }

                    //call
                    else if (PlayerUtils.findMaxBet(playerDataIngame) > Integer.parseInt(playerDataIngame[ moveCount % playerDataIngame.length ].get(BET_ROUND).toString()) &&
                             PlayerUtils.findMaxBet(playerDataIngame) <= Integer.parseInt(playerDataIngame[ moveCount % playerDataIngame.length ].get(CHIPS_VALUE).toString())) {
                        currentPlayerNumberChoice = PlayerUtils.findMaxBet(playerDataIngame) - Integer.parseInt(playerDataIngame[ moveCount % playerDataIngame.length ].get(BET_ROUND).toString());

                        playerDataIngame[ moveCount % playerDataIngame.length ].put(CHIPS_VALUE, Integer.parseInt(playerDataIngame[ moveCount % playerDataIngame.length ].get(CHIPS_VALUE).toString()) - currentPlayerNumberChoice); //current chips at player
                        playerDataIngame[ moveCount % playerDataIngame.length ].put(BET_ROUND, Integer.parseInt(playerDataIngame[ moveCount % playerDataIngame.length ].get(BET_ROUND).toString()) + currentPlayerNumberChoice); // current bet at player

                        currentPot += currentPlayerNumberChoice;
                        System.out.println("call success; added chips " + currentPlayerNumberChoice);
                        setPlayerStageFlag(playerDataIngame, moveCount % playerDataIngame.length, "check flag");
                    }

                    return currentPlayerMoveChoice;
                    //check current bet = highest or equal
                }
            }
            case "bet" -> {

                currentPlayerMoveChoice = 2;

                if (currentPlayerMoveChoice == 2) { //bet
                    if (currentPlayerNumberChoice <= 0) {
                        //currentPlayerNumberChoice = minBetSize;
                    }
                    if (raiseCount < 4 || playerDataIngame.length < 3) {

                        if (currentPlayerNumberChoice > PlayerUtils.findMaxBet(playerDataIngame) * 2) { // ()TODO && > max bet * 2
                            //refresh buttons
                        }
                        currentPlayerNumberChoice =
                                Integer.parseInt(update.getCallbackQuery().getData().toLowerCase().replaceAll(BET_REGEXP, "$3"));
                        playerDataIngame[ moveCount % playerDataIngame.length ].put(CHIPS_VALUE, Integer.parseInt(playerDataIngame[ moveCount % playerDataIngame.length ].get(CHIPS_VALUE).toString()) - currentPlayerNumberChoice); //current chips at player
                        playerDataIngame[ moveCount % playerDataIngame.length ].put(BET_ROUND, Integer.parseInt(playerDataIngame[ moveCount % playerDataIngame.length ].get(BET_ROUND).toString()) + currentPlayerNumberChoice); // current bet at player
                        currentPot += currentPlayerNumberChoice;
                        raiseCount++;
                        return currentPlayerMoveChoice;

                    }
                }
            }
            case "allin" -> {

                currentPlayerMoveChoice = 3;

                if (currentPlayerMoveChoice == 3) { //allin
                    if (raiseCount < 4 || playerDataIngame.length < 3) {
                        //refresh buttons
                        currentPlayerNumberChoice = Integer.parseInt(playerDataIngame[ moveCount % playerDataIngame.length ].get(CHIPS_VALUE).toString());
                        playerDataIngame[ moveCount % playerDataIngame.length ].put(CHIPS_VALUE,
                                Integer.parseInt(playerDataIngame[ moveCount % playerDataIngame.length ].get(CHIPS_VALUE).toString()) - currentPlayerNumberChoice); //current chips at player
                        playerDataIngame[ moveCount % playerDataIngame.length ].put(BET_ROUND,
                                Integer.parseInt(playerDataIngame[ moveCount % playerDataIngame.length ].get(BET_ROUND).toString()) + currentPlayerNumberChoice); // current bet at player
                        currentPot += currentPlayerNumberChoice;
                        setPlayerStageFlag(playerDataIngame, moveCount % playerDataIngame.length, "allin flag");
                        raiseCount++;
                        return currentPlayerMoveChoice;
                    } else {
                        System.out.println("raisecount >= 4, only call or fold is a option");
                    }

                }
            }
            default -> {
                //return currentPlayerMoveChoice;
            }
        }
        //        if (currentPlayerMoveChoice == -1) {
        //            sendToPlayer(playerDataIngame[moveCount].get(USERID).toString(),"to make bet choose 1 action from 1st menu row (if a choose bet, also push 1 button on 2nd row");
        //        }
        //        System.out.println("extractMove finish");
        return currentPlayerMoveChoice;
    }

    private void inlineResultDeck(Update update, ArrayList<String>[][] playersCard, HashMap[] playerDataIngame, Integer dealNumber)  {
        if (update.hasInlineQuery()) {
            for (int iteratePlayer = 0; iteratePlayer < playerDataIngame.length; iteratePlayer++) {
                if (update.getInlineQuery().getFrom().getId().toString().equals(playerDataIngame[ iteratePlayer ].get(USER_ID).toString())) {
                    AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
                    List<InlineQueryResult> resultsList = new ArrayList<>();
                    InlineQueryResultArticle article = new InlineQueryResultArticle();
                    InputTextMessageContent messageContent = new InputTextMessageContent();

                    String hand = (playersCard[ iteratePlayer ][ 0 ].get(0) + playersCard[ iteratePlayer ][ 1 ].get(0));
                    String chips = playerDataIngame[iteratePlayer].get(CHIPS_VALUE).toString();
                    String name = playerDataIngame[iteratePlayer].get(USER_NAME).toString();

                    messageContent.setMessageText(AUTODELETE_TEXT);
                    article.setInputMessageContent(messageContent);
                    article.setId(Integer.toString(0));
                    article.setTitle("Deal №" + dealNumber);
                    article.setDescription("Your hand: " + hand);
                    resultsList.add(article);

                    answerInlineQuery.setCacheTime(0);
                    answerInlineQuery.setInlineQueryId(update.getInlineQuery().getId());
                    answerInlineQuery.setIsPersonal(true);
                    answerInlineQuery.setResults(resultsList);

                    try {
                        execute(answerInlineQuery);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Integer checkPlayerAllinOrFold(Integer moveCount) {
        if (Objects.equals(playerDataIngame[ moveCount % playerDataIngame.length ].get(FOLD_FLAG).toString(), "1") ||
            Objects.equals(playerDataIngame[ moveCount % playerDataIngame.length ].get(ALLIN_FLAG).toString(), "1")) {

            System.out.println("found fold or allin at " + playerDataIngame[ moveCount % playerDataIngame.length ].get(USER_NICKNAME));
            moveCount = iterateMove(playerDataIngame, moveCount);
        }
        return moveCount;
    }

    public Integer currentStageTableText(Update update, String userIdCallback, Integer moveCount) {

        if (stageStarted) {

            moveCount    = moveCount % playerDataIngame.length;
            stageStarted = false;
        }

        moveCount = checkPlayerAllinOrFold(moveCount);

        if (checkPlayerCallback(update, userIdCallback, moveCount)) {

            moveCount = iterateMove(playerDataIngame, moveCount);

        }

        if (switchStage(moveCount, playerDataIngame)) {

            currentPlayerStage++;
            processPlayerBetsFromStageToRound(playerDataIngame);
            unsetAllPlayerStageFlag(playerDataIngame, "check flag");
            tableCards = getCurrentStageTableCardsText(currentPlayerStage, cardDeck);
            stageStarted = true;

        }

        //messageEditWithButtons(roundAnnounceText(playerDataIngame) + DOUBLE_NEXTLINE + CURRENT_POT_TEXT + currentPot + DOUBLE_NEXTLINE + "current move " + moveCount + ": " + playerDataIngame[ moveCount % playerDataIngame.length ].get(USERNICK)+ DOUBLE_NEXTLINE +  tableCards, currentDealMessageId);

        return moveCount;

    }

    public  String deckDealCombosText(HashMap[] playerDataIngame, ArrayList<String>[][] playerCards, Integer HAND_CARDS_COUNT, List<String> cardDeck) {
        // text                  = EMPTY_STRING;
        currentPlayerHandText = EMPTY_STRING;
        allHandsText          = EMPTY_STRING;
        for (int playerNumber = 0; playerNumber < playerDataIngame.length; playerNumber++) {

            StringBuilder hand = new StringBuilder();
            for (int playerCardNumber = 0; playerCardNumber < HAND_CARDS_COUNT; playerCardNumber++) {
                hand.append(playerCards[ playerNumber ][ playerCardNumber ].get(0));
            }

            currentPlayerHandText = playerNumber + " hand: " + hand + NEXTLINE;

            //sendToPlayer(playerDataIngame[ y ].get(USERID).toString(), "dealNumber " + dealNumber + NEXT_LINE + "Your hand: " + currentPlayerHandText);
            //            sendToPlayer(playerDataIngame[ playerNumber ].get(USERID).toString(), "deal " + dealNumber + DOT + NEXT_LINE + hand +
            //                                                                    " your cards at dealNumber" + dealNumber + DOT + NEXT_LINE); // + currentPlayerHandText);
            allHandsText = allHandsText + currentPlayerHandText;

        }
        return allHandsText;
    }

    public String getCurrentStageTableCardsText(Integer currentPlayerStage, List<String> cardDeck) {
        String currentTableCards = EMPTY_STRING;

        if (currentPlayerStage > -1) {
            String flopCards = dealFlop(playerDataIngame.length, HAND_CARDS_COUNT, cardDeck);
            String turnCards  = flopCards + DeckUtils.dealTurn(playerDataIngame.length, HAND_CARDS_COUNT, cardDeck);
            String riverCards = turnCards + DeckUtils.dealRiver(playerDataIngame.length, HAND_CARDS_COUNT, cardDeck);
            if (currentPlayerStage == 0) {
                currentTableCards = PHASE_PREFLOP_TEXT;
            } else if (currentPlayerStage == 1) {
                currentTableCards = PHASE_FLOP_TEXT + NEXTLINE + flopCards;
            } else if (currentPlayerStage == 2) {
                currentTableCards = PHASE_TURN_TEXT + NEXTLINE + turnCards;
            } else if (currentPlayerStage >= 3) {
                currentTableCards = PHASE_RIVER_TEXT + NEXTLINE + riverCards;
            }
        }
        return currentTableCards;
    }

    public boolean checkPlayerCallback(Update update, String userIdCallback, Integer moveCount) {
        boolean res = false;
        if (update.hasCallbackQuery()) {
            // if (userIdCallback.toString().equals(playerDataIngame[ moveCount % playerDataIngame.length ].get(USERID).toString())) {
            if (true) {
                if (extractMove(update, moveCount) >= 0) {
                    res = true;
                    return res;
                }

//                editMessageWithButtons(getRoundAnnounceString(playerDataIngame) + DOUBLE_NEXTLINE + DECK_SHUFFLE_TEXT + DOUBLE_NEXTLINE + CURRENT_POT_TEXT + currentPot + DOUBLE_NEXTLINE + "current move " + moveCount + ": " + playerDataIngame[ moveCount % playerDataIngame.length ].get(USERNICK)
//                                       + DOUBLE_NEXTLINE + tableCards + DOUBLE_NEXTLINE + "press @pokedgram_bot to view cards", currentDealMessageId);
            } else if (!userIdCallback.toString().equals(playerDataIngame[ moveCount % playerDataIngame.length ].get(USER_ID).toString()) && !userIdCallback.equals("0")) {
                messageSendToPlayer(userIdCallback, "userId " + userIdCallback + " not matched " +
                                                    playerDataIngame[ moveCount % playerDataIngame.length ].get(USER_ID) + NEXTLINE +
                                                    "expecting move from player" +
                                                    playerDataIngame[ moveCount % playerDataIngame.length ].get(USER_ID) + " (" + playerDataIngame[ moveCount % playerDataIngame.length ].get(USER_NAME) + ")" + NEXTLINE);
            } else if (userIdCallback.equals("0")) {
                System.out.println("userIdCallback = " + userIdCallback);
            }
            //userIdCallback = "0";
            // }

        }
        return res;
    }



    @Override
    public void onRegister() {
        try {

            message.setChatId(PLAYING_ROOM_ID);
            message.setText(COMMANDS_TEXT);
            execute(message);
            //System.out.println("onRegister ok: " + message.toString());
            //sendSticker();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("onRegister exception: " + message);
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {

        return TOKEN;

    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasCallbackQuery()) {
            System.out.println("onUpdateReceived callback usrtext = " + userIdMessage + ", usrcallback = " + userIdCallback);

            userIdCallback = String.valueOf(update.getCallbackQuery().getFrom().getId());
            userIdMessage  = "0";
        }

        if (update.hasMessage()) {
            System.out.println("onUpdateReceived msg usrtext = " + userIdMessage + ", usrcallback = " + userIdCallback);

            if (update.getMessage().getText().matches(AUTODELETE_REGEXP)) {
                messageDelete(update.getMessage().getMessageId());
            }

            userName      = update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName();
            userId        = String.valueOf(update.getMessage().getFrom().getId());
            nickName      = update.getMessage().getFrom().getUserName();
            userIdMessage = update.getMessage().getFrom().getId().toString();

            System.out.println("got update with text, from userName: " + userName + "(userId " + userId + ")");
            userIdCallback = "0";

        }

        String extractCommand = null;
        if (update.hasMessage() && update.getMessage().hasText() && currentPlayerStage == -2) {
            if (!gameStarted) {
                if (!pregameStarted) {

                    String arg;
                    extractCommand = update.getMessage().getText().replaceAll(COMMAND_REGEXP, "$1");
                    if (extractCommand.length() < update.getMessage().getText().length()) {
                        try {
                            arg = update.getMessage().getText().replaceAll(ARGUMENT_REGEXP, "$2");
                            if (arg.length() > 0) {
                                System.out.println("arg = " + arg + NEXTLINE + "extractCommand = " + extractCommand);
                                extractNumber = Integer.parseInt(arg);
                            } else {
                                extractNumber = 0;
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                    }
                    switchPregame(extractCommand, extractNumber, update);

                    while (pregameStarted && !gameStarted) {
                        int tryCount = 0;
                        int buttonId;
                        cardDeck = deckInit();

                        cardDeck = deckShuffle(cardDeck);
                        tryCount++;
                        Integer preGameMessageId = messageSendToPlayingRoomAndGetMessageId(DECK_SHUFFLE_TEXT);
                        ArrayList<String>[][] playerCards = deckDeal(maxPlayers, 1, cardDeck);
                        allHandsText = dealCardsText(playersQueue, playerCards, 1);

                        if (ROLL_VISIBILITY) {
                            messageEdit(DECK_SHUFFLE_TEXT + DOUBLE_NEXTLINE + allHandsText, preGameMessageId);
                        } else {
                            messageEdit(DECK_SHUFFLE_TEXT + NEXTLINE + CARDS_DEALED_TEXT + DOUBLE_NEXTLINE, preGameMessageId);
                        }

                        buttonId = RankUtils.checkCardRankPregame(maxPlayers, playerCards);

                        if (buttonId == -1) {
                            messageEdit(DECK_SHUFFLE_TEXT + DOUBLE_NEXTLINE + allHandsText + DOUBLE_NEXTLINE + "We've equal highest, redraw (trycount " + tryCount + ")", preGameMessageId); //+ DOUBLE_NEXTLINE + "Deck info: " + deck, rollMessageId);
                            gameStarted = false;
                        } else {
                            messageEdit(DECK_SHUFFLE_TEXT + DOUBLE_NEXTLINE + allHandsText + DOUBLE_NEXTLINE + "@" + playersQueue[ buttonId ].get(USER_NAME).toString() + " starting on the button", preGameMessageId); // + TRIPLE_NEXT_LINE + "Deck info: " + deck, rollMessageId);
                            gameStarted        = true;
                            playerDataIngame   = playersQueue;
                            playerDataIngame   = PlayerUtils.shiftPlayerOrder(playerDataIngame, buttonId);
                            currentPlayerStage = -1;
                            userIdMessage      = "0";
                            userIdCallback     = "0";
                            break;

                            //TODO delete pregame commands and add game commands promt
                            //TODO create comfortable UX to make moves (inline/buttons/etc)
                        }
                        //} return;

                    } //pregame started (reg finished). works fine
                    // if (update.hasMessage() && update.getMessage().hasText() && gameStarted) {

                }
            }
        }
        if (currentPlayerStage > -2) {

            if (currentPlayerStage == -1) {
                dealNumber++;
                if (dealNumber == 1) {
                    cardDeck = deckInit();
                } else {
                    playerDataIngame = PlayerUtils.shiftPlayerOrder(playerDataIngame, 1);
                }

                //boolean isSidePotExist = false;
                currentPot     = 0;
                moveCount      = 0;
                raiseCount     = 0;
                tableCards     = EMPTY_STRING;
                smallBlindSize = ((dealNumber / BLIND_INCREASE_RATE_DEALS) + 1) * START_SMALL_BLIND_SIZE;
                bigBlindSize   = smallBlindSize * 2;
                minBetSize     = bigBlindSize;

                if (playerDataIngame.length == 2) {
                    smallBlindId = 0;
                    bigBlindId   = smallBlindId + 1;

                    playerDataIngame[ smallBlindId ].put(CHIPS_VALUE, Integer.parseInt(playerDataIngame[ smallBlindId ].get(CHIPS_VALUE).toString()) - smallBlindSize); //current chips at player
                    playerDataIngame[ smallBlindId ].put(BET_ROUND, Integer.parseInt(playerDataIngame[ smallBlindId ].get(BET_ROUND).toString()) + smallBlindSize); // current
                    // bet at player
                    playerDataIngame[ bigBlindId ].put(CHIPS_VALUE, Integer.parseInt(playerDataIngame[ bigBlindId ].get(CHIPS_VALUE).toString()) - bigBlindSize); //current chips at player
                    playerDataIngame[ bigBlindId ].put(BET_ROUND, Integer.parseInt(playerDataIngame[ bigBlindId ].get(BET_ROUND).toString()) + bigBlindSize); // current bet at player

                    currentPot += Integer.parseInt(playerDataIngame[ smallBlindId ].get(BET_ROUND).toString());
                    currentPot += Integer.parseInt(playerDataIngame[ bigBlindId ].get(BET_ROUND).toString());

                    //public Integer betBlindsToPot() {}

                }

                String blindsInfo = "deal number: " + dealNumber + DOUBLE_NEXTLINE + "\uD83D\uDD35 small blind @ " + playerDataIngame[ (moveCount) % playerDataIngame.length ].get(USER_NAME) + NEXTLINE + "\uD83D\uDFE1 big blind @ " + playerDataIngame[ (moveCount + 1) % playerDataIngame.length ].get(USER_NAME);
                dealOrderMsg = messageSendToPlayingRoomAndGetMessageId(blindsInfo);

                cardDeck     = deckShuffle(cardDeck);
                playerCards  = DeckUtils.deckDeal(playerDataIngame.length, HAND_CARDS_COUNT, cardDeck);
                allHandsText = dealCardsText(playersQueue, playerCards, HAND_CARDS_COUNT);

                currentDealMessageId = messageSendToPlayingRoomWithButtonsAndGetMessageId(
                        roundAnnounceText(playerDataIngame) + DOUBLE_NEXTLINE +
                        CURRENT_POT_TEXT + currentPot + DOUBLE_NEXTLINE +
                        "current move " + moveCount + ": " + playerDataIngame[ moveCount % playerDataIngame.length ].get(USER_NICKNAME) + DOUBLE_NEXTLINE +
                        tableCards + DOUBLE_NEXTLINE
                );

                currentPlayerMoveChoice = 0;
                currentPlayerStage      = 0;
                //currentPlayerNumberChoice = bigBlindSize;
            }
            if (currentPlayerStage >= 0 && currentPlayerStage <= 4) { //preflop - river

                //TODO fix minBetSize logic
                minBetSize = PlayerUtils.findMaxBet(playerDataIngame);
                if (minBetSize == 0) {
                    minBetSize = bigBlindSize;
                }

                System.out.println("gameStarted; cardsDealt == true; userIdCallback = " + userIdCallback);
                if (currentPlayerStage >= 0) {
                    inlineResultDeck(update, playerCards, playerDataIngame, dealNumber);
                }
                //preflop flop turn river
                if (currentPlayerStage >= 0 && currentPlayerStage <= 3) {

                    tableCards = getCurrentStageTableCardsText(currentPlayerStage, cardDeck);
                    moveCount  = currentStageTableText(update, userIdCallback, moveCount);
                    //  if (checkSameStage != currentPlayerStage) {
                    messageEditWithInterface(
                            roundAnnounceText(playerDataIngame) + DOUBLE_NEXTLINE +
                            CURRENT_POT_TEXT + currentPot + DOUBLE_NEXTLINE +
                            "current move " + moveCount + ": " + playerDataIngame[ moveCount % playerDataIngame.length ].get(USER_NICKNAME) + DOUBLE_NEXTLINE +
                            tableCards + TRIPLE_NEXTLINE, currentDealMessageId);
                    //     checkSameStage = currentPlayerStage;
                    // }

                }

                //showdown
                if (currentPlayerStage == 4) {
//                String resultCards = EMPTY_STRING;
//                String resultCombo = EMPTY_STRING;
                    StringBuilder res = new StringBuilder(EMPTY_STRING);
                    System.out.println("currentPlayerStage 4 - showdown");

                    getShowdownWinnerId(playerDataIngame, playerCards, cardDeck);

                    ArrayList<?>[] playerCardsWithTable = new ArrayList[]{};
                    System.out.println("final results: (//str fl 8 //quad 7 //fullhouse 6 //flash 5 //straight 4 //triple 3 //two pair 2 //pair 1 //high card 0)");
                    for (int iteratePlayer = 0; iteratePlayer < playerDataIngame.length; iteratePlayer++) {
                        System.out.println("player " + iteratePlayer + ": " + playerDataIngame[ iteratePlayer ].get(COMBOPOWER_VALUE).toString());
                        //resultCombo += "player" +y + ": " + playerDataIngame[y].get(COMBOPOWER_VALUE) + NEXT_LINE;
                        playerCardsWithTable = getPlayerCardsWithTable(moveCount + iteratePlayer, playerCards, playerDataIngame);
                        //resultCards += String.valueOf(Arrays.stream(playerCardsWithTable).toList()) + NEXT_LINE;
                        res
                                .append("player ")
                                .append(iteratePlayer)
                                .append(": ")
                                .append(playerDataIngame[ iteratePlayer ].get(COMBOPOWER_VALUE))
                                .append(playerCardsWithTable[ 0 ])
                                .append(NEXTLINE);
                    }

                    //messageSendToPlayingRoom(resultCards + NEXT_LINE + resultCombo);

                    execMessage(msgForPlayingRoom(res.toString())); //messageSendToPlayingRoom(res.toString());
                    userIdCallback          = "0";
                    userIdMessage           = "0";
                    update                  = null;
                    currentPlayerMoveChoice = -1;
                    currentPlayerStage      = -1;
                    currentPot              = 0;
                    smallBlindSize          = 0;
                    bigBlindSize            = 0;
                    clearPlayerBets(playerDataIngame);
                    unsetAllPlayerStageFlag(playerDataIngame, "clean all");
                    unsetRoundResult(playerDataIngame);
                }

            }
            try {
                Thread.sleep(SLEEP_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        update = null;

        System.out.println("onUpdate end" + nickName);
    }
    
    String userIdMessage = EMPTY_STRING;
    String userIdCallback = EMPTY_STRING;
    public static HashMap[] getShowdownWinnerId(HashMap[] playerDataIngame, ArrayList<String>[][] playerCards, List<?> cardDeck) {
        int winnerId, winnerCount = -1;
        // int winnerCount = -1;

        ArrayList[] tableArray = DeckUtils.dealTable(playerDataIngame.length, cardDeck);
        if (false) {
            //sidepot
        }
        boolean gotWinner = false;

        //calculate winner and process chips
        while (!gotWinner) {

            //System.out.println("findWinner start");

            // TODO count kickers on table for some cases
            winnerCount += RankUtils.checkDistinct(playerCards, playerDataIngame);
            winnerCount += RankUtils.checkFlash(playerDataIngame, playerCards, tableArray);
            winnerCount += RankUtils.checkStraight(playerCards);

            System.out.println("checkDistinct winnerCount after checkDistinct checkFlash checkStraight: " + winnerCount);
            //str fl 8 //quad 7 //fullhouse 6 //flash 5 //straight 4 //triple 3 //two pair 2 //pair 1 //high card 0
            if (Integer.parseInt(playerDataIngame[ 0 ].get(COMBOPOWER_VALUE).toString()) >
                Integer.parseInt(playerDataIngame[ 1 ].get(COMBOPOWER_VALUE).toString())) {
                System.out.println("player 0 win");
                winnerId = 0; //player 0 win
            } else if (Integer.parseInt(playerDataIngame[ 1 ].get(COMBOPOWER_VALUE).toString()) >
                       Integer.parseInt(playerDataIngame[ 0 ].get(COMBOPOWER_VALUE).toString())) {

                System.out.println("player 1 win");

                winnerId = 1; //player 1 win
            } else if ( // comboValue ==
                    Integer.parseInt(playerDataIngame[ 0 ].get(COMBOPOWER_VALUE).toString()) ==
                    Integer.parseInt(playerDataIngame[ 1 ].get(COMBOPOWER_VALUE).toString()) &&
                    (Integer.parseInt(playerDataIngame[ 0 ].get(COMBOPOWER_VALUE).toString()) > -1 &&
                     Integer.parseInt(playerDataIngame[ 1 ].get(COMBOPOWER_VALUE).toString()) > -1)
            ) {
                System.out.println("playerDataIngame tie, checking kicker:");
                try {
                    winnerCount = RankUtils.checkKicker(playerDataIngame, playerCards, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /*
            // winnerCount = checkKicker(playerDataIngame, playerCards, tableArray);
            if (winnerCount > 0) {
                System.out.println("checkDistinct winnerCount: " + winnerCount);
                //find kicker or split reward
                gotWinner = true;
                break;
            }
*/
            System.out.println("findWinner end"+ NEXTLINE);

            break;
        }

        int playerIncome = getSplitRewardSize(currentPot, playerDataIngame);
        if (winnerCount > 1) {
            for (HashMap player : playerDataIngame) {
                player.put(CHIPS_VALUE, Integer.parseInt(player.get(CHIPS_VALUE).toString()) + playerIncome);
                currentPot = 0;
            }
            System.out.println("winnerCount > 1; income " + playerIncome);
            gotWinner = true;
            // process chips
        } else if (winnerCount == 0) {
            System.out.println("winnerCount == 0; no combo found. Splitting pot, setting gotWinner true " + playerIncome);
            for (HashMap player : playerDataIngame) {
                player.put(CHIPS_VALUE, Integer.parseInt(player.get(CHIPS_VALUE).toString()) + playerIncome);
                currentPot = 0;
                gotWinner  = true;
            }
        } else if (winnerCount == 1) {
            System.out.println("winnerCount == 1; we have winner");
            //get winnerId
        }

        return playerDataIngame;
    }

    //sendsticker
    /*public void messageSendStickerToPlayingRoom() {
        try {
            String currentId = message.getChatId();
            message.setChatId(PLAYING_ROOM_ID);
            SendSticker sendSticker = new SendSticker();
            sendSticker.setChatId(PLAYING_ROOM_ID);
            sendSticker.setSticker(new InputFile(STICKER));
            execute(sendSticker);
            try {
                message.setChatId(currentId);
            } catch (NullPointerException e) {
                System.out.println("exception: " + message);
            }
            Thread.sleep(SLEEP_MS);
        } catch (TelegramApiException | InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    public void messageSendToPlayingRoom(String text) {
        try {
            message = new SendMessage();
            message.setChatId(PLAYING_ROOM_ID);
            message.setText(text);
            execute(message);
            Thread.sleep(SLEEP_MS);

        } catch (InterruptedException | TelegramApiException e) {
            e.printStackTrace();
            System.out.println("exception: " + e.getMessage() + NEXTLINE + "message: " + message.getText());
        }
    }

    public SendMessage msgForPlayingRoom(String text) {

        message.setChatId(PLAYING_ROOM_ID);
        message.setText(text);
        return message;
    }
    public Integer messageSendToPlayingRoomAndGetMessageId(String text) {

        int messageId = -1;
        try {
            message.setChatId(PLAYING_ROOM_ID);
            message.setText(text);
            messageId = execute(message).getMessageId();
            Thread.sleep(SLEEP_MS);
        } catch (NullPointerException | InterruptedException | TelegramApiException e) {
            e.printStackTrace();
            System.out.println("exception: messageId = " + messageId + "; message: " + message.getText());
        }
        return messageId;
    }
    private Integer messageSendToPlayingRoomWithButtonsAndGetMessageId(String text) {
        int messageId = -1;
        try {
            message.setChatId(PLAYING_ROOM_ID);
            message.setText(text);
            message.setReplyMarkup(ControlUtils.makeButtons(moveCount, playerDataIngame));

            messageId = execute(message).getMessageId();
            Thread.sleep(SLEEP_MS);
        } catch (NullPointerException | InterruptedException | TelegramApiException e) {
            e.printStackTrace();
            System.out.println("exception: messageId = " + messageId + "; message: " + message.getText());
        }

        return messageId;
    }
    public boolean messageSendToPlayer(String userId, String text) {

        message.setChatId(userId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        //Thread.sleep(SLEEP_MS);
        return true;
        //System.out.println("ok: " + message);
    }
    public void messageSendToPlayerBack(String userId, String text) {

        try {
            message.setChatId(userId);
            message.setText(text);
            //Message execute =
            execute(message);
            //System.out.println("ok: " + message);
            Thread.sleep(SLEEP_MS);
        } catch (InterruptedException | TelegramApiException e) {
            e.printStackTrace();
            System.out.println("exception: " + message.getText());
        }

    }
    public void messageDelete(Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        try {
            deleteMessage.setChatId(PLAYING_ROOM_ID);
            deleteMessage.setMessageId(messageId);
            execute(deleteMessage);

            deleteMessage.setChatId(userId);
            deleteMessage.setMessageId(messageId);
            execute(deleteMessage);

            Thread.sleep(SLEEP_MS);
        } catch (TelegramApiException | NullPointerException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("exception: messageId = " + messageId + "; message: " + message.getText());
        }
    }
    public void messageEdit(String newText, Integer messageId) {
        EditMessageText msg = new EditMessageText();
        msg.setChatId(PLAYING_ROOM_ID);
        msg.setMessageId(messageId);
        String currentText = msg.getText();

        if (currentText!=null && currentText.length()>0) {
            if (!currentText.equals(newText)) {
                try {
                    msg.setText(newText);
                    //msg.setText(text);
                    execute(msg);
                    //Thread.sleep(SLEEP_MS);
                } catch (TelegramApiException | NumberFormatException e) {
                    e.printStackTrace();
                    System.out.println("exception: messageId = " + messageId + "; message: " + msg.getText());
                }
            } else {
                System.out.println("editMessage tried edit same message");
            }
        }
        System.out.println("editMessage msg.getText(); == null");
    }
    public void messageEditInc(String newText, Integer messageId) {
        EditMessageText msg = new EditMessageText();
        msg.setChatId(PLAYING_ROOM_ID);
        msg.setMessageId(messageId);
        String currentText = msg.getText();
        if (currentText!=null && currentText.length()>0) {
            if (!currentText.equals(newText)) {

                msg.setMessageId(messageId);
                //String currentText = msg.getText();
                msg.setText(currentText + NEXTLINE + newText);
                try {
                    execute(msg);
                } catch (TelegramApiException | NumberFormatException e) {
                    e.printStackTrace();
                    System.out.println("exception: messageId = " + messageId + "; message: " + msg.getText());
                }
            } else {
                System.out.println("messageEditInc tried edit same message");
            }

            System.out.println("messageEditInc msg.getText(); == null");
        }
    }
    public void messageEditIncWithButtons(String text, Integer messageId) {
        EditMessageText msg = new EditMessageText();

        msg.setMessageId(messageId);
        String currentText = msg.getText();
        try {
            msg.setChatId(PLAYING_ROOM_ID);
            msg.setText(currentText + text);
            msg.setReplyMarkup(ControlUtils.makeButtons(moveCount, playerDataIngame));
            execute(msg);
            //Thread.sleep(SLEEP_MS);
            //return currentText;
        } catch (TelegramApiException | NumberFormatException e) {
            e.printStackTrace();
            System.out.println("exception: messageId = " + messageId + "; message: " + msg.getText());
        }
    }
    public void messageEditWithInterface(String newText, Integer messageId) {
        EditMessageText msg = new EditMessageText();
        msg.setChatId(PLAYING_ROOM_ID);
        msg.setMessageId(messageId);
        try {
            msg.setText(newText+msg.getMessageId());
            msg.setReplyMarkup(ControlUtils.makeButtons(moveCount, playerDataIngame));
            execute(msg);
            Thread.sleep(SLEEP_MS);
        } catch (TelegramApiException | NumberFormatException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("exception: messageId = " + messageId + "; message: " + msg.getText());
        }
    }

    public void execMessage(SendMessage message) {

        if (message.getText() != null) {
        } else {
            message.setText("empty txt crutch");
        }

        if (message.getChatId() != null) {

        } else {
            message.setChatId(PLAYING_ROOM_ID);
        }

        try {
            execute(message);
            Thread.sleep(SLEEP_MS);
        } catch (TelegramApiException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    
}