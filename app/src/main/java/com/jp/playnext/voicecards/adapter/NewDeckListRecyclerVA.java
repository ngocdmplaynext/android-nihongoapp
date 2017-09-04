package com.jp.playnext.voicecards.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.fragment.NewDeckListFragment;
import com.jp.playnext.voicecards.model.RecordSentence;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ngocdm on 5/30/17.
 */

public class NewDeckListRecyclerVA extends RecyclerView.Adapter<NewDeckListRecyclerVA.ViewHolder> {

    ArrayList<RecordSentence> sentences;
    NewDeckListFragment.OnNewDeckListFragmentInteraction mListener;

    public NewDeckListRecyclerVA(ArrayList<RecordSentence> sentences, NewDeckListFragment.OnNewDeckListFragmentInteraction mListener) {
        this.sentences = sentences;
        this.mListener = mListener;
    }

    @Override
    public NewDeckListRecyclerVA.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_new_deck, parent, false);
        return new NewDeckListRecyclerVA.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewDeckListRecyclerVA.ViewHolder holder, int position) {
        RecordSentence sentence = sentences.get(position);
        holder.mSentence = sentence;
        holder.mSentenceView.setText(sentence.getSentence());
        holder.mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditClicked(holder.mSentence);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sentences.size();
    }

    public void addAllData(ArrayList<RecordSentence> sentences) {
        this.sentences = sentences;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.tv_new_deck_card_sentence)
        public TextView mSentenceView;
        @BindView(R.id.btn_edit)
        public Button mEditBtn;

        public RecordSentence mSentence;

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
