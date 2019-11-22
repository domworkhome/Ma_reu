package com.lamzone.mareu.Controllers.Activities;

import android.app.AlertDialog;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.lamzone.mareu.Controllers.DI.DI;
import com.lamzone.mareu.Controllers.Fragments.MeetingsListFragment;
import com.lamzone.mareu.Models.Meeting;
import com.lamzone.mareu.Models.MeetingRoom;
import com.lamzone.mareu.R;
import com.lamzone.mareu.Services.ApiService;
import com.lamzone.mareu.Services.DummyGenerator;
import com.lamzone.mareu.Views.Adapters.MeetingsListAdapter;
import com.lamzone.mareu.Views.Dialogs.AddMeetingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
//    @BindView(R.id.list_meetings)
//    RecyclerView mRecyclerView;
    private ApiService mApiService;
    public List<Meeting> mMeetings;
    private MeetingsListAdapter mListAdapter;
    MeetingsListFragment mMeetingsListFragment;
    List<MeetingRoom> listMeetingRoomNameAndPic;
    private ImageView roomSpinnerPic;
    private String TAG;
    String itemName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
            this.configureMeetingsFragment();
        }

    }

    private void configureMeetingsFragment() {
        if (mMeetingsListFragment == null) {
            mMeetingsListFragment = new MeetingsListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_main_container, mMeetingsListFragment).commit();
        }
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
                Log.d(TAG, "onOptionsItemSelected: date");
//                configureAndShowAlertDate();
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

        //Utils.initRoomSpinner(view, spinner);
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

        builder.setTitle("Choisir une salle")
                .setView(view)
                .setPositiveButton("Filtrer",
                        (dialog, which) -> {
                    ArrayList<Meeting> rooms = DI.getApiService().filter(itemName);
                            Log.d(TAG, "configureAndShowAlertDialog: " + itemName);
                            //initList(rooms);
                            Log.d(TAG, "roomfilter: " + rooms);
                        })
                .setNegativeButton("Annuler",
                        (dialog, which) -> {
                        });

        builder.create().show();
    }

    public void initList(){
        RecyclerView recyclerView = findViewById(R.id.list_meetings);
        mApiService = DI.getApiService();
        mMeetings = mApiService.getMeetings();
        mListAdapter = new MeetingsListAdapter(mMeetings);
        recyclerView.setAdapter(mListAdapter);
    }
//    private void configureAndShowAlertDate() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//
//        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.filter_list_dialog, null);
//
//        Spinner spinner = view.findViewById(R.id.spinner_choice);
//        //List<String> arrayList= Utils.initSpinnerDate(mReunions);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                itemName = spinner.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//    }
}