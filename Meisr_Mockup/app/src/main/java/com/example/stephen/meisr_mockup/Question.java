package com.example.stephen.meisr_mockup;

/**
 * Created by sabow on 3/20/2018.
 */

public class Question {

    private String questionText;
    private Question nextQuestion;
    private int moduleNumber;
    private int questionNumber;
    private int questionAnswer;
    private int questionAge;

    public Question(String text, int module, int question, int age)
    {
        questionText = text;
        moduleNumber = module;
        questionNumber = question;
        questionAge = age;
    }

    public void setNextQuestion(Question next)
    {
        nextQuestion = next;
    }

    public void setQuestionAnswer(int answer)
    {
        questionAnswer = answer;
    }

    public void setQuestionAge(int age)
    {
        questionAge = age;
    }

    public String getQuestionText()
    {
        return questionText;
    }

    public int getQuestionModule()
    {
        return moduleNumber;
    }

    public int getQuestionAnswer()
    {
        return questionAnswer;
    }

    public int getQuestionNumber()
    {
        return questionNumber;
    }

    public int getQuestionAge()
    {
        return questionAge;
    }

    public Question getNextQuestion()
    {
        return nextQuestion;
    }

}
