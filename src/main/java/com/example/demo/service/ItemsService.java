package com.example.demo.service;

import com.example.demo.model.Item;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ItemsService {

    List<Item> findAllItems();
    Item findItemById(Long id);
    Item saveItem(Item item);
    Item updateItem(Item item);
    void deleteItemById(Long id);


}
