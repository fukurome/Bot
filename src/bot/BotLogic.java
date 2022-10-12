package bot;

import java.io.*;

import bot.SimpleBot.*;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;


public class BotLogic {
   SimpleBot answeringBot = new SimpleBot();
    public void writeMessageInFile(String message, String user_ID) {
        String directory = System.getProperty("user.dir");
        String filePath = directory +"/" + user_ID + ".txt";
            try {
                File f = new File(filePath);
            }
            catch (Exception e) {
                System.err.println(e);
            }
            File file = new File(filePath);
            try {
                FileWriter writer = new FileWriter(filePath, true);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                bufferWriter.write(message + "\n");
                bufferWriter.close();
            } catch (IOException e) {
                System.out.println(e);
            }
    }
    public boolean isFileEmpty(File file) {
        return file.length() == 0;
    }

    public String fileReader (File file) {
        if (file.exists() && !isFileEmpty(file)) {
            try {
                FileReader fr = new FileReader(file);
                BufferedReader reader = new BufferedReader(fr);
                String answer = reader.readLine();
                return answer;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public String getReply(String message, String user_ID) {
        String directory = System.getProperty("user.dir");
        String filePath = directory +"/" + user_ID + ".txt";
        if (message.equals("/start")) {
            String respond = "Привет, я Супа-дупа, а кто ты?";
            try {
                File f = new File(filePath);
                f.createNewFile();
            }
            catch (Exception e) {
                System.err.println(e);
            }
            return respond;
        }
        File file = new File(filePath);
        if(file.exists() && isFileEmpty(file)) {
            writeMessageInFile(message, user_ID);
            return "Приятно познакомиться, " + message;
        }
        String[] answer = answeringBot.sayInReturn(message, user_ID);
        if (answer[1] == "anecdote")
            writeMessageInFile(answer[1], user_ID);
        return answer[0];
    }

}
