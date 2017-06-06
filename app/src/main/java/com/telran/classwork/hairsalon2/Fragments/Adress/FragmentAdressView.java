package com.telran.classwork.hairsalon2.Fragments.Adress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telran.classwork.hairsalon2.R;

/**
 * Created by vadim on 02.04.2017.
 */

public class FragmentAdressView extends Fragment {
    private static final String ADRESS = "adress";
    private static final String NOTE = "note";


    private String adress,note;


    private TextView adressTxt, noteTxt;


    public FragmentAdressView() {
        // Required empty public constructor
    }

    public static FragmentAdressView newInstance(String adress, String note) {
        FragmentAdressView fragment = new FragmentAdressView();
        Bundle args = new Bundle();
        args.putString(ADRESS, adress);
        args.putString(NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            adress = getArguments().getString(ADRESS, "");
            note = getArguments().getString(NOTE, "");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adress_list_row, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adressTxt = (TextView) view.findViewById(R.id.adress_txt);
        noteTxt = (TextView) view.findViewById(R.id.note_txt) ;
        adressTxt.setText(adress);
        noteTxt.setText(note);
    }
}