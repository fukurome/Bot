package org.supaDupa;

import org.supaDupa.bot.Bot;
import org.supaDupa.bot.*;
import org.supaDupa.bot.botLogic.BotLogic;
import org.supaDupa.bot.botLogic.SimpleBot.ResponseToUserAndEventType.AnswerChooser;
import org.supaDupa.bot.botLogic.SimpleBot.ResponseToUserAndEventType.GoogleSheets;
import org.supaDupa.bot.botLogic.SimpleBot.ResponseToUserAndEventType.ResponseToUserAndEventType;
import org.supaDupa.bot.botLogic.SimpleBot.SimpleBot;
import org.supaDupa.bot.botLogic.UserDataRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        try {
            String APPLICATION_NAME = "Google Sheets fot TG-bot";
            String SPREADSHEETS_ID = "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw";
            GoogleSheets googleSheets = new GoogleSheets();
            googleSheets.APPLICATION_NAME = APPLICATION_NAME;
            googleSheets.SPREADSHEETS_ID = SPREADSHEETS_ID;
            UserDataRepository repository = new UserDataRepository();
            ResponseToUserAndEventType response = new ResponseToUserAndEventType();
            AnswerChooser answerChooser = new AnswerChooser(googleSheets, response, repository);
            SimpleBot simpleBot = new SimpleBot(answerChooser, response);
            BotLogic botLogic = new BotLogic(simpleBot, response);
            Bot supaDupa = new Bot(botLogic, repository);
            supaDupa.makeBot();

        }catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(e.toString());
        }

    }
}
