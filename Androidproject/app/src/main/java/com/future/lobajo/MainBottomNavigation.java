package com.future.lobajo;

import android.content.Intent;
import android.os.Bundle;

import com.future.lobajo.Activities.ARActivity;
import com.future.lobajo.Activities.MapsActivity;
import com.future.lobajo.Activities.NewsFeedActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.widget.TextView;

public class MainBottomNavigation extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent2 = new Intent(MainBottomNavigation.this, NewsFeedActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent = new Intent(MainBottomNavigation.this, MapsActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
                    Intent intent3 = new Intent(MainBottomNavigation.this, ARActivity.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottom_navigation);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
