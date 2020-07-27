package com.example.finalproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.finalproject.DrinkActivity;
import com.example.finalproject.Interface.ItemClickListener;
import com.example.finalproject.Model.category;
import com.example.finalproject.R;
import com.example.finalproject.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    Context context;

    public CategoryAdapter(Context context, List<category> categories) {
        this.context = context;
        this.categories = categories;
    }

    List<category> categories;



    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(context).inflate(R.layout.rexycler_menu_item_layout,null);
        return new CategoryViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Picasso.with(context)
                .load(Common.BASE_URL+categories.get(position).Link)
                .into(holder.img_product);

        holder.txt_menu_name.setText(categories.get(position).Name);

        //event

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {

                Common.currentcategory = categories.get(position);
                //start new activity
                context.startActivity(new Intent(context, DrinkActivity.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView img_product;
        TextView txt_product_name,txt_sugar_ice,txt_price;
        ElegantNumberButton txt_amount;
        public RelativeLayout view_background;
        public LinearLayout view_foreGround;

        public CartViewHolder(@androidx.annotation.NonNull View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.img_product);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_sugar_ice = itemView.findViewById(R.id.txt_sugar_ice);
            txt_price = itemView.findViewById(R.id.txt_price_cart_itemLayout);
            view_background =itemView.findViewById(R.id.view_background);
            view_foreGround = itemView.findViewById(R.id.view_foreGround);
        }
    }
}
