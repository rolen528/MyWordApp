package com.rolen.myword;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 이전 화면에서 점수 및 정답 개수 받아오기
        int score = getIntent().getIntExtra("score", 0);
        int correctCount = getIntent().getIntExtra("correctCount", 0);
        int wrongCount = getIntent().getIntExtra("wrongCount", 0);

        // UI 요소 설정
        TextView tvFinalScore = findViewById(R.id.tvFinalScore);
        TextView tvCorrectCount = findViewById(R.id.tvCorrectCount);
        TextView tvWrongCount = findViewById(R.id.tvWrongCount);
        Button btnRestart = findViewById(R.id.btnRestart);
        Button btnHome = findViewById(R.id.btnHome);

        tvFinalScore.setText("최종 점수: " + score);
        tvCorrectCount.setText("정답 개수: " + correctCount);
        tvWrongCount.setText("오답 개수: " + wrongCount);

        // 다시 시작 버튼
        btnRestart.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
            startActivity(intent);
            finish();
        });

        // 홈으로 가기 버튼
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
