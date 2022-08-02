package org.pokedgram.utils;

import org.pokedgram.PokedgramBot;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.pokedgram.ConstStrings.CHIPS_VALUE;
import static org.pokedgram.ConstStrings.bigBlindSize;

public class ControlUtils extends PokedgramBot {

    public static InlineKeyboardMarkup makeButtons(int moveCount, HashMap[] playerDataIngame) {

    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    InlineKeyboardButton buttonFoldPass = new InlineKeyboardButton();
    InlineKeyboardButton buttonCheckCall = new InlineKeyboardButton();
    //InlineKeyboardButton buttonBet = new InlineKeyboardButton();
    InlineKeyboardButton buttonBetX1 = new InlineKeyboardButton();
    InlineKeyboardButton buttonBetX2 = new InlineKeyboardButton();
    InlineKeyboardButton buttonBetX3 = new InlineKeyboardButton();
    InlineKeyboardButton buttonBetX4 = new InlineKeyboardButton();
    InlineKeyboardButton buttonBetAllin = new InlineKeyboardButton();


    int minBetSize = bigBlindSize;
    buttonCheckCall.setText("check/call");
    buttonFoldPass.setText("fold/pass");
    buttonBetX1.setText("bet " + minBetSize);
    buttonBetX2.setText("bet " + minBetSize * 2);
    buttonBetX3.setText("bet " + minBetSize * 3);
    buttonBetX4.setText("bet " + minBetSize * 4);
    buttonBetAllin.setText("\uD83D\uDD34 all-in");

    buttonCheckCall.setCallbackData("checkcall");
    buttonFoldPass.setCallbackData("fold");
    buttonBetX1.setCallbackData("bet " + minBetSize);
    buttonBetX2.setCallbackData("bet " + minBetSize * 2);
    buttonBetX3.setCallbackData("bet " + minBetSize * 3);
    buttonBetX4.setCallbackData("bet " + minBetSize * 4);
    buttonBetAllin.setCallbackData("allin " + playerDataIngame[ moveCount % playerDataIngame.length ].get(CHIPS_VALUE));

    List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
    List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

    keyboardButtonsRow1.add(buttonFoldPass);
    keyboardButtonsRow1.add(buttonCheckCall);
    keyboardButtonsRow2.add(buttonBetX1);
    keyboardButtonsRow2.add(buttonBetX2);
    keyboardButtonsRow2.add(buttonBetX3);
    keyboardButtonsRow2.add(buttonBetX4);
    keyboardButtonsRow2.add(buttonBetAllin);

    List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

    rowList.add(keyboardButtonsRow1);
    rowList.add(keyboardButtonsRow2);

    inlineKeyboardMarkup.setKeyboard(rowList);
    return inlineKeyboardMarkup;
}



}
