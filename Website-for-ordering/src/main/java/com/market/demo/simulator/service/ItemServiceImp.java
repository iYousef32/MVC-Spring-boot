package com.market.demo.simulator.service;

import com.market.demo.simulator.dao.ItemDao;
import com.market.demo.simulator.entity.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImp implements ItemService{

    ItemDao itemDao;

    EntityManager entityManager;

    @Autowired
    public ItemServiceImp(ItemDao itemDao, EntityManager entityManager){
        this.entityManager = entityManager;
        this.itemDao = itemDao;
    }

    @Override
    public List<Item> findAll() {
        return itemDao.findAll();
    }

    @Override
    public List<Item> findCategory(String category) {

        TypedQuery<Item> query = entityManager.createQuery("select i from Item i where i.category = :data", Item.class);
        query.setParameter("data", category);

        List<Item> itemList = query.getResultList();

        return itemList;
    }

    @Override
    public List<Item> findOtherCategory() {
        TypedQuery<Item> query = entityManager.createQuery("select i from Item i where i.category NOT in " +
                "('Food','Furniture', 'Electronic')", Item.class);

        List<Item> itemList = query.getResultList();

        return itemList;
    }


    @Override
    public Item findItemById(int theId) {
        Optional<Item> result = itemDao.findById(theId);

        Item item = null;
        if(result.isPresent()){
            item = result.get();
        }

        return item;
    }

    @Override
    public List<Item> findItemByName(String name) {
       String theName = name.toLowerCase();

       TypedQuery<Item> query = entityManager.createQuery("select i from Item i where i.name like :data", Item.class);
       query.setParameter("data", "%" + theName + "%");


       List<Item> items = query.getResultList();

       return items;
    }

    @Override
    @Transactional
    public void save(Item item) {
        itemDao.save(item);
    }

    @Override
    @Transactional
    public void update(Item item) {
        entityManager.merge(item);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        itemDao.deleteById(theId);
    }
}
