package com.example.pizzaapp;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;

// Starting Screen contains button to go to Menu page.
public class StartingPage extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_page);
    }
    // Redirecting to Menu Page
    public void ToOrderingMenuPage(View view) {
        Intent intent = new Intent(StartingPage.this, OrderingMenuPage.class);
        startActivity(intent);
    }
}