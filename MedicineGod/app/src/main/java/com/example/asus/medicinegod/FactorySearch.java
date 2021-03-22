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

public class FactorySearch extends AppCompatActivity {

    PreparedStatement stmt = null;
    private int id;
    private String company, address ,principal ,tel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.factory_searchview);

        Button button = (Button) findViewById(R.id.button_factory_search);
        final EditText facname = (EditText) findViewById(R.id.fac_searchname);
        final EditText person = (EditText) findViewById(R.id.fac_searchperson);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        Connection conn = null;
                        conn = (Connection) DBDao.getConn();
                        String sql;
                        if (facname.length() !=0 && person.length() != 0 ){
                            sql = "select supply_id,supply_companny,supply_address,supply_principal,supply_tel from supply where supply_companny like '%" +facname.getText().toString()+"%' and supply_principal like '%"+person.getText().toString()+"%' ";
                        }
                        else if (facname.length() !=0 && person.length() == 0){
                            sql = "select supply_id,supply_companny,supply_address,supply_principal,supply_tel from supply where supply_companny like '%" +facname.getText().toString()+"%' ";
                        }
                        else {
                            sql = "select supply_id,supply_companny,supply_address,supply_principal,supply_tel from supply where supply_principal like '%"+person.getText().toString()+"%' ";
                        }
                        try {
                            stmt = conn.prepareStatement(sql);
                            ResultSet rs = stmt.executeQuery();
                            while (rs.next()) {
                                id = rs.getInt(1);
                                company = rs.getString(2);
                                address = rs.getString(3);
                                principal = rs.getString(4);
                                tel = rs.getString(5);
                                Handler mainHandler = new Handler(Looper.getMainLooper());
                                mainHandler.post(new Runnable() {
                                    public void run() {
                                        LinearLayout mainLinerLayout = (LinearLayout) FactorySearch.this.findViewById(R.id.factory_searchview);
                                        TextView textview=new TextView(FactorySearch.this);
                                        textview.setText(id+" "+company+" "+address+" "+principal+" "+tel);
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
