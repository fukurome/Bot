package org.supaDupa.bot.botLogic;


import org.supaDupa.bot.botLogic.SimpleBot.ResponseToUserAndEventType.ResponseToUserAndEventType;
import org.supaDupa.bot.botLogic.SimpleBot.SimpleBot;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class BotLogic {
    SimpleBot simpleBot;
    ResponseToUserAndEventType r;
    public BotLogic(SimpleBot simpleBot, ResponseToUserAndEventType response) {
        this.simpleBot = simpleBot;
        this.r = response;
    }
    public ResponseToUserAndEventType getReply(String message, String user_ID, Boolean fileIsEmpty) throws GeneralSecurityException, IOException {
        if (message.equals("/start")) {
            r.response = "Привет, я Супа-дупа, а кто ты?";
            r.event = "new user";
            return r;
        }
        if(fileIsEmpty) {
            r.response = "Приятно познакомиться, " + message;
            r.event = message;
            return r;
        }
        return simpleBot.sayInReturn(message, user_ID);
    }

}