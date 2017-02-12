package br.com.luisfernandezbr.androidbasics_project03;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.com.luisfernandezbr.androidbasics_project03.pojo.Answer;
import br.com.luisfernandezbr.androidbasics_project03.pojo.Question;
import br.com.luisfernandezbr.androidbasics_project03.pojo.SingleOptionQuestion;
import br.com.luisfernandezbr.androidbasics_project03.pojo.SingleTextQuestion;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Question> questionList = this.loadQuestions();
    }

    private List<Question> loadQuestions() {
        Question question1 = this.getSingleTextQuestion();
        Question question2 = this.getSingleOptionQuestion();

        List<Question> questionList = new ArrayList<>(4);
        questionList.add(question1);
        questionList.add(question2);

        return questionList;
    }

    @NonNull
    private Question getSingleTextQuestion() {
        return new SingleTextQuestion("What is the name of the coach that led the team in the last Golden State Warrior's title?", new Answer("Steve Kerr", true));
    }

    @NonNull
    private Question getSingleOptionQuestion() {
        List<Answer> answerList = new ArrayList<>(4);
        answerList.add(new Answer("Michael Jordan", true));
        answerList.add(new Answer("Stephen Curry", false));
        answerList.add(new Answer("Klay Thompson", false));
        answerList.add(new Answer("Andre Iguodala", false));
        return new SingleOptionQuestion("What of the following player was not in the champion team in the 2015/2016 season?", answerList);
    }
}