package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Adapter.OrderDetailAdapter;
import com.example.finalproject.Model.Cart;
import com.example.finalproject.Retrofit.ShopApi;
import com.example.finalproject.Utils.Common;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    TextView txt_order_address,txt_order_comment,txt_order_status,txt_order_id,txt_order_price;
    Button btn_cancel;
    RecyclerView recycler_order_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);


        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrder();
            }
        });
        txt_order_address = findViewById(R.id.txt_order_address);
        txt_order_comment = findViewById(R.id.txt_order_comment);
        txt_order_id = findViewById(R.id.txt_order_id);
        txt_order_price = findViewById(R.id.txt_order_price);
        txt_order_status = findViewById(R.id.txt_order_status);

        recycler_order_detail = findViewById(R.id.recycler_order_detail);
        recycler_order_detail.setLayoutManager(new LinearLayoutManager(this));
        recycler_order_detail.setHasFixedSize(true);

        txt_order_status.setText(new StringBuilder("Order Status : ").append(Common.ConvertCodeToStatus(Common.currentOrder.getOrderStatus())));
        txt_order_price.setText(new StringBuilder("$").append(Common.currentOrder.getOrderPrice()));
        txt_order_id.setText(new StringBuilder("#").append(Common.currentOrder.getOrderId()));
        txt_order_comment.setText(Common.currentOrder.getOrderComment());
        txt_order_address.setText(Common.currentOrder.getOrderAddress());
        
        //DisplayOrderDetail();
    }

    private void cancelOrder() {
        ShopApi mService = Common.getApi();
        mService.CancelOrder(String.valueOf(Common.currentOrder.getOrderId()),Common.currentUser.getUserPhone())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(OrderDetailActivity.this,response.body(),Toast.LENGTH_LONG).show();
                        if (response.body().contains("order has been canceled"))
                            finish();

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("DEBUG",t.getMessage());
                    }
                });
    }

   /* private void DisplayOrderDetail() {

        List<Cart> orderDetail = new Gson().fromJson(Common.currentOrder.getOrderDetail() ,
                new TypeToken<List<Cart>>().getType());

        recycler_order_detail.setAdapter(new OrderDetailAdapter(this,orderDetail));
    }*/
}