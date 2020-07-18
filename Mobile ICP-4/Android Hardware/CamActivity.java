package com.example.androidhardware;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CamActivity extends AppCompatActivity {
    public static final int CAMERA_REQ = 01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);
    }

    public void openCam(View view) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, CAMERA_REQ);
    }

    @Override
    protected void onActivityResult(int rqtCode, int rsltCode, @Nullable Intent data) {
        super.onActivityResult(rqtCode, rsltCode, data);
        if (rqtCode == CAMERA_REQ) {
            Bitmap b = (Bitmap) data.getExtras().get("data");
            ImageView iv = findViewById(R.id.imageView);
            iv.setImageBitmap(b);
        }
    }
}