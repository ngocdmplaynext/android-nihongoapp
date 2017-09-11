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
import com.jp.playnext.voicecards.adapter.MainThemeListRecyclerVA;
import com.jp.playnext.voicecards.model.Deck;
import com.jp.playnext.voicecards.model.InterfaceFactory;
import com.jp.playnext.voicecards.model.Theme;
import com.jp.playnext.voicecards.model.ThemeInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by danielmorais on 2/21/17.
 */

public class MainListFragment extends Fragment {

    private static final String ARG_THEME_LIST = "arg_theme_list";

    private ArrayList<Theme> alThemes;
    private MainListFragment.OnMainListFragmentInteraction mListener;
    private MainThemeListRecyclerVA adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MainListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MainListFragment newInstance() {
        MainListFragment fragment = new MainListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alThemes = new ArrayList<Theme>();
        getThemeData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deck_list, container, false);
        adapter = new MainThemeListRecyclerVA(new ArrayList<Theme>(), mListener);
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
        if (context instanceof MainListFragment.OnMainListFragmentInteraction) {
            mListener = (MainListFragment.OnMainListFragmentInteraction) context;
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

    private  void getThemeData() {
        ThemeInterface themeInterface = InterfaceFactory.createRetrofitService(ThemeInterface.class);
        Call<ArrayList<Theme>> callTheme = themeInterface.getTheme();
        callTheme.enqueue(new Callback<ArrayList<Theme>>() {
            @Override
            public void onResponse(Call<ArrayList<Theme>> call, Response<ArrayList<Theme>> response) {
                alThemes = response.body();
                updateListView(alThemes);
            }

            @Override
            public void onFailure(Call<ArrayList<Theme>> call, Throwable t) {

            }
        });
    }

    public void updateListView(ArrayList<Theme> alThemes) {
        adapter.addAllData(alThemes);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMainListFragmentInteraction {
        // TODO: Update argument type and name
        void onThemeClicked(Theme theme);
    }
}
