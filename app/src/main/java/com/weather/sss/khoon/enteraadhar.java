package com.weather.sss.khoon;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class enteraadhar extends AppCompatActivity {
    EditText aadhar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enteraadhar);
        aadhar = findViewById(R.id.editText3);
    }


    boolean internet_connection() {
        //Check if connected to internet, output accordingly
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }




    public void del(View v){

        String aadhar1 = aadhar.getText().toString();


        if(aadhar1.equals("")) {
            Toast.makeText(getApplicationContext(), "Fields can't be Empty", Toast.LENGTH_LONG).show();
        }
        else
        {
            if (internet_connection()) {
                new enteraadhar.ExecuteTask().execute(aadhar1,"0");
            }
            else
            {
                Toast.makeText(getApplicationContext(),"No Internet Connection !!",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void delN(View v){

        String aadhar1 = aadhar.getText().toString();


        if(aadhar1.equals("")) {
            Toast.makeText(getApplicationContext(), "Fields can't be Empty", Toast.LENGTH_LONG).show();
        }
        else
        {
            if (internet_connection()) {
                new enteraadhar.ExecuteTask().execute(aadhar1,"90");
            }
            else
            {
                Toast.makeText(getApplicationContext(),"No Internet Connection !!",Toast.LENGTH_LONG).show();
            }
        }
    }

    class ExecuteTask extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... params) {
            String res;
            res = PostData(params);
            return res;
        }
        @Override
        protected void onPostExecute(String result)
        {

            if(result.equals("success"))
            {
                Toast.makeText(getApplicationContext(),"Data deleted Successfully !!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(),login.class);
                startActivity(intent);
                finish();
            }
            if(result.equals("success90"))
            {
                Toast.makeText(getApplicationContext(),"Your Data hidden Successfully for 90 days!!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(),login.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

            }

        }}

    public String PostData(String[] value)
    {
        String s = "";
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("https://sss-test.000webhostapp.com/khoon/delete.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("aadhar", value[0]));
            list.add(new BasicNameValuePair("del", value[1]));
            //end new data
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            s = readResponse(httpResponse);
        }
        catch (Exception exception)
        {

        }
        return s;
    }
    public String readResponse(HttpResponse res)
    {
        InputStream is = null;
        String return_text = "";
        try
        {
            is = res.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while((line = bufferedReader.readLine())!=null)
            {
                sb.append(line);
            }

            return_text = sb.toString();
        }catch(Exception e)
        {

        }
        return return_text;
    }


}
