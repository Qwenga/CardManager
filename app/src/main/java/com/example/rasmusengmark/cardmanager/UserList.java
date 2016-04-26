package com.example.rasmusengmark.cardmanager;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_ID;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_NAME;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_EMAIL;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikkel on 19-04-2016.
 */
public class UserList extends BaseAdapter {

    private List<User> users = new ArrayList<User>();

    private Context context;

    private SQLiteAdapter dbAdapter;

    public UserList(Context context) {
        this.context = context;

        dbAdapter = LoginActivity.dbHandler;
        dbAdapter.open();

        Cursor cursor = dbAdapter.readAll();

        cursor.moveToFirst();

        do {
            User user = new User();
            user.setId(cursor.getLong(cursor.getColumnIndex(USER_ID)));
            user.setName(cursor.getString(cursor.getColumnIndex(USER_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(USER_EMAIL)));
            users.add(user);
        }
        while (cursor.moveToNext());

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
            holder.textViewName = (TextView) convertView.findViewById(R.id.textViewName);
            holder.textViewEmail = (TextView) convertView.findViewById(R.id.textViewEmail);

            convertView.setTag(holder);
            user = getItem(position);

            holder.textViewId.setText(user.getId().toString());
            holder.textViewName.setText(user.getName());
            holder.textViewEmail.setText(user.getEmail().toString());

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

       /* if ( position % 2 == 0 ){
            convertView.setBackgroundColor(parent.getResources().getColor(android.R.color.holo_green_light));
        }
        else {
            convertView.setBackgroundColor(parent.getResources().getColor(android.R.color.background_light));
        }*/

        return convertView;
    }

    static class ViewHolder {
        TextView textViewId;
        TextView textViewName;
        TextView textViewEmail;
    }
}
