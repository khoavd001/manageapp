package com.example.manage.Adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.manage.Activity.ui.song.UpdateSong;
import com.example.manage.Model.BaiHat;
import com.example.manage.R;
import com.example.manage.Service.APIService;
import com.example.manage.Service.DataService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSongAdapter extends RecyclerView.Adapter<AllSongAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> arrayList;

    public AllSongAdapter(Context context, ArrayList<BaiHat> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_all_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = arrayList.get(position);
        Picasso.with(context).load(baiHat.getHinhBaiHat()).into(holder.imageView);
        holder.TenCaSi.setText(baiHat.getTenAllCaSi());
        holder.TenBaiHat.setText(baiHat.getTenBaiHat());
        holder.idbaihat.setText(baiHat.getIdBaiHat());
        holder.tenbaihat=baiHat.getTenBaiHat().toString();
        holder.linkbaihat=baiHat.getLinkBaiHat().toString();
        holder.hinhbaihat=baiHat.getHinhBaiHat().toString();


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView TenBaiHat, TenCaSi, idbaihat;
        ImageButton delete;
        ImageButton update;
        String tenbaihat,hinhbaihat,linkbaihat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_song_all);
            TenBaiHat = itemView.findViewById(R.id.txt_song_all);
            TenCaSi = itemView.findViewById(R.id.txt_song_casi_all);
            delete=(ImageButton) itemView.findViewById(R.id.deletebtn);
            idbaihat=itemView.findViewById(R.id.idbaihat);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id= Integer.parseInt(idbaihat.getText().toString());
                    deletebaihat(id);
//                    removedAt(getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyDataSetChanged();
                    Intent intent = new Intent(context, UpdateSong.class);
                    intent.putExtra("tenbaihat",tenbaihat);
                    intent.putExtra("linkbaihat",linkbaihat);
                    intent.putExtra("hinhbaihat", hinhbaihat);
                    intent.putExtra("tencasi", TenCaSi.getText().toString());
                    intent.putExtra("idbaihat", idbaihat.getText().toString());
                    intent.putExtra("mangbaihat", arrayList);
                    intent.putExtra("position", getPosition());
                    context.startActivity(intent);
                }
            });



        }
    }
    private void deletebaihat(int idbaihat){
        DataService dataService= APIService.getService();
        Call<List<BaiHat>> callback=dataService.GetDelteBaiHat(idbaihat);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                arrayList=(ArrayList<BaiHat>) response.body();

            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });

    }
    public void removedAt(int position){
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayList.size());
    }

    public void setArrayList(ArrayList<BaiHat> baiHats){
        arrayList = baiHats;
        notifyDataSetChanged();
    }

}