package com.example.stephen.meisr_mockup;

/**
 * Created by kevin on 3/8/2018.
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainPage  extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        final Button newsurvey = findViewById(R.id.newsurvey);
        newsurvey.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                Intent nextScreen = new Intent(view.getContext(), Explaination.class);
                startActivityForResult(nextScreen, 0);


                //query login information from database
            }
        });


    }

}
