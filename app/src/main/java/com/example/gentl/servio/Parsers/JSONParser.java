package com.example.gentl.servio.Parsers;

/**
 * Created by AlexanderPC on 12.08.2018.
 */

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JSONParser {

    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {}

    // function get json from url
    // by making HTTP POST or GET mehtod
    public static JSONObject makeHttpRequest(String url, String method, FormBody.Builder params) {
        // Making HTTP request
        try {
            final OkHttpClient client = new OkHttpClient();
            Request request = null;
            // check for request method
            if (method.equals("POST"))
            {
                // request method is POST
                RequestBody formBody = params.build();

                request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();
            } else if (method.equals("GET")) {
                // request method is GET
                request = new Request.Builder()
                        .url(url)
                        .build();
            }
            final Response response = client.newCall(request).execute();
            json = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e ){
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        // return JSON String
        return jObj;
    }
}
