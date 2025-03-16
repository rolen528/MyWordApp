package com.rolen.myword;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private TextView tvQuestion, tvResult, tvScore, tvProgress;
    private EditText etAnswer;
    private Button btnCheck;
    private ProgressBar progressBar;
    private CountDownTimer timer;
    private int score = 0;
    private int timeLeft = 10000; // 10초 (밀리초)

    private List<String[]> wordList;
    private int currentIndex = 0;
    private boolean isEnglishQuestion = true;
    private int correctCount = 0;
    private int wrongCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvQuestion = findViewById(R.id.tvQuestion);
        tvResult = findViewById(R.id.tvResult);
        tvScore = findViewById(R.id.tvScore);
        tvProgress = findViewById(R.id.tvProgress);
        etAnswer = findViewById(R.id.etAnswer);
        btnCheck = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progressBar);

        // 🔹 전달된 단어 리스트 받기
        wordList = (List<String[]>) getIntent().getSerializableExtra("wordList");

        updateScore();
        loadNextQuestion();

        btnCheck.setOnClickListener(v -> checkAnswer());
        // 🔍 디버깅용 로그 추가
        if (wordList == null || wordList.isEmpty()) {
            Log.e("QuizActivity", "⚠️ 단어 리스트가 비어있음! 파일이 제대로 로드되지 않았을 가능성이 높음.");
        } else {
            Log.d("QuizActivity", "✅ 단어 개수: " + wordList.size());
        }

        currentIndex = 0;
        loadNextQuestion();
    }

    private void loadNextQuestion() {
        if (currentIndex >= wordList.size()) {
            tvQuestion.setText("퀴즈 종료!");
            btnCheck.setEnabled(false);
            btnCheck.setOnClickListener(null);
            return;
        }

        Random random = new Random();
        isEnglishQuestion = random.nextBoolean();

        String questionText = isEnglishQuestion ? wordList.get(currentIndex)[0] : wordList.get(currentIndex)[1];
        tvQuestion.setText(questionText);
        tvProgress.setText((currentIndex + 1) + " / " + wordList.size());
        etAnswer.getText().clear();
        etAnswer.requestFocus();
        tvResult.setVisibility(View.GONE);

        Animation enterAnimation = new TranslateAnimation(500, 0, 0, 0);
        enterAnimation.setDuration(500);
        tvQuestion.startAnimation(enterAnimation);

        startTimer();
    }

    private void startTimer() {
        if (timer != null) timer.cancel();
        progressBar.setProgress(100);
        timer = new CountDownTimer(timeLeft, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (millisUntilFinished * 100 / timeLeft);
                progressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                progressBar.setProgress(0);
                checkAnswer();
            }
        };
        timer.start();
    }

    private void checkAnswer() {
        if (timer != null) timer.cancel();

        String userInput = etAnswer.getText().toString().trim().toLowerCase();
        String correctAnswer = isEnglishQuestion ? wordList.get(currentIndex)[1] : wordList.get(currentIndex)[0];

        if (userInput.equalsIgnoreCase(correctAnswer)) {
            tvResult.setText("정답! 🎉");
            tvResult.setTextColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark));
            score += 10;
            correctCount++;
        } else {
            tvResult.setText("오답!\n정답: " + correctAnswer);
            tvResult.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light));
            score -= 5;
            wrongCount++;
        }

        updateScore();
        tvResult.setVisibility(View.VISIBLE);

        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fadeIn.setDuration(500);
        tvResult.startAnimation(fadeIn);

        tvQuestion.postDelayed(() -> {
            currentIndex++;
            if (currentIndex >= wordList.size()) {
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("correctCount", correctCount);
                intent.putExtra("wrongCount", wrongCount);
                startActivity(intent);
                finish();
            } else {
                loadNextQuestion();
            }
        }, 1000);
    }

    private void updateScore() {
        tvScore.setText("점수: " + score);
    }
}
