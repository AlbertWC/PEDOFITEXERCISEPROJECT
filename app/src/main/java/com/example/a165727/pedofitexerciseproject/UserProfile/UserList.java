package com.example.a165727.pedofitexerciseproject.UserProfile;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.a165727.pedofitexerciseproject.R;

import java.util.List;

public class UserList extends ArrayAdapter<User>  {

    private Activity context;
    private List<User> userList;


    public UserList(Activity context, List<User>userList)
    {
        super(context, R.layout.activity_profile, userList );
        this.context = context;
        this.userList = userList;
    }


    @NonNull
    @Override
    public View getView(int position,  @Nullable View convertView,  @NonNull ViewGroup parent) {

        LayoutInflater inflater =  context.getLayoutInflater();

        View listviewItem = inflater.inflate(R.layout.activity_profile, null , true);

        TextView textViewHeight = listviewItem.findViewById(R.id.tv_profile_nickname);
        User user = userList.get(position);

        textViewHeight.setText(user.getHeight());

        return listviewItem;
    }
}
