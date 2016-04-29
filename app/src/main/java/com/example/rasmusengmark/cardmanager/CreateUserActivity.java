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

        editTextEmail.setError(null);
        editTextPassword.setError(null);
        editTextFirstName.setError(null);
        editTextLastName.setError(null);
        editTextAge.setError(null);
        editTextCpr.setError(null);
    }

    private void initListeners() {
        btnCreateUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email =  editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String firstname =  editTextFirstName.getText().toString();
                String lastname =  editTextLastName.getText().toString();
                String cpr = editTextCpr.getText().toString();
               // int age = Integer.parseInt(editTextAge.getText().toString());
                int age = 10;

                validateInput(email, password, firstname, lastname, cpr, age);
            }
        });
    }

    public void validateInput(String email, String password, String firstname, String lastname, String cpr, int age){
        boolean cancel = false;
        View focusView = null;

        // Check for a valid CPR-number.
        if (!isCprValid(cpr)) {
            editTextCpr.setError(getString(R.string.error_invalid_cpr));
            focusView = editTextCpr;
            cancel = true;
        }
        // Check for a valid last name
        if (!isNameValid(lastname)) {
            editTextLastName.setError(getString(R.string.error_invalid_name));
            focusView = editTextLastName;
            cancel = true;
        }
        // Check for a valid first name.
        if (!isNameValid(firstname)) {
            editTextFirstName.setError(getString(R.string.error_invalid_name));
            focusView = editTextFirstName;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password)) {
            editTextPassword.setError(getString(R.string.error_invalid_password));
            focusView = editTextPassword;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError(getString(R.string.error_email_required));
            focusView = editTextEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            editTextEmail.setError(getString(R.string.error_invalid_email));
            focusView = editTextEmail;
            cancel = true;
        }
        //Will not create user if the input does not fit the criteria
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            dbAdapter.open();
            User user = new User();

            user.setEmail(email);
            user.setPassword(password);
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setCpr(cpr);
            user.setAge(age);

            dbAdapter.create(user);
            dbAdapter.close();
            Toast.makeText(context, "Your user were successfully created", Toast.LENGTH_LONG).show();
            startActivity(new Intent(context, LoginActivity.class));
        }
    }

    private boolean isEmailValid(String email) {
        // X@X.XX as minimum
        if (email.length() < 6 || (!email.contains("@"))) {
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean isNameValid(String name) {
         if(!(name.matches("[a-zA-Z]+")) || name.length() < 2 ){
             return false;
         }
        return true;
    }

    private boolean isAgeValid(int age) {
        //TODO: Replace this with your own logic
        return age > 4 && age < 100 ;
    }
    private boolean isCprValid(String cpr) {
        if (cpr.length() != 10){
            return false;
        }
        else if(!(cpr.matches("[0-9]+"))){
            return false;
        }
        return true;
    }

}
