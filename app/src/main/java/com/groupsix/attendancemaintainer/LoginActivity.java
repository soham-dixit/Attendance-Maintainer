package com.groupsix.attendancemaintainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    DataBaseHelper myDb;
    private EditText mloginusername, mloginpassword;
    private RelativeLayout mlogin, mgotoregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDb = new DataBaseHelper(this);
        mloginusername=findViewById(R.id.loginusername);
        mloginpassword=findViewById(R.id.loginpassword);
        mlogin=findViewById(R.id.login);
        mgotoregister=findViewById(R.id.gotoregister);

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mloginusername.getText().toString().trim();
                String password = mloginpassword.getText().toString().trim();
                if (username.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "All fields are required",Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkUsername = myDb.checkUsername(username);
                    if(checkUsername){
                        Boolean matchUser = myDb.matchuserpass(username, password);
                        if(matchUser){
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
//                            Toast.makeText(LoginActivity.this, "matchUser"+matchUser, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "User does not exist!", Toast.LENGTH_SHORT).show();
                        mloginusername.setText("");
                        mloginpassword.setText("");
                    }
                }
            }
        });

        mgotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}