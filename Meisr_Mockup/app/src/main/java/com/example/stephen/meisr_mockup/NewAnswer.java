package com.example.stephen.meisr_mockup;

import java.io.Serializable;

/**
 * Created by sabow on 4/5/2018.
 */

public class NewAnswer implements Serializable {

    private int module;
    private int questionID;
    private int answer;
    private String questionText;
    private int startAge;

    public NewAnswer(int mod, int ans, int id, String text, int age) {
        module = mod;
        answer = ans;
        questionID = id;
        questionText = text;
        startAge = age;
    }

    public int getModuleId() {
        return module;
    }

    public int getAge()
    {
        return startAge;
    }

    public int getAnswer() {
        return answer;
    }

    public String getText()
    {
        return questionText;
    }

    public int getQuestionID()
    {
        return questionID;
    }

    public void setModuleID(int id)
    {
        module = id;
    }

    public void setQuestionID(int id)
    {
        questionID = id;
    }

    public void setAnswer(int ans)
    {
        answer = ans;
    }

    public void setQuestionText(String text)
    {
        questionText = text;
    }

}
