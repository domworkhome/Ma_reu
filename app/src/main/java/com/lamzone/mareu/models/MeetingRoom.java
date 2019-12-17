package com.lamzone.mareu.models;

import android.support.annotation.NonNull;

import java.util.Objects;

public class MeetingRoom {

    private String mMeetingRoomName;
    private int mMeetingRoomPic;

    public MeetingRoom(String meetingRoomName, int meetingRoomPic) {
        mMeetingRoomName = meetingRoomName;
        mMeetingRoomPic = meetingRoomPic;
    }

    public String getMeetingRoomName() {
        return mMeetingRoomName;
    }

    public int getMeetingRoomPic() {
        return mMeetingRoomPic;
    }

    @NonNull
    @Override
    public String toString() {
        return mMeetingRoomName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        MeetingRoom meetingRoom = (MeetingRoom) obj;

        return Objects.equals(this.mMeetingRoomName, meetingRoom.mMeetingRoomName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mMeetingRoomName);
    }
}
