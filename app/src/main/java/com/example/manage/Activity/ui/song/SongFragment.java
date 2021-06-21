package com.example.manage.Activity.ui.song;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manage.Activity.ManageActivity;
import com.example.manage.Adapter.AllSongAdapter;
import com.example.manage.MainActivity;
import com.example.manage.Model.BaiHat;
import com.example.manage.R;
import com.example.manage.Service.APIService;
import com.example.manage.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SongFragment extends Fragment {

    AllSongAdapter searchBaiHatadapter;
    SearchView searchView;
    public static RecyclerView recyclerView;
    ArrayList<BaiHat> arrayList;
    ImageButton imageButton;
    public static AllSongAdapter adapter;
    Toolbar toolbar;
    TextView textViewkhongtimthay;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_updatesong,container,false);
        textViewkhongtimthay=root.findViewById(R.id.textviewkhongcodulieu);

        imageButton=root.findViewById(R.id.imageaddbtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AddSongActivity.class);
                startActivity(intent);
            }
        });
//        searchView=root.findViewById(R.id.searchview_song);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                    Searchtukhoa(query);
//                    return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                return false;
//            }
//        });

        recyclerView=root.findViewById(R.id.rv_allsong);
        GetData();
        hasOptionsMenu();
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView=(androidx.appcompat.widget.SearchView)menuItem.getActionView();

        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    private void Searchtukhoa(String query){
        DataService dataService=APIService.getService();
        Call<List<BaiHat>> callback=dataService.GetSearchBaiHat(query);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> mangbaihat=(ArrayList<BaiHat>) response.body();
                if(mangbaihat.size()>0){
                    searchBaiHatadapter= new AllSongAdapter(getActivity(),mangbaihat);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(searchBaiHatadapter);
                    textViewkhongtimthay.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }else {
                    recyclerView.setVisibility(View.GONE);
                    textViewkhongtimthay.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
    private void GetData() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 100);
                if(ManageActivity.arrayList != null){
                    arrayList = ManageActivity.arrayList;
                    adapter= new AllSongAdapter(getActivity(),ManageActivity.arrayList);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);
                    Log.e("BBB", "TC");
                    handler.removeCallbacks(this);
                }
            }
        };
        handler.postDelayed(runnable, 100);
    }
}


