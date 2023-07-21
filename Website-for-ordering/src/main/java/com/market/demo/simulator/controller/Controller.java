package com.market.demo.simulator.controller;

import com.market.demo.simulator.entity.Item;
import com.market.demo.simulator.entity.Order;
import com.market.demo.simulator.service.ItemService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/item")
public class Controller {

    private int counter = 0;
    private ArrayList<Item> itemList;

    private List<Item> basketList;

    private int basketCounter = 0;

    private Order basketOrder;
    private Order theOrder;

    ItemService itemService;

    @Autowired
    public Controller(ItemService itemService){
        this.itemService = itemService;
    }


    @GetMapping("/home")
    public String displayAllItems(Model theModel){
        List<Item> itemList = itemService.findAll();


        theModel.addAttribute("searchItem", new Item());
        theModel.addAttribute("listItems", itemList);

        return "item-list";
    }

    @GetMapping("/page")
    public String displayGivenCategory(@RequestParam("category") String category,Model theModel){
        List<Item> items = null;
        if(!category.equalsIgnoreCase("Other")) {
            items = itemService.findCategory(category);
        }else {
            items = itemService.findOtherCategory();
        }

        theModel.addAttribute("searchItem", new Item());
        theModel.addAttribute("listItems", items);

        return "result-list";
    }

    @GetMapping("/admin/showFormForAdd")
    public String showFormForAdd(Model theModel){

        theModel.addAttribute("newItem", new Item());
        theModel.addAttribute("searchItem", new Item());

        return "save-form";
    }

    @PostMapping("/admin/saveItem")
    public String saveItem(@ModelAttribute("newItem") Item item){

        itemService.save(item);

        return "redirect:/item/home";
    }

    @PostMapping("/searchForItem")
    public String searchForItem(@ModelAttribute("searchItem") Item item, Model theModel){

        List<Item> items = itemService.findItemByName(item.getName());

        theModel.addAttribute("items", items);
        theModel.addAttribute("searchItem", new Item());

        return "result-item";
    }

    @GetMapping("/showResult")
    public String showResult(@RequestParam("theId") int theId, Model theModel){
        List<Item> items = new ArrayList<>();
               Item theItem = itemService.findItemById(theId);
               items.add(theItem);

        theModel.addAttribute("items", items);
        theModel.addAttribute("searchItem", new Item());

        return "/result-item";
    }

    @GetMapping("/admin/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("itemId") int theId, Model theModel){
        Item item = itemService.findItemById(theId);

        theModel.addAttribute("newItem", item);
        theModel.addAttribute("searchItem", new Item());

        return "save-form";

    }

    @GetMapping("/admin/deleteItem")
    public String deleteItem(@RequestParam("itemId") int theId){
        itemService.deleteById(theId);

        return "redirect:/item/home";
    }

    @GetMapping("/admin/showFormForSell")
    public String showFormForSell(Model theModel){


        List<Item> itemList = new ArrayList<>();
        Order order = new Order(itemList);

        theModel.addAttribute("emptyItem", new Item());
        theModel.addAttribute("item2", itemList);
        theModel.addAttribute("order", order);
        theModel.addAttribute("searchItem", new Item());

        return "sell-form";
    }

    @PostMapping("/admin/showFormForSell2")
    public String showFormForSell2(@ModelAttribute("emptyItem") Item item,Model theModel){

        Item theItem = itemService.findItemById(item.getId());
        Item theItem2 = new Item(theItem.getName(),theItem.getPrice());
        theItem2.setId(theItem.getId());
        if(counter == 0) {
            itemList = new ArrayList<>();
            theOrder = new Order(itemList);
        }
        counter += 1;
        itemList.add(theItem2);
        theOrder.setItemList(itemList);
        theModel.addAttribute("order", theOrder);
        theModel.addAttribute("searchItem", new Item());

        return "sell-form";
    }

