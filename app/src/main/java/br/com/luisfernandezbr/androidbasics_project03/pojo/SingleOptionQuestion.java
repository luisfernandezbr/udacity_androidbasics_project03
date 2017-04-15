package br.com.luisfernandezbr.androidbasics_project03.pojo;

import java.util.Map;

/**
 * Created by luis.fernandez on 2/12/17.
 */

public class SingleOptionQuestion extends Question {

    public static final String TYPE = "SINGLE_OPTION";

    private Map<Integer, Answer> answerMap;
    private Integer userAnswer = -1;

    public SingleOptionQuestion(String value, Map<Integer, Answer> answerMap) {
        super(value, TYPE);
        this.answerMap = answerMap;
    }

    public Map<Integer, Answer> getAnswerMap() {
        return answerMap;
    }

    public void setUserAnswer(Integer userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Integer getUserAnswer() {
        return userAnswer;
    }

    @Override
    public boolean isAnswered() {
        return userAnswer != -1;
    }
}
