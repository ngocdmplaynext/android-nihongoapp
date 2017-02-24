package com.jp.playnext.voicecards.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.fragment.DeckFragment;
import com.jp.playnext.voicecards.model.Card;
import com.jp.playnext.voicecards.model.Deck;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Card} and makes a call to the
 * specified {@link DeckFragment.OnDeckFragmentInteraction}.
 */
public class DeckRecyclerVA extends RecyclerView.Adapter<DeckRecyclerVA.ViewHolder> {

    private final Deck deck;
    private final DeckFragment.OnDeckFragmentInteraction mListener;

    public DeckRecyclerVA(Deck deck, DeckFragment.OnDeckFragmentInteraction listener) {
        this.deck = deck;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_deck_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mCard = deck.get(position);
        holder.mSentenceView.setText(deck.get(position).getDisplaySentence());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onCardClicked(holder.mCard);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return deck.getCards().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.tv_sentence)
        public TextView mSentenceView;
        public Card mCard;

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