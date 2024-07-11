package com.example.plantapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Home_Adapter extends RecyclerView.Adapter<Home_Adapter.Myviewhelper> {
    Context context;
    List<HomeModel> plants;
    private List<HomeModel> fullList;

    // private List<FavouriteModel> desc=new ArrayList<>();
    // private int[] plantPhoto ;
    public Home_Adapter(Context context, List<HomeModel> plants /*, List<FavouriteModel> Desc*/ /*int[]photos*/) {
        this.context = context;
        this.plants = plants;
        this.fullList = new ArrayList<>(plants) ;
        // this.desc=Desc;
        //  this.plantPhoto=photos;
    }

    @NonNull
    @Override
    public Myviewhelper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_list, parent, false);
        Myviewhelper myviewhelper = new Myviewhelper(view);

        return myviewhelper;
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewhelper holder, int position) {

        holder.textView.setText(plants.get(position).getName());
        holder.imageView.setImageResource(plants.get(position).getImage_id());
        holder.bottomMoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Api.class);
                String plant_name = holder.textView.getText().toString();
                intent.putExtra("plant_name", plant_name);
                context.startActivity(intent);
            }
        });
        // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();

    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    public static class Myviewhelper extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView textView2;
        Button bottomMoreDetails;
        //  Button button1,button2;
        MaterialRippleLayout rippleLayout;

        public Myviewhelper(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.images_home);
            textView = itemView.findViewById(R.id.textname_home);
            rippleLayout = itemView.findViewById(R.id.myprofile_mrl_home);
            bottomMoreDetails = itemView.findViewById(R.id.home_details_buttom);

        }
    }

    public Filter getFilter() {
        return makeFilter;
    }

    private Filter makeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<HomeModel> listfilter = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                listfilter.addAll(fullList);
            } else {
                String filterPattern = constraint.toString().trim().toLowerCase().trim();
                for (HomeModel model : fullList) {
                    if (model.getName().toLowerCase().contains(filterPattern)) {
                        listfilter.add(model);
                    }
                }
            }
            FilterResults results = new FilterResults(); //Holds the results of a filtering operation.
            results.values = listfilter;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            plants.clear();
            plants.addAll((List) results.values);
            notifyDataSetChanged();
            }

        };
    private BottomNavigationView.OnNavigationItemSelectedListener navlistener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment SelectedFragment = null;
            switch (menuItem.getItemId()) {

                case R.id.favourite_home:
                    SelectedFragment = new Favourite();
                    break;
                case R.id.share:
                    SelectedFragment = new Home();
                    break;

            }
            //  getSupportFragmentManager().beginTransaction().replace(R.id.home_menu,SelectedFragment).commit();
            return true;
        }

    };
}

