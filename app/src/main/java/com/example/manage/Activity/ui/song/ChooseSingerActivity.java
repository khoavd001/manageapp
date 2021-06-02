package com.example.manage.Activity.ui.song;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.manage.Adapter.AllSingerAdapter;
import com.example.manage.Adapter.AllSingerSongUpdate;
import com.example.manage.Model.CaSi;
import com.example.manage.R;
import com.example.manage.Service.APIService;
import com.example.manage.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseSingerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AllSingerSongUpdate adapter;
    TextView textViewkhongtimthay;
    Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_singer);
        recyclerView=findViewById(R.id.choosesingerrcv);
        textViewkhongtimthay=findViewById(R.id.textviewkhongcodulieusingerchoose);
        Intent intent=getIntent();
        GetData();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        this.menu=menu;

        SearchManager manager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        androidx.appcompat.widget.SearchView searchView=(androidx.appcompat.widget.SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Searchtukhoa(newText);
                return true;
            }
        });

        return true;
    }
    private void Searchtukhoa(String query){
        DataService dataService=APIService.getService();
        Call<List<CaSi>> callback=dataService.GetSearchCaSi(query);
        callback.enqueue(new Callback<List<CaSi>>() {
            @Override
            public void onResponse(Call<List<CaSi>> call, Response<List<CaSi>> response) {
                ArrayList<CaSi> mangbaihat=(ArrayList<CaSi>) response.body();
                if(mangbaihat.size()>0){
                    adapter= new AllSingerSongUpdate(ChooseSingerActivity.this,mangbaihat);
                    GridLayoutManager gridLayoutManager=new GridLayoutManager(ChooseSingerActivity.this,2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(adapter);
                    textViewkhongtimthay.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }else {
                    recyclerView.setVisibility(View.GONE);
                    textViewkhongtimthay.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<CaSi>> call, Throwable t) {

            }
        });
    }
    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<CaSi>> callback = dataService.GetAllSinger();
        callback.enqueue(new Callback<List<CaSi>>() {
            @Override
            public void onResponse(Call<List<CaSi>> call, Response<List<CaSi>> response) {
                ArrayList<CaSi> arrayList = (ArrayList<CaSi>) response.body();
                adapter = new AllSingerSongUpdate(ChooseSingerActivity.this, arrayList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(ChooseSingerActivity.this, 2));
            }

            @Override
            public void onFailure(Call<List<CaSi>> call, Throwable t) {

            }
        });

    }
}