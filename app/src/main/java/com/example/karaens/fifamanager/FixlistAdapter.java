package com.example.karaens.fifamanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FixlistAdapter extends ArrayAdapter<fixelement>{
    private Context mContext;
    int mResource;
    String mt1,mt2,mdate,mvenue,mtime;
    byte[] micon1,micon2;
    TextView textTeam1,textTeam2,textDate,textTime,textVenue;
    ImageView imageIcon1,imageIcon2;
    Bitmap bm1,bm2;
    static String team;

    public FixlistAdapter(@NonNull Context context, int resource, @NonNull List<fixelement> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        mt1=getItem(position).getTeam1();
        mt2=getItem(position).getTeam2();
        mdate=getItem(position).getDate();
        mtime=getItem(position).getTime();
        mvenue=getItem(position).getVenue();
        micon1=getItem(position).getIcon1();
        micon2=getItem(position).getIcon2();

        LayoutInflater inflater=LayoutInflater.from(mContext);
        if(convertView==null){
           convertView=inflater.inflate(mResource,parent,false);
        }

        textTeam1=convertView.findViewById(R.id.textTeam1);
        textTeam2=convertView.findViewById(R.id.textTeam2);
        textDate=convertView.findViewById(R.id.textDate);
        textTime=convertView.findViewById(R.id.textTime);
        textVenue=convertView.findViewById(R.id.textVenue);
        imageIcon1=convertView.findViewById(R.id.imageIcon1);
        imageIcon2=convertView.findViewById(R.id.imageIcon2);

        textTeam1.setText(mt1);
        textTeam2.setText(mt2);
        textDate.setText(mdate);
        textTime.setText(mtime);
        textVenue.setText(mvenue);

        bm1= BitmapFactory.decodeByteArray(micon1,0,micon1.length);
        bm2= BitmapFactory.decodeByteArray(micon2,0,micon2.length);
        imageIcon1.setImageBitmap(bm1);
        imageIcon2.setImageBitmap(bm2);

        imageIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(mContext,SplFixtures.class);
              team=Fixtures.Fixlist.get(position).getTeam1();
              intent.putExtra("bytes",Fixtures.Fixlist.get(position).getIcon1());
              mContext.startActivity(intent);
            }
        });
        imageIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SplFixtures.class);
                team=Fixtures.Fixlist.get(position).getTeam2();
                intent.putExtra("bytes",Fixtures.Fixlist.get(position).getIcon2());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }
}
