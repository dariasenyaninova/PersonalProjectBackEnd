package com.example.demo.controller;

import com.example.demo.model.Item;
import com.example.demo.service.ShoppingCartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShoppingCartController.class)
public class ShoppingCartControllerTest {
    @MockBean
    private ShoppingCartService cartService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void addItem_ValidToken_ReturnsItemAdded() throws Exception {
        when(cartService.addItem(1L, "validToken")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shop/cart/add")
                        .param("itemId", "1")
                        .param("token", "validToken"))
                .andExpect(status().isOk())
                .andExpect(content().string("Item added"));

        verify(cartService, times(1)).addItem(1L, "validToken");
    }

    @Test
    void addItem_InvalidToken_ReturnsInvalidToken() throws Exception {
        when(cartService.addItem(1L, "invalidToken")).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shop/cart/add")
                        .param("itemId", "1")
                        .param("token", "invalidToken"))
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid token"));

        verify(cartService, times(1)).addItem(1L, "invalidToken");
    }

    @Test
    void removeItem_ValidToken_ReturnsItemDeleted() throws Exception {
        when(cartService.deleteItem(1L, "validToken")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/shop/cart/remove")
                        .param("itemId", "1")
                        .param("token", "validToken"))
                .andExpect(status().isOk())
                .andExpect(content().string("Item deleted"));

        verify(cartService, times(1)).deleteItem(1L, "validToken");
    }

    @Test
    void removeItem_InvalidToken_ReturnsInvalidToken() throws Exception {
        when(cartService.deleteItem(1L, "invalidToken")).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/shop/cart/remove")
                        .param("itemId", "1")
                        .param("token", "invalidToken"))
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid token"));

        verify(cartService, times(1)).deleteItem(1L, "invalidToken");
    }

    @Test
    void showCart_ValidToken_ReturnsItemList() throws Exception {
        List<Item> itemList = List.of(
                new Item(1L, "Item 1", "Description 1", 100),
                new Item(2L, "Item 2", "Description 2", 200)
        );
        String jsonList = mapper.writeValueAsString(itemList);

        when(cartService.showCart("validToken")).thenReturn(itemList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shop/cart")
                        .param("token", "validToken"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonList));

        verify(cartService, times(1)).showCart("validToken");
    }
}

