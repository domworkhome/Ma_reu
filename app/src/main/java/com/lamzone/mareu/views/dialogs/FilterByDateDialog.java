package com.lamzone.mareu.views.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import com.lamzone.mareu.R;
import com.lamzone.mareu.di.Di;
import com.lamzone.mareu.models.Meeting;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import static android.support.constraint.Constraints.TAG;

public class FilterByDateDialog extends DialogFragment{

    @BindView(R.id.tv_filterdatepicker)
    TextView tvFilterDatePicker;

    public interface filterByDate {
        void onDateFilterButtonClick(List<Meeting> meetingList);
    }

    filterByDate callback;

    public void setFilterByDateCallback(filterByDate callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), mDateDataSet,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                datePickerDialog.setCancelable(false);
            return datePickerDialog;
    }

    private final DatePickerDialog.OnDateSetListener mDateDataSet = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth ) {
            month = month+1;
            List<Meeting> filteredMeetings = Di.getApiService().filter(dayOfMonth+"/"+ month +"/" + year);
            callback.onDateFilterButtonClick(filteredMeetings);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (FilterByDateDialog.filterByDate) getActivity();
        } catch (ClassCastException e){
            Log.d(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }
}