package com.example.plantapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter_result extends ArrayAdapter {
    List<String> name;
    int[] image;
    Context context;
    public Adapter_result(@NonNull Context context,List<String> name, int[] image) {
        super(context, R.layout.search_recycle_view);
        this.image=image;
        this.name=name;
        this.context=context;
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder=new ViewHolder();
        if(convertView==null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.search_recycle_view, parent, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.search_imageView);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.name_textView);
            viewHolder.moreDetailsButton=(Button)convertView.findViewById(R.id.more_details_buttom);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.imageView.setImageResource(image[position]);
        viewHolder.textView.setText(name.get(position));
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.moreDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Api.class);
                String plant_name= finalViewHolder.textView.getText().toString();
                intent.putExtra("plant_name",plant_name);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    static class ViewHolder
    {
        TextView textView;
        ImageView imageView;
        Button moreDetailsButton;
    }

}
