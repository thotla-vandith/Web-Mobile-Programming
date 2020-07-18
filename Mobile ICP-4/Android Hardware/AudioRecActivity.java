package com.example.androidhardware;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioRecActivity extends AppCompatActivity {
    Button buttonStartRecord, buttonStopRecord, buttonStartPlay, buttonStopPlay;
    MediaRecorder AudioRecorder;
    MediaPlayer AudioPlayer;
    String PathToSave = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audiorec);
        buttonStartRecord = findViewById(R.id.buttonStartRec);
        buttonStopRecord = findViewById(R.id.buttonStopRec);
        buttonStartPlay = findViewById(R.id.buttonStartPlay);
        buttonStopPlay = findViewById(R.id.buttonStopPlay);
        if (!checkPermission()) {
            requestPermission();
        }
    }

    public void startrecord(View view) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename;
        filename = "Recording" + timeStamp + ".3gp";
        PathToSave = getFilesDir() + filename;
        Toast.makeText(getApplicationContext(), "Recording - Start Speaking..", Toast.LENGTH_SHORT).show();

        setupMediaRecord();
        try {
            AudioRecorder.prepare();
            AudioRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.RECORD_AUDIO
        }, 1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000:
                break;
        }
    }

    private boolean checkPermission() {
        int recordPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return recordPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void setupMediaRecord() {
        AudioRecorder = new MediaRecorder();
        AudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        AudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        AudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        AudioRecorder.setOutputFile(PathToSave);
    }

    public void stopRecord(View view) {
        Toast.makeText(getApplicationContext(), "Stop Recording...!!", Toast.LENGTH_SHORT).show();
        AudioRecorder.stop();
        AudioRecorder.reset();
        AudioRecorder.release();
        AudioRecorder = null;
    }

    public void playRecord(View view) {
        AudioPlayer = new MediaPlayer();
        try {
            Toast.makeText(getApplicationContext(), "Play Recording..!!", Toast.LENGTH_SHORT).show();
            AudioPlayer.setDataSource(PathToSave);
            AudioPlayer.prepare();
            AudioPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopPlay(View view) {
        Toast.makeText(getApplicationContext(), "Stop playing Recording..!!", Toast.LENGTH_SHORT).show();
        AudioPlayer.stop();
        AudioPlayer.release();
        setupMediaRecord();
    }
}