package com.example.asus.medicinegod;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicalSearch extends AppCompatActivity {

    PreparedStatement stmt = null;
    private String name ,speci ,supp;
    private int id ,num;
    private double price;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_searchview);

        Button button = (Button) findViewById(R.id.button_medical_search);
        final EditText mediname = (EditText) findViewById(R.id.medicalname);
        final EditText facname = (EditText) findViewById(R.id.factorname);

        button.setOnClickListener(new View.OnClickListener() {
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
                        else {
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
                                        LinearLayout mainLinerLayout = (LinearLayout) MedicalSearch.this.findViewById(R.id.medical_searchview);
                                        TextView textview=new TextView(MedicalSearch.this);
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
