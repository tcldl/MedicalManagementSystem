package com.example.asus.medicinegod;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserSearchFragment extends Fragment {

    PreparedStatement stmt = null;
    private String name ,speci ,supp;
    private int id ,num;
    private double price;

    public UserSearchFragment() {
        // Required empty public constructor
    }

    //单例模式
    public static UserSearchFragment newInstance(){
        UserSearchFragment searchFragment=new UserSearchFragment();
        return searchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.usersearchview, container, false);

    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button button1 = (Button) getActivity().findViewById(R.id.button_user_searchmedical);
        final EditText mediname = (EditText) getActivity().findViewById(R.id.user_mediname);
        final EditText facname = (EditText) getActivity().findViewById(R.id.user_facname);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    public void run() {
                        Connection conn = null;
                        conn = (Connection) DBDao.getConn();
                        String sql;
                        if (mediname.length() !=0 && facname.length() != 0){
                            sql = "select medical_id,medical_name,specification,supplier,price,storage from medical where (medical_name like '%" +mediname.getText().toString()+"%') and (supplier like '%"+facname.getText().toString()+"%') ";
                        }
                        else if (mediname.length() !=0 && facname.length() == 0){
                            sql = "select medical_id,medical_name,specification,supplier,price,storage from medical where medical_name like '%" +mediname.getText().toString()+"%' ";
                        }
                        else{
                            sql = "select medical_id,medical_name,specification,supplier,price,storage from medical where supplier like '%"+facname.getText().toString()+"%' ";
                        }
                        try {
                            stmt = conn.prepareStatement(sql);
                            ResultSet rs = stmt.executeQuery();
                            while (rs.next()) {
                                id = rs.getInt(1);
                                name = rs.getString(2);
                                speci = rs.getString(3);
                                supp = rs.getString(4);
                                price = rs.getDouble(5);
                                num = rs.getInt(6);
                                Handler mainHandler = new Handler(Looper.getMainLooper());
                                mainHandler.post(new Runnable() {
                                    public void run() {
                                        LinearLayout mainLinerLayout = (LinearLayout) getActivity().findViewById(R.id.user_searchmedical);
                                        TextView textview=new TextView(getActivity());
                                        textview.setText(id+" "+name+" "+speci+" "+supp+" "+price+" "+num);
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
        });

    }

}