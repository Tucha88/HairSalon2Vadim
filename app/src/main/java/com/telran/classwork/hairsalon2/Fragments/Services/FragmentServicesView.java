package com.telran.classwork.hairsalon2.Fragments.Services;

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

public class FragmentServicesView extends Fragment {
    private static final String SERVICE = "service";
    private static final String PRICE = "price";
    private static final String TIME = "time";

    private String service;
    private String price;
    private String time;

    private TextView serviceTxt, priceTxt, timeTxt;


    public FragmentServicesView() {
        // Required empty public constructor
    }

    public static FragmentServicesView newInstance(String service, String price, String time) {
        FragmentServicesView fragment = new FragmentServicesView();
        Bundle args = new Bundle();
        args.putString(SERVICE, service);
        args.putString(PRICE, price);
        args.putString(TIME, time);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            service = getArguments().getString(SERVICE,"");
            price = getArguments().getString(PRICE,"");
            time = getArguments().getString(TIME,"");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_services_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        serviceTxt = (TextView) view.findViewById(R.id.services_txt);
        priceTxt = (TextView) view.findViewById(R.id.price_txt);
        timeTxt = (TextView) view.findViewById(R.id.time_txt);
        serviceTxt.setText(service);
        priceTxt.setText(price);
        timeTxt.setText(time);
    }
}
