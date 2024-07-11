package com.example.plantapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.ArrayList;
import java.util.List;

public class Favourite_Adapter extends RecyclerView.Adapter<Favourite_Adapter.Myviewhelper> {
     Context context;
     List<FavouriteModel> plants;
    List<Integer> img_ID_List;
     int pp;
   // private List<FavouriteModel> desc=new ArrayList<>();
   // private int[] plantPhoto ;
    public Favourite_Adapter(Context context,List<FavouriteModel> plants ,List<Integer> img_ID_List/*, List<FavouriteModel> Desc*/ /*int[]photos*/) {
        this.context = context;
        this.plants = plants;
        this.img_ID_List=img_ID_List;
       // this.desc=Desc;
      //  this.plantPhoto=photos;
    }

    @NonNull
    @Override
    public Myviewhelper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favourite_list,parent,false);
        Myviewhelper myviewhelper=new Myviewhelper(view);
        return myviewhelper;
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewhelper holder, final int position) {
//FavouriteModel favouriteModel=names.get(position);

      /*  holder.imageView.setImageResource(plantPhoto[position]);
holder.textView.setText("plant : "+position);
holder.textView2.setText("plant ::: "+position);*/
      holder.textView.setText(plants.get(position).getName());
      holder.imageView.setImageBitmap(plants.get(position).getImage_id());
       //  pp=plants.get(position).getId();
      holder.button1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              database db=new database(context);
              db.deleteFromFavorite(plants.get(position).getId(),img_ID_List.get(position));
              plants.remove(position);
              notifyDataSetChanged();
             // Toast.makeText(context,""+position,Toast.LENGTH_LONG).show();

          }
      });
        holder.moreDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Api.class);
                String plant_name= holder.textView.getText().toString();
                intent.putExtra("plant_name",plant_name);
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return plants.size();
    }

    public static class Myviewhelper extends RecyclerView.ViewHolder {
        ImageView imageView ;
        TextView textView ;
        Button moreDetailsButton ;
        Button button1;
        MaterialRippleLayout rippleLayout ;
        public Myviewhelper(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.images) ;
            textView = itemView.findViewById(R.id.textname) ;
            moreDetailsButton=itemView.findViewById(R.id.details_button1);
            rippleLayout = itemView.findViewById(R.id.myprofile_mrl) ;
            button1=itemView.findViewById(R.id.btn_delete);
        }
    }
}
