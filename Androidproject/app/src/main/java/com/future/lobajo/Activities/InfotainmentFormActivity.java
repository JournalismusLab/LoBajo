package com.future.lobajo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.future.lobajo.R;

public class InfotainmentFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infotainment_form);
    }

    public void onClickSubmit(){

        Intent changeBacktoARIntent = new Intent(this,ARActivity.class);
        startActivity(changeBacktoARIntent);
    }
}
