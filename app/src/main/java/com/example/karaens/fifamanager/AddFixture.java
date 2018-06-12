package com.example.karaens.fifamanager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddFixture extends AppCompatActivity {

    static TextView af_t1,af_t2,af_date,af_time,af_venue;
    static ImageView af_icon1,af_icon2;
    Button af_cam1,af_cam2,af_gal1,af_gal2,af_add;
    int res_code,flag;
    static int tag;
    Uri uri;
    Bitmap af_b1,af_b2;
    static String key;
    final int source=1;
    static byte[] image;
    byte[] b1,b2;
    ByteArrayOutputStream bis;
    DatabaseHelper myDb=new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fixture);
        af_t1=findViewById(R.id.af_t1);
        af_t2=findViewById(R.id.af_t2);
        af_date=findViewById(R.id.af_date);
        af_time=findViewById(R.id.af_time);
        af_venue=findViewById(R.id.af_venue);
        af_icon1=findViewById(R.id.af_icon1);
        af_icon2=findViewById(R.id.af_icon2);
        af_cam1=findViewById(R.id.af_cam1);
        af_cam2=findViewById(R.id.af_cam2);
        af_gal1=findViewById(R.id.af_gal1);
        af_gal2=findViewById(R.id.af_gal2);
        af_add=findViewById(R.id.af_add);

        af_cam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(af_t1.getText()!=null) {
                    res_code = 1;
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), res_code);
                }
                else
                    Toast.makeText(getBaseContext(),"Fill in the team name first !",Toast.LENGTH_LONG).show();
            }
        });
        af_gal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(af_t1.getText()!=null) {
                    res_code=2;
                    startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT,MediaStore.Images.Media.INTERNAL_CONTENT_URI),res_code);
                }
                else
                    Toast.makeText(getBaseContext(),"Fill in the team name first !",Toast.LENGTH_LONG).show();
            }
        });
        af_cam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(af_t2.getText()!=null) {
                    res_code=3;
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),res_code);
                }
                else
                    Toast.makeText(getBaseContext(),"Fill in the team name first !",Toast.LENGTH_LONG).show();
            }
        });
        af_gal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(af_t2.getText()!=null) {
                    res_code=4;
                    startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT,MediaStore.Images.Media.INTERNAL_CONTENT_URI),res_code);
                }
                else
                    Toast.makeText(getBaseContext(),"Fill in the team name first !",Toast.LENGTH_LONG).show();
            }
        });
        af_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bis=new ByteArrayOutputStream();
                af_b1.compress(Bitmap.CompressFormat.PNG,0,bis);
                b1=bis.toByteArray();
                bis=new ByteArrayOutputStream();
                af_b2.compress(Bitmap.CompressFormat.PNG,0,bis);
                b2=bis.toByteArray();
                fixelement fixture=new fixelement(af_t1.getText().toString(),af_t2.getText().toString(),af_date.getText().toString(),
                                                  af_time.getText().toString(),af_venue.getText().toString(),b1,b2);
                if(fixture!=null){
                    Fixtures.Fixlist.add(fixture);
                    if(myDb.addData(String.valueOf(Fixtures.Fixlist.size()-1),af_t1.getText().toString(),af_t2.getText().toString(),af_date.getText().toString(),
                            af_time.getText().toString(),af_venue.getText().toString(),b1,b2)){
                        Toast.makeText(getBaseContext(),"Fixture added !",Toast.LENGTH_LONG).show();
                    }
                    startActivity(new Intent(AddFixture.this,Fixtures.class));
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
         af_b1=(Bitmap) data.getExtras().get("data");
         key=af_t1.getText().toString();
         checkRepeat(af_b1,key);
         if(flag!=1)
         {
           af_icon1.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));
         }
         else
             tag=1;
        }
        if(resultCode==RESULT_OK&&requestCode==2){
         uri=data.getData();
            try {
                af_b1=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                key=af_t1.getText().toString();
                checkRepeat(af_b1,key);
                if(flag!=1)
                {
                    af_icon1.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));
                }
                else
                    tag=1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(resultCode==RESULT_OK&&requestCode==3){
            af_b2=(Bitmap) data.getExtras().get("data");
            key=af_t2.getText().toString();
            checkRepeat(af_b2,key);
            if(flag!=1)
            {
                af_icon2.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));
            }
            else
                tag=2;
        }
        if(resultCode==RESULT_OK&&requestCode==4){
            uri=data.getData();
            try {
                af_b2=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                key=af_t2.getText().toString();
                checkRepeat(af_b2,key);
                if(flag!=1)
                {
                    af_icon2.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));
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
        tag=0;
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
            Intent intent=new Intent(AddFixture.this,Alert.class);
            intent.putExtra("source",source);
            startActivity(intent);
            }
    }

    @Override
    protected void onStart() {
            super.onStart();
                if (tag == 1) {
                    af_b1 = BitmapFactory.decodeByteArray(image, 0, image.length);
                    af_icon1.setImageBitmap(af_b1);
                }
                if (tag == 2) {
                    af_b2 = BitmapFactory.decodeByteArray(image, 0, image.length);
                    af_icon2.setImageBitmap(af_b2);
                }
            }
        }

