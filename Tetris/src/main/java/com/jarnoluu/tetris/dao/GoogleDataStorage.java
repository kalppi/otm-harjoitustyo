package com.jarnoluu.tetris.dao;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GoogleDataStorage implements IDataStorage {
    private static final String APPLICATION_NAME = "Tetris";
    private static final String SPREADSHEET_ID = "1Fr4kPz8__PT8skuvX2y376JWg9wf4LCSziPnWpoiRhY";
    private static final Set<String> SCOPES = SheetsScopes.all();
    private static final String KEY_FILE = "/key.json";
    
    private Credential authorize() throws IOException, GeneralSecurityException {
        Credential credential = GoogleCredential
            .fromStream(GoogleDataStorage.class.getResourceAsStream(KEY_FILE))
            .createScoped(SCOPES);
        
        return credential;
    }

    private Sheets getSheetsService() throws IOException, GeneralSecurityException {
        Credential credential = this.authorize();
        
        return new Sheets.Builder(
          GoogleNetHttpTransport.newTrustedTransport(), 
          JacksonFactory.getDefaultInstance(), credential)
          .setApplicationName(APPLICATION_NAME)
          .build();
    }

    @Override
    public boolean saveObject(IDataObject obj) {
        try {
            Map<Object, Object> data = obj.getData();
            
            List<List<Object>> objects = new ArrayList();
            data.entrySet().forEach((e) -> {
                objects.add(Arrays.asList(e.getKey(), e.getValue()));
            });
            
            Sheets service = this.getSheetsService();
        
            ValueRange body = new ValueRange().setValues(objects);
        
            service.spreadsheets().values()
                    .update(SPREADSHEET_ID, "A1", body)
                    .setValueInputOption("RAW")
                    .execute();
            
            return true;
        } catch (IOException | GeneralSecurityException ex) {
            return false;
        }
    }

    @Override
    public Map<Object, Object> load() {
        try {
            Map<Object, Object> data = new HashMap();
            
            Sheets service = this.getSheetsService();
            
            ValueRange results = service.spreadsheets().values()
                    .get(SPREADSHEET_ID, "A1:B5")
                    .execute();
            
            List<List<Object>> values = results.getValues();
            
            values.forEach((row) -> {
                data.put(row.get(0), row.get(1));
            });
            
            return data;
        } catch (IOException | GeneralSecurityException ex) {
            return null;
        }
    }
}
