package pl.scribeapp.classifier.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by piotrekd on 11/23/13.
 */
public interface ServiceConnector {
    public String request(String action, String data) throws IOException;
}
