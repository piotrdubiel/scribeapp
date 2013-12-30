package pl.scribeapp.connection;

import android.util.Base64;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;
import javax.security.auth.login.LoginException;

import pl.scribeapp.app.Navigator;
import pl.scribeapp.classifier.artifacts.ClassificationRequest;
import pl.scribeapp.classifier.artifacts.ClassificationResult;
import pl.scribeapp.connection.exceptions.RecognitionException;
import pl.scribeapp.connection.utils.RequestHandler;

/**
 * Created by piotrekd on 11/25/13.
 */
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
    public ClassificationResult recognize(ClassificationRequest request) throws RecognitionException {
        try {
            HttpResponse response = RequestHandler.request(URI + "recognize", request.toByteArray(), navigator.getSession().token);
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
