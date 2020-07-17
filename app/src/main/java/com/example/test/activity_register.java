package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_register extends AppCompatActivity {
    reg db;

    EditText fulname, user, pass, cpass;
    Button btnreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new reg(this);
        fulname = findViewById(R.id.fullname);
        user = findViewById(R.id.user1);
        pass = findViewById(R.id.pas1);
        cpass = findViewById(R.id.pas2);

        btnreg = findViewById(R.id.btnregister);

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = fulname.getText().toString();
                String username = user.getText().toString();
                String password = pass.getText().toString();
                String confirm_password = cpass.getText().toString();

                if(fullname.equals("") || username.equals("") || password.equals("") || confirm_password.equals("")){
                    Toast.makeText(getApplicationContext(), "Fields Required", Toast.LENGTH_SHORT).show();
                }else{
                    if(password.equals(confirm_password)){
                        Boolean checkusername = db.CheckUsername(username);
                        if(checkusername == true){
                            Boolean insert = db.Insert(fullname, username, password);
                            if(insert == true){
                                Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                                fulname.setText("");
                                user.setText("");
                                pass.setText("");
                                cpass.setText("");
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Username already taken", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
