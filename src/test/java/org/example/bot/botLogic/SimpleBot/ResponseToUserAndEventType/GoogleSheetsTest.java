package org.example.bot.botLogic.SimpleBot.ResponseToUserAndEventType;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.*;

class GoogleSheetsTest {

    @Test
    void readSheetColTest() throws IOException, GeneralSecurityException {
        GoogleSheets gs = new GoogleSheets();
        String[] phrases = gs.readSheetCol("R3C4:R12C4", "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        assertEquals("Нет ничего ценнее слов, сказанных к месту и ко времени.", phrases[0]);

    }

    @Test
    void readSheetRowTest() throws IOException, GeneralSecurityException {
        GoogleSheets gs = new GoogleSheets();
        String[] phrases = gs.readSheetRow("R2C1:R2C25", "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        assertEquals("COMMON_PHRASES", phrases[0]);
    }
}