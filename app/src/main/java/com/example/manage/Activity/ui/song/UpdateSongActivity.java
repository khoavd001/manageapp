package com.example.manage.Activity.ui.song;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.manage.Activity.ManageActivity;
import com.example.manage.Activity.ui.singer.DetailSingerActivity;
import com.example.manage.Adapter.AllSongAdapter;
import com.example.manage.Model.BaiHat;
import com.example.manage.R;
import com.example.manage.Service.APIService;
import com.example.manage.Service.DataService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
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
    TextInputEditText tenbaihatedit,linkbaihatedit,linkhinhbaihat;
    ImageView hinhbaihatedit;
    Button capnhap,chonlinkfile;
    MaterialButton cancle;
    Spinner spinner;
    ImageButton imageButton;

    AllSongAdapter adapter;
    ArrayList<BaiHat> arrayList;
    int RequestAvatar = 123;
    int RequestMp3=456;
    Toolbar toolbar;
    int Pos;
    RadioButton rdlinkhinh,rdfilehinh,rdlinkmp3,rdfilemp3;
    Bitmap bitmap,bitmapmp3;
    int idbaihatint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_song);
        tenbaihatedit=findViewById(R.id.tenbaihatupdate);
        hinhbaihatedit=findViewById(R.id.hinhbaihatupdate);
        linkbaihatedit=findViewById(R.id.linkbaihatupdate);
        linkhinhbaihat=findViewById(R.id.linkhinhbaihatupdate);
//        chonlinkfile=findViewById(R.id.chonlinkfinkmp3btn);
        toolbar=findViewById(R.id.fixtoolbar);
        capnhap=findViewById(R.id.fixsongbtn);
        rdlinkhinh=findViewById(R.id.linkimageupdate_a);
        rdfilehinh=findViewById(R.id.linkimamgeupdate_b);
        rdlinkmp3=findViewById(R.id.linksongupdate_a);
        rdfilemp3=findViewById(R.id.linksongupdate_b);
        cancle=findViewById(R.id.cancelupdatebtn);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(UpdateSongActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Closing Activity")
                        .setMessage("Are you sure you want to close this activity?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        capnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();

            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Chỉnh sửa bài hát");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        chooseimage();
        setrdchecked();
        GetIntent();
//        choosemp3();

    }
    public void chooseimage(){
        hinhbaihatedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageUpload(RequestAvatar);

            }
        });
    }
    public void choosemp3(){
        chonlinkfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectMp3Upload(RequestMp3);
            }
        });
    }
    private void SelectMp3Upload(int code) {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, RequestMp3);
    }


    private void SelectImageUpload(int code) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, RequestAvatar);
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
    private void setrdchecked(){
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
    String selectedpath="";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();
            Uri mp3=data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                if (requestCode == RequestAvatar) {
                    hinhbaihatedit.setImageBitmap(bitmap);


                }
                if(requestCode==RequestMp3)
                {

                    System.out.println("RequestMp3");
                    selectedpath=getPath(mp3);
                    linkbaihatedit.setText(selectedpath);
                }



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public void update(){


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
            byte[] imageInByte = byteArrayOutputStream.toByteArray();
            String encodedImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);


//        FileInputStream fileInputStream=new FileInputStream(new File(selectedpath));

        String TenFile = "https://regulatory-alcoholi.000webhostapp.com/server/picture/" + "HinhBaiHat"+idbaihat + ".jpg";
        DataService dataService= APIService.getService();
        Call<String> callback=dataService.GetUpdateSong(idbaihatint, tenbaihatedit.getText().toString(), encodedImage, "HinhBaiHat"+idbaihat, linkbaihatedit.getText().toString());
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result=(String) response.body();
                if (result.equals("that bai")) {
                    Toast.makeText(UpdateSongActivity.this, "Lỗi Hệ Thống", Toast.LENGTH_SHORT).show();

                } else{

                    ManageActivity.arrayList.get(Pos).setTenBaiHat(tenbaihatedit.getText().toString());
                    ManageActivity.arrayList.get(Pos).setHinhBaiHat(TenFile);
                    ManageActivity.arrayList.get(Pos).setLinkBaiHat(linkbaihatedit.getText().toString());
                    if(DetailSingerActivity.tempbaiHatArrayList!=null) {
                        DetailSingerActivity.tempbaiHatArrayList.get(Pos).setTenBaiHat(tenbaihatedit.getText().toString());
                        DetailSingerActivity.tempbaiHatArrayList.get(Pos).setLinkBaiHat(linkbaihatedit.getText().toString());
                        DetailSingerActivity.tempbaiHatArrayList.get(Pos).setHinhBaiHat(TenFile);
                    }
                    SongFragment.adapter.notifyItemChanged(Pos);
                    Toast.makeText(UpdateSongActivity.this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }

                SongFragment.adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(UpdateSongActivity.this, "Lỗi ", Toast.LENGTH_SHORT).show();

            }
        });



    }

}