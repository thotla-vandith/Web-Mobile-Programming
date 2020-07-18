package com.example.androidhardware;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Intent redirect = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openMaps(View view) {
        redirect = new Intent(MainActivity.this, GmapsActivity.class);
        startActivity(redirect);
    }

    public void takePic(View view) {
        redirect = new Intent(MainActivity.this, CamActivity.class);
        startActivity(redirect);
    }

    public void AudioRec(View view) {
        redirect = new Intent(MainActivity.this, AudioRecActivity.class);
        startActivity(redirect);
    }

    public void KeepNotes(View view) {
        redirect = new Intent(MainActivity.this, NotesActivity.class);
        startActivity(redirect);
    }
}