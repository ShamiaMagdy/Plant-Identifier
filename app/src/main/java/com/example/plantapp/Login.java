package com.example.plantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static String sharedPrefFile="Preferences_file";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView signUp=(TextView)findViewById(R.id.textView2);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
        Button button=(Button)findViewById(R.id.button);
        final Button save=(Button)findViewById(R.id.button10);
        TextView textView2=(TextView)findViewById(R.id.textView12);
        final EditText username=(EditText)findViewById(R.id.editText);
        final EditText password=(EditText)findViewById(R.id.editText2);
        final CheckBox checkBox=(CheckBox)findViewById(R.id.checkBox);
        TextView textView=(TextView)findViewById(R.id.textView2);//SignUP
        sharedPreferences=getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        //customers.insert_into_Tables();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!username.getText().toString().equals("")&&!password.getText().toString().equals("")) {
                    if(checkBox.isChecked())
                    {
                        Boolean boolIsChecked=checkBox.isChecked();
                        editor=sharedPreferences.edit();
                        editor.putString("Username",username.getText().toString());
                        editor.putString("Password",password.getText().toString());
                        editor.putBoolean("remember",boolIsChecked);
                        editor.apply();
                        //Toast.makeText(getApplicationContext(),"Your information saved ^_^",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        sharedPreferences.edit().clear().apply();
                    }
                    if (username.getText().toString().equals("Donia")&&password.getText().toString().equals("1234")) {
                        Intent intent = new Intent(Login.this, Profile.class);
                        intent.putExtra("cust_username",username.getText().toString());
                        startActivity(intent);
                        //username.getText().clear();
                        //password.getText().clear();

                    } else if (!(username.getText().toString().equals("Donia"))||!(password.getText().toString().equals("1234"))) {
                        Toast.makeText(getApplicationContext(), "Enter correct password and username", Toast.LENGTH_LONG).show();
                        //username.setText("");
                        password.setText("");
                    }
                }
            }
        });
        if(sharedPreferences.contains("Username"))
        {
            String un=sharedPreferences.getString("Username","not found");
            username.setText(un.toString());
        }
        if(sharedPreferences.contains("Password"))
        {
            String p=sharedPreferences.getString("Password","not found");
            password.setText(p.toString());
        }
        if(sharedPreferences.contains("remember"))
        {
            Boolean check=sharedPreferences.getBoolean("remember",false);
            checkBox.setChecked(check);
            /*if(check==true)
            {
                Intent intent=new Intent(Login.this,Home.class);
                startActivity(intent);
            }*/
        }
        /*textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Please Enter New Password",Toast.LENGTH_LONG).show();
                password.getText().clear();
                save.setVisibility(v.VISIBLE);

            }
        });*/
        /*save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customers.changePassword(username.getText().toString(),password.getText().toString());
                Toast.makeText(getApplicationContext(),"Your password changed",Toast.LENGTH_LONG).show();
            }
        });*/

    }
}
