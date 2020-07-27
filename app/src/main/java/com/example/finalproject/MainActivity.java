package com.example.finalproject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.Toast;


import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.finalproject.Adapter.CartAdapter;
import com.example.finalproject.Adapter.CategoryAdapter;
import com.example.finalproject.Model.Banner;
import com.example.finalproject.Model.Cart;
import com.example.finalproject.Model.Drinks;
import com.example.finalproject.Model.category;
import com.example.finalproject.Retrofit.ShopApi;
import com.example.finalproject.Utils.Common;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private SliderLayout sliderLayout;
    private ShopApi mService;
    private CompositeDisposable compositeDisposable;
    private RecyclerView lst_menu;
    private NotificationBadge badge;
    private ImageView cart_icon;
    private static final int REQUEST_PERMISiON=1001;

    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mdrawerToggle;
    private CircleImageView img_avatar;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mService = Common.getApi();

        //set drawer layout
        mdrawerLayout = findViewById(R.id.drawerLayoutMain);
        mdrawerToggle = new ActionBarDrawerToggle(this,mdrawerLayout,R.string.open,R.string.close);
        mdrawerLayout.addDrawerListener(mdrawerToggle);
        mdrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@androidx.annotation.NonNull MenuItem item) {
                UserMenuselector(item);
                return false;
            }
        });

        //set slider layout
        sliderLayout = findViewById(R.id.slider);

        //set list menu of products
        lst_menu = findViewById(R.id.lst_menu);
        lst_menu.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        lst_menu.setHasFixedSize(true);


        //get banner from server
        getBannersImage();

        //get menu information from server
        getMenu();

        //get newest topping list
        getToppingList();


    }

    private void UserMenuselector(MenuItem item) {

        int id = item.getItemId();

        if (id==R.id.nav_signOut)
        {
            //create confirm dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit application");
            builder.setMessage("Do you want to exit application?");

            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    //clear all activity
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
            });

            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }
            });
        }

        if (id==R.id.Fave_Item)
        {
            startActivity(new Intent(MainActivity.this,favouriteListactivity.class));

        }
        if (id==R.id.nav_order)
        {
            //if (Common.currentUser != null) {
                startActivity(new Intent(MainActivity.this, ShowOrderActivity.class));
            //}
           // else {
               // Toast.makeText(this, "please login to use this feature", Toast.LENGTH_SHORT).show();
           // }
        }

    }

    private void getToppingList() {

        mService.getDrinks(Common.TOPPING_MENU_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Drinks>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Drinks> drinks) {
                        Common.toppingList = drinks;

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void getMenu() {
        mService.getMenu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<category>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull List<category> categories) {
                        displayMenu(categories);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void displayMenu(List<category> categories) {

        CategoryAdapter adapter = new CategoryAdapter(this, categories);
        lst_menu.setAdapter(adapter);
    }

    private void getBannersImage() {
        mService.getBaners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Banner>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {


                    }

                    @Override
                    public void onNext(@NonNull List<Banner> banners) {
                        displayImage(banners);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void displayImage(List<Banner> banners) {

        HashMap<String, String> bannerMap = new HashMap<>();

        for (Banner item : banners)
            bannerMap.put(item.getName(), item.getLink());

        for (String name : bannerMap.keySet()) {

            TextSliderView textSliderView = new TextSliderView(this);

            textSliderView.description(name)
                    .image(Common.BASE_URL+bannerMap.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            sliderLayout.addSlider(textSliderView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       // return true;
        getMenuInflater().inflate(R.menu.menu_action_bar,menu);
       // View view = menu.findItem(R.id.cart_menu).getActionView();
        //badge = view.findViewById(R.id.cart_notif);
//        cart_icon = view.findViewById(R.id.cart_menu);
//        cart_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,CartActivity.class));
//            }
//        });
        //UpdateCartItem();
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull MenuItem item) {
        if (mdrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        int id = item.getItemId();

        if (id == R.id.cart_menu)
        {
            startActivity(new Intent(this,CartActivity.class));
            return true;
        }
        if (id==R.id.search_menu)
        {
            startActivity(new Intent(MainActivity.this,SearchActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

