package org.example.bot.botLogic.SimpleBot.ResponseToUserAndEventType;

import org.example.bot.botLogic.SimpleBot.SimpleBot;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class AnswerChooserTest {
    Pattern pattern;
    final Map<String, String> PATTERNS_FOR_LINKS = new HashMap<String, String>() {{
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
    }};
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
        put("зачем\\s.*тут", "whatareyoudoing");
        put("зачем\\s.*здесь", "whatareyoudoing");
        put("что\\s.*делаешь", "whatareyoudoing");
        put("чем\\s.*занимаешься", "whatareyoudoing");
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
        AnswerChooser ac = new AnswerChooser();
        GoogleSheets googleSheets = new GoogleSheets();
        String patternsOfAnswers[] = googleSheets.readSheetRow("R2C1:R2C25", "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        int c = ac.find(patternsOfAnswers,"ELUSIVE_ANSWERS");
        assertEquals(2, c);
    }

    @Test
    void chooseLinkTest() throws GeneralSecurityException, IOException {
        AnswerChooser ac = new AnswerChooser();
        GoogleSheets googleSheets = new GoogleSheets();
        String patternsOfAnswers[] = googleSheets.readSheetRow("R2C1:R2C25", "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        String a = "";
        for( Map.Entry<String, String> link : PATTERNS_FOR_LINKS.entrySet()) {
            a = ac.chooseLink(link, "грустно", patternsOfAnswers, "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        }
        assertEquals("Привет", a);
    }
    @Test
    void chooseAnswerTest() throws GeneralSecurityException, IOException {
        AnswerChooser ac = new AnswerChooser();
        GoogleSheets googleSheets = new GoogleSheets();
        String patternsOfAnswers[] = googleSheets.readSheetRow("R2C1:R2C25", "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        String a = "";
        for( Map.Entry<String, String> map : PATTERNS_FOR_ANALYSIS.entrySet())
            a = ac.chooseAnswer(map, pattern, "привет", patternsOfAnswers, "Google Sheets fot TG-bot", "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw");
        assertEquals("Привет", a);
    }

}