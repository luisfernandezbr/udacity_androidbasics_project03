package br.com.luisfernandezbr.androidbasics_project03.pojo;

import java.util.List;

/**
 * Created by luis.fernandez on 2/12/17.
 */

public class SingleOptionQuestion extends Question {

    private List<Answer> answerList;

    public SingleOptionQuestion(String value, List<Answer> answerList) {
        super(value, "SINGLE_OPTION");
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
