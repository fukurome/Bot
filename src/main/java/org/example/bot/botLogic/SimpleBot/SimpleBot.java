package org.example.bot.botLogic.SimpleBot;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.regex.Pattern;

import com.google.common.collect.Iterators;
import org.example.bot.botLogic.SimpleBot.ResponseToUserAndEventType.*;
import org.example.bot.botLogic.UserDataRepository;
import org.glassfish.grizzly.utils.ArrayUtils;


public class SimpleBot {
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
    Pattern pattern; // for regexp
    Random random; // for random answers
    GoogleSheets googleSheets = new GoogleSheets();
    UserDataRepository repository = new UserDataRepository();

    public SimpleBot() {
        random = new Random();
    }

    public static <String> int find(String[] arr, String target) {
        //return Iterators.indexOf(Iterators.forArray(arr), x -> x.equals(target));
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i].equals(target))
                return i + 1;
        }
        return -1;
    }

    public String chooseLink(String convertedMessage, String[] patternsOfAnswers, String APPLICATION_NAME, String SPREADSHEETS_ID) throws GeneralSecurityException, IOException {
        for (Map.Entry<String, String> link : PATTERNS_FOR_LINKS.entrySet()) {
            Pattern linkPattern = Pattern.compile(link.getKey());
            if (linkPattern.matcher(convertedMessage).find()) {
                int colOfPattern = find(patternsOfAnswers, link.getValue().toUpperCase() + "_LINKS");
                String links[] = googleSheets.readSheetCol("R3C" + colOfPattern + ":R12C" + colOfPattern, APPLICATION_NAME, SPREADSHEETS_ID);
                return links[random.nextInt(links.length)];
            }
        }
        return "";
    }

    public String chooseAnswer(Map.Entry<String, String> map, String convertedMessage, String[] patternsOfAnswers, String APPLICATION_NAME, String SPREADSHEETS_ID) throws GeneralSecurityException, IOException {
        if (pattern.matcher(convertedMessage).find()) {
            int colOfPattern = find(patternsOfAnswers, map.getValue().toUpperCase());
            String answers[] = googleSheets.readSheetCol("R3C" + colOfPattern + ":R12C" + colOfPattern, APPLICATION_NAME, SPREADSHEETS_ID);
            return answers[random.nextInt(answers.length)];
        }
        return "";
    }

    public String commonAnswer(String message, String[] patternsOfAnswers, String APPLICATION_NAME, String SPREADSHEETS_ID) throws GeneralSecurityException, IOException {
        String target;
        if (message.trim().endsWith("?"))
            target = "ELUSIVE_ANSWERS";
        else target = "COMMON_PHRASES";
        int colOfPattern = find(patternsOfAnswers, target);
        String answers[] = googleSheets.readSheetCol("R3C" + colOfPattern + ":R12C" + colOfPattern, APPLICATION_NAME, SPREADSHEETS_ID);
        return answers[random.nextInt(answers.length)];
    }
    public ResponseToUserAndEventType chooseAnecdote(Map.Entry<String, String> map, ResponseToUserAndEventType r, String user_ID, String convertedMessage, String[] patternsOfAnswers, String APPLICATION_NAME, String SPREADSHEETS_ID) throws GeneralSecurityException, IOException {
        int colOfPattern = find(patternsOfAnswers, map.getValue().toUpperCase());
        String say[] = googleSheets.readSheetCol("R3C" + colOfPattern + ":R12C" + colOfPattern, APPLICATION_NAME, SPREADSHEETS_ID);
        String event = "anecdote";
        if (convertedMessage.contains("мюллер"))
            event = "anecdotem";
        if (convertedMessage.contains("штирлиц"))
            event = "anecdotesh";
        while (true) {
            String jokeTypes[] = googleSheets.readSheetCol("R3C" + (colOfPattern + 1) + ":R12C" + (colOfPattern + 1), APPLICATION_NAME, SPREADSHEETS_ID);
            for (int i = 0; i < jokeTypes.length; ++i) {
                if (jokeTypes[i].contains(event) && repository.presenceOfAnAnecdote(user_ID, jokeTypes[i])) {
                    r.response = say[i];
                    r.event = jokeTypes[i];
                    return r;
                }
            }
            r.response = "Я рассказал все анекдоты :(";
            r.event = "anecdote";
            return r;
        }
    }

    public ResponseToUserAndEventType sayInReturn(String message, String user_ID, String APPLICATION_NAME, String SPREADSHEETS_ID) throws FileNotFoundException, IOException, GeneralSecurityException {
        String answer = "";
        String key = "common";
        ResponseToUserAndEventType r = new ResponseToUserAndEventType();
        String patternsOfAnswers[] = googleSheets.readSheetRow("R2C1:R2C25", APPLICATION_NAME, SPREADSHEETS_ID);
        answer = commonAnswer(message, patternsOfAnswers, APPLICATION_NAME, SPREADSHEETS_ID);
        String convertedMessage = String.join(" ", message.toLowerCase().split("[ {,|.}?]+"));
        for (Map.Entry<String, String> map : PATTERNS_FOR_ANALYSIS.entrySet()) {
            pattern = Pattern.compile(map.getKey());
            if (pattern.matcher(convertedMessage).find()) {
                if (map.getValue().equals("anecdote")) {
                    r = chooseAnecdote(map, r, user_ID, convertedMessage, patternsOfAnswers, APPLICATION_NAME, SPREADSHEETS_ID);
                    return r;
                }
                r.response = chooseAnswer(map, convertedMessage, patternsOfAnswers, APPLICATION_NAME, SPREADSHEETS_ID) + chooseLink(convertedMessage, patternsOfAnswers, APPLICATION_NAME, SPREADSHEETS_ID);
                r.event = map.getValue();
                return r;
            }
        }
        r.response = answer;
        r.event = key;
        return r;
    }

}
