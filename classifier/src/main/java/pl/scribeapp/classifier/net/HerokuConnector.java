package pl.scribeapp.classifier.net;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotrekd on 11/25/13.
 */
public class HerokuConnector implements ServiceConnector {
    private static final String URI = "http://scribe-server.herokuapp.com";

    @Override
    public String request(String action, String data) throws IOException {
        HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(URI + "/" + action);
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            params.add(new BasicNameValuePair("id", "12345"));
            params.add(new BasicNameValuePair("data", "2435345345"));
            request.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse response = client.execute(request);
            return readResponse(response);
    }

    private String readResponse(HttpResponse response) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder data_builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            data_builder.append(line);
        }
        return data_builder.toString();
    }
}
