package pl.scribeapp.classifier.net;

import android.os.AsyncTask;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by piotrekd on 11/25/13.
 */
public class PostTask extends AsyncTask<HttpPost, Void, String> {
    public String response;

    @Override
    protected String doInBackground(HttpPost... requests) {
        HttpClient client = new DefaultHttpClient();
        try {
            HttpResponse response = client.execute(requests[0]);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder data_builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                data_builder.append(line);
            }
            return data_builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        response = s;
    }
}
