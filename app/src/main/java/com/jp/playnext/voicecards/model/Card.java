package com.jp.playnext.voicecards.model;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.util.Log;


import com.jp.playnext.voicecards.Utils;
import com.jp.playnext.voicecards.database.DBHelper;
import com.jp.playnext.voicecards.database.DBThemeHelper;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;

import java.lang.reflect.Array;
import java.util.LinkedList;

import static org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.*;

/**
 * Created by danielmorais on 2/8/17.
 */

public class Card implements Parcelable {

    private static final String TAG = Card.class.getSimpleName()+"123";
    private int id;
    private String sentence;
    private String title;
    private String parentDeck;
    private float bestScore;


    public Card() {

    }


    public Card(String sentence, String parentDeck) {
        this(sentence, sentence, parentDeck, -1, 0.0f);
    }

    public Card(String sentence, String title, String parentDeck, int id, float bestScore) {
        this.sentence = sentence;
        this.title = title;
        this.id = id;
        this.parentDeck = parentDeck;
    }


    /**
     * @param text
     * @return SpannableString highlighting differences
     */
    public SpannableString difference(String text) {
        return difference(text, null);
    }


    /**
     * @param text
     * @return SpannableString highlighting differences
     */
    public SpannableString difference(String text, Color color) {
        Log.v(TAG, "Expected words:" + sentence);
        Log.v(TAG, "Words received words:" + text);

        //return differenceDiffLib(text, color);
        return differenceTest(text);
    }

    private SpannableString differenceTest(String resultText) {
        String[] sentenceArray = sentence.split("\\s+");

        SpannableString spannable = new SpannableString(getDisplaySentence());
        String backgroundColor = "#FF0000";

        int start = 0;
        int end = 0;
        for (String s : sentenceArray) {
            end = end + s.length();

            if (!resultText.contains(s)) {
                spannable.setSpan(
                        new BackgroundColorSpan(Color.parseColor(backgroundColor)),
                        start,
                        end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            start = end;
        }

        return spannable;
    }

    /**
     * @param
     * @param
     * @return SpannableString highlighting differences
     */
    /*
    private SpannableString differenceDiffLib(String text, Color color) {
        DiffMatchPatch diff = new DiffMatchPatch();

        LinkedList<DiffMatchPatch.Diff> diffs = diff.diffMain(sentence.toLowerCase(), text.toLowerCase());
        diff.diffCleanupEfficiency(diffs);

        String newText = "";
        for (Diff diff1 : diffs) {
            if (diff1.operation != Operation.INSERT) //Comment to insert different text
                newText += diff1.text;
        }


        SpannableString spannable = new SpannableString(newText);
        int start = 0;

        for (Diff diff1 : diffs) {
            int end = start + diff1.text.length();

            String backgroundColor = "#00000000";
            switch (diff1.operation) {
                case INSERT: //Do nothing

                    // Uncomment to paint inserted text
//                    backgroundColor = "#FF0000";
//                    spannable.setSpan(
//                            new BackgroundColorSpan(Color.parseColor(backgroundColor)),
//                            start,
//                            end,
//                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    break;

                    continue;

                case DELETE: //Paint red
                    backgroundColor = "#FF0000";
                    spannable.setSpan(
                            new BackgroundColorSpan(Color.parseColor(backgroundColor)),
                            start,
                            end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;

                case EQUAL://Do nothing
                    break;

                default:
                    break;
            }


            start = end;
        }

        return spannable;
    }
*/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sentence);
    }

    protected Card(Parcel in) {
        this.sentence = in.readString();
    }

    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel source) {
            return new Card(source);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card))
            return false;
        Card otherCard = (Card) obj;

        return TextUtils.equals(sentence, otherCard.getSentence());

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Remove spaces if a sentence is Japanese
     *
     * @return
     */
    public String getDisplaySentence() {
        if (Utils.isJapanese(sentence))
            return sentence.replaceAll("\\s+", "");

        return sentence;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Card ID: " + id + ". Title:" + title + ". Sentence:" + sentence + ". Parent Deck:" + parentDeck;
    }

    public String getParentDeck() {
        return parentDeck;
    }

    public void setParentDeck(String parentDeck) {
        this.parentDeck = parentDeck;
    }

    public float getBestScore() {
        return bestScore;
    }

    public String getBestScoreString() {
        return String.format("%.2f", bestScore) + "%";
    }


    public void setBestScore(Context context, float bestScore) {
        if (bestScore > this.bestScore) {
            this.bestScore = bestScore;
            if (id < -1)
                DBHelper.getInstance(context).dbCardHelper.insertCard(this);
            else
                DBHelper.getInstance(context).dbCardHelper.updateCard(this);
        }
    }

}
