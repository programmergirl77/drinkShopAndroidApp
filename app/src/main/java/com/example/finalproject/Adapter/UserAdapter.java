package com.example.finalproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Model.Users;
import com.example.finalproject.R;
import com.example.finalproject.Utils.Common;
import com.squareup.picasso.Picasso;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    Context context;
    Users users;

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.nav_header_layout,parent,false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        Picasso.with(context)
                .load(Common.BASE_URL+users.getAvatarUrl())
                .into(holder.img_avatar);
        holder.txt_user_name.setText(users.getUsername());
        //event
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
