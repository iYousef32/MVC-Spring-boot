package com.market.demo.simulator.dao;

import com.market.demo.simulator.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ItemDao extends JpaRepository<Item, Integer> {


}
