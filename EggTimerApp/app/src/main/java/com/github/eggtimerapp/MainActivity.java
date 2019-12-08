package com.github.eggtimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import java.nio.channels.SeekableByteChannel;

public class MainActivity extends AppCompatActivity {
    SeekBar sk;
    EditText text;
    public void updateTimer(int seondsLeft){
        int minutes = seondsLeft / 60;
        int seconds = seondsLeft - (int) minutes * 60;
        String secondsWith0 = "" + seconds;
        if(seconds <10)
            secondsWith0 = "0" + seconds;
        text.setText("0" + minutes + ":" + secondsWith0);
    }
    public void timerControl(View view){
        Log.i("X", "FALLANDO");
        new CountDownTimer(sk.getProgress()*1000,1000){
            @Override
            public void onTick(long l) {
                updateTimer((int)l);
                Log.i("X", "FALLANDO");
            };

            @Override
            public void onFinish() {
                text.setText("00:04");
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                mp.start();
            }
        };
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sk = (SeekBar) findViewById(R.id.seekBar);
        sk.setMax(400);
        sk.setProgress(20);
        Log.i("2X", "FALLANDO");
        text =  (EditText) findViewById(R.id.timeText);
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
