package com.example.plantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.tensorflow.lite.Interpreter;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
/*Button capture_photo;
ImageView photo;
private  static  final int  PERMISSION_CODE =1000;
    private  static  final int  IMAGE_CAPTURE_CODE =1001;
    Uri image_uri;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*capture_photo=(Button)findViewById(R.id.capture_button);
photo=(ImageView)findViewById(R.id.capture_photo);*/
        //  toolbar=(Toolbar)findViewById(R.id.toolbar);
        //setActionBar(toolbar);    }
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomnavigation);
       bottomNavigationView.setOnNavigationItemSelectedListener(navlistener);
       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
/*int pass_value=getIntent().getExtras().getInt("id");

        Toast.makeText(getApplicationContext(), " "+ pass_value, Toast.LENGTH_LONG).show();*/
      /* capture_photo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                   if(checkSelfPermission(Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED ||
                           checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                       String[]permission={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                       requestPermissions(permission,PERMISSION_CODE);
                   }
                   else {
                       opencamera();
                   }
               }
               else {
                   opencamera();
               }
           }
       });*/
    }
    /*private void opencamera()
    {
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From The Camera");
        image_uri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraintent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraintent,IMAGE_CAPTURE_CODE);

    }*/

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       switch (requestCode)
       {
           case PERMISSION_CODE:{
               if(grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   opencamera();
               }
               else {
                   Toast.makeText(this,"permisson denied ....",Toast.LENGTH_LONG).show();
               }
           }
       }
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK)
        {
            photo.setImageURI(image_uri);
        }
    }*/

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment SelectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.account:
                    SelectedFragment=new LoginFragment();
                    break;
                case R.id.favourite:
                    SelectedFragment=new Favourite();
                    break;
                case R.id.Home:
                    SelectedFragment=new Home();
                    break;
                case R.id.camera:
                    SelectedFragment=new Camera();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,SelectedFragment).commit();
            return true;
        }

    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_navigation,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account:

                return true;
        }
        return false;
    }
}
