package br.com.luisfernandezbr.androidbasics_project03.pojo;

import android.text.TextUtils;

/**
 * Created by luis.fernandez on 2/12/17.
 */

public class SingleTextQuestion extends Question {

    public static final String TYPE = "SINGLE_TEXT";

    private Answer answer;
    private String userAnswer;

    public SingleTextQuestion(String value, Answer answer) {
        super(value, TYPE);
        this.answer = answer;
    }

    public Answer getAnswer() {
        return answer;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    @Override
    public boolean isAnswered() {
        return !TextUtils.isEmpty(userAnswer.trim());
    }

    @Override
    public boolean isCorrectAnswered() {
        return this.answer.getValue().equals(this.getUserAnswer());
    }
}
