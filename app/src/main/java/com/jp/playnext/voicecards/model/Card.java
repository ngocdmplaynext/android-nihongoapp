package com.jp.playnext.voicecards.model;

import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;

import com.jp.playnext.voicecards.Utils;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.*;

/**
 * Created by danielmorais on 2/8/17.
 */

public class Card {

    private static final String TAG = Card.class.getSimpleName();
    private String sentence;


    public String getSentence() {
        return sentence;
    }


    public Card(String sentence) {
        this.sentence = sentence;
    }

    
    /**
     *
     * @param text
     * @return SpannableString highlighting differences
     */
    public SpannableString difference(String text) {
        return difference(text, null);
    }


    /**
     *
     * @param text
     * @return SpannableString highlighting differences
     */
    public SpannableString difference(String text, Color color) {
        Log.v(TAG, "Expected words:" + sentence);
        Log.v(TAG, "Words received words:" + text);

        return differenceDiffLib(text, color);
    }

    /**
     *
     * @param text
     * @param color
     * @return SpannableString highlighting differences
     */
    private SpannableString differenceDiffLib(String text, Color color) {
        DiffMatchPatch diff = new DiffMatchPatch();

        LinkedList<DiffMatchPatch.Diff> diffs = diff.diffMain(text.toLowerCase(), sentence.toLowerCase());
        diff.diffCleanupEfficiency(diffs);

        SpannableString spannable = new SpannableString(text);
        int start = 0;

        for (Diff diff1 : diffs) {
            int end = start + diff1.text.length();

            switch (diff1.operation) {
                case DELETE: //Paint red
                    spannable.setSpan(
                            new BackgroundColorSpan(Color.parseColor("#FF4081")),
                            start,
                            end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;

                case INSERT: // Don't add difference, jump to next diff
                    continue;

                case EQUAL://Do nothing
                    break;

                default:
                    break;
            }
            start = end;
        }

        return spannable;
    }

}
