package com.example.finalproject.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.finalproject.CartActivity;
import com.example.finalproject.Interface.ItemClickListener;
import com.example.finalproject.Model.Cart;
import com.example.finalproject.Model.Favourite;
import com.example.finalproject.R;
import com.example.finalproject.Retrofit.ShopApi;
import com.example.finalproject.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    List<Cart>cartList;
    ShopApi mService;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(context).inflate(R.layout.cart_item_layout,parent,false);
        return new CartViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
       Picasso.with(context)
                .load(Common.BASE_URL+cartList.get(position).image)
                .into(holder.img_product);

        holder.txt_amount.setNumber(String.valueOf(cartList.get(position).Amount));
        holder.txt_price.setText(new StringBuilder("$").append(cartList.get(position).price));
        holder.txt_product_name.setText(cartList.get(position).Name);
        holder.txt_sugar_ice.setText(new StringBuilder("sugar: ")
                .append(cartList.get(position).Sugar).append("%").append("\n").append("Ice: ").append(cartList.get(position).Ice)
                .append("%").toString());

        mService = Common.getApi();

        //auto save item when user change amount
        holder.txt_amount.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Cart cart = cartList.get(position);
                cart.Amount=newValue;

                // Common.cartRepository.updateCart(cart);
            }
        });

        /*holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {
                Common.currentCartItem = cartList.get(position);

                context.startActivity(new Intent(context, CartActivity.class));
            }
        });*/

        holder.delete_icon_cart_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DeleteCartSelectItem(position);

            }
        });
    }

    private void DeleteCartSelectItem(int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View itemview = LayoutInflater.from(context)
                .inflate(R.layout.delete_cart_item_layout,null);

        alert.setPositiveButton("Delete this item", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

                mService.DeleteCartItem(cartList.get(position).id)
                        .observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Cart>() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Cart cart) {

                                removeItem(position);
                                Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.setView(itemview);
        alert.show();

    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public void removeItem(int position){
        cartList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Cart item, int position)
    {
        cartList.add(position,item);
        notifyItemInserted(position);
    }

    public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView img_product,delete_icon_cart_item;
        TextView txt_product_name,txt_sugar_ice,txt_price;
        ElegantNumberButton txt_amount;
        public RelativeLayout view_background;
        public LinearLayout view_foreGround;

        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            delete_icon_cart_item= itemView.findViewById(R.id.delete_icon_cart_item_img);
            img_product = itemView.findViewById(R.id.img_product_cart);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_sugar_ice = itemView.findViewById(R.id.txt_sugar_ice);
            txt_amount = itemView.findViewById(R.id.txt_amount);
            txt_price = itemView.findViewById(R.id.txt_price_cart_itemLayout);
            view_background =itemView.findViewById(R.id.view_background);
            view_foreGround = itemView.findViewById(R.id.view_foreGround);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view);

        }
    }
}
