package com.example.stephen.meisr_mockup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

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

    public Survey(int age, JSONArray questions)
    {
        enteredAge = age;
        oneCounter = 0;
        threeCounter = 0;
        prevOneCounter = 0;
        prevThreeCounter = 0;
        modules = new ArrayList<Module>();
        ageMilestones = new ArrayList<Integer>();
        ageMilestones.add(0);
        for(int i = 0; i < 30; i++)
        {
            modules.add(null);
        }
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
            JSONObject routine = question.getJSONObject("routine");
            if(modules.get(routine.getInt("id")) == null)
            {
                modules.set(routine.getInt("id"), new Module(routine.getInt("id")));
            }
            modules.get(routine.getInt("id")).addQuestion(question);
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
        for(int i = 1; i <  modules.size(); i++) {
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
                if(answers[i] == 3)
                {
                    threeCounter++;
                }
                else if(answers[i] == 1)
                {
                    oneCounter++;
                }
                JSONObject currentQ = currentQuestions.getJSONObject(i);
                JSONObject routine = currentQ.getJSONObject("routine");
                NewAnswer answeredQuestion = new NewAnswer(routine.getInt("id"), answers[i], currentQ.getInt("id"), currentQ.getString("question_text"));
                currentModule.answerQuestion(answeredQuestion);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }
        advanceQuestion();
    }

    public JSONArray getQuestions()
    {
        JSONArray returnQuestions = new JSONArray();
        int i = 0;
        JSONObject question = null;
        while(i < 4)
        {
            if(currentModule.peekQuestion(currentAge) == null && !currentModule.isEmpty())
            {
                currentAge += 1;
                while(!ageMilestones.contains(currentAge))
                {
                    currentAge = currentAge + 1;
                }
                question = currentModule.getQuestion(currentAge);
            }
            else if(currentModule.peekQuestion(currentAge) != null)
            {
                question = currentModule.getQuestion(currentAge);
            }
            else
            {
                currentQuestions = returnQuestions;
                return returnQuestions;
            }
            returnQuestions.put(question);
            i++;
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
            currentModule.fillOnesAbove(currentAge);
            if(currentIndex != 0) {
                currentAge = ageMilestones.get(currentIndex - 1);

                JSONObject nextQuestion = currentModule.peekQuestion(currentAge);
                while (nextQuestion == null) {
                    currentIndex = currentIndex - 1;
                    if (currentIndex >= 0) {
                        currentAge = ageMilestones.get(currentIndex);
                        nextQuestion = currentModule.peekQuestion(currentAge);
                    } else {
                        currentModule.markCanComplete();
                    }
                }
            }
            else
            {
                currentModule.markCanComplete();
            }
            oneCounter = 0;
            prevOneCounter = 0;
            threeCounter = 0;
            prevThreeCounter = 0;
        }
        else if(threeCounter + prevThreeCounter > 4)
        {
            currentModule.fillThreesAbove(currentAge);
            if(currentIndex != ageMilestones.size() - 1) {
                currentAge = ageMilestones.get(currentIndex + 1);

                JSONObject nextQuestion = currentModule.peekQuestion(currentAge);
                while (nextQuestion == null) {
                    currentIndex = currentIndex + 1;
                    if (currentIndex <= ageMilestones.size() - 1) {
                        currentAge = ageMilestones.get(currentIndex);
                        nextQuestion = currentModule.peekQuestion(currentAge);
                    } else {
                        currentModule.markCanComplete();
                    }
                }
            }
            else
            {
                currentModule.markCanComplete();
            }
            oneCounter = 0;
            prevOneCounter = 0;
            threeCounter = 0;
            prevThreeCounter = 0;
        }
        else
        {
            JSONObject nextQuestion = currentModule.peekQuestion(currentAge);
            if(currentAge != enteredAge) {
                while (nextQuestion == null) {
                    if(currentModule.isEmpty())
                    {
                        currentModule.markComplete();
                        break;
                    }
                    currentIndex = currentIndex + 1;
                    if (currentIndex >= 0 && currentIndex <= ageMilestones.size() - 1) {
                        currentAge = ageMilestones.get(currentIndex);
                        nextQuestion = currentModule.getQuestion(currentAge);
                    } else {
                        currentModule.markCanComplete();
                    }
                }
                prevOneCounter = oneCounter;
                oneCounter = 0;
                prevThreeCounter = threeCounter;
                threeCounter = 0;
            }
            else if(nextQuestion == null)
            {
                currentModule.markComplete();
            }
        }
    }

    public ArrayList<NewAnswer> getLastAnswered()
    {
        return currentModule.getPreviousAnswers();
    }

    public Stack<NewAnswer> getModuleAnswers(int mod)
    {
        Module thisMod = modules.get(mod);
        return thisMod.getAnswers();
    }

    public void updateAnswers(Stack<NewAnswer> updated)
    {
        while(!updated.isEmpty())
        {
            currentModule.pushAnswer(updated.pop());
        }
    }

    public void pushBackQuestionList()
    {
        try {
            for (int i = 0; i < currentQuestions.length(); i++) {
                currentModule.addQuestion(currentQuestions.getJSONObject(i));
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    public boolean isModuleComplete(int id)
    {
        return modules.get(id).isComplete();
    }

    public boolean canModuleComplete(int id)
    {
        return modules.get(id).canComplete();
    }

    //To Do
    //Add network functions
    //Add question answering
    //Add question selection algorithm

}
