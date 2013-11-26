package pl.scribeapp.classifier.net;

/**
 * Created by piotrekd on 11/23/13.
 */
public interface ServiceConnector {
    public String request(String action, String data);
}
