package com.example.finalproject.Adapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalproject.Interface.ItemClickListener;
import com.example.finalproject.R;


public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView img_avatar;
    TextView txt_user_name;

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        img_avatar = itemView.findViewById(R.id.image_avatar);
        txt_user_name=itemView.findViewById(R.id.nav_username);
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view);

    }
}
