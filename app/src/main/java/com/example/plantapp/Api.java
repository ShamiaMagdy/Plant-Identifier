package com.example.plantapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Api extends AppCompatActivity {
    private RequestQueue queue;
    TextView common_name ;
    TextView scientific_name;
    TextView family_common_name ;
    TextView family;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        final String ss= getIntent().getStringExtra("plant_name");
        common_name =(TextView) findViewById(R.id.common_name);
        scientific_name =(TextView) findViewById(R.id.scientific_name);
        TextView plantname=(TextView)findViewById(R.id.plant_name_Text);
        family_common_name=(TextView) findViewById(R.id.family_name);
        family=(TextView) findViewById(R.id.family);

        queue= Volley.newRequestQueue(this);

        Button btn = (Button) findViewById(R.id.queryButton);

        plantname.setText(ss);
        jsonParse(ss);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                String url = "https://en.wikipedia.org/wiki/" + ss;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    private void jsonParse(String ss)
    {
        String url ="https://trefle.io/api/v1/plants/search?token=lJJ96KcotlrVWyTNtLuuds8xIGHBBC2CdLi-CM73FIY&q="+ss;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(0);
                                String coName = employee.getString("common_name");
                                String  sc_name = employee.getString("scientific_name");
                                String Family = employee.getString("family");
                                String familyCommonName= employee.getString("family_common_name");
                                common_name.setText(coName);
                                scientific_name.setText(sc_name);
                                family.setText(Family);
                                family_common_name.setText(familyCommonName);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    };

}

    /*Button btn = (Button) findViewById(R.id.queryButton);
       /* btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"this",Toast.LENGTH_LONG).show();
                String query = textViewName.getText().toString();
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.setClassName("com.google.android.googlequicksearchbox", "com.google.android.googlequicksearchbox.SearchActivity");
                intent.putExtra("query", query);
                startActivity(intent);



// Add the request to the RequestQueue.
               // queue.add(jj);
            }
        });
    }
}
*/