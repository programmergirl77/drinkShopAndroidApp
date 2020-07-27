package com.example.finalproject.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    Context context;
    List<Favourite> favouriteList;
    ShopApi mService;

    public FavouriteAdapter(Context context, List<Favourite> favouriteList) {
        this.context = context;
        this.favouriteList = favouriteList;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.fave_item_layout,parent,false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        mService = Common.getApi();

        Picasso.with(context).load(favouriteList.get(position).Link).into(holder.img_product);
        holder.txt_price.setText(new StringBuilder("$").append(favouriteList.get(position).price).toString());
        holder.txt_product_name_fave.setText(favouriteList.get(position).Name);

        holder.delete_icon_cart_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DeleteFavouriteSelectItem(position);

            }
        });

    }

    private void DeleteFavouriteSelectItem(int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View itemview = LayoutInflater.from(context)
                .inflate(R.layout.delete_cart_item_layout,null);

        alert.setPositiveButton("Delete this item", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

                mService.DeleteFavouriteItem(favouriteList.get(position).id)
                        .observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Favourite>() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Favourite favourite) {

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
        return favouriteList.size();
    }

    public class FavouriteViewHolder extends RecyclerView.ViewHolder {
        ImageView img_product,delete_icon_cart_item;
        TextView txt_price,txt_product_name_fave;
        public LinearLayout view_foreGround;
        public RelativeLayout view_background;
        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            delete_icon_cart_item = itemView.findViewById(R.id.delete_icon_fave_item);
            img_product = itemView.findViewById(R.id.img_product_fave);
            txt_price = itemView.findViewById(R.id.txt_price_fave_itemLayout);
            txt_product_name_fave = itemView.findViewById(R.id.txt_product_name_fave);
            view_background = itemView.findViewById(R.id.view_background);
            view_foreGround = itemView.findViewById(R.id.view_foreGround);
        }
    }

    public  void removeItem(int position){
        favouriteList.remove(position);
        notifyItemRemoved(position);
    }
    public void restoreItem(Favourite item,int position )
    {
        favouriteList.add(position,item);
        notifyItemInserted(position);
    }
}
