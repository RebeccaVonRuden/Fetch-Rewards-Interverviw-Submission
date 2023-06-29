package com.example.fetchcode;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fetchcode.models.Item;
import com.example.fetchcode.utills.ApiService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    TextView Welcome;
    ListView list;
    ArrayList<Item> itemList;
    ArrayList<String> itemlist2;
    ArrayAdapter<String> listAdapt;
    ArrayAdapter<String> listAdapt2;
    ArrayList<JSONObject>  allDataPoints;
    ArrayList<String> itemString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Welcome = findViewById(R.id.Welcome);
        list = findViewById(R.id.list_Of_Res);
        itemList = new ArrayList<Item>();
        itemString = new ArrayList<String>();
        createList();



        ApiService apiService = new ApiService();
        apiService.fetchDataFromApi(new ApiService.ApiCallback() {
            @Override
            public void onSuccess(List<Item> items) {
                // Handle successful API response here
//                Comparator<Item> compare = Comparator.comparing(Item -> Item.getId());
//                compare = compare.thenComparing(Comparator.comparing(Item -> Item.getName()));

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
                                }

                        } catch (NullPointerException e){
                            System.err.println("NullPointerException occurred for item: " + item);
                            // Continue the loop
                    }
                    Collections.sort(itemList);
                    for(int i =0; i < itemList.size();i++){
                        Item currentItem = itemList.get(i);
                        String tempString;
                        tempString = "List Id: " +  currentItem.getListId()  + " , Name:  " + currentItem.getName() + " , Id: " + currentItem.getId();
                        itemString.add(tempString);
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

            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle API request failure here
                // Display an error message or retry the request
                throw new RuntimeException(errorMessage);
            }
        });

    }

    private void createList() {
        itemlist2 = new ArrayList<String>();
        listAdapt2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, itemlist2);
    }
}