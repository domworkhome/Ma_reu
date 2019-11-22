package com.lamzone.mareu.Models;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.util.Objects;

import butterknife.BindView;

/**
 * // Created by Stéphane TAILLET on 20/10/2019
 */
public class MeetingRoom {

    // FIELDS

    public String mMeetingRoomName;
    public int mMeetingRoomPic;

    // CONSTRUCTOR

    public MeetingRoom(String meetingRoomName, int meetingRoomPic) {
        mMeetingRoomName = meetingRoomName;
        mMeetingRoomPic = meetingRoomPic;
    }

    // METHODS

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
        // Same address
        if (this == obj) return true;

        // Null or the class is different
        if (obj == null || getClass() != obj.getClass()) return false;

        // Cast Object to Room
        MeetingRoom meetingRoom = (MeetingRoom) obj;

        return Objects.equals(this.mMeetingRoomName, meetingRoom.mMeetingRoomName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.mMeetingRoomName);
    }
}
