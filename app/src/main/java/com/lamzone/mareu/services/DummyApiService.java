package com.lamzone.mareu.services;

import com.lamzone.mareu.models.Meeting;
import java.util.ArrayList;
import java.util.List;

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
    public ArrayList<Meeting> filter(String meeting) {
        ArrayList<Meeting> filteredList = new ArrayList<>();
        for (int i = 0; i < mMeetingList.size(); i ++)
        {
            if (mMeetingList.get(i).getMeetingRoom().getMeetingRoomName().equals(meeting))
            {
                filteredList.add(mMeetingList.get(i));
            } else if (mMeetingList.get(i).getMeetingDate().contains(meeting))
            {
                filteredList.add(mMeetingList.get(i));
            }
        }
        return filteredList;
    }
}
