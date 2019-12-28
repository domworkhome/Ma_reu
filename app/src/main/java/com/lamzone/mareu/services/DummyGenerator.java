package com.lamzone.mareu.services;

import com.lamzone.mareu.models.Meeting;
import com.lamzone.mareu.models.MeetingRoom;
import com.lamzone.mareu.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyGenerator implements ApiService{

    private static List<MeetingRoom> mMeetingRoomList = Arrays.asList(

            new MeetingRoom("Bowser", R.drawable.ic_bowser),
            new MeetingRoom("Daisy", R.drawable.ic_daisy),
            new MeetingRoom("Goomba", R.drawable.ic_goomba),
            new MeetingRoom("Koopa", R.drawable.ic_koopa),
            new MeetingRoom("Luigi", R.drawable.ic_luigi),
            new MeetingRoom("Mario", R.drawable.ic_mario),
            new MeetingRoom("Peach", R.drawable.ic_peach),
            new MeetingRoom("Toad", R.drawable.ic_toad),
            new MeetingRoom("Wario", R.drawable.ic_wario),
            new MeetingRoom("Yoshi", R.drawable.ic_yoshi)
    );

    private static List<String> mMemberList = Arrays.asList(

            ("anakin@lamzone.com"),
            ("cassandra@lamzone.com"),
            ("ilann@lamzone.com"),
            ("marie@lamzone.com"),
            ("michael@lamzone.com"),
            ("negan@lamzone.com"),
            ("sowann@lamzone.com"),
            ("stephane@lamzone.com"),
            ("stephanie@lamzone.com"),
            ("zena@lamzone.com")
    );

    private static List<Meeting> dummyMeetings = Arrays.asList(
            new Meeting("Livraison", "28/12/2019 - 9:00 ", mMeetingRoomList.get(5), mMemberList.get(5) + ", " + mMemberList.get(7)),
            new Meeting("Ajout catalogue", "28/12/2019 - 10:00 ", mMeetingRoomList.get(4), mMemberList.get(4) + ", " + mMemberList.get(9)),
            new Meeting("Ressources", "29/12/2019 - 10:30", mMeetingRoomList.get(7), mMemberList.get(7) + ", " + mMemberList.get(0)),
            new Meeting("Ressources", "29/12/2019 - 10:30", mMeetingRoomList.get(9), mMemberList.get(9) + ", " + mMemberList.get(0))
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