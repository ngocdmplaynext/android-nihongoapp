package com.jp.playnext.voicecards.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.fragment.CardListFragment;
import com.jp.playnext.voicecards.fragment.TeachersFragment;
import com.jp.playnext.voicecards.model.Card;
import com.jp.playnext.voicecards.model.InterfaceFactory;
import com.jp.playnext.voicecards.model.User;
import com.jp.playnext.voicecards.model.UserDefaultImpl;
import com.jp.playnext.voicecards.model.UserInterface;

import java.util.ArrayList;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeachersActivity extends AppCompatActivity
        implements  TeachersFragment.OnTeacherFragmentInteraction {

    ArrayList<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        initData();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_cards_fragment, TeachersFragment.newInstance(users), TeachersFragment.class.getName())
                    .commit();
        }

        ButterKnife.bind(this);

        getUsersData();
    }

    private void initData() {
        users = new ArrayList<User>();
    }

    public void getUsersData() {
        UserInterface userInterface = InterfaceFactory.createRetrofitService(UserInterface.class);
        UserDefaultImpl userDefault = new UserDefaultImpl(this);
        Call<ArrayList<User>> callUser = userInterface.getTeachers(userDefault.getToken());
        callUser.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                users = response.body();
                updateData(users);
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });
    }

    private void updateData(ArrayList<User> users) {
        TeachersFragment teacherFragment = (TeachersFragment) getSupportFragmentManager().findFragmentByTag(TeachersFragment.class.getName());
        teacherFragment.updateListView(users);
    }

    @Override
    public void onBtnTouch(User user) {

    }
}
