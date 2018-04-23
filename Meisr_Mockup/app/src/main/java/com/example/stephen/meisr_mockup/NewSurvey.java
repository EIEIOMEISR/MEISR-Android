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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


public class NewSurvey extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_survey);

        Intent myIntent = getIntent(); // gets the previously created intent
        System.out.println("HERE IS AGE, JSONARRAY, AND MODULE STEVEN!!!");
        final String agef = myIntent.getStringExtra("age");
        final String Jsonarray = myIntent.getStringExtra("JSONARRAY");
        final String mod = myIntent.getStringExtra("Module");
        final String ind = myIntent.getStringExtra("Index");
        final Answer foo = (Answer) myIntent.getExtras().getSerializable("Answers");
        final String token = myIntent.getStringExtra("Token");

        System.out.println("New Survey Token");
        System.out.println(token);


        int tempindex = Integer.parseInt(ind);
        int int1 = 0;
        int int2 = 0;
        int int3 = 0;
        int int4 = 0;

        int age = Integer.parseInt(agef);

       try{
            JSONArray jsonArr = new JSONArray(Jsonarray);
            Survey returnQues = new Survey(age, jsonArr);
            returnQues.selectModule(1);

            System.out.println("GET QUESTIONS CALL!");
            System.out.println(returnQues.getQuestions());
            int[] lel = new int[4];
            lel[0] = 1;
            lel[1] = 1;
            lel[2] = 1;
            lel[3] = 1;


            returnQues.answerQuestion(lel);

            System.out.println("GET QUESTIONS CALL2!");
            System.out.println(returnQues.getQuestions());

            ArrayList<NewAnswer> test = returnQues.getLastAnswered();

           System.out.println("GET PREVIOUS QUESTIONS!");
           test.get(0).getText();
            test.get(1).getText();
            test.get(2).getText();
            test.get(3).getText();
           System.out.println("GET PREVIOUS QUESTIONS DONE!");




        } catch (JSONException e) {
        System.out.println("REtrival Failed");
        // Recovery
         }


        System.out.println(agef);
        System.out.println(Jsonarray);
        System.out.println(mod);




        final TextView mTextView = (TextView) findViewById(R.id.textView12);
        final TextView q1 = (TextView) findViewById(R.id.textView6);
        final TextView q2 = (TextView) findViewById(R.id.textView7);
        final TextView q3 = (TextView) findViewById(R.id.textView8);
        final TextView q4 = (TextView) findViewById(R.id.textView9);


        try {
            JSONArray jsonArr = new JSONArray(Jsonarray);

            //survey.setQuestions(jsonArr);
            System.out.println("IN VOLLEY LOOOOOOOOOOPPPPP!!!");
            System.out.println(tempindex);
            System.out.println(mod);

            for(int i = 0; i< jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                JSONObject obj2 = (JSONObject) jsonObj.get("routine");
                String modstr = (String) obj2.get("description");
                //String modstr = (String) jsonObj.get("routine");
                //String modstr = Integer.toString(modint);
                if(modstr.equals(mod)){
                    jsonObj = jsonArr.getJSONObject(i+tempindex);
                    System.out.println("created object");
                    System.out.println(jsonObj);
                    System.out.println(jsonObj.get("id"));
                    System.out.println(jsonObj.get("question_text"));
                    String what = (String) jsonObj.get("question_text");
                    q1.setText(what);
                    int1 = (int) jsonObj.get("id");


                    JSONObject jsonObj2 = jsonArr.getJSONObject(i+1+tempindex);
                    String what2 = (String) jsonObj2.get("question_text");
                    q2.setText(what2);
                    int2 = (int) jsonObj2.get("id");


                    JSONObject jsonObj3 = jsonArr.getJSONObject(i+2+tempindex);
                    String what3 = (String) jsonObj3.get("question_text");
                    q3.setText(what3);
                    int3 = (int) jsonObj3.get("id");


                    JSONObject jsonObj4 = jsonArr.getJSONObject(i+3+tempindex);
                    String what4 = (String) jsonObj4.get("question_text");
                    q4.setText(what4);
                    int4 = (int) jsonObj4.get("id");


                    tempindex = tempindex + 4;
                    break;

                }



            }


        } catch (JSONException e) {
            System.out.println("REtrival Failed");
            // Recovery
        }

        //Fill in radio buttons

        List<Integer> fooids = foo.getIds();
        List<Integer> foovals = foo.getValues();

        System.out.println("FOO ANSWERS ARE HERE");

        for(int i = 0; i<fooids.size(); i++){
            System.out.println(fooids.get(i));
            System.out.println(foovals.get(i));

        }

        int currentqid;
            currentqid = int1;
            System.out.println(int1);

            for(int j = 0; j< fooids.size(); j++){
                if(fooids.get(j) == currentqid){
                    int tempval = foovals.get(j);
                    switch(tempval){
                        case 1:
                        RadioButton rb = (RadioButton) findViewById(R.id.radiobutton1);
                        rb.setChecked(true);
                        break;
                        case 2:
                            RadioButton rb2 = (RadioButton) findViewById(R.id.radiobutton2);
                            rb2.setChecked(true);
                            break;

                        case 3:
                            RadioButton rb3 = (RadioButton) findViewById(R.id.radiobutton3);
                            rb3.setChecked(true);
                            break;




                    }

                }
            }
        currentqid = int2;
        for(int j = 0; j< fooids.size(); j++){
            if(fooids.get(j) == currentqid){
                int tempval = foovals.get(j);
                switch(tempval){
                    case 1:
                        RadioButton rb = (RadioButton) findViewById(R.id.radiobutton5);
                        rb.setChecked(true);
                        break;
                    case 2:
                        RadioButton rb2 = (RadioButton) findViewById(R.id.radiobutton6);
                        rb2.setChecked(true);
                        break;

                    case 3:
                        RadioButton rb3 = (RadioButton) findViewById(R.id.radiobutton7);
                        rb3.setChecked(true);
                        break;



                }

            }
        }

        currentqid = int3;
        for(int j = 0; j< fooids.size(); j++){
            if(fooids.get(j) == currentqid){
                int tempval = foovals.get(j);
                switch(tempval){
                    case 1:
                        RadioButton rb = (RadioButton) findViewById(R.id.radiobutton9);
                        rb.setChecked(true);
                        break;
                    case 2:
                        RadioButton rb2 = (RadioButton) findViewById(R.id.radiobutton10);
                        rb2.setChecked(true);
                        break;

                    case 3:
                        RadioButton rb3 = (RadioButton) findViewById(R.id.radiobutton11);
                        rb3.setChecked(true);
                        break;



                }

            }
        }
        currentqid = int4;
        for(int j = 0; j< fooids.size(); j++){
            if(fooids.get(j) == currentqid){
                int tempval = foovals.get(j);
                switch(tempval){
                    case 1:
                        RadioButton rb = (RadioButton) findViewById(R.id.radiobutton13);
                        rb.setChecked(true);
                        break;
                    case 2:
                        RadioButton rb2 = (RadioButton) findViewById(R.id.radiobutton14);
                        rb2.setChecked(true);
                        break;

                    case 3:
                        RadioButton rb3 = (RadioButton) findViewById(R.id.radiobutton15);
                        rb3.setChecked(true);
                        break;



                }

            }
        }



