package com.sadaqaworks.quranprojects.model;

import android.app.Activity;

public class TriviaQuestion extends Activity {
    private String surah_id;
    private String question;
    private String opta;
    private String optb;
    private String optc;
    private String optd;
    private String answer;

    public TriviaQuestion(String surah_id, String q, String oa, String ob, String oc, String od, String ans) {
        question = q;
        opta = oa;
        optb = ob;
        optc = oc;
        optd = od;
        answer = ans;
    }

    public TriviaQuestion() {
        surah_id = "";
        question = "";
        opta = "";
        optb = "";
        optc = "";
        optd = "";
        answer = "";
    }

    public String getSurah_id() {
        return surah_id;
    }

    public void setSurah_id(String surah_id) {
        this.surah_id = surah_id;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptA() {
        return opta;
    }

    public String getOptB() {
        return optb;
    }

    public String getOptC() {
        return optc;
    }

    public String getOptD() {
        return optd;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestion(String q1) {
        question = q1;
    }

    public void setOptA(String o1) {
        opta = o1;
    }

    public void setOptB(String o2) {
        optb = o2;
    }

    public void setOptC(String o3) {
        optc = o3;
    }

    public void setOptD(String o4) {
        optd = o4;
    }

    public void setAnswer(String ans) {
        answer = ans;
    }


}
