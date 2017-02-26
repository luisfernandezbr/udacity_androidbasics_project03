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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.luisfernandezbr.androidbasics_project03.pojo.Answer;
import br.com.luisfernandezbr.androidbasics_project03.pojo.MultipleOptionQuestion;
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

        this.showNextQuestion(questionList, currentQuestionsIndex);
    }

    private void showNextQuestion(List<Question> questionList, int currentQuestionIndex) {

        if (currentQuestionIndex < questionList.size()) {
            Question question = questionList.get(currentQuestionIndex);

            currentQuestionsIndex++;

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
                case MultipleOptionQuestion.TYPE : {
                    this.showMultipleOptionQuestion((MultipleOptionQuestion) question);
                    break;
                }
            }
        } else {
            Toast.makeText(this, "Finalizar jogo!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showMultipleOptionQuestion(MultipleOptionQuestion question) {
        LinearLayout layout = this.inflateLayout(R.layout.comp_layout_checkbox_answer);
        this.setQuestionTitle(layout, question.getValue());

        int [] checkboxIds = {R.id.checkboxAnswer_01, R.id.checkboxAnswer_02, R.id.checkboxAnswer_03, R.id.checkboxAnswer_04};

        Map<Integer, Answer> answerMap = question.getAnswerMap();

        for (Integer key : answerMap.keySet()) {
            Answer answer = answerMap.get(key);

            CheckBox checkbox = this.findCheckboxById(layout, checkboxIds[key - 1]);
            checkbox.setText(answer.getValue());
            checkbox.setTag(answer);
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Answer answer = (Answer) buttonView.getTag();
                        Toast.makeText(MainActivity.this, String.format("ANSWER:\n%s\n%s", answer.getValue().toString(), answer.isCorrect()), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        Button buttonConfirmAnswer = (Button) findViewById(R.id.buttonConfirmAnswer);
        buttonConfirmAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(MainActivity.this, String.format("Question: %s\nAnswer: %s", question.getValue(), editAnswer.getText().toString()), Toast.LENGTH_SHORT).show();

                showNextQuestion(questionList, currentQuestionsIndex);
            }
        });

        this.addQuestionView(layout);
    }

    private void showSingleOptionQuestion(final SingleOptionQuestion question) {
        LinearLayout layout = this.inflateLayout(R.layout.comp_layout_radio_answer);
        this.setQuestionTitle(layout, question.getValue());

        final RadioGroup radioGroup = (RadioGroup) layout.findViewById(R.id.radioGroupAnswers);

        int [] radioIds = {R.id.radioButtonAnswer_01, R.id.radioButtonAnswer_02, R.id.radioButtonAnswer_03, R.id.radioButtonAnswer_04};

        Map<Integer, Answer> answerMap = question.getAnswerMap();

        for (Integer key : answerMap.keySet()) {
            Answer answer = answerMap.get(key);

            RadioButton radioButton = this.findRadioButtonById(layout, radioIds[key - 1]);
            radioButton.setText(answer.getValue());
            radioButton.setTag(answer);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Answer answer = (Answer) buttonView.getTag();
                        Toast.makeText(MainActivity.this, String.format("ANSWER:\n%s\n%s", answer.getValue().toString(), answer.isCorrect()), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        Button buttonConfirmAnswer = (Button) findViewById(R.id.buttonConfirmAnswer);
        buttonConfirmAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(MainActivity.this, String.format("Question: %s\nAnswer: %s", question.getValue(), editAnswer.getText().toString()), Toast.LENGTH_SHORT).show();

                showNextQuestion(questionList, currentQuestionsIndex);
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
                showNextQuestion(questionList, currentQuestionsIndex);
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

    private CheckBox findCheckboxById(LinearLayout layout, @IdRes int checkboxId) {
        return (CheckBox) layout.findViewById(checkboxId);
    }

    private ViewGroup loadLayoutContent() {
        return (ViewGroup) findViewById(R.id.layoutContent);
    }


    private List<Question> loadQuestions() {
        Question question1 = this.getSingleTextQuestion();
        Question question2 = this.getSingleOptionQuestion();
        Question question3 = this.getMultipleOptionQuestion();

        List<Question> questionList = new ArrayList<>(2);

        questionList.add(question3);
        questionList.add(question2);
        //questionList.add(question1);


        return questionList;
    }

    @NonNull
    private Question getSingleTextQuestion() {
        return new SingleTextQuestion("What is the name of the coach that led the team in the last Golden State Warrior's title?", new Answer("Steve Kerr", true));
    }

    @NonNull
    private Question getSingleOptionQuestion() {
        Map<Integer, Answer> answerMap = new HashMap<>(4);
        answerMap.put(1, new Answer("Michael Jordan", true));
        answerMap.put(2, new Answer("Stephen Curry", false));
        answerMap.put(3, new Answer("Klay Thompson", false));
        answerMap.put(4, new Answer("Andre Iguodala", false));
        return new SingleOptionQuestion("What of the following player was not in the champion team in the 2015/2016 season?", answerMap);
    }

    @NonNull
    private Question getMultipleOptionQuestion() {
        Map<Integer, Answer> answerMap = new HashMap<>(4);
        answerMap.put(1, new Answer("White", false));
        answerMap.put(2, new Answer("Yellow", true));
        answerMap.put(3, new Answer("Red", false));
        answerMap.put(4, new Answer("Blue", true));
        return new MultipleOptionQuestion("What of the following colors are in the Golden State Warriors logo?", answerMap);
    }
}