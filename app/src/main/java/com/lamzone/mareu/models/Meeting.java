package com.lamzone.mareu.models;

import java.util.Objects;

/**
 * // Created by Stéphane TAILLET on 20/10/2019
 */
public class Meeting {

    private String mMeetingTopic;
    private String mMeetingDate;
    private String mMeetingHour;
    private MeetingRoom mMeetingRoom;
    private String  mMembers;
    private MeetingRoom mMeetingRoomAndPic;

    public Meeting(String meetingTopic, String meetingDate, String meetingHour, MeetingRoom meetingRoom, String members, MeetingRoom meetingRoomAndPic) {
        mMeetingTopic = meetingTopic;
        mMeetingDate = meetingDate;
        mMeetingHour = meetingHour;
        mMeetingRoom = meetingRoom;
        mMembers = members;
        mMeetingRoomAndPic = meetingRoomAndPic;
    }

    public String getMeetingTopic() {
        return mMeetingTopic;
    }

    public String getMeetingDate() {
        return mMeetingDate;
    }

    public String getMeetingHour() {
        return mMeetingHour;
    }

    public MeetingRoom  getMeetingRoom() {
        return mMeetingRoom;
    }

    public String getMembers() {
        return mMembers + "@lamzone.com";
    }

    public MeetingRoom getMeetingRoomAndPic() {
        return mMeetingRoomAndPic;
    }


    @Override
    public boolean equals(Object obj) {
        // Same address
        if (this == obj) return true;

        // Null or the class is different
        if (obj == null || getClass() != obj.getClass()) return false;

        // Cast Object to Room
        Meeting meeting = (Meeting) obj;

        return Objects.equals(this.mMeetingTopic, meeting.mMeetingTopic);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mMeetingTopic);
    }
}
