package com.example.frida.closet;


import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class ToiletDataManager {
    private static String result;

    public ToiletDataManager(){
        new HttpAsyncTask().execute("http://188.6.28.33:80/toilet/all");
    }

    public static String GET(String url) {
        InputStream inputStream = null;
        String res = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            // convert inputstream to string
            if (inputStream != null)
                res = convertInputStreamToString(inputStream);
            else
                return res;
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return res;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        result = "";
        String res;

        while ((res = bufferedReader.readLine()) != null)
            result += res;

        //Log.d("result %s", result);
        JSONArray json = null;
        try {
            json = new JSONArray(result);
            //result = Integer.toString(json.length());
            //line = json.get(1).getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        inputStream.close();
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.s
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
        }
    }

    public String getResult(){
        //Log.d("result %s", this.result);
        return result;
    }
}
