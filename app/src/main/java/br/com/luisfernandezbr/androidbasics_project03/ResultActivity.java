package br.com.luisfernandezbr.androidbasics_project03;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import br.com.luisfernandezbr.androidbasics_project03.pojo.Answer;
import br.com.luisfernandezbr.androidbasics_project03.pojo.MultipleOptionQuestion;
import br.com.luisfernandezbr.androidbasics_project03.pojo.Question;
import br.com.luisfernandezbr.androidbasics_project03.pojo.SingleOptionQuestion;
import br.com.luisfernandezbr.androidbasics_project03.pojo.SingleTextQuestion;

public class ResultActivity extends AppCompatActivity {

    public static final String EXTRA_QUESTION_LIST = "questionList";

    private TextView textResult;

    private int numberOfQuestions;
    private int numberOfCorrectQuestions;
    private ArrayList<Question> questionList;

    public static void start(Context context, ArrayList<Question> questionList) {
        Intent starter = new Intent(context, ResultActivity.class);
        starter.putExtra(EXTRA_QUESTION_LIST, questionList);
        context.startActivity(starter);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        this.questionList = (ArrayList<Question>) getIntent().getSerializableExtra(EXTRA_QUESTION_LIST);

        this.textResult = (TextView) findViewById(R.id.textResult);

        this.previewResult();
    }

    private void previewResult() {
        String result = "Questions explanation:\n\n";

        this.numberOfQuestions = questionList.size();

        for (int i = 0; i < questionList.size() ; i++) {
            Question question = questionList.get(i);

            if (question.isCorrectAnswered()) {
                numberOfCorrectQuestions++;
            }

            result += this.getQuestionResultTitle(i, question);

            switch (question.getType()) {
                case SingleTextQuestion.TYPE : {
                    result += this.getSingleTextResult((SingleTextQuestion) question);
                    break;
                }
                case SingleOptionQuestion.TYPE : {
                    result += this.getSingleOptionResult((SingleOptionQuestion) question);
                    break;
                }
                case MultipleOptionQuestion.TYPE : {
                    result += this.getMultipleOptionsResult((MultipleOptionQuestion) question);
                    break;
                }
            }
        }

        textResult.setText(result);
    }

    private String getQuestionResultTitle(int questionIndex, Question question) {
        return String.format(Locale.getDefault(),
                ">>>> QUESTION %d <<<<:\n" +
                        "%s\n\n", (questionIndex + 1), question.getValue());
    }

    private void showScore() {
        String scoreMessage = this.getScoreMessage(numberOfQuestions, numberOfCorrectQuestions);
        this.showToast(scoreMessage);
        this.textResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        this.textResult.setText(scoreMessage);
    }

    private String getScoreMessage(double numberOfQuestions, double numberOfCorrectQuestions) {
        double percentage = (numberOfCorrectQuestions / numberOfQuestions) * 100;

        return String.format(Locale.getDefault(), "YOUR SCORE IS %d of %d\n%d percent of correctness",
                (int)numberOfCorrectQuestions,
                (int)numberOfQuestions,
                (int)percentage);
    }

    private void showToast(String scoreMessage) {
        Toast.makeText(this, scoreMessage, Toast.LENGTH_LONG).show();
    }

    String message =
            " CORRECT ANSWER:\n " +
            "     %s\n\n" +
            " YOUR ANSWER:\n" +
            "     %s\n\n\n";

    private String getSingleTextResult(SingleTextQuestion question) {
        return String.format(message, question.getAnswer().getValue(), question.getUserAnswer());
    }

    private String getSingleOptionResult(SingleOptionQuestion question) {
        Map<Integer, Answer> answerMap = question.getAnswerMap();

        String correctAnswer = "";
        String userAnswer = answerMap.get(question.getUserAnswer()).getValue();

        for (Integer key : answerMap.keySet()) {
            Answer answer = answerMap.get(key);

            if (answer.isCorrect()) {
                correctAnswer = answer.getValue();
                break;
            }
        }

        return String.format(message, correctAnswer, userAnswer);
    }

    private String getMultipleOptionsResult(MultipleOptionQuestion question) {
        String correctAnswer = "";
        String userAnswer = "";

        Map<Integer, Answer> answerMap = question.getAnswerMap();


        for (Integer key : question.getUserAnswerList()) {
            userAnswer += answerMap.get(key).getValue() + ", ";
        }

        for (Integer key : answerMap.keySet()) {
            Answer answer = answerMap.get(key);

            if (answer.isCorrect()) {
                correctAnswer += answer.getValue() + ", ";
            }
        }

        return String.format(message, correctAnswer, userAnswer);
    }

    public void onClickPlayAgain(View view) {
        MainActivity.start(this);
        finish();
    }

    public void onClickShowResult(View view) {
        this.showScore();

        view.setVisibility(View.GONE);
        findViewById(R.id.buttonPlayAgain).setVisibility(View.VISIBLE);
    }
}
