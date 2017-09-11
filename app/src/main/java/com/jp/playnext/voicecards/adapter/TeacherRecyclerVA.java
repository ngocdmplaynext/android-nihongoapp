package com.jp.playnext.voicecards.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jp.playnext.voicecards.R;
import com.jp.playnext.voicecards.fragment.CardListFragment;
import com.jp.playnext.voicecards.fragment.TeachersFragment;
import com.jp.playnext.voicecards.model.Card;
import com.jp.playnext.voicecards.model.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ngocdm on 9/8/17.
 */

public class TeacherRecyclerVA extends RecyclerView.Adapter<TeacherRecyclerVA.ViewHolder> {
    ArrayList<User> users;
    private final TeachersFragment.OnTeacherFragmentInteraction mListener;

    public  TeacherRecyclerVA(ArrayList<User> users, TeachersFragment.OnTeacherFragmentInteraction mListener) {
        this.users = users;
        this.mListener = mListener;
    }

    @Override
    public TeacherRecyclerVA.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_teacher, parent, false);
        return new TeacherRecyclerVA.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TeacherRecyclerVA.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.mUser = user;
        holder.tvName.setText(user.getName());
        String btnText = user.getBookmarked() ? "unBookmark" : "Bookmark";
        holder.btnBookmark.setText(btnText);

        holder.btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener == null) return;
                mListener.onBtnTouch(holder.mUser);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void addAllData(ArrayList<User> users) {
        this.users = users;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.tv_teachers_name)
        public TextView tvName;
        @BindView(R.id.btn_teacher_bookmark)
        public Button btnBookmark;

        public User mUser;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
