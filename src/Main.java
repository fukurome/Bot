import bot.*;

public class Main {
    public static void main(String[] args) {
        String user_ID = System.getProperty("user.name");
        BotLogic bot = new BotLogic();
        bot.sayHello(user_ID);
        while (true) {
            String message = bot.getMessage();
            String reply = bot.getReply(message, user_ID);
            System.out.println(reply);
        }
    }
}
