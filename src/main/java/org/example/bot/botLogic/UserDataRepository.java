package org.example.bot.botLogic;

import java.io.*;

public class UserDataRepository {
    String directory = System.getProperty("user.dir");
    public String getUserData(String user_ID) {
        return directory;
    }
    public void saveData(String message, String user_ID) throws IOException {
        /*String filePath = directory + "/" + user_ID + ".txt";
        File file = new File(filePath);
        FileWriter writer = new FileWriter(filePath, true);
        BufferedWriter bufferWriter = new BufferedWriter(writer);
        bufferWriter.write(message + "\n");
        bufferWriter.close();*/
        String filePath = directory + "/" + user_ID + ".txt";
        File file = new File(filePath);
        FileWriter writer = new FileWriter(filePath, true);
        BufferedWriter bufferWriter = new BufferedWriter(writer);
        try {
            bufferWriter.write(message + "\n");
        } catch (Exception e) {
            System.err.println("Ой, я сломался");
        } finally {
            bufferWriter.close();
        }

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
