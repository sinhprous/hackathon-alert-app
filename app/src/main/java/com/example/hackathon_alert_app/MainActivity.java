package com.example.hackathon_alert_app;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer  mediaPlayer = null;

        MyTimerTask myTask = new MyTimerTask(MainActivity.this, mediaPlayer);
        Timer myTimer = new Timer();

        myTimer.schedule(myTask, 1500, 7000);
    }

    class MyTimerTask extends TimerTask {
        AppCompatActivity activity;
        MediaPlayer  mediaPlayer;

        MyTimerTask(AppCompatActivity activity, MediaPlayer mediaPlayer){
            this.activity = activity;
            this.mediaPlayer = mediaPlayer;
        }

        public void run() {
            GetTask getTask = new GetTask(MainActivity.this);
            getTask.execute();
        }
    }
}
