package com.example.stephen.meisr_mockup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 3/29/2018.
 */

public class Answer implements Serializable {
    List<Integer> values =new ArrayList<>();
    List<Integer> ids =new ArrayList<>();

    public List getValues(){
        return values;
    }

    public List getIds(){
        return ids;
    }

    public void setValues(List x){
        values = x;
    }

    public void setIds(List x){
        ids = x;
    }

}
