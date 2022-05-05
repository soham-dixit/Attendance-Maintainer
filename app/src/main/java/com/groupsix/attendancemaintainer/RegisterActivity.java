package com.groupsix.attendancemaintainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    DataBaseHelper myDb;
    private EditText mregisterusername, mregisterpass;
    private RelativeLayout mregister;
    private TextView mgotologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb = new DataBaseHelper(this);
        mregisterusername=findViewById(R.id.registerusername);
        mregisterpass=findViewById(R.id.registerpass);
        mregister=findViewById(R.id.register);
        mgotologin=findViewById(R.id.gotologin);

        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mregisterusername.getText().toString().trim();
                String password = mregisterpass.getText().toString().trim();
//                Long insert = myDb.insertCredentials(username, password);

                if (username.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "All fields are required",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Long insert = myDb.insertCredentials(username, password);
                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(i);
//                    Toast.makeText(getApplicationContext(), "Hi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }
}