package com.example.demo.controller;

import com.example.demo.model.Item;
import com.example.demo.service.ItemsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/shop")
@AllArgsConstructor
public class ItemController {
    private ItemsService shopService;

    @GetMapping("greetings")
    public String start() {
        return "Welcome to candle shop!";
    }

    @GetMapping("all-items")
    public List<Item> findAllItems() {
        return shopService.findAllItems();
    }

    @GetMapping("Item/{id}")
    public Item findItemById(@PathVariable Long id) {
        return shopService.findItemById(id);
    }

    @PostMapping("save-items")
    public Item saveItem(@RequestBody Item item) {
        return shopService.saveItem(item);
    }

    @PostMapping("edit-item")
    public Item updateItem(@RequestBody Item item) {
        return shopService.updateItem(item);
    }

    @DeleteMapping("delete-item/{id}")
    public void deleteItemById(@PathVariable Long id) {
        shopService.deleteItemById(id);
    }
}


