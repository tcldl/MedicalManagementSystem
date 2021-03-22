package com.example.asus.medicinegod;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FindView extends AppCompatActivity {

    PreparedStatement stmt=null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_view);

        Button button1 = (Button) findViewById(R.id.button_find);
        Button button2 = (Button) findViewById(R.id.button_findview_back);
        final EditText tel = (EditText) findViewById(R.id.find_tel);
        final EditText password = (EditText) findViewById(R.id.find_password);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        Connection conn = null;
                        conn = (Connection) DBDao.getConn();
                        String sql = "select * from user where usertel = '" + tel.getText().toString() + "' ";
                        try {
                            stmt = conn.prepareStatement(sql);
                            ResultSet rs = stmt.executeQuery();
                            if (rs.next()) {
                                String sql1 = "update user set userpassword=? where usertel=? ";
                                PreparedStatement pst;
                                pst = (PreparedStatement) conn.prepareStatement(sql1);
                                pst.setString(1, password.getText().toString());
                                pst.setString(2, tel.getText().toString());
                                pst.executeUpdate();
                                pst.close();
                                rs.close();
                                conn.close();
                                Looper.prepare();
                                Toast.makeText(FindView.this, "修改成功", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(FindView.this, MainActivity.class);
                                startActivity(intent);
                                Looper.loop();
                            }
                            else {
                                Looper.prepare();
                                Toast.makeText(FindView.this, "手机号不存在", Toast.LENGTH_LONG).show();
                                Looper.loop();
                                rs.close();
                                conn.close();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindView.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
