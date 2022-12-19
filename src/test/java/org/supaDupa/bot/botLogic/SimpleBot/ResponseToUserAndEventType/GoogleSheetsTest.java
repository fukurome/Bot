package org.supaDupa.bot.botLogic.SimpleBot.ResponseToUserAndEventType;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.*;

class GoogleSheetsTest {

    @Test
    void readSheetColTest() throws IOException, GeneralSecurityException {
        GoogleSheets googleSheets = new GoogleSheets();
        googleSheets.APPLICATION_NAME = "Google Sheets fot TG-bot";
        googleSheets.SPREADSHEETS_ID = "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw";
        String[] phrases = googleSheets.readSheetCol("R3C1:R12C1");
        assertEquals("Нет ничего ценнее слов, сказанных к месту и ко времени.", phrases[0]);

    }

    @Test
    void readSheetRowTest() throws IOException, GeneralSecurityException {
        GoogleSheets googleSheets = new GoogleSheets();
        googleSheets.APPLICATION_NAME = "Google Sheets fot TG-bot";
        googleSheets.SPREADSHEETS_ID = "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw";
        String[] phrases = googleSheets.readSheetRow("R2C1:R2C25");
        assertEquals("COMMON_PHRASES", phrases[0]);
    }
}