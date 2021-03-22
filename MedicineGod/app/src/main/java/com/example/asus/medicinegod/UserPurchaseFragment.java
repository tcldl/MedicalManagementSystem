package com.example.asus.medicinegod;

import android.app.Fragment;
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


public class UserPurchaseFragment extends Fragment {

    PreparedStatement stmt = null , stmt1 = null;
    private String mediname , name, tel, address;
    private double medipri, total ;
    private int grade, medinum;

    public UserPurchaseFragment() {
        // Required empty public constructor
    }

    //单例模式
    public static UserPurchaseFragment newInstance() {
        UserPurchaseFragment purchaseFragment = new UserPurchaseFragment();
        return purchaseFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.userpurchaseview, container, false);

    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final EditText id = (EditText) getActivity().findViewById(R.id.user_purchasemediid);
        final EditText num = (EditText) getActivity().findViewById(R.id.user_purchasenum);
        Button button1 = (Button) getActivity().findViewById(R.id.button_user_purchasemedical);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.length() != 0 && num.length() != 0) {
                    new Thread(new Runnable() {
                        public void run() {
                            Connection conn = null;
                            conn = (Connection) DBDao.getConn();
                            String sql = "select medical_name,price,storage from medical where medical_id = '" + id.getText().toString() + "' ";
                            try {
                                stmt = conn.prepareStatement(sql);
                                ResultSet rs = stmt.executeQuery();
                                if (rs.next()) {
                                    mediname = rs.getString(1);
                                    medipri = rs.getDouble(2);
                                    medinum = rs.getInt(3);
                                    total = medipri * Integer.parseInt(num.getText().toString());
                                    String sql1 = "update user set usergrade=usergrade-? where usertel='15223159188' ";
                                    String sql2 = "select username,usertel,useraddress,usergrade from user where usertel = '15223159188' ";
                                    String sql3 = "insert into userorder(order_id,order_username,order_usertel,order_useraddress,medical_id,medical_name,medical_price,medical_num) values(?,?,?,?,?,?,?,?) ";
                                    PreparedStatement pst;
                                    try {
                                        stmt1 = conn.prepareStatement(sql2);
                                        ResultSet rs1 = stmt1.executeQuery();
                                        if (rs1.next()) {
                                            name = rs1.getString(1);
                                            tel = rs1.getString(2);
                                            address = rs1.getString(3);
                                            grade = rs1.getInt(4);
                                        }

                                        if (grade <= total){
                                            Looper.prepare();
                                            Toast.makeText(getActivity(), "余额不足", Toast.LENGTH_LONG).show();
                                            Looper.loop();
                                        }
                                        else if (medinum < Integer.parseInt(num.getText().toString())) {
                                            Looper.prepare();
                                            Toast.makeText(getActivity(), "药品数量不足", Toast.LENGTH_LONG).show();
                                            Looper.loop();
                                        }
                                        else {
                                            pst = (PreparedStatement) conn.prepareStatement(sql1);
                                            pst.setDouble(1, total);
                                            pst.executeUpdate();

                                            pst = (PreparedStatement) conn.prepareStatement(sql3);
                                            pst.setInt(1, 0);
                                            pst.setString(2, name);
                                            pst.setString(3, tel);
                                            pst.setString(4, address);
                                            pst.setInt(5, Integer.parseInt(id.getText().toString()));
                                            pst.setString(6, mediname);
                                            pst.setDouble(7, medipri);
                                            pst.setInt(8, Integer.parseInt(num.getText().toString()));
                                            pst.executeUpdate();

                                            rs1.close();
                                            pst.close();
                                            Looper.prepare();
                                            Toast.makeText(getActivity(), "购买成功", Toast.LENGTH_LONG).show();
                                            Looper.loop();
                                        }

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
                } else {
                    Toast.makeText(getActivity(), "输入不能为空", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

}
