package com.example.manage.Service;

import com.example.manage.Model.Account;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @FormUrlEncoded
    @POST("login.php")
    Call<List<Account>> Login(@Field("username") String username, @Field("password") String password);
}
