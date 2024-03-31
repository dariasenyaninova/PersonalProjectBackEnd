package com.example.demo.service.impl;

import com.example.demo.model.Item;
import com.example.demo.repository.PostgreItemRepository;
import com.example.demo.service.ItemsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostgreItemService implements ItemsService {
    private PostgreItemRepository repository;
    @Override
    public List<Item> findAllItems() {
        return repository.findAll();
    }

    @Override
    public Item findItemById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Item saveItem(Item item) {
        return repository.save(item);
    }

    @Override
    public Item updateItem(Item item) {
        return repository.save(item);
    }

    @Override
    public void deleteItemById(Long id) {
        repository.deleteById(id);
    }
}
