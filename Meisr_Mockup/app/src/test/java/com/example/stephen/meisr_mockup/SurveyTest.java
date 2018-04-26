package com.example.stephen.meisr_mockup;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

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
    @Test
    public void surveyAnswerRetrieveQuestions() throws Exception {
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
        int[] answers = new int[] {1, 3, 1, 1};
        testSurvey.answerQuestion(answers);
        JSONArray returnedAnswers = testSurvey.getLastAnswered();
        assertEquals(4, returnedAnswers.length());
    }
    @Test
    public void surveySkipDownQuestions() throws Exception {
        JSONArray questions = new JSONArray();
        for(int j = 0; j < 6; j++) {
            for(int k = 0; k < 60; k++) {
                for (int i = 0; i < 10; i++) {
                    JSONObject question = new JSONObject();
                    question.put("id", i + k);
                    question.put("starting_age", k);
                    question.put("question_text", "Test question!");
                    JSONObject routine = new JSONObject();
                    routine.put("id", j);
                    question.put("routine", routine);
                    questions.put(question);
                }
            }
        }
        Survey testSurvey = new Survey(36, questions);
        testSurvey.selectModule(1);
        JSONArray returnedQuestions = testSurvey.getQuestions();
        int[] answers = new int[] {1, 3, 1, 1};
        testSurvey.answerQuestion(answers);
        testSurvey.getQuestions();
        testSurvey.answerQuestion(answers);
        returnedQuestions = testSurvey.getQuestions();
        JSONArray returnedAnswers = testSurvey.getLastAnswered();
        assertEquals(23, returnedQuestions.getJSONObject(0).getInt("starting_age"));
    }
    @Test
    public void surveySkipUpQuestions() throws Exception {
        JSONArray questions = new JSONArray();
        for(int j = 0; j < 6; j++) {
            for(int k = 0; k < 60; k++) {
                for (int i = 0; i < 10; i++) {
                    JSONObject question = new JSONObject();
                    question.put("id", i + k);
                    question.put("starting_age", k);
                    question.put("question_text", "Test question!");
                    JSONObject routine = new JSONObject();
                    routine.put("id", j);
                    question.put("routine", routine);
                    questions.put(question);
                }
            }
        }
        Survey testSurvey = new Survey(36, questions);
        testSurvey.selectModule(1);
        JSONArray returnedQuestions = testSurvey.getQuestions();
        int[] answers = new int[] {1, 3, 3, 3};
        testSurvey.answerQuestion(answers);
        testSurvey.getQuestions();
        testSurvey.answerQuestion(answers);
        returnedQuestions = testSurvey.getQuestions();
        JSONArray returnedAnswers = testSurvey.getLastAnswered();
        assertEquals(25, returnedQuestions.getJSONObject(0).getInt("starting_age"));
    }
    @Test
    public void surveyTestCanComplete() throws Exception {
        JSONArray questions = new JSONArray();
        for(int j = 0; j < 6; j++) {
            for(int k = 0; k < 60; k++) {
                for (int i = 0; i < 10; i++) {
                    JSONObject question = new JSONObject();
                    question.put("id", i + k);
                    question.put("starting_age", k);
                    question.put("question_text", "Test question!");
                    JSONObject routine = new JSONObject();
                    routine.put("id", j);
                    question.put("routine", routine);
                    questions.put(question);
                }
            }
        }
        Survey testSurvey = new Survey(12, questions);
        testSurvey.selectModule(1);
        JSONArray returnedQuestions = testSurvey.getQuestions();
        int[] answers = new int[] {1, 3, 1, 1};
        testSurvey.answerQuestion(answers);
        testSurvey.getQuestions();
        testSurvey.answerQuestion(answers);
        returnedQuestions = testSurvey.getQuestions();
        JSONArray returnedAnswers = testSurvey.getLastAnswered();
        assertEquals(true, testSurvey.canModuleComplete(1));
    }
    @Test
    public void surveyTestComplete() throws Exception {
        JSONArray questions = new JSONArray();
        for (int i = 0; i < 10; i++) {
                    JSONObject question = new JSONObject();
                    question.put("id", i);
                    question.put("starting_age", 7);
                    question.put("question_text", "Test question!");
                    JSONObject routine = new JSONObject();
                    routine.put("id", 1);
                    question.put("routine", routine);
                    questions.put(question);
        }
        Survey testSurvey = new Survey(12, questions);
        testSurvey.selectModule(1);
        JSONArray returnedQuestions = testSurvey.getQuestions();
        int[] answers = new int[] {1, 3, 3, 1};
        testSurvey.answerQuestion(answers);
        testSurvey.getQuestions();
        testSurvey.answerQuestion(answers);
        testSurvey.getQuestions();
        testSurvey.answerQuestion(answers);
        testSurvey.getQuestions();
        testSurvey.answerQuestion(answers);
        testSurvey.getQuestions();
        testSurvey.answerQuestion(answers);
        testSurvey.getQuestions();
        testSurvey.answerQuestion(answers);
        testSurvey.getQuestions();
        testSurvey.answerQuestion(answers);
        testSurvey.getQuestions();
        testSurvey.answerQuestion(answers);
        testSurvey.getQuestions();
        testSurvey.answerQuestion(answers);
        testSurvey.getQuestions();
        testSurvey.answerQuestion(answers);
        assertEquals(true, testSurvey.isModuleComplete(1));
    }
}
