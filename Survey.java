package com.example.stephen.meisr_mockup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Stephen on 3/6/2018.
 */

public class Survey {

    private ArrayList<Question> questions;
    private int currentModule;
    private int currentAge;
    private int enteredAge;
    private int maxAge;
    private int threeCount;
    private int oneCount;
    private int[] markedAges; //Use this to check against allowable ages

    Survey(int module, int age)
    {
        currentAge = age;
        enteredAge = age;
        currentModule = module;
    }

    public void loadSurvey()
    {
        //connect to database here
        //Parse response
        //Set length of array of questions to the max age
        //Insert questions by age into a slot and chain them
    }

    public void answerSurveyQuestion()
    {
        Question currentQuestion = getCurrentQuestion();
        //Display survey question to UI
        //retrieve answer from submit button/radiobutton
        //call question's answerQuestion() method
        //Add current question to answered questions list for later scoring
    }

    public Question getCurrentQuestion()
    {
        Question currentQuestion = null;
        if(questions.get(currentAge) == null)
        {
            if(currentAge == enteredAge)
            {
                //prompt for continue or score
            }
            else if(currentAge < enteredAge && currentAge < maxAge)
            {
                currentAge++;
                while(currentAge < maxAge && !Arrays.asList(markedAges).contains(currentAge))
                {
                    currentAge++;
                }
                currentQuestion = getCurrentQuestion();
            }
        }
        else
        {
            currentQuestion = questions.get(currentAge);
        }
        return currentQuestion;
    }


}
