package com.lamzone.mareu.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import com.lamzone.mareu.R;
import java.util.ArrayList;
import java.util.List;
import static android.support.constraint.Constraints.TAG;

public class MembersDialog extends DialogFragment {

    private List<String> meetingMembers;

    public interface SelectedMembers {
        void onMembersPositiveButtonClick(String selectedMembers);
    }
    SelectedMembers callback;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        meetingMembers = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choisissez les participants");
        builder.setMultiChoiceItems(R.array.members, null, (dialog, which, isChecked) -> {
            String[] items = getContext().getResources().getStringArray(R.array.members);
            if(isChecked){
                meetingMembers.add(items[which]);
            } else if(meetingMembers.contains(items[which])){
                meetingMembers.remove(items[which]);
            }
        });

        builder.setPositiveButton("OK", (dialog, which) -> {
            String final_selection = "";
            for(String Item : meetingMembers){
                final_selection = Item + "@lamzone.com, " + final_selection;
            }
           callback.onMembersPositiveButtonClick(final_selection);
        });

        builder.setNegativeButton("ANNULER", (dialog, which) -> {
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (SelectedMembers) getTargetFragment();
        } catch (ClassCastException e){
            Log.d(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }
}
