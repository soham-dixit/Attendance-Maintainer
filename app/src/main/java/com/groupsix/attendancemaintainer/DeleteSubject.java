package com.groupsix.attendancemaintainer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteSubject extends AppCompatActivity
{
    EditText e1;
    DataBaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_subject);
        ActionBar actionbar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF6900"));
        actionbar.setBackgroundDrawable(colorDrawable);
        e1=findViewById(R.id.enter_id);
        myDb=new DataBaseHelper(this);
    }
    public void DELETESUBJECT(View view)
    {
        String s=e1.getText().toString();
        if(s.length()==0)
        {
            Toast.makeText(DeleteSubject.this, "Empty Field Encountered", Toast.LENGTH_SHORT).show();
        }
        else
        {
            int x = myDb.deleteData(s);
            if (x == -1)
                Toast.makeText(DeleteSubject.this, "Error Occured", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(DeleteSubject.this, "Successful", Toast.LENGTH_SHORT).show();
            e1.setText("");
        }
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(DeleteSubject.this,MainActivity.class);
        startActivity(i);

    }
}
