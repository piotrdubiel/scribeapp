package io.scribeapp.connection;

import javax.security.auth.login.LoginException;

import io.scribeapp.classifier.artifacts.ClassificationRequest;
import io.scribeapp.classifier.artifacts.ClassificationResult;
import io.scribeapp.connection.exceptions.RecognitionException;

/**
 * Created by piotrekd on 11/23/13.
 */
public interface ServiceConnector {
    public Session login(String username, String password) throws LoginException;
    public Session register(String username, String password) throws Exception;
    public ClassificationResult recognize(ClassificationRequest request) throws RecognitionException;
}
