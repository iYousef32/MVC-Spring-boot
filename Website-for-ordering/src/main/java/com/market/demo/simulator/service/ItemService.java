package com.market.demo.simulator.service;

import com.market.demo.simulator.entity.Item;

import java.util.List;

public interface ItemService {

    List<Item> findAll();

    List<Item> findCategory(String category);

    List<Item> findOtherCategory();

    Item findItemById(int theId);

    List<Item> findItemByName(String name);

    void save(Item item);

    void update(Item item);

    void deleteById(int theId);
}
