package org.pokedgram.other;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static ch.qos.logback.core.util.Loader.getResourceBySelfClassLoader;

// TODO() check sources and test
public class AsCinema extends TelegramLongPollingBot {

    URL cfgPath = getResourceBySelfClassLoader("config.tg"); // added to .gitignore. 1 line - token, 2nd - roomId, 3rd - room invite link
    URL stickerPath = getResourceBySelfClassLoader("stickerpack.list");
    Path path, path1;
    List<String> cfg, stickerpack;
    SendMessage message = new SendMessage();

    {
        try {
            path = Paths.get(cfgPath.toURI());
            path1 = Paths.get(stickerPath.toURI());
            cfg = Files.readAllLines(path, StandardCharsets.UTF_8);
            stickerpack = Files.readAllLines(path1, StandardCharsets.UTF_8);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
    String playingRoomId = cfg.get(1);
    //String playingRoomLink = cfg.get(2);

    @Override
    public void onRegister() {
        try {

            message.setChatId(playingRoomId);
            sendSticker();
            message.setText("Welcome to ASCIINEMA");
            //execute(message);
            System.out.println("onRegister ok: " + message.toString());
            aniGif();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("onRegister exception: " + message);
        }
    }

    @Override
    public String getBotUsername() {

        return cfg.get(3);
    }

    @Override
    public String getBotToken() {

        return cfg.get(0);

    }

    public void sendToPlayingRoom(SendMessage message, String text) {
        try {
            message.setChatId(playingRoomId);
            message.setText(text);
            execute(message);
            Thread.sleep(2000);
        } catch (TelegramApiException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendSticker() throws InterruptedException {
        try {
            String currentId = message.getChatId();
            message.setChatId(playingRoomId);
            SendSticker sendSticker = new SendSticker();
            sendSticker.setChatId(playingRoomId);
            sendSticker.setSticker(new InputFile(stickerpack.get(0)));
            execute(sendSticker);
            try {
                message.setChatId(currentId);
            } catch (NullPointerException e) {
                System.out.println("exception: " + message);
            }
            Thread.sleep(2300);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            Thread.sleep(5000);
        }
    }

    public Integer sendToPlayingRoomAndGetMessageId(SendMessage message, String text) {
        int messageId = -1;
        try {
            message.setChatId(playingRoomId);
            message.setText(text);
            messageId = execute(message).getMessageId();
            Thread.sleep(3000);
        } catch (TelegramApiException | NullPointerException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("exception: messageId = " + messageId + "; message: " + message);
        }
        return messageId;
    }


    public void editMessage(String text, Integer messageId) {
        EditMessageText msg = new EditMessageText();
        msg.setParseMode("MarkdownV2");
        try {
            msg.setChatId(playingRoomId);
            msg.setMessageId(messageId);
            msg.setText(text);
            execute(msg);
            Thread.sleep(2500);
        } catch (TelegramApiException | NumberFormatException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("exception: messageId = " + messageId + "; message: " + msg);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("onUpdateReceived inited");
        SendMessage message = new SendMessage();

        if (update.hasMessage() && update.getMessage().hasText()) {
            String userName, userId;
            userName = update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName();
            userId = String.valueOf(update.getMessage().getFrom().getId());
            System.out.println("got update with text, from userName" + userName + "(" + userId + ")");


        }

    }

    public void aniGif() {
        String[] n = new String[ 108 ];//{};
        //frames
        //TODO() refactor frame storing
        {
            n[ 0 ]   = "```" +
                       "                  .                                                                                 \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                            ..                                      \n" +
                       "             .                                                                                      \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                               .                                    \n" +
                       "                                 .                                                                  \n" +
                       "                                 .                                                                  \n" +
                       "      .                                                                            ,.               \n" +
                       "                                                                                   .                \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                              .                                                                     \n" +
                       "                                                                                                    \n" +
                       "                       .  .                                                                         \n" +
                       "```";
            n[ 1 ]   = "```\n                  .                                                                                 \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                            ..                                      \n             .                                    .                                                 \n                                                 ...                                                \n                                                .   .                                               \n                                               .     .                                              \n                                             ...      .                                             \n                                     ...               .      ..                                    \n                                       .                     .                                      \n                                        ..                 .                                        \n                                           .             .     .                                    \n                                 .         .             .                                          \n                                 .         .             .                                          \n      .                                   ..              .                        .,               \n                                          .. .         .  .                         .               \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                              .                                                                     \n                                                                                                    \n                      ..  .                                                                         \n```";
            n[ 2 ]   = "```\n                  .                                                                                 \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                            ..         .                            \n             .                                   .,.                                                \n                                                .,.,.                                               \n                                               .,  .,.                                              \n                                              .,.    ,.                                             \n                                       .......,..    .........                                      \n                                    .,......           ........,.                                   \n                                      ...                   ...                                     \n                                       ....               ...                                       \n                                          ..             ..    .                                    \n                                           ,             ,                                          \n                                ..        ..             ..                                         \n                                          ,.  .........  .,                         ,               \n                                         .,.....     .....,.                        .               \n                                         ....           ....                                        \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                              .                                                                     \n                                                                                                    \n                      .   .                                                                         \n```";
            n[ 3 ]   = "```\n                  .                                                                                 \n                                                                                                    \n           .                                                                                        \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                             .         .                            \n            ..                                   ,:,                                                \n                                                ,:.,,                                               \n                                               ,:.  ,,                                              \n                                              ,:.    ,,                                             \n                                     .....,,,,,..    .,,,,.........                                 \n                                    ,:,......          .......,:,.         .                        \n                                     .,,.                    .,.                                    \n                                       .,,.               .,,.                                      \n                                         .,,             ,,.   .                                    \n                                          ,,           ..:,                                         \n                                ..        :,             .:                                         \n                                        ..:.  ..,,,,,..   :.                        ,.              \n                                         .:.,,,,.. ..,,,,.:,                        ..              \n                                         ,:,..         ..,:,                                        \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                      .   .                                                                         \n```";
            n[ 4 ]   = "```\n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                  ,.         .         .                            \n            ..                                   :i;..                                              \n                                               .;;.::.                                              \n                                              .;;.. ::.                                             \n                                             .;:   . ::.                                            \n                                    ..,,,,,::::..     ::,,,,,,,.....                                \n                                   ,i;:,,....          .....,,,:;,         .                        \n                                    .,:,.                    .:,.                                   \n                                      .,:,.                ,:,.                                     \n                                        .,:,             ,:,.  .                                    \n                                          :,            .:;                                         \n                                ..       .;.           .  ;.                                        \n                                         ,;    .,:::,..   ;, .                      ..              \n                                         ::.,:::,...,:::,.,;                        ..              \n                                       ..i;::,.       ..,::i.                                       \n                                         ..               ..                                        \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                         ..                                                                         \n```";
            n[ 5 ]   = "```\n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                  ...        .                                      \n                                                 :fi..       .                                      \n            .                                  .;t:1;..                                             \n                                              .;t:..1;.                                             \n                                             .;t,....i;                                             \n                                         ...,;t,    ..1;....        ..                              \n                                  ,::;;;;iiii;...    ..;;;;;;;;;:::,.                               \n                                 .iti:,,,,..              ...,,,;ti.       .                        \n                                  .,;i:..                    .,;;.                                  \n                                    .,;i:.                 .:i;,                                    \n                                       .;i;..             :i;, .                                    \n                                .        :t..           .,1i                                        \n                                .       .ii           ....:1..                                      \n                                        .t:.    .,::..   .,t,.                       ..             \n                                        :1...,;ii;;;i;:,...1;.                       ..             \n                                      ..i1:iii;,.   .,:;i;:it..                                     \n                                      ..11;:.           .,;i1.                                      \n                                        ..                                                          \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                         .                                                                          \n```";
            n[ 6 ]   = "```\n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                  ;,,.       .                                      \n                                                .1ft,.       .                                      \n            .                                 ..tt,;t,.                                             \n                                             ..t1,..;t,.                                            \n                                            ..t1.. ..;t,.                                           \n                                      ....,::t1.    ..;t,,,....      ..                             \n                                 ,;;iiiiiiiii;...   ...;;;iiiiii;;;;:.                              \n                                .;ft;,,,,..                 ..,,:1t;.      .                        \n                                ...;ii,...                    .;i;.                                 \n                                  ...;ii,.                  .;i;.                                   \n                                      .:ii,.              .;i;. .                                   \n                                .       .ti.            ..;f..                                      \n                                .      .,f:            ....t:.                                      \n                                       .;f..     .,,.  . ..1i..                      ..             \n                                    . ..1i.  .:;i1iii;:....:t..                      .,             \n                                      ..f:,;i1i;,...,:iii;:,f:.                                     \n                                     ..:L11i:.        ..,;iiLi.                                     \n                                      .,:.                ..,.                                      \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                         .                                                                          \n```";
            n[ 7 ]   = "```\n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                                                                    \n                                                    ..                                              \n                                                 ,fi,.       .                                      \n                                               .:L1fi..      .                                      \n           ..                                 .:L;,,t;..                                            \n                                             .:L;...,t;..                                           \n                                           ..:L;.  ..,t;.                                           \n                                   ....,,::;if;.     .,t;::,,,...    ...                            \n                                :1ii111iiiii;, ..    ..,;;;iiiiiii;ii:.                             \n                               .:tti,,,...                   ....:1t:                               \n                               ...:i1:...                     .,ii:.                                \n                  .              ...:i1;..                  .,i1:.                                  \n                                     .:i1;..               ,ii:.                                    \n                                .      .:f,.             .,fi.                                      \n                                .      .;f..            .,.i1..                                     \n                                      ..ti..      ..  ... .:f,.                       .             \n                                    . .,f:.   .:;11i;:,. ...f;..                      ,             \n                                      .;t..,;111;:,,;i1i;:,.11..                                    \n                                     ..tfi11i:.      ..,;i111L,.                                    \n                                    ...11:,.             .,:i1.                                     \n                                      ..                                                            \n                                                                                         .          \n                                                                                                    \n                             .                                                                      \n                         .                                                                          \n```";
            n[ 8 ]   = "```\n                                                                                                    \n                                                                                             .      \n                                                                                                    \n                                                                                                    \n                                                  :,,. .                                            \n                                                .1Lf:..      .                                      \n                                              ..1f:if,.                 .                           \n           .                                 ..1f,,.it,.                                            \n                                            ..1f,....it,.                                           \n                                           .,1f,.   ..it..               .                          \n                                  ..,,,::;;itt.      ..it;;;::,,....  .,.                           \n                               :t11111iiiii;,. .       .:::;;iiiiiiit1:.                            \n                              .:tfi:......             .       ...;t1:                              \n                              ...,i1i,..                    .  .:1i,.                               \n                  .             ...,i1i,..                   .:1i,                                  \n                                    .,i1i,..               .;1i:.                                   \n                                      ..t1..              .iL:.                                     \n                                .     ..ti.             ..,,f,.                                     \n                                      .,L,.            .. ..ti..                      .             \n                                    . .it..    .:;ii:..  ...if..                      ,.            \n                                     ..fi. .,;1t1i:;i1i;:,..,L:..                                   \n                                     .,L:;it1i:,     .:;i1ii:fi..                                   \n                                    ..iC11;,.           .,:i1ft..                                   \n                                     .,,.                  ..,.                                     \n                                                                                         .          \n                                                                                                    \n                             .                                                                      \n                         .                                                                          \n```";
            n[ 9 ]   = "```\n                                                                                                    \n                                                                                             .      \n                                                                                                    \n                                                    ..                                              \n                                                 ,f1,. .                                            \n                                               .:Ltfi..      .                                      \n                                              .:L1,:fi..                                            \n           .                                 .:Li...,fi..                                           \n                                           ..:Li.. ..,fi..                                          \n                                          ..:Li.    ..,fi..          .   ..                         \n                                .,,:::;;;i11f;.      ..:fiii;;;::,,,....,.                          \n                             .;ft1111ii;;;:,.  .       ..,,::;;iii1i1ft:.                           \n                             .:1t1:.....                        ...;t1,                             \n                              ..,it1:...                        .;1i,.                              \n                                ..,;11:..                    ..;1i,.                                \n                                  ...;11:...                .;1i,                                   \n                                     ..;L:.                ,ft,.                                    \n                               ..     .:L,.              .,,11..                                    \n                                     ..1t..         .  .....:L,.                       .            \n                                    ..,L;..     .,::.     ..,L;.                      .,            \n                                   . .:L,.  .,;1t1i111;,.....1t..                                   \n                                    ..1t.,;1tt1;,...,:i11i;:,;L,,.                                  \n                                    .,Lt1t1i:.         .,;i1t1C;..                                  \n                  .                ..,ti:.                ..:;1:                                    \n                                    ...                                                             \n                                                                                                    \n                             .                                                                      \n                         .                                                                          \n```";
            n[ 10 ]  = "```\n                                                                                                    \n                                                                                             .      \n                                                     .                                              \n                                                  ::,.                                              \n                                               ..1CL:..                                             \n                                              ..tL:iL,..     .                                      \n                                             ..tL:,.if,..                                           \n           .                           .    ..tL:....if,..                                          \n                                          ...tL,..  ..if,..                                         \n                                        ..,,tL,    . ..if,...             ..                        \n                              .,::;;;iii11ttt,        ..iti1iiii;;:::,,.,,.                         \n                            .iLf111ii;;:::,.   .        ...,,::;;iiii1Lf:.                          \n                            .:it1:.... .                          ..;ti,    .                       \n                             ..,;t1:...                       .  .iti,                              \n                               ...;11:...                      .iti,                                \n                                 ...;11;..                   ,iti,                                  \n                                   ...:f1..                 :Li,.                                   \n                               ..    ..t1..               .,:L,..                                   \n                                     .,L:.               ....f1..                      .            \n                                    ..iL..        .,.  .   ..if..                      ,.           \n                                    ..f1..   .,:1tt11;,.. ..,,L:..                                  \n                                    .,L:...:itt1i:,,;i11i;,,..f1..                                  \n                                   ..iL:i1tti:.       .,;111i;1L,,                                  \n                                   ..tL11;,.             ..:i1tC,.                                  \n                                   ..:,                     ...,                                    \n                                                                                                    \n                             .                                                                      \n                         .                                                                          \n```";
            n[ 11 ]  = "```\n                               .                                                                    \n                                                                                             .      \n                                                  ....                                              \n                                                 ,Lf:,.                                             \n                                               .:Ctft,.                                             \n                                              .:C1,,f1..     .                                      \n                                             .:C1,,.,f1..               .                           \n          ..                           .   ..:C1.. ..,f1..                                          \n                                         ...;C1..    .,f1..                                         \n                                    ....,,:iCi.    . ..,f1,,,...           ..                       \n                            .,:;;iii11111tt1;.        ..,11111111ii;;;:,,,,.                        \n                           .1Cf1ii;;:::,,,.    .          ....,,::;;ii1LL;. .                       \n                          .,,;tt;....  .               .           .,iti,   .                       \n                            ..,;tt;,..                           .,iti,.                            \n                              ...;tt;....                       ,iti.                               \n                                ...:1t;,..                    ,iti.                                 \n                               .   ..:1f,.                  .ifi,                                   \n                               ..   ..:L,.                 .:tf..                                   \n                                    ..1f..               ....:C,.                                   \n                                    ..Li..         ..  ...  ..Li..                     ,,           \n                                   ..;C,.      .:i1i;,.   ....1f..                                  \n                                   ..tf..  .:i1ft1;i1t1i:,...,:C:..                                 \n                                   .,L;.,;1tt1;,.   ..:i1t1i:,,fi..                                 \n                                  ..;Gttfti:.           .,:itt1LL,.                                 \n                                  ..;fi:.                  ..:;11.                                  \n                                   ...                       .                                      \n                             .                                                                      \n                                                                                                    \n```";
            n[ 12 ]  = "```\n                                                                                                    \n         .                                          ..                                              \n                                                 .ii,,                                              \n                                               ..tCCi,.                                             \n                                              .,fL:iC;..                                            \n                                             .,fL:,.;L;..    ..                                     \n          .                                 .,fL:....;L;..              .                           \n          .                           ..   .,fL:..  ..;L;..                                         \n                                         ..,fL,.     ..;L:..                                        \n                               ......,,,:;ifL,        ..;Ci::,,,...         ..                      \n                           ,:;i1111ttttttt1i.         ...;ii111111111ii;:;::.                       \n                         .,tGf1i;;::,,...                    ....,,:;;;1LCi..                       \n                         .,,;tti,...  .                             .,iti,  .                       \n                           ..,;tti,..                             .,1ti,.                           \n                             ...:tti,.....                      .,1ti.                              \n                                ..:1ti,.                      .,1ti.                                \n                               .  ..:1f;..                   .tfi,.                                 \n                               ,.   ..L1..                  ,;Ci..                                  \n                                    .:C:.                 ..,.tf..                                  \n                                   ..iL..           ..   .. ..;C,.                      :           \n                                   ..f1..       .,::,.      ..,Li..                                 \n                                  ..:C:..   .,;1ffttt1i:.....,.tf..                                 \n                                  ..1L...,:itfti:...,;itt1;:,..;C,,.                                \n                                  ..ft:itft1;,        ..,;1tt1i:Ci,.                                \n                                 ..:GLt1;,.               ..:ittCf..                      .         \n                                  .,;,.                      ..,:,                                  \n                             .    .                                                                 \n                                                                                                    \n```";
            n[ 13 ]  = "```\n                               .                                                                    \n                                                  ,,,,                                              \n                                                .;GC;,.                                             \n                                               .iG1tL:..                                            \n                                              .iGi,,tL,..                                           \n                                            ..iGi,...tL,.. . ..                                     \n          .                                ..iGi,. ...tL,..             .                           \n          .                           ..  ..iGi..   ...tL,..                                        \n                                         .,iGi.       ..tf,..                                       \n                             .....,,::;;i1tC;.        ...ffii;;::,,,...  ..  ..                     \n                         .;ii1ttttttt1111i;,          . .,;;;i1111tttt111iii;,                      \n                        .:LGfi;::,,.....       .                ....,:::iLCi,                       \n                        .,,;tfi,...                                ...,iti, .                       \n                      .   ...;tfi,..                               .,1fi,                           \n                            ...:tfi,..  .                         ,1ti.                             \n                              ...:1fi,.                        .:1fi.                               \n                               ....:1f1,.                     ,tti.                                 \n                               ,. ...iC,.                   .,1G,..                                 \n                                   ..ff..                  .,,,C;..                                 \n                                   .,Ci..                 .. ..ft..                     :.          \n                                  ..;C,.          ...        ..;C,.                                 \n                                  ..ff..      .:ittt1;,.   ...,,Ci..                                \n                                  .,C;..  .:itff1;::i1tti;,...,.ff..                                \n                                 ..iC,.,;1ffti:.     .,:ittti;:,;C,,.                               \n                                 ..fC1ffti:.             .,;1tft1Gi,.                     .         \n                                 .,ffi;,.                   ..:i1Li                                 \n                             .   ..,.                         .. .                                  \n                                                           ..                                       \n```";
            n[ 14 ]  = "```\n                               .                 .;i:,. .                                           \n                                               ..tG01,..                                      .     \n                                              ..tGiiGi..                                            \n                                             .,tG;,,iGi..                                           \n                                            .,tG;,...;Gi..                                          \n                                           .,tG;,.  ..iGi..  ..                                     \n         ..                               .,tG;..    ..;Gi..            .                           \n                                      ....,tG;..      ..iGi...                                      \n                                    ...,,:fG;.         ..iGi,,...            .   ..                 \n                        ..,,::;;ii11tttffff:.           ..ifttttt11ii;;::,,,...,,.                  \n                      .1Lffffttt11ii;;::,.                ...,,::;;ii11ttttttLt;.                   \n                     .,iLCt:,,...    .         .                      ....:tCf,                     \n                     ...,;fL1:...                                    ...,1f1:.                      \n                        ..,;tf1,...                                  .,1f1,.                .       \n                 .        ...;tL1,...                              .,1f1,.                          \n                            ...:tL1:..                          ..,1f1,                             \n                              ...:tft:..                        ,tf1,.                              \n                              .,...:Lf,.                      .:Cf,..                               \n                                  .,L1...                    .::ff.,                                \n                                 ..;G;..                    ....iG,..                    :.         \n                                 ..tC,.             ..     .  ..,Gi..                             . \n                                 .,Ct..    .     .,:..        ...ff..                               \n                                ..;G:..      .,itffft1;,.. .  ...iG,,.                              \n                 .              ..tC,,.  .,;1fLf1:,,;1tfti;,...,,:Gi..                              \n                                .,C1..,;1fLf1;,       .,;1tfti;,,,fL,,.                             \n                               .,;G11fLLti,.             ..:itfft1tG:,.                             \n                            .. .,1Gfti:.                     .,:itfG;.                              \n                               .,::.                       .  .....,.                               \n```";
            n[ 15 ]  = "```\n                               .               ..;G0i,..                                            \n                                               .i0ffG;,.                                            \n                                             ..i0t:,tC:,.                                           \n                                            ..iGt,,,,tC:,.                                          \n                                           ..;Gt,...,,tC:..                                         \n                                          ..;Gt,..  ..,tC:.. .,                                     \n         .                               ..;Gt,.     ..,tC:..           .                           \n                                      ....i0t..        .,tC:..                                      \n                               .....,,::;10t.          .,,fC;::,,,....        .   ..                \n                      ..::;;ii11tttffffff1i..           ..,1tttttftttt11ii;::,,,,:.                 \n                    .:CCffftt1ii;;::,,,.                   .....,,,:;;ii11tttfCLi.     .            \n                    ,:1LL1:,....     .         .                        ...,1Lf:                    \n                    ...,ifL1:...                                      ...,1Lt:.                     \n                      ...,;fL1,..                                   . .,1Lt:. .             .       \n                 .       ...;fL1,...                                 ,1ft,.                         \n                 .         ...;tL1:..                           . .,1Lt,.                           \n                             ...:tLt:..                          :1L1,.                             \n                              .,..:fC:,.                       .;Gt:...                             \n                                ...1C,...                     .:;Gi..                               \n                                 .,Ct..                      ....fL..                    ,,         \n                                ..:G;..                     .  ..iG:,.                            . \n                                ..tC,.     .       ....    .   .,,Gi..                              \n                                .,Ct..         .:itti:..   .   .,.fL...                             \n                 .             ..;G;..     .:itLLti1tffti:......,,iG:,.                             \n                               ..tC,.. .,itLLf1:.  ..,;1tff1;,...,:G1,,.                            \n                               .,Ct,:;tLLf1;,.       . ..,;1fff1;:,fL,,.                            \n                            ...,;0ffLLt;,.                 ..:;1fLfL0;,.                            \n                              .,iCti:.                     .. ..,:itC:.                             \n```";
            n[ 16 ]  = "```\n                                               .,CLfC:,.                                            \n                                              .,CC;:LL,,.                                           \n                                             .:CC:,,,LL,,. .                                        \n                                           ..:CC:,..,,LL,,.                                         \n                                          ..:CC:,.  .,,LL,,.  .                                     \n                                         ..:CC:..    .,,LL,,...                                     \n         .                              ..:CC:..      .,,LL,,.          .                           \n                                      ...:CC,.         .,,LL,..                                     \n                          ......,,::;;i1tCC,.           .,:CLiii;;::,,....      .  ...              \n                    .,:ii11tttfffffftttti;.              ..:ii11tttffffttt11ii;::::,.               \n                  .,t0Gftt1ii;;:,,,....                    .  . ....,,::;;i111tC0t:.                \n  .               .,:1LL1,.....                .                         ....iff;.                  \n                  ...,,iLL1,...                                        ....iLf;..                   \n                     ...,ifL1,...                                      ..iLf:.                      \n                 .      ..,;fL1,..                                   ..iLf:.                        \n                 .        ..,;fL1:..                            .  ..1Lt:.                          \n                            ...;fLt:...                           ,1Lt:.                            \n                              ,,.;tG;,.                         .;Gf:....                           \n                                ..:Gi,. .                      .:1G:..                              \n                                .,1G,,.                       .,,,G1..                    :         \n                                .,Cf..                       .  ..fL,..                   .         \n                               ..:0i..     .        ...         .,iG:,.                             \n                               ..tG,,.           ,:;,.          .,,G1..                             \n                 .            ..,Cf,.       .,;1fLLLft1:,.. ..  .,.fC,,.                            \n                              ..;0;,..   .:ifLLfi:,,:itffti:,....,,;0;,.                            \n                              ..tG,...:itLLf1;.      ..,:1fLfti:,,,:Gt,,.                           \n                            ..,,Cf:itLLf1;,.             ..,;1fLfti;fC,,.                           \n                            ..,;0CLLti,.                   ....:itLLC0:.                            \n```";
            n[ 17 ]  = "```\n                                               .tG;:CL,,.                                           \n        .                                    ..f0i,,;Gf,,.                                          \n                                            .,f0i,.,,;Gf,,.                                         \n                                           .,f0i,. ..,:Gf,,.                                        \n                                          .,f0i,.    .,;Gf,,. .                                     \n                                        ..,f0i,.      .,;Gf,,.,                                     \n        ..                             ..,f0i..        .,;Gf,,..         .                          \n                                     ..,:f0;.           .,;Gt,....                     .            \n                       ....,,,::;;i11ttfLG;..           ..,iGftt111i;;::,,........  .,.             \n                  .:;1tttfffffffftt111i;,.                ..,:;;ii1ttfffffffttt1iiii:.              \n                 .;C8Ct1ii;::,,.......                        .     ....,,::;;i1L0C;.  .            \n  .             .,,:1LLi,.....                 .                          ....;fLi.                 \n                 ...,:1LL1,...                                            ..;LLi..                  \n                    ...,iLL1,...                                         .;LLi.                     \n                 .    ...,iLL1,...                                     .iLf;..                      \n                         ..,iLL1:..                             .    .iLf;.                         \n                           ..,ifC1:,..                             ,iLf;..                          \n                             .,,;fC1,,.                           iCf;...                           \n                               ..,Cf,,  .                       .:LG,.. .                           \n                               .,;0i,.                         .,,:0;,.                   ,,        \n                               .,1G:,.                       ..  .,Gt...                  .         \n                              .,,CL,.      .         ..         .,.tC,,.                            \n                              .,;0i,.             .....          .,;0;,.                            \n                              .,tG:,.         .,;1ff1;,..   .    .,,Ct.,.                           \n                              ,,Cf,,.     .,;1LCLtiitLLf1;,.......,,tG,,.                  .        \n                             .,;0i,.   .:1fCCf1:.   .,:itLLfi:,...,,;0;,.                           \n                            ..,tG,..,ifLCf1;,.         ..,:1tLLti:,,,Cf..                           \n                            .,,Cf,ifCLt;,.                 ..,;1fLL1:tC..                           \n```";
            n[ 18 ]  = "```\n                                              .i01,,:Gt,,.                                          \n                                             .i0t,,,,i01,,.                                         \n                                           ..18t,....,i01,,.                                        \n                                          .,10t,.    .,i01,,.                                       \n                                         .,10t,.      .,i01,,...                                    \n                                        .,10f,.        ,,i01,,,                                     \n        ..                            ..,18f,.          .,i01,....       .                          \n                                 ....,:;t8t.             .,i01:,,....                   ..          \n                    ..,,,::;;ii1tttffLLLL1..             ..,1Lffffftt11ii;;:,,,.....  ,,.           \n                .;1tffLLLLfffftt1ii;;;:.                   ...,,::;;i11ttfffffffft1tt1,             \n               .,t00fi;;:,,,....   ..                         .        .....,,::;tG01, ..           \n               .,,;tCLi,,,..                   .                           ....:fL1,.               \n               ....,:tCLi,...                                             ...;fC1,.                 \n                   ...:1CLi,...                                           .;fC1,.                   \n                ..   ...,1LL1,..                                        .;fL1..                     \n                       ...,1LC1,...                             .     .;fLi..                       \n                         ...,iLC1:,.                               ..;LLi..                         \n                            ..:iLC1,,.                             ;CLi,..                          \n                              ..,fG,,.  .                        .;GC,...                           \n  .                            .,Cf,,                           ,:,1G:,.                  .:        \n                              .,;0i,.                         .....:0i,.                   .       .\n                              .,t0:,.      .                     .,,Cf,,.                           \n                             .,,CL,.                ...          ..,10:,.                           \n                             .,;0i,.            .,;i:..     ..    .,:0i,.                           \n                             .,tG:,.        .,;tLCLLLLt;,.....    .,,CL,,.                          \n                            .,,Gf,,      ,;1LCCfi:,,:itLLf1;,......,,10:,.                          \n                            .,;0i..  .,ifCCf1;..     ..,:itLLf1;,,,,,:0i,.                          \n                            ,,tG,...iLCLt;,.             ..,;1fLLt:,,,Cf..                          \n```";
            n[ 19 ]  = "```\n                                            ..:Gf,...i0i,,.                                    .    \n                                            .;GC:...,,t0i,.                                         \n                                          ..;0C:..  ..,t0i,,.                                       \n                                         .,;0C:..    .,,t0i,.                                       \n                                        .,;0C:..      .,,t0i,..                                     \n                                      ..,;0C:.         .,,t0i,,.      .                             \n        .                            ..,;0C:.           .,,t0i....      ..                          \n                      ..  .......,,::;1t0C,.             .,,f01i;::,,......         ..   ...        \n                 ..,::;;ii11ttffLLLLLLLt1,.               ..:1ttfffLLffftt11i;;::,,,...,,.          \n              .:fLLLLLLLfftt1ii;;::,,,.                     ......,,::;;i11ttfffLLffLLf;.           \n             .,;L0G1;:,,,...      ..                          ..           ....,,,iL0L:..           \n             .,,,;fCLi,,,..                    .                            ....:tCf:.              \n               ...,:tCLi,...                                               ...:tCf:.                \n                  ...:tCLi,..                                              .:tCt:.           .      \n                .   ...:tCLi,...                                         .:fCt:.                    \n                .     ...:1CLi,...                              .      .:fCt,.                      \n                        ...:1CC1,,.                                 ..:fCt,.                        \n                          ...,tCC1,,.                               ;LCt,.                          \n                            ...,t8;,.   .                         .;0L:...                          \n  .                           .,tG:,.                            ,::LL,,.                  ,.       \n                             .,,GL,,                            ....10:,.                  ..      .\n                             .,;8i,.       .                   .  .,:01,..                          \n                             .,f0:,.                 ..           .,,LC,,.                          \n                .           .,:Gf,.               ..,..     ..    ..,i0;,.                          \n                            .,i8i,.           .,itLLti:...         .,:0t,.                          \n                            .,fG:,.       .,;tLCCf1itLCLti:....   ..,,LC,,.                         \n                           .,,Gf,.     .:1LCCf1:.. ...:itLLf1;,......,i0:,.                         \n                           .,;0;.   .,iLGLt;,.         ..,:1fLCt;....,:Gi.                          \n```";
            n[ 20 ]  = "```\n                                            .,CG:,. ..10i,..                                        \n                                           .,C0;..  .,,f0;,.                                        \n                                          .:C0;..    .,,L0;,,.                                      \n                                         .:C0;,.      .,,L0;,.                                      \n                                       ..:C0i,.        .,,L0;,,.                                    \n                                     ..,:C0i..          .,,L0;:,.     .  .                          \n        .                          ...,;C0;.             .,,LG;.....    .. .                        \n                    ........,,::;;i11tLG0:.               .,:CGtt11i;;::,,......     ...  ...       \n               .,:;ii11ttffLLLLLLfffft1;,.                 .,:ii1ttfffLLLLLfftt11i;;::,,,:,.        \n            .:fGCLLLfftt1ii;::,,,....                        .. ......,,::;;ii1ttffLfLGGt:          \n            .:1C0Li:,,.....       .                           ..      .       .....:tGCi.           \n            ,,,:iLGL;,,,.                      .                             ....,1CLi..            \n              ...,;LGf;,..                                                   ..,1CLi..              \n                 ..,;fGf;,..                                                .,1CL;..         .      \n                .  ..,;fGLi,...                                           .,tCL;..                  \n                .    ..,:fGLi,...                               .       .,tCL;.                     \n                       ..,:tGLi,..                                    .,tCf;.                       \n                         ...:tGL1,,.                                 :fCf;...                       \n                           ...:t81,,                               .;0C;...                         \n                            ..,;0i,.                              .;;Gf,,.                 .,       \n                             .,f0:,.                             .,,.f0:,.                 .,      .\n                            .,:Gf,,.       .                    .  .,i8i,.                          \n                            .,i8i,.                                .,,Gf,,.                         \n                .         ..,,fG:,.                 ...            .,,f0:,.                         \n                           .,:0f,,.             .,;i;,....          .,;8i,.                         \n                           .,i8i,.          .,;tCCCCCLfi:,...       .,,Gf,,.                        \n                           ,,fG:.        .:1LCCL1:,,:itLCLti:,..    .,,tG:,.                        \n                          .,:Gt.      .,1LGCt;,.    ...,:1fCCf;.     .,;0;.                         \n```";
            n[ 21 ]  = "```\n                                           ..t0i,.   ..f0;,.                                        \n                                          .,f81,.    .,:CG;,.                                       \n                                         .,L81,.      .,:CG;,.                                      \n                                        .:f81,.        .,:CG;,.                                     \n                                      ..:f8t,.         ..,:CG;:,                                    \n                                   ...,:L8t,.            .,:CG;,....  .  .                          \n       ..                       ....,,;L81.               .,:CG;,,............                .     \n                 ......,,,:;;ii1ttffLCCG1..                .,;GCLLfftt11i;;::,,..........  .,.      \n            .,:i11tfffLLLLLLLLfft1111;,.                    ..,::;ii1ttffLLLLLLLfftt1ii;;:;:.       \n          .,t80CLfft1ii;::,,........                          ..   .......,,,::;ii1ttffG8Ci.        \n          .,;tCGf;,,.....     .                               .       ..        ....,iLCt,          \n .        ..,,:1CGf;,,..                       .                             .. ...iLGt,.           \n             ..,,iCGf:,...  .                                                ....iLC1,.             \n               ...,iLGf;,..                                                  ..iCC1,. .      .      \n                . ..,iLGf;,..                                              ..iCC1..                 \n                .   ..,iLGf;,...                                .        ..iCCi..                   \n                      ..,;LGL;,,.                                     ...1CCi..                     \n                        ..,;fGLi,..                                   ,1CCi..                       \n                          ..,;f0t:,.                                .;GCi,..                        \n                           ..,:0f,,.                               .;181,,.                 ,.      \n                            .,18i,..                              .,,,CC,,.                 ,.      \n                           .,,LG:,.        .                     .. ,,t8;,.                         \n                           .,:0f,,                                  .,;8t,,.                        \n                          ..,18i,.                   ..             .,,CC,,.                        \n                          .,,LG:,.                .,,..      .      .,,18;,.                        \n                          .,:0f,,             .,itLCfi:,....      .  .,:0t,,.               .       \n                          .,18;,.          .:1LGGL11tLCLti:,..       .,,CC,,.                       \n                         .,,LC.         .,1CGCt;,....,:1fCCLi,.       ,,10,.                        \n```";
            n[ 22 ]  = "```\n                                          ..18t,.     .,CG;,.                                   .   \n                                          .18f,.      .,;GG:,.                                      \n                                        .,t@L:.        .,;GG:,.                                     \n                                       .,t8L:.          .,;GG:,..                                   \n                                     ..,t@L:.            .,;GC;,..                                  \n                                  ...,,t@L,.             .,,;GG:,..........                         \n       .         . ...     .....,,::;iL@f,.               .,,;0Ci;::,,........ .  . ..         ..   \n              ...,,,,::;ii1ttffLLLCCCCLt,.                 ..,iLLLLLLLLfftt1ii;;:,,,.......  ,,.    \n          .;i1ffLLLCCCCLLLfft11i;;;::.                       ....,,::;;i11tffLLLLLLLLfftt1i11:.     \n        .,10@GLt11i;::,,,..... ....                            . . ............,,::;ii1tL08f:.      \n        .,:;fGCt:,,,.....                                     .                 ......:fGL;.        \n .      ...,,;tGGt:,,..                        .                             ..  ...:fGL;..         \n           ...,:tGGt:,..                                                      ....:fGf:..           \n              ..,:1CGt:,..                                                    ..:fGf:..             \n                ..,:1CGt:,..                                                ..;LGf:.                \n                . ...,1CGf;,..                                  .         ..;LGt:.                  \n                    ..,,iCGf;,..                                        ..;LGt,.                    \n                      ...,iCGLi,...                                    ,iLGt,.                      \n                        ...,iL0f:,.                                   :GGt:...                      \n .                        ..,,GG:,.                                 .;f81,,.                .,      \n                           .,;8t,,..                               .:::GL,,.                .,      \n                           .,t8;,..        .                      ...,,L0:,.                        \n                          .,,CG:,.                                   ,,i8i,..                       \n                .        ..,;8t,,.                                   .,:0L,,.                       \n                          ,,t8;,.                   ...              .,,f0:,.                       \n                         .,,CG:,.                ,;1;,....            .,i8i,,.              .       \n                         .,;8t,.             .:1LGCCCCfi:,..          .,:GL,,.                      \n                         ,,10:.           .,1CGCt;,,;1fCGLi,.         .,,fG,.                       \n```";
            n[ 23 ]  = "```\n                                        ..:GG;,.        .:0C:,.                                 .   \n                                       ..;00i,.         ,,i8L:,.                                    \n                                      .,i00i,.          .,,18L:,.                                   \n                                     .,i00i,.            .,,18L:,..                                 \n                          .     . ...,i08i.            .  .,,18L:,.....                             \n                        . ..  .....,:100;.                 .,,18L:,...............                  \n      ..    ...........,,::;ii1ttfLLC00;..                  .,:t8CLLfft11i;;::,,...............  ...\n        ..,:;;ii1ttffLLCCCCCCLLffttti:,                       .,:;i11tffLLLCCCCLLLftt11i;;:::,,,::. \n     .,tCCCCGCCCLfftt1i;;::,,......              ...           ..........,,,::;;i11tffLLCCCCLCCLi.  \n     .:t08Gt;::,,.......  ..   .                .. ..                ..... ............,,,:tG8L:.   \n    .,,,;1C0Li:,,,..                           ..   ..                             .....,iCGf:.     \n.     ..,,:1C0Li,,..                         ...     ....                     .      ..iCGf:..      \n         ..,,iC0Li,,..                ......            .. .....                   ..iCGf:.         \n           ..,,iL0Ci,,..               .                    ..                   ..iCGt,.           \n             ..,,iL0C1,,..              ...               ..                  ...iCGt,.             \n               ...,;L0C1:,..               ..           ..      ..           ..iCGt,..              \n             .   ..,,;L0C1:,..             .             .                 .,1GG1,.                 \n                   ..,,;f0G1::. .          .             .               .,1GG1,..                  \n                      .,,;fGGt:,,         ..   ... ....  ..             .tGG1,..                    \n .                      .,,:L8i:,.        ....         ....            ,L@t:..                .     \n                         .,,L0;,.  .                                 .:i181,,.               .:     \n                         .,;0C:,. .        .                        .,,,:0C,,.                      \n                         ,,1@1,,                                    . .,,L8;,,                      \n                        .,:L8;,.                                      .,,i@1,,.                     \n                        .,;0L:,.                                       .,:0C,,.                     \n                        .,1@1,.                      ..                .,,f8;,,             .       \n                       .,,L0:,.                  .,;;,..               .,,i81,,                     \n                       .,:0L.                 .,1CGGGC1,.               .,:GL..                     \n```";
            n[ 24 ]  = "```\n                                        .,C0i,..        ..;8L:,..                               .   \n                                       .:C81,.          .,,18L:,.                                   \n                                      .;G81,.            .,:t8L:,.                                  \n                                    .,;G@t,.              .,:t8L:,..                                \n                          ..   .....,;G@1,.                .,:t@L:,......                           \n              .  ..............,,::;108i.                   .,:t@L;::,,,.................           \n      ..  .......,,,::;ii1ttfLLCCCGGCCi..                    .,:fGCCCCCLLfft11i;;::,,............  .\n      .:;i11ttfLLCCCCCCCCLLftt1iii;:...           .           ..,,,::;ii1ttfLLLCCCCCCLLfft11i;;::;;,\n   .,t08GGCCLfft11i;::,,,.........               ,,,            ..............,,,::;ii1ttfLLCCG80L:.\n   .:iL00Li:,,,....... ..                       ,. ,,                 .      .............,,;L0G1.  \n   .,,:;fG0f;,,,..                             ,.   .,                                ...,:f0C1,.   \n.    ..,,;tG0f;,,..                     ......,,     .,......                 .      ...:f0Ci..     \n       ..,,:tG0f;,,..                .,........       ........,.                    ..:f0Ci..       \n          ..,:tG0f;,,..               ...                .  ...                   ..:f0C;...  .     \n            ..,:1G0Li,,..              ....               ...                   ..:L0L;..           \n              .,,:1C0Li,,..               .,.           .,.     ..            ..;L0L;.              \n             .  ..,:1C0Li,,..              ,.           .,                  ..;L0L;..               \n                  ..,,iC0Li,,,            .,     ...     ,.               ..;L0L:..                 \n                    ..,,iC0C1:,,.         ., ........... ,.              .1C0L;..                   \n.                     ..,,iC81:,.         ,,....     ....,,             ,f@L;,...             .     \n                        .,,t@i,,.         ..             ..           .:if8i,,.               :,    \n                        .,:G0:,.  .                                  .,:,:8L,,.                     \n                        .,;8L:,.                                    .. .,:C0:,.                     \n                       .,,t@1,.                                        .,,1@1,,.                    \n                       .,:G0:,.                                        .,,;8L,,.                    \n                       .,;8L:,.                                         .,:C0:,,.                   \n                      .,,t@i,.                       .                  .,,1@i,.                    \n                      .,:CG,.                   ..;ti,                  ..,;8f..                    \n```";
            n[ 25 ]  = "```\n                                       .,L81,.           ..i8L:,,.                               .  \n                                      .,L@f,.            .,,t@L:,.                                  \n                                     .:C@f:.              .,:f@L:,..                                \n                                  ..,;C@f:.                .,:f@L:,,.                               \n                          .........,;C@f,.                  .,:f@f:,.............                   \n      .   ................,,,::;i1tfG@t,.                    .,:f@Lt1ii;:::,,................       \n      .....,,,,::;ii1ttfLLCCCGCCCCGLti,.                      .,:tffLCCCCCCCLLLft11i;;::,,,........ \n   .:;1tffLLCCCGGGCCCLfft11i;;::::.   .          .,.           ......,,:::;ii1tffLLCCCCCCCLLfft11ii1\n .,t0@0CLLft1ii;::,,,............   .           .:::.             .................,,::;;i11tffL0@0t\n .,;1C0Gt;,,,.......   .                       .:,.,:.                          .............,1C0f:.\n .,,,:iL0Gt:,,,.                              .:,   ,:.                                 ...,1G0f:.. \n.   .,,,iL0Gt:,,..                    .....,,,:,   . ,:,,......               .         .,1G0t:..   \n      ..,,;f0Gt:,,..                .::,,,,,...       ..,,,,,,::.                     .,1G0t,.      \n        ..,,;f0Gt:,,..               .,,,                   ,,,.                    .,1GGt,.  .     \n           .,,;f00f;,,..               .,,,.             .,,,.                    .,tGG1,.          \n            ..,,;f00f;,,..                ,:.           .:,      .              .,tGG1,.            \n             ...,,:t00f;,,..              .:..          .;. .                 .,t0G1,.              \n                 .,,:tG0f;,,.,            ,:     ...     :,                ..:t0G1,.                \n                   .,,:tG0Li,,..       .. :, .,,,,,,,,.. ,:               .;f0G1,.                  \n.                    ..,:1G8t:,.         .;::,,..   ..,:::;.             .18G1,...             .    \n                      ..,,i@f:,.         .,..           ..,.            ,iC8i,,.              .;    \n                       .,:L8i,.                                       .,::;@f,,.                    \n                       .,;0G:,.                                      ....,:GG:,.                    \n                       ,,1@f:,.                                         .,,f@i,,.                   \n                      .,:L8i,.                                         ..,,i@f,,.                   \n                      .,;0G:,.                                          ..,:GG:,.            .      \n                     .,,i@f,.                                            .,,f8i,.                   \n                     .,:f8:.                                             .,,i8t..                   \n```";
            n[ 26 ]  = "```\n                                      ..t@f:.             ..1@L:,..                              .  \n                                     .,f@C:..             .,:f@f:,..                                \n                                    .:L@C;.               ..,:f@f:,..                               \n                               ....,:L@C:.                 ..,:f@f:,,... .                          \n                    .............,:;L@L,.                    .,;f@f:,,................              \n      . .............,,,:;;ii1tffLC0@L,..                    .,,:L8CLLfft11i;::,,,..................\n   ...:,:::;;ii1tffLLCCGGGGCCCLLLft;:..                        .,:i11tffLLCCCCGCCCLLftt1ii;::,,,,,..\n.,itfLCCGGGGGGCCLLftt1i;;::,,,,,,.    .         .,;,..          .........,,,::;;i11tffLLCCCGCCCCLfft\n,10@8Cft1i;;::,,,....... .......                :i;i:                   ...............,,,::;;i1tL8@\n,:itG0C1:,,,,.....                            .,i,.,i,        ..                      ........,;L0C1\n.,,:;tG8C1:,,..                              .,i,   ,i,        .                         ....;L0Ci,.\n  ..,,:1G8C1:,,.   .                ...,,,,:::;,     ,;::,,,,,....            .         ...;L0Ci..  \n     .,,:1C0C1:,..                 .,i;:::,,,..       ..,,,:::;i:.                     ..;L0C;..    \n       .,,:iC0C1:,,..                ,::,.               . .,::,.                    ..;L0C;....    \n         ..,:iC8G1:,,.                 ,::,.             .,::,                     ..iC0L;..        \n           ..,:iL0Gt:,,.                 .:;.           .;;,     .               ..iC0L;..          \n             ..,,iL0Gt:,,.                ,i...         .i,                    ..iC0L:..            \n               ..,,iL0Gt:,,..,  .        .;;     ...    .;;.                 .,iG0f:..              \n                 ..,,;f00f;,,,.        . .i:..,::;:;::,..,i.               .:1G0f:..                \n.                  ..,,;f00t:,.          ,1:;;:,.. ..,:;;:1,              .i00f:..                  \n                     ..,,i@C:,.          ,;:,.         .,:;,             .iG@1,,.              :,   \n                      .,,t@t,,.                                         ,;;1@t:,.                   \n                      .,:C8;,.                                        ....,;0C:,.                   \n                      .,;8C:,.                                           .,:L8;,,.                  \n                     .,:t@t:,.                                         . .,,1@t,,.                  \n                     .,:C8;,.                                          . .,,;0G:,.           .      \n                     .,;8C:.                                              .,:L8;,.                  \n                    .,:1@i..                                              .,,1@1..                  \n```";
            n[ 27 ]  = "```\n      .                              ..1@C:,.              ..1@L:,..                             .  \n                                    .,t@G;,.               .,:L@f:,..                               \n                                   .,f@G;..                .,,:L@f:,,.                              \n                            ......,:f@G;.                   .,,:L@f:,...... ..                      \n              ................,,:;1L@G:.                     .,::L@f;::,,,................          \n     ..........,,,::;;i11tffLCCGG00GC:..                      .,,;C0GGGCCLLfft11i;;::,,,............\n ..,:;;;i11tffLCCGGGGGGCCLLfft111i,..           ......          .,,::;;i11tfLLCCCGGGGCCCLfft1ii;;::,\ntLCGGGGGGGCCLfft1ii;::,,,.......     ..         .;ti..            ............,,,::;;i1ttfLLCCCGGGCC\nC@@Gf1i;::,,,.............    .                .;1:1;.                    . ................,,,:::;t\n:iL00fi:,,,...                                .;1,.,1;.        .      .                  .......,1C0\n,,:if00f;,,,.                               ..;1,. .,1i.                                 .  ..,1G0f:\n ..,,;f00L;,,..                    ..,,:::;;;ii,.   .,ii;;;:::,,...           .           ..,tG0t:..\n   ..,,;f00Li,,..                  ,i1i;:::,,..        .,,:::;i11,                      ..,t00t:..  \n      .,,;t00Li,,..  .              .:;;,...             ...,;i:.                     ..,t00t,..    \n       ..,,:tG8Li,,..                 .:i;:.             .:;i:.                      .:f0G1,..      \n          .,,:tG8Ci:,..                 .:ii.           .;i:.   ..                ..:f0G1,.         \n            .,,:1G8C1:,,..               .;1..         ..ii.                     .:f0G1,.           \n              .,,:1G8C1:,,. ..           .1;..   ...    .:1.                  ..:L0G1,.             \n                ..,:1C8G1;,...         ..,t,..,:;iii;:,..,t:. .              ,;L0Gi,.               \n.                 .,,:1C8G1:,.          .;t:;ii;:,..:;ii;:1;..              :C0G1,..                \n                    .,,:18G;,.          .i1;:,.       .,:;1i.             .;G@t:,.              ;   \n                     .,,i8L:,.           ..               ..             .;it@1:,.                  \n                     .,:f@1,,.                                          .,,,;8C:,.                  \n                   . .,;00;,.                                             .,:C8;,,.                 \n               .    .,,1@L:,.                                             .,:t@t:,.                 \n                    .,:f@1:,                                            . .,,;8C:,..         .      \n                    .,;00;,                                                .,:C8;,.                 \n                   .,:i@f...                                               .,,t@i..                 \n```";
            n[ 28 ]  = "```\n      .                             ..i8G;,.                ..t@L:,..                            .  \n                                   ..180i,.                 .,:L@f:,..                              \n                                ...,t@81,.                  .,,;C@f:,,.                             \n                       ..........,:t@0i.                     .,,;C@f:,............                  \n         ...............,,:::;;i1fC@0;..                      .:,;C@Lt1ii;::,,,..................   \n    .,....,,,::;;i11tfLLCCGGGGGG0GLt;.                         .,,;fLCCGGGGGGCCLLftt1i;;::,,,.......\n,,;i1ttfLLCCGGGGGGGCCLfft1ii;;;::. .           ...:,..           ..,,,,,::;;i1ttfLLCCGGGGGGCCLfftt1i\nGGGGGCCLLft11i;;::,,...........       .        .,tLt,.        .    ................,,,::;ii1tffLLCCC\n80fi;::,,...........                          .,t1:1t,.                       . ................,...\ntG8Gt;:,,,..                                 .,11,,,1t,.       .      .                      .....:t\n,:1C8Gt;,,..   .                           ..,11,. ..1t,..                               .. . ..;L8G\n.,,:iC80t;,,..                    .,,:;;;iiii11,.   ..i1iii;;;;:,,..           .            ..;C8Ci.\n  ..,:iL80t;,,..                  .;fti;;::,..         ..,::;;itfi.                       ..iC8Ci.. \n    ..,:iL80f;,,..  .              .,ii;,..              ....:ii:.                     ..,iC8C;..   \n      ..,,iL80f;,,..                ..,i1;,.             .,;1i:. .                    .,iG8L;..     \n        ..,,;L00f;,,..                 .,;t;.           .:ti:.  ..                  .,1G8L;..       \n          ..,,;f00L;,,...               ..ti..          .it,.                     .,1G8L:..         \n            ..,,;f08Li,,..  ,.          .:f:..    .     .,f:.                   .,1G0f:..           \n              ..,,;f08Li:,. ..         ..;f,...,;i1i;,....ti...               .,tG0f:..             \n                ..,,;t08Ci:,.          ..t1:;i11i:,:;11i;:it,.               ,f00f:...              \n                  ..,,;t80i:,.         .,ft1i:,.      ,:i1tL:.             .:C@L;,..            ::  \n                    ..,;8G:,.           .:,.             .,:.             .;1L@1:,.                 \n                    .,:t@f:,.                                            .,:,i@L:,.                 \n                   ..,:C@i,,.                                           .. .,:G0;,,.                \n               .   .,,i8G;,.                                               .,:f@1,,.                \n                   .,:t@f:,.                                               .,,i@L:,.         .      \n                   .,:C8i,.                                                .,,:G0;,.                \n                  ..,;8C,...                                                .,:f@i.                 \n```";
            n[ 29 ]  = "```\n                                   ..;00i,.                  .,f@L:,,.                            . \n                                  ..i88t,.                   .,;C@f:,..                             \n                              ....,18@t,.                   ..,,;C@f:,,...                          \n                  .............,,;t881.                       .,,;C@f:,,................            \n      .............,,,::;ii1tffLCG881..                        ,,:iG@CCLftt1ii;::,,,,...............\n   ..,,,::;ii1ttfLCCGGGGGGGGCCCCL1;:.           .   .            .,;11tfLLCCGGGGGGGCLLftt1ii;::,,,,.\ni1fLCGGGGGGGGGGCLLft11i;;::,,,,.               ..:t;,.           ........,,,,::;ii1ttfLLCGGGGGGGCCLL\nCCLLftt1ii;::,,,..............       ..       .,;LfL;,.                 ...............,,,,::;ii1tt1\nfi::,,,..........                             .:fi,iL;..                             .............  \n08C1:,,,..                                   .:fi,,,;L;.              .                           ..\n;t08C1:,,.                              ....,;fi,. ..;L;,...        .                     .     .,1G\n,,;tG8C1:,,..                    .,:;;iii1111ti,.   ..;t1111iii;;:,.           .             ..:t00f\n..,,:tG8C1:,,.                   .:ff1;;::,,..          .,,::;;1ff;.                      ...:f00t:.\n  ..,,:tG8G1:,,.    .             .,;1i:...               ...:i1;,.                    .  .:f00t,.. \n     .,,:1G8G1:,,.                  .,;11;, .             ,:11;,..                     ..:f00t,.    \n       .,,:1G8Gt:,,.                  .,;1t,.           .,11;,....                   ..;L8G1,.      \n         .,,:1C8Gt:,,..  .             ..:L;.           .:L;..                     ..;L8G1,.        \n           .,,:1C80t;,,..   ,.          .if,..         ...f1..                  ...;L8G1,.          \n             .,,:iC80f;,,.  ..         .,tt,. .,:i1i:,. ..1f,..                ..;C8G1,.            \n              ..,,:iL80Li,,,           .,L;,:i1t1i;i1t1i:,;L:..               .1C8G1,..             \n                 .,,:iL80i:,.          .iCtt1i:,.   ..:i1ttC1..              ,L@G1,..            ;. \n                  ..,,;08;:,           .:1;,.           .,:1;.             .:1G@1,,.             .  \n                   ..,1@C:,.             .                 .              .:::i@L:,,                \n                   .,:L@t:,.                                             ....,;00;,,.               \n                   .,;08i,..                                                .,:L@1:,.               \n                  .,:1@C:,.                                                 .,:1@L:,.        .      \n                  .,:f@t,.                                                  .,,;00;,.               \n                  .,;G0:. ..                                                 .,:L@;..               \n```";
            n[ 30 ]  = "```\n                                  ..;081:.                    .,f@f:,,.                           . \n       .                        ...i0@f:.                     .,;G@f:,,.                            \n                        . .......,18@f,.                    . .,,;G@f:,,.......     .               \n            ...............,,,:;if8@t,.                        .,:;G@fi;:,,,,...............        \n     .........,,,:;;i11tfLLCCGG0000t,.                         ,.,:iG0GGGGCCLLft11i;;::,,,,.........\n ..:;;ii1tffLCCGG0000GGGCLLfttt1;,.             ......           ..,::;ii1ttfLLCGGGGGGGGCCLfft11i;;:\nLCGG000GGGCCLftt1i;;::,,,......                .,1C1,,             ...........,,,,::;ii1tffLCCGGGGGG\ntt1ii;:::,,,.............. .         ..       .,1L1Lt,.                     ................,,,,:::,\n,.,,,,......                       .         .,iL;,:ft,.                                  .......   \nGt;:,,..                                    .,1L;,.,:f1,.             .                             \nC88L;:,,.                             ....,,:1L;..  .:ft:,,.....     .                    .       .:\n:iL88Li,,,..                    .,;ii111111111:,.   ..:111111111ii;:.          .               ..iC0\n,,:iL88Li:,,.                   .,1Lti;::,,.             .,,::;itLt:.                      . .,iG8Ci\n .,,,;L08Li:,,.                   .:i1;,...               ...,;11:.                        .,1G8Ci..\n   ..,,;f08Ci:,,.   .              ..:1ti:, .             .:;t1:,..                      .,1G8C;..  \n     ..,,;f08C1:,,.                   .:iti..            .it1:....                   ...,1G8L;..    \n       ..,,;f08C1:,,.   .              ..1L,.           .,ft,..                      .,108L;..      \n         ..,,;t08C1:,,..    ,          .,f1..          ...iL,.                    ..,t08L;...       \n           ..,,;t08G1:,,.   .          .:Li,.  .,:i:,.  .,:C;..                 ..:t08f:..          \n             ..,,:tG8Gt;,,..          ..iL:,,;itt111tti;,,,f1..                .;f08f:..            \n               ..,,:tG8Gi:,.          .,fL1ttti:,. .,:i1tt1fL,.               .t80f;....         .: \n                 ..,,;G@1:,.          .,ff1;,.         .,:itf,.              ,10@t:,..           .. \n                  ..,i80;,.             ..                 .,              .,;;t@f:,,               \n                  .,:t@L:,.              .                  .             ...,,i80;,,.              \n              .   .,;C@1:,.                                                  ,,:C@1:,.              \n                 .,:i80;,.                                                   .,:t@f:,.        .     \n                 .,:t@L:.                                                    .,:;8G;,.              \n                 .,:C8;.  .                                                  .,,:C8;.               \n```";
            n[ 31 ]  = "```\n     .                           ..:G@f:.                      .,L@L;,,.                          . \n                 .            ....;G@L:.                       .,;G@f;,,.                           \n                   .............,i0@L,.                     .  .,,iG@f:,,.............              \n       ...............,,,::;;i1tL8@f,.                         ..,:i0@Lt1ii;:::,,,................  \n    ....,,,::;;i11tfLLCGGG000000GL1,.                          ,..,:iLCGGGG00GGGCCLftt1ii;::,,,,....\n,:i1tffLCGGG00000GGCLLftt1ii;;;,..              .,i:,.            ..,,,,::;;ii1ttfLCCGGG000GGGCLLftt\nGGGGGCCLftt1ii;::,,,..........                 ,:fCL;,.             ...............,,,:::;ii1tffLLCL\n;::,,,,.............                 ..       .:ff;tL:,.                        ....................\n........                           .         .:ff:,,1L:.          .                          . .    \n:,,,,.                                     ..,ff,,..,1L:.                                           \n8Gt;,,..   .                        ....,,::;ff,.   .,1L;::,,.....   ..                   .         \ntG@0t;,,..                     .,;1i11111t111i,,.    ..i11111111111i:.         .                 .,1\n,:1G80f;,,..                    .iLfi::,,,.               .,,,::ifL1,.                        ..:f00\n.,,:1C80f;,,..                   .,it1:..,.              . ...:iti:.                        ..:L80t:\n ..,,:1C80f;,,,.    .             ..,it1:,.               .,:1ti:..                       ..;L80t:..\n    .,,:1C80f;:,,.                  ..,;tt:.             .,tti:...                    . ..;L80t,..  \n      .,,:iC88Li:,..    .             ..,Lt,             .iC:..                       ..;L80t,.     \n        .,,:iL88Li:,,.     ..          .:C;,.       .  ...:Li,.                     ..;C8G1,.       \n          .,,:iL88Li:,,.   ..         .,1L,,.   .,:,.   ..,ft,.                   .,iC8G1,.         \n            .,,:iL88L1:,...           .,ft,..,;1ttttt1;,...1L,..                .:iC8G1,..          \n              .,,:iL08Ci:,.           .:C1;ittti;,.,:ittt1;;C;,.              ..;G8G1,...         :,\n                .,,:;G@t:,.           .iGft1;,.       .,:itfGt,.              .i0@f:,..           ,.\n                 ..,;08i:,.           .,;,.               .,;:.              ,;if@f:,,.             \n                 .,:1@G;,.              .  .                .              .,,,,i@G;,,.             \n              .  .,:L@f:,.                                                 . .,:;G@i:,.             \n                 .,;08i:,                                                     .,:f@f:,.       .     \n                .,:1@G:.                                                      .,:i8G;,.             \n                .,:f@1.                                                       .,,;G8;.              \n```";
            n[ 32 ]  = "```\n     .                         ..:L@C;,                          .,C@L;,,.                          \n                 .      ........:C@0;. .                         ,:i0@f:,,........                  \n         ...............,,,,::;1G@G:.                            .,:10@fi;::,,,,................    \n    .......,,,::;;ii1tfLLCGG00888C;..                          .. .,:108000GGCCLftt1i;;::,,,,.......\n..:;ii1tffLCCG0000000GGCCLffft1:,.              .,;,,.         ..  .,,;;;i1ttfLCCGG000000GGCCLftt1ii\nCG000000GGCLfft1ii;::,,,,,,,..                 ,:fGC;,.             ......,,..,,,,:::;ii1tffLCCGG00G\nt1ii;:::,,,................                   .:fLifC:,.                    ................,,,,::,,\n...........                          .       .,fL;::tL:,.                                  .......  \n                                   .     . ..,fL:,.,,tL:,.          .                               \n                                          ..,fL:,  ..,tL:..                                         \n.....                           ..,,:::;;ii1fL:,.    .,tL1ii;;::,,,.. ...                 .         \nGt;,,..                      .,iftttttt111i;:,..      .,:;ii111tttttft:.       .                    \nG@8Li:,,.                     .:tL1;,,,...                  ...,,:1ffi.                           ..\n:1C88Li:,,..                   .,:1ti:,,..               .  ..,,itt;,.                         ..:f0\n,,:iC88Li:,,...                 ..,:1t1:,.                 .,,itt;,..                        ..;L88f\n .,,:iL88C1:,,..                   ..:1tt:..             ..,1tt;,..                        ..;C80t:.\n   .,,:iL88C1:,,..                   .,:Lf,.              .1C;,..                        ..iC80t:.  \n     .,,:iL88C1:,,..       ..         .:L1,.            ..,;C;,.                      ..,iC@0t,.    \n       .,,:iL8@G1:,,..     ..         .iG;..              .,Lt,.                     .,iG@01,.      \n         .,,:if0@G1:,,.              .,tL,,.. .,:i11;,.  ..,tC,.                   .,1G@G1,.        \n           .,,:;f0@Gf::,.            .,Ct,,,;itff1i1tft1;,,,;G;..                 ,tG@G1,..        .\n             .,,,;f0@L;:.           .,;G11tff1i:... .,;1tft1iCt,.               .,C@Gt:...         .\n               .,,:L@f:,.           .,1Gfti:..          .,;1fCL,.              .:tG@f:,,.           \n                .,i081:,..            ,;..                  .::.              .:;:1@C;:,.           \n              ..,:t@G;,.               .                     ..              ...,:i08i:,.           \n               .,:L@f:,.                                                       .,,:L@t:,,     .     \n              .,:i081,.                                                         .,:t@C;,.           \n              .,:1@C,.                                                          .,:;00:..           \n```";
            n[ 33 ]  = "```\n     .                       ...,L@0i,.                           .:C@L;:,..                        \n                ..............,:L@0i.                            .,:i0@f:,,.............            \n      .............,,,,::;;i1tf0@0;.                             .,,:18@Cft1i;;::,,,,,..............\n   ..,,,,::;;i11tfLLCGG00000880CL;..            ......         .. ..,:1CGG000000GGGCLfft11i;;::,,,,,\n:itfLLCGG00000000GGCLfft11iii:...              .,iLt:,.        ..   .,,,,:::;ii1ttfLCCGG000000GGCCLL\n00GGCCLLft11i;;::,,,........                  .,iCLGt,,               .....,......,,,,,::;;ii1ttfLLL\n;::,,,..............                         .,;Ct:iCt,.                         ...................\n....... .                            .      .,;Ct:,,;Ct,.                                    ..  .  \n                                   .     ...,;Ct:. .,;Ct,.. .                                       \n                                        ...,;C1,.   .,;Ct,..          .                             \n                             ...,,::;;ii111tL1,.     .,;Lft11iii;:::,,....                          \n:,,,..                      .,1Lftttt11ii;;:,...       .,,:;ii11ttttfLf;.      ..                   \n8Gt;,,..   .                 .:tft;,,....               .     ..,,:1ff;..                           \nf0@0f;,,,.                    .,:1f1:,...                   ...,:ift;,.                         . .;\n,;t0@0f;:,,. ..                 ..:it1:,,                   .,:ift;,..                         .,1G8\n.,,;tG@0f;:,...                   ..:itti...              ..:tft;,..                         .:t0@Ci\n ..,,;tG@0Li:,,.                    ..:1C;..              .,Lf;,..                        ..:t0@Ci,.\n   ..,,;1G@8Li:,,.         .         .,iC:,.              .,ff,,.                       ..:f88C;..  \n     ..,,:1C@8Li:,,.       ..        .,LL,,.        ..    ..iG;,.                     ..:f88L;,.    \n       ..,,:1C@8Li:,,.               .:G1,..   .,:;:,.    .,:G1,.                   ..:f88L;..      \n         ..,,:1C88C1:,,.          . ..1G;,..,:itfftfft1;,...,LL,.                  .iL88L;..        \n           ..,,:1C8@f;:,.           .,fL:;itffti:,..,;1ffti;:1C:,.               ..f@8L;,..         \n             ..,,:L@C;,.            ,;GCfft1;,.  .    ..:itfff01,.               ,t8@f:,,.          \n              ..,;G@t:,.            .,t1;,.                .:if;..             .:;;t@C;:,.          \n             ..,:188i:,.              ..                      .               ..,,:i88i:,.          \n              .,:f@C;,.               .                       .                 .,:;C@t:,,    .     \n              .:;G@t,.                                                           .,:t@C;,.          \n             .,:i80:.                                                            .,:i80:.           \n```";
            n[ 34 ]  = "```\n    .                     .....,f@8i,.                            ..:G@L;:,....                     \n          .. ..............,,,;L@81.                              .,:10@f;:,,,..............        \n     .........,,,:::;ii1tffLCG8@81..                 .            .,,:t8@GGCLftt1i;;:::,,,,,........\n ..,::;;i1ttfLCCG000888000GGGCt1:.             ..,i:,.         .,   .,:1ffLCCGG0000000GGCLLft11ii;::\nfLGG00088000GGCLfft1ii;;::::,.                .,:f0Gi,.        ..    ..,,,,,,,:::;ii1ttfLCCGG0000000\nCLLft11i;;::,,,............                   ,:fCifC;,.               ................,,,,,::;;iii;\n,,...............                            ,:fC;::tC;,.                             ...........   \n.                                    .      ,:fC;,.,,tC;,.                                   .      \n                              .    .     ...,fC;,.  .,tC;,.                                         \n                              .   . ......,:fC;,.    .,tC;,,...       ..  ..                        \n                            ..,:;;;ii11ttttfL;,.      ,,tftttt11ii;;;:,,,,.                .        \n. ...                      .,tCLftt11ii;;:,... .       ...,::;;ii1tttfCL;.      .                   \nt;:,,..                     .:1ft;,,....                .      ...,:1ft;..                          \n88C1:,,..                    .,:ift;,,...                    ...,:1ft;,.                        .  .\niL8@G1;,,..  .                 ..,ift;,,.                    ,,:1ft;,..                          .:t\n,:iL8@Gt;,,...                   ..,ift1,..                ..;tf1;,..                         .,iC88\n.,,:iL8@Gt;,,..                    ..,iCt,.                .;Ct:,..                         .,iC@0t:\n  .,,:if0@Gt;:,..          .         ,:Lf,.               .,;Gi,.                         .,iG@0t:..\n    .,,:;f0@0f;,,,.        ,         ,iGi,..        ..    ..,Cf,.                       .,1G@0t:..  \n      .,,:;f0@0f;:,,.               .,tC:,.     ..,,.      .,tC:,.                   ..,1G@0t,..    \n        ..,:;f0@0fi:,,..          . .:Cf,,....:itfffti:,....,iGi,.                  .:tG@01,..      \n          ..,:;t0@8f;:,.         .  .;Gi,,:;1fff1;::itffti:,,:Cf,,.               ..i0@0t:...       \n            ..,,;L@Gi:,.           .,tG1tfLf1;,.     .,:1tfftifC:,.               .18@L;,,.         \n             ..,;C@L:,. .          .,LGfti:.             .,;1fCG;,               ,i1f@C;:,.         \n             .,:i8@1:,.             .:;..                    .,;.              .,,,:188i:,.         \n             .,:t@0i:.               ..                        .               . .,:;G@t:,.    .    \n             .,;C@L:.                                                            .,,:f@L;,.         \n            .,:i08;.                                                              .,:180:.          \n```";
            n[ 35 ]  = "```\n    .                .........,t881,.                               .:G@L;,,.........               \n      .. .............,,,,::;iL@@1.                                .,:18@Li;::,,,,,...............  \n    ....,,,,::;;ii1tfLLCGG008888t,.             .......            .,,:f888000GGCLfft1ii;;::,,,,,,..\n.,;i1tffLCGG008888800GGCLLffti:,.              .,iCf:,.         , .  .,:;ii1ttfLCCGG00088000GCCLfft1\nG088000GGCLLft11i;;::,,,,,,.                  .,iGLGL:,.        .     .......,,,,,,:::;ii1tffLCCGG0G\n1i1i;::,,,,..............                    .,iGt;iCf:,.              .  .. ..............,,,,,,,..\n...........   .                      .      .,iGt::,;Cf:,.                                 .....   .\n                                     .     .,iGt:,..,;Cf:,                                          \n                              .   ..   ....,iGt:.   .,;Cf,..                                        \n                                .......,,::1Gt:.   . .,;Cf;:,,....... ...  .                        \n                          ..,:;ii11ttttfffff1,..      .,;ttffftttt11ii;;:,,.               .        \n                          .:fGLtt11i;;::,,.... .        ....,,::;ii1ttfGCi,.    .                   \n.....                      .,iffi,,.....                ..      ...,:1ft;..                         \nGfi:,,..                    .,:iffi,,...                      ...,;tLt;,.                       .   \nG@8Li:,,..   .                ..,iffi,,,.                    .,,;tf1:,..                           .\n;1G@8L1:,,.. .                  ..,iff1;..                   ,itf1:,.                           .,1G\n,,;1G@8C1:,,..                    ..,;fL:..                .,tC1:,..                         ..:f8@G\n..,,:1C@@C1:,,..           .        .,1G;,.                .,fC:,.                         ..;f8@Ci,\n  ..,,:1C@@C1:,,..        .,        .:LL,,.                .,iGi,.                       ..;f8@Ci,. \n    ..,,:1C8@G1:,,..                .;Gt,.          .       ,:Gt,.                     ..;L8@Li..   \n      ..,,:iC8@Gt;,,...           ..,1G;,..   .,;1t1i:..   .,,fC:..                  .,;L8@L;,.  .  \n        ..,,:iL8@0t;:,.          . .,LC:,..,;1fLLt11fLft;:,..,10;,..               ..;C88Li,..      \n          ..,,:iL@0i:,.            ,:Gt:;1tLLti:.....,;1fLf1i:;Gt,..               .;0@Gi:,..       \n            ..,:L@C;,.            .,10LLLf1;,.          .:itfLfGC:.               .;tC@C;:,.        \n             ,:iG@f:,.            ..iL1;,.                  .:iff,.             .,:::1@0i:,.        \n            .,:1881:,                ,                         ..               ...,:i0@t:,,   .    \n            .,;L@G;,                 .                         ..                 .,,:L@L;,.        \n           .,:;G@1.                                                               ..,:1@G:.         \n```";
            n[ 36 ]  = "```\n    .          ............,,,18@t,.                                 .;0@L;,,,,............         \n     ...........,,,,,::;;i11tC@@f..                 ..              .,:t8@Cft1i;;:::,,,,,,,.........\n   .,,,::;;i11tfLCCG0008888880Ct,.             .,:ti,,.             .,,:tGG00888800GGCLLft11i;;:::,,\ni1fLCGG008888800GGCLLft11iii:...              .,:L00t:,.        ,     .,,::::;ii1ttfLCCG000888800GGC\n00GGCLLft11i;;::,,,,....,,.                   ,:LCit01,,.       .     ............,,,,,,::;;ii1ttfff\n::;,,,..............   .                    .,:LC;::1G1,.              .   .      .............,... \n.......   .                          .     .,:LC;:,,:1G1,.                                         .\n                                     .    .,:LC;,.  .,1G1,.                                       . \n                                ....    ..,:LC;,.    .,1G1,..     . .                               \n                            . .....,,,::;;iLC;,.      .,1Gti;::,,,........  ..                      \n                         .,;ii1tttffffffftt1;,..      ..,i1ttffffffttt11i;::.              .        \n                         .:L0Lt1ii;::,,...  .  .        ..  ..,,,:;;i11fGG1,.   .                   \n                          .,iffi:,,....                 .        ...,;tLt;,.                        \n;,,,..                     .,:iff1:,....                      ....,;tLt;,.                          \n@Gt;:,,.     .               ..,iff1:,,.                      .,,;tLt:,..                           \nL8@0fi:,,.   .                 ..,;tf1;,.                   . :itL1:,.                            .:\n:if0@0fi:,,..                    ..,;tCi,.                  .,fL1:,..                          .,iC8\n,,:;f0@8Li:,,.             .       .,:Cf:.                  .;Gt,,.                         ..,1G@8f\n .,,:;f0@8Li:,,.          .,       .,i0i,.                 ..,LC:,.                        .,1G@0t:.\n   ..,:;tG@8Li:,,.                 .,fG:,.          ..      .,10;,.                    ...,1G@0t:.. \n     ..,:;tG@8L1:,,...            ..:Gf,,.     .,:i;,.      .,;Gt,.                    .:t0@0t:...  \n       .,,,;tG@8C1::,.           ..,i01,,...,:itLLfLLf1;,....,:LC:,.                  ,f0@0t:...    \n         .,,,;tG@01:,.            .,fG;,,:itfLft;:,,:ifLLf1;,,,t0;,.                .:G@0t;,..      \n           .,,:t@0i:,.            ,:CL1tfLLti:.       .,:1fLLf11Gt,.               .;f0@L;:,.       \n            .:;C@L;:.            .,i0GLfi:,.              .,;1fL0L:.              .:;:t@0i:,.       \n           .,:i8@t:,.             .,i;,.                      .,i;..             ...,:i0@t:,.  .    \n           .,:t@0i,.                ..                          ..                 .,:;C@L;,.  .    \n           .,;L@f..                                             .                  .,,:t@G,.        \n```";
            n[ 37 ]  = "```\n    . .    ..............,,::10@f,.            .                      .;0@L;:,,,,,,.............    \n     .....,,,,,:::;ii1ttfLCC0@@C,..             .,:,,..              .,:f@@0GCLftt1ii;;::,,,,,,,....\n..,:;;i1ttfLCCG008888888000GLt1,               ,:t0Gi:,.             .,,:1fLCCG0088888800GCCLftt1ii;\nLG0888888000GCCLft11i;;::::.  .               ,:t0fCG;,.        ,  .  ...,,,,,,::;;ii1ttfLCCG0008880\nLLft11i;;:::,,,,..........                   .,10t;;LG;,.       .        .............,,,,,,,::;;;;:\n,,,.............                            .:1Gt:,,:LC;,.             .   .           ..........   \n.                                    .     .,1Gt:,..,:LC;,.                                        .\n                                     .   ..,10t:,   .,:LC;,.                                        \n                             .......  ...,,10t:.     .,:LC;,..    . ..    .                         \n                           ....,,,::;;ii11fGt:,       .,:LCt11i;;::,,,,....  ..                     \n                        .:11tfffffffffttt1i;,.         ..:;i1tttfffffffftt1i;,             .        \n                        .;C0L1ii;:,,....   .   .        .     ....,::;iitC0f:.  .                   \n                        ..:iff1:,,..  .                  .        ..,,;tLt;,.                       \n. ...                     .,:ifL1:,,...                          ..,;fLt;,,.                     .  \nt;:,,..                     ..,ifL1:,,..                       ,,,;tLt;,..                          \n@@Gt;,,..   ..                ..,;fL1;:..                     .:ifLt:,..                            \n1C8@Gt;:,,.                     ..,;fCt,..                   .:LLt:,..                           .,1\n,:1C8@Gt;:,,.             .       .,,fG;,                    ,t0i,,.                          ..;f8@\n.,,:1L8@0t;:,,.           ,.       ,:CL:,.                  .,;Gt,.                         ..;L8@Ci\n ..,,:iL8@0f;:,,.                 .,i0t,..          .. .     .:CC:,                       ..;L8@Ci,.\n   ..,,:iL8@0fi:,,.   .           .,t0;,.       ..,,.        .,t0;,.                    .,;L@@Ci,.  \n      .,,:iL8@0Li,,,.            ..:CC:,.. ...,;1fLfti:,... ..,i0t,..                  .1C@@Li,.    \n       ..,,:iL0@01;,.            .,;0t:,..,;1fLLf1;itLLLti:,..,:CC:,.                .,L@8Ci,..     \n          .,,:t@81:,.            .,t0i:;1fLLf1;,..  ..:itLLLti::t0;,.               .:f8@C;:,.      \n           .,;L@G;:,.            ,:CGfLCLt;,.            .:itLLLL8t:.              .:iif@0i:,.      \n           .:iG@L;,.             .:LCti:.                   .,:1fGt,.             .,,,:18@1:,...    \n          .,:1881,.                ,,                           .:.                 .,:;C@L;,. .    \n          .,;f@C,.                 ..                            .                  .,,:f@C,.       \n```";
            n[ 38 ]  = "```" +
                       "    . ..............,,,,::;it0@L,.             ........               ..i8@Li;;::,,,,,,,,,..........\n" +
                       "    ..,,,,::;;ii1ttfLCCG00888@G:..             .,;Lf;,..              .,;f888800GGCLfft11i;;:::,,,,,\n" +
                       ",:itffLCGG00888888800GCCLLft;:.               .,iGG0L;,.              .,,:ii1ttfLCCG0088888800GGCLff\n" +
                       "0888800GCCLftt1i;;:::,,,,,.                  .,;GCi1GL:,.       ,.     ...,..,,,,,,,::;;ii1tffLCCGGG\n" +
                       "1i;;:::,,,,,..............                  .,;GC;::iGL:,.                 ...............,,,,,,,,..\n" +
                       ".,..........                               .,;CC;:..:iGL:,.             .  ..            . .....    \n" +
                       "                                     .    .,;CC;,.  .:iGL:,.                                        \n" +
                       "                                    ... ..,;GC;,.    .,iGL:,...                                     \n" +
                       "                         .   ...........,,;GC;,.      .,iGL:,......  ........                       \n" +
                       "                         .,,,,::;;ii11ttffCL;,.        .,iGLfft11ii;;:::,,,,. ..                    \n" +
                       "                      .,1fffLLLLfffft11ii;:,.           ..,:;ii1ttfffLLLLLffti,.                    \n" +
                       "                      .,iCGL1;;:,,....     .   .        . .      ...,,:;;1C0L;. ..                  \n" +
                       "                       ..:ifL1:,,,.                                .,,,;fLt;,..                     \n" +
                       "                         .,:ifL1:,,...                            .,,;fLf;,,.                    .  \n" +
                       "......                     ..,ifLt:,,,..                       .,.,;fLt;,..                         \n" +
                       "Gfi:,,..    ..               ..,;fLt;:. .                      .:ifLt;,..                           \n" +
                       "0@8C1:,,..                     ..,;fLf:..                    ..;LLt;,..                            .\n" +
                       ";tG@8C1:,,..              .      .,,10t,.                    .:CG;,,.                           .,iL\n" +
                       ",:;tG@@C1;,,..            ,.      .:t0;:..                   .,t0i,.                          .:tG@8\n" +
                       "..,:;tG@@C1;,,,.                  .:CG:,.                    .,;0t,.                       ..:t0@0t:\n" +
                       "  ..,,;tG@@Gt;:,..                ,;0f,.        .   ..       .,:CC:,.                    ..:t0@0t:..\n" +
                       "    ..,,;1C@@Gf;,,,.             .,t0i,..     .,:it1;,..  .   .,f0i,.                   .;f0@0t:..  \n" +
                       "      ..,,;1C8@G1;,.             .:CG;,....,:itLCLffLCf1;,....,,i0t,..                ..t8@0t:..    \n" +
                       "        ..,,:t8@f;,.            .,;0f:,,:itLCLfi:,..,;1fLLf1;,,,;CC:,.                ,t8@Ci:,.     \n" +
                       "         ..,:f@0i:,.            .,101itLCLf1;,.       ..,;1LCLf1if0;,.              .,i1L@Gi:,.     \n" +
                       "          .,;C@C;,.             .:C8CCL1;,.                .:itLCC8t:.             .,,,:1@81:,..    \n" +
                       "         .,:18@f,.              ..it;,.                       ..:if;..             . .,:iG@f;,. .   \n" +
                       "         .,:t@0:.                 .,                              ,                  .,:;L@C,.      \n" +
                       "```";
            n[ 39 ]  = "```" +
                       "     .........,,,,,::;;i1tfL0@G:..             .,:11:,.                .,i8@Cft1ii;;::,,,,,,,,......\n" +
                       "  ..,::;;i11tffLCGG008888888GC;..             .,;L80f:,.               .,;f0008888800GGCLLft11i;;:::\n" +
                       "1fCGG00888888800GGCLftt11i;,..               .,:L0tf0t:,.              .,,:::;;ii1tffLCGG00888888000\n" +
                       "00GCCLfft1ii;;::,,,,...,..                  .,:f01;;t0t:,.      ,.      .........,,,,,,,:::;;ii1tttt\n" +
                       "::,,,,,.............                       .,:f01:,,:t0t:,.                 .    .................  \n" +
                       ".,....                                    .,:f01:,..,:t0t:,.               ..                       \n" +
                       "                                     .   .,:f01:.    ,:t0t:,.                                       \n" +
                       "                                    .....,:f01:.      ,:t0t,,.....                                  \n" +
                       "                        .   ..........,,:;L01:.       .,:t0t;:,,..............   .                  \n" +
                       "                      ..,,::;;ii11ttfffLLLC1,,.        .,:tCLLLffftt11ii;:::,,....                  \n" +
                       "                    ..;fCLLLLLfftt11i;;::,...            ...,::;ii11ttffLLLLLCf;.           .       \n" +
                       "                     .,1CGfi::,,...            .        .          ...,,::iLGCi,..                  \n" +
                       "                      ..:iLLt;,,,.                                  .,,,;tCfi:.                     \n" +
                       "                        .,:ifLt:,,.                                .,,;fCfi,,.                   .  \n" +
                       "                          .,,ifLt:,,,. .                        ..,,;fCf;,..                        \n" +
                       ";,,,,.      .               ..,ifLt;:, .                        ,:ifCf;,..                          \n" +
                       "@0fi:,,.   .                  ..,;fLf;..                      ..iLLt;,..                            \n" +
                       "L8@8Li:,,..               .     .,,iGC:,                      .;GC;,,.                            .,\n" +
                       ":iL8@8Li:,,..             ,      .,;Gf:,                      ,:CC:,.                          ..;f0\n" +
                       ",,:if0@8Li:,,..                  .,18i,..                     .,t0i,.                        .,iC@@C\n" +
                       "..,,:if0@8L1:,,..                .:LG;,.        .   ..        .,i0t:..                     .,iC@@Ci,\n" +
                       "   .,,:if0@8C1;,,..             .,;0L:,.       ..,::,....     .,:GC:,.                   .:1G@@Ci,. \n" +
                       "     .,,:if0@@Ci;:.             .,181,,.. ...,;1fLCCLti:,......,:f0i,.                 ..i0@8Li,..  \n" +
                       "      ..,,:;f8@L;,.             .:LG;,,..,:1fCCLti:;ifLCLti:,..,:i0t,..                .18@G1:,.    \n" +
                       "        ..,:t88t:,.            .,;0L:::itLCLfi:.......,:1fLCLti::;GC:,.               ,itC@Gi:,.    \n" +
                       "         .,;L@0i:.             .:18ffLCCf1:,.            .,;1fCCLtL0i,.             .,:::t@81:,..   \n" +
                       "        .,:iG@L:.              .:f8Cf1;,.                    .,;tLC8t,.             ...,:i0@f:,..   \n" +
                       "        .,:188i..               .,i,..                          ..:i,.                .,:;C@L,.     \n" +
                       "```";
            n[ 40 ]  = "```" +
                       "    .....,,,,:::;;i1ttfLCGG8@0;..             .,:t801:,.                .,t@@0GCLfft1ii;;:::,,,,,,,,\n" +
                       "..,;i11tffLCGG008888888000GLt;.               ,:t0LC01:,.               .,;tLCCG00888888000GCCLft11i\n" +
                       "G0888888800GGCLfft1ii;;;:,...                ,:10LiiL01:,.              .,,,,,,:::;;i11tffLCGG008888\n" +
                       "Lftt11i;;:::,,,,.........                   ,:10f;::;L0i:,.     ,.         .........,,,,,,,,,:::;;::\n" +
                       ",,..............                           ,:10f;,..,;L0i:,.            .   .          ..........   \n" +
                       ",.                                        .:10L;,.  .,;L0i:,               ..                       \n" +
                       "                                     .  ..:10L;,.    .,;L0i:..                                      \n" +
                       "                            ............,:10L;,.      .,:L0i,,.........                             \n" +
                       "                      ... ........,,,:;;it0f:,.        .,;L0ti;::,,,............  ..                \n" +
                       "                    ..,:;ii11ttfffLLLLLLLft:,.          .,;tfLLLLLLLfftt11ii;;:,,,.                 \n" +
                       "                   .,1GGCLLLftt1ii;;:,,,....              .. .,,::;;ii1ttfLLLCGC1,.         .       \n" +
                       "                    .:1CCt;:,,....       .     .                      ..,,:;fCC1,,                  \n" +
                       "                     .,:1LCt;,,,.                                    ..,,;tCLi:,                    \n" +
                       "                      ..,:iLCt:,,..                                 .,,;tCLi:,..                 .  \n" +
                       "                         .,:iLCt:,,..  .                         ..,,;fCfi,,.                       \n" +
                       ". ...       .             ..,,ifCt;:,                            ::;fCfi,,.                         \n" +
                       "fi:,,,.    .                 ..,ifCfi...                       ..1fCf;,,.                           \n" +
                       "@@Gt;:,,.                 .    .,,iCG;,.                       ,i0Li,,.                             \n" +
                       "1C@@0fi:,,.              .,     .,:LG;:.                      .,i0f:,.                           .,i\n" +
                       ",;1C@@0fi:,,..                  .,;0L,,.                      .,:CG;,.                        ..:t0@\n" +
                       ".,::1C8@0fi:,,.                 .,18t,.               .        .,f8i,.                      ..:f8@0t\n" +
                       " ..,,:1C8@0fi:,...              .:L0;,.         ........       .,i8f:,.                   .,;f8@0t:.\n" +
                       "   ..,,:1L8@8Ci::,             .,;0L:,.   .....:;1Lf1;,.... .  .,:GG;,.                 ..:C8@0t:...\n" +
                       "     ..,,:iL8@Ci:,.            .,18t:,.....,;tLCCLttLCCf1;,.....,:f8i,.                 .;0@0f;,..  \n" +
                       "       .,,:10@f;,..            .:L0i:,,,;1fCCLti:,..,:itLCLfi:,.,:i8f,,.               .;f0@Ci:,.   \n" +
                       "        .,:f@81:,             .,;0L;;1fCCCfi:,.     .  .,:ifCCLfi;;CG:,..             ,:;;f@81:,.   \n" +
                       "        .:;C@G;,              .:t8CCCCf1:,.                .,;1fCCCG81:.             ...,:18@t:,.   \n" +
                       "     . .,:10@t..              .,1GL1;,.                        .,;tLGi,.               .,:;C@L,.    \n" +
                       "```";
            n[ 41 ]  = "```" +
                       " ..,:;;ii1ttfLCGG0888@@@880G1,.              .,;L0ii0L;,.                 .:f0088@@88800GCLLft11i;;:\n" +
                       "tLCG00888@888800GCCLft11i:,.                .,;L8t::10L;,.                .,:;;ii1ttfLLCGG0088888880\n" +
                       "00GCCLftt1ii;;::,,,,,...                   .,;L8t:,,:10C;,.      .        ......,,,,,,,,:::;;ii1ttt1\n" +
                       "::,,,,,............                       .,;L8t:,...:10C;,.    .,                ............,,..  \n" +
                       ",...... .                                .,;L8t:,.   ,:18L;,.               .                       \n" +
                       ".                                       .,;L8t:,      ,:18L;,.          .   .                       \n" +
                       "                                 . .....,;L8t:,.       ,:18L;,....                                  \n" +
                       "                        .............,,:;L8t:,          ,:10L;,,...................                .\n" +
                       "                   ....,,,,::;;;i11ttffLC0t:,.          .,:10CLfftt1ii;;::,,,,...,. ...             \n" +
                       "                 ,;i1tffLLLCCCCLLLfft11i;:,.              .,:;i11tffLLLLCCCLLLfft1i;:.              \n" +
                       "                .,1G8Gftt1i;;:,,,....    .                  .   ......,,::;i1ttfG80t,.      .       \n" +
                       "                 .,;tCC1:,,,...                .                        ..,,,:1CCf;,.               \n" +
                       "                   .:;tCC1:,,..                                     .   .,,:tCCt;:,                 \n" +
                       "                   ..,,:tCC1:,,.                                      .,,:1CCt;,,.                . \n" +
                       "                      ..,:1CCt:,,...                               ...,:tCCt:,..                    \n" +
                       "           ..            .,:1LCt;,:.                              .:,:tCC1:,..                      \n" +
                       ".  ..                     ..,:1LCti,...                           ,itCC1:,..                        \n" +
                       "i:,,,..                      .,:1L0t,,.                         .,t0C1:,,.                          \n" +
                       "@8L1:,,..                ,.   .,:i0C;,..                        .;C0i:,.                            \n" +
                       "L8@8C1;,,..                   ..:t81:,..                        .,18t:,.                          .,\n" +
                       ":iL8@8C1;:,,.                  .:L8i,.                          .,;0L:,.                       ..:f0\n" +
                       ",,:iL0@@C1;:,..  .            .,;0C:,.              ...          ,:C0i,.                     .,iL8@0\n" +
                       "..,,:if0@@Gt;,,,.             .,t8t:,.    ..   .........    .    .,t8t,.                    ,1C@@Gt:\n" +
                       "  ..,,:if0@@Ci:,.             .:L8i,.      ...,:ifCLti:,.....    .,i0C:,.                 .,L@@Gt:,.\n" +
                       "    ..,,:iG@01:,.            .,;0C;,..  ..,:ifCGGLttLGGCti:,......,:C0i,.                .:L@@Ci:,. \n" +
                       "      .,:10@C;,.             .,18t:,..,:itLGGLti:,...,;tLGGLt;:,.,,:t8t:,.              .:i1C@Gi:,. \n" +
                       "      .,;f@@t,.              .:L0i::;1LCGLti:...     ....:itLCCL1;::i0L:,.             .,,::f@8t:,  \n" +
                       "    ...:;C@G:.               ,:GCitCGCf1:,..              ..,:ifCGCtiCG;,              . .,:10@t,.  \n" +
                       "```";
            n[ 42 ]  = "```" +
                       ".,:i1ttfLCGG0888@@@@800GCLti,.              .,:t8t::10f:,.                 .,1fLCG0088@@@8800GCCLftt\n" +
                       "G088@@88800GGCLfft1i;::,..                 .,:t8L:,,:f8f:,. .               ..,,,:;;i11tffLCCG008888\n" +
                       "Lftt11i;;:::,,,,......                    .,:f8L;,...:f8f;,.     .            .......,,,,,,,::::;;:,\n" +
                       ",,,.............                         .,:f8L;,.   ,:f8f;,.   .,                    ...........   \n" +
                       ".                                       .,:f8L;,.    .,:f8f;,.              .                       \n" +
                       "                                       .,:t8L;,.      .,:f8f;,..            .                       \n" +
                       "                               ......,.,:t8L;,..       .,:f8f:,,.......                             \n" +
                       "                  ..  ............,,,::;f8L;,.          .,:f8Li::,,,.................               \n" +
                       "                  ,,,,:::;;ii1ttffLLLCCCCf;,.            .,:fCCCCLLLfftt1ii;:::,,,,,. ..            \n" +
                       "              ..:1ffLCCCCCCCLLfftt1ii;:,,,.                .,,,:;;i11tffLLCCCCCCCLffti,.            \n" +
                       "              ..:f88Ct1i;::,,,....      ..                  ..       ....,,::;i1tL08L;.     .       \n" +
                       "                .,ifCL1:,,,...                 .                         ..,,,:1LGfi,..             \n" +
                       "                 .,:;fGC1:,,..   .                                   ..  .,,:1CGfi:,.               \n" +
                       "                   .,,;tCC1:,,..                                       ..,:1CGf;:,..              . \n" +
                       "                   . .,,;tCC1:,,...                                 ...,:1CGf;,,.                   \n" +
                       "           .           ..,:tCCt:,,.                               ..,,:1CGt;,,.                     \n" +
                       "                         ..,:1CCti: .                              ,i1CGt;,,.                       \n" +
                       "......                     ..,:1CGt,,.                           .,tGCt;,,.                         \n" +
                       "L1;:,,..                 :   .,,;G8i,. .                       . ,;00i:,...                         \n" +
                       "@@0Li:,,..                    .,i0C:,..                         .,:L81:,.                           \n" +
                       "1C@@8Li:,,..                  .:t@t:,.                           .,18f:,.                        .,;\n" +
                       ":;1C8@8L1:,,..   .            ,:C8i,.                 ..         .,;0G;,.                     ..:tG@\n" +
                       ".,,;1C8@8C1:,,,.             .,i0C;,.     .     ..  ...     .     ,:L8i,.                    .;f0@8L\n" +
                       " ..,::1L8@8Li;,.             .,t8f:,.      . ...,:ii:,.....       .,t8f:,.                 ..f8@8L;,\n" +
                       "   ..,,:1G@8t;,.             ,:C8i,..     ..,;1fCGGGGCf1:,.....   .,;0G;,.                .,f@@G1:,.\n" +
                       "     .,:iG@Gi:.             .,;0C;,.....,:ifCGGLti::;1LGGCfi:,.....,:L8i,.               .,itG@Gi:,.\n" +
                       "     .,:t8@L:..             .,18t:,.,:;tLGGLti:........,;tLGGLti:,.,,18t:,              .,::;f@81:. \n" +
                       "    ..,;f@8;..              .,f0i,,:tCGCfi:,..           .,:ifCGCt;,,;GL:.              ...,:t8@1.. \n" +
                       "```";
            n[ 43 ]  = "```" +
                       ";itLCCG0088@@@@880GCLfti;:..                ,:10L:,,,t8t:,.                   .:;1tfLCG088@@@@8800GG\n" +
                       "8888800GCCLft1ii;:,,..                    .,:10Gi,..,:L8t:,...                   ..,,::;ii11tffLCCCC\n" +
                       "1ii;;:::,,,......                         ,:10Gi,.   ,;L8t:,.    .                .......,,,,,,,,,..\n" +
                       ",.........                               ,:10Gi:.     ,;L8t:,.  .,                         .....    \n" +
                       "                                       .,:10Gi:.      .,;L8t:,.             .                       \n" +
                       "                                     ..,:10Gi:.        .,;L8t:,..           .                       \n" +
                       "                         ............,,:10Gi:.          .,;C8t:,...........                         \n" +
                       "                .............,,,,::;ii1f8Gi:.            .,;L8L1ii;::,,,..............   ..         \n" +
                       "              . ,::;;;ii11tffLLCCCCCCCCLfi,..             .,;tfCCCCCCCCLLfft11ii;;:::,..,.          \n" +
                       "             .:tLCCCGGCCCLLftt1ii;::,,....                  ....,,::;;i11tffLLCCCCCCCCfi..          \n" +
                       "             .,iC8Gfi;;:,,.....         .                      ..       ....,,,:;itG8G1,.           \n" +
                       "              ..:iLGL1::,,..                   .                           ..,,:iLGC1;..            \n" +
                       "                .,:iLGL1:,,..   .                                         .,,:iLGL1:,.              \n" +
                       "                  .,:ifGC1:,,.                                          ..,:iLGLi:,..             . \n" +
                       "                   ..,,;fGC1:,,...                                    ..,:iLGLi:,..                 \n" +
                       "           .          .,,;fGC1:,,,                                ...,,:1LGLi:,.                    \n" +
                       "                       ..,,;tGC1;:.                                 :;1CGfi:,.                      \n" +
                       "                          ..,;tGGf,,.                            ..,1GGfi:,.                        \n" +
                       ":,,,,..                 .:  .,,;C8t:. .                           ,i8Gi:,,.                         \n" +
                       "8Ct;:,,..                    .,;C0i:. .                          .,;G0i:,.                          \n" +
                       "0@@0fi:,,..                  .,i8C;,.                            ..:f@t:,.                         .\n" +
                       ";tG@@0fi:,,..    .           .:t@f:,.                             .,18L:,.                      ..;f\n" +
                       ",:;tG@@0L1:,,..              ,:C81,.      .      .  ...           .,;00;,.                    .:1G@@\n" +
                       ".,,:;tG@@8Li;:.             .,i8C;,.           ...,,,....          .:L8t,,                  ..10@@G1\n" +
                       "  .,,:;tG@@f;,.             .,t@f:,.       ...,;1fCCf1;,..... .    .,18L:,.                .,t@@0t:,\n" +
                       "   ..,,;C@8t:,.             ,:C8i:.    ...,:1fC0GLttLGGGf1;,....   .,;G0;,.                ,iL0@Ci:,\n" +
                       "    .,:10@G;..             .,i0C;,   ..:itCGGCti:,..,,;tLGGCfi:,.  ..:f81:.              .,:;;C@01:.\n" +
                       "    .,;t8@t..              .,18t,. ..;fCGCfi:,...     ..,:itCGGf;,. .,i8f,.              ..,,;f@8i..\n" +
                       "```";
            n[ 44 ]  = "```" +
                       "fCG088@@@@@800GCLt1i:,.                    .:iGG;,...:L81:,.                      .,;i1fLCG0088@@@@8\n" +
                       "00GCCLfft1i;::,...                        .:iG81:.   ,;G81:,..                       ..,,,:;;ii1ttt1\n" +
                       ":::,,,,.....                             .:iG8t:.    .,iG8t:,.   .                     ......,,,..  \n" +
                       "......                                  .:iG8t;,.     .:iG8t:,. ..                                  \n" +
                       "                                       .:iG8t;,        .:iG81:,.        .   .                       \n" +
                       "                                    ..,:iG8t;,          .:iG8t:,...         .                       \n" +
                       "                     ...............,::iG8t:,            .:iG81:,,...............                   \n" +
                       "              .........,,,,,::;;i11tffL08t:,.             .:iG8Cfft11i;;::,,,,........,.  ..        \n" +
                       "            ..,:;ii11tffLLCCCGCCCCCLfft1i:.                .,;i1ffLLCCCCCCCCLLfft11ii;;,,,.         \n" +
                       "           .,1C0GGGCCLLftt1ii;::,,,... ..                    .. ....,,::;;i11tffLCCGGG0Gf:.         \n" +
                       "            .:tG0C1;::,,......                                 ..          ....,,:;iL00f;..  .      \n" +
                       "             .,;1CGLi::,,..                    .                            ..,,:ifGGt;,..          \n" +
                       "              .,,:1CGLi:,,..                                                .,:;fGGt;:,.            \n" +
                       "                ..,:iLGLi:,,..                                           ..,:;fGCt;,,.              \n" +
                       "                  ..,:iLGLi:,,...                                       .,,ifGC1:,..                \n" +
                       "           .         .,:iLGC1:,,,.                                .. .,,:ifGC1:,..                  \n" +
                       "                      ..,:ifGC1;;.                                   :;iLGC1:,..                    \n" +
                       "                         .,,;fGCf,..                               .,1CGC1:,..                      \n" +
                       ".  ..                   ,, .,:iL8f:,.                             .,18G1:,,.                        \n" +
                       "t;:,,,.                     .,:f8t;,                              .:10C;:,.                         \n" +
                       "@@Gt;:,,.                   .,;G8i:..                             .,:C81:,.                       . \n" +
                       "L0@@Gt;:,,.                 .,18G;,.                               .:f@f:,. .                     .,\n" +
                       ":if0@@Gfi:,,..              .:f@f:,.      .           .            .,i8G;,.                    .,;f0\n" +
                       ",,:if0@@0f;::,              ,;G8i,.             ... ....           .,;G8i,.                  ..iG@@0\n" +
                       "..,,:if0@@Li:,.            .,i8C;,.          ...,;11;,......  .     .:f@f:,.                ..18@0f;\n" +
                       "  ..,,:f@@f;,.             .:f@f:,.      ...,;1LG0GGGGL1;,,....     .,i8C;,.                ,iC8@C;:\n" +
                       "   ..,iC@81,.              ,;C8i,.     .,:ifCGGCti::;1LGGCf1;,.    . .;C8i,.              .,:;iG@Gi:\n" +
                       "   .,:10@L,.               ,;0C:.    .,;fG0Gf1:,......,:itCGGLi,.    .,t81,.              .,,:;L@8;.\n" +
                       "```";
            n[ 45 ]  = "```" +
                       "08@@@@880GCLft1;:,.                       .:;C81,.   .:C01:,.                          .:;1tfLCG0888\n" +
                       "LLft11i;::,..                            .:;C@f:,    .,i001:,.                            ..,,::;;::\n" +
                       ",,.......                               .,iC@f;,.     .,1081:,.  ,.                         .....   \n" +
                       "..                                     .:;C@f;,.       .:1001:,. ..                                 \n" +
                       "                                     ..:iC@L;,.         .:1001:,.       .   .                       \n" +
                       "                               ......,,;C@L;,.           ,:1001:,,.......                           \n" +
                       "             .    ...............,,,:;iC@f;,.             ,:1001;:,,,................. ..           \n" +
                       "             ....,,,,,,::;;ii1ttfLLCCGG0f;,.              .,:1G0GCCLLftt1ii;;::,,,,,,,,,,. ...      \n" +
                       "          .,:;1ttffLLCCCGGGGCCCLfft11i;::.                  .,::i11ttfLLCCCGGGCCCLLfftt1i:;:.       \n" +
                       "         ..;C88GCCLftt1ii;::,,,.....   ..                     ..  .....,,,::;;i11tfLCCG080t,..      \n" +
                       "          .,;fGGfi::,,......                                    .       .   .....,,:;tG0L1,. .      \n" +
                       "            .:;tGGfi::,,.                      .                              .,,:;tG0Li:,.         \n" +
                       "             .,:;tGGfi:,,..                                                  .,,;tG0fi:,..          \n" +
                       "               .,,;1CGLi:,,..                                              .,,;tGGfi:,..           .\n" +
                       "                 .,,:1CGLi:,,.                                           .,,;tGGf;:,.               \n" +
                       "           .       ..,:1CGLi:,,,.                                 ..  ..,,;fGGf;,,.                 \n" +
                       "                     ..,:1C0Li::.                                     ::;fGGt;,,.                   \n" +
                       "                        .,:iL0Cf:...                                ..1LGGt;,,.                     \n" +
                       "       .                :,.,:1C8L:,.                               .,100t;:,.                       \n" +
                       ",..,..                     .,:18C;,.                               .:t@L;:,.                        \n" +
                       "Gf1;,,,.                    ,:L@t:,.                               .,;00i:,.                        \n" +
                       "8@8Ct;:,,.                 .,iG8i:.                                 ,:L@t:,.                       .\n" +
                       "1L8@@Gt;:,...              .,18C;,.       .           .             .,t@L;,.                     .,i\n" +
                       ",:1L8@@Gf;::,.             .:L@f:,.                 ...             .,i00i,.                   .;f0@\n" +
                       ".,,:1L0@@Ci:,.            .,;08i:.             ...,,,.....           ,:L@t:,.               ...i8@8C\n" +
                       " .,,:;t8@Ci:.             .,18C;,.         ...,;1fGGL1;:,...  .      .:t@L:,.                .;C@@Li\n" +
                       "  ..,;L@@t:.              .:f@t:.        .,:ifC00CftLG0GL1;,..        ,i0G;,.               ,:i10@Ci\n" +
                       "   .:iG@0:..              .:C0;,       .,;fG0Gf1:,,,,:itC00L1,.       .:L8i,.              .,,:;C@0;\n" +
                       "```";
            n[ 46 ]  = "```" +
                       "@8800GCLf1i;,...                       . .,;f8f:.     .;00i:,.                             .,;i1tfLL\n" +
                       "1i;;:,...                               .,;f@C;,.     .,1801:,.                               ......\n" +
                       "....                                   .,;L@Ci,.       .:t801:,. ,.                         .       \n" +
                       "                                      .,;L@Ci:.         .:t801:,...                                 \n" +
                       "                                    ..,;L@Ci:.          .,:t801:,.      .   ..                      \n" +
                       "                          ..........,:;L@Ci:.            .,:t80i:,,...........                      \n" +
                       "            .. .............,,,,::;;i1L@Ci:.              .,:t80ti;;::,,,,.................         \n" +
                       "           .,,,,::::;;ii1ttfLLCCGGGGGGCLi:.                .,:1CCGGGGGCCLLftt1ii;;::,,,,,,,. ...    \n" +
                       "        .,;1tfLCCCGGGGGGCCLLfft1ii;;:,,,.                    .,,,::;ii1ttfLLCCGGGGGGCCCLff1ii,.     \n" +
                       "        .:t8@0CLft1ii;::,,,........    .                       .    ........,,,::;i11tfLG8@Gi,.     \n" +
                       "         .,iLGGt;::,,,....                                      .             ....,,:;1C0Ct:..      \n" +
                       "          .,:ifGGf;:,,..                       .                                .,:;1C0Ct;:.        \n" +
                       "            .,:;fGGf;:,,..                                                    .,,:1C0C1:,,.         \n" +
                       "             ..,:;fGGf;:,,.                                                 .,,:1C0C1:,...         .\n" +
                       "                .,:;tGGf;:,,..                                            .,,:1C0Li:,..             \n" +
                       "          .       .,,;tGGfi:,,,.                                   .   ..,,:1C0Li:,..               \n" +
                       "                    .,,;tG0Li::,                                      .::;tG0Li:,.                  \n" +
                       "                      .,,:tC0Lt;...                                  ..1fG0Li:,.                    \n" +
                       "                        :,,:tG8L:,.                                 .,i00Li:,..                     \n" +
                       ".  ..                   . .,:1001,.   .                             .:L@L;:,.                       \n" +
                       "i::,,..                    ,:t@L;:.                                 .:i8Gi:,.                       \n" +
                       "@0L1;,,,.                 .,;C@t:,.                                 .,;G81:...                      \n" +
                       "G@@8C1;:,,.               .,i08i:.        .                          ,:f@f:,.                     ..\n" +
                       ";tC8@8Gt;,,,.             .:t@C;,.                    .              .,18G;,.                    ,1L\n" +
                       ",:;1C8@@C1;,.             ,:C@t:,.              .. .....             .,;G81,..                ..i0@@\n" +
                       ".,,:;t0@01:,             .,;00i:..           ...,:11;,,...            .:f@f:,.                .;G@@L\n" +
                       " ..,:t8@C:.              .:1@C;,           .,:ifG0000GLt;:..          .,18C;,.               .:it8@C\n" +
                       "  .,iL@8i..              .:f@1,.         .,;fG0GL1;;itC00C1:.          ,:G0;,               .,::i0@G\n" +
                       "```";
            n[ 47 ]  = "```" +
                       "GCLft1i:,.                              .,;t8C;,       ,i8Gi:,.                                 .:;;\n" +
                       ":,..                                   .,;t80i:.       .:t8Gi:,.                                    \n" +
                       "                                      .,;f801:.         .:t80i:,.,,                                 \n" +
                       "                        .     .      .,;f801:.           ,;f8G1:,..                                 \n" +
                       "                                  ...,;f801:,            .,;f8G1:,....  .   ..                      \n" +
                       "                   ...............,,,;t801:.              .,;f8Gi:,,...............                 \n" +
                       "         ..............,,,,:::;ii1ttfC801:,                .,;f@0Lft1ii;;::,,,,...............  ..  \n" +
                       "       . .,:::;;;ii11tfLLCCGGGGGGGCCCfti:..                 .,:1tLCCCGGGGGGCCLLftt1ii;;:::::,..,.   \n" +
                       "      .,ifLCGGGGGGGGGCLLftt1ii;::,,,.....                     .,...,,::;;ii1tffLCCGGGGGGGGCCffi,    \n" +
                       "      .,iC@8Cft1i;::,,,......... .                             ..    ...........,,,:;;i1tL0@8L;..   \n" +
                       "       ..:1C0Ct;::,,....                                        .               ...,,::ifGGLi:.     \n" +
                       "         .:;1C0Gt;:,,..                        .                                 .,::if00fi:,.      \n" +
                       "          .,,:iL0Gt;:,,.                                                       ..,:iL0Gf;:,. .      \n" +
                       "            ..,:iL0Gt;:,..                                                   ..,:iL0Gt;:,.         .\n" +
                       "               .,:iL0Gt;:,..                                               ..,:iL0Gt;:,.            \n" +
                       "          .      .,:if0Gt;:,,..                                    .    ...,:iL0Gt;,,.              \n" +
                       "                   .,:;f0Gf;::,                                         ,::1L0Gt;,,.                \n" +
                       "                     .,:;fGGf1;...                                    ..i1C0Gt;,,.                  \n" +
                       "                       ,;,;fG0L:,.                                   .,iG0Ct;,,.                    \n" +
                       "                        ..,:i08t:.    .                              .;C@L;:,..                     \n" +
                       "......                    ,:18Gi:,.                                  ,:t@Ci:,.                      \n" +
                       "Cti:,,,.                  ,:f@L;,.                                  ..,i081:,.                      \n" +
                       "@@8L1;:,..               .,;G@t:,.        .                          .,;C@f:,.                      \n" +
                       "tG@@8Ct;,,,.             .,180i,.                                     .:t@C;,.                    .;\n" +
                       ":;tG@@8Ci;:.           . .:f@C;,.                   ....              .,i08i,.                  .:C8\n" +
                       ",,:;t0@@f;,.            .,;C@t:,.              ...,,,,..              .,;C@t:,.                .;G@@\n" +
                       "..,:i0@0i,.             .,i00i,.             .,:itCGL1;:..             .:t@L;,.               .:iL@@\n" +
                       " .,;f8@f...             .,1@L:.            .,;fG00LLC00Ct:.             ,;0G;,               .,::18@\n" +
                       "```";
            n[ 48 ]  = "```" +
                       "ti;:,.                                ..,:100i,.       .,18Gi:,.                                    \n" +
                       ".                                     .,:t08t:.         .:f@Gi:,.                                   \n" +
                       "                                     .,:t88f;..          .;L@Gi:,,,                                 \n" +
                       "                       ..     .     .,:t88f;,             ,;f@Gi:,.                                 \n" +
                       "                              .....,,:t88f;,.             .,;L@Gi:,,......  ..                      \n" +
                       "              .................,,,,:;t88t:,.               .,;f@Gi;:,,,..................           \n" +
                       "        .....,,,,,,,,,:::;ii11tfLLCCG08t;,.                 .:;L80GCCLfft1ii;;::,,,,,,.....,,,,.  ..\n" +
                       "     ...:;;ii11ttfLLCCGGGGGGGGCCLfft1i;,..                   ..:;itffLLCCGGGGGGGGCLLfft11ii;;;:.,,. \n" +
                       "    .,iCGGG000GGCCLLft11i;;::,,,.... ...                       ........,,,::;ii1ttfLLCGGG000GGGLi.  \n" +
                       "     .:f08Gf1i;::,,,........  .                                        ... ........,,,::;itL08G1,.  \n" +
                       "     ..,ifG0C1;::,,..                                           .                  .,,,:;tG0Ct;,.   \n" +
                       "       .,:;tG0C1::,,.   .                      .                               .  .,,:;tG0C1;:,     \n" +
                       "         .,:;tC0C1::,..                                                          .,:;tG0C1:,,..     \n" +
                       "           .,,;1C0C1::,..                                                      .,:;tG0Li:,..       .\n" +
                       "          .  .,,:1C0C1;:,..                                                  .,:;fG0Li:,..          \n" +
                       "          .    ..,:1C0Gt;:,...                                     .     ...,:;f00Li:,...           \n" +
                       "                 ..,:1C0Gt;::,                                           ,,:if00Li:,..              \n" +
                       "                   ..,:iL0Gti;..                                        .iif00fi:,..                \n" +
                       "                     ..:;iL00L:,..                                    .,;C00fi:,.                   \n" +
                       "                       .,,:1G@f:,.    .                               .;C@Ci:,,.                    \n" +
                       "                         ,:iG8t;,.                                    ,;f@L;:,.                     \n" +
                       ";,,,,..                  .:t@G;:,.                                    .:180i:,.                     \n" +
                       "@Gfi:,,..               .,:L@L;,.         .                           .,;G@t:,.                     \n" +
                       "0@@0L1;,,,.             .,i0@t:,.                                      ,:L@L;,.                    .\n" +
                       "if0@@8Ci;:,            ..:1@0i,.                                       .,180i,.                   ,f\n" +
                       ",:if0@@Ci,..           .,:L@L;,.                ..  ...                .,iG@1:,.                .:C@\n" +
                       ",,:;C@8t,.             .,;G8t:.                 .:ii;:.                 .:f@f;,.               .,;L@\n" +
                       ".,:10@G:..             .,i8G;,               .,;fG000Ct:..              .,i8G;,                ,,:i8\n" +
                       "```";
            n[ 49 ]  = "```" +
                       ".  .                                  .,:1G81:.         .,t@Ci:,.                                   \n" +
                       "                                     .,:10@f;,.          ,:L@Gi:,.                                  \n" +
                       "                                     ,:10@L;,.           .,;L@Gi:,:                                 \n" +
                       "                       .        ....,:10@Li,              .,iL@Gi:,..                               \n" +
                       "                       ...........,,:10@L;,.               .:;L@Gi:,,............                   \n" +
                       "           ..............,,,,,,::;;it0@f;,.                 .:;L@Gti;;::,,,,,,..................    \n" +
                       "      .,,,,,,,,,:::;;ii1ttfLLCCGGG00GGf;,.                   .:iLG000GGGCCLfft11i;;::,,,,,,,,,,,,  .\n" +
                       "   ..,;i1ttffLLCCGGG000GGGCLLftt1ii;::,..                      .,::;ii1ttfLCCGGG000GGGCCLfftt11i:::,\n" +
                       "  .,;C88000GGCLfft1ii;;::,,,.......  ..          ...            ... .......,,,::;;ii1tffLCGG00080C;.\n" +
                       "   .,iC00C1i;:,,,........                       .. ..                  ..     ........,,,:;itC80L;,.\n" +
                       "    ..:1L00Li;:,,,.                            ..   ..          .                    .,,:;1C00fi:.. \n" +
                       "      .,:if00Li::,..                        . ..     .. ..                        ...,::1C0Gfi:,.   \n" +
                       "       ..,:if00Li:,,...              .....               .. ...                   .,,:1C0Gt;:,..    \n" +
                       "          .,:;fG0Li:,,..               .                     .                  .,,:1C8Gt;:,.  .    \n" +
                       "          . .,:;tG0L1:,,..               .                ..                  .,,;1C0Gt;:,.         \n" +
                       "          .   .,:;tG0C1::,...              ..           ..         .        .,,;1C8Gt;,,.           \n" +
                       "                .,:;tG0C1::,,.             .             .               .,,:;tG8C1;,,.             \n" +
                       "                  .,:;tC0C1;;.             .             .               .;;tG8C1:,,.               \n" +
                       "                    .,,itC0Gf:,..         ..   ..   ..   ..            .,:LG8C1;:,.                 \n" +
                       "                      .,:;t0@f:,.         ....         ....            ,;C@G1;:,.                   \n" +
                       "                        ,:iC@L;,..                                     ,;C@L;:,.                    \n" +
                       "......                  .:1881:,..                                     ,:t@Gi:,.                    \n" +
                       "L1;:,,..                ,:f@Gi:..         .                            .,i08t:,.                    \n" +
                       "@@0L1;,,...            .,;C@L;,.                                       .,;C@L:,.                    \n" +
                       "L0@@8Ci;:,.            .,i881:,.                                        .:t@G;,.                   .\n" +
                       ":if0@@G1:...          .,:t@Gi,.                                         .,i081:,.                 .L\n" +
                       ",,;f@@C:.              ,;L@f:.                     .                     ,;C@f:,                ..,L\n" +
                       ",:iC@8i..              ,;G8i,.                  .:1fi,.                  .:1@C:,                 ..;\n" +
                       "```";
            n[ 50 ]  = "```" +
                       "   .          .                      .:iC@L;,.            .:L@Ci:,.                                 \n" +
                       "                                    .:iC@Gi:.             .,;G@Ci:,.                                \n" +
                       "                              .   ..:iC@G1:.               .,iG@Ci;,..                              \n" +
                       "                       .   ......,,:iG@G1:.                 .:iG@Ci:,,.......                       \n" +
                       "              .............,,,,,,:;iC@G1:,                   .:iG@Ci::,,,,...,.............         \n" +
                       "  .......,,,,,,,,,,,:::;;ii1tffLCCG0@G1:,.                   .,:1G@0GCCLftt1ii;;::,,,,,,,,........,,\n" +
                       "  .::::;;;;i11ttfLCCGG000000GGGCLLf11;,..                      .,;11fLLCGGG000000GGCLLftt1ii;;::::::\n" +
                       ",1tLCCGG0000000GGCLLftt1i;;:::,,,...,.           .,.             .,...,,,,::;;i1ttfLLCGG0000000GGCLf\n" +
                       ":t8@@GCLft1i;;::,,,,...........   .             .:::.             .    .............,,,:::;i1tffCG@@\n" +
                       ".,if08Ct;::,,,.....                            .:, ,:.                  .            .....,,::;tC80L\n" +
                       "  ,:if08Ct;:,,..                              .:.   .:.                                 .,,:;tG80fi;\n" +
                       "   .,:itG8Gt;:,,..                     .....,,:,     .:,,.....                     ..  .,:;tG8Gfi:,.\n" +
                       "    ..,:;tG8Gt;:,,..  .             .::,,,....         ...,,,,::.                    .,:;tG8Gti:,.. \n" +
                       "       .,:;tG8Gt;:,,..               .,,.                   .,..                   .,:;tG8Gt;:,..   \n" +
                       "         .,:;tG8Gt;:,,.                .,,..              .,,.                   .,:;tG8Gt;:,.      \n" +
                       "         . .,:;1C8Gt;:,,...               .:.           .:,.       .           .,:;tG8Gt;:,.        \n" +
                       "             .,:;1C8Gfi:,,,.               :.           .:.                 ..,:;f08C1;:,.          \n" +
                       "               ..,:1C80fi::.              ,:     ...     :,                .::if08C1;:,.            \n" +
                       "                 .,,:1C80ft:...           :, ..,,,,,,,.. ,:               .:tL08C1;:,.              \n" +
                       "                   ..,:1C88f:,.          .;:,,,.    ..,,,,;.             ,:f88C1;:,.                \n" +
                       "                     .,:;L@01,.  .        ,..           ..,              ,i0@Li:,..                 \n" +
                       "                      .,iC@L;:.                                         .,;L@Ci:,.                  \n" +
                       "......                .,10@t:,. .         .                              .:t@01:,.                  \n" +
                       "L1;:,,...             ,:f@0i:,                                           .,i0@f:,.                  \n" +
                       "@@0Li::,,            .,;C@C;,.                                            ,;L@C;,..                 \n" +
                       "L0@@0fi:. .          .,i0@f:,.                                            .:t@0i:,.                 \n" +
                       ";1G@@f:.             .:t@0i,.                                             .,i0@t:,                  \n" +
                       ":10@G:..             .:f@L:.                                               .:L@L:.                 .\n" +
                       "```";
            n[ 51 ]  = "```\n              .                     .:iL@G;,.              .:L@Ci:,.                                \n                                   .:iL@01:.               .,iG@Ci;,.                               \n                             ..  .,:iC@0t:.                 .,1G@C1:,..                             \n                      ..........,,:iC@0t:.                   .:1G@Ci:,,,...........                 \n       ..............,,,,,,,:::;;i1C@0t:,                     ,:1G@G1i;;::,,,,,,,.............. ..  \n..,...,,,,,,,,::::;;i11tffLCCGG00000Gt;,.                     .,:1G00000GGCCLfft1ii;;:::,,,,,,,,,,,,\n.:;;iii11tffLCCGG0000000GGCLLftt11;:;,.                         .,::;i1ttfLLCGG0000000GGCCLfft11i;;:\nLGG0000000GGCCLfft1ii;;::,,,,........            ,;,              .........,,,:::;ii1ttfLCCGG000000G\nC@@0Lt1ii;::,,,,........... .     .             ,i:i,                .  .  . ...........,,,,::;i1tfL\n:1C00L1;::,,,...                               ,;,.,;,                  .                ..,,,:;if00\n.:;1L80L1;:,,..                               ,i.   .;,                               .   .,::1L08Ct\n .,,;1L08L1;:,,.                    ....,,,,,:;,     .;:,,,,,.....                 ..   .,,:iL08C1;:\n   ..,:iL08C1;:,..    .             ,;;:,,,,..         ..,,,,:;i,.                    .,,:iL88L1;:,.\n     ..,:if08C1;:,,.                 .,,.                   .:,.                    .,,:1L88Li:,,.  \n       ..,:if08C1;:,..                ..,:,.              ,::.                    .,,:1C80Li:,..    \n         ..,:if08C1;:,.. .               .:;.           .;:.       .            .,:;1C80Li:,..      \n            .,:if08C1;:,,,.               ,i.           .i,                  ..,:;1C80fi:,..        \n              .,:;fG8Gt;::,               ::     ...     ::                 .::;1C80fi:,.           \n                .,:;tG0Gt1:...           .;, ..,:::::,.. ,;.              ..,1tC80fi:,..            \n                  .,:;tG80t:,..       .  ,i::::,.   .,::::i,              ,:1080fi:,..              \n                    .,:iL@81:.           ,;,..         ..,;,              ,i0@Ci:,,.                \n                     .,;L@Gi:.                                           .,iC@Ci:,.                 \n                     .,iG@L;,.  .         .                               ,:t@01:,.                 \n;,,,,..              .:t88t:,.                                            .,18@f:,.                 \n8Gfi:,,,.           .,:L@Gi:..                                            .,;C@C;,.                 \n8@@0fi:.  .         .,;G@L;,.                                             ..:f@0i:,.                \n1C@@G;..            .:188t:.                                               .,188t:.                 \n;C@@1..             .:t@G;,.                                                ,:L@f:.                .\n```";
            n[ 52 ]  = "```\n              .                    .:;L80i:.                .;C@Ci:,.                               \n                                  .,;L@8t:.                 .,i0@C1:,..                             \n                             ....,,iL@8f;.                   .,10@G1:,,.....                        \n                ............,,,,,:;L@8f;,                     .:10@Ci:,,,,,,............            \n.    ..........,,,,,,,,:::;;ii1ttLG@8f;,.                      ,:10@GLft1ii;:;:,,,,,,,,,,...........\n,,.,,,,,::::;;ii11tfLLCGG000000000CLt;,.                       .,:1LCG00000000GGCLLft11i;;::::,,,,,,\nii1tffLLCGG000000000GCCLfft1ii;;::,::.            . .            .,:,,:;;ii1ttfLCCGG00000000GGCLLft1\n000000GGCLLft11i;;::,,,,........ ....           .:t;..             ............,,,,::;;ii1tffLCCGG0G\n80Lt1i;::,,,,,.......       .     .            .;1:1; .                           .........,,,,::::;\nfG80fi;::,,...                                .:i...i;.                                    ..,,::;1L\n:itG80fi::,,.                                .;1.   .i;.                                   .,,:itG80\n.,:;tG80fi::,..  .                 ...,,:::::;i.     .i;::::,,,....                ..     .,:;tG80fi\n  .,:;tC80fi::,..                  .i1;::,,,..         ..,,,::;1i.                     ..,:;fG8Gf;:,\n    .,:;1C80fi::,..                 .,::.                 . .:;,.                    ..,:ifG8Gt;:,. \n      .,:;1C80Li::,..                 .,;:..              ,:;,.                    ..,:if08Gt;:,.   \n        .,,;1C80Li::,..                 .,;;.           .;;,.      ..            ..,:if08Gt;:,.     \n          .,,;1L88L1;:,...                :i..          .i;.                  ...,:if08Gt;:,.       \n            ..,:1L88L1;::,               .i: .    .     .:1.                 .,::iL08C1;:,.         \n              ..,:iL08L11:....           .1....,:;;;:,.  .1,                 ,i1L08C1;:,.           \n                ..,:iL080t,,..        ...;1,:;;:,...,:;;:,1;.             ..,iG88C1;:,.             \n                  ..,:1C@81:,.          .;1;:.         .::1;.             .,i0@Gt;:,.               \n                    .,;f@0t:..                                            .,iG@Ci:,.                \n                    .,;C@C;:..            .                               .,:f@01:,.                \n......              .:10@f;,.             .                                .:188t:,.                \nL1i:,,,.            ,:f@81:,.                                              .,iG@L;,.                \n@@0fi;,. ..        .,;C@Gi,.                                                ,:L@Gi:..               \nC8@8t,.            .,i0@f:.                                               . .,1881:.                \nt8@C,.             .,180i,.                                                  ,;C@f:.                \n```";
            n[ 53 ]  = "```\n                                  .,;f88t:.                  ,;G@Ci:,.                              \n                                ..,;f8@L;,.                  .,10@C1:,..                            \n                       .  ......,:;L@@L;,.                    .:t0@Gi:,,.......                     \n          ............,,,,,,,,::;if88L;,                       .:t0@Ci;:,,,,,,,,,.............      \n........,,,,,,,,,,:::;;ii1ttfLCCG0@@Li:.                        ,;t0@0GCCLftt11i;;:::,,,,,,,,,,.....\n,,,,:::;;ii1ttfLLCGG000888000GGCLft1;,.                          .:ittLCCGG00088000GGCCLftt1ii;;::,,\ntfLCGG000888000GGCLLft11i;;::,,,,..,,            .:...            .,,..,,,::;;ii1tffLCCGG00088000GCL\nGGGCCLftt1ii;:::,,,............   ..           ..1f1..              .    ...........,,,,::;;ii1tffLL\nf1i;::,,,,........                            ..11,i1..                              .........,,,,,,\n88Cti;:,,...                                 ..1i...i1.                                      ..,,,,:\n1L08Gt;:,,..                                ..11.. ..i1..                                    .,::ifG\n,:if08Gt;:,,..   .                ..,,:::;;;;ii.    ..;i;;;;:::,,,..                .      .,:;1C88C\n..,:if08Gt;:,,..                  .:t1;::,,,..          .,,,::;1t;.                      .,:;1C88C1;\n  ..,:if08Gt;:,,...                 .:;,...               . .,;;..                     .,:;1C88L1;:,\n    ..,:ifG8Gti:,,..                 .,;;:.               .:i;,.                     .,:;1C88Li::,. \n       .,:;tG8Gfi:,,..                 ..:i:            .:i;,      ..              .,:;tC80Li:,..   \n         .,:;tG80fi::,...               ..1i..          .;t..                  ...,:;tG80Li:,..     \n           .,:;tG80fi:::,               .,t,..         ...t,.                 .,,:;tG80Li:,..       \n             .,:;tG80fii:.              .;t....,:;i;:,.  .t;..                ,;itG80fi:,..         \n               .,:;tC80C1,,...        ...1i,:;ii;,.,:ii;:,i1..              .,;C080fi:,..           \n                 .,:;tG@01:,.           .fti;:.       .,;i1f,.             .,iG@0fi:,,.             \n                   .,;t88f;,.           .,.               .,.              .,10@Li:,..              \n                   .,;L@Gi:,.                                              .,;L@01:,.               \n.                  .,iG@C;,.              .                                 .:t@8t:,.               \n;:,,,,.            .:t8@f;,.                                                .,i0@L;,.               \n@0Li;:.  .        .,;L@01:.                                                 .,;C@Gi:,.              \n8@@L;.            .,iG@C;,                                                   .:t@81:..              \nG@8i..            .,i08t:.                                                   .,;G@t:.               \n```";
            n[ 54 ]  = "```\n                                 .,;f88f:.                    ,;G@Ci:,..                            \n                              ...,;f8@C;,.                   ..:10@Ci:,...   .                      \n                   ..........,,,:;f8@Ci,.                      .:t0@Ci:,,,,..........               \n      ...........,,,,,,,,,:::;;i1L8@Ci,.                        ,;t0@Cti;;:::,,,,,,,,,,.............\n.....,,,,,,,:::;;;i11tffLCCG0008888Ci:.                         .,;t0888000GGCLfft11i;;::::,,,,,,,,,\n,::;i11tffLCCGG008888800GGCLLftt1;;:,.                            .,;;i1tffLCCG0008888000GGCLfft1i;;\nG000888800GGCLLft11ii;:::,,,,,.......          ..,1:..             .,.....,,,,,::;;ii1tffLCGG0008800\nfftt11i;;::,,,,...........  .     .           ..,ftf:..                      ............,,,,::;;iii\n::::,,,,......              .     .           .,f;.;f:..                .                 ........,,\nGfi;:,,...                                   .,f;...:f:.                                       ...,,\nG88L1;:,,..                              ....:f;..  .:f:...                                   .,,,:;\n;1C88L1;:,,.                      ,:::;;iiiii1;..   ..:1iiii;;;:::,.                .       ..,:;tG8\n,:;1C88C1;:,,.                    ,tti:::,,..           ..,,,::ift:.                      ..,:if0@0f\n .,:;1C88C1;:,,. .                 .,i;,. .               ....;i:.                      ..,:if080fi:\n   .,,;1L88C1;:,,..                 ..:i;,.               .,;i:..                     ..,:iL08Gt;:,.\n     .,,;1L88C1;:,,.                  ..:i1,.            .ii:..     .               .,,:iL08Gt;:,.  \n       ..,:iL08Gt;:,,...                .,f:.           .:f,..                    .,,:iL88Gt;:,..   \n         ..,:iL08Gt;:,,,                .;t..           ..ti..                  ,,,;1L88Gt;:,.      \n           ..,:if08Gtii:.              ..t1.. ..,;i;,.   .it.. .               .;;1L88Ct;:,.        \n              .,:if080C1,...          ...f:.,;i1i;:;i1i;,.,f,.               ..:fC88C1;:,..         \n                .,:if0@01:,.           .;C11i;,..    .,;i11Ci..             .,;L@8Ct;:,.            \n                 .,:;t8@C;,.            ,i:..            .:i:               .,18@Ci:,,.             \n                  .,;L@8t;,.                                                .,;C@G1:,.              \n                  .,iC@Gi:,.              .                                 .,:f@8t:,.              \n,......           .:10@L;,.                                                  .:10@L;,..             \nGfi;:,   .        ,:f@8t:,.                                                  .,;G@Gi:,.             \n@@G1,             ,;C@Gi,.                                                    .:f@81:.              \n@@L,..           .,;G@f:,                                                     .,i0@t:.              \n```";
            n[ 55 ]  = "```" +
                       "             .                  .,;t0@L;,.                     ,;G@Ci:,..                           \n" +
                       "                            ...,,;t0@Gi,.                    . .:188Ci:,,... ..                     \n" +
                       "              ...........,,,,,::;t8@G1,.                        .:t0@Ci::,,,,,,,...........         \n" +
                       "     .......,,,,,,,,:::;;;ii1ttfC8@G1:.                         .,;f8@GLft11i;;::::,,,,,,,,,,,......\n" +
                       "...,,,::::;;ii11tfLLCGG0088888880CLi:,                           .,;tCG088888800GGCCLftt1ii;;:::,,,,\n" +
                       "i1tfLLCCG0008888800GGCLLft11ii;;,::..           .....              .,:,:;;ii1tffLCCG0008888800GGCLft\n" +
                       "08800GGCCLftt1ii;;::,,,,,............          ..iLi..              ...........,,,,,::;;ii1tffLCCGGG\n" +
                       "i;;;:::,,,,...........      .                 ..ifif1..                        . ............,,,,:,,\n" +
                       ":,,,,,.....                       .          ..;f:.,f1..                .                     .....,\n" +
                       "i::,,,..                                    ..if,...,fi.                                       .  .,\n" +
                       "8Gfi::,...                             .....,if:.   .,t1,....                                  ...,,\n" +
                       "f0@0fi;:,,.                      .:;;iiiiiiiii,.     ..i1iiiiiii;;:,.               .         .,,:;t\n" +
                       ":ifG@0fi;:,..                    .if1;:,,...             ...,,,:1f1,                        .,:;1C88\n" +
                       ".,:itG@0Li;:,..  .                .,;i:...                ....:ii,.                      ..,:;tC88C1\n" +
                       "  .,:;tG@0Li;:,...                  .,;i:..               ..:ii,..                     ..,:;tG88C1;:\n" +
                       "    .,:;tG88L1;:,..                   .,;1;.             .:1i,.     .                ..,:;tG88L1;:,.\n" +
                       "      .,:;tG88L1;:,....                 .if,.           ..t1...                    ..,:;tG@8L1;,,.  \n" +
                       "        .,:;tC88L1;:,,,                ..t1..           ..;f..                   .,,:itG@0Li:,,.    \n" +
                       "          .,:;1C88C1;;:.               .,L:..  ..:;:.    .,L:. .                .:;ifG@0Li:,..      \n" +
                       "           ..,:;1C88CL1,...           ..;f...:;11i;i11;:...fi..                .:tL0@0fi:,,.        \n" +
                       "              .,:;1C88Gi:,.           ..tf;111;,.   .,;i11itf..              .,:f8@0fi:,,.          \n" +
                       "                .,:;t8@C;,.           ..tti:..          .:itf.               .,18@C1;:,.            \n" +
                       "                 .,;f88fi,.             ..            .    ..                .:iC@G1:,..            \n" +
                       "                 .,;C@01:,.               .                                  .,:f@8t:,.             \n" +
                       ".   ..           .,i0@Ci:.                                                    .:18@L;,..            \n" +
                       "1:,:,.   .       ,:t8@f;,.                                                    .,iG@Gi:,.            \n" +
                       "@0t:.            ,;L@01:.                                                      ,:L@01:.             \n" +
                       "@8;.             ,;C@C;,.                                                      .,18@t:.             \n" +
                       "```";
            n[ 56 ]  = "```" +
                       "             .                ..,;t0@Ci,.                       ,i0@Ci:,...                         \n" +
                       "                   .   .......,:;t0@01:.                        .:t0@Ci:,,,........   ..            \n" +
                       "        ...........,,,,,,,,,::;it0@01,.                         ..:f8@C1;:::,,,,,,,,,,..........    \n" +
                       ".......,,,,,,,,:::;;;i11ttfLCCG08@0t;.                           .,;f8@80GCLfft11ii;;:::,,,,,,,,,,,,\n" +
                       ",,,::;;ii1ttfLLCGG0088888800GGCLfti:..                            .,:1tfCGG00088888000GCCLfft11i;;:,\n" +
                       "fLGG00888888000GCCLftt1ii;;:::,,.,,..           ..;,..              .,,.,,:::;;i11tffLCGG0008888800G\n" +
                       "CGCCLftt1ii;;:::,,,,.............. ..         ...tCL,.               ..   ..........,,,,:::;;i11tfff\n" +
                       ",,,,,,,,..........          .     .           ..tt,1L,.                              ...............\n" +
                       ",,.....                                      ..tt,..if,..                                         ..\n" +
                       ",,,,..                                      ..tt.....if,.                                      ..  .\n" +
                       "fi;:,,.. .                          ......,,:tt..   ..if:,,......    .                          . ..\n" +
                       "88Ct;:,,..                      .;iii11111iii;..     ..:iiii1111iii;,.              ..          .,,,\n" +
                       "1L8@Gti:,,..                    .;ft;,,,...               ....,,;tfi.                        ..,:;tC\n" +
                       ":;1L8@Gti:,,..   .                .:i;,...                .....;1;..                      ..,,:iL0@0\n" +
                       "..,;iL8@Gfi:,,..                   ..:1i,..                .,;1;..                       .,,;1L0@0fi\n" +
                       "  ..,:if0@Gfi::,..                   ..:ii,.              .i1;..    .                  .,:;1L8@Gfi:,\n" +
                       "    ..,:iL0@0fi;:,.....               ...f1..            .;L,..                      .,:;1L8@Gti:,..\n" +
                       "      ..,:if0@0fi;:,,.                 .,L:..           ..,L:..                   ..,:;1C8@Gt;:,.   \n" +
                       "        ..,:if0@0fi;::.               ..iL...   ..,.     ..f1...                 .::;1C88Gt;:,..    \n" +
                       "          ..,:if0@0Ltt,...            ..f1....:i11111i:....iL..                  ,1tC88Gt;:,.       \n" +
                       "            ..,:if0@8C;:,.            .,L;:i111;:...,;111i::L:..              .,:1088Gt;:,.         \n" +
                       "               .,:if8@Ci,.       .    .;Gt1i:.         .:i1tC1..              .,18@0ti:,..          \n" +
                       "                .,;t8@Ci:.             .:,.           .   ..:,                .:1G@G1:,,.           \n" +
                       "                .,;L@8t;,.                .                                   .,;L@8t:,.            \n" +
                       "                .:iG@01:,.                                                     .:t8@f;,.            \n" +
                       ",..,.    .      .:10@Ci,.                                                      .,i0@Ci:,.           \n" +
                       "0t:,            .:f@8f:.                                                        ,;L@01:.            \n" +
                       "@f..            ,:L@Gi,.                                                        .,188t:.            \n" +
                       "```";
            n[ 57 ]  = "```" +
                       "       .     .               ..,:tG@Gi,.                        .,i0@C1:,,..                        \n" +
                       "                ...........,,,:;10@8t:.                          .:t8@Ci::,,,,..........            \n" +
                       "      .......,,,,,,,,,:::::;;i1f0@0t:.                           .,:f8@Gt1i;;:::,,,,,,,,,,,,........\n" +
                       "....,,,,,::::;;ii11tffLCCG00888880t;,.                            .,;f8888800GGCLLftt1ii;;::::,,,,,,\n" +
                       "::ii1tffLCCG00888888800GGCCLfftii;,.                .               .:ii1tffLCCG00888888800GGCLLft1i\n" +
                       "0088888800GCCLftt1ii;;:::,,,,...,,. .          ..:Li..               .,...,,,,,:::;;i11tffLCGG000880\n" +
                       "ttt1ii;;:::,,,,,............    .  ..         ..;LtC1..                     ............,,,,,:::;;;;\n" +
                       ",.............              .    ..          ..:Li,:Li..                                  ..........\n" +
                       ",..                                         ..:L;...:Li..                                           \n" +
                       ",..     .                                   .:L;.. ..,Li..              .                       .   \n" +
                       ":,,,...                           .....,,,::iL;..   ..,L1::,,,.....   .                         .   \n" +
                       "Gfi;:,,..                      .;1i111111iii;,..      .,;iii111111i1i,.             ..            .,\n" +
                       "G@8C1;:,,..                    .:tti,.....                 .....,;tf;.                         .,,,:\n" +
                       ";tG@8C1;:,,.                     .,ii:....                .....,i1:..                       ..,:;1C0\n" +
                       ",:;tG88C1;:,,..  .                ..:i1:..                 ..,i1:...                      ..,:;tG@8C\n" +
                       " .,:;tC88Ct;:,,..                    .,i1;.              ..:11:.... .                   ..,:itG@8C1;\n" +
                       "   .,:;tC88Ct;:,,..                   ..;C:.             .,L1...                      ..,:ifG@8L1;:,\n" +
                       "     .,:;1C8@Gti:,,,.                 ..if..             ..1t..                    ...,:if0@8L1;:,. \n" +
                       "       .,:;1C8@Gti:::.                ..f1..     ...     ..;C,..                  .,::if0@8L1;,,.   \n" +
                       "         .,:;1C8@Gfi1:..             ..:C:.....:i111i:.. ..,Li..                  ,i1f0@0L1;,,.     \n" +
                       "           .,:;1L8@0C:,,.            ..iL,,:;1t1i:,,;1t1i:..tt..                .,iG8@0L1;:,.       \n" +
                       "             .,:;1C8@Ci:.            ..LL1tti:..      .:i1t1fC,.               .,i0@8L1;:,..        \n" +
                       "               .,;10@G1:.             .11;,.             ..:it,.               .:10@G1;,,.          \n" +
                       "               .,:f@@f;:..                                   .                 .,;L@8t;,.           \n" +
                       "               .,;C@8t;,.                                    .                  ,:f@@f;,.           \n" +
                       ".  .           .:i0@Gi:.                                                        .:10@Ci:,.          \n" +
                       "f:,.           .:t8@L;,                                                         .,;C@01:.           \n" +
                       "8:.            .:f@01,.                                                          .:t88t:.           \n" +
                       "```";
            n[ 58 ]  = "```" +
                       "       .     .           ....,,:1G@01,.                          .,18@C1:,,......                   \n" +
                       "            ..........,,,,,,,:;1G@8t:.                            .;f8@Ci;:,,,,,,,,...........      \n" +
                       "   .....,,,,,,,,,::::;;;i11tfLC0@8f:,.                            .,;L8@0Lftt1ii;;::::,,,,,,,,,,,...\n" +
                       "..,,:::;;;ii1ttfLLCGG0088888888GGf;,.                              .,;fG08888888800GCCLfft11ii;;::,,\n" +
                       "1tfLCGG008888888800GCCLftt11i;:::,.             ..:,..               .,::;ii11tffLCCG008888888800GCL\n" +
                       "08800GCCLfft11i;;:::,,,,,.......,.  .          ..tGL,..               .,.......,,,,,:::;;i11tffLCCGG\n" +
                       ";;;::::,,,,,..........             ..         ..tL:tL,..                         ...........,,,,,,,,\n" +
                       ".........                   .                ..tf,..1L,..                                      .....\n" +
                       ".           .                               ..tL,....1L,..                                          \n" +
                       ".                                          ..tL,.   ..1L,.          .    .                      .  .\n" +
                       ",....                            ...,,,::;;itf,.     ..1fi;;::,,,.... ..                        .   \n" +
                       ";::,,..                       .;t111111iii;;:...       .,;;;ii111111t1:.             .             .\n" +
                       "8Gfi::,..                     .,1fi,.....                    ....,;ft;.                        ....,\n" +
                       "L0@0fi;:,..                     .,i1;....                 . ....:11:..                        ..,:;1\n" +
                       ":iL0@0L1;:,,.                    ..,i1;,..                  ..:11:...                       .,:;1L0@\n" +
                       ".,:if0@8L1;:,,..                    .,i1i,..              ..;11:... .                    ..,:;1C8@0f\n" +
                       " ..,:if0@8L1;:,,..                   ..,ft..             ..iC:...                       .,:;1C8@0fi:\n" +
                       "   ..,:if0@8L1;:,,..                  .,Li..              .:C:..                    ...,:;1C8@Gfi:,.\n" +
                       "     ..,:ifG@8C1;::,,                 .:C,..              ..L1..                   .,,:;tC8@Gti:,.. \n" +
                       "        .,:ifG@8C1i;i.               ..1L...  ..:;1i:.    ..1L..                   ,;itC8@Gti:,.    \n" +
                       "          .,:itG@8GL:,,.             ..L1...,;1tti;i1t1i:...:C:..                ..;LG8@Gti:,..     \n" +
                       "            .,:itG@@Li:,.            .:Cii1tt1;,.    .:i1t1i;L1..               .,iC@8Gti:,,.       \n" +
                       "             .,,;10@0t:..            .iGt1;,.           .,:1tCf..               .:t8@G1;:,. .       \n" +
                       "              .,:t8@Li:.              .,.             .     .,,                 .:iC@0t;,,.         \n" +
                       "              .,;L@8f;,.                                                        .,:f@@f;,.          \n" +
                       "        .     .:iG@01:,                                       .                  .:18@Ci:,.         \n" +
                       ":.      .     .:10@C;,                                                           .,;G@01:.          \n" +
                       "t.            .:t@8t:.                                                            ,:f@8t:.          \n" +
                       "```";
            n[ 59 ]  = "```" +
                       "      .      ...........,,,,:;iC@@f:. .                            .:18@Ci;:,,,,,.......... ..      \n" +
                       "     ......,,,,,,,,,::::;;ii1tC@@L;,.                              .::f@@Gt1i;;::::,,,,,,,,,,,......\n" +
                       "...,,,::::;;;ii11tffLCGG00888@@8Ci,.                                .,;L8@@88800GCCLfft11ii;;:::,,,,\n" +
                       ";i1tffLCCG00888@@@88800GCCLLft11:.              ..;,..                .:ii1ffLCGG00888@@@88800GCCLt1\n" +
                       "08@@88800GGCLfft11ii;;::,,,,..,,.             ...t0C:..                .,,..,,,:::;;;i11tffLCCG00880\n" +
                       "1111ii;;:::,,,,,..........     .    .         ..fC;tC:..                .    ...........,,,,,,:::;;;\n" +
                       "............. ..                   .         ..tL:,,tC:..                               .. .........\n" +
                       "                                            ..tL:....1C:..                                          \n" +
                       "                                          ...tC:.. ...1C:..             .                           \n" +
                       "                                      .....,tL,..    ..1C:....                                      \n" +
                       "                            ...,::;;iii111ttf,..     ...1ft1111ii;;::,....                          \n" +
                       ",.  .                       .1Cft111i;;::,,.....       ....,::;;ii11tfCf:.           ..             \n" +
                       ":,,,...                     .,it1:....                          ...,it1:.                       .   \n" +
                       "Gfi;:,,..                     ..;t1:....                     ....,it1,..                         ...\n" +
                       "0@8Ct;:,,..                    ...;11:...                    ..,iti,...                        ..,,:\n" +
                       "if0@8Cti:,,..                     ..;11;. .                 .:iti,....                       .,:;1fG\n" +
                       ",:ifG@8Cti:,,..                     ..;Lt..                .:C1,...                       ..,:;tC8@0\n" +
                       " .,:ifG@@Gti:,,...                   .,Lt..                .:C;..                       ..,:;tC8@0fi\n" +
                       "  ..,:itG@@Gti::,,..                 .:G:..                ..Lt..                     .,,:;tG@@Gfi:,\n" +
                       "     .,:itG@@Gti;;.:: .             ..1C,..      ....     ...1C,.                    .:;itG@@Gfi:,..\n" +
                       "       .,:;tG8@Gft:...              ..Lt... ..,;1ttt1;,..  ..;G;..                 ..,tfG@@Gti:,..  \n" +
                       "         .,:;tC8@8f::,.         .  ..:G;..,:itfti:,,;1ft1;,..,Ct..                .,:t8@@Gfi:,..    \n" +
                       "           .,:it0@8f:,.            ..1Ci1tft1:..      .:itft1;fC,..               .:t8@0fi;,,.      \n" +
                       "            .,:10@0t;,.            ..fGf1;,.          .  ..:itLG:.                .:10@0t;:,.       \n" +
                       "             ,:t8@Ci:,.              ,,.                     ..,.                 .,;L@@f;,,.       \n" +
                       "            .,;L@@L;,.                                                             ,:t8@Ci:,.       \n" +
                       "        .   .,iG@01:.                                                              .,i0@G1:.        \n" +
                       "            .,i0@C;,.                                                              .,:L@81:.        \n" +
                       "```";
            n[ 60 ]  = "```" +
                       "      .. .........,,,,,,,,::;iL8@L:.                                :,18@Ci;::,,,,,,,,,,..........  \n" +
                       "......,,,,,,,,,:::;;;iii1ttfLG@@Ci,.                                ,,;L@@GLfft1ii;;;::::,,,,,,,,,,,\n" +
                       ",,,::;;iii1ttfLLCGG0088@@@@@800Ci,.              .....               .,;L008@@@@88800GGCLLftt1ii;;::\n" +
                       "tfCGG00888@@@88800GGCLfft11i;:;:.              ..;Ct,..                .:;:;i11tffLCCG00888@@@8880GC\n" +
                       "0800GGCLLft11ii;;:::,,,,,,....,.              ..;GfGf,.                 .,....,,,,,,:::;;;i11tffLCCC\n" +
                       "::::::,,,,,,,.........              .        ..;G1,:Cf..                         ...........,,,,,,,,\n" +
                       ".........  ..                      .        ..;C1,..:Cf..                                       ....\n" +
                       "                                           ..;C1,. ..,Ct..                                       .  \n" +
                       "                                          ..;G1..   ..,Ct..             .                           \n" +
                       "                                  .......,,iG1..      .,Cf:,......      .                        .  \n" +
                       "                           .,,:;;ii111tttttti...      ..,tttttt111iii;:,,,,.                        \n" +
                       ".                         .,tGLt11i;;:,,,..  .          . ...,,::;;i11fGC;.          ..             \n" +
                       ",.....                     ..;tt:....                            ...,it1:..                     .   \n" +
                       "i::,,..                      ..;tt;....                        ...,1f1,..                        .  \n" +
                       "8Gfi;:,...                    ...:1t;...                      ..,1ti,..                         ...,\n" +
                       "C8@0L1i:,,..                     ..:1t;,                     .:1ti,...                        ..,,:i\n" +
                       ";1L8@8L1;:,,..                    ...:tL,.                  .1Li,..                         ..,:ifG8\n" +
                       ",:;1L8@8L1;:,,..                    ..iG:.                 .,fL,..                       ..,,;1L0@8C\n" +
                       " .,:;1L0@8C1;:,,,.                  ..fL...                ..;G;..                     ..,:;1L0@8C1;\n" +
                       "   .,,;1L0@8Ct;::, ;.               .:G1..                 ..,C1..                    .::;1L8@8C1;:,\n" +
                       "     ..,:iL0@8Ct1:....              .iG:...   ..:it1;,.    ...fC,.                    ,1tL8@8L1;:,. \n" +
                       "       .,,:if0@80t:,,.          .  ..fL,....:itff1iitft1:.....iG:..                .,,108@8C1;:,.   \n" +
                       "         ..,:iL0@8f;,.         .   .,C1,:itfft;,... ..:1tfti:,,C1..                .:10@8C1;:,.     \n" +
                       "           .,:iG@8L;,.             .i0fffti:..          .,;1fffGC,.                .:t0@0t;:,..     \n" +
                       "           .,:t0@G1;,..            .;fi:..                  .,;tt..                .:;C@8f;:,.      \n" +
                       "           .,;f@@Ci,..               .                         ..                   ,:f@@Ci:,.      \n" +
                       "       ..  .,iC@8f:.                 .                         .                    .:10@G1:.       \n" +
                       "           .,iG@Gi,.                                                                .,;C@01:.       \n" +
                       "```";
            n[ 61 ]  = "```" +
                       "      .......,,,,,,,,,::::;i1L8@C;,                                 :,,t8@C1i;:::,,,,,,,,,,,,.......\n" +
                       "....,,,,,::::;;;ii11tffLLCG00@@0i:.                                 ..,;L@@80GCCLftt11ii;;:::::,,,,,\n" +
                       "::;i11ttfLCCG00888@@@@@88000CLLi,..             .,1;..                ..;fLCG0088@@@@@88800GCCLftti;\n" +
                       "G088@@@@@8880GGCLLft11ii;;::,,:.              ..,L00i...                .,:,::;;;i11tffLCCG00888@@88\n" +
                       "LLLLftt1ii;;:::,,,,,,..........              ..,LC;1Gi...                ..........,,,,,,:::;;ii1111\n" +
                       ",,,,,,.,.........                  ..        .,fC:,,iGi..                             ..............\n" +
                       "....                               .        .,fC:....iGi..                                        ..\n" +
                       "                                 .      . ..,fC:..  ..iGi..                                         \n" +
                       "                                         ..,fC:.     ..iG;..            ..                          \n" +
                       "                              ........,,::;LC,.       ..iG1;::,,..........  .                    .  \n" +
                       "                         .,:;;111ttttttttt1i,...       ..:1ttttttttt111i;::,.                       \n" +
                       "                          ,fGL1ii;:,,.....              ..  ....,,::;iitGGi.          .             \n" +
                       ".                         ..;tt;.... .                    .       ...,1f1:..                    .   \n" +
                       ",,....                      ..;tt;....                          ...,1f1,..                       .. \n" +
                       "fi;:,,..                    ....:tt;....                      ...,1fi,...                          .\n" +
                       "@@Gfi;;,...                    ...:1t;,.                      .:1fi,..                          ...,\n" +
                       "tG@@Gfi;:,...                     ..:1L;..                  .,tLi,..                          .,,;it\n" +
                       ":itG@@0f1;:,,..                    ..,Cf,.                  .:G1...                        ..,:itC8@\n" +
                       ".,:;tC8@0f1;:,,,.                   .;G:..                  ..fL,.                      ...,:ifG@@0f\n" +
                       "  .,:;tC8@0L1;::, ,;                .tG,..           .      ..iG:..                    .,::ifG@@Gfi:\n" +
                       "    .,:;tC8@0L11:....              .,Cf...     ..,;:..      ..:G1..                    ,i1fG@@Gfi:,.\n" +
                       "      .,:;1C8@0Gt,,..           .  .;0i... ..,;1fffffti:..  ..,LL,.                  .,;C0@@Gfi:,.. \n" +
                       "        .,:;1C8@8t;,.          .  ..tC,..,;1fff1:...,;tffti:...1G:...               .:iG@@Gfi:,,.   \n" +
                       "          .,:iC@@Ci,.             .,Cf;1fff1;,.       ..,itfftiiG1..                .:t8@0t;:,.     \n" +
                       "          .,:10@0t;,.             .;0CLt;,.               ..:ifL0L,.                .:iC@8f;:,.     \n" +
                       "          .,:t8@G1:.              ..;:..                      .,::.                 .,;L@@Li:,.     \n" +
                       "       .   ,;L@@L;,                                                                  .:18@G1:.      \n" +
                       "          .,;C@81:.                                                                  .,;C@01:.      \n" +
                       "```";
            n[ 62 ]  = "```" +
                       "  . ....,,,,,,,,:::::;;;ii1tL8@G;,.                                 :,.:t@@Ct11i;;:::::,,,,,,,,,,,,.\n" +
                       ".,,,,:::;;;ii11tffLCCGG0888@@@01:,              ..,...              . .,;C@@@88800GCCLfft11ii;;;::,,\n" +
                       "i1tfLCCG00888@@@@@8880GGCCLf1ti,.              ..10C:..                ..:11tLLCCG0088@@@@@@8800GCLf\n" +
                       "8@@@88800GCLLftt1ii;;;:::,,,.,,               ..10tCG:..                 .,,.,,::::;;ii11tffLCGG0080\n" +
                       "111ii;;::::,,,,,...........  ..              ..i01,:LC:..                 .. ...........,,,,,,::::::\n" +
                       "..............                     ..       ..iG1,..,fC:..                                 .........\n" +
                       "           .                       .       ..iG1,....,LC,..                                         \n" +
                       "                                 .      ....i01,.    .,LC,..                                        \n" +
                       "                                        ...i01..     ..,LC,..           ...                         \n" +
                       "                           .......,,::;;i1tGi..       ..,fC1ii;::,,........  ..                  .  \n" +
                       "                        .:ii1ttfffffttt11i;,....        ..:i111ttfffffttt1i;:.                      \n" +
                       "                        .:LGLi;;:,,....                  .     ....,,:;;tCGt,         .             \n" +
                       "                         ..:tt;....  .                           .....,1f1:.                        \n" +
                       ",.                        ...:tfi.....                           ...:1f1,..                       . \n" +
                       ":,,,,..                     ...:tfi,...                        ...:1f1,...                       .  \n" +
                       "GL1;:,:..                     ...:1fi,..                       ,:tfi,..                           ..\n" +
                       "0@8Cti::,..                      ..:tL1..                    .,ffi,...                         ..,,:\n" +
                       "iL0@8Gti;:,..                     ...tG:.                    .10;...                         .,,:ifC\n" +
                       ",;if0@@Gti::,...                   .,Cf..                   ..:G1..                       ..,:;1L8@8\n" +
                       "..,:if0@@Gfi;::,. :,              ..:01..                    ..LL,.                     .,,:;1C8@8Ct\n" +
                       "  ..,:if0@@Gfii:....              ..1G:..        ....        ..10:..                    ,;itC8@8C1;:\n" +
                       "    ..,:ifG@@GCt,,..            ...,CL,..    ..:1tLft;,.   ....:01..                  ..;LG8@8C1;:,.\n" +
                       "      ..,:ifG@@01;,.           .  .:01,....:itLLti::1fLf1;,....,CL,...               .,iC@@8C1;:,.  \n" +
                       "        .,,;1C@@Gi,.             ..1G:,:itLLti:..    .,;1fLf1;,,1G:..                .:f@@0fi:,,.   \n" +
                       "         .,:iG@8fi:.             .,CGfLLf1:..            .,;tfLff81..                .:1G@8f;:,.    \n" +
                       "          ,:10@0t:,.             .,LL1;,.                    .:itC1.                 .,;L@@Li:,.    \n" +
                       "       .  ,;f8@Ci,.                ..                            ..                   .:t8@G1:.     \n" +
                       "          ,;L@8t:,                                               .                    .,iG@01:.     \n" +
                       "```";
            n[ 63 ]  = "```" +
                       ".....,,,,,,,::::;;;ii11tffLC8@0i,.              .. ....             ,: ,:f@@GCLft11ii;;;::::,,,,,,,,\n" +
                       ",,::;iii11tffLCCG00888@@@@@800t:,              ..:Lt,..                .,;L088@@@@88800GGCLLftt1ii;:\n" +
                       "LCG0088@@@@@@@8800GCCLftt11;;;..              ..:GG0L,..                 .,;;i11tffLCGG0088@@@@@@880\n" +
                       "0000GCCLftt11ii;;:::,,,,,...,,               ..:GC;iGf,..                 .,...,,,,,,:::;;ii11tffLLL\n" +
                       "::::::,,,,,,............                    ..:CL:,,;Gf,..                .   ..  .........,,,,,,,,,\n" +
                       "..........                         .       ..:CL:....;Gf,..                                       ..\n" +
                       "                                   .      ..:CL:..  ..;Gf,.                                         \n" +
                       "                                 .      ...:GC:..    ..;Gf,...                                      \n" +
                       "                         .           .....:CL,..      ..;Gf,....        .. ..                       \n" +
                       "                         .....,,::;;ii11tfLL,..        ..;CLft11ii;::,,...... ..                 .. \n" +
                       "                       ,ittffffffftt11i;;:,.. ..         ..,;;ii11ttffffffttti,                     \n" +
                       "                       .;LGf;:,,.....                    .        ....,,:iLGf:.       ..            \n" +
                       "                        ..;tfi,.......                           . ....:1f1:.                       \n" +
                       ".                        ...;tfi,....                             ...:1f1:...                       \n" +
                       ",.....                     ...:tfi,....                         ...:tL1,...                         \n" +
                       "i;::,,,.                      ..:tfi,..                        .,:tf1:...                           \n" +
                       "@0L1;:,,..                     ...:tft...                     .:ff1,..                            .,\n" +
                       "C8@8C1;:,,..                     ...;01..                     ,LG:...                          .,,:;\n" +
                       ";1C8@8Ct;:,,...                   ..10:..                    ..1G:..                        ..,:itC8\n" +
                       ",:;1C8@8Cti::,,  .;               .,LC,..                     .:01..                      ,,,;if0@@0\n" +
                       " .,:;1L8@8Cti;:.  .              ..:0t..                      .,CL,..                    .;;if0@@Gfi\n" +
                       "   .,:;1L8@8CL1,.. .            ...10;..      ..,;1i:..   .   ..t0:..                  ..:fC0@@Gfi:,\n" +
                       "     .,:;1L0@@01;,.            . .,LC,... ..,;1LLfttLLfi:..  ...;01...                .,;f8@@Gfi;,..\n" +
                       "       .,:;1C@@Gi:,              .:0t,..,;1fLLt;,....:itLLti:...,CC,..                .:f@@8L1;:,.  \n" +
                       "        .,:iC@@C1:.             ..10i;1fLLti,..        ..:1fLLti:t0:..                .:t0@8t;:,.   \n" +
                       "         ,:10@8f;,.             .,L8CCf1:..                .,;1LCC81..                .,;C@@Li:,.   \n" +
                       "       . .:t8@01:.               .i1:..                       ..,;1:..                 ,:f@@Ci:.    \n" +
                       "         ,:f@@L:,.                 .                              .                    .,i0@0i,.    \n" +
                       "```";
            n[ 64 ]  = "```" +
                       ".,,,,,:::::;;;ii11tffLCCG008@8t:.               .,1i,..             .: .,;L@@80GGCLfft11ii;;;::::,,,\n" +
                       ":;i1ttffLCCG00888@@@@@88800CCt:..             ..,f88t,..                .,;fCG0088@@@@@@8800GGCLLft1\n" +
                       "08@@@@@@@8800GCCLfft11ii;;:::.                .,f01t01...                  ,:::;;ii11tfLLCGG0088@@@8\n" +
                       "LLLfft11ii;;:::,,,,,,,........               .,f0i,,101...                 ....,,..,,,,,,::::;;ii111\n" +
                       ",,,,,,,.,...........                        .,f0i,..,101..                             .............\n" +
                       "...    .                           .       .,f0i,.....101..                                         \n" +
                       "                                   .     ..,f0i,.    ..101..                                       .\n" +
                       "                           .     .      ..,f0i..      ..101...                                      \n" +
                       "                        .   ............,:f0i..        ..101,,......     .   .                      \n" +
                       "                       ...,,::;;i11ttffffLL;..          ..1Lffffftt1ii;:::,,.. ..                 . \n" +
                       "                      :tLLLLLfftt11i;;::,.... ..          ...,::;;i11ttffLLfLLt:.                   \n" +
                       "                      .iLCt:,,....                       .          .....,;fGL;.      ..            \n" +
                       "                       ..;ffi,....                                  ....:1Lt:..                     \n" +
                       "                        ...;tfi,...                                ...:tLt:...                      \n" +
                       "..   .                    ...;tfi,... .                          ...:tLt:...                        \n" +
                       ",,,,.,.                     ...:tf1,...                         ..:tLt:...                          \n" +
                       "L1;::,...                     ...:tft,..                      ..;fLt,...                           .\n" +
                       "@@0f1;:,,..                     ...;GL,.                      .:GL:...                          ..,,\n" +
                       "fG@@0L1;:,,...                   ..:Gf,.                      .,CL,..                         .,,:i1\n" +
                       ":ifG@@0L1;:,,,.  ,:              ..i8i..                      ..t0;..                      .,,:;tC8@\n" +
                       ".,:ifG@@0L1;;:.. ..              .,LG:..            ..        ..;01..                     .:;itG8@8C\n" +
                       "  .,:itG8@0Lf1,... .            ..:0f,..        ..:,..        ..,CC,..                   .:tfG8@8C1;\n" +
                       "    .,:itG8@8Gi:,..            . .18i...    ..:ifLLLft;,..  .....t0;..                 .,:t0@@8C1;:,\n" +
                       "      .,:itG@@G1:,.             .,LG:.....,itLCL1;,,;tLLf1;,.....;0t..                 .:t8@8Ct;:,..\n" +
                       "       .,:iL@@G1:.              .:0f,,,;tLCLt;,..    ..,itLLf1:,.:CC,..                .:t8@0ti:,.. \n" +
                       "        ,:iG@@Li:.             ..18ftLCLti,..            ..:ifLCf1L0;..                .:iG@@Li:,.  \n" +
                       "      ...:10@8t:.              ..f0Cfi:..                    ..:1fC81.                  ,;L@@Ci:.   \n" +
                       "        .:t8@C;,.               .,:...                          ..,:..                  .:10@Gi,.   \n" +
                       "```";
            n[ 65 ]  = "```" +
                       ",,,,:;;;;ii11tffLCCG00888@@@8f:,              ...180i,..             :  .,;C@@@@8800GGCLLftt1iii;:::\n" +
                       "1tfLCGG00888@@@@@@8800GCCLfti,.               ..10LC0i..                  .:1tfLCGG0088@@@@@@8880GCL\n" +
                       "@@@@8800GGCLLft11ii;;;::,,,,.  .             ..i0f::L0;...                  ,,,,,::;;;ii1ttfLLCGG000\n" +
                       "111ii;;;:::,,,,,,,..........                ..i0f:,.,L0;..                   ..........,,,,,,,::::::\n" +
                       ",............                              ..i0f,....,L0;..                                .........\n" +
                       "                                   .      ..i0f,..  ..,L0;..                                        \n" +
                       "                                   .     ..i0f,.     ..,L0;..                                      .\n" +
                       "                           .    ..    ....i0f,.       ..,L0;...       .                             \n" +
                       "                       .  ..........,,::;t0f,.         ..,L01;:,,........... ...                    \n" +
                       "                    ...,:;;ii11tffffLLfffti,..           .,1tfffLLffftt11i;;::,.,,.               . \n" +
                       "                    .iCGLLLfft1ii;::,,... . . ..           . ...,,::;ii1ttfLLCGCi.                  \n" +
                       "                    .,iLC1,.....                         .            .....:tCLi..     .            \n" +
                       "                     ..,ifLi,....                                    ....,1Lf;,.                    \n" +
                       "                       ...;fLi,...                                  ...,1Lf;...                     \n" +
                       "                         ...;fL1,.... .                          ....:1Lt:...                       \n" +
                       ",.  .,                     ...;tL1,.. .                          ..:tLt:...                         \n" +
                       ";:,,,...                     ...:fLt:..                        ..;fLt:...                           \n" +
                       "0Lti::,,.                     ....;LG:..                       .;0f:...                           ..\n" +
                       "8@@Gfi;:,,..                    ..,fG:,.                       .;0t,..                         ..,,:\n" +
                       "1L0@@Gfi;:,,..   :.              .:0f,..                       .,CG,..                      ..,,:itL\n" +
                       ":;1L0@@Gfi;::.   ,.             ..i81..                        ..t8;..                     .::;1L0@@\n" +
                       ".,,;1L0@@0fti,...               .,L0:..          .....         ..;0t..                     ,1tL0@@Gf\n" +
                       "  ..,:iL0@@8Gi,,..             ..:0L,..      ..,;1fti:...      ..,CG,..                 .,,108@@Gfi:\n" +
                       "    .,::iL0@@G1:,.             ..181,... ...:1fCCf11fCLti,.......,t8;..                 .:t0@@Gfi:,,\n" +
                       "      .,:if8@8t:,.             .,L0;....:ifCCL1:.....,;tLCLt;,....;8t..                 .:f8@0ti:,,.\n" +
                       "       ,:iC@@C1:.             ..:GL::itLCLt;,..         .,;tLCLt;::CC,..                .:1G@8fi:,. \n" +
                       "      ..:1G@@f;,              ..18CCCLti,..                ..,itLCLG8i..   .            .,;L@@Ci:.  \n" +
                       "       .:10@0i,.              ..iGf1:..                        ..:1LGi..                 ,:18@Gi,.  \n" +
                       "```";
            n[ 66 ]  = "```" +
                       ",::;i111tffLCCG00888@@@@@@80L;,.              ..:GCGG:..             ,.  .,;L08@@@@@@8880GGCLLftt1i;\n" +
                       "LC0088@@@@@@@8800GGCLLftt1i:,.               ..:GGi;GG:..                  .,;i1ttfLLCGG0888@@@@@@80\n" +
                       "000GGCLLftt11ii;;:::,:,,,....               ..:GG;,.;GG:..                   ....,,,,:::;;ii11ttfLLL\n" +
                       ":::::::,,,,,,.........                     ..:GG;,...:GG:..                    .............,,,,,,,,\n" +
                       "..........                                ..:GG;,.  ..:GG:..                  .                 ....\n" +
                       "                                   .     ..:GG;,.    ..:GG:..                                       \n" +
                       "                                   .   ...:GG;..      ..:GC:.. .                                   .\n" +
                       "                           ...   .   ....:GG;..        ..:GC:.....    .        .                    \n" +
                       "                     .  ........,,::;ii1tGG:..          ..:GGt1i;;::,,...........  ..               \n" +
                       "                   .,:;i11ttffLLLLLLfftt1;,..            ..,;1ttffLLLLLffftt1ii;,,:.              . \n" +
                       "                  .,f00Lfft1i;;:,,.....       ..           .   .....,,:;;i1tffL00f:                 \n" +
                       "                   .,1LLi,......                         .             .....,1LL1,.    .            \n" +
                       "                    ..,iLLi,....                                       ...,1LLi,.                   \n" +
                       "                      ..,iLLi,...                                     ..,1Lfi,..                    \n" +
                       "                        ...;fL1,....  .                           ....,1Lf;...                      \n" +
                       ".   ..                    ...;fL1,... .                           ..,tLf;...                        \n" +
                       ",,....                      ...;fL1;..                           .;tLf;...                          \n" +
                       "1;;:,,..                      ...;LGi..                         .i0L;...                        .   \n" +
                       "@8Cti::,...                    ...i8t,.                        .,t8i...                         ...,\n" +
                       "C8@8Cti;:,,.    ,:              .,L0:,.                        ..:0f,..                      ...,,:;\n" +
                       ";tC8@8Gti;::.   .,              .:0C,..                         .,CG:..                     .,::itC0\n" +
                       ",:;tC8@@Gt1i,...               .,i81..          .   ...         ..t8i..                     ,i1fG@@8\n" +
                       " .,:;1C8@80Ci,,..              .,L0;..         ..,;:.. . .      ..;8f,.                   .,iC0@@8L1\n" +
                       "   .,:;1C8@@G1:,.             ..:0L,.. . ....,;tLCCCCL1:....... ..,CG:..                 .:1G@@0Ct;:\n" +
                       "     .,:if8@@L;,.             ..181,.....,;1LCCf1:,,:1fCCfi:......,t8i..                 .:L@@0fi:,,\n" +
                       "      .:;L@@0t;,              .,L0;,..:1fCCL1;,..    ..,;1LCCfi:...;8f,..                .:t0@8fi:,.\n" +
                       "      ,,iC@@C;,.              .:GCi1fCCLt;,..            ..,;tLCLti;CG:.                 .,;C@@Ci,. \n" +
                       "      .,iG@8t:.              ..i8GGCfi,..                    ..,ifCGG8i..                .,:t8@C;,. \n" +
                       "```";
            n[ 67 ]  = "```" +
                       ";i1tffLCCGG0888@@@@@@880GCL1:..              ..,f0;;0L,..                 ..:1LCG088@@@@@@8880GGCLft\n" +
                       "08@@@@@@88800GCCLfft1ii;:,.                 ..,L81,,i8L,..                   ..,:;ii1tffLCCG00888@@8\n" +
                       "LLLfft11ii;;::::,,,,,,...                  ..,L81,...i8L,..                      ....,,,,::::;;ii111\n" +
                       ",,,,,,,,,..........                       ..,L81,.  ..i8L,..                    .      .............\n" +
                       "..   .  .                                ..,L81,.    ..i8L,..                                       \n" +
                       "                                   .    ..,L81,.      ..i8L,..                                      \n" +
                       "                                  ..  ...,L81,.        ..i8L,... .                                  \n" +
                       "                          ..............:L81,.          ..i8L,.........     .. ...                  \n" +
                       "                    ........,,::;i11ttfLC01,..           ..iGCLftt11i;::,,.........  .              \n" +
                       "                 .:;i1tffLLLLLLLLfft11ii:,..              ..,:;i1ttffLLLLLLLLfft1i;;,.            ..\n" +
                       "                 .iG8Gft1i;::,.......         ..                 .......,,:;i1tfG8Gi.               \n" +
                       "                 ..:1LLi,.....                           .               ....,iLC1:..  ..           \n" +
                       "                   ..,1LLi,....                                         ...,iLC1,..                 \n" +
                       "                    ...,iLLi,...                                      ...,iLC1,....                 \n" +
                       "                      ...,iLLi,....   .                             ...,1LLi,...                    \n" +
                       "    ..                  ...,iLL1,...  .                           ...:1LLi,...                      \n" +
                       ",.                        ....;LL1;.                              .;1CLi,...                        \n" +
                       ":,,,....                     ...iLG1..                          ..1GLi,...                          \n" +
                       "Cti;:,,..                     ...:0C:..                         .:C0;...                           .\n" +
                       "@@0L1;:,,...    :.             ..181,,.                         ..i81,..                       ...,,\n" +
                       "fG@@8Cti:::.    :.             .,L8;..                           .:0L,..                     .,,,:;1\n" +
                       ":ifG@@8Ctii,...                .:0C,..          .   .            .,C0:..                    .,;itC8@\n" +
                       ".,:ifG@@8GLi,,...             ..18t,.            .......         ..181..                   ..iLG8@@G\n" +
                       "  .,:itG8@@Ci;,.              .,L0;..    .  ...,;tLL1;,... ...   ..;0L,.                  .:iC@@8Gfi\n" +
                       "   ..,:if8@@L;,.             ..:0C,...   ..,;tLGCf11fCGL1;,........,L0:..                 ,;L@@0L1;:\n" +
                       "     .,;t8@8fi,.             ..i8t,....,;1LGCL1:......:1LCCL1:.....,181..                 .;f8@8fi:,\n" +
                       "     .:;L@@Gi:.              .,f0;,,:1fCCLt;,..        ..,;1LCCfi:,,;0L,.                 .:iG@@Li,.\n" +
                       "     .,;C@@f:,               .,GC:1LGCfi,..                ..,;tCGL1:LG,.                 .,:f@@C;,.\n" +
                       "```";
            n[ 68 ]  = "```" +
                       "CG088@@@@@@@880GCLf1;:..                    ..;0L,...t81,..   .                  ..,;1tLCG008@@@@@@8\n" +
                       "0000GCCLfft1ii;:,,.                       ...i0G;.. .,L81,..                          ..,:;ii1tffLLL\n" +
                       "::;;:::,,,,,....    ..                    .,i8G;..   .,L81,..                            .....,,,,,,\n" +
                       "..........                               .,i0G;,.     .:L8t,..                  .                ...\n" +
                       "                                        .,i0G;,.      ..:L81,..                                     \n" +
                       "                                  ..  ..,i0G;,..       ..,L81,...                                   \n" +
                       "                               .. ......i8G;..          ..,L81,....    ...                          \n" +
                       "                ..  .............,,::;it8G;..            ..,L8fi;::,,.................              \n" +
                       "                ..,,::;;i11tffLLCCCCCLLft:..              ..,1fLCLCCCCLLfft11i;::,,,......          \n" +
                       "              ,1LCCCGCCCLLftt1i;;:,,......                  .....,,,::;i11tffLLCCCCCCLf;.          .\n" +
                       "              .;L8Gt;::,......          .     ..                         ......,:;1G8Gi..           \n" +
                       "               .,;fCf;,.....                             .                 ....,;fGLi,...           \n" +
                       "                ...:fCL;,...                                              ....;fGL;,..              \n" +
                       "                  ...:tCL;,...                                           ...;fGf;,...               \n" +
                       "                    ...:tCLi,...                                       ...;fGf;...                  \n" +
                       "   ..                 ...:tCLi,...    .                              ,..;LGf:...                    \n" +
                       "                        ...,1CLi,,                                  .,;LGf:...                      \n" +
                       "..     .                  ...,1CCt...                             ..iCGf:...                        \n" +
                       ",,,...                      ...:L81,.                            ..;8G;,..  .                    .  \n" +
                       "f1;::,....     ,.           ...:C0;,..                            .:GG;..                          .\n" +
                       "@8Gf1;:,,.     :,            ..;8C,..                             ..f81,..                      ...,\n" +
                       "C8@@0L1;;:.                  ..t@t,.                              ..i8L,..                     ,:::;\n" +
                       ";1C8@@0Lfi,....              .,C8;..                 ...          ..:G0:..                    .;tfC0\n" +
                       ",:;1C8@@8C;:,..             ..;8C:..     .     .........     .     .,f@1..                  .,:L8@@8\n" +
                       " .,:;1C8@@Ci:,.             ..t@t,.      .. ...:ifCCfi:.... .      ..i8L,..                 ,;L@@8Cf\n" +
                       "   .,;1G@@01:.              .,C8;,.      ..,ifCGGL11fCGCfi,... .   ..:G0:..                 ,;C@@0ti\n" +
                       "    ,;t0@8f;,.              .;0C,.    ..;tLGGL1;......:1LGGLt;,..   .,f8i...                ,:t8@8f;\n" +
                       "    ,:t8@0i:.               .i81..  .,tCGCt;,..        ..,;tCGCt:.. ..;8t...                .,;G@@f;\n" +
                       "```";
            n[ 69 ]  = "```" +
                       "8@@@@@@8800GCLt1;,.                        ..:GG:.. ..L0i,..  .                       .,:itfLCG088@8\n" +
                       "LLLLfft1i;::,..                          ...;G8i..  ..:G8i,..                             ..,,:;ii11\n" +
                       ",,,,,,,.....        ..                   ..;G81,.     .:G81,..                                ......\n" +
                       ".... .                                  ..;G81,.      ..:G81,..                 ..                  \n" +
                       "                                      ...;G81,.        ..:G8i,..                                    \n" +
                       "                                  .. ...;G81,.          ..;G81,...                                  \n" +
                       "                        . .............;G81,.            ..:G8i,.......     ..  .                   \n" +
                       "               .............,,::;ii1tfL08i,.              ..:G8Lft1ii;::,,..............   .        \n" +
                       "            ...,:;ii11tffLLCCCCCCCLLffti:..                ..,;1tffLLCCCCCCLLfft1ii;;:,.,,.         \n" +
                       "            .iC0GGGCCLfft1i;;:,,......  .                     .  .....,,::;i11tfLLCCGG0Gf,         .\n" +
                       "            .,1C0Li:,,.....                   ..                            .....,,;f0Gt:.          \n" +
                       "             ..,iLGf;,.....                              .                   ....:tGC1:...          \n" +
                       "               ..,;LGf;....                                                 ...:tGC1,...            \n" +
                       "               .....;fGf;....                                             ...:tGCi,...              \n" +
                       "                   ...;fGL;....                                         ...:fGCi,...                \n" +
                       "   ..                ...:fGL;,...     .                              .,..:fGLi,...                  \n" +
                       "                       ...:fGL;,,.                                   .,:fGLi,...                    \n" +
                       "                         ...:tGLt...                               ..iLGLi,...                      \n" +
                       ",. ...                     ...:L8t,..                              .i8Gi,....                       \n" +
                       ";:,,,...       .            ..,t8t:...                            .,i0C:...                         \n" +
                       "0Lti::,,..    .;            ..:G8;..                              ..,C8i,.                       ...\n" +
                       "@@8Gfi;::.                  ..i8C:..                               ..t@t,..                     .,,,\n" +
                       "tG8@@Gfti,... .             .,f@t,.                    .           ..i8C:..                    .:iit\n" +
                       ":itC8@@0C;,,..              .:G8i..      .       .  ....     .      .:C8;..                  .,:f08@\n" +
                       ".,:itC8@@G1:,.              .;8C:..      ..   ...:ii:......         .,t@t,.                 .,;L@@@0\n" +
                       "  ,,;1C@@8t:.              ..t@t,.        ...:ifGGGGGGL1:.....      ..;8C,..                 ,iG@@Gt\n" +
                       "   ,:1G@@Ci:.              .,C8;..      .,;tCGGL1;,,:1fGGCfi,...     .,C8;..                 ,;f8@0t\n" +
                       "   .:10@8t:,.              .:0L,.     .:tC0Cf;,... .....;tLGGf;..    ..18i..                 .:i0@8f\n" +
                       "```";
            n[ 70 ]  = "```" +
                       "@@@880GCLfti:,..                          ..:L8i..   .,C0;,.. .                           ..:;1tLCGG\n" +
                       "1111i;::,..                              ..:L8f,.    ..;00i,..                                 ..,,,\n" +
                       ",......             .                   ..:C@f:.       .;08i,..                                     \n" +
                       "                                       ..:C@f:.        .,i00i,..                ..                  \n" +
                       "                                      ..:C@f:.          ..i00i,..                                   \n" +
                       "                                  .,...:C@f:..           ..i00i.......                              \n" +
                       "              .    .................,,;C@f,..             ..;08i,,..................   .            \n" +
                       "             ..........,,,:;;i1ttfLLCCG0t,..               .,iGGGCLLfft1ii;::,,...........  ..      \n" +
                       "          .,,:;1ttffLLCCCGGCCCLLft11i;:,..                  ..,,;ii1ttfLLCCCCCCCLLfft11i;::,.       \n" +
                       "          .:L88GCCLft1i;;:,,........                               .......,,::;i11tfLCG88G1.        \n" +
                       "           .:tGGf;,.......                    ..                              .....,:1CGLi..        \n" +
                       "            ..:1CGt:,....                                .                    ....,1CGf;,.          \n" +
                       "             ...,1CGt:....                                                   ...,1CGf:...           \n" +
                       "              ....,iCGf:....                                              ....,1CGt:...             \n" +
                       "                 ...,iLGf:....                                           ...,1GGt:...               \n" +
                       "   .                ..,iLGf;....      .                               ,...,tGGt:...                 \n" +
                       "                    ....,;LGf;,,.                                     .,:tGG1,...                   \n" +
                       "                        ..,;LGL1,..                                 ..ifGG1,...                     \n" +
                       ".                         ..,;L8f,..                                .i0G1:...                       \n" +
                       ",,.....       .            ..,i8C;. ..                             .,t@L:...                        \n" +
                       "1i;:,,,..     ;:            .,L@1,..                               ..:00;..                         \n" +
                       "@0Cti:::.                   .;G8;..                                 .,L@1,..                     .,,\n" +
                       "G@@8Gf1i,...  .            ..18C:..                    .            ..1@L,..                    .:;:\n" +
                       "ifG@@80Ci,,..              .,L@t,.       .       .   ...             .;00;..                  ..,tCG\n" +
                       ",:ifG@@@C1;,.              .:G0;..       .      .........            .,L@1...                .,;f8@@\n" +
                       ".,:;1C8@@L;,.             ..i8C:..        .. ..:ifCGf1:.....         ..1@L,..                .,10@@G\n" +
                       "  ,:iC@@0t:.              .,f@t,.         .,;tC0GCttLG0Gfi,...       ..:0G:..                 ,;L@@0\n" +
                       "  .,iG@@L;,.              .,C0:.        .:tG0Gfi,.....:1C0GL;..      ...f8;..                .,:18@8\n" +
                       "```";
            n[ 71 ]  = "```" +
                       "0GGCLf1i:..                              ..,t8f,.     .:0G;,...                               ..,:ii\n" +
                       ":::,,.                                  ..,f@C:.      ..i80;,..                                     \n" +
                       ".                   .                  ..:f@C;..       ..180i,..                                    \n" +
                       "                                      ..:f@C;,.         .,180i,..                .                  \n" +
                       "                                   ....:f@C;..           .,180i,..                                  \n" +
                       "                            . ........,f@C;..             .,180;............                        \n" +
                       "            .   ................,,::;iL@C:..               .,1801;::,,.....................         \n" +
                       "           ......,,,::;;i1ttfLLCCGGGGGCf:..                 .,iLCGGGGCCCLfft1i;;:,,,.......  ..     \n" +
                       "         .;i1tLLCCCGGGGGCCLfft1ii;::,....                     ...,,:;;i11tfLLCCGGGGCCCLLftiii,      \n" +
                       "        ..t8@0Cft1ii;:,,.......        .                       . .      .......,,:;i1tfLG@@C;.      \n" +
                       "         ..;fGC1:,......                      ..                                .....,iL0C1,.       \n" +
                       "           .,;tGG1:.....                                 .                      ...,iL0Ci:..        \n" +
                       "            ...:tGG1:....                                                     ...,;L0Li,...         \n" +
                       "              ...:1GGt:....                                                 ...,iL0L;,..            \n" +
                       "                ...,1GGt:....                                             ...,iC0L;,..              \n" +
                       "  ..              ...,1CGt:....                                       .....,iC0L;...                \n" +
                       "                    ...,1CGt:.,.                                       ..,iC0f;...                  \n" +
                       "                      ...,iCGfi,..                                   ..;1C0f;...                    \n" +
                       "                         ..,iC0f,..                                 ..iG0f;,..                      \n" +
                       ",.   .                    ..,;00i..  .                              .,f@f,,..                       \n" +
                       ":,,,,...     .i            .,t@L:,.                                 .,;8G;..                        \n" +
                       "Gf1;::,.                   .,L@t,.                                   .:G8i,.                      .,\n" +
                       "@@8Ctii:...               ..;00;..                                   .,f@f,..                     ,:\n" +
                       "L0@@8GLi,,..              ..1@C:..                    ..             ..i8G:..                  . .if\n" +
                       ";1L0@@@Gi;,.              .,L@t,.        .      .   ....              .:G8i..                 .,:t0@\n" +
                       ",:;1C8@@Ci,.             ..:00;..             ...,i1:.....            .,f@t,..                .:18@@\n" +
                       " .:;f8@8f;.              ..1@L:.            .,;tC00GGGL1:..            .i8C:..                .,iG@@\n" +
                       " .,;L@@Gi:,.             ..f8i..          .:tG0Gfi::;1C00Li.         . .,C0:.                 .,:t@@\n" +
                       "```";
            n[ 72 ]  = "```" +
                       "tti;:..                                  .,18C:.       .;8C;...                                     \n" +
                       ".                                      ..,t80;..       ..18G;...                                    \n" +
                       "                    .                 ..,t@0i,.         .,t@G;,..                                   \n" +
                       "                                      .,t80i,.           .,t8G;,..               .                  \n" +
                       "                                  ....,t801,.             .,t@G;...                                 \n" +
                       "                      ..  ...........,t80i,.              ..,t@G;.............. ..                  \n" +
                       "          .. ..............,,,:;;i1tfC@G;..                ..,t@0ft1ii;::,,..................       \n" +
                       "         ...,,,::;;i1ttfLLCCGGGGGGCCLf1;,.                   .,itfCCCGGGGGGCCLfft1ii;::,,,,.. ...   \n" +
                       "       .itfLCGGGGGGGCCLfft1i;;:,,.......                       .......,::;ii1tfLLCCGGGGGGGCLfti.    \n" +
                       "       .;C@8Ct1i;:,,........                                     ..       . .......,,:;i1f0@8f,.    \n" +
                       "       ..,iL0Ci,,.......                      ..                                  ....,:tGGf;..     \n" +
                       "         ..,iL0Ci,....                                   .                       ....:t0Gt:,..      \n" +
                       "          ...,;fGCi,....                                                        ...:f0Gt:...        \n" +
                       "             ..,;f0C1,....                                                    ...:f0G1:...          \n" +
                       "               ...:fGC1,....                                                ..,;f0G1,...            \n" +
                       "  ..             ...:fGG1,.....                                       .   ..,;L0C1,...              \n" +
                       "                   ...:tGG1:...                                         ..,;L0C1,...                \n" +
                       "                    ....:tGGt;,..                                      .:iL0Ci,...                  \n" +
                       "                       ...:tG0f,..                                   ..;G0C1,...                    \n" +
                       ".                        ..,;G81,.   .                               .,C@f:,..                      \n" +
                       ",,.....      ;:           .,i8G;,.                                   .,1@C:..                       \n" +
                       "1;::,,.                   .,f@L:..                                   ..;08i,..                     .\n" +
                       "@0L1i;:..                 .:C@1,..                                   ..,C@t,..                    .,\n" +
                       "0@@8CLi,...              ..i80;..                                     .,t@C:..                    .;\n" +
                       "1L0@@@Gi;,..             .,t@L:..        .           ..                .;08;..                  .,iG\n" +
                       ":;tC8@@G1:.              .,C@1,.                ........               .,C@1,..                .,10@\n" +
                       ".,;t0@@Ci,              ..;00;.               .,;tCGfi:...             ..1@L:.                 .,iG@\n" +
                       ".,:t8@81:,.             ..1@f,.             .:tG0GLfC00Ci,.             .:00:.                  ,:t@\n" +
                       "```";
            n[ 73 ]  = "```" +
                       ".                                      ..,i00;..        .i8C:,..                                    \n" +
                       "                                      ..,1081,.         .,t@C;...                                   \n" +
                       "                    .                 .,188t,.           .,f@G;,...                                 \n" +
                       "                                     .,188t:.             .:f@G;,..        .     .                  \n" +
                       "                                 ....,188t,.              ..:f@G;........  .                        \n" +
                       "                ..................,,:1881,.                ..,f@G;,...................              \n" +
                       "        ..............,,,:;;i1ttfLCCG801,.                  ..,f80GCLLft1ii;::,,...............   ..\n" +
                       "      ...,:;;ii1ttfLLCGGGGGGGCCLLftti::..                     .,:;1ttfLCCGGGGGGGCCLfft1ii;;::,..,.. \n" +
                       "     .;LGGG000GGCCLft11i;::,,......  ..                         .  .......,,::;i1ttfLCCGGG00GGCL;.  \n" +
                       "     .,t08Gfi;:,,.........                                       ..       .     ......,,:;1L8@Gi..  \n" +
                       "      ..:1C0L;,,.....                         ..                                  .....,:1C0Li,..   \n" +
                       "       ...:1C0L;,....                                    .                        ....,1G0Li,..     \n" +
                       "         ...,iC0Li,....                                                          ...,1G0L;,...      \n" +
                       "           ...,iC0Li,...                                                       ...,1G0L;,... .      \n" +
                       "              ..,iL0Ci,....                                                  ...:tG0f;,..           \n" +
                       "  .             ..,;L0Ci,....                                         .    ...:tG0f;...             \n" +
                       "                  ..,;L0Ci,...                                           ...:tG0f:...               \n" +
                       "                    ..,;L0C1::.                                         .:;tGGt:...                 \n" +
                       "                      ..,;f0Gf...                                     ..:L00f:,...                  \n" +
                       "                        ..,iG@t,..                                    .:C@L;,...                    \n" +
                       ",.          .i           ..;G81:.                                     .:f@L:...                     \n" +
                       ":,,,,..      .           .,1@G:,.                                     ..i80;..                      \n" +
                       "Cti;;:..                 .,L@L,..                                     ..:G@1,...                   .\n" +
                       "@@0Cf1,...              ..;081,.                                       .,f@L,..                    ,\n" +
                       "C8@@8Gi:,,.             ..1@G;..         .                             ..i80;..                  . :\n" +
                       ";tC8@@0t:,              .,f@L:.                      ..                 .:G@1,.                  .;G\n" +
                       ",;1G@@0t:.              .:G81,.                 .,;i:...                .,f@f,.                  .:C\n" +
                       ",:10@@L;,..             .;8G:.                .:tG080C1,.               ..;8C,.                  ..i\n" +
                       "```";
            n[ 74 ]  = "```" +
                       "                                       .,;G8i..         ..1@L:,..                                   \n" +
                       "                                      .,i0@f,.           .,L@C;...                                  \n" +
                       "                    .                .,i0@f:.             .:L@G;,..              .                  \n" +
                       "                                   ..,i0@L:.              ..:L@G;...       .     .                  \n" +
                       "                           .........,i0@f:.                ..:L@C;........... ..                    \n" +
                       "          .  ..................,,:;it0@f,..                 ..:L@G1;:,,.........................    \n" +
                       "      ..........,,,,:;;i1ttfLCCGGG00GGt:..                   ..:fG00GGGGCLLft1ii;::,,............  .\n" +
                       "    .,,;i11tffLCCGGG00GGGCCLfft1i;;:,....                      ...,:;;i1tffLCCGGG00GGGCLLftt1ii;,,:.\n" +
                       "   .:C88000GCCLft1i;;:,,..........                                   ..........,,:;ii1tfLCCG00080L:.\n" +
                       "   ..;L00Li:,,........                                            .       .       .......,,:1C80f:. \n" +
                       "     .,;f00f;,,....                           ..                                     ....,iL0Gt;,.  \n" +
                       "      ..,:t00f:,....                                     .                          ...,;L8Gt:,..   \n" +
                       "        ...:tG0f:,....                                                            ...,iL0G1:...     \n" +
                       "          ...:tG0f;,...                                                         ...,iL0G1,....      \n" +
                       "            ...:1G0f;,...                                                     ...,iC0C1,...         \n" +
                       "  .           ...,1G0L;,.....                                         .     ...,iC0Ci,...           \n" +
                       "                 ..,1C0L;,...                                             ...,iC0Ci,...             \n" +
                       "                  ...,iC0Li:,.                                           .,:1G0Ci,...               \n" +
                       "                     ..,iC0Ct...                                       ..,fG0Ci,..                  \n" +
                       "                      ...,1G@f,..                                      .,L@Ci,...                   \n" +
                       ".           ;,          ..:C@f:.                                       .:C@f:...                    \n" +
                       ",,....      ..          ..i88i,..                                      .,1@G;...                    \n" +
                       "i;:::.                  .,t@G:,.                                        .;081,..                    \n" +
                       "8GLti:....              .:C@f,..                                        .,C@f,..                    \n" +
                       "8@@8G1:,,.             ..;881,.          .                              .,t@G:..                    \n" +
                       "tC8@@0t;,.             .,t@G;..                                         ..;08i,.                    \n" +
                       ":1C@@@L:.              .:L@f,.                                           .,L@t,.                    \n" +
                       ":iC@@Gi:.              .:G8;..                  .,1f;,.                  ..i8C,.                    \n" +
                       "```";
            n[ 75 ]  = "```" +
                       "                                      ..;C@t,.            .t@L:,..                                  \n" +
                       "                                     ..;G@C:..            .,C@C;...                                 \n" +
                       "                    .              ..,;G@C;..             ..:C@C;,..              .                 \n" +
                       "                                 ....iG@C;..                .;C@C;....     .     ..                 \n" +
                       "                      .............,;G@C;..                 ..:C@C:,.............                   \n" +
                       "     ..  .................,,::;;i1tL0@L:..                   ..:C@Gft1i;;:,,,.....................  \n" +
                       "    ......,,,,::;;i1tffLCCGG0000GGGCft:..                     ..:tfCGGG000GGGCCLft11i;::,,,........ \n" +
                       "  ,:;1tfLLCGGG00000GGCLLft1ii;::,,.....                         .....,,::;i11tfLCCGG00000GGCCLfft1;i\n" +
                       " .,f8@8GGCLft1i;::,,.........       ..                           ..      ..........,,::;i1tfLCC08@8L\n" +
                       "  .,1C0Gt;,,....... .                                             .       .          ......,,;f00C1,\n" +
                       "   ..:1C8G1:,....                             ..                                      ....,:t08Li:. \n" +
                       "     ..,iL0G1:,...                                       .                           ...,:f00Li,..  \n" +
                       "      ...,;L0Gt:,....                                                               ...:f00L;,..   .\n" +
                       "        ...,;L0Gt:,...                                                            ..,:f00f;,...     \n" +
                       "          ...,;f00t:,...                                                        ..,;f00f;,..        \n" +
                       " ..          ..,;f00t:,....                                           ..     ...,;f00f:...          \n" +
                       "              ...,:f00f;,...                                               ...,;f00t:...            \n" +
                       "                 ...:t00f;,,..                                            .,,;L00t:...              \n" +
                       "                  ....:t00L1...                                          .,tL00t:,..                \n" +
                       "                     ...:t08t,..    .                                   .,f80t:,..                  \n" +
                       "           ,;         ...:L@G;..                                        .;G@f:,..                   \n" +
                       ",.         .,          ..;G@1,..                                        .,t@G;...                   \n" +
                       ":,,,,.                 ..188i,..                                        ..i88i,..                   \n" +
                       "Lt1i:...              ..,L@C:..                                          .:G@f,..                   \n" +
                       "@@0Gt:,,.             ..:G@f,..          .                               .,f@C:..                   \n" +
                       "G8@@8fi:.           . ..i88i,.                                           ..i88i,.                   \n" +
                       "iL8@@Gi,              .,f@C:.                                             .:G@t,.                   \n" +
                       ";f8@8f;,              .,C@1..                                             ..t@L,.                   \n" +
                       "```";
            n[ 76 ]  = "```" +
                       "                                     ..:L@L,.             ..f@L:,..                                 \n" +
                       "                           .        ..;C@G;..              .:C@C;,..                                \n" +
                       "                   .               ..;C@Gi..               ..;G@C;,..             .                 \n" +
                       "                            .. .....;C@Gi,.                 ..;G@C;.........   .  .                 \n" +
                       "               ..................,:;C@Gi..                   ..;C@C;,,..................            \n" +
                       "   . ...............,,,::;ii1tfLLCG0@G;,.                     ..;G@0GCLfft1i;;:,,,..................\n" +
                       "  ...,,,:::;ii1tffLCGG000000GGCCLLfi;:..                       ..:;ifLLCCGG00000GGCCLftt1i;;::,,,...\n" +
                       ",i1fLCGG0000000GCCLftt1i;::,,.........                           .........,,::;i1ttfLCCG0000000GGCLt\n" +
                       ".10@@GLft1i;::,,..........                                            .. ..  ...........,,:;i1tfLG@@\n" +
                       "..;fG0Ci:,......    .                                             .                   .......,:1C8Gf\n" +
                       "  .,;tG8Ci:,....                              ..                                        ..,,:iC8Gt;,\n" +
                       "   ...:tG8Ci,....                                        .                             ...:1C8Gt:,..\n" +
                       "     ...:1G8Ci,.....                                                                 ...,1C8G1:... .\n" +
                       "       ...,1C8C1,....                                                              ...,1C8G1,...    \n" +
                       "         ...,iC8G1,....                                                          ...:1G8C1,...      \n" +
                       " .         ...,iC8G1:,...                                             ..       ...:1G8Ci,....       \n" +
                       "             ...,iC0G1:,...                                                 ....:tG8Ci,...          \n" +
                       "               ...,iL0Gt:,,.                                               ..,:t08Ci,...            \n" +
                       "                  ..,;L0Gt1,..                                             .1f08Li,...              \n" +
                       "                   ...,;L88t,..     .                                    .,t88Ci,...                \n" +
                       "           ;.        ...:f@0i..                                          .;0@f:,..                  \n" +
                       ".          :.         ..:C@L:,..                                        ..:f@C;...                  \n" +
                       ",,,.                  ..i0@1,..                                          ..1@0i,..                  \n" +
                       "i;;:...               .,t@0i,..                                          ..;0@t,..                  \n" +
                       "8GCt:,...             .:C@C:..           .                                .,L@C:..                  \n" +
                       "8@@8fi:.            ...;0@t,.                                             ..t@0;,.                  \n" +
                       "f0@@8t:.             .,1@0;..                                              .;0@1,.                  \n" +
                       "1G@@Ci,.             .,f@f,.                                               .,f@f,.                  \n" +
                       "```";
            n[ 77 ]  = "```" +
                       "                                   ..:f80i..                .,C@L;,..                               \n" +
                       "                   .       .      ..:f@8t,.                 ..;0@C;,..                              \n" +
                       "                   .          .....:L@8f,.                   ..i0@C;,...          .                 \n" +
                       "                  . .............,:L@8t,.                     .,i0@C;,...............               \n" +
                       "      .................,,,::;;i1tfG@8t,..                      .,i0@Gft1i;;:,,,.....................\n" +
                       ".........,,,,:;;i11tfLCCGG00000000CL1,..                        .,ifCG00000000GCCLft11i;::,,,.......\n" +
                       ":;i1tffLCCGG0008000GGCLLft1ii;::,.....                            ....,::;;i1tffLCGG0000000GGCCLft1i\n" +
                       "008800GGCLft11i;::,,..........  .  .                               ..  ............,,,:;;i1tfLLCGG0G\n" +
                       "@0L1;::,..........  .                                                     .           ..........,,,,\n" +
                       "tG8Gt;,,......                                                    .                         .....,if\n" +
                       ",:1G8Gt:,....                                 ..                                          .,...:1G80\n" +
                       "...:1C8Gt:,.....                                         .                                .,.:1G8Gt:\n" +
                       "  ...,iC80t:,...                                                                        ...:1G8Gt:..\n" +
                       "    ...,iC80t;,...                                                                    ...:tG8G1:... \n" +
                       "      ...,iL80f;,...                                                                ..,:t08C1:...   \n" +
                       "..       ..,;L80f;,....                                                .          ..,:t08C1,...     \n" +
                       "          ...,;L80f;,....                                                     . ..,;f08Ci,...       \n" +
                       "            ...,;f08L;,...                                                    ..,;f08Ci,...         \n" +
                       "              ...,;f08Li;,.                                                  .:;f08Ci,...           \n" +
                       "                 ..,;f08Gi...                                              ..;C08Ci,...             \n" +
                       "         .:        ..,;L@81,.       .                                      .;0@G1:...               \n" +
                       "         .i.        ..,f@01,.  .                                           .iG@L:,..                \n" +
                       "..                  ..:C@C:,.                                              .,f@0i,..                \n" +
                       ":,.  .              ..i0@f:....                                            ..18@t,..                \n" +
                       "11;....             .,t@81,..            .                                 ..;G@L:...               \n" +
                       "@8Li:,.            ..:C@G;..                                                .,L@G;,..               \n" +
                       "8@@Gi,.            ..;0@f,.                                                 ..1881,.                \n" +
                       "8@@L;,             ..i80;..                                                 ..,C@t,.                \n" +
                       "```";
            n[ 78 ]  = "```" +
                       "                                  ..:t881,.                  .,G@L;,..                              \n" +
                       "                   .       .     ..:f8@f:.                   ..i0@C;,..                             \n" +
                       "                   .        ......:f@@L:.                     ..10@C;,........    ..                \n" +
                       "             ..................,,;f@8f:.                       .,10@C;,,....................        \n" +
                       "..  ..............,,,::;ii1tffLCG0@@f:..                        .,10@8GCLLft1i;;::,,,...............\n" +
                       "....,,,::;ii1tffLCGG000888000GCCLt1i,..                         ..,;1tLCCGG00888000GGCLfft1i;;::,,..\n" +
                       "tfLCCG000888000GCCLft11i;::,,........                              .......,,,:;;i1tfLLCGG00888000GCL\n" +
                       "CGGCLLft1i;::,,,...........     ..                                          ............,,,:;ii1tfff\n" +
                       "t;::,,........                                                            .               ..........\n" +
                       "88L1:,......                                                      .                           ......\n" +
                       ";f08Ci:,....                                  ..                                           .....,;fG\n" +
                       ".,;f08C1:,...                                            .                                 ...,iL88C\n" +
                       "...,:t08C1:,...                                                                          ...,iL88Li,\n" +
                       "   ..,:t08G1:,...                                                                      ...,iC88L;,..\n" +
                       "     ...:1G8G1:,...                  .                                               ...,iC80f;,.. .\n" +
                       ",      ...:1G8Gt:,....               .                                 .           ...,iC80f;,..    \n" +
                       "         ...:1G8Gt:,....                                                         ...:1C80f;,..      \n" +
                       "           ...:1C80t:,...                                                      ...:1G80f;,..        \n" +
                       "             ...,1C80t;:,.                                                   ..::1G80t:,..          \n" +
                       "               ...,iC80Ci...                                                ..:LG80t;,..            \n" +
                       "         ,.       ..,1C@0i,..       .                                       .:C@0f;,..              \n" +
                       "         i:       ...,t8@f:.                                               ..i0@L;,..               \n" +
                       ".                  ..:L@G;,.                                               ..:L@0i,..               \n" +
                       ",,                 ..;G@L:.. .                                              .,1@8t,..               \n" +
                       "i:. ...            ..18@t,..            ..                                  ..;0@L:..               \n" +
                       "0Li:,.            ..,f@0i,.                                                  .:L@G;,.               \n" +
                       "@@0t:.             .;G@C:.                                      .            ..t@81,.               \n" +
                       "@@01:.             .;081..                                                  ...:G@t,.               \n" +
                       "```";
            n[ 79 ]  = "```" +
                       "                                  .,t0@t,.                    .:G@L;,..                             \n" +
                       "                   .       .  ....,t8@C:..                    ..i8@C;....                           \n" +
                       "                   .    .........:f8@C;.                       ..18@C;,.......... ..                \n" +
                       "         .................,,,::;if8@L;..                        .,10@C1;::,,,................... .  \n" +
                       "............,,,,::;ii1tfLLCG0008888L;..                          .,10888000GCCLft1ii;::,,,,.........\n" +
                       "..,:;ii1tfLLCGG008888800GCCLftt1i::,..                            ..::;11tfLLCG000888800GGCLLft1ii:,\n" +
                       "CG08888800GGCLftt1i;::,,,........ ..                                ... .......,,::;ii1tfLCCG000880G\n" +
                       "ttt1ii;::,,...........   ..     .                                                 ...........,,::;;;\n" +
                       "........... ..                                                            .                   ......\n" +
                       "Ct;,,....                                                         .                             ....\n" +
                       "C80f;,,...                                    ..                                           ..  ...,:\n" +
                       ",iC88L;,....                                                                               ....,:tG8\n" +
                       "..,iL88Li,,..                                                                             ...,:t0@Gt\n" +
                       "  ..,iL88Li,....                                                                         ..,;t0@Gt:,\n" +
                       "   ...,;L88Li:,...                                                                    ...,;f08G1:...\n" +
                       ".     ..,;f88Ci:,...                 .                                 .            ...,;f08G1:...  \n" +
                       "       ...,;f08C1:,....                                                           ...,;f08C1:...    \n" +
                       "          ..,;f08C1:,...                                                        ...,;f88Ci:...      \n" +
                       "            ..,;f08C1:,,.                                                      .,:iL88Ci,...        \n" +
                       "              ..,:t08Gfi....                                                 ..,tC88Ci,...          \n" +
                       "        ..      ..,;t0@Gi,..        .                                        .:L@8C1:...            \n" +
                       "        :i       ...,18@L:..                                                ..18@L;,...             \n" +
                       "                  ..,f@81:.                                                  .:L@Gi,..              \n" +
                       ",.                ..:C@G;,.  .                                               .,t@81,..              \n" +
                       ":,                ..i8@L:..             ..                                   ..i8@f:..              \n" +
                       "fi.,..            .,t@8t,.                                                    .:C@G;,.              \n" +
                       "@0t:.             .:C@G;..                                      .             .,f@81,.              \n" +
                       "@@L:.             .:G@f,.                                                   ....;0@t..              \n" +
                       "```";
            n[ 80 ]  = "```" +
                       "                                ..,10@L,.                      .:G@L;,...                           \n" +
                       "                  ..      ..  ...,t0@G;.                       ..i8@C;,....  ..                     \n" +
                       "                . .............,:t8@G;..                        .,t8@C;,...........,....            \n" +
                       "      ...............,,,::;;i1tfC8@C;..                          .,t8@0Lt1ii;::,,,..................\n" +
                       ".......,,,::;;i11tfLCCG008888888GCL;,.                            .,1CG088888800GGCLftt1i;;::,,,....\n" +
                       ":;1tfLCCG0008888800GGCLftt1i;;:,..,.                               ....,::;;i1tfLLCG0008888800GGCLt1\n" +
                       "08800GGCLfft1i;;:,,,..........  ...                                  ..  ...........,,::;ii1tfLLCGGG\n" +
                       "::::,,............       ..     .                                                     ..............\n" +
                       "...........                                                               .                   ..  ..\n" +
                       ":,.....                                                           .                               ..\n" +
                       "8C1:,....                                     ..                                            .   ....\n" +
                       "tG@0t;,...                                                .                                 . ...,:1\n" +
                       ",:tG@0t;,....                                                                                ..,iC88\n" +
                       "...:1G@0f;,...                                                                            ...:1C88Li\n" +
                       "  ...:1G80f;,,...                    .                                                  ...:1C88Li,.\n" +
                       "    ...:1C80f;,....                  .                                 .              ...:1G@8L;,...\n" +
                       "      ...:1C80f;,....                                                               ...:1G@0f;,..   \n" +
                       "        ...,iC88Li,,...                                                          ...,:1G@0f;,..     \n" +
                       "          ...,iC88Li,,..                                                        .,,:tG@0f;,..       \n" +
                       "            ...,iL88Lt;...                                                      .1fG@0f;,..         \n" +
                       "              ...,iL88G;,...        .                                         .,t8@0f;,..           \n" +
                       "       .1.      ..,:t8@C:..                                                  ..i8@Ci:...            \n" +
                       "                 ..,t88f:..                                                  ..;G@Gi,..             \n" +
                       ".                ..:C@0i,..  .                                                .,f@81,..             \n" +
                       ",.               ..;0@C;,.              ..                                    .,18@f:..             \n" +
                       "i. ..            .,18@f:..                                                    ..;G@C;,.             \n" +
                       "0f:,             .:L@8i..                                                      .,f@0i,.             \n" +
                       "@G;,             .:C@L,.                                                     . ..;0@1..             \n" +
                       "```";
            n[ 81 ]  = "```" +
                       "                           .   ..,10@C:.                        .;0@L;,...                          \n" +
                       "                  ..      ......,10@0i..                        ..18@C;,........                    \n" +
                       "          ..................,,,:t0@0i..                          .,t8@Ci:,,........,..........      \n" +
                       "...............,,,,::;ii1tffLCG08@01,..                           .,t8@8GCCLft11i;::,,,,............\n" +
                       "...,,::;ii1tffLCGG0088888800GGCLtt;,.                              .,itfCGG00888888800GCLLft1ii;::,.\n" +
                       "fLCG0088888880GGCLfft1i;;:,,,,.....                                  .....,,,::;;i1tfLCCG0088888880C\n" +
                       "CCCLfft1i;;::,,............ .. ..                                     .    ..............,,::;ii1ttt\n" +
                       ".............            .                                                                  ........\n" +
                       ".. ..... .                                                                                          \n" +
                       "......                                                            .                                 \n" +
                       "1:,.....                                      ..                                            ..     .\n" +
                       "88C1:,....                                                .                                 .  .....\n" +
                       "iL88C1:,...                                                                                   ..,:1L\n" +
                       ".,;f0@G1:,....                                                                             ...,;f0@0\n" +
                       " ..,;f0@G1:,....                     .                                                   ...,;f0@Gt:\n" +
                       "   ..,;f0@Gt:,....                   .                                 ..              ...,;L8@Gt:,.\n" +
                       "    ...,;f0@Gt;,....                                                                 ...,iL8@G1:,.. \n" +
                       "     . ..,:t0@0t;,....                                                            ....,iL88C1:...   \n" +
                       "         ..,:t0@0t;,,.                                                            ..,iL88C1:...     \n" +
                       "           ..,:tG@0fi;...                                                        .i1C88C1:...       \n" +
                       "             ..,:tG@8C:,..          .                                          ..i088C1:...         \n" +
                       "       1:      ..,;f8@C;..                                                    ..i8@Gt:,...          \n" +
                       "       ..       ..,18@C;..                                                     .i0@G;,..            \n" +
                       "                ..,f@8t,..   .                                                 .,L@81,..            \n" +
                       ".               ..;G@0i,.               ..                                     .,18@f:..            \n" +
                       ",               ..i8@C:..                                                      ..;0@C;,.            \n" +
                       "f:..            .,f@8t,.                                                        .,L@0i,.            \n" +
                       "81,             .,L@G:..                                                     .  ..i8@1..            \n" +
                       "```";
            n[ 82 ]  = "```" +
                       "                           . ...,iG@0;.                         ..;0@C;,...                         \n" +
                       "                  .............,10@81,.                          .,t8@C;,.............              \n" +
                       "       ................,,,,::;it0@01..                           ..,t8@G1i;:,,,,...,............... \n" +
                       "..........,,,::;;i11tfLLCG008888801,..                             .:f08@8800GGCLfft1i;;::,,,.......\n" +
                       ",,:i11tfLLCG00888888880GGCLfft1;;:,.                                ..:;i1tfLLCG00888888800GGCLft1i;\n" +
                       "G088888800GCLLft1ii;::,,...........                                   ..........,,::;ii1tfLCCG008880\n" +
                       "111ii;::,,............   .     ..                                               .............,,,::::\n" +
                       "....... ...              .                                                                      .  .\n" +
                       "..       .                                                                                          \n" +
                       "..                                                                .                                 \n" +
                       ".......                                       ..                                            ..      \n" +
                       "Ct;,....                                                  .                                  .    ..\n" +
                       "G@8Li,,....                                                                                    ....,\n" +
                       ":1G@8Li:,.....                                                                              ...,:iL0\n" +
                       "..:1C88Li:,...                       .                                                     ..,:1G@8C\n" +
                       " ...:1C88Ci:,...                     .                                 ..                ..,:1G@8Li,\n" +
                       "   ...,iC88C1:,.....                                                                   ..,:tG@8Li,..\n" +
                       "    ....,iL88C1:,....                                                               ...,;tG@8L;,... \n" +
                       "      ....,iL8@C1:,...                                                            ...,;t0@0f;,..    \n" +
                       "          ..,iL88G1;:.                                                            .:if0@0f;,...     \n" +
                       "           ...,iL8@0L,...           .                                           ..;C0@0f;,... .     \n" +
                       "      ;i     ...,iL8@C;..                                                      ..;0@8fi,...         \n" +
                       "      .,       ..,i0@0i,.     .                                                .,18@Gi,...          \n" +
                       "               ..,t@@f:,.    .                                                 ..:L@81,..           \n" +
                       "               ..:C@81,..               ..                                      .,t@@f:..           \n" +
                       ".              ..;0@G;,.                                                        ..i0@C;,.           \n" +
                       ":              .,t8@L,.                                                          .:C@0i,.           \n" +
                       "t.             .,f@0i..                                                      .   ..1881..           \n" +
                       "```";
            n[ 83 ]  = "```" +
                       "                          .....,iG@8i..                          ..;8@C;,........                   \n" +
                       "               ..............,:iG@8t,.                            .,t8@C;,.................         \n" +
                       " .   .............,,,,::;ii1tfL0@8t,..                            ..,f@@0Lft1i;;::,,,...............\n" +
                       "....,,,,::;;i1ttfLCGG0088888888GCt:.                                .:tCG8888888800GCCLft1ii;::,,,..\n" +
                       "i1fLCCG008888888800GCLLft1ii;:,,,,.                                  ..,,,:;ii1tfLLCG008888888800GLf\n" +
                       "08800GCLLft1ii;::,,,.......... ....                                    .  ..........,,,::;ii1tfLLCGC\n" +
                       ",,:,,,............             ..                                                .    ..............\n" +
                       "                         .                                                                          \n" +
                       "                                                                                                    \n" +
                       ".                                                                 .                                 \n" +
                       "..    .                                       .                                              ,      \n" +
                       ":,.....                                                   .                                  .      \n" +
                       "8Gt;,....                                                                                         ..\n" +
                       "f0@0f;,.... ..                                                                                ....,;\n" +
                       ",;f0@0f;,.....                       .                                                      ...,if0@\n" +
                       "..,;f0@0f;,....                      .                                  .                 ...,iL8@0t\n" +
                       "  ..,;t0@0f;,,...  .                                                                    ...,iL8@Gt:,\n" +
                       "    ..,:tG@8Li:,....                                                                  ...:iL8@Gt:,..\n" +
                       "      ..,:tG@8Li:,...                                                               ...:1C8@G1:,.. .\n" +
                       "        ...:1G@8Li::..                                                             .::1C8@C1:,..    \n" +
                       "          ...:1G@8Cf,...            .                                            ..:LG8@G1:,...     \n" +
                       "     ,i     ..,:1G@@L:,.                                                        ..;C@@Gt:,..        \n" +
                       "     .:     ....,i0@81,.      .                                                 ..18@Gi:...         \n" +
                       "              ..,18@L;,.     .                                                  .,;C@81,..          \n" +
                       "               .:L@8t:..                .                                        .,f@@f:..          \n" +
                       "              ..;G@01,.                                                          .,18@C;,.          \n" +
                       "              .,18@C:.                                                            .:G@0i,.          \n" +
                       "              ..t@81..                                                       .    ..t@81..          \n" +
                       "```";
            n[ 84 ]  = "```" +
                       "                .   .  .......,;C@81.                              .i8@C;,.........        .        \n" +
                       "        ..................,,,:iC@8f,.                              .,t8@Ci:,,,..................... \n" +
                       "............,,,,::;;i11tfLLCG08@@L:..                             ...,f@@80GCLftt1ii;::,,,,.........\n" +
                       "..,::;ii1tffLCGG08888@@88800GGLt1:.                                 ..,1tLCG00888@@88880GGCLftt1i;:,\n" +
                       "LG0888@@@88800GCLLft1ii;::,,,......                                   .....,,,::;ii1tfLLCG00888@@880\n" +
                       "CCLLft1ii;::,,,...........    .. ..                                     .     ...........,,,::;ii111\n" +
                       "..............                 .                                                          ..........\n" +
                       "                         .                                                                          \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       ".                                             .                                              ..     \n" +
                       ".....                                                     .                                         \n" +
                       "1;,....                                                                                            .\n" +
                       "@8C1:,....  .                                                                                 ......\n" +
                       "1C8@G1;,...                          .                                                        ...:it\n" +
                       ".,iC8@Gt:,....                       .                                  .                  ...,:tG@8\n" +
                       "...,iL8@Gt;,....                                                                          ..,;t0@8Li\n" +
                       "  ...,iL8@Gt;,.....                                                                    ...,;f0@8Li,.\n" +
                       "     ..,iL8@0t;,...                                                                  ...,;f0@8Li,...\n" +
                       "      ...,;f0@0f;:,..                                            .                  .,:;f0@8Li,...  \n" +
                       "        ...,;f0@0Lt,...             .                                             ..,tL0@0fi,...    \n" +
                       "    .;.   ...,;f0@8f:,.                                                          ..:f8@0Li,...      \n" +
                       "    .i.     ...:1G@8t,.       .                                                  ..1@@G1:,...  .    \n" +
                       "             ..,10@G1,.      .                         .                         .,iG@01,...        \n" +
                       "              .,f@@L:..                 .                                        ..,f@@f:..         \n" +
                       "             ..:C@8t,.                                                            .,18@C;,.         \n" +
                       "             ..i0@0;..                                                            ..;G@0i,.         \n" +
                       "             ..i8@f,.                                                              .,f@81..         \n" +
                       "```";
            n[ 85 ]  = "```\n              ...............,;C@@t,.                               .i8@C;,.............  ..        \n      ............,.,,,,,:;;itC@@L,..                               .,f@@C1i;::,,,,.................\n.......,,,,::;;i11tfLCCG0088@@@8C:..                                 .:L8@@@8800GCLLft1ii;;::,,,....\n,:i1tfLLCG00888@@@@8800GCCLft1;;,.                                    .,;;1tfLLCG0088@@@@88800GCLfti\n08@@88800GCCLft11i;::,,,..........                                     .........,,,::;ii1tfLLCG00880\niiii;::,,,.............        . .                                               .............,,,::,\n....... .   .                  .                                                                    \n                         .                                                                          \n                                                                                                    \n                                                                   .                                \n                                              .                                              .,     \n..                                                        .                                         \n......                                                                                              \nGt;,....    .                                                                                  .  ..\n0@8Li:,...                           .                                                         .....\n;tG@8L1:,...                         .                                  .                    ...,ifG\n.,:tG@8C1:,...                                                                             ..,:iC8@0\n ..,:tG@8C1:,....                                                                        ..,:1C8@0t;\n   ..,:1G@8C1:,...                                                                    ....:1C@@Gt;,.\n     ..,:1C@@G1:,,.                                              .                   .,,:1C@@Gt:,.. \n       ...:1C8@Gt1,...              .                                                .itG@@Gt:,..   \n    :.   ...:1C8@8t,,..                                                           ..,10@@Gt;,...    \n    1:     ..,:t0@8t,..       .                                                   ..18@0f;,... .    \n            ..,i0@0t:..      .                                                    .,10@01,...       \n            ..,t8@C;,.                  .                                         ..:L@@f:...       \n            ..:L@@L:..                                                             .,t@@L;,.        \n             .;G@81,.                                                              ..;0@Gi,.        \n            ..i0@C:.                                                                .,f@8i..        \n```";
            n[ 86 ]  = "```" +
                       "        ...............,,,::iL8@C,.                                  ..18@Ci::,,,.................. \n" +
                       "..........,,,,::;iii1tffLCG08@@0;..                                   .:L@@80GCLfft1ii;;::,,,,......\n" +
                       ".,:;ii1tfLLCGG088@@@@@@8880GCLf;..                                     .,tfCG0088@@@@@@8800GCLfft1;:\n" +
                       "G088@@@@@8880GGCLft11i;;::,,....                                         ...,,,::;ii1tfLLCG0088@@@@8\n" +
                       "LLLftt1i;;::,,,...........   ..  .                                       ...  ............,,,::;iiii\n" +
                       "..............                   .                                                        . ........\n" +
                       "   .                           .                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                              .                    .                                \n" +
                       "                                           .  .                                               ..    \n" +
                       "                                                          .                                         \n" +
                       ".                                                                                                   \n" +
                       "......      .                                                                                       \n" +
                       "t;:,...                                                                                            .\n" +
                       "@@Gt;,....                           .                                  ..                      ....\n" +
                       "1C@@Gt;,....                                                                                  ...,;1\n" +
                       ",:1C@@Gt;,,...                                                                              ..,:1C8@\n" +
                       "...:1C@@Gf;,.....                                                                        ...,:1G@@Gt\n" +
                       "  ...:1C8@0f;,,..                                                .                      ..,:tG@@Gt;,\n" +
                       "    ...:iC8@0fi;,.                 ..                                                  .:;tG@@Gt;,..\n" +
                       "      ...:iL8@0G1....                                                                ..:C0@@Gt;,..  \n" +
                       "  i1    ...:1L8@01,..         .                                                     ..;G@@Gt;,...   \n" +
                       "          ..,;C@@C:..        .                         .                            .,t8@01:,..     \n" +
                       "          ..,i0@8t:..                   .                                           .,;G@8t:...     \n" +
                       "           .,t8@Gi,.                    .                                            .,L@@L;,.      \n" +
                       "           .:L@@L:..                                                                 ..18@Gi,.      \n" +
                       "           .:C@0i..                                                                   .:C@0i..      \n" +
                       "```";
            n[ 87 ]  = "```\n ..  .............,,,,::;;i1L8@G:.                                    ..1@@Cti;;::,,,,..............\n.....,,,,::;;i11tfLLCGG0888@@@01,.                                     .:L@@@@8800GCLLft11i;;::,,,..\n;i1fLLCG0088@@@@@@@880GGCLLt1i:..                                       .,iitfLCCG0088@@@@@@8800GCLt\n8@@@8880GGCLfft1i;;::,,,.......                                          .......,,,,::;ii1tfLCCG0080\niiii;::,,,............           .                                         ..     ............,,,,,,\n........... .                    .                                                                  \n                               .                                                                    \n                                                                                                    \n                                                                                                    \n                                              .                    .                                \n                                           .  .                                               .,    \n                                                          .                                         \n                                                                                                    \n..          .                                                                                       \n......                                                                                              \nGfi:,...                             .                                  ..                        ..\n0@8C1:,....                                                                                   ......\n;f0@8C1:,....                                                                                 ..,;tL\n.,;f0@8C1;,.....                                                                           ...,iL0@8\n ..,;tG@@Gt;,...                                                                         ...:iL8@8Li\n  ...,;tG@@Gt;:,.                  .                                                    .,:iL8@8Li:.\n     ..,;tG@@GLi....                                                                  ..,fG8@8Li:...\n ;1   ...,;tG@@0i:..          .                                                       .;L@@8L1:,..  \n ..      ..,iC@@G;..                                   .                             ..t@@0t;,...   \n          .,;G@8f;..                                                                 .,iG@8t:...    \n          .,18@01,.                     .                                            ..:L@@L;,.     \n          .,f@@C;..                                                                   .,t8@Gi,.     \n          .,L@81..                                                                    ..:G@0i.      \n```";
            n[ 88 ]  = "```" +
                       "... .........,,,,::;;i11tfLC8@0;.                                      ..t@@GLft11i;;::,,,,,........\n" +
                       "...,::;ii1ttfLLCG0088@@@@@@80G1,.                                       .:L08@@@@@@8800GCCLftt1ii;,,\n" +
                       "fLG0088@@@@@@@8800GCLfft1ii::,.                                           .::;i11tfLCCG008@@@@@@@88G\n" +
                       "000GGCLfft1ii;::,,,...........                                            ...........,,,::;;i11tfLLL\n" +
                       ",,,,,...............             .                                                    ..............\n" +
                       "    .   .                        .                                                                  \n" +
                       "                               .                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                              .                    .                                \n" +
                       "                                              .                                                ,    \n" +
                       "                                                          .                                         \n" +
                       "                                                                                                    \n" +
                       ".                                                                                                   \n" +
                       ".. ..                                                                                               \n" +
                       ";,,....                                                                 ..                          \n" +
                       "@Gfi:,...                                                                                         ..\n" +
                       "C8@8Li:,....                                                                                   ...,,\n" +
                       ":iL8@8Li:,....                                                                               ..,:1C0\n" +
                       "..:iL8@8L1:,...                                                                           ...,;tG@@0\n" +
                       " ...,iL8@8L1::,.                   .                                                     .,:;f0@@Gt;\n" +
                       "   ...,iL0@8Cfi....                                                                     .,tL0@@Gt;,.\n" +
                       ";1    ..,iL0@@G;,..           .                                                       ..:f8@@Gt;,.. \n" +
                       ",:     ...:1C@@G;..                                                                   ..t@@8fi,...  \n" +
                       "         .,;C@@Ci,.                                                                   .,10@8t:,...  \n" +
                       "         .,i0@8t:..                     .                                             ..:C@@L;,.    \n" +
                       "         .,t8@0i...                                                                    .,t@@C;,.    \n" +
                       "         .,f@@f,.                                                                      ..;0@0;..    \n" +
                       "```";
            n[ 89 ]  = "```" +
                       ".......,,,,:::;ii11tfLLCG00@@8i..                                       .,f@@80GCCLftt1i;;:::,,,....\n" +
                       ",,;i1ttfLCCG0088@@@@@@@8800CL1,.                                         .:tLG0088@@@@@@8800GGCLft1;\n" +
                       "08@@@@@@@8800GCLLft11i;::,,...                                             ...,::;;i1ttfLCGG088@@@@8\n" +
                       "LLLft11i;;::,,,..........  ..                                               .. ...........,,,::;;iii\n" +
                       "..............                  ..                                                       ... .......\n" +
                       "    .   .                        .                                                                  \n" +
                       "                               .                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                           .                        \n" +
                       "                                              .                    .                                \n" +
                       "                                              .                                                .,   \n" +
                       "                                                          .                                         \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       ".                                                                                                   \n" +
                       "......                              .                                    ,                          \n" +
                       "ti:,....                                                                                           .\n" +
                       "@@Gf;,,...                                                                                      . ..\n" +
                       "tG@@0fi:,....                                                                                 ...,:i\n" +
                       ",:tG@@0fi:,...                                                                             ....:1L8@\n" +
                       "..,:1C@@0fi:,,.                    .                                                      ..,:1C8@8L\n" +
                       "  ..,:1C8@0Lt;....                                                                        .itC8@8Li:\n" +
                       "i.  ..,:1C8@8G;,..           .                                                          .,10@@8L1:..\n" +
                       ";     ..,:tG@@Gi,.                                                                     ..t8@8C1:,.. \n" +
                       "       ..,:L@@01,.                                                                     .,t8@8t:,..  \n" +
                       "        ..;G@@L;,.                      .                                              ..;G@@f:,..  \n" +
                       "        .,10@81,. .                                                                     .,f@@C;..   \n" +
                       "        ..18@C:..                                                                       ..i0@G;..   \n" +
                       "```";
            n[ 90 ]  = "```" +
                       "....,,::;;;i1ttfLLCG0088@@@@8t..                                         .,L@@@@@800GGCLftt1ii;;:,,.\n" +
                       "i1tLCCG0088@@@@@@@8800GCCLf1;,.                                           .,i1fLCGG0088@@@@@@8880GCf\n" +
                       "@@@@@880GGCLftt1i;;::,,,.....                                               .....,,,::;ii1ttfLCGG000\n" +
                       ";ii;;::,,,.............    .                                                 .  ...............,,,,,\n" +
                       "...........                     ..                                                            .    .\n" +
                       "                                                                                                    \n" +
                       "                               .                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                              .                    .                                \n" +
                       "                                              .                                                 ,   \n" +
                       "                                                          .                                         \n" +
                       "                                                                                                    \n" +
                       "           .                                                                                        \n" +
                       "                                                                                                    \n" +
                       "..                                  .                                    ,                          \n" +
                       ",......                                                                                             \n" +
                       "0L1:,....                                                                                         ..\n" +
                       "8@@Gt;,....                                                                                     ....\n" +
                       "if0@@Gt;,....                                                                               ....,:1f\n" +
                       ".,;f0@@Gt;,,..                     .                                                        .,,if0@@\n" +
                       "...,;f0@@Gf1;....                                                                          .;1L0@@Gt\n" +
                       "   ..,;fG@@0C:,..            .                                                           ..iG8@@Gt;,\n" +
                       "   . ..,;f0@@Gi,..                                                                      .,10@@Gf;,..\n" +
                       "      ..,:f8@8t,.                                                                       .,f@@0t:,.. \n" +
                       "       ..:L@@Ci,.                       .                                               .,i0@8f:,.  \n" +
                       "       ..iG@@f,.  .                                                                     ..:L@@C;..  \n" +
                       "       ..i0@0;..                                                 .                       ..18@G;.   \n" +
                       "```";
            n[ 91 ]  = "```" +
                       "..,:;ii11tfLLCG0088@@@@@@@80f,.                                           .,f08@@@@@@@880GGCLftt1i;:\n" +
                       "LCG088@@@@@@@@880GGCLfft1i;,..                                              .,;i1tffLCGG088@@@@@@@80\n" +
                       "000GGCLfft1ii;::,,,........                                                  ........,,,,::;ii1tffff\n" +
                       ",,,,,,.............             .                                               ..  ................\n" +
                       "    . .                         ..                                                                  \n" +
                       "                                                                                                    \n" +
                       "                               .                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                              .                    .                                \n" +
                       "                                              .                                                 ,.  \n" +
                       "                                                          .                                         \n" +
                       "                                                                                                    \n" +
                       "           .                                                                                        \n" +
                       "                                                                                                    \n" +
                       ".                                   .                                    ..                         \n" +
                       ".....                                                                                               \n" +
                       "i:,,...                                                                                             \n" +
                       "@0L1:,....                                                                                        ..\n" +
                       "C8@8C1;,....                                                                                  .....,\n" +
                       ":1C8@8C1;,,..                      .                                                         ..,:1L0\n" +
                       "..:1L8@8C1i:..  .                                                                           .:itG@@8\n" +
                       "....:iL8@8GL:....            .                                                            ..;C0@@0Li\n" +
                       "   ...:iL8@@Ci,..                                                                        .,iG@@0L1:.\n" +
                       "     ..,;f8@@f:..                                                                        .,L@@0t;,..\n" +
                       "      ..:f@@01:.                        .                                                .,10@8f:,. \n" +
                       "       .;C@@C:..                                                                         ..:C@@L;.. \n" +
                       "       .;G@81..                                                  .                        .,t8@C:.  \n" +
                       "```";
            n[ 92 ]  = "```" +
                       "::i1tfLLCG0088@@@@@@@880GCf1,.                                             .,ifCG088@@@@@@@880GGCLt1\n" +
                       "08@@@@@@@8800GCCLft1i;;:,..  .                                                ..,::;i1ffLCCG0088@@@8\n" +
                       "LLLft11i;;::,,,........                                                      .    ........,,,::;;ii;\n" +
                       "..............                  .                                                           ........\n" +
                       ".     .                         ..                                                                  \n" +
                       "                                                                                                    \n" +
                       "                               .                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                              .                    .                                \n" +
                       "                                              .                                                 .,  \n" +
                       "                                                          .                                         \n" +
                       "                                                                                                    \n" +
                       "           .                                                                                        \n" +
                       "                                                                                                    \n" +
                       "                                    .                                    ..                         \n" +
                       ".                                                                                                   \n" +
                       "......                                                                                              \n" +
                       "L1;,,...                                                                                           .\n" +
                       "@@0Li:,....                                                                                     ....\n" +
                       "tG@@8L1:,...                       .                                                          ...,:i\n" +
                       ",:tG@@8L1;:..                                                                                .,:1L0@\n" +
                       "..,:tG@@8Cf;...              .                                                             ..:fG8@@G\n" +
                       "  ..,:1C8@@C;,..                                                                          .,;C@@8Gt;\n" +
                       "   ...,;f8@@L:..            .                                                             .,L@@0fi:.\n" +
                       "     ..:t8@8f:.                         .                                                 .,f8@8t:,.\n" +
                       "      .:L@@G;..                                                                           ..;G@@L;..\n" +
                       "     ..:C@@f,.                                                   .                         .,f@@C:. \n" +
                       "```";
            n[ 93 ]  = "```" +
                       "1tLCGG088@@@@@@@@800CLf1;:,.                                                  .:;1fLCG08@@@@@@@@80GC\n" +
                       "@@@@8800GGCLfft1i;:,,...                                                          ...,;;i1ttfLCCG000\n" +
                       "iiii;::,,,,.......                                                                    .........,,,,,\n" +
                       ".......... .                    .                                                                   \n" +
                       "                                .                                                                   \n" +
                       "                                                                                                    \n" +
                       "                               .                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                              .                    .                                \n" +
                       "                                              .                                                  ,. \n" +
                       "                                                          .                                         \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                    .                                    ..                         \n" +
                       "                                                                                                    \n" +
                       ".....                                                                                               \n" +
                       ":,.....                                                                                             \n" +
                       "8Ct;:,....                                                                                         .\n" +
                       "0@@0fi:,...                        .                                                           .....\n" +
                       ";f0@@0Li::..                                                                                  .,,;1f\n" +
                       ".,;f0@@0Lf:...               .                                                               .:tL0@@\n" +
                       "...,;f0@@8L;,..                                                                            .,:L8@@0f\n" +
                       "  ...,if0@@C;..             .                                                              .,L@@8L1:\n" +
                       "    ..,10@@C;..                         .                                                  .:L@@0t:,\n" +
                       "     .,f8@81,.                                                                             .,i0@8f:.\n" +
                       "     .,f@@C:..                                                   .                         ..,L@@L,.\n" +
                       "```";
            n[ 94 ]  = "```" +
                       "CG088@@@@@@@880GCfti;,.  .                                                        .,:itfCG088@@@@@@8\n" +
                       "0000GCCLft1i;;:,...                                                                   ...,,:;i1tffff\n" +
                       ",,,,,,........                                                                             .........\n" +
                       "... ..                          .                                                                   \n" +
                       "                                .                                                                   \n" +
                       "                                                                                                    \n" +
                       "                              .                                                                     \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                              .                                                     \n" +
                       "                                              .                                                  ., \n" +
                       "                                                          .                                         \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                    .                                     .                         \n" +
                       "                                                                          .                         \n" +
                       ". .                                                                                                 \n" +
                       "......                                                                                              \n" +
                       "1;:,.....                                                         .                                 \n" +
                       "@8Gt;,,...                         .                                                            ....\n" +
                       "L8@@Gfi:,..                                                                                    ....,\n" +
                       ":iL8@@Gft:..                 .                                                                 ,itC0\n" +
                       "..:iL0@@8L:,..                                                                              ..,f8@@8\n" +
                       "  ..:iL8@@C;,.              .                                                               .:L@@8Ct\n" +
                       "   ..,iG@@0i,.                         ..                                                   .:C@@01:\n" +
                       "    .,18@@f:.                                                                               .,18@8f:\n" +
                       "    .,18@0;..                                                    .                          ..:G@@f,\n" +
                       "```";
            n[ 95 ]  = "```" +
                       "@@@880GCLt1;:. ..        .                                                                 .,:itfLCC\n" +
                       "iiii;:,...                                                                             .       .....\n" +
                       "......                                                                                              \n" +
                       "                                .                                                                   \n" +
                       "                                .                                                                   \n" +
                       "                                                                                                    \n" +
                       "                              .                                                                     \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                              .                                                     \n" +
                       "                                              .                                                   .,\n" +
                       "                                                          .                                         \n" +
                       "                                                            .                                       \n" +
                       "          .                                                                                         \n" +
                       "                                    .                                                               \n" +
                       "                                    .                                     ..                        \n" +
                       "                                                                          .                         \n" +
                       "  .                                                                                                 \n" +
                       ".                                                                                                   \n" +
                       "......                                                            .                                 \n" +
                       "i:,,....                          ..                                                                \n" +
                       "@0L1:,,..                                                                                        ...\n" +
                       "G@@8Cti:...                  .                                                                   .,.\n" +
                       ";tG@@8GL:....                                                                                 ...1LC\n" +
                       ".,;tG@@@Ci,..               .                                                                 .:f8@@\n" +
                       " ..,iC@@@L:.                           ..                                                    ..i8@@G\n" +
                       "  ..;C@@01,.     .                                                                            .:L@@0\n" +
                       "  ..:G@@L,..                                                     .                            ..i8@8\n" +
                       "```";
            n[ 96 ]  = "```" +
                       "GGGCfti;,.     ..        .                                                             .        .:;i\n" +
                       ",,,...                                                                                 .            \n" +
                       "                                                                              .                     \n" +
                       "                                .                                                                   \n" +
                       "                                .                                                                   \n" +
                       "                                                                                                    \n" +
                       "                              .                                                                     \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                              .                     .                               \n" +
                       "                                              .                                                    ,\n" +
                       "                                                          .                                         \n" +
                       "                                                            .                                       \n" +
                       "          .                                                                                         \n" +
                       "                                                                                                    \n" +
                       "                                    .                                     ..                        \n" +
                       "                                                                          .                         \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "..                                                                .                                 \n" +
                       "........                          ..                                                                \n" +
                       "Cti:,...                                                                                           .\n" +
                       "@@0L1;:..                    .                                                                   ...\n" +
                       "f0@@8GL;....                                                                                     .;1\n" +
                       ",;f0@@@Ci:..                                                                                   .,10@\n" +
                       "..,iL8@@C:..                           ..                                                     ..i8@@\n" +
                       " ..:f8@8f:.                                                                                    .;G@@\n" +
                       " ..,L@@G:..                                                      .                             .,t8@\n" +
                       "```";
            n[ 97 ]  = "```" +
                       "t1i:,.         .                                                                       .            \n" +
                       "                                                                                       .            \n" +
                       "                                                                              .                     \n" +
                       "                               ..                                                                   \n" +
                       "                                .                                                                   \n" +
                       "                                                                                                    \n" +
                       "                              .                                                                     \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                              .                     .                               \n" +
                       "                                              .                                                    .\n" +
                       "                                                          .                                         \n" +
                       "                                                            .                                       \n" +
                       "          .                                                                                         \n" +
                       "                                                                                                    \n" +
                       "                                    .                                     ..                        \n" +
                       "                                                                           .                        \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       ".                                                                 .                                 \n" +
                       ".. ....                           ..                                                                \n" +
                       ";,,....                                                                                             \n" +
                       "@Gfi::..                                                                                           .\n" +
                       "0@@0Cf;....                                                                                       .,\n" +
                       "iL0@@8C;:..                .                                                                    ..;L\n" +
                       ".:1L8@@Gi..                            .                                                        .i8@\n" +
                       "..,10@@G;.                                                                                      .;G@\n" +
                       " .,18@81,.                                                                                      ..t@\n" +
                       "```";
            n[ 98 ]  = "```" +
                       ".              .                                                                       ..           \n" +
                       "                                                                                        .           \n" +
                       "                                                                              .                     \n" +
                       "                               ..                                                                   \n" +
                       "                                .                                                                   \n" +
                       "                                                                                                    \n" +
                       "                              .                                                                     \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                              .                     .                               \n" +
                       "                                              .                                                     \n" +
                       "                                                          .                                         \n" +
                       "                                                            .                                       \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                    .                                      .                        \n" +
                       "                                                                           .                        \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                  .                                 \n" +
                       ".                                 .                                                                 \n" +
                       "......                                                                                              \n" +
                       "L1;:,..                     .                                                                       \n" +
                       "@@0Lt;.....                                                                                        .\n" +
                       "L8@@8G;,,.                 .                                                                       ,\n" +
                       ":1C8@@01,.                             .                                                         .:C\n" +
                       ".,iG@@81.                              .                                                         .,C\n" +
                       "..;0@@f:.                                                                                          ;\n" +
                       "```";
            n[ 99 ]  = "```" +
                       "               .                                                                        .           \n" +
                       "                                                                                                    \n" +
                       "                                                                              .                     \n" +
                       "                               ..                                                                   \n" +
                       "                                .                                                                   \n" +
                       "                                                                                                    \n" +
                       "                       .      .                                                                     \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                              .                                                     \n" +
                       "                                              .                                                     \n" +
                       "                                                          .                                         \n" +
                       "                                                            .                                       \n" +
                       "         .                                                                                          \n" +
                       "                                                                                                    \n" +
                       "                                    .                                      ,                        \n" +
                       "                                                                           .                        \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                  .                                 \n" +
                       "                                  .                                                                 \n" +
                       ".. ..                                                                                               \n" +
                       ":,,,..                      .                                                                       \n" +
                       "8Gf1;,..  .                                                                                         \n" +
                       "8@@8Gi,,..                 .                                                                        \n" +
                       "1C8@@0t:.                              .                                                            \n" +
                       ",;C@@@L,.                              .                                                            \n" +
                       ".:C@@G;,.                                                                                           \n" +
                       "```";
            n[ 100 ] = "```" +
                       "               .                                                                        ,           \n" +
                       "                                                                                                    \n" +
                       "                                                                              .                     \n" +
                       "                               .                                                                    \n" +
                       "                                .                                                                   \n" +
                       "                                                                                                    \n" +
                       "                       .      .                                                                     \n" +
                       "                                                                                                    \n" +
                       "                                                                    .                               \n" +
                       "                                             .,                     .                               \n" +
                       "                                                                                                    \n" +
                       "                                                          .                                         \n" +
                       "                                                            .                                       \n" +
                       "         .                                                                                          \n" +
                       "                                                                                                    \n" +
                       "                                    .                                      .                        \n" +
                       "                                                                           .                        \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                  .                                                                 \n" +
                       "..                                .                                                                 \n" +
                       ".....                       .                                                                       \n" +
                       "f1;;,..                     .                                                                       \n" +
                       "@@0C1....                  .                                                                        \n" +
                       "C8@@0t;..                                                                                           \n" +
                       ";L8@@G;.                               .                                                            \n" +
                       ",t8@8t,.                                                                                            \n" +
                       "```";
            n[ 101 ] = "```" +
                       "              ..                                                                        ..          \n" +
                       "                                                                                                    \n" +
                       "                               .                                                                    \n" +
                       "                               .                                                                    \n" +
                       "                                .                                                                   \n" +
                       "                                                                                                    \n" +
                       "                       .      .                                                                     \n" +
                       "                                                                                                    \n" +
                       "                                                                    .                               \n" +
                       "                                             .,                     .                               \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                            .                                       \n" +
                       "         .                                                                                          \n" +
                       "                                                                                                    \n" +
                       "                                   ..                                      ..                       \n" +
                       "                                                                           ..                       \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                  .                                                                 \n" +
                       ".                                 .                                                                 \n" +
                       "....                                                                                                \n" +
                       ":,,,..                      .                                                                       \n" +
                       "8GL1,...                   .                                                                        \n" +
                       "@@@8f;,.                                                                                            \n" +
                       "f0@@81.                                .                                                            \n" +
                       "iG@@C;.                                                                                             \n" +
                       "```";
            n[ 102 ] = "```" +
                       "              .                                                                         .,          \n" +
                       "                                                                                                    \n" +
                       "                               .                                                                    \n" +
                       "                               .                                                                    \n" +
                       "                                .                                                                   \n" +
                       "                                                                                                    \n" +
                       "                       .      .                                                                     \n" +
                       "                                                                                                    \n" +
                       "                                                                    .                               \n" +
                       "                                             .,                                                     \n" +
                       "                                                                                                    \n" +
                       "                                                           .                                        \n" +
                       "                                                            .                                       \n" +
                       "         .                                                                                          \n" +
                       "                                                                                                    \n" +
                       "                                   .                                       ..                       \n" +
                       "                                                                            .                       \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                  .                                                                 \n" +
                       "                                  .                                                                 \n" +
                       "...                                                                                                 \n" +
                       ".....                       .                                                                       \n" +
                       "Lt1,....                   .                                                                        \n" +
                       "@@8f;,.                                                                                             \n" +
                       "G@@@L,.                                .                                                            \n" +
                       "L@@81,.         .                                                                                   \n" +
                       "```";
            n[ 103 ] = "```" +
                       "                            .                                                            ,          \n" +
                       "                                                                                                    \n" +
                       "                               .                                                                    \n" +
                       "                               .                                                                    \n" +
                       "                                .                                                                   \n" +
                       "                                                                                                    \n" +
                       "                       .                                                                            \n" +
                       "                                                                                                    \n" +
                       "                                                                    .                               \n" +
                       "                                             .,                                                     \n" +
                       "                                                                                                    \n" +
                       "                                                           .                                        \n" +
                       "                                                            .                                       \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                   .                                        .                       \n" +
                       "                                                                            .                       \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                  .                                                                 \n" +
                       "                                  .                                                                 \n" +
                       "..                                                                                                  \n" +
                       "...                         .                                                                       \n" +
                       "i;,....                    .                                                                        \n" +
                       "@0L;:..                                                                                             \n" +
                       "@@@G;.                                 .                                                            \n" +
                       "8@@L:.          .                                                                                   \n" +
                       "```";
            n[ 104 ] = "```" +
                       "                            .                                                            ..         \n" +
                       "                                                                                                    \n" +
                       "                              ..                                               .                    \n" +
                       "                                                                                                    \n" +
                       "                                .                                                                   \n" +
                       "                                                                                                    \n" +
                       "                             .                                                                      \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                             ..                                                     \n" +
                       "                                                                                                    \n" +
                       "                                                           .                                        \n" +
                       "                                                            .                                       \n" +
                       "        .                                                                                           \n" +
                       "                                                                                                    \n" +
                       "                                   .                                        ..                      \n" +
                       "                                                                            ..                      \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                  .                                                                 \n" +
                       "                                                                                                    \n" +
                       ".                          ..                                                                       \n" +
                       "..                                                                                                  \n" +
                       "t:...                                                  .                                            \n" +
                       "@0t,.                                  .                                                            \n" +
                       "@@f,.                                                                                               \n" +
                       "```";
            n[ 105 ] = "```" +
                       "                            .                                                             .         \n" +
                       "                                                                                                    \n" +
                       "                              ..                                                                    \n" +
                       "                                                                                                    \n" +
                       "                               .                                                                    \n" +
                       "                                                                                                    \n" +
                       "                      .      .                                                                      \n" +
                       "                                                                                                    \n" +
                       "                                                                     .                              \n" +
                       "                                             ..                                                     \n" +
                       "                                                                                                    \n" +
                       "                                                           .                                        \n" +
                       "                                                            .                                       \n" +
                       "        .                                                                                           \n" +
                       "                                                                                                    \n" +
                       "                                   .                                        ..                      \n" +
                       "                                                                            ..                      \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                 ..                                                                 \n" +
                       "                                                                                                    \n" +
                       ".                          .                                                                        \n" +
                       "..                                                                                                  \n" +
                       ":. ..                                                                                               \n" +
                       "0t,.                                  .. .                                                          \n" +
                       "@G:.           .                                                                                    \n" +
                       "```";
            n[ 106 ] = "```" +
                       "                            .                                                             .         \n" +
                       "                                                                                                    \n" +
                       "                              .                                                                     \n" +
                       "                                                                                                    \n" +
                       "                               .                                                                    \n" +
                       "                                                                                                    \n" +
                       "                      .      .                                                                      \n" +
                       "                                                                                                    \n" +
                       "                                                                     .                              \n" +
                       "                                             ..                                                     \n" +
                       "                                                                                                    \n" +
                       "                                                           .                                        \n" +
                       "                                                            .                                       \n" +
                       "        .                                                                                           \n" +
                       "                                                                                                    \n" +
                       "                                   .                                         .                      \n" +
                       "                                                                             .                      \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                 ..                                                                 \n" +
                       "                                                                                                    \n" +
                       "                           .                                                                       .\n" +
                       ".                         .                                                                         \n" +
                       ".                                                      .                                            \n" +
                       "t,.                                   ..                                                            \n" +
                       "@i.            .                                                                                    \n" +
                       "```";
            n[ 107 ] = "```" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                              ,                                                                     \n" +
                       "                                                                                                    \n" +
                       "                               .                                                                    \n" +
                       "                                                                                                    \n" +
                       "                      .      .                                                                      \n" +
                       "                                                                                                    \n" +
                       "                                                                     .                              \n" +
                       "                                             ..                                                     \n" +
                       "                                                                                                    \n" +
                       "                                                           .                                        \n" +
                       "                                                                                                    \n" +
                       "        .                                                                                           \n" +
                       "                                                                                                    \n" +
                       "                                   .                                         .                      \n" +
                       "                                                                             .                      \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                                                                                    \n" +
                       "                                 .                                                                  \n" +
                       "                                                                                                    \n" +
                       "                           .                                                                        \n" +
                       ".                         .                                                                         \n" +
                       "                                                       .                                            \n" +
                       ",                                     ..                                                            \n" +
                       "t.             .                                                                                    \n" +
                       "```";
        } // 1st mov stars ~100 frames
        /*{
            String[] width100pentagram = new String[ 31 ];
            width100pentagram[ 0 ]  = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         .......,..........                                         \n" +
                                      "                                  ...,,,,,,,,:,,,,::,,,,:,,,,,,...                                  \n" +
                                      "                              .,,,:,,,,::,.,,:,,,,::,,,,:,..,:,,,,,,..                              \n" +
                                      "                          ..,,,,.,:,,,,:;:,,.  .,:;:,.. ..,,::,,.,::,,,,..                          \n" +
                                      "                       ..,:,,::.,,....,::;,,,::,,:i;,,,,,,,:;:,...,,,.,::,,,.                       \n" +
                                      "                     .,,:,,.,,:::,..,::::,,,:,,,;;;:;,,:::,,::,,.. .,,::,.,,,,.                     \n" +
                                      "                  .,,,,.,:,..,:;;::::,,.      ,:;:i;;:.      ,,:;:,,:;;:,,,::,,,.                   \n" +
                                      "                .,,,:,.,,. .,:,;;;::,......,,::,.;11,,::,..    .,::;i;,,. .,,,.,,,,                 \n" +
                                      "               ,,,,.,:::,,,:;:,;:;;:,,,:::;i;:,:i1111:,::;;::,,,::;;:;,:;,..,:,::,,,.               \n" +
                                      "             .,,:,,,,.:::,,,. ,;:.;;::::::;;:,:ii,1:;1;::;;::::,,;;:;:..:::,:;;:,..,,,.             \n" +
                                      "            .,,.,:,. ,,,:    .::.,;:.,,,,..  .;1i;Li;1i,  .,,::::;:.,:,  ..,:,:..,,:,,,.            \n" +
                                      "           ,,,,.,,..,;:,   .,;:::i:        .;;i;iCG1,;ii:.       ,i:,::,    ,:::  .,..,,,           \n" +
                                      "          ,,,,::::,,::...,,:;;i::.       ,:;;;,.LCGL; ,;;;:.      .;::;;;,.  .::,..,,,::,,          \n" +
                                      "         ,,,.,,,:;;;::;::,,,:::     ..,;ii;;::;LGCCGLt;:;;ii;,.     .:;::::::,::::;;::,.,,,         \n" +
                                      "        ,,::,,. ,,;;:;11;;;iii::;;:;;;ifLtt11tCG1LCtCCft1ttLfi;;:,,.  ,:;:,,,:;;;i;,:.:.,,:,        \n" +
                                      "       .,,.,,  ,::,,;:,i111;;iii;:::,;t1ffii:tGf;CG;fGi:iifLtt;:;;;iiii;;i11i1i;::::, .,,:,,.       \n" +
                                      "      .,,,,,,,,::, .::.:ii:;tfti::;1ff11;.  ;LGGC00CCGL:  .i11ti;,,,;i11iii11:,;, ,;:. .,.,,,       \n" +
                                      "      ,,,,::;;::.   ::::;;ii1ifLtttt1:.    iCfL:i001;CLC;   .:;tLf11tLft1:ii:.::. .::,,,:,,,,,      \n" +
                                      "     .,,..:.,,::   .;;;;: :ii: itt.       ;L1f,iGGGG;,LtL;      .,1ft1.;i1;;::::.   ,::;::,,,,      \n" +
                                      "     .,,:,,  :::  .::;:;   i;;,,LL:      iCLCtC8L;:C8LiLfL;      .tf1 :;i; .;;;;,   ,:,,.:..,,.     \n" +
                                      "     ,,,,,. .:;, ,::,,::   ,;:;,tt:     1008GGC:    ;GGC0G01     ,fL;,i:;   :::::,  ,::. ,,:,,,     \n" +
                                      "     ,,,.:,,,,:::;,.,i:    .;;;tit;   .tLCGGG,        ;G00GC1.   :fit;:;,   .;;,,::.,::, ,,.,,,     \n" +
                                      "     ,,:::;;;;;:;;;;;,      i1fLf1.  :LtLiLG1          tGCtLtf,  .ttLf1i     .;;,,;;:;::,,:.,,,     \n" +
                                      "     ,,,.:,,::;:;;,,;;.    .iiff1t  ;CLf:;8C           .08;iffC; .ttLt1;      :;::;;:;;;;::::,,     \n" +
                                      "     ,,,.,. ,::,.::,,;;.   :;;;tif:tGCC:iG0;.           t0C,:CCG1i1i1;;;    .;;,.,;:::,,,,,.,,,     \n" +
                                      "     ,,,:,. .::,  ,:::::  .i:i.iLfLf1GCC8GCC0Cf1;,:itfCGCGG8tCGtLCft.i:;,   ::,,::. ,;:. ,:,,,,     \n" +
                                      "     .,,.,,.:,:.   :;;;:. ii;, tCCLiLG0Gfi:;1fCG080GCfti;;tC0GGf;LCC,.;;;  .;:;:,  .:,:  ,,:,,.     \n" +
                                      "     .,,,,::;::,   .::::;iii;,fCCCGL11LGCGLffffC08GCfftfLLGCG1itCCCCL::ii: ::;;;    :::,,:.,,,      \n" +
                                      "      ,,,,,:,,,::. .:,.;1;:ttCGCLfCLCLLf;;i;;;:::;::::;;;i;itfLLCCCCGGfiii;;:,:,   ,::;;:::,,,      \n" +
                                      "       ,,..,. .:;, ::,;11iiii11i,,,:;111i.                .ii1t1;:,:i1ft;:11,.::  :::,.,,,,,,       \n" +
                                      "       .,,:,,. ,,:::;i1ii1i;;;;ii;;;:;ttLfi1;,       ,;i11Lftt;::::;iii;i111i,:;,,::. .:,.,,.       \n" +
                                      "        ,,,,.:.::;i:;;:,,,:::,  .,,,:;ifftt1tLt;, .;tft11ttLfi;;::::,:ii;;;;i1;:;;,, .,,:,,.        \n" +
                                      "         ,,,,,::;::::,,:,::;;i:,     .,;;;;;::tft1tft:,:;;;;:,.     .:::,,,::::;;;;:,:..,,,         \n" +
                                      "          ,::,,,,..,;:.  .,;;;::;.      .:;;;, ;tL1: :;;;:.       ,:;i;;:,.. ,::,,::::,,:,          \n" +
                                      "           ,,,.,:.  ,::,    ,::.:i,       .:iii:tC1:ii;:.       .;;,:::,   .,:;, .,,.,,:.           \n" +
                                      "            .,,:,,,..:::,,.  ,:,.;;::::,,. .,i1;1f;i1:.  .,,,:,,;;..:,    .:,,. .,:,,,,.            \n" +
                                      "             .,,,..,:;;:,:::..:;:;:,,:::;i;::;1;;1:1i:::;;::::::;;.::. ,,:::::.,,,,:,,              \n" +
                                      "               .,,,::,,,..,;::;:;;:::,,,:;:::,:1111;:,:;i;:::,,::;;:;,:;:,,,:::,.,,,.               \n" +
                                      "                 .,,,..,,. .,,;i:::,.     .,,:,,11:.,:,,...  ..,::;;;,,.  .,,.,::,.                 \n" +
                                      "                   .,,,:,,,,:;;:,,:;:,,      .:;;i:;:.     .,,:::::;;:,.,,:,.,,,.                   \n" +
                                      "                     .,,,,.,::,,.  .,,::::::::,;;;;:,::::,,:,:,,..,:::,,.,,:,,.                     \n" +
                                      "                       ..,,:,,.,,,,,.::;:,..,,,,;i:,,,,,,,;::,...,,..,:,,,,.                        \n" +
                                      "                          ..,,,,::,.,,:,,,....,:;;:,.  .,,:;,,,,::,,:,,,..                          \n" +
                                      "                              ..,,,,,::,.,,:,,,,::,,,,:,,.,::,,,,,,,..                              \n" +
                                      "                                   ...,,,,,:,,,,::,,,,:,,:,,,,...                                   \n" +
                                      "                                           ..............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 1 ]  = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         .......,,.........                                         \n" +
                                      "                                  ...,,,:,,,,::,,,,:,,,,,:,,,,,...                                  \n" +
                                      "                              ..,,,:,,,,::..,,:,,,,::.,,::,.,,:,,,,,,.                              \n" +
                                      "                          ..,,,,,.,:,,,,:;:,.  ..,:;:,.. ..,,::,,,,::,:,..                          \n" +
                                      "                       .,,,,.,::,,,. ..,:;:,,::,,,;i:,,,,..,:;:,.,,:,.,,::,..                       \n" +
                                      "                     .,,,:,,,,,;;,,.,::::,.,,,,,:;;;::,::::,::,:,. ..,,::,.,,,.                     \n" +
                                      "                  .,,,,.,,,...,:i::::,,..     .,;::i;;,.     .,,;:,,,:;;,,,,::,,,                   \n" +
                                      "                 ,,,,::,,,..,:,:;;;::,.....,,::,..i11:::,..    ..:::;i:,,..,:,.,,,,                 \n" +
                                      "               .,,,.,,:;:,,:;:.:;:;;:,,,:::ii:::;i111i,,::;;:,,,::;;;;::::. .,,,::,,.               \n" +
                                      "             .,,,:,,,.,,::,,. .::.,;;::::::;:,,:i;:1:i1::;;;;::,,,;;:;..:::,,:;;:,.,,,.             \n" +
                                      "            ,,,..,,  .:,:.   .::,,;;..,,,..   ,i1;1fii1;, .,,,:::;;..::  .,,::::.,,,::,.            \n" +
                                      "           ,,::,,:..,;:,.  .,;;::;;.        ,;iiiLGL::ii;,       .;;.,:,    .:,:. .:,.,:,           \n" +
                                      "          ,,,.,:;;;:::,..,,:::;;,,       .:;;;:,LCCC1. :;;:.      .;:::;;,.  .:;:. .,.,,:,          \n" +
                                      "         ,,,,.:.,:;;;:;i;:,,:::.   ..,:;iii;:::fGCCGLf1:;;;i;,.     ,:;;;:::,,,::::::::,,,,         \n" +
                                      "        ,,,,:,  ,,:;:;;11i1i;ii;ii;;;;ifLff111LG1fCtLfff1ttLfi;:,,.   .:::,,,:;;;;i:::,..,,,        \n" +
                                      "       .,,..,. ,;;,.;:.:11iiiii;:,,,:it1tfii;tCLiCG;tG;,iitLt1;:;;;i;;:;;;i;;;i;:::,, .,,:,,.       \n" +
                                      "      .,,:,:::,,:,  ,:,:ii;:tfft;;;1Lf1i;.  iLGGLG8GCGC:  .i11t;:,,,;i1i;;i111;;:.,;:. .,,,,,       \n" +
                                      "      ,,,.,:::::.   ::;:;.;1i;iftf1ii,.    ;LfL::G8t;CLL;   ,;ifLfi;itLtti:1i,,:, .::,..,,.,,,      \n" +
                                      "     .,,,.,..,,:   .;;;;, .;i; :tf,       ;L1f,;GGGGi.LtL;      ,;1ftf;:iiii:,::.  .,::;::::,,      \n" +
                                      "     .,,::,  ::,  .::,:;.  :i:; fL;      iGLGfC8C;,L8LiffL;       ift, ;ii,.:;;;,   .:,:::,.,,.     \n" +
                                      "     ,,,.,, ,::,.,:,.:;,   .i:i,1ti     1G080GL:    ;GGCGCG1     .tC1 i:i,  :;:;:.  ,::, ,,,,,,     \n" +
                                      "     ,,,,:::::;:;;,,:;,     ;;;tit;   .tLCCG0:        ;G000C1.   ,fit;;;:   .;:,,::..:;. .,,,,,     \n" +
                                      "     ,,,,::;;;;:;;;;;,      i1fLf1   :LtLif0t          1CCfLff,   t1ff1i.    ,;:.,;;::,:..: ,,,     \n" +
                                      "     ,,..:..,,::;:,.:i,    ,iiLfit  ;CLf:i8C           .08;;LtC; .tfLt1;      ,i;;;;:;;;;:::,,,     \n" +
                                      "     ,,,,,. ,;:..,:,,:;.   ;:;;t1t,fGCG:iG8i.           f0C,,CCG11111;;:     :i,.:;::;:,:::,,,,     \n" +
                                      "     ,,,,,, ,::,  .:;:;,  :;;; tLfLf1GGG8CCC0Cti;,;itfCCCGG01CCfLCt1.i:i.  .:;,,,:, ,;:. ,,.,,,     \n" +
                                      "     .:,.,:::,:.   ,;;;:.:1i; :fLCL1LG0Ct;:;1fGG080GLfti;;fG0G0t;CGf.::i,  .;::::. .:::  ,,:,,.     \n" +
                                      "     .,,::::::,,.  ,::,:iiii;iLCCCGLt1LCGCLffffG00GCfftfLCGGC1ifGCCCL;ii;  :;;;:   .:,,.,,.,,,      \n" +
                                      "      ,,,.,,..,::. ,:,,11:itfCCCLLLLCLLf;ii;;;::;::::;;;;i;itffLCCCGGGti1;,;:::,   .::;::,,,,,      \n" +
                                      "       ,,,,:. .;:,.;;i1111;;i11i::,::1i1i.                .;i1fti;:;1ff1:iii,,:,  ,:,,:::,:,,       \n" +
                                      "       .,,:,,..:,;;:ii;:;ii;::::;;;;;;ttCt11;.       :;ii1Lttt;::,,;iii;i111:.:;.,;:, .,..,,.       \n" +
                                      "        ,,,.,::;;;;:;;:,,,:::.   ..,:;ifft11fL1;  :iff1i1ttLfi;;;;;;;ii;iii11;;:;:,, .,::,,.        \n" +
                                      "         ,,,,::::,:::,,,,:;;;;:,      ,;i;;;,ift11ffi,:;;;;;:,..    ,:::,,:;;;:;i;:,.:.,,:,         \n" +
                                      "          ,,,,.,. .:;:.  .,:::,:;.      .:;;; .1ffi..;;;:,.       ,:;;;::,,..,::::;;:,.,,,          \n" +
                                      "           ,:,,,:. .:,:.    ,:,.;;.       ,;ii:;Cf;;1i;,        .;;::;:.   .,::,..:,,::,,           \n" +
                                      "            .,::,,,.::::,,.  ::.,i;:;::,,..,;1i;fi;1;.  ..,,,,,,;;.,:,    ,:,:. .,,.,,,.            \n" +
                                      "             .,,,.,:::,,,:;:.,;:;;,,,::;;i;::1i:1:ii:,:;;:::::::i,,;:  .,,:::,.,,,:,,,              \n" +
                                      "               .,,:,,,,. .,:,::;;;::,,,:;:::,,i111;::::i;:::,,,:;;:;:,:;:,,;;:,,.,,,.               \n" +
                                      "                 .,,,.,:,..,,;i;:::.      .,:::11;.,::,,... ..,::;;;::,. .,,,::,,,.                 \n" +
                                      "                   .,::,,,,:;;:,,,;::,. ..  .,;;i:;;,.     .,,:::::i:,...,:..,,,.                   \n" +
                                      "                     .,,,.,:,,,.. ..:,:::::;:,::;;;:,::,,,:::,,,.,:;;,,,,,:,,,.                     \n" +
                                      "                       ..,:,,,.,:,,,,:;:,..,,,,:i;,,,:,,,:;:,....,,.,:,,:,,.                        \n" +
                                      "                          ..,,,::,,.,:,,,....,::;:,.. .,,:;:,,,,:,.,,,,,..                          \n" +
                                      "                              ..,,,,::,,.,::,..::,.,,::,..::,,,,:,,,..                              \n" +
                                      "                                   ...,,,,:,,,,::,,,,:,,,,,,,,...                                   \n" +
                                      "                                           ..............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 2 ]  = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                          .......,,........                                         \n" +
                                      "                                  ...,,,,:,,,,::,,,,:,,,,,:,,,....                                  \n" +
                                      "                              ..,:,::,,,,::..,,,,,,,:,.,,::..,,:,,,,..                              \n" +
                                      "                          .,,,:,,..,:,,,,:;:,.  ..,:;:,....,,,::,.,,::,,..                          \n" +
                                      "                       .,,,,,.,::,,.  ..,:;:,,::,,:i;,:,,...,:;:,,,,:,.,,,,..                       \n" +
                                      "                     .,:,,:,.,,:;;,,,:::,:,.,,,,,;;;::,,:::::::,,.  .,,,::,,:,.                     \n" +
                                      "                  .,,:,,.,,.. .:;i:::,,..      .:;:;;;:.  ... ,,:;:,.,:;:,,.,,:,..                  \n" +
                                      "                .,,,.,:::,..,::,;;;::,,....,,:::,.,i11:::,.     .,:::ii::..,,:,,:,.                 \n" +
                                      "               .,:,,.,,:;:::::,,;::i:,,,,::;i;::;;i111i,,:::;:,,,::;;:;:::. .,,.,,:,.               \n" +
                                      "             .,:,,:,,..,,:,..  ,:,.;;:::::::::,,:i:;1:1i::;i;;::,,:i;;:.,;:,,,::::.,:,.             \n" +
                                      "            .:,,.,,. .:::,   .:::,;;,..,.,..  .:i1:tt;1i;..,,,:;::i:.::. .,::::::,,.,,:.            \n" +
                                      "           ,,,:::::,,::,.  .,:i;:;;.        .:;i1LCCt,;i;;.       :i,.::.   .:,,, ,,:,,,,           \n" +
                                      "          ,,,.,,,;i:;:,,,,,:,::;,.       .,:;;::LCCCt; ,;:;,       ;;,:::,.  .:;:. .,..,,,          \n" +
                                      "         ,,:,,,. ::i;:;1ii;:;;;:....,,:;ii;;::,fGCCCftf;:i;ii:.     ,:;;;;:,,..::,,,,:,:,,,         \n" +
                                      "        ,:,.,,  ,:,:;;:;1111;;ii1i;;;;1fffft11LG1tGtCftLt1tLfi;:,..   .::::,,:;::;;;;::,.,,,        \n" +
                                      "       .,,,.:,.,:;, ::..;ii;i11;:.,,:it1tfii;tCLiC0;1G;,iitLt1;;;;;;:,,,:;;:,:;i:;;,,,.,.,,,.       \n" +
                                      "      .,,,:::;:,:.  ,:,,i:;;;tLLtiitLL1i:.  iCCGLC8GCGC:  .it11;,,,:;iii;;i1111i;,,:;. .:,,,,       \n" +
                                      "      ,,,.,,,:,:.   :;;:;..11i,1tfi;:,     iLtL::C8LiCCL;   ,;1ffti:;1ftti;i1;::: .::,. ,,.,,,      \n" +
                                      "     .:,,,,. ,,:,  .:;;;,  ,ii, tL;       ;LtL:iCG0Gi.LtL;     .:itftLt;1i;1;,,:,  .:,::,:::,,      \n" +
                                      "     .,,,,, .:;,  ,::,,:,  .i:i.iL1      iGCGLG8Ci,L8f;ffL;       ;t1; :i1;,::::,   .::;::,,,,.     \n" +
                                      "     ,,,.,,,,::,,::,.:;,    ;:i,111     1C080Cf,    ;GGLGCGi      1Lf :;:;. :;;i;.  .:,,.,,.,,,     \n" +
                                      "     ,,::::;:;;:;;:,;;.     ;;;t11i   .tfCLG0:       .iG008Gt.   .t1t;;:;.  .;::::, .:;. .,::,,     \n" +
                                      "     ,,,.:,:;;;:;:::;,     .ittLL1   :LfLit0t          iGCCLff,   t1tLii,    :i:.,::::,, .,.,,,     \n" +
                                      "     ,,,.:. ,,:,::,.:i,    :iiL11t  ;LCf:i8L           .G8i;LtL; .tfLt1i      ,;;:;;:;;;:,:,,,,     \n" +
                                      "     ,,::,. ,;:. ,::::;.  .;:;;tt1.fGCG;1G0;.           f0L.:CCG11111;;:     ,;:,:;;:;::;:::,,,     \n" +
                                      "     ,,,.,,.,,:.  .;;;;, .i:i,.Lftff1GG00CCC0Cti;,;1tfLCCGG0iLCfLCti.i:;    :;,.,:,.,::,.,,.,,,     \n" +
                                      "     .,,,,:;;::.   ,:::::i1i, i1LCL1LC0Lt::;1L0G080CLfti;ifG0G0t;CGt,;:i.  ,:,,::. .:;:  ,:,,,.     \n" +
                                      "     .,,:,:,,,,:.  ,:,,ii;i1ifLCCC0C11CC0CffffL080CLfttLLCCGC11LCCCCL1;i,  :;;;:.  ,:,, ,,,,,,      \n" +
                                      "      ,,,.,, .,::  :::i1i;11tLCCLLffCLLf;ii;;:::;::::;;;;;;i1tffCLCGGCti1.,;:;;,   .:,:,,,.,,.      \n" +
                                      "      .,,,,:. .::,,;i1111i;:i11i;::::i11;                 .;i1Lfi;i1fLf;;;;;,::,  ,:,:;::,:,,       \n" +
                                      "       .:,,.:.,,,;;:i:,,:;;:,.,,:;;;;;ttCt1i:       .:iii1Lttt;:,,,:;1ii;11;..:: ,;:,.,,.,,,.       \n" +
                                      "        ,,,.,:;;;;;::::,,:::;.    .,:;ifft1tLfi, .:tft1i1tfLfi;;;;iiii;i1111i:;::,:,  ,,,,:.        \n" +
                                      "         ,,::,:,.,,::..,,:;;;;:,      ,;i;;::ttt1tft:,:;;;;;:,,.....::::::;ii;:;i:: .,,,:,,         \n" +
                                      "          ,,,..,. .:::.   ,:::,;;       ,;:;, ;tL1:.:;;;:,.       ,:;::::,,..,:::;;,:,.,,,          \n" +
                                      "           ,,,,:,, ,,::.   .::,,i: .     .;;i;:tCi:1i;:.        ,;::;;:..  .,;:,,:::::,,,           \n" +
                                      "            .:,,.,,::;:::,. .::.;;::;::,,..;1i;t1:1i,   .,,,,,.:;:,::,    :,:,. .,..,,,.            \n" +
                                      "             .,:,,:::,..,:;,.:;;;:,,,:;;i;:,i1:1;;i:,,:;:::::::;:.,:,  .,,:,: .,,:,,:,              \n" +
                                      "               .,:,,.,,. .:::;;;;;:,,,:::::,,i111i;:::;i;::,,,:;;::;,,;::,:;:,,.,,:,.               \n" +
                                      "                 .,:,,:,,.,::i;:::,.     ..,:;11i,.,::,,......,::;;;,:,...:,::,,:,.                 \n" +
                                      "                   .,:,,.,:;::...:;::, ...  .:;;;:;:.     ...,::::i;,....,,.,,:,.                   \n" +
                                      "                     .,:,,:,,,,.  .,,:::::::,,:;;;:,:,,,,,:,:::,,,;;,,,.,:,,:,.                     \n" +
                                      "                       ..,,,,.,:,,,,:;:,...,,,,;i:,,:,,,:;:,.. .,,,::,.,,,,.                        \n" +
                                      "                          ..,,::,,.,::,,,....,:;:,..  .,:;:,,,,:,.,,,:,,..                          \n" +
                                      "                              ..,,,,:,,,,::,,.::,,.,:,,..::,,,,:,,,,..                              \n" +
                                      "                                   ...,,,:,,,,,:,,,,::,,,,:,,,...                                   \n" +
                                      "                                          ...............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 3 ]  = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         .........,,.......                                         \n" +
                                      "                                  ...,,,,,:,,,,::,,,,:,,,,,,,,,...                                  \n" +
                                      "                              ..,,:,::,.,,:,.,,,,,,,,:,.,,::,,,,:,,,..                              \n" +
                                      "                          ..,,,:,,.,,,,,,,:;:,.  ..,:;:,...,,,,::..,,:,,..                          \n" +
                                      "                       .,,:,,.,,::,,.  ..,:;:::::,,;i;,:,,...,:;:,,,::,,:,,,.                       \n" +
                                      "                     .,,,.,:,,,.:;:,,:::::,..,,,,:;;;::,:;::::;:,,. .,,.,::,,,.                     \n" +
                                      "                  .,,,:,..,.  .,:i;::,,...     .,;::;;;,  .....,:::,..,:::,.,,,,..                  \n" +
                                      "                .,,,,.::::,.,::::;;;::,,....,:::,,.:111::,.     ..:::;i;::.,,::,,,,                 \n" +
                                      "               .,,:,.,,.::::::,.:;:;i:,,,,::;i;;;;;i111;,:::::,,,::;;;;:,:,  ,,..,,,.               \n" +
                                      "             .,,,.,,.. ,,::.   ,:: :;;:;::::::,,,:i:ii:1;,:;i;;:,,,;i:;.,:;,..,,,:,,,,.             \n" +
                                      "            .,:,,,,...::,,    ,;:,:;:.......   .:1i;fi;1i:..,::;;:;;,:;, .,:::::;:,.,,,.            \n" +
                                      "           ,:,,,:;;,,::,   .,:;i;:;,        .,;;1LLfC::iii,     . ,i,.::.  ..,:,:.,,,:,,,           \n" +
                                      "          ,,,..:.:;;;:,,:::,,,:;:.       .,:;;:;LCCCf1. ;:;:.      :;,,::,    :::. .,,.,,,          \n" +
                                      "         ,,,:,,. ,,;;;;i11ii;;;;:,,,,::i1i;;;:,fGGCCL1f1:iiii:.     ,::;;;:,...::,,..,,,,:,         \n" +
                                      "        ,,,.,,  ,::,;;,:;111i:ii1;;;;:1fffftiifGttGfCt1Lf1tff1i:,.    .:;::,::::::;;;:::,,,,        \n" +
                                      "       .,,,,::,,::, ,:,.;;i;;tti:.,,;1f11fii;tCCiL0iiC;.;i1Lf1;;i;;:,...,:;:,,:;:;;:,:,,.,,,.       \n" +
                                      "      .,,,,::;::,.  ,:::;:,i;itLf11tfL1;:.  iCCGLL8GLGC:  .;t1i:,,:;iiii::i1ii1i;:,::, .,::,,       \n" +
                                      "      ,,,.,, ,,:,   ,;;;;. :11::1f1:,.     ;LtL,,L8CiCCL;   ,i1ft1;,:i1t1iii11i;:  :;:  ,,.,,,      \n" +
                                      "     .,,::,. ,::,  .:::;:  .;;; iL1       ;LtL;iCG0G1.ftC;    .,;1fttfC111:i1:,:,  ,:,,.,,,,,,      \n" +
                                      "     .,,.,, .:;,  ,:,.::,   ;:;,:Lt.     i0GGLG8Ci,f8f;ftL;       ,f11,.ii1;:,::,   ,::;;::,,,.     \n" +
                                      "     ,,,.,::,,:,,;:.,;;,    :;;,i1t.    1CG00Cf,    ;0GfCCCi      ifL,.i:i: :;;;;.   :,,:,,.,,,     \n" +
                                      "     ,,::::;;;;:;;;:;:.     :;;111i   .tfCfCG:       .iG808Gt.    1ft;;:;,  .;:;::. .;;, .:,:,,     \n" +
                                      "     ,,..:..::;;;:,:;:     .itfLfi   ,Lffi10t          iGL0Lff,   t11L1i:    :i:.,::,::. .,,,,,     \n" +
                                      "     ,,,,,. ,::,,:,.:i:    ;;1Li11  ;LCt:i8L           .G81;LtL; .1LLt1i.     :;:,:;:;::..:.,,,     \n" +
                                      "     ,,,,:. ,::. .:;;:;.  :;;:;ffi.fGCGit08;.           L0L.:LLG111t1;;:     .;;:;;;:;;;;::::,,     \n" +
                                      "     ,,, ,::,::   .;;;;, ;i:; ;fftffiGG00LLG0C1i:,;1tfLCCGGG:LCLLCt;,;;:    ,;:.,::,,:,,::,.,,,     \n" +
                                      "     .,,::::;::.   ,:,,;i1i; :t1LCCtLCGfi,:;tC0G80GCft1ii1L00G0t;CG1::;;   ,:,,::,  :;:. ,,.,:.     \n" +
                                      "     .,,,.,..,,:,  :,,;1;:11tCLCCC0L11CG0LfftfC08GCfftffLGC0L11LCCCCL1;;.  :;:::.  ,,:. .,::,,      \n" +
                                      "      ,,,.,,  :;: .;;111iii1tfCCLCffCLffii;;;;:;:::::;;;;;ii11tLCCCCCCf1: ,:;;;,   ,:,,.:..,,,      \n" +
                                      "      .,,:,,. ,::,:;iiiiii::i11ii;:::i1t;                 .:;1Lf1i1fLf1;i,;;:::.  .::;;::,,,,       \n" +
                                      "       .,,..,,:,;i;;;:..:;:,  .,::;;;;1fC11i,       ,;iii1L1tfi:,..:itt;;1;:.,:, ,;:,,::,,,,.       \n" +
                                      "        ,,,,::::;;::::::,;:;;.    .,:;1fft1fC1i. ,itf1iitffffi;;;;i1ii:i111;::;:,::, .:,.,,,        \n" +
                                      "         ,,,,.,. .:::  .,:;;;::,     .:ii;i,ittt1ffi,,:;;;;i;:,,,,,,;;;;;i11i;:;;:, .,,:,,,         \n" +
                                      "          ,,,.,:. .:::    .::,,;:      .:;:; .1Lfi.,;;;;:,.       .;;:,:,:,,,,:;;;,.:.,,,,          \n" +
                                      "           ,,,:,,,.:,:,..  .::.:i,...    ,iii:;Cf;i1;:,         ,;:;;;:,.  .,;:,,:;:,,,:.           \n" +
                                      "            .,,,.,:;::,::,. ,;:,;;:;;:::,.:i1;1f:i1,   ..,,,...:i:,::,   .::::. ,,.,,:,.            \n" +
                                      "             .,,,,:,,...,;:,,;;i:,,,:;;i;:,;1:ii;i:,,::;::::;:;;,.::.  ..::,. ..:,.,,,              \n" +
                                      "               .,,,..,,  ,:,;;;:;:,,,,:::::,;111i:;::;i;::,,,,:i::;:.,::::;:.,,.,::,.               \n" +
                                      "                 .,,,::,,,::;;;:::..     .,:;11i:.,:::,,.....,::;;;:::,..,::::.,,,.                 \n" +
                                      "                   .,,,,.::::,..,:::,.....  ,;;;:;;,.     ..,,,::;i:.  .,,..,::,.                   \n" +
                                      "                     .,,,:,,.,,...,,:;:,::;:,::;;;:,,,,,,:,::::,,:;:.,.,:,,:,,.                     \n" +
                                      "                       ..,,:,,::,,,:;:,....,:,;i;,,,:,,:;:,..  .,,:::,,,,:,..                       \n" +
                                      "                          ..,,:,,.,::,,,,...,:;:,..  .,:;:,,,,:,,.,,:,,,.                           \n" +
                                      "                              ..,,,:,,,,::,,.,:,,,,,:,..,:,,,,::,,,...                              \n" +
                                      "                                   ..,,,,,,,,,:,,,,::,,,,:,,,,...                                   \n" +
                                      "                                           ..............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 4 ]  = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         ..........,.......                                         \n" +
                                      "                                  ...,,,,,,:,,,,:,,,,,:,,:,,,,,...                                  \n" +
                                      "                              ..,,,,,:,,.,,:,.,,,,,,,,:,.,,:,.,,,:,,,.                              \n" +
                                      "                          ..,,,,:,..,,,,,,,:;:,.  ..,:;:,...,,,,::.,,,,,..                          \n" +
                                      "                       .,,,:,,.,,:::,.  ..,:;,:::,,:;i:,:,....,:;:,,,::,,:,..                       \n" +
                                      "                     .,,,,.::,,..:;:,:;;:,,,..,,,::;;;:,,:;:,,:;::...,,,.,::,,.                     \n" +
                                      "                  .,,,::,,,,  ..,;i;::,...     ..:;:;;;:.  ....,::;,...,,::,.,,,,                   \n" +
                                      "                 ,,,,.,,:;:,,:;:,;:;:::,,,.,,::::,.,;i11:,.      .,:::;;:::,,,:,,,,                 \n" +
                                      "               .,,,:,,,.,::::,,.,;:,i;:,,,,::;i;:;;;1111;,::::,,,,:;:;;;:,,  .:,.,,,.               \n" +
                                      "             .,,,..,.  .,,:.   .::..;;:;::::::,,,.:;;1;;1::;i;;:,,,,;;:,,:;:. .,,,::,,.             \n" +
                                      "            ,,,:,::,.,:;:,.   ,;;::i:.  ....    ,;1:itii1i,.,::;;::;:,;: .:::,,::;:.,,,.            \n" +
                                      "           ,,,.,::;;,::,.  .,::;i:;,         ,::iLL1Ct,;i;;.    ...;;.::,  .,,:::,,,,,::,           \n" +
                                      "          ,::,.,..:;;;::;;;::::::.       .,:;;;;LCCLft; ,;:;,      ,i,.::,    ,::, .,,,,:,          \n" +
                                      "         ,,,,:,  ,,:;;;;i111i;iii;::::;i1i;;;:,fCGGCC1tt;;iii;.     ,::::;;,.  ,;:. .,..,,,         \n" +
                                      "        ,,,..:..,;:,:;,.:;11i;;ii;::::1ffLft1ifGttGfCf;fL11ff1i:,.   .,:i;;:::::::;::,::,,,,        \n" +
                                      "       .,,:::::,::. .::.;;:i:1ft;,,,;tft1fi;;tCCiL01iC; :1iLf1;;i;:,.   ,:;:,.,;;;i;::::..,,.       \n" +
                                      "      .,,..:,:::,   .;;:;; :1iifLfttffi;:.  ;CLGLf0GLGG:  .;t1i::,:;ii;;:,;i;;;i;:,:,:..,,:,,       \n" +
                                      "      ,,,,,, .,,:   ,;;;;. .ii;.itf,..     ;L1L,,f8GtCGL;   :i1t1i:,,;i1i;i1111i;. :;:  ,,,,,,      \n" +
                                      "     .,,,:,  ,::,  ,::,::   :;;.,Lf.      ;CtL;iCG0Gt.LfL;    .,itft1tCf1t;:1i;:,  ,:,, .,.,,,      \n" +
                                      "     .,,.,:.,:;, .::,.::,   :;::.ff,     i000CG8C1,f@L:ftL;       .ttti ;iiii,,:,   ,,::,:::,,.     \n" +
                                      "     ,,,,::;::;,:;:,,;;.    :;i,;1t,    1LG08Ct,    :0GtLLCi      ;ffi :;ii,:;;;:.   :::;:,.,,,     \n" +
                                      "     ,,,,:,:;i;:;;;;;,      ;i;1t1;   .ffCtC0:       .1C0000t.    ;ffi:;:;. .;:;;:. .::,..,.,,,     \n" +
                                      "     ,,,.,. ,:::;:.,;;.    ,1tfffi   ,fLfi10t          :0L0Cff,   11iL1;;    :;:,::,.::, .,:,,,     \n" +
                                      "     ,,::,. ,;:..::,:;:   .i;tL;ti  iLCf:i8f           .G8f;LtL;  1fLtti.    .;:,.:;:::, .,.,,,     \n" +
                                      "     ,,,.:..,::   :;;:;. .;:i,iLL:.fGCG1f00;,           LGL ;LLG111ti;i:      :;;;;;:;i;:,:,,,,     \n" +
                                      "     ,,,.::;::,   .::;::,1;;, 1tt1ftiG0G0CLG8C1i::i1ttLCCG0C,LCCLCt::;;,    ,;;,,;;,,:,:;::,,,,     \n" +
                                      "     .,,:::,:,:,   ,:,:iiii:.tttCGCtLGGti,:;t00G80CLftt1i1C8GG0t;GG1;,i:   ,:,.,:,. :;:..:..,:.     \n" +
                                      "     .,,,.,. ,,:,  ::;i1:ittfLfCCGGC11GGGLfffL080CLLftffLGC0fttCCGCCLi;:   ;:,::.  ,::, .:,:,,      \n" +
                                      "      ,,,,:,  :;: ,;11111i;i1fCLLLffLLffii;;;::;::::;;;;;iiiiitLCCCLLCti. ,;;;;.   :,,. ,.,,:,      \n" +
                                      "       ,,:,,..:,:::;;;;;;;,,:;iii;::,i1t;.                .:;1Lt11fLLti1: i::;:   .::;:,:.,,,       \n" +
                                      "       .:,..:;:;;i:;;,.,:;:,   .,::;i;tLL11;.      .:;iiitf1tf1:,,,;1fi:i;;:.::. ,::,:::,:,,.       \n" +
                                      "        ,,,:::,,:::::,:::;;i:,.    .:i1fft1LL1; .:1ft1iitffffi::;;iii;;111;:.:;:,:;,..:..,,.        \n" +
                                      "         ,,,..,. .:;,  .,:;::::,     .;i;i::ttt1tLt;,,:;;;ii;::::::;i;;i111i;;:;:,. .,:,,,,         \n" +
                                      "          ,,,,,,. ,::,    ,::.,i,      ,;:;, ;fL1:.:;;;:,..       ,;:,,:::;:::;;;:.,,.,,,,          \n" +
                                      "           ,::,,,,::::,,.  ,:,.;;,,...  .;;i;,fLi;1i:,.         ,;;i;::,.  .,::,;;::,.,,,           \n" +
                                      "            .,,..:;::,,:::. :;:;;::;;:::,:i1;if;;1:.   ...... ,;i::;;.   .,:;:..,,,,:,,.            \n" +
                                      "             .,,::,,,. .:;:,::;;,,,,:;i;:,:1;i1;;:,,,:::::::::;:.,:,    .:,,. .,:..,,,              \n" +
                                      "               .,,,.,:...:,:;i::;:,,,,,,::,;111i;;;:;i;:::,,,:;;,;;,.,:::::,.,,,::,,.               \n" +
                                      "                 .,,::,,,:::;::::,.      .,:1ii;..,:::,.....,:::;;;,:::,,:;::,.,,,.                 \n" +
                                      "                   .,,,.,::,,. .,:::,.,,.. .:;;;:;,.     ....,::;i;,.  .,,.,:,,,.                   \n" +
                                      "                     .,,:,,.,,,...::;:,,:;;,,:;;;::,,,,.:,::::,,:;:.,,,::.,,,,.                     \n" +
                                      "                       ..,:,,::,,,:;:,.. .,:,:i;,,,:::,;:,..  .,:::,,.,,:,,.                        \n" +
                                      "                          ..,,,:,,::,,,,,..,:;:,..  .,:;:,,,,,,,.,,:,,,,..                          \n" +
                                      "                              ..,,,,,,,::,,.,:,,,,,:,..,:,,,,::,,,,,..                              \n" +
                                      "                                   ..,,,,,,,,:,,,,::,,,,:,,,,,...                                   \n" +
                                      "                                           ..............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 5 ]  = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         .......,..........                                         \n" +
                                      "                                  ...,,,,,,,::,,,,:,,,,,:,,,,,,...                                  \n" +
                                      "                              .,,,,,,,,:,.,,,:,.,,,,,,,::,.,::,,,,,,..                              \n" +
                                      "                          ..,,,,,,:,.,,,....,;::,. ...,:;:,.,,,,,,:,,,,,..                          \n" +
                                      "                       .,,:,,:,.,,,:::.....,:;:::;:,,;;;:::.  ..,:;,.,,::,,,.                       \n" +
                                      "                     .,::,..,,....:;;,:;;::,,.....,:;;;:,,:;:,,,;:::.,,:,.,,,,.                     \n" +
                                      "                  .,,,,,:::::..,,,;;;:,..       ..::::;::. .,,,,,:::,  ..,,:,,,,..                  \n" +
                                      "                .,,::,.,,.;;::;;:,;;;;;::,,,,,,;;::..;;;1i,.     .,::,,,:::;,.,,,,,                 \n" +
                                      "               .,,,,:,.. ,,::..  ,:,.;;:,,,,::;i;;;;:;i1ii:::,,,...,;:;i;;,:.,,:,,,,.               \n" +
                                      "             .,::,,,,..,::,:    ,::,::;:::::,::,,...:;i1;1;,:;;;;:,,:;;:,:::,  ,,.,,,,.             \n" +
                                      "            .,,.,::;:,:;:..  .,:;;:;;,.   ...    .:1;;ti;11;,:;:;;:,:;;;, ,;:,..,,,:,,,.            \n" +
                                      "           ,:,,.,,.;;;:,..,,:,:,:;:,          ,,;LLiiLf,;i;i.  ..,,:i:.;:  .,:::::;:..,,,           \n" +
                                      "          ,,,::,. .:;;;:;;11i;;;;;........,::;;;LLLL1Lf: ,;:;.      ;;.,:,   ..::,,.,,:,,,          \n" +
                                      "         ,,,.,,..,:::;;,:;;111;i11i;;;;1t1ii;::fLCCGft1tt;i1ii,     .;:,,::.   .:;,  ,,.,:,         \n" +
                                      "        ,,:,,::,,;:. ::.,:;;i;11;:.,,:1ftfft1;tGLtGLG1;1LL1tL1i:,    .,:;;;;:,,.:::,..,.,,,,        \n" +
                                      "       .,,.,:,;::,   ,:::i, i;ifft;;ifLt11i;;fCGtLGfiL; .i1tLt;ii;:.    ,;;;:,,;;;;;;::::,,,.       \n" +
                                      "      .,,,.,, ,,:.   :;;:;  ,1i;1fft11i,.   ;LLGLtC0fCG:   :tti,::;;;:,. .:;:,,:;:;::,;:,.,,,       \n" +
                                      "      ,,,,:. .:,:.  ,:::;.  .;i: tLi       ;L1L,.i00LLGC:   ,11t;,,,:ii1;::;;ii;i;.,::, ,,:,,,      \n" +
                                      "     .,,..:..:;:. .,:,.::.   ;:; iC;      iGfCt1CG0GL.ffL;   .,;tff1:;tft1ii11i1i, .::, .,.,,,      \n" +
                                      "     .:,,,:;:,:,.:::.,;:.   .i:;.iti     iG08G08Lt,t8L,ftL;      .;tftC1;1i;ii;::  .:,,..,,,,,.     \n" +
                                      "     ,,,,:,:;;;:;;;:;;,     ,;;:;11:    1LCC8C1.    :0C1fLCi      .11t: :;i1;:,::   .::;::::,,,     \n" +
                                      "     ,,,.:. ::;;;:,:;:     .;iitft;   .ffL1f0i       .tGGGGGt.    ,fLf.:;;;..;;;;,   ,:,:::.,,,     \n" +
                                      "     ,,::,. ,::,::,,;i,    ;1fftf;   ,fLf;18t          :8L88Lf,   :fiff;;:   ;;;;:,  :;:..:,,,,     \n" +
                                      "     ,,,,:..:;:  ,:;;;:  .;;;tfif,  iCCf:18t            C0GtLtf;  1ffft1:    ,i:.,:::::. .,,:,,     \n" +
                                      "     ,,,.::;,:,   ,;:;;.,i:;,.fff..tCCGLL00;,           CGC ;LtG1i1f1ii;      :;:,:;;;;: ,, ,,,     \n" +
                                      "     ,,:::,:;::.   ::,:i1ii, it11iLfiGGCCLLG8L1;:;1tttLLGC0f LCCGLt,:;;,    .:;::i;::;;;:,:,,,,     \n" +
                                      "     .,,.,, .,,:. .::;1i;11;tC1fLCGfLGL1;,:iC0G80GLfttt11f08CGGt1GC1;,i    ,::,,::,.,:,:::,,,,.     \n" +
                                      "     .,,,,,. ,;:. ,i1i11iiittitCCCGCtfG0CLLfLG00CLLftfffCC0CttfCCGLLLi;   ,:,.,:,  .:;,..:.,,,      \n" +
                                      "      ,,,,,, ,::,.;;;ii;;,:;itCCLfffLLff;;;;;;;:::::;;;;;iiiiitfLCLLLCt   ,i:::.  ,,,,  ,::,,,      \n" +
                                      "      .,,,.::;::;;:;:,,:;,. .,:;i;;;,i1t,                  .:111tffti1t, .;:i;:   .:,, ,,.,,,       \n" +
                                      "       .,,,::,:;;;:;:,,:;;;,    .,:ii;tLt1i,.      ,:;iii1t1fLt;:;1ff;:;.:i:::,  .,::;,:,,,:.       \n" +
                                      "        ,,,,.,. ,::,..,:;;;;::.    .:11Lt1CL1i..:1ft1ii1tfftfi:,,,:;11;1;;:..::.,:;,,::,,:,.        \n" +
                                      "         ,:,.,,. ,;:.   .::,,:;.     ,iii;:111ftLt:,,::;;;11i;;;;i11ii111;;;:;;:::, .:..,,,         \n" +
                                      "          ,:,:,,.:,::..   ,:,.;:      .;:;, ;fL1:,:;;;::,,.. .   .;::;;i11;;:;;;:. .,::,,,          \n" +
                                      "           ,,,..:;:::::,.  :;.:i:,:..  .i;1;,Lfii1i:,.         .,:;:::,,,...,::;;.,,.,,:.           \n" +
                                      "            .,,,:,,...,:;, ,;;;:,:;;:;:,;1i;1t:;i,    ....   .:i;;;;,.   .,:;:,:;::,,:,.            \n" +
                                      "             .,,,,.,,  ,:::,:;;:,,:;;;;:,;1i1i;:..,,:::,,::;:;:,,::,   .:,:,. .:,.,:,,.             \n" +
                                      "               .,,,,:,,.::;;i;:;,.....,:::111i;:;;;;i:::,,,,:i;.:;, ..,::,. ..,:.,,,.               \n" +
                                      "                 .,,,..:;:,,,,,::,.     ..i1;;:.,::;:,,,,,,:::;;;;,:;;,:;;.,,.,::,.                 \n" +
                                      "                   .,,,,,,,..  ,:::,,,,,. .::;::;,.      ....,:;;;,,...:::::,,,,.                   \n" +
                                      "                     .,,,,.,:,,.::;:,,,:;::,:;;;::,,,.,,,::;;:,;;,....,,.,,:,,.                     \n" +
                                      "                       ..,,:,,,.,;,,..  .,::;;:,,:::,:;:.....,:;:,,,.,:,,:,..                       \n" +
                                      "                          ..,,,,,:,,,,,,,,:;:,... .,:;:,.,.,,,,.,:,,:,,,.                           \n" +
                                      "                              ..,,,,,,:,,.,::,,,,:,,.,:,,..,:,,,,,,,..                              \n" +
                                      "                                   ...,,,,,:,,,,,:,,,,:,,:,,,,...                                   \n" +
                                      "                                          ...............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 6 ]  = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         .......,..........                                         \n" +
                                      "                                  ...,,,,,,,,:,,,,,:,,,,::,,,,,...                                  \n" +
                                      "                              ..,,:,,,,::,.,,,:,.,,,,,,,::..,,:,,,,,,.                              \n" +
                                      "                          ..,:,,,,::,,,,.....,;::.. ...,:;:,,,,,.,,:,,,,..                          \n" +
                                      "                       .,,,,,::,,,,,;:,,,...,:;::;:,,:;;;,:,. ...,::,.,,::,,.                       \n" +
                                      "                     .,,::,.,,.....;;:,::::,,,....,:;;;;,,:;;,,,,:::,,,:,..:,,.                     \n" +
                                      "                  .,,,,.,::;:,,,,,:;;;,,.        .,::,;;:,  ,,,,,::::. ..,,,:,,,,                   \n" +
                                      "                .,:,::,,,.,;:::::,:;;;;;::,,,,,:;;:,.,i;;1;.      .,::,.,,,::,.,,,,                 \n" +
                                      "               .,,,.,,.  .,,:..  .::.,i;::,,,,:;i;;i::;i1i1::,,.....:;:ii;:::,,,:,,,.               \n" +
                                      "             .,,:,,::,.,::,,.   ,;;::;;:::::,,:,,.. ,:;11i1:,:;;;::,,:;;:,:::  ,,..,,,.             \n" +
                                      "            ,,,..:,:;:,;:.   ..:;;;;i:.    ..     .;i:iti;1i::;;;i:,,;;;: ,:;,. .,,,::,.            \n" +
                                      "           ,,:,,,, :;;:,,,:;;:::,:;,          .,;LL1itC;:ii;;  ..,::i;,::. .,,:,:::;,.,,,           \n" +
                                      "          ,:,.:,  .:;;;;;i;11iiiii:,,,..,,,::;;;LCLfifL1. ;:;:      ,i.,::   .,:;,:,,,,,,,          \n" +
                                      "         ,,,,,:,.:;:,;;,,;;;11i;i1i;;;;ttti;;::LLLCGL1t1ti;1ii;      ;:.,::.   .::: .,,,,,,         \n" +
                                      "        ,,,:::;;,::. ,:,,:;,;;;fi:..,:1f1fLttitCLfGLGi:itCt1L11;,    .,:::;;:,. ,::, .,..,,,        \n" +
                                      "       .,,..:.,::,   ,;;:i: ,i:1Lf1i1tLt11i::fGGfL0fiL: .:11Lt;ii:,.   .,;;;;,::;;:;:,,:::,,.       \n" +
                                      "      .,,,,,. .,,,   :;;;:   i1i;tffii;,.   ;LfGL1L8tLG:   ,tti,::i;:,.   ,;;,.:;;;;;::;:.,,,       \n" +
                                      "      ,,,,:, .:::.  ,:,,;,   :ii iLt       ;L1L:.;G0CC0C:   ,111:,,,;iii:,,::;;;;:,.:,:.,,,,,,      \n" +
                                      "     .,,,.:,,,;:  .::,.::.   ;:;.:L1      iGLGftCG00L,fLf;   .:itfti:;it1iii11ii1: .:;, .:,,,,      \n" +
                                      "     .,,:::;:::,,;;:,:;:.    i:;.i1i     iC08GG8Lt:18L,ftL;      ,ifftLfi11;i1ii;. .:,,  ,..,,.     \n" +
                                      "     ,,,.:..;;;;;;;;;;.     ,;;:;11:    1LCf0G1.    ,0GitfCi       1111 .ii1i;,::   ,:,:,,:,,,,     \n" +
                                      "     ,,,.,. ,::;;:.,;:.    .iiitft:   .fff1t0i       ,fGCGGGt.    ,ffL..i;i;:::::,   ,::;::,,,,     \n" +
                                      "     ,,::,  :::.,:::;;,   ,i1Lf1L:   ,fLf;18t.         :8C08CL,   ,f1ffi;;,  ;;;i:.  :;,,,:.,,,     \n" +
                                      "     ,,,.:,,,;,  .;i;;:  :;;itftf.  iGGL:t@t            C0GLLtf;  iLtff1;.   ,i;,,:,,::, .:::,,     \n" +
                                      "     ,,,,::;::,   ,;::::ii;; ,Ltt .tLCGCC00;:          .GGG ;LtC1;tf1ii;.    .:;,.:;;;:. ,,.,,,     \n" +
                                      "     ,,:,:,.:,:,  .:::;i1i; ,t1t;;LfiGGLLfLG8fi;:i1tttfLGC01 LCGGLt,:;;,     ,;;;;;;;;;;.,,.,,,     \n" +
                                      "     .:,.,,  ,,:. .;ii1i;111LL1fLCCfLGLt:,:100G80Cfttttt1L00LGGttCCt;:i    .:;,,:;:.,:,:;:::,,.     \n" +
                                      "     .,,,,,. :;:  ;iii1iiii1ti1CLCCCtLGGCLffC00GCLLffftLGC0LffLCCGLLL1:   ,:,.,:,. .:;,,,:.,,,      \n" +
                                      "      ,,,,,,,:,:,,;;;;;:,.,:;tCCLfffffLf;;;;;;;::::;;;:;;iiiiittLCLfCC1   :;,::,  .:::. ,:,,:,      \n" +
                                      "       ,,,.:::;;;;;;,.,;;,   .,:i;;;,itt,                  .,iiifftiiti  .;;;;,   :,,. ,,,,,,       \n" +
                                      "       .,,:,:.,:;::::::;;;;,.    ,:ii;fL1t;..     .:;;;;;111fLti;1fL1:i. ;;:;;,   ,::,.:..,,.       \n" +
                                      "        ,,,..,. ,:;, .,:;:::::.    ,;1tL1fCt1; ,;tt1i;i1tfftL1:,.,:it;i;,;:.,:, ,::,;;:,:,,,        \n" +
                                      "         ,,,,:,. :::.   .::,.:;      ;ii1:;tiftffi,,,:;;;i1t1;;;ii1i;i11;;;,:;;,::,.,:.,,:,         \n" +
                                      "          ,:,,,,,::;:,.   ::,,i,      :;:; .tLf;,:;;;;::,,.......:i;iii11;i;;;;;,. .,:,,,,          \n" +
                                      "           ,:,.,;:,,,,,,. .;:,;;:::..  ;iii:iL1i11;,.          .,;:,:::::,..,:;;, ,,.,:,.           \n" +
                                      "            .,:,,,,. .,;:, :;;:,,:;;;;::i1;it;;i:     ....   ,:i:;;:,.   .,:;,:;:,:.,,,.            \n" +
                                      "             .,,,..,,. :,:::;;::,::;;;:,;1i1i;:...,,::,,::;::;:,:::.   ,,,::,.,:,,::,,              \n" +
                                      "               .,,::,,,:::;i;:;:......,::111i;::;;;i;:,,,,,:;i,.::. ..,:,,. .,:,.,,,.               \n" +
                                      "                 .,,,.,::,,..,:::,      .;1;;;,.,:;:,,,,,,,:;;;;;:,:;:,;;..,,,::,,.                 \n" +
                                      "                   .,,::,,,,. .:,::::,,,. ,:;;:;:,.     .  .,,;;;:,,,.,::::,.,,,.                   \n" +
                                      "                     .,,:,,::,,:;::,.,,;;:,:;;;;:,,,..,,,:;;:,:;:....,,..,:,,,.                     \n" +
                                      "                       ..,:,,,.,:,,,.. .,::;;;,,,::::;:,.....:;;,,,.,::,:,,.                        \n" +
                                      "                          ..,,,,:,,.,:,,,:;:,... .,:;:,....,,,,,::,,,,,,..                          \n" +
                                      "                              ..,,,,,:,,..::,,,,:,,.,::,,,,:,,,,,,,,..                              \n" +
                                      "                                   ...,,,,:,,,,,:,,,,::,,,,,,,,..                                   \n" +
                                      "                                          ...............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 7 ]  = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         ........,.........                                         \n" +
                                      "                                  ...,,,,:,,,,:,,,,::,,,,,,,,,....                                  \n" +
                                      "                             ...,:,:,,,,::,.,,,:,.,,,,.,,:,.,,::,,,,..                              \n" +
                                      "                          .,,,:,,.,:::,,......,;::.  ..,,:;,,,,,..,,:,,,..                          \n" +
                                      "                       ..,,,,,::,,,.,;:,,,...,:;,:;:,,:;:;,:,  ..,,::..,,,,..                       \n" +
                                      "                     .,:,::,,,,. ..:;;:,:::,,,,....,:;;;:,,:;:,..,:::,,,:,,,:,.                     \n" +
                                      "                  .,,:,..:,:;:,::::;;;:,.        .,,:::;::  .,,,::;,:,  ,,,.,,:,..                  \n" +
                                      "                .,,,,::,,. :::,:,,,;;:;;;:,,,,,,:;;:,.:i:;i:      .,:;,...,,::..:,.                 \n" +
                                      "               .,:,..,,  .,,:,    ::, :i::,,,,::;;:i;::;i11i:,,.....,;:;i;::;:,,,,:,.               \n" +
                                      "             .,:,:::::,:;;:,.   .;;::;::,::::,::,..  ,:i11i1:::;;;::::;;;::,:, ,:,.,,,.             \n" +
                                      "            .:,,.,,.;;::,. . ..,::;;;:,     ..    .,;;:11ii1i::;;;;,.:;;:..:;:. .,,,,,,.            \n" +
                                      "           ,,,:,,. .;;;:,,:iii;;,:;,          ..:LL1iiff,;iii, ..,::;i::;. .:,,,,,:::,,,,           \n" +
                                      "          ,,,.,,. .::;;;;i;;111ii1i:::,,,,,::;;;LCLL;1Lf: .;:;.     .i:.:,. ..,:;::;:,,,,,          \n" +
                                      "         ,,:,,::,:;:,:;: :;;;1iiiii;;:;tfti;;::fCLCGL1f1t1:11ii.     :;.,::.   .:::..,:,,,,         \n" +
                                      "        ,:,,:,:;::,. .::,;;,.i:1t;,..:tL1tLftitCLfGLG1.11CL1Lt1i,    .:::::;:,. .::, .,..,:,        \n" +
                                      "       .,,..:..:,:   .;;;;:  ;i;fLf11tLf1i;::fGGLL0LiC: .,11ff;ii:,    .,;;:i::::::::..,,:,:.       \n" +
                                      "      .,,::,. .,,,   ,::;;   ,11:itfi;;,    ;ftGLtt8tfG;   ,tti,;;i;,.    :;;,.,;;;i;;:::,,,,       \n" +
                                      "      ,,,.,, ,::,.  ,:,.::   ,;i,,Lf.      iL1L: :C0GC0C:   .11i:,,:;i;;,. ,:;;:;::,:,:::,.,,,      \n" +
                                      "     .,,,,:::,;, .,:,.,::.   :;::.ft      ;GC0LfCC0GC:fLf;   .:ift1;,;i1i;;;;1;;i;  :;: .:::,,      \n" +
                                      "     .,,,:,,;;:,,;i:,;;,.    i:;.;11.    iCG0GG8Lt:18C,ftLi     .;tft1fLt11ii1i11, .::,  ,,.,,.     \n" +
                                      "     ,,,.,, :;;;;::;;;.     :i;:;11:    tLCt0Gi.    ,0GitfCi       ;t1f: ;iiii;::   ,,,,.,,,,,,     \n" +
                                      "     ,,,,,. ,::;;:.,;;.    :iiitL1:   .tLf1101       ,fGLCCGt     .ttf: :;;i;:,::.   ,::::::,,,     \n" +
                                      "     ,,,,:..:;: .::;;;,   :itLt1L:   ,fLf;18t.         :8CG8GL,   .ftfti;;:. ;;:;:.  ,:,:::.,,,     \n" +
                                      "     ,,,.:::,;,  .:;:;: ,;;;;tfft   iGCL:t@1            C0GCLff;  ;L1ff1i,   ,i;;::..:;: .:,,,,     \n" +
                                      "     ,,,::,:::,   .::,;i1;i, ;t1t .tLL0GC0Gi:          .GCG ;LtL1;tftiii.    .;:,.:;;::. .,,,,,     \n" +
                                      "     ,,,.,, ,,,,  .::;iiii;.if1f;;CfiGCLLfLG8fi;:i1tttfLGC0; LCCGLt::;i,     .;;;::;;i;: ,,.,,,     \n" +
                                      "     .,,.,,  :::  :11111i11tLt1tCGCLCGft:,:f0GG8GLfttttttC80fGCftCCt;:;    .:;:,:i:,,::;::::,,.     \n" +
                                      "     .,,:,,..:;: .;;;ii;;;;i1;1CLCCCfLGGLLffG00GLLLfttfLCG0ffLCCCGLLL1,   ,::.,::. .::,:::.,,,      \n" +
                                      "      ,,,.,:::::,::;::::, .,:fCCLfffffLt;;;;;;:::::;;:;;;ii;iittCCfLCCi   ::.::,  .:::. ,,.,,.      \n" +
                                      "       ,,,,::,;;i;;;,.:;;:   .,:;i:;,it1.                   ,;;ift1;1t:  .;;:;,  .:,,. .,,:,,       \n" +
                                      "       .,,:,,..,:::,:::;:;;:.    ,;ii;ff1t:,      ,:;;;;;111LLt11fLf;i:  ;;;;;.   ::: .:.,,,.       \n" +
                                      "        ,:,.,,  ,;:. ..:;::,::.    ,i1fL1LL11,.:1tt1i;i1fLttL1,..,:f1:i.:;:,::. .:::;:::,,:.        \n" +
                                      "         ,,,,:,.,:::.   .::,.;:     .ii1i:i11ftLt;,,,:;;;ittt;;;;iiii11;;;: :;:,;;,,::,,::,         \n" +
                                      "          ,,,..:;::::,,  .::.:i.     .;:i. :LL1:,;;;;;::,,,,,,,,:i1ii111;;i;;;;:,. .:,.,,,          \n" +
                                      "           ,,,,::,,.,,,:. ,;::i;:;,,. ,ii1;:Lti11;,..          .,;,,;;;i;,,,:;;;..,,,:,,,           \n" +
                                      "            .,,,.,,. .:;:..:;;,.,;;;;::i1ii1i:;:.    ....   .,;;;;::,.   ..:::;:.:,.,,,.            \n" +
                                      "             .,:,.,:,.,::::;;;::::;;::,:ii11i:....,::,,::::,;::,:;:.   ,,:;:,,:::::,:,              \n" +
                                      "               .,:,,,,:;::;;:::,    ..,:11i;;::;i;i;::,,,,::i: ,:,   .:,,,  .,,..,:,.               \n" +
                                      "                 .,,..::,,...,;:,.      :i;:i:.,:;;:,,,,,,:;;;:;;,,::,::, ,,,::,,,.                 \n" +
                                      "                   .,:,,.,,,..,::;::,,:.  ,:;:::,.         .,:;;;,::,,:;:,:.,,:,.                   \n" +
                                      "                     .,:,,,:,,,;::,..,:;:,,;i;;:,.,..,,,::::,:;;,.. .,,.,::,:,.                     \n" +
                                      "                       ..,,,,.,::,,..  ,:,;:;,.,:;:,;:,...,,,:;,.,,,::,,,,,.                        \n" +
                                      "                          ..,,:,,,..::,,,;:,,..  .::;,.....,,,:::,,,,:,,.                           \n" +
                                      "                              ..,,,,:,,,.,:,,.,:,,.,:,,,.,::,,,::,,,..                              \n" +
                                      "                                   ...,,,,,,,,,:,,,,::,,,,:,,,...                                   \n" +
                                      "                                          ...............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 8 ]  = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         .........,.......                                          \n" +
                                      "                                  ...,,,,,:,,,,:,,,,::,,,,,,,,,...                                  \n" +
                                      "                              .,,,,,:,,.,::,.,,,:,.,,,,,,,:,.,,,:,,,..                              \n" +
                                      "                          ..,,,:,..,,:::,......:;:,. ....,:;,,,:,..,,:,,..                          \n" +
                                      "                       .,,:,,.,:,,,..:;:,:,,,,:;:,:;:,,;:;:,:. ..,,,:,.,:,,,.                       \n" +
                                      "                     .,,,,::,::. ..,;;;,,:::,,,. ...:;;;;:,:;;,...,:::,,,:,,,,.                     \n" +
                                      "                  .,,::,.,,.:;,,;:::;;;,,.        .,::::;:. .,,,,:;;,:...,,.,,,,.                   \n" +
                                      "                .,,,,,:,.. .:::,,..:;::;;::,,,,::;;;:.,;;:;;,     .,:;:. ..,,:,,,,.                 \n" +
                                      "               .,:,,,,,...,,,:.   ,::.,i;::,,,,::i;;i;:;;;i1i,,.    .:::;;:::;,.,:,,.               \n" +
                                      "             .,,,,:,:;:,;;:,.   .:;;:i;:,,:::,,::... .,:i1iii::::;;:::;;;;::,:.,,:,,,,.             \n" +
                                      "            .,:,.,:.,;::,. .,.,,:::;;:..     .      ,;:;11;i1;:;;;;:..:;:,.::;. .,,.,,,.            \n" +
                                      "           ,:,,:,.  ;;;;:,::11ii;:;:.          .:LLi11iLi:iiii...,;::;;:;:  ::,,..,,:,,,,           \n" +
                                      "          ,,,..:..,::;;;::i:;11i;11;;::::::::;;;LCLL:ifC1  ;:;:     .;;.::. .,,::::::,.,,,          \n" +
                                      "         ,,:,,:;:,;:..:: ,:;,iii1;;:::;fft1i;::fCLLGCttt11;;tii:     ,;..,:.   .::,:.,,:,,,         \n" +
                                      "        ,,,.,,.:::,   :;:;;: ,;:f1:,,:tLttLftifCLtGLCt iifCtffii:    .::,,::,.   :;:  ,,.,,,        \n" +
                                      "       .,:,,,. ,,:.  .:;;;:  .ii1LLftttf1;;,,fGGCL0L1C; .,ittL;ii;,    ,:;;:;;:,,:::,..,.,,,.       \n" +
                                      "      .:,,,:. ,:,,.  ,:,:;.   i1;:1f1::.    ;ftGft18ttC;   .1f1,;;;;,    .:;;:.,:;;i;:,,::,,,       \n" +
                                      "      ,,,.,:.,;;,  .::,.,:   .;;; fL,      iC1L: :L0GC0C;   .11i,,,:;i;,.  ,:::::;;::::;:,.,,,      \n" +
                                      "     .,,:,::;,:, .::,,,::.   ,;:;.tf,     ;GC0CLCC00C;fCL;   .;1t1;:,;i1i:::,i;:;;. :::,.:,:,,      \n" +
                                      "     .,,,:,.;;;::;i;:;;,     i;;.:11,    iCGGGG0Lf;i0G,ftLi    .,ifL1itLt11i11ii1;  :::  ,,,,,.     \n" +
                                      "     ,,,.,, ,;;;;:.:;;.     :i;:;t1:    tLC1C0i.    ,GG;tfCi       ;f1L1,;1i;iii;. .:,,. ,,.,,,     \n" +
                                      "     ,,:::  ,:::::.,;;.   .;i11tL1,   .tLfii0t       ,fGfLCG1     .titi .i;11;,,:.  .::;,,:::,,     \n" +
                                      "     ,,,.:,,:;,  :;i:i,  ,;;fL11f.   ,LCf;18t.         :0GG0GL,    tfft;;;;:.;;:;:   ,:,;;:.,,,     \n" +
                                      "     ,,,,:;:::.   :;:;:,:;;;:tff1   iGGL;f@i            L0G0CfL;  :f1tLti;.  ,i;;;:  :;:,.:.,,,     \n" +
                                      "     ,,:,:.,:,:.  .:,:i11;;  1111 ,tff00G0Gi:          .GCG,;LtL1:1Ltiii:    ,;;,.:::::, .:::,,     \n" +
                                      "     ,,,.,, .,,:  ,ii1i;i1;:fLtf:;CL1GLtffLG0ti:;i1t1tfL0CG: LCC0Ct::;i,     .;;;,:;;;;, ,,.,,,     \n" +
                                      "     .,,,:, .::, .i1ii11ii1tf1itGGCLCGf1,,:C0C00CLfttttftG8GtGCLtLCt;;;    .,;;:;i:::;;;.,,,,:.     \n" +
                                      "     .,,,,,.,::, ,;;:;;,,::i1;1LLCCLfCGCLLfLG00CLLffttfCG0GffLCCCGLLL1.   ,::,.::,. ::,;;:,,,,      \n" +
                                      "      ,,,.,:;::::;;::::;,  .,tCCLfLffLL1;;;;;;::::;;;:;;;ii;i11fCGfLCCi  .:,.,:,  .:;;,.:,.,,,      \n" +
                                      "       ,,,::,,:;;;;:,,;;;:.  .,,;i:;,1fi.                   ,::1f1;i11.  .i:::,  .,,:. .:,,,,       \n" +
                                      "       .,,,.,. ,:::.,:;;:;::,    .;iiiLtti,,     .::;;;:;ii1Ltt1fLLiii   ;;;;:   .:,, ,,.,,,.       \n" +
                                      "        ,,,.,,  :;:   .,::,,::     ,i1LttCf1i.:;tt1i;i1tfL1tL1,..:1f:i, :;;:;:  .,:::.:,.,,.        \n" +
                                      "         ,,,,,,,:,;:.   .:,.,i,     :ii1::11ftffi:,,::;;;1ttt;:;;iiii1i:;:,.::.,;;,:;:,::,,         \n" +
                                      "          ,,,.,;:::::,,  .::.;;..    :;:;  1Cf;::;;;;;:::,,,::::;11;i11;:i;:;;;::...:..,,,          \n" +
                                      "           ,,,,:,,..,,::  :;:;;:;;,,..ii1i:1fi11i:,.           .:;:;ii1i::,:;;;:  .,:,,:.           \n" +
                                      "            .,:,.,,. ,;::.,:;:..:;;i;:;1i;11;::.     ...    .,;;;:::,...  .,::;,.:..,:,.            \n" +
                                      "             .,,,,:,,.:,;:;;;;::::;::::ii11i:....,,::,,:::,::;;:;;,.  ..,:;;,:;:,:,,,,              \n" +
                                      "               .,,,,.:;:,:;:::,.    ..,i1i;;;:;i;;;::,,,,::;;..::,   .:,,,. .,,,,,:,.               \n" +
                                      "                 .,,,,:,,.. .:;:,....  ,:;:;;..:;;:,,,,,,:;;;::;:.,:::::...,,:,,,,.                 \n" +
                                      "                   .,,:,.,:..,:,;::,,:,. .:;::;:,.        .,:;;;::::,,;:.:,.,,:,.                   \n" +
                                      "                     .,,,,:,,,::,,...,;;:,:i;;::,...,,,,:::,,;;:..  ,:,,::,:,,.                     \n" +
                                      "                       ..,,:,,::,,,.. .:,;:::,,:;:,;:,...,,,:;,.,,,,:,.,,:,..                       \n" +
                                      "                          ..,,:,,..,:,,,;:,.... .::;,......,,:,:,,,,:,,,.                           \n" +
                                      "                              ..,,,:,,,.,:,,.,::,.,:,,,.,::,,,,:,,,,..                              \n" +
                                      "                                   ...,,,,,,,,:,,,,,:,,,,,,,,,...                                   \n" +
                                      "                                           ..............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 9 ]  = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         ..........,.......                                         \n" +
                                      "                                  ...,,,:,::,,,,:,,,,::,,:,,,,,...                                  \n" +
                                      "                              ..,,,,,:,..,::,.,,,:,,,:,..,,:,.,,,:,,,.                              \n" +
                                      "                          ..,:,,:,.,:,:::,......:;:,. ....,::,,,:,.,:,,,..                          \n" +
                                      "                       .,,,:,,.,,,....:;:::,,,,:;:,;;,,:;:;:::. ..,,,:,,,,,..                       \n" +
                                      "                     .,,,,,:,::,....:;;:,,::,,,,. ..,:;;;;,,:;:,...,,;:,,,:,,,.                     \n" +
                                      "                  .,,,:,,.,..;:,:;::;;;:,..       .,,:::;:,  .:,,,:;:::..,,..,,,,                   \n" +
                                      "                 ,,,,.,,..  ,:;,,..,:;,:;;::,,,,::;;;,.,i:;::.  ....::;,  .,,,:,,,,                 \n" +
                                      "               .:,:,,::,.,,:,,,   .::,.:i;;:,,,,:;i:;;:;;;;i1i,.     ,:::::,,::,.,,,.               \n" +
                                      "             .,,,.,:,:;,:;:,.   .,;;;;i::,,:::,,:,.. ..,;i1iii::::::::;;;i:;:::,,::,,,.             \n" +
                                      "            .,::,,,..;;;,.  ,;::::,;;:..            .,:;i11;11::;;;;,.,;;:.,::: .,,.,,,.            \n" +
                                      "           ,,,.,,.  ,;;;;:;;:11ii;;;,           ,Lf;i1i1f:;iii:.,,:;:;;;;:  ,::,  .,,,::,           \n" +
                                      "          ,:,,,:,.:;::;;:,i;:i11;itii;;;;;::;;;;LCCL;;tLL, .i:i.   ..:i,:;.  ,,,::,,::.,,,          \n" +
                                      "         ,,,::,:;,::. ::,,:;:.i;1i::,,:fft1i;::fCLLGLftL11i:11i;     .i,.,:.  ..,;:::,,,:,,         \n" +
                                      "        ,,,.,: ,::,   :;;:i:. ;:ifi;,:tLf1LLt1fCLtCCCf.;11CL1L11:.    ::,,,:,.   :::. ,:,,,,        \n" +
                                      "       .,:,,,. ,,,,   :;:;:   :1itfLft1fi;:,,tCGCL0LtC; .,;t1Li;i;,    ,::;:;;:,.,::. .,..,,.       \n" +
                                      "      .,,,,:..,::,.  ::,,;.   ;1i.1ft,,.    ;f1Cfti0ftC;   .if1,;;;:.    .:;;;,,:;;;;:..,,:,,       \n" +
                                      "      ,,,.,::,:;,  .:,,.,:.   ;;; 1C;      iCtL; ,f80C0G:    11i,,:;;;:.   ,;::,:;;;::::::.,,,      \n" +
                                      "     .,,:::,:::,.,;;,,:;:.   ,i,;.1f;     ;GG0GCGC00CitGL;   .;iti:,:iii;,,..;;:;;, ,:,:,:.,,,      \n" +
                                      "     .,,.,, :;;;;:;;:;:.     i;;,:11,    iCGG0CGLf;i0G,ftfi    .:1fL1;1ttiiii1i;i;. ,;;. ,:,,,.     \n" +
                                      "     ,,,,,, .:;;;: ::;.    .;i;:it1:    tLC1L0i     .GG;tfCi      .iLtLf;;1iii111:  :,,. ,,.,,,     \n" +
                                      "     ,,,,:..:::.,:::;;.   ,;111fLi.   .tffii0t       ,LGffCC1     .1111  ;ii1i;::.  ,,,:..:,,,,     \n" +
                                      "     ,,,.:::,;,  ,;;:i, .:iifL1ft    ,LCf;18t.         :0GC0Gf,    1fft:;;;;:;:,:,   ,:::::,,,,     \n" +
                                      "     ,,,::::::,   ,:,:;;i;;:,fff1   iGGC;f@i            f0C8GfL;  ,fttLfi;,  :i:i;,  ,;,:::.,,,     \n" +
                                      "     ,,,,:..:,,,  .:;;i1ii: ,ti1i ,tff0000Ci:          ,GL0:;Ltf1:1Lt11i;.   ,;;:,::,::: .:,,,,     \n" +
                                      "     ,,,.,, .:,, .;111iii1iiLLtL;;LL1Gf1ttLG0ti:;11t1tfL0CG. fCC0Ct::;i:     .;;:.:;;;:. ,,,,,,     \n" +
                                      "     .,,::, ,;;, .;;:i1;iiitt1:tGGCfCGft,,i00C80CfttttfffG8C1GCCtCCt;;;     .;;:;;;;;;;: ,,.,,.     \n" +
                                      "     .,,,.:::,:,.:;;;;:..,,;ii1LLCCLfGGCLffCG0GLLLfttfLCG0CfLCLCLCLLL1.   .::,,:;:..,::;::::,,      \n" +
                                      "      ,,,.::::;;;;;,,,:;,   .tCCLfLffLLi;;i;;:::;:;;::;;iii;i11LGCfLCCi  .:,.,:,.  ,;:,::,.,,,      \n" +
                                      "       ,,:,:..:;;;;:,:;;;:.  .,,;i;;,1fi                    ,,:ft1:11;.  ,;,::,  .,::, .:,,,,       \n" +
                                      "       .,,..,. ,::,.,:;;::::,    .;i;1L1f;:,     ,::;;;::;itfttfLLti1,   ;;;;:   ,,,. .,,:,,.       \n" +
                                      "        ,,,,:, .:::    ,:,,,::    .:11L1LL11;,;1tt1i;i1tff1fL1,,:ifi:; .:i:;;,   :::..:..,,.        \n" +
                                      "         ,,:,,::::;,..  .:,.,i      ;i1i,;11Ltft:,,,::;;;1tft:::;:i1ii,;;:.,::.,::,;::,:,,,         \n" +
                                      "          ,,,.::,,,,,,,  .;:,i:..   .i,i, ,LL1::;;;;;;::::::;;;;iti;11i:;i,:;;:::,.,:,,,:,          \n" +
                                      "           ,:,:,,.  ,:;,  ;;;;::;:,,.;iiii;fii1i;,..           ,;;;ii11:;::;;;;,  .:,.,,.           \n" +
                                      "            .,,,.,,. :::,.:;;,.,;;;;::1i;11i::.      ..     .,:;;,:::,:.  .,:;:..,.,::,.            \n" +
                                      "             .,,,,,,,:::;;i;;;,,::::::iii1i:.....,::,,::::,::i;;;;,   ..,:;:,;:,:,.,,,              \n" +
                                      "               .,,,.,::,.:::::,     ..i1i:;;;:;;;i::,,,,::;i:.,::.   ,,,,,..,:,,,:,,.               \n" +
                                      "                 ,,,,:,,,.  ,;::,.... .,:;;;,.,;;::,,,,,::;;::;;,.,,,;:, ..,,,.,,,.                 \n" +
                                      "                   .,,,..:,,.:::;:,,::,. ,;;:;:,.         .,;;;;::;:,;:.,,.,,:,,.                   \n" +
                                      "                     .,,:,,,,:;,,....:;:::;i;;:,....,,,::::,:;;,....:::,:,,,,,.                     \n" +
                                      "                       ..,:,,,:,,,,...::::,:,.,;;,:;:,..,:::;:....,:,.,,:,,.                        \n" +
                                      "                          ..,,,:,.,:,,,::,.... .,:;,......,::,,:..,:,,,,..                          \n" +
                                      "                              ..,,,,,,,,:,,..::,.,:,,,.,::,.,,:,,,,,..                              \n" +
                                      "                                   ...,,,,:,,:,,,,,:,,,,:,,,,,...                                   \n" +
                                      "                                          ...............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 10 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         ........,,........                                         \n" +
                                      "                                  ...,,,,:,:,,,,::,,,,::,,,,,,,...                                  \n" +
                                      "                             ...,,,,,,:,,.,:,..,,,:,,,:,.,,:,,.,,,:,..                              \n" +
                                      "                          ..,,:,::,.,,,:::,......:::,. ....,::,,::,.,,,,..                          \n" +
                                      "                       .,,,::,.,,,.....::,::,,,,:;::;:,,::,;::,..,,,,,,,,,,..                       \n" +
                                      "                     .,:,,.:,,::,...,;;;:,,,,,,,,....,;;;i::::;,....,::,.,,,:,.                     \n" +
                                      "                  ..,,,:,,,,.,;:,;;::;;;:,.        .,::::;:. .,:,,,:;:::.,:,.,:,..                  \n" +
                                      "                .,,:,.,:,.  .,::.. .::,,;;;::,,,:::;;;..:;:;,,. ...,::::. .,,,,,:,.                 \n" +
                                      "               .,,,:,:;:,::::,,   .:;:.,;;;:,,,,,:;;;i;:;;:;i1;.     .:::::..,::.,:,.               \n" +
                                      "             .,:,..:..;:,:,...  .,:;;;i::,,,::,,:,,.. ..,;11ii;::::::,:;;i;:;,;:,,:,:,.             \n" +
                                      "            .:,:,,,. :;;:....;i;;;,:;,.              .,:;i1i;1i:;;;;;.,:;:,,:::..,:..,,.            \n" +
                                      "           ,,,.,:. .,;;;;;;;::11iiii:,..        ,Lf:i11iti;i1ii,,,:;:::;;;. .::,  .,,,,:,           \n" +
                                      "          ,,:,,::,:;:,:;: :;;;i1i;11ii;ii;;;;;;;LCCL;:1fCi  ;:;:   ..:i:,;,  ,,,,,.,::,,,,          \n" +
                                      "         ,,,,:,,;::,. ,::,::; :i;t:,..:fftti;;:fCLLCfftft11:;tii,     ;:.::.  .,,:;:;:,.,,,         \n" +
                                      "        ,:,..,. :,:   ,;;;i:  .i:fti;;1fL1fLt1fGLtCCCf,:1ifCtLt1;.    ,:,.,:,    ::::.,,,,:,        \n" +
                                      "       .:,::,  ,,,,   :;,;:   .1iiffLtit1:,,.tCCGL0CtC; .,:ttf1;i;,    ,::::;;,, .::, .,..,,.       \n" +
                                      "      .,,,.:,.:;:,   ,:,.:,   ,ii,;ff:..    iL1CftiGL1C;    ;Lt,;;;:.   .,:;:i;:::;:;:..,,:,,       \n" +
                                      "      ,,,,::;,::.  ,:,.,::.   :;;,:C1      iCfCi ,100CGG:    i1i.,:;;:,    ,i::.,;;;;;:,,:,,,,      \n" +
                                      "     .,,,,:.::::,,;;:,;;,.   .i:;.iti     ;GG0GGGC08CtfGL;   .;it;,,:iii:.. .:;;;;:.,:,:;:.,,,      \n" +
                                      "     .,,.., ,;;;;::;;;:     .i;;,:i1:    iCGL0CGffii00,fffi    ,;tffi;it1;;;:ii,;;. .;:,.,::,,.     \n" +
                                      "     ,,:,:. .::;;: ,;;,    .;i;:if1:    tLL1f81     .GG;1fCi      ,1LtLL1i1ii11i1i. ,::. ,,.,,,     \n" +
                                      "     ,,,,:.,:;:.,:;;;i.  .:i1t1fLi.   .tffii0t       ,LGttLG1      ;1if, :iiiiii;,  ,,,, .,.,,,     \n" +
                                      "     ,,,,:;:,;.  .;;:;,.:;;1fL1f1    ,LCf;t8t.         ;0GLGGf,    1fff,,;;iii:,:,   ,:;:,:::,,     \n" +
                                      "     ,,:::.:;:,   ,:,:i1i;;..ftti   iGGCiL@;            t8C80fC;  .tf1Lfii:, :;:;;.  ,;,:;:.,,,     \n" +
                                      "     ,,,.,. ,,,,  :ii1iiii, ;tit: ,fft0800G1:          ,GL0i;Ltf1,1Ct111;,   ,i;;::,.:::..:,,,,     \n" +
                                      "     ,,,,,, .::, .i1i11ii1itLftL1iLCtGf;1tLG01i;i11t11fL0CG  fCLGCf;:;i;.    ,:;, :;;::. .,,,,,     \n" +
                                      "     .,,,:,.::;. .;:,i;,;;;11i:1GGCfCGft,,t8GC0GLftttffLf08LiGCCtCCt;;;     .:;;;:;;;;;..:..,,.     \n" +
                                      "     .,,..:;:::,,:;;;;:.  .:;i1LfCCffGGCLfLCG0CLLffttfLCG0LfCCLCLCLLL1.   .:;:,:;;,.::::.:,,,,      \n" +
                                      "      ,,,,:,.:;;;;;,.:;i,    tCCLfLffCfi;;i;;:::;;;::;;;ii;;i11CGLfLCCi  ,::.,::.  ,:::;:,,,,.      \n" +
                                      "      .,,,.,..,::;:::;;:;:,. .,.;i;;,tf;                    ..;ft:;11,.  ::.,:,  .,::,.,:.,,,       \n" +
                                      "       .,,..:. ,;:. .,:;::::,    ,;i;tftt:,.    .,:;;;:,::;tt1tLffiii    ;;:;,   :,,. .,,:,,.       \n" +
                                      "        ,,,:,,.::::    ,:,.,;,    .iitftCfit::ittti;;ittLftLfi::itf:i. .;i;;;.   ::: .,..,:.        \n" +
                                      "         ,,,.,:::;::,.  .::.;;     ,iit::i1tftfi:,,,::;;ittft,,,,;t;i,.;::.::. .:::;,,:,,:,         \n" +
                                      "          ,,,,:,,..,,,,  ,;,:i:,.   :;;;. 1Cti:;i;:;;;;;:;;;i;ii11;i1i:;i:.:;:,;;,,::,,:,,          \n" +
                                      "           ,,,,.,,  ,::. ,:;;::;;::,:ii1i;11i1i;,,.          .,;iii111::;:;;;;;,. ,:..,,,           \n" +
                                      "            .:,,,:,..:::,:;;:,,;;;;::i1:1ii;,.      ..      .,:;,::;;i:. ..:;;: .,,,:,,.            \n" +
                                      "             .,:,,,,:::::ii;;:,,,::::i1i1i;.....,,::,,:::,,:;i;;;:.   ..,:;,:;.,:..,,,              \n" +
                                      "               .,:,.::,..,,:::.     .;1i;:;i:;i;;;:,,,,,::i;,.:;:   .,,:::,,:::,,,:,.               \n" +
                                      "                 .,:,,,,,. .::::,,,.....;:;:.,;;;::,,,,::;;;,:;:..,,::,.  .,:.,,:,.                 \n" +
                                      "                   .,:,.,:,.:::::,,,:,...:;:::,,         .,:i;;::;;,:;..,,,,:,:,.                   \n" +
                                      "                     .,:,:,,,:,,.. .,;::::i;;:,....,,,::::,:;;;....,::,:,.,,,,.                     \n" +
                                      "                        .,,,,:,,,,,..,:::,::.,:;,:;:,.,,::,;:.....,:..,::,,.                        \n" +
                                      "                          ..,,,,,,::,,::,,.....,:;:......,:::,:,.,::,,,,.                           \n" +
                                      "                              ..,,,,,,,::,,.,:,.,:,,,..::,..,:,,,,,,..                              \n" +
                                      "                                   ...,,,,,,::,,,,:,,,,:,,,,,,...                                   \n" +
                                      "                                           ..............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 11 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         ..........,.......                                         \n" +
                                      "                                  ...,,,,,,,:,,,,::,,,,,:,,,,,,...                                  \n" +
                                      "                              .,,,,,,,::,,,::,.,,,,:,,,:,.,,:,,,,,,,..                              \n" +
                                      "                          ..,,,,,::,.,,,:;:,......:::,......,::,,::,,,,,..                          \n" +
                                      "                       ..,:,:,,,,,.....,;:,;:,,,,:;::;:,,:,,:::,..,.,,:,,,,..                       \n" +
                                      "                     .,::,.,:,,;:,,,.:i;:,,,,,,,,. ...:;;i;:::;:,....,::,.,,:,.                     \n" +
                                      "                  .,,,,,:,,,. :;::;::;;;;,,.       .,,:::;;, .,::,,,:::;,,,:,,,,.                   \n" +
                                      "                .,,:,,,,,....,,:,.  ,::.:;;:::,,,:::;;:.,;;;: ....,,::::, .,,,,:,:,                 \n" +
                                      "               .,,,,:,:;,:;::,,.   :;;,,:i;;:,,,,::;;;i:;i;,;i1:      ,::,,,.,,:,,,,.               \n" +
                                      "             .,:,,.,, ,;:,,.  ...,::;;i;:,.,,::,,:,......,ii1i1;::,,,,,;;;i;:,:;,,,,,,.             \n" +
                                      "            .,,,::.. :;;;,..,,i1ii;,;:.          .    .,:;i1;;1;:;;;;:,,;;:,;:::.,:,,,,.            \n" +
                                      "           ,:,,,:,.,::;;;;;i;:;11ii1i:,,..      ,Lf::i1iit;ii1ii,::;;:,:;;, .:;:. ,,.,:,,           \n" +
                                      "          ,,,:,:;:,;:,,::..;;:;i1iiiii;i1ii;;;;;LLCLi;itLL. ,i:i.  .,,i;:;:  .:,,...,:,,,,          \n" +
                                      "         ,,,.,, ,;:,. .:;:;::. ;;11,. ,tLtt1;;:fCLLCftftL11;:11;;     :i.::.  .,,::,:;,.,:,         \n" +
                                      "        ,,:,,,. ,,:.  .;;;;:   ;;iLt1i1tLttLf1fGL1LGCL:,1itCftfii,    .;,.,:,   .,:,:,,,:,,,        \n" +
                                      "       .,,,,, .,:,,.  ::,;;    ;1i1tLf1i1,,. 1CLGf0CtC;  ,:1ttt:ii:    ,::,,::,.  :;:  :,,,,.       \n" +
                                      "      .,,,.::,:;:.  .,:,.::    ii;,ff;..    iL1CftiCC1C:    :Lf,;;;:.   .,:;:;;::,;::, .,.,,,       \n" +
                                      "      ,,:,:,::::. .:::,,:,.   ,;::.Lf      ;CfG1 ,iG8CGG;    iti.::;;:.    ,i;:.,;;;;;,.,:,,,,      \n" +
                                      "     .,,,,:..;;:::;i::;:,    .i:;.;t1     ;CG0G00C08CffGC;   .;i1;,,:ii;,.  ,:;;::;:,:::::.,,,      \n" +
                                      "     .,,.,, .;;;;;.:;;,     .i;;,:11:    iCGf0CCffiiG0,tff;    ,;tf1;;i1i:::.;i:;;, .;::,,,,,,.     \n" +
                                      "     ,,,,:. ,:::::.:;;,    ,iii;1f1:    1LL1t8i     .CG:1fL1      :tLtfLt1iii1i;i;. ,::, .:,,,,     \n" +
                                      "     ,,,.::,:;, .:;;;i.  ,;itt1fL:    .tftii0f       ,LGt1LGt.     :tifi.,ii;;1i1;. ,,,. ,,.,,,     \n" +
                                      "     ,,,,::::;.  .::,;;:;;;1fLtL;    :LGf;t8t.         ;0CLGGf,    1t1f. ;ii11:::.  .:,:..:,,,,     \n" +
                                      "     ,,,,: .:,:.  ,;:;11ii: .f1ti   iCGC1L@;            t8L80fC;   iftLf1i;:,:;,;:   ,:::::,,,,     \n" +
                                      "     ,,,.,. .,,, ,i111;iii,,tf1f, .ff10800C1:          :CL0tiLtf1,iLfit1i;.  ,i;;;:. :;:,::.,,,     \n" +
                                      "     ,,,,:. ,;:. .;;;i1iii1fLt1ftiLLf0t:itLG01i;i11111tL0CC  fGfGCLi:;i;,    ,:;,.:::::, .::,,,     \n" +
                                      "     .,,,,,::::  ,;;:i:.,::i1i:iCGGfLCft,.L8CG0CfftttfLLL881iGCCtCCt;;;.     :;;:,;;;;;..,.,,,.     \n" +
                                      "      ,,,,:::::::;::;::,   ,:itLfCCffGCCLLCC00LCLfftffLCG0LLCCLCfCLLL1.   .,;;::i;:,:;;..:.,,,      \n" +
                                      "      ,,,::,.:;i;;;..;;;,    1CCLfLfLCfi;ii;::;;;;;::;;;;i;;1i1CGLfLCCi  ,::,,::,. .::::::,,,,      \n" +
                                      "       ,,,.,. ,:::,::;;:;:,. ,,.;i;:,ff,                    ..ift,i1i,.  :,.,:,   ,:;:,::.,,,       \n" +
                                      "       .,,,,:  :;:  .,::,,,:,    ,ii;f1f1::     .::;;::,,,;ti1fffti1:   .i:,;,  .,,:, .:,,,:.       \n" +
                                      "        ,,,,,,::,;,.   ,:,.,;.    ,iiLtLL111:i1tt1i;i1tfLttLti;i1L;;:  .;;;;;.  ,:,, .,,,:,.        \n" +
                                      "         ,:,.,;,,::,,.  .::.i,     ;i1i,:1tLtft;,,,:::;;1ttL1..,,1i;; ,;:::;:  .,:;, :,.,,,         \n" +
                                      "          ,,,,,,. .,,:.  :;:;i:,.  .i:i, .LL1;;;i;:;;;;;;;iii;ii1ii1i;;;;..;;,,;;,:;:,:,:,          \n" +
                                      "           ,,:..,, .:;:. :;;,,:i;::,i11iiit;ii;:,..        ..,:i1ii11;:;;;;;;;:,..,:.,,:.           \n" +
                                      "            .,,,,:,.:,::,;;;:,:;;;;:;1;i1i;:.                ,;;,;ii1i....,;;;, .,:,,:,.            \n" +
                                      "             .,,:,.:;:,:;i;;;,,,,,,:;1iiii,.....,::,,:::,,::ii;;::. .. ..:::;, ,,.,,:,              \n" +
                                      "               .,,,,:,,...,::,.     :1;;:;i;:i;;;:,,,,,:;;i,.:;;,   .,,::;,,;:,:,,,,.               \n" +
                                      "                 .,,,,.,,. ,::;:,,...  :;;;..:;;::,,,,,:;;;,,::, ..,:,,.  .:,,,,:,.                 \n" +
                                      "                   .,,,,::,,;::,,,,::,. ,;::;:,.        ..,;;;;::;::;, .,,,:,,,,.                   \n" +
                                      "                     .,,,,.,::.... .::,;:;;;;:,...,,,,:,,,,;;;:..,,:;,,:,.,:,,.                     \n" +
                                      "                       ..,,,,:,..,,.,:::,,:..:;::;:,..,::,::......,,,,,:,:,.                        \n" +
                                      "                          ..,,,,,::,,,:,,.....,:::......,:::,,,.,::,,,,,.                           \n" +
                                      "                              ..,,,,,,,:,,.,:,,,,,,,..,::..,::,,,,,,..                              \n" +
                                      "                                   ..,,,,,,,,,,,,::,,,,:,,,,,,...                                   \n" +
                                      "                                           ..............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 12 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         .......,..,.......                                         \n" +
                                      "                                  ...,,,,,,,,:,,,,::,,,,,,,,,,,...                                  \n" +
                                      "                              .,,,:,,,,:,,,,,:,.,,,,:,,,:,.,,:,,,:,,,.                              \n" +
                                      "                          ..,,,,,,:,,.,,,:::.......:::,.....,,:,,,:,,,,,..                          \n" +
                                      "                       .,,,,,:,,,:,...,.,;::;:,,,:;;,:;,.::.,:::,.,,.,,:,,,,.                       \n" +
                                      "                     .,,:,..,,.:;,::,,;;;:,,.,,,,,. ..,:;;i::::;:. ...,:,.,:,,.                     \n" +
                                      "                  .,,,,.,,,.. .:;:::,:;;;:,..       .,::::;:..,::,,,,,,:;,,:,,,,,.                  \n" +
                                      "                .,:,:,,::,...,,::.  .::,.;;;:::,,:::;;;,.,;;:.  ..,,:;:,;. ,,..,:,,                 \n" +
                                      "               .,,,.:,.::,;;:,,.   ,;;;:,;i;;:,,,,:;;:i;:i;::;;i.     .:::....,,,,,,.               \n" +
                                      "             .,,:,,,,..:::,.   ::,:::;;;:,..,,:,,,,,......:ii111;,,,,,,:;:;;,,,::,.,,,.             \n" +
                                      "            ,,,,,:,. ,;;;:,,,::111i;;;,          .      ,:;i1;i1::;;;;,,;;;::;,:,,,:,,,.            \n" +
                                      "           ,,:,,::,,:::;;;:;i::;11;11;::,,...   ,Lf,,;ii;1iiii11;:;:i;,,:;: .,:;, ,,..,:,           \n" +
                                      "          ,:,,:,,;:::..:;,.:;;::iiiii;;iti1i;i;;LLCLi;;1fC; .;;;;   ,,;i;;;.  :,,. .,,:,:,          \n" +
                                      "         ,,,.,: .:::   :;;;;:, .i:t:. .1Cttti;:fCLLCtiftLt1;:;f;i.    ,i,,;,  .,,::,,::.,,,         \n" +
                                      "        ,,,:,,. ,,,,  .;::;:   .i;fft111ff1fftLGL1LGCL;:i1ifLtL1i;     ;:.,:,   .,;::;:,,:,,        \n" +
                                      "       .,,.,:..:::,.  ,:,,i.   ,1iittL1;i,.. 1LL0fGG1C;  ,:;f1f;ii:    ,:,,,::.   ,::, ::,,,.       \n" +
                                      "      .,,,,:;::;:.  .:,,.,:.   ;ii.tLi      iC1CftifC1C:    ,fL::i;;.   .,:;:;;;:,:::, ,,..,,       \n" +
                                      "      ,,,,:,,:::..,:;:,::,.   ,;:; tL,     ;Cf0t.,:L8CGC;    :t1.:::;,     ,;;;,,;;;;;, ,,,:,,      \n" +
                                      "     .,,..:..;;;:;;i;:;:.    ,;:;.:11.    iCG0G00LG@CCfGC;    ii1:,,;i;,.   ,:::,:;::::,,:,,,,      \n" +
                                      "     .,:,,, .:;;;; ,;;:     ,i;:,;1i:    1CGtGGCtt1iC8:tLf;    :ifti:;i1;:,. :i:;;:..:::;:,.,,.     \n" +
                                      "     ,,,,:..:::,::::;;,   .:iii;tfi,    1LL1181     .CG:1tL1     .iff1tftiii;ii:;:  .:;: .:::,,     \n" +
                                      "     ,,,.:;:,;,  ,;;:i. ,;i1ft1Lf,    .tLtiiGf       ,LG11LGt.     :f1ft::iiii1i1i, ,,,. ,,.,,,     \n" +
                                      "     ,,:::.:;:,   ::,;i;;i;itLtL:    :LGLif81          i0CfCGf,    11if. :iiiii;;,  ,,,, .:.,,,     \n" +
                                      "     ,,,.,. ,,:, .:iiiiiii, ,fi1i   iLGGtL@;            1@L88LCi   ;LtLti;i;;i:,::   ,:::,:,:,,     \n" +
                                      "     ,,,.,. ,,,, ,i1i1iiii:;fftf, .fL1G080Ct;          :CL0LtLtL1.;Lfitti;:. ,i:;;,  ,;,:;:.,,,     \n" +
                                      "     ,,:,:..:;:  .::,ii;iiitf1iff1LLf01,;fCGG1i;i1t111tCGCL  fGfGCL1;iii:.   ,;;:,;:,:::.,:,,,,     \n" +
                                      "     .,,.,:;:::..:;;;i, .,,;1i:;LGGfLCLt,,G8CGGLftttffCLC80i1GCLtCC1;;i.     :;;,.;;;;:. ,,,,,.     \n" +
                                      "     .,,,,:,,::;;;:.::;,   .,;tCfCCffGCCLCCC0GCCffttfLCC0GfCCLCCfCLLL1.    ,;;:;i;::;;: .,.,,,      \n" +
                                      "      ,,:,,, ,;;;;;,,;;;:.   1CCLfLfCCtiiii;:;;;;;::;;;;;;;i1itCCffLCCi  .::,,:;:. .:::,,::,,.      \n" +
                                      "      .,,,.,. ,::,.::;::;::. ,,.;ii::Lt.                     .tfi,11;,. .:,.,:,.  .:;,:;:.,,,       \n" +
                                      "       .,,,:,.,::,   .::,.,:,    :iiif1Li;,    .,::;;:,.,,;1itLtti1i.   ,i,,:,  .,,:,..:,,,,.       \n" +
                                      "        ,,:.,:;::;,..  ,:,.:;     ;i1LtCfit1;1tt1i;;i1tff1ffti1tft;i   .;;:;:   ,,,. .,,::,,        \n" +
                                      "         ,,,,::.,,:,,.  ,;,:i,    .iit:,:tfftfi:,,,:::;;1ttLi. .;t;i  ::;;;;:  .::: .:..,,,         \n" +
                                      "          ,,,,,,. .,::  .;;;;;::.  ;:i;, iCt1;ii;;:;;;;;iii1i;iiiiii;:;;: ,;:.,;::;,,:,,:.          \n" +
                                      "           ,:,..:, ,;:,..:;:.,;;:;:;1ii1i1i;i;:,..      ...,,:;1i;11;:;i;:;;;:::,,::,,:,.           \n" +
                                      "            .,:,:,,::,;::;;;::;;;;:;1i;1i;,.       .         ,;:;ii1i,,,.,:;;;. .,:,,,,.            \n" +
                                      "             .,,,.,::,,,;;:;,...,,,;111;i:..,..,,::,:::,.,::i;;::,,,,   .,::: .,,,,:,,              \n" +
                                      "               .,,,,,,....:::.     ,i:;::;i:;i;;::,,,,:;;i:,,;;:.   .,,:;;,::,::.,,,.               \n" +
                                      "                 .,:,..,,..;,;;:,,,.  .:;;,.,;;:::,,,,::;;:.,::. ..::,,...,:,,,:,,.                 \n" +
                                      "                   .,,,::,,::,,,.,:::,..:;:;::..        .,:;;;:::::;:. ..,,,.,,,.                   \n" +
                                      "                     .,,:.,,:,... .:;,;::i;;:,...,,,,,,,,,:;;;,,,,:;:.:,.,::,,.                     \n" +
                                      "                       ..,:,:,..,,.,:::,.:,.,;:,;:,.,,:;::;,.,...,:,,,:,:,,.                        \n" +
                                      "                          ..,,,,,:,.,:,,,....,:::.......:::,,,.,,:,,,,,,..                          \n" +
                                      "                              ..,,,,,,:,,.,::,,,,,,,.,:,,,,::,,,,,,,..                              \n" +
                                      "                                   ...,,,,,,,,,,::,,,,:,,,,,,,,..                                   \n" +
                                      "                                           ..............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 13 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         ........,.........                                         \n" +
                                      "                                  ...,,,,:,,,::,,,,:,,,,,,,,,,....                                  \n" +
                                      "                              ..,,,:,,,,:,,,,,,,.,,,,,,,::,.,::,,,,,..                              \n" +
                                      "                          .,,,:,,.,:,,.,.,:::......,:::,....,,:,,,,:,,,,..                          \n" +
                                      "                       ..,,,,,:,,::....,.:;,:;:,,,:;;,;;,,:,.,:::,,:,.,:,,,..                       \n" +
                                      "                     .,:,:,..,..::,;:,:i;;,...,,,,,....:;;;;:;::;, ...,,:,,,:,.                     \n" +
                                      "                  .,,,,,,:,... ,:;,:,,;;;;:,.       ..,::::;, .,::,..,,,::,,:,:,..                  \n" +
                                      "                .,,,,:,:::,,,,,,:,   :;:.,;;;::,,,:::;;;..:;;,   ,,,::;,::.,:,.,:,.                 \n" +
                                      "               .,,,.,:..;::;:,,.   .:;;;::i;;:,,,,,:;;;i:;i;:;::;.    .,::. ..,,:,:,.               \n" +
                                      "             .,:,:,,...:::,    .i;::,:;;:,,..,::,,,:,...,.,;;;111:,,...,;:;;,..,:,.,:,.             \n" +
                                      "            .:,,.:,...;;;;::,:::111i;i:.         .       ,:;11;ii::;;;;:;;;;:;::;:,,:,,.            \n" +
                                      "           ,,:,,:;:,;;:;;;,:i;;:i1i;11;;;,,,... ,Lf,,:;i;i1i1ii1i::;;;:.,;;,.,::: .:,.,,,           \n" +
                                      "          ,,,.:,.:;::. ,;:.::i:,;iii;;:;t11i;i;;LLCLii;1tLf .,i:i,  ,::;;;;,  :::. .,,,:,,          \n" +
                                      "         ,,,.,,. ,::.  ,;;;i:,  :;i1.  ;Lftt1;:fCLLCf;ftfLt;,,11;:    .i:,;,  .,,,,..,:,,,,         \n" +
                                      "        ,,,,:, .,,,,   :;,;;    :iiLff11tL1tftLGC1fGCLi;;titLfffii.    :;.,:,  .,,;;,:;,.,:,        \n" +
                                      "       .,,.,:,,;;:,   ,:,.;,    i1;11Lt;i:   iLf0fGG1C;  ,;;tttiii;    ,:,.,::.   ,:::.,,:,:.       \n" +
                                      "      .,,:,::;,::   ,:,..,:.   :ii,;Ct.     iC1LttifC1C:    .tC;,i;;.   .:::::;:,..::, .:.,,,       \n" +
                                      "      ,,,.:, :::..,;;::::,.   .;:; 1L:     ;Cf0L,,,f8CCC:    ,t1.,:::,    .:;;;::;;;;;, ,,,:,,      \n" +
                                      "     .,,,.,..;;;;;::;;;,     ,i:;.,11,    iCGGG08CG@CCLGC;    iii:,:;i;.    ,;:: :;;;::..::,,,      \n" +
                                      "     .,,::, ,::;;;..;::    .:i;:,;1i:    1LG1CGLtt1iC8;tLL;    :if1;:;ii;,.  ,;;;;:,.:::;:,.,,.     \n" +
                                      "     ,,,.:,,:;:.,;;;;i.  .,;1i;iff;,    1ff1i81      L0:1tfi     ,1Lfi1ttii;,;i,:;.  :::,,:,:,,     \n" +
                                      "     ,,,,::::;,  ,;:,i,.:iitff1Lt.    .fLfiiGf       .LG11fGt      :LtLfi;iii1i;i;. .::, .:,,,,     \n" +
                                      "     ,,:,: .:::   ,;:;11ii;:1Ltf:    :LGLif01          i0LfCGf,    i1;f, .iii;11i;, ,,,. .,.,,,     \n" +
                                      "     ,,,.,. ,,,, ,i111;iii. :f;t;   iLG0fL@:            i@L08LLi   :ftL1;;iii1;::,   ::;..:,:,,     \n" +
                                      "     ,,,,:. :::. .;i;i1iiii1LftL: .fL1G080Cf:          ;CC0CfCtL1.,fLiff1i;,.,i:;;,  ,;,;::,,,,     \n" +
                                      "     ,,,,:,::::  .;:,i:,;ii1t1ifL1LLf01.:fCGG1iii11111tCGCL  tGfCCLt;iii:,   ,;;;::,.:::,,:.,,,     \n" +
                                      "     .,,.,:::::.,:;;;;,  .,:ii;:fGGfLCL1.;80CGCLft1tffCLG8G:1GCLfGC1:;i,     ;:;..;;;;:. ,::,,.     \n" +
                                      "     .,,:,:..:;;;;, ::;,    .:tCfLCtLGLCLCLC0CCLffttfLCC0CLCCLCCfCLLL1.    .:;;;;:;;;;: ,,.,,,      \n" +
                                      "      ,,,.,, ,;:;;::;;;;:.   1CCLfffCL1iii;;;;;;;;::;;;;;;;i1ifCCfLLCCi  .:::,:;:,.,::: ,:,,,.      \n" +
                                      "       ,,,.:. ,::..,:;:::::. ,,.;ii,iC1.                     ,tL::1i:,. ,:,.,::.  .::,;::,:,,       \n" +
                                      "       .:,:,,,:::,   .::,.,:,    ;1i1ttf;;,    .:::;:,....;iifL1ti1;    :;.,:,  .,:::,,:.,,:.       \n" +
                                      "        ,:,.,::,;:,,.  ,:,.;:    .iifffL1ifiittt1i;;1ttft1Lt11ttLii:   .;;:;:  .:,,. .,,,,:.        \n" +
                                      "         ,,,,:,..,,,,.  :;,;;..   ;;1i.,;fLttt;::,,:::;ittLL: .,1;i:  ,:i;;;,  .;:, .,..,:,         \n" +
                                      "          ,,:,,,. .:::  ,;;;;:::. ,i;i:,.ff1i;ii;;:;;;i;i11t;;i;iii;::i:,.::, ,::;:.:,,,,,          \n" +
                                      "           ,,,.,:..::::.,;;,.:;;;:;11;i1i1;;;:,..      ...,:;;11:i1i::;i::;;;:;;,:::,,,,,           \n" +
                                      "            .,,:,,:;,:;:;;;;:;;;:::1i;1i;,.                 .;;;i111:::,::;;;:...,:.,,,.            \n" +
                                      "              ,:,.,:,..,;;::.....,:111:;;,,,..,,::,,::,,.,:;;;:::::;    .,;:,..,,,:,:,              \n" +
                                      "               .,:,,,,,. .::,,.   .:,:;:;i::i;;;:,,,,,:;;i,:;;;:.  ..,::;::;..:,.,:,.               \n" +
                                      "                 .,:,.,:,.:::;::,,,   ,:;:..;;;::,,,,::;;;,.::,  .,:,,,,,,::,,:,:,.                 \n" +
                                      "                   .,:,:,,::,....,::,..,;;;;:,.        .,:;;;;,,::;:, ...,:.,,:,.                   \n" +
                                      "                     .,:,,,,,,... ,;,:;:;;;:,....,,,,,,,,:;;;:,::,;:.,:..,:,,,.                     \n" +
                                      "                       ..,,,,..,:,,:::,.,,.,;;,;;:,.,:;::;,.,....::,,:,,,,,.                        \n" +
                                      "                          ..,,:,:,,,,,,,....,:::.......:::,,,.,,:,,,,:,,.                           \n" +
                                      "                              ..,,,,,::,.,::,,,,,,,.,::,,,,:,,,,:,,,..                              \n" +
                                      "                                   ...,,,,,,,,,,:,,,,:,,,,,,,,...                                   \n" +
                                      "                                          ...............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 14 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         ........,.,.......                                         \n" +
                                      "                                  ...,,,,,,,,,,:,,,,::,,,:,,,,,...                                  \n" +
                                      "                              ..,,,,::,.,::,,,,,,,.,,,,,,,:,,.,:,,,,..                              \n" +
                                      "                          ..,:,:,.,,:,,....,:::......,:::..,.,,,,,.,:,,,..                          \n" +
                                      "                       .,,,:,.,:,,::,...:.,;:,;;:,,,:;::;:.,,..,:;,,:,,.,:,,.                       \n" +
                                      "                     .,,,,:,,... ,;,:;:;;;;:....,,,,,...,;;;;:;;,::..,.,,:,,,,.                     \n" +
                                      "                  .,:,:,,,:,....,:;,,.,;;;;:,..      ..,::;;;,.,,;:,....,:,,.:,,,                   \n" +
                                      "                 ,,,,,:,,::,;::,,,.  .:;:..;;;:::,,,::;;;,.,;:.  .,,,:::::;,,:,,,,,                 \n" +
                                      "               .,,:,,,, .:::,... .,.,;:;i;:;;;;:,,,,,:;;i::;;;;, .....,:::;. ,,..,:,.               \n" +
                                      "             .,,,.:,...:::;,.  ..;1ii::;:,,,..,,:,,:,,...,,:;:,;i1i.    .::::. .,:,,,,.             \n" +
                                      "            .,:,.,::,::;;;;::;;;::1i;11;,.       ..        .:i1ii1i::;:::;;;;:,,,:,.,:,.            \n" +
                                      "           ,,,,:,.:::;,.:;:.,;;;;;11;i1iii:::,,.,Lf,..,,::;i;11;i1;;;;i:.,;;,,;::;,,:,,:,           \n" +
                                      "          ,:,..,. :::.  :;;:;:;..,i:1::.;Lt1iiiiLCCL;iii11f1.::i;i:.,;::::;:  .::: .:..,,,          \n" +
                                      "         ,,,,,,  ,,,,  .;::i;.   ,iit,.,1Lf1fi:fLLLLf:it1fLt,..i1;;  .,;i;;;.  :,,. .,,,:,,         \n" +
                                      "        ,,,.,:.,::,,.  ,:,,i.    ;i1LfLtitt;11LGGttGGL1titfitLfLti;    .i:,;,  .,,::..::.,,,        \n" +
                                      "       ,,:,,:;::;,.  .::,.,:    ,ii;t1Li;:   iLt0LC0tL;   :;iLtt;11,    :,.,:,   .:;::;:,,,,.       \n" +
                                      "      .,,,,:..:::  .:;:,,::.   .i;i.fL:     ;CtCt11tLtC;     :Lf.i;i.   ,::,,::,.  :::,.::,,,       \n" +
                                      "      ,,,.,, ,;:;:;;i;:;:.    ,;:;.:f1     ;CLG0;,,;0GLC:     1f:,;:;.   .,:;:;i;:,::;. ,,.,,,      \n" +
                                      "     .,,:,, .:;;;;,.::i.     :i;:.,i1;    iGGCGG@CC8GGCGC;    ;1i,,:;i,     ,i:;.,;;;;: .,,:,,      \n" +
                                      "     .,,.::,:::,:;::;;:   .:ii;::1ti:    iLC1t8L1tt1L8ttCLi    :1t;,:ii;,    ::;,,;::::,.,:,,,.     \n" +
                                      "     ,,,.::;,;,  ,;;:i, ,;;1tiitLt:.    1ff1;8f     .f8:tffi    .;fL1;i1i::. ,i:;;:. ::,;;,.,,,     \n" +
                                      "     ,,:::..;::   :::i1iiiitLftL;     .fCfi1Gf       .LGiifCt     .iLtfLtii;;i;,::   :::,.:,:,,     \n" +
                                      "     ,,,.,. ,,,, :i1i1;iii. ;fit;    :LGLtL0i         .10f1CGL,    ;tif;.,iiiiiii;. ,,,, .,,,,,     \n" +
                                      "     ,,,,,. :,:. .;i;iiiii,,1L1f:   ifC8CL@;            i@LC0Cf;   ;tif; ,iiii1iii, ,,,, .,.,,,     \n" +
                                      "     ,,:,:.::;,  .::,;;:;iitLftLi..tCtCC00CL:          ;LC00GCtC1..iLtfLtii;;i;,::   ::;..:::,,     \n" +
                                      "     ,,,.:;:,:: .:;;;i. .,:i1i;iffLLfG1 .fC0C1ii1111ii1GGCt. tGfCLfL1;i1i;:. ,i:;;:  ::,;:,.,,,     \n" +
                                      "     .,,,:,.,::::;,,;::    .;i;:tGGfLCC1,L8GLGLff1tffCGL081,tCCLfGGt:;i;,   .::;::;:::::,::,,,.     \n" +
                                      "     .,,,,,. :;;;;,,;;i,     ,1CLLLtCCLCGLL0GLLffttfLCC0GLCCLLGLtCLLC1,     ,i;:.,;;;;: .:,:,,      \n" +
                                      "      ,,,.,, .:::,:;i::;:,.  1CCLLfLCfiiii;;;;;;;::;;;;;;;iii1LCLfLLCCi  .,:;:;;;:,;::..,..,,.      \n" +
                                      "       ,,:::.::::   ,::,,::. ,,,ii;,Lf:                      iLt.iii,,. ,::,,::,.  :::..:,,,,       \n" +
                                      "       .,,.,::,:;:.   ,:,.::    ,1iittL1i;.   .:;:::,.   .ii1L1ti1i.   .;,.,:,   .:;::;:,,,,.       \n" +
                                      "        ,,,,:,..,:,,.  ,;,:i.   .i;fLfft1ft1ftt1i;;i1tttift1tffL1i:    ,i,,:,  .,,::,.:,.,,,        \n" +
                                      "         ,,:,,,. .,:,  .;;;i;,.  ;;1i. ,fLt11;:::,,,::it1fL1..,t;i,  .,;;:;:   :,,. .,,:,:,         \n" +
                                      "          ,,,.,:. :::. .:;:::;;,,;;ii;:,1t11iii;;::;;iii11f;,:;1;i,.,;:::;;,  ,;:, .,..,:,          \n" +
                                      "           ,:,,,,:;,:;,:;;,.:i;;:i1i;1i;i;,,,..     ..,,::iii1i;1i;;;;i,.:;:,:;,::,,:,,,.           \n" +
                                      "            .,:,.,:,.,:i;;;::::::i1iiii:.                  ,;11iii:::;:::;;i;:,,::,.,:,.            \n" +
                                      "             .,,,,:,...,:::.    .ii;;,:;::,,.,,::,,::,..,,::;,:ii1:.    ,;::,..,,,,,,,              \n" +
                                      "               .,:,..,, ,;:::,,...  ,;;;;::i;;:,,,,,:;;i;,;;;;:.......,::::  ,,,,:,,.               \n" +
                                      "                 .,,,,:,:;:::::,,,.  .:;,.,;;;::,,,,::;;:..:;:   .,,,:::,::,::,,,,.                 \n" +
                                      "                   .,:,.,,:,.. .,::,,.:;;;;:..        .,:;;;;,.,:;:,....,:,.,:,,.                   \n" +
                                      "                     .,,,,:,,,,..::,;:,;;;;,,..,,,,,...,:;;i::;::;, .,.,,,,,,,.                     \n" +
                                      "                       ..,:,,,,:,,::...,,.:;,:;:,.,:;;,;:,.,...,::,,:,,,:,,.                        \n" +
                                      "                          ..,,,:,,,,,,,.,.,:;:,......:::,.,..,,:,,,,,,,,..                          \n" +
                                      "                              ..,,,,:,.,,:,,,,::,.,,,,,,,::,.,:,,,,,..                              \n" +
                                      "                                   ...,,,,,,,:,,,,,:,:,,,,,,,,...                                   \n" +
                                      "                                           ..............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 15 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         ........,,........                                         \n" +
                                      "                                  ...,,,,,,:,,,::,,,,:,,,,,,,,,...                                  \n" +
                                      "                             ...,,,,,:,,.,:,,,,,,,..,,,,,,::,,,::,,,..                              \n" +
                                      "                          ..,,:,:,,,,,,.....,:::......,:::..,.,,:,,,,:,,..                          \n" +
                                      "                       .,,,:,..,:,,::,,.,,.,;::;:,,,,:;::;,.,...,::,,:,,,,,..                       \n" +
                                      "                     .,:,,,:,,.. .::,;;:;;;:,....,,,,...,:;;i::;:,;,.,,.,,:,:,.                     \n" +
                                      "                  ..,,:,,,::,....,::,. :;::::,..      ..,:;;;:,,,:;:,  ..,:,,,:,..                  \n" +
                                      "                .,,,,.,:..;::;::,,,   :;;,.,;;;::,,,:::;;;..:;,   ,:,,,,,,::,,:,:,.                 \n" +
                                      "               .,,,,,,.. ,::,.    ,:,:::;i::i:;:,,,,,:;;;;:;;;;:.  ..,,:;::: .:..,:,.               \n" +
                                      "             .,,,.,:,..:;;;:....,,;11i:;;,.,...,:,,,:,,..,,:;;,:;;i;    .,;:,...,,,,:,.             \n" +
                                      "            .:,:,,:;,:;:;;;;:;;;::;1i;1i:,.      ..         .;iiii1;::::::;;i:...,,.,,:.            \n" +
                                      "           ,,,.,:..;::,.,;:..:i;;:;1i;1ii1;;;:,.,Lf. ...,,:;;i1i:11;:;;;,,;;:,;;,:::,:,:,           \n" +
                                      "          ,,:.,,. ,:;,  ,;;;;::,  :;;;,.,Lfti;iiLCLL;ii;11tt::;;1;i:,:;:,,;;, ,::;,.:,.,,,          \n" +
                                      "         ,,,,:,..,,,,.  :;,;;.    i;ti,,ifLtft;fLLLLt:;1ttLL: .,t;i.  ,:i;;;.  .:,, .,,,:,,         \n" +
                                      "        ,:,.,;:,;:,,.  ,:,.;,    ,iiffLL11f;i1LGGt1GGLtf11L11tffL;i,   .;;,;:  .,,,,..,:,,:,        \n" +
                                      "       .,,:,,,:::,   .::,.,:.   .ii;11f1;:.  iLt0CL0fL;   :iiff11i1;    ::.,:,   ,:;:,::,.,:.       \n" +
                                      "      .,,,,:. :::..,:;::::,.   .;;i.1Li     ;CtCt11fLtC;     ,fL,:ii,   .:,.,::.   :::;,:,:,,       \n" +
                                      "      ,,,.,, ,;;;;;:;;;;:.    ,;:;,,tt.    ;GLC81,,,GGfC:     iL;.;:;.   .,:;::;;:.,;;, ,,.,,,      \n" +
                                      "     .:,:::..:;;;;, ::i,    .;i;:.:i1;    iGGLGG@CC8GGGGC;    :11,,:;;,     ,;;;::;;;;: .,.,,,      \n" +
                                      "     .,,.,:::::.,:;;;;,  .,;ii;:;1ti:    iLC118LitttL0ftGLi    ;1t;,:;i:.    ::;..;;;::, ,::,,.     \n" +
                                      "     ,,,,:,:::,  .;:,i:,;iitt1ifLi,     1ft1;8f      f8;tffi    ,iffiii1;,.  ,i;;;:..:;::::.,,,     \n" +
                                      "     ,,,,:. :::. .;i;i1iiii1LftL,     .fCfi1Gf.      .LGiifLt     ,tL1fftii:,:;,:;.  ,:::::,,,,     \n" +
                                      "     ,,..,. ,,,, :i111;iii. :f;1;    :LGCtC0;         .t8tiLGL,    :ftLi,;ii11;:;,  .:::..:,,,,     \n" +
                                      "     ,,:,: .:::.  ,;:i1i;i;:tLtf:   1LL8GL@;            i@fL0Gf;   i1if: .iii;111i, ,,,. ,,.,,,     \n" +
                                      "     ,,,,::::;,  ,;:,i,.:iitft1Lt..tLtCfG0LL:          ;fC000C1Ct. ;LtLL1iiii1i:;:  .::: .:,,,,     \n" +
                                      "     ,,,.::,:::.,;;;;i.  .,;1iiitLCCfG1  LLGLt11111iii10GCt,.t0fLftLti1t1;;:.:i,;;.  ::::,,,,,,     \n" +
                                      "     .,,,:, .:;;;;..;::     :i;;tCCfCLG1:G8CLCfft1tfLCGL00:,tLCLfGGf:;ii:.   :;;;:;,,:::::,.,,.     \n" +
                                      "      ,,,.,..:;;;;::;;;:.    .1CLLCfCCLCCLC0CCLfttffLCC0CLGLLLGftCLLC1:.    ,;:: :;;;;:..:::,,      \n" +
                                      "      ,,,.:, ,:;,.,:;::::,.  1CCLLfCCfi1i;;;;;;;:::;;;;;;;iiitCCfffLCCi   .:;:;;::;;::,.,,.,,.      \n" +
                                      "       ,,:,::;:::   .::,.,:. ,,:ii::Lt.                     .1C;,ii;.,. ,:::,:;,, .::, .:.,,,       \n" +
                                      "       .:,.,:,,:;:,.  ,:,.;:    ;1i11Lfii;.. .,:;:::.    ,;iftt1i1;    ,:,.,:,   .::,;,,,:,,.       \n" +
                                      "        ,,,,:,  ,,,,.  :;,;;    :iiLft11tL1tftt1i;i1tttiifi1ffffii.    :;.::.  .,:::,::,.,:.        \n" +
                                      "         ,,,..,. ,::.  ,;;;i:,  ,i;t,  :LL1ti;:::,,,:;1ttLf;..i1;;   .,i;,;:  .:,,. .,:,,:,         \n" +
                                      "          ,,,.,:.,;::, ,::.,:;:,:iii;i::t11i;i;;;::;ii;i1ff.,:i;;: .:::;;;;,  :::. .,.,:,,          \n" +
                                      "           ,,,:,:;:,;;:;;;,,i;::;11:11;;;,,..      ..,:;;;1i1i;i1;:;;i:..;;,.:::;.,:,,,,,           \n" +
                                      "            .:,,.:,...;;;;::,:::i11ii;;.                 .,;i1;i1;::;;;:;;;;:::,;:,,:,,.            \n" +
                                      "             .,:,:,,...,:;,.    ;;:;::;;;:,.,,::,,::,,.,,,,;;:i11;... ..::;;,..,:,.,:,              \n" +
                                      "               .,:,.,:..;::;::,..  .:;;;:,;;;;,,,,,:;;;i::i;:;:,:. ...,,::, .,,,,,,,.               \n" +
                                      "                 .,:,:,,::,,,,,,:,.  ,;:..;;;::,,,,::;;;,.,;:,   ,,,::;::;,,:,.,:,.                 \n" +
                                      "                   .,:,,.:,... ,:;::,,;;;;:,.        .,:;;;;:.,,;:,....,::,,:,:,.                   \n" +
                                      "                     .,:,:,,.,,.:;,::,:;;;:,,,,,,,,,...,;;;;:;;,;, ...,,:,,,,,.                     \n" +
                                      "                        .,,,,,:,,::,...,.,;::;:,.,,:;,:;,.,,..,::,,:,..,,,,.                        \n" +
                                      "                          ..,,:,,,,:,,.,,.:;:,......:::,....,,,,,,,:,:,,.                           \n" +
                                      "                              ..,,,:,,,,::,,,::,..,,,,,,::,.,::,,,,,..                              \n" +
                                      "                                   ...,,,,,,,,:,,,,::,,,,,,,,,...                                   \n" +
                                      "                                          ...............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 16 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         .........,........                                         \n" +
                                      "                                  ...,,,,,:,,,,,::,,,::,,,,,,,,...                                  \n" +
                                      "                              .,,,,,,,:,..,:,,,,,,,..,,,,,,::,,,:,,,..                              \n" +
                                      "                          ..,,,,,:,.,:,,.....::::.... .,:;,.,,.,::,,,,,,..                          \n" +
                                      "                       .,,:,:,..,,.,:::,,:,,:;::;:,.,::;,::,,,...,:,,::,,,,,.                       \n" +
                                      "                     .,,,.,,:,... .:::;:;;;;:.....,,,,,..,:;;;,::,:;,.:,.,::,,.                     \n" +
                                      "                  .,,,,:,,:::,,,.,::,. .;;:;::..       .,:;;;;:,:::;:. ..,:,.,,,.                   \n" +
                                      "                .,,:,..,,.,:,;;:,,,.  ,;;;..:;;:::,,,:::;;:.,::   .:,,,,..,::,,:,,,                 \n" +
                                      "               .,,,:,,....:::.     :i:;::i;:;;;;:,,,,,:;;i:::;;;.   .,,:;:,;:.:,.,,,.               \n" +
                                      "             .,:,.,::,.,;;;;,,.,,,,i11i;i,......,,,,::,,.,,:;;:,::,;:   .,:::..,,,,:,,.             \n" +
                                      "            .,,,:,,:::;::;;:,:;;;::i1;i1;;,.      .          :i;;i11;,:,,:;;;;. .,:.,,,.            \n" +
                                      "           ,:,..:. ,;:, .:;:.,;i:::iiii1i1i;i;:,:Lf.   ...,:;;iti;11;:;i:,;;;:::,,::,,:,,           \n" +
                                      "          ,,:,,,. .,::  .;;;i;:,   i:i:. 1Lt1;;iLCLL;;i;i11t;:iiiiii::;;:.:;:.,:::;.,:,,,,          \n" +
                                      "         ,,,,::.,,,,,.  ,;,:i.    ,iit::;tfftf;fCLLCt:;ittfLi. .i1;;  ,:i;;;,   ::: .,..,:,         \n" +
                                      "        ,,,.,:;,;;,.   ,:,.:;    .;itftCtiti:iLCGf1CGftft1Lt11tfL1;;   .;;:;:   :,,,  ,,:,,,        \n" +
                                      "       .,,,:,.:::,   .::,.,:,    ;iiiftf;:,  iC1GGf0fLi ..:iitL1ti1i.   ,;,,:,  .,:::.,:,.,,.       \n" +
                                      "      .,,,.,. ,:;,,::;::;:,.   .;;i,;Lt.    ;C1Gt1tfL1C;     .tLi,ii:   .:,..::.  .:;,:::,,,,       \n" +
                                      "      ,,:,,, ,;;;;:,:;;;,     ,;::,,1t,    ;GCC8f:,.L0fC:     :L1 ;:;.   .:::,:;:,..:::.,:,,,,      \n" +
                                      "     .,,,,:,,::;;;:.::;,   .,ii;,,:1i;    iCGfCC@GC80GGGC;    ,t1,,;:;.    .:;;;;:;;;;: .,.,,,      \n" +
                                      "     .,,.,:;::: .:;;;i, .,,;1i;:itti,    iLL1;8Ci1ttC0CtGLi    ;11:,:ii,     ::;..;;;;:. ,,,,,.     \n" +
                                      "     ,,,,:..:::  .::,ii;iiitf11Lf;.     tff1;0L     .t8;tfLi    ,ift;iii:.   ,;;;:;,,:::.,:.,,,     \n" +
                                      "     ,,,.,. ,,:, ,i1i1iiii:;fftf,     .fCf1tGf.       LGiifLt.    :ff1ff1i;, ,i:;;,  ,:,;;:.,,,     \n" +
                                      "     ,,,.,. ,,,, .:iiiiiii, ,fiti    ,LGCfC0;         .t0tiLGL,    ;LtL1;;i;i1;,:,   ,:;,.:::,,     \n" +
                                      "     ,,:::.:;:,   ::,;i;;i;itLtL:   1LL80L@;            i@LfGGL;   i1;f, ,iii;1ii;. ,,,, .,.,,,     \n" +
                                      "     ,,,.:;:,;,  ,;;:i. ,;i1tt1Lf,.1LfCtC0LC,          ;tC080G1Ct. :LtLf;;iiii1iii, ,,:, .,.,,,     \n" +
                                      "     ,,,,:,.:::,::::;;,   .:iii;1LCCfG1  CC0Lt11111i;i10GCt:,t0fLf1Lfi1ftii;:ii,::.  :::..:,,,,     \n" +
                                      "     .,:,,, .:;;;; ,;;:     ,i;;tCCtCCGii08CLCfft1ffLGGC8G,,tLCLfGGf:;ii;,.  ,i;;;:..:::;:,.,,.     \n" +
                                      "     .,,,.,..;;;:;;;;:;:.    .1CLLCfCCLCCfG0CCLfttffCCG0CCCLCCGffCLLC1;.    :;:,.:;;;::,,:,,,,      \n" +
                                      "      ,,,,:,.:::...:;:,,::.  1CCLffCCti1i;;;;;;;::;;;;;:;iiiitCCfLfLCCi   .:;;;:,;;;;;, ,,,:,.      \n" +
                                      "       ,,,,:;:,;:.  .:,,.,:. ,,;i1.if1.                     ,tL::ii;.,. .:::::;:,.,::. ,,.,,,       \n" +
                                      "       .,,.,:..:::,.  ,:,,i.   .i1ittLti1:...,:;;::,.    :;iL1fiii:    ,:,.,::.   :::,.,:,,,.       \n" +
                                      "        ,,,:,,. ,,,,   :;:;;.   i;tLt1i1fL1fft1i;;i1tt1;1titLtL1i;     ;:.,:.  .,:;::;:,.:,.        \n" +
                                      "         ,,,..:. :::   ,;;;;:,  i;t;.  iLft1;;:::,,,:;ftfft:,,t;i,    ,i:,;,  .,,,,..::,,,,         \n" +
                                      "          ,,,,:,,;::;,.:;, :;;::iiiiii;iti1i;;;;;:;;ii;1tCi ,;i:;  .::;;;;;.  ::,. .,,,:,,          \n" +
                                      "           ,,:,,::,,:::;;;:;i;:;11;iti::,,..      ..,:;i;i1i1ii1i:;;;;,.:;:..::;, ,:.,,:.           \n" +
                                      "            .,,,,:,. .;;;:,,,,,i1ii;:i,         .       .,:i1i;1;::;;;::;;;::;,::,,:,:,.            \n" +
                                      "             .,,:,,,,..:::,.   ,:,,::;;i::,.,:::,,::,..,,.:i;i11;,,....,;:;;,.,::,.,,,              \n" +
                                      "               .,,,.,:.:;,;;:,,.   .:;;:,:i;;:,,,,,:;;i;:ii::;:;,    .,:::....,,,,:,.               \n" +
                                      "                 .,,:,,::,...,,::..  ::,.:;;::,,,,:::;;:.,;;:.  .,,,:;;,;,.,:..,:,.                 \n" +
                                      "                   .,,,.,,,.. .:;:::::;;;:,..        .,:;:;:..,:::,..,,::,,,:,,,.                   \n" +
                                      "                     .,,::,.,:.,;:::,,;;;:,,,,,,,,,...,:;;i::;,::. ..,,:,,,,,,.                     \n" +
                                      "                       ..,,:,:,,,:,...,.,;::;:,,.,:;::;:.,:.,::;,,,:..,:,,,..                       \n" +
                                      "                          ..,,,,,,::,.,,,:;:,......:::,....,,,:,.,:,,,,,.                           \n" +
                                      "                              ..,,,,,,,::,,.,:,..,,,,,,::,.,,:,,,,,...                              \n" +
                                      "                                   ..,,,,,,:,:,,,,::,,,,,:,,,,...                                   \n" +
                                      "                                          ...............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 17 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         .......,..,.......                                         \n" +
                                      "                                  ...,,,,,,:,,,,,::,,,::,:,,,,,...                                  \n" +
                                      "                              ..,,,,,,::,..,:,,::,,,.,,:,.,,:,,,,:,,,.                              \n" +
                                      "                          ..,,,,,::,,::,,... .:::,.... .,:;,,,,.,::,,,,,..                          \n" +
                                      "                       .,,,,,,,.,,..::::,::,,:;,:;:,,,:;;,;:.,....:,,,:,,:,..                       \n" +
                                      "                     .,,:,.,:,... .,;::::i;;;,....,,,,,,,,:;;;:,,,,:;,,:..,:,,.                     \n" +
                                      "                   ,,,,,:,,::::,,,,::,. :;::::,..       .,:;;;;,:;::;, ..,,:,,,,,                   \n" +
                                      "                .,:,,,.,,  :::;:,,... .:;;:..;;;:::,,,::;;;,,::,  .,:,,....:,,,,:,,                 \n" +
                                      "               .,,,,:,..,.,::,      ;ii::;i::i;;;:,,,,,:;;;,,:;;,   .,,:;;,:;,,:,,,,.               \n" +
                                      "             .,,:,,:;,,:;i;;:,,,,,::ii1ii;......,,:,,::,,.::i;;:::..,.  .,,::. ,,.,:,,.             \n" +
                                      "            ,,,,::,.:,::,;;:,,;;;;::i1:11i;,.                ,;;:iii1;,,.,:;;;, .,:,,:,.            \n" +
                                      "           ,,,..,, .:;:. :;;,,:i;:,:ii1iiitiiii::Lf.     ..,,:;11;i1i::;;:;;;;::,.::,,,,,           \n" +
                                      "          ,:,:,,. .,,:.  ;;:;;,,   ,;:;. ,Cf1;;iLCCL;;;;;ii1i;i1iii1;:;;;..;:.,:;,::,,:,:,          \n" +
                                      "         ,,,.::,,::,,.  ,:,,i,     i;ti:;1tLtf1fCLLCt:;i1ttL1. .,t;i: ,:;;;;:  .,::. :,.,,,         \n" +
                                      "        ,,:,,,:::;,.   ,:,.,;.    :i1LtLLi1i,;LCGftCGftff1fLt1itfL;i,   ;;;;:.  ,:,, .,,,:,,        \n" +
                                      "       .,,,,, .::,  .,::,,::,    :ii:f1f;,,  iC1GGf0LCi ..:1i1Lftii1,   .i:,:,  .,,:,..:,.,,.       \n" +
                                      "      .,,,.,. ,:::,:;i;:;:,.   .;;i::Lf,    ;CiGfitfLiL;    ..iLt.ii;    :,.,:,.  .:;:,::.,,,       \n" +
                                      "      ,,:,:, :;i;;;..;;;,    .:;::,.1t:    iGGC8L;, tGfC:     ,Lf ;:;,   .::,,:;:. .::::,::,,,      \n" +
                                      "     .,,.,:::::::;::;::,  .,:ii:,,;1i;    iCGtLC@GC0GG0GC;    .11:.;:;.    ,:;:;i;;:;;;..:.,,,      \n" +
                                      "     .,,,,,::::  ,;;:i:.,::i1i:;1ft;,    ifL1;8C;1ffCGGtGCi    ;1i:,;ii.     :;;,.;;;;;. ,,,,,.     \n" +
                                      "     ,,,,:. :::. .;;:i1ii11fLttLt:      tLf1;0L     .t8itfLi    :1f1;;ii,    ,;;:,:::::, .:,,,,     \n" +
                                      "     ,,,.,. .,,, ,i111;iii,,1f1f,     .tGL1tGf.       LGi1fLt.    ;Lfitti;,. ,i:;;:  ,;::::.,,,     \n" +
                                      "     ,,,,: .:,:.  ,;;;11ii: ,f1ti    :fGCLGG;         .t81iLGL,    ;LtLti;;;:;:,::   ,:::,:,,,,     \n" +
                                      "     ,,,,:::::,  .::,;;:;;;1fLtL;   iCL88L@i            1@ftGGC;   111t. :ii1i;;;,  .:,:..:,,,,     \n" +
                                      "     ,,,.::::;, .:;;;i.  ,;ittifL;,tftL1f0LC,          ;tG088G1Lt. :f1f1,:iiii111i, ,,,. ,,.,,,     \n" +
                                      "     ,,,,:. ,:::::.:;;,    ,iii:iLCGfGt  GC0Lt11111i;i10GLt;:fGtCfiff1tLt1iii1i:;:. .:;: .::,,,     \n" +
                                      "     .,,,,, .;;;;;.:;;:     .i;;tCCtCCGit88LLLffttffC0GC8L.,tLCLLGGL;:i1i::, :i:;;, .;::::,,,,.     \n" +
                                      "     .,,,.:..;;:,:;i::;;,.   .1CLLCfCLCCLL0GCLfftffLCL00CCLLCCGffCLLL1;:.   ::::,:;:::::::,,,,      \n" +
                                      "      ,,:::,::::. .,::,,::.  1CCLfLGC1i1;;i;;;;::;;;;;::;ii;ifCLfLfCCCi   .,;;;.,;;;;;, ,,:,,.      \n" +
                                      "       ,,,.::,:;:,  .,:,.,:  ,,iii,tfi..                    :ff,;;i:.,. .::;:;;::,:::. ,,.,,,       \n" +
                                      "       .,,,,,..,:,,.  ,;,:i.   :1ittfft1t:,,.::;:::,     ::1ftf;ii,    ,:,,,::.   ::: .:,,,,.       \n" +
                                      "        ,,:,,,. ,,:,  .;;;;;.  :i;Lt1;itLttLft1i;i1tt1i:t11LLtLii,    .;,.,:,   .:;,:::,,,,,        \n" +
                                      "         ,,,.,: ,;:,.  :;:;:;, :ii1,. .tLtt1;;::,,,,:1ftLt1:,i1;;     :i.::.  .,,::,,:,.,,,         \n" +
                                      "          ,,,:,:;:,;;,,:;..;;;;i1i;iii;i1ii;;;;;;:;ii;;1fL. ,i:i,  .::i;:;:  .:,,. .,,:,:,          \n" +
                                      "           ,:,.,:,.,,:;;;;:;;::i1i;1i:,,..        .,:;ii;tiiii1i::;;i:,,;;: .:;:. ,,..,,.           \n" +
                                      "            .,,,::,. ,;;;,..,,;1ii;,;;,         .      .,;;1i;1i:;;;;:,:;;;,;:,:,,:,,,,.            \n" +
                                      "             .,::,.,, ,;:::.  ....:::;;i::,,:::,,::,...,.,iii11i:,,,,,,:;;i;,,:::.,:,,              \n" +
                                      "               .,,,,:,:;,,;::,,.   ,;;:,,i;;:,,,,,:;;;i:;i;:;;i;     .,::,...,,:,,,,.               \n" +
                                      "                 .,:,,,,:. ..,,:,.. ,::,,;;:::,,,,::;;:..:;;:  ..,,,:;,:: .,,.,:,,.                 \n" +
                                      "                   .,,,,:,,,. ,;::;::;;;;:..        .,:;::;, .,::,,,,,:;:,,:,,,,.                   \n" +
                                      "                     .,,:,..:,,;:,,,.:;;::,,,,,,,,...,:;;;;:;::;,  ..,,:,.,:,,.                     \n" +
                                      "                       ..,:,::,,,,......:;,::,,.,:;:,;:,.:,,:::,.,,..,:,:,,.                        \n" +
                                      "                          ..,,,,,::,.,,,,;:,......,::,.....,,::.,::,,,,,..                          \n" +
                                      "                              ..,,,,,,::,,.,:,,.,,,,,.,:,..,::,,,,,,..                              \n" +
                                      "                                   ...,,,,:,,:,,,,:,,,,,,,,,,,...                                   \n" +
                                      "                                           ..............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 18 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         ........,.........                                         \n" +
                                      "                                  ...,,,,:,,:,,,,,:,,,,:,,:,,,,...                                  \n" +
                                      "                              ..,,,,,,,:,,.,::,.::,,,.,::,.,,:,,,,,,..                              \n" +
                                      "                          .,,,:,,,:,,,::..... .::;,......,::,,,..,:,,,,,..                          \n" +
                                      "                       ..,,,,:,,,,,..::::,::,:;;,:;:,,,:;:,;:.....,,.,,:,,,..                       \n" +
                                      "                     .,:,,,.::,.....:;:::;i;;:,.. .,,,,,,,,:;;;..,,,;:,:,.,,:,.                     \n" +
                                      "                  .,,,,.,:,,:::::,,,:,. .:;::::,.       ..,:i;;::;;,;:..,,,,:,:,..                  \n" +
                                      "                .,:,,,,,,. ,::::,,.. ..,;:;,.,;;;::,,,,::;;:,:::  .,::,.. .,:,.,:,,                 \n" +
                                      "               .,,..::,.,,,::,.     .i1i::;;:;;:i;:,,,,:;;i:,,;;:   .,,::::,:::::,:,.               \n" +
                                      "             .,:,,,,:::;:i;;;:,::::::iii1i:... ..,,,,,::,.,:;i;;::,  ....,:::: ,,..,:,.             \n" +
                                      "            .:,.,:, ,:::.:;;,.,;;;;::1i:1ii;,.               .:;,:;;i1,...,;;;: .,,:,,,.            \n" +
                                      "           ,,,,,,. .:::. ,;;:::;;::,;iiii;t1i1i;:Lf.       ...,;1ii11i:;;;;;;;:,..,:..,,,           \n" +
                                      "          ,,,,:,..,,,,.  :;,;i,,.   ;:;,  fCti:iLCLL;;;;;;iii;ii1i;11i:;;,.:;:,;;,,::,,:,,          \n" +
                                      "         ,,,.,;::;:,,.  ,:,.;:     :i1t:;11fffffCLLCt:;i1ttLt,..,;t;i..;::,::. .,:;:.,:.,,,         \n" +
                                      "        ,,,:,,.:::,    ,:,.,;.    ,iifftCti1,,LCGLtCGf1fLttLfi;i1f1:i  .:;;;;.  .:,, .,.,,:,        \n" +
                                      "       .,,.,,  ,;:  .,;:::::,    :ii:ttt1:,  iCtC0fGCC1.,,:111tLtti1i    ;;,;,  .,,,,  ,:,,:.       \n" +
                                      "      .,,,.,..:::;:::;;:;:,.   .;;;::ff:    ;CiCLitfC1L:    ..;ff,;ii.   ::.,:,   ,:;:.,:.,,,       \n" +
                                      "      ,,,,:,.:;;;;;..:;i,    ,:;;:,.it;    ;GGC0Gi, 1CfC;      fL.::;,   .::,.::,  .:::;:,,:,,      \n" +
                                      "     .,,..:;:::,,;;;;;:. ..:iii:,,;ti;    iCGtfC80C0GG0GC:     1t:.;:i.   .,;;::i;:,:;;,.:,,,,      \n" +
                                      "     .,,,:,.::;. .;:,i;,;;;11i;ifft;,    ifft:8G;iffGC0fGCi    ;1i,,;;;.     :;;;,;;;;;..,..,,.     \n" +
                                      "     ,,,,,. ,::, ,i1i11ii1itCftL1,      1Lf1;0C     .181tLL1    :1fi;;i;.    ,:;,.:;:::. .:::,,     \n" +
                                      "     ,,,.,. ,,,, .:ii1;iii, ;fit:     .tGL1fGf.       LG;1fL1.   .iLt1t1i:.  ,i;;;:. :;:,,:.,,,     \n" +
                                      "     ,,:::.,;:,   ,:,:i1i;;.,ftti    :fGGLGG:         .f81;fCL,    1ftLfi;;:.:;:;:.  ,;,:;:,,,,     \n" +
                                      "     ,,,,:;:,;.  .;;:;,.:;i1fL1fi   iCL08C8i            1@f1CGC;   1ttf..;;i1i:::.  .:::,.:,:,,     \n" +
                                      "     ,,,.:.,:;: ,:;;;i.  .:i111fLi,tftLit0LG.          ;1G08801ft. ;tif; ,ii;i1ii:. ,,,. ,,.,,,     \n" +
                                      "     ,,:,:. .::;;: ,;;,    .;ii:ifGGLGt  GC0Lt11t11i;it0GLti;fGtCfi1ftfLti1ii11;ii. ,::. ,:,,,,     \n" +
                                      "     .,,.., ,;;;;;:;;i:     .i;;tCCtCLGiL80fLfftttfLG0CG81.,tfGLfGGCi:i11;;:,;i:;;. .;::.,,,,,.     \n" +
                                      "      ,,,,:.,;::,,;;:,;;,.   .tLLLCLCLCCfL0GCLfttffLCC0GCLfLCGGtLCLfL1i;,.  ,:;;::;,,:::;:.,,,      \n" +
                                      "      ,,:,::;:::.  ,::..::.  1CCLfLGL11i;;i;;;;::;;;;::;;i;;iLCffLfCCCi    ,i;:.,;;;i;:.,:,,,.      \n" +
                                      "       ,,,.:,.:;:,.  ,:,.::  ,,i1;:ff:..                    ;Lt,;;i:.,. .,;;:;;:::;::, .,.,,,       \n" +
                                      "       .:,::,  ,,,,   :;,;;    i1iffLt1tt;:,,::;;:,.    .:;ttft;i;.    ,::,:;:,. .:;, .:..,,.       \n" +
                                      "        ,:,,.,. :,:   .;;;i:. .i:tfi;:ifLtfLtti;;i1tti::tifCtftii.    ,:,.,:,   .::::.,,:,:,        \n" +
                                      "         ,,,.:,.::::. .::,::;.,i;t;,,.,tftti;;::,,,:iftff1i::tii,     ;;.::.  .,::;,::,.,:,         \n" +
                                      "          ,,:,,::,,;:,:;:.:;;;i11;i1ii;ii;;:;;;;;:;i;:itC1 .:;:;   .,:i;:;,  ,,,,...,:,,,,          \n" +
                                      "           ,,,..:,..,;;;;;;;;:i1iiii;,..         ..,;i1i11;iii1:,::i;,::;:, .:;,  ,,.,:,,           \n" +
                                      "            .,,,:,,. :;;:,...:i;;;:,;:..      ..      .,;ii1:1i::;;;;,,:;;:,;,:,.,:,,,,.            \n" +
                                      "             .,:,..:,.:::::,..   ,:;;;i;:,,:::,,::,,.....;i1iii::,,,,,:;;;i:::;:,,,,:,              \n" +
                                      "               .,:,:,:;:,,:::,,.   :;:.,;i;::,,,,:;i;i;:i;::i1i.     .:::,,..,::,,,,.               \n" +
                                      "                 .,:,..:,.  .,::,.. :;:,;;;::,,,,::;;;,.,;:;......,,::::. .,,,,,:,.                 \n" +
                                      "                   .,:,:,,,,..::,;;::;;i:,.         ,,:;:;:...,:,,,:::::,,:,,,:,.                   \n" +
                                      "                     .,:,,.,:,::,....;;;:,,,::,,,....,:;;i;:::;,. ...,:,.,:,:,.                     \n" +
                                      "                       ..,,,:,,.,,.....:;:::,,.,:;:,;:,.,:,::::..,,,,,,,,,,.                        \n" +
                                      "                          ..,,,,,:,.,:,,::,......,;::.....,,::,,::,,,,,,.                           \n" +
                                      "                              ..,,,,,,:,,.,::,.,,,:,.,::..,::,,,:,,,..                              \n" +
                                      "                                   ...,,,:,,:,,,,:,,,,,:,,,,,,...                                   \n" +
                                      "                                          ................                                          \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 19 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         .......,..........                                         \n" +
                                      "                                  ...,,,,,,,,:,,,,,:,,,,:,,,,,,...                                  \n" +
                                      "                              .,,,,,:,,,:,,.,:,,,::,,,.,::,.,,:,,,,,..                              \n" +
                                      "                          ..,,,,,,,:,,,;:,.... ,::;,......,::,,,.,,:,:,,..                          \n" +
                                      "                       .,,:,,,:,,,,...:,;::;,,:;:,;:,,,,::::;,....,,,.,,:,,,.                       \n" +
                                      "                     .,,,:,,,::,....,;;:,:;;;;:... .,,,:,,,,:;;,...,:::,:,,,,,.                     \n" +
                                      "                  .,,:,.,:,.,:,;;:,,::. .:;;:::,..       .,,;;;;:;;::;:.,,.,::,:..                  \n" +
                                      "                .,,,,:,,..  :;:,....  ,,;;;;,.:;;;::,,,:::;;:,;:. .,:::,  ..,,.,,,.                 \n" +
                                      "               .,:,.,::,,:::::.     .,11i:;;::i;;i::,,,,:;;i,.:;:.   ,,,:,,.,::,,,,,.               \n" +
                                      "             .,:,:,,,:::;;i;;;::::::::1i11i:.. ...,:,,:::,,::i;;;;,.  ..,:;:,;,.:,.,,,.             \n" +
                                      "            .,,..,,  :::,.:;:,.:i;;;:;1i;1ii::,             ..:;:,:::;;.  .,;;:..,,,:,,.            \n" +
                                      "           ,:,,,,. .,:;. .;;;;::;:,..;i1i;;fi11i;Lf,          .:ii;i11i:;;;;;;;,  .:,.,,,           \n" +
                                      "          ,,,.::,,,,,,,  ,;,,i:..   ,;:;  ;Cf1:;LLCL;;;;:;;;;;;i11;i11;:;;.:;;,:;:,::,,:,,          \n" +
                                      "         ,,:.,:;::;,..  ,:,.:;     .iiti:i11LtfLCLLCt:;;ittft:,,::1ii;.;::.,:, .:::;:,:,,:,         \n" +
                                      "        ,,,,:, ,:::   .,:,.,:,    .;11L1CLi1:.LCGCtLCf1fLf1fL1::;1L;;: .:i:;;,   :::..:..,,,        \n" +
                                      "       .:,..,. ,::,.,:;;::::,    ,;i;1L1t:,. iC1C0fGCC1.,:;1t1tfff1i1,   ;;:;:   ,,,. .,,:,,.       \n" +
                                      "      .:,:,,..:;;;;:,:;;;:.    .;;;;,tf;    iC1LGitfC1L:    .,:ffi,1i,   ,;,,:,  .,::,..:.,,,       \n" +
                                      "      ,,,,::::;;;;;,,::;.   .,;;::,,i1i    ;GGC001, iLfC;      1C:.;;:   .:,.,::.  ,;:,::,,,,,      \n" +
                                      "     .,,,.:::,:,.:;;;;:..,,;ii;,,:iti;.   iLGttC80CGCG0GC:     ;fi ;:i.   .:;:,:;;,.,:::,:::,,      \n" +
                                      "     .,,::, ,;;, .;;:ii;iiitti;1Lf1:.    ifft:0G;ifL0C0CGCi    :11,,;;;     .:;:;:;;;;;, ,..,,.     \n" +
                                      "     ,,,.,, .,,, .i111iii1;iLftL;.      1Cf1;0C.    .18ttCL1    ;1t;;;i;     ,;;, :;;::. ,,,,,,     \n" +
                                      "     ,,,,:..:,,,  .:;ii1ii: .ti1;     .tGCtfGf.       fG;1fL1.   .1Lt111;,   ,i;:::,.:::..:,,,,     \n" +
                                      "     ,,,::,:::,   ,:,:;;i;;::fffi    :LG0CGG:         .f81;fCf,   .tt1Lfi;:. :i:;;.  ,;,:::.,,,     \n" +
                                      "     ,,,.:::,;,  ,;;:i, .:;ifL1ft.  iLf08C81            t@tiCGG;   1tft.:;;iii,,:,   ,:::,::,,,     \n" +
                                      "     ,,,,:..:::,,:::;;.   ,;111tLi,tftL:i0LG.          ;1G0080tft. i1it, :i1iii;;.  ,,,, .,.,,,     \n" +
                                      "     ,,,,,, .:;;;: :;;.     ;i;:;fGGLCt ,0C0Lf11t1i;;it8GLt11LGtCf;iLtLL1i1ii1111i. ,,:. ,,.,,,     \n" +
                                      "     .,,.,, :;;;;:;;:;:.     ;;;tCLtCCCiG8GtLfttttfC00C00;,,tfGCLCGG1:it1ii;;ii:;;. ,;:, ,::,,.     \n" +
                                      "     .,,:::,:::,.,:;:,:;:.   .tLLLCLCLCLfC0GCLfttfLCLG0GCffLCGGtLGLLL1ii:,. .:;;;;:.,:,:::.,,,      \n" +
                                      "      ,,,.,::,:;,  .::,.,:.  1CCLfCGf11i;iii;;::;;;;:::;;i;;iLCfLLfCCC1    ,i:,.,;;;;;:,::,,,,      \n" +
                                      "       ,,,,:..,::,.  ,:,,;,  ,;11:itf:,.                   .if1,;;i:.,. ..;;:i:::;;:;:..,,:,,       \n" +
                                      "       .,::,,. ,,,,   :;:;:   ,1itLLft1f1;;::;;;::,     ,:;f1Li;i;.    ,::::;::, ,;:. .,..,,.       \n" +
                                      "        ,,,..: .:::   ,;;:i:. ;;;Li:,:1Lf1fLt1i;i1tt1;,;11LLtL11:     ::,.,:,    :::,.,:,,,.        \n" +
                                      "         ,,,,:,:;:::. ,:,.:;;.ii1i::,,:tft1;;;::,,,:1f1L11;,i1ii     .i,.::.  .,:;::::,,:,,         \n" +
                                      "          ,:,,,::.,;::;;:,;;:i11;it1i;;;::::::;;;;;;::1LC, ,i:;.   ..:i,:;.  ,,,,,,,::,,,,          \n" +
                                      "           ,,,.,:.  ,;;;;::;:i1ii;;i,.          ..,:i1iit;ii1i;.,,:;:::;;:  ,::,  ,,,:,:.           \n" +
                                      "            .,::,,,..:;;,.  .;::::,;;:,.     ...     .,:;i1;i1::;;;;:.,:;:.,::: .:,.,,,.            \n" +
                                      "             .,,,.,:,:;,:;:,..   ,;;;;i::,::::,,::,,....:i11ii::::::,,;;;i;;:::,,,:,:,              \n" +
                                      "               .,,:,,::,.,,:,,,   .::,.:i;::,,,,::i;;i:;;;:i1i,.     ,::::,.,::,.,,,.               \n" +
                                      "                 .,,,.,:,.. ,:::,,.,;;,:;;::,,,,,::;;,.,;;;:,. ....,::;,  .,,,:,:,.                 \n" +
                                      "                   .,,::,.,,.:;,:;;:;;;;,..        .,:;:;;, ..::,,:;:,:,,,:..,:,.                   \n" +
                                      "                     .,,,,,:,:::....,;;:,,:::,,,....,:;;i;:::;:.....,::,,,::,,.                     \n" +
                                      "                       ..,,:,,.,,,....,;:::,,,,,;;,:;,.,:,::::...,,,,:,,,:,.                        \n" +
                                      "                          ..,,:,:,..,,,::,......,;::......,::,,,:,,,:,,,..                          \n" +
                                      "                              ..,,,:,:,,.,::,.,,,:,.,::,.,,:,,,:,,,,..                              \n" +
                                      "                                   ...,,,,,:,,,,:,,,,,:,,,,,,,...                                   \n" +
                                      "                                           ..............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 20 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         .......,,.,.......                                         \n" +
                                      "                                  ...,,,,,,,,,:,,,,::,,,,:,,,,,...                                  \n" +
                                      "                              ..,,,:,,.,,:,,.,:,,,::,,,.,::,.,::,:,,..                              \n" +
                                      "                          ..,,,,,..::,,:;:.... .,::;,......,::,:,.,::,:,..                          \n" +
                                      "                       .,,,,.,:,,,.. .,:,;:;:,,:;:,;:,,,,::,:;,.,.,,:,.,,:,,.                       \n" +
                                      "                     .,,,::,,,;:,,..,:;:,,:i;;:,....,,,,:::,:;;:....,::,::,,,,.                     \n" +
                                      "                  .,,,,,.,,..,:,;::,,,,  ,:;:::,,.        .,:i;;::;;,:;,.:,.,:,,,                   \n" +
                                      "                 ,,,,,:,,.  ,:::....   :;;:;:.,:;;::,,,,::;;;,:;:..,,::,. ..,:.,,,,                 \n" +
                                      "               .,,,..:;:,:;:::,.   ..,:11i;;;:;i:;;:,,,,,::i;..::,   .:,,,...:,,,:,,.               \n" +
                                      "             .,,,,:,,,:,;:;;;;:::;;::::1i11i:.. ..,::,::::,::;;:;;,.   .,:;;,:;,,:.,,,.             \n" +
                                      "            .,,,.,,. ,;:, ::;,.,;;;;;:i1;i1i;;:.            ..::;:::,,,,  .,:;;..,,.,:,.            \n" +
                                      "           ,,,:,,,..,,:,  :;:;;:;:,..,ii1;,tfi11iLf,.          .;;:iii1i:;::;;;:  .,:,,,,           \n" +
                                      "          ,,,.,;:::::,,  .::.i:..    ;:;, .fLt;;LLCL;;;;::::;:;;iti;111::i::;;:::,.,:,.,:,          \n" +
                                      "         ,,:,,,,:,;,.   .:,.,i.     ;i1t:itifttCGLLCt:;;;1tft::::;;1ii::;:..::..:;,:;:,:,,,         \n" +
                                      "        ,,,.,,  :::   .,::,,:,    .:11LtfCt1;.fCCCtLCtitfL1fL1:,:;tt,i. :;:;;,   ,::: :,.,,,        \n" +
                                      "       .,,,.,. ,:::,,:;;:;::.    ,;i;iLtt;,. iC1C0LCCGt,:;;1ftttLLfi1;   :;;;:   ,:,, .,,,,,.       \n" +
                                      "      .,,,::,,;;;;;:,,;;;:.    ,;;;;,1fi.   iC1L0itLC1L:    ,::tf1,i1;   .i,,:,  .,,:, .:,,,,       \n" +
                                      "      ,,,.,;;::::;;::::;.  .,:;;:,,,i11    iGGC00f, iLtC;      ;C1 ;;;   .:,.,:,.  ,;;,,:,.,,,      \n" +
                                      "     .,,:,:.,::, ,;;:;;,,::i1i;,,;1ti;.   ;LG1iC00CCLG0GG:     :f1 ;,i,   .:;,,:;:..,::;::,:,,.     \n" +
                                      "     .,,,:, .;:, .ii;i11ii1tf1i1Lfi,.    1Ltt,00;;fL0C0GGCi    :11:,;;;     ,;;:;;:;:;;: ,,.,,.     \n" +
                                      "     ,,,.,, .,,:  ,ii1i;i1;:fftL:       1Cf1;0C.    .10LtCL1    ;1t;:;i:     .;;:.:;;;;. ,,.,,,     \n" +
                                      "     ,,:::.,:,:.  .:::i1i;;  1111     .tGCfLGf.       f0i1fL1.   ,1Lt11i;.   ,;;,,::,::, .:::,,     \n" +
                                      "     ,,,,:;:::.   :;:;:,;;;;;fff1    :LG0CGG,         .f8i;fCf,   ,f1tLfi;,  :i:i;,  ,;:,,:.,,,     \n" +
                                      "     ,,,.:,,:;, .:;i:i,  ,;ifL1tf,  iLfG0C0t            t@t;CGG;   tfLt:;;;;:;::;,   ,::;::,,,,     \n" +
                                      "     ,,:::. ,:::::.,;;.   .;i11tL1:tftL::0CC           :iG0000fft. 1111  ;i11i:::.  .:,:..:,:,,     \n" +
                                      "     ,,,.,, ,;;;;:.:;;.     :i;:;fC0LCf :0C0Lft1t1i;;if8GLfttCC1LL:;LtLf:;1i;i1ii, .:,,. ,,.,,,     \n" +
                                      "     .,,,,,.:;;::;i;:;;,     ;:;tCLtCCC108CtffttttLC00C0C:,:tfGCLCGC1;1ftiiii1i;i;  :;;. ,:,,,.     \n" +
                                      "     .,,:,::;,:, .:::,,::.   ,tLLLCCCCCffG0CCfttffLLC00GLfLLCGCtLGLLL1i1;:,,,;;:;:, ,:,:,:,:,,      \n" +
                                      "      ,,,.,:,,;;,  .,:,.,:.  1CCLfGCf11i;ii;;;:;;;;:::;;;;;;1LLfLffCCC1.   ,;::,:;;;::::;,.,,.      \n" +
                                      "       ,,,,:. .:,,.  ,:,:;.  ,i1i;1f1::,                   .1fi,;:i;,,.  .;;;;,,:;;;;:.,:::,,       \n" +
                                      "       .,,,.,. ,,:.   :;;;;   iiiLLftttL1i;::;;;::.     ,,1ttL;ii;.    ,::;:;;:,.:::. .,.,,,.       \n" +
                                      "        ,,,.,,.:::,.  :;:;;: .i:f1:,.:1LttLft1i;i1tti:.iitCttL1i,     ::,,::,.  .:::  :,,,,,        \n" +
                                      "         ,,::,:;:,;:,.::.,:;:;1i1;;;::;ttt1;;;::,,:ifftf11::1ii:     ,i..::.  ..:;,:,,,,,,,         \n" +
                                      "          ,:,,.:,.,::;;;::i:;11i;11i;::::,,:::;;;;;::;tCt  :;::    ..;;.::. .,,:::,:;,.,:,          \n" +
                                      "           ,:,,:,.  :;;;::::i1ii;:;;.           .,:i11it1:i1ii..,,;;:;;;;:  ::,. .,,,,,,,           \n" +
                                      "            .,:,..,.,;::,.  ,.,,:::i;:,.    ...     .::;i1i;1;:;;;;:,.,;:,.::;, .,,.,:,.            \n" +
                                      "             .,,,,:,:;:,;;:,..  .,;;:;;::,:::,,::,,....:i11i1:::::::,:;;;;:;,:,,,:,,,,              \n" +
                                      "               .,::,,,:...,,,:.   ,::..;i::,,,,::;;;i;:;;;i11,..    .,:::::,:;:..,,,.               \n" +
                                      "                 .,,,,:,,. .:::,:,.:;::;;;:,,,,,,:;;:..:;:;:,  ....,:::. ..,,:,,,,.                 \n" +
                                      "                   .,::,.,:.,;:,::::;;;:,.        ..,:::;:. .,:,,::;,:,.,:,.,:,,.                   \n" +
                                      "                     .,,:,::,,:,.  .;;;:,:::,,,,...,::;;i:,::;,...,,::,,,:,,,,.                     \n" +
                                      "                       ..,:,,.,:,,.,.,;:,,,...,:;,:;:,,:::;,:,...,,,,:,,:,,.                        \n" +
                                      "                          ..,,,:,,.,:,:,,......,:::, ....,:;,,,::..,,,,,..                          \n" +
                                      "                              ..,,,,::,,,::,.,,,:,.,::,.,,:,,,,,,,,,..                              \n" +
                                      "                                   ...,,,,:,,,,::,,,,:,,,,,,,,...                                   \n" +
                                      "                                           ..............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 21 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         ........,,.......                                          \n" +
                                      "                                  ...,,,,:,,,,,:,,,,::,,,,:,,,,...                                  \n" +
                                      "                              ..,,,::,,.,::,,.,:,,,::,,..,:,,,,:,,,,...                             \n" +
                                      "                          ..,,:,,,.,:,,,:;,,... .,:::,.....,,::::,,,::,,..                          \n" +
                                      "                       .,,,,,.,:,,,.. .,::;:;:,,;;::;:,,,,,,,;:,.,.,::.,,,,..                       \n" +
                                      "                     .,:,,::,,:;::,..,:;:,,;;;;:,....,,,::::,:;;,.. .,,,,:,,:,.                     \n" +
                                      "                  .,,:,,.,,. .:,:;::,,,. .:;;,::,.        ..,;;;;:::,,:;,,,.,,:,.                   \n" +
                                      "                .,,,.,::,....:;:,.     .;i;:i,.,;;;:,,,,,::;;;:;;,,::,::, .,,:,,:,,                 \n" +
                                      "               .,,,,,,::::;;:::......,,;1ii;;::i;:i;:,,,,::;i: ,:,    :,,,. .,,.,,:,.               \n" +
                                      "             .,,,.,:. :,:::;;;::::;;::,;ii1i;:. ...,:,,::::,::;:;;:.   .,:;;,,;:::,,:,.             \n" +
                                      "            .:,,.,,. .:;:.,:;;,.:;;;;::i1;i1i:;:.     .     .,:;;;::,.... .:,:;:.:,.,,:.            \n" +
                                      "           ,,,,::,..,,,:  ,;::i:::,.. :iii::L1i11Lf,.           ,;,:;;i1;:,,:;;;. .,,:,:,           \n" +
                                      "          ,,,.,:;:;::,.  .::.;;.     ,;,;  iLLi;LLCL;;;::,,,,:::;11ii111:i;:;;;::. .:,.,,,          \n" +
                                      "         ,,,:,,.,:::    .::..;,     ,iiti;111f1CGLLCf:;;;itft;:;;iii1i1:;;, ::,,:;,,::,,:,,         \n" +
                                      "        ,,,.,,  ,;:. .,:;::,:,     ,i1ff1Cf1i tCLGfCCtitfLttL1:..:if;;; :;::::  .,::;,,:.,,,        \n" +
                                      "       .,,,,,..,:::,::;;:;;,.    ,;ii;Lt1i,. iLiC0LCGGt::;i1Lft1fLLt;i,  ;;;;;.   :,: .,.,,:.       \n" +
                                      "      .,,,,:::;;i;;:,.:;;,    .,;;;;,1f1.   iGtf81tLGtL:    ,::1ft;:11.  .;:,;,  .:,,. .:,:,,       \n" +
                                      "      ,,,.,:;,::,::;::::, .,:;i;:,,:i11.   iG0CG0L: ;L1C;      ,Lf.:;;.   :,.,:,  .:;;,.,,.,,,      \n" +
                                      "     .:,::, .:;: .;;:ii;;;;i1i;,;itti:.   ;fCt;C00CCfCGCG:     ,ft.::;,   .::,.::,. ,:,:;:,,,,      \n" +
                                      "     .,,.,,  :::  ;1i11ii11tLt1tLt;.     1Ltt,G0i;fC8C00GL;    ,11:,;;;    .,;;,;i;::;;;,,:,,,.     \n" +
                                      "     ,,,.,, ,,,,  .:;iiiii;.if1f;       1CftiGG.    .10CtCL1    ;11;:;i,     .;;;,:;;;;, ,,.,,,     \n" +
                                      "     ,,:::,:;:,   .:,,;i1;;, it1t.    .tGCLLGt.       t0i1fL1    :tLt1ii:    ,;;,.:::::. .,,,,,     \n" +
                                      "     ,,,.:::,;,  .:;:;: ,;;;itfft    :LG8GG0,         .f8iifLf,   :L1tLti:   ,;;;;:. :;:..:,,,,     \n" +
                                      "     ,,,,:..:;: .::;;;,   ;itLt1L:  iLfCGG0f            t@1;CGG;  .tff1;;;;,.;;:;:   ,:,:;:.,,,     \n" +
                                      "     ,,,,,. .::;;: ,;;.    ,iiitL1;tLtL:,0CC           :i00GG0LLt..t1t; ,i;1i;,::.  .::;:,:::,,     \n" +
                                      "     ,,,.,, :;;;;::;;;.     :i;::tC0CCf i0CGLtttt1i:;if8GffffCCiLL:;f1fi.;1iiii;:. .:,,. ,,.,,,     \n" +
                                      "     .,,,:,,;::,,;i:,:;,.    i:;tCLtLGCt08CtfttttfLG8GG0f:,:tfGCLCGCtitLt11i11ii1;  :::  ,,,,,.     \n" +
                                      "     .,,,,:::,:,  ,::,.::.   ,tLLLGCCCLff0GCLfttfLLLG00GffLLGGLfCCCLLii1i::::i;;;;. :::..:,:,,      \n" +
                                      "      ,,,.,, ,:;:.  ,:,.::   1CCffCCttii;ii;;;:;;:::::;;;;;;tLLfLffLCCt,.. ,::::;::,:::;:,.,,.      \n" +
                                      "       ,,:,,. .,,:.  ,::;;.  :t1;1tfi::,                   ,tti,;;i;,,.  .:;;:.,;;;i;;,,:,,,,       \n" +
                                      "       .,,,.:. :,:   .;i;;;  :i;tLft1tfL1ii;;;;;:,      ,:t1ff;ii:.    ,:;;:;;:,,:::,..,.,,,.       \n" +
                                      "        ,,,,:,,;::,. .::,;;: i:if;,..:1LttLf1i;i1tt1:.,11LC1Lt1i,    .::,,::,.  .;;, .:,.,:,        \n" +
                                      "         ,,:,,::,,;:,,;: :;;;11iiii;;:;ttti;;;::,,:tLtL11i:i1ii.     :;.,::.   .:::,.,,:,,,         \n" +
                                      "          ,,,.,:. .,:;;;:;;:111ii1i:,,,,,,,,::;;;;;,:1LL: .i:;.     .i:.::  .,,::::;:..,,,          \n" +
                                      "           ,,,:,,, .;;;:,,,;ii;;:,;,           ..,;11i1L:;1ii: .,,;:;i::;, .:,,...,::,,,.           \n" +
                                      "            .,,,.,,.:;:::. .  .,::;;;:,.    ...    .:;:i1i;1i::;;;;,.,;;:..:;:. .,,.,,,.            \n" +
                                      "             .,:,,::::,,;;:,,   .:;;:;:;,::::,,::,,...,;11i1;,::;;::::;;;::::,.,:,,,,,              \n" +
                                      "               .,:,,.,,  .,,,:.   ,:, :i;:,,,,::;i;;;::;;i11:,..    .:::;;::;:,.,,:,.               \n" +
                                      "                 .,:,::,,. ,:::::,,;;:;;;:,,,,,,:;;:,.,i:;;:    . .,:;,. ..,,:,,,,.                 \n" +
                                      "                   .,:,,.:,,;:,,::,;;;:,.         .,:::;::. .,,,::;:::..,,,.,,:,.                   \n" +
                                      "                     .,:,::,,,,. ..,;;:,::::,,,....,:;;i;,,:;:,..,,:;,,,:,,,,,.                     \n" +
                                      "                       ..,,,,,::,.,.,;;:,,...,:;::;:,.,;:;::,. ..,,,:,.,,,,.                        \n" +
                                      "                          ..,,:,,,,:::,,,.....,:::,. ...,:;:,,::..,,,:,,.                           \n" +
                                      "                              ..,,,::,,,,:,.,,,:,.,,:,.,,::,,,,::,,,..                              \n" +
                                      "                                   ...,,,,,,,,::,,,,:,,,,,,,,,...                                   \n" +
                                      "                                          ...............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 22 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         .........,........                                         \n" +
                                      "                                  ....,,,,:,,,,::,,,,::,,,,:,,,...                                  \n" +
                                      "                              .,,,,,,:,,.,:,,,,,,,,,::,,.,,:,,,,:,,,..                              \n" +
                                      "                          ..,,,::,,.,:,,,:;:,... .,:;:,.....,,,::,.,,:,,,.                          \n" +
                                      "                       ..,:,,,.,:,,..  .:::;;;,,:;:,:;:,..,,,:;:.,,,,:,,,,,,.                       \n" +
                                      "                     .,,,.,:,,,::::,.,:;::,:;;;;,,....,,,::::,;;:....,,.,,:,,,.                     \n" +
                                      "                  .,,,,,,,,.  ,:,::,,,,.  ,:;::::,.        .,:;;;:::,,,;:,:,.,,,..                  \n" +
                                      "                .,,:,.,::,,..,::,.      ,ii:;;..:;;;,,,,,,::;;:;;:,::::;:..,,,:,,:,                 \n" +
                                      "               .,,,:,,,:::;i;:;,.....,,,;111i;:;i;;i::,,,,::;;..::.  .,:,,.  .,,.,,,.               \n" +
                                      "             .,,,.,:. .:,:,:;;:,,:;;;::,iii1i;:. ..,::,::::,::;::;;.   ,,:;:,.:::,:,,,.             \n" +
                                      "            .,:,,,,. .:;:. :;;:,,;;:;::;11;11;;i:.    ..     .:i;i;:,..   .::,:;,,:.,,,.            \n" +
                                      "           ,:,.,;:,,,,,,. .;:,i;::,.. .i;i;,tL1i1Lf:..          ,;:,::;;;:,,:;;;, ,,,::,,           \n" +
                                      "          ,,:,,,:::;,,.  .::.,i.      ;:;: ,tLt;LLCL;;;::,,,,,,,,;iiii11i;i;;;;:,. .,,.,,,          \n" +
                                      "         ,,,,:, .:::    .::,.::     .ii11:1tif1LGCLCf:;;iittt;;;;i1i;11i;;;.:;:,:;,.,:,,,:,         \n" +
                                      "        ,,,.,,  ,:;. .,:;::::,.    ,;1tL1fCti,1GLGfCC1itfLttf1:..,:1t:i,,;;,::. .:::;::::,,,        \n" +
                                      "       .,,:,,.,:;:;::::;;;;,.   .,;ii;ff11,. ;LiLGLLGGt::ii1fLt1ifLLi;i  ;;:i;.   :::..:..,,.       \n" +
                                      "      .,,,.:::;;;;;;,.,;;,   .,:;;::,1tt,   iGff8ttLGfL:    ,;;ift1:i1;  .;;:;,   :,,. .,,:,,       \n" +
                                      "      ,,:,,,,:,:.:;;;;;:,.,:;ii;,,,:111.   ;C0CC0C: ;L1C;      .fL:.i;,   ::,::,  .:::. ,:,,,,      \n" +
                                      "     .,,,,:. :;:  ;iii1iiii11i;:itfti:    ifLt:C00CCtLGCG;      tf.,:;:   .::.,::. .:;,,::.,,,      \n" +
                                      "     .:,.,,  ,,:. ,iii1ii111Lf1ff1:      iLtt,G0i:tL8GG0GC;    .11;.;:i    .:;:,:;:,,::;::::,,.     \n" +
                                      "     ,,:,:,.:,:,  .:::ii1i; ,t1t;       1CftiGG.    .1GGfCL1    ;11::;i,     .;;;;:;;;;: ,,.,,,     \n" +
                                      "     ,,,,::;::,   ,;::::i;;; :ftt.    .tGGCCGt.       t011fLt.   ;tftiii.    .;:,.:;;::. ,,,,,,     \n" +
                                      "     ,,,.:,,,;,  .;i:;:  :;;ifttf.   :LC8GC0,         .L0iifLf,   ;L1fL1i,   ,;;:::..:;:  ::,,,     \n" +
                                      "     ,,::,  :::.,:,:;i,   ,i1ff1L;  iftLLG0f            f81:LCG;  .ftft;;;:  ;;;i:.  ,;,:,:.,,,     \n" +
                                      "     ,,,.,, ,::;;:.:;;.    .iiitf1;tCtC: GCC           :i00CGGLLt..ftf, ;;ii;:,::.   ,::;::,,,,     \n" +
                                      "     ,,,.:..;;;;;;;;;;.     ,i;::tCGCCf tGCGLfttt1;:;iL8GffLLGCiLL:it1t,.ii1ii:::   ,,,:.,,,,,,     \n" +
                                      "     .,,:::;;::,,:;:,:;:.    i:itCCtfGCL80LttttttfC08G001:,:tLGLLGCLt1LL111;i1iii. .:,, .,..,,.     \n" +
                                      "     .,,,.::,,;:. .::,.::.   :1LLLGCCLffL0CCLftffLLCG00CffLCGGffCCCLLii11;;ii1i;i; .:;: .:,,,,      \n" +
                                      "      ,,,,:, .:::.  ,::,;,   1CCfLCLttiiiii;;:;;;::::;;;;;;;fLffLffLCCt;:..,:;;;;::,:,:,,,.,,.      \n" +
                                      "       ,,:,,, .,,,   ,;;;;.  itiittfi;;,                   ,tti,;;i;:,.  .:;;,.,;;;;;;:::.,,,       \n" +
                                      "       .,,..:.,::,   ,;;:i: .i:1LftiitCf111;;;;;:.     ..;t1Lf;i;:,    .,i;;;::::::::..,,:,,.       \n" +
                                      "        ,,,:::;;,::. ,:,.:;,:i:fi:..,:1ftfLt1i;i1tt;,.;1tCf1L11;.    .:::::;:.. ,;:. .,..,,.        \n" +
                                      "         ,,,,,:,.,;:,;;,.;;;111;i1ii;;;tt1i;;::,,,ifftf11;:1ii;      ;:.,::.   .:::..,:,,,,         \n" +
                                      "          ,,,.:,. .,;;;;;i;11iii;i;,......,,::;;;;:,;tLt. ;:::      ,i,,:,  ..,:;:::,.,,,,          \n" +
                                      "           ,,:,,,, ,;;;,..:;;:::,:;,           .,:i1iiL1:i1i;  .,:::;;,:;. .:,,,,,::,.,:.           \n" +
                                      "            .,,,.:,,;:,;:,.   .,:;;;i:,    ...     ,;;;11;11::;;;;:,,:;:: ,:;,. .,,,,:,.            \n" +
                                      "             .,,,:,::,.,:::,,   .;;:,:;::;::,,::,,...,;i1ii;,::;;:::::;;;,:,:..,:..,,,              \n" +
                                      "               .,,,.,:.. .,,:,.  .::..i;:,,,,,:;i;;;;:;;i11;,,.... .:;:;i;:::,,,:::,.               \n" +
                                      "                 .,,::,,,..:;::;:,:;;;;;::,,,,,,:;;,.,;;:i;.      ,:;:,..,,::,.,:,.                 \n" +
                                      "                   .,,,.,::::,.,,,:;;;,,.  .      .:;:;;:, .,,,,:::,:. .,,,,,::,.                   \n" +
                                      "                     .,,,:,..,,....:;;,:;;:,,,..,,,::;;;:,:;;,,.,::;:,,::,,:,,.                     \n" +
                                      "                       ..,,,,,:,.,,.:;:,....,:;:,::,,,;;;::,. ...,,:,.,,,:,..                       \n" +
                                      "                          ..,,,,,,:::,,,.....,:;:,. ...,,;:,,,:,.,,::,,,.                           \n" +
                                      "                              ..,,,:,,,,:,..,,:,.,,:,,,,::,.,,::,,,...                              \n" +
                                      "                                   ..,,,,,,,,::,,,,:,,,,,,,,,,...                                   \n" +
                                      "                                           ..............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 23 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         ..................                                         \n" +
                                      "                                  ...,,,,:,,:,,,,::,,,,:,,,,,,,...                                  \n" +
                                      "                             ...,,,,,,::,,.,:,,,,,,,,,::,,.,::,,,,,,,.                              \n" +
                                      "                          ..,,:,,::,,,,,..,:;:,..  .,:;:,....,,.,::,,,,,..                          \n" +
                                      "                       ..,,,::,,,::,,.. .,:,:;;:,,:::,;:,.....,:;:,,.,,:,:,..                       \n" +
                                      "                     .,:,,..,,..,::;:,,:;;:,:;;;;:,.,..,,,:;;:,:;;....,,,.,,:,.                     \n" +
                                      "                  ..,,,,:,,,.  ,:::,,,,..  :;;;:::,.       ..,:;;;:,...,::::,,:,,                   \n" +
                                      "                ..,:,,,,:;:::::::,.     .,:1i;;,.,:;;:,,,,,,:;:;;;::;;:,:;,,,.,,:,,                 \n" +
                                      "               .,,.,:,,.,,:;i;:;:,,,,,,::,ii11i:;;;;i;::,,,,:;i,,;:  .,,:,, ..,:,,,,.               \n" +
                                      "             .,:,,.,,  ,;::,:;;:,,,;;;;;,:1;i1;;,..,,::,:::;:;;:.::,    ,,::. .,,.,,:,.             \n" +
                                      "            .:,,:::,..,::, .;:;;::;;:::,:i1;iti:1;,    ...    ,;;:;;:..  .,:;:,:::::,,,.            \n" +
                                      "           ,,,,.,:::::,,.  ,:,,i:,,..   ;;i;,1C1iCL;:,          ,:;:::,,,...,::;;,,:.,,:,           \n" +
                                      "          ,,,,,,..:,:.    ,:,.;;      .;:;: ,tLtLLCL;;;::,.....  .:;::;;i1i;::;;:, .,,:,,,          \n" +
                                      "         ,,,.,,  ,;:.  .,:::,::.     ,iiii:1t1tLGCCCf,:;i;1t1;;;;;11i;111i;;:;;;::,  :,.,,,         \n" +
                                      "        ,:,:,,..,:::,,,:;;;;:,.    ,:i1Lf1LL1;tGLGtLGt;1tfftfi:,,,:;i1iii;:,.::.,:;,,::,,,,,        \n" +
                                      "       .,,,,;::;i;;;;,,,;;:.    ,:;ii;tLt1i. iL;f0ftGCt:;111tLf;::itL1:i,,i:::,  .,::;:::,,:.       \n" +
                                      "      .,,,.,,:,::::;::::;:..,:;;;;:::i1t:   ;GCf8CtLGLL:   ,:1t1tfffii1; .;:;;:   .:,: ,,.,,,       \n" +
                                      "      ,,,::, .:;, ;iii1ii::i1ii,,,:it1i,   ;CGLf00i.:L1L:      .;Lt.,ii,  ,i:::,  .:,,  ,,:,,,      \n" +
                                      "     .:,..,. ,::, .;ii11;i1tfti;tLf1:,    iLff.LG0GLitCfC;      ;C1 ;:;.  .:,,::,  .:;, .:.,,,      \n" +
                                      "     .,,,,,.,,,:.  :,:ii;ii:iLtf1,.      iLtt,C81,tL8GG80G;     iti.;:i.   ,::,,::,.,:,,::,,,,.     \n" +
                                      "     ,,,,::;::,   .::,:;i1;; ,t1t.      1CLf1GG,    ,tC8CCL1    ;11::;;,    .:;::;;::;;;::::,,,     \n" +
                                      "     ,,,.:,:,:,   :;;;;. ;;;;.tLf,    .fGG0GC1.       10f1Lft.   itf1ii;      :;;,:;;;;:.,,.,,,     \n" +
                                      "     ,,:,,  :;: .:::;;:   :;;ff;f:   :LL00L0,         .L0iifLf,   1ftft1:    ,i:.,:::::. .,,,,,     \n" +
                                      "     ,,,,,. ,:::;:.,;;.    :1tffLi  iftLiG0L            L8i:LCC;  ;fift;;:   ;:;;:, .:;: .:,,,,     \n" +
                                      "     ,,,.:..:;;;;::;;:      ;i;1t1itGfL: CGL           ,;00fLGCCt.,fLt.;;;; .;;;;,   ::,::,.,,,     \n" +
                                      "     ,,::::;;;;::i;,:;:.    ,;;::tCCCCf.C0GGLftt1i::i1C8GfLGG0C;ffit1t, ;;1i;:,::   .::;::::,,,     \n" +
                                      "     .,,.,::,,:,.,::,.::,   .i,itCGif0GG8Gt11tttfCG80G0L;:,itCGLfGCLt1Li;1i;1i:::  .:,,..,,,,,.     \n" +
                                      "     .,,,,:. :;:.  ,::,::.  .iiCLCGCCftfGGGCffftLLCG80CffLLGGGttCGCCCttft1ii111i;. ,::. .,.,,,      \n" +
                                      "      ,,::,,  :,:.  ,::;;.  ,fCLLLCLf1iiiii;;;;;::::;;:;;;;iLfLLfffLCCt1;::iiiiii: ,::. ,,:,,.      \n" +
                                      "       ,,,.:,.:,:    :;;:;  i1i1Lfft1ti,,                 .:t1;,;;;i;;:...:;:,::;:;::,:,,.,,,       \n" +
                                      "       .:,,,::;:::.  ::,:i,,i,tf1;,,;tLt1t1iii;:,      .:11fL1;i;:,.    ,;;;,,,;;:;;;::;,,,:.       \n" +
                                      "        ,:,,,:,,,;;,.;: ,:;1iiii;;,:::1ftfft1ii1ft;, ,itCf1ff1i:.    .::;;;;:,,.:::,..,,:,,.        \n" +
                                      "         ,,,.,,  ,::;;;:;;111i;i1i;;;;;i1i;;;:,,,iff1f1ti:i;ii.     ,;,,:::.   ,:;. .:,.,:,         \n" +
                                      "          ,,::,,. ,;i;::;i1i;;::::       .,,:;;;;,,;tLt. ;:;:      .i:.,:,    ,:,:..,,:,,,          \n" +
                                      "           ,,,,.:,,;:::,....,,::;;:,.         .,;11i1Ci:iii:  ..,,,;i.,:,  .,,:::::,.,,,,           \n" +
                                      "            .:,,::::,,:;:,.   .:;;:;;,  .....    .;1:it;;1i,,;::;;,:;;:;..:::,..,::,,,,.            \n" +
                                      "             .,:,,.,,. .,,,,    ,::.:;;:;::,:::,,,.,;;1ii1,:;;;;:,,,:;::,:::. .,,.,,:,              \n" +
                                      "               .,:,,:,,. ,::,,,. :;,,i;:,,,,::;i;;;::i111;::,,,,..,:;:;i;::,.,,:,,:,.               \n" +
                                      "                 .,:,,.,,:;:,:;;::;;;:::,,..,,,:;:,.,;;i1:.      .,::,,:::;:,.,,:,.                 \n" +
                                      "                   .,:,,::,:, ..,:;;;:,....     .,:;:;:;,  .,,,,::::.  .,,,:,,,,.                   \n" +
                                      "                     .,:,,.,,,,...;;:,:;::,,,.,,,,:;;;;:,:;:,,,:;::,..:,..,,:,.                     \n" +
                                      "                       ..,,,:,,.,,:;:,. ...,:;,:::,,,;;:,:,. ..,,;:,,,::,,,.                        \n" +
                                      "                           .,,,,,:,,.,,,,,,,:;:,.  ..,:;:,,,,,.,,::,,:,,..                          \n" +
                                      "                              ..,,,,,,::,.,,::.,,::,.,,:,.,,::,,,,,,..                              \n" +
                                      "                                   ...,,,,,,:,,,,::,,,,:,,:,,,...                                   \n" +
                                      "                                          ................                                          \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 24 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         ..................                                         \n" +
                                      "                                  ...,,,,,,,,:,,,,::,,,,:,,,,,,...                                  \n" +
                                      "                              .,,,,,,,,::,,.::,,,,,,,.,::,,.,::,,,,,..                              \n" +
                                      "                          ..,,,,.,:,,,,,...,:;:,..  .,:;:,,,,,,,.,::,:,,..                          \n" +
                                      "                       ..,:,,:,,,,:;:,...,,:,;i;,,:::::;:,..  .,:;:,,.,::,,,.                       \n" +
                                      "                     .,::,,.,,...,::;:,,:;:,,:;;;:,,,,..,,::;::,:;:...,:,.,,,,.                     \n" +
                                      "                  .,,:,.,::,.. .::::.....  .;:;::;,.      ...,::;i:,.  .,,,::,,,..                  \n" +
                                      "                .,,::,,,,::;;::::,.      ,,;1ii:.,,:;:,,,,,,,::;;;;,:;:,,;;,:,.,,:.                 \n" +
                                      "               ,,,,.,,. .:,:;;:;;,,,,:::::,i111i:;;;;i;::,,,,:;;,:;..,,,:::..,,,:,,,.               \n" +
                                      "             .,:::,,,. .:;:,::i;,,,:;;i;:,;1:1i;;,.,,,:::::;::;:.,::    .:,:.  ,,..,,,.             \n" +
                                      "            .,,..::::,,::,  :;:;;::;;::,.:i1;if;;1:.   .....  ,;i::;;,   .,:;:.,:::,:,,.            \n" +
                                      "           ,:,,.,,::;:,,.  ,:,.;;...    ,i;i::LL1LLi::.         ,:;i;::,.  .,:::;;,:,.,,,           \n" +
                                      "          ,,,,:,. :,:,    ,:,.:i.      ,;:;. iffLCCf;;;;:,.       .;::::;;i;::;;;: .,,,:,,          \n" +
                                      "         ,,,.,,. .:;,  .,;;::::,     .iiii:if1tCCGGCf,:;;;i1i;;::;;iiii1111i;;;;::,  ,:,,:,         \n" +
                                      "        ,,::::,,:::::::::;;i:,.   .,:i1Lf1tCt;fCfG1fGt;1tfftfi::::;ii;ii11;: :;,,:;,.,:.,,:,        \n" +
                                      "       .:,.,:;:;;i:;;,.,;;:.   .,:;i;;tLfi1, iC;t0f1GCt:;1t1tft;:,:itf;:;:;:,::  .:::;::::,,.       \n" +
                                      "      .,,:,,.,:,:::;;;;;;;,,;;i;;:,,:i1t:.  ;GGL0GfLGLC:  .::1ftttfLtii1, ;:;;:    :::,,:.,,,       \n" +
                                      "      ,,,,:, .:;, ,i11111i;i1i;,,:;1t1i,   ;LGLtG01.:L1L;     ..:ft;.ii;  .;;;:,  .:,, .,,,,,,      \n" +
                                      "     .,,..,. ,,:,  ::;i1:ittfL1itLf1:.    iLff.fG0GCiiCtC;      ,Lf.:;;,   ;:,::,  ,::, .:,,,,      \n" +
                                      "     .:,:::,:,,,   ::,:iiii:.tttt,       iLtt:C8t,1C@GC00G;     :ft.;:i,   ,::.,::. ,::,,:..,,.     \n" +
                                      "     ,,,.::;::,   .::;:::1;;, 1tf,      1CLLtGG,    ,fC8GCLi    :11;:;;,    .;;,,;;::;::;:::,,,     \n" +
                                      "     ,,,.:..,::  .:;;:;  .;:;,1Lf:    .f0000C1.       i0ftCft.   i1t1;i:      :;;::;:i;;,,:.,,,     \n" +
                                      "     ,,::,. ,;:.,::,:;:   .i;tL;ti   :fLG0L0,         .f0iifLf,   1ffft1,    ,;:..:;:::, .,.,,,     \n" +
                                      "     ,,,.,. ,::;;:.,;;.    ,1tfLfi  iftL;C8C            L8i:fCL;  1tiL1;;.   :;;,::..:;, .,::,,     \n" +
                                      "     ,,,,:,:;i;:;;;;;:      :;;1t1itGfL:.CGf           ,;8GtfGCGt.:LLi,i:;, .i;;;,  .::,,,,.,,,     \n" +
                                      "     ,,,,::;::;,:;;,,;;.    :;i,:tCLCCf,GGGCCLtt1i::itC8CLC0G0Ciff1tt1 ,;;1::::::    :::;::,,,,     \n" +
                                      "     .,,.,,.,:;, .::,.::,   :i,itCC:f0GG8C1i1ttfLG08G00t;:,1tGGLtCCCttt,;iiii:,:,  .,,,:,:,:,,.     \n" +
                                      "     .,,,:,  ,::,  ,::,::   :;1CCCGCLttf0GGLfftfLLC080LfffLGGG1tCGCCCffftti:1ii::  ,::, .,.,,,      \n" +
                                      "      ,,,,,, .,,:   ,;;;;.  ;fCLLCCCLtiiiii;;;;;::::;;;;;;iiffLLffLLLCt1i;i11111i, :;:  ,,,,,.      \n" +
                                      "       ,,,.:,::::   .:;::; :1i1fLftttf1:,                 .;t1;,:;;i;i;:,,;;;:;;;:::,:,,,.:,,       \n" +
                                      "       .,,:::::,::. .::.:;:i:if1;,,,;tft1f1iii;:.      .;11LL1;i;::,    ,:;:,,,;;;i;;:;:,.,:.       \n" +
                                      "        ,,,..:..,;:,:;,.:;111i;ii;::::1fffftii1tf1:. ;ifLt1ff1i:.    .,:i;;:::,,::::,,:::,,,        \n" +
                                      "         ,,,,:,  .,:;:;;i111ii;ii;:::::iii;;;:,,:tLttttt::i;i;.     ,;:::;:,.  ,;:. .,..,,,         \n" +
                                      "          ,::,.,. :;;;::;;;;::,:;.       .,,:;;;:.:1Lf; ,;:;,      ,i,.,:.    ,::, .,,,,,,          \n" +
                                      "           ,,,.,::;;:::,.  .,::;i;;,         .,:i1iiLL:;i;;.  ..,,,;;.::,  .,,::::,,.,,:.           \n" +
                                      "            .,,:,,::..:;:,.   .;;::i;, ......   .:1;;fi;1i:,:::;;::;;:;: .:::,,,:::.,,,.            \n" +
                                      "             .,,,..,,  .,,:.    ,:,.:;:;:::::::,,.,;;ii;1:,:;i;;:,,,;;::,:;:. .,,,::,,              \n" +
                                      "               .,,,:,,,.,:::::,.,;:,;;:,,,,::;i;:;;:i111i,::,,,,.,,;;:i;:,:..,:,.,:,.               \n" +
                                      "                 .,,,.,::;;,,:;:,;:;:::,.....,:::,..;ii1:,.      .,::::;:::,,,,::,.                 \n" +
                                      "                   .,,,:,,,,.  .,;i;::,....     .,;::;::. ..,,,,:::,. .,,,:,.,:,.                   \n" +
                                      "                     .,,,,.,:,.,.:;:,::;::,:..,,,::;;;:,,:;:,,:;::,..,,,.,,:,,.                     \n" +
                                      "                       ..,,:,,.,,:::,.  ..,:;::::,,,;i;,:,. ..,,::,,,::,,:,.                        \n" +
                                      "                          ..,,,,::,.,,,,,,,:;:,.  ..,:::,..,,,,,,:,,:,,,.                           \n" +
                                      "                              ..,,,,,::,,,,:,..,::,.,,:,.,,::,,,,,,,..                              \n" +
                                      "                                   ...,,,,,:,,,,::,,,,:,,,,,,,...                                   \n" +
                                      "                                          ...............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 25 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         .......,..........                                         \n" +
                                      "                                  ...,,,:,,,,::,,,,:,,,,,:,,,,,...                                  \n" +
                                      "                              ..,,::,,.,::,..::,,,,,,,.,::,,.,:,,:,,,.                              \n" +
                                      "                          ..,:,,,.,:,,,,....::;:...  .,:;,,,.,,,..,::,:,..                          \n" +
                                      "                       .,,,,.,:,,,,;::,...,::,ii:,,:::::;:,..  .,:::,.,,::,,.                       \n" +
                                      "                     .,,,:,.,,,...,,:::,::;,,::;;;:,,,,.,:,::::,,;;,.,,,:,.,,,.                     \n" +
                                      "                  .,:,,..:;:,...,;::,..... .,;;;:::,.     ...,,::;i:.   ,,.,,:,,,                   \n" +
                                      "                 ,,,,:,,,,::;;;:::.      .,:;11i,.,::::,,..,,:::;;;::::,,:;::,.,,,,                 \n" +
                                      "               .,,,..,.  ,:::;;;;:,,,:::::,,i111i;;;:;i:::,,,,:i::;:.,::::::.,,,,:,,.               \n" +
                                      "             .,,,::,,...,;:,,:;i:,,,:;;i;:,ii:1;;;,,,,::::::;:;;,.::.   .:,,. ..:,.,,,.             \n" +
                                      "            ,,,,.,:;:::::,. ,;,:;;:;;::,..;1i;tt:11,.   ..... .:i:::;,   .,:;:..,:,,::,.            \n" +
                                      "           ,,::,,,.:,;,..  ,::.:;...     :;ii,1CfLLi;:,         ,;:i;;:,.  .,::,:;::,.,,,           \n" +
                                      "          ,,,.,,  ,::,    ,::,,;,      .;:;: :tfCCCL;;;;:,.       .::,,,::::::;;;;..:.,,:,          \n" +
                                      "         ,,,,.,. .::, ..,;;;;::,     .;iii;:tf1CCCGGt,:;;;i1i:::,,::;;;;i111i;:;;,, .,:,,,,         \n" +
                                      "        ,,,,::::;;:::::::;:;:.    .,:i1fft1LLifCfG1fGfi1tffffi::;;iii;;111i;,,;:,::, .:..,,,        \n" +
                                      "       .,,..::::;i;;;,.,:;:,  .,:;;i;;1fLi1; iC;10fiCCt;i1f1tf1;,,,;1ft:ii;:.::. ,::,:::,:,,.       \n" +
                                      "      .,,:,,. :::,:;iiiii;::iii;;:,,:11t;.  ;GGL00LLGLC:  .:;1Lft1tLL1iii.;;:;:.  .,::;::,,,,       \n" +
                                      "      ,,,.,,  :;: .;i111iii1ti;,,;1tt1i,   iLGL1G8t,:LtL;     .,,tt1.;1i, ,;;;;,   ,,,, :..,,,      \n" +
                                      "     .,,,.,..,,:,  ::,;1;;11tCtttfti,.    iLff.fG0GC;;LtL:       fL:.;;;   :;:::.  ,::, .,::,,      \n" +
                                      "     .,,::::;::.   ,:,,;i1i; :t1t.       iLtt:C8t,1C8GCGGG;     .ff.::;:   ,::.,:,. :;:. ,..,,.     \n" +
                                      "     ,,,.,::,::   .;;;;, ;i:; ;ff;      1CCCtGG:    :fC80GCi    ,t1;,;;,    ,;;,,:;,,:,::::,,,,     \n" +
                                      "     ,,,,,. ,::. .:;;:;.  :;;:iff;    .f0008Ci.       ;0CtCft.   i1t1;i:      :;;;;;:;i;::::,,,     \n" +
                                      "     ,,,,,. ,::,,:,.:;:    ;;tLit1   :ffC0L0,          f0iifLf,   1LLfti.    .;;,.:;;:::..: ,,,     \n" +
                                      "     ,,..:..::;;;:,:;:     .ittLL1  iLtL;f8C            C8;:fCL;  tiiL1;;    :;:.::,,::. .,,,,,     \n" +
                                      "     ,,,:::;;;;:;;;:;:.     :;;1t1i1GLL,.C0f           .i8Gt1GCGt.iLf;:;;:  .;:;;:. .::, .,,,,,     \n" +
                                      "     ,,,,:::,,:,,;:,.;;,    ::i,;tCLLCL:GGGCCLtt1;,:itC0CLC000Gifftff; ;;i;.:;;;:.   :::::,.,,,     \n" +
                                      "     .:,.,, .:;:  ,:,.,:,   ;;::tGC:f0G00L1i1ttfLG08G0C1;::1fGCLtCCL1t; ;i1i;,,:,   ,::;::::,,.     \n" +
                                      "     .,,::,. ,::,  .:::;:   ;;1CCGCCL11L0CGLfftfLCG08CffffL0CCi1CGCCCLLt11:;1;:,,  ,:,,.,,.,,,      \n" +
                                      "      ,,,.,, ,,:,   ,;;;;, :1fCCCCCCLt11ii;;;;;:::::;:;;;;iiffLCffLLCCf1iii1111i;. :;:  ,,.,,.      \n" +
                                      "       :,,,::;::,.  .:::;;.i;1fLLti1fLi;:.                .i11i:::;iiii;::;i;iii;:,:,, ,,,:,,       \n" +
                                      "       .,,:,::,,::, ,:,.;;i;:tti:.,,;1ft1L1iii;,       ,i11Lf1;;;::,.   ,:;:..:;;;i;:;::..,,.       \n" +
                                      "        ,,,.,,. ,::,:;,,;1111:ii1i;;:;ifffftii1fti, .i1Cf1tff1;:.    .,;;:;:::::::;;::::,,,,        \n" +
                                      "         ,,,::,. ,,;;;;i11ii;;;;:,,,,::;i;;;;:,,iff1ttti,;;ii:      ,:::;;:,.  :::. .,.,,,,         \n" +
                                      "          ,,,,.:.,;;;::,::::,,,::.       .,:;;;;,.;fL1. ;:;:.      :;,,::.    :::. .:,.,:,          \n" +
                                      "           ,:,.,:;;:,::,.  .,:;;;:;,         ,:;1i;tC;:iii,    ...,i:.::.  ..,:,:.,,,::,.           \n" +
                                      "            .,:,,,,,..::,,.   ,;:,:i:....,...   ,ii:t1;i1;.,:::;;:;;::;, .:::,::;:,.,,,.            \n" +
                                      "             .,,,.,:.. .,,:..  .::.,;;:;:::::::,,,;;i1:1;,:;i;;:,,,:i;:,,:;,...,,::,,,              \n" +
                                      "               .,,:,.,,.:;::::,.:;::i:,,,,::;i;::;:i111i,:::,:,,,,:;:;;;,:, .,,..,,,.               \n" +
                                      "                 .,,,.,:::,.,::::;;;::,.....,::::,.,ii1;:,.     .,::::;;::,,,,:,,,.                 \n" +
                                      "                   .,,:,..,,.  .:i;::,,...      ,:;:;:;,  .....,:::,...,:::..,,,.                   \n" +
                                      "                     .,,:,,:,,,.,;;,,::::,:,.,,,,:;;;;:,:;:,,:;:,,...,,..,:,,,.                     \n" +
                                      "                       ..,:,,,.,::,,.  ..,:;:::::,,:i;,:,....,::;,,,,:,,:,,.                        \n" +
                                      "                          ..,:,::,..,:,,,,:;:,.  ..,:;:,...,,,,,:,.,,,,,..                          \n" +
                                      "                              ..,,:,::,,,,::..,::,.,,::.,,::,,,,,,,,..                              \n" +
                                      "                                   ...,,,,:,,,,::,,,,:,,,,,,,,...                                   \n" +
                                      "                                           ...............                                          \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 26 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         ........,.........                                         \n" +
                                      "                                  ...,,,,:,,,,::,,,,:,,,,,:,,,....                                  \n" +
                                      "                              ..,,,::,,.,:,,.,::,,,,:,,.,::,,,,:,,,,..                              \n" +
                                      "                          ..,,:,,..,:,,,....,::;:..  .,,:;,,,,,:,.,,:,,,..                          \n" +
                                      "                       .,,,,,.,:,,,:;;:...,,:,:i;:,,:::,:;,,.  .,,,::,.,,:,..                       \n" +
                                      "                     .,:,,:,,,..  .:,;::::::,::;;;:,,,,,.,:,:::,,:;;,,,,::,,:,.                     \n" +
                                      "                  .,,:,,.,:;::..,:;::. ...  .;;;;:;:.      ..,,::;i;,. ..,,.,,:,,                   \n" +
                                      "                 .,,.,:,,.,::i;:::,.     .,::i11;,.,:::,....,,:::;;;,::,.,::::,,:,,                 \n" +
                                      "               .,:,..,.  .:::;;;;::,,,:;:::,,1111;;:::ii:::,,,:;;:;;.,:::::;,,,.,::,.               \n" +
                                      "             .,:,,:::,..,:;,.;:;;,,,::;;i;::1i:1:;;,,,:::::::::i: ,:,  ..,:,, ..,:,,:,.             \n" +
                                      "            .:,,.,,::;::,,. .;:.;;::;:,,..,i1iif1;1;,   ...... :;;,::,    ,,::. .,..,,,.            \n" +
                                      "           ,,,,:,, ,,:,.   .::.,i,       .;;i::LLLL1i;:.        ,;:;i;:,.  .,::,,::::,,:,           \n" +
                                      "          ,,,.,,. .:::   .,:::,;:      .:;:;. itCCCL::;;:,.       .:;:,,,,,,,,::;i:,:..,,,          \n" +
                                      "         ,,::,:,.,,::..,,:;;;::,     .:ii;i:iftLCCGGt,:;;iii;:,,,...:;;;:;i1i;;;i:, .,,::,,         \n" +
                                      "        ,,,.,:;;i;;:;::,,::::.    .,:;1fft1fL1fCtGttGfi1tffffi;;;;i1ii;i111i:,;;:::,  ,,.,,,        \n" +
                                      "       .,,,.,.:,:;:;i:,,:;;:.,,:;;;;;;ttL1i;.iGii0LiCC1;i1f1tfi:,..:;tti;1i:.,:, ,;:,,,:,,,,.       \n" +
                                      "      .,,,,,. ,::,,;i1111i;:iii;:,,,;11t;.  ;GGCG0LLGCC;  .;i1Lft11fLf1;i::;,::,  .:::;::,,,,       \n" +
                                      "      ,,,.,, .,::  ::;i1i;1ttti::itft1;,   iLCLiC8L,:CtL;     ,::tft;,i1i ,;;;;,   .:,:.,,.,,,      \n" +
                                      "     .,,:,:,,,,:.  ,:,,ii;i1iLftf1i:.     iLtf tG0GC;;LtL:       1Li :;i.  :;:::.  ,:,, .,,:,,      \n" +
                                      "     ,,,,::;:::.   ,:::::iii. 11f,       iLtf;C8f,1C8CLGGG;      tL:.;:i   ,:,,::,  :;:. ,,,,,.     \n" +
                                      "     ,,,.,,,,,:.  .;;;;, ,i:i,,LLi      1GCGfGG:    :LC000Ci    .t1i,;::    ,;:.,::,,:,,,:,.,,,     \n" +
                                      "     ,,::,. ,;:. ,::::;.  ,;:;itt1    .fG800C;        ;GCLCft.   i111;;:     .;;:;;;:;;;;::::,,     \n" +
                                      "     ,,,.,. ,:::::,.:i,    :i1L111   :ffLGLG;          f011ffL,   1LLtti      :;:,:;;;::,,:.,,,     \n" +
                                      "     ,,,.:,:;;;:;:::;,     .i1tLf1  iL1L:t8C            C8;:fCL;  fitLii:    :i:.,::,::, .,.,,,     \n" +
                                      "     ,,::::;:;;:;;:,;;,     :;;111i1GLL,,C0t           .i8G11GCGt.1ft;;:;.  .;::::. .:;, .,::,,     \n" +
                                      "     ,,,.,,,,::,.::,.:;,    ;:i.itGLLCLi0GGGCLft1;,;itC0CLC0GGGiLftfL.,i:i. :;;;;.  .:,,,,,.,,,     \n" +
                                      "     .,,,,, .:;:  ,::,,:,  .i:;,fGL:f0G0Gfi;itfLC088G0L1;::tL0CL1CCf11.,iii;::::,   .::;;::,,,.     \n" +
                                      "     .,,:,,. ,,:,  .:;;;:  ,i;1LCCCCL11CGCCfftffLC080LffffC0CCi1C0CCCLLi1i:ii,,:,  ,:,,,,:,,,,      \n" +
                                      "      ,,,.,,.:,:.   ,;;:;..i1fCGGCLCft11i;;;;;;::::;::;;;iiiffCCfLLLCCLt1i;i1i;:: .::,  ,,.,,.      \n" +
                                      "       ,,:,::;:::.  ,::,;:;;;fLLtiiifL1;:.                 i11i:::;ii11i:;i1i11i;,,::. .,:,,,       \n" +
                                      "       .:,,.,,.,:;, ::..;ii;i11;:.,,:ifttL1ii;:.       ,i1tCf1;;;;;:,...:;;,,,;;:;;:,:,,.,,:.       \n" +
                                      "        ,:,.,,  ,::::;:;1111;;ii1i;;;;iffft1i1tft;. :ifLt1tffi;:,.    .;:;:,,::::;;;;;:,,,:.        \n" +
                                      "         ,,::,,. ,:i;:;i1i;::;;:....,,:;i;;;;:,:tLt1ttt::i;i;,      ,::;;;:,...::,..,,,:,,,         \n" +
                                      "          ,,,.,:,;i:::,,.,,,,::;:.       .,:;;;:.:1Lt; ,;:;,       ;;,,::.   .:::. .,..,,,          \n" +
                                      "           ,,,:::::,,:;,.  .,:;;:;;,        .:;i1;iCf:;i;;.     . :i.,::   ..::,, ,,,,,,,           \n" +
                                      "            .,,,..,. .:::,    ,::,:;:..,,,,.   ,;1:1f;i1;,.,:::;::;;.::. .,:::;::,,.,,,.            \n" +
                                      "             .,:,,:,,. ,,:,..  ,:,.:;:::::::;:,,,i;;1:ii::;i;;:,,,,;;::.,;:,..,:::,,:,              \n" +
                                      "               .,:,,.,,:;:,:::,,;;:i;:,,,:::ii:::;;111i,,:::::,,,:;;;;;:::. .,,.,,:,.               \n" +
                                      "                 .,:,,::::...,:,;;;:::,.....,,::,..;11;::..     .,:::;i::,.,,:,,:,.                 \n" +
                                      "                   .,:,,.,,.. .,;i:::,,...     .:;:;;;:.  ....,::;:...::;:,.,,:,.                   \n" +
                                      "                     .,:,,:,,,,,;;,,,:::,:,.,,,,,:;;;:,,::::::;,,.  .,,.,:,,:,.                     \n" +
                                      "                       ..,,,,.,::,,,. ..,:;:,,::,,:ii:,,,...,:;;,,,,:,.,,,,.                        \n" +
                                      "                          ..,,::,,.,:,.,,:;:,.. ..,:;;,,...,,,,:,.,,,:,,..                          \n" +
                                      "                              ..,,,,:,,,,::,.,,:,,,,::.,,::,,,,::,,,..                              \n" +
                                      "                                   ...,,,,,,,,,:,,,,::,,,,:,,,...                                   \n" +
                                      "                                          ................                                          \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 27 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         .........,........                                         \n" +
                                      "                                  ...,,,,,:,,,,::,,,,:,,,,,,,,,...                                  \n" +
                                      "                              ..,,,,::,,.,:,,.,::,,,,:,,.,::,,,,:,,,..                              \n" +
                                      "                          ..,,,:,,.,,:,,,....,:;;,..  .,::;,,,,::,.,,:,,..                          \n" +
                                      "                       .,,:,,..,:,,,:;;,,..,,:,;i;,,::,,,;:,..  .,,,::,,,,,,.                       \n" +
                                      "                     .,,,.,:,,,.  .,,::,::::,,::;;;,,,,,,.:::::,,,:;:,,,,::,,,.                     \n" +
                                      "                  .,,::,.,,:;;:,,,;:,,      .:;;i:;:,.     ..,,:::;i:. ..,,..,,,..                  \n" +
                                      "                .,,,,.:,...:,;i;::,.     ..,:,;11;.,:::,,.....,::;;;:::,..,,,::,,,,                 \n" +
                                      "               .,,:,.,,. .:::::;;;::,,,;;:::,:1111;;::;i;::,,,,:i;:;,,:;:,:;;,,..,,,.               \n" +
                                      "             .,,,.,:::,,,:;,.,;:;;,,:::;i;:,;1:;1:i;,,,:;::::::;;,.:,  .,,:::..,,::,,,.             \n" +
                                      "            .,:,,,,,:::,,,. .::.,i::;:,,,..:i1;if;i1;.   .,,,,.,;;.:::    ,:,:. .,,.,,,.            \n" +
                                      "           ,:,.::. .:,:.   .::,.i:        :;ii,iCCLii;;,        .;;:;;;,.  .,;:,.,::::,,,           \n" +
                                      "          ,,,..,. .:;,.  .,;:::::       ,;;;: :tCCCf,:;;;,.       ,:i;:::,,..,:::;;::,.,,,          \n" +
                                      "         ,,,:::::,:::,,,::;;;;:,     .:ii;i;:tfLCCCGt,:;;iii;:,...  ,:::,::;i;:;i;:..:.,,,,         \n" +
                                      "        ,,,.,:::;;;:;;:,,,:::.   .,,:;ifft1tLffLtGttGL11tffLfi;;;;iiiii;i111i:;:;,:,  ,:,,:,        \n" +
                                      "       .,,:,,..,,;::ii;:;i;;:::;;;;;:;ttLtii,iG1i0LiCC1;iift1t;:,,,:;iiiii1i,.::.,;:,..,.,,,.       \n" +
                                      "      .,,,,:. .;:,.;;i111i;;iii:,,,:;111;.  ;CGCG8CLGCL;  .;i1LL1i;1fLfi:i;;,,:,  ,:,::::,:,,       \n" +
                                      "      ,,,.,,..,::. ,:,:1i:itff1;;1fLti;,   iLCL;L8C::CtC;    .,;i1ftf:ii1,,;:;;,   .::;:,,.,,,      \n" +
                                      "     .,,::::::,,.  ,::,:iiii:iLtf1;,      iLtf.10GGC;:ftL:       ;Lt..ii:  :;;;:.  .:,, .,.,,,      \n" +
                                      "     .,,.,:::,:.   :;;;:.:1i: :tf;       iLff;C8f,iC8CfCCG;      iL1 ;:i.  ,;:,::. .:;:  ,::,,.     \n" +
                                      "     ,,,,,, ,::.  ,:;:;,  ;;;; fL1      tGCGLGG:    :LC080Ci     1t1,i:;    :;:.,:,.,::,.,,.,,,     \n" +
                                      "     ,,,:,. ,;:..,:,,:;.   ;:;it1t    .tG800C;        :0GCCLt.   i111;;:     ,;:,:;;:;::;::,,,,     \n" +
                                      "     ,,,.:..,,::;:..:i,    ,i1Lt1t   :ffLCCG;          f0tiLfL,  .1fLt1i      ,;;:;;:;;;:::,,,,     \n" +
                                      "     ,,,,::;;;;:;;;;;,      i1tLf1  iCtL:18C           .G8;;fCL:  tiffii.    ,i:.,;:::,, .:.,,,     \n" +
                                      "     ,,,,:::::;:;;:,:;,     ;;;11111GCC,:C01           .i8Gi;GCGt,t1t;;:;   .;:,,:, .:;. .,:,,,     \n" +
                                      "     ,,,.,, ,::,.,:,,,;:    ;:i.1tGLfCL18GGCCLfti;,;itG0CCC8GGGiLftLt ;;;;  :;;;:.  ,:,, ,,.,,,     \n" +
                                      "     .,,::,  :::. .::::;.  ,i::.fGL;f0G0Gt;;itfLG080GGf1;:;tC0CL1LCLt: ;i1:,:;;;,   .:,:::,.,,.     \n" +
                                      "     .,,,.,..,,:.  .:;;;,  ;i;iLCCCGfitGGGCLftffCG08GLfffLGGCLitC0CCCL1;1iii:,::.  .:,::::::,,      \n" +
                                      "      ,,,.,::;::.   ,;;:;,;1itGGGCLCfft1i;i;;;;::::;::;;;ii;fLLCfLLLCCLtti:11:,:, .::,..,..,,,      \n" +
                                      "      .,,:,:::,,:,  ,:,,;ii:1Lfti;;iff1i;.                .i11i:,,:;111i;;1111i;;.,::. .:,,,,       \n" +
                                      "       .,,..,. ,;;,.;:.,i1iiiii;:,,::it1fL1ii;:       .;11fLt1;;;;;;:,,:;i;:::ii:;;,:..,,:,,.       \n" +
                                      "        ,,,,:,  ,,:;:;;11i1i;ii;i;;;;;ifLtt1i1ffi:  ;1Lf11tffi;:,..   .:::,,,:;::;;;;::,.,,.        \n" +
                                      "         ,,,,.:..:;;;:;i;:,,:::,   ...,;;;;;;:,iff11tf1,;;;i;,     .,:;;;;:,,.,::,,,::::,,,         \n" +
                                      "          ,,,.,::;;:::,..,,:::;;:,       .,:;;;..ifL1. :;;:.      .;:,:::,   .:;:. .,.,,,,          \n" +
                                      "           ,,::,,:,.,::,.  .,;;;:;;.        .:;1i:fC;:ii;,       .;;.,:,    .:,:. ,,:,,:.           \n" +
                                      "            .,,,.,,. .:,:,    ,:,.;;,.,,,,..  .;1;;fi;1i,..,,:;;:;;,.::  .,:::::,,,,::,.            \n" +
                                      "             .,,,::,,.,:::,,.  :;.,;;::::::;;:,,ii:1;;1:::;i;::,,,;;:;,.:;:,,,:::,.,,,              \n" +
                                      "               .,,,.,::;;,,:;:,:;:;;:,,,,::;i;:::;1111,,:::;:,,,::;;;::,:,  .,,.,::,.               \n" +
                                      "                 .,,,::,,,. .,::;;;::,.. ...,,::,.;11;::,.     ..:::;i;,,.,,:,.,,,.                 \n" +
                                      "                   .,,,..,,...,:i;::::,,.      ,;;:i;;,  ... .,:;;,..:;;:,,.,::,.                   \n" +
                                      "                     .,,,:,,,,,;;:,.,,::::,,,::,:;;;::,:;::::;,:,. ..,,,:,,,,,.                     \n" +
                                      "                       ..,,,,,::,,,.  ..,;;,,,:,,,;i:,,,,..,:;;:,,,:,.,,,:,..                       \n" +
                                      "                          ..,,:,,,,::,,,,;:,,.  .,,;;:,....,,,:,.,,,:,,,.                           \n" +
                                      "                              ..,,,:,,,,::,.,,:,,.,::,.,,:,.,,::,,,...                              \n" +
                                      "                                   ...,,,,,:,,:,,,,::,,,,,,,,,...                                   \n" +
                                      "                                           ..............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";

            width100pentagram[ 28 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         ..........,.......                                         \n" +
                                      "                                  ...,,,,,,:,,,,:,,,,,:,,:,,,,,...                                  \n" +
                                      "                              .,,,,,,:,,.,,:,,,,::,,,,:,,.,:,,,,,:,,,.                              \n" +
                                      "                          ..,:,,:,,,,::,,,.  .,:;;,..  .,,:;,,,,::,,,,:,..                          \n" +
                                      "                       .,,,:,,.,,,..,::;,,.,:,,,;i:,,::,,:;:,.. .,,.,::,,,,,.                       \n" +
                                      "                     .,,,..,:,,,. ..,,::,::::,:;:;;:,,:,,,,:,::,..,:;:,,.,::,,.                     \n" +
                                      "                  .,,,::,,,,:;;:,,:;:,.      ,;;i;:;:.     ...,::::;;:...,:,.,,,,                   \n" +
                                      "                 ,,,,.,,.. ,,,i;:;:,.    ..,::,;11:.,::,,......,;:;;;,:.  .,,,::,,,                 \n" +
                                      "               .,,,:,,,...:;::;:;;:::,::;;;:,,;1111;:::;i;::,,,:;i;;:,:;:,,:;::,.,,,.               \n" +
                                      "             .,,,.,::;;,,:::..;::i:,,:::;i;::i1:ii:1;,::;:::::::;:.::. .,,,:::.,,,::,,.             \n" +
                                      "            ,,,:,,,.,,::,..  ,:..;;:::,,,. .:i1;tf;i1:.  ..,,,,.;;,,::.   .:,:. .,,..,,.            \n" +
                                      "           ,,,.,,. .:::.    ,:,.;;.       ,;;i;,fGL;ii;:.       .;;::;;,.  .,:;,..,,,,:,,           \n" +
                                      "          ,::,.,,..:;:.  .,;;;:::.      .:;;;..iCGCf.:;;;:.       ,:;i;::,,..,::,:;;:,,,,,          \n" +
                                      "         ,,,,:::;::::,,::::;;i,.     .,;ii;;:ifCGCCGf::;;;ii;,..    .:::,,,:;;:;;;::.:..,,,         \n" +
                                      "        ,,,..,,:,;i:;;:,,,;::.  .,,:;;ifLtt1ffLLtCf1GL111ffLfi;;;:;;;;iiiiii1i;:;:,, .,,:,,,        \n" +
                                      "       .,,:,,. ,,:::;11ii1;;;;iii;;;:;ttLtii:1Gt;0L;LC1;iiLt1t;,:,::iii;i111;.:;,,;:, .:..,,.       \n" +
                                      "      .,,..,. .:;, ::,;11iii11;:,,,:it11;.  ;CGCG8GCGCL;  .;1tff1;:;1fft;;ii,.::  ,:,,,:,,:,,       \n" +
                                      "      ,,,,,:,,,::. ,:,.;1;;ttLf1i1fLt;:.   iLLL;t8G::CfC;   ..:i1tftL1;iii:;:::,   .::;;::,,,,      \n" +
                                      "     .,,,,::;::,   .::::;iii;,ttfi:.      iLtf.iGGGG;:f1L;       ,ft; ;ii, :;;;;.   :,,,,,.,,,      \n" +
                                      "     .,,.,,.:,:.   :;;;:..ii;..tfi       iCfLiC8L,;C8LfCLC;      ;Lf :;;:  .;::::. .:::  ,,:,,.     \n" +
                                      "     ,,,:,. .::,  ,:::;:  ,i:i.iLt.     t0G0CGC:    :CG080Gi     itt.i:i.   :;,,::, ,;:. ,,.,,,     \n" +
                                      "     ,,,,,. ,::,.::,,;;.   :;;itif,   .tC000C:        :0GGCLt.   i1it;;;     :i,.,;:::,,,,,.,,,     \n" +
                                      "     ,,,.:,,::;:;;,,;;.    .i1ff11   :ffLtCGi          fGf1Ltf,  .tfLt1;      ,i;:;;:;;;;::::,,     \n" +
                                      "     ,,:::;;;;;:;;;;;,      i1tLf1  iCtL:i8C            G8:;fLC: .t1ffii.    ,;:.,;;::,:,,,.,,,     \n" +
                                      "     ,,,.,,,,,:::;,.,i:     ;;;1i1itGCC,:C01           .18C;:GCGt:ti1;:;:   ,;;,,::.,::. .,,,,,     \n" +
                                      "     ,,,.,. .:;, ,::,,::   ,;:;.tfGLfGLt8GGCGCfti:,;1fC0CCG8CGG1LLfLi.i:i.  :;:::,  ,::. ,,:,,,     \n" +
                                      "     .:,:,,  :::. .::;:;   ;;;.,CCL;fGG0Ct;;itfCG080GCfi;:ifG0GfiLCCt .;ii..;;;;,   .:,:,:..,,.     \n" +
                                      "     .,,,.:.,,,:   .;;;;: ,ii:;CCCCCtitGCGLLftffCG80CffffLGCGf1tCGCCCf,;iii;:,::.  .,::;:::,,,      \n" +
                                      "      ,,,,::;;::,   ::::;:iiifGGCCCCLLfti;i;;;::::;:::;;;i;;fLCCLLfLCGLtt;;1;.,:. .::,,,:,,,,.      \n" +
                                      "      .,,,,,,,,::, .::.:ii:;tfti:::itt1i;.                .i111;:,,:i11i;ii11i:;:.,;:. ,:.,,,       \n" +
                                      "       .,,.,,. ,::,,;:,i111i;iii;::::;t1fL1ii;,       ,;1ifLtt;;;;;;;;;;;iiiiii;:::,, .,,:,,.       \n" +
                                      "        ,,,:,,. ,,;;:;i1;;;;ii::;::;;;ifftt11tft;, ,;tLt1ttfti;:,,.   ,:::,.,:;;;i;::,:.,,,,        \n" +
                                      "         ,,,..:,:;;;::;::,,,:::.    ..,:;;;;:,:tft1tft;:;;;;;,      ,:i;;::,,,,::::;:::,,,,         \n" +
                                      "          ,:,,::;:,,::, .,,:;;i;:,       .:;;;: :1Lt; ,;;;:.      ,;:::;;,.  .:;,. ,,.,,:,          \n" +
                                      "           ,,,,.,,..,;:,.   ,;::,;;.        :;ii:iCt:;ii;.       .i:.::,    ,::,  ,:,.,,.           \n" +
                                      "            .,,..:,. .:,:.   .::.,;;,,:,,,.  .:11;f1;1i:...,,:;::;;.,:,  .,,:::,.,,,:,,.            \n" +
                                      "             .,,::,,,.:::,:,. .::.;;::::::;;::,;1:ii:1;::;i;:::,,:;:;;..:::,,:;:,..,,,              \n" +
                                      "               .,,,.,::;:.,:;:,;:;;;:,,,::;i;:::;1111:,:::;;,,,:::;;:;::;,  .,,,:,,,.               \n" +
                                      "                 .,,::,,,.  .:,;;;::,..  ...,,:,.:11:,::,.     .,:::;i,:...,:,.,,,.                 \n" +
                                      "                   .,,,.,:,,..:;;:::::,,.     .:;:;;;:.      ,,:;:,,,;;:,,.,::,,.                   \n" +
                                      "                     .,,:,,.,,:;:,..,,:,:,,::::,:;;:;,,::::,::,,.  .,,,:,.,,,,.                     \n" +
                                      "                       ..,,,,::,.,,....::;,,,,,,,:i;,,,,..,,;;:,,,,,,.,,:,,.                        \n" +
                                      "                          ..,,,,,,::,,,,;:,,.  .,,;;:,....,,,;,,,,,:,,,,..                          \n" +
                                      "                              ..,,:,,,,::,.,,:,,,,::,,,,:,,,,,:,,,,,..                              \n" +
                                      "                                   ..,,,,,:,,:,,,,::,,,,:,,,,,...                                   \n" +
                                      "                                           ...............                                          \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 29 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         ..................                                         \n" +
                                      "                                  ...,,,,:,::,,,,:,,,,,:,,,,,,,...                                  \n" +
                                      "                              ..,,,,,,:,,.,::,,,,::,,,::,..,:,,,,,,,,.                              \n" +
                                      "                          ..,,:,,:,,,,::,,..  .,:;:,..  .,,::,,.,:,,,,,,..                          \n" +
                                      "                       .,,,,:,..,,...,:;;,,,,:,,:i;,,,:,,,:;:,....,,.,::,:,..                       \n" +
                                      "                     .,:,,.,:::,....,:,:,,:::,,:;;;;:,::,,,::::,...,:;:,..,::,.                     \n" +
                                      "                   .,:,,:,,,,:;;,,:;:,,.     .:;:i::;,      .,,:::::;::.,,,:,,:,.                   \n" +
                                      "                .,,:,..,,  .,,:i;:;,..  ...,,:,.;ti,.::,,... ..,::;;;,,,  .,,.,::,,                 \n" +
                                      "               .,,,,:::,..,;:,::;;:::,,::;;;:,,i1111;,::;i;::,,::;;:;:,;;,.,::::.,:,.               \n" +
                                      "             .,:,,.,,:;::::,. :;,;;:,::::;;:::ii,1;;1;::;;:::::,;i,:;, .,::::;,,,.,::,.             \n" +
                                      "            .,,,:,,..:,:,.   ,:,.:;,,:,,,.  ,;1i;f1;1i,  ..,,,,,:;,.::.    ::,, .,:,,,,.            \n" +
                                      "           ,,,..,.  :::,   .,::,:i,       .:;ii,iGC;;ii;,        :i,,::,    ,:;,  ,,.,,:,           \n" +
                                      "          ,,::,::,,,::. ..,;i;;::.      .:;;;: :LCGf..:;;:,       ,::;;;:,.. ,::,,:::::,,,          \n" +
                                      "         ,,,.,:;;;;;::,:::,::;:.     .,;ii;;::tCGCGGLi::;;ii;,.     .:::,,,,::::;;;;:,,.,,,         \n" +
                                      "        ,,,,,,.,,:i:;i;,,:;;;:..,,::;;ifftt1tfCC1CL1CCt11ttLfi;;::::,,;;;;::;ii:;;,,..,,:,,,        \n" +
                                      "       .,,,,,  ,::,:;:1111i;;iiii;:::;t1Lfii:1Gt;GC;LG1;iiLf1t;:::;;iii;;1t11::;:,::, .:,,,,.       \n" +
                                      "      .,,,.,. ,:;, ,;,.i1i;i1ti:,,:;1t11;.  ;LGCC00CCGL:  .i1tfti:,,;1fti:i1: ::. :;:,.,,.,,,       \n" +
                                      "      ,,::::::,:,  .::.:ii;11tCt11fft:,.   iCLL;100i;CfC;   .,:tttttfL11iii;:,:,   ,::;;:::,,,      \n" +
                                      "     .,,,,::;::,   .::::::1ii.;1t1.       ;Ltf.iGGGG;,ftL;       .tt1.,ii;.::;;;.   ,:,:,:.,,,      \n" +
                                      "     .,,.,, ,,:,   :;;;;  :i;: iLt.      iLfL1C8L:;C8L1CfC;      ,LL,.;;i. .;;;;,   :,,. ,,,,,.     \n" +
                                      "     ,,,:,. .;:, .::,:::   ;:i,:Lf,     10G0GGC:    :CGG000i     :ff,;:;:   :::,::. ,;:. .,,,,,     \n" +
                                      "     ,,,.:. ,,:,,;,.,;:.   ,;:;t;f:   .tLG00G:        :GG0CLt.   ;tit;;;.   .;;,.,;:::,,.,, ,,,     \n" +
                                      "     ,,,,::::;;:;;::;:      i1fft1   :ftL1CG1          tGL1Ltf,  .tfLt1;      :;::;;:;;:;::::,,     \n" +
                                      "     ,,,,::;:;;:;;::;:      i1tLtt  ;Cff:i8C           .G8;;fLC: .t1Ltii     .;;,:;;:;:::::,,,,     \n" +
                                      "     ,,, :..,,:,,;,.,;:.   .;;;1it;tGCC,;C0i            18C::CCG1;ti1;;;.   .;;,,::,,::, .:.,,,     \n" +
                                      "     ,,,,:. .;:, .::,:::   ::;:,ffCLtGCL8GGCGCf1i::i1fCGCCG8LGG1LCfL,,;:;   :::,:,  ,:;. .,:,,,     \n" +
                                      "     .,,,,, .,::   ,;;;;. .i;; ;GCLiLG00L1:i1tLGG00GGLti;:1f00GfiLCCi :;i: .;;;;,   ,:,,.:.,,,.     \n" +
                                      "     .,,..:,:::,   .;;;::.iii,;CCCGCt1tGCGLLtffLG08GLfftLLGCGt1tCGCCCi.ii1::::::.   ,::;::,,,,      \n" +
                                      "      ,,::::::,:,  .::,:;i;i1fGCCCCCLLLt;;i;;;::::;:::;;;i;;fLLCCCLLCGfti:ii:.::. .::,:::::,,.      \n" +
                                      "       ,,,.,,.,:;, .:, ;1i:itti;,,,;1t1i;.                .ii11i:,,,;it1i;i1i.,;, :;:. ,,.,,,       \n" +
                                      "       .:,,,,  ,::,:;:;11t1;;iii;;:::;ttLL11i:.       :i11LLtt;:;;;iiii;;i1111;;::::, .,,,,,.       \n" +
                                      "        ,,,,,,..,,i;:ii::::;;:..,:::;;ifftt11fL1:  :iff11ttffi;::,,...:;;:,,:;i:;i:,,.:.,,:.        \n" +
                                      "         ,,,.,,:;;;;:::::,,,:;:.     .,:;;;;:,iff11ff1,:;;;;:,.     .:;::,:::,::::;;;:,.,:,         \n" +
                                      "          ,,,::::,,,::. ..,:;;;::,       ,:;;;..iffi,.:;;:,       ,;:;;i:,.  .::,..,,,::,,          \n" +
                                      "           ,,,,.,,  ,:::    ,::,,i:        .;i1;:fL;:ii;,        :i,,::,    :::,  ,,.,,,.           \n" +
                                      "            .,,,,:,. ,,:,.   .::.,;:,::,,,.  ,i1;1f;i1:. .,,,::::;:.::.  ..,:,: .,,:,,,.            \n" +
                                      "             .,:,,.,,:;:::::, ,;::i:,:::::;;::;1;;1:1i:::i;:::,,:;;:;, ,:::,:;:,,.,,,,              \n" +
                                      "               .,:,,::::..,:;,:;;i;::,,:::i;::,:1111;,,:;;;:,,,::;;;::,::,..,:::,,:,.               \n" +
                                      "                 .,:,,.,,.  ,,,i;:;:..   ...,::,,1t:.::,..    ..,;:;i:,,  .,,..,:,.                 \n" +
                                      "                   .,:,,:,,,.::;:,:::,,.      ,;:;i:;,.     .,,:;:,,;;:,,,,:,,,,.                   \n" +
                                      "                     .,:,,..,:::,...,,,::,::::,:;;;;:,::::,::,,,. .,,:::,.,,,,.                     \n" +
                                      "                       ..,,,::,.,,,...,:;:,,,,,,,;i:,,,,.,,;;:...,,,..,:,,,.                        \n" +
                                      "                          ..,,,,,::,.,,::,,.. .,,:;:,....,,,::,,,,:,,:,,.                           \n" +
                                      "                              ..,,,,,,::,..,:,,.,::,.,,::,.,,:,,:,,,..                              \n" +
                                      "                                   ...,,,,,,:,,,,,:,,,,:,,:,,,...                                   \n" +
                                      "                                          ................                                          \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
            width100pentagram[ 30 ] = "```                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "                                         ..................                                         \n" +
                                      "                                  ...,,,,,,,::,,,,:,,,,,:,,,,,,...                                  \n" +
                                      "                              .,,,,,,,,:,..,::,,,,:,,,,::,.,,:,,,,,,..                              \n" +
                                      "                          ..,,,,.,:,,,,:::,.. ..,:;:,.. ..,,::,,.,:,,:,,..                          \n" +
                                      "                       ..,:,,:,.,,....,:;:,,,::,,;i:,,:,,,,:;:,...,,,.,::,,..                       \n" +
                                      "                     .,::,..,:;:,...,::::,,:::,,;;;:;,,:::,,:,:,. ..,:::,.,,,,.                     \n" +
                                      "                  .,,,,.:,,,.,:i:::::,,.      ,:::i:;:.      ,,:;::,;;:,,,,::,:,.                   \n" +
                                      "                .,,::,.,,. .,:,;;:::,......,,::,.iti.,:,,...  ..,;:;i;,,. .,,..,,,.                 \n" +
                                      "               .,:,.::::,,,:;,,;:;;:,,,:::i;::,:i111i,,::;;:,,,:::;;:;,:;,..,:,:,,,,.               \n" +
                                      "             .,::,.,,,:::::,. ,;:,;;::::::;;:,:1;:t:i1:::;;::::,,;;,;: .:::::;;:,.,,,,.             \n" +
                                      "            .,,.,:,. ,,::    .::.:;:,,,,,..  ,;1;ifii1;,  .,,,::,;:.,:,  ..,:,:..,,:,,,.            \n" +
                                      "           ,:,,.,, .,;:,   .,;:::i:        ,;;i;;CGi,iii:.       ,i:,::,    ,::,  ,,..,,,           \n" +
                                      "          ,,,,::::,,::...,,:;;i::,       ,:;;:..LCGL: :;;;:.      ,::;;i:,.. .::,..,:,::,,          \n" +
                                      "         ,,,.,,,;;;;::::,,,,:::.    ..,;ii;;::iLGCCGLt::;;ii;,.     .:;::,:::,::::;;;:,.,:,         \n" +
                                      "        ,,::,,. ,,;;;;1i:;;ii;:,:::;;;ifLft11tCC1LLtCCft1ttLfi;;:,,,..,;;:,,,;i::i:,,.:.,,,,        \n" +
                                      "       .,,.,:  ,;:,:;,:1111;;iii;::::;t1ffii:tGf;CG;fGi:iifLtt;:::;iiii;;i1111;;::::, .,,,,,.       \n" +
                                      "      .,,,.,,.,::, .:: ;1i:itf1;:::itf11;.  ;LGCC00CCGL:  .i11ti;,,.:it1i;i11,,;, :;:. ,,.,,,       \n" +
                                      "      ,,,,::;;::,   ,:,:;iiiiiLftttt1:,.   iCfL:i001;CLC;   .:;tff11tLft1:ii:.::. .::,:::::,,,      \n" +
                                      "     .,,..:,:,:,   .;;;:: ;ii,.1tt.       ;L1f,iGGGG;,ftL;      .,1ft;.ii1::::::.   ,::;::,,,,      \n" +
                                      "     .,,,,, .,::  .::;:;  .i;;.:Lf,      iCfC1C8L::C8L1LfL;      .tfi :;i: .;;;;,   ,:,,.,..,,.     \n" +
                                      "     ,,,,,. .;:. ,::,:::   :;;;,ff:     1008GGC:    ;GGG0G0i     ,fL:,i:;   :::::,  ,:;. .,:,,,     \n" +
                                      "     ,,,.:,,,,:,:;,.,i:    .;;;tit;   .tLC0GG,        ;G00GL1.   :fit;:;,   .;;,,::,,::, .,.,,,     \n" +
                                      "     ,,::::;:;;:;;;;;,      i1fLf1   :ftCiCG1          tGC1Ltf,  .ttLf1i     .:;,:;;:;:::,:,,,,     \n" +
                                      "     ,,,.:,:::;:;;,,;;.    .iiff1t  ;CLf:;8C           .G8;;ffC; .ttLt1;      :;::;;:;;:;:::,,,     \n" +
                                      "     ,,,.:. ,::,.::,,;;.   ,;;;tit:tGCC,;G8i            t0C::CCG1i1i1;;;.   .;;,.,;:,:,,.,, ,,,     \n" +
                                      "     ,,,:,. .::,  ,:::::  .i:i.;LfCLtGCC8GCCGCf1;,:i1fCGGGG8fCGtLCft.;:;,   ::,,::. ,;:. .,,,,,     \n" +
                                      "     .,,..,.,,:,   :;;;;. ;i;, 1CCLiLG0Gfi:;ifCGG80GCfti;;1L0GGf;LCC,.;;i. .;:;;,  .:,,..,,,,,.     \n" +
                                      "     .,,,,::;::,   .:::::;1i;.tCCGGCt1fGCGLLtffCG80CfftfLLGGGtitCGCCL::ii;.::;;;.   ::::,:.,,,      \n" +
                                      "      ,,,,,:,:,::. .::.;ii:1tLGCLLCCCLLf;;i;;;:::;:::;;;;i;itLLLCCCCGGf1;ii;:,::   ,::;;:::,,.      \n" +
                                      "      .,,..,. .:;, :;,:11iii11i;,,,:i11ii.                .ii1t1;,,,;1fti:i1: ::. :;:,.,,.,,,       \n" +
                                      "       .,,::,. ,::::;i1ii1i;;iiii;;;:;ttLfi1i,       .;i11Lftt;:::;;iii;;1111::;:,::. .:,.,,.       \n" +
                                      "        ,,,,.:.:,;i::;;,,,:;:,  .,,::;ifftt1tLfi, .;tLt11ttLfi;;::::,,;;;;::i1;:;;,, .,,:,,.        \n" +
                                      "         ,:,.,:;;;::::,:::,::;:,     .,;;;;;::tftitff;,:;;;;:,.     .;::,,,:::::;;;;,,,.,,,         \n" +
                                      "          ,,::,,,..,;:.  .,;;;::;,      .,;;;: :1Lt; ,;;;:.       ,;:;;;:,.. ,::,,:::::,,,          \n" +
                                      "           ,,,..,.  ,::,    ,::,:i,        :;ii:1Ct:ii;:.       .;i,:::,   .::;, .,,.,,,.           \n" +
                                      "            .,,,:,,..:,;,..  ,:, :;::::,,,  ,;1;if;;1;.  .,,,::,;;,.::.   .:,,. .,:,,,,.            \n" +
                                      "             .,,,..,:;;:,:::..:;:;;,,::::;;:::1i:1:i1:::;;:::::,;;,:;. ,,::::;,,,.,::,              \n" +
                                      "               .,,,::,:,..,;:,;:;;:::,,,:;;::,,i111i:,::;;:::,,::;;:;,:;:,.,::::.,,,.               \n" +
                                      "                 .,,,..,,. .,,;i:::,.     .,,:,,it;.,::,...  ..,::;;;,:.  .,,.,::,.                 \n" +
                                      "                   .,,,::,,,,;;:,,:;:,,.     .:;:i:;:.      ,,::::,:;:,.,,::.,,,.                   \n" +
                                      "                     .,,,,.,::,,.. .,,::,:::::,;:;;;,::::,,:,:,,...,:;:,.,,:,,.                     \n" +
                                      "                       ..,,::,..,,,..,:;:,..,,,,:i:,,,,,,,;;:,...,,,.,:,,:,.                        \n" +
                                      "                          ..,,:,::,,,,::,,.....,:;:,.. ..,,;:,,,,:,,:,,,.                           \n" +
                                      "                              ..,,,:,,:,,.,::,,,,:,,,,::,..,:,,,,,,,..                              \n" +
                                      "                                   ...,,,,,:,,,,,:,,,,:,,,,,,,...                                   \n" +
                                      "                                           ..............                                           \n" +
                                      "                                                                                                    \n" +
                                      "                                                                                                    \n" +
                                      "```";
        }*/ // 2nd mov pentagram ~30 frames
        SendMessage message = new SendMessage();
        message.setParseMode("MarkdownV2");
        Integer i = 0;
        Integer cinemaMessageId = sendToPlayingRoomAndGetMessageId(message, "kek");
        // Integer cinemaMessageId = 71734; 44 cinema msgid
        for (i = 0; i <= 107; i++) {
            Integer s = Integer.parseInt("4") + Integer.parseInt(String.valueOf(i % 3));
            editMessage(n[ i ] + "\n0\\." + s + " fps", cinemaMessageId);
            if (i == 107) {
                i = 0;
            }
        }


    }

}
