package com.example.stephen.meisr_mockup;


/**
 * Created by Stephen on 3/6/2018.
 */

public class Question {

    private int id;
    private int module;
    private int answer;
    private String questionText;
    private Question nextQuestion;
    private String[] domains;


    Question(int identity, int section, String text)
    {
        id = identity;
        module = section;
        questionText = text;
    }

    public void setNextQuestion(Question next)
    {
        this.nextQuestion = next;
    }

    public void answerQuestion(int response)
    {
        answer = response;
    }

    public int getId()
    {
        return id;
    }

    public int getModule()
    {
        return module;
    }

    public String getQuestionText()
    {
        return questionText;
    }

    public Question getNextQuestion()
    {
        return nextQuestion;
    }

    public int getAnswer()
    {
        return answer;
    }

}
