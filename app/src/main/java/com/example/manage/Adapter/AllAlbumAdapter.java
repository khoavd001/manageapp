package com.example.manage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manage.Activity.ui.singer.DetailSinger;
import com.example.manage.Model.Album;
import com.example.manage.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllAlbumAdapter extends RecyclerView.Adapter<AllAlbumAdapter.ViewHolder> {


    Context context;
    ArrayList<Album> arrayList;

    public AllAlbumAdapter(Context context, ArrayList<Album> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_all_album, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = arrayList.get(position);
        holder.TenAlbum.setText(album.getTenAlbum());
        holder.TenCaSi.setText(album.getTenCaSi());
        Picasso.with(context).load(album.getHinhAlbum().toString()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView TenCaSi, TenAlbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_album_all);
            TenAlbum = itemView.findViewById(R.id.txt_album_all);
            TenCaSi = itemView.findViewById(R.id.txt_album_casi_all);


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
//                    intent.putExtra("album", arrayList.get(getPosition()));
//                    context.startActivity(intent);
//                }
//            });
        }
    }
}
