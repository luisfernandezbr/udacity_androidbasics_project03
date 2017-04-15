package br.com.luisfernandezbr.androidbasics_project03.pojo;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by luis.fernandez on 2/12/17.
 */

public class MultipleOptionQuestion extends Question {

    public static final String TYPE = "MULTIPLE_OPTION";

    private Map<Integer, Answer> answerMap;
    private List<Integer> userAnswerList;

    public MultipleOptionQuestion(String value, Map<Integer, Answer> answerMap) {
        super(value, TYPE);
        this.answerMap = answerMap;
        this.userAnswerList = new ArrayList<>(answerMap.size());
    }

    public Map<Integer, Answer> getAnswerMap() {
        return answerMap;
    }

    public void addSelectedOption(Integer optionIndex) {
        this.userAnswerList.add(optionIndex);
    }

    public void removeSelectedOption(Integer optionIndex) {
        this.userAnswerList.remove(optionIndex);
    }

    public List<Integer> getUserAnswerList() {
        return userAnswerList;
    }

    public boolean isAnswered() {
        return userAnswerList.size() > 0;
    }
}
