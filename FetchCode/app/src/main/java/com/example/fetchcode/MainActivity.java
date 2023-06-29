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

import com.example.fetchcode.models.Item;
import com.example.fetchcode.utills.ApiService;
import com.example.fetchcode.utills.NetworkU;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView Welcome;
    TextView IdDataResult;
    Button updateData;
    ListView list;
    ArrayList<Item> itemList;
    ArrayList<String> itemlist2;
//    ArrayAdapter<Item> listAdapt;
    ArrayAdapter<String> listAdapt;
    ArrayAdapter<String> listAdapt2;
    ArrayList<JSONObject>  allDataPoints;
    ArrayList<String> itemString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Welcome = findViewById(R.id.Welcome);
        IdDataResult = findViewById(R.id.Id_Data_Result);
        updateData = findViewById(R.id.update_data);
        list = findViewById(R.id.list_Of_Res);
        itemList = new ArrayList<Item>();
        itemString = new ArrayList<String>();
        createList();



        ApiService apiService = new ApiService();
        apiService.fetchDataFromApi(new ApiService.ApiCallback() {
            @Override
            public void onSuccess(List<Item> items) {
                // Handle successful API response here
                Comparator<Item> compare = Comparator.comparing(Item -> Item.getId());
                compare = compare.thenComparing(Comparator.comparing(Item -> Item.getName()));

                for (Item item : items) {
                    int id = item.getId();
                    int listId = item.getListId();
                        try {
                            String name = item.getName();
                            if (name != null && !name.isEmpty()) {
                                    Item currentItem = new Item();
                                    currentItem.setId(id);
                                    currentItem.setListId(listId);
                                    currentItem.setName(name);
                                    itemList.add(currentItem);


                                    String tempString;
                                    tempString = "Item Id: " +  currentItem.getListId()  + " , Name:  " + currentItem.getName() + " , Id: " + currentItem.getId();
                                    itemString.add(tempString);
                                }
                            Collections.sort(itemList);
                        } catch (NullPointerException e){
                            System.err.println("NullPointerException occurred for item: " + item);
                            // Continue the loop
                    }

                }
//                listAdapt = new ArrayAdapter<Item>(MainActivity.this, android.R.layout.simple_list_item_1, itemList);
                listAdapt = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, itemString);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.setAdapter(listAdapt);
                    }
                });
//                list.setOnClickListener(new AdapterView.OnItemClickListener()) {
//                    @Override
//                    public void onItemClick(AdapterView<> adapterView, View view, int i, long l) {
//
//                    }
//                }){;

            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle API request failure here
                // Display an error message or retry the request
                throw new RuntimeException(errorMessage);
            }
        });

        updateData.setOnClickListener(this);
    }

    private void createList() {
        itemlist2 = new ArrayList<String>();
        listAdapt2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, itemlist2);
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
            if (!s.isEmpty()) {
                try {
                    parseJson(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        public void parseJson(String data) throws JSONException {
;
            JSONArray jsonArray = null;
            allDataPoints = new ArrayList<JSONObject>();
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
                    if (name != "" == true) {
                        allDataPoints.remove(i);
                    }
                    else{
                        itemlist2.add(allDataPoints.get(i).toString());
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
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    IdDataResult.setText(allDataPoints.toString());
                    list.setAdapter(listAdapt2);
                    listAdapt2.notifyDataSetChanged();
                }
            });

        }
    }
}