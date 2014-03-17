package io.scribeapp.connection;

import android.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.security.auth.login.LoginException;

import io.scribeapp.app.Navigator;
import io.scribeapp.classifier.artifacts.ClassificationRequest;
import io.scribeapp.classifier.artifacts.ClassificationResult;
import io.scribeapp.connection.exceptions.RecognitionException;
import io.scribeapp.connection.utils.RequestHandler;

/**
 * Created by piotrekd on 11/25/13.
 */
@Singleton
public class HerokuConnector implements ServiceConnector {
    private String URI = "http://scribe-server.herokuapp.com/api/";
    @Inject
    Navigator navigator;

    public HerokuConnector() {}

    @Override
    public Session login(String username, String password) throws LoginException {
        String authorizationString = "Basic " + Base64.encodeToString((username + ":" + password).getBytes(), Base64.DEFAULT);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", authorizationString);

        try {
            HttpResponse response = RequestHandler.request(URI + "token", null,  null, headers);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                String token = RequestHandler.readResponse(response);
                return new Session(username, token);
            }
            else {
                throw new LoginException();
            }
        } catch (IOException e) {
            throw new LoginException();
        }
    }

    @Override
    public Session register(String username, String password) throws Exception {
        try {
            HashMap<String,String> data = new HashMap<>();
            data.put("email", username);
            data.put("password", password);
            HttpResponse response = RequestHandler.request(URI + "register", data);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                String token = RequestHandler.readResponse(response);
                return new Session(username, token);
            }
            else {
                throw new Exception();
            }
        } catch (IOException e) {
            throw new Exception();
        }
    }

    @Override
    public ClassificationResult recognize(ClassificationRequest request) throws RecognitionException {
        try {
            final Session session = navigator.getSession();
            if (session == null) {
                throw new RecognitionException();
            }
            HttpResponse response = RequestHandler.request(URI + "pca/recognize", request.toByteArray(), session.token);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                return new ClassificationResult(RequestHandler.readResponse(response));
            }
            else {
                throw new RecognitionException();
            }
        } catch (IOException e) {
            throw new RecognitionException();
        }
    }
}
