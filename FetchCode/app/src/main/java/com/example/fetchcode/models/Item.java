package com.example.fetchcode.models;

import com.google.gson.annotations.SerializedName;

public class Item implements Comparable<Item>{
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
        listId= newListId;
    }
    public void setName(String newName) {
        name = newName;
    }


//    @Override
//    public int compareTo(Item item) {
//        if (this.listId == item.listId) {
//            return this.name.compareTo(item.name);
//        } else {
//            return Integer.compare(this.listId, item.listId);
//        }
//    }

    @Override
    public int compareTo(Item item) {
        return this.getListId() > item.getListId() ? 1: (this.getListId() < item.getListId() ? -1 : 0);
    }

}
