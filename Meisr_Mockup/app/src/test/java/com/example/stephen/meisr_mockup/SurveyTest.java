package com.example.stephen.meisr_mockup;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sabow on 4/16/2018.
 */

public class SurveyTest {
    @Test
    public void surveyQuestionsCorrect() throws Exception {
        JSONArray questions = new JSONArray();
        for(int j = 0; j < 6; j++) {
            for (int i = 0; i < 5; i++) {
                JSONObject question = new JSONObject();
                question.put("id", i);
                question.put("starting_age", 7);
                question.put("question_text", "Test question!");
                JSONObject routine = new JSONObject();
                routine.put("id", j);
                question.put("routine", routine);
                questions.put(question);
            }
        }
        Survey testSurvey = new Survey(7, questions);
        testSurvey.selectModule(1);
        JSONArray returnedQuestions = testSurvey.getQuestions();
        assertEquals(4, returnedQuestions.length());
    }
    @Test
    public void surveyQuestionsSecondRetrieveCorrect() throws Exception {
        JSONArray questions = new JSONArray();
        for(int j = 0; j < 6; j++) {
            for (int i = 0; i < 5; i++) {
                JSONObject question = new JSONObject();
                question.put("id", i);
                question.put("starting_age", 7);
                question.put("question_text", "Test question!");
                JSONObject routine = new JSONObject();
                routine.put("id", j);
                question.put("routine", routine);
                questions.put(question);
            }
        }
        Survey testSurvey = new Survey(7, questions);
        testSurvey.selectModule(1);
        JSONArray returnedQuestions = testSurvey.getQuestions();
        JSONArray secondReturnedQuestions = testSurvey.getQuestions();
        assertEquals(1, secondReturnedQuestions.length());
    }

}
