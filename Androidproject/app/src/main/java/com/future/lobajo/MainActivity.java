package com.future.lobajo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);


        //set to maps view
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View maps_view = vi.inflate(R.layout.activity_maps, linearLayout);


        linearLayout.addView(maps_view,0);

        //container = (ViewGroup) findViewById(R.id.layoutInserPoint);
        //container.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void onNewsClick(View view){
        LinearLayout linearLayout = findViewById(R.id.linearLayout);


        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View news_view = vi.inflate(R.layout.activity_news_feed, linearLayout);




        //remove current shown view
        linearLayout.removeViewAt(0);

        //add view
        linearLayout.addView(news_view,0);

    }

    public void onMapsClick(View view) {



        //set to maps view
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View maps_view = vi.inflate(R.layout.activity_maps, null);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);


        //remove current shown view
        linearLayout.removeViewAt(0);

        //add view
        linearLayout.addView(maps_view,0);
    }

}
