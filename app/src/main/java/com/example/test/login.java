package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    Button b1,b2;
    EditText ed1,ed2;

    reg db1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loginactivity);

        db1 = new reg(this);

        b1 = findViewById(R.id.login);
        b2 = findViewById(R.id.register);
        ed1 = findViewById(R.id.user);
        ed2 = findViewById(R.id.pass);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ed1.getText().toString();
                String password = ed2.getText().toString();

                Boolean check = db1.CheckLogin(username, password);

                if(check != null && check == true){
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.activity_main);
                    Intent intent = new Intent(login.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
                }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    setContentView(R.layout.activity_register);
                    Intent intent = new Intent(login.this, activity_register.class);
                    startActivity(intent);
            }
        });
    }
}
