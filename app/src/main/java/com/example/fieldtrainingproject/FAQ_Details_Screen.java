package com.example.fieldtrainingproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
public class FAQ_Details_Screen extends Fragment {

    private String title, content;

    public FAQ_Details_Screen() {}

    public static FAQ_Details_Screen newInstance(String title, String content) {
        FAQ_Details_Screen fragment = new FAQ_Details_Screen();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("content", content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");
            content = getArguments().getString("content");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_f_a_q__details__screen, container, false);

        TextView titleView = view.findViewById(R.id.titleTextView);
        TextView contentView = view.findViewById(R.id.bioContent);

        titleView.setText(title);
        contentView.setText(content);

        return view;
    }
}