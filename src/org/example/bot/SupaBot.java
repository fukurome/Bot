package org.example.bot;

import org.example.bot.botLogic.*;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.FileNotFoundException;

public class SupaBot extends TelegramLongPollingBot {
   @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            String user_ID = update.getMessage().getChatId().toString();
            UserDataRepository repository = new UserDataRepository();
            String directory = repository.getUserData(user_ID); //убрать обращение к директории
            BotLogic bot = new BotLogic();
            try {
                Boolean sayHello = repository.isUserFileEmpty(user_ID);
                String[] reply = bot.getReply(update.getMessage().getText(), user_ID, directory, sayHello); //вместо reply - новый класс "ответ пользователю и вид события"
                if (reply[1].equals("new user"))
                    repository.addUser(user_ID);
                if (sayHello)
                    repository.saveData(reply[1], user_ID, directory);
                if (reply[1].equals("anecdote"))
                    repository.saveData(reply[1], user_ID, directory);
                message.setText(reply[0]);
            } catch (Exception e) {
                System.err.println("Ой, я сломался");
            }
            try {
                execute(message); // Call method to send the message
            }
             catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public String getBotUsername() {
        return "pupa_supa_dupa_bot";
    }

    @Override
    public String getBotToken() {
        return "5773339114:AAFm5a0YkPgb92sCwz34CHlr6CibF3TODgE";
    }
}

