package com.example.karaens.fifamanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Alert extends AppCompatActivity {

    String s;
    static byte[] img;
    int src;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        TextView ok= findViewById(R.id.textOk);
        TextView no= findViewById(R.id.textNo);

        src=getIntent().getIntExtra("source",0);
        if(src==1)
        {
            s=AddFixture.key;
            img=AddFixture.image;
        }
        else if(src==2)
        {
            s=UpdateItem.key;
            img=UpdateItem.image;
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<Fixtures.Fixlist.size();i++)
                {
                    fixelement fixture=Fixtures.Fixlist.get(i);
                    if(s.equalsIgnoreCase(fixture.getTeam1()))
                    {
                        fixture.setIcon1(img);
                    }
                    if(s.equalsIgnoreCase(fixture.getTeam2()))
                    {
                        fixture.setIcon2(img);
                    }
                }
                if(src==1) {
                    AddFixture.image = img;
                    Intent intent1=new Intent(Alert.this,AddFixture.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent1);
                }
                else if(src==2){
                    UpdateItem.image=img;
                    Intent intent2=new Intent(Alert.this,UpdateItem.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent2);
                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<Fixtures.Fixlist.size();i++)
                {
                    fixelement fixture=Fixtures.Fixlist.get(i);
                    if(s.equalsIgnoreCase(fixture.getTeam1()))
                    {
                        img=fixture.getIcon1();
                        break;
                    }
                    if(s.equalsIgnoreCase(fixture.getTeam2()))
                    {
                        img=fixture.getIcon2();
                        break;
                    }
                }
                if(src==1) {
                    AddFixture.image = img;
                    Intent intent1=new Intent(Alert.this,AddFixture.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent1);
                }
                else if(src==2){
                    UpdateItem.image=img;
                    Intent intent2=new Intent(Alert.this,UpdateItem.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent2);
                }
                }
        });
    }
}
