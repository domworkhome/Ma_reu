package com.lamzone.mareu;

import com.lamzone.mareu.di.Di;
import com.lamzone.mareu.models.Meeting;
import com.lamzone.mareu.services.ApiService;
import com.lamzone.mareu.services.DummyApiService;
import com.lamzone.mareu.services.DummyGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)

public class ApiServiceTest {

    private ApiService mService;

    @Before
    public void setUp() {
        this.mService = Di.getNewInstanceApiService();
    }

    @Test
    public void apiService_getMeetings() {
        List<Meeting> actualMeetings = this.mService.getMeetings();
        List<Meeting> expectedMeetings = DummyGenerator.dummyMeetingsGenerator();

        assertThat(actualMeetings, containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void apiService_deleteMeeting() {
        Meeting meeting = this.mService.getMeetings().get(0);
        this.mService.deleteMeeting(meeting);

        assertFalse(this.mService.getMeetings().contains(meeting));
    }

    @Test
    public void apiService_addMeeting() {

        Meeting meeting = new Meeting("Réunion test", "30/10/2019 - 09:30",
                DummyGenerator.dummyMeetingRoomAndPicGenerator().get(6),"sowann");

        assertFalse(this.mService.getMeetings().contains(meeting));

        this.mService.addMeeting(meeting);

        assertTrue(this.mService.getMeetings().contains(meeting));
    }

    @Test
    public void getFilteredListByRoom() {
            String Roomtext = "Bowser";
            Meeting meetingFilteredTest = new Meeting("reunion1",
                    "31/12/2019 - 14:00",
                    DummyGenerator.dummyMeetingRoomAndPicGenerator().get(0) ,
                    "sowann@lamzone.com, negan@lamzone.com");
                mService.addMeeting(meetingFilteredTest);
            ArrayList<Meeting> filteredMeeting = mService.filter(Roomtext);
            assertEquals(1, filteredMeeting.size());
        }

    @Test
    public void getFilteredListByDate() {
        String DateText = "31/12/2019";
        Meeting meetingFilteredTest = new Meeting("reunion1",
                "31/12/2019 - 14:00",
                DummyGenerator.dummyMeetingRoomAndPicGenerator().get(0) ,
                "sowann@lamzone.com, negan@lamzone.com");
        mService.addMeeting(meetingFilteredTest);
        ArrayList<Meeting> filteredMeeting = mService.filter(DateText);
        assertEquals(1, filteredMeeting.size());
    }
}