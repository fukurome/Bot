import bot.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        try {
            BotLogic bot = new BotLogic();
            String user_ID = System.getProperty("user.name");
            Scanner in = new Scanner(System.in);
            String directory = System.getProperty("user.dir");
            String filePath = directory +"/" + user_ID + ".txt";
            File file = new File(filePath);
            if(file.exists() && file.length() != 0) {
                String name = bot.readingUserFile(user_ID, directory);
                System.out.println("Рад тебя снова видеть, " + name + "!");
            }
            else
                System.out.println("Если хотите начать общение, введите /start");
            while (true) {
                String message = in.nextLine();
                    String reply = bot.getReply(message, user_ID, directory);
                    System.out.println(reply);
            }
        }
        catch (Exception e) {
            System.err.println("Ой, я сломался");
        }

    }
}
