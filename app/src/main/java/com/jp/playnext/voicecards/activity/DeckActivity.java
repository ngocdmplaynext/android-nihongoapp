package com.jp.playnext.voicecards.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.fragment.DeckFragment;
import com.jp.playnext.voicecards.model.Card;
import com.jp.playnext.voicecards.model.Deck;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeckActivity extends AppCompatActivity
        implements DeckFragment.OnDeckFragmentInteraction{

    private static final String TAG = DeckActivity.class.getSimpleName();

    Deck deck;

    @BindView(R.id.tv_deck_title)
    TextView tvDeckTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);

        deck = new Deck("Deck Name",  getResources().getStringArray(R.array.Words));

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_deck_fragment, DeckFragment.newInstance(deck))
                    .commit();
        }

        ButterKnife.bind(this);
        tvDeckTitle.setText(deck.getName());
    }

    @Override
    public void onCardClicked(Card card){
        Log.v(TAG, "On click");
        CardActivity.newInstance(this, deck, card);
    }
}
