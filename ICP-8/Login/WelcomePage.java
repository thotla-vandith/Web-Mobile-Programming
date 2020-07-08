// Java File for Welcome Page
//Importing the neccessary packages
package com.csee5590.mobilelogin;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
//Java class consisting of a logout button which redirects to login page
public class WelcomePage extends AppCompatActivity{
    Button LogoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomepage);
        LogoutButton = findViewById(R.id.LogoutButtonXML);
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
