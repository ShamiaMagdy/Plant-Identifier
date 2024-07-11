package com.example.plantapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Camera extends Fragment {

    Button capture_photo,upload_photo;
    ImageView photo;
    private   final int  PERMISSION_CODE =1000;
    Bitmap myimage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.camera,container,false);

photo=(ImageView)rootView.findViewById(R.id.capture_photo);
        capture_photo=(Button) rootView.findViewById(R.id.btn_take_photo);
        upload_photo=(Button)rootView.findViewById(R.id.btn_upload_photo);

        capture_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraintent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(cameraintent,PERMISSION_CODE);
            }
        });

        upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery,100);
            }
        });

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int userID = sharedPref.getInt("userID", 0);
        Toast.makeText(getContext(), "userID= " + userID, Toast.LENGTH_LONG).show();

        SharedPreferences mPrefs =getActivity().getSharedPreferences("IDvalue", 0);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt("userID", userID);
        editor.commit();

        return rootView;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PERMISSION_CODE&&resultCode==getActivity().RESULT_OK){
            myimage = (Bitmap)data.getExtras().get("data");
            //photo.setImageBitmap(myimage);
            //photo.setVisibility(View.VISIBLE);
            /*Intent intent=new Intent(getActivity(),Camera_Result.class);
            intent.putExtra("passphoto",myimage);
            getActivity().startActivity(intent);*/
            Bundle extras = data.getExtras();
            Intent intent=new Intent(getActivity(),Camera_Result.class);
            intent.putExtras(extras);
            startActivity(intent);
        }

        else if(requestCode==100 && resultCode==getActivity().RESULT_OK)
        {
            Uri uri=data.getData();
            photo.setImageURI(uri);

            Intent intent=new Intent(getActivity(),Camera_Result.class);
            intent.putExtra("uri",uri);
            startActivity(intent);
        }
    }




}
