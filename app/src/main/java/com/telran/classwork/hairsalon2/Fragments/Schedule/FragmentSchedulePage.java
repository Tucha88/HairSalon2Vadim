package com.telran.classwork.hairsalon2.Fragments.Schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.telran.classwork.hairsalon2.R;

/**
 * Created by vadim on 26.04.2017.
 */

public class FragmentSchedulePage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private static final String TITLE = "title";

    private String title;

    public static FragmentSchedulePage newInstance(String title) {
        FragmentSchedulePage fragment = new FragmentSchedulePage();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView titleTxt = (TextView) view.findViewById(R.id.fragment_title);
        FrameLayout mainContainer = (FrameLayout) view.findViewById(R.id.fragment_main_container);
        titleTxt.setText(title);

    }

}
