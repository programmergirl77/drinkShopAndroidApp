package com.example.finalproject.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Interface.ItemClickListener;
import com.example.finalproject.R;

import io.reactivex.rxjava3.annotations.NonNull;

public class DrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView img_product;
    TextView txt_drink_name,txt_price;
    ItemClickListener itemClickListener;

    ImageView btn_add_to_cart,btn_favourite;

    public void setItemclicklistener(ItemClickListener itemclicklistener)
    {
        this.itemClickListener = itemclicklistener;
    }

    public DrinkViewHolder(@NonNull View itemView) {
        super(itemView);

        img_product = itemView.findViewById(R.id.image_product);
        txt_drink_name = itemView.findViewById(R.id.text_drink_name);
        txt_price = itemView.findViewById(R.id.txt_price);
        btn_add_to_cart = itemView.findViewById(R.id.btn_add_to_cart);
        btn_favourite = itemView.findViewById(R.id.btn_favourite);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v);

    }
}
