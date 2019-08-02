package com.example.hackathon_alert_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AudioManager manager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        MyTimerTask myTask = new MyTimerTask(MainActivity.this, manager);
        Timer myTimer = new Timer();

        myTimer.schedule(myTask, 1000, 1000);
    }

    class MyTimerTask extends TimerTask {
        AppCompatActivity activity;
        AudioManager manager;

        MyTimerTask(AppCompatActivity activity, AudioManager manager){
            this.activity = activity;
            this.manager = manager;
        }

        public void run() {
            if(!manager.isMusicActive()) {
                GetTask getTask = new GetTask(MainActivity.this);
                getTask.execute();
            }
        }
    }
}
