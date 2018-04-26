package com.example.stephen.meisr_mockup;

import android.content.Intent;
import android.graphics.Color;
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
import java.util.List;

/**
 * Created by kevin on 4/2/2018.
 */

public class ModuleSelection extends AppCompatActivity{

    String token;
    JSONArray jsonarray;
    String wentback = "false";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_selection);

        Intent myIntent = getIntent(); // gets the previously created intent
        token = myIntent.getStringExtra("Token");


        System.out.println("IN MODULE SELECTION");
        final String agef = myIntent.getStringExtra("age");
        final String Jsonarray = myIntent.getStringExtra("JSONARRAY");
        final Answer foo = (Answer) myIntent.getExtras().getSerializable("Answers");

        wentback = myIntent.getStringExtra("flag");

        System.out.println(agef);
        System.out.println(Jsonarray);
        ArrayList<String> listItems = new ArrayList<String>();
        ArrayList<String> listItems2 = new ArrayList<String>();
        ArrayList<String> listItems3 = new ArrayList<String>();
        final ArrayList<Integer> indexlistItems2 = new ArrayList<Integer>();
        final ArrayList<Integer> indexlistItems3 = new ArrayList<Integer>();
        ArrayList<Integer> completedRoutines = new ArrayList<Integer>();
        ArrayList<Integer> questionsinRoutine = new ArrayList<Integer>();
        ArrayList<Integer> questionsinAnswer = new ArrayList<Integer>();

        ArrayList<ArrayList> containid = new ArrayList<ArrayList>();


        try {
        jsonarray = new JSONArray(Jsonarray);
        int age = Integer.parseInt(agef);
        int counter = 1;
        int flag = 0;
        int countroutines = 0;
        int totcount = 0;
        for(int i = 0; i< jsonarray.length(); i++){
            JSONObject obj = jsonarray.getJSONObject(i);
            JSONObject obj2 = (JSONObject) obj.get("routine");
            String mods = (String) obj2.get("description");
            int modid = (int) obj.get("id");
            int qage = (int) obj.get("starting_age");
            //String mods = Integer.toString(mod);
            if(qage > age){
                continue;
            }
            totcount++;
            if(listItems.contains(mods)){
                counter++;
                containid.get(countroutines).add(modid);
                //nothing
            }else{
                listItems.add(mods);
                containid.add(new ArrayList<Integer>());
                if(flag != 0) {
                    countroutines++;
                }

                containid.get(countroutines).add(modid);

                if(flag == 0){
                    flag = 1;
                    //counter++;
                }else {
                    questionsinRoutine.add(counter);
                    counter = 1;

                }
            }

        }
            questionsinRoutine.add(counter);



            System.out.println("IDS IN ROUTINES");
            System.out.println(containid.get(0));
            System.out.println(containid.get(1));
            System.out.println(containid.get(2));




            List<Integer> fooids = foo.getIds();
            List<Integer> foovals = foo.getValues();

            int inAns = 0;
            for(int i =0; i <= countroutines; i++){
                inAns = 0;
                for(int j = 0; j<fooids.size(); j++){
                    if(containid.get(i).contains(fooids.get(j))){
                        inAns++;
                        System.out.println(i);
                        System.out.println(fooids.get(j));
                    }

                }
                questionsinAnswer.add(inAns);

            }

            System.out.println("QUESTIONS IN Answer");
            System.out.println(questionsinAnswer.get(0));
            System.out.println(questionsinAnswer.get(1));
            System.out.println(questionsinAnswer.get(2));

            System.out.println("QUESTIONS IN ROUTNINES");
            System.out.println(questionsinRoutine.get(0));
            System.out.println(questionsinRoutine.get(1));
            System.out.println(questionsinRoutine.get(2));
            System.out.println(totcount);

            int flagcomplete = 0;
            for(int i =0; i <= countroutines; i++){
                if(questionsinAnswer.get(i) == questionsinRoutine.get(i)){
                    completedRoutines.add(1);
                }else{
                    completedRoutines.add(0);
                    flagcomplete = 1;
                }
            }
            int wbflag = 0;
            if(wentback ==null){

            }else{
                if(wentback.equals("true")){
                    wbflag =1;
                }
            }

            if(flagcomplete == 0 && wbflag==0){
                Intent nextScreen = new Intent(getApplicationContext(), CheckSubmit.class);
                nextScreen.putExtra("age", agef);
                nextScreen.putExtra("JSONARRAY", Jsonarray);
                //nextScreen.putExtra("Module", itemValue);
                nextScreen.putExtra("Index", "0");
                nextScreen.putExtra("Answers", (Serializable) foo);
                //nextScreen.putExtra("retQues", (Serializable) returnQues);
                nextScreen.putExtra("Token", token);
                //nextScreen.putExtra("nextFunc", nextFunc);


                startActivity(nextScreen);
                startActivityForResult(nextScreen, 0);

            }

            System.out.println("WHICH MODUELS COMPLETE");
            System.out.println(completedRoutines);

            for(int i =0; i<listItems.size(); i++){
                if(completedRoutines.get(i)==1){
                    listItems3.add(listItems.get(i));
                    indexlistItems3.add(i);
                }else{
                    listItems2.add(listItems.get(i));
                    indexlistItems2.add(i);

                }
            }
            System.out.println("WHICH MODUELS COMPLETE");
            System.out.println(listItems2);
            System.out.println(listItems3);







        }catch (JSONException e){

        }



        final ListView myListView = (ListView) findViewById(R.id.listView);
        final ListView myListView2 = (ListView) findViewById(R.id.listView3);
        //myListView.setBackgroundColor(Color.GREEN);

        List<String> lol = new ArrayList<>();
        lol.add("1");


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems2);
        //CustomListAdapter adapter = new CustomListAdapter(this , android.R.layout.simple_list_item_1 , lol);

        myListView.setAdapter(adapter);

        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems3);
        myListView2.setAdapter(adapter2);


        //myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                itemPosition = indexlistItems2.get(position);
                System.out.println("CLICKED CHILDREN");
                System.out.println(itemPosition);


                // ListView Clicked item value
                String  itemValue    = (String) myListView.getItemAtPosition(position);
                System.out.println("Before creating Survey obj");
                int ag = Integer.parseInt(agef);
                final Survey returnQues;
                try {
                    System.out.println("MS trycatch");
                    System.out.println(Jsonarray);

                    JSONArray lol = new JSONArray(Jsonarray);
                    System.out.println("created json");
                    System.out.println(ag);

                    returnQues = new Survey(ag, lol);
                    System.out.println("inbtw creating Survey obj");
                    System.out.println(itemPosition);

                    returnQues.selectModule(itemPosition + 1);

                    MyApp app = (MyApp) getApplicationContext();
                    app.setSurvey(returnQues);

                    String nextFunc = "Continue";


                    System.out.println("After creating Survey obj");
                    Intent nextScreen = new Intent(view.getContext(), NewSurvey.class);
                    nextScreen.putExtra("age", agef);
                    nextScreen.putExtra("JSONARRAY", Jsonarray);
                    nextScreen.putExtra("Module", itemValue);
                    nextScreen.putExtra("Index", "0");
                    nextScreen.putExtra("Answers", (Serializable) foo);
                    //nextScreen.putExtra("retQues", (Serializable) returnQues);
                    nextScreen.putExtra("Token", token);
                    nextScreen.putExtra("nextFunc", nextFunc);


                    startActivity(nextScreen);
                    startActivityForResult(nextScreen, 0);

                }catch(JSONException e){

                }



            }



        });

        myListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;
                itemPosition = indexlistItems3.get(position);

                System.out.println("CLICKED CHILDREN");
                System.out.println(myListView2.getChildCount());


                // ListView Clicked item value
                String  itemValue    = (String) myListView2.getItemAtPosition(position);
                System.out.println("Before creating Survey obj");
                int ag = Integer.parseInt(agef);
                final Survey returnQues;
                try {
                    JSONArray lol = new JSONArray(Jsonarray);
                    returnQues = new Survey(Integer.parseInt(agef), lol);
                    System.out.println("inbtw creating Survey obj");
                    System.out.println(itemPosition);
                    returnQues.selectModule(itemPosition + 1);

                    MyApp app = (MyApp) getApplicationContext();
                    app.setSurvey(returnQues);

                    String nextFunc = "Continue";


                    System.out.println("After creating Survey obj");
                    Intent nextScreen = new Intent(view.getContext(), NewSurvey.class);
                    nextScreen.putExtra("age", agef);
                    nextScreen.putExtra("JSONARRAY", Jsonarray);
                    nextScreen.putExtra("Module", itemValue);
                    nextScreen.putExtra("Index", "0");
                    nextScreen.putExtra("Answers", (Serializable) foo);
                    //nextScreen.putExtra("retQues", (Serializable) returnQues);
                    nextScreen.putExtra("Token", token);
                    nextScreen.putExtra("nextFunc", nextFunc);


                    startActivity(nextScreen);
                    startActivityForResult(nextScreen, 0);

                }catch(JSONException e){

                }



            }



        });
    }

}
