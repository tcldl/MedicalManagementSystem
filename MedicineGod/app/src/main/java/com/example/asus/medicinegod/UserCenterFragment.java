package com.example.asus.medicinegod;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserCenterFragment extends Fragment {

    PreparedStatement stmt = null;
    private String username, usertel, useraddress;
    private int usergrade;

    public UserCenterFragment() {
        // Required empty public constructor
    }

    //单例模式
    public static UserCenterFragment newInstance(){
        UserCenterFragment centerFragment=new UserCenterFragment();
        return centerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.usercenterview, container, false);

    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final TextView name = (TextView) getActivity().findViewById(R.id.user_centername);
        final TextView tel = (TextView) getActivity().findViewById(R.id.user_centertel);
        final TextView grade = (TextView) getActivity().findViewById(R.id.user_centergrade);
        final TextView address = (TextView) getActivity().findViewById(R.id.user_centeraddress);
        Button button1 = (Button) getActivity().findViewById(R.id.button_user_centerback);
        Button button2 = (Button) getActivity().findViewById(R.id.button_user_centerchang);
        Button button3 = (Button) getActivity().findViewById(R.id.button_user_centeradd);

        new Thread(new Runnable() {
            public void run() {
                Connection conn = null;
                conn = (Connection) DBDao.getConn();
                String sql = "select username,usertel,usergrade,useraddress from user where usertel = '15223159188' ";
                try {
                    stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        username = rs.getString(1);
                        usertel = rs.getString(2);
                        usergrade = rs.getInt(3);
                        useraddress = rs.getString(4);
                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        mainHandler.post(new Runnable() {
                            public void run() {
                                name.setText(username);
                                tel.setText(usertel);
                                grade.setText(usergrade+"");
                                address.setText(useraddress);
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

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , MainActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , UserChangeView.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , UserAddview.class);
                startActivity(intent);
            }
        });

    }

}
