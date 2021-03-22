package com.example.asus.medicinegod;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class UserHomeFragment extends Fragment {


    public UserHomeFragment() {
        // Required empty public constructor
    }

    //单例模式
    public static UserHomeFragment newInstance(){
        UserHomeFragment homeFragment=new UserHomeFragment();
        return homeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.userhomeview, container, false);

    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
