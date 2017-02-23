package com.jp.playnext.voicecards;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by danielmorais on 2/8/17.
 */

public class Utils {

    public static List<String> difference(String str1, String str2){

        String[] strList1 = str1.split(" ");
        String[] strList2 = str2.split(" ");

        List<String> list1 = Arrays.asList(strList1);
        List<String> list2 = Arrays.asList(strList2);

        // Prepare a union
        List<String> union = new ArrayList<>(list1);
        union.addAll(list2);

        // Prepare an intersection
        List<String> intersection = new ArrayList<>(list1);
        intersection.retainAll(list2);

        // Subtract the intersection from the union
        union.removeAll(intersection);
        return union;
    }

    public static boolean saveAudio(InputStream in, File file){

        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String confidentToString(float confidence){
        return String.format("%.2f", confidence * 100 + "%");
    }
}
