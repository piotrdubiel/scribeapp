package pl.scribeapp.classifier.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by piotrekd on 11/23/13.
 */
public interface ServiceConnector {
    public String request(String action, HashMap<String, String> data) throws IOException;
    public String request(String action, byte[] data) throws IOException;
}
