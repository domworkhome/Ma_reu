package com.lamzone.mareu.Services;

import com.lamzone.mareu.Models.Meeting;
import com.lamzone.mareu.Models.MeetingRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * // Created by Stéphane TAILLET on 20/10/2019
 */
public interface ApiService {

    List<Meeting> getMeetings();

    /**
     *
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     *
     * @param meeting
     */
    void addMeeting(Meeting meeting);
//
//    /**
//     *
//     * @return
//     */
//    //List<String> getMeetingRoom();
//
//    /**
//     *
//     * @return
//     */
//   // List<MeetingRoom> getMeetingRoomPic();

    /**
     *
     * @return
     */
    List<MeetingRoom> getMeetingRoomAndPic();

    /**
     *
     * @return
     */
    List<String> getMembers();

    ArrayList<Meeting> filter(String text);
}
