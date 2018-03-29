package com.example.stephen.meisr_mockup;

/**
 * Created by kevin on 3/8/2018.
 */
import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;
import java.util.HashMap;
import java.io.Console;


public class NewSurvey  extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_survey);

        /*final TextView mTextView = (TextView) findViewById(R.id.textView12);

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://skim99.pythonanywhere.com/api/questions/?format=json";
        String url2 = "https://api.androidhive.info/volley/person_object.json";*/


// Request a string response from the provided URL.

    /*    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("GOT JSON", "YEEEEEEEEEEEEEEEEEEEEeEhaW");
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            System.out.println(response.toString(4));
                            mTextView.setText(response.toString(4));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("NO JSON", "NOOOOOOOOOOOOOOOOOO");


                VolleyLog.e("Error: ", error.getMessage());
                VolleyLog.e("Error: ", error.networkResponse);


            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);*/


        /*Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("param1", "name");
        jsonParams.put("param2", "email");

        jsonParams.put("param3", "phone");

        JSONObject jsonObj = new JSONObject(jsonParams);


        JsonObjectRequest myRequest = new JsonObjectRequest(
                Request.Method.GET, url,
                jsonObj,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("GOT JSON", "YEEEEEEEEEEEEEEEEEEEEeEhaW");
                        Log.d("ROLL TIDE", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("NO JSON", "NOOOOOOOOOOOOOOOOOO");
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", "My useragent");
                return headers;
            }
        };
        queue.add(myRequest);*/


        final TextView mTextView = (TextView) findViewById(R.id.textView12);
        final TextView q1 = (TextView) findViewById(R.id.textView6);
        final TextView q2 = (TextView) findViewById(R.id.textView7);
// ...

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url3 ="http://www.google.com";
        String url2 = "https://api.androidhive.info/volley/person_object.json";
        String url ="http://skim99.pythonanywhere.com/api/questions/?format=json";
        final String lol = "lol";

        final Survey survey = new Survey();



// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response);
                        System.out.println(response);
                        System.out.println(response.substring(1, response.length()-1));
                        String gloop = response.substring(1, response.length()-1);

                        try{
                            JSONArray jsonArr = new JSONArray(response);
                           // survey.setQuestions(jsonArr, "thisstr");
                            JSONObject jsonObj = jsonArr.getJSONObject(0);

                            System.out.println("created object");
                            System.out.println(jsonObj);
                            System.out.println(jsonObj.get("id"));
                            System.out.println(jsonObj.get("question_text"));
                            String what = (String) jsonObj.get("question_text");
                            q1.setText(what);


                            JSONObject jsonObj2 = jsonArr.getJSONObject(1);

                            String what2 = (String) jsonObj2.get("question_text");

                            q2.setText(what2);


                        }catch(JSONException e){
                            // Recovery
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
        //stringRequest
        //JSONArray questions = survey.getQuestions();
        String what = survey.getString();
        System.out.println(what);
       // System.out.println(questions);

        final Survey tester = new Survey();
        tester.setQuestions("tester string");
        String answer = tester.getString();

        /*try {
            JSONObject jsonObj = questions.getJSONObject(0);

            System.out.println("created objectOUTSIDE");
            System.out.println(jsonObj);
            System.out.println(jsonObj.get("id"));
            System.out.println(jsonObj.get("question_text"));

        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
            // Do something to recover ... or kill the app.
        }*/

        final Button submit = findViewById(R.id.button5);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                int value = 0;
                switch (selectedId) {
                    case R.id.radiobutton1:
                        value = 1;
                        break;
                    case R.id.radiobutton2:
                        value = 2;
                        break;
                    case R.id.radiobutton3:
                        value = 3;
                        break;

                    case R.id.radiobutton4:
                        value = 4;
                        break;

                }
                System.out.println("value");
                System.out.println(value);





                //Intent nextScreen = new Intent(view.getContext(), NewSurvey.class);
                //startActivityForResult(nextScreen, 0);


                //query login information from database
            }
        });





// Access the RequestQueue through your singleton class.



    }

}
