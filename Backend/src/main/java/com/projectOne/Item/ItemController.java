package com.projectOne.Item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public List<Item> getItems() {
        return itemService.getAllItemsFromDB();
    }

    @PostMapping("/items/add")
    public String addItem(@RequestBody Item item) {

        Item newItem = new Item(item.getItemId(), item.getItemName(), item.getItemPrice(), item.getIsAvailable());
        itemService.addItemsInDB(newItem);

        return "Item Add to DB";
    }

    @PatchMapping("/items/update")
    public String updateItem(@RequestBody Item item){
        itemService.updateItemInDB(item);

        return "Updated Successfully";
    }
    @DeleteMapping("/items/{id}")
    public String deleteItem(@PathVariable int id) {

        itemService.delete(id);
        return "Item deleted from DB";
    }
}
