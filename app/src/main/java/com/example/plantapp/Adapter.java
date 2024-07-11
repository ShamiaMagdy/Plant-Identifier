package com.example.plantapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

public class Adapter extends BaseAdapter {
    List<AdapterModel> history_Model;
    Context context;
    int resource;
    List<Integer> img_ID_List;
    public Adapter(@NonNull Context context, int resource, List<AdapterModel> history_Model, List<Integer> img_ID_List) {
        //super(context, R.layout.history_recycle_view);
        this.history_Model=history_Model;
        this.resource=resource;
        this.context=context;
        this.img_ID_List=img_ID_List;
    }

    @Override
    public int getCount() {
        return history_Model.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder=new ViewHolder();
        if(convertView==null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.history_recycle_view, parent, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView7);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView7);
            viewHolder.detailsButton = (Button) convertView.findViewById(R.id.details_button);
            viewHolder.deletButton=(Button)convertView.findViewById(R.id.button3);
            viewHolder.favButton=(Button)convertView.findViewById(R.id.fav_button3);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.imageView.setImageBitmap(history_Model.get(position).getImage_id());
        viewHolder.textView.setText(history_Model.get(position).getName());
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Api.class);
                String plant_name= finalViewHolder1.textView.getText().toString();
                intent.putExtra("plant_name",plant_name);
                context.startActivity(intent);
            }
        });
        viewHolder.deletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database db=new database(context);
                db.deleteFromHistory(history_Model.get(position).getId(),img_ID_List.get(position));
                history_Model.remove(position);
                notifyDataSetChanged();
            }
        });
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database db=new database(context);
                db.updateFavorite(history_Model.get(position).getId(),img_ID_List.get(position));
                Toast.makeText(context, "done", Toast.LENGTH_LONG).show();
                finalViewHolder.favButton.setTextColor(ContextCompat.getColor(context, R.color.colorAqua));
            }
        });
        return convertView;
    }
    static class ViewHolder
    {
        TextView textView;
        ImageView imageView;
        Button detailsButton;
        Button deletButton;
        Button favButton;
    }

}
