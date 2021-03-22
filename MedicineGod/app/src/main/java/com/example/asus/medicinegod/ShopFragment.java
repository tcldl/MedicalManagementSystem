package com.example.asus.medicinegod;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ShopFragment extends Fragment {


    public ShopFragment() {
        // Required empty public constructor
    }

    //单例模式
    public static ShopFragment newInstance(){
        ShopFragment shopFragment=new ShopFragment();
        return shopFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.factory_view, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button button1 = (Button) getActivity().findViewById(R.id.factory_searh);
        Button button2 = (Button) getActivity().findViewById(R.id.factory_add);
        Button button3 = (Button) getActivity().findViewById(R.id.factory_delete);
        Button button4 = (Button) getActivity().findViewById(R.id.factory_change);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , FactorySearch.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , FactoryAdd.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , FactoryDelete.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , FactoryChange.class);
                startActivity(intent);
            }
        });

    }

}