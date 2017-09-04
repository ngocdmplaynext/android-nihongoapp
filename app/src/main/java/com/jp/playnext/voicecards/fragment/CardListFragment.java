package com.jp.playnext.voicecards.fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.adapter.CardListRecyclerVA;
import com.jp.playnext.voicecards.adapter.DeckListRecyclerVA;
import com.jp.playnext.voicecards.model.Card;
import com.jp.playnext.voicecards.model.Deck;
import com.jp.playnext.voicecards.model.Theme;

import java.util.ArrayList;

/**
 * Created by ngocdm on 5/19/17.
 */

public class CardListFragment extends Fragment {
    private static final String ARG_CARD_LIST = "arg_card_list";

    private ArrayList<Card> cards;

    private CardListRecyclerVA adapter;

    private CardListFragment.OnCardListFragmentInteraction mListener;

    public CardListFragment() {

    }

    public static CardListFragment newInstance( ArrayList<Card> cards) {
        CardListFragment fragment = new CardListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_CARD_LIST, cards);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            cards = getArguments().getParcelableArrayList(ARG_CARD_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cards_list, container, false);
        adapter = new CardListRecyclerVA(new ArrayList<Card>(), mListener);
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
        if (context instanceof CardListFragment.OnCardListFragmentInteraction) {
            mListener = (CardListFragment.OnCardListFragmentInteraction) context;
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

    public void updateListView(ArrayList<Card> cards) {
        adapter.addAllData(cards);
    }

    public interface OnCardListFragmentInteraction {
        // TODO: Update argument type and name
        void onCardClicked(Card card);
        void onBtnTouch(Card card);
        void onBtnCancel(Card card);
    }

}


