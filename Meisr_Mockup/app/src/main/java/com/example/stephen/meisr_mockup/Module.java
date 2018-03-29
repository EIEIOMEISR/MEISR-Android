package com.example.stephen.meisr_mockup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sabow on 3/26/2018.
 */

public class Module {

    private ArrayList<JSONObject> questionList;
    private ArrayList<JSONObject> answeredList;
    private int id;

    public Module()
    {
        questionList =  new ArrayList<JSONObject>();
        answeredList = new ArrayList<JSONObject>();
    }

    public void addQuestion(JSONObject question)
    {
        questionList.add(question);
    }

    public JSONObject getQuestion(int age)
    {

        JSONObject question = null;
        try {
            for (int i = 0; i < questionList.size(); i++) {
                if (questionList.get(i).getInt("starting_age") == age) {
                    question = questionList.get(i);
                    questionList.remove(i);
                    break;
                }
            }

        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return question;
    }

    public int getId()
    {
        return id;
    }
}
