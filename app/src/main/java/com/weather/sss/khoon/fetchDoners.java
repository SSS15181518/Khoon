package com.weather.sss.khoon;
import java.lang.String;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class fetchDoners extends AsyncTask<Void,Void,Void>{
    String data ="";
    String dataParsed = "";
    String singleParsed ="";
    ArrayList<JSONObject> donersJson;
    @Override
    protected Void doInBackground(Void... voids) {
        try {

            URL url1 = new URL("https://sss-test.000webhostapp.com/khoon/doners.php");
            HttpURLConnection httpURLConnection1 = (HttpURLConnection) url1.openConnection();
            httpURLConnection1.getInputStream();

            URL url = new URL("https://sss-test.000webhostapp.com/khoon/doners.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }


            JSONArray JA = new JSONArray(data);
            donersJson = new ArrayList<JSONObject>(0);

            for(int i =0 ;i <JA.length(); i++){
                JSONObject JO = (JSONObject) JA.get(i);

                /*String category = JO.getString("category");
                if (JO.equals("1")) {
                    feedbackJson.add(JO);
                }*/

                if ((search.search_area.equalsIgnoreCase(JO.getString("area")))||search.search_area.equalsIgnoreCase(JO.getString("city"))||search.search_area.equalsIgnoreCase(JO.getString("zip"))||search.search_area.equalsIgnoreCase(JO.getString("state"))) {
                    donersJson.add(JO);
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //feedback.data.setText(this.dataParsed);
        BloodAdapter fa = new BloodAdapter(donersJson);
        search.doners.setAdapter(fa);
        search.progressBar.setVisibility(View.GONE);
        search.loading.setVisibility(View.GONE);

    }

    private class BloodAdapter extends ArrayAdapter<JSONObject> {

        private final LayoutInflater li;

        BloodAdapter(ArrayList<JSONObject> doners) {
            super(search.search, R.layout.listofdoners, doners);
            li = LayoutInflater.from(search.search);
        }

        @Override
        public View getView(int pos, View view, ViewGroup parent) {
            view = li.inflate(R.layout.listofdoners, parent, false);
            JSONObject JO = donersJson.get(pos);
            TextView name = view.findViewById(R.id.name_list);
            TextView phno = view.findViewById(R.id.phno_list);
            TextView email = view.findViewById(R.id.email_list);
            TextView area = view.findViewById(R.id.area_list);
            TextView city = view.findViewById(R.id.city_list);
            TextView state = view.findViewById(R.id.state_list);
            TextView zip = view.findViewById(R.id.zip_list);
            TextView bg = view.findViewById(R.id.bloodgroup_list);

            try {

                name.setText(JO.getString("name"));
                phno.setText(JO.getString("phno"));
                email.setText(JO.getString("email"));
                area.setText(JO.getString("area"));
                city.setText(JO.getString("city"));
                state.setText(JO.getString("state"));
                zip.setText(JO.getString("zip"));
                bg.setText(JO.getString("bg"));

            } catch (Exception e) {

            }

            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(search.search, eid.getText(), Toast.LENGTH_SHORT).show();
                }
            });*/

            return view;
        }
    }
}
