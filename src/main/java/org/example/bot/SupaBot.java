package org.example.bot;

import org.example.bot.botLogic.*;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import org.example.bot.botLogic.SimpleBot.ResponseToUserAndEventType.ResponseToUserAndEventType;

public class SupaBot extends TelegramLongPollingBot {
   @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            String user_ID = update.getMessage().getChatId().toString();
            UserDataRepository repository = new UserDataRepository();
            BotLogic bot = new BotLogic();
            try {
                Boolean sayHello = repository.isUserFileEmpty(user_ID);
                ResponseToUserAndEventType reply = bot.getReply(update.getMessage().getText(), user_ID, sayHello); //вместо reply - новый класс "ответ пользователю и вид события"
                if ((reply.event).equals("new user"))
                    repository.addUser(user_ID);
                if (sayHello)
                    repository.saveData((reply.event), user_ID);
                if ((reply.event).equals("anecdote"))
                    repository.saveData((reply.event), user_ID);
                message.setText(reply.response);
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
        File file = new File("C:\\Users\\Irina Shingalova\\Desktop\\token.txt");
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = new BufferedReader(fr);
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return line;
   }

}

