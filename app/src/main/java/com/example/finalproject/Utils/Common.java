package com.example.finalproject.Utils;

import com.example.finalproject.Model.Cart;
import com.example.finalproject.Model.Drinks;
import com.example.finalproject.Model.Favourite;
import com.example.finalproject.Model.Order;
import com.example.finalproject.Model.Users;
import com.example.finalproject.Model.category;
import com.example.finalproject.Retrofit.RetrofitClient;
import com.example.finalproject.Retrofit.ShopApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Common {

    public static final String BASE_URL ="http://192.168.1.104/myFinalProject/";


    public static final String TOPPING_MENU_ID = "5";

    //new variable to hold data of menu items
    public static category currentcategory = null ;
    public static Order currentUser = null ;
    public static Order currentOrder = null;

    public static ShopApi insertData;


    //to fetch topping list
    public static List<Drinks> toppingList = new ArrayList<>();


    //create to variable one for list of topping an another for sum of topping price
    //double variable to hold sum of topping price
    public static Double Toppingprice = 0.0;
    //list string to hold topping value
    public static List<String> toppingAdded = new ArrayList<>();


    //hold fields
    public static int SizeOfCup = -1; //
    public static int sugar = -1;
    public static int ice = -1;

    public static String ConvertCodeToStatus(int OrderStatus){
        switch (OrderStatus)
        {
            case 0:
                return "placed";
            case 1:
                return "processing";
            case 2:
                return "Shipping";
            case 3:
                return "Shipped";
            case  -1:
                return "Cancelled";
            default:
                return "order error";

        }
    }

    public static ShopApi getApi(){

       /* HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .method(original.method(),original.body()).build();

                        return chain.proceed(request);
                    }
                }).build();
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit= builder.client(okHttpClient)
                .build();
        return retrofit.create(ShopApi.class);*/
        return RetrofitClient.getClient(BASE_URL).create(ShopApi.class);

    }
}
