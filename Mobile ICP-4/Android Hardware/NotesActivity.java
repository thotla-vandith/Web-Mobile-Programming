package com.example.androidhardware;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesActivity extends AppCompatActivity {
    EditText textInfo;
    TextView labelInfo;
    private File PathToSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        textInfo = findViewById(R.id.textInfoXML);
        labelInfo = findViewById(R.id.labelInfoXML);
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String filename = "sample" + timeStamp + ".txt";

        PathToSave = new File(getFilesDir(), filename);
    }
    // Code to save the text
    public void saveInfo(View view) {
        String text = textInfo.getText().toString();
        text = text + " ";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(PathToSave, true);
            fos.write(text.getBytes());
            textInfo.getText().clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // Code to display the above saved text
    public void loadInfo(View view) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(PathToSave);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            labelInfo.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}