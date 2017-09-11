package com.jp.playnext.voicecards.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.adapter.CardListRecyclerVA;
import com.jp.playnext.voicecards.adapter.TeacherRecyclerVA;
import com.jp.playnext.voicecards.model.Card;
import com.jp.playnext.voicecards.model.InterfaceFactory;
import com.jp.playnext.voicecards.model.User;
import com.jp.playnext.voicecards.model.UserDefaultImpl;
import com.jp.playnext.voicecards.model.UserInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ngocdm on 9/8/17.
 */

public class TeachersFragment extends Fragment {
    private static final String ARG_TEACHER_LIST = "arg_teacher_list";
    private static final String TAG = TeachersFragment.class.getSimpleName();

    private ArrayList<User> users;

    private TeacherRecyclerVA adapter;

    private Context context;

    private TeachersFragment.OnTeacherFragmentInteraction mListener;

    public TeachersFragment() {

    }

    public static TeachersFragment newInstance( ArrayList<User> users) {
        TeachersFragment fragment = new TeachersFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_TEACHER_LIST, users);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teachers, container, false);
        adapter = new TeacherRecyclerVA(new ArrayList<User>(), mListener);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            RecyclerView recyclerView = (RecyclerView) view;

            //  if (deck.getCards().size() <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            /*
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, deck.getCards().size() ));
            }
*/
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        getUsersData();
        if (context instanceof TeachersFragment.OnTeacherFragmentInteraction) {
            mListener = (TeachersFragment.OnTeacherFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void getUsersData() {
        UserInterface userInterface = InterfaceFactory.createRetrofitService(UserInterface.class);
        UserDefaultImpl userDefault = new UserDefaultImpl(this.context);
        Call<ArrayList<User>> callUser = userInterface.getTeachers(userDefault.getToken());
        callUser.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                users = response.body();
                updateListView(users);
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });
    }

    public void updateListView(ArrayList<User> users) {
        adapter.addAllData(users);
    }

    public interface OnTeacherFragmentInteraction {
        // TODO: Update argument type and name
        void onBtnTouch(User user);
    }
}
