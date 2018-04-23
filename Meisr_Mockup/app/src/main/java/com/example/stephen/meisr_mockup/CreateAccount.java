package com.example.stephen.meisr_mockup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.toolbox.StringRequest.*;

/**
 * Created by kevin on 3/10/2018.
 */

public class CreateAccount extends AppCompatActivity {

    private void sharedResponse(String response){
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("CreateAccount", response);
        editor.commit();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        //This is a test edit

        final RequestQueue queue = Volley.newRequestQueue(this);
        final SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);



        final Button cancel = findViewById(R.id.button3);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                Intent nextScreen = new Intent(view.getContext(), Mockup1.class);
                startActivityForResult(nextScreen, 0);
            }

        });


        final Button create = findViewById(R.id.button4);
        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                TextView editText = findViewById(R.id.editText3);
                TextView editText2 = findViewById(R.id.editText4);
                TextView editText3 = findViewById(R.id.editText6);
                TextView editText4 = findViewById(R.id.editText12);
                TextView editText5 = findViewById(R.id.editText7);


                final String login = editText.getText().toString();
                final String password = editText2.getText().toString();
                final String password2 = editText3.getText().toString();
                final String email = editText4.getText().toString();
                final String dob = editText5.getText().toString();
                System.out.println(dob);

                if (password.equals(password2)) {
                    String url = "http://skim99.pythonanywhere.com/api/rest-auth/registration/";


                    StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("Success!");
                            Log.d("Response", response);
                            //sharedResponse(response);
                            sharedResponse(response);



                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Failure!");
                            System.out.println(error);

                            error.printStackTrace();
                            String response = "Failure";
                            sharedResponse(response);

                            //sharedResponse(response);

                            //Log.d("Error.Response", response);
                        }
                    }
                    ) {
                        @Override
                        protected Map<String, String> getParams(){
                            Map<String, String> params = new HashMap<String, String>();
                            //System.out.println("IN GET PARAMETERS CA");
                            params.put("username", login);
                            params.put("email", email);
                            params.put("password1", password);
                            params.put("password2", password2);
                            params.put("birth_date", dob);

                            return params;
                        }
                    };
                    //postRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    queue.add(postRequest);

                    RequestQueue.RequestFinishedListener listener =
                            new RequestQueue.RequestFinishedListener()
                            { @Override public void onRequestFinished(Request request)
                            {
                    final String mResponse = m.getString("CreateAccount", "");

                    System.out.print("DONE WITH ACCOUNT CREATION: RESPONSE");
                    System.out.println(mResponse);


                    if(mResponse.equals("Failure")){
                        Toast.makeText(getApplicationContext(), "Failed to Create Account.", 4).show();

                    }else {
                        Toast.makeText(getApplicationContext(), "Account Created!", 4).show();
                    }
                    //SEND INFO TO DATABASE
                    Intent nextScreen = new Intent(getApplicationContext(), Mockup1.class);
                    startActivityForResult(nextScreen, 0);

                            }
                            };
                    queue.addRequestFinishedListener(listener);
                } else
                    Toast.makeText(view.getContext(), "Passwords do not match", 3).show();

            }
            });
    }
}
