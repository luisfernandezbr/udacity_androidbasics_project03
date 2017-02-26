package br.com.luisfernandezbr.androidbasics_project03;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.luisfernandezbr.androidbasics_project03.pojo.Answer;
import br.com.luisfernandezbr.androidbasics_project03.pojo.MultipleOptionQuestion;
import br.com.luisfernandezbr.androidbasics_project03.pojo.Question;
import br.com.luisfernandezbr.androidbasics_project03.pojo.SingleOptionQuestion;
import br.com.luisfernandezbr.androidbasics_project03.pojo.SingleTextQuestion;

public class ResultActivity extends AppCompatActivity {

    public static final String EXTRA_QUESTION_LIST = "questionList";

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

        ArrayList<Question> questionList = (ArrayList<Question>) getIntent().getSerializableExtra(EXTRA_QUESTION_LIST);

        String result = "Quiz Result!\n\n";

        for (int i = 0; i < questionList.size() ; i++) {
            Question question = questionList.get(i);

            result += String.format(
                    " >>>>>>>>>> QUESTION %d:\n" +
                    "%s\n\n", (i + 1), question.getValue());

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

        //Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        TextView textResult = (TextView) findViewById(R.id.textResult);
        textResult.setText(result);
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
}
