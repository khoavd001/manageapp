package com.example.manage.Activity.ui.singer;


import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manage.R;

public class SongsingerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_songsinger);
        Anhxa();
    }
    private void Anhxa(){
        recyclerView=findViewById(R.id.rv_songsinger);
        textView=findViewById(R.id.textviewkhongcodulieusongersinger);
    }
}
