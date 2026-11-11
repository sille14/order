package com.example.order.service;

import com.example.order.model.Book;
import com.example.order.model.ShoppingCart;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class ShoppingCartService {

    private final ShoppingCart shoppingCart;

    public ShoppingCartService() {
        this.shoppingCart = new ShoppingCart();
    }

    public void addBook(Book book) {
        shoppingCart.addBook(book);
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
}
