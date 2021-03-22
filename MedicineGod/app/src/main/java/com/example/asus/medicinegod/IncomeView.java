package com.example.asus.medicinegod;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IncomeView extends AppCompatActivity {

    PreparedStatement stmt = null;
    private int id, num;
    private double price, totel;
    private String name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incomeview);

        new Thread(new Runnable() {
            public void run() {
                Connection conn = null;
                conn = (Connection) DBDao.getConn();
                String sql = "select * from income ";
                try {
                    stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        id = rs.getInt(1);
                        name = rs.getString(2);
                        price = rs.getDouble(3);
                        num = rs.getInt(4);
                        totel = rs.getDouble(5);
                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        mainHandler.post(new Runnable() {
                            public void run() {
                                LinearLayout mainLinerLayout = (LinearLayout) IncomeView.this.findViewById(R.id.income_view);
                                TextView textview=new TextView(IncomeView.this);
                                textview.setText(id+" "+name+" "+price+" "+num+" "+totel);
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
