package org.supaDupa.bot.botLogic.SimpleBot.ResponseToUserAndEventType;

import org.supaDupa.bot.botLogic.UserDataRepository;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class AnswerChooserTest {
    //Pattern pattern;
    @Test
    void find() throws GeneralSecurityException, IOException {
        GoogleSheets googleSheets = new GoogleSheets();
        ResponseToUserAndEventType r = new ResponseToUserAndEventType();
        UserDataRepository repository = new UserDataRepository();
        AnswerChooser ac = new AnswerChooser(googleSheets, r, repository);
        googleSheets.APPLICATION_NAME = "Google Sheets fot TG-bot";
        googleSheets.SPREADSHEETS_ID = "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw";
        String patternsOfAnswers[] = googleSheets.readSheetRow("R2C1:R2C25");
        int c = ac.find(patternsOfAnswers,"ELUSIVE_ANSWERS");
        assertEquals(2, c);
    }

    @Test
    void chooseLinkTest() throws GeneralSecurityException, IOException {
        GoogleSheets googleSheets = new GoogleSheets();
        ResponseToUserAndEventType r = new ResponseToUserAndEventType();
        UserDataRepository repository = new UserDataRepository();
        AnswerChooser ac = new AnswerChooser(googleSheets, r, repository);
        googleSheets.APPLICATION_NAME = "Google Sheets fot TG-bot";
        googleSheets.SPREADSHEETS_ID = "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw";
        String patternsOfAnswers[] = googleSheets.readSheetRow("R2C1:R2C25");
        String a = "";
        a = ac.chooseLink("DESPAIR_LINKS", patternsOfAnswers);
        assertEquals("грусть", a);
    }

    @Test
    void chooseAnswerTest() throws GeneralSecurityException, IOException {
        GoogleSheets googleSheets = new GoogleSheets();
        ResponseToUserAndEventType r = new ResponseToUserAndEventType();
        UserDataRepository repository = new UserDataRepository();
        AnswerChooser ac = new AnswerChooser(googleSheets, r, repository);
        googleSheets.APPLICATION_NAME = "Google Sheets fot TG-bot";
        googleSheets.SPREADSHEETS_ID = "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw";
        String patternsOfAnswers[] = googleSheets.readSheetRow("R2C1:R2C25");
        String a = "";
        Pattern pattern = Pattern.compile("привет");
        a = ac.chooseAnswer("HELLO", pattern, "привет", patternsOfAnswers);
        assertEquals("Привет", a);
    }

}