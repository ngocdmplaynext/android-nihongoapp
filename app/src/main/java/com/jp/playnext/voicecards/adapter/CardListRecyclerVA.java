package com.jp.playnext.voicecards.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.fragment.CardListFragment;
import com.jp.playnext.voicecards.model.Card;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ngocdm on 5/19/17.
 */

public class CardListRecyclerVA extends RecyclerView.Adapter<CardListRecyclerVA.ViewHolder> {

    ArrayList<Card> cards;
    private final CardListFragment.OnCardListFragmentInteraction mListener;

    public  CardListRecyclerVA(ArrayList<Card> cards, CardListFragment.OnCardListFragmentInteraction mListener) {
        this.cards = cards;
        this.mListener = mListener;
    }

    @Override
    public CardListRecyclerVA.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_card, parent, false);
        return new CardListRecyclerVA.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardListRecyclerVA.ViewHolder holder, final int position) {
        Card card = cards.get(position);
        holder.mCard = card;
        holder.mSentenceView.setText(card.getName());
        holder.mRomajiView.setText(card.getName());
        holder.mScoreView.setText(card.getBestScore().toString());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) return;
                mListener.onCardClicked(holder.mCard);
            }
        });

        holder.mRecordBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mListener == null) return false;
                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        mListener.onBtnTouch(holder.mCard);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_CANCEL:
                        mListener.onBtnCancel(holder.mCard);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void addAllData(ArrayList<Card> cards) {
        this.cards = cards;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.tv_cards_sentence)
        public TextView mSentenceView;
        @BindView(R.id.tv_cards_romaji)
        public  TextView mRomajiView;
        @BindView(R.id.tv_cards_score)
        public  TextView mScoreView;
        @BindView(R.id.btn_record_card)
        public Button mRecordBtn;

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
