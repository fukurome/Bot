package org.supaDupa.bot.botLogic;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {
    public ReplyKeyboardMarkup keyboard(String typeOfKeyboard) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        switch (typeOfKeyboard) {
            case "defaultKeyboard":
                row.add("Анекдот");
                row.add("Поддержи меня, пожалуйста");
                keyboardRows.add(row);
                keyboardMarkup.setKeyboard(keyboardRows);
                break;
            case "anecdote":
                row.add("Анекдот про Штирлица");
                row.add("Анекдот про Мюллера");
                row.add("Рандомный анекдот");
                keyboardRows.add(row);

                row = new KeyboardRow();
                row.add("Назад");
                keyboardRows.add(row);

                keyboardMarkup.setKeyboard(keyboardRows);
                break;
            case "feelings":
                row.add("Грусть");
                row.add("Одиночество");
                row.add("Тревогу");
                keyboardRows.add(row);

                row = new KeyboardRow();
                row.add("Злость");
                row.add("Выгорание");
                row.add("Отчаяние");
                keyboardRows.add(row);

                row = new KeyboardRow();
                row.add("Назад");
                keyboardRows.add(row);

                keyboardMarkup.setKeyboard(keyboardRows);
                /*keyboardMarkup.setResizeKeyboard(true);*/
                break;
        }

        return keyboardMarkup;
    }
}
