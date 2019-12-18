package com.lamzone.mareu.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.lamzone.mareu.R;
import com.lamzone.mareu.di.Di;
import com.lamzone.mareu.models.Meeting;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingsListAdapter extends RecyclerView.Adapter<MeetingsListAdapter.MeetingsViewHolder> {

    public List<Meeting> mMeetings;

    public MeetingsListAdapter(List<Meeting> meetingList) {
        mMeetings = meetingList;
    }

    public static class MeetingsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_meeting_room_icon)
        ImageView mMeetingRoomIcon;
        @BindView(R.id.item_meeting_info)
        TextView mMeetingInfo;
        @BindView(R.id.item_meeting_guests)
        TextView mMeetingGuests;
        @BindView(R.id.item_meeting_delete)
        ImageButton mMeetingDelete;

        public MeetingsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void updateMeeting (Meeting meeting){
            String createMeetingInfo =  meeting.getMeetingTopic() + "\n" + meeting.getMeetingDate() + " - " + meeting.getMeetingRoom();
            mMeetingInfo.setText(createMeetingInfo);
            String createMeetingGuests = meeting.getMembers();
            mMeetingGuests.setText(createMeetingGuests);
            mMeetingRoomIcon.setImageResource(meeting.getMeetingRoom().getMeetingRoomPic());
        }
    }

    public void updateList(List<Meeting> meetingList){
        mMeetings.clear();
        mMeetings.addAll(new ArrayList<>((meetingList)));
        notifyDataSetChanged();
    }

    @Override
    public MeetingsListAdapter.MeetingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meeting, parent, false);
        MeetingsViewHolder meetingsViewHolder = new MeetingsViewHolder(v);
        return meetingsViewHolder;
    }

    @Override
    public void onBindViewHolder(MeetingsListAdapter.MeetingsViewHolder meetingsViewHolder, int position) {
        Meeting meeting = mMeetings.get(position);
        meetingsViewHolder.updateMeeting(meeting);

        meetingsViewHolder.mMeetingDelete.setOnClickListener(v -> {
            Di.getApiService().deleteMeeting(meeting);
            updateList(Di.getApiService().getMeetings());
            });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }
}