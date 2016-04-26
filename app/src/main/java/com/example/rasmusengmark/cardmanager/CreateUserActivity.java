package com.example.rasmusengmark.cardmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUserActivity extends AppCompatActivity {

    Context context;
    public static SQLiteAdapter dbAdapter;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button btnCreateUser;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextAge;
    private EditText editTextCpr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        context = this;

        dbAdapter = new SQLiteAdapter(context);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initViews();
        initListeners();
    }

    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextCpr = (EditText) findViewById(R.id.editTextCpr);
        btnCreateUser = (Button) findViewById(R.id.btnCreateUser);
    }

    private void initListeners() {
        btnCreateUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dbAdapter.open();
                User user = new User();

                user.setPassword(editTextPassword.getText().toString());
                user.setFirstName(editTextFirstName.getText().toString());
                user.setLastName(editTextLastName.getText().toString());
                user.setAge(Integer.parseInt(editTextAge.getText().toString()));
                user.setCpr(editTextCpr.getText().toString());

                if (isEmailValid(editTextEmail.getText().toString())) {
                    user.setEmail(editTextEmail.getText().toString());
                }
                else{
                    Toast.makeText(context, "FEJL", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(context, LoginActivity.class));
                }

                dbAdapter.create(user);
                dbAdapter.close();

                Toast.makeText(context, "your user were successfully created", Toast.LENGTH_LONG).show();
                startActivity(new Intent(context, LoginActivity.class));
            }
        });
    }
/**
    private void attemptCreate() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }
 */
    private boolean isEmailValid(String email) {
        if (email.length() < 6){ // X@X.XX as minimum
            return false;
        }
        else if (!email.contains("@")) {
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isFirstNameValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}
