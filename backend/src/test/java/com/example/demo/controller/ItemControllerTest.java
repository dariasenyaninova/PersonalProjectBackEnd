package com.example.demo.controller;

import com.example.demo.model.Item;
import com.example.demo.service.ItemsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @MockBean
    private ItemsService itemsService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void findAllItems() throws Exception{
    Item item1 = new Item(1L, "Name", "desc", 123);
    Item item2 = new Item(2L, "Name2", "desc2", 234);

    List<Item> items = List.of(item1, item2);

    String jsonItems = mapper.writeValueAsString(items);

    when(itemsService.findAllItems()).thenReturn(items);

        mockMvc.perform(get("/api/v1/shop/all-items"))
            .andExpect(status().isOk())
            .andExpect(content().json(jsonItems));

    verify(itemsService, times(1)).findAllItems();
}


    @Test
    void findItemById() throws Exception{
        Item item = new Item(1L, "Name", "desc", 123);
        String jsonItem = mapper.writeValueAsString(item);

        when(itemsService.findItemById(1L)).thenReturn(item);

        mockMvc.perform(get("/api/v1/shop/Item/{id}", 1L))
                        .andExpect(status().isOk())
                        .andExpect(content().json(jsonItem));


        verify(itemsService, times(1)).findItemById(1L);
    }


    @Test
    void saveItem() throws Exception {
        Item itemRequest = new Item(null, "Name", "desc", 123);
        Item itemSaved = new Item(1L, "Name", "desc", 123);
        String jsonItemRequest = mapper.writeValueAsString(itemRequest);
        String jsonItemResponse = mapper.writeValueAsString(itemSaved);

        when(itemsService.saveItem(itemRequest)).thenReturn(itemSaved);

        mockMvc.perform(post("/api/v1/shop/save-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonItemRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonItemResponse));


        verify(itemsService, times(1)).saveItem(itemRequest);
    }

    @Test
    void updateItem() throws Exception {
        Item itemToUpdate = new Item(1L, "Updated Name", "Updated desc", 321);
        String jsonItemToUpdate = mapper.writeValueAsString(itemToUpdate);

        when(itemsService.updateItem(any(Item.class))).thenReturn(itemToUpdate);

        mockMvc.perform(post("/api/v1/shop/edit-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonItemToUpdate))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonItemToUpdate));

        verify(itemsService, times(1)).updateItem(any(Item.class));
    }

    @Test
    void deleteItemById() throws Exception {
        Long itemIdToDelete = 1L;

        mockMvc.perform(delete("/api/v1/shop/delete-item/{id}", itemIdToDelete))
                .andExpect(status().isOk());

        verify(itemsService, times(1)).deleteItemById(itemIdToDelete);
    }
}




