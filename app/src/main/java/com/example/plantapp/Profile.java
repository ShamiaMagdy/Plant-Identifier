package com.example.plantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    String userName;
    TextView profile_textView;
    private int[] img_id={R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4};
    private String[] name={"Plant1","Plant2","Plant3","Plant4"};
    private String[] description={"This is Plant1","This is Plant2","This is Plant3","This is Plant4"};
    Adapter historyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ListView listView=(ListView)findViewById(R.id.listView1);
        ImageView imageView_Setting=(ImageView)findViewById(R.id.imageView6);
        CircleImageView circleImageView=(CircleImageView)findViewById(R.id.profile_image);
        profile_textView=(TextView)findViewById(R.id.textView6);
        imageView_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(Profile.this,v);
                popupMenu.setOnMenuItemClickListener(Profile.this);
                popupMenu.inflate(R.menu.menu);
                popupMenu.show();

            }
        });
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            userName=bundle.getString("UserName");
            profile_textView.setText(userName);
            circleImageView.setImageResource(R.drawable.pimg);
        }
        //historyAdapter = new Adapter(this, name, img_id, description);
        //listView.setAdapter(historyAdapter);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Login:
                Intent intent=new Intent(Profile.this,Login.class);
                startActivity(intent);
                return true;
            case R.id.Logout:
                return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
