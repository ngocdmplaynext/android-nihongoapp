package com.jp.playnext.voicecards.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.fragment.ThemeFragment;
import com.jp.playnext.voicecards.model.Card;
import com.jp.playnext.voicecards.model.Deck;
import com.jp.playnext.voicecards.model.Theme;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Card} and makes a call to the
 * specified {@link ThemeFragment.OnThemeFragmentInteraction}.
 */
public class ThemeRecyclerVA extends RecyclerView.Adapter<ThemeRecyclerVA.ViewHolder> {

    private final Theme theme;
    private final ThemeFragment.OnThemeFragmentInteraction mListener;

    public ThemeRecyclerVA(Theme theme, ThemeFragment.OnThemeFragmentInteraction listener) {
        this.theme = theme;
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
//        holder.mSentenceView.setText(theme.get(position).getName());
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onDeckClicked(holder.mDeck);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.tv_sentence)
        public TextView mSentenceView;

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