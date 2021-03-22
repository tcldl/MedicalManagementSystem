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

public class MedicalAdd extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_addview);

        Button button = (Button) findViewById(R.id.medical_confirm_add);
        final EditText id = (EditText) findViewById(R.id.medical_addid);
        final EditText name = (EditText) findViewById(R.id.medical_addname);
        final EditText prove = (EditText) findViewById(R.id.medical_addprove);
        final EditText specif = (EditText) findViewById(R.id.medical_addspecif);
        final EditText supp = (EditText) findViewById(R.id.medical_addsupp);
        final EditText pri = (EditText) findViewById(R.id.medical_addpri);
        final EditText num = (EditText) findViewById(R.id.medical_addnum);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.length() ==0 && name.length() != 0 && prove.length() != 0 && specif.length() != 0 && supp.length() != 0 && pri.length() != 0 && num.length() != 0) {
                    new Thread(new Runnable() {
                        public void run() {
                            Connection conn = null;
                            int u = 0;
                            conn = (Connection) DBDao.getConn();
                            String sql = "insert into medical(medical_id,medical_name,approval_number,specification,supplier,price,storage) values(?,?,?,?,?,?,?)";
                            PreparedStatement pst;
                            try {
                                pst = (PreparedStatement) conn.prepareStatement(sql);
                                pst.setInt(1, 0);
                                pst.setString(2, name.getText().toString());
                                pst.setString(3, prove.getText().toString());
                                pst.setString(4, specif.getText().toString());
                                pst.setString(5, supp.getText().toString());
                                pst.setDouble(6, Double.parseDouble(pri.getText().toString()));
                                pst.setInt(7, Integer.parseInt(num.getText().toString()));
                                u = pst.executeUpdate();
                                pst.close();
                                conn.close();
                                Looper.prepare();
                                Toast.makeText(MedicalAdd.this,"增加成功", Toast.LENGTH_LONG).show();
                                Looper.loop();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                else if (id.length() !=0 && name.length() == 0 && prove.length() == 0 && specif.length() == 0 && supp.length() == 0 && pri.length() == 0 && num.length() != 0) {
                    new Thread(new Runnable() {
                        public void run() {
                            Connection conn = null;
                            conn = (Connection) DBDao.getConn();
                            String sql = "update medical set storage=storage+? where medical_id=? ";
                            PreparedStatement pst;
                            try {
                                pst = (PreparedStatement) conn.prepareStatement(sql);
                                pst.setInt(1, Integer.parseInt(num.getText().toString()));
                                pst.setInt(2, Integer.parseInt(id.getText().toString()));
                                pst.executeUpdate();
                                pst.close();
                                conn.close();
                                Looper.prepare();
                                Toast.makeText(MedicalAdd.this,"增加成功", Toast.LENGTH_LONG).show();
                                Looper.loop();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                else{
                    Toast.makeText(MedicalAdd.this,"输入不能为空", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
