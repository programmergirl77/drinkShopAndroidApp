package com.example.finalproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.finalproject.Model.Cart;
import com.example.finalproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>{

    Context context;
    List<Cart> cartList;

    public OrderDetailAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public OrderDetailAdapter.OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(context).inflate(R.layout.order_detail_layout,parent,false);
        return new OrderDetailAdapter.OrderDetailViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.OrderDetailViewHolder holder, int position) {
       /* Picasso.with(context)
                .load(cartList.get(position).Link)
                .into(holder.img_product);*/

        holder.txt_price.setText(new StringBuilder("$").append(cartList.get(position).price));
        holder.txt_product_name.setText(cartList.get(position).Name);
        holder.txt_sugar_ice.setText(new StringBuilder("sugar: ")
                .append(cartList.get(position).Sugar).append("%").append("/n").append("Ice: ").append(cartList.get(position).Ice)
                .append("%").toString());

    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public void removeItem(int position){
        cartList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Cart item, int position )
    {
        cartList.add(position,item);
        notifyItemInserted(position);
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder {

        ImageView img_product;
        TextView txt_product_name,txt_sugar_ice,txt_price;

        public RelativeLayout view_background;
        public LinearLayout view_foreGround;

        public OrderDetailViewHolder(@NonNull View itemView) {
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
