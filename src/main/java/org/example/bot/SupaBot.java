package org.example.bot;

import org.example.bot.botLogic.*;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileNotFoundException;


public class SupaBot extends TelegramLongPollingBot {
    String directory = System.getProperty("user.dir");

   @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            String user_ID = update.getMessage().getChatId().toString();
            BotLogic bot = new BotLogic();
            try {
                String reply = bot.getReply(update.getMessage().getText(), user_ID, directory);
                message.setText(reply);
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
   public String getMessage(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            //Извлекаем из объекта сообщение пользователя
            String response = update.getMessage().getText();
            //Достаем из inMess id чата пользователя
            String chatId = update.getMessage().getChatId().toString();
            //Получаем текст сообщения пользователя, отправляем в написанный нами обработчик
            return response;
        }
        return "";
    }
    /*public void sendReply (String reply, String chatId) {
        SendMessage outMess = new SendMessage();
        //Добавляем в наше сообщение id чата, а также наш ответ
        outMess.setChatId(chatId);
        outMess.setText(reply);
        //Отправка в чат
        try {
            execute(outMess);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public String getBotUsername() {
        return "pupa_supa_dupa_bot";
    }

    @Override
    public String getBotToken() {
        return "5773339114:AAFm5a0YkPgb92sCwz34CHlr6CibF3TODgE";
    }
}

