package com.jp.playnext.voicecards.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.fragment.DeckListFragment;
import com.jp.playnext.voicecards.model.Deck;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ngocdm on 5/18/17.
 */

public class DeckListRecyclerVA extends RecyclerView.Adapter<DeckListRecyclerVA.ViewHolder> {
    private ArrayList<Deck> decks;
    private final DeckListFragment.OnDeckListFragmentInteraction mListener;

    public DeckListRecyclerVA(ArrayList<Deck> decks, DeckListFragment.OnDeckListFragmentInteraction listener) {
        this.decks = decks;
        mListener = listener;
    }

    @Override
    public DeckListRecyclerVA.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_deck_card, parent, false);
        return new DeckListRecyclerVA.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DeckListRecyclerVA.ViewHolder holder, int position) {
        holder.mDeck = decks.get(position);
        holder.mSentenceView.setText(decks.get(position).getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onDeckClicked(holder.mDeck);
                }
            }
        });
    }

    public void addAllData(ArrayList<Deck> decks) {
        this.decks = decks;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return decks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.tv_sentence)
        public TextView mSentenceView;
        public Deck mDeck;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mSentenceView.getText() + "'";
        }
    }
}
