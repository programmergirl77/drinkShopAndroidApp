package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.finalproject.Adapter.CartAdapter;
import com.example.finalproject.Adapter.FavouriteAdapter;
import com.example.finalproject.Model.Cart;
import com.example.finalproject.Model.Favourite;
import com.example.finalproject.Retrofit.ShopApi;
import com.example.finalproject.Utils.Common;
import com.example.finalproject.Utils.RecyclerItemTouchHelper;
import com.example.finalproject.Utils.RecyclerItemTouchHelperListner;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class favouriteListactivity extends AppCompatActivity  {
    private static final String TAG = "favouriteListactivity";
    RelativeLayout rootlayout;
    RecyclerView recycler_fave;
    CompositeDisposable compositeDisposable;
    FavouriteAdapter favouriteAdapter;
    List<Favourite> localFavourite = new ArrayList<>();
    private ShopApi mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_listactivity);

        //get api from web service(server)
        mService = Common.getApi();
        compositeDisposable = new CompositeDisposable();
        rootlayout = findViewById(R.id.rootlayout);

        recycler_fave = findViewById(R.id.recycler_fave);
        recycler_fave.setLayoutManager(new LinearLayoutManager(this));
        recycler_fave.setHasFixedSize(true);

        getFavouritesItem();
    }

   /*private void InsertFavouritesItem() {
        Log.d(TAG, "check incoming variables.");

        if (getIntent().hasExtra("name") &&
             getIntent().hasExtra("price") && getIntent().hasExtra("image")) {

            //get choice from adapter and set variable
            String name = getIntent().getStringExtra("name");
            double price = getIntent().getDoubleExtra("price", 0.0);
            String Link = getIntent().getStringExtra("image");

            mService.InsertToFavourite(name,Link,price)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Favourite>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull List<Favourite> carts) {

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }
       getFavouritesItem();
    }*/

    private void getFavouritesItem() {
        mService.getFavouriteItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Favourite>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Favourite> favourites) {
                        DisplayItems(favourites);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void DisplayItems(List<Favourite> favourites) {
        FavouriteAdapter adapter = new FavouriteAdapter(this,favourites);
        recycler_fave.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //loadFavouritesItem();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

   // @Override
   /* public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof FavouriteAdapter.FavouriteViewHolder)
        {
            //String name = localFavourite.get(viewHolder.getAdapterPosition()).name;

            Favourite deleteItem = localFavourite.get(viewHolder.getAdapterPosition());
            int deleteIndex = viewHolder.getAdapterPosition();

            //delete item from adapter
            favouriteAdapter.removeItem(deleteIndex);
            //delete item from database


          /*  Snackbar snackbar = Snackbar.make(rootlayout,new StringBuilder(name).append("removed from favourite list").toString(),Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    favouriteAdapter.restoreItem(deleteItem,deleteIndex);
                    //common.faverepository.insertfave(deleteItem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();*/
}