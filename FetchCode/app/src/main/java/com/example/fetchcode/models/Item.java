package com.example.fetchcode.models;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;
import java.util.Objects;

public class Item {
//   implements  Comparable<Item>
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && listId == item.listId && Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, listId, name);
    }

    // Trying to get sort to work with Name //Call//    Collections.sort(itemList, Item.ItemNameSort)
    public static Comparator<Item> ItemNameSort = new Comparator<Item>() {
        @Override
        public int compare(Item item1, Item item2) {
            String name1 = item1.getName();
            String name2 = item2.getName();
            if (name1 == null && name2 == null) {
                return 0;
            } else if (name1 == null) {
                return -1;
            } else if (name2 == null) {
                return 1;
            } else {
                name1 = name1.toLowerCase();
                name2 = name2.toLowerCase();
                return name1.compareTo(name2);
            }
        }
    };
}
