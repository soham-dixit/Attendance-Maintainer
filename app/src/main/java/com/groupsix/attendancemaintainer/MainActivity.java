package com.groupsix.attendancemaintainer;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity<subject> extends AppCompatActivity {
    DataBaseHelper myDb;
    ListView listView;
    SubjectAdapter subjects;
    ArrayList<subject> names;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDb = new DataBaseHelper(this);
        names = new ArrayList<>();
        subjects = new SubjectAdapter(this, names);
        listView = findViewById(R.id.list_view);
        listView.setAdapter((ListAdapter) subjects);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor res1 = myDb.getAllData();
                for (int z=0; z<=1; z++){
                    res1.moveToNext();
                }

            }
        });

    }
}