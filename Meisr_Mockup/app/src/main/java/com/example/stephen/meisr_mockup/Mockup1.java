package com.example.stephen.meisr_mockup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Mockup1 extends AppCompatActivity {

    private void sharedResponse(String response){
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("Token", response);
        editor.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mockup1);

        final TextView editText = findViewById(R.id.editText);
        final TextView editText2 = findViewById(R.id.editText2);




        final RequestQueue queue = Volley.newRequestQueue(this);
        final SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);




        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                //

                final String login = editText.getText().toString();
                final String password = editText2.getText().toString();
                System.out.println(login);
                System.out.println(password);


    final String url = "http://httpbin.org/get?param1=hello";
// add it to the RequestQueue
    String url2 = "http://httpbin.org/post";
    String url3 = "http://skim99.pythonanywhere.com/rest-auth/login/";
            //"(POST){'test': '', 'password2': ''}";


    StringRequest postRequest = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
    @Override
    public void onResponse(String response) {
        System.out.println("Success!");
        Log.d("Response", response);
        sharedResponse(response);


    }
    }, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("Failure!");
        error.printStackTrace();
        String response = "Failure";
        sharedResponse(response);

        NetworkResponse response2 = error.networkResponse;
        if (error instanceof ServerError && response2 != null) {
            try {
                String res = new String(response2.data,
                        HttpHeaderParser.parseCharset(response2.headers, "utf-8"));
                // Now you can use any deserializer to make sense of data
                System.out.println("ERROR RESPONSE");
                System.out.println(res);

                JSONObject obj = new JSONObject(res);
                System.out.println("ERROR RESPONSE");
                System.out.println(res);
            } catch (UnsupportedEncodingException e1) {
                // Couldn't properly decode data to string
                e1.printStackTrace();
            } catch (JSONException e2) {
                // returned data is not JSONObject?
                e2.printStackTrace();
            }
        }


        //Log.d("Error.Response", response);
    }
    }
) {
    @Override
    protected Map<String, String> getParams(){
    Map<String, String> params = new HashMap<String, String>();
    System.out.println("IN GET PARAMETERS");
    System.out.println(login);
    System.out.println(password);
    //params.put("username", login);
    params.put("email", "kziegler@crimson.ua.edu");
    //params.put("password", password);
        params.put("username", "kevinZ");
        params.put("password", "password2");

        return params;
    }
                };
                queue.add(postRequest);


                //queue.addRequestFinishedListener();
                RequestQueue.RequestFinishedListener listener =
                        new RequestQueue.RequestFinishedListener()
                        { @Override public void onRequestFinished(Request request)
                        {

                            final String mResponse = m.getString("Token", "");
                            System.out.println("GOT TOKEN OUTSIDE!");
                            System.out.println(mResponse);
                            if(mResponse.equals("Failure")) {
                                Toast.makeText(getApplicationContext(), "Login Failed", 3).show();
                            }else{
                                //login is correct
                                Toast.makeText(getApplicationContext(), "Login Suceeded", 3).show();
                                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                                myIntent.putExtra("Token", mResponse);
                                startActivity(myIntent);
                                startActivityForResult(myIntent, 0);
                            }


                        }
                        };
                queue.addRequestFinishedListener(listener);



                //SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);


                Log.d("Login is", login);
                Log.d("Password is", password);

                /*if(login.equals("username") && password.equals("password")){
                    Log.d("correct password", password);
                    Intent nextScreen = new Intent(view.getContext(), MainPage.class);
                    startActivityForResult(nextScreen, 0);
                }
                else
                    Toast.makeText(view.getContext(), "Login Failed", 3).show();
                */

                //query login information from database
            }
        });


        final Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                Intent nextScreen2 = new Intent(view.getContext(), CreateAccount.class);
                startActivityForResult(nextScreen2, 0);
            }

        });

        final Button but = findViewById(R.id.tester);
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                Intent nextScreen = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(nextScreen, 0);


                //query login information from database
            }
        });





        }


}
