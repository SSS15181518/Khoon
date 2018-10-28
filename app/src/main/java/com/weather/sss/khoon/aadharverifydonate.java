package com.weather.sss.khoon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class aadharverifydonate extends AppCompatActivity {
    EditText aadhar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadharverifydonate);
        aadhar = findViewById(R.id.editText);
    }
    public void donateBlood(View view)
    {
        Intent intent = new Intent(getApplication(),signup.class);
        intent.putExtra("aadhar", aadhar.getText().toString());
        startActivity(intent);
    }
}
