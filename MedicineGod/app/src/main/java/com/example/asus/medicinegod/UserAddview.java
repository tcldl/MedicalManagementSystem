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
import java.sql.SQLException;

public class UserAddview extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useraddview);

        Button button1 = (Button) findViewById(R.id.button_user_addconfirm);
        Button button2 = (Button) findViewById(R.id.button_user_addback);
        final EditText grade = (EditText) findViewById(R.id.add_grade);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (grade.length() != 0 ) {
                    new Thread(new Runnable() {
                        public void run() {
                            Connection conn = null;
                            conn = (Connection) DBDao.getConn();
                            String sql = "update user set usergrade=usergrade+? where usertel='15223159188' ";
                            PreparedStatement pst;
                            try {
                                pst = (PreparedStatement) conn.prepareStatement(sql);
                                pst.setInt(1, Integer.parseInt(grade.getText().toString()));
                                pst.executeUpdate();
                                pst.close();
                                conn.close();
                                Looper.prepare();
                                Toast.makeText(UserAddview.this, "充值成功", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(UserAddview.this , ManagerView.class);
                                startActivity(intent);
                                Looper.loop();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    Toast.makeText(UserAddview.this, "输入不能为空", Toast.LENGTH_LONG).show();
                }
            }

        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAddview.this , ManagerView.class);
                startActivity(intent);
            }

        });

    }

}
