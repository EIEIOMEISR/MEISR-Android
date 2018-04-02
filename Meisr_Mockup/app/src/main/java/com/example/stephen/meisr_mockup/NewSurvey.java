package com.example.stephen.meisr_mockup;

/**
 * Created by kevin on 3/8/2018.
 */
import android.app.DownloadManager;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.provider.Telephony.Carriers.PASSWORD;


public class NewSurvey extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_survey);
        System.out.println("wtTTTTTTTTTTTTTTTTTTTTTTTTTTTTTf");

        Intent myIntent = getIntent(); // gets the previously created intent
        System.out.println("LOL");
        final String agef = myIntent.getStringExtra("age");
        final String Jsonarray = myIntent.getStringExtra("JSONARRAY");

        System.out.println(agef);
        System.out.println(Jsonarray);

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
        final TextView q3 = (TextView) findViewById(R.id.textView8);
        final TextView q4 = (TextView) findViewById(R.id.textView9);


        try {
            JSONArray jsonArr = new JSONArray(Jsonarray);

            //survey.setQuestions(jsonArr);
            System.out.println("IN VOLLEY");
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

            JSONObject jsonObj3 = jsonArr.getJSONObject(2);
            String what3 = (String) jsonObj3.get("question_text");
            q3.setText(what3);

            JSONObject jsonObj4 = jsonArr.getJSONObject(3);
            String what4 = (String) jsonObj4.get("question_text");
            q4.setText(what4);



        } catch (JSONException e) {
            System.out.println("REtrival Failed");
            // Recovery
        }

// ...

// Instantiate the RequestQueue.
        //RequestQueue queue = Volley.newRequestQueue(this);
        String url3 ="http://www.google.com";
        String url2 = "https://api.androidhive.info/volley/person_object.json";
        String url ="http://skim99.pythonanywhere.com/api/questions/?format=json";
        String urlf="http://skim99.pythonanywhere.com/api/questions/?format=json";

        /*final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                System.out.println("We got the array");

                //HERE ARE THE VARAIABLES STEPHEN
                //System.out.print(result);
                System.out.println(agef);




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
        };*/

// Request a string response from the provided URL.
        System.out.println("Before Request");

        /*StringRequest stringRequest;


        stringRequest = new StringRequest(Request.Method.GET, urlf,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response);
                        System.out.println("Got response");
                        //System.out.println(response);
                        try {
                            JSONArray jsonArr = new JSONArray(response);

                            //survey.setQuestions(jsonArr);
                            System.out.println("IN VOLLEY");
                            callback.onSuccess(jsonArr);

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

                            JSONObject jsonObj3 = jsonArr.getJSONObject(2);
                            String what3 = (String) jsonObj3.get("question_text");
                            q3.setText(what3);

                            JSONObject jsonObj4 = jsonArr.getJSONObject(3);
                            String what4 = (String) jsonObj4.get("question_text");
                            q4.setText(what4);



                        } catch (JSONException e) {
                            System.out.println("REtrival Failed");
                            // Recovery
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
                error.printStackTrace();
            }
        });*/
       /* {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("key", "value");
                return headers;
            }
        };*/

// Add the request to the RequestQueue.
        /*stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);*/
        //stringRequest
        /*JSONArray questions = survey.getQuestions();
        String out = survey.getString();
        System.out.println("DID VOLLEY OUTPUT?");
        System.out.println(out);

        try {
            JSONObject jsonObj = questions.getJSONObject(0);

            System.out.println("created objectOUTSIDE");
            System.out.println(jsonObj);
            System.out.println(jsonObj.get("id"));
            System.out.println(jsonObj.get("question_text"));

        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
            // Do something to recover ... or kill the app.
        }
*/

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

                RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
                int selectedId2 = radioGroup2.getCheckedRadioButtonId();
                int value2 = 0;
                switch (selectedId2) {
                    case R.id.radiobutton5:
                        value2 = 1;
                        break;
                    case R.id.radiobutton6:
                        value2 = 2;
                        break;
                    case R.id.radiobutton7:
                        value2 = 3;
                        break;

                    case R.id.radiobutton8:
                        value2 = 4;
                        break;

                }
                System.out.println("value2");
                System.out.println(value2);

                RadioGroup radioGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);
                int selectedId3 = radioGroup3.getCheckedRadioButtonId();
                int value3 = 0;
                switch (selectedId3) {
                    case R.id.radiobutton9:
                        value3 = 1;
                        break;
                    case R.id.radiobutton10:
                        value3 = 2;
                        break;
                    case R.id.radiobutton11:
                        value3 = 3;
                        break;

                    case R.id.radiobutton12:
                        value3 = 4;
                        break;

                }
                System.out.println("value3");
                System.out.println(value3);

                RadioGroup radioGroup4 = (RadioGroup) findViewById(R.id.radioGroup4);
                int selectedId4 = radioGroup4.getCheckedRadioButtonId();
                int value4 = 0;
                switch (selectedId4) {
                    case R.id.radiobutton13:
                        value4 = 1;
                        break;
                    case R.id.radiobutton14:
                        value4 = 2;
                        break;
                    case R.id.radiobutton15:
                        value4 = 3;
                        break;

                    case R.id.radiobutton16:
                        value4 = 4;
                        break;

                }
                System.out.println("value4");
                System.out.println(value4);




                Intent nextScreen = new Intent(view.getContext(), NewSurvey.class);
                startActivityForResult(nextScreen, 0);


                //query login information from database
            }
        });

        final Button save = findViewById(R.id.submit);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                Intent nextScreen = new Intent(view.getContext(), MainPage.class);
                startActivityForResult(nextScreen, 0);


                //query login information from database
            }
        });






// Access the RequestQueue through your singleton class.



    }

}
