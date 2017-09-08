package com.jp.playnext.voicecards.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ngocdm on 9/7/17.
 */

public class FileUtils {
    public static void cleanDocumentsDirectory(@NonNull String content, Context context) {
        File dir = context.getExternalFilesDir(null);
        for( File file: dir.listFiles() ) {
            String filePath = file.getPath();
            if (filePath.contains(content)) {
                boolean deleted = file.delete();
                if (!deleted) {
                    Toast.makeText(context, "Can not delete file!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
