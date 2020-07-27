package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.finalproject.Adapter.OrderAdapter;
import com.example.finalproject.Model.Order;
import com.example.finalproject.Retrofit.ShopApi;
import com.example.finalproject.Utils.Common;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ShowOrderActivity extends AppCompatActivity {

    ShopApi mService;
    RecyclerView recyclerView;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);

        mService = Common.getApi();

        recyclerView=findViewById(R.id.recycler_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        bottomNavigationView = findViewById(R.id.bottomNavigation_showOrder);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@androidx.annotation.NonNull MenuItem item) {
                if (item.getItemId() == R.id.order_new)
                {
                    LoadOrder("0");
                }

                if (item.getItemId() == R.id.order_cancel)
                {
                    LoadOrder("-1");
                }
                else if (item.getItemId() == R.id.order_process)
                {
                    LoadOrder("1");
                }
                else if (item.getItemId() == R.id.order_shipping)
                {
                    LoadOrder("2");
                }
                else if (item.getItemId() == R.id.order_shipped)
                {
                    LoadOrder("3");
                }


                return;
            }
        });
        LoadOrder("0");
    }

    private void LoadOrder(String StatusCode) {
        //if (Common.currentUser !=null)
        //{
        mService.getOrder(Common.currentUser.getUserPhone(),StatusCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Order>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Order> orders) {
                        DisplayOrder(orders);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    //}
       /* else {
            Toast.makeText(this, "please logging again!", Toast.LENGTH_SHORT).show();
            finish();
        }*/
    }

    private void DisplayOrder(List<Order> orders) {
        OrderAdapter adapter = new OrderAdapter(this,orders);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadOrder("0");
    }
}