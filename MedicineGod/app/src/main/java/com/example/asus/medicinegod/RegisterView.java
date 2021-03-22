package com.example.asus.medicinegod;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterView extends AppCompatActivity {

    PreparedStatement stmt=null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        RadioGroup radio = findViewById(R.id.radiobutton_register);
        final RadioButton radioButton1 = findViewById(R.id.radiobutton_males);
        final RadioButton radioButton2 = findViewById(R.id.radiobutton_females);
        final Button button4 = (Button) findViewById(R.id.button_registerview_register);
        Button button5 = (Button) findViewById(R.id.button_registerview_back);
        final EditText edit_username = (EditText) findViewById(R.id.register_name);
        final EditText edit_password = (EditText) findViewById(R.id.register_password);
        final EditText edit_phone = (EditText) findViewById(R.id.register_phone);
        final EditText edit_addr = (EditText) findViewById(R.id.register_addr);

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButton1.isChecked()) {
                    button4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new Thread(new Runnable() {
                                public void run() {
                                    Connection conn = null;
                                    conn = (Connection) DBDao.getConn();
                                    String sql = "select * from user where usertel = '" + edit_phone.getText().toString() + "' ";
                                    try {
                                        stmt = conn.prepareStatement(sql);
                                        ResultSet rs = stmt.executeQuery();
                                        if (rs.next()) {
                                            Looper.prepare();
                                            Toast.makeText(RegisterView.this, "该手机号已存在", Toast.LENGTH_LONG).show();
                                            Looper.loop();
                                            rs.close();
                                            conn.close();
                                        } else {
                                            String sql1 = "insert into user(userid,username,userpassword,usersex,usertel,usergrade,useraddress) values(?,?,?,?,?,?,?)";
                                            PreparedStatement pst;
                                            pst = (PreparedStatement) conn.prepareStatement(sql1);
                                            pst.setInt(1, 0);
                                            pst.setString(2, edit_username.getText().toString());
                                            pst.setString(3, edit_password.getText().toString());
                                            pst.setString(4, "男");
                                            pst.setString(5, edit_phone.getText().toString());
                                            pst.setInt(6, 0);
                                            pst.setString(7, edit_addr.getText().toString());
                                            pst.executeUpdate();
                                            pst.close();
                                            rs.close();
                                            conn.close();
                                            Looper.prepare();
                                            Toast.makeText(RegisterView.this, "注册成功", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(RegisterView.this, MainActivity.class);
                                            startActivity(intent);
                                            Looper.loop();
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    });
                }



                if (radioButton2.isChecked()) {
                    button4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new Thread(new Runnable() {
                                public void run() {
                                    Connection conn = null;
                                    conn = (Connection) DBDao.getConn();
                                    String sql = "select * from user where usertel = '" + edit_phone.getText().toString() + "' ";
                                    try {
                                        stmt = conn.prepareStatement(sql);
                                        ResultSet rs = stmt.executeQuery();
                                        if (rs.next()) {
                                            Looper.prepare();
                                            Toast.makeText(RegisterView.this, "该手机号已存在", Toast.LENGTH_LONG).show();
                                            Looper.loop();
                                            rs.close();
                                            conn.close();
                                        } else {
                                            String sql1 = "insert into user(userid,username,userpassword,usersex,usertel,usergrade,useraddress) values(?,?,?,?,?,?,?)";
                                            PreparedStatement pst;
                                            pst = (PreparedStatement) conn.prepareStatement(sql1);
                                            pst.setInt(1, 0);
                                            pst.setString(2, edit_username.getText().toString());
                                            pst.setString(3, edit_password.getText().toString());
                                            pst.setString(4, "女");
                                            pst.setString(5, edit_phone.getText().toString());
                                            pst.setInt(6, 0);
                                            pst.setString(7, edit_addr.getText().toString());
                                            pst.executeUpdate();
                                            pst.close();
                                            rs.close();
                                            conn.close();
                                            Looper.prepare();
                                            Toast.makeText(RegisterView.this, "注册成功", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(RegisterView.this, MainActivity.class);
                                            startActivity(intent);
                                            Looper.loop();
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    });
                }
            }

        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterView.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
