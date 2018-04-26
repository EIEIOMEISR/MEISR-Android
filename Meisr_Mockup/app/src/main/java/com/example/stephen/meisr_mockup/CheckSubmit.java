package com.example.stephen.meisr_mockup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevin on 4/25/2018.
 */

public class CheckSubmit extends AppCompatActivity {
    String token;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_submit);

        Intent myIntent = getIntent(); // gets the previously created intent
        token = myIntent.getStringExtra("Token");


        System.out.println("IN MODULE SELECTION");
        final String agef = myIntent.getStringExtra("age");
        final String Jsonarray = myIntent.getStringExtra("JSONARRAY");
        final Answer foo = (Answer) myIntent.getExtras().getSerializable("Answers");

        final Button submit = findViewById(R.id.button8);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button


                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest;

                String url = "http://www.meisr.org/api/score_survey/";
                System.out.println("STARTED SUBMITTTT!!");


                stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println("SCORRRRRRREDD SURVEYYYYYYYYY newsurveyresp!!");
                                System.out.println(response);
                                try {
                                    JSONArray jsonArr = new JSONArray(response);
                                    //survey.setQuestions(jsonArr);
                                    System.out.println("IN VOLLEY Scores");
                                    System.out.print(jsonArr.length());
                                    System.out.println("after len");



                                    //callback.onSuccess(jsonArr);
                                    //sharedResponse(response);


                                } catch (JSONException e) {
                                    System.out.println("REtrival Failed");
                                    // Recovery
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("That didn't work Submit newsurvey!");
                        error.printStackTrace();
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


                    }
                }){
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        try {
                            JSONObject jsonObj = new JSONObject(token);
                            String tok = (String) jsonObj.get("token");
                            System.out.println("JWT INC");

                            System.out.println(tok);
                            params.put("Authorization", "JWT " + tok);


                        } catch (JSONException e) {
                            System.out.println("Messed up Token");
                        }
                        return params;
                    }

                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(stringRequest);

                RequestQueue.RequestFinishedListener listener =
                        new RequestQueue.RequestFinishedListener()
                        { @Override public void onRequestFinished(Request request) {
                            System.out.println("FINSIHED SUBMIT VOLLEY");

                            Intent myIntent = new Intent(getApplicationContext(), DisplayModule.class);
                            myIntent.putExtra("age",agef);
                            myIntent.putExtra("JSONArray", Jsonarray);
                            //myIntent.putExtra("Module", mod);
                            myIntent.putExtra("Token", token);
                            //myIntent.putExtra("Index",Index);
                            myIntent.putExtra("Answers", (Serializable) foo);
                            startActivity(myIntent);
                            startActivityForResult(myIntent, 0);


                        }
                        };
                queue.addRequestFinishedListener(listener);


                //query login information from database
            }
        });

        final Button back = findViewById(R.id.button10);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button





                Intent myIntent = new Intent(view.getContext(), ModuleSelection.class);
                myIntent.putExtra("age",agef);
                myIntent.putExtra("JSONARRAY", Jsonarray);
                //myIntent.putExtra("Module", mod);
                myIntent.putExtra("Token", token);
                //myIntent.putExtra("Index",Index);
                myIntent.putExtra("Answers", (Serializable) foo);
                myIntent.putExtra("flag", "true");

                startActivity(myIntent);
                startActivityForResult(myIntent, 0);


                //query login information from database
            }
        });
    }
}
