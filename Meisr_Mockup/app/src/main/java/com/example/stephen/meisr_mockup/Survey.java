package com.example.stephen.meisr_mockup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Created by sabow on 3/26/2018.
 */

public class Survey implements Serializable {

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

    /*
    This class is designed to store and perform actions upon module objects
    in the completion of the survey.
    To construct a Survey object, pass in the age entered in the app and a JSONArray of all of the
    questions in the survey.
    */
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
            for (int i = 0; i < 30; i++) {
                modules.add(null);
            }
            try {
                for (int i = 0; i < questions.length(); i++) {
                    addQuestion(questions.getJSONObject(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Collections.sort(ageMilestones);
            initCurrentAge();

    }

    private void initCurrentAge()
    {
        if (enteredAge <= 12) {
            currentAge = 0;
        } else if (enteredAge > ageMilestones.get(ageMilestones.size() - 1)) {
            currentAge = ageMilestones.get(ageMilestones.size() - 1) - 12;
            while (!ageMilestones.contains(currentAge)) {
                currentAge = currentAge - 1;
            }
        } else {
            currentAge = enteredAge - 24;
            while (!ageMilestones.contains(currentAge)) {
                currentAge = currentAge - 1;
            }
        }
    }


    /*
    This method takes a question in the form of a JSONObject and adds it to the appropriate module.
    If the module has not been created, the module iss created and added to the list of modules.
    If the starting age for the question is not in the age milestones, it is added to the age
    milestones.
     */
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

    public int returncurrentModuleId(){
        return currentModuleId;
    }

    /*
    This method is used to indicate which module actions are to be performed upon.
    This method should be called whenever the user selects a module.
     */
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
        initCurrentAge();
    }

    /*
    This method takes an array of integers to answer the current questions and push the answers
    to the current module.
     */
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

    /*
    This method takes no arguments and returns a JSONArray of size 1-4 representing the current
    questions to be answered. If the method returns null, there are no questions to be answered.
     */
    public JSONArray getQuestions()
    {

        JSONArray returnQuestions = new JSONArray();
        int i = 0;
        JSONObject question = null;
        int maxAge = ageMilestones.get(ageMilestones.size() - 1);
        while(i < 4)
        {
            if(currentModule.isEmpty())
            {
                currentQuestions = returnQuestions;
                currentModule.markComplete();
                return returnQuestions;
            }
            if(currentModule.peekQuestion(currentAge) == null)
            {
                while(currentModule.peekQuestion(currentAge) == null && currentAge <= maxAge)
                {
                    currentAge++;
                    if(currentAge > maxAge)
                    {
                        currentAge = 0;
                    }
                }
                if(currentAge > maxAge)
                {
                    currentModule.markComplete();
                    currentQuestions = returnQuestions;
                    return returnQuestions;
                }
                else
                {
                    returnQuestions.put(currentModule.getQuestion(currentAge));
                    i++;
                }
            }
            else
            {
                returnQuestions.put(currentModule.getQuestion(currentAge));
                i++;
            }
        }
        currentQuestions = returnQuestions;
        return returnQuestions;

        /*JSONArray returnQuestions = new JSONArray();
        int i = 0;
        JSONObject question = null;
        while(i < 4)
        {
            if(currentModule.peekQuestion(currentAge) == null && !currentModule.isEmpty())
            {
                currentAge += 1;
                while(!ageMilestones.contains(currentAge) || currentModule.peekQuestion(currentAge) == null)
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
        return returnQuestions;*/
    }

    /*
    This method checks the answer history to determine if an advancement or regression of age
    needs to be applied or if the guardian has answered all of the questions at the current age.
     */
    public void advanceQuestion()
    {
        Collections.sort(ageMilestones);
        int currentIndex = ageMilestones.indexOf(currentAge);
        if(oneCounter + prevOneCounter > 4)
        {
            currentModule.fillOnesAbove(currentAge);
            if(currentModule.isEmpty())
            {
                currentModule.markComplete();
            }
            else if(currentIndex != 0) {
                currentAge = ageMilestones.get(currentIndex - 1);

                JSONObject nextQuestion = currentModule.peekQuestion(currentAge);
                while (nextQuestion == null) {
                    currentIndex = currentIndex - 1;
                    if (currentIndex >= 0) {
                        currentAge = ageMilestones.get(currentIndex);
                        nextQuestion = currentModule.peekQuestion(currentAge);
                    } else {
                        currentModule.markComplete();
                        break;
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
            if(currentModule.isEmpty())
            {
                currentModule.markComplete();
            }
            if(currentIndex != ageMilestones.size() - 1) {
                currentAge = ageMilestones.get(currentIndex + 1);

                JSONObject nextQuestion = currentModule.peekQuestion(currentAge);
                while (nextQuestion == null) {
                    currentIndex = currentIndex + 1;
                    if (currentIndex <= ageMilestones.size() - 1) {
                        currentAge = ageMilestones.get(currentIndex);
                        nextQuestion = currentModule.peekQuestion(currentAge);
                    } else {
                        currentModule.markComplete();
                        break;
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
        /*else
        {   //In the case that neither of the skipping conditions are met, simply circle through all questions
            //until the module is empty
            JSONObject nextQuestion = currentModule.peekQuestion(currentAge);
            if(currentModule.isEmpty())
            {
                currentModule.markComplete();
            }
            else
            {
                int maxAge = ageMilestones.get(ageMilestones.size() - 1);
                int minAge = ageMilestones.get(0);
                currentIndex = ageMilestones.indexOf(currentAge);
                while(nextQuestion == null)
                {
                    currentIndex++;
                    if(currentIndex >= ageMilestones.size())
                    {
                        currentIndex = 0;
                    }
                    currentAge = ageMilestones.get(currentIndex);
                    nextQuestion = currentModule.peekQuestion(currentAge);
                }
            }
            /*if(currentAge != enteredAge) {
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
                if(currentModule.isEmpty()) {
                    currentModule.markComplete();
                }
                else
                {
                    currentModule.markCanComplete();
                }
            }
        }*/
    }

    /*
    This method returns an arraylist of NewAnswer objects representing the last set of answers submitted
    to the module.
     */
    public JSONArray getLastAnswered()
    {

            pushBackQuestionList();
            JSONArray returnedQuestions = new JSONArray();
        try {
            ArrayList<NewAnswer> answers = currentModule.getPreviousAnswers();
            if(answers == null)
            {
                return returnedQuestions;
            }
            else {
                for (int i = 0; i < answers.size(); i++) {
                    JSONObject question = new JSONObject();
                    NewAnswer answer = answers.get(i);
                    question.put("id", answer.getQuestionID());
                    question.put("question_text", answer.getText());
                    JSONObject routine = new JSONObject();
                    routine.put("id", currentModuleId);
                    question.put("routine", routine);
                    returnedQuestions.put(question);
                }
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        currentQuestions = returnedQuestions;
        return returnedQuestions;
    }

    /*
    This method returns a stack containing all of the answers submitted to a module.
     */
    public Stack<NewAnswer> getModuleAnswers(int mod)
    {
        Module thisMod = modules.get(mod);
        return thisMod.getAnswers();
    }

    /*
    This method is used to update the answers held in answer objects returned by getLastAnswered().
    This method should be called whether or not the answers have changed.
     */
    public void updateAnswers(Stack<NewAnswer> updated)
    {
        while(!updated.isEmpty())
        {
            currentModule.pushAnswer(updated.pop());
        }
    }

    /*
    This method puts questions back into the module if the user chooses to go back to previous
    questions.
     */
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

    /*
    This method returns a flag indicating if the indicated module is complete.
     */
    public boolean isModuleComplete(int id)
    {
        return modules.get(id).isComplete();
    }

    /*
    This method returns a flag indicating if the indicated module can be completed.
     */
    public boolean canModuleComplete(int id)
    {
        return modules.get(id).canComplete();
    }

    //To Do
    //Add network functions
    //Add question answering
    //Add question selection algorithm

}
