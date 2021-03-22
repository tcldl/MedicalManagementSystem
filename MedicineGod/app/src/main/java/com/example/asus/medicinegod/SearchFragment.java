package com.example.asus.medicinegod;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SearchFragment extends Fragment {

    PreparedStatement stmt = null;
    private String username ,usertel ,useraddress,mediname;
    private int id ,mediid,medinum;
    private double mediprice;

    public SearchFragment() {
        // Required empty public constructor
    }

    //单例模式
    public static SearchFragment newInstance(){
        SearchFragment searchFragment=new SearchFragment();
        return searchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.orderview, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new Thread(new Runnable() {
            public void run() {
                Connection conn = null;
                conn = (Connection) DBDao.getConn();
                String sql = "select * from userorder ";
                try {
                    stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        id = rs.getInt(1);
                        username = rs.getString(2);
                        usertel = rs.getString(3);
                        useraddress = rs.getString(4);
                        mediid = rs.getInt(5);
                        mediname = rs.getString(6);
                        mediprice = rs.getDouble(7);
                        medinum = rs.getInt(8);
                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        mainHandler.post(new Runnable() {
                            public void run() {
                                LinearLayout mainLinerLayout = (LinearLayout) getActivity().findViewById(R.id.order_view);
                                TextView textview=new TextView(getActivity());
                                textview.setText(id+" "+username+" "+usertel+" "+useraddress+" "+mediid+" "+mediname+" "+mediprice+" "+medinum);
                                mainLinerLayout.addView(textview);
                                ;
                            }
                        });
                    }
                    rs.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}