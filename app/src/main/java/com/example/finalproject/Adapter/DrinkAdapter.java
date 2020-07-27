package com.example.finalproject.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.finalproject.CartActivity;
import com.example.finalproject.DrinkActivity;
import com.example.finalproject.Interface.ItemClickListener;
import com.example.finalproject.MainActivity;
import com.example.finalproject.Model.Cart;
import com.example.finalproject.Model.Drinks;
import com.example.finalproject.Model.Favourite;
import com.example.finalproject.Model.InsertResp;
import com.example.finalproject.R;
import com.example.finalproject.Retrofit.ShopApi;
import com.example.finalproject.Utils.Common;
import com.example.finalproject.favouriteListactivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkViewHolder> {

    Context context;
    List<Drinks> drinksList;

    ShopApi mService;

    public DrinkAdapter(Context context, List<Drinks> drinksList) {
        this.context = context;
        this.drinksList = drinksList;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.drink_item_layout,null);
        return new DrinkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {

        //get API
        mService = Common.getApi();

        //set price
        holder.txt_price.setText(new StringBuilder("$").append(drinksList.get(position).price).toString());

        //set image
        Picasso.with(context)
                .load(Common.BASE_URL+drinksList.get(position).Link)
                .into(holder.img_product);

        //set drink name
        holder.txt_drink_name.setText(drinksList.get(position).Name);

        //show click when user clicked on product
        holder.setItemclicklistener(new ItemClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"clicked",Toast.LENGTH_LONG).show();
            }
        });


        holder.btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showADDToCartDialog(position);
            }
        });

       holder.btn_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             holder.btn_favourite.setImageResource(R.drawable.ic_baseline_favorite_24);
                addOrRemoveFavourite(position);
            }
        });

    }

    private void addOrRemoveFavourite(int position) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View itemview = LayoutInflater.from(context)
                .inflate(R.layout.confirm_add_to_cart_layout,null);

        //view
        ImageView img_product_dialog =itemview.findViewById(R.id.img_product_confirm);
        TextView txt_product_dialog = itemview.findViewById(R.id.txt_cconfirm_product_name);
        TextView txt_product_price = itemview.findViewById(R.id.txt_confirm_product_price);


        //set data
        Picasso.with(context).load(drinksList.get(position).Link).into(img_product_dialog);
        txt_product_dialog.setText(new StringBuilder(drinksList.get(position).Name));



        double price = (Double.parseDouble(drinksList.get(position).price));

        txt_product_price.setText(new StringBuilder("$").append(price));

        StringBuilder topping_final_comment = new StringBuilder("");
        for (String Line : Common.toppingAdded)
            topping_final_comment.append(Line).append("\n");


        double finalPrice = price;
        alert.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                try {

                    //add to sqlite
                    //create new cart item
                    String name = txt_product_dialog.getText().toString();
                    double priceee = finalPrice;
                    String image = drinksList.get(position).Link;

                    mService.InsertToFavourite(name,image,priceee)
                            .observeOn(Schedulers.io())
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<List<Favourite>>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@NonNull List<Favourite> favourites) {

                                }

                                @Override
                                public void onError(@NonNull Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });


                    //add to db
                    Intent confirmDialog = new Intent(context, favouriteListactivity.class);
                    confirmDialog.putExtra("name",name);
                    confirmDialog.putExtra("price",priceee);
                    confirmDialog.putExtra("image",drinksList.get(position).Link);
                    context.startActivity(confirmDialog);

                } catch (Exception ex) {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.startActivity(new Intent(context,MainActivity.class));
            }
        });
        alert.setView(itemview);
        alert.show();
    }


    private void showADDToCartDialog(int position) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View itemview = LayoutInflater.from(context)
                .inflate(R.layout.add_to_cart_layout,null);

        //view
        ImageView img_product_dialog =itemview.findViewById(R.id.img_cart_product);
        ElegantNumberButton elegantNumberButton =itemview.findViewById(R.id.txt_count);
        TextView txt_product_name = itemview.findViewById(R.id.txt_cart_product_name);

        EditText edit_comment = itemview.findViewById(R.id.edit_comment);

        RadioButton rdi_sizeM = itemview.findViewById(R.id.rd_sizM);
        RadioButton rdi_sizel = itemview.findViewById(R.id.rd_sizL);

        //set views and click them and set changes
        rdi_sizeM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.SizeOfCup = 0;
                }
            }
        });

        rdi_sizel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.SizeOfCup = 1;
                }
            }
        });

        RadioButton rdi_sugar100 = itemview.findViewById(R.id.rd_sugar100);
        RadioButton rdi_sugar70 = itemview.findViewById(R.id.rd_sugar70);
        RadioButton rdi_sugar50 = itemview.findViewById(R.id.rd_sugar50);
        RadioButton rdi_sugar30 = itemview.findViewById(R.id.rd_sugar30);
        RadioButton rdi_sugar_free = itemview.findViewById(R.id.rd_sugar_free);

        rdi_sugar30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.sugar = 30;
                }
            }
        });

        rdi_sugar50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.sugar = 50;
                }
            }
        });

        rdi_sugar70.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.sugar = 70;
                }
            }
        });

        rdi_sugar100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.sugar = 100;
                }
            }
        });

        rdi_sugar_free.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.sugar = 0;
                }
            }
        });

        RadioButton rdi_ice_free = itemview.findViewById(R.id.rd_ice_free);
        RadioButton rdi_ice_100 = itemview.findViewById(R.id.rd_ice100);
        RadioButton rdi_ice_70 = itemview.findViewById(R.id.rd_ice70);
        RadioButton rdi_ice_30 = itemview.findViewById(R.id.rd_ice30);
        RadioButton rdi_ice_50 = itemview.findViewById(R.id.rd_ice50);

        rdi_ice_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                {
                    Common.ice = 50;
                }
            }
        });

        rdi_ice_free.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.ice = 0;
                }
            }
        });

        rdi_ice_30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.ice = 30;
                }
            }
        });

        rdi_ice_70.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.ice = 70;
                }
            }
        });

        rdi_ice_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.ice = 100;
                }
            }
        });

        RecyclerView toppingList = itemview.findViewById(R.id.topping_item_recycler);
        toppingList.setLayoutManager(new LinearLayoutManager(context));
        toppingList.setHasFixedSize(true);

        //set multichoice adapter to this adapter
        MultiChoiceAdapter adapter = new MultiChoiceAdapter(context, Common.toppingList);
        toppingList.setAdapter(adapter);

        Picasso.with(context)
                .load(Common.BASE_URL+drinksList.get(position).Link)
                .into(img_product_dialog);
        txt_product_name.setText(drinksList.get(position).Name);

        alert.setView(itemview);
        alert.setPositiveButton("Add To Cart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (Common.SizeOfCup==-1)
                {
                    Toast.makeText(context,"please choose size of cup",Toast.LENGTH_LONG).show();
                    return;
                }
                if (Common.sugar==-1)
                {
                    Toast.makeText(context,"please choose sugar",Toast.LENGTH_LONG).show();
                    return;
                }
                if (Common.ice==-1)
                {
                    Toast.makeText(context,"please choose ice",Toast.LENGTH_LONG).show();
                    return;
                }

                ShowConfirmDialog(position,elegantNumberButton.getNumber());
                dialog.dismiss();

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(context, DrinkActivity.class);
                context.startActivity(intent);
            }
        });
        alert.show();
    }

    private void ShowConfirmDialog(int position, String number) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View itemview = LayoutInflater.from(context)
                .inflate(R.layout.confirm_add_to_cart_layout,null);

        //view
        ImageView img_product_dialog =itemview.findViewById(R.id.img_product_confirm);
        TextView txt_product_dialog = itemview.findViewById(R.id.txt_cconfirm_product_name);
        TextView txt_product_price = itemview.findViewById(R.id.txt_confirm_product_price);
        TextView txt_sugar = itemview.findViewById(R.id.txt_confirm_sugar);
        TextView txt_ice = itemview.findViewById(R.id.txt_confirm_ice);
        TextView txt_topping = itemview.findViewById(R.id.confirm_txt_topping_extra);

        //set data
        Picasso.with(context).load(Common.BASE_URL+drinksList.get(position).Link).into(img_product_dialog);
        txt_product_dialog.setText(new StringBuilder(drinksList.get(position).Name).append("x")
                .append(number)
                .append(Common.SizeOfCup == 0 ? "Size M" : "Size L").toString());

        txt_ice.setText(new StringBuilder("ice : ").append(Common.ice).append("%").toString());
        txt_sugar.setText(new StringBuilder("sugar :" ).append(Common.sugar).append("%").toString());

        double price = (Double.parseDouble(drinksList.get(position).price) * Double.parseDouble(number)) + Common.Toppingprice;

        if (Common.SizeOfCup == 1)
            price+=3.0;

        txt_product_price.setText(new StringBuilder("$").append(price));

        StringBuilder topping_final_comment = new StringBuilder("");
        for (String Line : Common.toppingAdded)
            topping_final_comment.append(Line).append("\n");

        txt_topping.setText(topping_final_comment);

       final double finalPrice = price;
        alert.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                try {
                    //add to sqlite
                    //create new cart item
                    String name = txt_product_dialog.getText().toString();
                    int amount = Integer.parseInt(number);
                    int ice = Common.ice;
                    int sugar = Common.sugar;
                    double priceee = finalPrice;
                    String topping_extras = txt_topping.getText().toString();
                    String image = drinksList.get(position).Link;

                    mService.InsertToCart(name,image,amount,priceee,sugar,ice,topping_extras)
                            .observeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<InsertResp>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@NonNull InsertResp response) {
                                    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    Toast.makeText(context, "fail"+e.getMessage(), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onComplete() {

                                }
                            });

                    //add to db
                  //  Intent confirmDialog = new Intent(context, CartActivity.class);
                  //  confirmDialog.putExtra("name",name);
                   // confirmDialog.putExtra("amount",amount);
                   // confirmDialog.putExtra("ice",ice);
                  //  confirmDialog.putExtra("sugar",sugar);
                  //  confirmDialog.putExtra("price",priceee);
                   // confirmDialog.putExtra("toppingextras",topping_extras);
                   // confirmDialog.putExtra("image",drinksList.get(position).Link);
                   // context.startActivity(confirmDialog);

                } catch (Exception ex) {

                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.startActivity(new Intent(context,DrinkActivity.class));
            }
        });
        alert.setView(itemview);
        alert.show();
    }

    @Override
    public int getItemCount() {
        return drinksList.size();
    }
}
