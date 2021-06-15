package com.example.manage.Activity.ui.album;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.manage.Adapter.AllAlbumAdapter;
import com.example.manage.Adapter.AllSingerAdapter;
import com.example.manage.Model.Album;
import com.example.manage.Model.CaSi;
import com.example.manage.R;
import com.example.manage.Service.APIService;
import com.example.manage.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateAlbumFragment extends Fragment {

    AllAlbumAdapter adapter;
    ArrayList<Album> arrayList;
    RecyclerView recyclerView;
    TextView textViewkhongtimthay;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_updatealbum, container, false);
//        textViewkhongtimthay=root.findViewById(R.id.textviewkhongcodulieualbum);


        recyclerView=root.findViewById(R.id.rv_allalbum);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        GetData();
        return root;
    }
    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<Album>> callback = dataService.GetAllAlbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                arrayList = (ArrayList<Album>) response.body();
                adapter = new AllAlbumAdapter(getActivity(), arrayList);

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

}