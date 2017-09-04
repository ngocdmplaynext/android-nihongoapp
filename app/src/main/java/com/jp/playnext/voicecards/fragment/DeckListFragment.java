package com.jp.playnext.voicecards.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.adapter.DeckListRecyclerVA;
import com.jp.playnext.voicecards.model.Deck;

import java.util.ArrayList;

/**
 * Created by ngocdm on 5/18/17.
 */

public class DeckListFragment extends Fragment {
    private static final String ARG_DECK_LIST = "arg_deck_list";

    private ArrayList<Deck> decks;

    private DeckListFragment.OnDeckListFragmentInteraction mListener;
    private DeckListRecyclerVA adapter;

    public DeckListFragment() {

    }

    public static DeckListFragment newInstance( ArrayList<Deck> decks) {
        DeckListFragment fragment = new DeckListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_DECK_LIST, decks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            decks = getArguments().getParcelableArrayList(ARG_DECK_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deck_list, container, false);
        adapter = new DeckListRecyclerVA(new ArrayList<Deck>(), mListener);
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
        if (context instanceof DeckListFragment.OnDeckListFragmentInteraction) {
            mListener = (DeckListFragment.OnDeckListFragmentInteraction) context;
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

    public void updateListView(ArrayList<Deck> decks) {
        adapter.addAllData(decks);
    }

    public interface OnDeckListFragmentInteraction {
        // TODO: Update argument type and name
        void onDeckClicked(Deck deck);
    }
}
