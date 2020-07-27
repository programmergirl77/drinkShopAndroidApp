package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.finalproject.Adapter.CartAdapter;
import com.example.finalproject.Model.Cart;
import com.example.finalproject.Retrofit.ShopApi;
import com.example.finalproject.Utils.Common;
import com.example.finalproject.Utils.RecyclerItemTouchHelper;
import com.example.finalproject.Utils.RecyclerItemTouchHelperListner;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity  {

    Button btn_place_order;
    private ShopApi mService;
    RelativeLayout rootlayout;
    EditText edt_comment,edt_UserPhone;

    private static final String TAG = "CartActivity";
    List<Cart> cartList = new ArrayList<>();
    List<Cart> carts;
    CartAdapter cartAdapter;
    RecyclerView recycler_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mService = Common.getApi();

        recycler_cart = findViewById(R.id.recycler_cart);
        recycler_cart.setLayoutManager(new LinearLayoutManager(this));
        recycler_cart.setHasFixedSize(true);


       //rootlayout = findViewById(R.id.rootlayout);
      // ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT, (RecyclerItemTouchHelperListner) this);

      //new ItemTouchHelper(simpleCallback).attachToRecyclerView(recycler_cart);



       // edt_comment = findViewById(R.id.edt_comment_submit);

       btn_place_order = findViewById(R.id.btn_place_order);
       btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder();
            }
        });

        getCartItems();
    }


    private void getCartItems() {
        mService.getCartItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Cart>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Cart> carts) {
                        DisplayCartItem(carts);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void DisplayCartItem(List<Cart> carts)
    {
       // cartList = carts;
        cartAdapter = new CartAdapter(this,carts);
        recycler_cart.setAdapter(cartAdapter);
    }

    private void placeOrder() {
        //create dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Submit Order");

        View submit_order_layout = getLayoutInflater().from(this).inflate(R.layout.submit_order_layout,null);
        final EditText edt_other_address = submit_order_layout.findViewById(R.id.edt_other_address_submit);

        RadioButton rdi_user_address = submit_order_layout.findViewById(R.id.rdi_user_address_submit);
        RadioButton rdi_other_address = submit_order_layout.findViewById(R.id.rdi_other_address_submit);

        //event
        rdi_user_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    edt_other_address.setEnabled(false);
            }
        });

        rdi_other_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    edt_other_address.setEnabled(true);

            }
        });

        builder.setView(submit_order_layout);

        builder.setNegativeButton("Cancel:", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                edt_comment = findViewById(R.id.edt_comment_submit);
                edt_UserPhone = findViewById(R.id.edt_UserPhone_submit);

                String order_comment = edt_comment.getText().toString();
                String UserPhone = edt_UserPhone.getText().toString();
                String orderAddress;


                if (rdi_user_address.isChecked())
                    orderAddress = Common.currentUser.getOrderAddress();

                else if (rdi_other_address.isChecked())
                    orderAddress = rdi_other_address.getText().toString();
                else
                    orderAddress = " ";

                if(!TextUtils.isEmpty(orderAddress))
                    sendOrderToServer(mService.SumPriceCart(),carts,order_comment,orderAddress,UserPhone);
              else {
                    Toast.makeText(CartActivity.this, "Order Address cant Null", Toast.LENGTH_SHORT).show();
                }
            }
      });
        builder.show();
}

    private void sendOrderToServer(Float sumPriceCart, List<Cart> carts, String order_comment, String orderAddress, String userPhone) {

        String OrderDetail = null;
        if (carts.size() > 0) {
            OrderDetail = new Gson().toJson(carts);
        }
        mService.submitOrder(sumPriceCart, OrderDetail,order_comment,orderAddress,userPhone)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(CartActivity.this, "order Submit", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("error",t.getMessage());

                    }
                });

    }



   // @Override
    /*//exit application when click back button
    boolean isBackButtonClick = false;

    @Override
    public void onBackPressed() {
        if (isBackButtonClick) {
            super.onBackPressed();
            return;
        }
        this.isBackButtonClick = true;
        Toast.makeText(this,"please click back button to exit",Toast.LENGTH_LONG).show();
    }*/

  //  @Override
   /* protected void onResume() {
        super.onResume();
        //isBackButtonClick = false;

    }*/

  /* @Override
   public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof CartAdapter.CartViewHolder){

            String name = cartList.get(viewHolder.getAdapterPosition()).Name;


             Cart deleteItem = cartList.get(viewHolder.getAdapterPosition());
             int deleteIndex = viewHolder.getAdapterPosition();

            //delete item from adapter
            CartAdapter.removeItem(deleteIndex);

            //delete item from database
            // Common.cartrepository.deletecartitem(deleteItem);

            mService.DeleteCartItem(deleteItem)
                    .observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull String s) {
                            Toast.makeText(CartActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });


        Snackbar snackbar = Snackbar.make(rootlayout,new StringBuilder(name).append("removed from favourite list").toString(),Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CartAdapter.restoreItem(deleteItem,deleteIndex);

                   mService.InsertToCart(deleteItem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
   }*/


}