package com.example.fieldtrainingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ShareBottomSheet extends BottomSheetDialogFragment {

    private String shareText;

    public ShareBottomSheet(String textToShare) {
        this.shareText = textToShare;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_share, container, false);

        view.findViewById(R.id.share_gmail).setOnClickListener(v -> shareViaApp("com.google.android.gm", "Gmail"));
        view.findViewById(R.id.share_facebook).setOnClickListener(v -> shareViaApp("com.facebook.katana", "Facebook"));
        view.findViewById(R.id.share_messenger).setOnClickListener(v -> shareViaApp("com.facebook.orca", "Messenger"));
        view.findViewById(R.id.share_whatsapp).setOnClickListener(v -> shareViaApp("com.whatsapp", "WhatsApp"));

        return view;
    }

    private void shareViaApp(String packageName, String appName) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        shareIntent.setPackage(packageName);

        try {
            startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), appName + " is not installed.", Toast.LENGTH_SHORT).show();

        }
        dismiss();
    }
}