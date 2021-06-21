package com.example.manage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manage.Activity.ManageActivity;
import com.example.manage.Model.Account;
import com.example.manage.Service.APIService;
import com.example.manage.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button buttonlogin;
    EditText usertext,passtext;
//    public static ArrayList<BaiHat> arrayList;
    String username,password;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonlogin=findViewById(R.id.login);
        usertext=findViewById(R.id.usernametext);
        passtext=findViewById(R.id.passwordtext);
        passtext.setTransformationMethod(new PasswordTransformationMethod());
        buttonlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                username=usertext.getText().toString();
                password=passtext.getText().toString();
                if(username.length()>0 && password.length()>0){
                    progressDialog=ProgressDialog.show(MainActivity.this,"Đang đăng nhấp...", "Loading...!",false, false);
                    DataService dataService= APIService.getService();
                    Call<List<Account>> callback=dataService.Login(username,password);
                    callback.enqueue(new Callback<List<Account>>() {
                        @Override
                        public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {

                            ArrayList<Account> mangaccount=(ArrayList<Account>) response.body();
                            if(mangaccount.size()>0){
                                Intent intent=new Intent(MainActivity.this, ManageActivity.class);
//                                intent.putExtra("accountname", username);
                                startActivity(intent);
                                progressDialog.dismiss();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Không có tài khoản", Toast.LENGTH_SHORT);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Account>> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT);
                        }
                    });
                }
            }
        });
//        GetDataSong();

    }


}