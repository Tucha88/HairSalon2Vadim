package com.telran.classwork.hairsalon2.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;
import com.telran.classwork.hairsalon2.Models.Auth;
import com.telran.classwork.hairsalon2.Models.Master;
import com.telran.classwork.hairsalon2.Models.MasterLanguage;
import com.telran.classwork.hairsalon2.Models.Provider;
import com.telran.classwork.hairsalon2.Models.Token;
import com.telran.classwork.hairsalon2.PrivateAccountActivity;
import com.telran.classwork.hairsalon2.R;

import java.io.IOException;

/**
 * Created by vadim on 19.02.2017.
 */

public class FragmentRegistration extends Fragment {

    private EditText inputName, inputLastName, inputEmail, inputPhone, inputPassword, inputReTypePassword;
    private Button nextBtnRegistration, languageBtn;
    private View progressViewReg;
    private View loginFormViewReg;
    private RadioButton men, women, all;
    private FrameLayout containerLang;
    private RegistrationListener listener;

    private Master currentMaster = new Master();
    private Handler handler;
    private TextView resTXT;


    private RegestrationLoginTask regAuthTask = null;

    public FragmentRegistration() {
    }
    public void setCallBackListener(RegistrationListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputName = (EditText) view.findViewById(R.id.input_name);
        inputLastName = (EditText) view.findViewById(R.id.input_last_name);
        inputEmail = (EditText) view.findViewById(R.id.input_email);
        inputPhone = (EditText) view.findViewById(R.id.input_phone_number);
        inputPassword = (EditText) view.findViewById(R.id.input_password);
        inputReTypePassword = (EditText) view.findViewById(R.id.input_password2);
        men = (RadioButton) view.findViewById(R.id.radio_men_btn);
        women = (RadioButton) view.findViewById(R.id.radio_women_btn);
        all = (RadioButton) view.findViewById(R.id.radio_all_btn);
        languageBtn = (Button) view.findViewById(R.id.language_btn);
        resTXT = (TextView) view.findViewById(R.id.res_txt);
        languageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Please select the language of communication");
                LayoutInflater inflater = LayoutInflater.from(builder.getContext());
                View dialog = inflater.inflate(R.layout.alert_language, null);
                final CheckBox rusLang = (CheckBox) dialog.findViewById(R.id.russian_check);
                final CheckBox hebLang = (CheckBox) dialog.findViewById(R.id.hebrew_check);
                final CheckBox engLang = (CheckBox) dialog.findViewById(R.id.english_check);
                builder.setView(dialog)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                if (rusLang.isChecked()) {
                                    currentMaster.addLangs(new MasterLanguage("Russian", "RUS"));
                                }
                                if (hebLang.isChecked()) {
                                    currentMaster.addLangs(new MasterLanguage("HEBREW", "HEB"));
                                }
                                if (engLang.isChecked()) {
                                    currentMaster.addLangs(new MasterLanguage("ENGLISH", "EN"));
                                }
                            }
                        });
                AlertDialog myDialog = builder.create();
                myDialog.show();

            }
        });
        nextBtnRegistration = (Button) view.findViewById(R.id.next_btn_registration);
        nextBtnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InspectionEntry();

