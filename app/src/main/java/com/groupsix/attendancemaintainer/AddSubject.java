package com.groupsix.attendancemaintainer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class AddSubject extends AppCompatActivity {
    Button b;
    EditText e1,e2;
    DataBaseHelper mmyDb;
    SubjectAdapter subjects;
    ArrayList<subject> names;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_subject);
        ActionBar actionbar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF6900"));
        actionbar.setBackgroundDrawable(colorDrawable);
        b=findViewById(R.id.enter_add_subject);
        e1=findViewById(R.id.enter_subject_name);
        e2=findViewById(R.id.enter_minimum_percent);
        mmyDb=new DataBaseHelper(this);
        names=new ArrayList<>();
        subjects=new SubjectAdapter(this,names);

    }
    public void ADDSUBJECT(View view)
    {
        String subject_name=e1.getText().toString();
        String subject_minimum=e2.getText().toString();
        if((subject_minimum.length()==0)||(subject_name.length()==0))
        {
            Toast.makeText(AddSubject.this, "Empty Field Encountered", Toast.LENGTH_SHORT).show();
        }
        else
        {
            long insertdata = mmyDb.insertData(subject_name, subject_minimum);
            if (insertdata == -1)
                Toast.makeText(AddSubject.this, "Error Occured", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(AddSubject.this, "Successful", Toast.LENGTH_SHORT).show();
            e1.setText("");
            e2.setText("");
            subjects.notifyDataSetChanged();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AddSubject.this, MainActivity.class);
        startActivity(i);
    }
}
