package com.rolen.myword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Spinner categorySpinner, countSpinner;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // UI 요소 연결
        categorySpinner = findViewById(R.id.categorySpinner);
        countSpinner = findViewById(R.id.countSpinner);
        startButton = findViewById(R.id.startButton);

        // 시작 버튼 클릭 이벤트
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCategory = categorySpinner.getSelectedItem().toString();
                String selectedCount = countSpinner.getSelectedItem().toString();

                // 선택된 값을 다음 화면에 전달
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra("category", selectedCategory);
                intent.putExtra("count", selectedCount);
                startActivity(intent);
            }
        });


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }
}