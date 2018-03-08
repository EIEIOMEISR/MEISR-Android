package com.example.stephen.meisr_mockup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Mockup1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mockup1);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.d("myTag", "This is my message");
                TextView editText = findViewById(R.id.editText);
                TextView editText2 = findViewById(R.id.editText2);


                String login = editText.getText().toString();
                String password = editText2.getText().toString();

                Log.d("Login is", login);
                Log.d("Password is", password);




                //query login information from database
            }
        });

    }


}
