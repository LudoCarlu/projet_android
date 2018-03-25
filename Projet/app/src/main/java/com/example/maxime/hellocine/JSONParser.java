package com.example.maxime.hellocine;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
/**
 * Created by ludoviccarlu on 25/03/2018.
 */

public class JSONParser {

    private static String TAG = "JSONParser";

    public JSONParser() {};

    public static String getJsonFromUrl (String reqUrl) {

        String result = "";

        //HTTP
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if(HttpURLConnection.HTTP_OK==conn.getResponseCode()) {
                //Lire la reponse
                InputStream in = new BufferedInputStream(conn.getInputStream());
                result = parseInputStream(in);
                Log.d("OK","Films downloaded");
            }


        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return result;
    }


    public static String parseInputStream (InputStream inputStream) {

        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String currentline;

        try {
            while ((currentline = streamReader.readLine() ) != null) {
                stringBuilder.append(currentline);
            }

        }
        catch (IOException e) {
            Log.e("JSONParser", "IOException (parseInputStream): " + e);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
