package com.example.karaens.fifamanager;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SplFixtures extends AppCompatActivity {

    ListView splListView;
    ArrayList<fixelement> splFixList=new ArrayList<>();
    String team;
    byte[] mBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spl_fixtures);

        team=FixlistAdapter.team;
        mBytes=getIntent().getByteArrayExtra("bytes");

        fixelement fixture;
        for(int i=0;i<Fixtures.Fixlist.size();i++)
        {
            fixture=Fixtures.Fixlist.get(i);
            if((team.equalsIgnoreCase(fixture.getTeam1()))||
                    (team.equalsIgnoreCase(fixture.getTeam2()))) {
                splFixList.add(fixture);
            }
        }
        TextView teamName=findViewById(R.id.teamName);
        ImageView teamIcon=findViewById(R.id.teamIcon);
        splListView=findViewById(R.id.splListView);
        teamName.setText(team);
        teamIcon.setImageBitmap(BitmapFactory.decodeByteArray(mBytes,0,mBytes.length));

        FixlistAdapter adapter=new FixlistAdapter(this,R.layout.fixelement_layout,splFixList);
        splListView.setAdapter(adapter);

    }
}
