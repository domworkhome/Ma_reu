package com.lamzone.mareu.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.lamzone.mareu.R;
import com.lamzone.mareu.controllers.fragments.MeetingsListFragment;
import com.lamzone.mareu.di.Di;
import com.lamzone.mareu.models.Meeting;
import com.lamzone.mareu.views.dialogs.FilterByDateDialog;
import com.lamzone.mareu.views.dialogs.FilterByRoomDialog;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FilterByRoomDialog.filterByRoom, FilterByDateDialog.filterByDate {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_room: {
                filterByRoomButton();
                return true;
            }
            case R.id.filter_date: {
                filterByDateButton();
                return true;
            }
            case R.id.no_filter: {
                displayMainMeetingsList();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void filterByRoomButton(){
        FilterByRoomDialog filterByRoomDialog = new FilterByRoomDialog();
        filterByRoomDialog.show(getSupportFragmentManager().beginTransaction(),"filterByRoomDialog");
        filterByRoomDialog.setFilterByRoomCallback(this);
    }

    private void filterByDateButton(){
        FilterByDateDialog filterByDateDialog = new FilterByDateDialog();
        filterByDateDialog.show(getSupportFragmentManager(),"filterByDateDialog");
        filterByDateDialog.setFilterByDateCallback(this);
    }

    public void displayMainMeetingsList(){
        MeetingsListFragment meetingsListFragment = (MeetingsListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_main_container);

        if(meetingsListFragment != null) {
            meetingsListFragment.updateFilteredListByRoom(Di.getApiService().getMeetings());
        }
    }

    @Override
    public void onRoomFilterButtonClick(List<Meeting> meetingList) {
        MeetingsListFragment meetingsListFragment = (MeetingsListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_main_container);

        if(meetingsListFragment != null){
            meetingsListFragment.updateFilteredListByRoom(meetingList);
        }
    }

    @Override
    public void onDateFilterButtonClick(List<Meeting> meetingList) {
        MeetingsListFragment meetingsListFragment = (MeetingsListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_main_container);

        if(meetingsListFragment != null) {
            meetingsListFragment.updateFilteredListByRoom(meetingList);
        }
    }
}