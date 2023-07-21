package com.market.demo.simulator.entity;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private List<Item> itemList;

    public Order(){

    }

    public Order(List<Item> itemList) {
        this.itemList = itemList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void addItem(Item item){

        if(itemList == null){
            itemList = new ArrayList<>();
        }

        itemList.add(new Item(item.getId(),item.getName(), item.getPrice()));

    }
}
