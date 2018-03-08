package com.example.stephen.meisr_mockup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Mockup1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mockup1);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                Log.d("myTag", "This is my message");
                TextView editText = findViewById(R.id.editText);
                TextView editText2 = findViewById(R.id.editText2);


                String login = editText.getText().toString();
                String password = editText2.getText().toString();

                Log.d("Login is", login);
                Log.d("Password is", password);

                if(login.equals("username") && password.equals("password")){
                    Log.d("correct password", password);
                    Intent nextScreen = new Intent(view.getContext(), MainPage.class);
                    startActivityForResult(nextScreen, 0);
                }
                else
                    Toast.makeText(view.getContext(), "Login Failed", 3).show();


                //query login information from database
            }
        });

    }


}
