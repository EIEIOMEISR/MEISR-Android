package com.example.stephen.meisr_mockup;

/**
 * Created by kevin on 3/8/2018.
 * This is the most complicated Class by far there are incode comments to explain what it is doing
 * The function of this class is to iterate through questions
 * This class is called by ModuleSelection
 * This class navigates back to ModuleSelection when the Routine is deemed correct
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
import java.util.Stack;


public class NewSurvey extends AppCompatActivity {

    int modnum;
    String Func;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_survey);

        //Break down of NewSurvey Class

        //Lines 88 through 800 a try catch to parse what to do based on what the variable "Func" is
        //"Continue" proceeds as normal, it pulls questions from getQuestions()
        //"Back" returns previous questions
        //"Complete submits the survey.
        // It parses a stack to make sure and submit an skipped questions using a nest volley call to answer/update
        //It also parses questions up to the age to make sure they have all been answered. If they have not the answer/update volley call is used

        //Lines ~800-915 fill in radio buttons based on previous answers
        //Answers are stored in the answer class object foo

        //ON CLICK Listener for Continue Button Lines 920-1300
        //First it retrieves the values in the radio buttons
        //Next there is an answer/update volley call for the answers
        //Next the routine is checked to see if it is complete
        //Finally the NewSurvey Class is called again to restart this screen



        //ON CLICK Listener for Back Lines 1300-1330
        // Recalls NewSurvey with the var Func = "Back"



        //Call Intents to get previously passed messages
        Intent myIntent = getIntent();
        System.out.println("HERE IS AGE, JSONARRAY, AND MODULE STEVEN!!!");
        final String agef = myIntent.getStringExtra("age");
        final String Jsonarray = myIntent.getStringExtra("JSONARRAY");
        final String mod = myIntent.getStringExtra("Module");
        final String ind = myIntent.getStringExtra("Index");
        final Answer foo = (Answer) myIntent.getExtras().getSerializable("Answers");
        final String token = myIntent.getStringExtra("Token");
        Func = myIntent.getStringExtra("nextFunc");

        System.out.println("New Survey Token");
        System.out.println(token);



        //Declare values for radio buttons and questions
        int int1 = 0;
        int int2 = 0;
        int int3 = 0;
        int int4 = 0;

        int age = Integer.parseInt(agef);

        final TextView mTextView = (TextView) findViewById(R.id.textView12);
        final TextView q1 = (TextView) findViewById(R.id.textView6);
        final TextView q2 = (TextView) findViewById(R.id.textView7);
        final TextView q3 = (TextView) findViewById(R.id.textView8);
        final TextView q4 = (TextView) findViewById(R.id.textView9);




        //Lines 88 through 800 a try catch to parse what to do based on what the variable "Func" is
        //"Continue" proceeds as normal, it pulls questions from getQuestions()
        //"Back" returns previous questions
        //"Complete submits the survey.
        // It parses a stack to make sure and submit an skipped questions using a nest volley call to answer/update
        //It also parses questions up to the age to make sure they have all been answered. If they have not the answer/update volley call is used

        try {
            JSONArray jsonArr = new JSONArray(Jsonarray);


            MyApp app = (MyApp)getApplicationContext();
            Survey returnQues = app.getSurvey();

            System.out.println("GET QUESTIONS CALL SurveyStep!");
            System.out.println(Func);
            String flag = "t";

            if(Func.equals("Back")){
                System.out.println("Pre CRASHED");

                JSONArray x = returnQues.getLastAnswered();
                System.out.println(x);
                System.out.println("Past CRASHED");

                try{
                  JSONObject jsonObj = x.getJSONObject(0);

                }catch(JSONException e){
                    flag = "n";
                    System.out.println("ISNULL");

                }
                System.out.println("Past try");

                try {
                    JSONObject jsonObj = x.getJSONObject(0);
                    String what = (String) jsonObj.get("question_text");
                    what = what.replaceAll("â\u0080\u0099", "\'");
                    what = what.replaceAll("â\u0080\u009C", "\"");
                    what = what.replaceAll("â\u0080\u009D", "\"");
                    q4.setText(what);
                    int4 = (int) jsonObj.get("id");
                    System.out.println(int4);
                    JSONObject routobj = (JSONObject) jsonObj.get("routine");
                    modnum = (int) Integer.parseInt(routobj.get("number").toString());
                    System.out.println("Modnum1");
                    System.out.println(modnum);

                    JSONObject jsonObj2 = x.getJSONObject(1);
                    String what2 = (String) jsonObj2.get("question_text");
                    what2 = what2.replaceAll("â\u0080\u0099", "\'");
                    what2 = what2.replaceAll("â\u0080\u009C", "\"");
                    what2 = what2.replaceAll("â\u0080\u009D", "\"");
                    q3.setText(what2);
                    int3 = (int) jsonObj2.get("id");
                    System.out.println(int3);


                    JSONObject jsonObj3 = x.getJSONObject(2);
                    String what3 = (String) jsonObj3.get("question_text");
                    what3 = what3.replaceAll("â\u0080\u0099", "\'");
                    what3 = what3.replaceAll("â\u0080\u009C", "\"");
                    what3 = what3.replaceAll("â\u0080\u009D", "\"");
                    q2.setText(what3);
                    int2 = (int) jsonObj3.get("id");
                    System.out.println(int2);


                    JSONObject jsonObj4 = x.getJSONObject(3);
                    String what4 = (String) jsonObj4.get("question_text");
                    what4 = what4.replaceAll("â\u0080\u0099", "\'");
                    what4 = what4.replaceAll("â\u0080\u009C", "\"");
                    what4 = what4.replaceAll("â\u0080\u009D", "\"");
                    q1.setText(what4);
                    int1 = (int) jsonObj4.get("id");
                    System.out.println(int1);

                }catch(JSONException e){

                }

            }

            System.out.println("flag");
            System.out.println(flag);

            if(flag.equals("n")) {
                System.out.println("flag Was null");
                int[] answersarray = new int[4];
                answersarray[0] = 2;
                answersarray[1] = 2;
                answersarray[2] = 2;
                answersarray[3] = 2;
                returnQues.answerQuestion(answersarray);


                JSONArray x = returnQues.getLastAnswered();
                JSONObject jsonObj = x.getJSONObject(0);
                String what = (String) jsonObj.get("question_text");
                what = what.replaceAll("â\u0080\u0099", "\'");
                what = what.replaceAll("â\u0080\u009C", "\"");
                what = what.replaceAll("â\u0080\u009D", "\"");
                q4.setText(what);
                int4 = (int) jsonObj.get("id");
                System.out.println(int4);
                JSONObject routobj = (JSONObject) jsonObj.get("routine");
                modnum = (int) Integer.parseInt(routobj.get("number").toString());
                System.out.println("Modnum2");
                System.out.println(modnum);

                JSONObject jsonObj2 = x.getJSONObject(1);
                String what2 = (String) jsonObj2.get("question_text");
                what2 = what2.replaceAll("â\u0080\u0099", "\'");
                what2 = what2.replaceAll("â\u0080\u009C", "\"");
                what2 = what2.replaceAll("â\u0080\u009D", "\"");
                q3.setText(what2);
                int3 = (int) jsonObj2.get("id");
                System.out.println(int3);


                JSONObject jsonObj3 = x.getJSONObject(2);
                String what3 = (String) jsonObj3.get("question_text");
                what3 = what3.replaceAll("â\u0080\u0099", "\'");
                what3 = what3.replaceAll("â\u0080\u009C", "\"");
                what3 = what3.replaceAll("â\u0080\u009D", "\"");
                q2.setText(what3);
                int2 = (int) jsonObj3.get("id");
                System.out.println(int2);


                JSONObject jsonObj4 = x.getJSONObject(3);
                String what4 = (String) jsonObj4.get("question_text");
                what4 = what4.replaceAll("â\u0080\u0099", "\'");
                what4 = what4.replaceAll("â\u0080\u009C", "\"");
                what4 = what4.replaceAll("â\u0080\u009D", "\"");
                q1.setText(what4);
                int1 = (int) jsonObj4.get("id");
                System.out.println(int1);


            }else if(Func.equals("Continue")) {
                JSONArray x = returnQues.getQuestions();
                System.out.println(x);

                System.out.println(mod);

                System.out.println("LENGTH81 OF JSON ARRAY");
                System.out.println(x);
                int lengthradio = x.length();
                System.out.println(x.length());

                //Dont display radio buttons if there are no questions
                if(lengthradio < 4){
                    RadioButton rb = (RadioButton) findViewById(R.id.radiobutton13);
                    rb.setChecked(true);
                    rb.setVisibility(View.INVISIBLE);
                    RadioButton rb2 = (RadioButton) findViewById(R.id.radiobutton14);
                    rb2.setVisibility(View.INVISIBLE);
                    RadioButton rb3 = (RadioButton) findViewById(R.id.radiobutton15);
                    rb3.setVisibility(View.INVISIBLE);
                }
                if(lengthradio < 3){
                    RadioButton rb = (RadioButton) findViewById(R.id.radiobutton9);
                    rb.setChecked(true);
                    rb.setVisibility(View.INVISIBLE);
                    RadioButton rb2 = (RadioButton) findViewById(R.id.radiobutton10);
                    rb2.setVisibility(View.INVISIBLE);
                    RadioButton rb3 = (RadioButton) findViewById(R.id.radiobutton11);
                    rb3.setVisibility(View.INVISIBLE);
                }
                if(lengthradio < 2){
                    RadioButton rb = (RadioButton) findViewById(R.id.radiobutton5);
                    rb.setChecked(true);
                    rb.setVisibility(View.INVISIBLE);
                    RadioButton rb2 = (RadioButton) findViewById(R.id.radiobutton6);
                    rb2.setVisibility(View.INVISIBLE);
                    RadioButton rb3 = (RadioButton) findViewById(R.id.radiobutton7);
                    rb3.setVisibility(View.INVISIBLE);
                }

                if(lengthradio < 2){
                    RadioButton rb = (RadioButton) findViewById(R.id.radiobutton5);
                    rb.setChecked(true);
                    rb.setVisibility(View.INVISIBLE);
                    RadioButton rb2 = (RadioButton) findViewById(R.id.radiobutton6);
                    rb2.setVisibility(View.INVISIBLE);
                    RadioButton rb3 = (RadioButton) findViewById(R.id.radiobutton7);
                    rb3.setVisibility(View.INVISIBLE);
                }
                if(lengthradio < 1){
                    RadioButton rb = (RadioButton) findViewById(R.id.radiobutton1);
                    rb.setChecked(true);
                    rb.setVisibility(View.INVISIBLE);
                    RadioButton rb2 = (RadioButton) findViewById(R.id.radiobutton2);
                    rb2.setVisibility(View.INVISIBLE);
                    RadioButton rb3 = (RadioButton) findViewById(R.id.radiobutton3);
                    rb3.setVisibility(View.INVISIBLE);
                    Func = "Complete";
                }

                if(Func.equals("Continue")){

                JSONObject jsonObj = x.getJSONObject(0);
                String what = (String) jsonObj.get("question_text");
                what = what.replaceAll("â\u0080\u0099", "\'");
                what = what.replaceAll("â\u0080\u009C", "\"");
                what = what.replaceAll("â\u0080\u009D", "\"");
                q1.setText(what);
                int1 = (int) jsonObj.get("id");
                System.out.println(int1);
                JSONObject routobj = (JSONObject) jsonObj.get("routine");
                modnum = (int) Integer.parseInt(routobj.get("number").toString());
                System.out.println("Modnum3");
                System.out.println(modnum);


                JSONObject jsonObj2 = x.getJSONObject(1);
                String what2 = (String) jsonObj2.get("question_text");
                what2 = what2.replaceAll("â\u0080\u0099", "\'");
                what2 = what2.replaceAll("â\u0080\u009C", "\"");
                what2 = what2.replaceAll("â\u0080\u009D", "\"");
                q2.setText(what2);
                int2 = (int) jsonObj2.get("id");
                System.out.println(int2);

                //Attending to sound of caregiverâs voice
                //Using pointing to communicate (e.g., as if to say âlookâ or âI wantâ)

                JSONObject jsonObj3 = x.getJSONObject(2);
                String what3 = (String) jsonObj3.get("question_text");
                what3 = what3.replaceAll("â\u0080\u0099", "\'");
                what3 = what3.replaceAll("â\u0080\u009C", "\"");
                what3 = what3.replaceAll("â\u0080\u009D", "\"");
                q3.setText(what3);
                int3 = (int) jsonObj3.get("id");
                System.out.println(int3);


                JSONObject jsonObj4 = x.getJSONObject(3);
                String what4 = (String) jsonObj4.get("question_text");
                what4 = what4.replaceAll("â\u0080\u0099", "\'");
                what4 = what4.replaceAll("â\u0080\u009C", "\"");
                what4 = what4.replaceAll("â\u0080\u009D", "\"");
                q4.setText(what4);
                int4 = (int) jsonObj4.get("id");
                System.out.println(int4);

            }}
            if(Func.equals("Complete")){
                System.out.println("IN COMPLETE");

                Toast.makeText(getApplicationContext(), "Module Complete!", Toast.LENGTH_LONG).show();

                //JSONArray x = returnQues.getQuestions();
                //JSONObject jsonObj = x.getJSONObject(1);
                //JSONObject routobj = (JSONObject) jsonObj.get("routine");
                //modnum = (int) Integer.parseInt(routobj.get("number").toString());
                modnum = returnQues.returncurrentModuleId();
                System.out.println("Modnum3");
                System.out.println(modnum);

                System.out.println("trying to get STACK");
                System.out.println(modnum);
                Stack<NewAnswer> test = returnQues.getModuleAnswers(modnum);
                System.out.println("GOT STACK");
                List<Integer> fooids = foo.getIds();
                List<Integer> foovals = foo.getValues();

                Toast.makeText(getApplicationContext(), "Submitting Routine", Toast.LENGTH_LONG).show();

                //Parses the stack of answered and skipped questions
                //Submits a volley call for any previously unanswered question

                while(test.isEmpty()==false){
                    NewAnswer na = test.pop();
                    int qid = na.getQuestionID();
                    int qa = na.getAnswer();
                    System.out.println(qid);
                    System.out.println(qa);

                    if(fooids.contains(qid) == false){
                        fooids.add(qid);
                        foovals.add(qa);
                    }else{
                        //continue;
                    }

                    final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = "https://www.meisr.org/api/answers/";

                    final String subid = Integer.toString(qid);
                    System.out.println("SUBID IS");
                    System.out.println(subid);
                    final String subval = Integer.toString(qa);




                    //Nested Volley Call: try answer if that fails try update

                    StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("Success!");
                            Log.d("Response", response);

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

                                    JSONObject obj = new JSONObject(res);
                                } catch (UnsupportedEncodingException e1) {
                                    // Couldn't properly decode data to string
                                    //e1.printStackTrace();
                                } catch (JSONException e2) {
                                    // returned data is not JSONObject?
                                    //e2.printStackTrace();
                                }
                            }

                            String url2 = "https://www.meisr.org/api/answers/" + subid + "/";

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
                                    params.put("question", subid);
                                    params.put("rating", subval);

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
                            params.put("question", subid);
                            params.put("rating", subval);

                            return params;
                        }

                    };

                    queue.add(postRequest);




                }
                System.out.println("Finished printing STACK");
                System.out.println(jsonArr.length());


                System.out.println("Complete FOO ANSWERS ARE HERE");
                System.out.println(fooids);
                System.out.println(foovals);



                //Make sure all questions to the age have been submitted
                //If any are not call the answer/update nested volley call again
                for(int i =0; i<jsonArr.length(); i++){
                    JSONObject temp = (JSONObject) jsonArr.get(i);
                    int startage = (int) temp.get("starting_age");
                    System.out.println("Age Compare");
                    System.out.println(agef);
                    System.out.println(startage);

                    if(startage > Integer.parseInt(agef)){
                        continue;
                    }
                    JSONObject temp2 = (JSONObject) temp.get("routine");
                    int tempmodnum = (int) temp2.get("number");

                    System.out.println("MOd compare");
                    System.out.println(modnum);
                    System.out.println(tempmodnum);


                    if(tempmodnum != modnum){
                        continue;
                    }

                    int tempid = (int) temp.get("id");

                    System.out.println("MOd compare");
                    if(fooids.contains(tempid)){
                        continue;
                    }else{
                        System.out.println("DOES NOT CONTAIN ID: ");
                        System.out.println(temp);
                        //Instantiate volley call to fix it

                        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        String url = "https://www.meisr.org/api/answers/";

                        final String subid = Integer.toString(tempid);
                        System.out.println("SUBID IS");
                        System.out.println(subid);
                        final String subval = "3";

                        fooids.add(Integer.parseInt(subid));
                        foovals.add(3);





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
                                    String url2 = "https://www.meisr.org/api/answers/" + subid + "/";

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
                                            params.put("question", subid);
                                            params.put("rating", subval);

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
                                    params.put("question", subid);
                                    params.put("rating", subval);

                                    return params;
                                }

                            };

                            queue.add(postRequest);

                        }




                }

                foo.setIds(fooids);
                foo.setValues(foovals);


                //Bring them back to module selection
                Intent next = new Intent(getApplicationContext(), ModuleSelection.class);
                next.putExtra("age",agef);
                next.putExtra("JSONARRAY", Jsonarray);
                next.putExtra("Module", mod);
                next.putExtra("Answers", (Serializable) foo);

                //myIntent.putExtra("retQues", (Serializable) returnQues);
                next.putExtra("Token", token);

                startActivity(next);
                startActivityForResult(next, 0);



            }


                     System.out.println("Got questions from step");

            app.setSurvey(returnQues);




        } catch (JSONException e) {
            System.out.println("REtrival Failed");
        }

        //Lines ~800-915 fill in radio buttons based on previous answers
        //Answers are stored in the answer class object foo

        List<Integer> fooids = foo.getIds();
        List<Integer> foovals = foo.getValues();

        System.out.println("FOO ANSWERS ARE HERE");

        for(int i = 0; i<fooids.size(); i++){
            System.out.println("id" + fooids.get(i));
            System.out.println("val" + foovals.get(i));


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



        System.out.println("Before Request");

        //ON CLICK Listener for Continue Button Lines 920-1300
        //First it retrieves the values in the radio buttons
        //Next there is an answer/update volley call for the answers
        //Next the routine is checked to see if it is complete
        //Finally the NewSurvey Class is called again to restart this screen


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


                }
                System.out.println("value4");
                System.out.println(value4);
                if(value==0 || value2 == 0 || value3 == 0 || value4 ==0){
                    Toast.makeText(getApplicationContext(), "Please Answer All Questions!", Toast.LENGTH_LONG).show();
                }else {

                    System.out.println(fint);
                    System.out.println(sint);
                    System.out.println(tint);
                    System.out.println(foint);

                    List<Integer> y = foo.getIds();
                    List<Integer> x = foo.getValues();
                    List<Integer> arval = new ArrayList<>();
                    List<Integer> arids = new ArrayList<>();
                    arval.add(value);
                    arval.add(value2);
                    arval.add(value3);
                    arval.add(value4);

                    arids.add(fint);
                    arids.add(sint);
                    arids.add(tint);
                    arids.add(foint);

                    for(int i = 0; i<arids.size(); i++){
                        if(y.contains(arids.get(i))){
                            int index = 0;
                            for(int j=0; j<y.size(); j++){
                                if(y.get(j)==arids.get(i)){
                                    index = j;
                                }
                            }
                            x.set(index, arval.get(i));

                        }else{
                            x.add(arval.get(i));
                            y.add(arids.get(i));
                        }
                    }


                    foo.setIds(y);
                    foo.setValues(x);
                    System.out.println("VALUES AND IDS BEING ADDED TO ANSWER");
                    for (int i = 0; i < x.size(); i++) {

                        System.out.println("val: " + x.get(i) + " id: " + y.get(i));
                    }

                    int[] answersarray = new int[4];
                    answersarray[0] = value;
                    answersarray[1] = value2;
                    answersarray[2] = value3;
                    answersarray[3] = value4;


                    MyApp app = (MyApp) getApplicationContext();
                    Survey returnQues = app.getSurvey();

                    System.out.println("before answerques");
                    System.out.println(answersarray[0]);
                    returnQues.answerQuestion(answersarray);
                    System.out.println("after answerques");
                    app.setSurvey(returnQues);


                    //Volley POST for the 4 answers
                    final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = "https://www.meisr.org/api/answers/";

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


                    for (int i = 0; i < 4; i++) {
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
                                String url2 = "https://www.meisr.org/api/answers/" + qids.get(count) + "/";

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


                    System.out.println("Bottomcont");
                    System.out.println(modnum);
                    String nextFunc;

                    if(modnum > 0) {
                        Boolean complete = returnQues.isModuleComplete(modnum);
                        System.out.println(complete);
                        if (complete == false) {
                            nextFunc = "Continue";
                        } else {
                            nextFunc = "Complete";

                        }
                    } else{
                        nextFunc = "Complete";
                    }

                    Intent myIntent = new Intent(view.getContext(), NewSurvey.class);
                    myIntent.putExtra("age", agef);
                    myIntent.putExtra("JSONARRAY", Jsonarray);
                    myIntent.putExtra("Module", mod);
                    myIntent.putExtra("Answers", (Serializable) foo);
                    myIntent.putExtra("nextFunc", nextFunc);
                    myIntent.putExtra("Token", token);

                    startActivity(myIntent);
                    startActivityForResult(myIntent, 0);


                }
            }
        });


        //ON CLICK Listener for Back Lines 1300-1330
        // Recalls NewSurvey with the var Func = "Back"

        final Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                MyApp app = (MyApp) getApplicationContext();
                Survey returnQues = app.getSurvey();

                Stack<NewAnswer> test = returnQues.getModuleAnswers(modnum);

                if(test.isEmpty()){
                    System.out.println("FIRST BACK");
                }else {

                    String nextFunc = "Back";
                    Intent myIntent = new Intent(view.getContext(), NewSurvey.class);
                    myIntent.putExtra("age", agef);
                    myIntent.putExtra("JSONARRAY", Jsonarray);
                    myIntent.putExtra("Module", mod);
                    myIntent.putExtra("Answers", (Serializable) foo);
                    myIntent.putExtra("nextFunc", nextFunc);

                    //myIntent.putExtra("retQues", (Serializable) returnQues);
                    myIntent.putExtra("Token", token);

                    startActivity(myIntent);
                    startActivityForResult(myIntent, 0);


                }
                //query login information from database
            }
        });






// Access the RequestQueue through your singleton class.



    }

}
