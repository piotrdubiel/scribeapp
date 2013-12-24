package pl.scribeapp.classifier.net;

import android.util.Base64;
import android.util.Log;

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
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

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
    private static String URI = "http://scribe-server.herokuapp.com/api/";
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
        HttpPost request = new HttpPost(URI + action);
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        String image_b64 = Base64.encodeToString(data, Base64.DEFAULT);
        JSONObject json = new JSONObject();
        try {
            json.put("token", "asd");
            json.put("data", image_b64);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity se = new StringEntity(json.toString());
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        request.setEntity(se);
        Log.d("POST", URI + " -> " + json.toString());

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
