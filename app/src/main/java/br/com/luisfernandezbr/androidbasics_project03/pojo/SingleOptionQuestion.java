package br.com.luisfernandezbr.androidbasics_project03.pojo;

import java.util.List;

/**
 * Created by luis.fernandez on 2/12/17.
 */

public class SingleOptionQuestion extends Question {

    public static final String TYPE = "SINGLE_OPTION";

    private List<Answer> answerList;

    public SingleOptionQuestion(String value, List<Answer> answerList) {
        super(value, TYPE);
        this.answerList = answerList;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    @Override
    public boolean isCorrectAnswer(Answer answer) {
        return false;
    }
}
