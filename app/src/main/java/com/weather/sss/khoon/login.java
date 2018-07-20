package com.weather.sss.khoon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void donateBlood(View view)
    {
        Intent intent = new Intent(getApplication(),signup.class);
        startActivity(intent);
    }

    public void searchBlood(View view)
    {
        Intent intent = new Intent(getApplication(),search.class);
        startActivity(intent);
    }


}
