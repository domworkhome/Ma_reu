package com.lamzone.mareu.Services;

import com.lamzone.mareu.Models.Meeting;
import com.lamzone.mareu.Models.MeetingRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * // Created by Stéphane TAILLET on 20/10/2019
 */
public class DummyApiService implements ApiService {

    private List<Meeting> mMeetingList = DummyGenerator.dummyMeetingsGenerator();
    private List<MeetingRoom> mMeetingRoomsAndPic = DummyGenerator.dummyMeetingRoomAndPicGenerator();
    private List<String> mMemberList = DummyGenerator.dummyMembersGenerator();

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
    public List<MeetingRoom> getMeetingRoomAndPic() {
        return this.mMeetingRoomsAndPic;
    }

    @Override
    public List<String> getMembers() {
        return this.mMemberList;
    }

    @Override
    public ArrayList<Meeting> filter(String text) {
        return null;
    }
}
