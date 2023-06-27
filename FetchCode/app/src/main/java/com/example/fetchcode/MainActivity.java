package com.example.fetchcode;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fetchcode.utills.NetworkU;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView Welcome;
    TextView IdDataResult;
    Button updateData;
    ListView list;
    ArrayList<String> itemList;
    ArrayAdapter<String> listAdapt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Welcome = findViewById(R.id.Welcome);
        IdDataResult = findViewById(R.id.Id_Data_Result);
        updateData = findViewById(R.id.update_data);
        list = findViewById(R.id.list_Of_Res);
        createList();

        updateData.setOnClickListener(this);
    }

    private void createList() {
        itemList = new ArrayList<>();
        listAdapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, itemList);
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
                data = NetworkU.makeHTTPrequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  data;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                if(!s.isEmpty()){
                parseJson(s);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        public void parseJson(String data) throws JSONException {
;
            JSONArray jsonArray = null;
            ArrayList<JSONObject>  allDataPoints = new ArrayList<JSONObject>();
            JSONObject current = null;

            try{
                jsonArray = new JSONArray(data);
            } catch ( JSONException e) {
                e.printStackTrace();
            }
            try {
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject currentObject = jsonArray.getJSONObject(i);
                    allDataPoints.add(currentObject);

                }
            } catch (JSONException e){
                e.printStackTrace();
            }

            for (int i = 0; i < allDataPoints.size();i++) {
                current = allDataPoints.get(i);
                if(current.getString("name") != "null" || current.getString("name") != null){
                    String name = current.getString("name");
                    // filter out unwanted data
                    if (name != "") {
                        allDataPoints.remove(i);
                    }
                    else{
                        itemList.add(allDataPoints.get(i).toString());
                    }
                }
            }
//                for (int i = 0; i < dataArray.length(); i++){
//                JSONObject allData = dataArray.getJSONObject(i);
//                String listID = allData.get("listId").toString();
//                String name = allData.get("name").toString();
//
//                Collections.sort(dataArray,Collections.reverseOrder());
//                dataArray = Arrays.sort(dataArray,)
//            }
            IdDataResult.setText(allDataPoints.toString());
            list.setAdapter(listAdapt);            listAdapt.notifyDataSetChanged();
        }
    }
}