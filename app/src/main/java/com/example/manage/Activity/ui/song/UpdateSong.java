package com.example.manage.Activity.ui.song;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.manage.Adapter.AllSingerSongUpdate;
import com.example.manage.Adapter.AllSongAdapter;
import com.example.manage.Model.BaiHat;
import com.example.manage.R;
import com.example.manage.Service.APIService;
import com.example.manage.Service.DataService;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateSong extends AppCompatActivity {
    BaiHat baiHat;
    String tenbaihat=new String(),linkbaihat,hinhbaihat,idbaihat;
    EditText tenbaihatedit,linkbaihatedit,hinhbaihatedit;
    Button capnhap;
    Spinner spinner;
    ImageButton imageButton;
    TextView textViewtencasi;
    AllSongAdapter adapter;
    ArrayList<BaiHat> arrayList;
    int Pos;
    int idbaihatint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_song);
        tenbaihatedit=findViewById(R.id.tenbaihatedit);
        hinhbaihatedit=findViewById(R.id.hinhbaihatedit);
        linkbaihatedit=findViewById(R.id.linkbaihateditext);
        imageButton=findViewById(R.id.imgsingerchoose);
        textViewtencasi=findViewById(R.id.tencasiupdate);
        capnhap=findViewById(R.id.fixsongbtn);
        capnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                update(Integer.parseInt(idbaihat),tenbaihatedit.getText().toString(),hinhbaihatedit.getText().toString(),linkbaihatedit.getText().toString(),textViewtencasi.getText().toString());
                

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent=new Intent(UpdateSong.this, ChooseSingerActivity.class);
               UpdateSong.this.startActivity(intent);
            }
        });


        Intent intent = getIntent();

        if (intent.hasExtra("tenbaihat")) {

            tenbaihatedit.setText((String) intent.getSerializableExtra("tenbaihat"));

        }
        if (intent.hasExtra("hinhbaihat")) {

            hinhbaihatedit.setText((String) intent.getSerializableExtra("hinhbaihat"));


        }

        if (intent.hasExtra("linkbaihat")) {

            linkbaihatedit.setText((String) intent.getSerializableExtra("linkbaihat"));

        }
        if (intent.hasExtra("tencasi")) {

            textViewtencasi.setText((String) intent.getSerializableExtra("tencasi"));

        }
        if (intent.hasExtra("idbaihat")) {

            idbaihat=(String) intent.getSerializableExtra("idbaihat");

            idbaihatint=Integer.parseInt(idbaihat);

        }
        if(intent.hasExtra("posoition"))
            Pos=intent.getIntExtra("position", 0);
        if(intent.hasExtra("mangbaihat"))
            arrayList=intent.getParcelableArrayListExtra("mangbaihat");
            baiHat=arrayList.get(Pos);


    }

    public void update(int IdBaiHat, String TenBaiHat, String HinhBaiHat, String LinkBaiHat, String TenCaSi){


        DataService dataService= APIService.getService();
        Call<String> callback=dataService.GetUpdateSong(IdBaiHat, TenBaiHat, HinhBaiHat, LinkBaiHat, TenCaSi);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result=(String) response.body();
                if (result.equals("Error updating record")) {
                    Toast.makeText(UpdateSong.this, "Lỗi Hệ Thống", Toast.LENGTH_SHORT).show();

                } else{
                    UpdateSongFragment.arrayList.get(Pos).setTenBaiHat(TenBaiHat);
                    UpdateSongFragment.arrayList.get(Pos).setLinkBaiHat(LinkBaiHat);
                    UpdateSongFragment.arrayList.get(Pos).setHinhBaiHat(HinhBaiHat);


                    UpdateSongFragment.adapter.notifyDataSetChanged();
                    Toast.makeText(UpdateSong.this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }

                UpdateSongFragment.adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(UpdateSong.this, "Lỗi ", Toast.LENGTH_SHORT).show();

            }
        });



    }

}