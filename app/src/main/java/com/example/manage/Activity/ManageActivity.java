package com.example.manage.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manage.Activity.ui.song.SongFragment;
import com.example.manage.Adapter.AllSongAdapter;
import com.example.manage.MainActivity;
import com.example.manage.Model.BaiHat;
import com.example.manage.R;
import com.example.manage.Service.APIService;
import com.example.manage.Service.DataService;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static ArrayList<BaiHat> arrayList;
    String username,password;
    ProgressDialog progressDialog;
    TextView accountname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        accountname=findViewById(R.id.accountname);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
//        Intent intent=getIntent();
//        accountname.setText("hello");
        GetDataSong();

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Bạn có chắc muốn đăng xuất")
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

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode==KeyEvent.KEYCODE_BACK) {
//            AlertDialog.Builder b = new AlertDialog.Builder(this);
//            b.setTitle("Exit");
//            b.setMessage("Bạn có muốn đăng xuất ?");
//            b.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                }
//            });
//            b.setNegativeButton(android.R.string.no, null);
//        }
//        return super.onKeyDown(keyCode, event);
//
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void GetDataSong() {
        progressDialog=ProgressDialog.show(ManageActivity.this,"Đang tải danh sách...", "Loading...!",false, false);
        DataService dataService=APIService.getService();
        Call<List<BaiHat>> callback=dataService.GetSearchBaiHat("");
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                arrayList=(ArrayList<BaiHat>) response.body();
                if(arrayList.size()>0){
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
}