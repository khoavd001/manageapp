package com.example.manage.Activity.ui.song;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.manage.Activity.ui.singer.DetailSingerActivity;
import com.example.manage.Adapter.AllSongAdapter;
import com.example.manage.Model.BaiHat;
import com.example.manage.R;
import com.example.manage.Service.APIService;
import com.example.manage.Service.DataService;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateSongActivity extends AppCompatActivity {
    BaiHat baiHat;
    String tenbaihat=new String(),linkbaihat,hinhbaihat,idbaihat;
    EditText tenbaihatedit,linkbaihatedit;
    ImageView hinhbaihatedit;
    Button capnhap;
    Spinner spinner;
    ImageButton imageButton;
    TextView textViewtencasi;
    AllSongAdapter adapter;
    ArrayList<BaiHat> arrayList;
    int RequestAvatar = 123;
    Toolbar toolbar;
    int Pos;
    Bitmap bitmap;
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
                update();

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent=new Intent(UpdateSongActivity.this, ChooseSingerActivity.class);
               UpdateSongActivity.this.startActivity(intent);
            }
        });
        GetIntent();
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(baiHat.getTenBaiHat().toString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSupportActionBar().setHomeButtonEnabled(true);
        chooseimage();

    }
    public void chooseimage(){
        hinhbaihatedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageUpload(RequestAvatar);

            }
        });
    }



    private void SelectImageUpload(int code) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, code);
    }
    private void GetIntent(){


        Intent intent = getIntent();

        if (intent.hasExtra("tenbaihat")) {

            tenbaihatedit.setText((String) intent.getSerializableExtra("tenbaihat"));

        }
        if (intent.hasExtra("hinhbaihat")) {
            hinhbaihat=(String) intent.getSerializableExtra("hinhbaihat");

            Picasso.with(this).load(hinhbaihat).into(hinhbaihatedit);


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
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                if (requestCode == RequestAvatar) {
                    hinhbaihatedit.setImageBitmap(bitmap);


                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void update(){


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageInByte,Base64.DEFAULT);

        String TenFile = "https://regulatory-alcoholi.000webhostapp.com/server/picture/" + "HinhBaiHat"+idbaihat + ".jpg";
        DataService dataService= APIService.getService();
        Call<String> callback=dataService.GetUpdateSong(idbaihatint, tenbaihatedit.getText().toString(), encodedImage, "HinhBaiHat"+idbaihat, linkbaihatedit.getText().toString(), textViewtencasi.getText().toString());
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result=(String) response.body();
                if (result.equals("that bai")) {
                    Toast.makeText(UpdateSongActivity.this, "Lỗi Hệ Thống", Toast.LENGTH_SHORT).show();

                } else{

                    UpdateSongFragment.arrayList.get(Pos).setTenBaiHat(tenbaihatedit.getText().toString());
                    UpdateSongFragment.arrayList.get(Pos).setHinhBaiHat(TenFile);
                    UpdateSongFragment.arrayList.get(Pos).setLinkBaiHat(linkbaihatedit.getText().toString());
                    if(DetailSingerActivity.tempbaiHatArrayList!=null) {
                        DetailSingerActivity.tempbaiHatArrayList.get(Pos).setTenBaiHat(tenbaihatedit.getText().toString());
                        DetailSingerActivity.tempbaiHatArrayList.get(Pos).setLinkBaiHat(linkbaihatedit.getText().toString());
                        DetailSingerActivity.tempbaiHatArrayList.get(Pos).setHinhBaiHat(TenFile);
                    }
                    UpdateSongFragment.adapter.notifyItemChanged(Pos);
                    Toast.makeText(UpdateSongActivity.this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }

                UpdateSongFragment.adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(UpdateSongActivity.this, "Lỗi ", Toast.LENGTH_SHORT).show();

            }
        });



    }

}