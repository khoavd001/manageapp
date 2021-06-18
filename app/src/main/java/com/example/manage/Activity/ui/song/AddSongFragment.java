package com.example.manage.Activity.ui.song;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.manage.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSongFragment extends Fragment {

    EditText tenbaihatadd, linkbaihatadd;
    ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_addsong, container, false);
        tenbaihatadd=view.findViewById(R.id.tenbaihatadd);
        linkbaihatadd=view.findViewById(R.id.linkbaihatadd);
        imageView=view.findViewById(R.id.hinhbaihatadd);
        return view;

    }

}