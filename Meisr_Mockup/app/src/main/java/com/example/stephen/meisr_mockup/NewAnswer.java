package com.example.stephen.meisr_mockup;

/**
 * Created by sabow on 4/5/2018.
 */

public class NewAnswer {

    private int module;
    private int questionID;
    private int answer;
    private String questionText;

    public NewAnswer(int mod, int ans, int id, String text) {
        module = mod;
        answer = ans;
        questionID = id;
        questionText = text;
    }

    public int getModuleId() {
        return module;
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
