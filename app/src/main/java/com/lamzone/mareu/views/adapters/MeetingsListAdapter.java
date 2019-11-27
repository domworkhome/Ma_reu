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
import com.lamzone.mareu.models.Meeting;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * // Created by Stéphane TAILLET on 20/10/2019
 */
public class MeetingsListAdapter extends RecyclerView.Adapter<MeetingsListAdapter.MeetingsViewHolder> {

    private List<Meeting> mMeetings;

    public MeetingsListAdapter(List<Meeting> meetings) {
        mMeetings = meetings;
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

            String createMeetingInfo =  meeting.getMeetingTopic() + " - " + meeting.getMeetingDate() + " - " + meeting.getMeetingHour() + " - " + meeting.getMeetingRoom();
            mMeetingInfo.setText(createMeetingInfo);
            String createMeetingGuests = meeting.getMembers();
            mMeetingGuests.setText(createMeetingGuests);
            mMeetingRoomIcon.setImageResource(meeting.getMeetingRoomAndPic().getMeetingRoomPic());
        }
    }

    @Override
    public MeetingsListAdapter.MeetingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meeting, parent, false);

        MeetingsViewHolder meetingsViewHolder = new MeetingsViewHolder(v);
        return meetingsViewHolder;
    }

    @Override
    public void onBindViewHolder(MeetingsListAdapter.MeetingsViewHolder meetingsViewHolder, int position) {
        Meeting meeting = mMeetings.get(position);
        meetingsViewHolder.updateMeeting(meeting);

        meetingsViewHolder.mMeetingDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMeetings.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mMeetings.size());
                Toast.makeText(v.getContext(),R.string.meeting_deleted_french, Toast.LENGTH_SHORT).show();
                }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }
}