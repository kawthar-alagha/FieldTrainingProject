package com.example.fieldtrainingproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class TakeActionBottomSheet extends BottomSheetDialogFragment {

    private String phoneNumber;
    private String whatsappNumber;

    public TakeActionBottomSheet(String phoneNumber, String whatsappNumber) {
        this.phoneNumber = phoneNumber;
        this.whatsappNumber = whatsappNumber;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_take_action, container, false);

        view.findViewById(R.id.action_call).setOnClickListener(v -> {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
            } else {
                Toast.makeText(getContext(), "Phone number not available.", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });

        view.findViewById(R.id.action_send_sms).setOnClickListener(v -> {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("smsto:" + phoneNumber));
                startActivity(smsIntent);
            } else {
                Toast.makeText(getContext(), "Phone number not available for SMS.", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });

        view.findViewById(R.id.action_whatsapp).setOnClickListener(v -> {
            if (whatsappNumber != null && !whatsappNumber.isEmpty()) {
                try {
                    Uri uri = Uri.parse("whatsapp://send?phone=" + whatsappNumber);
                    Intent whatsappIntent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(whatsappIntent);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "WhatsApp is not installed.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "WhatsApp number not available.", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });

        return view;
    }
}