package com.example.fieldtrainingproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Locale;

public class LanguagePreferenceBottomSheet extends BottomSheetDialogFragment {

    private LinearLayout arabicLayout;
    private LinearLayout englishLayout;
    private ImageView checkmarkArabic;
    private ImageView checkmarkEnglish;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "app_settings";
    private static final String KEY_LANGUAGE = "app_language";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_language_preference, container, false);

        arabicLayout = view.findViewById(R.id.language_arabic);
        englishLayout = view.findViewById(R.id.language_english);
        checkmarkArabic = view.findViewById(R.id.checkmark_arabic);
        checkmarkEnglish = view.findViewById(R.id.checkmark_english);

        sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        updateCheckmarks();

        arabicLayout.setOnClickListener(v -> {
            setAppLanguage("ar");
            Toast.makeText(getContext(), "Language set to Arabic", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        englishLayout.setOnClickListener(v -> {
            setAppLanguage("en");
            Toast.makeText(getContext(), "Language set to English", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        return view;
    }

    private void updateCheckmarks() {
        String currentLanguage = sharedPreferences.getString(KEY_LANGUAGE, "en");

        if (currentLanguage.equals("ar")) {
            checkmarkArabic.setVisibility(View.VISIBLE);
            checkmarkEnglish.setVisibility(View.INVISIBLE);
        } else {
            checkmarkArabic.setVisibility(View.INVISIBLE);
            checkmarkEnglish.setVisibility(View.VISIBLE);
        }
    }

    private void setAppLanguage(String languageCode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LANGUAGE, languageCode);
        editor.apply();

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());


        getActivity().recreate();
    }
}