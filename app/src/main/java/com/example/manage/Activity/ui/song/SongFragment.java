package com.example.manage.Activity.ui.song;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.manage.Adapter.ViewPagerAdapter;
import com.example.manage.Model.BaiHat;
import com.example.manage.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class SongFragment extends Fragment {


    TabLayout tabLayout;
    ViewPager viewPager;

    ArrayList<BaiHat> arrayList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_song,container,false);


        viewPager = root.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = root.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new UpdateSongFragment(), "Chỉnh sửa");
        adapter.addFragment(new AddSongFragment(), "Thêm");

        viewPager.setAdapter(adapter);
    }


}