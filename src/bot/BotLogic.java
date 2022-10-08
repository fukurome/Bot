package bot;

import java.io.*;
import java.util.Scanner;
import bot.SimpleBot.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

    public String getMessage() {
        Scanner in = new Scanner(System.in);
        String message = in.nextLine();
        return message;
    }


    public String getReply(String message, String user_ID) {
        String[] answer = answeringBot.sayInReturn(message, user_ID);
        if (answer[1] == "anecdote")
            writeMessageInFile(answer[1], user_ID);
        return answer[0];
    }

    public boolean isFileEmpty(File file) {
        return file.length() == 0;
    }

    public void sayHello(String user_ID) {
        String directory = System.getProperty("user.dir");
        File file = new File(directory +"/" + user_ID + ".txt");
        if (file.exists() && !isFileEmpty(file)) {
            try {
                    FileReader fr = new FileReader(file);
                    //создаем BufferedReader с существующего FileReader для построчного считывания
                    BufferedReader reader = new BufferedReader(fr);
                    String name = reader.readLine();
                    System.out.println("Рад тебя снова видеть, " + name + "!");

            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            Scanner in = new Scanner(System.in);
            String start = "";
            while (!start.equals("/start")) {
                System.out.println("Если хотите начать общение, введите /start");
                start = in.nextLine();
            }
            System.out.println("Привет, я Супа-дупа, а кто ты?");
            String name = getMessage();
            System.out.println("Приятно познакомиться, " + name);
            writeMessageInFile(name, user_ID);
        }

    }

}
