package com.example.demo.service;

import com.example.demo.model.Item;

import java.util.List;

public interface ShoppingCartService {
    boolean addItem(Long itemId, String token);
    boolean deleteItem(Long itemId, String token);
    List<Item> showCart(String token);
}
