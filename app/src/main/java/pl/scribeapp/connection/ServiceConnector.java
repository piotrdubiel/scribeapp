package pl.scribeapp.connection;

import org.apache.http.HttpException;

import javax.security.auth.login.LoginException;

import pl.scribeapp.classifier.artifacts.ClassificationRequest;
import pl.scribeapp.classifier.artifacts.ClassificationResult;
import pl.scribeapp.classifier.artifacts.LabelClassificationResult;
import pl.scribeapp.connection.exceptions.RecognitionException;

/**
 * Created by piotrekd on 11/23/13.
 */
public interface ServiceConnector {
    public Session login(String username, String password) throws LoginException;
    public Session register(String username, String password) throws Exception;
    public ClassificationResult recognize(ClassificationRequest request) throws RecognitionException;
}
