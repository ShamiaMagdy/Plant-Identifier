package com.example.plantapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register_Fragment extends Fragment {

    CircleImageView profile_image, showimage;
    EditText username , email , password;
    Button register , show;
    database db;

    int REQUEST_CODE_GALLARY=999;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_register2,container,false);

        profile_image=(CircleImageView)v.findViewById(R.id.profile_image);
        username=(EditText)v.findViewById(R.id.editText7);
        email=(EditText)v.findViewById(R.id.editText8);
        password=(EditText)v.findViewById(R.id.editText10);
        register=(Button)v.findViewById(R.id.button5);
      //  show=(Button)v.findViewById(R.id.button20);
        //showimage=(CircleImageView)v.findViewById(R.id.show_image);
        db=new database(getContext());

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery,100);
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

                            Toast.makeText(getContext(), "Register Successfully", Toast.LENGTH_LONG).show();
                            username.setText("");
                            email.setText("");
                            password.setText("");
                            // profile_image.setImageResource(R.mipmap.ic_launcher);
                            profile_image.setImageResource(R.drawable.profile);

                            Intent intent=new Intent(getActivity(),MainActivity.class);
                            getActivity().startActivity(intent);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else if(cursor.getCount()>=1)
                    {
                        Toast.makeText(getContext(), "this username is exist please enter another username", Toast.LENGTH_LONG).show();
                        username.setText("");
                        password.setText("");
                        email.setText("");
                        profile_image.setImageResource(R.drawable.profile);
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Please Enter Complete Data", Toast.LENGTH_LONG).show();
                }

            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_GALLARY&&resultCode==getActivity().RESULT_OK){
            Bitmap myimage = (Bitmap)data.getExtras().get("data");
            //photo.setImageBitmap(myimage);
            //photo.setVisibility(View.VISIBLE);
            Intent intent=new Intent(getActivity(),Camera_Result.class);
            intent.putExtra("passphoto",myimage);
            getActivity().startActivity(intent);
        }

        else if(requestCode==100 && resultCode==getActivity().RESULT_OK)
        {
            Uri uri=data.getData();
            profile_image.setImageURI(uri);
        }
    }
////////////////////////////////////////////////////////////////////////////
    public byte[] ImageviewTobyte(CircleImageView image){
        Bitmap bitmap =((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bytearray=stream.toByteArray();
        return bytearray;
    }
}
