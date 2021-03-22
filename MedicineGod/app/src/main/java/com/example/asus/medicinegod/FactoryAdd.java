package com.example.asus.medicinegod;

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

public class FactoryAdd extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.factory_addview);

        Button button = (Button) findViewById(R.id.fac_confirm_add);
        final EditText name = (EditText) findViewById(R.id.fac_addname);
        final EditText addr = (EditText) findViewById(R.id.fac_addaddress);
        final EditText person = (EditText) findViewById(R.id.fac_addper);
        final EditText tel = (EditText) findViewById(R.id.fac_addtel);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.length() != 0 && addr.length() != 0 && person.length() != 0 && tel.length() != 0 ) {
                    new Thread(new Runnable() {
                        public void run() {
                            Connection conn = null;
                            int u = 0;
                            conn = (Connection) DBDao.getConn();
                            String sql = "insert into supply(supply_id,supply_companny,supply_address,supply_principal,supply_tel) values(?,?,?,?,?)";
                            PreparedStatement pst;
                            try {
                                pst = (PreparedStatement) conn.prepareStatement(sql);
                                pst.setInt(1, 0);
                                pst.setString(2, name.getText().toString());
                                pst.setString(3, addr.getText().toString());
                                pst.setString(4, person.getText().toString());
                                pst.setString(5, tel.getText().toString());
                                u = pst.executeUpdate();
                                pst.close();
                                conn.close();
                                Looper.prepare();
                                Toast.makeText(FactoryAdd.this,"增加成功", Toast.LENGTH_LONG).show();
                                Looper.loop();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                else{
                    Toast.makeText(FactoryAdd.this,"所有输入不能为空", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
