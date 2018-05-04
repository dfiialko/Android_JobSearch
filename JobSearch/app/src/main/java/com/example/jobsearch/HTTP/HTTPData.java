package com.example.jobsearch.HTTP;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by User on 3/26/2018.
 */

public class HTTPData {
    public static String stream = null;
    public HTTPData() {
    }

    public String GetHTTPData(String path){
        String stream = null;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                StringBuilder sb = new StringBuilder();
                InputStream ib = new BufferedInputStream(connection.getInputStream());

                BufferedReader br = new BufferedReader(new InputStreamReader(ib));

                String line;

                while ((line = br.readLine()) != null){
                    sb.append(line);
                }
                stream = sb.toString();
                connection.disconnect();
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return stream;
    }
}
