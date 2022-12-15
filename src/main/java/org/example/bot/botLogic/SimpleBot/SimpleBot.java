package org.example.bot.botLogic.SimpleBot;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import org.example.bot.botLogic.SimpleBot.ResponseToUserAndEventType.*;
import org.example.bot.botLogic.UserDataRepository;

public class SimpleBot {

    final Map<String, String> PATTERNS_FOR_LINKS = new HashMap<String, String>() {{
        put("груст", "SAD_LINKS");
        put("уныл", "SAD_LINKS");
        put("плак", "SAD_LINKS");
        put("плохо", "SAD_LINKS");
        put("одинок", "ALONE_LINKS");
        put("одиночеств", "ALONE_LINKS");
        put("трево", "ANXIETY_LINKS");
        put("злюсь", "ANGER_LINKS");
        put("злость", "ANGER_LINKS");
        put("выгорани", "BURNOUT_LINKS");
        put("выгорел", "BURNOUT_LINKS");
        put("отчаян", "DESPAIR_LINKS");
    }};
    final Map<String, String> PATTERNS_FOR_ANALYSIS = new HashMap<String, String>() {{
        put("груст", "SAD");
        put("уныл", "SAD");
        put("плак", "SAD");
        put("плохо", "SAD");
        put("одинок", "ALONE");
        put("одиночеств", "ALONE");
        put("трево", "ANXIETY");
        put("злюсь", "ANGER");
        put("злость", "ANGER");
        put("выгорани", "BURNOUT");
        put("выгорел", "BURNOUT");
        put("отчаян", "DESPAIR");
        //anecdote
        put("анекдот", "ANECDOTE");
        put("шутк", "ANECDOTE");
        //put("мюллер", "anecdotem");
        //put("штирлиц", "anecdotesh");
        //reaction
        put("смешно", "REACTION");
        put("несмешно", "REACTION");
        put("нравится", "REACTION");
        // hello
        put("хай", "HELLO");
        put("привет", "HELLO");
        put("здравствуй", "HELLO");
        // who
        put("кто\\s.*ты", "WHO");
        put("ты\\s.*кто", "WHO");
        // name
        put("как\\s.*зовут", "NAME");
        put("как\\s.*имя", "NAME");
        put("есть\\s.*имя", "NAME");
        put("какое\\s.*имя", "NAME");
        // howareyou
        put("как\\s.*дела", "HOWAREYOU");
        put("как\\s.*жизнь", "HOWAREYOU");
        put("как\\s.*ты", "HOWAREYOU");
        // whatdoyoudoing
        put("зачем\\s.*тут", "WHATAREYOUDOING");
        put("зачем\\s.*здесь", "WHATAREYOUDOING");
        put("что\\s.*делаешь", "WHATAREYOUDOING");
        put("чем\\s.*занимаешься", "WHATAREYOUDOING");
        // whatdoyoulike
        put("что\\s.*нравится", "WHATDOYOULIKE");
        put("что\\s.*любишь", "WHATDOYOULIKE");
       /* // iamfeelling
        put("кажется", "iamfeelling");
        put("чувствую", "iamfeelling");
        put("испытываю", "iamfeelling");*/
        // yes
        put("да", "YES");
        put("согласен", "YES");
        put("согласна", "YES");
        put("и правда", "YES");
        // bye
        put("прощай", "BYE");
        //put("пока", "bye");
        put("увидимся", "BYE");
        put("до\\s.*свидания", "BYE");
    }};

    Pattern pattern; // for regexp
    Random random; // for random answers

    public SimpleBot() {
        random = new Random();
    }

    public ResponseToUserAndEventType sayInReturn(String message, String user_ID, String APPLICATION_NAME, String SPREADSHEETS_ID) throws FileNotFoundException, IOException, GeneralSecurityException {
        String answer = "";
        String key = "common";
        ResponseToUserAndEventType r = new ResponseToUserAndEventType();
        UserDataRepository repository = new UserDataRepository();
        r.event = key;
        if (message.equals("/help")) {
            r.response = "Привет! Я бот Супа-Дупа. Мы можем с тобой поговорить о чём-нибудь, " +
                    "или я могу рассказать тебе анекдот.";
            r.event = "settings";
            return r;
        }
        GoogleSheets sheet = new GoogleSheets();
        String range;
        if (message.trim().endsWith("?"))
            range = "B:B";
        else range = "A:A";
        String respond[] = sheet.ReadSheet(range, APPLICATION_NAME, SPREADSHEETS_ID);
        r.response = respond[random.nextInt(respond.length)];
        String convertedMessage = String.join(" ", message.toLowerCase().split("[ {,|.}?]+"));
        for (Map.Entry<String, String> o : PATTERNS_FOR_ANALYSIS.entrySet()) {
            pattern = Pattern.compile(o.getKey());
            if (pattern.matcher(convertedMessage).find()) {
                if (o.getValue().equals("ANECDOTE"))
                {
                    String say[] = sheet.ReadSheet("C:C", APPLICATION_NAME, SPREADSHEETS_ID);
                    while (true) {
                        if (repository.getLineCountByReader(user_ID) == false) {
                            r.response = "Я рассказал все анекдоты :(";
                            r.event = "ANECDOTE";
                            return r;
                        }
                        r.response = say[random.nextInt(say.length)];
                        r.event = "ANECDOTE";
                        if (repository.presenceOfAnAnecdote(user_ID, r.event)) {
                            return r;
                        }
                    }
                }
                String responsePattern = o.getValue();
                String formula = "=XMATCH(\"" + responsePattern + "\";D2:X2)";
                sheet.WriteFormula(formula, "A1", APPLICATION_NAME, SPREADSHEETS_ID);
                formula = "=REPLACE(ADDRESS(2;A1;4); 2; 1; \"\")";
                sheet.WriteFormula(formula, "B1", APPLICATION_NAME, SPREADSHEETS_ID);
                String patternColumn = sheet.GetColumn("B1", APPLICATION_NAME, SPREADSHEETS_ID);
                String say[] = sheet.ReadSheet(patternColumn + ":" + patternColumn, APPLICATION_NAME, SPREADSHEETS_ID);
                System.out.println(say);
                r.response = say[random.nextInt(say.length)];
                r.event = o.getValue();
                for (Map.Entry<String, String> link: PATTERNS_FOR_LINKS.entrySet()) {
                    Pattern linkPattern = Pattern.compile(link.getKey());
                    if (linkPattern.matcher(convertedMessage).find()) {
                        responsePattern = link.getValue();
                        formula = "=XMATCH(\"" + responsePattern + "\";D2:X2)";
                        sheet.WriteFormula(formula, "C1", APPLICATION_NAME, SPREADSHEETS_ID);
                        formula = "=REPLACE(ADDRESS(2;C1;4); 2; 1; \"\")";
                        sheet.WriteFormula(formula, "D1", APPLICATION_NAME, SPREADSHEETS_ID);
                        patternColumn = sheet.GetColumn("D1", APPLICATION_NAME, SPREADSHEETS_ID);
                        String links[] = sheet.ReadSheet(patternColumn + ":" + patternColumn, APPLICATION_NAME, SPREADSHEETS_ID);
                        r.response = say[random.nextInt(say.length)] + links[random.nextInt(links.length)];
                    }
                }
            }
            return r;
        }
        return r;
    }
}
