package com.rolen.myword;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.rolen.myword.utils.CsvWordLoader;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Spinner categorySpinner, countSpinner;
    private Button startButton, fileButton;
    private ActivityResultLauncher<Intent> filePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 연결
        categorySpinner = findViewById(R.id.categorySpinner);
        countSpinner = findViewById(R.id.countSpinner);
        startButton = findViewById(R.id.startButton);
        fileButton = findViewById(R.id.fileButton);

        // 파일 선택 런처 초기화
        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri fileUri = result.getData().getData();
                        if (fileUri != null) {
                            startQuizWithFile(fileUri);
                        }
                    }
                }
        );

        // 시작 버튼 클릭 이벤트 (카테고리 선택)
        startButton.setOnClickListener(v -> {
            String selectedCategory = categorySpinner.getSelectedItem().toString();
            String selectedCount = countSpinner.getSelectedItem().toString();
            String filePath = getFilesDir().getAbsolutePath() + "/" + selectedCategory + ".csv";

            startQuizWithCategory(filePath, selectedCount);
        });

        // 파일 선택 버튼 클릭 이벤트
        fileButton.setOnClickListener(v -> openFilePicker());
    }

    /**
     * 📌 파일 선택기 열기
     */
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/csv");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        filePickerLauncher.launch(Intent.createChooser(intent, "CSV 파일 선택"));
    }

    /**
     * 📌 카테고리 선택 후 퀴즈 시작 (assets에서 로드)
     */
    private void startQuizWithCategory(String filePath, String count) {
        List<String[]> wordList = CsvWordLoader.loadFromAssets(this, filePath);

        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra("wordList", (ArrayList<String[]>) wordList);
        intent.putExtra("count", count);
        startActivity(intent);
    }

    /**
     * 📌 파일 선택 후 퀴즈 시작 (사용자 파일 로드)
     */
    private void startQuizWithFile(Uri fileUri) {
        List<String[]> wordList = CsvWordLoader.loadFromFileUri(this, fileUri);
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra("wordList", (ArrayList<String[]>) wordList);
        startActivity(intent);
    }
}
