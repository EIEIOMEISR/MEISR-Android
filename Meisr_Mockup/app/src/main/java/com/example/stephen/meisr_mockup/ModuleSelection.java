package com.example.stephen.meisr_mockup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kevin on 4/2/2018.
 */

public class ModuleSelection extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_selection);

        Intent myIntent = getIntent(); // gets the previously created intent
        System.out.println("HERE IS AGE AND JSONARRAY STEVEN");
        final String agef = myIntent.getStringExtra("age");
        final String Jsonarray = myIntent.getStringExtra("JSONARRAY");

        System.out.println(agef);
        System.out.println(Jsonarray);
        ArrayList<String> listItems = new ArrayList<String>();

        try {
        JSONArray jsonarray = new JSONArray(Jsonarray);
        for(int i = 0; i< jsonarray.length(); i++){
            JSONObject obj = jsonarray.getJSONObject(i);
            int mod = (int) obj.get("routine");
            String mods = Integer.toString(mod);
            if(listItems.contains(mods)){
                //nothing
            }else{
                listItems.add(mods);
            }

        }


    }catch (JSONException e){

        }

        final ListView myListView = (ListView) findViewById(R.id.listView);


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        myListView.setAdapter(adapter);

        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) myListView.getItemAtPosition(position);


                Intent myIntent = new Intent(view.getContext(), NewSurvey.class);
                myIntent.putExtra("age",agef);
                myIntent.putExtra("JSONARRAY", Jsonarray);
                myIntent.putExtra("Module", itemValue);
                myIntent.putExtra("Index", "0");
                startActivity(myIntent);
                startActivityForResult(myIntent, 0);

            }



        });
    }

}
