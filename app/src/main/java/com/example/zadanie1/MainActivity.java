package com.example.zadanie1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button podpowiedzButton;
    private TextView questionTextView;
    private Question[] questions = new Question[] {
            new Question(R.string.q_frequency, true),
            new Question(R.string.q_strings, false),
            new Question(R.string.q_invented, false),
            new Question(R.string.q_tuning, true)
    };
    private int currentIndex = 0;
    private static final String KEY_CURRENT_INDEX = "current_index";
    public static final String KEY_EXTRA_ANWSER = "Zadanie2.correctAnwser";
    private static final int REQUEST_CODE_PROMPT = 0;
    private boolean anwserWasShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "Wywołanie metody cyklu życia: onCreate");
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        podpowiedzButton = findViewById(R.id.podpowiedz_button);
        questionTextView = findViewById(R.id.question_text_view);
        if(savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        podpowiedzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnwser = questions[currentIndex].isTrueAnwser();
                intent.putExtra(KEY_EXTRA_ANWSER, correctAnwser);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            }
        });
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnwserCorrectness(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnwserCorrectness(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1) % questions.length;
                anwserWasShown = false;
                setNextQuestion();
            }
        });
        setNextQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "Wywołanie metody cyklu życia: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "Wywołanie metody cyklu życia: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "Wywołanie metody cyklu życia: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "Wywołanie metody cyklu życia: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "Wywołanie metody cyklu życia: onDestroy");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MainActivity", "Wywołana została metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    private void checkAnwserCorrectness(boolean userAnwser) {
        boolean correctAnwser = questions[currentIndex].isTrueAnwser();
        int resultMessageId = 0;
        if (anwserWasShown) {
            resultMessageId = R.string.anwser_was_shown;
        }
        else {
            if(userAnwser == correctAnwser) {
                resultMessageId = R.string.correct_anwser;
            }
            else {
                resultMessageId = R.string.incorrect_anwser;
            }
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion() {
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {return;}
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) {return;}
            anwserWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANWSER_SHOWN, false);
        }
    }
}