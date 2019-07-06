package com.future.lobajo;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.future.lobajo.Activities.MapsActivity;
import com.future.lobajo.R;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_news_feed);

    }

    public void onMapsClick(View view) {



        LinearLayout linearLayout = findViewById(R.id.linearLayout);



        //View mapFragment = findViewById(R.id.map);

        //linearLayout.addView(mapFragment,0);
    }

}
