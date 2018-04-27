package com.example.stephen.meisr_mockup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.toolbox.StringRequest.*;

/**
 * Created by kevin on 3/10/2018.
 * This class is called by Mockup1
 * This class just gathers the information needed to create an account
 * It makes a volley post to check the information is correct String url = "http://www.meisr.org/api/rest-auth/registration/";
 * The email is sent a verification
 */

public class CreateAccount extends AppCompatActivity {

    String Errormess;
    int flag;
    CreateAccount lol;
    AlertDialog alertDialog;


    private void sharedResponse(String response){
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("CreateAccount", response);
        editor.commit();
    }
    private void sharedResponse2(String response){
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("CreateAccount2", response);
        editor.commit();
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        //This is a test edit

        System.out.println("NEW ACCOUNT INBT");

        final RequestQueue queue = Volley.newRequestQueue(this);
        final SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
         String prev1 = "";
         String prev2 = "";
         String prev3 = "";
         String prev4 = "";
         String prev5 = "";


        if( getIntent().getExtras() != null) {

            Intent myIntent = getIntent();
            prev1 = myIntent.getStringExtra("login");
            prev2 = myIntent.getStringExtra("password1");
            prev3 = myIntent.getStringExtra("password2");
            prev4 = myIntent.getStringExtra("email");
            prev5 = myIntent.getStringExtra("birth_date");

            System.out.println("LOGIN IS");
            System.out.println(prev1);
            System.out.println(prev2);
            System.out.println(prev3);
            System.out.println(prev4);
            System.out.println(prev5);
        }




        TextView editText = findViewById(R.id.editText3);
        TextView editText2 = findViewById(R.id.editText4);
        TextView editText3 = findViewById(R.id.editText6);
        TextView editText4 = findViewById(R.id.editText12);
        TextView editText5 = findViewById(R.id.editText7);

        editText.setText(prev1);
        editText2.setText(prev2);
        editText3.setText(prev3);
        editText4.setText(prev4);
        editText5.setText(prev5);



        final Button cancel = findViewById(R.id.button3);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                Intent nextScreen = new Intent(view.getContext(), Mockup1.class);
                startActivityForResult(nextScreen, 0);
            }

        });


        final Button create = findViewById(R.id.button4);
        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                System.out.println("IN CREATE CLICK LISTENER");

                // Code here executes on main thread after user presses button
                TextView editText = findViewById(R.id.editText3);
                TextView editText2 = findViewById(R.id.editText4);
                TextView editText3 = findViewById(R.id.editText6);
                TextView editText4 = findViewById(R.id.editText12);
                TextView editText5 = findViewById(R.id.editText7);


                final String login = editText.getText().toString();
                final String password = editText2.getText().toString();
                final String password2 = editText3.getText().toString();
                final String email = editText4.getText().toString();
                final String dob = editText5.getText().toString();
                System.out.println(dob);

                if (password.equals(password2)) {
                    String url = "http://www.meisr.org/api/rest-auth/registration/";


                    StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("Success!");
                            Log.d("Response", response);
                            //sharedResponse(response);
                            sharedResponse(response);





                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Failure!");
                            System.out.println(error);

                            error.printStackTrace();
                            String response = "Failure";
                            sharedResponse(response);

                            //sharedResponse(response);

                            //Log.d("Error.Response", response);

                            NetworkResponse response2 = error.networkResponse;
                            if (error instanceof ServerError && response2 != null) {
                                try {
                                    String res = new String(response2.data,
                                            HttpHeaderParser.parseCharset(response2.headers, "utf-8"));
                                    // Now you can use any deserializer to make sense of data
                                    System.out.println("ERROR RESPONSE");
                                    System.out.println(res);
                                    sharedResponse2(res);

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
                    }
                    ) {
                        @Override
                        protected Map<String, String> getParams(){
                            Map<String, String> params = new HashMap<String, String>();
                            //System.out.println("IN GET PARAMETERS CA");
                            params.put("username", login);
                            params.put("email", email);
                            params.put("password1", password);
                            params.put("password2", password2);
                            params.put("birth_date", dob);

                            return params;
                        }
                    };
                    //postRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    queue.add(postRequest);


                    RequestQueue.RequestFinishedListener listener =
                            new RequestQueue.RequestFinishedListener()
                            { @Override public void onRequestFinished(Request request)
                            {
                    final String mResponse = m.getString("CreateAccount", "");
                    final String error = m.getString("CreateAccount2", "");

                    System.out.println("DONE WITH ACCOUNT CREATION: RESPONSE");
                    System.out.println(mResponse);

                    System.out.println(mResponse.equals("Failure"));
                    ArrayList<String> ACerrors = new ArrayList<String>();
                    ArrayList<String> ACfields = new ArrayList<String>();


                                try{
                        //JSONArray ac = new JSONArray("["+error+"]");
                        //System.out.println(ac.length());


                        //JSONObject temp = (JSONObject) ac.getJSONObject(0);
                        JSONObject temp = new JSONObject(error);
                        System.out.println(temp);

                        try {


                            JSONArray user = (JSONArray) temp.get("username");
                            System.out.println(user.get(0));
                            ACerrors.add(user.get(0).toString());
                            ACfields.add("username");

                        }catch(JSONException e){

                        }
                                    try {

                        JSONArray email = (JSONArray) temp.get("email");
                        ACerrors.add(email.get(0).toString());
                        ACfields.add("email");

                                }catch(JSONException e){

                                }

                                    try {

                        JSONArray password1 = (JSONArray) temp.get("password1");
                        ACerrors.add(password1.get(0).toString());
                        ACfields.add("password1");

                            }catch(JSONException e){

                            }
                                    try {

                        JSONArray password2 = (JSONArray) temp.get("password2");
                        ACerrors.add(password2.get(0).toString());
                        ACfields.add("password2");
                            }catch(JSONException e){

                    }
                                    try {

                        JSONArray dob = (JSONArray) temp.get("birth_date");
                        ACerrors.add(dob.get(0).toString());
                        ACfields.add("birth_date");
                }catch(JSONException e){

                }


                        System.out.println(ACerrors);
                        System.out.println(ACfields);

                        Errormess = "";

                        for(int i = 0; i< ACerrors.size(); i++){
                            Errormess = Errormess + "\n" + ACfields.get(i) + ": "+ ACerrors.get(i);
                        }





                    }catch(JSONException e){
                        System.out.println("json eror");

                    }
                                System.out.println("afterjsonarray1");



                                if(mResponse.equals("Failure")){
                        System.out.println("before ac error");

                        //Toast.makeText(getApplicationContext(), error, 4).show();
                        //Toast.makeText(getApplicationContext(), "Failed to Create Account.", 4).show();

                        if(alertDialog == null) {
                            alertDialog = new AlertDialog.Builder(CreateAccount.this).create(); //Read Update
                            alertDialog.setTitle("Error");
                            System.out.print("settitle");
                            alertDialog.setMessage("Some Fields are incorrect\n" + Errormess);
                            alertDialog.setCancelable(false);
                            alertDialog.setCanceledOnTouchOutside(false);



                            alertDialog.setButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // here you can add functions

                                    Intent nextScreen2 = new Intent(getApplicationContext(), CreateAccount.class);
                                    System.out.println("IN ALRT");
                                    System.out.println(login);
                                    nextScreen2.putExtra("login", login);
                                    nextScreen2.putExtra("password1", password);
                                    nextScreen2.putExtra("password2", password2);
                                    nextScreen2.putExtra("email", email);
                                    nextScreen2.putExtra("birth_date", dob);


                                    startActivityForResult(nextScreen2, 0);

                                }
                            });
                        }

                        alertDialog.show();  //<-- See This!
                        sharedResponse("");



                    }else if(mResponse.equals("Failure") != true) {
                       // Toast.makeText(getApplicationContext(), "Account Created!", 4).show();


                                    AlertDialog alertDialog = new AlertDialog.Builder(CreateAccount.this).create(); //Read Update
                                    alertDialog.setTitle("Account Created!");
                                    alertDialog.setCancelable(false);
                                    alertDialog.setCanceledOnTouchOutside(false);

                                    alertDialog.setMessage( "A verification email has been sent to you at:\n" + email);
                                    alertDialog.setButton("Continue", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // here you can add functions
                                            Intent nextScreen = new Intent(getApplicationContext(), Mockup1.class);
                                            startActivityForResult(nextScreen, 0);

                                        }

                                    });

                                    alertDialog.show();  //<-- See This!

                    }
                    //SEND INFO TO DATABASE
                            }
                            };
                    queue.addRequestFinishedListener(listener);
                } else
                    Toast.makeText(view.getContext(), "Passwords do not match", 3).show();

            }
            });
    }
}
