package com.example.manage.Adapter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

     List<Fragment> list = new ArrayList<>();
     List<String> mFragmentTitleList = new ArrayList<>();



    public void setList(List<Fragment> list) {
        this.list = list;
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
    public void addFragment(Fragment fragment, String title) {
        list.add(fragment);
        mFragmentTitleList.add(title);
    }
}