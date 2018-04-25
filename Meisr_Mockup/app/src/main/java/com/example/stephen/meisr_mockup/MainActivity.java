package com.example.stephen.meisr_mockup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String token;
    String array;
    private void sharedResponse(String response){
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString("Response", response);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Intent myIntent = getIntent(); // gets the previously created intent
        System.out.println("HERE IS AGE, JSONARRAY, AND MODULE STEVEN!!!");
        token = myIntent.getStringExtra("Token");

        System.out.println("Token in MainActivity");
        System.out.println(token);

        String url ="http://skim99.pythonanywhere.com/api/questions/?format=json";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest;


        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Got response");
                        System.out.println(response);
                        try {
                            JSONArray jsonArr = new JSONArray(response);

                            //survey.setQuestions(jsonArr);
                            System.out.println("IN VOLLEY");
                            //callback.onSuccess(jsonArr);
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
        array = mResponse;

        System.out.println("SECOND VOLLEY CALL");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final Button but = findViewById(R.id.button9);
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                Intent nextScreen = new Intent(view.getContext(), Explaination.class);
                nextScreen.putExtra("Token", token);
                nextScreen.putExtra("JSONArray", array);

                startActivityForResult(nextScreen, 0);


                //query login information from database
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent nextScreen = new Intent(getApplicationContext(), Explaination.class);
            //nextScreen.putExtra("JSONARRAY", token);
            nextScreen.putExtra("Token", token);
            nextScreen.putExtra("JSONArray", array);

            startActivityForResult(nextScreen, 0);


                    //query login information from database
        } else if (id == R.id.nav_gallery) {

            Intent nextScreen = new Intent(getApplicationContext(), DisplayModule.class);
            //nextScreen.putExtra("JSONARRAY", token);
            nextScreen.putExtra("Token", token);
            nextScreen.putExtra("JSONArray", array);
            startActivityForResult(nextScreen, 0);

        } else if (id == R.id.nav_slideshow) {

                    // Code here executes on main thread after user presses button
                    Intent nextScreen = new Intent(getApplicationContext(), Mockup1.class);
                    startActivityForResult(nextScreen, 0);


                    //query login information from database

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
