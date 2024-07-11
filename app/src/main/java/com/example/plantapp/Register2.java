package com.example.plantapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register2 extends AppCompatActivity {
CircleImageView profile_image, showimage;
EditText username , email , password;
Button register , show;
database db;

int REQUEST_CODE_GALLARY=999;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE_GALLARY)
        {
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLARY);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"you don't have permission to access gallery",Toast.LENGTH_LONG);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE_GALLARY && resultCode==RESULT_OK&&data!=null)
        {
            Uri uri=data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                profile_image.setImageBitmap(bitmap);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        profile_image=(CircleImageView)findViewById(R.id.profile_image);
        username=(EditText)findViewById(R.id.editText7);
        email=(EditText)findViewById(R.id.editText8);
        password=(EditText)findViewById(R.id.editText10);
        register=(Button)findViewById(R.id.button5);
       // show=(Button)findViewById(R.id.button20);
       // showimage=(CircleImageView)findViewById(R.id.show_image);
        db=new database(this);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(Register2.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLARY);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!(username.getText().toString().equals(""))&&!(email.getText().toString().equals(""))&&!(password.getText().toString().equals(""))) {
                    Cursor cursor=db.checkuser(username.getText().toString());
                    if(cursor.getCount()<1) {
                       /* Long r = db.insertData(username.getText().toString(),email.getText().toString(),password.getText().toString(),ImageviewTobyte(profile_image));
                        if (r > 0) {
                            Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Register fail", Toast.LENGTH_LONG).show();
                        }*/
                        try {
                            db.insertData(username.getText().toString(),email.getText().toString(),password.getText().toString(),ImageviewTobyte(profile_image));

                            Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_LONG).show();
                            username.setText("");
                            email.setText("");
                            password.setText("");
                            // profile_image.setImageResource(R.mipmap.ic_launcher);
                            profile_image.setImageResource(R.drawable.profile);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else if(cursor.getCount()>=1)
                    {
                        Toast.makeText(getApplicationContext(), "this username is exist please enter another username", Toast.LENGTH_LONG).show();
                        username.setText("");
                        password.setText("");
                        email.setText("");
                        profile_image.setImageResource(R.drawable.profile);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Complete Data", Toast.LENGTH_LONG).show();
                }


            }
        });

        ////////////////////////////////////////////
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               showimage.setImageBitmap(db.getData(4));
            }
        });

    }
    public byte[] ImageviewTobyte(CircleImageView image){
        Bitmap bitmap =((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bytearray=stream.toByteArray();
        return bytearray;
    }


}
