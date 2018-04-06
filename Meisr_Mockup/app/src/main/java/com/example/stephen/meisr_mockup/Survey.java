package com.example.stephen.meisr_mockup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sabow on 3/26/2018.
 */

public class Survey {

    private int enteredAge;
    private int currentAge;
    private int oneCounter;
    private int threeCounter;
    private int prevOneCounter;
    private int prevThreeCounter;
    private JSONArray currentQuestions;
    private int currentModuleId;
    private Module currentModule;
    private ArrayList<Integer> ageMilestones;
    private ArrayList<Module> modules;

    public Survey(int age, int module, JSONArray questions)
    {
        enteredAge = age;
        oneCounter = 0;
        threeCounter = 0;
        prevOneCounter = 0;
        prevThreeCounter = 0;
        try
        {
            for(int i = 0; i < questions.length(); i++)
            {
                addQuestion(questions.getJSONObject(i));
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        Collections.sort(ageMilestones);
        if(enteredAge <= 12)
        {
            currentAge = 0;
        }
        else if(enteredAge > ageMilestones.get(ageMilestones.size() - 1))
        {
            currentAge = ageMilestones.get(ageMilestones.size() - 1) - 12;
            while(!ageMilestones.contains(currentAge))
            {
                currentAge = currentAge - 1;
            }
        }
        else
        {
            currentAge = enteredAge - 12;
            while(!ageMilestones.contains(currentAge))
            {
                currentAge = currentAge - 1;
            }
        }

    }

    public void addQuestion(JSONObject question)
    {
        try {
            if(modules.get(question.getInt("section")) == null)
            {
                modules.add(question.getInt("section"), new Module(question.getInt("section")));
            }
            modules.get(question.getInt("section")).addQuestion(question);
            if(!ageMilestones.contains(question.getInt("starting_age")))
            {
                ageMilestones.add(question.getInt("starting_age"));
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void selectModule(int mod)
    {
        currentModuleId = mod;
        for(int i = 0; i <  modules.size(); i++) {
            if(modules.get(i).getId() == mod)
            {
                currentModule = modules.get(i);
                break;
            }
        }
    }

    public void answerQuestion(int[] answers)
    {
        for(int i = 0; i < currentQuestions.length(); i++)
        {
            try {
                JSONObject currentQ = currentQuestions.getJSONObject(i);
                NewAnswer answeredQuestion = new NewAnswer(currentQ.getInt("section"), answers[i], currentQ.getString("id"), currentQ.getString("text"));
                currentModule.answerQuestion(answeredQuestion);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public JSONArray getQuestions()
    {
        JSONArray returnQuestions = new JSONArray();
        int i = 0;
        JSONObject question = currentModule.getQuestion(currentAge);
        while(i < 5 && question != null)
        {
            returnQuestions.put(question);
            i++;
            question = currentModule.getQuestion(currentAge);
        }
        currentQuestions = returnQuestions;
        return returnQuestions;
    }

    public void advanceQuestion()
    {
        Collections.sort(ageMilestones);
        int currentIndex = ageMilestones.indexOf(currentAge);
        if(oneCounter + prevOneCounter > 4)
        {
            if(currentIndex != 0)
            {
                currentAge = ageMilestones.get(currentIndex - 1);
            }
            JSONObject nextQuestion = currentModule.getQuestion(currentAge);
            while(nextQuestion == null)
            {
                currentIndex = currentIndex - 1;
                if(currentIndex >= 0) {
                    currentAge = ageMilestones.get(currentIndex);
                    nextQuestion = currentModule.getQuestion(currentAge);
                }
                else
                {
                    //Prompt to end survey here
                }
            }
        }
        else if(threeCounter + prevThreeCounter > 4)
        {
            if(currentIndex != ageMilestones.size() - 1)
            {
                currentAge = ageMilestones.get(currentIndex + 1);
            }
            JSONObject nextQuestion = currentModule.getQuestion(currentAge);
            while(nextQuestion == null)
            {
                currentIndex = currentIndex + 1;
                if(currentIndex <= ageMilestones.size() - 1) {
                    currentAge = ageMilestones.get(currentIndex);
                    nextQuestion = currentModule.getQuestion(currentAge);
                }
                else
                {
                    //Prompt to end survey here
                }
            }
        }
        else
        {
            JSONObject nextQuestion = currentModule.getQuestion(currentAge);
            if(currentAge != enteredAge) {
                while (nextQuestion == null) {
                    currentIndex = currentIndex + 1;
                    if (currentIndex <= ageMilestones.size() - 1) {
                        currentAge = ageMilestones.get(currentIndex);
                        nextQuestion = currentModule.getQuestion(currentAge);
                    } else {
                        //Prompt to end survey here
                    }
                }
            }
            else if(nextQuestion == null)
            {
                //prompt to end survey here
            }
        }
    }

    //To Do
    //Add network functions
    //Add question answering
    //Add question selection algorithm

}
