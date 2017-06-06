package com.telran.classwork.hairsalon2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;
import com.telran.classwork.hairsalon2.Fragments.FragmentRegistration;
import com.telran.classwork.hairsalon2.Models.AuthReg;
import com.telran.classwork.hairsalon2.Models.Provider;
import com.telran.classwork.hairsalon2.Models.Token;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements FragmentRegistration.RegistrationListener {


    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView loginView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView registrationBtn,resultLogin;
    private ScrollView scrollView;
    private Button loginButton;
    private FrameLayout mContainer;
    private boolean isRegistration = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sPref = getSharedPreferences("AUTHLOG",MODE_PRIVATE);
            String token = sPref.getString("TOKEN",null);
         if (token!=null){
             startPrivateAccountActivity();
             }
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        resultLogin = (TextView) findViewById(R.id.result_login);
        loginView = (AutoCompleteTextView) findViewById(R.id.input_login);
        mContainer = (FrameLayout) findViewById(R.id.frame_container);
        scrollView = (ScrollView) findViewById(R.id.login_password_form);
        registrationBtn = (TextView) findViewById(R.id.registration_btn);
        registrationBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegistrationFragment();

            }
        });
        mPasswordView = (EditText) findViewById(R.id.input_password_login);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL){
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        loginButton = (Button) findViewById(R.id.login_btn);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO: START ASYNTask
                attemptLogin();

            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

    }



    private void showRegistrationFragment() {
        isRegistration = true;
        FragmentRegistration registration = new FragmentRegistration();
        registration.setCallBackListener(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, registration, "FRAG_REG")
                .commit();
        scrollView.setVisibility(View.INVISIBLE);
        loginButton.setVisibility(View.INVISIBLE);
        mContainer.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle("Registration");
    }


    private boolean attemptLogin() {
        if (mAuthTask != null) {
            return true;
        }
        loginView.setError(null);
        mPasswordView.setError(null);
        String login = loginView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password) && isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(login)) {
            loginView.setError(getString(R.string.error_field_required));
            focusView = loginView;
            cancel = true;
        } else if (!isLoginValid(login)) {
            loginView.setError(getString(R.string.error_invalid_login));
            focusView = loginView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(login, password);
            mAuthTask.execute((Void) null);
        }
        return true;
    }


    private boolean isLoginValid(String login) {
        //TODO: Replace this with your own logic
        return login.contains("");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length()>5;
    }

    @Override
    public void onBackPressed() {
        if (isRegistration){
            FragmentRegistration registration = (FragmentRegistration) getSupportFragmentManager().findFragmentByTag("FRAG_REG");
            if (registration!=null){
                getSupportFragmentManager().beginTransaction()
                        .remove(registration)
                        .commit();
            }
            mContainer.setVisibility(View.GONE);
            mLoginFormView.setVisibility(View.VISIBLE);
            isRegistration = false;
            getSupportActionBar().setTitle("Login");

        }else {
            super.onBackPressed();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            if (isRegistration)
                mContainer.setVisibility(show ? View.VISIBLE : View.GONE);
            else
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void registrationFragCallBack(String login, String password) {
        showProgress(true);
        new UserLoginTask(login,password).execute();
    }
    private void startPrivateAccountActivity() {
        Intent intent = new Intent(LoginActivity.this, PrivateAccountActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public class UserLoginTask extends AsyncTask<Void, Void, String> {
        private final String path = "/login/login";
        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected String doInBackground(Void... params) {
//           TODO: Request to server
            String result = "Login ok!";
            AuthReg auth = new AuthReg(mEmail, mPassword);
            Gson gson = new Gson();
            String json = gson.toJson(auth);
            try {
                Response response = Provider.getInstance().post(path, json, "");
                if (response.code() < 400) {
                    String responseBody = response.body().string();
                    if (!responseBody.isEmpty()) {
                        Token token = gson.fromJson(responseBody, Token.class);
                        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("AUTHLOG", Context.MODE_PRIVATE);
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
        protected void onPostExecute(final String success) {
            mAuthTask = null;
            showProgress(false);
            resultLogin.setText(success);
            if (success != null) {
                SharedPreferences sPref = getSharedPreferences("AUTH",MODE_PRIVATE);
                SharedPreferences.Editor editor = sPref.edit();
                editor.putString("TOKEN",success.toString());
                editor.apply();
                if (resultLogin.getText().toString().equals("Login ok!")) {
                    startPrivateAccountActivity();
                }else {
                    Toast.makeText(LoginActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }

            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

}