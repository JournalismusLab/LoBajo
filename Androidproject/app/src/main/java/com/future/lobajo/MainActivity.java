package com.future.lobajo;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onMapsClick(View view) {



        LinearLayout linearLayout = findViewById(R.id.linearLayout);



        //View mapFragment = findViewById(R.id.map);

        //linearLayout.addView(mapFragment,0);
    }

}
