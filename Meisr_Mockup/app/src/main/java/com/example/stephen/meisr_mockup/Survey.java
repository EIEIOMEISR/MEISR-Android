package com.example.stephen.meisr_mockup;

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
    private JSONObject currentQuestion;
    private int currentModuleId;
    private Module currentModule;
    private ArrayList<Integer> ageMilestones;
    private ArrayList<Module> modules;

    public Survey(int age)
    {
        enteredAge = age;
    }

    public void addQuestion(JSONObject question)
    {
        try {
            modules.get(question.getInt("module")).addQuestion(question);
            if(!ageMilestones.contains(question.getInt("age")))
            {
                ageMilestones.add(question.getInt("age"));
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

    public void answerQuestion()
    {

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
