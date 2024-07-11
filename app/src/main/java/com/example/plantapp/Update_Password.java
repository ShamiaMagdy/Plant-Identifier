package com.example.plantapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Update_Password extends Fragment {
    EditText username , newpass;
    Button savechanges;
    database db;
    String userName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.update_password,container,false);
        username=(EditText)v.findViewById(R.id.editText7);
        newpass =(EditText)v.findViewById(R.id.editText10);
        savechanges=(Button)v.findViewById(R.id.button5);
        db=new database(getContext());

        Bundle bundle=getActivity().getIntent().getExtras();
        if(bundle!=null)
        {
            userName=bundle.getString("username");
            username.setText(userName);

        }

        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldusername = username.getText().toString();
                String newpassword=  newpass.getText().toString();
                db.forget_password(oldusername,newpassword);
                Toast.makeText(getContext(),"Password changed successfully",Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }
}
