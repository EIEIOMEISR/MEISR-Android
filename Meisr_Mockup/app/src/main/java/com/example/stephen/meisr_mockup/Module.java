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
    private boolean canBeCompleted = false;
    private boolean empty = false;
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
                    if(questionList.size() == 0)
                    {
                        empty = true;
                    }
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

    public JSONObject peekQuestion(int age)
    {
        JSONObject question = null;
        try {
            for (int i = 0; i < questionList.size(); i++) {
                if (questionList.get(i).getInt("starting_age") == age) {
                    question = questionList.get(i);
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

    public void pushAnswer(NewAnswer answer)
    {
        answers.push(answer);
    }

    public boolean isComplete()
    {
        return complete;
    }

    public boolean isEmpty()
    {
        return empty;
    }

    public boolean canComplete()
    {
        return canBeCompleted;
    }

    public void markComplete()
    {
        complete = true;
    }

    public void markCanComplete()
    {
        canBeCompleted = true;
    }

    public void fillOnesAbove(int age)
    {
        try {
            for (int i = 0; i < questionList.size(); i++) {
                if (questionList.get(i).getInt("starting_age") > age) {
                    NewAnswer answer = new NewAnswer(id, 1, questionList.get(i).getInt("id"), questionList.get(i).getString("question_text"));
                    questionList.remove(i);
                    answerQuestion(answer);
                }
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void fillThreesAbove(int age)
    {
            try {
                for (int i = 0; i < questionList.size(); i++) {
                    if (questionList.get(i).getInt("starting_age") < age) {
                        NewAnswer answer = new NewAnswer(id, 3, questionList.get(i).getInt("id"), questionList.get(i).getString("question_text"));
                        questionList.remove(i);
                        answerQuestion(answer);
                    }
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }
    public void fillInRemainingQuestions()
    {
        try {
            for (int i = 0; i < questionList.size(); i++) {
                    NewAnswer answer = new NewAnswer(id, 0, questionList.get(i).getInt("id"), questionList.get(i).getString("question_text"));
                    questionList.remove(i);
                    answerQuestion(answer);
                    complete = true;
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
    
}

