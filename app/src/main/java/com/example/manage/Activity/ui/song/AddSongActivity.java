package com.example.manage.Activity.ui.song;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.manage.Activity.ManageActivity;
import com.example.manage.Activity.ui.singer.DetailSingerActivity;
import com.example.manage.Model.BaiHat;
import com.example.manage.R;
import com.example.manage.Service.APIService;
import com.example.manage.Service.DataService;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddSongActivity extends AppCompatActivity {
    TextInputEditText tenbaihatedit,linkbaihatedit,linkhinhbaihat;
    RadioButton rdlinkhinh,rdfilehinh,rdlinkmp3,rdfilemp3;
    ImageView hinhbaihat;
    Button thembtn;
    int Requesthinhbaihat=123;
    Bitmap bitmap;
    BaiHat baiHat=new BaiHat();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixsongactivity);
        tenbaihatedit=findViewById(R.id.tenbaihataddsong);
        Toolbar toolbar=findViewById(R.id.addsongtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Thêm bài hát");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        hinhbaihat=(ImageView)findViewById(R.id.hinhbaihatadd);


        Intent intent=getIntent();
        chooseimage();
        AnhXa();
        onclick();

        setcheckrd();
    }
    public void chooseimage(){
        hinhbaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageUpload(Requesthinhbaihat);
            }
        });
    }
    private void AnhXa(){
        tenbaihatedit=findViewById(R.id.tenbaihataddsong);
        linkbaihatedit=findViewById(R.id.edittxtlinkbaihataddsong);
        linkhinhbaihat=findViewById(R.id.edittxtaddsongimage);

        thembtn=findViewById(R.id.addsongbtn);
        rdlinkhinh=findViewById(R.id.linkimage_a);
        rdfilehinh=findViewById(R.id.linkimamge_b);
        rdlinkmp3=findViewById(R.id.linksong_a);
        rdfilemp3=findViewById(R.id.linksong_b);

    }
    private void setcheckrd(){
        rdlinkhinh.setOnCheckedChangeListener(listenerrdlinkhinh);
        rdfilehinh.setOnCheckedChangeListener(listenerfilehinh);
        rdlinkmp3.setOnCheckedChangeListener(listenerrdlinkbaihat);
        rdfilemp3.setOnCheckedChangeListener(listenerfilebaihat);
    }
    CompoundButton.OnCheckedChangeListener listenerrdlinkhinh=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                enableEditText(linkhinhbaihat);
            }
        }
    };
    CompoundButton.OnCheckedChangeListener listenerfilehinh=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                disableEditText(linkhinhbaihat);
            }
        }
    };
    CompoundButton.OnCheckedChangeListener listenerrdlinkbaihat=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                enableEditText(linkbaihatedit);
            }
        }
    };
    CompoundButton.OnCheckedChangeListener listenerfilebaihat=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                disableEditText(linkbaihatedit);
            }
        }
    };

    private void enableEditText(TextInputEditText editTextWight) {
        editTextWight.setEnabled(true);
        editTextWight.setInputType(InputType.TYPE_CLASS_TEXT);
        editTextWight.setFocusable(true);
        editTextWight.setSingleLine(false);

    }
    private void disableEditText(TextInputEditText editTextWight) {
        editTextWight.setEnabled(false);
        editTextWight.setInputType(InputType.TYPE_NULL);
        editTextWight.setFocusable(false);
        editTextWight.setFocusableInTouchMode(true);

    }
    private void SelectImageUpload(int code) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Requesthinhbaihat);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                if (requestCode == Requesthinhbaihat) {
                    hinhbaihat.setImageBitmap(bitmap);


                }




            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onclick(){



        thembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addsong();
            }
        });
    }
    public void addsong(){


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageInByte,Base64.DEFAULT);

//        FileInputStream fileInputStream=new FileInputStream(new File(selectedpath));

        String TenFile = "https://regulatory-alcoholi.000webhostapp.com/server/picture/" + "HinhBaiHat"+tenbaihatedit.getText().toString() + ".jpg";
        DataService dataService= APIService.getService();
        Call<String> callback=dataService.AddSong(tenbaihatedit.getText().toString(), encodedImage, "HinhBaiHat"+tenbaihatedit.getText().toString(), linkbaihatedit.getText().toString());
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                String result=(String) response.body();
                if (result.equals("that bai")) {
                    Toast.makeText(AddSongActivity.this, "Lỗi Hệ Thống", Toast.LENGTH_SHORT).show();

                } else{
                    baiHat.setTenBaiHat(tenbaihatedit.getText().toString());
                    baiHat.setLinkBaiHat(linkbaihatedit.getText().toString());
                    baiHat.setHinhBaiHat(TenFile);
                    ManageActivity.arrayList.add(baiHat);
                    SongFragment.adapter.notifyDataSetChanged();
                    Toast.makeText(AddSongActivity.this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }



            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(AddSongActivity.this, "Lỗi ", Toast.LENGTH_SHORT).show();

            }
        });



    }



}