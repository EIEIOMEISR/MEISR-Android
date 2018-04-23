package com.example.stephen.meisr_mockup;

import android.app.Application;

/**
 * Created by sabow on 4/23/2018.
 */

class MyApp extends Application {
    private Survey survey;
    public Survey getSurvey(){
        return survey;
    }
    public void setSurvey(Survey surv)
    {
        survey = surv;
    }
}
