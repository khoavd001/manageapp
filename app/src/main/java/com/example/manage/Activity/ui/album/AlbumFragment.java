package com.example.manage.Activity.ui.album;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.manage.Activity.ui.song.AddSongFragment;
import com.example.manage.Activity.ui.song.UpdateSongFragment;
import com.example.manage.Adapter.ViewPagerAdapter;
import com.example.manage.R;
import com.google.android.material.tabs.TabLayout;


public class AlbumFragment extends Fragment {


    TabLayout tabLayout;
    ViewPager viewPager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_album,container,false);
        viewPager = root.findViewById(R.id.viewpager_album);
        setupViewPager(viewPager);
        tabLayout = root.findViewById(R.id.tablayout_album);
        tabLayout.setupWithViewPager(viewPager);
        return root;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new UpdateAlbum(), "Chỉnh sửa");
        adapter.addFragment(new AddAlbum(), "Thêm");

        viewPager.setAdapter(adapter);
    }
}