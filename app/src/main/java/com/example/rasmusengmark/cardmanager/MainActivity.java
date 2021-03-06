package com.example.rasmusengmark.cardmanager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UserList userList;
    private SQLiteAdapter dbHandler;
    private Context context;

    private Long currentID;

    private Button btnDeleteUser;

    private ImageView imageCode;

    private TextView textViewId;
    private TextView textViewEmail;
    private TextView textViewPassword;
    private TextView textViewFirstName;
    private TextView textViewLastName;
    private TextView textViewCpr;

    private TextView textViewSideEmail;
    private TextView textViewSideName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Activity activity = this;
        context = this;
        dbHandler = LoginActivity.dbAdapter;
        userList = new UserList(this);

       currentID = getIntent().getExtras().getLong("userID"); //Getting the ID for the user logged in

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        User currentUser = userList.getCurrentAccount(currentID); // Getting the user that is logged in now
        setSideBarUser(currentUser, header); //Passes it to viewAccount() that will set the text box
        navigationView.getMenu().getItem(0).setChecked(true);

        ImageButton button = (ImageButton) findViewById(R.id.clubmatas);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.pickedcard_activity);
            }
        });

        ImageButton addCard = (ImageButton) findViewById(R.id.pluscard);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("[Scan the code on your loyalty card]");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_help){
            Intent intent = new Intent(this, MapsActivity.class);
            this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mycards) {

            setContentView(R.layout.activity_main);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View header=navigationView.getHeaderView(0);
            User currentUser = userList.getCurrentAccount(currentID); // Getting the user that is logged in now
            setSideBarUser(currentUser, header); //Passes it to viewAccount() that will set the text box
            navigationView.getMenu().getItem(0).setChecked(true);


        } else if (id == R.id.nav_giftcards) {

            setContentView(R.layout.activity_second);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View header=navigationView.getHeaderView(0);
            User currentUser = userList.getCurrentAccount(currentID); // Getting the user that is logged in now
            setSideBarUser(currentUser, header); //Passes it to viewAccount() that will set the text box
            navigationView.getMenu().getItem(1).setChecked(true);

        } else if (id == R.id.nav_account) {

            setContentView(R.layout.activity_third);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View header=navigationView.getHeaderView(0);
            User currentUser = userList.getCurrentAccount(currentID); // Getting the user that is logged in now
            setSideBarUser(currentUser, header); //Passes it to viewAccount() that will set the text box
            navigationView.getMenu().getItem(2).setChecked(true);

            userList = new UserList(this);
            viewAccount(currentUser); //Passes it to viewAccount() that will set the text box

            btnDeleteUser.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    AlertDialog show = new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Are you sure you want to delete your account?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    userList.deleteUser(currentID);
                                    Intent homescreen = new Intent(MainActivity.this, LoginActivity.class);
                                    homescreen.addFlags(getIntent().FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(homescreen);
                                    MainActivity.this.finish();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });


        } else if (id == R.id.nav_logout) {

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.getMenu().getItem(3).setChecked(true);
            currentID = 0L;
            Intent homescreen = new Intent(this,LoginActivity.class);
            homescreen.addFlags(getIntent().FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homescreen);
            this.finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setSideBarUser(User user, View headerView){

        textViewSideEmail = (TextView) headerView.findViewById(R.id.email_drawer_lol);
        textViewSideName = (TextView) headerView.findViewById(R.id.name_drawer_lol);

        textViewSideEmail.setText(user.getEmail());
        textViewSideName.setText(user.getFirstName() + " " + user.getLastName());

    }

    public void viewAccount(User user){
        btnDeleteUser = (Button) findViewById(R.id.btnDeleteUser);
        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewPassword = (TextView) findViewById(R.id.textViewPassword);
        textViewFirstName = (TextView) findViewById(R.id.textViewFirstName);
        textViewLastName = (TextView) findViewById(R.id.textViewLastName);
        textViewCpr = (TextView) findViewById(R.id.textViewCpr);

        textViewId.setText(user.getId().toString());
        textViewEmail.setText(user.getEmail());
        textViewPassword.setText(user.getPassword());
        textViewFirstName.setText(user.getFirstName());
        textViewLastName.setText(user.getLastName());
        textViewCpr.setText(user.getCpr());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                setContentView(R.layout.pickedcard_activity);
                imageCode = (ImageView) findViewById(R.id.imageCode);
                Bitmap myBitmap = BitmapFactory.decodeFile(result.getBarcodeImagePath());
                imageCode.setImageBitmap(myBitmap);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
