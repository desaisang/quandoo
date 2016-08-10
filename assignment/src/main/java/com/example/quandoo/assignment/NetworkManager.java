package com.example.quandoo.assignment;

import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Class to manage the network operations.
 */
public class NetworkManager {

    private static String TAG = "NetworkManager";

    public static InputStream get(String uri) {
        URL url;
        InputStream in = null;

        try {
            url = new URL(uri);
            String protocol = url.getProtocol();
            Log.d(TAG, "GET URL: " + url.toString());


            if (protocol.equalsIgnoreCase("https")) {
                HttpsURLConnection urlConn = null;
                urlConn = (HttpsURLConnection) url.openConnection();
                int respCode = urlConn.getResponseCode();
                Log.d(TAG, " GET HTTPS Response Code: " + respCode);

                if (respCode == HttpURLConnection.HTTP_OK) {
                    in = urlConn.getInputStream();
                } else {
                    //TODO
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception " + e.getStackTrace());
        }
        return in;
    }
}
