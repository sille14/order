
package com.example.order.service;

import com.example.order.model.Book;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShoppingCartServiceTest {

    @Autowired
    private ShoppingCartService cartService;

    // Simulate an HTTP request for a particular session
    private void useSession(MockHttpSession session) {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request.setSession(session);

        RequestContextHolder.setRequestAttributes(
                new ServletRequestAttributes(request)
        );
    }

    @AfterEach
    void cleanup() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void shoppingCartsAreSeparatedBySession() {

        // Arrange
        Book book = new Book();
        book.setIsbn(9780132350884L);
        book.setTitle("Clean Code");

        MockHttpSession sessionA = new MockHttpSession();
        MockHttpSession sessionB = new MockHttpSession();

        // User A adds a book twice
        useSession(sessionA);

        cartService.addBook(book);
        cartService.addBook(book);

        assertEquals(
                2,
                cartService.getShoppingCart().getTotalQuantity()
        );

        // User B should have an empty cart
        useSession(sessionB);

        assertEquals(
                0,
                cartService.getShoppingCart().getTotalQuantity()
        );

        // User B adds one book
        cartService.addBook(book);

        assertEquals(
                1,
                cartService.getShoppingCart().getTotalQuantity()
        );

        // User A's cart still contains two books
        useSession(sessionA);

        assertEquals(
                2,
                cartService.getShoppingCart().getTotalQuantity()
        );
    }
}
