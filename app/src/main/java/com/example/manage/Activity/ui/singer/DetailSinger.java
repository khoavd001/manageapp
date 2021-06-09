package com.example.manage.Activity.ui.singer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manage.Activity.ui.song.UpdateSongFragment;
import com.example.manage.Adapter.AllSongAdapter;
import com.example.manage.Model.BaiHat;
import com.example.manage.Model.CaSi;
import com.example.manage.R;
import com.example.manage.Service.APIService;
import com.example.manage.Service.DataService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSinger extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    ImageView imageView;
    RecyclerView recyclerViewBaiHat, recyclerViewAlBum;
    Button btn;
    ProgressBar progressBar;
    TextView txtAlbum;
    ArrayList<BaiHat> baiHatArrayList= UpdateSongFragment.arrayList;
    public static ArrayList<BaiHat> tempbaiHatArrayList;
    int IdCaSi;
//    ArrayList<Album> albumArrayList;
    AllSongAdapter Songadapter;
    CaSi caSi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_singer);
        AnhXa();
        GetIntent();
        toolbar=findViewById(R.id.toolbar_detailsinger);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(caSi.getTenCaSi().toString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    private void ViewMore(){
        if(baiHatArrayList.size() < 5){
            btn.setVisibility(View.INVISIBLE);
        }
        else
        {
            tempbaiHatArrayList.add(baiHatArrayList.get(0));
            tempbaiHatArrayList.add(baiHatArrayList.get(1));
            tempbaiHatArrayList.add(baiHatArrayList.get(2));
            tempbaiHatArrayList.add(baiHatArrayList.get(3));
        }
    }
    private void AnhXa(){
    imageView=findViewById(R.id.img_detailsinger);
    recyclerViewBaiHat=findViewById(R.id.rv_detailsinger_baihat);
    toolbar=findViewById(R.id.toolbar_detailsinger);
    tempbaiHatArrayList=new ArrayList<>();
//    recyclerViewAlBum=findViewById(R.id.rv_detailsinger_album);
    }
    private void GetIntent(){
        Intent intent = getIntent();

        if (intent.hasExtra("CaSi")) {
            caSi = (CaSi) intent.getSerializableExtra("CaSi");
            IdCaSi=Integer.parseInt(caSi.getIdCaSi());
            getbaihatcasi(IdCaSi);
//            getAlbumCaSi(caSi.getIdCaSi());
            setInfoCaSi();
        }
    }
    private void setDataBaiHat() {
        if( baiHatArrayList.size() < 5)
            Songadapter = new AllSongAdapter(this, baiHatArrayList);
        else
            Songadapter = new AllSongAdapter(this, tempbaiHatArrayList);
        recyclerViewBaiHat.setAdapter(Songadapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailSinger.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewBaiHat.setLayoutManager(linearLayoutManager);
    }
    private void getbaihatcasi(int idcasi){
        DataService dataService=APIService.getService();
        Call<List<BaiHat>> callback=dataService.GetBaiHatCaSi(idcasi);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                baiHatArrayList=(ArrayList<BaiHat>) response.body();
               ViewMore();
               setDataBaiHat();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
    private void setInfoCaSi() {
        Picasso.with(this).load(caSi.getHinhCaSi()).into(imageView);
        toolbar.setTitle(caSi.getTenCaSi());
    }

}