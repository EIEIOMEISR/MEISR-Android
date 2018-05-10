package com.example.stephen.meisr_mockup;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

/**
 * Created by kevin on 3/29/2018.
 * This is called by Mockup1
 * This class displays the background information of the survey
 * The class makes a volley call to http://www.meisr.org/api/questions/?format=json inorder to retrieve the questions
 * There is a side bar used for calling the following classes: Explaination, DisplayModule, and Mockup1
 * You can also prompt for more information
 * The skeleton of this class was taken from an online example
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String token;
    String array;

    //Shared Response Functions remember responses of Volley
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
        Intent myIntent = getIntent(); // gets the previously created intent
        System.out.println("HERE IS AGE, JSONARRAY, AND MODULE STEVEN!!!");
        token = myIntent.getStringExtra("Token");

        System.out.println("Token in MainActivity");
        System.out.println(token);

        String url ="https://www.meisr.org/api/questions/?format=json";
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
                            System.out.println(jsonArr.length());
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
                String temp = array;
                nextScreen.putExtra("JSONARRAY", temp);

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
            nextScreen.putExtra("JSONARRAY", array);

            startActivityForResult(nextScreen, 0);


                    //query login information from database
        } else if (id == R.id.nav_gallery) {

            Intent nextScreen = new Intent(getApplicationContext(), DisplayModule.class);
            //nextScreen.putExtra("JSONARRAY", token);
            nextScreen.putExtra("Token", token);
            nextScreen.putExtra("JSONARRAY", array);
            startActivityForResult(nextScreen, 0);

        } else if (id == R.id.nav_slideshow) {


            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
            alertDialog.setTitle("Help");
            alertDialog.setMessage( "This application lets the user fill out a MEISR survey. " +
                            "You must be connected to the internet for the application to store and score answers. " +
                    "Upon completion the answers are scored by age can be viewed under Display Results." +
                            "\n\nIf you have technical problems please visit our online page" +
                            " at meisr.org or contact kziegler@crimson.ua.edu");
            alertDialog.setButton("Continue", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // here you can add functions
                }
            });


            alertDialog.show();  //<-- See This!



            //query login information from database

        } else if (id == R.id.nav_manage) {
            System.out.println("GO LOGIN PAGE");
            // Code here executes on main thread after user presses button
            Intent nextScreen = new Intent(getApplicationContext(), Mockup1.class);
            startActivityForResult(nextScreen, 0);

        } else if (id == R.id.nav_share) {

            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
            alertDialog.setTitle("Contact Information");
            alertDialog.setMessage( "This application was developed by students at The University of Alabama upon the request of Dr. Robin McWilliam. " +
                    "For more information about this survey please visit the Evidence-based International Early Intervention Office (EIEIO). \n" +
                    "\nURL: http://eieio.ua.edu/\n" +
                    "Email: eieio@ua.edu\n" +
                    "Phone: (205)-348-6010\n" +
                    "Address:The University of Alabama\n" +
                    "Box 870232\n" +
                    "Tuscaloosa, Alabama 35487-0232");
            alertDialog.setButton("Continue", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // here you can add functions
                }

            });

            alertDialog.show();  //<-- See This!



        }
        //else if (id == R.id.nav_send) {

        //}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
