package io.scribeapp.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.inject.Inject;

import io.scribeapp.connection.Session;

public class SessionLoader {
    @Inject
    Context context;

    public Session load() {
        try {
            FileInputStream inputStream = context.openFileInput(Config.SESSION_FILENAME);
            if (inputStream != null) {
                String username;
                String token;
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                username = bufferedReader.readLine();
                token = bufferedReader.readLine();
                if (username == null || token == null) {
                    return null;
                }
                inputStream.close();
                return new Session(username, token);
            }
        } catch (IOException e) {
        }
        return null;
    }

    public void save(Session session) {
        if (session == null) return;
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(Config.SESSION_FILENAME, Context.MODE_PRIVATE);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(session.username);
            bufferedWriter.newLine();
            bufferedWriter.write(session.token);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
