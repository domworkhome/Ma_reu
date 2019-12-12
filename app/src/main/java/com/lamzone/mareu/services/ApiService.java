package com.lamzone.mareu.services;

import com.lamzone.mareu.models.Meeting;
import com.lamzone.mareu.models.MeetingRoom;

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

    ArrayList<Meeting> filterByRoom(String meetingRoom);
}
