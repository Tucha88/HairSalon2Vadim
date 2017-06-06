package com.telran.classwork.hairsalon2.Fragments.Adress;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.okhttp.Response;
import com.telran.classwork.hairsalon2.Models.Adress;
import com.telran.classwork.hairsalon2.Models.HttpProvider;
import com.telran.classwork.hairsalon2.Models.Provider;
import com.telran.classwork.hairsalon2.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim on 02.04.2017.
 */

public class FragmentAdressAdd extends Fragment {
    private EditText inputAdress,inputNote;
    private FloatingActionButton saveBtn;
    private AddAdressItemTask addItemTask;
    private ProgressBar addItemProgress;
    private GoogleMap mMap;
    private FragmentAdressAdd.AddAdressItemFragmentListener mListener;
    private Button searchAdress;
    public FragmentAdressAdd() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adress_add, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(-34, 151);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            }
        });
        searchAdress = (Button) view.findViewById(R.id.search_adress);
        searchAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearch();
            }
        });
        inputAdress = (EditText) view.findViewById(R.id.input_adress);
        inputNote = (EditText) view.findViewById(R.id.input_note);
        addItemProgress = (ProgressBar) view.findViewById(R.id.add_item_progress);
        saveBtn = (FloatingActionButton) view.findViewById(R.id.add_adress_btn);
        final String address = inputAdress.getText().toString();
        final String note = inputNote.getText().toString();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemTask = new AddAdressItemTask(address,note,getContext());
                addItemTask.execute();
            }
        });
    }

    private void onSearch() {
        String location = inputAdress.getText().toString();
        List<Address> adressesSearch = null;
        if (location !=null || !location.equals("")){
            Geocoder geocoder = new Geocoder(getContext());
            try {
                adressesSearch =  geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address adresses = adressesSearch.get(0);
            LatLng latLng = new LatLng(adresses.getLatitude(), adresses.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentAdressAdd.AddAdressItemFragmentListener) {
            mListener = (FragmentAdressAdd.AddAdressItemFragmentListener) context;
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

    public interface AddAdressItemFragmentListener {
        void callback();
    }

    private class AddAdressItemTask extends AsyncTask<Void,Void,String> {
        private String adress, note ;
        private final String path = "/master/address";
        private Context context;

        public AddAdressItemTask(String adress, String note, Context context) {
            this.adress = adress;
            this.note = note;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adress = String.valueOf(inputAdress.getText());
            note = String.valueOf(inputNote.getText());
            inputAdress.setVisibility(View.INVISIBLE);
            inputNote.setVisibility(View.INVISIBLE);
            saveBtn.setVisibility(View.INVISIBLE);
            addItemProgress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {

            // TODO: 26.03.2017 Get token from shared preferences
            // TODO: 26.03.2017 Checking if asynctask was stopped!
            String result = "adress ok!";
            Adress adrs = new Adress(adress,note);
            HttpProvider.getInstanceAdr().addAdress(adrs);
            ArrayList<Adress> addresses = HttpProvider.getInstanceAdr().getAdrList();
            Gson gson = new Gson();
            String json = gson.toJson(addresses);
            try {
                SharedPreferences sPref = getActivity().getSharedPreferences("AUTHLOG",Context.MODE_PRIVATE);
                String token = sPref.getString("TOKEN",null);
                Log.d("TAG", "doInBackground: " +token);

                Response response = Provider.getInstance().put(path, json, token);
                if (response.code() < 400) {
                    String responseBody = response.body().string();
                    Log.d("TAG", "doInBackground: "+responseBody);
                    if (!responseBody.isEmpty()) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences("ADDRESS", Context.MODE_PRIVATE);
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
            inputAdress.setVisibility(View.VISIBLE);
            inputNote.setVisibility(View.VISIBLE);
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
            inputAdress.setVisibility(View.VISIBLE);
            inputNote.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.VISIBLE);
            addItemProgress.setVisibility(View.INVISIBLE);
        }
    }
}