    @RequestMapping(value = "/admin/sellItem", method = RequestMethod.POST, params = "btnSell")
    public String sellItem(@ModelAttribute("order") Order order){

        List<Item> itemList = order.getItemList();
        if(itemList != null) {
            for (Item tempItem : itemList) {
                Item item = itemService.findItemById(tempItem.getId());
                if (item.getQuantity() - tempItem.getQuantity() >= 0) {
                    item.setQuantity(item.getQuantity() - tempItem.getQuantity());
                }

                itemService.update(item);
            }
        }
        counter = 0;
        this.itemList = null;
        this.theOrder = null;

        return "redirect:/item/home";
    }

    @GetMapping("/showBasket")
    public String showBasket(@RequestParam("itemId") int theId,Model theModel){

        // in case of remove an item
        if(theId < 0){
            theId = theId * -1;
            Item theItem = itemService.findItemById(theId);
            List<Item> items = basketOrder.getItemList();

            if(items != null) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getId() == theItem.getId()) {
                        items.remove(i);
                    }
                }
            }
            basketCounter -= 1;
            basketOrder.setItemList(items);
            basketList = basketOrder.getItemList();
            theModel.addAttribute("order", basketOrder);
            theModel.addAttribute("searchItem", new Item());
            return "basket-list";
        }else {
            // in case of add an item
            Item theItem = itemService.findItemById(theId);
            Item item = null;
            if (theItem.getQuantity() == 0) {
                item = new Item(theItem.getId(), theItem.getName(), 0, theItem.getPrice(), theItem.getCategory());
            } else {
                item = new Item(theItem.getId(), theItem.getName(), 1, theItem.getPrice(), theItem.getCategory(),theItem.getImage());
            }

            if (basketCounter == 0) {
                basketList = new ArrayList<>();
                basketOrder = new Order(basketList);
            }
            basketCounter += 1;
            basketList.add(item);
            basketOrder.setItemList(basketList);
            theModel.addAttribute("order", basketOrder);
            theModel.addAttribute("searchItem", new Item());

            return "basket-list";
        }
    }

    @GetMapping("/showBasket2")
    public String showBasket2(Model theModel){

        if(basketCounter == 0){
            basketList = new ArrayList<>();
            basketOrder = new Order(basketList);
        }
        theModel.addAttribute("order", basketOrder);
        theModel.addAttribute("searchItem", new Item());

        return "basket-list";
    }

    @PostMapping("/orderItems")
    public String orderItems(@ModelAttribute("order") Order theOrder, Model theModel){

        double sum = 0;

        List<Item> itemList = theOrder.getItemList();

        if(itemList != null) {
            for(Item tempItem : itemList){
                Item item = itemService.findItemById(tempItem.getId());
                if (item.getQuantity() == 0) {
                    itemList.remove(tempItem);
                }
            }
            for (Item tempItem : itemList) {
                Item item = itemService.findItemById(tempItem.getId());
                if (item.getQuantity() - tempItem.getQuantity() >= 0 && item.getQuantity() != 0) {

                    tempItem.setPrice( tempItem.getQuantity() * item.getPrice());

                    sum += tempItem.getPrice();

                }


            }
        }
        basketOrder.setItemList(itemList);
        theModel.addAttribute("sum", sum);
        theModel.addAttribute("order", basketOrder);
        basketCounter = 0;
        this.basketList = null;
        this.basketOrder = null;

        return "payment";
    }

    @PostMapping("/confirm")
    public String confirmOrder(@ModelAttribute("order") Order theOrder) {


        List<Item> itemList = theOrder.getItemList();

        if (itemList != null) {
            for (Item tempItem : itemList) {
                Item item = itemService.findItemById(tempItem.getId());
                if (item.getQuantity() - tempItem.getQuantity() >= 0) {
                    item.setQuantity(item.getQuantity() - tempItem.getQuantity());
                }

                itemService.update(item);
            }
        }

        return "redirect:/item/home";
    }
}
