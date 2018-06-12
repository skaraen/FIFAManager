package com.example.karaens.fifamanager;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListItem extends AppCompatActivity {

    Button buttonUpdate,buttonDelete;
    TextView li_t1,li_t2,li_date,li_time,li_venue;
    ImageView li_icon1,li_icon2;
    int pos;
    fixelement fixture;
    int ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        pos=getIntent().getIntExtra("position",0);

        buttonUpdate=findViewById(R.id.buttonUpdate);
        buttonDelete=findViewById(R.id.buttonDelete);
        li_t1=findViewById(R.id.textTeam1);
        li_t2=findViewById(R.id.textTeam2);
        li_date=findViewById(R.id.textDate);
        li_time=findViewById(R.id.textTime);
        li_venue=findViewById(R.id.textVenue);
        li_icon1=findViewById(R.id.imageIcon1);
        li_icon2=findViewById(R.id.imageIcon2);

        fixture=Fixtures.Fixlist.get(pos);

        li_t1.setText(fixture.getTeam1());
        li_t2.setText(fixture.getTeam2());
        li_date.setText(fixture.getDate());
        li_time.setText(fixture.getTime());
        li_venue.setText(fixture.getVenue());
        li_icon1.setImageBitmap(BitmapFactory.decodeByteArray(fixture.getIcon1(),0,fixture.getIcon1().length));
        li_icon2.setImageBitmap(BitmapFactory.decodeByteArray(fixture.getIcon2(),0,fixture.getIcon2().length));

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListItem.this,UpdateItem.class);
                intent.putExtra("pos",pos);
                startActivity(intent);
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper myDb=new DatabaseHelper(getBaseContext());
                int deletedRows=myDb.deleteData(String.valueOf(pos));
                if(deletedRows>0){
                    Fixtures.Fixlist.remove(pos);
                    for (int i=pos;i<Fixtures.Fixlist.size();i++)
                    {
                        fixture=Fixtures.Fixlist.get(i);
                        myDb.addData(String.valueOf(i),fixture.getTeam1(),fixture.getTeam2(),fixture.getDate(),fixture.getTime(),
                                        fixture.getVenue(),fixture.getIcon1(),fixture.getIcon2());
                        myDb.deleteData(String.valueOf(i+1));
                    }
                    Toast.makeText(getBaseContext(),"Fixture deleted",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getBaseContext(),"Fixture not deleted",Toast.LENGTH_LONG).show();
                Hello.restore(getBaseContext());
                startActivity(new Intent(ListItem.this,Fixtures.class));
            }
        });
    }
}
