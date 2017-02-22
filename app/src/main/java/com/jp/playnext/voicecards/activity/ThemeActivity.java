package com.jp.playnext.voicecards.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.fragment.ThemeFragment;
import com.jp.playnext.voicecards.model.Deck;
import com.jp.playnext.voicecards.model.Theme;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThemeActivity extends AppCompatActivity
        implements ThemeFragment.OnThemeFragmentInteraction{

    private static final String TAG = ThemeActivity.class.getSimpleName();
    private static final String ARGS_THEME = "args_theme";
    Theme theme;

    @BindView(R.id.tv_theme_title)
    TextView tvDeckTitle;

    public static void newInstance(Context context, Theme theme){
        Intent intent = new Intent(context, ThemeActivity.class);
        intent.putExtra(ARGS_THEME, theme);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        if(this.getIntent() != null){
            theme = getIntent().getParcelableExtra(ARGS_THEME);
        }else {
            Toast.makeText(this, "Theme is null", Toast.LENGTH_LONG);
            finish();
        }


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
        deck.loadCards(this);
        DeckActivity.newInstance(this, deck);
    }
}
