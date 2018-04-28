package com.example.frida.closet;


import android.app.AlertDialog;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

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
import android.os.Message;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class ToiletDataManager {
    private static String result;
    public static String STATUS;
    private MapsActivity mapsActivity;

    public ToiletDataManager(MapsActivity mapsActivity){
        new HttpAsyncTask().execute("http://80.211.203.158:8080/toilet/all");
        this.mapsActivity = mapsActivity;
    }

    public ToiletDataManager(){
        new HttpAsyncTask().execute("http://80.211.203.158:8080/toilet/all");
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

        JSONArray json = null;
        try {
            json = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        inputStream.close();
        return result;
    }

    private String sendPost(final String... strings) {
        StringBuffer response = new StringBuffer();
                try {
                    URL url = new URL("http://80.211.203.158:8080/toilet/addOne");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    Log.d("add: %s", strings[0] + " " + strings[1] + " " + strings[2]);
                    double lat = Double.parseDouble(strings[1]);
                    double lon = Double.parseDouble(strings[2]);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("name", strings[0]);
                    jsonParam.put("latitude", lat);
                    jsonParam.put("longitude", lon);

                    if(strings.length > 3 && !strings[3].equals("")){
                        JSONArray openClose = new JSONArray();
                        String[] days = strings[3].split(";");
                        for(String d : days){
                            String[] day = d.split(",");
                            JSONObject openingHours = new JSONObject();
                            openingHours.put("day", day[0]);
                            day[1] = Integer.parseInt(day[1]) < 10 ? "0" + day[1] : day[1];
                            day[2] = Integer.parseInt(day[2]) < 10 ? "0" + day[2] : day[2];
                            day[3] = Integer.parseInt(day[3]) < 10 ? "0" + day[3] : day[3];
                            day[4] = Integer.parseInt(day[4]) < 10 ? "0" + day[4] : day[4];
                            openingHours.put("openingHour", day[1] + ":" + day[2]);
                            openingHours.put("closingHour", day[3] + ":" + day[4]);
                            openClose.put(openingHours);
                        }
                        jsonParam.put("openingHours", openClose);
                    }

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();
                    STATUS = String.valueOf(conn.getResponseCode());
                    Log.i("STATUS", STATUS);
                    //Log.d("MSG", conn.getResponseMessage());
                    //Log.i("MSG" , conn.getResponseMessage());
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputline;

                    while((inputline = in.readLine()) != null){
                        response.append(inputline);
                    }
                    in.close();
                    JSONObject input = new JSONObject(response.toString());
                    Log.i("MSG", input.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
        return response.toString();
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

    public String newPostAsync(String... strings){
        try {
            return new PostMethodAsync().execute(strings).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }

    private class PostMethodAsync extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            return sendPost(strings);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("bla", "recieved");
            mapsActivity.update();
        }
    }

}
