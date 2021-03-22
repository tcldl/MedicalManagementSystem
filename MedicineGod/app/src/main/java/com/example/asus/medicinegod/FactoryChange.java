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

public class FactoryChange extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.factory_changeview);

        Button button = (Button) findViewById(R.id.fac_confirm_change);
        final EditText id = (EditText) findViewById(R.id.fac_changeid);
        final EditText addr = (EditText) findViewById(R.id.fac_changeaddr);
        final EditText person = (EditText) findViewById(R.id.facc_changeper);
        final EditText tel = (EditText) findViewById(R.id.facc_changetel);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.length() != 0 && addr.length() != 0 && person.length() != 0 && tel.length() != 0 ) {
                    new Thread(new Runnable() {
                        public void run() {
                            Connection conn = null;
                            conn = (Connection) DBDao.getConn();
                            String sql = "update supply set supply_address=?,supply_principal=?,supply_tel=? where supply_id=? ";
                            PreparedStatement pst;
                            try {
                                pst = (PreparedStatement) conn.prepareStatement(sql);
                                pst.setString(1, addr.getText().toString());
                                pst.setString(2, person.getText().toString());
                                pst.setString(3, tel.getText().toString());
                                pst.setInt(4, Integer.parseInt(id.getText().toString()));
                                pst.executeUpdate();
                                pst.close();
                                conn.close();
                                Looper.prepare();
                                Toast.makeText(FactoryChange.this, "修改成功", Toast.LENGTH_LONG).show();
                                Looper.loop();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    Toast.makeText(FactoryChange.this, "所有输入不能为空", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}
