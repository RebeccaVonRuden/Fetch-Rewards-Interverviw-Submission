package com.example.fetchcode;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.example.fetchcode.utills.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView Welcome;
    TextView IdDataResult;
    Button update_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Welcome = findViewById(R.id.Welcome);
        IdDataResult = findViewById(R.id.IdDataResult);
        update_data = findViewById(R.id.update_data);

        update_data.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.update_data){
            try {
                getData();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getData() throws MalformedURLException {
        Uri uri = Uri.parse("https://fetch-hiring.s3.amazonaws.com/hiring.json")
                .buildUpon().build();
        URL url = new URL(uri.toString());
        new accessAPI().execute(url);
    }

    class accessAPI extends AsyncTask<URL,Void,String>{
        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String data = null;
            try{
                data = Network.makeHTTPrequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  data;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                parseJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        public void parseJson(String data) throws JSONException {
            JSONObject jsonObject = null;
            JSONArray dataArray = null;
            try{
                jsonObject = new JSONObject(data);
            } catch ( JSONException e) {
                e.printStackTrace();
            }
            try {
                dataArray = jsonObject.getJSONArray("data");
            } catch (JSONException e){
                e.printStackTrace();
            }

            for (int i = 0; i < dataArray.length(); i++){
                JSONObject allData = dataArray.getJSONObject(i);
                String name = allData.get("name").toString();
                String listID = allData.get("listId").toString();
                String id = allData.get("id").toString();
                // filter out unwanted data
//                if(name ==""){
//                    // delete entry
//                }
//                else{
//
//                }
            }
        }
    }
}