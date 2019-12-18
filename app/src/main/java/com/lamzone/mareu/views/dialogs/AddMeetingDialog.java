package com.lamzone.mareu.views.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.lamzone.mareu.R;
import com.lamzone.mareu.di.Di;
import com.lamzone.mareu.models.Meeting;
import com.lamzone.mareu.models.MeetingRoom;
import com.lamzone.mareu.services.DummyGenerator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import static android.support.constraint.Constraints.TAG;

public class AddMeetingDialog extends DialogFragment implements MembersDialog.SelectedMembers{

    @BindView(R.id.meeting_topic)
    EditText meeting_topic;
    @BindView(R.id.tv_datepicker)
    TextView tvDatePicker;
    @BindView(R.id.room_spinner)
    Spinner roomSpinner;
    @BindView(R.id.spinner_meeting_room_icon)
    ImageView roomSpinnerPic;
    @BindView(R.id.members_spinner)
    TextView membersSpinner;
    private MeetingRoom meetingRoomNameOrPic;
    private SimpleDateFormat mSimpleDateFormat;
    private Calendar mCalendar;
    private final static String EMPTY_STRING = "";
    public static final int REQUEST_CODE = 999;
    final static long formatedDate = 24*60*60*1000;
    List<MeetingRoom> listMeetingRoomNameAndPic;

    public interface NewMeetingDatasListener {
        void onPositiveButtonClick();
    }
    NewMeetingDatasListener callback;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_meeting, null);
        ButterKnife.bind(this, dialogView);

        listMeetingRoomNameAndPic = DummyGenerator.dummyMeetingRoomAndPicGenerator();
        mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault());

        final View.OnClickListener dateListener = view -> {
            mCalendar = Calendar.getInstance();
            if(getContext() == null) return;
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), mDateDataSet,
                    mCalendar.get(Calendar.YEAR),
                    mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + formatedDate);
            datePickerDialog.show();
        };
        tvDatePicker.setOnClickListener(dateListener);

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,
                listMeetingRoomNameAndPic);
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

        membersSpinner.setOnClickListener(v -> {
            MembersDialog membersDialog = new MembersDialog();
            membersDialog.setItemsAlreadyChecked(null);
            membersDialog.setTargetFragment(AddMeetingDialog.this, REQUEST_CODE);
            membersDialog.show(getFragmentManager().beginTransaction(),"addmeetingdialog");
        });

        builder.setView(dialogView);
        builder.setPositiveButton(R.string.add_button_french, null);
        builder.setNegativeButton(getString(R.string.cancel_button_french), (dialog, which) -> {
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        alertDialog.show();

        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(v -> {
            String meetingMembers = membersSpinner.getText().toString();
            String addMeetingTopic = meeting_topic.getText().toString();
            String meetingDate = tvDatePicker.getText().toString();
            if (addMeetingTopic.equals(EMPTY_STRING)
                    || meetingDate.equals(EMPTY_STRING)
                    || meetingMembers.equals(EMPTY_STRING)) {
                Toast toast = Toast.makeText(getContext(),getString(R.string.Fill_all_fields),Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                Meeting newMeeting = new Meeting(addMeetingTopic, meetingDate, meetingRoomNameOrPic, meetingMembers);
                Di.getApiService().addMeeting(newMeeting);
                callback.onPositiveButtonClick();
                alertDialog.dismiss();
            }
        });
        return alertDialog;
    }

    private final DatePickerDialog.OnDateSetListener mDateDataSet = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth ) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            new  TimePickerDialog(getContext(), mTimeDataSet, mCalendar.get(Calendar.HOUR_OF_DAY),
                    mCalendar.get(Calendar.MINUTE), true).show();
        }
    };

    private final TimePickerDialog.OnTimeSetListener mTimeDataSet = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCalendar.set(Calendar.MINUTE, minute);
            tvDatePicker.setText(mSimpleDateFormat.format(mCalendar.getTime()));
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (AddMeetingDialog.NewMeetingDatasListener) getTargetFragment();
        } catch (ClassCastException e){
            Log.d(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }

    @Override
    public void onMembersPositiveButtonClick(String selectedMembers) {
        membersSpinner.setText(selectedMembers);
    }
}