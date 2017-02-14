package com.jp.playnext.voicecards;

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
}
