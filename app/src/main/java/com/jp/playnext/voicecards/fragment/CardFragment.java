package com.jp.playnext.voicecards.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.model.Card;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment {

    private static final String CARD_KEY = "CARD_KEY";

    Card card;

    TextView tvCard;
    TextView tvExpectedSentence;
    TextView tvReceivedSentence;
    TextView tvPercentage;

    //DEBUG
    TextView tvTextResult;
    TextView tvDisplayText;

    //private OnFragmentInteractionListener mListener;


    //================= DEFAULT METHODS=============================================================

    public CardFragment() {
        // Required empty public constructor
    }

    /**
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardFragment newInstance(Card card) {
        CardFragment cardFragment = new CardFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putParcelable(CARD_KEY, card);
        cardFragment.setArguments(args);
        return cardFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            card = getArguments().getParcelable(CARD_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_card, container, false);

        tvCard = (TextView) rootView.findViewById(R.id.tv_card);
        tvExpectedSentence = (TextView) rootView.findViewById(R.id.tv_expected_sentence);
        tvReceivedSentence = (TextView) rootView.findViewById(R.id.tv_received_sentence);
        tvPercentage = (TextView) rootView.findViewById(R.id.tv_percentage);
        tvExpectedSentence.setText("");
        tvReceivedSentence.setText("");
        tvPercentage.setText("");

        //Debug
        tvTextResult = (TextView) rootView.findViewById(R.id.tv_result_text);
        tvDisplayText = (TextView) rootView.findViewById(R.id.tv_result_analysis);
        tvTextResult.setText("");
        tvDisplayText.setText("");

        if (card != null)
            tvCard.setText(card.getSentence());

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }
    */
    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    /*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    */
    //================= METHODS============================================================

    /**
     * @param result     Sentences
     * @param confidence The confidence array
     */
    public void displayResult(ArrayList<String> result, float[] confidence) {


        tvExpectedSentence.setText(card.difference(result.get(0)));
        tvReceivedSentence.setText("Received:"+result.get(0));
        tvPercentage.setText(String.format("%.2f", confidence[0] * 100) + "%");

        /*
        //==========================DEBUG===========================================================

        String displayText = "";

        for (int i = 0; i < result.size(); i++) {
            Log.d("Text", result.get(i));
            displayText += "Result " + i + ":" + result.get(i);
            if (confidence.length > i)
                displayText += " confidence:" + String.valueOf(confidence[i] * 100) + "\n";
        }

        for (float textConfidence : confidence) {
            Log.d("Confidence", String.valueOf(textConfidence * 100));
            displayText += textConfidence + " !;! ";

        }

        tvDisplayText.setText(displayText);

        String[] arr = result.get(0).split(" ");

        tvTextResult.setText("");

        for (String s : arr) {
            SpannableString str = new SpannableString(s);

            tvTextResult.setText(TextUtils.concat(tvTextResult.getText(), str) + " ");
        }

        //==========================================================================================
        */
    }

}
