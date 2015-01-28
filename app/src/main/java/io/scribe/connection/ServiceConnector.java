package io.scribe.connection;

import javax.security.auth.login.LoginException;

import io.scribe.classifier.model.ClassificationRequest;
import io.scribe.classifier.model.ClassificationResult;
import io.scribe.connection.exceptions.RecognitionException;

/**
 * Created by piotrekd on 11/23/13.
 */
public interface ServiceConnector {
    public Session login(String username, String password) throws LoginException;
    public Session register(String username, String password) throws Exception;
    public ClassificationResult recognize(ClassificationRequest request) throws RecognitionException;
}
