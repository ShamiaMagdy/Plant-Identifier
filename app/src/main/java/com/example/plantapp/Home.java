package com.example.plantapp;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private RecyclerView recyclerView;
    List<String> labelList;
    private SearchView searchEditText ;
    private Home_Adapter adapter ;
    int[] img_ID={R.drawable.aser_buergerianum_miq,R.drawable.acer_plamatum,R.drawable.aesculus_chinensis,R.drawable.citrus_reticulate_blanco,
            R.drawable.coriander,R.drawable.ginkgo_biloba_l,R.drawable.indigofera,R.drawable.magnolia,R.drawable.nerium_oleander_l,
            R.drawable.osmanthus_fragrans,R.drawable.peach,R.drawable.viburnum_awabuki_k_koch,R.drawable.quercos_macrokcarba,
            R.drawable.tomato,R.drawable.tilia_cordata,R.drawable.tilia_euro,R.drawable.tilia_tomentosa,R.drawable.grape,R.drawable.tonna,
            R.drawable.ulmus_american,R.drawable.ulmus_glabra};
    
    private List<HomeModel> lstplant;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview= inflater.inflate(R.layout.home,container,false);

        recyclerView=(RecyclerView)rootview.findViewById(R.id.home_recycleview);
        final Home_Adapter adapter=new Home_Adapter(getContext(),lstplant);
        searchEditText= (SearchView) rootview.findViewById(R.id.search_ID);
       /* LinearLayoutManager manager = new LinearLayoutManager(getContext() ,  LinearLayoutManager.VERTICAL , false) ;
        manager.setStackFromEnd(false);
        recyclerView.setLayoutManager(manager);*/
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        searchEditText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return rootview;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstplant=new ArrayList<>();
        labelList = new ArrayList<>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("Labels.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                labelList.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0;i<(img_ID.length);i++)
        {
            lstplant.add(new HomeModel(labelList.get(i),img_ID[i]));
        }



    }






}
