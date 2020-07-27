package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Adapter.DrinkAdapter;
import com.example.finalproject.Model.Drinks;
import com.example.finalproject.Retrofit.ShopApi;
import com.example.finalproject.Utils.Common;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DrinkActivity extends AppCompatActivity {

    ShopApi mService;

    RecyclerView lst_drink;
    TextView txt_banner_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        mService= Common.getApi();


        lst_drink = findViewById(R.id.recycler_drinks);
        lst_drink.setLayoutManager( new GridLayoutManager(this,2));
        lst_drink.setHasFixedSize(true);

        txt_banner_name = findViewById(R.id.txt_banner_name);
        txt_banner_name.setText(Common.currentcategory.Name);
        
        LoadListDrink(Common.currentcategory.id);
    }

    private void LoadListDrink(String Menuid) {

        mService.getDrinks(Menuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Drinks>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Drinks> drinks) {
                        DisplayListDrink(drinks);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void DisplayListDrink(List<Drinks> drinks) {

        DrinkAdapter drinkAdapter = new DrinkAdapter(this,drinks);
        lst_drink.setAdapter(drinkAdapter);
    }

}
