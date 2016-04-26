package com.example.rasmusengmark.cardmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUserActivity extends AppCompatActivity {

    Context context;
    public static SQLiteAdapter dbAdapter;

    private Button btnCreateUser;
    private EditText editTextName;
    private EditText editTextEmail;

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
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextAge);
        btnCreateUser = (Button) findViewById(R.id.btnCreateUser);
    }

    private void initListeners() {
        btnCreateUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dbAdapter.open();
                User user = new User();

                user.setName(editTextName.getText().toString());
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

    private boolean isEmailValid(String email) {
        if (email.length() < 6){ // X@X.XX as minimum
            return false;
        }
        //else if (!email.contains("@")) {
       //     return false;
      //  }
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}
