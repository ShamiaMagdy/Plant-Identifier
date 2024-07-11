package com.example.plantapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.android.volley.VolleyLog.TAG;

public class UserProfile extends Fragment implements PopupMenu.OnMenuItemClickListener{

    String userName;
    TextView profile_textView;
    database db;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    Bundle bundle;

    private int[] img_id={R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4};
    private String name="";
    private String description="";
    Bitmap bitmaps;
    Adapter historyAdapter;
    List<AdapterModel> historyModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_profile,container,false);
        ListView listView=(ListView)v.findViewById(R.id.listView1);
        ImageView imageView_Setting=(ImageView)v.findViewById(R.id.imageView6);
        CircleImageView circleImageView=(CircleImageView)v.findViewById(R.id.profile_image);
        TextView deleteHistory=(TextView)v.findViewById(R.id.textView3);
        profile_textView=(TextView)v.findViewById(R.id.textView6);
        db=new database(getContext());
        imageView_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(getActivity().getApplicationContext(),v);
                popupMenu.setOnMenuItemClickListener(UserProfile.this);
                popupMenu.inflate(R.menu.menu);
                popupMenu.show();

            }
        });
        /*Bundle bundle=getActivity().getIntent().getExtras();
        if(bundle!=null)
        {
            userName=bundle.getString("UserName");
            profile_textView.setText(userName);
            circleImageView.setImageResource(R.drawable.pimg);
        }*/


//mtoggle=new ActionBarDrawerToggle(getActivity().getApplicationContext(),drawerLayout,toolbar,"","");
        Bundle bb=getArguments();
        int res = bb.getInt("id");
        Cursor cursor=db.getData1(res);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            userName=cursor.getString(1);
            profile_textView.setText(userName);
            Toast.makeText(getContext(), " " + res, Toast.LENGTH_LONG).show();
            byte[]img=cursor.getBlob(4);
            Bitmap bt= BitmapFactory.decodeByteArray(img,0,img.length);
            circleImageView.setImageBitmap(bt);
        }

        SharedPreferences sharedPref = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("userID", res);
        editor.commit();

        db=new database(getContext());
        Cursor cursor1=db.viewHistory(res);
        historyModel=new ArrayList<>();
        final List<Integer> image_ID_list=new ArrayList<Integer>();
        while(!cursor1.isAfterLast())
        {
            name=(cursor1.getString(3));
            byte[] img=cursor1.getBlob(2);
            bitmaps=(BitmapFactory.decodeByteArray(img,0,img.length));
            description=(" ");
            image_ID_list.add(cursor1.getInt(0));
            historyModel.add(new AdapterModel(name,description,bitmaps,res));
            cursor1.moveToNext();
        }
        historyAdapter = new Adapter(getActivity(),R.layout.history_recycle_view,historyModel,image_ID_list);
        listView.setAdapter(historyAdapter);

        deleteHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteHistory();
                historyModel.clear();
                historyAdapter.notifyDataSetChanged();
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuVisibility(true);


    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Login:
              /*  Intent intent=new Intent(getActivity(),Login2.class);
                getActivity().startActivity(intent);*/
                LoginFragment userProfile=new LoginFragment();
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,userProfile);
                transaction.commit();
                return true;
            case R.id.Logout:
                sharedPreferences=getContext().getSharedPreferences("Login"  , Context.MODE_PRIVATE);
                sharedPreferencesEditor=sharedPreferences.edit();
                sharedPreferencesEditor.putBoolean("saveLogin" , false);
                sharedPreferencesEditor.putString("Username" , "");
                sharedPreferencesEditor.putString("Password" , "");
                sharedPreferencesEditor.apply();
                sharedPreferencesEditor.commit();
                /*SharedPreferences preferences = getActivity().getPreferences( Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();*/
                LoginFragment userProfile1=new LoginFragment();
                userProfile1.check=false;
                FragmentTransaction transaction1=getFragmentManager().beginTransaction();
                transaction1.replace(R.id.fragment_container,userProfile1);
                transaction1.commit();
                SharedPreferences sharedPref = getActivity().getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("userID", 0);
                editor.commit();
                //getActivity().finish();
                return true;

        }
        return false;
    }
}