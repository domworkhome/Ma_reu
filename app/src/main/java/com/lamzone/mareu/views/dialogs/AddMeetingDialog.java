package com.lamzone.mareu.views.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lamzone.mareu.R;
import com.lamzone.mareu.controllers.di.Di;
import com.lamzone.mareu.models.Meeting;
import com.lamzone.mareu.models.MeetingRoom;
import com.lamzone.mareu.services.DummyGenerator;
import com.lamzone.mareu.views.adapters.MeetingsListAdapter;
import com.lamzone.mareu.views.adapters.MultipleCheckboxSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * // Created by Stéphane TAILLET on 26/10/2019
 */
public class AddMeetingDialog extends DialogFragment {

    private Spinner roomSpinner;
    private MultipleCheckboxSpinner membersSpinner;
    private ImageView roomSpinnerPic;
    private ImageView iv_date;
    private String currentDate;
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
    private MeetingsListAdapter mListAdapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_meeting, null);

        iv_date = dialogView.findViewById(R.id.iv_datepicker);
        currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        tv_date = dialogView.findViewById(R.id.tv_datepicker);
        tv_date.setText(currentDate);

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
                        year, month, day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String chosendate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                tv_date.setText(chosendate.format("%02d/%02d/%02d", day, month, year));
            }
        };

        // ----------------------------------------------------------------------------- DATE PICKER

        // TIME PICKER -----------------------------------------------------------------------------
        iv_time = dialogView.findViewById(R.id.iv_timepicker);
        tv_time = dialogView.findViewById(R.id.tv_timepicker);
        iv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                hour = cal.get(Calendar.HOUR_OF_DAY);
                minute = cal.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener,
                        hour, minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Calendar datetime = Calendar.getInstance();
                Calendar c = Calendar.getInstance();
                datetime.set(Calendar.DAY_OF_MONTH, day);
                datetime.set(Calendar.MONTH, month);
                datetime.set(Calendar.YEAR, year);
                datetime.set(Calendar.HOUR_OF_DAY, hour);
                datetime.set(Calendar.MINUTE, minute);
                if (datetime.getTimeInMillis() >= c.getTimeInMillis()) {
                    //it's after current
                    hour = hour % 24;
                    tv_time.setText(String.format("%02d:%02d", hour, minute));
                } else {
                    //it's before current'
                    Toast.makeText(getContext(), "Choisir une heure future", Toast.LENGTH_LONG).show();
                }
            }
        };

        // ----------------------------------------------------------------------------- TIME PICKER

        // SPINNER TO CHOOSE MEETINGROOM -----------------------------------------------------------

        roomSpinner = dialogView.findViewById(R.id.room_spinner);
        roomSpinnerPic = dialogView.findViewById(R.id.spinner_meeting_room_icon);
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

        // ----------------------------------------------------------- SPINNER TO CHOOSE MEETINGROOM

        // SPINNER TO CHOOSE MEMBERS ---------------------------------------------------------------

        membersSpinner = (MultipleCheckboxSpinner) dialogView.findViewById(R.id.members_spinner);
        listMembers = DummyGenerator.dummyMembersGenerator();
        membersSpinner.setItems(listMembers);
        membersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // --------------------------------------------------------------- SPINNER TO CHOOSE MEMBERS
        builder.setView(dialogView);
        builder.setPositiveButton("Ajouter", null);
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        alertDialog.show();

        Button positiveButton = alertDialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meetingMembers = membersSpinner.getSelectedItemsAsString();
                EditText mMeetingTopic = dialogView.findViewById(R.id.meeting_topic);
                String addMeetingTopic = mMeetingTopic.getText().toString();
                String meetingDate = tv_date.getText().toString();
                String meetingTime = tv_time.getText().toString();
                if (addMeetingTopic != "" && meetingDate != "" && meetingTime != "" && meetingMembers != "") {
                    alertDialog.dismiss();
                    Meeting newMeeting = new Meeting(addMeetingTopic, meetingDate, meetingTime, meetingRoomNameOrPic, meetingMembers, meetingRoomNameOrPic);
                    Di.getApiService().addMeeting(newMeeting);

                } else {
                    android.support.v7.app.AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                            .setTitle(getString(R.string.addmeeting_fill_all_fields_french))
                            .setMessage(getString(R.string.addmeeting_fields_french))
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
            }
        });
        return alertDialog;
    }
}