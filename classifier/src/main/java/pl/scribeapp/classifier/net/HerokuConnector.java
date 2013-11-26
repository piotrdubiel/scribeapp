package pl.scribeapp.classifier.net;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotrekd on 11/25/13.
 */
public class HerokuConnector implements ServiceConnector {
    private static final String URI = "http://scribe-server.herokuapp.com";

    @Override
    public String request(String action, String data) {
        HttpPost request = new HttpPost(URI + "/" + action);
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("id", "12345"));
        params.add(new BasicNameValuePair("data", "sf"));
        try {
            request.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        PostTask task = (PostTask) new PostTask().execute(requestßß);
        return task.response;
    }
}
