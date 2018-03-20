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

    public Question(String text, int module, int question)
    {
        questionText = text;
        moduleNumber = module;
        questionNumber = question;
    }

    public void setNextQuestion(Question next)
    {
        nextQuestion = next;
    }

    public void setQuestionAnswer(int answer)
    {
        questionAnswer = answer;
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

    public Question getNextQuestion()
    {
        return nextQuestion;
    }

}
