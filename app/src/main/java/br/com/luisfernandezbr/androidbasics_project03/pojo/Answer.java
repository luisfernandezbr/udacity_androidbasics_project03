package br.com.luisfernandezbr.androidbasics_project03.pojo;

/**
 * Created by luis.fernandez on 2/12/17.
 */

public class Answer {

    private String value;
    private boolean isCorrect;

    public Answer(String value, boolean isCorrect) {
        this.value = value;
        this.isCorrect = isCorrect;
    }

    public String getValue() {
        return value;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;

        Answer answer = (Answer) o;

        return value != null ? value.equals(answer.value) : answer.value == null;

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
