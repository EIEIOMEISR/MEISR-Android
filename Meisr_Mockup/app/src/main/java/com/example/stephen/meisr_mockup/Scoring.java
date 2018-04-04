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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Scoring  extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoring);

        System.out.println("Here is the Json array, an array of ids, and an array of values");
        System.out.println("you need to look link the id to the id in the JSON array to figure out modules and domains");

        System.out.println("HERE IS AGE, JSONARRAY, AND MODULE Chase!!!");
        Intent myIntent = getIntent(); // gets the previously created intent

        final String agef = myIntent.getStringExtra("age");
        final String Jsonarray = myIntent.getStringExtra("JSONARRAY");
        final String mod = myIntent.getStringExtra("Module");
        final String ind = myIntent.getStringExtra("Index");
        final Answer foo = (Answer) myIntent.getExtras().getSerializable("Answers");

        List<Integer> y = foo.getIds();
        List<Integer> x = foo.getValues();

        foo.setIds(y);
        foo.setValues(x);
        System.out.println("VALUES AND IDS BEING ADDED TO ANSWER");
        for(int i = 0; i < x.size(); i++){

            System.out.println("val: "+x.get(i)+" id: "+y.get(i));
        }

        System.out.println(Jsonarray);



    }

}
