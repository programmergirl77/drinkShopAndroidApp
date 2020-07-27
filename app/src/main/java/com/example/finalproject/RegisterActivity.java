package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Model.RegisterResponse;
import com.example.finalproject.Retrofit.ShopApi;
import com.example.finalproject.Utils.Common;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstname,lastname,email,password,username;
    private Button registerBtn;
    private TextView login_txt;
    ShopApi mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mService = Common.getApi();

        firstname = findViewById(R.id.edt_firstname);
        lastname = findViewById(R.id.edt_Lastname);
        email = findViewById(R.id.edt_userName);
        password = findViewById(R.id.edt_pass);
        username = findViewById(R.id.edt_name);
        registerBtn = findViewById(R.id.btn_register);
        login_txt=findViewById(R.id.txt_login);

        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Fname = firstname.getText().toString();
                String Lname = lastname.getText().toString();
                String Emaill = email.getText().toString();
                String pass = password.getText().toString();
                String user = username.getText().toString();
                //check to write all fields
                if (TextUtils.isEmpty(Emaill))
                {
                    Toast.makeText(RegisterActivity.this,"please enter your email...",Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(pass))
                {
                    Toast.makeText(RegisterActivity.this,"please enter your password...",Toast.LENGTH_LONG).show();

                }
                else if (TextUtils.isEmpty(Fname))
                {
                    Toast.makeText(RegisterActivity.this,"please enter your first name...",Toast.LENGTH_LONG).show();

                }
                else if (TextUtils.isEmpty(Lname))
                {
                    Toast.makeText(RegisterActivity.this,"please enter your last name...",Toast.LENGTH_LONG).show();

                }
                else if (TextUtils.isEmpty(user))
                {
                    Toast.makeText(RegisterActivity.this,"please enter your username...",Toast.LENGTH_LONG).show();

                }
                else {
                    Log.v("register","before");
                   RegisterMain(Fname,Lname,Emaill,user,pass);
                }
            }
        });
    }

    private void RegisterMain(String fname, String lname, String emaill, String user, String pass) {
       Call<String> responseCall= mService.register(fname,lname,user,emaill,pass);
       responseCall.enqueue(new Callback<String>() {
           @Override
           public void onResponse(Call<String> call, Response<String> response) {
               Toast.makeText(RegisterActivity.this, "Registration done successfully ", Toast.LENGTH_SHORT).show();
               startActivity(new Intent(RegisterActivity.this,MainActivity.class));
               finish();
           }

           @Override
           public void onFailure(Call<String> call, Throwable t) {

           }
       });
         /*       .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegisterResponse registerResponse) {
                        Toast.makeText(RegisterActivity.this, "Registration done successfully ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
    }
}