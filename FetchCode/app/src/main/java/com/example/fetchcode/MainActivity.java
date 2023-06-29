package com.example.fetchcode;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fetchcode.models.Item;
import com.example.fetchcode.utills.ApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    TextView Welcome;
    ListView list;
    ArrayList<Item> itemList;
    ArrayAdapter<String> listAdapt;
    ArrayList<String> itemString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Welcome = findViewById(R.id.Welcome);
        list = findViewById(R.id.list_Of_Res);
        itemList = new ArrayList<>();
        itemString = new ArrayList<>();


        ApiService apiService = new ApiService();
        apiService.fetchDataFromApi(new ApiService.ApiCallback() {
            @Override
            public void onSuccess(List<Item> items) {
                // Group items by listId
                HashMap<Integer, List<Item>> groupedItems = groupItemsByListId(items);

                // Sort groups by name
                List<List<Item>> sortedGroups = sortGroupsByName(groupedItems);

                // Prepare sorted list for ListView
                prepareListForListView(sortedGroups);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.setAdapter(listAdapt);
                    }
                });

            }

            @Override
            public void onFailure(String errorMessage) {
                // Display an error message
                throw new RuntimeException(errorMessage);
            }
        });

    }
    // Group items by listId
    private HashMap<Integer, List<Item>> groupItemsByListId(List<Item> items) {
        HashMap<Integer, List<Item>> groupedItems = new HashMap<>();
        for (Item item : items) {
            int listId = item.getListId();
            if (!groupedItems.containsKey(listId)) {
                groupedItems.put(listId, new ArrayList<>());
            }
            groupedItems.get(listId).add(item);
        }
        return groupedItems;
    }

    // Sort groups by name
    private List<List<Item>> sortGroupsByName(HashMap<Integer, List<Item>> groupedItems) {
        List<List<Item>> sortedGroups = new ArrayList<>(groupedItems.values());
        Comparator<List<Item>> groupComparator = new Comparator<List<Item>>() {
            @Override
            public int compare(List<Item> group1, List<Item> group2) {
                if (group1.isEmpty() && group2.isEmpty()) {
                    return 0;
                } else if (group1.isEmpty()) {
                    return -1;
                } else if (group2.isEmpty()) {
                    return 1;
                } else {
                    String name1 = group1.get(0).getName();
                    String name2 = group2.get(0).getName();
                    if (name1 == null && name2 == null) {
                        return 0;
                    } else if (name1 == null) {
                        return -1;
                    } else if (name2 == null) {
                        return 1;
                    } else {
                        int num1 = extractNumber(name1);
                        int num2 = extractNumber(name2);
                        return Integer.compare(num1, num2);
                    }
                }
            }
            // Helper method to extract numerical value from a string
            private int extractNumber(String name) {
                String[] parts = name.split("\\s+");
                for (String part : parts) {
                    if (part.matches("\\d+")) {
                        return Integer.parseInt(part);
                    }
                }
                return 0; // Default value if no numerical value found
            }
        };
        Collections.sort(sortedGroups, groupComparator);
        return sortedGroups;
    }

    // Prepare sorted list for ListView
    private void prepareListForListView(List<List<Item>> sortedGroups) {
        for (List<Item> group : sortedGroups) {
            for (Item item : group) {
                String name = item.getName();
                if (name != null && !name.isEmpty()) {
                    itemString.add(prepareString(item.getListId(), item.getName(), item.getId()));
                }
            }
        }
        listAdapt = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, itemString);
    }

    // prepare string for printing
    public static String prepareString(int listId, String name, int id){
        return "List Id: " + listId + ", Name: " + name + ", Id: " + id;
    }
}