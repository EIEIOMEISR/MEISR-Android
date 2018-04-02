package com.example.stephen.meisr_mockup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kevin on 3/29/2018.
 */

public class Explaination extends AppCompatActivity {

    private void sharedResponse(String response){
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("Response", response);
        editor.commit();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explaination);
        String url ="http://skim99.pythonanywhere.com/api/questions/?format=json";



            final VolleyCallback callback = new VolleyCallback() {
                @Override
                public void onSuccess(JSONArray result) {
                    System.out.println("We got the array");

                    //HERE ARE THE VARAIABLES STEPHEN
                    //System.out.print(result);


                    JSONObject jsonObj = null;
                    try {
                        jsonObj = result.getJSONObject(0);
                        System.out.println("created object");
                        System.out.println(jsonObj);
                        System.out.println(jsonObj.get("id"));
                        System.out.println(jsonObj.get("question_text"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
            };


            RequestQueue queue = Volley.newRequestQueue(this);

            StringRequest stringRequest;


            stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("Got response");
                            //System.out.println(response);
                            try {
                                JSONArray jsonArr = new JSONArray(response);

                                //survey.setQuestions(jsonArr);
                                System.out.println("IN VOLLEY");
                                callback.onSuccess(jsonArr);
                                sharedResponse(response);


                            } catch (JSONException e) {
                                System.out.println("REtrival Failed");
                                // Recovery
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("That didn't work!");
                    error.printStackTrace();
                }
            });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

        System.out.println("Wooooooooooooorked");
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        final String mResponse = m.getString("Response", "");
        //System.out.println(mResponse);



            final Button next = findViewById(R.id.button6);
        final EditText textviewage = findViewById(R.id.editText5);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
               // final NewSurvey test = new NewSurvey(16);

                String age = textviewage.getText().toString();

                //Intent nextScreen = new Intent(view.getContext(), NewSurvey.class);
                Intent myIntent = new Intent(view.getContext(), NewSurvey.class);
                myIntent.putExtra("age",age);
                myIntent.putExtra("JSONARRAY", mResponse);
                startActivity(myIntent);
                startActivityForResult(myIntent, 0);


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

