package com.weather.sss.khoon;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class signup extends AppCompatActivity {

    EditText name, phno, email, area, city, state, zip;
    Spinner bloodgroup;
    String aadhar;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = findViewById(R.id.name);
        phno = (EditText)findViewById(R.id.phno);
        email = (EditText)findViewById(R.id.email);
         area = (EditText)findViewById(R.id.area);
         city = (EditText)findViewById(R.id.city);
         state = (EditText)findViewById(R.id.state);
         zip = (EditText)findViewById(R.id.zip_list);
        bloodgroup = findViewById(R.id.bloodgroup);

        adapter = ArrayAdapter.createFromResource(this,R.array.bloodGroup,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodgroup.setAdapter(adapter);
        bloodgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intent = getIntent();
        aadhar = intent.getExtras().getString("aadhar");
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

    public void addData(View v){
        Button b = findViewById(R.id.register);

        String name1 = name.getText().toString();
        String phno1 = phno.getText().toString();
        String email1 = email.getText().toString();
        String area1 = area.getText().toString();
        String city1 = city.getText().toString();
        String state1 = state.getText().toString();
        String zip1 = zip.getText().toString();
        String bg = bloodgroup.getSelectedItem().toString();

        if(name1.equals("")||phno1.equals("")||area1.equals("")||city1.equals("")||state1.equals("")||email1.equals("")||zip1.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Fields can't be Empty",Toast.LENGTH_LONG).show();
        }
        else if(bg.equalsIgnoreCase("choose blood group"))
        {
            Toast.makeText(getApplicationContext(),"Select Blood Group",Toast.LENGTH_LONG).show();
        }
        else
        {
            if (internet_connection()) {
                new signup.ExecuteTask().execute(name1,phno1,email1,area1,city1,state1,zip1,bg,aadhar);
                b.setText("Loading ...");
            }
            else
            {
              //  Snackbar.make(v, "No Internet Connection !!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Toast.makeText(getApplicationContext(),"Data submitted Successfully.",Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(),"Data Submitted Successfully !! Now You can Donate whenever You get a call. ",Toast.LENGTH_SHORT).show();
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
            HttpPost httpPost = new HttpPost("https://sss-test.000webhostapp.com/khoon/addemp.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("name", value[0]));
            list.add(new BasicNameValuePair("phno", value[1]));
            list.add(new BasicNameValuePair("email", value[2]));
            list.add(new BasicNameValuePair("area", value[3]));
            list.add(new BasicNameValuePair("city", value[4]));
            list.add(new BasicNameValuePair("state", value[5]));
            list.add(new BasicNameValuePair("zip", value[6]));
            list.add(new BasicNameValuePair("bg", value[7]));
            list.add(new BasicNameValuePair("aadhar", value[8]));
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

