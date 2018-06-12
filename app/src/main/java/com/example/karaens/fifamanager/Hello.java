package com.example.karaens.fifamanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class Hello extends AppCompatActivity {

    Context mContext=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        Button start=findViewById(R.id.buttonStart);
        Button reset=findViewById(R.id.reset);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Hello.this,Fixtures.class));
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset(mContext);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        restore(mContext);
        }
        static void reset(Context context)
        {
            DatabaseHelper myDb=new DatabaseHelper(context);
            int res=myDb.resetData();
            if(res==0)
            {
                Toast.makeText(context,"Database reset successful",Toast.LENGTH_LONG).show();
                Fixtures.Fixlist.clear();
            }
            else
                Toast.makeText(context,"Reset unsuccessful",Toast.LENGTH_LONG).show();
        }
       static void restore(Context context){
           DatabaseHelper myDb=new DatabaseHelper(context);
           Cursor res=myDb.getData();
           if(res.getCount()==0){
               Toast.makeText(context,"Database is empty.",Toast.LENGTH_LONG).show();
           }
           else{
               Fixtures.Fixlist.clear();
               while (res.moveToNext()){
                fixelement fixture=new fixelement(res.getString(1),res.getString(2),res.getString(3),
                                                  res.getString(4),res.getString(5),res.getBlob(6),
                                                  res.getBlob(7));
                Fixtures.Fixlist.add(fixture);
               }
           }

       }

    }

