package com.example.stephen.meisr_mockup;

import java.util.ArrayList;

/**
 * Created by sabow on 3/26/2018.
 */

public class Module {

    private ArrayList<Question> questionList;
    private int id;

    public Module()
    {
        questionList =  new ArrayList<Question>();
    }

    public void addQuestion(Question question)
    {
        boolean ageIncluded = false;
        for(int i = 0; i < questionList.size(); i++)
        {
            if(questionList.get(i).getQuestionAge() == question.getQuestionAge())
            {
                question.setNextQuestion(questionList.get(i));
                questionList.add(i, question);
                ageIncluded = true;
            }
        }
        if(!ageIncluded)
        {
            questionList.add(question);
        }
    }

    public Question getQuestion(int age)
    {
        Question foundQuestion = null;
        for(int i = 0; i < questionList.size(); i++)
        {
            if(questionList.get(i).getQuestionAge() == age)
            {
                foundQuestion = questionList.get(i);
                questionList.add(i, foundQuestion.getNextQuestion());
            }
        }
        return foundQuestion;
    }

    public int getId()
    {
        return id;
    }
}
