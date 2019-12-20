package com.lamzone.mareu.services;

import com.lamzone.mareu.models.Meeting;
import java.util.ArrayList;
import java.util.List;

public interface ApiService {

    List<Meeting> getMeetings();

    /**
     * Delete selected meeting
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Add a new meeting
     * @param meeting
     */
    void addMeeting(Meeting meeting);

    ArrayList<Meeting> filter(String meetingFilter);
}
