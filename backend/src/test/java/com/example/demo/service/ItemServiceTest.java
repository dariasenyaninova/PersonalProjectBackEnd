package com.example.demo.service;


import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @InjectMocks
    private ItemsService itemsService;
    @Mock
    private ItemRepository repository;

    @Test
    public void findItemById_exists(){
        Item item = new Item(1L, "Name", "desc", 123);

        when(repository.findById(1L)).thenReturn(Optional.of(item));

        Item actualItem = itemsService.findItemById(1L);

        assertEquals(item, actualItem);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void findItemById_wrong_id(){
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Item actualItem = itemsService.findItemById(1L);

        assertNull(actualItem);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void findAllItems_no_elements(){
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Item> actual = itemsService.findAllItems();

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findAllItems_many_elements(){
        Item item1 = new Item(1L, "Name", "desc", 123);
        Item item2 = new Item(2L, "Name2", "desc2", 234);

        List<Item> list = List.of(item1, item2);

        when(repository.findAll()).thenReturn(list);

        List<Item> actual = itemsService.findAllItems();

        assertEquals(list, actual);
        verify(repository, times(1)).findAll();
    }

    @Test
    void saveItem() {
        Item itemToSave = new Item(1L, "Name", "desc", 123);

        when(repository.save(itemToSave)).thenReturn(itemToSave);

        Item savedItem = itemsService.saveItem(itemToSave);

        assertEquals(itemToSave, savedItem);
        verify(repository, times(1)).save(itemToSave);
    }

    @Test
    void updateItem() {
        Item itemToUpdate = new Item(1L, "Updated Name", "Updated desc", 321);

        when(repository.save(itemToUpdate)).thenReturn(itemToUpdate);

        Item updatedItem = itemsService.updateItem(itemToUpdate);

        assertEquals(itemToUpdate, updatedItem);
        verify(repository, times(1)).save(itemToUpdate);
    }

    @Test
    void deleteItemById() {
        Long itemIdToDelete = 1L;

        itemsService.deleteItemById(itemIdToDelete);

        verify(repository, times(1)).deleteById(itemIdToDelete);
    }


}
