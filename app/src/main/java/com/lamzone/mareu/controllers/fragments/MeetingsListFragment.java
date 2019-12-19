package com.lamzone.mareu.controllers.fragments;

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
import com.lamzone.mareu.di.Di;
import com.lamzone.mareu.models.Meeting;
import com.lamzone.mareu.services.ApiService;
import com.lamzone.mareu.views.adapters.MeetingsListAdapter;
import com.lamzone.mareu.views.dialogs.AddMeetingDialog;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingsListFragment extends Fragment implements AddMeetingDialog.NewMeetingDatasListener{

    public List<Meeting> mMeetings;
    @BindView(R.id.list_meetings)
    RecyclerView mRecyclerView;
    @BindView(R.id.meeting_fab_add)
    FloatingActionButton mAddFab;
    public static final int REQUEST_CODE = 999;
    ApiService mApiService;
    MeetingsListAdapter mListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetings_list, container, false);
        ButterKnife.bind(this, view);
        onClickMeetingAddFab();
        initList();
        return view;
    }

    public void initList(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mApiService = Di.getApiService();
        mMeetings = mApiService.getMeetings();
        mListAdapter = new MeetingsListAdapter(new ArrayList<>(mMeetings));
        updateFilteredListByRoom(mMeetings);
        mRecyclerView.setAdapter(mListAdapter);
        dataChanged();
    }

    public void updateFilteredListByRoom(List<Meeting> meetingList){
        mListAdapter.updateList(meetingList);
    }

    private void onClickMeetingAddFab(){
        mAddFab.setOnClickListener(view -> {
            AddMeetingDialog addMeetingDialog = new AddMeetingDialog();
            addMeetingDialog.setTargetFragment(MeetingsListFragment.this, REQUEST_CODE);
            addMeetingDialog.show(getFragmentManager().beginTransaction(),"addmeetingdialog");
        });
    }

    @Override
    public void onPositiveButtonClick() {
        initList();
    }

    public void dataChanged()
    {
        mListAdapter.notifyDataSetChanged();
    }
}