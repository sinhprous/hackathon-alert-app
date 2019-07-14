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

        MyTimerTask myTask = new MyTimerTask(MainActivity.this);
        Timer myTimer = new Timer();

        myTimer.schedule(myTask, 1500, 7000);

        MyTimerTask2 myTask2 = new MyTimerTask2(MainActivity.this);
        Timer myTimer2 = new Timer();

        myTimer2.schedule(myTask2, 1500, 5000);
    }

    class MyTimerTask extends TimerTask {
        AppCompatActivity activity;

        MyTimerTask(AppCompatActivity activity){
            this.activity = activity;
        }

        public void run() {
            GetTask getTask = new GetTask(MainActivity.this);
            getTask.execute();
        }
    }

    class MyTimerTask2 extends TimerTask {
        AppCompatActivity activity;

        MyTimerTask2(AppCompatActivity activity){
            this.activity = activity;
        }

        public void run() {
            GetTask2 getTask = new GetTask2(MainActivity.this);
            getTask.execute();
        }
    }
}
