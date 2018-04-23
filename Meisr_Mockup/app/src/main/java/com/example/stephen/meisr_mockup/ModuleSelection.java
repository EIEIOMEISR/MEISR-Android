package com.example.stephen.meisr_mockup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kevin on 4/2/2018.
 */

public class ModuleSelection extends AppCompatActivity{

    String token;
    JSONArray jsonarray;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_selection);

        Intent myIntent = getIntent(); // gets the previously created intent
        token = myIntent.getStringExtra("Token");


        System.out.println("HERE IS AGE AND JSONARRAY STEVEN");
        final String agef = myIntent.getStringExtra("age");
        final String Jsonarray = myIntent.getStringExtra("JSONARRAY");
        final Answer foo = (Answer) myIntent.getExtras().getSerializable("Answers");

        System.out.println(agef);
        System.out.println(Jsonarray);
        ArrayList<String> listItems = new ArrayList<String>();

        try {
        jsonarray = new JSONArray(Jsonarray);
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
                System.out.println("Before creating Survey obj");
                int ag = Integer.parseInt(agef);
                final Survey returnQues;

                    returnQues = new Survey(Integer.parseInt(agef), Jsonarray);
                    System.out.println("inbtw creating Survey obj");
                    System.out.println(itemPosition);
                    returnQues.selectModule(itemPosition+1);

                    System.out.println("After creating Survey obj");
                    Intent nextScreen = new Intent(view.getContext(), NewSurvey.class);
                    nextScreen.putExtra("age",agef);
                    nextScreen.putExtra("JSONARRAY", Jsonarray);
                    nextScreen.putExtra("Module", itemValue);
                    nextScreen.putExtra("Index", "0");
                    nextScreen.putExtra("Answers", (Serializable) foo);
                    //nextScreen.putExtra("retQues", (Serializable) returnQues);
                    nextScreen.putExtra("Token", token);

                    startActivity(nextScreen);
                    startActivityForResult(nextScreen, 0);





            }



        });
    }

}
