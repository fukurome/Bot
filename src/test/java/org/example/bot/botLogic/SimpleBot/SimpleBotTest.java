package org.example.bot.botLogic.SimpleBot;

import org.example.bot.botLogic.SimpleBot.ResponseToUserAndEventType.GoogleSheets;
import org.example.bot.botLogic.SimpleBot.ResponseToUserAndEventType.ResponseToUserAndEventType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleBotTest {
    @Test
    void sayInReturn() throws GeneralSecurityException, IOException {
        SimpleBot sd = new SimpleBot();
        ResponseToUserAndEventType t = sd.sayInReturn("плохо", "123", "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        assertEquals("привет", t.response);
    }

}