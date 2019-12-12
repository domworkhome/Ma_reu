package com.lamzone.mareu.services;

import com.lamzone.mareu.models.Meeting;
import com.lamzone.mareu.models.MeetingRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * // Created by Stéphane TAILLET on 20/10/2019
 */
public class DummyApiService implements ApiService {

    private List<Meeting> mMeetingList = DummyGenerator.dummyMeetingsGenerator();

    @Override
    public List<Meeting> getMeetings() {
        return mMeetingList;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        this.mMeetingList.remove(meeting);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        this.mMeetingList.add(meeting);
    }

    @Override
    public List<Meeting> filterByRoom(MeetingRoom meetingRoom) {
        List<Meeting> filteredList = new ArrayList<>();
        for (int i = 0; i < mMeetingList.size(); i ++)
        {
            if (mMeetingList.get(i).getMeetingRoom().getMeetingRoomName().equals(meetingRoom.getMeetingRoomName()))
            {
                filteredList.add(mMeetingList.get(i));
            }
        }
        return filteredList;
    }
}
