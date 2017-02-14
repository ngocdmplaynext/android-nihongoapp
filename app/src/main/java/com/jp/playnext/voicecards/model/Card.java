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

/**
 * Created by danielmorais on 2/8/17.
 */

public class Card {

    private String sentence;

    public Card(String sentence) {
        this.sentence = sentence;
    }

    public SpannableString difference(String text) {
        return difference(text, null);
    }

    public SpannableString difference(String text, Color color) {
        Log.v("CARD", "Expected words:" + sentence);
        Log.v("CARD", "Words received words:" + text);

        //return differenceDiffLib(text, color);
        SpannableString spannable = differenceUtils(text, color);

        return spannable;
    }

    /**
     * TEST USING GOOGLE LIBRARY
     * @param text
     * @param color
     * @return
     */
    private SpannableString differenceDiffLib(String text, Color color) {
        //TODO Calculate difference betweeen sentence and text
        //TODO return difference with highlight if color not null
        List<String> different_words = Utils.difference(sentence, text);


        DiffMatchPatch diff = new DiffMatchPatch();

        LinkedList<DiffMatchPatch.Diff> diffs = diff.diffMain(text, sentence);

        diff.diffCleanupEfficiency(diffs);

        Log.v("CARD", "=========DIFF =======");

        for (DiffMatchPatch.Diff diff1 : diffs) {
            Log.v("CARD", "DIFF:" + diff1.toString());
        }

        SpannableString spannable = new SpannableString(text);

        String prettyDif = diff.diffPrettyHtml(diffs);
        Log.v("CARD", "PRETTY DIF: " + prettyDif);
        Log.v("CARD", "=========END DIFF =======");

        spannable = new SpannableString(Html.fromHtml(prettyDif));
        return spannable;
    }

    /**
     * TEST USING SIMPLE LOCAL LIBRARY
     * @param text
     * @param color
     * @return
     */
    private SpannableString differenceUtils(String text, Color color) {
        //TODO Calculate difference betweeen sentence and text
        //TODO return difference with highlight if color not null
        List<String> different_words = Utils.difference(sentence, text);
        SpannableString spannable = new SpannableString(text);


        for (String s : different_words) {
            Log.v("CARD", "Different words:" + s);

            Pattern pattern = Pattern.compile("(?i)" + s);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                spannable.setSpan(
                        new BackgroundColorSpan(Color.parseColor("#BFFFC6")),
                        matcher.start(),
                        matcher.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }


        }

        return spannable;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}
