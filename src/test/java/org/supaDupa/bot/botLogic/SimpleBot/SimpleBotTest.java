package org.supaDupa.bot.botLogic.SimpleBot;

import org.supaDupa.bot.botLogic.SimpleBot.ResponseToUserAndEventType.AnswerChooser;
import org.supaDupa.bot.botLogic.SimpleBot.ResponseToUserAndEventType.GoogleSheets;
import org.supaDupa.bot.botLogic.SimpleBot.ResponseToUserAndEventType.ResponseToUserAndEventType;
import org.supaDupa.bot.botLogic.UserDataRepository;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleBotTest {
    @Test
    void sayInReturn() throws GeneralSecurityException, IOException {
        ResponseToUserAndEventType r = new ResponseToUserAndEventType();
        GoogleSheets googleSheets = new GoogleSheets();
        googleSheets.APPLICATION_NAME = "Google Sheets fot TG-bot";
        googleSheets.SPREADSHEETS_ID = "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw";
        SimpleBot sd = new SimpleBot(new AnswerChooser(googleSheets, r, new UserDataRepository()), r);
        r = sd.sayInReturn("плохо", "123");
        assertEquals("грусть", r.response);
    }

}