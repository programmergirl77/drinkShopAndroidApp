package com.example.finalproject.Retrofit;

import com.example.finalproject.Model.Banner;
import com.example.finalproject.Model.Cart;
import com.example.finalproject.Model.Drinks;
import com.example.finalproject.Model.Favourite;
import com.example.finalproject.Model.InsertResp;
import com.example.finalproject.Model.LoginResponse;
import com.example.finalproject.Model.Order;
import com.example.finalproject.Model.RegisterResponse;
import com.example.finalproject.Model.Users;
import com.example.finalproject.Model.category;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ShopApi {

    @FormUrlEncoded
    @POST("register.php")
    Call<String> register(
            @Field("firstName") String firstname,
            @Field("lastName") String lastName,
            @Field("userName") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Observable<LoginResponse> login(
            @Field("userName") String username,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("getDrink.php")
    Observable<List<Drinks>> getDrinks(@Field("MenuID") String MenuId);


    @GET("getBanners.php")
    Observable<List<Banner>> getBaners();

    @GET("getMenu.php")
    Observable<List<category>> getMenu();

    @GET("getCartItems.php")
    Observable<List<Cart>> getCartItems();


    @GET("getFavouriteItems.php")
    Observable<List<Favourite>> getFavouriteItems();

    @GET("CountCartItem.php")
    Observable<Cart> countCartItem();

    @FormUrlEncoded
    @POST("insertToCart.php")
    Observable<InsertResp> InsertToCart(@Field("Name") String name, @Field("image") String image,
                                        @Field("Amount") int amount,
                                        @Field("price") double price, @Field("Sugar") int sugar,
                                        @Field("Ice") int ice, @Field("ToppingExtras") String toppingExtras
                                        );

    @FormUrlEncoded
    @POST("InsertToFavourite.php")
    Observable<List<Favourite>> InsertToFavourite(@Field("Name") String name,@Field("Link") String image,
                                                  @Field("price") double price);

    @Multipart
    @POST("Upload.php")
    Call<String> uploadFile(@Part("userName") String userName, @Part MultipartBody.Part file);

    @GET("getAllDrinks.php")
    Observable<List<Drinks>> getAllDrinks();

    @POST("submitOrder.php")
    Call<String> submitOrder(@Field("price") float price,
                             @Field("orderDetail") String orderDetail,
                             @Field("comment") String comment,
                             @Field("Address") String Address,
                             @Field("phone") String phone);
    @FormUrlEncoded
    @POST("getOrder.php")
    Observable<List<Order>> getOrder(@Field("UserPhone") String UserPhone,
                                     @Field("Status") String Status);

    @FormUrlEncoded
    @POST("CancelOrder.php")
    Call<String> CancelOrder(@Field("orderId") String orderId,
                                     @Field("userPhone") String userPhone);


    @FormUrlEncoded
    @POST("DeleteCartItem.php")
    Observable<Cart> DeleteCartItem(@Field("id") int id);

    @FormUrlEncoded
    @POST("DeleteFavouriteItem.php")
    Observable<Favourite> DeleteFavouriteItem(@Field("id") int id);


    @GET("SumPriceCart.php")
    Float SumPriceCart();


}
