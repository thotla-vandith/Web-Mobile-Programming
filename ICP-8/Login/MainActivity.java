// Java file for Main Log In page
// Importing all the neccesary packages
package com.csee5590.mobilelogin;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    // Initializing variables
    Button Loginbutton;
    EditText ControlUsername;
    EditText ControlPassword;
    TextView ControlStatus;
    String uname;
    String pwd;
    boolean flag = false;
    //Function to acquire login credentials and comparing with the given credentials
    //if matched then it redirects to welcome page else it displays incorrect password
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ControlUsername = findViewById(R.id.UsernameXML);
        ControlPassword = findViewById(R.id.PasswordXML);
        ControlStatus = findViewById(R.id.StatusLabelXML);
        uname = ControlUsername.getText().toString();
        pwd = ControlPassword.getText().toString();
        Loginbutton = findViewById(R.id.LoginbuttonXML);
        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ControlUsername.getText().toString().isEmpty() && !ControlPassword.getText().toString().isEmpty()) {
                    if (ControlUsername.getText().toString().equals("vandith") && ControlPassword.getText().toString().equals("vandith123"))
                        { flag = true; }
                }
                if (!flag)
                    { ControlStatus.setText("Incorrect Username or Password"); }
                else
                    { reDirectToWelcomePage(); }
            }
        });
    }
    public void reDirectToWelcomePage () {
        Intent intent = new Intent(MainActivity.this, WelcomePage.class);
        startActivity(intent);
    }
}
