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

import java.sql.PreparedStatement;

public class MainActivity extends AppCompatActivity {

    private Button btn_login, btn_register, btn_forget;
    PreparedStatement stmt=null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化按钮
        RadioGroup radio = findViewById(R.id.radiobutton_main);
        final RadioButton radioButton1 = findViewById(R.id.radiobutton_user);
        final RadioButton radioButton2 = findViewById(R.id.radiobutton_manager);
        final Button button1 = (Button) findViewById(R.id.button_login);
        Button button2 = (Button) findViewById(R.id.button_register);
        Button button3 = (Button) findViewById(R.id.button_forget);
        final EditText edit_phone = (EditText) findViewById(R.id.phone);
        final EditText edit_password = (EditText) findViewById(R.id.password);

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioButton1.isChecked()) {
                    button1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            new Thread(new Runnable() {
                                public void run() {
                                    Login_centain log =new Login_centain();
                                    final boolean loged = log.userlogin(edit_phone.getText().toString(), edit_password.getText().toString());
                                    if (loged){
                                        Looper.prepare();
                                        Toast.makeText(MainActivity.this,"登陆成功", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this, ManagerView.class);
                                        startActivity(intent);
                                        Looper.loop();
                                    }
                                    else{
                                        Looper.prepare();
                                        Toast.makeText(MainActivity.this,"用户名或密码不对,请重试", Toast.LENGTH_LONG).show();
                                        Looper.loop();
                                    }
                                }

                            }).start();

                        }
                    });

                }

                if (radioButton2.isChecked()) {
                    button1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            new Thread(new Runnable() {
                                public void run() {
                                    Login_centain log =new Login_centain();
                                    final boolean loged = log.managerlogin(edit_phone.getText().toString(), edit_password.getText().toString());
                                    if (loged){
                                        Looper.prepare();
                                        Toast.makeText(MainActivity.this,"登陆成功", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this, UserView.class);
                                        startActivity(intent);
                                        Looper.loop();
                                    }
                                    else{
                                        Looper.prepare();
                                        Toast.makeText(MainActivity.this,"用户名或密码不对,请重试", Toast.LENGTH_LONG).show();
                                        Looper.loop();
                                    }
                                }

                            }).start();

                        }
                    });

                }
            }

        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterView.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FindView.class);
                startActivity(intent);
            }
        });

    }

}
