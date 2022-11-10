package org.example.bot.botLogic;


import java.io.*;

import org.example.bot.botLogic.SimpleBot.SimpleBot;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import org.example.bot.botLogic.SimpleBot.ResponseToUserAndEventType.ResponseToUserAndEventType;


public class BotLogic {
    SimpleBot answeringBot = new SimpleBot();
    public ResponseToUserAndEventType getReply(String message, String user_ID, Boolean fileIsEmpty) throws FileNotFoundException, IOException {
        ResponseToUserAndEventType r = new ResponseToUserAndEventType();
        if (message.equals("/start")) {
            r.response = "Привет, я Супа-дупа, а кто ты?";
            r.event = "new user";
            return r;
        }
        if(fileIsEmpty) {
            String[] respond = {"Приятно познакомиться, " + message, message};
            r.response = "Приятно познакомиться, " + message;
            r.event = message;
            return r;
        }
        return answeringBot.sayInReturn(message, user_ID);
    }

}