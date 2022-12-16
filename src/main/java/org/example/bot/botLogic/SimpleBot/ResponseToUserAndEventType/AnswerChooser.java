package org.example.bot.botLogic.SimpleBot.ResponseToUserAndEventType;

import org.example.bot.botLogic.UserDataRepository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public class AnswerChooser {
    GoogleSheets googleSheets = new GoogleSheets();
    UserDataRepository repository = new UserDataRepository();
    Random random;
    //Pattern pattern;
    public AnswerChooser() {
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
    public String chooseLink(Map.Entry<String, String> link, String convertedMessage, String[] patternsOfAnswers, String APPLICATION_NAME, String SPREADSHEETS_ID) throws GeneralSecurityException, IOException {
        Pattern linkPattern = Pattern.compile(link.getKey());
        if (linkPattern.matcher(convertedMessage).find()) {
            int colOfPattern = find(patternsOfAnswers, link.getValue().toUpperCase() + "_LINKS");
            String links[] = googleSheets.readSheetCol("R3C" + colOfPattern + ":R12C" + colOfPattern, APPLICATION_NAME, SPREADSHEETS_ID);
            return links[random.nextInt(links.length)];
        }
        return "";
    }

    public String chooseAnswer(Map.Entry<String, String> map, Pattern pattern, String convertedMessage, String[] patternsOfAnswers, String APPLICATION_NAME, String SPREADSHEETS_ID) throws GeneralSecurityException, IOException {
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
}
