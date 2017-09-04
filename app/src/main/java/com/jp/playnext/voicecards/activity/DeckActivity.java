package com.jp.playnext.voicecards.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.fragment.DeckFragment;
import com.jp.playnext.voicecards.fragment.DeckListFragment;
import com.jp.playnext.voicecards.fragment.MainListFragment;
import com.jp.playnext.voicecards.model.Card;
import com.jp.playnext.voicecards.model.Deck;
import com.jp.playnext.voicecards.model.DeckInterface;
import com.jp.playnext.voicecards.model.InterfaceFactory;
import com.jp.playnext.voicecards.model.Theme;
import com.jp.playnext.voicecards.model.ThemeInterface;
import com.jp.playnext.voicecards.model.UserDefaultImpl;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeckActivity extends AppCompatActivity
        implements DeckListFragment.OnDeckListFragmentInteraction {

    private static final String TAG = DeckActivity.class.getSimpleName();
    private static final String ARGS_THEME = "args_theme";
    Theme theme;
    ArrayList<Deck> decks;

    public static void newInstance(Context context, Theme theme){
        Intent intent = new Intent(context, DeckActivity.class);
        intent.putExtra(ARGS_THEME, theme);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);

        if(getIntent() != null){
            theme = getIntent().getParcelableExtra(ARGS_THEME);
        }else {
            Toast.makeText(this, "THEME DOESN'T EXIST", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.deck_toolbar);
        setSupportActionBar(toolbar);

        initData();

        getDecksData();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_deck_fragment, DeckListFragment.newInstance(decks), DeckListFragment.class.getName())
                    .commit();
        }

        ButterKnife.bind(this);

        TextView tvTitle = (TextView) toolbar.findViewById(R.id.deck_toolbar_title);
        tvTitle.setText(theme.getName());

        Button btnNewDeck = (Button) toolbar.findViewById(R.id.btn_new_deck);
        final Context context = this;
        btnNewDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewDeckActivity.newInstance(context, theme);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDecksData();
    }

    private void initData() {
        decks = new ArrayList<Deck>();
        //  alThemes = DBHelper.getInstance(this).dbThemeHelper.getAllTheme();
    }

    private void getDecksData() {
        DeckInterface deckInterface = InterfaceFactory.createRetrofitService(DeckInterface.class);
        UserDefaultImpl userDefault = new UserDefaultImpl(this);
        Call<ArrayList<Deck>> callDeck = deckInterface.getDecks(userDefault.getToken(),theme.getId());
        callDeck.enqueue(new Callback<ArrayList<Deck>>() {
            @Override
            public void onResponse(Call<ArrayList<Deck>> call, Response<ArrayList<Deck>> response) {
                decks = response.body();
                updateData(decks);
            }

            @Override
            public void onFailure(Call<ArrayList<Deck>> call, Throwable t) {

            }
        });
    }

    private void updateData(ArrayList<Deck> decks) {
        DeckListFragment deckListFragment = (DeckListFragment) getSupportFragmentManager().findFragmentByTag(DeckListFragment.class.getName());
        deckListFragment.updateListView(decks);
    }



    /**
     * Start Card activity
     * @param card
     */
    @Override
    public void onDeckClicked(Deck deck){
        Log.v(TAG, "On click");
        CardsActivity.newInstance(this, deck);
    }
}
