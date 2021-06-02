package com.example.manage.Activity.ui.singer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.manage.Adapter.AllSingerAdapter;
import com.example.manage.Adapter.AllSingerSongUpdate;
import com.example.manage.Adapter.AllSongAdapter;
import com.example.manage.Model.BaiHat;
import com.example.manage.Model.CaSi;
import com.example.manage.R;
import com.example.manage.Service.APIService;
import com.example.manage.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingerFragment extends Fragment {
    AllSingerAdapter adapter;
    private SingerViewModel singerViewModel;
    RecyclerView recyclerView;
    TextView textViewkhongtimthay;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        singerViewModel =
                new ViewModelProvider(this).get(SingerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_singer, container, false);
        textViewkhongtimthay=root.findViewById(R.id.textviewkhongcodulieusinger);
        singerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        recyclerView=root.findViewById(R.id.rc_allsinger);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        GetData();

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
        Call<List<CaSi>> callback=dataService.GetSearchCaSi(query);
        callback.enqueue(new Callback<List<CaSi>>() {
            @Override
            public void onResponse(Call<List<CaSi>> call, Response<List<CaSi>> response) {
                ArrayList<CaSi> mangbaihat=(ArrayList<CaSi>) response.body();
                if(mangbaihat.size()>0){
                    adapter= new AllSingerAdapter(getActivity(),mangbaihat);
                    GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
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
                adapter = new AllSingerAdapter(getActivity(), arrayList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            }

            @Override
            public void onFailure(Call<List<CaSi>> call, Throwable t) {

            }
        });

    }
}