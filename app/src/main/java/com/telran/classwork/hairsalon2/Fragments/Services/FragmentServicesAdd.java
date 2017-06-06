package com.telran.classwork.hairsalon2.Fragments.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;
import com.telran.classwork.hairsalon2.Models.HttpProvider;
import com.telran.classwork.hairsalon2.Models.Provider;
import com.telran.classwork.hairsalon2.Models.Services;
import com.telran.classwork.hairsalon2.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vadim on 02.04.2017.
 */

public class FragmentServicesAdd extends Fragment {
    private EditText inputServices, inputPrice, inputTime;
    private FloatingActionButton saveBtn;
    private AddItemTask addItemTask;
    private ProgressBar addItemProgress;

    private AddItemFragmentListener mListener;

    public FragmentServicesAdd() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_services_add, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputServices = (EditText) view.findViewById(R.id.input_custom_serv);
        inputPrice = (EditText) view.findViewById(R.id.input_custom_price);
        inputTime = (EditText) view.findViewById(R.id.input_custom_time);
        addItemProgress = (ProgressBar) view.findViewById(R.id.add_item_progress);
        saveBtn = (FloatingActionButton) view.findViewById(R.id.fab_save_item);
        final String srv = inputServices.getText().toString();
        final String prc = inputPrice.getText().toString();
        final String tm = inputTime.getText().toString();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemTask = new AddItemTask(srv,prc,tm,getContext());
                addItemTask.execute();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddItemFragmentListener) {
            mListener = (AddItemFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AddItemFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        addItemTask = null;
    }

    public interface AddItemFragmentListener {
        void callback();
    }

    private class AddItemTask extends AsyncTask<Void,Void,String> {
        private String service, price, time ;
        private final String path = "/master/services";
        private Context context;

        public AddItemTask(String service, String price, String time, Context context) {
            this.service = service;
            this.price = price;
            this.time = time;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            service = String.valueOf(inputServices.getText());
            price = String.valueOf(inputPrice.getText());
            time = String.valueOf(inputTime.getText());
            inputServices.setVisibility(View.INVISIBLE);
            inputPrice.setVisibility(View.INVISIBLE);
            inputTime.setVisibility(View.INVISIBLE);
            saveBtn.setVisibility(View.INVISIBLE);
            addItemProgress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {

            // TODO: 26.03.2017 Get token from shared preferences
            // TODO: 26.03.2017 Checking if asynctask was stopped!
            String result = "adress ok!";
            Services serv = new Services(service,price,time);
            HttpProvider.getInstanceServ().addServices(serv);
            ArrayList<Services> servisses = HttpProvider.getInstanceServ().getServList();
            Gson gson = new Gson();
            String json = gson.toJson(servisses);
            try {

                SharedPreferences sPref = getActivity().getSharedPreferences("AUTHLOG",Context.MODE_PRIVATE);
                String token = "Bearer "+ sPref.getString("TOKEN",null);

                Response response = Provider.getInstance().post(path, json, token);
                if (response.code() < 400) {
                    String responseBody = response.body().string();
                    Log.d("TAG", "doInBackground: "+responseBody);
                    if (!responseBody.isEmpty()) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences("SERVICES", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.commit();
                    } else {
                        result = "Server did not answer!";
                    }
                } else if (response.code() == 409) {
                    result = "Error, User already exist!";
                } else {
                    String responseBody = response.body().string();
                    Log.d("TAG", "doInBackground: "+responseBody);
                    result = "Server error, call to support!";
                }
            } catch (IOException e) {
                e.printStackTrace();
                result = "Connection error!";


            }
            return result;
        }

        @Override
        protected void onPostExecute(final String aVoid) {
            super.onPostExecute(aVoid);
            inputServices.setVisibility(View.VISIBLE);
            inputPrice.setVisibility(View.VISIBLE);
            inputTime.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.VISIBLE);
            addItemProgress.setVisibility(View.INVISIBLE);
            if (mListener!=null){
                mListener.callback();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            addItemTask = null;
            inputServices.setVisibility(View.VISIBLE);
            inputPrice.setVisibility(View.VISIBLE);
            inputTime.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.VISIBLE);
            addItemProgress.setVisibility(View.INVISIBLE);
        }
    }
}
