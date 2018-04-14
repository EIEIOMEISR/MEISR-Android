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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kevin on 3/29/2018.
 */

public class Explaination extends AppCompatActivity {

    String token;


    private void sharedResponse(String response){
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("Response", response);
        editor.commit();
    }
    private void sharedResponse2(String response){
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("Response2", response);
        editor.commit();
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explaination);
        String url ="http://skim99.pythonanywhere.com/api/questions/?format=json";

        Intent myIntent = getIntent(); // gets the previously created intent
        token = myIntent.getStringExtra("Token");


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

        System.out.println("SECOND VOLLEY CALL");



        String url2 ="http://skim99.pythonanywhere.com/api/answers/";

        StringRequest previousanswers = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Got response ANSWERS");
                        System.out.println(response);
                        try {
                            JSONArray jsonArr = new JSONArray(response);

                            //survey.setQuestions(jsonArr);
                            System.out.println("IN VOLLEY");
                            //callback.onSuccess(jsonArr);
                            sharedResponse2(response);


                        } catch (JSONException e) {
                            System.out.println("REtrival Failed");
                            // Recovery
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work ANSWERS!");
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
                    System.out.println("Token in Explaination \n JWT INC");

                    System.out.println(tok);
                    params.put("Authorization", "JWT " + tok);


                } catch (JSONException e) {
                    System.out.println("Messed up Token");
                }
                return params;
            }

        };
        previousanswers.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(previousanswers);

        System.out.println("Wooooooooooooorked2");
        System.out.println(token);
        SharedPreferences m2 = PreferenceManager.getDefaultSharedPreferences(this);
        final String prevAns = m2.getString("Response2", "");
        System.out.println("PREVIOUS ANSWERS");
        System.out.println(prevAns);

        final Answer foo = new Answer();
        List<Integer> ids = foo.getIds();
        List<Integer> vals = foo.getValues();


        try {
            JSONArray jsonArr = new JSONArray(prevAns);
            for(int i = 0; i<jsonArr.length(); i++){
                JSONObject obj = jsonArr.getJSONObject(i);
                int qid = (int) obj.get("question");
                int qval = (int) obj.get("rating");
                System.out.println(qid);
                System.out.println(qval);

                // int tempqid = Integer.parseInt(qid);
                //int tempqval = Integer.parseInt(qval);
                ids.add(qid);
                vals.add(qval);

            }

    }catch( JSONException e) {

        }
        foo.setIds(ids);
        foo.setValues(vals);

        final Button next = findViewById(R.id.button6);
        final EditText textviewage = findViewById(R.id.editText5);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
               // final NewSurvey test = new NewSurvey(16);

                String age = textviewage.getText().toString();

                //Intent nextScreen = new Intent(view.getContext(), NewSurvey.class);
                System.out.println("End of Explaination");
                //System.out.println(mResponse);
                Intent nextScreen = new Intent(view.getContext(), ModuleSelection.class);
                nextScreen.putExtra("age",age);
                nextScreen.putExtra("JSONARRAY", mResponse);
                nextScreen.putExtra("Answers", foo);
                nextScreen.putExtra("Token", token);

                startActivity(nextScreen);
                startActivityForResult(nextScreen, 0);


                //query login information from database
            }
        });

            final Button back = findViewById(R.id.button7);
            back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // Code here executes on main thread after user presses button
                    Intent nextScreen = new Intent(view.getContext(), MainPage.class);
                    nextScreen.putExtra("Token", token);

                    startActivityForResult(nextScreen, 0);


                    //query login information from database
                }
            });



    }

    }

