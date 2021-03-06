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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by kevin on 3/29/2018.
 * This is called by MainActivity
 * This class displays the instructions of the survey and prompts them for the childs age
 * This class calls ModuleSelection
 */

public class Explaination extends AppCompatActivity {

    String token;
    String array;
    int syncflag = 0;


    private void sharedResponse(String response){
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("Response2", response);
        editor.commit();
    }
    private void sharedResponse2(String response){
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("Response", response);
        editor.commit();
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explaination);

        Intent myIntent = getIntent(); // gets the previously created intent
        token = myIntent.getStringExtra("Token");
        array = myIntent.getStringExtra("JSONARRAY");
        final SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);



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

        String url2 ="https://www.meisr.org/api/answers/";

        StringRequest previousanswers = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Got response ANSWERS");
                        System.out.println(response);
                        sharedResponse(response);
                        syncflag =1;

                        try {
                            JSONArray jsonArr = new JSONArray(response);

                            //survey.setQuestions(jsonArr);
                            System.out.println("IN VOLLEY");
                            //callback.onSuccess(jsonArr);

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
        //previousanswers.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(previousanswers);

        RequestQueue.RequestFinishedListener listener =
                new RequestQueue.RequestFinishedListener()
                { @Override public void onRequestFinished(Request request)
                {

                    System.out.println("Wooooooooooooorked2");
                    System.out.println(token);
                    final String prevAns = m.getString("Response2", "");
                    System.out.println("PREVIOUS ANSWERS");
                    System.out.println(prevAns);

                    try {
                        JSONArray temp = new JSONArray(prevAns);
                        System.out.println(temp.length());
                    }catch(JSONException e){

                    }

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

                            if (age.equals("")) {
                                System.out.println("NOAGE");
                                Toast.makeText(view.getContext(), "Please enter an Age!", 3).show();

                            }else{

                            System.out.println("End of Explaination");
                            //System.out.println(mResponse);
                            Intent nextScreen = new Intent(view.getContext(), ModuleSelection.class);
                            nextScreen.putExtra("age", age);
                            nextScreen.putExtra("JSONARRAY", array);
                            nextScreen.putExtra("Answers", foo);
                            nextScreen.putExtra("Token", token);

                            startActivity(nextScreen);
                            startActivityForResult(nextScreen, 0);


                            //query login information from database
                        }
                    }
                    });

                }
                };

        queue.addRequestFinishedListener(listener);



        //if(queue.addRequestFinishedListener();)



            final Button back = findViewById(R.id.button7);
            back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // Code here executes on main thread after user presses button
                    Intent nextScreen = new Intent(view.getContext(), MainActivity.class);
                    nextScreen.putExtra("Token", token);
                    nextScreen.putExtra("JSONARRAY", array);

                    startActivityForResult(nextScreen, 0);


                    //query login information from database
                }
            });



    }

    }

