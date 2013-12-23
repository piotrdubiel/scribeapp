package pl.scribeapp.classifier.net;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by piotrekd on 11/25/13.
 */
public class HerokuConnector implements ServiceConnector {
    private static final String URI = "http://scribe-server.herokuapp.com";
    private String token;

    public HerokuConnector() {}

    public HerokuConnector(String token) {
        this.token = token;
    }

    @Override
    public String request(String action, HashMap<String, String> data) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(URI + "/" + action);
        //data.put("token", TOKEN);

        //request.setEntity(new StringEntity(json));

        HttpResponse response = client.execute(request);
        return readResponse(response);
    }

    @Override
    public String request(String action, byte[] data) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(URI + "/" + action);
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);

        request.setEntity(new ByteArrayEntity(data));

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
