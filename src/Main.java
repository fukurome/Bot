import bot.*;

public class Main {
    public static void main(String[] args) {
        String user_ID = System.getProperty("user.name");
        BotLogic Bot = new BotLogic();
        Bot.sayHello(user_ID);
        while (true) {
            String message = Bot.getMessage();
            String reply = Bot.getReply(message, user_ID);
            System.out.println(reply);
        }
    }
}
