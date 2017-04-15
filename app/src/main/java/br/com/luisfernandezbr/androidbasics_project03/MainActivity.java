package br.com.luisfernandezbr.androidbasics_project03;

import android.content.Context;
import android.content.Intent;
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

    public static final String EXTRA_QUESTION_LIST = "EXTRA_QUESTION_LIST";
    public static final String EXTRA_CURRENT_INDEX = "EXTRA_CURRENT_INDEX";

    private int currentQuestionsIndex = 0;

    private ArrayList<Question> questionList;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            questionList = (ArrayList<Question>) savedInstanceState.getSerializable(EXTRA_QUESTION_LIST);
            currentQuestionsIndex = savedInstanceState.getInt(EXTRA_CURRENT_INDEX);
        } else {
            questionList = this.loadQuestions();
            currentQuestionsIndex = 0;
        }

        this.showNextQuestion(questionList, currentQuestionsIndex);
    }

    private void showNextQuestion(List<Question> questionList, int currentQuestionIndex) {
        if (currentQuestionIndex < questionList.size()) {
            Question question = questionList.get(currentQuestionIndex);

            loadLayoutContent().removeAllViews();

            switch (question.getType()) {
                case SingleTextQuestion.TYPE: {
                    this.showSingleTextQuestion((SingleTextQuestion) question);
                    break;
                }
                case SingleOptionQuestion.TYPE: {
                    this.showSingleOptionQuestion((SingleOptionQuestion) question);
                    break;
                }
                case MultipleOptionQuestion.TYPE: {
                    this.showMultipleOptionQuestion((MultipleOptionQuestion) question);
                    break;
                }
            }
        } else {
            ResultActivity.start(this, (ArrayList<Question>) questionList);
            finish();
        }
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
                String userAnswer = editAnswer.getText().toString();
                question.setUserAnswer(userAnswer);

                if (question.isAnswered()) {
                    showNextQuestion(questionList, ++currentQuestionsIndex);
                } else {
                    Toast.makeText(MainActivity.this, "Provide your answer before proceed. ; )", Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.addQuestionView(layout);
    }

    private void showSingleOptionQuestion(final SingleOptionQuestion question) {
        LinearLayout layout = this.inflateLayout(R.layout.comp_layout_radio_answer);
        this.setQuestionTitle(layout, question.getValue());

        int[] radioIds = {R.id.radioButtonAnswer_01, R.id.radioButtonAnswer_02, R.id.radioButtonAnswer_03, R.id.radioButtonAnswer_04};

        Map<Integer, Answer> answerMap = question.getAnswerMap();

        for (final Integer key : answerMap.keySet()) {
            Answer answer = answerMap.get(key);

            RadioButton radioButton = this.findRadioButtonById(layout, radioIds[key - 1]);
            radioButton.setText(answer.getValue());
            radioButton.setTag(answer);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        question.setUserAnswer(key);
                    }
                }
            });
        }

        Button buttonConfirmAnswer = (Button) findViewById(R.id.buttonConfirmAnswer);
        buttonConfirmAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (question.isAnswered()) {
                    showNextQuestion(questionList, ++currentQuestionsIndex);
                } else {
                    Toast.makeText(MainActivity.this, "Provide your answer before proceed. ; )", Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.addQuestionView(layout);
    }

    private void showMultipleOptionQuestion(final MultipleOptionQuestion question) {
        LinearLayout layout = this.inflateLayout(R.layout.comp_layout_checkbox_answer);
        this.setQuestionTitle(layout, question.getValue());

        final int[] checkboxIds = {R.id.checkboxAnswer_01, R.id.checkboxAnswer_02, R.id.checkboxAnswer_03, R.id.checkboxAnswer_04};

        Map<Integer, Answer> answerMap = question.getAnswerMap();

        for (final Integer key : answerMap.keySet()) {
            final Answer answer = answerMap.get(key);

            CheckBox checkbox = this.findCheckboxById(layout, checkboxIds[key - 1]);
            checkbox.setText(answer.getValue());
            checkbox.setTag(answer);
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        question.addSelectedOption(key);

                    } else {
                        question.removeSelectedOption(key);
                    }
                }
            });
        }

        Button buttonConfirmAnswer = (Button) findViewById(R.id.buttonConfirmAnswer);
        buttonConfirmAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (question.isAnswered()) {
                    showNextQuestion(questionList, ++currentQuestionsIndex);
                } else {
                    Toast.makeText(MainActivity.this, "Provide your answer before proceed. ; )", Toast.LENGTH_SHORT).show();
                }
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

    private ArrayList<Question> loadQuestions() {
        Question question1 = this.getSingleTextQuestion();
        Question question2 = this.getSingleOptionQuestion();
        Question question3 = this.getSingleOptionQuestionTwo();
        Question question4 = this.getMultipleOptionQuestion();

        ArrayList<Question> questionList = new ArrayList<>(4);

        questionList.add(question4);
        questionList.add(question3);
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
        Map<Integer, Answer> answerMap = new HashMap<>(4);
        answerMap.put(1, new Answer("Michael Jordan", true));
        answerMap.put(2, new Answer("Stephen Curry", false));
        answerMap.put(3, new Answer("Klay Thompson", false));
        answerMap.put(4, new Answer("Andre Iguodala", false));
        return new SingleOptionQuestion("What of the following player was not in the champion team in the 2015/2016 season?", answerMap);
    }

    private Question getSingleOptionQuestionTwo() {
        Map<Integer, Answer> answerMap = new HashMap<>(4);
        answerMap.put(1, new Answer("73/9 wins/loses ", true));
        answerMap.put(2, new Answer("69/12 wins/loses", false));
        answerMap.put(3, new Answer("79/3 wins/loses", false));
        answerMap.put(4, new Answer("56/26 wins/loses", false));
        return new SingleOptionQuestion("How many games Golden State Warriors won when broke the wins record in 2015/2016 NBA regular season?" , answerMap);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(EXTRA_QUESTION_LIST, questionList);
        outState.putInt(EXTRA_CURRENT_INDEX, currentQuestionsIndex);

        super.onSaveInstanceState(outState);
    }
}