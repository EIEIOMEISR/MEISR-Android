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
    private int startingAge;
    private int prevOneCounter;
    private int prevThreeCounter;
    private JSONArray currentQuestions;
    private JSONArray pushBackQuestions;
    private int currentModuleId;
    private Module currentModule;
    private ArrayList<Integer> ageMilestones;
    private ArrayList<Module> modules;
    private ArrayList<NewAnswer> currentPastAnswers = null;
    private Stack<NewAnswer> retrievedPastAnswers;

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
            retrievedPastAnswers = new Stack();
            for (int i = 0; i < 100; i++) {
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
    {//Set the initial value of starting age based on the relation of enteredAge to ageMilestones
        if (enteredAge <= 12) {
            currentAge = 0;
        } else if (enteredAge > ageMilestones.get(ageMilestones.size() - 1)) {
            if(ageMilestones.get(ageMilestones.size() - 1) > 24) {
                currentAge = ageMilestones.get(ageMilestones.size() - 1) - 24;
            }
            else
            {
                currentAge = ageMilestones.get(ageMilestones.size() - 1) - 12;
            }
            while (!ageMilestones.contains(currentAge)) {
                currentAge = currentAge - 1;
            }
        } else {
            currentAge = enteredAge - 12;
            while (!ageMilestones.contains(currentAge)) {
                currentAge = currentAge - 1;
            }
        }
        startingAge = currentAge;
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
            if(modules.get(routine.getInt("number")) == null)
            {
                modules.set(routine.getInt("number"), new Module(routine.getInt("number")));
            }
            modules.get(routine.getInt("number")).addQuestion(question);
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
                NewAnswer answeredQuestion = new NewAnswer(routine.getInt("id"), answers[i], currentQ.getInt("id"), currentQ.getString("question_text"), currentQ.getInt("starting_age"));
                currentModule.answerQuestion(answeredQuestion);
                currentPastAnswers = null;
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
        currentPastAnswers = null;
        JSONArray returnQuestions = new JSONArray();
        int i = 0;
        JSONObject question = null;
        int maxAge = ageMilestones.get(ageMilestones.size() - 1);
        if(!retrievedPastAnswers.isEmpty()) {//Check to see if we are going back over questions
            try {
                for (int j = 0; i < 4; i++) {
                    question = new JSONObject();
                    NewAnswer answer = retrievedPastAnswers.pop();
                    question.put("id", answer.getQuestionID());
                    question.put("question_text", answer.getText());
                    JSONObject routine = new JSONObject();
                    routine.put("number", currentModuleId);
                    question.put("routine", routine);
                    question.put("starting_age", answer.getAge());
                    returnQuestions.put(question);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(pushBackQuestions != null)
        {//Check if we have just finished going back over questions
            returnQuestions = pushBackQuestions;
            pushBackQuestions = null;
        }
        else
        {
            while (i < 4) {//Gather questions until enteredAge is reached, the routine is empty, or 4 questions are retrieved
                if (currentModule.isEmpty()) {
                    currentQuestions = returnQuestions;
                    currentModule.markComplete();
                    return returnQuestions;
                }
                if (currentModule.peekQuestion(currentAge) == null) {
                    while (currentModule.peekQuestion(currentAge) == null && currentAge <= maxAge) {
                        currentAge++;
                    }
                    if (currentAge > maxAge) {
                        currentModule.fillThreesBelow(currentAge);
                        currentModule.markComplete();
                        currentQuestions = returnQuestions;
                        currentQuestions = returnQuestions;
                        return returnQuestions;
                    }
                    else if(currentAge > enteredAge)
                    {
                        currentModule.fillOnesAbove(currentAge, enteredAge);
                        currentModule.fillThreesBelow(startingAge);
                        currentModule.markComplete();
                        currentQuestions = returnQuestions;
                        return returnQuestions;
                    }
                    else {
                        returnQuestions.put(currentModule.getQuestion(currentAge));
                        i++;
                    }
                } else {
                    returnQuestions.put(currentModule.getQuestion(currentAge));
                    i++;
                }
            }
        }
        currentQuestions = returnQuestions;
        return returnQuestions;
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
        {//This case indicates that the user has failed out of their current age.
            //All questions from this age to the entered age will be marked as 1.
            if(currentModule.isEmpty())
            {
                currentModule.markComplete();
            }
            else if(currentIndex != 0) {
                currentAge = ageMilestones.get(currentIndex - 1);
                //Search for unanswered questions below the current age.
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
                currentModule.fillOnesAbove(currentAge, enteredAge);
            }
            else
            {
                currentModule.fillOnesAbove(currentAge, enteredAge);
                currentModule.markCanComplete(); //Might need to remove this condition
            }
            oneCounter = 0;
            prevOneCounter = 0;
            threeCounter = 0;
            prevThreeCounter = 0;
        }
        else if(threeCounter + prevThreeCounter > 4)
        {//This case indicated that the user appears to be beyond the current age
            if(currentModule.isEmpty())
            {
                currentModule.markComplete();
            }
            else if(currentIndex != ageMilestones.size() - 1) {
                currentAge = ageMilestones.get(currentIndex + 1);
                //Search for unanswered questions above the current age
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
                currentModule.fillThreesBelow(currentAge);
            }
            else
            {
                currentModule.fillThreesBelow(currentAge);
                currentModule.markCanComplete();
            }
            oneCounter = 0;
            prevOneCounter = 0;
            threeCounter = 0;
            prevThreeCounter = 0;
        }
    }

    /*
    This method returns an arraylist of NewAnswer objects representing the last set of answers submitted
    to the module.
     */
    public JSONArray getLastAnswered()
    {

        //pushBackQuestionList();
        JSONArray returnedQuestions = new JSONArray();
        if(currentPastAnswers != null)
        {
            for(int i =  0; i < currentPastAnswers.size(); i++)
            {//Retain app history if the user goes back more than once
                retrievedPastAnswers.push(currentPastAnswers.get(i));
            }
        }
        else
        {//Retain the questions displayed when the user pressed back
            pushBackQuestions = currentQuestions;
        }
        try {
            ArrayList<NewAnswer> answers = currentModule.getPreviousAnswers();
            currentPastAnswers = answers;
            if(answers == null)
            {//If no questions are answered, return an empty array
                return returnedQuestions;
            }
            else {//build JSONArray of answers
                for (int i = 0; i < answers.size(); i++) {
                    JSONObject question = new JSONObject();
                    NewAnswer answer = answers.get(i);
                    question.put("id", answer.getQuestionID());
                    question.put("question_text", answer.getText());
                    JSONObject routine = new JSONObject();
                    routine.put("number", currentModuleId);
                    question.put("routine", routine);
                    question.put("starting_age", answer.getAge());
                    returnedQuestions.put(question);
                }
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        //Revert counter.
        //Set to 0 if the user continues to go back.
        currentQuestions = returnedQuestions;
        threeCounter = prevThreeCounter;
        prevThreeCounter = 0;
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

}
