package com.example.quandoo.assignment;

import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * The module to which interacts with the android network system to get/send the data.
 */
public class NetworkManager {

    private static String TAG = "NetworkManager";

    /**
     * The method interacts with the server to get the data.
     * @param uri Server Url
     * @return data in stream
     */
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
