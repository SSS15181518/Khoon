package com.weather.sss.khoon;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class search extends AppCompatActivity {
    public static EditText search1;
    public static ListView doners;
    public static ProgressBar progressBar;
    public  static TextView loading;
    public static search search;
    public static String search_area;
    ImageButton imageButton;


    boolean internet_connection(){
        //Check if connected to internet, output accordingly
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = this;

        doners = findViewById(R.id.doners);
        progressBar = findViewById(R.id.progressBar2);
        loading = findViewById(R.id.loading);
        search1 = findViewById(R.id.editText8);
        imageButton = findViewById(R.id.imageButton);


        progressBar.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);





    }

    public void searchFor(View v){
        InputMethodManager inputManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        search_area = search1.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        search1.setVisibility(View.GONE);
        imageButton.setVisibility(View.INVISIBLE);
        if (internet_connection()) {
            fetchDoners process = new fetchDoners();
            process.execute();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No Internet Connection !!",Toast.LENGTH_LONG).show();
        }
    }
}
