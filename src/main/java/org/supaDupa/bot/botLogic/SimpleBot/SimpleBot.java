package org.supaDupa.bot.botLogic.SimpleBot;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.regex.Pattern;

import org.supaDupa.bot.botLogic.SimpleBot.ResponseToUserAndEventType.AnswerChooser;
import org.supaDupa.bot.botLogic.SimpleBot.ResponseToUserAndEventType.ResponseToUserAndEventType;
import org.supaDupa.bot.botLogic.SimpleBot.ResponseToUserAndEventType.*;


public class SimpleBot {
    AnswerChooser answerChooser;
    ResponseToUserAndEventType r;
    public SimpleBot(AnswerChooser answerChooser, ResponseToUserAndEventType response) {
        this.answerChooser = answerChooser;
        this.r = response;
    }
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
    Pattern pattern; // for regexp

    public ResponseToUserAndEventType sayInReturn(String message, String user_ID) throws FileNotFoundException, IOException, GeneralSecurityException {
        String answer = "";
        String key = "common";
        String patternsOfAnswers[] = answerChooser.makeArrayOfPatterns();
        answer = answerChooser.commonAnswer(message, patternsOfAnswers);
        String convertedMessage = String.join(" ", message.toLowerCase().split("[ {,|.}?]+"));
        for (Map.Entry<String, String> map : PATTERNS_FOR_ANALYSIS.entrySet()) {
            pattern = Pattern.compile(map.getKey());
            if (pattern.matcher(convertedMessage).find()) {
                if (map.getValue().equals("anecdote")) {
                    r = answerChooser.chooseAnecdote(map, user_ID, convertedMessage, patternsOfAnswers);
                    return r;
                }
                r.response = answerChooser.chooseAnswer(map.getValue().toUpperCase(), pattern, convertedMessage, patternsOfAnswers);
                r.event = map.getValue();
                String links = "";
                for (Map.Entry<String, String> link : PATTERNS_FOR_LINKS.entrySet()) {
                    Pattern linkPattern = Pattern.compile(link.getKey());
                    if (linkPattern.matcher(convertedMessage).find()) {
                        links = answerChooser.chooseLink(link.getValue().toUpperCase() + "_LINKS", patternsOfAnswers);
                        r.response += links;
                        return r;
                    }
                }
                return r;
            }
        }
        r.response = answer;
        r.event = key;
        return r;
    }

}
