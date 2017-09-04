package com.jp.playnext.voicecards.fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.adapter.NewDeckListRecyclerVA;
import com.jp.playnext.voicecards.model.RecordSentence;

import java.util.ArrayList;

/**
 * Created by ngocdm on 5/29/17.
 */

public class NewDeckListFragment extends Fragment {
    private static final String ARG_NEW_DECK_LIST = "arg_new_deck_list";

    private ArrayList<RecordSentence> sentences;

    private NewDeckListFragment.OnNewDeckListFragmentInteraction mListener;

    NewDeckListRecyclerVA adapter;

    public NewDeckListFragment() {

    }

    public static NewDeckListFragment newInstance( ArrayList<RecordSentence> sentences) {
        NewDeckListFragment fragment = new NewDeckListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_NEW_DECK_LIST, sentences);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            sentences = getArguments().getParcelableArrayList(ARG_NEW_DECK_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_deck, container, false);
        adapter = new NewDeckListRecyclerVA(new ArrayList<RecordSentence>(), mListener);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            RecyclerView recyclerView = (RecyclerView) view;

            //  if (deck.getCards().size() <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            /*
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, deck.getCards().size() ));
            }
*/
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewDeckListFragment.OnNewDeckListFragmentInteraction) {
            mListener = (NewDeckListFragment.OnNewDeckListFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateListView(ArrayList<RecordSentence> sentences) {
        adapter.addAllData(sentences);
    }

    public interface OnNewDeckListFragmentInteraction {
        void onEditClicked(RecordSentence sentence);
    }
}