//                TODO: next Fragment private account
            }
        });
        progressViewReg = view.findViewById(R.id.registration_progress);
        loginFormViewReg = view.findViewById(R.id.regestration_form);
        handler = new Handler();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginFormViewReg.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormViewReg.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormViewReg.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressViewReg.setVisibility(show ? View.VISIBLE : View.GONE);
            progressViewReg.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressViewReg.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progressViewReg.setVisibility(show ? View.VISIBLE : View.GONE);
            loginFormViewReg.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class RegestrationLoginTask extends AsyncTask<Void, Void, String> {
        private Context context;
        private String name, lastName, email, password, phoneNumber;

        private final String path = "/register/master";
        public RegestrationLoginTask(Context context, String name, String lastName, String email, String password, String phoneNumber) {
            this.context = context;
            this.name = name;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
            this.phoneNumber = phoneNumber;
        }

        @Override
        protected void onPreExecute() {
            name = String.valueOf(inputName.getText());
            lastName = String.valueOf(inputLastName.getText());
            email = String.valueOf(inputEmail.getText());
            password = String.valueOf(inputPassword.getText());
            phoneNumber = String.valueOf(inputPhone.getText());
            resTXT.setText("");
            showProgress(true);
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "Registration ok!";
            Auth auth = new Auth(name, lastName, email, password, phoneNumber);
            Gson gson = new Gson();
            String json = gson.toJson(auth);
            try {
                Response response = Provider.getInstance().post(path, json, "");
                if (response.code() < 400) {
                    String responseBody = response.body().string();
                    if (!responseBody.isEmpty()) {
                        Token token = gson.fromJson(responseBody, Token.class);
                        SharedPreferences sharedPreferences = context.getSharedPreferences("AUTH", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("TOKEN", token.getToken());
                        editor.commit();
                    } else {
                        result = "Server did not answer!";
                    }
                } else if (response.code() == 409) {
                    result = "Error, User already exist!";
                } else {
                    result = "Server error, call to support!";
                }
            } catch (IOException e) {
                e.printStackTrace();
                result = "Connection error!";


            }
            return result;
        }

        @Override
        protected void onPostExecute (final String success){

            regAuthTask = null;
            showProgress(false);
            resTXT.setText(success);
            if (success != null) {
                String[] langCodes = new String[currentMaster.getLangs().size()];
                int indx = 0;
                Intent intent = new Intent(getActivity().getApplicationContext(), PrivateAccountActivity.class);
                intent.putExtra("NAME", currentMaster.getName());
                intent.putExtra("LAST_NAME", currentMaster.getLastname());
                intent.putExtra("EMAIL", currentMaster.getEmail());
                intent.putExtra("PHONE", currentMaster.getPhone());
                for (MasterLanguage masterLang : currentMaster.getLangs()) {
                    langCodes[indx++] = masterLang.getLangCode();
                }
                intent.putExtra("LANGS", langCodes);
                intent.putExtra("PASSWORD", currentMaster.getPassword());
                intent.putExtra("RE_TYPE_PASSWORD", currentMaster.getReTypePassword());
                if (resTXT.getText().toString().equals("Registration ok!")) {
                    startActivity(intent);
                }else {

                }
            } else {

            }
        }

        @Override
        protected void onCancelled () {
            regAuthTask = null;
            showProgress(false);
        }
    }

    public void InspectionEntry() {

        inputName.setError(null);
        inputLastName.setError(null);
        inputEmail.setError(null);
        inputPhone.setError(null);
        inputPassword.setError(null);
        inputReTypePassword.setError(null);

        String name = inputName.getText().toString();
        String lastName = inputLastName.getText().toString();
        String email = inputEmail.getText().toString();
        String phone = inputPhone.getText().toString();
        String password = inputPassword.getText().toString();
        String rePassword = inputReTypePassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name) && isNameValid(name)) {
            inputName.setError(getString(R.string.error_name));
            focusView = inputName;
            cancel = true;
        } else if (TextUtils.isEmpty(lastName) && isLastNameValid(lastName)) {
            inputLastName.setError(getString(R.string.error_last_name));
            focusView = inputLastName;
            cancel = true;
        } else if (TextUtils.isEmpty(email)) {

            inputEmail.setError(getString(R.string.error_email));
            focusView = inputEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            inputEmail.setError(getString(R.string.error_invalid_email));
            focusView = inputEmail;
            cancel = true;

        } else if (TextUtils.isEmpty(phone)) {
            inputPhone.setError(getString(R.string.error_phone));
            focusView = inputPhone;
            cancel = true;

        } else if (!isPhoneValid(phone)) {
            inputPhone.setError(getString(R.string.error_invalid_phone));
            focusView = inputPhone;
            cancel = true;

        } else if (TextUtils.isEmpty(password) && isPasswordValid(password)) {
            inputPassword.setError(getString(R.string.error_password));
            focusView = inputPassword;
            cancel = true;
        } else if (TextUtils.isEmpty(rePassword) && isRePasswordValid(rePassword)) {
            inputReTypePassword.setError(getString(R.string.error_re_typepassword));
            focusView = inputReTypePassword;
            cancel = true;
        } else if (!isInvalidPassword()) {
            inputReTypePassword.setError(getString(R.string.error_invalid_second_password));
            focusView = inputReTypePassword;
            cancel = true;
        } else {
            currentMaster.setName(name);
            currentMaster.setEmail(email);
            currentMaster.setLastname(lastName);
            currentMaster.setPhone(phone);
            currentMaster.setPassword(password);
            currentMaster.setReTypePassword(rePassword);

            showProgress(true);
            regAuthTask = new RegestrationLoginTask(getContext(),name,lastName,email,password,phone);
            regAuthTask.execute();
        }
        return;

    }

    private boolean isNameValid(String name) {
        //TODO: Replace this with your own logic
        return name.contains("");
    }

    private boolean isLastNameValid(String lastName) {
        //TODO: Replace this with your own logic
        return lastName.contains("");
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPhoneValid(String phone) {
        //TODO: Replace this with your own logic
        return phone.contains("+972");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length()>5;
    }

    private boolean isRePasswordValid(String rePassword) {
        //TODO: Replace this with your own logic

        return rePassword.length()>5;
    }

    private boolean isInvalidPassword() {
        String pass1;
        String pass2;
        pass1 = String.valueOf(inputPassword.getText());
        pass2 = String.valueOf(inputReTypePassword.getText());
        if (pass2.equals(pass1)) {
            return true;
        }
        return false;
    }

    public interface RegistrationListener {
        void registrationFragCallBack(String login, String password);
    }

    class ErrorRequest implements Runnable {
        private String result;
        public ErrorRequest(String result){
            this.result = result;

        }

        @Override
        public void run() {
            showProgress(true);
            resTXT.setText(result);
        }
    }
    class ReguestOk implements Runnable{
        @Override
        public void run() {
            showProgress(false);
        }
    }
}
