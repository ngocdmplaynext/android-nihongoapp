package com.jp.playnext.voicecards.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.fragment.MainListFragment;
import com.jp.playnext.voicecards.fragment.TeachersFragment;
import com.jp.playnext.voicecards.model.InterfaceFactory;
import com.jp.playnext.voicecards.model.Theme;
import com.jp.playnext.voicecards.model.ThemeInterface;
import com.jp.playnext.voicecards.model.User;
import com.jp.playnext.voicecards.model.UserDefault;
import com.jp.playnext.voicecards.model.UserDefaultImpl;
import com.jp.playnext.voicecards.model.UserInterface;
import com.jp.playnext.voicecards.utils.FileUtils;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements MainListFragment.OnMainListFragmentInteraction,
TeachersFragment.OnTeacherFragmentInteraction {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private static final String TAG_THEME = "theme";
    private static final String TAG_TEACHERS = "teachers";
    public static String CURRENT_TAG = TAG_THEME;

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Handler mHandler;

    public static int navItemIndex = 1;

    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        UserDefault userDefault = new UserDefaultImpl(this);
        if (userDefault.isTeacher()) {
            menu.findItem(R.id.nav_teacher).setVisible(false);
        }

        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 1;
            CURRENT_TAG = TAG_THEME;
            loadHomeFragment();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//        final Context context = this;
//
//        if (id == R.id.nav_logout) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setMessage("ログアウトしてもよろしいですか");
//            builder.setCancelable(true);
//
//            builder.setPositiveButton(
//                    "はい",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            final UserDefaultImpl userDefault = new UserDefaultImpl(context);
//                            userDefault.resetToken();
//                            FileUtils.cleanDocumentsDirectory("card", context);
//                            Intent intent = new Intent(context, LoginActivity.class);
//                            startActivity(intent);
//                            dialog.cancel();
//                        }
//                    });
//
//            builder.setNegativeButton(
//                    "いいえ",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//
//            AlertDialog alert = builder.create();
//            alert.show();
//        } else if (id == R.id.nav_home) {
//            navItemIndex = 0;
//            CURRENT_TAG = TAG_THEME;
//        } else if (id == R.id.nav_teacher) {
//            navItemIndex = 1;
//            CURRENT_TAG = TAG_TEACHERS;
//        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    private void loadHomeFragment() {
        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();
    }

    private Fragment getHomeFragment() {
        selectNavMenu();
        switch (navItemIndex) {
            case 1:
                // home
                MainListFragment themeFragment = new MainListFragment();
                return themeFragment;
            case 2:
                // photos
                TeachersFragment teachersFragment = new TeachersFragment();
                return teachersFragment;
            default:
                return new MainListFragment();
        }
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        final Context context = this;

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Check to see which item was being clicked and perform appropriate action

                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_logout:
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
                        return false;
                    case R.id.nav_home:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_THEME;
                        loadHomeFragment();
                        break;
                    case R.id.nav_teacher:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_TEACHERS;
                        loadHomeFragment();
                        break;
                    default:
                        navItemIndex = 1;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
//                if (menuItem.getItemId() != R.id.nav_logout) {
//                    if (menuItem.isChecked()) {
//                        menuItem.setChecked(false);
//                    } else {
//                        menuItem.setChecked(true);
//                    }
//                    menuItem.setChecked(true);
//                } else {
//                    menuItem.setChecked(false);
//                }

                return true;
            }
        });
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
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

    @Override
    public void onBtnTouch(final User user, final Button btn) {
        final Context context = this;
        UserInterface userInterface = InterfaceFactory.createRetrofitService(UserInterface.class);
        UserDefault userDefault = new UserDefaultImpl(this);
        if (user.getBookmarked() == false) {
            Call<ResponseBody> call = userInterface.bookmarks(userDefault.getToken(), user.getUserId());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 1000) {
                        // Session is invalid
                        Toast.makeText(context, "Session is invalid", Toast.LENGTH_SHORT);
                    } else if (response.code() == 1001) { // This user is bookmarked
                        Toast.makeText(context, "This user is bookmarked", Toast.LENGTH_SHORT);
                    } else if (response.isSuccessful()) {
                        btn.setText("unBookmark");
                        user.setBookmarked(true);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(context, "Network error", Toast.LENGTH_SHORT);
                }
            });
        } else {
            Call<ResponseBody> call = userInterface.unBookmarks(userDefault.getToken(), user.getUserId());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 1000) {
                        // Session is invalid
                        Toast.makeText(context, "Session is invalid", Toast.LENGTH_SHORT);
                    } else if (response.code() == 1001) { // This user is bookmarked
                        Toast.makeText(context, "This user is bookmarked", Toast.LENGTH_SHORT);
                    } else if (response.isSuccessful()) {
                        btn.setText("Bookmark");
                        user.setBookmarked(false);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(context, "Network error", Toast.LENGTH_SHORT);
                }
            });
        }
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
