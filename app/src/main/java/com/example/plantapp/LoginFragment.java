package com.example.plantapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginFragment extends Fragment {
    EditText username,password;
    CircleImageView login;
    database db;
    TextView forget;
    CheckBox Rememberme;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    boolean check;
    String name;
    String pass;
    int id;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.login_fragment,container,false);
        TextView register = (TextView)v.findViewById(R.id.textView15);
        username=(EditText)v.findViewById(R.id.nameEdittext);
        password=(EditText)v.findViewById(R.id.passwordEdittext);
        login=(CircleImageView)v.findViewById(R.id.login_image);
        bundle=new Bundle();
        db=new database(getContext());
        Rememberme=(CheckBox)v.findViewById(R.id.Remember_checkBox2);
        forget=(TextView)v.findViewById(R.id.textView16);
        /////////////////////////////////
        sharedPreferences=getContext().getSharedPreferences("Login"  , Context.MODE_PRIVATE);
        sharedPreferencesEditor=sharedPreferences.edit();
        check = sharedPreferences.getBoolean("saveLogin", false);
        if(check==true)
        {
          //  Intent intent=new Intent(LoginFragment.this,MainActivity.class);
            String passusername= sharedPreferences.getString("Username" ,"");
            String passpassword= sharedPreferences.getString("Password" ,"");
            id=db.getid(passusername,passpassword);
            bundle.putInt("id",id);
            UserProfile userProfile=new UserProfile();
            FragmentTransaction transaction=getFragmentManager().beginTransaction();
            userProfile.setArguments(bundle);
            transaction.replace(R.id.fragment_container,userProfile);
            transaction.commit();
          //  intent.putExtra("user",passusername);
            //startActivity(intent);
        }
        /////////////////////////////////////
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register_Fragment register_fragment=new Register_Fragment();
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,register_fragment);
                transaction.commit();
            }
        });
        //////////////////////////////////////////////////////////////
       /*  name=username.getText().toString();
         pass=password.getText().toString();*/
        ///////////////////////////////////////////////////
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               /* String name=username.getText().toString();
                String pass=password.getText().toString();*/
                name=username.getText().toString();
                pass=password.getText().toString();
                if(!(name.equals(""))&&!(pass.equals(""))) {
                    Cursor cursor = db.login(name, pass);
                    if (cursor != null) {
                        if (cursor.getCount() > 0) {
                            cursor.moveToNext();
                            Toast.makeText(getContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                            /////////////////////////
                            saveToSharedPrefernces();
                            ////////////////////////
                            id=db.getid(name,pass);
                            Toast.makeText(getContext(), " "+ id, Toast.LENGTH_LONG).show();
                            bundle.putInt("id",id);
                            UserProfile userProfile=new UserProfile();
                            FragmentTransaction transaction=getFragmentManager().beginTransaction();
                            userProfile.setArguments(bundle);
                            transaction.replace(R.id.fragment_container,userProfile);
                            transaction.commit();

                        } else {
                            Toast.makeText(getContext(), "Enter a correct Username or Password or you may  don't have an account please SignUp first ", Toast.LENGTH_LONG).show();

                        }
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Enter complete data", Toast.LENGTH_LONG).show();
                }
            }
        });
        ////////////////////////////////////////////////
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String passusername=username.getText().toString();
                bundle.putString("username",passusername);
                Update_Password update_password=new Update_Password();
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                update_password.setArguments(bundle);
                transaction.replace(R.id.fragment_container,update_password);
                transaction.commit();
            }
        });

        return v;
    }

    public void saveToSharedPrefernces()
    {
        if(Rememberme.isChecked())
        {
            sharedPreferencesEditor.putBoolean("saveLogin" , true);
            sharedPreferencesEditor.putString("Username" , name);
            sharedPreferencesEditor.putString("Password" , pass);
            sharedPreferencesEditor.apply();
            sharedPreferencesEditor.commit();
        }
        else
        {
            sharedPreferencesEditor.putBoolean("saveLogin" , false);
            sharedPreferencesEditor.putString("Username" , "");
            sharedPreferencesEditor.putString("Password" , "");
            sharedPreferencesEditor.apply();
            sharedPreferencesEditor.commit();
        }
    }


}
