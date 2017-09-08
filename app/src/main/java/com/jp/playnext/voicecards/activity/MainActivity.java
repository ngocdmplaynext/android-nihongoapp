package com.jp.playnext.voicecards.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.fragment.MainListFragment;
import com.jp.playnext.voicecards.model.InterfaceFactory;
import com.jp.playnext.voicecards.model.Theme;
import com.jp.playnext.voicecards.model.ThemeInterface;
import com.jp.playnext.voicecards.model.UserDefaultImpl;
import com.jp.playnext.voicecards.utils.FileUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainListFragment.OnMainListFragmentInteraction {

    ArrayList<Theme> alThemes;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initData();

//        setUserData();

        getThemeData();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_theme_list_fragment, MainListFragment.newInstance(alThemes), MainListFragment.class.getName())
                    .commit();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

//    private void setUserData() {
//        // Khởi tạo OkHttpClient để lấy dữ liệu.
//        OkHttpClient client = new OkHttpClient();
//        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
//        Moshi moshi = new Moshi.Builder().build();
//        Type usersType = Types.newParameterizedType(List.class, User.class);
//        final JsonAdapter<List<User>> jsonAdapter = moshi.adapter(usersType);
//
//        // Tạo request lên server.
//        Request request = new Request.Builder()
//                .url("https://api.github.com/users")
//                .build();
//
//        // Thực thi request.
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("Error", "Network Error");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                // Lấy thông tin JSON trả về. Bạn có thể log lại biến json này để xem nó như thế nào.
//                String json = response.body().string();
//                final List<User> users = jsonAdapter.fromJson(json);
//
//                // Cho hiển thị lên RecyclerView.
////                runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        rvUsers.setAdapter(new UserAdapter(users, MainActivity.this));
////                    }
////                });
//            }
//        });
//    }

    private  void getThemeData() {
        ThemeInterface themeInterface = InterfaceFactory.createRetrofitService(ThemeInterface.class);
        Call<ArrayList<Theme>> callTheme = themeInterface.getTheme();
        callTheme.enqueue(new Callback<ArrayList<Theme>>() {
            @Override
            public void onResponse(Call<ArrayList<Theme>> call, Response<ArrayList<Theme>> response) {
                alThemes = response.body();
                updateData(alThemes);
            }

            @Override
            public void onFailure(Call<ArrayList<Theme>> call, Throwable t) {

            }
        });
    }

    private void updateData(ArrayList<Theme> alThemes) {
        MainListFragment mainListFragment = (MainListFragment) getSupportFragmentManager().findFragmentByTag(MainListFragment.class.getName());
        mainListFragment.updateListView(alThemes);
    }

    private void initData() {
        alThemes = new ArrayList<Theme>();
      //  alThemes = DBHelper.getInstance(this).dbThemeHelper.getAllTheme();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.theme, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        final Context context = this;

        if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("ログアウトしてもよろしいですか");
            builder.setCancelable(true);

            builder.setPositiveButton(
                    "はい",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            final UserDefaultImpl userDefault = new UserDefaultImpl(context);
                            userDefault.resetToken();
                            FileUtils.cleanDocumentsDirectory("card", context);
                            Intent intent = new Intent(context, LoginActivity.class);
                            startActivity(intent);
                            dialog.cancel();
                        }
                    });

            builder.setNegativeButton(
                    "いいえ",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        } else if (id == R.id.nav_home) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_teacher) {

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    /**
     * Start ThemeActivity
     *
     * @param theme
     */
    @Override
    public void onThemeClicked(Theme theme) {
        //Load Decks associated to this theme
      //  theme.loadDecks(this);
        DeckActivity.newInstance(this, theme);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        AppIndex.AppIndexApi.start(client2, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client2, getIndexApiAction());
        client2.disconnect();
    }
}
