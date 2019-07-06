package com.future.lobajo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.future.lobajo.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity {

    int maxFeedSize = 25;   // setting max size of News-Feed

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        final ArrayList<String> newsList = new ArrayList<>();

        for(int i = 0; i < maxFeedSize; i++) {

            String header = "News Header";  // reading news header from News API

            newsList.add(header);
        }

        ListView feed = findViewById(R.id.feed_lv);
        feed.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newsList));
    }
}