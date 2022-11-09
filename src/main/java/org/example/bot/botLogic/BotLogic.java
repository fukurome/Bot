package org.example.bot.botLogic;

import java.io.*;

import org.example.bot.botLogic.SimpleBot.SimpleBot;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;


public class BotLogic {
    SimpleBot answeringBot = new SimpleBot();
    public String[] getReply(String message, String user_ID, Boolean fileIsEmpty) throws FileNotFoundException, IOException {
        if (message.equals("/start")) {
            String[] respond = {"Привет, я Супа-дупа, а кто ты?", "new user"};
            return respond;
        }
        if(fileIsEmpty) {
            String[] respond = {"Приятно познакомиться, " + message, message};
            return respond;
        }
        return answeringBot.sayInReturn(message, user_ID);
    }

}