package com.example.mafia.data.remote;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.mafia.data.UserDto;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface UserApi {

    String BASE_URL = "https://mafia-6ea73-default-rtdb.europe-west1.firebasedatabase.app";

    @GET("users.json")
    Call<List<UserDto>> getUsers();

    static UserApi createApi() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserApi.class);
    }
}
