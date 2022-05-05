package com.groupsix.attendancemaintainer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

@SuppressLint("ValidFragment")
public class LayoutDialog extends AppCompatDialogFragment
{
    EditText subname,minpercent,present,absent;
    DataBaseHelper myDb;
    Context context;
    String mod_id,mod_sub_name,mod_min_percent,mod_present,mod_absent;
    public LayoutDialog(Context c,String id,String sub_name,String min_percent,String present,String absent)
    {
        context=c;
        mod_id=id;
        mod_sub_name=sub_name;
        mod_min_percent=min_percent;
        mod_present=present;
        mod_absent=absent;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        myDb=new DataBaseHelper(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);

        subname=view.findViewById(R.id.modify_subj_name);
        minpercent=view.findViewById(R.id.modify_min_percent);
        present=view.findViewById(R.id.modify_presents);
        absent=view.findViewById(R.id.modify_absents);

        subname.setText(mod_sub_name);
        minpercent.setText(mod_min_percent);
        present.setText(mod_present);
        absent.setText(mod_absent);

        builder.setView(view)
                .setTitle("MODIFY SUBJECT")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                    }
                })
                .setNeutralButton("DELETE", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        int x = myDb.deleteData(mod_id);
                        Intent in=new Intent(context,MainActivity.class);
                        startActivity(in);
                    }
                })
                .setPositiveButton("MODIFY", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        mod_present=present.getText().toString();
                        mod_absent=absent.getText().toString();
                        mod_sub_name=subname.getText().toString();
                        mod_min_percent=minpercent.getText().toString();
                        if((mod_min_percent.length()==0)||(mod_sub_name.length()==0)||(mod_present.length()==0)||(mod_absent.length()==0))
                        {
                            Toast.makeText(context, "Empty Field Encountered", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            int p = Integer.parseInt(mod_present);
                            int a = Integer.parseInt(mod_absent);
                            int cur;
                            if (a + p == 0)
                                cur = 0;
                            else
                                cur = (p * 100) / (a + p);
                            String current = String.valueOf(cur);
                            myDb.modify(mod_id, mod_sub_name, mod_present, mod_absent, current, mod_min_percent);

                            Intent in=new Intent(context,MainActivity.class);
                            startActivity(in);

                        }
                    }
                });
        return builder.create();
    }
}
