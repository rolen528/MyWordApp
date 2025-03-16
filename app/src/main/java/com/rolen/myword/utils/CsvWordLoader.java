package com.rolen.myword.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CSV 파일을 로드하는 유틸 클래스
 */
public class CsvWordLoader {

    private static final String TAG = "CsvWordLoader";

    /**
     * 📌 assets 폴더에서 CSV 파일 읽기
     * @param context 앱 컨텍스트
     * @param fileName assets/csv/ 폴더 내 CSV 파일 이름 (예: "toeic_700_30.csv")
     * @return 단어 리스트 (영어 단어 + 뜻 리스트)
     */
    public static List<String[]> loadFromAssets(Context context, String fileName) {
        List<String[]> wordList = new ArrayList<>();

        try {
//            InputStream is = context.getAssets().open(fileName);
            InputStream is = context.getAssets().open("business_english_10.csv");  // 파일명 직접 입력!

//            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                Log.d("CsvWordLoader", "읽은 줄: " + line);  // 🚀 CSV 한 줄 출력
                String[] parts = line.split(",");

                // 🚨 배열 길이가 2 미만이면 문제 발생 가능
                if (parts.length < 2) {
                    Log.e("CsvWordLoader", "⚠️ 잘못된 데이터 형식: " + Arrays.toString(parts));
                }
            }
            reader.close();
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] words = line.split("\t");  // 🔹 탭으로 구분
//                wordList.add(words);
//            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordList;
    }


    /**
     * 📌 사용자가 직접 선택한 CSV 파일 읽기
     * @param context 앱 컨텍스트
     * @param fileUri 사용자가 선택한 파일의 URI
     * @return 단어 리스트 (영어 단어 + 뜻 리스트)
     */
    public static List<String[]> loadFromFileUri(Context context, Uri fileUri) {
        List<String[]> wordList = new ArrayList<>();

        try {
            InputStream is = context.getContentResolver().openInputStream(fileUri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\t");
                wordList.add(words);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordList;
    }

}
