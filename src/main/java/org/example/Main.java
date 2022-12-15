package org.example;

import org.example.bot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.IOException;
import java.security.GeneralSecurityException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        try {
            String APPLICATION_NAME = "Google Sheets fot TG-bot";
            String SPREADSHEETS_ID = "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw";
            Bot supaDupa = new Bot();
            supaDupa.APPLICATION_NAME = APPLICATION_NAME;
            supaDupa.SPREADSHEETS_ID = SPREADSHEETS_ID;
            supaDupa.makeBot();
        }catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error");
        }  new GeneralSecurityException("Error1");

    }
}
