package com.lamzone.mareu.controllers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lamzone.mareu.R;
import com.lamzone.mareu.controllers.di.Di;
import com.lamzone.mareu.models.Meeting;
import com.lamzone.mareu.services.ApiService;
import com.lamzone.mareu.views.adapters.MeetingsListAdapter;
import com.lamzone.mareu.views.dialogs.AddMeetingDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * // Created by Stéphane TAILLET on 20/10/2019
 */
public class MeetingsListFragment extends Fragment {

    @BindView(R.id.list_meetings)
    RecyclerView mRecyclerView;
    @BindView(R.id.meeting_fab_add)
    FloatingActionButton mAddFab;
    ApiService mApiService;
    public List<Meeting> mMeetings;
    MeetingsListAdapter mListAdapter;

    public MeetingsListFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetings_list, container, false);

        Context context = view.getContext();

        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        this.onClickMeetingAddFab();
        initList();
        return view;
    }

    public void initList(){
        mApiService = Di.getApiService();
        mMeetings = mApiService.getMeetings();
        mListAdapter = new MeetingsListAdapter(mMeetings);
        mRecyclerView.setAdapter(mListAdapter);
    }

    private void onClickMeetingAddFab(){
        mAddFab.setOnClickListener(view -> {
            AddMeetingDialog addMeetingDialog = new AddMeetingDialog();
            addMeetingDialog.show(getFragmentManager().beginTransaction(),"addmeetingdialog");
        });
    }
}