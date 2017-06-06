package com.telran.classwork.hairsalon2.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.telran.classwork.hairsalon2.R;

import java.io.IOException;

/**
 * Created by vadim on 15.03.2017.
 */

public class FragmentPrivateAccount extends Fragment implements View.OnClickListener {
    private TextView nameMaster;
    private TextView lastNameMaster;
    private ImageView fotoMaster;
    public static final int REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_private_account, container, false);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameMaster = (TextView) view.findViewById(R.id.name_master_txt);
        lastNameMaster = (TextView) view.findViewById(R.id.last_name_master_txt);
        Intent intent = getActivity().getIntent();
        String name = intent.getStringExtra("NAME");
        String lastName = intent.getStringExtra("LAST_NAME");
        nameMaster.setText(name);
        lastNameMaster.setText(lastName);
        fotoMaster = (ImageView) view.findViewById(R.id.input_foto_master);
        fotoMaster.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;

        switch (requestCode) {
            case REQUEST:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fotoMaster.setImageBitmap(bitmap);


                }

        }
    }

}
