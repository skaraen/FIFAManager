package com.example.karaens.fifamanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Fixtures extends AppCompatActivity {

    ListView listViewFix;
    Button buttonAdd;
    Button buttonReset;
    static ArrayList<fixelement> Fixlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);
        listViewFix=findViewById(R.id.listViewFix);

        FixlistAdapter adapter=new FixlistAdapter(this,R.layout.fixelement_layout,Fixlist);
        listViewFix.setAdapter(adapter);

        buttonAdd=findViewById(R.id.buttonAdd);
        buttonReset=findViewById(R.id.buttonReset);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Fixtures.this,AddFixture.class));
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hello.reset(getBaseContext());
                startActivity(new Intent(Fixtures.this,Hello.class));
            }
        });

        listViewFix.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Fixtures.this,ListItem.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        Hello.restore(this);
    }
}
