package com.lamzone.mareu.services;

import com.lamzone.mareu.models.Meeting;
import com.lamzone.mareu.models.MeetingRoom;
import com.lamzone.mareu.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyGenerator implements ApiService{

    public static List<MeetingRoom> mMeetingRoomList = Arrays.asList(

            new MeetingRoom("Bowser", R.drawable.ic_bowser),
            new MeetingRoom("Daisy", R.drawable.ic_daisy),
            new MeetingRoom("Donkey Kong", R.drawable.ic_donkey_kong),
            new MeetingRoom("Goomba", R.drawable.ic_goomba),
            new MeetingRoom("Koopa", R.drawable.ic_koopa),
            new MeetingRoom("Luigi", R.drawable.ic_luigi),
            new MeetingRoom("Mario", R.drawable.ic_mario),
            new MeetingRoom("Peach", R.drawable.ic_peach),
            new MeetingRoom("Toad", R.drawable.ic_toad),
            new MeetingRoom("Yoshi", R.drawable.ic_yoshi)
    );

    private static List<String> mMemberList = Arrays.asList(

            ("anakin"),
            ("cassandra"),
            ("ilann"),
            ("marie"),
            ("michael"),
            ("negan"),
            ("sowann"),
            ("stephane"),
            ("stephanie"),
            ("zena")
    );

    private static List<Meeting> dummyMeetings = Arrays.asList(
            new Meeting("Livraison", "19/12/2019 - 9:00 ", mMeetingRoomList.get(5), mMemberList.get(5) + "@lamzone.com, " + mMemberList.get(7) + "@lamzone.com"),
            new Meeting("Ajout catalogue", "19/12/2019 - 10:00 ", mMeetingRoomList.get(4), mMemberList.get(4) + "@lamzone.com, " + mMemberList.get(9) + "@lamzone.com"),
            new Meeting("Ressources", "20/12/2019 - 10:30", mMeetingRoomList.get(6), mMemberList.get(6) + "@lamzone.com, " + mMemberList.get(0) + "@lamzone.com")
    );

    public static List<MeetingRoom> dummyMeetingRoomAndPicGenerator() {
        return new ArrayList<>(mMeetingRoomList);
    }

    public static List<String> dummyMembersGenerator() {
        return new ArrayList<>(mMemberList);
    }

    public static List<Meeting> dummyMeetingsGenerator() {
        return new ArrayList<>(dummyMeetings);
    }
}