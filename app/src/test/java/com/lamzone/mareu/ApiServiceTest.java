package com.lamzone.mareu;

import com.lamzone.mareu.di.Di;
import com.lamzone.mareu.models.Meeting;
import com.lamzone.mareu.services.ApiService;
import com.lamzone.mareu.services.DummyGenerator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)

public class ApiServiceTest {

    // FIELDS --------------------------------------------------------------------------------------

    private ApiService mService;

    // METHODS -------------------------------------------------------------------------------------

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

//    @Test
//    public void apiService_addMeeting() {
//
//        Meeting meeting = new Meeting(11, "Réunion test", "30/10/2019", "09:30", DummyGenerator.dummyMeetingRoomGenerator().get(6), DummyGenerator.dummyMembersgenerator().get(3));
//
//        assertFalse(this.mService.getMeetings().contains(meeting));
//
//        this.mService.addMeeting(meeting);
//
//        assertTrue(this.mService.getMeetings().contains(meeting));
//
//        //assertEquals(this.mService.getMeetings().size()+1, this.mService.getMeetings().size());
//
//    }

//    @Test
//    public void apiService_getRooms() {
//        List<MeetingRoom> actualRooms = this.mService.getMeetingRoom();
//        List<MeetingRoom> expectedRooms = DummyGenerator.dummyMeetingRoomGenerator();
//
//        assertThat(actualRooms, containsInAnyOrder(expectedRooms.toArray()));
//    }
//
//    @Test
//    public void apiService_getMembers() {
//        List<Member> actualMembers = this.mService.getMembers();
//        List<Member> expectedMembers = DummyGenerator.dummyMembersgenerator();
//
//        assertThat(actualMembers, containsInAnyOrder(expectedMembers.toArray()));
//    }
}