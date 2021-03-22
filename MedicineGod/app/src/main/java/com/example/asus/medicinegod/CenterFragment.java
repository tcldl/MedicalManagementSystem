package com.example.asus.medicinegod;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CenterFragment extends Fragment {

    PreparedStatement stmt = null;
    private int mediid, medinum;
    private String mediname;
    private double medipri;

    public CenterFragment() {
        // Required empty public constructor
    }

    //单例模式
    public static CenterFragment newInstance() {
        CenterFragment centerFragment = new CenterFragment();
        return centerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.center_view, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button button1 = (Button) getActivity().findViewById(R.id.button_sendconfirm);
        Button button2 = (Button) getActivity().findViewById(R.id.button_income);
        final EditText id = (EditText) getActivity().findViewById(R.id.send_id);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.length() != 0){
                    new Thread(new Runnable() {
                        public void run() {
                            Connection conn = null;
                            conn = (Connection) DBDao.getConn();
                            String sql = "select medical_id,medical_name,medical_price,medical_num from userorder where order_id = '"+id.getText().toString()+"' ";
                            try {
                                stmt = conn.prepareStatement(sql);
                                ResultSet rs = stmt.executeQuery();
                                if (rs.next()) {
                                    mediid = rs.getInt(1);
                                    mediname = rs.getString(2);
                                    medipri = rs.getDouble(3);
                                    medinum = rs.getInt(4);

                                    String sql1 = " update medical set storage=storage-? where medical_id=? ";
                                    String sql2 = " delete from userorder where order_id=? ";
                                    String sql3 = " insert into income(income_id,medical_name,medical_price,medical_num,income_total) values(?,?,?,?,?) ";
                                    PreparedStatement pst;
                                    try {
                                        pst = (PreparedStatement) conn.prepareStatement(sql1);
                                        pst.setInt(1, medinum);
                                        pst.setInt(2, mediid);
                                        pst.executeUpdate();

                                        pst = (PreparedStatement) conn.prepareStatement(sql2);
                                        pst.setInt(1, Integer.parseInt(id.getText().toString()));
                                        pst.executeUpdate();

                                        pst = (PreparedStatement) conn.prepareStatement(sql3);
                                        pst.setInt(1, 0);
                                        pst.setString(2, mediname);
                                        pst.setDouble(3, medipri);
                                        pst.setInt(4, medinum);
                                        pst.setDouble(5, medinum*medipri);
                                        pst.executeUpdate();

                                        pst.close();
                                        Looper.prepare();
                                        Toast.makeText(getActivity(), "发货成功", Toast.LENGTH_LONG).show();
                                        Looper.loop();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                                rs.close();
                                conn.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                else {
                    Toast.makeText(getActivity(), "输入不能为空", Toast.LENGTH_LONG).show();
                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , IncomeView.class);
                startActivity(intent);
            }

        });

    }

}