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
    final Map<String, String> PATTERNS_FOR_ANALYSIS = new HashMap<String, String>() {{
        put("/help", "settings");
        put("груст", "sad");
        put("уныл", "sad");
        put("плак", "sad");
        put("плохо", "sad");
        put("одинок", "alone");
        put("одиночеств", "alone");
        put("трево", "anxiety");
        put("злюсь", "anger");
        put("злость", "anger");
        put("выгорани", "burnout");
        put("выгорел", "burnout");
        put("отчаян", "despair");
        //anecdote
        put("анекдот", "anecdote");
        put("шутк", "anecdote");
        //put("мюллер", "anecdotem");
        //put("штирлиц", "anecdotesh");
        //reaction
        put("смешно", "reaction");
        put("несмешно", "reaction");
        put("нравится", "reaction");
        // hello
        put("хай", "hello");
        put("привет", "hello");
        put("здравствуй", "hello");
        // who
        put("кто\\s.*ты", "who");
        put("ты\\s.*кто", "who");
        // name
        put("как\\s.*зовут", "name");
        put("как\\s.*имя", "name");
        put("есть\\s.*имя", "name");
        put("какое\\s.*имя", "name");
        // howareyou
        put("как\\s.*дела", "howareyou");
        put("как\\s.*жизнь", "howareyou");
        put("как\\s.*ты", "howareyou");
        // whatdoyoudoing
        put("зачем\\s.*тут", "whatdoyoudoing");
        put("зачем\\s.*здесь", "whatdoyoudoing");
        put("что\\s.*делаешь", "whatdoyoudoing");
        put("чем\\s.*занимаешься", "whatdoyoudoing");
        // whatdoyoulike
        put("что\\s.*нравится", "whatdoyoulike");
        put("что\\s.*любишь", "whatdoyoulike");
        // iamfeelling
        put("кажется", "iamfeelling");
        put("чувствую", "iamfeelling");
        put("испытываю", "iamfeelling");
        // yes
        put("да", "yes");
        put("согласен", "yes");
        put("согласна", "yes");
        put("и правда", "yes");
        // bye
        put("прощай", "bye");
        //put("пока", "bye");
        put("увидимся", "bye");
        put("до\\s.*свидания", "bye");
    }};

    @Test
    void find() throws GeneralSecurityException, IOException {
        SimpleBot sd = new SimpleBot();
        GoogleSheets googleSheets = new GoogleSheets();
        String patternsOfAnswers[] = googleSheets.readSheetRow("R2C1:R2C25", "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        int c = sd.find(patternsOfAnswers,"ELUSIVE_ANSWERS");
        assertEquals(2, c);
    }

    @Test
    void chooseLinkTest() throws GeneralSecurityException, IOException {
        SimpleBot sd = new SimpleBot();
        GoogleSheets googleSheets = new GoogleSheets();
        String patternsOfAnswers[] = googleSheets.readSheetRow("R2C1:R2C25", "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        String a = sd.chooseLink("грустно", patternsOfAnswers, "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        //assertEquals("Привет", a);
    }
    @Test
    void chooseAnswerTest() throws GeneralSecurityException, IOException {
        SimpleBot sd = new SimpleBot();
        GoogleSheets googleSheets = new GoogleSheets();
        String patternsOfAnswers[] = googleSheets.readSheetRow("R2C1:R2C25", "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");

        //String a = sd.chooseAnswer(Map.Entry<String,String> map, pattern "привет", patternsOfAnswers, "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        //assertEquals("Привет", a);
    }

    @Test
    void sayInReturn() throws GeneralSecurityException, IOException {
        SimpleBot sd = new SimpleBot();
        ResponseToUserAndEventType t = sd.sayInReturn("анекдот", "413117615", "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        assertEquals("анекдот", t.response);
    }

}