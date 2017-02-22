package com.jp.playnext.voicecards.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.fragment.ThemeFragment;
import com.jp.playnext.voicecards.model.Deck;
import com.jp.playnext.voicecards.model.Theme;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThemeActivity extends AppCompatActivity
        implements ThemeFragment.OnThemeFragmentInteraction{

    private static final String TAG = ThemeActivity.class.getSimpleName();
    private static final String ARGS_DECK = "args_deck";
    Theme theme;

    @BindView(R.id.tv_theme_title)
    TextView tvDeckTitle;

    public static void newInstance(Context context, Deck deck){
        Intent intent = new Intent(context, ThemeActivity.class);
        intent.putExtra(ARGS_DECK, deck);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        Theme theme = new Theme();
        theme.addDeck(new Deck("English",  getResources().getStringArray(R.array.deck_english)));
        theme.addDeck(new Deck("Japanese",  getResources().getStringArray(R.array.deck_japanese)));

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_theme_fragment, ThemeFragment.newInstance(theme))
                    .commit();
        }

        ButterKnife.bind(this);
        tvDeckTitle.setText(theme.getName());
    }


    @Override
    public void onDeckClicked(Deck deck) {
        DeckActivity.newInstance(this, deck);
    }
}
