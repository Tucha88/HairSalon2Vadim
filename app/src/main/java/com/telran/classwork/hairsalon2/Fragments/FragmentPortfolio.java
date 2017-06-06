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

import com.telran.classwork.hairsalon2.R;

import java.io.IOException;

/**
 * Created by vadim on 02.03.2017.
 */

public class FragmentPortfolio extends Fragment implements View.OnClickListener {
    private ImageView photo1, photo2, photo3, photo4, photo5, photo6, photo7, photo8, photo9, photo10, photo11, photo12;
    public static final int REQUEST_1 = 1;
    public static final int REQUEST_2 = 2;
    public static final int REQUEST_3 = 3;
    public static final int REQUEST_4 = 4;
    public static final int REQUEST_5 = 5;
    public static final int REQUEST_6 = 6;
    public static final int REQUEST_7 = 7;
    public static final int REQUEST_8 = 8;
    public static final int REQUEST_9 = 9;
    public static final int REQUEST_10 = 10;
    public static final int REQUEST_11 = 11;
    public static final int REQUEST_12 = 12;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        photo1 = (ImageView) view.findViewById(R.id.photo_1);
        photo2 = (ImageView) view.findViewById(R.id.photo_2);
        photo3 = (ImageView) view.findViewById(R.id.photo_3);
        photo4 = (ImageView) view.findViewById(R.id.photo_4);
        photo5 = (ImageView) view.findViewById(R.id.photo_5);
        photo6 = (ImageView) view.findViewById(R.id.photo_6);
        photo7 = (ImageView) view.findViewById(R.id.photo_7);
        photo8 = (ImageView) view.findViewById(R.id.photo_8);
        photo9 = (ImageView) view.findViewById(R.id.photo_9);
        photo10 = (ImageView) view.findViewById(R.id.photo_10);
        photo11 = (ImageView) view.findViewById(R.id.photo_11);
        photo12 = (ImageView) view.findViewById(R.id.photo_12);
        photo1.setOnClickListener(this);
        photo2.setOnClickListener(this);
        photo3.setOnClickListener(this);
        photo4.setOnClickListener(this);
        photo5.setOnClickListener(this);
        photo6.setOnClickListener(this);
        photo7.setOnClickListener(this);
        photo8.setOnClickListener(this);
        photo9.setOnClickListener(this);
        photo10.setOnClickListener(this);
        photo11.setOnClickListener(this);
        photo12.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.photo_1) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/1");
            startActivityForResult(photoPickerIntent, REQUEST_1);
        } else if (view.getId() == R.id.photo_2) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/2");
            startActivityForResult(photoPickerIntent, REQUEST_2);
        } else if (view.getId() == R.id.photo_3) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/3");
            startActivityForResult(photoPickerIntent, REQUEST_3);
        } else if (view.getId() == R.id.photo_4) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/4");
            startActivityForResult(photoPickerIntent, REQUEST_4);
        } else if (view.getId() == R.id.photo_5) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/5");
            startActivityForResult(photoPickerIntent, REQUEST_5);
        } else if (view.getId() == R.id.photo_6) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/6");
            startActivityForResult(photoPickerIntent, REQUEST_6);
        } else if (view.getId() == R.id.photo_7) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/7");
            startActivityForResult(photoPickerIntent, REQUEST_7);
        } else if (view.getId() == R.id.photo_8) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/8");
            startActivityForResult(photoPickerIntent, REQUEST_8);
        } else if (view.getId() == R.id.photo_9) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/9");
            startActivityForResult(photoPickerIntent, REQUEST_9);
        } else if (view.getId() == R.id.photo_10) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/10");
            startActivityForResult(photoPickerIntent, REQUEST_10);
        } else if (view.getId() == R.id.photo_11) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/11");
            startActivityForResult(photoPickerIntent, REQUEST_11);
        } else if (view.getId() == R.id.photo_12) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/12");
            startActivityForResult(photoPickerIntent, REQUEST_12);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;

        switch (requestCode) {
            case REQUEST_1:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    photo1.setImageBitmap(bitmap);
                    break;
                }
            case REQUEST_2:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    photo2.setImageBitmap(bitmap);
                    break;
                }
            case REQUEST_3:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    photo3.setImageBitmap(bitmap);
                    break;
                }
            case REQUEST_4:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    photo4.setImageBitmap(bitmap);
                    break;
                }
            case REQUEST_5:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    photo5.setImageBitmap(bitmap);
                    break;
                }
            case REQUEST_6:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    photo6.setImageBitmap(bitmap);
                    break;
                }
            case REQUEST_7:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    photo7.setImageBitmap(bitmap);
                    break;
                }
            case REQUEST_8:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    photo8.setImageBitmap(bitmap);
                    break;
                }
            case REQUEST_9:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    photo9.setImageBitmap(bitmap);
                    break;
                }
            case REQUEST_10:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    photo10.setImageBitmap(bitmap);
                    break;
                }
            case REQUEST_11:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    photo11.setImageBitmap(bitmap);
                    break;
                }
            case REQUEST_12:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    photo12.setImageBitmap(bitmap);
                    break;
                }

        }
    }
}
