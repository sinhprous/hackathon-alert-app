package com.example.hackathon_alert_app;

import android.media.MediaPlayer;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTask extends AsyncTask<Void, Void, String> {

    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;
    private AppCompatActivity activity;

    public GetTask(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        String stringUrl = "https://delta-entry-160518.firebaseio.com/collectors.json";
        String result;
        String inputLine;
        try {
            //Create a URL object holding our url
            URL myUrl = new URL(stringUrl);
            //Create a connection
            HttpURLConnection connection =(HttpURLConnection)
                    myUrl.openConnection();
            //Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            //Connect to our url
            connection.connect();
            //Create a new InputStreamReader
            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());
            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            //Check if the line we are reading is not null
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();
            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();
        }
        catch(IOException e){
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    @Override
    protected void onPostExecute(final String result) {
        try {
            JSONObject jsonResult = new JSONObject(result);
            Double alert = jsonResult.getDouble("alert");
            String dateTime = jsonResult.getString("time");
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date recentTime = null;
            try {
                recentTime = (Date)formatter.parse(dateTime);
                long output = recentTime.getTime()/1000L;
                String str = Long.toString(output);
                long timestamp = Long.parseLong(str) * 1000;

                Long currentMilliTime = System.currentTimeMillis();
                if (currentMilliTime - timestamp < 10000){
                    if (alert>0.9){
                        MediaPlayer mediaPlayer = MediaPlayer.create(this.activity, R.raw.fiv);
                        mediaPlayer.start();
                    }else if(alert>0.8){
                        MediaPlayer mediaPlayer = MediaPlayer.create(this.activity, R.raw.four);
                        mediaPlayer.start();
                    }else if(alert>0.7){
                        MediaPlayer mediaPlayer = MediaPlayer.create(this.activity, R.raw.three);
                        mediaPlayer.start();
                    }else if(alert>0.6){
                        MediaPlayer mediaPlayer = MediaPlayer.create(this.activity, R.raw.two);
                        mediaPlayer.start();
                    }else if(alert>0.5){
                        MediaPlayer mediaPlayer = MediaPlayer.create(this.activity, R.raw.one);
                        mediaPlayer.start();
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCancelled() {
    }
}

