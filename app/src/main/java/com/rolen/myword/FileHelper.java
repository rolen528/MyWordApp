package com.rolen.myword;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileHelper {

    private static final String TAG = "FileHelper";

    /**
     * assets 폴더에 있는 CSV 파일을 내부 저장소로 복사하는 메서드
     */
    public static void copyAssetsToInternalStorage(Context context, String filename) {
        AssetManager assetManager = context.getAssets();
        File outFile = new File(context.getFilesDir(), filename);

        // 이미 복사된 파일이 있다면 다시 복사하지 않음
        if (outFile.exists()) {
            Log.d(TAG, filename + " 이미 내부 저장소에 존재함.");
            return;
        }

        try (InputStream in = assetManager.open(filename);
             FileOutputStream out = new FileOutputStream(outFile)) {

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            Log.d(TAG, filename + " 복사 완료!");
        } catch (IOException e) {
            Log.e(TAG, "파일 복사 실패: " + filename, e);
        }
    }
}
