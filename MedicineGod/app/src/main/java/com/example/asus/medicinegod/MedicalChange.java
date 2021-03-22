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

public class MedicalChange extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_changeview);

        Button button = (Button) findViewById(R.id.medical_confirm_change);
        final EditText id = (EditText) findViewById(R.id.medical_changeid);
        final EditText prove = (EditText) findViewById(R.id.medical_changepro);
        final EditText specif = (EditText) findViewById(R.id.medical_changespecif);
        final EditText supp = (EditText) findViewById(R.id.medical_changesupp);
        final EditText pri = (EditText) findViewById(R.id.medical_changepri);
        final EditText num = (EditText) findViewById(R.id.medical_changenum);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.length() != 0 && prove.length() != 0 && specif.length() != 0 && supp.length() != 0 && pri.length() != 0 && num.length() != 0) {
                    new Thread(new Runnable() {
                        public void run() {
                            Connection conn = null;
                            conn = (Connection) DBDao.getConn();
                            String sql = "update medical set approval_number=?,specification=?,supplier=?,price=?,storage=? where medical_id=? ";
                            PreparedStatement pst;
                            try {
                                pst = (PreparedStatement) conn.prepareStatement(sql);
                                pst.setString(1, prove.getText().toString());
                                pst.setString(2, specif.getText().toString());
                                pst.setString(3, supp.getText().toString());
                                pst.setDouble(4, Double.parseDouble(pri.getText().toString()));
                                pst.setInt(5, Integer.parseInt(num.getText().toString()));
                                pst.setInt(6, Integer.parseInt(id.getText().toString()));
                                pst.executeUpdate();
                                pst.close();
                                conn.close();
                                Looper.prepare();
                                Toast.makeText(MedicalChange.this, "修改成功", Toast.LENGTH_LONG).show();
                                Looper.loop();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    Toast.makeText(MedicalChange.this, "所有输入不能为空", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
