package com.example.stephen.meisr_mockup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by kevin on 3/29/2018.
 */

public class Explaination extends AppCompatActivity {
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explaination);

        final Button next = findViewById(R.id.button6);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                Intent nextScreen = new Intent(view.getContext(), NewSurvey.class);
                startActivityForResult(nextScreen, 0);


                //query login information from database
            }
        });

            final Button back = findViewById(R.id.button7);
            back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // Code here executes on main thread after user presses button
                    Intent nextScreen = new Intent(view.getContext(), MainPage.class);
                    startActivityForResult(nextScreen, 0);


                    //query login information from database
                }
            });



    }

    }

