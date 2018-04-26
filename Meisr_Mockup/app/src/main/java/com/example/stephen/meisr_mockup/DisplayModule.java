package com.example.stephen.meisr_mockup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.Map;

/**
 * Created by kevin on 4/14/2018.
 */

public class DisplayModule extends AppCompatActivity {

    String scoresArray;

    private void sharedResponse3(String response){
        SharedPreferences m3 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m3.edit();
        editor.putString("Response3", response);
        editor.commit();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_module);
        System.out.println("WIthin Display Module!");
        String url = "http://www.meisr.org/api/scores/";
        Intent myIntent = getIntent();

        final String token = myIntent.getStringExtra("Token");
        final String agef = myIntent.getStringExtra("age");
        final String Jsonarray = myIntent.getStringExtra("JSONARRAY");
        final Answer foo = (Answer) myIntent.getExtras().getSerializable("Answers");
        final SharedPreferences m3 = PreferenceManager.getDefaultSharedPreferences(this);



        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest;


        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Got response FOR SCORES JOHN41!!!");
                        System.out.println(response);
                        sharedResponse3(response);

                        try {
                            JSONArray jsonArr = new JSONArray(response);
                            //survey.setQuestions(jsonArr);
                            System.out.println("IN VOLLEY Scores");
                            System.out.print(jsonArr.length());
                            System.out.println("after len");

                            //callback.onSuccess(jsonArr);

                        } catch (JSONException e) {
                            System.out.println("REtrival Failed");
                            // Recovery
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("SCORES api didnt work!");
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
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);


        RequestQueue.RequestFinishedListener listener =
                new RequestQueue.RequestFinishedListener()
                { @Override public void onRequestFinished(Request request)
                {

                    final String mResponse = m3.getString("Response3", "");
                    System.out.println("75Outside Volley w/scores");
                    System.out.println(mResponse);




                    System.out.println(agef);
                    System.out.println(Jsonarray);
                    ArrayList<String> listItems = new ArrayList<String>();

                    try {
                        JSONArray jsonarray = new JSONArray(Jsonarray);
                        for(int i = 0; i< jsonarray.length(); i++){
                            JSONObject obj = jsonarray.getJSONObject(i);
                            JSONObject obj2 = (JSONObject) obj.get("routine");
                            String mods = (String) obj2.get("description");
                            //String mods = Integer.toString(mod);
                            if(listItems.contains(mods)){
                                //nothing
                            }else{
                                listItems.add(mods);
                            }

                        }


                    }catch (JSONException e){

                    }

                    final ListView myListView = (ListView) findViewById(R.id.listView2);


                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listItems);
                    myListView.setAdapter(adapter);

                    myListView.setAdapter(adapter);

                    myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            // ListView Clicked item index
                            int itemPosition     = position;

                            // ListView Clicked item value
                            String  itemValue    = (String) myListView.getItemAtPosition(position);
                            ArrayList<String> scorefull = new ArrayList<String>();
                            ArrayList<String> scoreage = new ArrayList<String>();
                            ArrayList<String> scoreid = new ArrayList<String>();


                            try{
                                JSONArray retScores = new JSONArray(mResponse);

                                for(int i = 0; i<retScores.length(); i++) {
                                    JSONObject temp = retScores.getJSONObject(i);
                                    JSONObject temp2 = (JSONObject) temp.get("routine");

                                    int module = Integer.parseInt(temp2.get("number").toString());
                                    if (module == (position + 1)) {
                                        scoreage.add(temp.get("score_age").toString());
                                        scorefull.add(temp.get("score_full").toString());
                                        scoreid.add(temp.get("id").toString());

                                    }
                                }

                            }catch(JSONException e){

                            }


                            System.out.println("SCORES ARRAYS");
                            System.out.println(position+1);
                            System.out.println(scorefull);
                            System.out.println(scoreage);
                            System.out.println(scoreid);


                            Intent nextScreen = new Intent(view.getContext(), DisplayGraphs.class);
                            nextScreen.putExtra("age",agef);
                            nextScreen.putExtra("JSONARRAY", Jsonarray);
                            nextScreen.putExtra("Module", itemValue);
                            nextScreen.putExtra("Index", "0");
                            nextScreen.putExtra("Answers", (Serializable) foo);
                            nextScreen.putExtra("Token", token);
                            nextScreen.putExtra("Score1", "82");
                            nextScreen.putExtra("Scorefull", scorefull);
                            nextScreen.putExtra("Scoreage", scoreage);



                            startActivity(nextScreen);
                            startActivityForResult(nextScreen, 0);

                        }



                    });



                }
                };
        queue.addRequestFinishedListener(listener);

        final Button back = findViewById(R.id.button12);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                Intent nextScreen = new Intent(view.getContext(), MainActivity.class);
                nextScreen.putExtra("Token", token);

                startActivityForResult(nextScreen, 0);


                //query login information from database
            }
        });



    }






}
