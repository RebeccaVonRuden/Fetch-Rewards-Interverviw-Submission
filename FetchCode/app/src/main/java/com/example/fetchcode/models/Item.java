package com.example.fetchcode.models;

import com.google.gson.annotations.SerializedName;

public class Item implements Comparable<Item>{
    @SerializedName("id")
    int id;
    @SerializedName("ItemId")
    int listId;
    @SerializedName("name")
    String name;

    public int getId()
    {
        return id;
    }

    public int getListId() {
        return listId;
    }

    public String getName()
    {
        return name;
    }

    public void setId(int newId){
        id = newId;
    }

    public void setListId(int newListId){
        listId = newListId;
    }

    public void setName(String newName) {
        name = newName;
    }

    @Override
    public int compareTo(Item item) {
        int result = Integer.compare(this.listId, item.listId);
        if (result == 0) {
            result = this.name.compareTo(item.name);
        }
        return result;
    }
}
