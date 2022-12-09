package org.example.bot.botLogic;

import java.io.*;


public class UserDataRepository {
    String directory = System.getProperty("user.dir");
    public String getUserData(String user_ID) {
        return directory;
    }
    public void saveData(String message, String user_ID) throws IOException {
        String filePath = directory + "/" + user_ID + ".txt";
        File file = new File(filePath);
        FileWriter writer = new FileWriter(filePath, true);
        BufferedWriter bufferWriter = new BufferedWriter(writer);
        try {
            bufferWriter.write(message + "\n");
        } finally {
            bufferWriter.close();
        }

    }
    public boolean presenceOfAnAnecdote(String user_ID, String word) throws IOException { //работа с файлами только в репозитории
        String directory = getUserData(user_ID);
        File file = new File(directory +"/" + user_ID + ".txt");
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String line = "";
        boolean exam = true;
        try {
            line = reader.readLine();
            if (word == line) {
                exam = false;
            }
        } finally {
            reader.close();
        }
        return exam;
    }
    public void addUser(String user_ID) throws IOException {
        String filePath = directory +"/" + user_ID + ".txt";
        File f = new File(filePath);
        f.createNewFile();
    }
    public boolean isUserFileEmpty(String user_ID) {
        String filePath = directory + "/" + user_ID + ".txt";
        File file = new File(filePath);
        return file.exists() && file.length() == 0;
    }
}
