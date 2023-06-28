package com.example.fetchcode.models;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("id")
    int id;
    @SerializedName("listId")
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
}
