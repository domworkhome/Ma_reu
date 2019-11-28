package com.lamzone.mareu.controllers.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.lamzone.mareu.R;
import com.lamzone.mareu.controllers.di.Di;
import com.lamzone.mareu.models.Meeting;
import com.lamzone.mareu.models.MeetingRoom;
import com.lamzone.mareu.services.DummyGenerator;
import com.lamzone.mareu.views.adapters.MeetingsListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    List<MeetingRoom> listMeetingRoomNameAndPic;
    private ImageView roomSpinnerPic;
    private String TAG;
    String itemName = "";
    MeetingsListAdapter mMeetingsListAdapter;

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
                configureAndShowAlertDialog();
                return true;
            }
            case R.id.filter_date: {
                return true;
            }
            case R.id.no_filter: {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.filter_list_dialog, null);

        Spinner spinner = view.findViewById(R.id.spinner_choice);
        roomSpinnerPic = view.findViewById(R.id.spinner_meeting_room_icon);
        listMeetingRoomNameAndPic = DummyGenerator.dummyMeetingRoomAndPicGenerator();

        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, listMeetingRoomNameAndPic);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MeetingRoom roomItemSpinner = (MeetingRoom) spinner.getSelectedItem();
                itemName = roomItemSpinner.getMeetingRoomName();
                roomSpinnerPic.setImageResource(roomItemSpinner.getMeetingRoomPic());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        builder.setTitle(getString(R.string.filter_room_title_french))
                .setView(view)
                .setPositiveButton(getString(R.string.filter_button_french),
                        (dialog, which) -> {
                    ArrayList<Meeting> rooms = Di.getApiService().filter(itemName);
                        })
                .setNegativeButton("Annuler",
                        (dialog, which) -> {
                        });

        builder.create().show();
    }
}