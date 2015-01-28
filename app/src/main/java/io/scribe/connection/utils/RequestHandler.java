package io.scribe.connection.utils;

import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;

import io.scribe.utils.Config;

/**
 * Created by piotrekd on 12/28/13.
 */
public class RequestHandler {
    public static HttpResponse request(String uri, HashMap<String, String> data, String token, HashMap<String, String> headers) throws IOException {
        if (token != null) {
            data.put("token", token);
        }

        JSONObject json = new JSONObject();
        if (data != null) {
            try {
                for (Entry<String, String> param : data.entrySet()) {
                    json.put(param.getKey(), param.getValue());
                }
            } catch (JSONException e) {
            }
        }

        HttpPost httpPost = new HttpPost(uri);
        if (headers != null) {
            for (Entry<String, String> header : headers.entrySet()) {
                httpPost.setHeader(header.getKey(), header.getValue());
            }
        }

        return sendJSON(httpPost, json);
    }

    public static HttpResponse request(String uri, HashMap<String, String> data, String token) throws IOException {
        return request(uri, data, token, null);
    }

    public static HttpResponse request(String uri, HashMap<String, String> data) throws IOException {
        return request(uri, data, null, null);
    }

    public static HttpResponse request(String uri, byte[] data, String token) throws IOException {
        String image_b64 = Base64.encodeToString(data, Base64.DEFAULT);
        JSONObject json = new JSONObject();
        try {
            if (token != null) {
                json.put("token", token);
            }
            json.put("data", image_b64);
        } catch (JSONException e) {
        }

        return sendJSON(new HttpPost(uri), json);
    }

    private static HttpResponse sendJSON(HttpPost request, JSONObject json) throws IOException {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, Config.CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, Config.SOCKET_TIMEOUT);
        HttpClient client = new DefaultHttpClient(params);
        StringEntity se = new StringEntity(json.toString());
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        request.setEntity(se);
        Log.d("POST", request.getURI() + " -> " + json.toString());

        return client.execute(request);
    }

    public static String readResponse(HttpResponse response) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder data_builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            data_builder.append(line);
        }
        final String s = data_builder.toString();
        return s;
    }
}
