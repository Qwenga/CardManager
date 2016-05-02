package com.example.rasmusengmark.cardmanager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;
    private UserLoginTask mAuthTask = null;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    //Getting to create user panel
    private Button createUser;
    private Context context;
    public static SQLiteAdapter dbAdapter;
    public UserList userList;
    private Long nextId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Access to the database
        context = this;
        dbAdapter = new SQLiteAdapter(context);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        setSnackBarListener();
        toCreateUserPanel(); //Setting up the button and actions
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
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
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_password_required));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_email_required));
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
            userList = new UserList(context);

            showProgress(true);
            Long currentID = userList.verifyAccount(email, password);
            if(!(currentID == 0)) { // If user input is correct
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("userID", currentID);
                Toast.makeText(context, "You have logged in successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                this.finish();
            }
            else{ // If wrong user input
                showProgress(false);
                mPasswordView.setText("");
                mPasswordView.setError(getString(R.string.error_wrong_input));
                mEmailView.setError(getString(R.string.error_wrong_input));
                focusView = mPasswordView;
                focusView.requestFocus();
                Toast.makeText(context, "Wrong e-mail or password", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
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
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    // Method to set up the the button that jumps to create user panel
    public void toCreateUserPanel(){
        createUser = (Button) findViewById(R.id.btnCreateUserPanel);
        createUser.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateUserActivity.class);
                if (nextId != null) {
                    intent.putExtra("nextId", nextId);
                }
                startActivity(intent);
            }
        });
    }

    public void setSnackBarListener(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = mEmailView.getText().toString();
                View focusView = mEmailView;
                if (TextUtils.isEmpty(email)) {
                    mEmailView.setError(getString(R.string.error_email_required));
                    focusView.requestFocus();

                } else {
                    Snackbar.make(view, "We have sent you an e-mail with your password", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Long> {

        private final String email;
        private final String password;

        UserLoginTask(String email_, String password_) {
            email = email_;
            password = password_;
        }

        @Override
        protected Long doInBackground(Void... params) {
            userList = new UserList(context);

            Long currentID = userList.verifyAccount(email, password);

            return currentID;
        }

        @Override
        protected void onPostExecute(final Long currentID) {
            mAuthTask = null;
            showProgress(false);

            if (!(currentID == 0)) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("userID", currentID);
                Toast.makeText(context, "You have logged in successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                LoginActivity.this.finish();
            } else {
                mPasswordView.setText("");
                mPasswordView.setError(getString(R.string.error_wrong_input));
                mEmailView.setError(getString(R.string.error_wrong_input));
                mPasswordView.requestFocus();
                Toast.makeText(context, "Wrong e-mail or password", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

