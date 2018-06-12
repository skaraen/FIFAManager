package com.example.karaens.fifamanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateItem extends AppCompatActivity {

    TextView uf_t1,uf_t2,uf_date,uf_time,uf_venue;
    ImageView uf_icon1,uf_icon2;
    Button uf_cam1,uf_cam2,uf_gal1,uf_gal2,uf_update;
    int res_code,flag;
    static int tag;
    Uri uri;
    Bitmap uf_b1,uf_b2;
    static String key;
    final int source=2;
    static byte[] image;
    byte[] b1,b2;
    ByteArrayOutputStream bis;
    DatabaseHelper myDb=new DatabaseHelper(this);
    int p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        p= getIntent().getIntExtra("pos",0);

        fixelement fixture=Fixtures.Fixlist.get(p);
        uf_t1=findViewById(R.id.uf_t1);
        uf_t2=findViewById(R.id.uf_t2);
        uf_date=findViewById(R.id.uf_date);
        uf_time=findViewById(R.id.uf_time);
        uf_venue=findViewById(R.id.uf_venue);
        uf_icon1=findViewById(R.id.uf_icon1);
        uf_icon2=findViewById(R.id.uf_icon2);
        uf_cam1=findViewById(R.id.uf_cam1);
        uf_cam2=findViewById(R.id.uf_cam2);
        uf_gal1=findViewById(R.id.uf_gal1);
        uf_gal2=findViewById(R.id.uf_gal2);
        uf_update=findViewById(R.id.uf_update);

        uf_t1.setText(fixture.getTeam1());
        uf_t2.setText(fixture.getTeam2());
        uf_date.setText(fixture.getDate());
        uf_time.setText(fixture.getTime());
        uf_venue.setText(fixture.getVenue());
        uf_b1=BitmapFactory.decodeByteArray(fixture.getIcon1(),0,fixture.getIcon1().length);
        uf_b2=BitmapFactory.decodeByteArray(fixture.getIcon2(),0,fixture.getIcon2().length);
        uf_icon1.setImageBitmap(uf_b1);
        uf_icon2.setImageBitmap(uf_b2);




        uf_cam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uf_t1.getText()!=null) {
                    res_code = 1;
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), res_code);
                }
                else
                    Toast.makeText(getBaseContext(),"Fill in the team name first !",Toast.LENGTH_LONG).show();
            }
        });
        uf_gal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uf_t1.getText()!=null) {
                    res_code=2;
                    startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT,MediaStore.Images.Media.INTERNAL_CONTENT_URI),res_code);
                }
                else
                    Toast.makeText(getBaseContext(),"Fill in the team name first !",Toast.LENGTH_LONG).show();
            }
        });
        uf_cam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uf_t2.getText()!=null) {
                    res_code=3;
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),res_code);
                }
                else
                    Toast.makeText(getBaseContext(),"Fill in the team name first !",Toast.LENGTH_LONG).show();
            }
        });
        uf_gal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uf_t2.getText()!=null) {
                    res_code=4;
                    startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT,MediaStore.Images.Media.INTERNAL_CONTENT_URI),res_code);
                }
                else
                    Toast.makeText(getBaseContext(),"Fill in the team name first !",Toast.LENGTH_LONG).show();
            }
        });
        uf_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bis=new ByteArrayOutputStream();
                uf_b1.compress(Bitmap.CompressFormat.PNG,0,bis);
                b1=bis.toByteArray();
                bis=new ByteArrayOutputStream();
                uf_b2.compress(Bitmap.CompressFormat.PNG,0,bis);
                b2=bis.toByteArray();
                fixelement fixture=new fixelement(uf_t1.getText().toString(),uf_t2.getText().toString(),uf_date.getText().toString(),
                        uf_time.getText().toString(),uf_venue.getText().toString(),b1,b2);
                if(fixture!=null){
                    if(myDb.updateData(String.valueOf(p),uf_t1.getText().toString(),uf_t2.getText().toString(),uf_date.getText().toString(),
                            uf_time.getText().toString(),uf_venue.getText().toString(),b1,b2)){
                        Toast.makeText(getBaseContext(),"Fixture updated !",Toast.LENGTH_LONG).show();

                    }
                    startActivity(new Intent(UpdateItem.this,Fixtures.class));
                }
                else
                    Toast.makeText(getBaseContext(),"Fill all the fields before submitting !",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==1){
            uf_b1=(Bitmap) data.getExtras().get("data");
            key=uf_t1.getText().toString();
            checkRepeat(uf_b1,key);
            if(flag!=1)
            {
                uf_icon1.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));
            }
            else
                tag=1;
        }
        if(resultCode==RESULT_OK&&requestCode==2){
            uri=data.getData();
            try {
                uf_b1=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                key=uf_t1.getText().toString();
                checkRepeat(uf_b1,key);
                if(flag!=1)
                {
                    uf_icon1.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));
                }
                else
                    tag=1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(resultCode==RESULT_OK&&requestCode==3){
            uf_b2=(Bitmap) data.getExtras().get("data");
            key=uf_t2.getText().toString();
            checkRepeat(uf_b2,key);
            if(flag!=1)
            {
                uf_icon2.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));
            }
            else
                tag=2;
        }
        if(resultCode==RESULT_OK&&requestCode==4){
            uri=data.getData();
            try {
                uf_b2=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                key=uf_t2.getText().toString();
                checkRepeat(uf_b2,key);
                if(flag!=1)
                {
                    uf_icon2.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));
                }
                else
                    tag=2;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    void checkRepeat(Bitmap bitmap,String s)
    {
        flag=0;
        bis=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,bis);
        image=bis.toByteArray();
        fixelement fixture;
        for(int i=0;i<Fixtures.Fixlist.size();i++)
        {
            fixture=Fixtures.Fixlist.get(i);
            if((s.equalsIgnoreCase(fixture.getTeam1()))&&image!=fixture.getIcon1()||
                    (s.equalsIgnoreCase(fixture.getTeam2()))&&(image!=fixture.getIcon2()))
            {
                flag=1;
                break;
            }
        }
        if (flag==1){
            Intent intent=new Intent(UpdateItem.this,Alert.class);
            intent.putExtra("source",source);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (tag == 1) {
            uf_b1 = BitmapFactory.decodeByteArray(image, 0, image.length);
            uf_icon1.setImageBitmap(uf_b1);
        }
        if (tag == 2) {
            uf_b2 = BitmapFactory.decodeByteArray(image, 0, image.length);
            uf_icon2.setImageBitmap(uf_b2);
        }
    }
}

