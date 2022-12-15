package org.example.bot.botLogic.SimpleBot.ResponseToUserAndEventType;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class GoogleSheets {
        private static Sheets sheetsService;
        //private static String APPLICATION_NAME = "Google Sheets fot TG-bot";
        //private static String SPREADSHEETS_ID = "1XK3jhU6eMbn4Ly2neSeBDQwyoP1bp5ZO0rhqQd17iFw";

        private Credential authorize() throws IOException, GeneralSecurityException {
            InputStream in = GoogleSheets.class.getResourceAsStream("/credentials.json");
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                    JacksonFactory.getDefaultInstance(), new InputStreamReader(in)
            );
            List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(),
                    clientSecrets, scopes)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                    .setAccessType("offline")
                    .build();
            Credential credential = new AuthorizationCodeInstalledApp(
                    flow, new LocalServerReceiver())
                    .authorize("user");

            return credential;
        }

        public Sheets getSheetsService(String APPLICATION_NAME) throws IOException, GeneralSecurityException {
            Credential credential = authorize();
            return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(), credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        }

        public String GetColumn(String range, String APPLICATION_NAME, String SPREADSHEETS_ID) throws IOException, GeneralSecurityException {
            sheetsService = getSheetsService(APPLICATION_NAME);
            //String range = "B16";

            Sheets.Spreadsheets.Values.Get request = sheetsService.spreadsheets().values()
                    .get(SPREADSHEETS_ID, range).setValueRenderOption("UNFORMATTED_VALUE");

            ValueRange response = request.execute();
            String column = response.getValues().get(0).get(0).toString();
            return column;
        }

            //запись формулы в табличку
        public void WriteFormula(String formula, String cell, String APPLICATION_NAME, String SPREADSHEETS_ID) throws IOException, GeneralSecurityException {
            //String formula = "=XMATCH(\"" + pattern + "\";A1:AR1)";
            sheetsService = getSheetsService(APPLICATION_NAME);
            ValueRange body = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(formula)
                ));

        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(SPREADSHEETS_ID, cell, body)
                .setValueInputOption("USER_ENTERED")
                .execute();
        }

        public String[] ReadSheet(String range, String APPLICATION_NAME, String SPREADSHEETS_ID) throws IOException, GeneralSecurityException {
            //String range = "A:A";
            sheetsService = getSheetsService(APPLICATION_NAME);
            ValueRange response = sheetsService.spreadsheets().values()
                    .get(SPREADSHEETS_ID, range)
                    .execute();

            List<List<Object>> values = response.getValues();
            int n = values.size();
            String[] answer = new String[n];
            if (values == null || values.isEmpty()) {
                System.out.println("No data found.");
            } else {
                int i = 0;
                for (List row : values) {
                    answer[i] = row.get(0).toString();
                    i++;
                }
            }
            return answer;
        }

}
