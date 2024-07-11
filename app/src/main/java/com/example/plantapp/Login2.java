package com.example.plantapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import de.hdodenhof.circleimageview.CircleImageView;

public class Login2 extends AppCompatActivity {
EditText username,password;
CircleImageView login;
database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        TextView register = (TextView)findViewById(R.id.textView15);
        username=(EditText)findViewById(R.id.editText7);
        password=(EditText)findViewById(R.id.editText10);
        login=(CircleImageView)findViewById(R.id.login_image);
        db=new database(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login2.this,Register2.class);
                startActivity(intent);
            }
        });

        /////////////////////////////////////////////////////
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                String pass=password.getText().toString();
                if(!(name.equals(""))&&!(pass.equals(""))) {
                    Cursor cursor = db.login(name, pass);
                    if (cursor != null) {
                        if (cursor.getCount() > 0) {
                            cursor.moveToNext();
                            Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                            int id=db.getid(name,pass);
                            Toast.makeText(getApplicationContext(), " "+ id, Toast.LENGTH_LONG).show();
                           /* Intent intent=new Intent(MainActivity.this,laptops_category.class);
                            startActivity(intent);*/
                            /*Intent intent=new Intent(Login2.this,UserProfile.class);
                            startActivity(intent);*/
                            /*FragmentManager fm=getSupportFragmentManager();
                            UserProfile fragment=new UserProfile();

                            fm.beginTransaction().replace(R.id.fragment_container,fragment).commit();*/
                            /////////////////////////////////////////////////////////
                           /* Intent intent=new Intent(Login2.this,MainActivity.class);
                            intent.putExtra("id",id);
                            startActivity(intent);*/
                           Bundle bundle=new Bundle();
                            bundle.putInt("id",id);
                            UserProfile userProfile=new UserProfile();
                            FragmentManager fragmentManager=getSupportFragmentManager();
                            FragmentTransaction transaction=fragmentManager.beginTransaction();
                            userProfile.setArguments(bundle);
                            transaction.replace(R.id.fragment_container,userProfile);
                            transaction.commit();

                        } else {
                            Toast.makeText(getApplicationContext(), "Enter a correct Username or Password or you may  don't have an account please SignUp first ", Toast.LENGTH_LONG).show();

                        }
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Enter complete data", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
