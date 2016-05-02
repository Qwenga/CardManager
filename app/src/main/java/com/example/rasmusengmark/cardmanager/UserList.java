package com.example.rasmusengmark.cardmanager;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_ID;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_EMAIL;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_PASSWORD;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_FIRSTNAME;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_LASTNAME;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_AGE;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_CPR;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikkel on 09-04-2016.
 */
public class UserList extends BaseAdapter {

    private List<User> users = new ArrayList<User>();

    private Context context;

    private SQLiteAdapter dbAdapter;

    public UserList(Context context) {
        this.context = context;

        dbAdapter = LoginActivity.dbAdapter;
        dbAdapter.open();

        Cursor cursor = dbAdapter.readAll();

        boolean hasFirst = cursor.moveToFirst();

        if (hasFirst == true) { //To test if database is emtpy so we don't get a nullpointer exception
            do {
                User user = new User();
                user.setId(cursor.getLong(cursor.getColumnIndex(USER_ID)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(USER_PASSWORD)));
                user.setFirstName(cursor.getString(cursor.getColumnIndex(USER_FIRSTNAME)));
                user.setLastName(cursor.getString(cursor.getColumnIndex(USER_LASTNAME)));
                user.setAge(cursor.getInt(cursor.getColumnIndex(USER_AGE)));
                user.setCpr(cursor.getString(cursor.getColumnIndex(USER_CPR)));
                users.add(user);
            }
        while (cursor.moveToNext());
        }

        dbAdapter.close();
    }

    public Boolean checkEmail(String emailInput){
        for (User x : users){
            String email = x.getEmail();
            if (email.equals(emailInput)){
                return false;
            }
        }
        return true;
    }

    public Long verifyAccount(String emailInput, String passwordInput){ // Return ID to pass on
        Long ID = 0L;
        for (User x : users){
            String email = x.getEmail();
            String password = x.getPassword();
            if (email.equals(emailInput) && password.equals(passwordInput)){
                ID = x.getId();
                return ID;
            }
        }
        return ID;
    }

    public User getCurrentAccount(Long userID){
        User currentUser = null;
        for (User x : users){
            Long listID = x.getId();
            if (listID == userID ){
                currentUser = x;
                return currentUser;
            }
        }
        return currentUser;
    }

    public void deleteUser (Long userID){
        dbAdapter.open();
        dbAdapter.delete(userID);
        dbAdapter.close();
    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        User user;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.my_accountinfo, parent, false);
            holder = new ViewHolder();
            holder.textViewId = (TextView) convertView.findViewById(R.id.textViewId);
            holder.textViewEmail = (TextView) convertView.findViewById(R.id.textViewEmail);
            holder.textViewPassword = (TextView) convertView.findViewById(R.id.textViewPassword);
            holder.textViewFirstName = (TextView) convertView.findViewById(R.id.textViewFirstName);
            holder.textViewLastName = (TextView) convertView.findViewById(R.id.textViewLastName);
            holder.textViewCpr = (TextView) convertView.findViewById(R.id.textViewCpr);

            convertView.setTag(holder);
            user = getItem(position);

            holder.textViewId.setText(user.getId().toString());
            holder.textViewEmail.setText(user.getEmail().toString());
            holder.textViewPassword.setText(user.getCpr());
            holder.textViewFirstName.setText(user.getFirstName());
            holder.textViewLastName.setText(user.getLastName());
            holder.textViewAge.setText(user.getAge());
            holder.textViewCpr.setText(user.getCpr());

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder {
        TextView textViewId;
        TextView textViewEmail;
        TextView textViewPassword;
        TextView textViewFirstName;
        TextView textViewLastName;
        TextView textViewAge;
        TextView textViewCpr;

    }
}
