package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.model.Session;
import com.example.demo.model.ShoppingCart;
import com.example.demo.model.User;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.ShoppingCartRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private ShoppingCartRepo shoppingCartRepo;

    @Mock
    private ItemRepository itemRepository;

    @Test
    void addItem_ValidItemAndToken_ReturnsTrue() {
        Item item = new Item(1L, "Item 1", "Description 1", 100);
        User user = new User("user@example.com", "password");
        Session session = new Session("validToken", user);
        ShoppingCart cart = new ShoppingCart();
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(sessionRepository.findById("validToken")).thenReturn(Optional.of(session));
        when(shoppingCartRepo.findByUserEmail(session.getUser().getEmail())).thenReturn(cart);

        assertTrue(shoppingCartService.addItem(1L, "validToken"));

        verify(itemRepository, times(1)).findById(1L);
        verify(sessionRepository, times(1)).findById("validToken");
        verify(shoppingCartRepo, times(1)).findByUserEmail(session.getUser().getEmail());
        verify(shoppingCartRepo, times(1)).save(cart);
    }

    @Test
    void addItem_FailureIfInvalidToken() {
        Long itemId = 1L;
        String token = "invalidToken";

        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());
        when(sessionRepository.findById(token)).thenReturn(Optional.empty());

        assertFalse(shoppingCartService.addItem(itemId, token));
        verify(shoppingCartRepo, never()).save(any(ShoppingCart.class));
    }

    @Test
    void deleteItem_ShouldRemoveItemFromCart() {
        Long itemId = 1L;
        String token = "validToken";
        Item item = new Item();
        User user = new User();
        user.setEmail("test@example.com");
        Session session = new Session(token, user);
        ShoppingCart cart = new ShoppingCart();
        cart.setItems(new ArrayList<>(Collections.singletonList(item)));

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(sessionRepository.findById(token)).thenReturn(Optional.of(session));
        when(shoppingCartRepo.findByUserEmail(user.getEmail())).thenReturn(cart);

        assertTrue(shoppingCartService.deleteItem(itemId, token));
        assertFalse(cart.getItems().contains(item));
        verify(shoppingCartRepo).save(cart);
    }

    @Test
    void showCart_ValidToken_ReturnsItems() {
            String token = "validToken";
            User user = new User();
            user.setEmail("test@example.com");
            Session session = new Session(token, user);
            ShoppingCart cart = new ShoppingCart();
            List<Item> expectedItems = new ArrayList<>(Collections.singletonList(new Item()));
            cart.setItems(expectedItems);

            when(sessionRepository.findById(token)).thenReturn(Optional.of(session));
            when(shoppingCartRepo.findByUserEmail(user.getEmail())).thenReturn(cart);

            List<Item> items = shoppingCartService.showCart(token);
            assertEquals(expectedItems, items);
        }

    @Test
    void showCart_InvalidToken_ReturnsEmptyList() {
        when(sessionRepository.findById("invalidToken")).thenReturn(Optional.empty());

        List<Item> items = shoppingCartService.showCart("invalidToken");

        assertTrue(items.isEmpty());
        verify(sessionRepository, times(1)).findById("invalidToken");
        verify(shoppingCartRepo, never()).findByUserEmail(anyString());
    }
}