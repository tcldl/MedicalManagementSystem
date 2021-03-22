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

public class UserChangeView extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userchangeview);

        Button button1 = (Button) findViewById(R.id.button_user_changeconfirm);
        Button button2 = (Button) findViewById(R.id.button_user_changeback);
        final EditText name = (EditText) findViewById(R.id.change_name);
        final EditText tel = (EditText) findViewById(R.id.change_tel);
        final EditText password = (EditText) findViewById(R.id.change_password);
        final EditText address = (EditText) findViewById(R.id.change_address);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (name.length() != 0 && tel.length() != 0 && password.length() != 0 && address.length() != 0 ) {
                    new Thread(new Runnable() {
                        public void run() {
                            Connection conn = null;
                            conn = (Connection) DBDao.getConn();
                            String sql = "update user set username=?,usertel=?,userpassword=?,useraddress=? where usertel='15223159188' ";
                            PreparedStatement pst;
                            try {
                                pst = (PreparedStatement) conn.prepareStatement(sql);
                                pst.setString(1, name.getText().toString());
                                pst.setString(2, tel.getText().toString());
                                pst.setString(3, password.getText().toString());
                                pst.setString(4, address.getText().toString());
                                pst.executeUpdate();
                                pst.close();
                                conn.close();
                                Looper.prepare();
                                Toast.makeText(UserChangeView.this, "修改成功", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(UserChangeView.this , ManagerView.class);
                                startActivity(intent);
                                Looper.loop();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    Toast.makeText(UserChangeView.this, "所有输入不能为空", Toast.LENGTH_LONG).show();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserChangeView.this , ManagerView.class);
                startActivity(intent);
            }

        });

    }

}
