package br.com.luisfernandezbr.androidbasics_project03.pojo;

/**
 * Created by luis.fernandez on 2/12/17.
 */

public class SingleTextQuestion extends Question {

    private Answer answer;

    public SingleTextQuestion(String value, Answer answer) {
        super(value, "SINGLE_TEXT");
        this.answer = answer;
    }

    public Answer getAnswer() {
        return answer;
    }

    @Override
    public boolean isCorrectAnswer(Answer answer) {
        return this.answer.equals(answer);
    }
}
