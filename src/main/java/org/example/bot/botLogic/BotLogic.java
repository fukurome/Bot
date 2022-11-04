package org.example.bot.botLogic;

import java.io.*;

import org.example.bot.botLogic.SimpleBot.SimpleBot;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;


public class BotLogic {
    SimpleBot answeringBot = new SimpleBot();
    public String readingUserFile (String user_ID, String directory) throws IOException {
        String filePath = directory +"/" + user_ID + ".txt";
        File file = new File(filePath);
        FileReader fr = new FileReader(user_ID);
        BufferedReader reader = new BufferedReader(fr);
        String answer = reader.readLine();
        reader.close();
        return answer;
    }
    public String[] getReply(String message, String user_ID, String directory, Boolean fileIsEmpty) throws FileNotFoundException, IOException {
        if (message.equals("/start")) {
            String[] respond = {"Привет, я Супа-дупа, а кто ты?", "new user"};
            return respond;
        }
        if(fileIsEmpty) {
            String[] respond = {"Приятно познакомиться, " + message, message};
            return respond;
        }
        return answeringBot.sayInReturn(message, user_ID, directory);
    }

}