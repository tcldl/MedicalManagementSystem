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

public class MedicalDelete extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_delview);

        Button button = (Button) findViewById(R.id.medical_confirm_del);
        final EditText del = (EditText) findViewById(R.id.medical_del);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (del.length() != 0 ) {
                    new Thread(new Runnable() {
                        public void run() {
                            Connection conn = null;
                            conn = (Connection) DBDao.getConn();
                            String sql = "delete from medical where medical_id=?";
                            PreparedStatement pst;
                            try {
                                pst = (PreparedStatement) conn.prepareStatement(sql);
                                pst.setInt(1, Integer.parseInt(del.getText().toString()));
                                pst.executeUpdate();
                                pst.close();
                                conn.close();
                                Looper.prepare();
                                Toast.makeText(MedicalDelete.this,"删除成功", Toast.LENGTH_LONG).show();
                                Looper.loop();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                else{
                    Toast.makeText(MedicalDelete.this,"输入不能为空", Toast.LENGTH_LONG).show();
                }
            }

        });

    }
}
