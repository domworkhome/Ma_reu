package com.lamzone.mareu.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import com.lamzone.mareu.R;
import com.lamzone.mareu.di.Di;
import com.lamzone.mareu.models.Meeting;
import com.lamzone.mareu.models.MeetingRoom;
import com.lamzone.mareu.services.DummyGenerator;
import java.util.List;
import static android.support.constraint.Constraints.TAG;

public class FilterByRoomDialog extends DialogFragment {

    public interface filterByRoom {
        void onRoomFilterButtonClick(List<Meeting> meetingList);
    }

    filterByRoom callback;

    public void setFilterByRoomCallback(filterByRoom callback) {
        this.callback = callback;
    }

    List<MeetingRoom> listMeetingRoomNameAndPic;
    private ImageView roomSpinnerPic;
    String itemName = "";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.filter_list_by_room_dialog, null);
        Spinner spinner = view.findViewById(R.id.spinner_choice);
        roomSpinnerPic = view.findViewById(R.id.spinner_meeting_room_icon);
        listMeetingRoomNameAndPic = DummyGenerator.dummyMeetingRoomAndPicGenerator();
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listMeetingRoomNameAndPic);
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
                            List<Meeting> filteredMeetings = Di.getApiService().filter(itemName);
                            callback.onRoomFilterButtonClick(filteredMeetings);
                        })
                .setNegativeButton(getString(R.string.cancel_button_french),
                        (dialog, which) -> {});

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (FilterByRoomDialog.filterByRoom) getActivity();
        } catch (ClassCastException e){
            Log.d(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }
}