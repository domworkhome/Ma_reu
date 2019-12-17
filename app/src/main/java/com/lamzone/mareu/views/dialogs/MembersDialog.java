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
import com.lamzone.mareu.services.DummyGenerator;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class MembersDialog extends DialogFragment {

    public void setItemsAlreadyChecked(boolean[] itemsAlreadyChecked){
        this.itemsAlreadyChecked = itemsAlreadyChecked;
    }

    boolean[] itemsAlreadyChecked;

    public interface SelectedMembers {
        void onMembersPositiveButtonClick(String selectedMembers);
    }

    SelectedMembers callback;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        List<String >meetingMembers = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choisissez les participants");
        String[]membersList = DummyGenerator.dummyMembersGenerator().toArray(new String[0]);
        itemsAlreadyChecked = new boolean[membersList.length];
        builder.setMultiChoiceItems(membersList, itemsAlreadyChecked, (dialog, which, isChecked) -> {
            if(isChecked){
                meetingMembers.add(membersList[which]);
            } else meetingMembers.remove(membersList[which]);
        });

        builder.setPositiveButton(R.string.add_button_french, (dialog, which) -> {
            String item = "";
            for(String listItem : meetingMembers){
                item = item + listItem;
                if(which != meetingMembers.size() -1){
                    item = item + ", ";
                }
            }
           callback.onMembersPositiveButtonClick(item);
        });

        builder.setNegativeButton(R.string.cancel_button_french, (dialog, which) -> {
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
