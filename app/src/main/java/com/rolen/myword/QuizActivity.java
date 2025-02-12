package com.rolen.myword;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {
    private TextView quizTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizTextView = findViewById(R.id.quizTextView);

        // 메인 화면에서 받은 데이터 표시
        String category = getIntent().getStringExtra("category");
        String count = getIntent().getStringExtra("count");

        quizTextView.setText("선택한 카테고리: " + category + "\n단어 개수: " + count);
    }
}
