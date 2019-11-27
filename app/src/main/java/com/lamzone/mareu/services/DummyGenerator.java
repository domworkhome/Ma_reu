package com.lamzone.mareu.services;

import com.lamzone.mareu.models.Meeting;
import com.lamzone.mareu.models.MeetingRoom;
import com.lamzone.mareu.models.Member;
import com.lamzone.mareu.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * // Created by Stéphane TAILLET on 20/10/2019
 */
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

            new Meeting("Nouveaux produits", "25/10/2019", "9:00", mMeetingRoomList.get(1), mMemberList.get(1) + "@lamzone.com, " + mMemberList.get(3), mMeetingRoomList.get(1)),
            new Meeting("Refonte plateforme", "25/10/2019","10:30", mMeetingRoomList.get(0), mMemberList.get(0) + "@lamzone.com, " + mMemberList.get(2), mMeetingRoomList.get(0)),
            new Meeting("Stratégie commerciale", "26/10/2019","14:30", mMeetingRoomList.get(3), mMemberList.get(9) + "@lamzone.com, " + mMemberList.get(6), mMeetingRoomList.get(3)),
            new Meeting("Mise à jour", "26/10/2019","15:00", mMeetingRoomList.get(8), mMemberList.get(8) + "@lamzone.com, " + mMemberList.get(4), mMeetingRoomList.get(8)),
            new Meeting("Evolution", "26/10/2019","15:00", mMeetingRoomList.get(2), mMemberList.get(7) + "@lamzone.com, " + mMemberList.get(1), mMeetingRoomList.get(2)),
            new Meeting("Nouvelle fonctionnalité", "27/10/2019","9:30", mMeetingRoomList.get(9), mMemberList.get(3) + "@lamzone.com, " + mMemberList.get(5), mMeetingRoomList.get(9)),
            new Meeting("Ventes", "28/10/2019","9:00", mMeetingRoomList.get(7), mMemberList.get(2) + "@lamzone.com, " + mMemberList.get(8), mMeetingRoomList.get(7)),
            new Meeting("Livraison", "28/10/2019","9:15", mMeetingRoomList.get(5), mMemberList.get(5) + "@lamzone.com, " + mMemberList.get(7), mMeetingRoomList.get(5)),
            new Meeting("Ajout catalogue", "28/10/2019","11:00", mMeetingRoomList.get(4), mMemberList.get(4) + "@lamzone.com, " + mMemberList.get(9), mMeetingRoomList.get(4)),
            new Meeting("Ressources", "28/10/2019","13:30", mMeetingRoomList.get(6), mMemberList.get(6) + "@lamzone.com, " + mMemberList.get(0), mMeetingRoomList.get(6))
    );

    public static List<MeetingRoom> dummyMeetingRoomAndPicGenerator() {
        return new ArrayList<>(mMeetingRoomList);
    }

    /**
     * Generates the dummy members
     * @return a {@link List} of {@link Member}
     */
    public static List<String> dummyMembersGenerator() {
        return new ArrayList<>(mMemberList);
    }

    public static List<Meeting> dummyMeetingsGenerator() {
        return new ArrayList<>(dummyMeetings);
    }

    @Override
    public ArrayList<Meeting> filter(String text){
        ArrayList<Meeting> reunionFiltered = new ArrayList<>();
        for (Meeting r : dummyMeetings) {
            if (r.getMeetingRoom().equals(text)){
                reunionFiltered.add(r);
            }
        }
        return reunionFiltered;
    }
}