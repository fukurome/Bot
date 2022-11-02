package bot;

import java.io.*;

import bot.SimpleBot.*;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;


public class BotLogic {
    SimpleBot answeringBot = new SimpleBot();
    public void writeMessageInFile(String message, String user_ID, String directory) throws IOException {
        String filePath = directory +"/" + user_ID + ".txt";
        File file = new File(filePath);
                FileWriter writer = new FileWriter(filePath, true);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                bufferWriter.write(message + "\n");
                bufferWriter.close();

    }
    public boolean isFileEmpty(File file) {
        return file.length() == 0;
    }

    public String readingUserFile (String user_ID, String directory) throws IOException {
        String filePath = directory +"/" + user_ID + ".txt";
        File file = new File(filePath);
        FileReader fr = new FileReader(user_ID);
        BufferedReader reader = new BufferedReader(fr);
        String answer = reader.readLine();
                return answer;
    }


    public String getReply(String message, String user_ID, String directory) throws FileNotFoundException, IOException {
        String filePath = directory +"/" + user_ID + ".txt";
        if (message.equals("/start")) {
            String respond = "Привет, я Супа-дупа, а кто ты?";
            File f = new File(filePath);
            f.createNewFile();
            return respond;
        }
        File file = new File(filePath);
        if(file.exists() && isFileEmpty(file)) {
            writeMessageInFile(message, user_ID, directory);
            return "Приятно познакомиться, " + message;
        }
        String[] answer = answeringBot.sayInReturn(message, user_ID, directory);
        if (answer[1] == "anecdote")
            writeMessageInFile(answer[1], user_ID, directory);
        return answer[0];
    }

}
