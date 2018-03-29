package com.example.stephen.meisr_mockup;

import java.util.ArrayList;

/**
 * Created by sabow on 3/26/2018.
 */

public class Survey {

    private int enteredAge;
    private int currentAge;
    private ArrayList<Integer> ageMilestones;
    private ArrayList<Module> modules;

    public Survey(int age)
    {
        enteredAge = age;
    }

    public void addQuestion(Question question)
    {
        modules.get(question.getQuestionModule()).addQuestion(question);
    }

    //To Do
    //Add network functions
    //Add question answering
    //Add question selection algorithm

}
