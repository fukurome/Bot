package org.example.bot.botLogic.SimpleBot.ResponseToUserAndEventType;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.*;

class GoogleSheetsTest {

    @Test
    void writeFormula() throws IOException, GeneralSecurityException {
        /*GoogleSheets gs = new GoogleSheets();
        gs.writeFormula("=XMATCH(\"COMMON_PHRASES\";A2:X2)", "A1", "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        String[] phrases = gs.readSheet("A1", "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        assertEquals("1", phrases[0]);*/
    }

    @Test
    void readSheetTest() throws IOException, GeneralSecurityException {
        GoogleSheets gs = new GoogleSheets();
        String[] phrases = gs.readSheetRow("patterns", "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        assertEquals("COMMON_PHRASES", phrases[0]);
        /*assertEquals("Нет ничего ценнее слов, сказанных к месту и ко времени.", phrases[1]);
        String[] link = gs.readSheet("D3", "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        assertEquals("https://www.teatr-benefis.ru/staty/chto-delat-esli-grustno/,", link[0]);*/
    }
}