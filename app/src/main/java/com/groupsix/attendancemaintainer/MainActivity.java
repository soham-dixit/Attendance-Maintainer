package com.groupsix.attendancemaintainer;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
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
        listView.setAdapter(subjects);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor res1 = myDb.getAllData();
                for (int z = 0; z <= i; z++) {
                    res1.moveToNext();
                }
                String mod_id = res1.getString(0);
                String mod_sub_name = res1.getString(1);
                String mod_min_percent = res1.getString(5);
                String mod_present = res1.getString(2);
                String mod_absent = res1.getString(3);
                LayoutDialog layoutDialog = new LayoutDialog(MainActivity.this, mod_id, mod_sub_name, mod_min_percent, mod_present, mod_absent);
                layoutDialog.show(getSupportFragmentManager(), "layout dialog");

            }
        });

        dl = findViewById(R.id.drawer_layout);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav = findViewById(R.id.nav_view);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_addsubject) {
                    Toast.makeText(MainActivity.this, "Clicked on ADD", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, AddSubject.class);
                    startActivity(i);
                }
                if (id == R.id.nav_removesubject) {
                    Toast.makeText(MainActivity.this, "Clicked on DELETE", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, DeleteSubject.class);
                    startActivity(i);
                }
                if (id == R.id.nav_logout) {
                    Toast.makeText(MainActivity.this, "Clicked on Logout", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finishAffinity();
                }
                if (id == R.id.nav_viewdata)
                    ViewData();
                return true;
            }
        });


        Cursor res = myDb.getAllData();
        while (res.moveToNext()) {
            String s0 = res.getString(0);
            String s1 = res.getString(1);
            String s2 = res.getString(5);
            String s3 = res.getString(2);
            String s4 = res.getString(3);
            int a = Integer.parseInt(s3);
            int b = Integer.parseInt(s4);
            int c;
            if (a + b == 0)
                c = 0;
            else
                c = (a * 100) / (a + b);
            String s5 = String.valueOf(c);
            names.add(new subject(s0, s1, s2, s3, s4, s5));
        }

    }

    public void ViewData() {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            //show error message;
            showmessgae("ERROR", "Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("ID: " + res.getString(0) + "\n");
            buffer.append("Name: " + res.getString(1) + "\n");
            buffer.append("Minimum: " + res.getString(5) + "\n\n");
        }
        //show all data
        showmessgae("DATA", buffer.toString());
    }

    public void showmessgae(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("no", null).show();
    }
}
