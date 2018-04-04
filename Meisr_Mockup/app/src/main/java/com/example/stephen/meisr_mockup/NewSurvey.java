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

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.provider.Telephony.Carriers.PASSWORD;


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

        int tempindex = Integer.parseInt(ind);
        int int1 = 0;
        int int2 = 0;
        int int3 = 0;
        int int4 = 0;


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
                String modstr = (String) jsonObj.get("routine");
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

// ...

// Instantiate the RequestQueue.
        //RequestQueue queue = Volley.newRequestQueue(this);
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

        final Button cont = findViewById(R.id.button5);
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
                for(int i = 0; i < x.size(); i++){

                    System.out.println("val: "+x.get(i)+" id: "+y.get(i));
                }






                String Index = Integer.toString(index);
                Intent myIntent = new Intent(view.getContext(), NewSurvey.class);
                myIntent.putExtra("age",agef);
                myIntent.putExtra("JSONARRAY", Jsonarray);
                myIntent.putExtra("Module", mod);
                myIntent.putExtra("Index",Index);
                myIntent.putExtra("Answers", (Serializable) foo);
                startActivity(myIntent);
                startActivityForResult(myIntent, 0);



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

        final Button chase = findViewById(R.id.button8);
        chase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button

                Intent myIntent = new Intent(view.getContext(), Scoring.class);
                myIntent.putExtra("age",agef);
                myIntent.putExtra("JSONARRAY", Jsonarray);
                myIntent.putExtra("Module", mod);
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
