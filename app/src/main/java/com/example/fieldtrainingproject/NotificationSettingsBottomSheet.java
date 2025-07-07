package com.example.fieldtrainingproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NotificationSettingsBottomSheet extends BottomSheetDialogFragment {

    private Switch generalNotificationsSwitch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_notification_settings, container, false);

        generalNotificationsSwitch = view.findViewById(R.id.switch_general_notifications);


        generalNotificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(getContext(), "General Notifications ON", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "General Notifications OFF", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}