package org.pokedgram.utils;

import org.pokedgram.ConstStrings;
import org.pokedgram.PokedgramBot;

import java.util.HashMap;

public class MsgUtils extends PokedgramBot {
    static public String roundAnnounceText(HashMap[] players) {
        return "1. @" + players[ 0 ].get("nickName").toString() + " bet: " + players[ 0 ].get("currentRoundBet").toString() + ". " +
               "Balance " + players[ 0 ].get("chips").toString() + ConstStrings.NEXTLINE +
               "2. @" + players[ 1 ].get("nickName").toString() + " bet: " + players[ 1 ].get("currentRoundBet").toString() + ". " +
               "Balance " + players[ 1 ].get("chips").toString();
    }

}
