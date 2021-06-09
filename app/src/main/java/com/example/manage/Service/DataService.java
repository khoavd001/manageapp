package com.example.manage.Service;

import com.example.manage.Model.Account;
import com.example.manage.Model.BaiHat;
import com.example.manage.Model.CaSi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @GET("getallsinger.php")
    Call<List<CaSi>> GetAllSinger();

    @FormUrlEncoded
    @POST("login.php")
    Call<List<Account>> Login(@Field("username") String username, @Field("password") String password);
    //GET
    @GET("getallsong.php")
    Call<List<BaiHat>> GetAllSong();

    @FormUrlEncoded
    @POST("getcasibaihat.php")
    Call<List<BaiHat>> GetBaiHatCaSi(@Field("IdCaSi") int IdCaSi);

    //SEARCH
    @FormUrlEncoded
    @POST("searchbaihat.php")
    Call<List<BaiHat>> GetSearchBaiHat(@Field("tukhoa") String tukhoa);

    @FormUrlEncoded
    @POST("searchcasi.php")
    Call<List<CaSi>> GetSearchCaSi(@Field("tukhoa") String tukhoa);


    //UPDATE
    @FormUrlEncoded
    @POST("update.php")
    Call<String> GetUpdateSong(@Field("IdBaiHat") int IdBaiHat, @Field("TenBaiHat") String TenBaiHat, @Field("HinhBaiHat") String HinhBaiHat, @Field("LinkBaiHat") String LinkBaiHat, @Field("TenCaSi") String TenCaSi);


    //DELETE
    @FormUrlEncoded
    @POST("delete.php")
    Call<List<BaiHat>> GetDelteBaiHat(@Field("IdBaiHat") int IdBaiHat);



}
