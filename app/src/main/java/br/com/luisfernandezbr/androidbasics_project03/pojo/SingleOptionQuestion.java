package br.com.luisfernandezbr.androidbasics_project03.pojo;

import java.util.Map;

/**
 * Created by luis.fernandez on 2/12/17.
 */

public class SingleOptionQuestion extends Question {

    public static final String TYPE = "SINGLE_OPTION";

    private Map<Integer, Answer> answerMap;
    private Integer userAnswer;

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

    public boolean isCorrectAnswer() {
        return answerMap.get(userAnswer).isCorrect();
    }

}
