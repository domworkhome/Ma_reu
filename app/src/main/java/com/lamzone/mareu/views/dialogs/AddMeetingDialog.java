package com.lamzone.mareu.views.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lamzone.mareu.R;
import com.lamzone.mareu.controllers.di.Di;
import com.lamzone.mareu.models.Meeting;
import com.lamzone.mareu.models.MeetingRoom;
import com.lamzone.mareu.services.DummyGenerator;
import com.lamzone.mareu.views.adapters.MultipleCheckboxSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;

/**
 * // Created by Stéphane TAILLET on 26/10/2019
 */
public class AddMeetingDialog extends DialogFragment {

    private Spinner roomSpinner;
    private MultipleCheckboxSpinner membersSpinner;
    private ImageView roomSpinnerPic;
    ImageView ivDate;
    private TextView tvDate;
    ImageView ivTime;
    private TextView tvTime;
    private final static String EMPTY_STRING = "";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private MeetingRoom meetingRoomNameOrPic;
    List<MeetingRoom> listMeetingRoomNameAndPic;
    List<String> listMembers;

    NewMeetingDatasListener callback;

    public void setNewMeetingDatasListener(NewMeetingDatasListener callback){
        this.callback = callback;
    }

    public interface NewMeetingDatasListener {
        void onPositiveButtonClick(Meeting meetingCreated);
    }

    public NewMeetingDatasListener newMeetingDatasListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_meeting, null);

        ivDate = dialogView.findViewById(R.id.iv_datepicker);
        tvDate = dialogView.findViewById(R.id.tv_datepicker);
        ivDate.setOnClickListener(dateListener);

        mDateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String chosendate = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault()).format(new Date());
            tvDate.setText(chosendate.format("%02d/%02d/%02d", day, month, year));
            chosendate = tvDate.getText().toString();

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.FRENCH);
            String currentDate = simpleDateFormat.format(calendar.getTime());

            if (chosendate.compareTo(currentDate) > 0) {
                Log.d(TAG, "onDateSet: La date choisie :" + chosendate + " est future à la date actuelle : " + currentDate);
            }
        };

        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        ivTime = dialogView.findViewById(R.id.iv_timepicker);
        tvTime = dialogView.findViewById(R.id.tv_timepicker);
        if (cal.get(Calendar.DATE) != Calendar.getInstance().get(Calendar.DATE)) {
            tvTime.setText(cal.get(Calendar.HOUR_OF_DAY) + getString(R.string.time_separator) + cal.get(Calendar.MINUTE));
        } else {
        }

        ivTime.setOnClickListener(timeListener);

        mTimeSetListener = (timePicker, hour, minute) -> {
            Calendar datetime = Calendar.getInstance();
            Calendar c = Calendar.getInstance();
            datetime.set(Calendar.DAY_OF_MONTH, day);
            datetime.set(Calendar.MONTH, month);
            datetime.set(Calendar.YEAR, year);
            datetime.set(Calendar.HOUR_OF_DAY, hour);
            datetime.set(Calendar.MINUTE, minute);
            if (tvDate.getText().toString().equals("")) {
                Toast toast = Toast.makeText(getContext(), "Veuillez d'abord choisir une date pour la réunion", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else if (datetime.getTimeInMillis() >= c.getTimeInMillis()) {
                hour = hour % 24;
                tvTime.setText(String.format("%02d:%02d", hour, minute));
            } else {
                Toast toast = Toast.makeText(getContext(), "Choisissez une heure future", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                tvTime.setText(EMPTY_STRING);
            }
        };

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

        membersSpinner = dialogView.findViewById(R.id.members_spinner);
        listMembers = DummyGenerator.dummyMembersGenerator();
        membersSpinner.setItems(listMembers);
        membersSpinner.setSelection(5);
        membersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        builder.setView(dialogView);
        builder.setPositiveButton("Ajouter", null);
        builder.setNegativeButton("Annuler", (dialog, which) -> {
        });

        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        alertDialog.show();

        Button positiveButton = alertDialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(v -> {
            String meetingMembers = membersSpinner.getSelectedItemsAsString();
            EditText mMeetingTopic = dialogView.findViewById(R.id.meeting_topic);
            String addMeetingTopic = mMeetingTopic.getText().toString();
            String meetingDate = tvDate.getText().toString();
            String meetingTime = tvTime.getText().toString();
            if (addMeetingTopic.equals(EMPTY_STRING)
                    || meetingTime.equals(EMPTY_STRING)
                    || meetingMembers.equals(EMPTY_STRING)) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle(getString(R.string.addmeeting_fill_all_fields_french))
                        .setMessage(getString(R.string.addmeeting_fields_french))
                        .setPositiveButton("OK", (dialog1, which) -> {
                        })
                        .show();
            } else {
                Meeting newMeeting = new Meeting(addMeetingTopic, meetingDate, meetingTime, meetingRoomNameOrPic, meetingMembers, meetingRoomNameOrPic);
                Di.getApiService().addMeeting(newMeeting);
                newMeetingDatasListener.onPositiveButtonClick(newMeeting);
                alertDialog.dismiss();
            }
        });
        return alertDialog;
    }

    View.OnClickListener dateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dateDialog = new DatePickerDialog(
                    getContext(),
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year, month, day);
            dateDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dateDialog.show();
        }
    };

    View.OnClickListener timeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TimePickerDialog timeDialog = new TimePickerDialog(
                    getContext(),
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mTimeSetListener,
                    hour, minute, true);
            timeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            timeDialog.show();
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            newMeetingDatasListener = (NewMeetingDatasListener) getTargetFragment();
        } catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassException : " + e.getMessage());
        }
    }
}