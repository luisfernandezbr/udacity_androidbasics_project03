package br.com.luisfernandezbr.androidbasics_project03;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.luisfernandezbr.androidbasics_project03.pojo.Answer;
import br.com.luisfernandezbr.androidbasics_project03.pojo.Question;
import br.com.luisfernandezbr.androidbasics_project03.pojo.SingleOptionQuestion;
import br.com.luisfernandezbr.androidbasics_project03.pojo.SingleTextQuestion;

public class MainActivity extends AppCompatActivity {

    private int currentQuestionsIndex = 0;

    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionList = this.loadQuestions();

        this.showNextQuestion(questionList, 0);
    }

    private void showNextQuestion(List<Question> questionList, int currentQuestionIndex) {
        Question question = questionList.get(currentQuestionIndex);

        loadLayoutContent().removeAllViews();

        switch (question.getType()) {
            case SingleTextQuestion.TYPE : {
                this.showSingleTextQuestion((SingleTextQuestion) question);
                break;
            }
            case SingleOptionQuestion.TYPE : {
                this.showSingleOptionQuestion((SingleOptionQuestion) question);
                break;
            }
        }
    }

    private void showSingleOptionQuestion(final SingleOptionQuestion question) {
        LinearLayout layout = this.inflateLayout(R.layout.comp_layout_radio_answer);
        this.setQuestionTitle(layout, question.getValue());

        final RadioGroup radioGroup = (RadioGroup) layout.findViewById(R.id.radioGroupAnswers);

        int [] radioIds = {R.id.radioButtonAnswer_01, R.id.radioButtonAnswer_02, R.id.radioButtonAnswer_03, R.id.radioButtonAnswer_04};

        List<Answer> answerList = question.getAnswerList();

        for (int i = 0; i < answerList.size(); i++) {
            RadioButton radioButton = findRadioButtonById(layout, radioIds[i]);
            Answer answer = answerList.get(i);
            radioButton.setText(answer.getValue());

            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Toast.makeText(MainActivity.this, "" + buttonView.getText(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        Button buttonConfirmAnswer = (Button) findViewById(R.id.buttonConfirmAnswer);
        buttonConfirmAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(MainActivity.this, String.format("Question: %s\nAnswer: %s", question.getValue(), editAnswer.getText().toString()), Toast.LENGTH_SHORT).show();

                showNextQuestion(questionList, currentQuestionsIndex++);
            }
        });

        this.addQuestionView(layout);
    }

    private void addQuestionView(LinearLayout layout) {
        this.loadLayoutContent().addView(layout);
    }

    private void showSingleTextQuestion(final SingleTextQuestion question) {
        LinearLayout layout = this.inflateLayout(R.layout.comp_layout_text_answer);
        this.setQuestionTitle(layout, question.getValue());

        final EditText editAnswer = (EditText) layout.findViewById(R.id.editAnswer);

        Button buttonConfirmAnswer = (Button) findViewById(R.id.buttonConfirmAnswer);
        buttonConfirmAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, String.format("QUESTION:\n %s\n\nANSWER:\n %s", question.getValue(), editAnswer.getText().toString()), Toast.LENGTH_SHORT).show();
            }
        });

        this.addQuestionView(layout);
    }

    private void setQuestionTitle(LinearLayout layout, String value) {
        TextView textQuestionValue = (TextView) layout.findViewById(R.id.textQuestionValue);
        textQuestionValue.setText(value);
    }

    private LinearLayout inflateLayout(@LayoutRes int resLayout) {
        LayoutInflater inflater = getLayoutInflater();
        return (LinearLayout) inflater.inflate(resLayout, this.loadLayoutContent(), false);
    }

    private RadioButton findRadioButtonById(LinearLayout layout, @IdRes int radioId) {
        return (RadioButton) layout.findViewById(radioId);
    }

    private ViewGroup loadLayoutContent() {
        return (ViewGroup) findViewById(R.id.layoutContent);
    }


    private List<Question> loadQuestions() {
        Question question1 = this.getSingleTextQuestion();
        Question question2 = this.getSingleOptionQuestion();

        List<Question> questionList = new ArrayList<>(4);
        questionList.add(question2);
        questionList.add(question1);

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