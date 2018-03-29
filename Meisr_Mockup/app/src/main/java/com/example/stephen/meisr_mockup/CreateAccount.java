package com.example.stephen.meisr_mockup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by kevin on 3/10/2018.
 */

public class CreateAccount extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        //This is a test edit


        final Button cancel = findViewById(R.id.button4);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                Intent nextScreen = new Intent(view.getContext(), Mockup1.class);
                startActivityForResult(nextScreen, 0);
            }

        });


        final Button create = findViewById(R.id.button3);
        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                TextView editText = findViewById(R.id.editText3);
                TextView editText2 = findViewById(R.id.editText4);
                TextView editText3 = findViewById(R.id.editText6);
                TextView editText4 = findViewById(R.id.editText12);


                String login = editText.getText().toString();
                String password = editText2.getText().toString();
                String password2 = editText3.getText().toString();
                String email = editText4.getText().toString();

                if (password.equals(password2)) {
                    Toast.makeText(view.getContext(), "Account Created!", 3).show();
                    //SEND INFO TO DATABASE
                    Intent nextScreen = new Intent(view.getContext(), Mockup1.class);
                    startActivityForResult(nextScreen, 0);
                } else
                    Toast.makeText(view.getContext(), "Passwords do not match", 3).show();

            }
            });
    }
}
