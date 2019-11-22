package com.lamzone.mareu.Views.Dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lamzone.mareu.Controllers.DI.DI;
import com.lamzone.mareu.Models.Meeting;
import com.lamzone.mareu.Models.MeetingRoom;
import com.lamzone.mareu.Models.Member;
import com.lamzone.mareu.R;
import com.lamzone.mareu.Services.DummyGenerator;
import com.lamzone.mareu.Views.Adapters.MeetingsListAdapter;
import com.lamzone.mareu.Views.Adapters.MultipleCheckboxSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * // Created by Stéphane TAILLET on 26/10/2019
 */
public class AddMeetingDialog extends DialogFragment {

    //FIELDS
    private Spinner roomSpinner;
    private MultipleCheckboxSpinner membersSpinner;
    private ImageView roomSpinnerPic;

    private ImageView iv_date;
    private TextView tv_date;
    private ImageView iv_time;
    private TextView tv_time;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    List<MeetingRoom> listMeetingRoomNameAndPic;
    private MeetingRoom meetingRoomNameOrPic;

    List<String> listMembers;
    private String meetingMembers;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_add_meeting, null);


        // DATE PICKER -----------------------------------------------------------------------------
        iv_date = view.findViewById(R.id.iv_datepicker);
        tv_date = view.findViewById(R.id.tv_datepicker);

        iv_time = view.findViewById(R.id.iv_timepicker);
        tv_time = view.findViewById(R.id.tv_timepicker);

        iv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                tv_date.setText(date);
            }
        };

        // DATE PICKER -----------------------------------------------------------------------------

        // TIME PICKER -----------------------------------------------------------------------------

        iv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                hour = cal.get(Calendar.HOUR);
                minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener,
                        hour,minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Calendar datetime = Calendar.getInstance();
                Calendar c = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, hour);
                datetime.set(Calendar.MINUTE, minute);
                if (datetime.getTimeInMillis() >= c.getTimeInMillis()) {
                    //it's after current
                    hour = hour % 24;
                    tv_time.setText(String.format("%02d:%02d", hour, minute));
                } else {
                    //it's before current'
                    Toast.makeText(getContext(), "Invalid Time", Toast.LENGTH_LONG).show();
                }
                //tv_time.setText(String.format("%02d:%02d",hour, minute));
            }
        };

        // TIME PICKER -----------------------------------------------------------------------------

        // SPINNER TO CHOOSE MEETINGROOM -----------------------------------------------------------

        roomSpinner = view.findViewById(R.id.room_spinner);
        roomSpinnerPic = view.findViewById(R.id.spinner_meeting_room_icon);
        listMeetingRoomNameAndPic = DummyGenerator.dummyMeetingRoomAndPicGenerator();

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listMeetingRoomNameAndPic);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        roomSpinner.setAdapter(adapter);

        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                meetingRoomNameOrPic = (MeetingRoom) roomSpinner.getSelectedItem();
                roomSpinnerPic.setImageResource(meetingRoomNameOrPic.getMeetingRoomPic());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // SPINNER TO CHOOSE MEETINGROOM -----------------------------------------------------------

        // SPINNER TO CHOOSE MEMBERS ---------------------------------------------------------------

        membersSpinner = (MultipleCheckboxSpinner) view.findViewById(R.id.members_spinner);
        listMembers = DummyGenerator.dummyMembersGenerator();

        membersSpinner.setItems(listMembers);

        membersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                meetingMembers = (String) membersSpinner.getSelectedItemsAsString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // SPINNER TO CHOOSE MEMBERS ---------------------------------------------------------------

        builder.setView(view);

        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText mMeetingTopic = view.findViewById(R.id.meeting_topic);

                String addMeetingTopic = mMeetingTopic.getText().toString();

                String meetingDate = tv_date.getText().toString();

                String meetingTime = tv_time.getText().toString();

                Meeting newMeeting = new Meeting (addMeetingTopic, meetingDate, meetingTime, meetingRoomNameOrPic, meetingMembers, meetingRoomNameOrPic);

                DI.getApiService().addMeeting(newMeeting);
            }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        return alertDialog;
    }
}