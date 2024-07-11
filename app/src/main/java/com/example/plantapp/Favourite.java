package com.example.plantapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Favourite extends Fragment {

    private  RecyclerView recyclerView;
    private List<FavouriteModel>lstplant;
    private String name="";
    private String description="";
    List<Integer> image_ID_list;
    Bitmap bitmaps;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview= inflater.inflate(R.layout.favourite,container,false);

        recyclerView=(RecyclerView)rootview.findViewById(R.id.favourite_recycleview);
        Favourite_Adapter adapter=new Favourite_Adapter(getContext(),lstplant,image_ID_list);


       /* LinearLayoutManager manager = new LinearLayoutManager(getContext() ,  LinearLayoutManager.VERTICAL , false) ;
        manager.setStackFromEnd(false);
        recyclerView.setLayoutManager(manager);*/
       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return rootview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstplant=new ArrayList<>();
        /*lstplant.add(new FavouriteModel("plant1","this is first plant",R.drawable.shams,1));
        lstplant.add(new FavouriteModel("plant2","this is second plant",R.drawable.flower,2));
        lstplant.add(new FavouriteModel("plant3","this is third plant",R.drawable.flower2,3));
        lstplant.add(new FavouriteModel("plant4","this is fourth plant",R.drawable.flower5,4));
        lstplant.add(new FavouriteModel("plant5","this is fifth plant",R.drawable.flower3,5));
        lstplant.add(new FavouriteModel("plant6","this is six plant",R.drawable.plant2,6));*/

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int userID = sharedPref.getInt("userID", 0);
        Toast.makeText(getContext(), "userID= " + userID, Toast.LENGTH_LONG).show();

        database db=new database(getContext());
        Cursor cursor=db.checkFavorite(userID);
        image_ID_list=new ArrayList<Integer>();
        while(!cursor.isAfterLast())
        {
            name=(cursor.getString(3));
            byte[] img=cursor.getBlob(2);
            bitmaps=(BitmapFactory.decodeByteArray(img,0,img.length));
            description=(" ");
            image_ID_list.add(cursor.getInt(0));
            lstplant.add(new FavouriteModel(name,description,bitmaps,userID));
            cursor.moveToNext();
        }


    }
}
