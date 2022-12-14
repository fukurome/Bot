package org.supaDupa.bot;

import org.supaDupa.bot.botLogic.BotLogic;
import org.supaDupa.bot.botLogic.SimpleBot.ResponseToUserAndEventType.ResponseToUserAndEventType;
import org.supaDupa.bot.botLogic.UserDataRepository;
import org.supaDupa.bot.botLogic.*;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
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
    ResponseToUserAndEventType reply;
    Keyboard buttons;
    public Bot(BotLogic botLogic, UserDataRepository repository, ResponseToUserAndEventType reply, Keyboard buttons) {
        this.bot = botLogic;
        this.repository = repository;
        this.reply = reply;
        this.buttons = buttons;
    }
    public void makeBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new SupaBot());
    }


public class SupaBot extends TelegramLongPollingBot  {
    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            String user_ID = update.getMessage().getChatId().toString();
            Keyboard buttons = new Keyboard();
            ReplyKeyboardMarkup keyboardMarkup;
            try {
                Boolean sayHello = repository.isUserFileEmpty(user_ID);
                String messageText = update.getMessage().getText();
                switch (messageText) {
                    case "??????????????":
                        reply.response = "?????????? ?????????????? ????????????? :)";
                        reply.event = "anecdote";
                        keyboardMarkup = buttons.keyboard(reply.event);
                        message.setReplyMarkup(keyboardMarkup);
                        break;
                        
                    case "???????????????? ????????, ????????????????????":
                        reply.response = "?????? ???? ???????????? ?????????????????????";
                        reply.event = "feelings";
                        keyboardMarkup = buttons.keyboard(reply.event);
                        message.setReplyMarkup(keyboardMarkup);
                        break;

                    case "??????????":
                        reply.response = "????, ?????????????????? ?? ?????????????? ????????";
                        reply.event = "defaultKeyboard";
                        keyboardMarkup = buttons.keyboard(reply.event);
                        message.setReplyMarkup(keyboardMarkup);
                        break;
                    case "/start":
                        reply = bot.getReply(messageText, user_ID, sayHello);
                        keyboardMarkup = buttons.keyboard("defaultKeyboard");
                        message.setReplyMarkup(keyboardMarkup);
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
}
}
