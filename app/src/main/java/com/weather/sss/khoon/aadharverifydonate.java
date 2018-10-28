package com.weather.sss.khoon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class aadharverifydonate extends AppCompatActivity {
    EditText aadhar,otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadharverifydonate);
        aadhar = findViewById(R.id.editText);
        otp = findViewById(R.id.editText2);
    }
    public void donateBlood(View view)
    {
        if(otp.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Enter OTP and Verify",Toast.LENGTH_LONG).show();
        }
        else if(otp.getText().toString().equals("1234")){
        Intent intent = new Intent(getApplication(),signup.class);
        intent.putExtra("aadhar", aadhar.getText().toString());
        startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"Incorrect OTP",Toast.LENGTH_LONG).show();
        }

    }
}
