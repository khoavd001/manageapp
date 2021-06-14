package com.example.manage.Activity.ui.singer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manage.Activity.ui.song.UpdateSongFragment;
import com.example.manage.Adapter.AllAlbumAdapter;
import com.example.manage.Adapter.AllSongAdapter;
import com.example.manage.Model.Album;
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

public class DetailSingerActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    ImageView imageView;
    RecyclerView recyclerViewBaiHat, recyclerViewAlBum;
    Button btn,addsongbtn;
    ProgressBar progressBar;
    TextView txtAlbum;
    ArrayList<BaiHat> baiHatArrayList= UpdateSongFragment.arrayList;
    ArrayList<Album> albumArrayList;
    public static ArrayList<BaiHat> tempbaiHatArrayList;
    int IdCaSi;

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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Songadapter.getItemCount() < 5){
                    Songadapter.setArrayList(baiHatArrayList);
                    btn.setText("Hiển thị ít");
                }
                else{
                    btn.setText("Xem Tất Cả");
                    Songadapter.setArrayList(tempbaiHatArrayList);
                }
            }
        });



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
    private void setDataAlbum() {
        if (albumArrayList.size() > 0) {

            AllAlbumAdapter adapter = new AllAlbumAdapter(this, albumArrayList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailSingerActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerViewAlBum.setLayoutManager(linearLayoutManager);
            recyclerViewAlBum.setAdapter(adapter);
        }
        else
        {
            txtAlbum.setVisibility(View.INVISIBLE);
        }


    }
    private void AnhXa(){
        recyclerViewAlBum=findViewById(R.id.rv_detailsinger_album);
        imageView=findViewById(R.id.img_detailsinger);
        recyclerViewBaiHat=findViewById(R.id.rv_detailsinger_baihat);
        toolbar=findViewById(R.id.toolbar_detailsinger);
        tempbaiHatArrayList=new ArrayList<>();
        btn=findViewById(R.id.btn_viewmore_baihat_detailsinger);
        addsongbtn=findViewById(R.id.addsongtosinger);

//    recyclerViewAlBum=findViewById(R.id.rv_detailsinger_album);
    }
    private void GetIntent(){
        Intent intent = getIntent();

        if (intent.hasExtra("CaSi")) {
            caSi = (CaSi) intent.getSerializableExtra("CaSi");
            IdCaSi=Integer.parseInt(caSi.getIdCaSi());
            getbaihatcasi(IdCaSi);
            getAlbumCaSi(IdCaSi);
            setInfoCaSi();
        }
    }
    private void getAlbumCaSi(int idCaSi) {
        DataService dataService = APIService.getService();
        Call<List<Album>> callback = dataService.GetAlbumCasi(idCaSi);
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                albumArrayList = (ArrayList<Album>) response.body();
                setDataAlbum();
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Toast.makeText(DetailSingerActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void setDataBaiHat() {
        if( baiHatArrayList.size() < 5)
            Songadapter = new AllSongAdapter(this, baiHatArrayList);
        else
            Songadapter = new AllSongAdapter(this, tempbaiHatArrayList);
        recyclerViewBaiHat.setAdapter(Songadapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailSingerActivity.this);
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