// Instantiate the RequestQueue.

        String url3 ="http://www.google.com";
        String url2 = "https://api.androidhive.info/volley/person_object.json";
        String url ="http://skim99.pythonanywhere.com/api/questions/?format=json";
        String urlf="http://skim99.pythonanywhere.com/api/questions/?format=json";


// Request a string response from the provided URL.
        System.out.println("Before Request");


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
        //stringRequest
        final int index = tempindex;
        final int fint = int1;
        final int sint = int2;
        final int tint = int3;
        final int foint = int4;

        final Button cont = findViewById(R.id.contbut);
        cont.setOnClickListener(new View.OnClickListener() {
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

                    //case R.id.radiobutton4:
                    //   value = 4;
                    //   break;

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

                    //case R.id.radiobutton8:
                    //    value2 = 4;
                    //   break;

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

                    // case R.id.radiobutton12:
                    //     value3 = 4;
                    //     break;

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

                    //case R.id.radiobutton16:
                    //    value4 = 4;
                    //    break;

                }
                System.out.println("value4");
                System.out.println(value4);
                System.out.println(fint);
                System.out.println(sint);
                System.out.println(tint);
                System.out.println(foint);

                List<Integer> y = foo.getIds();
                List<Integer> x = foo.getValues();

                x.add(value);
                x.add(value2);
                x.add(value3);
                x.add(value4);

                y.add(fint);
                y.add(sint);
                y.add(tint);
                y.add(foint);

                foo.setIds(y);
                foo.setValues(x);
                System.out.println("VALUES AND IDS BEING ADDED TO ANSWER");
                for (int i = 0; i < x.size(); i++) {

                    System.out.println("val: " + x.get(i) + " id: " + y.get(i));
                }

                //Volley POST for the 4 answers
                final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://skim99.pythonanywhere.com/api/answers/";

                final ArrayList<Integer> values = new ArrayList<>();
                final ArrayList<Integer> qids = new ArrayList<>();


                values.add(value);
                values.add(value2);
                values.add(value3);
                values.add(value4);
                qids.add(fint);
                qids.add(sint);
                qids.add(tint);
                qids.add(foint);



                for(int i = 0; i<4; i++){
                    final int count = i;

                StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Success!");
                        Log.d("Response", response);
                        //sharedResponse(response);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Failure!");
                        System.out.println(error);

                        error.printStackTrace();
                        String response = "Failure";

                        NetworkResponse response2 = error.networkResponse;
                        if (error instanceof ServerError && response2 != null) {
                            try {
                                String res = new String(response2.data,
                                        HttpHeaderParser.parseCharset(response2.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                //System.out.println("ERROR RESPONSE");
                                //System.out.println(res);

                                JSONObject obj = new JSONObject(res);
                                //System.out.println("ERROR RESPONSE");
                                //System.out.println(res);
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                //e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                //e2.printStackTrace();
                            }
                        }

                        //sharedResponse(response);

                        //Log.d("Error.Response", response);
                        String url2 = "http://skim99.pythonanywhere.com/api/answers/" + qids.get(count)+"/";

                        StringRequest postRequest = new StringRequest(Request.Method.PUT, url2, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println("Success!");
                                Log.d("Response", response);
                                //sharedResponse(response);


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("Failure!");
                                System.out.println(error);

                                error.printStackTrace();
                                String response = "Failure";

                                NetworkResponse response2 = error.networkResponse;
                                if (error instanceof ServerError && response2 != null) {
                                    try {
                                        String res = new String(response2.data,
                                                HttpHeaderParser.parseCharset(response2.headers, "utf-8"));
                                        // Now you can use any deserializer to make sense of data
                                        System.out.println("UPDATE ERROR RESPONSE");
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

                                //sharedResponse(response);

                                //Log.d("Error.Response", response);




                            }
                        }
                        ) {
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

                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                //System.out.println("IN GET PARAMETERS CA");
                                params.put("question", qids.get(count).toString());
                                params.put("rating", values.get(count).toString());

                                return params;
                            }

                        };

                        queue.add(postRequest);





                    }
                }
                ) {
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

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        //System.out.println("IN GET PARAMETERS CA");
                        params.put("question", qids.get(count).toString());
                        params.put("rating", values.get(count).toString());

                        return params;
                    }

                };

                queue.add(postRequest);

            }







                String Index = Integer.toString(index);
                Intent myIntent = new Intent(view.getContext(), NewSurvey.class);
                myIntent.putExtra("age",agef);
                myIntent.putExtra("JSONARRAY", Jsonarray);
                myIntent.putExtra("Module", mod);
                myIntent.putExtra("Index",Index);
                myIntent.putExtra("Answers", (Serializable) foo);
                myIntent.putExtra("Token", token);

                startActivity(myIntent);
                startActivityForResult(myIntent, 0);



                //query login information from database
            }
        });

        final Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                int tempindex = index - 8;
                if(tempindex < 0){
                    tempindex = 0;
                }
                String Index = Integer.toString(tempindex);
                Intent myIntent = new Intent(view.getContext(), NewSurvey.class);
                myIntent.putExtra("age",agef);
                myIntent.putExtra("JSONARRAY", Jsonarray);
                myIntent.putExtra("Module", mod);
                myIntent.putExtra("Index",Index);
                myIntent.putExtra("Answers", (Serializable) foo);
                myIntent.putExtra("Token", token);

                startActivity(myIntent);
                startActivityForResult(myIntent, 0);



                //query login information from database
            }
        });

        final Button chase = findViewById(R.id.submit);
        chase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button


                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest;

                String url = "http://skim99.pythonanywhere.com/api/score_survey/";
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
                System.out.println("FINSIHED SUBMIT VOLLEY");


                Intent myIntent = new Intent(view.getContext(), DisplayModule.class);
                myIntent.putExtra("age",agef);
                myIntent.putExtra("JSONARRAY", Jsonarray);
                myIntent.putExtra("Module", mod);
                myIntent.putExtra("Token", token);
                //myIntent.putExtra("Index",Index);
                myIntent.putExtra("Answers", (Serializable) foo);
                startActivity(myIntent);
                startActivityForResult(myIntent, 0);


                //query login information from database
            }
        });






// Access the RequestQueue through your singleton class.



    }

}
