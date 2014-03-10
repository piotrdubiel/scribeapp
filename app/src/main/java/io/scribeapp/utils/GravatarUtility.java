package io.scribeapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by piotrekd on 16/01/14.
 */
public class GravatarUtility {
    public static Bitmap hash(String query, boolean forceDefault) {
        try {
            String url_text = "http://www.gravatar.com/avatar/" + MD5.digest(query);
            if (forceDefault) {
                url_text += "?f=y&d=retro";
            }
            URL url = new URL(url_text);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            return BitmapFactory.decodeStream(bufferedInputStream);
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static Bitmap hash(String query) {
        return hash(query, true);
    }
}
