package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Adapter;

import com.example.finalproject.Adapter.DrinkAdapter;
import com.example.finalproject.Model.Drinks;
import com.example.finalproject.Retrofit.ShopApi;
import com.example.finalproject.Utils.Common;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    List<String> suggestList = new ArrayList<>();
    List<Drinks> LocalDataSource = new ArrayList<>();

    MaterialSearchBar searchBar;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    RecyclerView recycler_search;

    DrinkAdapter searchAdapter,Adapter;

    ShopApi mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //init service
        mService = Common.getApi();

        recycler_search = findViewById(R.id.recycler_searchbar);
        recycler_search.setLayoutManager(new GridLayoutManager(this,2));

        searchBar = findViewById(R.id.SearchBar);
        searchBar.setHint("Enter your Drink");
        
        LoadAllDrinks();

        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                List<String> suggest = new ArrayList<>();
                for (String search:suggestList)
                {
                    if (search.toLowerCase().contains(searchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                searchBar.setLastSuggestions(suggest);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                    recycler_search.setAdapter(Adapter);      //restore full of drinks
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    private void startSearch(CharSequence text) {
        List<Drinks> result = new ArrayList<>();
        for (Drinks drinks:LocalDataSource)
            if (drinks.Name.contains(text))
                result.add(drinks);
            searchAdapter = new DrinkAdapter(this,result);
            recycler_search.setAdapter(searchAdapter);
    }

    private void LoadAllDrinks() {

        mService.getAllDrinks()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<List<Drinks>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Drinks> drinks) {
                displayDrinkList(drinks);
                displaySuggestList(drinks);

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void displayDrinkList(List<Drinks> drinks) {

        LocalDataSource = drinks;
        Adapter = new DrinkAdapter(this,drinks);
        recycler_search.setAdapter(Adapter);
    }


    private void displaySuggestList(List<Drinks> drinks) {
        for (Drinks drinks1:drinks)
            suggestList.add(drinks1.Name);
        searchBar.setLastSuggestions(suggestList);
    }
}