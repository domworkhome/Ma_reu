package com.lamzone.mareu.models;

import java.util.Objects;

public class Meeting {

    private String mMeetingTopic;
    private String mMeetingDate;
    private MeetingRoom mMeetingRoom;
    private String  mMembers;

    public Meeting(String meetingTopic, String meetingDate, MeetingRoom meetingRoom, String members) {
        mMeetingTopic = meetingTopic;
        mMeetingDate = meetingDate;
        mMeetingRoom = meetingRoom;
        mMembers = members;
    }

    public String getMeetingTopic() {
        return mMeetingTopic;
    }

    public String getMeetingDate() {
        return mMeetingDate;
    }

    public MeetingRoom  getMeetingRoom() {
        return mMeetingRoom;
    }

    public String getMembers() {
        return mMembers;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;
        Meeting meeting = (Meeting) obj;

        return Objects.equals(this.mMeetingTopic, meeting.mMeetingTopic);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mMeetingTopic);
    }
}
