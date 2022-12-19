package org.supaDupa.bot.botLogic.SimpleBot.ResponseToUserAndEventType;

import org.supaDupa.bot.botLogic.UserDataRepository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public class AnswerChooser {
    GoogleSheets googleSheets;
    ResponseToUserAndEventType r;
    UserDataRepository repository;
    Random random;
    public AnswerChooser(GoogleSheets googleSheet, ResponseToUserAndEventType response, UserDataRepository repository) {
        this.googleSheets = googleSheet;
        this.r = response;
        this.repository = repository;
        random = new Random();
    }

    public static <String> int find(String[] arr, String target) {
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i].equals(target))
                return i + 1;
        }
        return -1;
    }
    public String[] makeArrayOfPatterns() throws GeneralSecurityException, IOException {
       return googleSheets.readSheetRow("R2C1:R2C25");
    }
    public String chooseLink(String target, String[] patternsOfAnswers) throws GeneralSecurityException, IOException {
        int colOfPattern = find(patternsOfAnswers, target);
        String links[] = googleSheets.readSheetCol("R3C" + colOfPattern + ":R12C" + colOfPattern);
        return links[random.nextInt(links.length)];
    }

    public String chooseAnswer(String target, Pattern pattern, String convertedMessage, String[] patternsOfAnswers) throws GeneralSecurityException, IOException {
        if (pattern.matcher(convertedMessage).find()) {
            int colOfPattern = find(patternsOfAnswers, target);
            String answers[] = googleSheets.readSheetCol("R3C" + colOfPattern + ":R12C" + colOfPattern);
            return answers[random.nextInt(answers.length)];
        }
        return "";
    }

    public String commonAnswer(String message, String[] patternsOfAnswers) throws GeneralSecurityException, IOException {
        String target;
        if (message.trim().endsWith("?"))
            target = "ELUSIVE_ANSWERS";
        else target = "COMMON_PHRASES";
        int colOfPattern = find(patternsOfAnswers, target);
        String answers[] = googleSheets.readSheetCol("R3C" + colOfPattern + ":R12C" + colOfPattern);
        return answers[random.nextInt(answers.length)];
    }
    public ResponseToUserAndEventType chooseAnecdote(Map.Entry<String, String> map, String user_ID, String convertedMessage, String[] patternsOfAnswers) throws GeneralSecurityException, IOException {
        int colOfPattern = find(patternsOfAnswers, map.getValue().toUpperCase());
        String say[] = googleSheets.readSheetCol("R3C" + colOfPattern + ":R12C" + colOfPattern);
        String event = "anecdote";
        if (convertedMessage.contains("мюллер"))
            event = "anecdotem";
        if (convertedMessage.contains("штирлиц"))
            event = "anecdotesh";
        while (true) {
            String jokeTypes[] = googleSheets.readSheetCol("R3C" + (colOfPattern + 1) + ":R12C" + (colOfPattern + 1));
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
