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


    public static Comparator<Item> ItemListIdSort = new Comparator<Item>() {
        @Override
        public int compare(Item item, Item i1) {

            int id1 = Integer.valueOf(item.getListId());
            int id2 = Integer.valueOf(i1.getListId());
            return Integer.compare(id1, id2);
        }

    };

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
    public static Comparator<Item> ItemListIdName = new Comparator<Item>() {
        @Override
        public int compare(Item item, Item i1) {
            int id1 = Integer.valueOf(item.getListId());
            int id2 = Integer.valueOf(i1.getListId());
            String name1 = item.getName();
            String name2 = item.getName();
            name1 = name1.toLowerCase();
            name2 = name2.toLowerCase();
            if(Integer.compare(id1, id2) == 0){
                return name1.compareTo(name2);
            }
            return Integer.compare(id1, id2);
        }
    };

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

}
