package org.supaDupa.bot;

import org.supaDupa.bot.botLogic.BotLogic;
import org.supaDupa.bot.botLogic.SimpleBot.ResponseToUserAndEventType.ResponseToUserAndEventType;
import org.supaDupa.bot.botLogic.UserDataRepository;
import org.supaDupa.bot.botLogic.*;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Bot {
    BotLogic bot;
    UserDataRepository repository;
    public Bot(BotLogic botLogic, UserDataRepository repository) {
        this.bot = botLogic;
        this.repository = repository;
    }
    public void makeBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new SupaBot());
    }


public class SupaBot extends TelegramLongPollingBot  {
    String typeOfKeyboard = "default";
    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            String user_ID = update.getMessage().getChatId().toString();
            ReplyKeyboardMarkup keyboardMarkup = keyboard(typeOfKeyboard);
            message.setReplyMarkup(keyboardMarkup);
            //UserDataRepository repository = new UserDataRepository();
            //BotLogic bot = new BotLogic();
            try {
                Boolean sayHello = repository.isUserFileEmpty(user_ID);
                String messageText = update.getMessage().getText();
                ResponseToUserAndEventType reply = null;
                switch (messageText) {
                    case "Анекдот":
                        message.setText("Какой анекдот хочешь? :)");
                        typeOfKeyboard = "anecdote";
                        keyboardMarkup = keyboard(typeOfKeyboard);
                        message.setReplyMarkup(keyboardMarkup);
                        execute(message);
                        break;
                        
                    case "Поддержи меня, пожалуйста":
                        message.setText("Что ты сейчас чувствуешь?");
                        typeOfKeyboard = "feelings";
                        keyboardMarkup = keyboard(typeOfKeyboard);
                        message.setReplyMarkup(keyboardMarkup);
                        execute(message);
                        break;

                    case "Назад":
                        typeOfKeyboard = "default";
                        keyboardMarkup = keyboard(typeOfKeyboard);
                        message.setReplyMarkup(keyboardMarkup);
                        execute(message);
                        break;
                    default:
                        reply = bot.getReply(messageText, user_ID, sayHello);
                }
                if ((reply.event).equals("new user"))
                    repository.addUser(user_ID);
                if (sayHello)
                    repository.saveData((reply.event), user_ID);
                if ((reply.event).contains("anecdote"))
                    repository.saveData((reply.event), user_ID);
                message.setText(reply.response);

                execute(message);
            }
            catch (Exception e) {
                System.err.println(e.toString());
            }


        }
    }
    @Override
    public String getBotUsername() {
        return "pupa_supa_dupa_bot";
    }

    @Override
    public String getBotToken() {
        Properties prop = new Properties();
        try {
            prop.load(Bot.class.getClassLoader().getResourceAsStream("config.properties"));
            return prop.getProperty("token");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public ReplyKeyboardMarkup keyboard(String typeOfKeyboard) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        switch (typeOfKeyboard) {
            case "default":
                row.add("Анекдот");
                row.add("Поддержи меня, пожалуйста");
                keyboardRows.add(row);
                keyboardMarkup.setKeyboard(keyboardRows);
                break;
            case "anecdote":
                row.add("Анекдот про Штирлица");
                row.add("Анекдот про Мюллера");
                row.add("Рандомный анекдот");
                keyboardRows.add(row);

                row = new KeyboardRow();
                row.add("Назад");
                keyboardRows.add(row);

                keyboardMarkup.setKeyboard(keyboardRows);
                break;
            case "feelings":
                row.add("Грусть");
                row.add("Одиночество");
                row.add("Тревогу");
                keyboardRows.add(row);

                row = new KeyboardRow();
                row.add("Злость");
                row.add("Выгорание");
                row.add("Отчаяние");
                keyboardRows.add(row);

                row = new KeyboardRow();
                row.add("Назад");
                keyboardRows.add(row);

                keyboardMarkup.setKeyboard(keyboardRows);
                break;
        }

        return keyboardMarkup;
    }

}
}
