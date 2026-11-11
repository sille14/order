package com.example.order.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShoppingCart {

    private final Map<Long, CartItem> items =
            new LinkedHashMap<>();

    // Add a book to the shopping cart
    public void addBook(Book book) {

        Objects.requireNonNull(book, "Book cannot be null");

        long isbn = book.getIsbn();

        CartItem item = items.get(isbn);

        if (item == null) {
            items.put(isbn, new CartItem(book));
        } else {
            item.incrementQuantity();
        }
    }

    // Retrieve all cart items
    public List<CartItem> getItems() {
        return List.copyOf(items.values());
    }

    // Calculate the total quantity of books
    public int getTotalQuantity() {
        return items.values().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}