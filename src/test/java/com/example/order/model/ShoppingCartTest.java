
package com.example.order.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    @Test
    void addBookToEmptyCart() {

        // Arrange
        ShoppingCart cart = new ShoppingCart();

        Book book = new Book();
        book.setIsbn(9780132350884L);
        book.setTitle("Clean Code");

        // Act
        cart.addBook(book);

        // Assert
        assertEquals(1, cart.getItems().size());
        assertEquals(1, cart.getTotalQuantity());
        assertEquals(
                9780132350884L,
                cart.getItems().get(0).getBook().getIsbn()
        );
    }

    @Test
    void addingSameBookIncreasesQuantity() {

        // Arrange
        ShoppingCart cart = new ShoppingCart();

        Book book = new Book();
        book.setIsbn(9780132350884L);
        book.setTitle("Clean Code");

        // Act
        cart.addBook(book);
        cart.addBook(book);

        // Assert
        assertEquals(1, cart.getItems().size());
        assertEquals(2, cart.getTotalQuantity());
        assertEquals(
                2,
                cart.getItems().get(0).getQuantity()
        );
    }

    @Test
    void addingDifferentBooksCreatesSeparateItems() {

        // Arrange
        ShoppingCart cart = new ShoppingCart();

        Book book1 = new Book();
        book1.setIsbn(9780132350884L);
        book1.setTitle("Clean Code");

        Book book2 = new Book();
        book2.setIsbn(9780134685991L);
        book2.setTitle("Effective Java");

        // Act
        cart.addBook(book1);
        cart.addBook(book2);

        // Assert
        assertEquals(2, cart.getItems().size());
        assertEquals(2, cart.getTotalQuantity());
    }

    @Test
    void emptyCartHasZeroBooks() {

        ShoppingCart cart = new ShoppingCart();

        assertTrue(cart.getItems().isEmpty());
        assertEquals(0, cart.getTotalQuantity());
    }
}
