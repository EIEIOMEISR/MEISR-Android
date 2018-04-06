package com.example.stephen.meisr_mockup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by sabow on 3/26/2018.
 */

public class Module {

    private ArrayList<JSONObject> questionList;
    private ArrayList<JSONObject> answeredList;
    private int id;
    private boolean complete = false;
    private Stack<NewAnswer> answers = new Stack();

    public Module(int idEntered)
    {
        id = idEntered;
        questionList =  new ArrayList<JSONObject>();
        answeredList = new ArrayList<JSONObject>();
    }

    public void addQuestion(JSONObject question)
    {
        questionList.add(question);
    }

    public void answerQuestion(NewAnswer answer)
    {
        answers.push(answer);
    }

    public Stack<NewAnswer> getAnswers()
    {
        return answers;
    }

    public ArrayList<NewAnswer> getPreviousAnswers()
    {
        ArrayList<NewAnswer> answerList = new ArrayList();
        int count = 0;
        if(answers.isEmpty())
        {
            return null;
        }
        while(!answers.isEmpty() && count < 4)
        {
            answerList.add(count, answers.pop());
            count++;
        }
        return answerList;
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
